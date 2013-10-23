## Copyright (C) 2010,2011,2012 Philip Nienhuis <prnienhuis _at- users.sf.net>
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

## __OTK_oct2spsh__ - read ODS spreadsheet data using Java & odftoolkit v 0.8.6+.
## You need proper java-for-octave & odfdom.jar 0.8.6+ & xercesImpl.jar 2.9.1
## in your javaclasspath.
##
## Author: Philip Nenhuis <pr.nienhuis at users.sf.net>
## Created: 2010-03-16, after oct2jotk2ods()
## Updates:
## 2010-03-17 Rebuild for odfdom-0.8
## 2010-03-19 Showstopper bug in odfdom-0.8 - getCellByPosition(<address>)
##            crashes on rows > #10 !!! Rest seems to work OK, however
## 2010-03-22 First somewhat usable version for odfdom 0.8
## 2010-03-29 Gave up. Writing a new empty sheet works, appending
##            data to an existing one can crash virtually anywhere.
##            The wait is for odfdom-0.8.+ ....
## 2010-06-05 odfdom 0.8.5 is there, next try....
## 2010-06-## odfdom 0.8.5 dropped, too buggy
## 2010-08-22 odfdom 0.8.6 is there... seems to work with just one bug, easily worked around
## 2010-10-27 Improved file change tracking tru ods.changed
## 2010-11-12 Improved file change tracking tru ods.changed
## 2010-12-08 Bugfixes (obj -> arg L.715; removed stray arg in call to spsh_prstype L.719)
## 2011-03-23 First try of odfdom 0.8.7
## 2012-06-08 Support for odfdom-incubator-0.8.8
## 2012-10-12 Renamed & moved into ./private

function [ ods, rstatus ] = __OTK_oct2spsh__ (c_arr, ods, wsh, crange, spsh_opts)

  persistent ctype;
  if (isempty (ctype))
    ## Number, Boolean, String, Formula, Empty; Date, Time - last two aren't used
    ctype = [1, 2, 3, 4, 5, 6, 7];
  endif

  rstatus = 0; changed = 0; newsh = 0;

  ## Get contents and table stuff from the workbook
  odfcont = ods.workbook;    ## Use a local copy just to be sure. octave 
                ## makes physical copies only when needed (?)
  odfroot = odfcont.getRootElement ();
  offsprdsh = ods.app.getContentRoot();
  if (strcmp (ods.odfvsn, "0.8.7") || strfind (ods.odfvsn, "0.8.8"))
    spsh = odfcont.getDocument ();
  else
    spsh = odfcont.getOdfDocument ();
  endif

  ## Get some basic spreadsheet data from the pointer using ODFtoolkit
  autostyles = odfcont.getOrCreateAutomaticStyles();
  officestyles = ods.app.getOrCreateDocumentStyles();

  ## Parse sheets ("tables") from ODS file
  sheets = ods.app.getTableList();
  nr_of_sheets = sheets.size ();
  ## Check user input & find sheet pointer
  if (~isnumeric (wsh))
    try
      sh = ods.app.getTableByName (wsh);
      ## We do need a sheet index number...
      ii = 0;
      while (ischar (wsh) && ii < nr_of_sheets) 
        sh_nm = sh.getTableName ();
        if (strcmp (sh_nm, wsh)) wsh = ii + 1; else ++ii; endif
      endwhile
    catch
      newsh = 1;
    end_try_catch
    if isempty (sh) newsh = 1; endif
  elseif (wsh < 1)
    ## Negative sheet number:
    error (sprintf ("Illegal worksheet nr. %d\n", wsh));
  elseif (wsh > nr_of_sheets)
    newsh = 1;
  else
    sh = sheets.get (wsh - 1);
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
  typearr = spsh_prstype (c_arr, nrows, ncols, ctype, spsh_opts);
  if ~(spsh_opts.formulas_as_text)
    ## Find formulas (designated by a string starting with "=" and ending in ")")
    fptr = cellfun (@(x) ischar (x) && strncmp (x, "=", 1) ...
                                    && strncmp (x(end:end), ")", 1), c_arr);
    typearr(fptr) = ctype(4);          ## FORMULA
  endif

  ## Prepare spreadsheet for writing (size, etc.). If needed create new sheet
  if (newsh)
    if (ods.changed > 2)
      ## New spreadsheet, use default first sheet
      sh = sheets.get (0);
    else
      ## Create a new sheet using DOM API. This part works OK.
      sh = sheets.get (nr_of_sheets - 1).newTable (spsh, nrows, ncols);
    endif
    changed = 1;
    if (isnumeric (wsh))
      ## Give sheet a name
      str = sprintf ("Sheet%d", wsh);
      sh.setTableName (str);
      wsh = str;
    else
      ## Assign name to sheet and change wsh into numeric pointer
      sh.setTableName (wsh);
    endif
    ## printf ("Sheet %s added to spreadsheet.\n", wsh);
    
  else
    ## Add "physical" rows & columns. Spreadsheet max. capacity checks have been done above
    ## Add spreadsheet data columns if needed. Compute nr of extra columns & rows.
    curr_ncols = sh.getColumnCount ();
    ii = max (0, lcol + ncols - curr_ncols);
    if (ii == 1)
      nwcols = sh.appendColumn ();
    else
      nwcols = sh.appendColumns (ii);
    endif

    ## Add spreadsheet rows if needed
    curr_nrows = sh.getRowCount ();
    ii = max (0, trow + nrows - curr_nrows);
    if (ii == 1)
      nwrows = sh.appendRow ();
    else
      nwrows = sh.appendRows (ii);
    endif
  endif
 
  ## Transfer array data to sheet
  for ii=1:nrows
    for jj=1:ncols
      ocell = sh.getCellByPosition (jj+lcol-1, ii+trow-1);
      if ~(isempty (ocell )) ## Might be spanned (merged), hidden, ....
        ## Number, String, Boolean, Date, Time
        try
          switch typearr (ii, jj)
            case {1, 6, 7}  ## Numeric, Date, Time
              ocell.setDoubleValue (c_arr{ii, jj}); 
            case 2  ## Logical / Boolean
              ## ocell.setBooleanValue (c_arr{ii, jj}); ## Doesn't work, bug in odfdom 0.8.6
              ## Bug workaround: 1. Remove all cell contents
              ocell.removeContent ();
              ## 2. Switch to TableTableElement API
              tocell = ocell.getOdfElement ();
              tocell.setAttributeNS ("office", "office:value-type", "boolean");
              ## 3. Add boolean-value attribute. 
              ## This is only accepted in TTE API with a NS tag (actual bug, IMO)
              if (c_arr {ii,jj})
                tocell.setAttributeNS ("office", "office:boolean-value", "true");
              else
                tocell.setAttributeNS ("office", "office:boolean-value", "false");
              endif
            case 3  ## String
              ocell.setStringValue (c_arr{ii, jj});
            case 4  ## Formula
              ocell.setFormula (c_arr{ii, jj});
            otherwise     ## 5, empty and catch-all
              ## The above is all octave has to offer & java can accept...
          endswitch
          changed = 1;
        catch
          printf ("\n");
        end_try_catch
      endif
    endfor
  endfor

  if (changed)  
    ods.changed = max (min (ods.changed, 2), changed);  ## Preserve 2 (new file), 1 (existing)
    rstatus = 1;
  endif

endfunction
