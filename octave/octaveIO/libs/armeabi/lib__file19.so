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
## @deftypefn {Function File} @var{ods} = odsopen (@var{filename})
## @deftypefnx {Function File} @var{ods} = odsopen (@var{filename}, @var{readwrite})
## @deftypefnx {Function File} @var{ods} = odsopen (@var{filename}, @var{readwrite}, @var{reqintf})
## Get a pointer to an OpenOffice_org spreadsheet in the form of return
## argument @var{ods}.
##
## Calling odsopen without specifying a return argument is fairly useless!
##
## To make this function work at all, you need the Java package >= 1.2.5 plus
## ODFtoolkit (version 0.7.5 or 0.8.6+) & xercesImpl, and/or jOpenDocument, and/or
## OpenOffice.org (or clones) installed on your computer + proper javaclasspath
## set. These interfaces are referred to as OTK, JOD, and UNO resp., and are
## preferred in that order by default (depending on their presence).
## For (currently experimental) UNO support, Octave-Java package 1.2.8 + latest
## fixes is imperative; furthermore the relevant classes had best be added to
## the javaclasspath by utility function chk_spreadsheet_support().
##
## @var{filename} must be a valid .ods OpenOffice.org file name including
## .ods suffix. If @var{filename} does not contain any directory path,
## the file is saved in the current directory.
## For UNO bridge, filenames need to be in the form "file:///<path_to_file>/filename";
## a URL will also work. If a plain file name is given (absolute or relative),
## odsopen() will transform it into proper form.
##
## @var{readwrite} must be set to true or numerical 1 if writing to spreadsheet
## is desired immediately after calling odsopen(). It merely serves proper
## handling of file errors (e.g., "file not found" or "new file created").
##
## Optional input argument @var{reqintf} can be used to override the ODS
## interface automatically selected by odsopen. Currently implemented interfaces
## are 'OTK' (Java/ODF Toolkit), 'JOD' (Java/jOpenDocument) and 'UNO'
## (Java/OpenOffice.org UNO bridge).
##
## Examples:
##
## @example
##   ods = odsopen ('test1.ods');
##   (get a pointer for reading from spreadsheet test1.ods)
##
##   ods = odsopen ('test2.ods', [], 'JOD');
##   (as above, indicate test2.ods will be read from; in this case using
##    the jOpenDocument interface is requested)
## @end example
##
## @seealso {odsclose, odsread, oct2ods, ods2oct, odsfinfo, chk_spreadsheet_support}
##
## @end deftypefn

## Author: Philip Nienhuis
## Created: 2009-12-13
## Updates: 
## 2009-12-30 ....<forgot what is was >
## 2010-01-17 Make sure proper dimensions are checked in parsed javaclasspath
## 2010-01-24 Added warning when trying to create a new spreadsheet using 
##            jOpenDocument
## 2010-03-01 Removed check for rt.jar in javaclasspath
## 2010-03-04 Slight texinfo adaptation (reqd. odfdom version = 0.7.5)
## 2010-03-14 Updated help text (section on readwrite)
## 2010-06-01 Added check for jOpenDocument version + suitable warning
## 2010-06-02 Added ";" to supress debug stuff around lines 115
##     ''     Moved JOD version check to subfunc getodsinterfaces
##     ''     Fiddled ods.changed flag when creating a spreadsheet to avoid
##            unnamed 1st sheets
## 2010-08-23 Added version field "odfvsn" to ods file ptr, set in
##            getodsinterfaces() (odfdom)
##     ''     Moved JOD version check to this func from subfunc
##            getodsinterfaces()
##     ''     Full support for odfdom 0.8.6 (in subfunc)
## 2010-08-27 Improved help text
## 2010-10-27 Improved tracking of file changes tru ods.changed
## 2010-11-12 Small changes to help text
##     ''     Added try-catch to file open sections to create fallback to other
##            interface
## 2011-05-06 Experimental UNO support
## 2011-05-18 Creating new spreadsheet docs in UNO now works
## 2011-06-06 Tamed down interface verbosity on first startup
##     ''     Multiple requested interfaces now possible 
## 2011-09-03 Reset chkintf if no ods support found to allow full interface
##            rediscovery (otherwise javaclasspath additions will never be
##            picked up)
## 2012-01-26 Fixed "seealso" help string
## 2012-02-26 Added ";" to suppress echo of filename f UNO
## 2012-06-06 Made interface checking routine less verbose when same requested
##            interface was used consecutively
## 2012-09-03 (in UNO section) replace canonicalize_file_name on non-Windows to
##            make_absolute_filename (see bug #36677)
##     ''     (in UNO section) web adresses need only two consecutive slashes
## 2012-10-07 Moved subfunc getodsinterfaces to ./private
## 2012-10-12 Moved all interface-specific file open stanzas to separate 
##            ./private funcs
## 2012-10-24 Style fixes
##      ''    Removed fall-back options for .sxc. Other than .xls this can be
##            inferred from file suffix
## 2012-12-18 Improved error messages

