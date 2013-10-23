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
## @deftypefn {Function File} {r =} isbusday (refdate)
## @deftypefnx {Function File} {r =} isbusday (refdate, holiday)
## @deftypefnx {Function File} {r =} isbusday (refdate, holiday, weekend)
##
## Return true if the @var{refdate} is a business date @var{refdate}.
## @var{holiday} is a vector of datenums that defines the holidays
## observed (the holidays function is used if not given). @var{weekend}
## defines the days of the week that should be considered weekends;
## [1 0 0 0 0 0 1] (default) indicates that Sunday and Saturday are
## weekends.
##
## @seealso{holidays, lbusdate, busdate, fbusdate}
## @end deftypefn

function mask = isbusday (rd, hol=[], wkend=[])

  if ~ isnumeric (rd)
	rd = datenum (rd);
  endif
  if isempty (hol)
    ## Get all possible holidays that could affect the output.
    hol = holidays (min(rd), max(rd));
  end
  if isempty (wkend)
    wkend = [1 0 0 0 0 0 1];
  elseif numel (wkend) ~= 7
    error ("wkend must have 7 elements")
  elseif nargin > 3
    print_usage ();
  endif

  mask = reshape (wkend (weekday (rd)), size (rd));
  if ~ isempty (hol)
    ## Is it a holiday?
    mask = mask | ismember(rd, hol);
  endif
  mask = ~mask;
endfunction

## Tests
## A normal day
%!assert(isbusday(datenum(2008,1,2)), true())
## A holiday
%!assert(isbusday(datenum(2008,1,1)), false())
%!assert(isbusday(datenum(2008,1,1), []), false())
## A weekend
%!assert(isbusday(datenum(2008,2,2)), false())
## An alternate holiday
%!assert(isbusday(datenum(2008,1,2), datenum(2008,1,2)), false())
## An alternate weekend
%!assert(isbusday(datenum(2008,1,2), [], zeros(1,7)), true())
%!assert(isbusday(datenum(2008,1,2), [], ones(1,7)), false())
## A vector
%!assert(isbusday([datenum(2008,1,2) datenum(2008,2,2)]), [true() false()])
## A vector in the other direction
%!assert(isbusday([datenum(2008,1,2);datenum(2008,2,2)]), [true();false()])
