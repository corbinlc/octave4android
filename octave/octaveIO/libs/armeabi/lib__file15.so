## Copyright (C) 2009,2010,2011,2012 Philip Nienhuis <prnienhuis at users.sf.net>
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
## @deftypefn {Function File} [ @var{xls}, @var{rstatus} ] = oct2xls (@var{arr}, @var{xls})
## @deftypefnx {Function File} [ @var{xls}, @var{rstatus} ] = oct2xls (@var{arr}, @var{xls}, @var{wsh})
## @deftypefnx {Function File} [ @var{xls}, @var{rstatus} ] = oct2xls (@var{arr}, @var{xls}, @var{wsh}, @var{range})
## @deftypefnx {Function File} [ @var{xls}, @var{rstatus} ] = oct2xls (@var{arr}, @var{xls}, @var{wsh}, @var{range}, @var{options})
##
## Add data in 1D/2D CELL array @var{arr} into a cell range specified in
## @var{range} in worksheet @var{wsh} in an Excel spreadsheet file
## pointed to in structure @var{xls}.
## Return argument @var{xls} equals supplied argument @var{xls} and is
## updated by oct2xls.
##
## A subsequent call to xlsclose is needed to write the updated spreadsheet
## to disk (and -if needed- close the Excel or Java invocation).
##
## @var{arr} can be any 1D or 2D array containing numerical or character
## data (cellstr) except complex. Mixed numeric/text arrays can only be
## cell arrays.
##
## @var{xls} must be a valid pointer struct created earlier by xlsopen.
##
## @var{wsh} can be a number or string (max. 31 chars).
## In case of a yet non-existing Excel file, the first worksheet will be
## used & named according to @var{wsh} - extra empty worksheets that Excel
## creates by default are deleted.
## In case of existing files, some checks are made for existing worksheet
## names or numbers, or whether @var{wsh} refers to an existing sheet with
## a type other than worksheet (e.g., chart).
## When new worksheets are to be added to the Excel file, they are
## inserted to the right of all existing worksheets. The pointer to the
## "active" sheet (shown when Excel opens the file) remains untouched.
##
## If @var{range} is omitted or just the top left cell of the range is
## specified, the actual range to be used is determined by the size of
## @var{arr}. If nothing is specified for @var{range} the top left cell
## is assumed to be 'A1'.
##
## Data are added to the worksheet, ignoring other data already present;
## existing data in the range to be used will be overwritten.
##
## If @var{range} contains merged cells, only the elements of @var{arr}
## corresponding to the top or left Excel cells of those merged cells
## will be written, other array cells corresponding to that cell will be
## ignored.
##
## Optional argument @var{options}, a structure, can be used to specify
## various write modes.
## Currently the only option field is "formulas_as_text", which -if set
## to 1 or TRUE- specifies that formula strings (i.e., text strings
## starting with "=" and ending in a ")" ) should be entered as litteral
## text strings rather than as spreadsheet formulas (the latter is the
## default).
##
## Beware that -if invoked- Excel invocations may be left running silently
## in case of COM errors. Invoke xlsclose with proper pointer struct to
## close them.
## When using Java, note that large data array sizes elements may exhaust
## the Java shared memory space for the default java memory settings.
## For larger arrays, appropriate memory settings are needed in the file
## java.opts; then the maximum array size for the Java-based spreadsheet
## options may be in the order of 10^6 elements. In caso of UNO this
## limit is not applicable and spreadsheets may be much larger.
##
## Examples:
##
## @example
##   [xlso, status] = xls2oct ('arr', xlsi, 'Third_sheet', 'AA31:AB278');
## @end example
##
## @seealso {xls2oct, xlsopen, xlsclose, xlsread, xlswrite, xlsfinfo}
##
## @end deftypefn

## Author: Philip Nienhuis
## Created: 2009-12-01
## Updates: 
## 2010-01-03 (OOXML support)
## 2010-03-14 Updated help text section on java memory usage
## 2010-07-27 Added formula writing support (based on patch by Benjamin Lindner)
## 2010-08-01 Added check on input array size vs. spreadsheet capacity
##     ''     Changed argument topleft into range (now compatible with ML); the
##     ''     old argument version (just topleft cell) is still recognized, though
## 2010-08014 Added char array conversion to 1x1 cell for character input arrays
## 2010-08-16 Added check on presence of output argument. Made wsh = 1 default
## 2010-08-17 Corrected texinfo ("topleft" => "range")
## 2010-08-25 Improved help text (section on java memory usage)
## 2010-11-12 Moved ptr struct check into main func. More input validity checks
## 2010-11-13 Added check for 2-D input array
## 2010-12-01 Better check on file pointer struct (ischar (xls.xtype))
## 2011-03-29 OpenXLS support added. Works but saving to file (xlsclose) doesn't work yet 
##      ''    Bug fixes (stray variable c_arr, and wrong test for valid xls struct)
## 2011-05-18 Experimental UNO support
## 2011-09-08 Bug fix in range arg check; code cleanup
## 2011-11-18 Fixed another bug in test for range parameter being character string
## 2012-01-26 Fixed "seealso" help string
## 2012-02-20 Fixed range parameter to be default empty string rather than empty numeral
## 2012-02-27 More range param fixes
## 2012-03-07 Updated texinfo help text
## 2012-05-22 Cast all numeric data in input array to double
## 2012-10-12 Moved all interface-specific subfubcs into ./private
## 2012-10-24 Style fixes
## 2012-12-18 Improved error/warning messages
##
## Latest subfunc update: 2012-10-12

