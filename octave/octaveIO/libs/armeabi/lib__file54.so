## Copyright (C) 2010,2011,2012 Philip Nienhuis <prnienhuis at users.sf.net>
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

## __OXS_getusedrange__

## Author: Philip <Philip@DESKPRN>
## Created: 2011-06-13
## Updates:
## 2011-06-29 try-catch to be able to skip non-data (e.g., graph) sheets
## 2012-10-12 Renamed & moved into ./private
## 2012-10-24 Style fixes

function [ trow, brow, lcol, rcol ] = __OXS_getusedrange__ (xls, wsh)

  sh = xls.workbook.getWorkSheet (wsh - 1);
  try
    ## Intriguing:  sh.getFirst<> is off by one, sh.getLast<> = OK.... 8-Z 
    trow = sh.getFirstRow () + 1;
    brow = sh.getLastRow ();
    lcol = sh.getFirstCol () + 1;
    rcol = sh.getLastCol ();
  catch
    ## Might be an empty sheet
    trow = brow = lcol = rcol = 0;
  end_try_catch
  ## Check for empty sheet
  if ((trow > brow) || (lcol > rcol))
    trow = brow = lcol = rcol = 0; 
  endif

endfunction
