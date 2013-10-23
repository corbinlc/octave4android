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
## @deftypefn {Function File} @var{xlsinterfaces} = getxlsinterfaces (@var{xlsinterfaces})
## Get supported Excel .xls file read/write interfaces from the system.
## Each interface for which the corresponding field is set to empty
## will be checked. So by manipulating the fields of input argument
## @var{xlsinterfaces} it is possible to specify which
## interface(s) should be checked.
##
## Currently implemented interfaces comprise:
## - ActiveX / COM (native Excel in the background)
## - Java & Apache POI
## - Java & JExcelAPI
## - Java & OpenXLS (only JRE >= 1.4 needed)
## - Java & UNO bridge (native OpenOffice.org in background) - EXPERIMENTAL!!
##
## Examples:
##
## @example
##   xlsinterfaces = getxlsinterfaces (xlsinterfaces);
## @end example

## Author: Philip Nienhuis
## Created: 2009-11-29
## Last updates: 
## 2009-12-27 Make sure proper dimensions are checked in parsed javaclasspath
## 2010-09-11 Rearranged code and clarified messages about missing classes
## 2010-09-27 More code cleanup
## 2010-10-20 Added check for minimum Java version (should be >= 6 / 1.6)
## 2010-11-05 Slight change to reporting to screen
## 2011-02-15 Adapted to javaclasspath calling style of java-1.2.8 pkg
## 2011-03-26 OpenXLS support added
##      ''    Bug fix: javaclasspath change wasn't picked up between calls with req.intf
## 2011-05-18 Experimental UNO support added
## 2011-05-29 Reduced verbosity
## 2011-06-06 Fix for javaclasspath format in *nix w java-1.2.8 pkg
## 2011-06-13 Fixed potentially faulty tests for java classlib presence
## 2011-09-03 Fixed order of xlsinterfaces.<member> statements in Java detection try-catch
##      ''    Reset tmp1 (always allow interface rediscovery) for empty xlsinterfaces arg
## 2011-09-08 Minor code cleanup
## 2011-09-18 Added temporary warning about UNO interface
## 2012-03-01 Changed UNO warning so that it is suppressed when UNO is not yet chosen
## 2012-03-07 Only check for COM if run on Windows
## 2012-03-21 Print newline if COM found but no Java support
##     ''     Improved logic for finding out what interfaces to check
##     ''     Fixed bugs with Java interface checking (tmp1 initialization)
## 2012-06-06 Improved & simplified Java check code
## 2012-09-03 Check for matching .jar names & javaclasspath was reversed (oops)
## 2012-10-07 Moved common classpath entry code to private function
## 2012-10-24 Style fixes
## 2012-12-18 POI 3.9 support (either xbeans.jar or xmlbeans.jar), see chk_jar_entries.m

