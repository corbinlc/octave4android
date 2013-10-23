## Copyright (C) 2010,2011,2012 Philip Nienhuis
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

## __COM_getusedrange__

## Author: Philip Nienhuis <prnienhuis@users.sf.net>
## Created: 2010-10-07
## Updates:
## 2012-10-12 Renamed & moved into ./private
## 2012-10-24 Style fixes

function [ trow, brow, lcol, rcol ] = __COM_getusedrange__ (xls, ii)

  sh = xls.workbook.Worksheets (ii);
  
  ## Decipher used range. Beware, UsedRange() returns *cached* rectangle of
  ## all spreadsheet cells containing *anything*, including just formatting
  ## (i.e., empty cells are included too). ==> This is an approximation only
  allcells = sh.UsedRange;
  
  ## Get top left cell as a Range object
  toplftcl = allcells.Columns(1).Rows(1);
  
  ## Count number of rows & cols in virtual range from A1 to top left cell
  lcol = sh.Range ("A1", toplftcl).columns.Count;
  trow = sh.Range ("A1", toplftcl).rows.Count;
  
  ## Add real occupied rows & cols to obtain end row & col
  brow = trow + allcells.rows.Count() - 1;
  rcol = lcol + allcells.columns.Count() - 1;
  
  ## Check if there are real data
  if ((lcol == rcol) && (trow = brow))
    if (isempty (toplftcl.Value))
      trow = brow = lcol = rcol = 0;
    endif
  endif

endfunction
