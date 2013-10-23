## Copyright (C) 2009,2010,2011,2012 Philip Nienhuis <prnienhuis at users.sf.net>
## 
## This program is free software; you can redistribute it and/or modify
## it under the terms of the GNU General Public License as published by
## the Free Software Foundation; either version 2 of the License, or
## (at your option) any later version.
## 
## This program is distributed in the hope that it will be useful,
## but WITHOUT ANY WARRANTY; without even the implied warranty of
## MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
## GNU General Public License for more details.
## 
## You should have received a copy of the GNU General Public License
## along with Octave; see the file COPYING.  If not, see
## <http://www.gnu.org/licenses/>.

## -*- texinfo -*-
## @deftypefn {Function File} @var{odsinterfaces} = getodsinterfaces (@var{odsinterfaces})
## Get supported OpenOffice.org .ods file read/write interfaces from
## the system.
## Each interface for which the corresponding field is set to empty
## will be checked. So by manipulating the fields of input argument
## @var{odsinterfaces} it is possible to specify which
## interface(s) should be checked.
##
## Currently implemented interfaces comprise:
## - Java & ODFtoolkit (www.apache.org)
## - Java & jOpenDocument (www.jopendocument.org)
## - Java & UNO bridge (OpenOffice.org)
##
## Examples:
##
## @example
##   odsinterfaces = getodsinterfaces (odsinterfaces);
## @end example

## Author: Philip Nienhuis
## Created: 2009-12-27
## Updates:
## 2010-01-14
## 2010-01-17 Make sure proper dimensions are checked in parsed javaclasspath
## 2010-04-11 Introduced check on odfdom.jar version - only 0.7.5 works properly
## 2010-06-02 Moved in check on JOD version
## 2010-06-05 Experimental odfdom 0.8.5 support
## 2010-06-## dropped 0.8.5, too buggy
## 2010-08-22 Experimental odfdom 0.8.6 support
## 2010-08-23 Added odfvsn (odfdom version string) to output struct argument
##     ''     Bugfix: moved JOD version check to main function (it can't work here)
##     ''     Finalized odfdom 0.8.6 support (even prefered version now)
## 2010-09-11 Somewhat clarified messages about missing java classes
##     ''     Rearranged code a bit; fixed typos in OTK detection code (odfdvsn -> odfvsn)
## 2010-09-27 More code cleanup
## 2010-11-12 Warning added about waning support for odfdom v. 0.7.5
## 2011-05-06 Fixed wrong strfind tests
##     ''     Experimental UNO support added
## 2011-05-18 Forgot to initialize odsinterfaces.UNO
## 2011-06-06 Fix for javaclasspath format in *nix w. java-1.2.8 pkg
##     ''     Implemented more rigid Java check
##     ''     Tamed down verbosity
## 2011-09-03 Fixed order of odsinterfaces.<member> statement in Java detection try-catch
##     ''     Reset tmp1 (always allow interface rediscovery) for empty odsinterfaces arg
## 2011-09-18 Added temporary warning about UNO interface
## 2012-03-22 Improved Java checks (analogous to xlsopen)
## 2012-06-06 Again improved & simplified Java-based interface checking support
## 2012-06-08 Support for odfdom-0.8.8 (-incubator)
## 2012-10-07 Moved common classpath entry code to ./private function
## 2012-10-07 Moved into ./private
## 2012-10-24 Style fixes

