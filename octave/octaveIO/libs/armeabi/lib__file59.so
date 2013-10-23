## Copyright (C) 2012 Philip Nienhuis <prnienhuis at users.sf.net>
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

## __OXS_xlsopen__ - inernal function for opening an xls file using Java / OpenXLS

## Author: Philip Nienhuis <prnienhuis at users.sf.net>
## Created: 2012-10-07
## Updates:
## 2012-10-24 Style fixes

function [ xls, xlssupport, lastintf ] = __OXS_spsh_open__ (xls, xwrite, filename, xlssupport, chk1)

    if (~chk1)
      error ("OXS can only read from .xls files")
    endif
    try
      wb = javaObject ("com.extentech.ExtenXLS.WorkBookHandle", filename);
      xls.xtype = "OXS";
      xls.app = "void - OpenXLS";
      xls.workbook = wb;
      xls.filename = filename;
      xlssupport += 8;
      lastintf = "OXS";
    catch
      printf ("Unsupported file format for OpenXLS - %s\n");
    end_try_catch

endfunction
