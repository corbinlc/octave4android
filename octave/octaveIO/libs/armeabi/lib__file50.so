## Copyright (C) 2010,2011,2012 Philip Nienhuis <prnienhuis@users.sf.net>
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

## __OTK_spsh2oct__: internal function for reading odf files using odfdom-0.8.6+

## Author: Philip Nienhuis <Philip@DESKPRN>
## Created: 2010-08-24. First workable version Aug 27, 2010
## Updates:
## 2010-10-27 Moved cropping rawarr from empty outer rows & columns to caller
## 2010-11-13 Added workaround for reading text cells in files made by jOpenDocument 1.2bx
## 2011-09-18 Comment out workaround for jOpenDocument bug (no OfficeValueAttr set)
##            because this casts all numeric cells to string type for properly written ODS1.2
##     ''     Remove rstatus var (now set in caller)
## 2012-10-12 Renamed & moved into ./private
## 2012-10-24 Style fixes

function [ rawarr, ods ] = __OTK_spsh2oct__ (ods, wsh, crange, spsh_opts)

  ## Get contents and table stuff from the workbook
  odfcont = ods.workbook;  ## Use a local copy just to be sure. octave 
                           ## makes physical copies only when needed (?)
  
  ## Parse sheets ("tables") from ODS file
  sheets = ods.app.getTableList();
  nr_of_sheets = sheets.size ();

  ## Check user input & find sheet pointer (1-based)
  if (~isnumeric (wsh))
    try
      sh = ods.app.getTableByName (wsh);
      sh_err = isempty (sh);
    catch
      sh_err = 1;
    end_try_catch
    if (sh_err)
      error (sprintf ("Sheet %s not found in file %s\n", wsh, ods.filename)); 
    endif
  elseif (wsh > nr_of_sheets || wsh < 1)
    ## We already have a numeric sheet pointer. If it's not in range:
    error (sprintf ("Worksheet no. %d out of range (1 - %d)", wsh, nr_of_sheets));
  else
    sh = sheets.get (wsh - 1);
  endif

  ## Either parse (given cell range) or prepare (unknown range) help variables 
  if (isempty (crange))
    if ~isnumeric (wsh)
      ## Get sheet index
      jj = nr_of_sheets;
      while jj-- >= 0
        if (strcmp (wsh, sheets.get(jj).getTableName()) == 1)
          wsh = jj +1;
          jj = -1;
        endif
      endwhile
    endif
    [ trow, brow, lcol, rcol ] = getusedrange (ods, wsh);
    nrows = brow - trow + 1;  ## Number of rows to be read
    ncols = rcol - lcol + 1;  ## Number of columns to be read
  else
    [dummy, nrows, ncols, trow, lcol] = parse_sp_range (crange);
    ## Check ODS row/column limits
    if (lcol > 1024 || trow > 65536) 
      error ("ods2oct: invalid range; max 1024 columns & 65536 rows."); 
    endif
    ## Truncate range silently if needed
    rcol = min (lcol + ncols - 1, 1024);
    ncols = min (ncols, 1024 - lcol + 1);
    nrows = min (nrows, 65536 - trow + 1);
    brow = trow + nrows - 1;
  endif

  ## Create storage for data content
  rawarr = cell (nrows, ncols);

  ## Read from worksheet row by row. Row numbers are 0-based
  for ii=trow:nrows+trow-1
    row = sh.getRowByIndex (ii-1);
    for jj=lcol:ncols+lcol-1;
      ocell = row.getCellByIndex (jj-1);
      if ~isempty (ocell)
        otype = deblank (tolower (ocell.getValueType ()));
         if (spsh_opts.formulas_as_text)
          if ~isempty (ocell.getFormula ())
            otype = "formula";
          endif
        endif
##        ## Provisions for catching jOpenDocument 1.2b bug where text cells
##        ## haven't been assigned an <office:value-type='string'> attribute
##        if (~isempty (ocell))
##          if (findstr ("<text:", char (ocell.getOdfElement ())))
##            otype = "string"; 
##          endif
##        endif
        ## At last, read the data
        switch otype
          case  {"float", "currency", "percentage"}
            rawarr(ii-trow+1, jj-lcol+1) = ocell.getDoubleValue ();
          case "date"
            ## Dive into TableTable API
            tvalue = ocell.getOdfElement ().getOfficeDateValueAttribute ();
            ## Dates are returned as octave datenums, i.e. 0-0-0000 based
            yr = str2num (tvalue(1:4));
            mo = str2num (tvalue(6:7));
            dy = str2num (tvalue(9:10));
            if (index (tvalue, "T"))
              hh = str2num (tvalue(12:13));
              mm = str2num (tvalue(15:16));
              ss = str2num (tvalue(18:19));
              rawarr(ii-trow+1, jj-lcol+1) = datenum (yr, mo, dy, hh, mm, ss);
            else
              rawarr(ii-trow+1, jj-lcol+1) = datenum (yr, mo, dy);
            endif
          case "time"
            ## Dive into TableTable API
            tvalue = ocell.getOdfElement ().getOfficeTimeValueAttribute ();
            if (index (tvalue, "PT"))
              hh = str2num (tvalue(3:4));
              mm = str2num (tvalue(6:7));
              ss = str2num (tvalue(9:10));
              rawarr(ii-trow+1, jj-lcol+1) = datenum (0, 0, 0, hh, mm, ss);
            endif
          case "boolean"
            rawarr(ii-trow+1, jj-lcol+1) = ocell.getBooleanValue ();
          case "string"
            rawarr(ii-trow+1, jj-lcol+1) = ocell.getStringValue ();
##          ## Code left in for in case odfdom 0.8.6+ has similar bug
##          ## as 0.7.5
##          cvalue = tcell.getOfficeStringValueAttribute ();
##          if (isempty (cvalue))     ## Happens with e.g., hyperlinks
##            tmp = char (tcell);
##            ## Hack string value from between <text:p|r> </text:p|r> tags
##            ist = findstr (tmp, "<text:");
##            if (ist)
##              ist = ist (length (ist));
##              ist = ist + 8;
##              ien = index (tmp(ist:end), "</text") + ist - 2;
##              tmp (ist:ien);
##              cvalue = tmp(ist:ien);
##            endif
##          endif
##          rawarr(ii-trow+1, jj-lcol+1)= cvalue;
          case "formula"
            rawarr(ii-trow+1, jj-lcol+1) = ocell.getFormula ();
          otherwise
            ## Nothing.
        endswitch
      endif
    endfor
  endfor

  ## Keep track of data rectangle limits
  ods.limits = [lcol, rcol; trow, brow];

endfunction