function [odsinterfaces] = getodsinterfaces (odsinterfaces)

  ## tmp1 = [] (not initialized), 0 (No Java detected), or 1 (Working Java found)
  persistent tmp1 = []; persistent jcp;  # Java class path
  persistent uno_1st_time = 0;

  if (isempty (odsinterfaces.OTK) && isempty (odsinterfaces.JOD) ...
                                  && isempty (odsinterfaces.UNO))
    ## Assume no interface detection has happened yet
    printf ("Detected ODS interfaces: ");
    tmp1 = [];
  elseif (isempty (odsinterfaces.OTK) || isempty (odsinterfaces.JOD) ...
                                      || isempty (odsinterfaces.UNO))
    ## Can't be first call. Here one of the Java interfaces is requested
    if (~tmp1)
      # Check Java support again
      tmp1 = [];
    endif
  endif
  deflt = 0;

  if (isempty (tmp1))
  ## Check Java support
    try
      jcp = javaclasspath ("-all");          # For java pkg >= 1.2.8
      if (isempty (jcp)), jcp = javaclasspath; endif  # For java pkg <  1.2.8
      ## If we get here, at least Java works. Now check for proper version (>= 1.6)
      jver = ...
        char (java_invoke ('java.lang.System', 'getProperty', 'java.version'));
      cjver = strsplit (jver, ".");
      if (sscanf (cjver{2}, "%d") < 6)
        warning ...
          ("\nJava version too old - you need at least Java 6 (v. 1.6.x.x)\n");
        return
      endif
      ## Now check for proper entries in class path. Under *nix the classpath
      ## must first be split up. In java 1.2.8+ javaclasspath is already a cell array
      if (isunix && ~iscell (jcp));
        jcp = strsplit (char (jcp), pathsep ()); 
      endif
      tmp1 = 1;
    catch
      ## No Java support
      tmp1 = 0;
      if (isempty (odsinterfaces.OTK) || isempty (odsinterfaces.JOD) ...
                                      || isempty (odsinterfaces.UNO))
        ## Some or all Java-based interface explicitly requested; but no Java support
        warning ...
          (" No Java support found (no Java JRE? no Java pkg installed AND loaded?");
      endif
      ## No specific Java-based interface requested. Just return
      odsinterfaces.OTK = 0;
      odsinterfaces.JOD = 0;
      odsinterfaces.UNO = 0;
      printf ("\n");
      return;
    end_try_catch
  endif

  ## Try Java & ODF toolkit
  if (isempty (odsinterfaces.OTK))
    odsinterfaces.OTK = 0;
    entries = {"odfdom", "xercesImpl"};
    ## Only under *nix we might use brute force: e.g., strfind(classpath, classname);
    ## under Windows we need the following more subtle, platform-independent approach:
    if (chk_jar_entries (jcp, entries) >= numel (entries))    
      ## Apparently all requested classes present.
      ## Only now we can check for proper odfdom version (only 0.7.5 & 0.8.6+ work OK).
      ## The odfdom team deemed it necessary to change the version call so we need this:
      odfvsn = " ";
      try
        ## New in 0.8.6
        odfvsn = ...
          java_invoke ("org.odftoolkit.odfdom.JarManifest", "getOdfdomVersion");
      catch
        odfvsn = ...
          java_invoke ("org.odftoolkit.odfdom.Version", "getApplicationVersion");
      end_try_catch
      ## For odfdom-incubator (= 0.8.8+), strip extra info
      odfvsn = regexp (odfvsn, '\d\.\d\.\d', "match"){1};
      if  ~(strcmp (odfvsn, "0.7.5") || strcmp (odfvsn, "0.8.6") ...
         || strcmp (odfvsn, "0.8.7") || strfind (odfvsn, "0.8.8"))
        warning ("\nodfdom version %s is not supported - use v. 0.8.6 or later\n", odfvsn);
      else
        if (strcmp (odfvsn, "0.7.5"))
          warning (["odfdom v. 0.7.5 support won't be maintained " ...
                    "- please upgrade to 0.8.6 or higher."]); 
        endif
        odsinterfaces.OTK = 1;
        printf ("OTK");
        if (deflt), printf ("; "); else, printf ("*; "); deflt = 1; endif
      endif
      odsinterfaces.odfvsn = odfvsn;
    else
      warning ("\nNot all required classes (.jar) in classpath for OTK");
    endif
  endif

  ## Try Java & jOpenDocument
  if (isempty (odsinterfaces.JOD))
    odsinterfaces.JOD = 0;
    entries = {"jOpenDocument"};
    if (chk_jar_entries (jcp, entries) >= numel (entries))
      odsinterfaces.JOD = 1;
      printf ("JOD");
      if (deflt), printf ("; "); else, printf ("*; "); deflt = 1; endif
    else
      warning ("\nNot all required classes (.jar) in classpath for JOD");
    endif
  endif

  ## Try Java & UNO
  if (isempty (odsinterfaces.UNO))
    odsinterfaces.UNO = 0;
    ## entries(1) = not a jar but a directory (<000_install_dir/program/>)
    entries = {"program", "unoil", "jurt", "juh", "unoloader", "ridl"};
    if (chk_jar_entries (jcp, entries) >= numel (entries))
      odsinterfaces.UNO = 1;
      printf ("UNO");
      if (deflt)
        printf ("; ");
      else
        printf ("*; "); 
        deflt = 1; 
        uno_1st_time = min (++uno_1st_time, 2); 
      endif
    else
      warning ("\nOne or more UNO classes (.jar) missing in javaclasspath");
    endif
  endif
  
  ## ---- Other interfaces here, similar to the ones above

  if (deflt), printf ("(* = active interface)\n"); endif

  ## FIXME the below stanza should be dropped once UNO is stable.
  ## Echo a suitable warning about experimental status:
  if (uno_1st_time == 1)
    ++uno_1st_time;
    printf ("\nPLEASE NOTE: UNO (=OpenOffice.org-behind-the-scenes) is EXPERIMENTAL\n");
    printf ("After you've opened a spreadsheet file using the UNO interface,\n");
    printf ("odsclose on that file will kill ALL OpenOffice.org invocations,\n");
    printf ("also those that were started outside and/or before Octave!\n");
    printf ("Trying to quit Octave w/o invoking odsclose will only hang Octave.\n\n");
  endif
  
endfunction
