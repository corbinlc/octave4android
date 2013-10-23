## Copyright (C) 2009,2010,2011,2012 Philip Nienhuis <prnienhuis _at- users.sf.net>
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

## __OTK_ods2oct__ - read ODS spreadsheet data using Java & odftoolkit 0.7.5
## You need proper java-for-octave & odfdom.jar + xercesImpl.jar 2.9.1
## in your javaclasspath.

## Author: Philip Nenhuis <pr.nienhuis at users.sf.net>
## Created: 2009-12-24
## Updates:
## 2010-01-08 First working version
## 2010-03-18 Fixed many bugs with wrong row references in case of empty upper rows
##     ""     Fixed reference to upper row in case of nr-rows-repeated top tablerow
##     ""     Tamed down memory usage for rawarr when desired data range is given
##     ""     Added call to getusedrange() for cases when no range was specified
## 2010-03-19 More code cleanup & fixes for bugs introduced 18/3/2010 8-()
## 2010-08-03 Added preliminary support for reading back formulas as text strings
## 2010-10-27 Moved cropping rawarr from empty outer rows & columns to caller
## 2011-09-18 Remove rstatus var (now set in caller)
## 2012-10-12 Renamed & moved 2 ./private; soon te be dropped as OTK 0.7.5 is too old
## 2012-10-24 Style fixes

