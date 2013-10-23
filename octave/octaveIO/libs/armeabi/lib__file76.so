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
## @deftypefn {Function File} [@var{xls}] = xlsclose (@var{xls})
## @deftypefnx {Function File} [@var{xls}] = xlsclose (@var{xls}, @var{filename})
## @deftypefnx {Function File} [@var{xls}] = xlsclose (@var{xls}, "FORCE")
## Close the Excel spreadsheet pointed to in struct @var{xls}, if needed
## write the file to disk. Based on information contained in @var{xls},
## xlsclose will determine if the file should be written to disk.
##
## If no errors occured during writing, the xls file pointer struct will be
## reset and -if COM interface was used- ActiveX/Excel will be closed.
## However if errors occurred, the file pinter will be untouched so you can
## clean up before a next try with xlsclose().
## Be warned that until xlsopen is called again with the same @var{xls} pointer
## struct, hidden Excel or Java applications with associated (possibly large)
## memory chunks are kept in memory, taking up resources.
## If (string) argument "FORCE" is supplied, the file pointer will be reset 
## regardless, whether the possibly modified file has been saved successfully
## or not. Hidden Excel (COM) or OpenOffice.org (UNO) invocations may live on,
## possibly even impeding proper shutdown of Octave.
##
## @var{filename} can be used to write changed spreadsheet files to
## an other file than opened with xlsopen(); unfortunately this doesn't work
## with JXL (JExcelAPI) interface.
##
## You need MS-Excel (95 - 2010), and/or the Java package => 1.2.8 plus Apache
## POI > 3.5 and/or JExcelAPI and/or OpenXLS and/or OpenOffice.org or clones
## installed on your computer + proper javaclasspath set, to make this
## function work at all.
##
## @var{xls} must be a valid pointer struct made by xlsopen() in the same
## octave session.
##
## Examples:
##
## @example
##   xls1 = xlsclose (xls1);
##   (Close spreadsheet file pointed to in pointer struct xls1; xls1 is reset)
## @end example
##
## @seealso {xlsopen, xlsread, xlswrite, xls2oct, oct2xls, xlsfinfo}
##
## @end deftypefn

## Author: Philip Nienhuis
## Created: 2009-11-29
## Updates: 
## 2010-01-03 (checked OOXML support)
## 2010-08-25 See also: xlsopen (instead of xlsclose)
## 2010-10-20 Improved tracking of file changes and need to write it to disk
## 2010-10-27 Various changes to catch errors when writing to disk;
##     "      Added input arg "keepxls" for use with xlswrite.m to save the
##     "      untouched file ptr struct in case of errors rather than wipe it
## 2010-11-12 Replaced 'keepxls' by new filename arg; catch write errors and
##            always keep file pointer in case of write errors
## 2011-03-26 Added OpenXLS support
## 2011-05-18 Added experimental UNO support, incl. saving newly created files
## 2011-09-08 Bug fix in check for filename input arg
## 2012-01-26 Fixed "seealso" help string
## 2012-09-03 (in UNO sections) replace canonicalize_file_name on non-Windows to
##            make_absolute_filename (see bug #36677)
##     ''     (in UNO section) web adresses need only two consecutive slashes
## 2012-10-12 Move most interface-specific code to ./private subfuncs
##     ''     Move "file ptr preserved" message to proper else clause
## 2012-10-24 Style fixes
## 2012-12-18 Improved error/warning messages

function [ xls ] = xlsclose (xls, varargs)

  force = 0;

  if (nargin > 1)
    for ii=2:nargin
      if (strcmpi (varargin{ii}, "force"))
        ## Close .xls anyway even if write errors occur
        force = 1;

      ## Interface-specific clauses here:
      elseif (~isempty (strfind (tolower (varargin{ii}), '.')))
        ## Apparently a file name. First some checks....
        if (xls.changed == 0 || xls.changed > 2)
          warning ("xlsclose: file %s wasn't changed, new filename ignored.", xls.filename);
        elseif (strcmp (xls.xtype, "JXL"))
          error ("xlsclose: JXL doesn't support changing filename, new filename ignored.");
        elseif (~((strcmp (xls.xtype, "COM") || strcmp (xls.xtype, "UNO")) ... 
                && isempty (strfind ( lower (filename), ".xls"))))
          # Excel/ActiveX && OOo (UNO bridge) will write any valid filetype; POI/JXL/OXS need .xls[x]
          error ("xlsclose: .xls or .xlsx suffix lacking in filename %s", filename);
        else
          ## For multi-user environments, uncomment below AND relevant stanza in xlsopen
          ## In case of COM, be sure to first close the open workbook
          ##if (strcmp (xls.xtype, 'COM'))
          ##   xls.app.Application.DisplayAlerts = 0;
          ##   xls.workbook.close();
          ##   xls.app.Application.DisplayAlerts = 0;
          ##endif
          ## Preprocessing / -checking ready. Assign filename arg to file ptr struct
          xls.nfilename = filename;
        endif
      endif
    endfor
  endif

  if (strcmp (xls.xtype, "COM"))
    xls = __COM_spsh_close__ (xls);

  elseif (strcmp (xls.xtype, "POI"))
    xls = __POI_spsh_close__ (xls);

  elseif (strcmp (xls.xtype, "JXL"))
    xls = __JXL_spsh_close__ (xls);

  elseif (strcmp (xls.xtype, "OXS"))
    xls = __OXS_spsh_close__ (xls);

  elseif (strcmp (xls.xtype, "UNO"))
    xls = __UNO_spsh_close__ (xls, force);

  ## elseif   <other interfaces here>

  endif

  if (xls.changed && xls.changed < 3)
    warning (sprintf ("xlsclose: file %s could not be saved. Read-only or in use elsewhere?",...
                      xls.filename));
    if (force)
      xls = [];
    else
      printf ("(File pointer preserved. Try saving again later...)\n");
    endif
  else
    xls = [];
  endif

endfunction
