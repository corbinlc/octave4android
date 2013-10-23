## Copyright (C) 2010,2011,2012 Philip Nienhuis <pr.nienhuis@users.sf.net>
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
## @deftypefn {Function File} [ @var{toprow#}, @var{bottomrow#}, @var{leftcol#}, @var{rightcol#} ] = getusedrange (@var{spptr}, @var{shindex#})
## Find occupied data range in worksheet @var{shindex#} in a spreadsheet
## pointed to in struct @var{spptr} (either MS-Excel or
## OpenOffice_org Calc).
##
## @var{shindex#} must be numeric and is 1-based. @var{spptr} can either
## refer to an MS-Excel spreadsheet (spptr returned by xlsopen) or an
## OpenOffice.org Calc spreadsheet (spptr returned by odsopen).
## None of these inputs are checked!
##
## Be aware that especially for OpenOffice.org Calc (ODS) spreadsheets 
## the results can only be obtained by counting all cells in all rows;
## this can be fairly time-consuming. Reliable ods data size results can
## only be obtained using UNO interface.
## For the ActiveX (COM) interface the underlying Visual Basic call relies
## on cached range values and counts empty cells with only formatting too,
## so COM returns only approximate (but then usually too big) range values.
##
## Examples:
##
## @example
##   [trow, brow, lcol, rcol] = getusedrange (ods2, 3);
##   (which returns the outermost row & column numbers of the rectangle
##    enveloping the occupied cells in the third sheet of an OpenOffice_org
##    Calc spreadsheet pointedto in struct ods2)
## @end example
##
## @example
##   [trow, brow, lcol, rcol] = getusedrange (xls3, 3);
##   (which returns the outermost row & column numbers of the rectangle
##    enveloping the occupied cells in the third sheet of an Excel
##    spreadsheet pointed to in struct xls3)
## @end example
##
## @seealso {xlsopen, xlsclose, odsopen, odsclose, xlsfinfo, odsfinfo}
##
## @end deftypefn

## Author: Philip Nienhuis <philip@JVC741>
## Created: 2010-03-18 (First usable version) for ODS (java/OTK)
## Updates:
## 2010-03-20 Added Excel support (Java/POI)
## 2010-05-23 Added in support for jOpenDocument ODS
## 2010-05-31 Fixed bugs in getusedrange_jod.m
## 2010-08-24 Added support for odfdom 0.8.6 (ODF Toolkit)
## 2010-08-27 Added checks for input arguments
##     ''     Indentation changed from tab to doublespace
## 2010-10-07 Added COM support (at last!)
## 2011-05-06 Experimental support for Java/UNO bridge
## 2011-06-13 OpenXLS support added
## 2011-09-08 Style & layout updates
## 2012-01-26 Fixed "seealso" help string
## 2012-06-08 Replaced tabs by double space
##     ''     Added COM and OXS to message about supported interfaces
## 2012-10-12 Moved all interface-specific subfuncs to . /private
## 2012-10-23 Added UNO to error message
##     ''     Style fixes

function [ trow, lrow, lcol, rcol ] = getusedrange (spptr, ii)

  ## Some checks
  if ~isstruct (spptr), error ("Illegal spreadsheet pointer argument"); endif

  if (strcmp (spptr.xtype, 'OTK'))
    [ trow, lrow, lcol, rcol ] = __OTK_getusedrange__ (spptr, ii);
  elseif (strcmp (spptr.xtype, "JOD"))
    [ trow, lrow, lcol, rcol ] = __JOD_getusedrange__ (spptr, ii);
  elseif (strcmp (spptr.xtype, "UNO"))
    [ trow, lrow, lcol, rcol ] = __UNO_getusedrange__ (spptr, ii);
  elseif (strcmp (spptr.xtype, "COM"))
    [ trow, lrow, lcol, rcol ] = __COM_getusedrange__ (spptr, ii);
  elseif (strcmp (spptr.xtype, "POI"))
    [ trow, lrow, lcol, rcol ] = __POI_getusedrange__ (spptr, ii);
  elseif (strcmp (spptr.xtype, "JXL"))
    [ trow, lrow, lcol, rcol ] = __JXL_getusedrange__ (spptr, ii);
  elseif (strcmp (spptr.xtype, "OXS"))
    [ trow, lrow, lcol, rcol ] = __OXS_getusedrange__ (spptr, ii);
  else
    error ...
      ("Unknown interface - only OTK, JOD, COM, POI, JXL, UNO and OXS implemented");
  endif

endfunction
