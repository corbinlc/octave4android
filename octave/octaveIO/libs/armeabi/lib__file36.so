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

## __JOD_spsh2oct__ - get data out of an ODS spreadsheet into octave using jOpenDocument.
## Watch out, no error checks, and spreadsheet formula error results
## are conveyed as 0 (zero).
##
## Author: Philip Nienhuis
## Created: 2009-12-13
## Last updates:
## 2010-12-31 Moved into subfunc of ods2oct
## 2010-08-12 Added separate stanzas for jOpenDocument v 1.2b3 and up. This version
##            allows better cell type parsing and is therefore more reliable
## 2010-10-27 Moved cropping rawarr from empty outer rows & columns to here
## 2010-11-13 Added workaround for reading text cells in files made by jOpenDocument 1.2bx
## 2011-09-18 Comment out workaround for jOpenDocument bug (no OfficeValueAttr set)
##            because this casts all numeric cells to string type for properly written ODS1.2
##     ''     Remove rstatus var (now set in caller)
## 2012-02-25 Fix reading string values written by JOD itself (no text attribue!!). But
##            the cntents could be BOOLEAN as well (JOD doesn't write OffVal attr either)
## 2012-02-26 Further workaround for reading strings (actually: cells w/o OfficeValueAttr)
## 2012-10-12 Renamed & moved into ./private
## 2012-10-24 Style fixes

function [ rawarr, ods] = __JOD_spsh2oct__ (ods, wsh, crange)

  persistent months;
  months = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};

  ## Check jOpenDocument version
  sh = ods.workbook.getSheet (0);
  cl = sh.getCellAt (0, 0);
  if (ods.odfvsn == 3)
    ## 1.2b3+ has public getValueType ()
    persistent ctype;
    if (isempty (ctype))
      BOOLEAN    = char (java_get ("org.jopendocument.dom.ODValueType", "BOOLEAN"));
      CURRENCY   = char (java_get ("org.jopendocument.dom.ODValueType", "CURRENCY"));
      DATE       = char (java_get ("org.jopendocument.dom.ODValueType", "DATE"));
      FLOAT      = char (java_get ("org.jopendocument.dom.ODValueType", "FLOAT"));
      PERCENTAGE = char (java_get ("org.jopendocument.dom.ODValueType", "PERCENTAGE"));
      STRING     = char (java_get ("org.jopendocument.dom.ODValueType", "STRING"));
      TIME       = char (java_get ("org.jopendocument.dom.ODValueType", "TIME"));
    endif
