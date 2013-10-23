## Copyright (C) 2008 Bill Denney <bill@denney.ws>
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
## @deftypefn {Function File} {exceldatenums =} m2xdate (datenums)
## @deftypefnx {Function File} {exceldatenums =} m2xdate (datenums, convention)
## @deftypefnx {Function File} {exceldatenums =} m2xdate (datenums, convention, "ExcelBug")
##
## Convert @var{datenums} from the internal date format to the format
## used by Microsoft Excel.  If set to 0 (default, Excel for Windows),
## @var{convention} specifies to use the Excel 1900 convention where Jan
## 1, 1900 corresponds to Excel serial date number 1.  If set to 1
## (Excel for Mac), @var{convention} specifies to use the Excel 1904
## convention where Jan 1, 1904 corresponds to Excel serial date number
## 0.
##
## Note that this does not take into account the Excel bug where 1900 is
## considered to be a leap year unless you give the "ExcelBug" option.
##
## Excel does not represent dates prior to 1 January 1900 using this
## format, so a warning will be issued if any dates preceed this date.
##
## @seealso{datenum, x2mdate}
## @end deftypefn

function dates = m2xdate (dates, convention, excelbug)

  if nargin == 1
	convention = 0;
	excelbug = false();
  elseif nargin == 2
	excelbug = false();
  elseif nargin == 3
	excelbug = strcmpi(excelbug, "ExcelBug");
  else
	print_usage ();
  endif

  if convention == 0
	adj = datenum(1900, 1, 1) - 2;
  elseif convention == 1
	adj = datenum(1904, 1, 1);
  endif

  if excelbug
	datemask = (dates < datenum(1900, 3, 1));
	dates(datemask) = dates(datemask) - 1;
  endif
  dates = dates - adj;
  if any (dates < 0)
	warning ("Negative date found, this will not work within MS Excel.")
  endif

endfunction

## Tests
%!assert(m2xdate(datenum(2008, 1, 1)), 39448)
%!assert(m2xdate(datenum(2007:2008, 1, 1)), [39083 39448])
%!assert(m2xdate(datenum(1900, 1, 1)), 2)
%!assert(m2xdate(datenum(1900, 1, 1), 0, "ExcelBug"), 1)
%!assert(m2xdate(datenum(1904, 1, 1), 1), 0)

