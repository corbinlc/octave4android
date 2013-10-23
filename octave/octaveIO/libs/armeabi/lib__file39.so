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

## __JOD_spsh_open

## Author: Philip Nienhuis <prnienhuis@users.sf.net>
## Created: 2012-10-12
## Updates:
## 2012-10-24 Style fixes

function [ ods, odssupport, lastintf ] = __JOD_spsh_open__ (ods, rw, filename, odssupport)

    file = java_new ("java.io.File", filename);
    jopendoc = "org.jopendocument.dom.spreadsheet.SpreadSheet";
    try
      if (rw > 2)
        ## Create an empty 2 x 2 default TableModel template
        tmodel= java_new ("javax.swing.table.DefaultTableModel", 2, 2);
        wb = java_invoke (jopendoc, "createEmpty", tmodel);
      else
        wb = java_invoke (jopendoc, "createFromFile", file);
      endif
      ods.workbook = wb;
      ods.filename = filename;
      ods.xtype = "JOD";
      ods.app = "file";
      ## Check jOpenDocument version. This can only work here when a
      ## workbook has been opened
      sh = ods.workbook.getSheet (0);
      cl = sh.getCellAt (0, 0);
      try
        # 1.2b3 has public getValueType ()
        cl.getValueType ();
        ods.odfvsn = 3;
      catch
        # 1.2b2 has not
        ods.odfvsn = 2;
        printf ("NOTE: jOpenDocument v. 1.2b2 has limited functionality. Try upgrading to 1.2\n");
      end_try_catch
      odssupport += 2;
      lastintf = "JOD";
    catch
      error ("Couldn't open file %s using JOD", filename);
    end_try_catch

endfunction
