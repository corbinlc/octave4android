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
## @deftypefn {Function File} [ @var{rawarr}, @var{ods}, @var{rstatus} ] = ods2oct (@var{ods})
## @deftypefnx {Function File} [ @var{rawarr}, @var{ods}, @var{rstatus} ] = ods2oct (@var{ods}, @var{wsh})
## @deftypefnx {Function File} [ @var{rawarr}, @var{ods}, @var{rstatus} ] = ods2oct (@var{ods}, @var{wsh}, @var{range})
## @deftypefnx {Function File} [ @var{rawarr}, @var{ods}, @var{rstatus} ] = ods2oct (@var{ods}, @var{wsh}, @var{range}, @var{options})
##
## Read data contained within cell range @var{range} from worksheet @var{wsh}
## in an OpenOffice_org Calc spreadsheet file pointed to in struct @var{ods}.
##
## @var{ods} is supposed to have been created earlier by odsopen in the
## same octave session.
##
## @var{wsh} is either numerical or text, in the latter case it is 
## case-sensitive.
## Note that in case of a numerical @var{wsh} this number refers to the
## position in the worksheet stack, counted from the left in a Calc
## window. The default is numerical 1, i.e. the leftmost worksheet
## in the ODS file.
##
## @var{range} is expected to be a regular spreadsheet range format,
## or "" (empty string, indicating all data in a worksheet).
## If no range is specified the occupied cell range will have to be
## determined behind the scenes first; this can take some time.
##
## Optional argument @var{options}, a structure, can be used to
## specify various read modes by setting option fields in the struct
## to true (1) or false (0). Currently recognized option fields are:
##
## @table @asis
## @item "formulas_as_text"
## If set to TRUE or 1, spreadsheet formulas (if at all present)
## are read as formula strings rather than the evaluated formula
## result values. This only works for the OTK and UNO interfaces.
## The default value is 0 (FALSE).
##
## @item 'strip_array'
## Set the value of this field set to TRUE or 1 to strip the returned
## output array @var{rawarr} from empty outer columns and rows. The
## spreadsheet cell rectangle limits from where the data actually
## came will be updated. The default value is FALSE or 0 (no cropping).
## @end table
##
## If only the first argument @var{ods} is specified, ods2oct will
## try to read all contents from the first = leftmost (or the only)
## worksheet (as if a range of @'' (empty string) was specified).
## 
## If only two arguments are specified, ods2oct assumes the second
## argument to be @var{wsh}. In that case ods2oct will try to read
## all data contained in that worksheet.
##
## Return argument @var{rawarr} contains the raw spreadsheet cell data.
## Use parsecell() to separate numeric and text values from @var{rawarr}.
##
## Optional return argument @var{ods} contains the pointer struct. Field
## @var{ods}.limits contains the outermost column and row numbers of the
## actually read cell range.
##
## Optional return argument @var{rstatus} will be set to 1 if the
## requested data have been read successfully, 0 otherwise.
##
## Erroneous data and empty cells turn up empty in @var{rawarr}.
## Date/time values in OpenOffice.org are returned as numerical values
## with base 1-1-0000 (same as octave). But beware that Excel spreadsheets
## rewritten by OpenOffice.org into .ods format may have numerical date
## cells with base 01-01-1900 (same as MS-Excel).
##
## When reading from merged cells, all array elements NOT corresponding 
## to the leftmost or upper OpenOffice.org cell will be treated as if the
## "corresponding" cells are empty.
##
## Examples:
##
## @example
##   A = ods2oct (ods1, '2nd_sheet', 'C3:ABS40000');
##   (which returns the numeric contents in range C3:ABS40000 in worksheet
##   '2nd_sheet' from a spreadsheet file pointed to in pointer struct ods1,
##   into numeric array A) 
## @end example
##
## @example
##   [An, ods2, status] = ods2oct (ods2, 'Third_sheet');
## @end example
##
## @seealso {odsopen, odsclose, parsecell, odsread, odsfinfo, oct2ods, odswrite}
##
## @end deftypefn

