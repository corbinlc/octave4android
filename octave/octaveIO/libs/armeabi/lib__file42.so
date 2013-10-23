## Copyright (C) 2009,2010,2011,2012 Philip Nienhuis <prnienhuis at users.sf.net>
## 
## This program is free software; you can redistribute it and/or modify
## it under the terms of the GNU General Public License as published by
## the Free Software Foundation; either version 2 of the License, or
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

## -*- texinfo -*-
## @deftypefn {Function File} [@var{obj}, @var{rstatus}, @var{xls} ] = __JXL_spsh2oct__ (@var{xls})
## @deftypefnx {Function File} [@var{obj}, @var{rstatus}, @var{xls} ] = __JXL_spsh2oct__ (@var{xls}, @var{wsh})
## @deftypefnx {Function File} [@var{obj}, @var{rstatus}, @var{xls} ] = __JXL_spsh2oct__ (@var{xls}, @var{wsh}, @var{range})
## Get cell contents in @var{range} in worksheet @var{wsh} in an Excel
## file pointed to in struct @var{xls} into the cell array @var{obj}.
## @var{range} can be a range or just the top left cell of the range.
##
## __JXL_spsh2oct__ should not be invoked directly but rather through xls2oct.
##
## Examples:
##
## @example
##   [Arr, status, xls] = __JXL_spsh2oct__ (xls, "Second_sheet", "B3:AY41");
##   B = __JXL_spsh2oct__ (xls, "Second_sheet");
## @end example
##
## @seealso {xls2oct, oct2xls, xlsopen, xlsclose, xlsread, xlswrite, oct2jxla2xls}
##
## @end deftypefn

## Author: Philip Nienhuis
## Created: 2009-12-04
## Updates:
## 2009-12-11 ??? some bug fix
## 2010-07-28 Added option to read formulas as text strings rather than evaluated value
##            Added check for proper xls structure
## 2010-07-29 Added check for too latge requested data rectangle
## 2010-10-10 Code cleanup: -getusedrange(); moved cropping result array to
##     ''     calling function
## 2010-11-12 Moved ptr struct check into main func
## 2010-11-13 Catch empty sheets when no range was specified
## 2011-04-11 (Ron Goldman <ron@ocean.org.il>) Fixed missing months var, wrong arg
##     ''     order in strsplit, wrong isTime condition
## 2012-01-26 Fixed "seealso" help string
## 2012-10-12 Renamed & moved into ./private
## 2012-10-24 Style fixes

