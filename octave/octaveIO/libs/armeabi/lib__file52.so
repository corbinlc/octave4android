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

## __OTK_spsh_info__

## Author: Philip Nienhuis <prnienhuis@users.sf.net>
## Created: 2012-10-12
## Updates:
## 2012-10-12 Moved into ./private
## 2012-10-24 Style fixes

function [sh_names] = __OTK_spsh_info__ (ods)

  ## Get contents and table (= sheet) stuff from the workbook
  if (strcmp (ods.odfvsn, "0.8.7") || strfind (ods.odfvsn, "0.8.8"))
    xpath = ods.workbook.getXPath;
  else
    xpath = ods.app.getXPath;
  endif

  ## Create an instance of type NODESET for use in subsequent statement
  NODESET = java_get ("javax.xml.xpath.XPathConstants", "NODESET");
  ## Parse sheets ("tables") from ODS file
  sheets = xpath.evaluate ("//table:table", ods.workbook, NODESET);
  nr_of_sheets = sheets.getLength(); 
  sh_names = cell (nr_of_sheets, 2);

  ## Get sheet names (& optionally data row count estimate)
  for ii=1:nr_of_sheets
    ## Check in first part of the sheet nodeset
    sh_names (ii) = sheets.item(ii-1).getTableNameAttribute ();
    [ tr, lr, lc, rc ] = getusedrange (ods, ii);
    if (tr)
      sh_names(ii, 2) = sprintf ("%s:%s", calccelladdress (tr, lc),... 
                        calccelladdress (lr, rc));
    endif
  endfor

endfunction
