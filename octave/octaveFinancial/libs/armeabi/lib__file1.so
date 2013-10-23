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
## @deftypefn {Function File} {b =} busdate (refdate)
## @deftypefnx {Function File} {b =} busdate (refdate, direction)
## @deftypefnx {Function File} {b =} busdate (refdate, direction, holiday)
## @deftypefnx {Function File} {b =} busdate (refdate, direction, holiday, weekend)
##
## Return the datenum of the next or previous business day from
## @var{refdate}. @var{direction} indicates the next day (default) if 1
## and the previous day if -1.  @var{holiday} is a vector of datenums
## that defines the holidays observed (the holidays function is used if
## not given).  @var{weekend} defines the days of the week that should
## be considered weekends; [1 0 0 0 0 0 1] (default) indicates that
## Sunday and Saturday are holidays.
##
## If any of the optional inputs (@var{direction}, @var{holiday},
## @var{weekend}) are empty, then the default is used.
##
## @seealso{holidays, lbusdate, isbusday, fbusdate}
## @end deftypefn

function rd = busdate (rd, d, hol, wkend)

  if ~isnumeric (rd)
	rd = datenum ( rd);
  endif
  if nargin < 2 || isempty (d)
	d = 1;
  elseif ~ all (abs (d) == 1)
	## People could use other numbers to skip days, but that is not
	## supported.
	error ("directions must all be either 1 or -1.")
  endif
  if nargin < 3
	hol = [];
  end
  if nargin < 4
	wkend = [];
  elseif nargin > 4
	print_usage ();
  endif

  rd += d;
  mask = ~isbusday (rd, hol, wkend);
  while any (mask)
	## Only recompute for the days that are not yet business days
	if isscalar (d)
	  rd(mask) += d;
	else
	  rd(mask) += d(mask);
	endif
	mask(mask) = ~isbusday (rd(mask), hol, wkend);
  endwhile

endfunction

## Tests
## A normal day
%!assert(busdate(datenum(2008,1,2)), datenum(2008,1,3))
## A holiday
%!assert(busdate(datenum(2007,12,31)), datenum(2008,1,2))
## Go over a weekend and start in a weekend
%!assert(busdate(datenum(2007,1,5)), datenum(2007,1,8))
%!assert(busdate(datenum(2007,1,6)), datenum(2007,1,8))
## Backward
%!assert(busdate(datenum(2008,1,3), -1), datenum(2008,1,2))
## Backward holiday
%!assert(busdate(datenum(2008,1,2), -1), datenum(2007,12,31))
## Backward with alternate holidays
%!assert(busdate(datenum(2008,1,2), -1, datenum(2007,1,1):datenum(2008,1,1)), datenum(2006,12,29))
## Multiple dates in both orientations
%!assert(busdate([datenum(2008,1,2) datenum(2007,1,1)]), [datenum(2008,1,3) datenum(2007,1,2)])
%!assert(busdate([datenum(2008,1,2) datenum(2007,1,1)], [1 1]), [datenum(2008,1,3) datenum(2007,1,2)])
%!assert(busdate([datenum(2008,1,2) datenum(2007,1,1)], 1), [datenum(2008,1,3) datenum(2007,1,2)])
%!assert(busdate([datenum(2008,1,2);datenum(2007,1,1)], [1;1]), [datenum(2008,1,3);datenum(2007,1,2)])
## Multiple dates with opposite directions holidays and weekends
%!assert(busdate([datenum(2008,1,2);datenum(2007,1,2)], [1;-1]), [datenum(2008,1,3);datenum(2006,12,29)])
## Alternate weekends
%!assert(busdate(datenum(2008,1,2), 1, holidays(datenum(2008,1,1), datenum(2008,1,31)), [1 0 0 0 0 0 0]), datenum(2008,1,3))
%!assert(busdate(datenum(2008,1,4), 1, holidays(datenum(2008,1,1), datenum(2008,1,31)), [1 0 0 0 0 0 0]), datenum(2008,1,5))
%!assert(busdate(datenum(2008,1,5), 1, holidays(datenum(2008,1,1), datenum(2008,1,31)), [1 0 0 0 0 0 0]), datenum(2008,1,7))
%!assert(busdate(datenum(2008,1,6), 1, holidays(datenum(2008,1,1), datenum(2008,1,31)), [1 0 0 0 0 0 0]), datenum(2008,1,7))
%!assert(busdate(datenum(2008,1,1), 1, holidays(datenum(2008,1,1), datenum(2008,1,31)), [1 1 1 1 1 1 0]), datenum(2008,1,5))
