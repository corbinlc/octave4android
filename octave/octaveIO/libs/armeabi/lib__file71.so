## Copyright (C) 2011,2012 Philip Nienhuis <prnienhuis@users.sf.net>
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

## __UNO_spsh2oct__ - Inernal function for reading from spreadsheets using UNO/Java

## Author: Philip Nienhuis <prnienhuis@users.sf.net>
## Created: 2011-05-05
## Updates:
## 2011-09-18 Adapted sh_names type to LO 3.4.1
## 2011-09-19 Try to decipher if formulas return numeric or string values
## 2012-10-12 Renamed & moved into ./private
## 2012-10-24 Style fixes
## 2012-12-21 Search for exact match when searching sheet names

function [rawarr, xls, rstatus] = __UNO_spsh2oct__  (xls, wsh, datrange, spsh_opts)

  sheets = xls.workbook.getSheets ();
  sh_names = sheets.getElementNames ();
  if (! iscell (sh_names))
    ## Java array (LibreOffice 3.4.+), convert to cellstr
    sh_names = char (sh_names);
  else
    sh_names = {sh_names};
  endif

  ## Check sheet pointer
  if (isnumeric (wsh))
    if (wsh < 1 || wsh > numel (sh_names))
      error ("Sheet index %d out of range 1-%d", wsh, numel (sh_names));
    endif
  else
    ii = strmatch (wsh, sh_names, "exact");
    if (isempty (ii)), error ("Sheet '%s' not found", wsh); endif
    wsh = ii;
  endif
  unotmp = java_new ("com.sun.star.uno.Type", "com.sun.star.sheet.XSpreadsheet");
  sh = sheets.getByName(sh_names{wsh}).getObject.queryInterface (unotmp);

  unotmp = java_new ("com.sun.star.uno.Type", "com.sun.star.sheet.XCellRangesQuery");
  xRQ = sh.queryInterface (unotmp);
  ## Get cell ranges of all rectangles containing data. Type values:
  ##java_get ("com.sun.star.sheet.CellFlags", "VALUE")      ans =  1
  ##java_get ("com.sun.star.sheet.CellFlags", "DATETIME")   ans =  2
  ##java_get ("com.sun.star.sheet.CellFlags", "STRING")     ans =  4
  ##java_get ("com.sun.star.sheet.CellFlags", "FORMULA")    ans =  16
  ## Yep, boolean is lacking...
  Cellflgs = javaObject ("java.lang.Short", "23");
  ccells = xRQ.queryContentCells (Cellflgs);
  addrs = ccells.getRangeAddressesAsString ();

  ## Strip sheet name from addresses
  adrblks = strsplit (addrs, ",");
  if (isempty (adrblks))
    warning ("Sheet %s contains no data", sh_names{wsh});
    return
  endif

  ## Either parse (given cell range) or prepare (unknown range) help variables.
  ## As OpenOffice knows the occupied range, we need the limits anyway to avoid
  ## out-of-range errors
  [ trow, brow, lcol, rcol ] = getusedrange (xls, wsh);
  if (isempty (datrange))
    nrows = brow - trow + 1;  ## Number of rows to be read
    ncols = rcol - lcol + 1;  ## Number of columns to be read
  else
    [dummy, nrows, ncols, srow, scol] = parse_sp_range (datrange);
    ## Truncate range silently if needed
    brow = min (srow + nrows - 1, brow);
    rcol = min (scol + ncols - 1, rcol);
    trow = max (trow, srow);
    lcol = max (lcol, scol);
    nrows = min (brow - trow + 1, nrows);  ## Number of rows to be read
    ncols = min (rcol - lcol + 1, ncols);  ## Number of columns to be read
  endif
  ## Create storage for data at Octave side
  rawarr = cell (nrows, ncols);

  ## Get data. Apparently row & column indices are 0-based in UNO
  for ii=trow-1:brow-1
    for jj=lcol-1:rcol-1
      XCell = sh.getCellByPosition (jj, ii);
      cType = XCell.getType ().getValue ();
      switch cType
        case 1  ## Value
          rawarr{ii-trow+2, jj-lcol+2} = XCell.getValue ();
        case 2  ## String
          unotmp = java_new ("com.sun.star.uno.Type", "com.sun.star.text.XText");
          rawarr{ii-trow+2, jj-lcol+2} = XCell.queryInterface (unotmp).getString ();
        case 3  ## Formula
          if (spsh_opts.formulas_as_text)
            rawarr{ii-trow+2, jj-lcol+2} = XCell.getFormula ();
          else
            ## Unfortunately OOo gives no clue as to the type of formula result
            unotmp = java_new ("com.sun.star.uno.Type", "com.sun.star.text.XText");
            rawarr{ii-trow+2, jj-lcol+2} = XCell.queryInterface (unotmp).getString ();
            tmp = str2double (rawarr{ii-trow+2, jj-lcol+2});
            ## If the string happens to contain just a number we'll assume it is numeric
            if (~isnan (tmp)); rawarr{ii-trow+2, jj-lcol+2} = tmp; endif
          endif
        otherwise
          ## Empty cell
      endswitch
    endfor
  endfor 

  ## Keep track of data rectangle limits
  xls.limits = [lcol, rcol; trow, brow];

  rstatus = ~isempty (rawarr);

endfunction
