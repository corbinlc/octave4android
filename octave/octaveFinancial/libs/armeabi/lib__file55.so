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
## @deftypefn {Function File} {datenums =} x2mdate (exceldatenums)
## @deftypefnx {Function File} {datenums =} x2mdate (exceldatenums, convention)
## @deftypefnx {Function File} {datenums =} x2mdate (exceldatenums, convention, "ExcelBug")
##
## Convert @var{datenums} from the Microsoft Excel date format to the
## format used by @code{datenum}.  If set to 0 (default, Excel for
## Windows), @var{convention} specifies to use the Excel 1900 convention
## where Jan 1, 1900 corresponds to Excel serial date number 1.  If set
## to 1 (Excel for Mac), @var{convention} specifies to use the Excel
## 1904 convention where Jan 1, 1904 corresponds to Excel serial date
## number 0.
##
## Note that this does not take into account the Excel bug where 1900 is
## considered to be a leap year unless you give the "ExcelBug" option.
##
## Excel does not represent dates prior to 1 January 1900 using this
## format, so a warning will be issued if any dates preceed this date.
##
## @seealso{datenum, x2mdate}
## @end deftypefn

function dates = x2mdate (dates, convention, excelbug)

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

  if any (dates < 0)
	warning ("Negative date found, this will not work within MS Excel.")
  endif

  if convention == 0
	adj = datenum(1900, 1, 1) - 2;
  elseif convention == 1
	adj = datenum(1904, 1, 1);
  endif

  if excelbug
	datemask = (dates < 61);
	dates(datemask) = dates(datemask) + 1;
  endif
  dates = dates + adj;

endfunction

## Tests
%!assert(x2mdate(39448), datenum(2008, 1, 1))
%!assert(x2mdate([39083 39448]), datenum(2007:2008, 1, 1))
%!assert(x2mdate(2), datenum(1900, 1, 1))
%!assert(x2mdate(1, 0, "ExcelBug"), datenum(1900, 1, 1))
%!assert(x2mdate(0, 1), datenum(1904, 1, 1))

