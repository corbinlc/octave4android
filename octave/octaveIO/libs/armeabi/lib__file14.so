## Copyright (C) 2009,2010,2011,2012 Philip Nienhuis <pr.nienhuis at users.sf.net>
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
## @deftypefn {Function File} [ @var{ods}, @var{rstatus} ] = oct2ods (@var{arr}, @var{ods})
## @deftypefnx {Function File} [ @var{ods}, @var{rstatus} ] = oct2ods (@var{arr}, @var{ods}, @var{wsh})
## @deftypefnx {Function File} [ @var{ods}, @var{rstatus} ] = oct2ods (@var{arr}, @var{ods}, @var{wsh}, @var{range})
## @deftypefnx {Function File} [ @var{ods}, @var{rstatus} ] = oct2ods (@var{arr}, @var{ods}, @var{wsh}, @var{range}, @var{options})
##
## Transfer data to an OpenOffice_org Calc spreadsheet previously opened
## by odsopen().
##
## Data in 1D/2D array @var{arr} are transferred into a cell range
## @var{range} in sheet @var{wsh}. @var{ods} must have been made earlier
## by odsopen(). Return argument @var{ods} should be the same as supplied
## argument @var{ods} and is updated by oct2ods. A subsequent call to
## odsclose is needed to write the updated spreadsheet to disk (and
## -if needed- close the Java invocation holding the file pointer).
##
## @var{arr} can be any 1D or 2D array containing numerical or character
## data (cellstr) except complex. Mixed numeric/text arrays can only be
## cell arrays.
##
## @var{ods} must be a valid pointer struct created earlier by odsopen.
##
## @var{wsh} can be a number (sheet name) or string (sheet number).
## In case of a yet non-existing Calc file, the first sheet will be
## used & named according to @var{wsh}.
## In case of existing files, some checks are made for existing sheet
## names or numbers.
## When new sheets are to be added to the Calc file, they are
## inserted to the right of all existing sheets. The pointer to the
## "active" sheet (shown when Calc opens the file) remains untouched.
##
## If @var{range} omitted, the top left cell where the data will be put
## is supposed to be 'A1'; only a top left cell address can be specified
## as well. In these cases the actual range to be used is determined by
## the size of @var{arr}.
## Be aware that large data array sizes may exhaust the java shared
## memory space. For larger arrays, appropriate memory settings are
## needed in the file java.opts; then the maximum array size for the
## java-based spreadsheet options can be in the order of perhaps 10^6
## elements.
##
## Optional argument @var{options}, a structure, can be used to specify
## various write modes.
## Currently the only option field is "formulas_as_text", which -if set
## to 1 or TRUE- specifies that formula strings (i.e., text strings
## starting with "=" and ending in a ")" ) should be entered as litteral
## text strings rather than as spreadsheet formulas (the latter is the
## default). As jOpenDocument doesn't support formula I/O at all yet,
## this option is ignored for the JOD interface.
##
## Data are added to the sheet, ignoring other data already present;
## existing data in the range to be used will be overwritten.
##
## If @var{range} contains merged cells, also the elements of @var{arr}
## not corresponding to the top or left Calc cells of those merged cells
## will be written, however they won't be shown until in Calc the merge is
## undone.
##
## Examples:
##
## @example
##   [ods, status] = ods2oct (arr, ods, 'Newsheet1', 'AA31:GH165');
##   Write array arr into sheet Newsheet1 with upperleft cell at AA31
## @end example
##
## @example
##   [ods, status] = ods2oct (@{'String'@}, ods, 'Oldsheet3', 'B15:B15');
##   Put a character string into cell B15 in sheet Oldsheet3
## @end example
##
## @seealso {ods2oct, odsopen, odsclose, odsread, odswrite, odsfinfo}
##
## @end deftypefn

