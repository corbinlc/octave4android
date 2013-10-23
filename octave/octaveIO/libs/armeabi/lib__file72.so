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

## __UNO_spsh_close__ - internal function: close a spreadsheet file using UNO

## Author: Philip Nienhuis <prnienhuis@users.sf.net>
## Created: 2012-10-12
## Updates:
## 2012-10-23 Style fixes

function [ xls ] = __UNO_spsh_close__ (xls, force)

  if (isfield (xls, "nfilename"))
    ## New filename specified
    if (strcmp (xls.xtype, 'UNO'))
      ## For UNO, turn filename into URL
      if    (~isempty (strmatch ("file:///", nfilename))... 
          || ~isempty (strmatch ("http://",  nfilename))...
          || ~isempty (strmatch ("ftp://",   nfilename))...   
          || ~isempty (strmatch ("www://",   nfilename)))
        ## Seems in proper shape for OOo (at first sight)
      else
        ## Transform into URL form
        if (ispc)
          fname = canonicalize_file_name (strsplit (nfilename, filesep){end});
        else
          fname = make_absolute_filename (strsplit (nfilename, filesep){end});
        endif
        ## On Windows, change backslash file separator into forward slash
        if (strcmp (filesep, "\\"))
          tmp = strsplit (fname, filesep);
          flen = numel (tmp);
          tmp(2:2:2*flen) = tmp;
          tmp(1:2:2*flen) = "/";
          nfilename = [ "file://" tmp{:} ];
        endif
      endif
    endif
  endif

  try
    if (xls.changed && xls.changed < 3)
      ## Workaround:
      unotmp = java_new ("com.sun.star.uno.Type", "com.sun.star.frame.XModel");
      xModel = xls.workbook.queryInterface (unotmp);
      unotmp = java_new ("com.sun.star.uno.Type", "com.sun.star.util.XModifiable");
      xModified = xModel.queryInterface (unotmp);
      if (xModified.isModified ())
        unotmp = ...
          java_new ("com.sun.star.uno.Type", "com.sun.star.frame.XStorable");  # isReadonly() ?    
        xStore = xls.app.xComp.queryInterface (unotmp);
        if (xls.changed == 2)
          ## Some trickery as Octave Java cannot create non-numeric arrays
          lProps = javaArray ("com.sun.star.beans.PropertyValue", 1);
          lProp = ...
            java_new ("com.sun.star.beans.PropertyValue", "Overwrite", 0, true, []);
          lProps(1) = lProp;
          ## OK, store file
          if (isfield (xls, "nfilename"))
            ## Store in another file 
            ## FIXME check if we need to close the old file
            xStore.storeAsURL (xls.nfilename, lProps);
          else
            xStore.storeAsURL (xls.filename, lProps);
          endif
        else
          xStore.store ();
        endif
      endif
    endif
    xls.changed = -1;    ## Needed for check on properly shutting down OOo
    ## Workaround:
    unotmp = java_new ("com.sun.star.uno.Type", "com.sun.star.frame.XModel");
    xModel = xls.app.xComp.queryInterface (unotmp);
    unotmp = java_new ("com.sun.star.uno.Type", "com.sun.star.util.XCloseable");
    xClosbl = xModel.queryInterface (unotmp);
    xClosbl.close (true);
    unotmp = java_new ("com.sun.star.uno.Type", "com.sun.star.frame.XDesktop");
    xDesk = xls.app.aLoader.queryInterface (unotmp);
    xDesk.terminate();
    xls.changed = 0;
  catch
    if (force)
      ## Force closing OOo
      unotmp = java_new ("com.sun.star.uno.Type", "com.sun.star.frame.XDesktop");
      xDesk = xls.app.aLoader.queryInterface (unotmp);
      xDesk.terminate();
    else
      warning ("Error closing xls pointer (UNO)");
    endif
    return
  end_try_catch

endfunction
