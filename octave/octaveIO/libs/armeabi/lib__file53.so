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

## __OTK_SPSH_open

## Author: Philip Nienhuis <prnienhuis@users.sf.net>
## Created: 2012-10-12
## Updates:
## 2012-10-24 Style fixes

function [ ods, odssupport, lastintf ] = __OTK_spsh_open__ (ods, rw, filename, odssupport)

    ## Parts after user gfterry in
    ## http://www.oooforum.org/forum/viewtopic.phtml?t=69060
    
    ## Get odfdom version
    persistent odfvsn; odfvsn = [];
    if (isempty (odfvsn))
      try
        odfvsn = " ";
        ## New in 0.8.6
        odfvsn = ...
          java_invoke ("org.odftoolkit.odfdom.JarManifest", "getOdfdomVersion");
      catch
        odfvsn = ...
          java_invoke ("org.odftoolkit.odfdom.Version", "getApplicationVersion");
      end_try_catch
      ## For odfdom-incubator (= 0.8.8+), strip extra info
      odfvsn = regexp (odfvsn, '\d\.\d\.\d', "match"){1};
    endif

    odftk = "org.odftoolkit.odfdom.doc";
    try
      if (rw > 2)
        ## New spreadsheet
        wb = java_invoke ([odftk ".OdfSpreadsheetDocument"], "newSpreadsheetDocument");
      else
        ## Existing spreadsheet
        wb = java_invoke ([odftk ".OdfDocument"], "loadDocument", filename);
      endif
      ods.workbook = wb.getContentDom ();    # Reads the entire spreadsheet
      ods.xtype = "OTK";
      ods.app = wb;
      ods.filename = filename;
      ods.odfvsn = odfvsn;
      odssupport += 1;
      lastintf = "OTK";
    catch
      error ("Couldn't open file %s using OTK", filename);
    end_try_catch

endfunction