## Author: Philip Nienhuis
## Created: 2009-12-13
## Updates:
## 2010-01-15 Updated texinfo header
## 2010-03-14 Updated help text (a.o. on java memory usage)
## 2010-03-25 see oct2jotk2ods
## 2010-03-28 Added basic support for ofdom v.0.8. Everything works except adding cols/rows
## 2010-03-29 Removed odfdom-0.8 support, it's simply too buggy :-( Added a warning instead
## 2010-06-01 Almost complete support for upcoming jOpenDocument 1.2b4. 1.2b3 still lacks a bit
## 2010-07-05 Added example for writng character strings
## 2010-07-29 Added option for entering / reading back spreadsheet formulas
## 2010-08-14 Moved check on input cell array to main function
## 2010-08-15 Texinfo header edits
## 2010-08-16 Added check on presence of output argument
## 2010-08-23 Added check on validity of ods file ptr
##    ''      Experimental support for odfdom 0.8.6 (in separate subfunc, to be integrated later)
## 2010-08-25 Improved help text (java memory, ranges)
## 2010-10-27 Improved file change tracking tru ods.changed
## 2010-11-12 Better input argument checks
## 2010-11-13 Reset ods.limits when read was successful
## 2010-11-13 Added check for 2-D input array
## 2011-03-23 First try of odfdom 0.8.7
## 2011-05-15 Experimental UNO support added
## 2011-11-18 Fixed bug in test for range parameter being character string
## 2012-01-26 Fixed "seealso" help string
## 2012-02-20 Fixed range parameter to be default empty string rather than empty numeral
## 2012-02-27 More range arg fixes
## 2012-03-07 Updated texinfo help text
## 2012-06-08 Support for odfdom-incubator-0.8.8
##     ''     Tabs replaced by double space
## 2012-10-12 Moved all interface-specific subfubcs into ./private
## 2012-10-24 Style fixes
## 2012-12-18 Improved error/warning messages
##
## Latest subfunc update: 2012-10-12

function [ ods, rstatus ] = oct2ods (c_arr, ods, wsh=1, crange="", spsh_opts=[])

  if (nargin < 2) error ("oct2xls needs a minimum of 2 arguments."); endif
  
  ## Check if input array is cell
  if (isempty (c_arr))
    warning ("oct2ods: request to write empty matrix - ignored."); 
    rstatus = 1;
    return;
  elseif (isnumeric (c_arr))
    c_arr = num2cell (c_arr);
  elseif (ischar(c_arr))
    c_arr = {c_arr};
    printf ("(oct2ods: input character array converted to 1x1 cell)\n");
  elseif (~iscell (c_arr))
    error ("oct2ods: input array neither cell nor numeric array");
  endif
  if (ndims (c_arr) > 2)
    error ("oct2ods: only 2-dimensional arrays can be written to spreadsheet");
  endif

  ## Check ods file pointer struct
  test1 = ~isfield (ods, "xtype");
  test1 = test1 || ~isfield (ods, "workbook");
  test1 = test1 || isempty (ods.workbook);
  test1 = test1 || isempty (ods.app);
  if test1
    error ("oct2ods: arg #2: Invalid ods file pointer struct");
  endif

  ## Check worksheet ptr
  if (~(ischar (wsh) || isnumeric (wsh)))
    error ("oct2ods: integer (index) or text (wsh name) expected for arg # 3");
  endif

  ## Check range
  if (~isempty (crange) && ~ischar (crange))
    error ("oct2ods: character string (range) expected for arg # 4");
  elseif (isempty (crange))
    crange = "";
  endif

  ## Various options 
  if (isempty (spsh_opts))
    spsh_opts.formulas_as_text = 0;
    ## other options to be implemented here
  elseif (isstruct (spsh_opts))
    if (~isfield (spsh_opts, "formulas_as_text"))
      spsh_opts.formulas_as_text = 0; 
    endif
    ## other options to be implemented here:
 
  else
    error ("oct2ods: structure expected for arg # 5" (options));
  endif
  
  if (nargout < 1)
    printf ("oct2ods: warning: no output spreadsheet file pointer specified.\n");
  endif

  if (strcmp (ods.xtype, "OTK"))
    ## Write ods file tru Java & ODF toolkit.
    switch ods.odfvsn
      case "0.7.5"
        [ ods, rstatus ] = __OTK_oct2ods__ (c_arr, ods, wsh, crange, spsh_opts);
      case {"0.8.6", "0.8.7", "0.8.8"}
        [ ods, rstatus ] = __OTK_oct2spsh__ (c_arr, ods, wsh, crange, spsh_opts);
      otherwise
        error ("oct2ods: unsupported odfdom version");
    endswitch

  elseif (strcmp (ods.xtype, "JOD"))
    ## Write ods file tru Java & jOpenDocument. API still leaves lots to be wished...
    [ ods, rstatus ] = __JOD_oct2spsh__ (c_arr, ods, wsh, crange);

  elseif (strcmp (ods.xtype, "UNO"))
    ## Write ods file tru Java & UNO bridge (OpenOffice.org & clones)
    [ ods, rstatus ] = __UNO_oct2spsh__ (c_arr, ods, wsh, crange, spsh_opts);

  ##elseif 
    ##---- < Other interfaces here >

  else
    error (sprintf ("ods2oct: unknown OpenOffice.org .ods interface - %s.",...
                    ods.xtype));
  endif

  if (rstatus), ods.limits = []; endif

endfunction
