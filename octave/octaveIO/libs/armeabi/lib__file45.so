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

## __JXL_xlsopen__ - Internal function for opening an xls file using Java / JExcelAPI

## Author: Philip <Philip@DESKPRN>
## Created: 2012-10-07
## Updates (possibly from xlsopen):
## 2010-11-05 Bug fix: JXL fallback from POI for BIFF5 is only useful for reading
## 2012-10-24 Style fixes

function [ xls, xlssupport, lastintf ] = __JXL_spsh_open__ (xls, xwrite, filename, xlssupport, chk1)

    if (~chk1)
      error ("JXL can only read reliably from .xls files")
    endif
    try
      xlsin = java_new ("java.io.File", filename);
      if (xwrite > 2)
        ## Get handle to new xls-file
        wb = java_invoke ("jxl.Workbook", "createWorkbook", xlsin);
      else
        ## Open existing file
        wb = java_invoke ("jxl.Workbook", "getWorkbook", xlsin);
      endif
      xls.xtype = "JXL";
      xls.app = xlsin;
      xls.workbook = wb;
      xls.filename = filename;
      xlssupport += 4;
      lastintf = "JXL";
    catch
      clear xlsin;
      if (xlsinterfaces.POI)
        ## Fall back to UNO only when that is stable (= closing soffice)
        printf ("... No luck with JXL either, unsupported file format.\n", filename);
      endif
    end_try_catch

endfunction
