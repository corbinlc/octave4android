## Copyright (C) 2011 Philip Nienhuis <prnienhuis@users.sf.net>
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

## -*- texinfo -*-
## @deftypefn {Function File} [ @var{xlso}, @var{rstatus} ] = __OXS_oct2spsh__ ( @var{arr}, @var{xlsi})
## @deftypefnx {Function File} [ @var{xlso}, @var{rstatus} ] = __OXS_oct2spsh__ (@var{arr}, @var{xlsi}, @var{wsh})
## @deftypefnx {Function File} [ @var{xlso}, @var{rstatus} ] = __OXS_oct2spsh__ (@var{arr}, @var{xlsi}, @var{wsh}, @var{range})
## @deftypefnx {Function File} [ @var{xlso}, @var{rstatus} ] = __OXS_oct2spsh__ (@var{arr}, @var{xlsi}, @var{wsh}, @var{range}, @var{options})
##
## Add data in 1D/2D CELL array @var{arr} into spreadsheet cell range @var{range}
## in worksheet @var{wsh} in an Excel spreadsheet file pointed to in structure
## @var{range}.
## Return argument @var{xlso} equals supplied argument @var{xlsi} and is
## updated by __OXS_oct2spsh__.
##
## __OXS_oct2spsh__ should not be invoked directly but rather through oct2xls.
##
## @end deftypefn

## Author: Philip Nienhuis <prnienhuis@users.sf.net>
## Created: 2011-03-29
## Updates:
## 2012-10-12 Renamed & moved into ./private
## 2012-10-24 Style fixes

function [ xls, rstatus ] = __OXS_oct2spsh__ (obj, xls, wsh, crange, spsh_opts)

  ## Preliminary sanity checks
  if (~strmatch (tolower (xls.filename(end-4:end-1)), ".xls"))  
    ## No OOXML in OXS
    error ("OXS interface can only write to Excel .xls files")
  endif
  
  changed = 0;

  persistent ctype;
  if (isempty (ctype))
    ## Number, Boolean, String, Formula, Empty
    ctype = [1, 2, 3, 4, 5];
  endif
  ## scratch vars
  rstatus = 0; f_errs = 0;
  
  ## Prepare workbook pointer if needed
  wb = xls.workbook;

  ## Check if requested worksheet exists in the file & if so, get pointer
  nr_of_sheets = wb.getNumWorkSheets ();    ## 1 based !!
  if (isnumeric (wsh))
    if (wsh > nr_of_sheets)
      ## Watch out as a sheet called Sheet%d can exist with a lower index...
      strng = sprintf ("Sheet%d", wsh);
      ii = 1;
      try
        ## While loop should be inside try-catch
        while (ii < 5)
          sh = wb.getWorkSheet (strng)
          strng = ['_' strng];
          ++ii;
        endwhile
      catch
        ## No worksheet named <strng> found => we can proceed
      end_try_catch
      if (ii >= 5)
        error (sprintf( " > 5 sheets named [_]Sheet%d already present!", wsh));
      endif
      sh = wb.createWorkSheet (strng); ++nr_of_sheets;
      xls.changed = min (xls.changed, 2);    ## Keep a 2 in case of new file
    else
      sh = wb.getWorkSheet (wsh - 1);        ## OXS sheet count 0-based
    endif
    printf ("(Writing to worksheet %s)\n", sh.getSheetName ());
  else
    try
      sh = wb.getWorkSheet (wsh);
    catch
      ## Sheet not found, just create it
      sh = wb.createWorkSheet (wsh); ++nr_of_sheets;
      xls.changed = min (xls.changed, 2);    ## Keep a 2 for new file
    end_try_catch
  endif

  ## Parse date ranges  
  [nr, nc] = size (obj);
  [topleft, nrows, ncols, trow, lcol] = ...
                      spsh_chkrange (crange, nr, nc, xls.xtype, xls.filename);
  if (nrows < nr || ncols < nc)
    warning ("Array truncated to fit in range");
    obj = obj(1:nrows, 1:ncols);
  endif

  ## Prepare type array
  typearr = spsh_prstype (obj, nrows, ncols, ctype, spsh_opts);
  if ~(spsh_opts.formulas_as_text)
    ## Remove leading '=' from formula strings  //FIXME needs updating
    fptr = ~(4 * (ones (size (typearr))) .- typearr);
    obj(fptr) = cellfun (@(x) x(2:end), obj(fptr), "Uniformoutput", false); 
  endif
  clear fptr

  for ii=1:ncols
    for jj=1:nrows
      try
        ## Set value
        sh.getCell(jj+trow-2, ii+lcol-2).setVal (obj{jj, ii});  ## Addr.cnt = 0-based
        changed = 1;
      catch
        ## Cell not existent. Add cell
        if ~(typearr(jj, ii) == 5)
          sh.add (obj{jj, ii}, jj+trow-2, ii+lcol-2);
          changed = 1;
        endif
      end_try_catch
    endfor
  endfor

  if (changed), xls.changed = max (xls.changed, 1); endif   ## Preserve 2 for new files
  rstatus = 1;

endfunction