function [ xls, rstatus ] = oct2xls (obj, xls, wsh=1, crange="", spsh_opts=[])

  if (nargin < 2) error ("oct2xls needs a minimum of 2 arguments."); endif
  
  ## Validate input array, make sure it is a cell array
  if (isempty (obj))
    warning ("oct2xls: request to write empty matrix - ignored."); 
    rstatus = 1;
    return;
  elseif (isnumeric (obj))
    obj = num2cell (obj);
  elseif (ischar (obj))
    obj = {obj};
    printf ("(oct2xls: input character array converted to 1x1 cell)\n");
  elseif (~iscell (obj))
    error ("oct2xls: input array neither cell nor numeric array");
  endif
  if (ndims (obj) > 2)
    error ("oct2xls: only 2-dimensional arrays can be written to spreadsheet"); 
  endif
  ## Cast all numerical values to double as spreadsheets only have double/boolean/text type
  idx = cellfun (@isnumeric, obj, "UniformOutput", true);
  obj(idx) = cellfun (@double, obj(idx), "UniformOutput", false);

  ## Check xls file pointer struct
  test1 = ~isfield (xls, "xtype");
  test1 = test1 || ~isfield (xls, "workbook");
  test1 = test1 || isempty (xls.workbook);
  test1 = test1 || isempty (xls.app);
  test1 = test1 || ~ischar (xls.xtype);
  if (test1)
    error ("oct2xls: invalid xls file pointer struct");
  endif

  ## Check worksheet ptr
  if (~(ischar (wsh) || isnumeric (wsh)))
    error ("Integer (index) or text (wsh name) expected for arg # 3");
  endif

  ## Check range
  if (~isempty (crange) && ~ischar (crange))
    error ("oct2xls: character string expected for arg # 4 (range)");
  elseif (isempty (crange))
    crange = "";
  endif

  ## Various options 
  if (isempty (spsh_opts))
    spsh_opts.formulas_as_text = 0;
    ## other options to be implemented here
  elseif (isstruct (spsh_opts))
    if (~isfield (spsh_opts, "formulas_as_text"))
      spsh_opts.formulas_as_text = 0; 
    endif
    ## other options to be implemented here

  else
    error ("oct2xls: structure expected for arg # 5");
  endif
  
  if (nargout < 1)
    printf ("oct2xls: warning: no output spreadsheet file pointer specified.\n");
  endif
  
  ## Select interface to be used
  if (strcmpi (xls.xtype, "COM"))
    ## ActiveX / COM
    [xls, rstatus] = __COM_oct2spsh__ (obj, xls, wsh, crange, spsh_opts);
  elseif (strcmpi (xls.xtype, "POI"))
    ## Invoke Java and Apache POI
    [xls, rstatus] = __POI_oct2spsh__ (obj, xls, wsh, crange, spsh_opts);
  elseif (strcmpi (xls.xtype, "JXL"))
    ## Invoke Java and JExcelAPI
    [xls, rstatus] = __JXL_oct2spsh__ (obj, xls, wsh, crange, spsh_opts);
  elseif (strcmpi (xls.xtype, "OXS"))
    ## Invoke Java and OpenXLS     ##### Not complete, saving file doesn't work yet!
    printf ("Sorry, writing with OpenXLS not reliable => not supported yet\n");
    ## [xls, rstatus] = __OXS_oct2spsh__ (obj, xls, wsh, crange, spsh_opts);
  elseif (strcmpi (xls.xtype, "UNO"))
    ## Invoke Java and UNO bridge (OpenOffice.org)
    [xls, rstatus] = __UNO_oct2spsh__ (obj, xls, wsh, crange, spsh_opts);
##elseif (strcmpi (xls.xtype, "<whatever>"))
    ##<Other Excel interfaces>
  else
    error (sprintf ("oct2xls: unknown Excel .xls interface - %s.", xls.xtype));
  endif

endfunction
