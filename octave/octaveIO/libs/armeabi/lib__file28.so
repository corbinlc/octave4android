## Copyright (C) 2012 Philip Nienhuis <prnienhuis@users.sf.net>
## 
## This program is free software; you can redistribute it and/or modify
## it under the terms of the GNU General Public License as published by
## the Free Software Foundation; either version 3 of the License, or
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

## __COM_spsh_close__ - internal function: close a spreadsheet file using COM

## Author: Philip Nienhuis <prnienhuis@users.sf.net>
## Created: 2012-10-12

function [ xls ] = __COM_spsh_close__ (xls)

    ## If file has been changed, write it out to disk.
    ##
    ## Note: COM / VB supports other Excel file formats as FileFormatNum:
    ## 4 = .wks - Lotus 1-2-3 / Microsoft Works
    ## 6 = .csv
    ## -4158 = .txt 
    ## 36 = .prn
    ## 50 = .xlsb - xlExcel12 (Excel Binary Workbook in 2007 with or without macro's)
    ## 51 = .xlsx - xlOpenXMLWorkbook (without macro's in 2007)
    ## 52 = .xlsm - xlOpenXMLWorkbookMacroEnabled (with or without macro's in 2007)
    ## 56 = .xls  - xlExcel8 (97-2003 format in Excel 2007)
    ## (see Excel Help, VB reference, Enumerations, xlFileType)
    
    ## xls.changed = 0: no changes: just close;
    ##               1: existing file with changes: save, close.
    ##               2: new file with data added: save, close
    ##               3: new file, no added added (empty): close & delete on disk

    xls.app.Application.DisplayAlerts = 0;
    try
      if (xls.changed > 0 && xls.changed < 3)
        if (isfield (xls, "nfilename"))
          fname = xls.nfilename;
        else
          fname = xls.filename;
        endif
        if (xls.changed == 2)
          ## Probably a newly created, or renamed, Excel file
##         printf ("Saving file %s ...\n", fname);
          xls.workbook.SaveAs (canonicalize_file_name (fname));
        elseif (xls.changed == 1)
          ## Just updated existing Excel file
          xls.workbook.Save ();
        endif
        xls.changed = 0;
        xls.workbook.Close (canonicalize_file_name (fname));
      endif
      xls.app.Quit ();
      delete (xls.workbook);  ## This statement actually closes the workbook
      delete (xls.app);     ## This statement actually closes down Excel
    catch
      xls.app.Application.DisplayAlerts = 1;
    end_try_catch

endfunction
