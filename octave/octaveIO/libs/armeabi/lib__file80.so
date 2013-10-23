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
## @deftypefn {Function File} @var{rstatus} = xlswrite (@var{filename}, @var{arr})
## @deftypefnx {Function File} @var{rstatus} = xlswrite (@var{filename}, @var{arr}, @var{wsh})
## @deftypefnx {Function File} @var{rstatus} = xlswrite (@var{filename}, @var{arr}, @var{wsh}, @var{range})
## @deftypefnx {Function File} @var{rstatus} = xlswrite (@var{filename}, @var{arr}, @var{wsh}, @var{range}, @var{reqintf})
## Add data in 1D/2D array @var{arr} to worksheet @var{wsh} in Excel
## spreadsheet file @var{filename} in cell range @var{range}.
##
## @var{rstatus} returns 1 if write succeeded, 0 otherwise.
##
## @var{filename} must be a valid .xls Excel file name (including file
## name extension). If @var{filename} does not contain any directory path,
## the file is saved in the current directory.
##
## @var{arr} can be any 1D or 2D array containing numerical or character
## data (cellstr) except complex. Mixed numeric/text arrays can only be
## cell arrays.
##
## If only 3 arguments are given, the 3rd is assumed to be a spreadsheet
## range if it contains a ":" or is a completely empty string (corresponding
## to A1:IV65336 for regular .xls or A1:XFD1048576 for OOXML .xlsx). The
## 3rd argument is assumed to refer to a worksheet if it is a numeric value
## or a non-empty text string not containing ":"
##
## @var{wsh} can be a number or string (max. 31 chars).
## In case of a not yet existing Excel file, the first worksheet will be
## used & named according to @var{wsh} - the extra worksheets that Excel
## normally creates by default are deleted.
## In case of existing files, some checks are made for existing worksheet
## names or numbers, or whether @var{wsh} refers to an existing sheet with
## a type other than worksheet (e.g., chart).
## When new worksheets are to be added to the Excel file, they are
## inserted to the right of all existing worksheets. The pointer to the
## "active" sheet (shown when Excel opens the file) remains untouched.
##
## @var{range} is expected to be a regular spreadsheet range.
## Data is added to the worksheet; existing data in the requested
## range will be overwritten.
## Array @var{arr} will be clipped at the right and/or bottom if its size
## is bigger than can be accommodated in @var{range}.
## If @var{arr} is smaller than the @var{range} allows, it is placed
## in the top left rectangle of @var{range} and remaining cell values
## outside the rectangle will be retained.
##
## If @var{range} contains merged cells, only the elements of @var{arr}
## corresponding to the top or left Excel cells of those merged cells
## will be written, other array cells corresponding to that cell will be
## ignored.
##
## The optional last argument @var{reqintf} can be used to override 
## the automatic selection by xlswrite of one interface out of the
## supported ones: 'com' (ActiveX/Excel), 'poi' (Java/Apache POI), 'jxl'
## (Java/JExcelAPI), or 'uno' (Java/OpenOffice.org). 'oxs' (Java/OpenXLS)
## is implemented but disabled for writing as it is too buggy. For
## writing to OOXML files (.xlsx) a value of 'com', 'poi' or 'uno' must
## be specified for @var{reqintf}. The value of @var{reqintf} is
## case-insensitive. Multiple interfaces can be selected if entered as
## a cell array of strings.
##
## xlswrite is a mere wrapper for various scripts which find out what
## Excel interface to use (COM, POI, etc) plus code to mimic the other
## brand's syntax. For each call to xlswrite such an interface must be
## started and possibly an Excel file loaded. When writing to multiple
## ranges and/or worksheets in the same Excel file, a speed bonus can be
## obtained by invoking those scripts directly with multiple calls to
## oct2xls (one for each sheet or range) surrounded by one call to
## xlsopen and xlsclose:
## (xlsopen / octxls / oct2xls / .... / xlsclose)
##
## Examples:
##
## @example
##   status = xlswrite ('test4.xls', 'arr', 'Third_sheet', 'C3:AB40');
##   (which adds the contents of array arr (any type) to range C3:AB40 
##   in worksheet 'Third_sheet' in file test4.xls and returns a logical 
##   True (= numerical 1) in status if al went well) 
## @end example
##
## @seealso {xlsread, oct2xls, xls2oct, xlsopen, xlsclose, xlsfinfo}
##
## @end deftypefn

## Author: Philip Nienhuis
## Created: 2009-10-16
## Updates:
## 2010-01-04 (Adapted range capacity checks to OOXML)
## 2010-01-12 (Bug fix; added unwind_protect to xlsopen...xlsclose calls)
## 2010-01-15 Fixed typos in texinfo
## 2010-08-18 Added check for existence of xls after call to xlsopen to 
##            avoid unneeded error message clutter
## 2010-10-27 Changed range -> crange to unhide other range functions
## 2011-09-08 Minor code syntax updates
## 2012-01-26 Fixed "seealso" help string
## 2012-06-07 Replaced all tabs by double space
## 2012-10-24 Style fixes
## 2012-12-23 Fix rare occasion of xlsclose error in unwind_protect block

function [ rstatus ] = xlswrite (filename, arr, arg3, arg4, arg5)

  rstatus = 0;

  ## Sanity checks
  if (nargin < 2)
    usage ("Insufficient arguments - see 'help xlswrite'");
  elseif (~ischar (filename))
    error ("First argument must be a filename (incl. suffix)");
  elseif (nargin == 2)
    ## Assume first worksheet and full worksheet starting at A1
    wsh = 1;
    if (strcmpi (filename(end-4:end-1), "xls"))
      crange = "A1:XFD1048576";   ## OOXML has ridiculously large limits 
    else
      crange = "A1:IV65536";      ## Regular xls limits
    endif
  elseif (nargin == 3)
    ## Find out whether 3rd argument = worksheet or range
    if (isnumeric (arg3) || (isempty (findstr (arg3, ":")) && ~isempty (arg3)))
      ## Apparently a worksheet specified
      wsh = arg3;
      if (strcmpi (filename(end-4:end-1), "xls"))
        crange = "A1:XFD1048576"; ## OOXML has ridiculously large limits 
      else
        crange = "A1:IV65536";    ## Regular xls limits
      endif
    else
      ## Range specified
      wsh = 1;
      crange = arg3;
    endif
  elseif (nargin >= 4)
    wsh = arg3;
    crange = arg4;
  endif
  if (nargin == 5)
    reqintf = arg5;
  else
    reqintf = [];
  endif
  
  ## Parse range
  [topleft, nrows, ncols, trow, lcol] = parse_sp_range (crange);
  
  ## Check if arr fits in range
  [nr, nc] = size (arr);
  if ((nr > nrows) || (nc > ncols))
    # Array too big; truncate
    nr = min (nrows, nr);
    nc = min (ncols, nc);
    warning ("xlswrite - array truncated to %d by %d to fit in range %s", ...
             nrows, ncols, crange);
  endif

  unwind_protect          ## Needed to be sure Excel can be closed i.c.o. errors
    xls_ok = 0;
    xls = xlsopen (filename, 1, reqintf);
    xls_ok = 1;

    [xls, rstatus] = oct2xls (arr(1:nr, 1:nc), xls, wsh, topleft);

  unwind_protect_cleanup
    if (xls_ok && ! isempty (xls))
      xls = xlsclose (xls);
    endif

  end_unwind_protect

endfunction
