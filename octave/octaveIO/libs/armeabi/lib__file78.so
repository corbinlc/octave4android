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
## @deftypefn {Function File} @var{xls} = xlsopen (@var{filename})
## @deftypefnx {Function File} @var{xls} = xlsopen (@var{filename}, @var{readwrite})
## @deftypefnx {Function File} @var{xls} = xlsopen (@var{filename}, @var{readwrite}, @var{reqintf})
## Get a pointer to an Excel spreadsheet in the form of return argument
## (file pointer struct) @var{xls}. After processing the spreadsheet,
## the file pointer must be explicitly closed by calling xlsclose().
##
## Calling xlsopen without specifying a return argument is fairly useless!
##
## To make this function work at all, you need MS-Excel (95 - 2003), and/or
## the Java package >= 1.2.8 plus Apache POI >= 3.5 and/or JExcelAPI and/or
## OpenXLS and/or OpenOffice.org (or clones) installed on your computer +
## proper javaclasspath set. These interfaces are referred to as COM, POI,
## JXL, OXS, and UNO, resp., and are preferred in that order by default
## (depending on their presence).
## For OOXML support, in addition to Apache POI support you also need the
## following jars in your javaclasspath: poi-ooxml-schemas-3.5.jar,
## xbean.jar and dom4j-1.6.1.jar (or later versions). Later OpenOffice.org
## versions (UNO) have support for OOXML as well.
## Excel'95 spreadsheets can only be read by JExcelAPI and OpenOffice.org.
##
## @var{filename} should be a valid .xls or xlsx Excel file name (including
## extension). But if you use the COM interface you can specify any extension
## that your installed Excel version can read AND write; the same goes for UNO
## (OpenOffice.org). Using the other Java interfaces, only .xls or .xlsx are
## allowed. If @var{filename} does not contain any directory path, the file
## is saved in the current directory.
##
## If @var{readwrite} is set to 0 (default value) or omitted, the Excel file
## is opened for reading. If @var{readwrite} is set to True or 1, an Excel
## file is opened (or created) for reading & writing.
##
## Optional input argument @var{reqintf} can be used to override the Excel
## interface that otherwise is automatically selected by xlsopen. Currently
## implemented interfaces (in order of preference) are 'COM' (Excel/COM),
## 'POI' (Java/Apache POI), 'JXL' (Java/JExcelAPI), 'OXS' (Java/OpenXLS), or
## 'UNO' (Java/OpenOffice.org - EXPERIMENTAL!).
## In most situations this parameter is unneeded as xlsopen automatically
## selects the most useful interface present.
##
## Beware: Excel invocations may be left running invisibly in case of COM
## errors or forgetting to close the file pointer. Similarly for OpenOffice.org
## which may even prevent Octave from being closed.
##
## Examples:
##
## @example
##   xls = xlsopen ('test1.xls');
##   (get a pointer for reading from spreadsheet test1.xls)
##
##   xls = xlsopen ('test2.xls', 1, 'POI');
##   (as above, indicate test2.xls will be written to; in this case using Java
##    and the Apache POI interface are requested)
## @end example
##
## @seealso {xlsclose, xlsread, xlswrite, xls2oct, oct2xls, xlsfinfo}
##
## @end deftypefn

## Author: Philip Nienhuis
## Created: 2009-11-29
## Updates:
## 2010-01-03 Added OOXML support
## 2010-01-10 Changed (java) interface preference order to COM->POI->JXL
## 2010-01-16 Removed echoeing debug info in POI stanza
## 2010-03-01 Removed javaclasspath check for rt.jar
## 2010-03-14 Fixed check on xwrite flag lines 204+, if xlsopen fails xls ptr
##            should be []
## 2010-08-25 Improved help text
## 2010-09-27 Improved POI help message for unrecognized .xls format to hint for BIFF5/JXL
## 2010-10-20 Improved code for tracking changes to new/existing files
##     ''     Lots of code cleanup, improved error checking and catching
##     ''     Implemented fallback to JXL if POI can't read a file.
## 2010-10-30 More fine-grained file existence/writable checks
## 2010-11-01 Added <COM>.Application.DisplayAlerts=0 in COM section to avoid Excel pop-ups
## 2010-11-05 Option for multiple requested interface types (cell array)
##     ''     Bug fix: JXL fallback from POI for BIFF5 is only useful for reading
## 2010-11-05 Slight change to reporting to screen
## 2010-11-08 Tested with POI 3.7 (OK)
## 2010-11-10 Texinfo header updated
## 2010-12-01 Small bugfix - reset xlssupport in l. 102
## 2010-12-06 Textual changes to info header 
## 2011-03-26 OpenXLS support added
## 2011-05-18 Experimental UNO support added, incl. creating new spreadsheets
## 2011-05-22 Textual changes in header
## 2011-05-29 Cleanup of comments & messages
## 2011-09-03 Reset chkintf to [] if no xls support was discovered (to allow
##            rediscovery of interfaces between xlsopen calls, e.g. javaclasspath changes)
## 2011-09-08 Minor code cleanup
## 2012-01-26 Fixed "seealso" help string
## 2012-06-06 Improved interface detection logic. No more messages if same interface is
##            requested & used consecutively
## 2012-06-07 Fixed mixed-up lastintf assignments for POI and JXL
## 2012-09-02 (in UNO section) web adresses need only two consecutive slashes
## 2012-09-03 (in UNO section) replace canonicalize_file_name on non-Windows to
##            make_absolute_filename (see bug #36677)
## 2012-10-07 Moved subfunc getxlsinterfaces to ./private
##     ''     Moved all interface-specific file open stanzas to separate ./private funcs
## 2012-10-24 Style fixes
## 2012-12-18 Improved warning/error messages

