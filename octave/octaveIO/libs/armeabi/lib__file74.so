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

## __UNO_xlsopen__ - Internal function for opening a spreadsheet file using Java / OOo/LO UNO

## Author: Philip Nienhuis <prnienhuis@users.sf.net>
## Created: 2012-10-07
## Updates (possibly from xlsopen):
## 2011-05-18 Experimental UNO support added, incl. creating new spreadsheets
## 2012-09-02 (in UNO section) web adresses need only two consecutive slashes
## 2012-09-03 (in UNO section) replace canonicalize_file_name on non-Windows to
##            make_absolute_filename (see bug #36677)
## 2012-10-24 Style fixes

function [ xls, xlssupport, lastintf ] = __UNO_spsh_open__ (xls, xwrite, filename, xlssupport)

    ## First, the file name must be transformed into a URL
    if (~isempty (strmatch ("file:///", filename)) || ~isempty (strmatch ("http://", filename))...
       || ~isempty (strmatch ("ftp://", filename)) || ~isempty (strmatch ("www://", filename)))
      ## Seems in proper shape for OOo (at first sight)
    else
      ## Transform into URL form. 
      ## FIXME make_absolute_filename() doesn't work across drive(-letters) so
      ##       until it is fixed we'll fall back on canonicalize_file_name() there
      if (ispc)
        fname = canonicalize_file_name (strsplit (filename, filesep){end});
      else
        fname = make_absolute_filename (strsplit (filename, filesep){end});
      endif
      ## On Windows, change backslash file separator into forward slash
      if (strcmp (filesep, "\\"))
        tmp = strsplit (fname, filesep);
        flen = numel (tmp);
        tmp(2:2:2*flen) = tmp;
        tmp(1:2:2*flen) = '/';
        fname = [ tmp{:} ];
      endif
      filename = [ "file://" fname ];
    endif
    try
      xContext = java_invoke ("com.sun.star.comp.helper.Bootstrap", "bootstrap");
      xMCF = xContext.getServiceManager ();
      oDesktop = xMCF.createInstanceWithContext ("com.sun.star.frame.Desktop", xContext);
      ## Workaround for <UNOruntime>.queryInterface():
      unotmp = java_new ("com.sun.star.uno.Type", "com.sun.star.frame.XComponentLoader");
      aLoader = oDesktop.queryInterface (unotmp);
      ## Some trickery as Octave Java cannot create initialized arrays
      lProps = javaArray ("com.sun.star.beans.PropertyValue", 1);
      lProp = java_new ("com.sun.star.beans.PropertyValue", "Hidden", 0, true, []);
      lProps(1) = lProp;
      if (xwrite > 2)
        xComp = aLoader.loadComponentFromURL ("private:factory/scalc", "_blank", 0, lProps);
      else
        xComp = aLoader.loadComponentFromURL (filename, "_blank", 0, lProps);
      endif
      ## Workaround for <UNOruntime>.queryInterface():
      unotmp = java_new ("com.sun.star.uno.Type", "com.sun.star.sheet.XSpreadsheetDocument");
      xSpdoc = xComp.queryInterface (unotmp);
      ## save in ods struct:
      xls.xtype = "UNO";
      xls.workbook = xSpdoc;    ## Needed to be able to close soffice in odsclose()
      xls.filename = filename;
      xls.app.xComp = xComp;    ## Needed to be able to close soffice in odsclose()
      xls.app.aLoader = aLoader;## Needed to be able to close soffice in odsclose()
      xls.odfvsn = "UNO";
      xlssupport += 16;
      lastintf = "UNO";
    catch
      error ("Couldn't open file %s using UNO", filename);
    end_try_catch

endfunction
