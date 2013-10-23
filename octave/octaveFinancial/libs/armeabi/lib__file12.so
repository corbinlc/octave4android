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
## @deftypefn {Function File} {[m, d] =} easter (y)
## @deftypefnx {Function File} {datenum =} easter (y)
##
## Return the month (@var{m}) and day (@var{d}) of Easter in the
## Gregorial calendar on a given year or years.
##
## @seealso{holidays}
## @end deftypefn

function varargout = easter (y)

  ## This uses the Meesus/Jones/Butcher Gregorian algorithm as described
  ## on http://en.wikipedia.org/wiki/Computus#Algorithms
  a = mod (y, 19);
  b = floor (y/100);
  c = mod (y, 100);
  d = floor (b/4);
  e = mod (b, 4);
  f = floor ((b + 8)/25);
  g = floor ((b - f + 1)/3);
  h = mod ((19*a+b-d-g+15), 30);
  i = floor (c/4);
  k = mod (c, 4);
  L = mod ((32 + 2*e + 2*i - h - k), 7);
  m = floor ((a + 11*h + 22*L)/451);
  mon = floor ((h + L - 7*m + 114)/31);
  day = 1 + mod ((h + L - 7*m + 114), 31);

  if nargout == 2
	varargout = {mon(:), day(:)};
  else
	varargout{1} = reshape (datenum (y(:), mon(:), day(:)), size (y));
  end

endfunction

## Tests
## Validate that it calculates the correct date for a decade
%!assert(easter(1990), datenum(1990, 4, 15))
%!assert(easter(1991), datenum(1991, 3, 31))
%!assert(easter(1992), datenum(1992, 4, 19))
%!assert(easter(1993), datenum(1993, 4, 11))
%!assert(easter(1994), datenum(1994, 4, 3))
%!assert(easter(1995), datenum(1995, 4, 16))
%!assert(easter(1996), datenum(1996, 4, 7))
%!assert(easter(1997), datenum(1997, 3, 30))
%!assert(easter(1998), datenum(1998, 4, 12))
%!assert(easter(1999), datenum(1999, 4, 4))
## Validate vector and matrix inputs
%!assert(easter([2000 2001]), [datenum(2000, 4, 23) datenum(2001, 4, 15)])
%!assert(easter([2002;2003]), [datenum(2002, 3, 31);datenum(2003, 4, 20)])
%!assert(easter([2004 2005;2006 2007;2008 2009]), [datenum(2004, 4, 11) datenum(2005, 3, 27);datenum(2006, 4, 16) datenum(2007, 4, 8);datenum(2008, 3, 23) datenum(2009, 4, 12)])
%!assert(easter([2002;2003]), [datenum(2002, 3, 31);datenum(2003, 4, 20)])