function [ xls ] = xlsopen (filename, xwrite=0, reqinterface=[])

  persistent xlsinterfaces; persistent chkintf; persistent lastintf;
  ## xlsinterfaces.<intf> = [] (not yet checked), 0 (found to be unsupported) or 1 (OK)
  if (isempty (chkintf));
      chkintf = 1;
      xlsinterfaces = struct ('COM', [], 'POI', [], 'JXL', [], 'OXS', [], 'UNO', []);
  endif
  if (isempty (lastintf))
    lastintf = "---";
  endif

  xlssupport = 0;

  if (nargout < 1)
      usage ("XLS = xlsopen (Xlfile [, Rw] [, reqintf]). But no return argument specified!"); 
  endif

  if (~(islogical (xwrite) || isnumeric (xwrite)))
      usage ("xlsopen.m: numerical or logical value expected for arg ## 2 (readwrite)")
  endif

  if (~isempty (reqinterface))
    if ~(ischar (reqinterface) || iscell (reqinterface))
      usage ("Arg ## 3 (interface) not recognized - character value required"); 
    endif
    ## Turn arg3 into cell array if needed
    if (~iscell (reqinterface))
      reqinterface = {reqinterface}; 
    endif
    ## Check if previously used interface matches a requested interface
    if (isempty (regexpi (reqinterface, lastintf, 'once'){1}))
      ## New interface requested
      xlsinterfaces.COM = 0; xlsinterfaces.POI = 0; xlsinterfaces.JXL = 0;
      xlsinterfaces.OXS = 0; xlsinterfaces.UNO = 0;
      for ii=1:numel (reqinterface)
        reqintf = toupper (reqinterface {ii});
        ## Try to invoke requested interface(s) for this call. Check if it
        ## is supported anyway by emptying the corresponding var.
        if     (strcmpi (reqintf, 'COM'))
          xlsinterfaces.COM = [];
        elseif (strcmpi (reqintf, 'POI'))
          xlsinterfaces.POI = [];
        elseif (strcmpi (reqintf, 'JXL'))
          xlsinterfaces.JXL = [];
        elseif (strcmpi (reqintf, 'OXS'))
          xlsinterfaces.OXS = [];
        elseif (strcmpi (reqintf, 'UNO'))
          xlsinterfaces.UNO = [];
        else 
          usage (sprintf (["xlsopen.m: unknown .xls interface \"%s\" requested.\n" 
                 "Only COM, POI, JXL, OXS or UNO supported\n"], reqinterface{}));
        endif
      endfor
      printf ("Checking requested interface(s):\n");
      xlsinterfaces = getxlsinterfaces (xlsinterfaces);
      ## Well, is/are the requested interface(s) supported on the system?
      xlsintf_cnt = 0;
      for ii=1:numel (reqinterface)
        if (~xlsinterfaces.(toupper (reqinterface{ii})))
          ## No it aint
          printf ("%s is not supported.\n", upper (reqinterface{ii}));
        else
          ++xlsintf_cnt;
        endif
      endfor
      ## Reset interface check indicator if no requested support found
      if (~xlsintf_cnt)
        chkintf = [];
        xls = [];
        return
      endif
    endif
  endif

  ## Var readwrite is really used to avoid creating files when wanting to read,
  ## or not finding not-yet-existing files when wanting to write a new one.

  ## Check if Excel file exists. Adapt file open mode for readwrite argument
  if (xwrite); fmode = 'r+b'; else fmode = 'rb'; endif
  fid = fopen (filename, fmode);
  if (fid < 0)                      ## File doesn't exist...
    if (~xwrite)                    ## ...which obviously is fatal for reading...
      error ( sprintf ("xlsopen.m: file %s not found\n", filename));
    else                            ## ...but for writing, we need more info:
      fid = fopen (filename, 'rb'); ## Check if it exists at all...
      if (fid < 0)                  ## File didn't exist yet. Simply create it
        printf ("Creating file %s\n", filename);
        xwrite = 3;
      else                          ## File exists, but isn't writable => Error
        fclose (fid);  ## Do not forget to close the handle neatly
        error (sprintf ("xlsopen.m: write mode requested but file %s is not writable\n", filename))
      endif
    endif
  else
    ## Close file anyway to avoid COM or Java errors
    fclose (fid);
  endif
  
  ## Check for the various Excel interfaces. No problem if they've already
  ## been checked, getxlsinterfaces (far below) just returns immediately then.
  xlsinterfaces = getxlsinterfaces (xlsinterfaces);

  ## Supported interfaces determined; Excel file type check moved to separate interfaces.
  chk1 = strcmpi (filename(end-3:end), '.xls');      ## Regular (binary) BIFF 
  chk2 = strcmpi (filename(end-4:end-1), '.xls');    ## Zipped XML / OOXML
  
  ## Initialize file ptr struct
  xls = struct ("xtype",    'NONE', 
                "app",      [], 
                "filename", [], 
                "workbook", [], 
                "changed",  0, 
                "limits",   []);

  ## Keep track of which interface is selected
  xlssupport = 0;

  ## Interface preference order is defined below: currently COM -> POI -> JXL -> OXS -> UNO
  ## chk1 & chk2 (xls file type) are conveyed depending on interface capabilities

  if (xlsinterfaces.COM && ~xlssupport)
    ## Excel functioning has been tested above & file exists, so we just invoke it.
    [ xls, xlssupport, lastintf ] = __COM_spsh_open__ (xls, xwrite, filename, xlssupport);
  endif

  if (xlsinterfaces.POI && ~xlssupport && (chk1 || chk2))
    [ xls, xlssupport, lastintf ] = __POI_spsh_open__ (xls, xwrite, filename, xlssupport, chk1, chk2, xlsinterfaces);
  elseif ~(chk1 || chk2)
    error ("xlsopen.m: unsupported file format for Apache POI")
  endif

  if (xlsinterfaces.JXL && ~xlssupport && chk1)
    [ xls, xlssupport, lastintf ] = __JXL_spsh_open__ (xls, xwrite, filename, xlssupport, chk1);
  elseif (~chk1)
    error ("xlsopen.m: unsupported file format for JExcelAPI")
  endif

  if (xlsinterfaces.OXS && ~xlssupport && chk1)
    [ xls, xlssupport, lastintf ] = __OXS_spsh_open__ (xls, xwrite, filename, xlssupport, chk1);
  elseif (~chk1)
    error ("xlsopen.m: unsupported file format for OpenXLS")
  endif

  if (xlsinterfaces.UNO && ~xlssupport)
    [ xls, xlssupport, lastintf ] = __UNO_spsh_open__ (xls, xwrite, filename, xlssupport);
  endif

  ## if 
  ##  ---- other interfaces
  ## endif

  ## Rounding up. If none of the xlsinterfaces is supported we're out of luck.
  if (~xlssupport)
    if (isempty (reqinterface))
      ## This message is appended after message from getxlsinterfaces()
      printf ("None.\n");
      warning ("xlsopen.m: no support for Excel .xls I/O"); 
    else
      warning ("xlsopen.m: file type not supported by %s %s %s %s %s", reqinterface{:});
    endif
    xls = [];
    ## Reset found interfaces for re-testing in the next call. Add interfaces if needed.
    chkintf = [];
  else
    ## From here on xwrite is tracked via xls.changed in the various lower
    ## level r/w routines
    xls.changed = xwrite;

    ## xls.changed = 0 (existing/only read from), 1 (existing/data added), 2 (new,
    ## data added) or 3 (pristine, no data added).
    ## Until something was written to existing files we keep status "unchanged".
    if (xls.changed == 1), xls.changed = 0; endif
  endif

endfunction
