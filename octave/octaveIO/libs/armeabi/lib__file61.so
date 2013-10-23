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

## __POI_getusedrange__ - get range of occupied data cells from Excel using java/POI

## Author: Philip <Philip@DESKPRN>
## Created: 2010-03-20
## Updates:
## 2012-10-12 Renamed & moved into ./private
## 2012-10-24 Style fixes

function [ trow, brow, lcol, rcol ] = __POI_getusedrange__ (xls, ii)

  persistent cblnk; 
  cblnk = java_get ("org.apache.poi.ss.usermodel.Cell", "CELL_TYPE_BLANK");

  sh = xls.workbook.getSheetAt (ii-1);          ## Java POI starts counting at 0 

  trow = sh.getFirstRowNum ();                  ## 0-based
  brow = sh.getLastRowNum ();                   ## 0-based
  ## Get column range
  lcol = 1048577;                               ## OOXML (xlsx) max. + 1
  rcol = 0;
  botrow = brow;
  for jj=trow:brow
    irow = sh.getRow (jj);
    if (~isempty (irow))
      scol = (irow.getFirstCellNum).intValue ();
      lcol = min (lcol, scol);
      ecol = (irow.getLastCellNum).intValue () - 1;
      rcol = max (rcol, ecol);
      ## Keep track of lowermost non-empty row as getLastRowNum() is unreliable
      if   ~(irow.getCell(scol).getCellType () == cblnk ...
          && irow.getCell(ecol).getCellType () == cblnk)
        botrow = jj;
      endif
    endif
  endfor
  if (lcol > 1048576)
    ## Empty sheet
    trow = 0; brow = 0; lcol = 0; rcol = 0;
  else
    brow = min (brow, botrow) + 1; ++trow; ++lcol; ++rcol;   ## 1-based retvals
  endif

endfunction
