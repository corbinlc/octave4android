## Copyright (C) 2009,2010,2011,2012 Philip Nienhuis <pr.nienhuis at users.sf.net>
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

## __JOD_oct2spsh__ - write data from octave to an ODS spreadsheet using the
## jOpenDocument interface.
##
## Author: Philip Nienhuis
## Created: 2009-12-13
## First usable version: 2010-01-14
## Updates:
## 2010-03-17 Adapted to simplified calccelladdress argument list
## 2010-04-24 Added ensureColumnCount & ensureRowCount
##            Fixed a few bugs with top row & left column indexes
##            Fixed a number of other stupid bugs
##            Added check on NaN before assigning data value to sprdsh-cell
## 2010-06-01 Checked logic. AFAICS all should work with upcoming jOpenDocument 1.2b4;
##            in 1.2b3 adding a newsheet always leaves an incomplete upper row;
##            supposedly (hopefully) that will be fixed in 1.2b4.
##     ''     Added check for jOpenDocument version. Adding sheets only works for
##            1.2b3+ (barring bug above)
## 2010-06-02 Fixed first sheet remaining in new spreadsheets
## 2010-08-01 Added option for crange to be only topleft cell address
##     ''     Code cleanup
## 2010-08-13 Fixed bug of ignoring text sheet name in case of new spreadsheet
## 2010-08-15 Fixed bug with invalid first sheet in new spreadsheets
## 2010-10-27 Improved file change tracking tru ods.changed
## 2010-11-12 Improved file change tracking tru ods.changed
## 2012-02-26 Write logicals as doubles (bug in jOpenDocument, would write as text)
## 2012-10-12 Renamed & moved into ./private
## 2012-10-24 Style fixes

function [ ods, rstatus ] = __JOD_oct2spsh__ (c_arr, ods, wsh, crange)

  rstatus = 0; sh = []; changed = 0;

  ## Get worksheet. Use first one if none given
  if (isempty (wsh)) wsh = 1; endif
  sh_cnt = ods.workbook.getSheetCount ();
  if (isnumeric (wsh))
    if (wsh > 1024)
      error ("Sheet number out of range of ODS specification (>1024)");
    elseif (wsh > sh_cnt)
      error ("Sheet number (%d) larger than number of sheets in file (%d)\n",...
              wsh, sh_cnt);
    else
      wsh = wsh - 1;
      sh = ods.workbook.getSheet (wsh);
      if (isempty (sh))
        ## Sheet number wsh didn't exist yet
        wsh = sprintf ("Sheet%d", wsh+1);
      elseif (ods.changed > 2)
        sh.setName ("Sheet1");
        changed = 1;
      endif
    endif
  endif
  ## wsh is now either a 0-based sheet no. or a string. In latter case:
  if (isempty (sh) && ischar (wsh))
    sh = ods.workbook.getSheet (wsh);
    if (isempty (sh))
      ## Still doesn't exist. Create sheet
      if (ods.odfvsn == 3)
        if (ods.changed > 2)
          ## 1st "new" -unnamed- sheet has already been made when creating the spreadsheet
          sh = ods.workbook.getSheet (0);
          sh.setName (wsh);
          changed = 1;
        else
          ## For existing spreadsheets
          ## printf ("Adding sheet '%s'\n", wsh);
          sh = ods.workbook.addSheet (sh_cnt, wsh);
          changed = 1;
        endif
      else
        error (["jOpenDocument v. 1.2b2 does not support adding sheets" ...
                " - upgrade to v. 1.2b3\n"]);
      endif
    endif
  endif

  [nr, nc] = size (c_arr);
  if (isempty (crange))
    trow = 0;
    lcol = 0;
    nrows = nr;
    ncols = nc;
  elseif (isempty (strfind (deblank (crange), ":"))) 
    [dummy1, dummy2, dummy3, trow, lcol] = parse_sp_range (crange);
    nrows = nr;
    ncols = nc;
    ## Row/col = 0 based in jOpenDocument
    trow = trow - 1; lcol = lcol - 1;
  else
    [dummy, nrows, ncols, trow, lcol] = parse_sp_range (crange);
    ## Row/col = 0 based in jOpenDocument
    trow = trow - 1; lcol = lcol - 1;
  endif

  if (trow > 65535 || lcol > 1023)
    error ("Topleft cell beyond spreadsheet limits (AMJ65536).");
  endif
  ## Check spreadsheet capacity beyond requested topleft cell
  nrows = min (nrows, 65536 - trow);    ## Remember, lcol & trow are zero-based
  ncols = min (ncols, 1024 - lcol);
  ## Check array size and requested range
  nrows = min (nrows, nr);
  ncols = min (ncols, nc);
  if (nrows < nr || ncols < nc) warning ("Array truncated to fit in range"); endif

  if (isnumeric (c_arr)) c_arr = num2cell (c_arr); endif

  ## Ensure sheet capacity is large enough to contain new data
  try    ## try-catch needed to work around bug in jOpenDocument v 1.2b3 and earlier
    sh.ensureColumnCount (lcol + ncols);  # Remember, lcol & trow are zero-based
  catch  ## catch is needed for new empty sheets (first ensureColCnt() hits null ptr)
    sh.ensureColumnCount (lcol + ncols);
    ## Kludge needed because upper row is defective (NPE jOpenDocument bug). ?Fixed in 1.2b4?
    if (trow == 0)
      ## Shift rows one down to avoid defective upper row
      ++trow;
      printf ("Info: empy upper row above data added to avoid JOD bug.\n");
    endif
  end_try_catch
  sh.ensureRowCount (trow + nrows);

  ## Write data to worksheet
  for ii = 1 : nrows
    for jj = 1 : ncols
      val = c_arr {ii, jj};
      if ((isnumeric (val) && ~isnan (val)) || ischar (val) || islogical (val))
        ## FIXME: jOpenDocument doesn't really support writing booleans (doesn't set OffValAttr)
        if (islogical (val)); val = double (val); endif
        try
          sh.getCellAt (jj + lcol - 1, ii + trow - 1).clearValue();
          jcell = sh.getCellAt (jj + lcol - 1, ii + trow - 1).setValue (val);
          changed = 1;
        catch
          ## No panic, probably a merged cell
          ##  printf (sprintf ("Cell skipped at (%d, %d)\n", ii+lcol-1, jj+trow-1));
        end_try_catch
      endif
    endfor
  endfor

  if (changed)
    ods.changed = max (min (ods.changed, 2), changed);  # Preserve 2 (new file), 1 (existing)
    rstatus = 1;
  endif

endfunction
