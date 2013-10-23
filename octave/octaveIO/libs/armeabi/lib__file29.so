## Copyright (C) 2012 Philip Nienhuis <prnienhuis@users.sf.net>
## 
## This program is free software; you can redistribute it and/or modify
## it under the terms of the GNU General Public License as published by
## the Free Software Foundation; either version 3 of the License, or
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

## __COM_spsh_info__

## Author: Philip Nienhuis <prnienhuis@users.sf.net>
## Created: 2012-10-12
## Updates:
## 2012-10-12 Moved into ./private
## 2012-10-24 Style fixes

function [sh_names] = __COM_spsh_info__ (xls)

  xlWorksheet = -4167; xlChart = 4;

  ## See if desired worksheet number or name exists
  sh_cnt = xls.workbook.Sheets.count;
  sh_names = cell (sh_cnt, 2);
  ws_cnt = 0; ch_cnt = 0; o_cnt = 0;
  for ii=1:sh_cnt
    sh_names(ii, 1) = xls.workbook.Sheets(ii).Name;
    if (xls.workbook.Sheets(ii).Type == xlWorksheet)
      [tr, lr, lc, rc] = getusedrange (xls, ++ws_cnt);
      if (tr)
        sh_names(ii, 2) = sprintf ...
                ("%s:%s", calccelladdress (tr, lc), calccelladdress (lr, rc));
      else
        sh_names(ii, 2) = "Empty";
      endif
    elseif (xls.workbook.Sheets(ii).Type == xlChart)
      sh_names(ii, 2) = sprintf ("Chart"); ++ch_cnt;
    else
      sh_names(ii, 2) = "Other_type"; ++o_cnt;
    endif
  endfor

endfunction
