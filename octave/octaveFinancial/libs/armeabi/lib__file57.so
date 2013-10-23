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
## @deftypefn {Function File} {@var{d} =} yeardays (@var{y})
## @deftypefnx {Function File} {@var{d} =} yeardays (@var{y}, @var{b})
## Return the number of days in the year @var{y} with an optional basis
## @var{b}.
##
## Valid bases
## @itemize @bullet
## @item 0
##   actual/actual (default)
## @item 1
##   30/360 (SIA)
## @item 2
##   actual/360
## @item 3
##   actual/365
## @item 4
##   30/360 (PSA)
## @item 5
##   30/360 (IDSA)
## @item 6
##   30/360 (European)
## @item 7
##   actual/365 (Japanese)
## @item 8
##   actual/actual (ISMA)
## @item 9
##   actual/360 (ISMA)
## @item 10
##   actual/365 (ISMA)
## @item 11
##   30/360E (ISMA)
## @end itemize
## @seealso{days365, days360, daysact, daysdif}
## @end deftypefn

function d = yeardays (y, basis)

  if (nargin == 1)
	basis = 0;
  elseif (nargin != 2)
    print_usage ();
  endif

  if isscalar (y)
	d = zeros (size (basis));
  elseif isscalar (basis)
	## the rest of the code is much simpler if you can be sure that
	## basis is a matrix if y is a matrix
	basis = basis * ones (size (y));
	d = zeros (size (y));
  else
	if ndims (y) == ndims (basis)
	  if ~ all (size (y) == size (basis))
		error ("year and basis must be the same size or one must be a scalar");
	  else
		d = zeros (size (y));
	  endif
	else
	  error ("year and basis must be the same size or one must be a scalar.")
	endif
  endif

  bact = ismember (basis(:), [0 8]);
  b360 = ismember (basis(:), [1 2 4 5 6 9 11]);
  b365 = ismember (basis(:), [3 7 10]);

  badbasismask = ~ (bact | b360 | b365);
  if any (badbasismask)
	badbasis = unique (basis(badbasismask));
	error ("Unsupported basis: %g\n", badbasis)
  endif

  d(bact) = 365 + (eomday(y(bact), 2) == 29);
  d(b360) = 360;
  d(b365) = 365;

endfunction

## Tests
%!assert(yeardays(2000), 366)
%!assert(yeardays(2001), 365)
%!assert(yeardays(2000:2004), [366 365 365 365 366])
%!assert(yeardays(2000, 0), 366)
%!assert(yeardays(2000, 1), 360)
%!assert(yeardays(2000, 2), 360)
%!assert(yeardays(2000, 3), 365)
%!assert(yeardays(2000, 4), 360)
%!assert(yeardays(2000, 5), 360)
%!assert(yeardays(2000, 6), 360)
%!assert(yeardays(2000, 7), 365)
%!assert(yeardays(2000, 8), 366)
%!assert(yeardays(2000, 9), 360)
%!assert(yeardays(2000, 10), 365)
%!assert(yeardays(2000, 11), 360)

