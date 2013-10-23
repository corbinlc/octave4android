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

## __UNO_getusedrange__

## Author: Philip Nienhuis <prnienhuis@users.sf.net>
## Created: 2011-05-06
## Updates:
## 2011-06-29 Fixed wrong address range inference in case of sheet names 
##            containing period(s)
## 2012-03-02 Adapted code to assess nr of range blocks to ';' separator for LO3.5+
## 2012-10-12 Renamed & moved into ./private
## 2012-10-24 Style fixes

function [ srow, erow, scol, ecol ] = __UNO_getusedrange__ (ods, ii)

  # Get desired sheet
  sheets = ods.workbook.getSheets ();
  sh_names = sheets.getElementNames ();
  unotmp = java_new ("com.sun.star.uno.Type", "com.sun.star.sheet.XSpreadsheet");
  sh = sheets.getByName (sh_names(ii)).getObject.queryInterface (unotmp);

  ## Prepare cell range query
  unotmp = java_new ("com.sun.star.uno.Type", "com.sun.star.sheet.XCellRangesQuery");
  xRQ = sh.queryInterface (unotmp);
  Cellflgs = javaObject ("java.lang.Short", "23");
  ccells = xRQ.queryContentCells (Cellflgs);

  ## Get addresses of all blocks containing data
  addrs = ccells.getRangeAddressesAsString ();

  ## Strip sheet name from addresses. Watch out, in LO3.5 they changed
  ## the separator from ',' to ';' (without telling me 8-Z)
  ## 1. Get nr of range blocks
  nblks = numel (strfind (addrs, sh_names(ii)));
  ## 2. First try with "," separator...
  adrblks = strsplit (addrs, ",");
  if (numel (adrblks) < nblks)
    ## Apparently we have a ';' separator, so try with semicolon
    adrblks = strsplit (addrs, ";");
  endif
  if (isempty (adrblks))
    srow = erow = scol = ecol = 0;
    return
  endif

  ## Find leftmost & rightmost columns, and highest and lowest row with data
  srow = scol = 1e10;
  erow = ecol = 0;
  for ii=1:numel (adrblks)
    ## Check if address contains a sheet name in quotes (happens if name contains a period)
    if (int8 (adrblks{ii}(1)) == 39)
      ## Strip sheet name part
      idx = findstr (adrblks{ii}, "'.");
      range = adrblks{ii}(idx+2 : end);
    else
      ## Same, but tru strsplit()
      range = strsplit (adrblks{ii}, "."){2};
    endif
    [dummy, nrows, ncols, trow, lcol] = parse_sp_range (range);
    brow = trow + nrows - 1;
    rcol = lcol + ncols - 1;
    srow = min (srow, trow);
    scol = min (scol, lcol);
    erow = max (erow, brow);
    ecol = max (ecol, rcol);
  endfor

endfunction
