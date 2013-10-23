## Copyright (C) 2012 Philip
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

## __POI_xlsopen__ - Internal function for opening an xls(x) file using Java/Apache POI

## Author: Philip <Philip@DESKPRN>
## Created: 2012-10-07
##
## Updates (possibly earlier in xlsopen):
## 2010-01-03 Added OOXML support
## 2010-01-16 Removed echoeing debug info in POI stanza
## 2010-09-27 Improved POI help message for unrecognized .xls format to hint for BIFF5/JXL
## 2010-11-08 Tested with POI 3.7 (OK)
## 2012-06-07 Fixed mixed-up lastintf assignments for POI and JXL
## 2012-10-24 Style fixes; added UNO to fall-back for BIFF5 formats

function [ xls, xlssupport, lastintf ] = __POI_spsh_open__ (xls, xwrite, filename, xlssupport, chk1, chk2, xlsinterfaces)

    ## Get handle to workbook
    try
      if (xwrite > 2)
        if (chk1)
          wb = java_new ("org.apache.poi.hssf.usermodel.HSSFWorkbook");
        elseif (chk2)
          wb = java_new ("org.apache.poi.xssf.usermodel.XSSFWorkbook");
        endif
        xls.app = "new_POI";
      else
        xlsin = java_new ("java.io.FileInputStream", filename);
        wb = java_invoke ("org.apache.poi.ss.usermodel.WorkbookFactory",...
                          "create", xlsin);
        xls.app = xlsin;
      endif
      xls.xtype = "POI";
      xls.workbook = wb;
      xls.filename = filename;
      xlssupport += 2;
      lastintf = "POI";
    catch
      clear xlsin;
      if (chk1 && (xlsinterfaces.JXL || xlsinterfaces.UNO))
        printf ...
        (["Couldn't open file %s using POI;\n" ...
          "trying Excel'95 format with JXL or UNO...\n"], filename);
      endif
    end_try_catch

endfunction
