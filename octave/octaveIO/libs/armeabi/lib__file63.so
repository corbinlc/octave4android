## Copyright (C) 2009,2010,2011,2012 Philip Nienhuis <prnienhuis at users.sf.net>
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
## @deftypefn {Function File} [@var{obj}, @var{rstatus}, @var{xls} ] = __POI_spsh2oct__ (@var{xls})
## @deftypefnx {Function File} [@var{obj}, @var{rstatus}, @var{xls} ] = __POI_spsh2oct__ (@var{xls}, @var{wsh})
## @deftypefnx {Function File} [@var{obj}, @var{rstatus}, @var{xls} ] = __POI_spsh2oct__ (@var{xls}, @var{wsh}, @var{range})
## Get cell contents in @var{range} in worksheet @var{wsh} in an Excel
## file pointed to in struct @var{xls} into the cell array @var{obj}.
## @var{range} can be a range or just the top left cell of the range.
##
## __POI_spsh2oct__ should not be invoked directly but rather through xls2oct.
##
## Examples:
##
## @example
##   [Arr, status, xls] = __POI_spsh2oct__ (xls, 'Second_sheet', 'B3:AY41');
##   B = __POI_spsh2oct__ (xls, 'Second_sheet', 'B3');
## @end example
##
## @seealso {xls2oct, oct2xls, xlsopen, xlsclose, xlsread, xlswrite, oct2jpoi2xls}
##
## @end deftypefn

## Author: Philip Nienhuis
## Created: 2009-11-23
## Updates: 
## 2010-01-11 Fall back to cached values when formula evaluator fails
## 2010-03-14 Fixed max column nr for OOXML for empty given range
## 2010-07-28 Added option to read formulas as text strings rather than evaluated value
## 2010-08-01 Some bug fixes for formula reading (cvalue rather than scell)
## 2010-10-10 Code cleanup: -getusedrange called; - fixed typo in formula evaluation msg;
##     ''     moved cropping output array to calling function.
## 2010-11-12 Moved ptr struct check into main func
## 2010-11-13 Catch empty sheets when no range was specified
## 2010-11-14 Fixed sheet # index (was offset by -1) in call to getusedrange() in case
##            of text sheet name arg
## 2012-01-26 Fixed "seealso" help string
## 2012-10-12 Renamed & moved into ./private
## 2012-10-24 Style fixes

function [ rawarr, xls, rstatus ] = __POI_spsh2oct__ (xls, wsh, cellrange, spsh_opts)

  persistent ctype;
  if (isempty (ctype))
    ## Get enumerated cell types. Beware as they start at 0 not 1
    ctype(1) = java_get ("org.apache.poi.ss.usermodel.Cell", "CELL_TYPE_NUMERIC");
    ctype(2) = java_get ("org.apache.poi.ss.usermodel.Cell", "CELL_TYPE_STRING");
    ctype(3) = java_get ("org.apache.poi.ss.usermodel.Cell", "CELL_TYPE_FORMULA");
    ctype(4) = java_get ("org.apache.poi.ss.usermodel.Cell", "CELL_TYPE_BLANK");
    ctype(5) = java_get ("org.apache.poi.ss.usermodel.Cell", "CELL_TYPE_BOOLEAN");
    ctype(6) = java_get ("org.apache.poi.ss.usermodel.Cell", "CELL_TYPE_ERROR");
  endif
  
  rstatus = 0; jerror = 0;
  wb = xls.workbook;

  ## Check if requested worksheet exists in the file & if so, get pointer
  nr_of_sheets = wb.getNumberOfSheets ();
  if (isnumeric (wsh))
    if (wsh > nr_of_sheets)
      error (sprintf ("Worksheet ## %d bigger than nr. of sheets (%d) in file %s",...
                      wsh, nr_of_sheets, xls.filename)); 
    endif
    sh = wb.getSheetAt (wsh - 1);      ## POI sheet count 0-based
