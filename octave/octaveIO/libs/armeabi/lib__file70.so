## Copyright (C) 2011,2012 Philip Nienhuis <prnienhuis@users.sf.net>
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

## oct2uno2xls - Internal function: write to spreadsheet file using UNO-Java bridge

## Author: Philip Nienhuis <prnienhuis@users.sf.net>
## Created: 2011-05-18
## 2011-09-18 Adapted sh_names type to LO 3.4.1
## 2011-09-23 Removed stray debug statements
## 2012-02-25 Fixed wrong var name in L.933
## 2012-02-25 Catch stray Java RuntimeException when deleting sheets
## 2012-02-26 Bug fix when adding sheets near L.994 (wrong if-else-end construct).
## 2012-02-27 Copyright strings updated
## 2012-05-21 "Double" cast added when writing numeric values
## 2012-05-21 "Double" cast moved into main func oct2xls
## 2012-10-12 Renamed & moved into ./private
## 2012-10-24 Style fixes
## 2012-12-21 Search for exact match when searching sheet names

function [ xls, rstatus ] = __UNO_oct2spsh__ (c_arr, xls, wsh, crange, spsh_opts)

  changed = 0;
  newsh = 0;
  ctype = [1, 2, 3, 4, 5];  ## Float, Logical, String, Formula, Empty

  ## Get handle to sheet, create a new one if needed
  sheets = xls.workbook.getSheets ();
  sh_names = sheets.getElementNames ();
  if (! iscell (sh_names))
    ## Java array (LibreOffice 3.4.+); convert to cellstr
    sh_names = char (sh_names);
  else
    sh_names = {sh_names};
  endif

  ## Clear default 2 last sheets in case of a new spreadsheet file
  if (xls.changed > 2)
    ii = numel (sh_names);
    while (ii > 1)
      shnm = sh_names{ii};
      try
        ## Catch harmless Java RuntimeException "out of range" in LibreOffice 3.5rc1
        sheets.removeByName (shnm);
      end_try_catch
      --ii;
    endwhile
    ## Give remaining sheet a name
    unotmp = java_new ("com.sun.star.uno.Type", "com.sun.star.sheet.XSpreadsheet");
    sh = sheets.getByName (sh_names{1}).getObject.queryInterface (unotmp);
    if (isnumeric (wsh)); wsh = sprintf ("Sheet%d", wsh); endif
    unotmp = java_new ("com.sun.star.uno.Type", "com.sun.star.container.XNamed");
    sh.queryInterface (unotmp).setName (wsh);
  else

    ## Check sheet pointer
    ## FIXME sheet capacity check needed
    if (isnumeric (wsh))
      if (wsh < 1)
        error ("Illegal sheet index: %d", wsh);
      elseif (wsh > numel (sh_names))
        ## New sheet to be added. First create sheet name but check if it already exists
        shname = sprintf ("Sheet%d", numel (sh_names) + 1);
        jj = strmatch (wsh, sh_names, "exact");
        if (~isempty (jj))
          ## New sheet name already in file, try to create a unique & reasonable one
          ii = 1; filler = ""; maxtry = 5;
          while (ii <= maxtry)
            shname = sprintf ("Sheet%s%d", [filler "_"], numel (sh_names + 1));
            if (isempty (strmatch (wsh, sh_names, "exact")))
              ii = 10;
            else
              ++ii;
            endif
          endwhile
          if (ii > maxtry + 1)
            error ("Could not add sheet with a unique name to file %s");
          endif
        endif
        wsh = shname;
        newsh = 1;
      else
        ## turn wsh index into the associated sheet name
        wsh = sh_names (wsh);
      endif
    else
      ## wsh is a sheet name. See if it exists already
      if (isempty (strmatch (wsh, sh_names, "exact")))
        ## Not found. New sheet to be added
        newsh = 1;
      endif
    endif
    if (newsh)
      ## Add a new sheet. Sheet index MUST be a Java Short object
      shptr = java_new ("java.lang.Short", sprintf ("%d", numel (sh_names) + 1));
      sh = sheets.insertNewByName (wsh, shptr);
    endif
    ## At this point we have a valid sheet name. Use it to get a sheet handle
    unotmp = java_new ("com.sun.star.uno.Type", "com.sun.star.sheet.XSpreadsheet");
    sh = sheets.getByName (wsh).getObject.queryInterface (unotmp);
  endif

  ## Check size of data array & range / capacity of worksheet & prepare vars
  [nr, nc] = size (c_arr);
  [topleft, nrows, ncols, trow, lcol] = ...
                      spsh_chkrange (crange, nr, nc, xls.xtype, xls.filename);
  --trow; --lcol;               ## Zero-based row ## & col ##
  if (nrows < nr || ncols < nc)
    warning ("Array truncated to fit in range");
    c_arr = c_arr(1:nrows, 1:ncols);
  endif
	
  ## Parse data array, setup typarr and throw out NaNs  to speed up writing;
  typearr = spsh_prstype (c_arr, nrows, ncols, ctype, spsh_opts, 0);
  if ~(spsh_opts.formulas_as_text)
    ## Find formulas (designated by a string starting with "=" and ending in ")")
    fptr = cellfun (@(x) ischar (x) && strncmp (x, "=", 1), c_arr);
    typearr(fptr) = ctype(4);   ## FORMULA
  endif

  ## Transfer data to sheet
  for ii=1:nrows
    for jj=1:ncols
      try
        XCell = sh.getCellByPosition (lcol+jj-1, trow+ii-1);
        switch typearr(ii, jj)
          case 1	      ## Float
            XCell.setValue (c_arr{ii, jj});
          case 2	      ## Logical. Convert to float as OOo has no Boolean type
            XCell.setValue (double (c_arr{ii, jj}));
          case 3	      ## String
            unotmp = java_new ("com.sun.star.uno.Type", "com.sun.star.text.XText");
            XCell.queryInterface (unotmp).setString (c_arr{ii, jj});
          case 4	      ## Formula
            if (spsh_opts.formulas_as_text)
              unotmp = java_new ("com.sun.star.uno.Type", "com.sun.star.text.XText");
              XCell.queryInterface (unotmp).setString (c_arr{ii, jj});
            else
              XCell.setFormula (c_arr{ii, jj});
            endif
          otherwise
            ## Empty cell
        endswitch
		    changed = 1;
      catch
        printf ("Error writing cell %s (typearr() = %d)\n",...
                calccelladdress(trow+ii, lcol+jj), typearr(ii, jj));
		  end_try_catch
    endfor
  endfor

  if (changed)	
    ## Preserve 2 (new file), 1 (existing)
    xls.changed = max (min (xls.changed, 2), changed);
    rstatus = 1;
  endif

endfunction
