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

## __OTK_oct2ods__
## write data array to an ODS spreadsheet using Java & ODFtoolkit 0.7.5
## Note: __OTK_oct2spsh__ uses more recent odfdom that operates at higher level

## I'm truly sorry that oct2jotk2ods is so ridiculously complex,
## and therefore so slow; but there's a good reason for that:
## Writing to ODS is already fairly complicated when just making a
## new sheet ("table"); but it really becomes a headache when
## writing to an existing sheet. In that case one should beware of
## table-number-columns-repeated, table-number-rows-repeated,
## covered (merged) cells, incomplete tables and rows, etc.
## ODF toolkit v. 0.7.5 does nothing to hide this from the user;
## you may sort it out all by yourself.

## Author: Philip Nienhuis <prnienhuis@users.sf.net>
## Created: 2010-01-07
## Updates: 
## 2010-01-14 (finally seems to work OK)
## 2010-03-08 Some comment lines adapted
## 2010-03-25 Try-catch added f. unpatched-for-booleans java-1.2.6 / 1.2.7 package
## 2010-04-11 Changed all references to "cell" to "scell" to avoid reserved keyword
##     ''     Small bugfix for cases with empty left columns (wrong cell reference)
## 2010-04-13 Fixed bug with stray cell copies beyond added data rectangle
## 2010-07-29 Added formula input support (based on xls patch by Benjamin Lindner)
## 2010-08-01 Added try-catch around formula input
##     ''     Changed range arg to also allow just topleft cell
## 2010-08-03 Moved range checks and type array parsing to separate functions
## 2010-08-13 Fixed empty Sheet1 in case of new spreadsheets, fix input text sheet name
## 2010-10-27 Improved file change tracking tru ods.changed
## 2010-11-12 Improved file change tracking tru ods.changed
## 2012-10-12 Renamed & moved into ./private
## 2012-10-24 Style fixes

function [ ods, rstatus ] = __OTK_oct2ods__ (c_arr, ods, wsh, crange, spsh_opts)

  persistent ctype;
  if (isempty (ctype))
    ## Number, Boolean, String, Formula, Empty, Date, Time (last 2 are ignored)
    ctype = [1, 2, 3, 4, 5, 6, 7];
  endif

  rstatus = 0; f_errs = 0;

  ## Get some basic spreadsheet data from the pointer using ODFtoolkit
  odfcont = ods.workbook;
  xpath = ods.app.getXPath ();
  offsprdsh = ods.app.getContentRoot();
  autostyles = odfcont.getOrCreateAutomaticStyles();
  officestyles = ods.app.getOrCreateDocumentStyles();

  ## Create an instance of type NODESET for use in subsequent statements
  NODESET = java_get ("javax.xml.xpath.XPathConstants", "NODESET");

  ## Parse sheets ("tables") from ODS file
  sheets = xpath.evaluate ("//table:table", odfcont, NODESET);
  nr_of_sheets = sheets.getLength ();
  newsh = 0;                ## Assume existing sheet
  if isempty (wsh) wsh = 1; endif
  if (~isnumeric (wsh))          ## Sheet name specified
    ## Search in sheet names, match sheet name to sheet number.
    ## Beware, 0-based index, 1-based count!
    ii = 0;
    while (++ii <= nr_of_sheets && ischar (wsh))  
      ## Look in first part of the sheet nodeset
      sh_name = sheets.item(ii-1).getTableNameAttribute ();
      if (strcmp (sh_name, wsh))
        ## Convert local copy of wsh into a number (pointer)
        wsh = ii - 1;
      endif
    endwhile
    if (ischar (wsh) && nr_of_sheets < 256) newsh = 1; endif
  else                    ## Sheet index specified
    if ((ods.changed > 2) || (wsh > nr_of_sheets && wsh < 256))  ## Max nr of sheets = 256
      ## Create a new sheet
      newsh = 1;
    elseif (wsh <=nr_of_sheets && wsh > 0)
      ## Existing sheet. Count = 1-based, index = 0-based
      --wsh; sh = sheets.item(wsh);
      printf ("Writing to sheet %s\n", sh.getTableNameAttribute());
    else
      error ("oct2ods: illegal sheet number.");
    endif
  endif