##  printf ("(Reading from worksheet %s)\n", sh.getSheetName ());
  else
    sh = wb.getSheet (wsh);
    if (isempty (sh))
      error (sprintf ("Worksheet %s not found in file %s", wsh, xls.filename)); 
    endif
  end

  ## Check ranges
  firstrow = sh.getFirstRowNum ();    ## 0-based
  lastrow = sh.getLastRowNum ();      ## 0-based
  if (isempty (cellrange))
    if (ischar (wsh))
      ## get numeric sheet index
      ii = wb.getSheetIndex (sh) + 1;
    else
      ii = wsh;
    endif
    [ firstrow, lastrow, lcol, rcol ] = getusedrange (xls, ii);
    if (firstrow == 0 && lastrow == 0)
      ## Empty sheet
      rawarr = {};
      printf ("Worksheet '%s' contains no data\n", sh.getSheetName ());
      rstatus = 1;
      return;
    else
      nrows = lastrow - firstrow + 1;
      ncols = rcol - lcol + 1;
    endif
  else
    ## Translate range to HSSF POI row & column numbers
    [topleft, nrows, ncols, firstrow, lcol] = parse_sp_range (cellrange);
    lastrow = firstrow + nrows - 1;
    rcol = lcol + ncols - 1;
  endif

  ## Create formula evaluator (needed to infer proper cell type into rawarr)
  frm_eval = wb.getCreationHelper().createFormulaEvaluator ();
  
  ## Read contents into rawarr
  rawarr = cell (nrows, ncols);               ## create placeholder
  for ii = firstrow:lastrow
    irow = sh.getRow (ii-1);
    if ~isempty (irow)
      scol = (irow.getFirstCellNum).intValue ();
      ecol = (irow.getLastCellNum).intValue () - 1;
      for jj = lcol:rcol
        scell = irow.getCell (jj-1);
        if ~isempty (scell)
          ## Explore cell contents
          type_of_cell = scell.getCellType ();
          if (type_of_cell == ctype(3))       ## Formula
            if ~(spsh_opts.formulas_as_text)
              try    
                ## Because not al Excel formulas have been implemented in POI
                cvalue = frm_eval.evaluate (scell);
                type_of_cell = cvalue.getCellType();
                ## Separate switch because form.eval. yields different type
                switch type_of_cell
                  case ctype (1)              ## Numeric
                    rawarr {ii+1-firstrow, jj+1-lcol} = cvalue.getNumberValue ();
                  case ctype(2)               ## String
                    rawarr {ii+1-firstrow, jj+1-lcol} = ...
                                          char (cvalue.getStringValue ());
                  case ctype (5)              ## Boolean
                    rawarr {ii+1-firstrow, jj+1-lcol} = cvalue.BooleanValue ();
                  otherwise
                    ## Nothing to do here
                endswitch
                ## Set cell type to blank to skip switch below
                type_of_cell = ctype(4);
              catch
                ## In case of formula errors we take the cached results
                type_of_cell = scell.getCachedFormulaResultType ();
                ## We only need one warning even for multiple errors 
                ++jerror;     
              end_try_catch
            endif
          endif
          ## Preparations done, get data values into data array
          switch type_of_cell
            case ctype(1)                     ## 0 Numeric
              rawarr {ii+1-firstrow, jj+1-lcol} = scell.getNumericCellValue ();
            case ctype(2)                     ## 1 String
              rawarr {ii+1-firstrow, jj+1-lcol} = ...
                                        char (scell.getRichStringCellValue ());
            case ctype(3)
              if (spsh_opts.formulas_as_text)
                tmp = char (scell.getCellFormula ());
                rawarr {ii+1-firstrow, jj+1-lcol} = ["=" tmp];
              endif
            case ctype(4)                     ## 3 Blank
              ## Blank; ignore until further notice
            case ctype(5)                     ## 4 Boolean
              rawarr {ii+1-firstrow, jj+1-lcol} = scell.getBooleanCellValue ();
            otherwise                         ## 5 Error
              ## Ignore
          endswitch
        endif
      endfor
    endif
  endfor

  if (jerror > 0)
    warning (sprintf ("%d cached values instead of formula evaluations read.\n",...
                      jerror));
  endif
  
  rstatus = 1;
  xls.limits = [lcol, rcol; firstrow, lastrow];
  
endfunction
