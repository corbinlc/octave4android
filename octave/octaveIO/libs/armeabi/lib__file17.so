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
## @deftypefn {Function File} [@var{ods}] = odsclose (@var{ods})
## @deftypefnx {Function File} [@var{ods}] = odsclose (@var{ods}, @var{filename})
## @deftypefnx {Function File} [@var{ods}] = odsclose (@var{ods}, "FORCE")
## Close the OpenOffice_org Calc spreadsheet pointed to in struct
## @var{ods}, if needed write the file to disk.
## odsclose will determine if the file must be written to disk based on
## information contained in @var{ods}.
## An empty pointer struct will be returned if no errors occurred. 
## Optional argument @var{filename} can be used to write changed spreadsheet
## files to an other file than opened by odsopen().
## Optional string argument "FORCE" can be specified to force resetting the
## file pointer struct. However, in case of UNO, a hidden OOo invocation
## may linger on in memory then, preventing proper closing of Octave.
##
## You need the Java package >= 1.2.6 plus odfdom.jar + xercesImpl.jar
## and/or jopendocument-<version>.jar installed on your computer +
## proper javaclasspath set, to make this function work at all.
## For UNO support, Octave-Java package >= 1.2.8 + latest fixes is imperative;
## furthermore the relevant classes had best be added to the javaclasspath by
## utility function chk_spreadsheet_support().
##
## @var{ods} must be a valid pointer struct made by odsopen() in the same
## octave session.
##
## Examples:
##
## @example
##   ods1 = odsclose (ods1);
##   (Close spreadsheet file pointed to in pointer struct ods1; ods1 is reset)
## @end example
##
## @seealso {odsopen, odsread, odswrite, ods2oct, oct2ods, odsfinfo, chk_spreadsheet_support}
##
## @end deftypefn

## Author: Philip Nienhuis
## Created: 2009-12-13
## Updates:
## 2010-01-08 (OTK ODS write support)
## 2010-04-13 Improved help text a little bit
## 2010-08-25 Swapped in texinfo help text
## 2010-10-17 Fixed typo in error message about unknown interface
## 2010-10-27 Improved file change tracking tru ods.changed
## 2010-11-12 Keep ods file pointer when write errors occur.
##     ''     Added optional filename arg to change filename to be written to
## 2011-05-06 Experimental UNO support
## 2011-05-07 In case of UNO, soffice now properly closed using xDesk
## 2011-05-18 Saving newly created files using UNO supported now
## 2011-09-08 FIXME - closing OOo kills all other OOo invocations (known Java-UNO issue)
## 2012-01-26 Fixed "seealso" help string
## 2012-06-08 tabs replaced by double space
## 2012-09-03 Extended file renaming section to xlsclose equivalent
## 2012-10-12 Move most interface-specific code to ./private subfuncs
##     ''     Move "file ptr preserved" message to proper else clause
## 2012-10-23 Style fixes
## 2012-12-18 Improved error/warning messages

function [ ods ] = odsclose (ods, varargs)

  ## If needed warn that dangling spreadsheet pointers may be left
  if (nargout < 1)
    warning ("odsclose.m: return argument missing - ods invocation not reset.");
  endif

  force = 0;

  if (nargin > 1)
    for ii=2:nargin
      if (strcmpi(varargin{ii}, "force"))
        ## Close .ods anyway even if write errors occur
        force = 1;

      elseif (~isempty (strfind (tolower (varargin{ii}), ".")))
        ## Apparently a file name. First some checks....
        if (ods.changed == 0 || ods.changed > 2)
          warning ("odsclose.m: file %s wasn't changed, new filename ignored.", ods.filename);
        elseif (~strcmp (xls.xtype, "UNO") && ...
                isempty (strfind ( lower (filename), ".ods")))
          ## UNO will write any file type, all other interfaces only .ods
            error ("odsclose.m: .ods suffix lacking in filename %s", filename);
        else
          ## Preprocessing / -checking ready. 
          ## Assign filename arg to file ptr struct
          ods.nfilename = filename;
        endif
      endif
    endfor
  endif

  if (strcmp (ods.xtype, "OTK"))
    ## Java & ODF toolkit
    ods = __OTK_spsh_close__ (ods, force);

  elseif (strcmp (ods.xtype, "JOD"))
    ## Java & jOpenDocument
    ods = __JOD_spsh_close__ (ods);

  elseif (strcmp (ods.xtype, "UNO"))
    ## Java & UNO bridge
    ods = __UNO_spsh_close__ (ods, force);

  ##elseif ---- < Other interfaces here >

  else
    error (sprintf ("ods2close: unknown OpenOffice.org .ods interface - %s.",...
                    ods.xtype));

  endif

  if (ods.changed && ods.changed < 3)
    error (sprintf ("odsclose.m: could not save file %s - read-only or in use elsewhere?",...
                    ods.filename));
    if (force)
      ods = [];
    else
      printf ("(File pointer preserved)\n");
    endif
  else
    ## Reset file pointer
    ods = [];
  endif

endfunction
