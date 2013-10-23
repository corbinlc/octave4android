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
## @deftypefn {Function File} {@var{e} =} eomdate (@var{y}, @var{m})
## Return the last day of the month @var{m} for the year @var{y} in
## datenum format.
## @seealso{datenum, datevec, weekday, eomday}
## @end deftypefn

function e = eomdate (y, m)

  if (nargin != 2)
    print_usage ();
  endif

  d = eomday (y, m);
  e = datenum (y, m, d);

endfunction

## Tests
## Leap years
%!assert(eomdate(2008, 2), datenum(2008, 2, 29))
%!assert(eomdate(2007, 2), datenum(2007, 2, 28))
## Vectors
%!assert(eomdate([2008 2007], [3 4]), [datenum(2008, 3, 31) datenum(2007, 4, 30)])
%!assert(eomdate([2008;2007], [3;4]), [datenum(2008, 3, 31);datenum(2007, 4, 30)])
