## Copyright (C) 2010,2011,2012 Philip Nienhuis, prnienhuis at users.sf.net
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

## __JXL_getusedrange__ - get occupied data cell range from Excel sheet
## using java/JExcelAPI

## Author: Philip <Philip@DESKPRN>
## Created: 2010-03-20
## Updates:
## 2012-10-12 Renamed & moved into ./private
## 2012-10-24 Style fixes

function [ trow, brow, lcol, rcol ] = __JXL_getusedrange__ (xls, wsh)

  persistent emptycell = (java_get ("jxl.CellType", "EMPTY")).toString ();

  sh = xls.workbook.getSheet (wsh - 1);      ## JXL sheet count 0-based

  brow = sh.getRows ();
  rcol = sh.getColumns ();
  
  if (brow == 0 || rcol == 0)
    ## Empty sheet
    trow = 0; lcol = 0; brow = 0; rcol = 0;
  else
    trow = brow + 1;
    lcol = rcol + 1;
    ## For loop coz we must check ALL rows for leftmost column
    for ii=0:brow-1    
      emptyrow = 1;
      jj = 0;
      ## While loop => only til first non-empty cell
      while (jj < rcol && emptyrow)   
        cell = sh.getCell (jj, ii);
        if ~(strcmp (char (cell.getType ()), emptycell))
          lcol = min (lcol, jj + 1);
          emptyrow = 0;
        endif
        ++jj;
      endwhile
      if ~(emptyrow); trow = min (trow, ii + 1); endif
    endfor
  endif

endfunction
