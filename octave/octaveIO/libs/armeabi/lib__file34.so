## Copyright (C) 2010,2011,2012 Philip Nienhuis <prnienhuis -aT- users.sf.net>
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

## __JOD_getusedrange__

## Author: Philip <Philip@DESKPRN>
## Created: 2010-05-25
## Last updates:
## 2010-05-31 Fixed ignoring table-covered-cells; fixed count of sheets comprising just A1:A1
##            Added option for wsh being a string argument 
## 2010-08-12 Little textual adaptations
## 2010-11-13 Catched jOpenDocument bug (1.2bx) where string cells have no office:value-type
##     ''     attrb set (by JOD). Somehow OTK is more robust as it catches these cells
## 2012-04-09 Fixed rowrepcnt (str2num not applied to tablerow)
## 2012-04-18 Added getUsedRange() method for JOD 1.3x and above 
## 2012-10-12 Renamed & moved into ./private
## 2012-10-24 Style fixes

function [ trow, brow, lcol, rcol ] = __JOD_getusedrange__ (ods, wsh)

  ## This function works by virtue of sheets in JOD actually being a Java string.
  ## It works outside of the Java memory/heap space which is an added benefit...
  ## (Read: this is one big dirty hack...... prone to crash Java on BIG spreadsheets)

  if (isnumeric (wsh))
    sh = char (ods.workbook.getSheet (wsh - 1));
  else
    sh = char (ods.workbook.getSheet (wsh));
  endif

  try
    ## Let's see if we have JOD v. 1.3x. If not, next call fails & we'll fall back to the old hack
    sh_rng = char (sh.getUsedRange ());
    if (isempty (sh_rng))
      ## Empty sheet
      trow = brow = lcol = rcol = 0;
    else
      ## Strip sheet name
      sh_rng = sh_rng(length (sh.getName) + 2 : end);
      ## Get rid of period
      sh_rng = strrep (sh_rng, ".", "");
      [~, nr, nc, trow, lcol] = parse_sp_range (sh_rng);
      brow = trow + nr - 1;
      rcol = lcol + nc - 1;
    endif
  
  catch
    ## Fall back to the old hack :-(
    sh = char (sh);

    ## 1. Get table-row pointers
    id_trow = strfind (sh, "<table:table-row");
    id = strfind (sh, "</table:table>") - 1;
    id_trow = [id_trow id];

    trow = rcol = 0;
    lcol = 1024; brow = 0;
    if (~isempty (id))
      ## 2. Loop over all table-rows
      rowrepcnt = 0;
      for irow = 1:length (id_trow)-1
        ## Isolate single table-row
        tablerow = sh(id_trow(irow):id_trow(irow+1)-1);
        ## Search table-cells. table-c covers both table-cell & table-covered-cell
        id_tcell = strfind (tablerow, "<table:table-c"); 
        id_tcell = [id_tcell id];
        rowl = length (tablerow);
        if (isempty (id_tcell(1:end-1)))
          rowend = rowl;
        else
          rowend = id_tcell(1);
        endif
        ## Add in table-number-rows-repeated attribute values
        rowrept = strfind (tablerow(1:rowend), "number-rows-repeated");
        if (~isempty (rowrept))
          [st, en] = regexp (tablerow(rowrept:min (rowend, rowrept+30)), '\d+');
          rowrepcnt += str2num (tablerow(rowrept+st-1:min (rowend, rowrept+en-1))) - 1;
        endif

        ## 3. Search table-cells. table-c is a table-covered-cell that is considered empty
        id_tcell = strfind (tablerow, "<table:table-c");
        if (~isempty (id_tcell))
          ## OK, this row has a value cell. Now table-covered-cells must be included.
          id_tcell2 = strfind (tablerow, "<table:covered-t");
          if (~isempty (id_tcell2)) id_tcell = sort ([id_tcell id_tcell2]); endif
          id_tcell = [id_tcell rowl];
          ## Search for non-empty cells (i.e., with an office:value-type attribute). But:
          ## jOpenDocument 1.2b3 has a bug: it often doesn't set this attr for string cells
          id_valtcell = strfind (tablerow, "office:value-type=");
          id_textonlycell = strfind (tablerow, "<text:");
          id_valtcell = sort ([id_valtcell id_textonlycell]);
          id_valtcell = [id_valtcell rowl];
          if (~isempty (id_valtcell(1:end-1)))
            brow = irow + rowrepcnt;
            ## First set trow if it hadn't already been found
            if (~trow) trow = irow; endif
            ## Search for repeated table-cells
            id_reptcell = strfind (tablerow, "number-columns-repeated");
            id_reptcell = [id_reptcell rowl];
            ## Search for leftmost non-empty table-cell. llcol = counter for this table-row
            llcol = 1;
            while (id_tcell (llcol) < id_valtcell(1) && llcol <= length (id_tcell) - 1)
              ++llcol;
            endwhile
            --llcol;
            ## Compensate for repeated cells. First count all repeats left of llcol
            ii = 1;
            repcnt = 0;
            if (~isempty (id_reptcell(1:end-1)))
              ## First try lcol
              while (ii <= length (id_reptcell) - 1 && id_reptcell(ii) < id_valtcell(1))
                ## Add all repeat counts left of leftmost data tcell minus 1 for each
                [st, en] = ...
                  regexp (tablerow(id_reptcell(ii):id_reptcell(ii)+30), '\d+');
                repcnt += ...
                  str2num (tablerow(id_reptcell(ii)+st-1:id_reptcell(ii)+en-1)) - 1;
                ++ii;
              endwhile
              ## Next, add current repcnt value to llcol and update lcol
              lcol = min (lcol, llcol + repcnt);
              ## Get last value table-cell in table-cell idx
              jj = 1;
              while (id_tcell (jj) < id_valtcell(length (id_valtcell)-1))
                ++jj;
              endwhile

              ## Get rest of column repeat counts in value table-cell range
              while (ii < length (id_reptcell) && id_reptcell(ii) < id_tcell(jj))
                ## Add all repeat counts minus 1 for each tcell in value tcell range
                [st, en] = ...
                  regexp (tablerow(id_reptcell(ii):id_reptcell(ii)+30), '\d+');
                repcnt += ...
                  str2num (tablerow(id_reptcell(ii)+st-1:id_reptcell(ii)+en-1)) - 1;
                ++ii;
              endwhile
            else
              ## In case left column = A
              lcol = min (lcol, llcol);
            endif
            ## Count all table-cells in value table-cell-range
            ii = 1;       ## Indexes cannot be negative
            while (ii < length (id_tcell) ...
                   && id_tcell(ii) < id_valtcell(length (id_valtcell) - 1))
              ++ii;
            endwhile
            --ii;
            rcol = max (rcol, ii + repcnt);
          endif
        endif
      endfor
    else
      ## No data found, empty sheet
      lcol = rcol = brow = trow = 0;
    endif

  end_try_catch

endfunction
