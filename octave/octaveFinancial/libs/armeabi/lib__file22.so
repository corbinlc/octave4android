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
## @deftypefn {Function File} {h =} holidays (startdate, enddate)
##
## Return a vector of datenums that were holidays between
## @var{startdate} and @var{enddate}, inclusive.  These holidays are
## trading holidays observed by the NYSE according to its rule 51.10. It
## does not take into account the exceptions for "unusual business
## conditions" or for additional days that have been called as holidays
## for one-time purposes.
##
## The complete list can be found at
## http://www.chronos-st.org/NYSE_Observed_Holidays-1885-Present.html
##
## @seealso{busdate, lbusdate, isbusday, fbusdate}
## @end deftypefn

function hol = holidays (sd, ed)

  sd = datenum (datevec (sd));
  ed = datenum (datevec (ed));

  ## just get the start and end years and generate all holidays in that range
  yrs = year(sd):year(ed);

  hol = [];
  ## New Year's Day
  tmphol = datenum (yrs, 1, 1);
  hol = [hol; tmphol(:)];
  ## Martin Luther King Day, the third Monday in January
  tmphol = nweekdate (3, 2, yrs, 1);
  hol = [hol; tmphol(:)];
  ## Washington's Birthday, the third Monday in February
  tmphol = nweekdate (3, 2, yrs, 2);
  hol = [hol; tmphol(:)];
  ## Good Friday
  tmphol = easter (yrs) - 2;
  hol = [hol; tmphol(:)];
  ## Memorial Day, the last Monday in May
  tmphol = lweekdate (2, yrs, 5);
  hol = [hol; tmphol(:)];
  ## Independence Day, July 4
  tmphol = datenum (yrs, 7, 4);
  hol = [hol; tmphol(:)];
  ## Labor Day, the first Monday in September
  tmphol = nweekdate (1, 2, yrs, 9);
  hol = [hol; tmphol(:)];
  ## Thanksgiving Day, the fourth Thursday in November
  tmphol = nweekdate (4, 5, yrs, 11);
  hol = [hol; tmphol(:)];
  ## Christmas Day
  tmphol = datenum (yrs, 12, 25);
  hol = [hol; tmphol(:)];

  ## Adjust for Saturdays and Sundays
  wd = weekday (hol);
  if any (wd == 1)
    hol(wd == 1) = hol(wd == 1) + 1;
  endif
  if any (wd == 7)
    hol(wd == 7) = hol(wd == 7) - 1;
  endif

  ## Trim out the days that are not in the date range
  hol(hol > ed | hol < sd) = [];
  hol = sort (hol);

endfunction

## Tests
%!assert(holidays(datenum(2008,1,1), datenum(2008,12,31)), datenum(2008*ones(9,1), [1;1;2;3;5;7;9;11;12], [1;21;18;21;26;4;1;27;25]))
## Test Independence day observing on a Monday (July 5) and Christmas
## observing on a Friday (Dec 24)
%!assert(holidays(datenum(2004,1,1), datenum(2004,12,31)), datenum(2004*ones(9,1), [1;1;2;4;5;7;9;11;12], [1;19;16;9;31;5;6;25;24]))
%!assert(holidays(datenum(2008,3,5), datenum(2008,3,8)), zeros(0,1))
%!assert(holidays(datenum(2008,3,5), datenum(2008,3,5)), zeros(0,1))
%!assert(holidays(datenum(2008,1,1), datenum(2008,1,1)), datenum(2008,1,1))