## Check size of data array & range / capacity of worksheet & prepare vars
  [nr, nc] = size (c_arr);
  [topleft, nrows, ncols, trow, lcol] = ...
                    spsh_chkrange (crange, nr, nc, ods.xtype, ods.filename);
  --trow; --lcol;                  ## Zero-based row ## & col ##
  if (nrows < nr || ncols < nc)
    warning ("Array truncated to fit in range");
    c_arr = c_arr(1:nrows, 1:ncols);
  endif
  
## Parse data array, setup typarr and throw out NaNs  to speed up writing;
  typearr = spsh_prstype (c_arr, nrows, ncols, ctype, spsh_opts, 0);
  if ~(spsh_opts.formulas_as_text)
    ## Find formulas (designated by a string starting with "=" and ending in ")")
    fptr = cellfun (@(x) ischar (x) && strncmp (x, "=", 1) ...
                                    && strncmp (x(end:end), ")", 1), c_arr);
    typearr(fptr) = ctype(4);          ## FORMULA
  endif

## Prepare worksheet for writing. If needed create new sheet
  if (newsh)
    if (ods.changed > 2)
      ## New spreadsheet. Prepare to use the default 1x1 first sheet.
      sh = sheets.item(0);
    else
      ## Other sheets exist, create a new sheet. First the basics
      sh = java_new ("org.odftoolkit.odfdom.doc.table.OdfTable", odfcont);
      ## Append sheet to spreadsheet ( contentRoot)
      offsprdsh.appendChild (sh);
      ## Rebuild sheets nodes
      sheets = xpath.evaluate ("//table:table", odfcont, NODESET);
    endif 

    ## Sheet name
    if (isnumeric (wsh))
      ## Give sheet a name
      str = sprintf ("Sheet%d", wsh);
      sh.setTableNameAttribute (str);
    else
      ## Assign name to sheet and change wsh into numeric pointer
      sh.setTableNameAttribute (wsh);
      wsh = sheets.getLength () - 1;
    endif
    ## Fixup wsh pointer in case of new spreadsheet
    if (ods.changed > 2) wsh = 0; endif

    ## Add table-column entry for style etc
    col = sh.addTableColumn ();
    col.setTableDefaultCellStyleNameAttribute ("Default");
    col.setTableNumberColumnsRepeatedAttribute (lcol + ncols + 1);
    col.setTableStyleNameAttribute ("co1");

  ## Build up the complete row & cell structure to cover the data array.
  ## This will speed up processing later

    ## 1. Build empty table row template
    row = java_new ("org.odftoolkit.odfdom.doc.table.OdfTableRow", odfcont);
    ## Create an empty tablecell & append it to the row
    scell = java_new ("org.odftoolkit.odfdom.doc.table.OdfTableCell", odfcont);
    scell = row.appendCell (scell);
    scell.setTableNumberColumnsRepeatedAttribute (1024);
    ## 2. If needed add empty filler row above the data rows & if needed add repeat count
    if (trow > 0)        
      sh.appendRow (row);
      if (trow > 1) row.setTableNumberRowsRepeatedAttribute (trow); endif
    endif
    ## 3. Add data rows; first one serves as a template
    drow = java_new ("org.odftoolkit.odfdom.doc.table.OdfTableRow", odfcont);
    if (lcol > 0) 
      scell = java_new ("org.odftoolkit.odfdom.doc.table.OdfTableCell", odfcont);
      drow.appendCell (scell);
      if (lcol > 1) scell.setTableNumberColumnsRepeatedAttribute (lcol); endif
    endif
    ## 4. Add data cell placeholders
    scell = java_new ("org.odftoolkit.odfdom.doc.table.OdfTableCell", odfcont);
    drow.appendCell (scell);
    for jj=2:ncols
      dcell = scell.cloneNode (1);    ## Deep copy
      drow.appendCell (dcell);
    endfor
    ## 5. Last cell is remaining column counter
    rest = max (1024 - lcol - ncols);
    if (rest)
      dcell = scell.cloneNode (1);    ## Deep copy
      drow.appendCell (dcell);
      if (rest > 1) dcell.setTableNumberColumnsRepeatedAttribute (rest); endif
    endif
    ## Only now add drow as otherwise for each cell an empty table-column is
    ## inserted above the rows (odftoolkit bug?)
    sh.appendRow (drow);
    if (ods.changed > 2)
      ## In case of a completely new spreadsheet, delete the first initial 1-cell row
      ## But check if it *is* a row...
      try
        sh.removeChild (drow.getPreviousRow ());
      catch
        ## Nothing. Apparently there was only the just appended row.
      end_try_catch
    endif
    ## 6. Row template ready. Copy row template down to cover future array
    for ii=2:nrows
      nrow = drow.cloneNode (1);  ## Deep copy
      sh.appendRow (nrow);
    endfor
    ods.changed = min (ods.changed, 2);    ## Keep 2 for new spshsht, 1 for existing + changed

  else
    ## Existing sheet. We must be prepared for all situations, incomplete rows,
    ## number-rows/columns-repeated, merged (spanning) cells, you name it.
    ## First explore row buildup of existing sheet using an XPath
    sh = sheets.item(wsh);                      ## 0 - based
    str = sprintf ("//table:table[%d]/table:table-row", wsh + 1);  ## 1 - based 
    trows = xpath.evaluate (str, odfcont, NODESET);
    nr_of_trows = trows.getLength();   ## Nr. of existing table-rows, not data rows!

    ## For the first rows we do some preprocessing here. Similar stuff for cells
    ## i.e. table-cells (columns) is done in the loops below.
    ## Make sure the upper data array row doesn't end up in a nr-rows-repeated row

    ## Provisionally! set start table-row in case "while" & "if" (split) are skipped
    drow = trows.item(0);  
    rowcnt = 0; trowcnt = 0;          ## Spreadsheet/ table-rows, resp;
    while (rowcnt < trow && trowcnt < nr_of_trows)
      ## Count rows & table-rows UNTIL we reach trow
      ++trowcnt;                ## Nr of table-rows
      row = drow;
      drow = row.getNextSibling ();
      repcnt = row.getTableNumberRowsRepeatedAttribute();
      rowcnt = rowcnt + repcnt;        ## Nr of spreadsheet rows
    endwhile
    rsplit = rowcnt - trow;
    if (rsplit > 0)
      ## Apparently a nr-rows-repeated top table-row must be split, as the
      ## first data row seems to be projected in it (1st while condition above!)
      row.removeAttribute ("table:number-rows-repeated");
      row.getCellAt (0).removeAttribute ("table:number-columns-repeated");
      nrow = row.cloneNode (1);
      drow = nrow;              ## Future upper data array row
      if (repcnt > 1)
        row.setTableNumberRowsRepeatedAttribute (repcnt - rsplit);
      else
        row.removeAttribute ("table:number-rows-repeated");
      endif
      rrow = row.getNextSibling ();
      sh.insertBefore (nrow, rrow);
      for jj=2:rsplit
        nrow = nrow.cloneNode (1);
        sh.insertBefore (nrow, rrow);
      endfor
    elseif (rsplit < 0)
      ## New data rows to be added below existing data & table(!) rows, i.e.
      ## beyond lower end of the current sheet. Add filler row and 1st data row
      row = java_new ("org.odftoolkit.odfdom.doc.table.OdfTableRow", odfcont);
      drow = row.cloneNode (1);                ## First data row
      row.setTableNumberRowsRepeatedAttribute (-rsplit);    ## Filler row
      scell = java_new ("org.odftoolkit.odfdom.doc.table.OdfTableCell", odfcont);
      dcell = scell.cloneNode (1);
      scell.setTableNumberColumnsRepeatedAttribute (COL_CAP);  ## Filler cell
      row.appendCell (scell);
      sh.appendRow (row);
      drow.appendCell (dcell);
      sh.appendRow (drow);
    endif
  endif