function [xlsinterfaces] = getxlsinterfaces (xlsinterfaces)

  ## tmp1 = [] (not initialized), 0 (No Java detected), or 1 (Working Java found)
  persistent tmp1 = []; persistent tmp2 = []; persistent jcp;  ## Java class path
  persistent uno_1st_time = 0;

  if  (isempty (xlsinterfaces.COM) && isempty (xlsinterfaces.POI) ...
    && isempty (xlsinterfaces.JXL) && isempty (xlsinterfaces.OXS) ...
    && isempty (xlsinterfaces.UNO))
    ## Looks like first call to xlsopen. Check Java support
    printf ("Detected XLS interfaces: ");
    tmp1 = [];
  elseif (isempty (xlsinterfaces.COM) || isempty (xlsinterfaces.POI) ... 
       || isempty (xlsinterfaces.JXL) || isempty (xlsinterfaces.OXS) ...
       || isempty (xlsinterfaces.UNO))
    ## Can't be first call. Here one of the Java interfaces is requested
    if (~tmp1)
      ## Check Java support again
      tmp1 = [];
    endif
  endif
  deflt = 0;

  ## Check if MS-Excel COM ActiveX server runs (only on Windows!)
  if (ispc && isempty (xlsinterfaces.COM))
    xlsinterfaces.COM = 0;
    if (ispc)
      try
        app = actxserver ("Excel.application");
        ## If we get here, the call succeeded & COM works.
        xlsinterfaces.COM = 1;
        ## Close Excel. Yep this is inefficient when we need only one r/w action,
        ## but it quickly pays off when we need to do more with the same file
        ## (+, MS-Excel code is in OS cache anyway after this call so no big deal)
        app.Quit();
        delete(app);
        printf ("COM");
        if (deflt), printf ("; "); else, printf ("*; "); deflt = 1; endif
      catch
        ## COM non-existent. Only print message if COM is explicitly requested (tmp1==[])
        if (~isempty (tmp1))
          printf ("ActiveX not working; no Excel installed?\n"); 
        endif
      end_try_catch
    endif
  endif

  if (isempty (tmp1))
    ## Try if Java package works properly by invoking javaclasspath
    try
      jcp = javaclasspath ("-all");                   ## For java pkg > 1.2.7
      if (isempty (jcp)), jcp = javaclasspath; endif  ## For java pkg < 1.2.8
      ## If we get here, at least Java works. Now check for proper version (>= 1.6)
      jver = char (java_invoke ("java.lang.System", "getProperty", "java.version"));
      cjver = strsplit (jver, '.');
      if (sscanf (cjver{2}, "%d") < 6)
        warning ("\nJava version might be too old - you need at least Java 6 (v. 1.6.x.x)\n");
        return
      endif
      ## Now check for proper entries in class path. Under *nix the classpath
      ## must first be split up. In java 1.2.8+ javaclasspath is already a cell array
      if (isunix && ~iscell (jcp)); jcp = strsplit (char (jcp), pathsep); endif
      tmp1 = 1;
    catch
      ## No Java support found
      tmp1 = 0;
      if (isempty (xlsinterfaces.POI) || isempty (xlsinterfaces.JXL)...
        || isempty (xlsinterfaces.OXS) || isempty (xlsinterfaces.UNO))
        ## Some or all Java-based interface(s) explicitly requested but no Java support
        warning (" No working Java support found. Java pkg properly installed?");
      endif
      ## Set Java interfaces to 0 anyway as there's no Java support
      xlsinterfaces.POI = 0;
      xlsinterfaces.JXL = 0;
      xlsinterfaces.OXS = 0;
      xlsinterfaces.UNO = 0;
      printf ("\n");
      ## No more need to try any Java interface
      return
    end_try_catch
  endif

  ## Try Java & Apache POI
  if (isempty (xlsinterfaces.POI))
    xlsinterfaces.POI = 0;
    ## Check basic .xls (BIFF8) support
    entries = {"poi-3", "poi-ooxml-3"};
    ## Only under *nix we might use brute force: e.g., strfind (classname, classpath);
    ## under Windows we need the following more subtle, platform-independent approach:
    if (chk_jar_entries (jcp, entries) >= numel (entries))
      xlsinterfaces.POI = 1;
      printf ("POI");
    endif
    ## Check OOXML support
    entries = {{"xbean", "xmlbean"}, "poi-ooxml-schemas", "dom4j"};
    if (chk_jar_entries (jcp, entries) >= numel (entries)), printf (" (& OOXML)"); endif
    if (xlsinterfaces.POI)
      if (deflt), printf ("; "); else, printf ("*; "); deflt = 1; endif
    endif
  endif

  ## Try Java & JExcelAPI
  if (isempty (xlsinterfaces.JXL))
    xlsinterfaces.JXL = 0;
    entries = {"jxl"};
    if (chk_jar_entries (jcp, entries) >= numel (entries))
      xlsinterfaces.JXL = 1;
      printf ("JXL");
      if (deflt), printf ("; "); else, printf ("*; "); deflt = 1; endif
    endif
  endif

  ## Try Java & OpenXLS
  if (isempty (xlsinterfaces.OXS))
    xlsinterfaces.OXS = 0;
    entries = {"openxls"};
    if (chk_jar_entries (jcp, entries) >= numel (entries))
      xlsinterfaces.OXS = 1;
      printf ("OXS");
      if (deflt), printf ("; "); else, printf ("*; "); deflt = 1; endif
    endif
  endif

  ## Try Java & UNO
  if (isempty (xlsinterfaces.UNO))
    xlsinterfaces.UNO = 0;
    ## entries0(1) = not a jar but a directory (<00o_install_dir/program/>)
    entries = {"program", "unoil", "jurt", "juh", "unoloader", "ridl"};
    if (chk_jar_entries (jcp, entries) >= numel (entries))
      xlsinterfaces.UNO = 1;
      printf ("UNO");
      if (deflt);
        printf ("; "); 
      else
        printf ("*; ");
        deflt = 1; 
        uno_1st_time = min (++uno_1st_time, 2);
      endif
    endif
  endif

  ## ---- Other interfaces here, similar to the ones above

  if (deflt), printf ("(* = active interface)\n"); endif

  ## FIXME the below stanza should be dropped once UNO is stable.
  # Echo a suitable warning about experimental status:
  if (uno_1st_time == 1)
    ++uno_1st_time;
    printf ("\nPLEASE NOTE: UNO (=OpenOffice.org-behind-the-scenes) is EXPERIMENTAL\n");
    printf ("After you've opened a spreadsheet file using the UNO interface,\n");
    printf ("xlsclose on that file will kill ALL OpenOffice.org invocations,\n");
    printf ("also those that were started outside and/or before Octave!\n");
    printf ("Trying to quit Octave w/o invoking xlsclose will only hang Octave.\n\n");
  endif

endfunction