function [ rawarr, xls, rstatus ] = __JXL_spsh2oct__ (xls, wsh, cellrange, spsh_opts)

  persistent ctype; persistent months;
  if (isempty (ctype))
    ctype = cell (11, 1);
    ## Get enumerated cell types. Beware as they start at 0 not 1
    ctype( 1) = (java_get ("jxl.CellType", "BOOLEAN")).toString ();
    ctype( 2) = (java_get ("jxl.CellType", "BOOLEAN_FORMULA")).toString ();
    ctype( 3) = (java_get ("jxl.CellType", "DATE")).toString ();
    ctype( 4) = (java_get ("jxl.CellType", "DATE_FORMULA")).toString ();
    ctype( 5) = (java_get ("jxl.CellType", "EMPTY")).toString ();
    ctype( 6) = (java_get ("jxl.CellType", "ERROR")).toString ();
    ctype( 7) = (java_get ("jxl.CellType", "FORMULA_ERROR")).toString ();
    ctype( 8) = (java_get ("jxl.CellType", "NUMBER")).toString ();
    ctype( 9) = (java_get ("jxl.CellType", "LABEL")).toString ();
    ctype(10) = (java_get ("jxl.CellType", "NUMBER_FORMULA")).toString ();
    ctype(11) = (java_get ("jxl.CellType", "STRING_FORMULA")).toString ();
    months = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
  endif
  
  rstatus = 0; 
  wb = xls.workbook;
  
  ## Check if requested worksheet exists in the file & if so, get pointer
  nr_of_sheets = wb.getNumberOfSheets ();
  shnames = char (wb.getSheetNames ());
  if (isnumeric (wsh))
    if (wsh > nr_of_sheets)
      error (sprintf ("Worksheet ## %d bigger than nr. of sheets (%d) in file %s",...
                      wsh, nr_of_sheets, xls.filename)); 
    endif
    sh = wb.getSheet (wsh - 1);      ## JXL sheet count 0-based
    ## printf ("(Reading from worksheet %s)\n", shnames {wsh});
  else
    sh = wb.getSheet (wsh);
    if (isempty (sh))
      error (sprintf ("Worksheet %s not found in file %s", wsh, xls.filename)); 
    endif
  end

  if (isempty (cellrange))
    ## Get numeric sheet pointer (1-based)
    ii = 1;
    while (ii <= nr_of_sheets)
      if (strcmp (wsh, shnames{ii}) == 1)
        wsh = ii;
        ii = nr_of_sheets + 1;
      else
        ++ii;
      endif
    endwhile
    ## Get data rectangle row & column numbers (1-based)
    [firstrow, lastrow, lcol, rcol] = getusedrange (xls, wsh);
    if (firstrow == 0 && lastrow == 0)
      ## Empty sheet
      rawarr = {};
      printf ("Worksheet '%s' contains no data\n", shnames {wsh});
      rstatus = 1;
      return;
    else
      nrows = lastrow - firstrow + 1;
      ncols = rcol - lcol + 1;
    endif
  else
    ## Translate range to row & column numbers (1-based)
    [dummy, nrows, ncols, firstrow, lcol] = parse_sp_range (cellrange);
    ## Check for too large requested range against actually present range
    lastrow = min (firstrow + nrows - 1, sh.getRows ());
    nrows = min (nrows, sh.getRows () - firstrow + 1);
    ncols = min (ncols, sh.getColumns () - lcol + 1);
    rcol = lcol + ncols - 1;
  endif

  ## Read contents into rawarr
  rawarr = cell (nrows, ncols);      ## create placeholder
  for jj = lcol : rcol
    for ii = firstrow:lastrow
      scell = sh.getCell (jj-1, ii-1);
      switch char (scell.getType ())
        case ctype {1}   ## Boolean
          rawarr {ii+1-firstrow, jj+1-lcol} = scell.getValue ();
        case ctype {2}   ## Boolean formula
          if (spsh_opts.formulas_as_text)
            tmp = scell.getFormula ();
            rawarr {ii+1-firstrow, jj+1-lcol} = ["=" tmp];
          else
            rawarr {ii+1-firstrow, jj+1-lcol} = scell.getValue ();
          endif
        case ctype {3}   ## Date
          try
            % Older JXL.JAR, returns float
            rawarr {ii+1-firstrow, jj+1-lcol} = scell.getValue ();
          catch
            % Newer JXL.JAR, returns date string w. epoch = 1-1-1900 :-(
            tmp = strsplit (char (scell.getDate ()), " ");
            yy = str2num (tmp{6});
            mo = find (ismember (months, upper (tmp{2})) == 1);
            dd = str2num (tmp{3});
            hh = str2num (tmp{4}(1:2));
            mi = str2num (tmp{4}(4:5));
            ss = str2num (tmp{4}(7:8));
            if (scell.isTime ())
              yy = mo = dd = 0;
            endif
            rawarr {ii+1-firstrow, jj+1-lcol} = datenum (yy, mo, dd, hh, mi, ss);
          end_try_catch
        case ctype {4}   ## Date formula
          if (spsh_opts.formulas_as_text)
            tmp = scell.getFormula ();
            rawarr {ii+1-firstrow, jj+1-lcol} = ["=" tmp];
          else
            unwind_protect
              % Older JXL.JAR, returns float
              tmp = scell.getValue ();
              % if we get here, we got a float (old JXL).
              % Check if it is time
              if (~scell.isTime ())
                % Reset rawarr <> so it can be processed below as date string
                rawarr {ii+1-firstrow, jj+1-lcol} = [];
              else
                rawarr {ii+1-firstrow, jj+1-lcol} = tmp;
              end
            unwind_protect_cleanup
              if (isempty (rawarr {ii+1-firstrow, jj+1-lcol}))
                % Newer JXL.JAR, returns date string w. epoch = 1-1-1900 :-(
                tmp = strsplit (char (scell.getDate ()), " ");
                yy = str2num (tmp{6});
                mo = find (ismember (months, upper (tmp{2})) == 1);
                dd = str2num (tmp{3});
                hh = str2num (tmp{4}(1:2));
                mi = str2num (tmp{4}(4:5));
                ss = str2num (tmp{4}(7:8));
                if (scell.isTime ())
                  yy = 0; mo = 0; dd = 0;
                end
                rawarr {ii+1-firstrow, jj+1-lcol} = datenum (yy, mo, dd, hh, mi, ss);
              endif
            end_unwind_protect
          endif
        case { ctype {5}, ctype {6}, ctype {7} }
          ## Empty, Error or Formula error. Nothing to do here
        case ctype {8}   ## Number
          rawarr {ii+1-firstrow, jj+1-lcol} = scell.getValue ();
        case ctype {9}   ## String
          rawarr {ii+1-firstrow, jj+1-lcol} = scell.getString ();
        case ctype {10}  ## Numerical formula
          if (spsh_opts.formulas_as_text)
            tmp = scell.getFormula ();
            rawarr {ii+1-firstrow, jj+1-lcol} = ["=" tmp];
          else
            rawarr {ii+1-firstrow, jj+1-lcol} = scell.getValue ();
          endif
        case ctype {11}  ## String formula
          if (spsh_opts.formulas_as_text)
            tmp = scell.getFormula ();
            rawarr {ii+1-firstrow, jj+1-lcol} = ["=" tmp];
          else
            rawarr {ii+1-firstrow, jj+1-lcol} = scell.getString ();
          endif
        otherwise
          ## Do nothing
      endswitch
    endfor
  endfor

  rstatus = 1;
  xls.limits = [lcol, rcol; firstrow, lastrow];
  
endfunction