## For each row, for each cell, add the data. Expand row/column-repeated nodes

  row = drow;      ## Start row; pointer still exists from above stanzas
  for ii=1:nrows
    if (~newsh)    ## Only for existing sheets the next checks should be made
      ## While processing next data rows, fix table-rows if needed
      if (isempty (row) || (row.getLength () < 1))
        ## Append an empty row with just one empty cell
        row = java_new ("org.odftoolkit.odfdom.doc.table.OdfTableRow", odfcont);
        scell = java_new ("org.odftoolkit.odfdom.doc.table.OdfTableCell", odfcont);
        scell.setTableNumberColumnsRepeatedAttribute (lcol + 1);
        row.appendCell (scell);
        sh.appendRow (row);
      else
        ## If needed expand nr-rows-repeated
        repcnt = row.getTableNumberRowsRepeatedAttribute ();
        if (repcnt > 1)
          row.removeAttribute ("table:number-rows-repeated");
          ## Insert new table-rows above row until our new data space is complete.
          ## Keep handle of upper new table-row as that's where data are added 1st
          drow = row.cloneNode (1);
          sh.insertBefore (drow, row);
          for kk=1:min (repcnt, nrows-ii)
            nrow = row.cloneNode (1);
            sh.insertBefore (nrow, row);
          endfor
          if (repcnt > nrows-ii+1)
            row.setTableNumberRowsRepeatedAttribute (repcnt - nrows +ii - 1);
          endif
          row = drow;
        endif
      endif

      ## Check if leftmost cell ends up in nr-cols-repeated cell
      colcnt = 0; tcellcnt = 0; rcellcnt = row.getLength();
      dcell = row.getCellAt (0);
      while (colcnt < lcol && tcellcnt < rcellcnt)
        ## Count columns UNTIL we hit lcol
        ++tcellcnt;            ## Nr of table-cells counted
        scell = dcell;
        dcell = scell.getNextSibling ();
        repcnt = scell.getTableNumberColumnsRepeatedAttribute ();
        colcnt = colcnt + repcnt;    ## Nr of spreadsheet cell counted
      endwhile
      csplit = colcnt - lcol;
      if (csplit > 0)
        ## Apparently a nr-columns-repeated cell must be split
        scell.removeAttribute ("table:number-columns-repeated");
        ncell = scell.cloneNode (1);
        if (repcnt > 1)
          scell.setTableNumberColumnsRepeatedAttribute (repcnt - csplit);
        else
          scell.removeAttribute ("table:number-columns-repeated");
        endif
        rcell = scell.getNextSibling ();
        row.insertBefore (ncell, rcell);
        for jj=2:csplit
          ncell = ncell.cloneNode (1);
          row.insertBefore (ncell, rcell);
        endfor
      elseif (csplit < 0)
        ## New cells to be added beyond current last cell & table-cell in row
        dcell = java_new ("org.odftoolkit.odfdom.doc.table.OdfTableCell", odfcont);
        scell = dcell.cloneNode (1);
        dcell.setTableNumberColumnsRepeatedAttribute (-csplit);
        row.appendCell (dcell);
        row.appendCell (scell);
      endif
    endif

  ## Write a row of data from data array, column by column
  
    for jj=1:ncols
      scell = row.getCellAt (lcol + jj - 1);
      if (~newsh)
        if (isempty (scell))
          ## Apparently end of row encountered. Add cell
          scell = java_new ("org.odftoolkit.odfdom.doc.table.OdfTableCell", odfcont);
          scell = row.appendCell (scell);
        else
          ## If needed expand nr-cols-repeated
          repcnt = scell.getTableNumberColumnsRepeatedAttribute ();
          if (repcnt > 1)
            scell.removeAttribute ("table:number-columns-repeated");
            for kk=2:repcnt
              ncell = scell.cloneNode (1);
              row.insertBefore (ncell, scell.getNextSibling ());
            endfor
          endif
        endif
        ## Clear text contents
        while (scell.hasChildNodes ())
          tmp = scell.getFirstChild ();
          scell.removeChild (tmp);
        endwhile
        scell.removeAttribute ("table:formula");
      endif

      ## Empty cell count stuff done. At last we can add the data
      switch (typearr (ii, jj))
        case 1  ## float
          scell.setOfficeValueTypeAttribute ("float");
          scell.setOfficeValueAttribute (c_arr{ii, jj});
        case 2    ## boolean
          ## Beware, for unpatched-for-booleans java-1.2.7- we must resort to floats
          try
            ## First try the preferred java-boolean way
            scell.setOfficeValueTypeAttribute ("boolean");
            scell.removeAttribute ("office:value");
            if (c_arr{ii, jj})
              scell.setOfficeBooleanValueAttribute (1);
            else
              scell.setOfficeBooleanValueAttribute (0);
            endif
          catch
            ## Unpatched java package. Fall back to transferring a float
            scell.setOfficeValueTypeAttribute ("float");
            if (c_arr{ii, jj})
              scell.setOfficeValueAttribute (1);
            else
              scell.setOfficeValueAttribute (0);
            endif
          end_try_catch
        case 3  ## string
          scell.setOfficeValueTypeAttribute ("string");
          pe = java_new ("org.odftoolkit.odfdom.doc.text.OdfTextParagraph",...
                          odfcont,"", c_arr{ii, jj});
          scell.appendChild (pe);
        case 4  ## Formula.  
          ## As we don't know the result type, simply remove previous type info.
          ## Once OOo Calc reads it, it'll add the missing attributes
          scell.removeAttribute ("office:value");
          scell.removeAttribute ("office:value-type");
          ## Try-catch not strictly needed, there's no formula validator yet
          try
            scell.setTableFormulaAttribute (c_arr{ii, jj});
            scell.setOfficeValueTypeAttribute ("string");
            pe = java_new ("org.odftoolkit.odfdom.doc.text.OdfTextParagraph",...
                            odfcont,"", "##Recalc Formula##");
            scell.appendChild (pe);
          catch
            ++f_errs;
            scell.setOfficeValueTypeAttribute ("string");
            pe = java_new ("org.odftoolkit.odfdom.doc.text.OdfTextParagraph",...
                            odfcont,"", c_arr{ii, jj});
            scell.appendChild (pe);
          end_try_catch
        case {0 5}  ## Empty. Clear value attributes
          if (~newsh)
            scell.removeAttribute ("office:value-type");
            scell.removeAttribute ("office:value");
          endif
        case 6  ## Date (implemented but Octave has no "date" data type - yet?)
          scell.setOfficeValueTypeAttribute ("date");
          [hh mo dd hh mi ss] = datevec (c_arr{ii,jj});
          str = sprintf ("%4d-%2d-%2dT%2d:%2d:%2d", yy, mo, dd, hh, mi, ss);
          scell.setOfficeDateValueAttribute (str);
        case 7  ## Time (implemented but Octave has no "time" data type)
          scell.setOfficeValueTypeAttribute ("time");
          [hh mo dd hh mi ss] = datevec (c_arr{ii,jj});
          str = sprintf ("PT%2d:%2d:%2d", hh, mi, ss);
          scell.setOfficeTimeValuettribute (str);
        otherwise
          ## Nothing
      endswitch

      scell = scell.getNextSibling ();

    endfor

    row = row.getNextSibling ();

  endfor

  if (f_errs) 
    printf ("%d formula errors encountered - please check input array\n", f_errs); 
  endif
  ods.changed = max (min (ods.changed, 2), changed);  ## Preserve 2 (new file), 1 (existing)
  rstatus = 1;
  
endfunction