## Author: Philip Nienhuis
## Created: 2009-12-13
## Updates:
## 2009-12-30 First working version
## 2010-03-19 Added check for odfdom version (should be 0.7.5 until further notice)
## 2010-03-20 Works for odfdom v 0.8 too. Added subfunction ods3jotk2oct for that
## 2010-04-06 Benchmarked odfdom versions. v0.7.5 is up to 7 times faster than v0.8!
##            So I added a warning for users using odfdom 0.8.
## 2010-04-11 Removed support for odfdom-0.8 - it's painfully slow and unreliable
## 2010-05-31 Updated help text (delay i.c.o. empty range due to getusedrange call)
## 2010-08-03 Added support for reading back formulas (works only in OTK)
## 2010-08-12 Added explicit support for jOpenDocument v 1.2b3+
## 2010-08-25 Improved helptext (moved some text around)
## 2010-08-27 Added ods3jotk2oct - internal function for odfdom-0.8.6.jar
##     ''     Extended check on spsh_opts (must be a struct) 
## 2010-10-27 Moved cropping rawarr from empty outer rows & columns to here
## 2011-05-06 Experimental UNO support
## 2011-09-18 Set rstatus var here
## 2012-01-26 Fixed "seealso" help string
## 2012-02-25 Added 0.8.7 to supported odfdom versions in L.155
## 2012-02-26 Updated texinfo header help text
## 2012-06-08 Support for odfdom-incubator 0.8.8
##     ''     Replaced tabs by double space
## 2012-10-12 Moved all interface-specific subfubcs into ./private
## 2012-10-24 Style fixes
##
## Latest subfunc update: 2012-10-12

function [ rawarr, ods, rstatus ] = ods2oct (ods, wsh=1, datrange=[], spsh_opts=[])

  ## Check if ods struct pointer seems valid
  if (~isstruct (ods)); error ("File ptr struct expected for arg @ 1"); endif
  test1 = ~isfield (ods, "xtype");
  test1 = test1 || ~isfield (ods, "workbook");
  test1 = test1 || isempty (ods.workbook);
  test1 = test1 || isempty (ods.app);
  if (test1)
    error ("Arg #1 is an invalid ods file struct");
  endif
  ## Check worksheet ptr
  if (~(ischar (wsh) || isnumeric (wsh)))
    error ("Integer (index) or text (wsh name) expected for arg # 2");
  endif
  ## Check range
  if (~(isempty (datrange) || ischar (datrange)))
    error ("Character string (range) expected for arg # 3");
  endif
  ## Check & setup options struct
  if (nargin < 4 || isempty (spsh_opts))
    spsh_opts.formulas_as_text = 0;
    spsh_opts.strip_array = 1;
    ## Other options here:

  elseif (~isstruct (spsh_opts))
    error ("struct expected for OPTIONS argument (# 4)");
  else
    if (~isfield (spsh_opts, "formulas_as_text"))
      spsh_opts.formulas_as_text = 0;
    endif
    if (~isfield (spsh_opts, "strip_array"))
      spsh_opts.strip_array = 1;
    endif
    ## Future options:

  endif

  ## Select the proper interfaces
  if (strcmp (ods.xtype, "OTK"))
    ## Read ods file tru Java & ODF toolkit
    switch ods.odfvsn
      case "0.7.5"
        [rawarr, ods] = __OTK_ods2oct__ (ods, wsh, datrange, spsh_opts);
      case {"0.8.6", "0.8.7", "0.8.8"}
        [rawarr, ods] = __OTK_spsh2oct__ (ods, wsh, datrange, spsh_opts);
      otherwise
        error ("Unsupported odfdom version or invalid ods file pointer.");
    endswitch
  elseif (strcmp (ods.xtype, "JOD"))
    ## Read ods file tru Java & jOpenDocument. JOD doesn't know about formulas :-(
    [rawarr, ods] = __JOD_spsh2oct__  (ods, wsh, datrange);
  elseif (strcmp (ods.xtype, "UNO"))
    ## Read ods file tru Java & UNO
    [rawarr, ods] = __UNO_spsh2oct__ (ods, wsh, datrange, spsh_opts);
  ##elseif 
  ##  ---- < Other interfaces here >
  else
    error (sprintf ("ods2oct: unknown OpenOffice.org .ods interface - %s.", ods.xtype));
  endif

  rstatus = ~isempty (rawarr);

  ## Optionally strip empty outer rows and columns & keep track of original data location
  if (spsh_opts.strip_array && rstatus)
    emptr = cellfun ("isempty", rawarr);
    if (all (all (emptr)))
      rawarr = {};
      ods.limits= [];
    else
      nrows = size (rawarr, 1); ncols = size (rawarr, 2);
      irowt = 1;
      while (all (emptr(irowt, :))); irowt++; endwhile
      irowb = nrows;
      while (all (emptr(irowb, :))); irowb--; endwhile
      icoll = 1;
      while (all (emptr(:, icoll))); icoll++; endwhile
      icolr = ncols;
      while (all (emptr(:, icolr))); icolr--; endwhile

      # Crop outer rows and columns and update limits
      rawarr = rawarr(irowt:irowb, icoll:icolr);
      ods.limits = ods.limits + [icoll-1, icolr-ncols; irowt-1, irowb-nrows];
    endif
  endif

endfunction