function [ rawarr, ods ] = __OTK_ods2oct__ (ods, wsh, crange, spsh_opts)

  ## Parts after user gfterry in
  ## http://www.oooforum.org/forum/viewtopic.phtml?t=69060
  
  ## Get contents and table stuff from the workbook
  odfcont = ods.workbook;    ## Use a local copy just to be sure. octave 
                            ## makes physical copies only when needed (?)
  xpath = ods.app.getXPath;
  
  ## AFAICS ODS spreadsheets have the following hierarchy (after Xpath processing):
  ## <table:table> - table nodes, the actual worksheets;
  ## <table:table-row> - row nodes, the rows in a worksheet;
  ## <table:table-cell> - cell nodes, the cells in a row;
  ## Styles (formatting) are defined in a section "settings" outside the
  ## contents proper but are referenced in the nodes.
  
  ## Create an instance of type NODESET for use in subsequent statement
  NODESET = java_get ("javax.xml.xpath.XPathConstants", "NODESET");
  ## Parse sheets ("tables") from ODS file
  sheets = xpath.evaluate ("//table:table", odfcont, NODESET);
  nr_of_sheets = sheets.getLength ();

  ## Check user input & find sheet pointer (1-based), using ugly hacks
  if (~isnumeric (wsh))
    ## Search in sheet names, match sheet name to sheet number
    ii = 0;
    while (++ii <= nr_of_sheets && ischar (wsh))  
      ## Look in first part of the sheet nodeset
      sh_name = sheets.item(ii-1).getTableNameAttribute ();
      if (strcmp (sh_name, wsh))
        ## Convert local copy of wsh into a number (pointer)
        wsh = ii;
      endif
    endwhile
    if (ischar (wsh))
      error (sprintf ("No worksheet '%s' found in file %s", wsh, ods.filename));
    endif
  elseif (wsh > nr_of_sheets || wsh < 1)
    ## We already have a numeric sheet pointer. If it's not in range:
    error (sprintf ("Worksheet no. %d out of range (1 - %d)", wsh, nr_of_sheets));
  endif

  ## Get table-rows in sheet no. wsh. Sheet count = 1-based (!)
  str = sprintf ("//table:table[%d]/table:table-row", wsh);
  sh = xpath.evaluate (str, odfcont, NODESET);
  nr_of_rows = sh.getLength (); 

  ## Either parse (given cell range) or prepare (unknown range) help variables 
  if (isempty (crange))
    [ trow, brow, lcol, rcol ] = getusedrange (ods, wsh);
    nrows = brow - trow + 1;  ## Number of rows to be read
    ncols = rcol - lcol + 1;  ## Number of columns to be read
  else
    [dummy, nrows, ncols, trow, lcol] = parse_sp_range (crange);
    brow = min (trow + nrows - 1, nr_of_rows);
    ## Check ODS column limits
    if (lcol > 1024 || trow > 65536) 
      error ("ods2oct: invalid range; max 1024 columns & 65536 rows."); 
    endif
    ## Truncate range silently if needed
    rcol = min (lcol + ncols - 1, 1024);
    ncols = min (ncols, 1024 - lcol + 1);
    nrows = min (nrows, 65536 - trow + 1);
  endif
  ## Create storage for data content
  rawarr = cell (nrows, ncols);

  ## Prepare reading sheet row by row
  rightmcol = 0;    ## Used to find actual rightmost column
  ii = trow - 1;    ## Spreadsheet row counter
  rowcnt = 0;
  ## Find uppermost requested *tablerow*. It may be influenced by nr-rows-repeated
  if (ii >= 1)
    tfillrows = 0;
    while (tfillrows < ii)
      row = sh.item(tfillrows);
      extrarows = row.getTableNumberRowsRepeatedAttribute ();
      tfillrows = tfillrows + extrarows;
      ++rowcnt;
    endwhile
    ## Desired top row may be in a nr-rows-repeated tablerow....
    if (tfillrows > ii); ii = tfillrows; endif
  endif

  ## Read from worksheet row by row. Row numbers are 0-based
  while (ii < brow)
    row = sh.item(rowcnt++);
    nr_of_cells = min (row.getLength (), rcol);
    rightmcol = max (rightmcol, nr_of_cells);  ## Keep track of max row length
    ## Read column (cell, "table-cell" in ODS speak) by column
    jj = lcol; 
    while (jj <= rcol)
      tcell = row.getCellAt(jj-1);
      form = 0;
      if (~isempty (tcell))     ## If empty it's possibly in columns-repeated/spanned
        if (spsh_opts.formulas_as_text)   ## Get spreadsheet formula rather than value
          ## Check for formula attribute
          tmp = tcell.getTableFormulaAttribute ();
          if isempty (tmp)
            form = 0;
          else
            if (strcmp (tolower (tmp(1:3)), "of:"))
              tmp (1:end-3) = tmp(4:end);
            endif
            rawarr(ii-trow+2, jj-lcol+1) = tmp;
            form = 1;
          endif
        endif
        if ~(form || index (char(tcell), "text:p>Err:") ...
                  || index (char(tcell), "text:p>##DIV"))  
          ## Get data from cell
          ctype = tcell.getOfficeValueTypeAttribute ();
          cvalue = tcell.getOfficeValueAttribute ();
          switch deblank (ctype)
            case  {"float", "currency", "percentage"}
              rawarr(ii-trow+2, jj-lcol+1) = cvalue;
            case "date"
              cvalue = tcell.getOfficeDateValueAttribute ();
              ## Dates are returned as octave datenums, i.e. 0-0-0000 based
              yr = str2num (cvalue(1:4));
              mo = str2num (cvalue(6:7));
              dy = str2num (cvalue(9:10));
              if (index (cvalue, "T"))
                hh = str2num (cvalue(12:13));
                mm = str2num (cvalue(15:16));
                ss = str2num (cvalue(18:19));
                rawarr(ii-trow+2, jj-lcol+1) = datenum (yr, mo, dy, hh, mm, ss);
              else
                rawarr(ii-trow+2, jj-lcol+1) = datenum (yr, mo, dy);
              endif
            case "time"
              cvalue = tcell.getOfficeTimeValueAttribute ();
              if (index (cvalue, "PT"))
                hh = str2num (cvalue(3:4));
                mm = str2num (cvalue(6:7));
                ss = str2num (cvalue(9:10));
                rawarr(ii-trow+2, jj-lcol+1) = datenum (0, 0, 0, hh, mm, ss);
              endif
            case "boolean"
              cvalue = tcell.getOfficeBooleanValueAttribute ();
              rawarr(ii-trow+2, jj-lcol+1) = cvalue; 
            case "string"
              cvalue = tcell.getOfficeStringValueAttribute ();
              if (isempty (cvalue))     ## Happens with e.g., hyperlinks
                tmp = char (tcell);
                ## Hack string value from between <text:p|r> </text:p|r> tags
                ist = findstr (tmp, "<text:");
                if (ist)
                  ist = ist (length (ist));
                  ist = ist + 8;
                  ien = index (tmp(ist:end), "</text") + ist - 2;
                  tmp (ist:ien);
                  cvalue = tmp(ist:ien);
                endif
              endif
              rawarr(ii-trow+2, jj-lcol+1)= cvalue;
            otherwise
              ## Nothing
          endswitch
        endif
      endif
      ++jj;            ## Next cell
    endwhile

    ## Check for repeated rows (i.e. condensed in one table-row)
    extrarows = row.getTableNumberRowsRepeatedAttribute () - 1;
    if (extrarows > 0 && (ii + extrarows) < 65535)
      ## Expand rawarr cf. table-row
      nr_of_rows = nr_of_rows + extrarows;
      ii = ii + extrarows;
    endif
    ++ii;
  endwhile

  ## Keep track of data rectangle limits
  ods.limits = [lcol, rcol; trow, brow];

endfunction