function [ ods ] = odsopen (filename, rw=0, reqinterface=[])

  persistent odsinterfaces; persistent chkintf; persistent lastintf;
  if (isempty (chkintf))
    odsinterfaces = struct ( "OTK", [], "JOD", [], "UNO", [] );
    chkintf = 1;
  endif
  if (isempty (lastintf)); lastintf = "---"; endif 
  
  if (nargout < 1)
    usage ("ODS = odsopen (ODSfile, [Rw]). But no return argument specified!");
  endif

  if (~isempty (reqinterface))
    if ~(ischar (reqinterface) || iscell (reqinterface))
      usage ("odsopen.m: arg # 3 (interface) not recognized");
    endif
    ## Turn arg3 into cell array if needed
    if (~iscell (reqinterface)), reqinterface = {reqinterface}; endif
    ## Check if previously used interface matches a requested interface
    if (isempty (regexpi (reqinterface, lastintf, "once"){1}))
      ## New interface requested
      odsinterfaces.OTK = 0; odsinterfaces.JOD = 0; odsinterfaces.UNO = 0;
      for ii=1:numel (reqinterface)
        reqintf = toupper (reqinterface {ii});
        ## Try to invoke requested interface(s) for this call. Check if it
        ## is supported anyway by emptying the corresponding var.
        if     (strcmp (reqintf, "OTK"))
          odsinterfaces.OTK = [];
        elseif (strcmp (reqintf, "JOD"))
          odsinterfaces.JOD = [];
        elseif (strcmp (reqintf, "UNO"))
          odsinterfaces.UNO = [];
        else 
          usage (sprintf (["odsopen.m: unknown .ods interface \"%s\" requested.\n" ...
                  "Only OTK, JOD or UNO supported\n"], reqinterface{}));
        endif
      endfor

      printf ("Checking requested interface(s):\n");
      odsinterfaces = getodsinterfaces (odsinterfaces);
      ## Well, is/are the requested interface(s) supported on the system?
      odsintf_cnt = 0;
      for ii=1:numel (reqinterface)
        if (~odsinterfaces.(toupper (reqinterface{ii})))
          ## No it aint
          printf ("%s is not supported.\n", toupper (reqinterface{ii}));
        else
          ++odsintf_cnt;
        endif
      endfor
      ## Reset interface check indicator if no requested support found
      if (~odsintf_cnt)
        chkintf = [];
        ods = [];
        return
      endif
    endif
  endif
  
  ## Var rw is really used to avoid creating files when wanting to read, or
  ## not finding not-yet-existing files when wanting to write a new one.
  ## Be sure it's either 0 or 1 initially
  if (rw), rw = 1; endif      

  ## Check if ODS file exists. Set open mode based on rw argument
  if (rw), fmode = "r+b"; else fmode = "rb"; endif
  fid = fopen (filename, fmode);
  if (fid < 0)
    if (~rw)                  ## Read mode requested but file doesn't exist
      err_str = sprintf ("odsopen.m: file %s not found\n", filename);
      error (err_str)
    else        
      ## For writing we need more info:
      fid = fopen (filename, "rb");  
      ## Check if it can be opened for reading
      if (fid < 0)            ## Not found => create it
        printf ("Creating file %s\n", filename);
        rw = 3;
      else                    ## Found but not writable = error
        fclose (fid);         ## Do not forget to close the handle neatly
        error (sprintf ("odsopen.m: write mode requested but file %s is not writable\n",...
                        filename))
      endif
    endif
  else
    ## Close file anyway to avoid Java errors
    fclose (fid);
  endif

  ## Check for the various ODS interfaces. No problem if they've already
  ## been checked, getodsinterfaces (far below) just returns immediately then.

  [odsinterfaces] = getodsinterfaces (odsinterfaces);

  ## Supported interfaces determined; now check ODS file type.

  chk3 = strcmpi (filename(end-3:end), '.ods');
  ## Only jOpenDocument (JOD) can read from .sxc files, but only if odfvsn = 2
  chk4 = strcmpi (filename(end-3:end), '.sxc');

  ods = struct ("xtype",    [], 
                "app",      [], 
                "filename", [], 
                "workbook", [], 
                "changed",  0, 
                "limits",   [], 
                "odfvsn",   []);

  ## Preferred interface = OTK (ODS toolkit & xerces), so it comes first. 
  ## Keep track of which interface is selected. Can be used for fallback to other intf
  odssupport = 0;

  if (odsinterfaces.OTK && ~odssupport && chk3)
    [ ods, odssupport, lastintf ] = ...
              __OTK_spsh_open__ (ods, rw, filename, odssupport);
  endif

  if (odsinterfaces.JOD && ~odssupport && (chk3 || chk4))
    [ ods, odssupport, lastintf ] = ...
              __JOD_spsh_open__ (ods, rw, filename, odssupport);
  endif

  if (odsinterfaces.UNO && ~odssupport)
    [ ods, odssupport, lastintf ] = ...
              __UNO_spsh_open__ (ods, rw, filename, odssupport);
  endif

  ## if 
  ##   <other interfaces here>

  if (~odssupport)
    ## Below message follows after getodsinterfaces
    printf ("None.\n");
    warning ("odsopen.m: no support for OpenOffice.org .ods I/O"); 
    ods = [];
    chkintf = [];
  else
    # From here on rw is tracked via ods.changed in the various lower
    # level r/w routines and it is only used to determine if an informative
    # message is to be given when saving a newly created ods file.
    ods.changed = rw;

    # ods.changed = 0 (existing/only read from), 1 (existing/data added), 2 (new,
    # data added) or 3 (pristine, no data added).
    # Until something was written to existing files we keep status "unchanged".
    if (ods.changed == 1); ods.changed = 0; endif
  endif

endfunction
