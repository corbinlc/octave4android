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
## @deftypefn {Function File} {b =} fbusdate (year, month)
## @deftypefnx {Function File} {b =} fbusdate (year, month, holiday)
## @deftypefnx {Function File} {b =} fbusdate (year, month, holiday, weekend)
##
## Return the datenum of the first business day of the @var{year} and
## @var{month}.  @var{holiday} is a vector of datenums that defines the
## holidays observed (the holidays function is used if not given).
## @var{weekend} defines the days of the week that should be considered
## weekends; [1 0 0 0 0 0 1] (default) indicates that Sunday and
## Saturday are holidays.
##
## If any of the optional inputs (@var{holiday}, @var{weekend}) are
## empty, then the default is used.
##
## @seealso{holidays, lbusdate, isbusday, busdate}
## @end deftypefn

function rd = fbusdate (y, m, hol, wkend)

  rd = datenum (y, m, 1);
  if nargin < 3
	hol = [];
  end
  if nargin < 4
	wkend = [];
  elseif nargin < 3 || nargin > 4
	print_usage ();
  endif

  ## Test from the day before the beginning of the month so that the
  ## first day of the month is captured.
  rd = busdate (rd-1, 1, hol, wkend);

endfunction

## Tests
## A normal day
%!assert(fbusdate(2008,2), datenum(2008,2,1))
## A holiday
%!assert(fbusdate(2008,1), datenum(2008,1,2))
## A weekend
%!assert(fbusdate(2008,3), datenum(2008,3,3))
