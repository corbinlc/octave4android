## Copyright (C) 2010,2011,2012 Philip Nienhuis <prnienhuis@users.sf.net>
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
## @deftypefn {Function File} [ @var{topleftaddr}, @var{nrows}, @var{ncols}, @var{toprow}, @var{leftcol} ] = spsh_chkrange ( @var{range}, @var{rowsize}, @var{colsize}, @var{intf-type}, @var{filename})
## (Internal function) Get and check various cell and range address parameters for spreadsheet input.
##
## spsh_chkrange should not be invoked directly but rather through oct2xls or oct2ods.
##
## Example:
##
## @example
##   [tl, nrw, ncl, trw, lcl] = spsh_chkrange (crange, nr, nc, xtype, fileptr);
## @end example
##
## @end deftypefn

## Author: Philip Nienhuis <prnienhuis@users.sf.net>
## Created: 2010-08-02
## Updates: 
## 2010-08-25 Option for supplying file pointer rather than interface_type & filename
##            (but this can be wasteful if the file ptr is copied)
## 2011-03-29 Bug fix - unrecognized pointer struct & wrong type error msg
## 2011-05-15 Experimental UNO support added (OpenOffice.org & clones)
## 2011-09-18 Updated UNO data row capacity for LibreOffice 3.4+ (now 1,048,576 rows)
## 2012-10-23 Style fixes

function [ topleft, nrows, ncols, trow, lcol ] = spsh_chkrange (crange, nr, nc, intf, filename=[])

	if (nargin == 4)
		## File pointer input assumed
		if (isstruct (intf))
			xtype = intf.xtype;
			filename = intf.filename;
		else
			error ("Too few or improper arguments supplied.");
		endif
	else
		## Interface type & filename supplied
		xtype = intf;
	endif

	## Define max row & column capacity from interface type & file suffix
	switch xtype
		case { 'COM', 'POI' }
			if (strmatch (tolower (filename(end-3:end)), '.xls'))
				## BIFF5 & BIFF8
				ROW_CAP = 65536;   COL_CAP = 256;
			else
				## OOXML (COM needs Excel 2007+ for this)
				ROW_CAP = 1048576; COL_CAP = 16384;
			endif
		case { 'JXL', 'OXS' }
			## JExcelAPI & OpenXLS can only process BIFF5 & BIFF8
			ROW_CAP = 65536;   COL_CAP = 256;
		case { 'OTK', 'JOD' }
			## ODS
			ROW_CAP = 65536;   COL_CAP = 1024;
		case { 'UNO' }
			## ODS; LibreOffice has a higher row capacity
			## FIXME - use UNO calls to check physical row capacity
      ## FIXME - LibreOffice has higher row capacity but its Java classes haven't been updated
			ROW_CAP = 1048576;   COL_CAP = 1024;
		otherwise
			error (sprintf ("Unknown interface type - %s\n", xtype));
	endswitch

	if (isempty (deblank (crange)))
		trow = 1;
		lcol = 1;
		nrows = nr;
		ncols = nc;
		topleft = 'A1';
	elseif (isempty (strfind (deblank (crange), ':')))
		## Only top left cell specified
		[topleft, dummy1, dummy2, trow, lcol] = parse_sp_range (crange);
		nrows = nr;
		ncols = nc;
	else
		[topleft, nrows, ncols, trow, lcol] = parse_sp_range (crange);
	endif
	if (trow > ROW_CAP || lcol > COL_CAP)
		error ("Topleft cell (%s) beyond spreadsheet limits.");
	endif
	## Check spreadsheet capacity beyond requested topleft cell
	nrows = min (nrows, ROW_CAP - trow + 1);
	ncols = min (ncols, COL_CAP - lcol + 1);
	## Check array size and requested range
	nrows = min (nrows, nr);
	ncols = min (ncols, nc);

endfunction
