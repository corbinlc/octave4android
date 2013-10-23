## Copyright (C) 2009,2010,2011,2012 Philip Nienhuis <pr.nienhuis at users.sf.net>
##
## This program is free software; you can redistribute it and/or modify it under
## the terms of the GNU General Public License as published by the Free Software
## Foundation; either version 3 of the License, or (at your option) any later
## version.
##
## This program is distributed in the hope that it will be useful, but WITHOUT
## ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
## FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
## details.
##
## You should have received a copy of the GNU General Public License along with
## this program; if not, see <http://www.gnu.org/licenses/>.

## -*- texinfo -*-
## @deftypefn {Function File} [@var{filetype}] = xlsfinfo (@var{filename} [, @var{reqintf}])
## @deftypefnx {Function File} [@var{filetype}, @var{sh_names}] = xlsfinfo (@var{filename} [, @var{reqintf}])
## @deftypefnx {Function File} [@var{filetype}, @var{sh_names}, @var{fformat}] = xlsfinfo (@var{filename} [, @var{reqintf}])
## Query Excel spreadsheet file @var{filename} for some info about its
## contents.
##
## If @var{filename} is a recognizable Excel spreadsheet file,
## @var{filetype} returns the string "Microsoft Excel Spreadsheet", or
## @'' (empty string) otherwise.
## 
## If @var{filename} is a recognizable Excel spreadsheet file, optional
## argument @var{sh_names} contains a list (cell array) of sheet
## names (and in case Excel is installed: sheet types) contained in
## @var{filename}, in the order (from left to right) in which they occur
## in the sheet stack.
##
## Optional return value @var{fformat} currently returns @'' (empty
## string) unless @var{filename} is a readable Excel 97-2003 .xls file or
## an Excel 2007 .xlsx / .xlsb file in which case @var{fformat} is set to
## "xlWorkbookNormal". Excel 95 .xls files can only be read through the JXL
## (JExcelAPI) or UNO (OpenOffice.org) Java-based interfaces.
##
## If no return arguments are specified the sheet names are echoed to the 
## terminal screen; in case of Java interfaces for each sheet the actual
## occupied data range is echoed as well. The occupied cell range will have
## to be determined behind the scenes first; this can take some time for the
## Java-based interfaces.
##
## If multiple xls interfaces have been installed, @var{reqintf} can be
## specified. This can sometimes be handy, e.g. to get an idea of occupied
## cell ranges in each worksheet using different interfaces (due to cached
## info and/or different treatment of empty but formatted cells, each
## interfaces may give different results).
##
## For use on OOXML spreadsheets one needs full POI and/or UNO support (see
## xlsopen) and 'poi' or 'uno' needs to be specified for @var{reqintf}. For
## Excel 95 file use 'jxl' or 'uno'.
##
## Examples:
##
## @example
##   exist = xlsfinfo ('test4.xls');
##   (Just checks if file test4.xls is a readable Excel file) 
## @end example
##
## @example
##   [exist, names] = xlsfinfo ('test4.xls');
##   (Checks if file test4.xls is a readable Excel file and return a 
##    list of sheet names and -types) 
## @end example
##
## @seealso {oct2xls, xlsread, xls2oct, xlswrite}
##
## @end deftypefn

## Author: Philip Nienhuis <prnienhuis@users.sourceforge.net>
## Created: 2009-10-27
## Updates:
## 2009-01-01 Echo sheet names to screen, request interface type)
## 2010-03-21 Better tabulated output; occupied date range per sheet echoed 
##            for Java interfaces (though it may be a bit off in case of JXL)
## 2010-05-31 Added remark about delays when determining occupied data range
## 2010-08-25 Improved help text (Excel file types)
## 2010-10-06 Added ";" to str2 declaration
##     ''     Added occupied range echo for COM interface (may be a bit off too)
## 2010-10-10 Made output arg2 contain only address ranges (or other sheet type names)
## 2010-11-01 Added other file type strings for return arg #3 (fformat)
## 2011-03-26 Added OpenXLS support
## 2011-05-18 Experimental UNO support
## 2011-09-08 Some code simplifications
## 2012-01-26 Fixed "seealso" help string
## 2012-02-25 Added info on occupied ranges to sh_names outarg for all interfaces
## 2012-10-12 Moved all interface-specific code into ./private subfuncs
## 2012-10-24 Style fixes

function [ filetype, sh_names, fformat ] = xlsfinfo (filename, reqintf=[])

  persistent str2; str2 = "                                 "; # 33 spaces
  persistent lstr2; lstr2 = length (str2);

  xls = xlsopen (filename, 0, reqintf);
  if (isempty (xls)); return; endif

  toscreen = nargout < 1;

  ## If any valid xls-pointer struct has been returned, it must be a valid Excel spreadsheet
  filetype = "Microsoft Excel Spreadsheet"; 
  fformat = "";

  if (strcmp (xls.xtype, "COM"))
    [sh_names] = __COM_spsh_info__ (xls);

  elseif (strcmp (xls.xtype, "POI"))
    [sh_names] = __POI_spsh_info__ (xls);

  elseif (strcmp (xls.xtype, "JXL"))
    [sh_names] = __JXL_spsh_info__ (xls);

  elseif (strcmp (xls.xtype, "OXS"))
    [sh_names] = __OXS_spsh_info__ (xls);

  elseif (strcmp (xls.xtype, "UNO"))
    [sh_names] = __UNO_spsh_info__ (xls);

##elseif   <Other Excel interfaces below>

  else
    error (sprintf ("xlsfinfo: unknown Excel .xls interface - %s.", xls.xtype));

  endif

  sh_cnt = size (sh_names, 1);
  if (toscreen)
    ## Echo sheet names to screen
    for ii=1:sh_cnt
      str1 = sprintf ("%3d: %s", ii, sh_names{ii, 1});
      if (index (sh_names{ii, 2}, ":"))
        str3 = [ "(Used range ~ " sh_names{ii, 2} ")" ];
      else
        str3 = sh_names{ii, 2};
      endif
      printf ("%s%s%s\n", str1, str2(1:lstr2-length (sh_names{ii, 1})), str3);
    endfor
  else
    if (sh_cnt > 0)
      if (strcmpi (xls.filename(end-2:end), "xls"))
        fformat = "xlWorkbookNormal";
      elseif (strcmpi (xls.filename(end-2:end), "csv"))
        fformat = "xlCSV";        ## Works only with COM
      elseif (strcmpi (xls.filename(end-3:end-1), "xls"))
        fformat = "xlOpenXMLWorkbook";
      elseif (strmatch ('htm', lower (xls.filename(end-3:end))))
        fformat = "xlHtml";       ##  Works only with COM
      else
        fformat = "";
      endif
    endif
  endif

  xlsclose (xls);

endfunction