##  else
##    ## 1.2b2 has not
##    ver = 2;
  endif

  if (isnumeric (wsh)); wsh = wsh - 1; endif   ## Sheet INDEX starts at 0
  ## Check if sheet exists. If wsh = numeric, nonexistent sheets throw errors.
  try
    sh = ods.workbook.getSheet (wsh);
  catch
    error ("Illegal sheet number (%d) requested for file %s\n", wsh+1, ods.filename);
  end_try_catch
  ## If wsh = string, nonexistent sheets yield empty results
  if (isempty (sh))
    error ("No sheet called '%s' present in file %s\n", wsh, ods.filename);
  endif

  ## Either parse (given cell range) or prepare (unknown range) help variables 
  if (isempty (crange))
    if (ods.odfvsn < 3)
      error ("No empty read range allowed in jOpenDocument version 1.2b2")
    else
      if (isnumeric (wsh)); wsh = wsh + 1; endif
      [ trow, brow, lcol, rcol ] = getusedrange (ods, wsh);
      nrows = brow - trow + 1;  ## Number of rows to be read
      ncols = rcol - lcol + 1;  ## Number of columns to be read
    endif
  else
    [dummy, nrows, ncols, trow, lcol] = parse_sp_range (crange);
    ## Check ODS column limits
    if (lcol > 1024 || trow > 65536) 
      error ("ods2oct: invalid range; max 1024 columns & 65536 rows."); 
    endif
    ## Truncate range silently if needed
    rcol = min (lcol + ncols - 1, 1024);
    ncols = min (ncols, 1024 - lcol + 1);
    nrows = min (nrows, 65536 - trow + 1);
    brow= trow + nrows - 1;
  endif
  ## Create storage for data content
  rawarr = cell (nrows, ncols);

  if (ods.odfvsn >= 3) 
    ## Version 1.2b3+
    for ii=1:nrows
      for jj = 1:ncols
        try
          scell = sh.getCellAt (lcol+jj-2, trow+ii-2);
          sctype = char (scell.getValueType ());
          switch sctype
            case { FLOAT, CURRENCY, PERCENTAGE }
              rawarr{ii, jj} = scell.getValue ().doubleValue ();
            case BOOLEAN
              rawarr {ii, jj} = scell.getValue () == 1;
            case STRING
              rawarr{ii, jj} = scell.getValue();
            case DATE
              tmp = strsplit (char (scell.getValue ()), " ");
              yy = str2num (tmp{6});
              mo = find (ismember (months, toupper (tmp{2})) == 1);
              dd = str2num (tmp{3});
              hh = str2num (tmp{4}(1:2));
              mi = str2num (tmp{4}(4:5));
              ss = str2num (tmp{4}(7:8));
              rawarr{ii, jj} = datenum (yy, mo, dd, hh, mi, ss);
            case TIME
              tmp = strsplit (char (scell.getValue ().getTime ()), " ");
              hh = str2num (tmp{4}(1:2)) /    24.0;
              mi = str2num (tmp{4}(4:5)) /  1440.0;
              ss = str2num (tmp{4}(7:8)) / 86600.0;
              rawarr {ii, jj} = hh + mi + ss;
            otherwise
              ## Workaround for sheets written by jOpenDocument (no value-type attrb):
              if (~isempty (scell.getValue) )
                ## FIXME Assume cell contains string if there's a text attr. 
                ## But it could be BOOLEAN too...
                if (findstr ("<text:", char (scell))), sctype = STRING; endif
                rawarr{ii, jj} = scell.getValue();
              endif
              ## Nothing
          endswitch
        catch
          ## Probably a merged cell, just skip
          ## printf ("Error in row %d, col %d (addr. %s)\n", 
          ## ii, jj, calccelladdress (lcol+jj-2, trow+ii-2));
        end_try_catch
      endfor
    endfor
  else  ## ods.odfvsn == 3
    ## 1.2b2
    for ii=1:nrows
      for jj = 1:ncols
        celladdress = calccelladdress (trow+ii-1, lcol+jj-1);
        try
          val = sh.getCellAt (celladdress).getValue ();
        catch
          ## No panic, probably a merged cell
          val = {};
        end_try_catch
        if (~isempty (val))
          if (ischar (val))
            ## Text string
            rawarr(ii, jj) = val;
          elseif (isnumeric (val))
            ## Boolean
            if (val) rawarr(ii, jj) = true; else; rawarr(ii, jj) = false; endif
          else
            try
              val = sh.getCellAt (celladdress).getValue ().doubleValue ();
              rawarr(ii, jj) = val;
            catch
              val = char (val);
              if (isempty (val))
                ## Probably empty Cell
              else
                ## Maybe date / time value. Dirty hack to get values:
                mo = strmatch (toupper (val(5:7)), months);
                dd = str2num (val(9:10));
                yy = str2num (val(25:end));
                hh = str2num (val(12:13));
                mm = str2num (val(15:16));
                ss = str2num (val(18:19));
                rawarr(ii, jj) = datenum (yy, mo, dd, hh, mm,ss);
              endif
            end_try_catch
          endif
        endif
      endfor
    endfor

  endif  

  ## Keep track of data rectangle limits
  ods.limits = [lcol, rcol; trow, brow];

endfunction
