## Copyright (C) 2007 Juan Aguado
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
## @deftypefn {Function File} {[@var{y}] =} sawtooth(@var{t})
## @deftypefnx {Function File} {[@var{y}] =} sawtooth(@var{t},@var{width})
## Generates a sawtooth wave of period @code{2 * pi} with limits @code{+1/-1}
##  for the elements of @var{t}.
##
## @var{width} is a real number between @code{0} and @code{1} which specifies
## the point between @code{0} and @code{2 * pi} where the maximum is. The
## function increases linearly from @code{-1} to @code{1} in  @code{[0, 2 * 
## pi * @var{width}]} interval, and decreases linearly from @code{1} to 
## @code{-1} in the interval @code{[2 * pi * @var{width}, 2 * pi]}.
##
## If @var{width} is 0.5, the function generates a standard triangular wave.
##
## If @var{width} is not specified, it takes a value of 1, which is a standard
## sawtooth function.
## @end deftypefn

function y = sawtooth (t,width)

  if (nargin < 1 || nargin > 2)
    print_usage ();
  endif

  if (nargin == 1)
    width = 1;
  else
    if (width < 0 || width > 1 || ! isreal (width))
      error ("width must be a real number between 0 and 1.");
    endif
  endif

  t = mod (t / (2 * pi), 1);
  y = zeros (size (t));

  if (width != 0)
    y (t < width) = 2 * t (t < width) / width - 1;
  endif

  if (width != 1)
    y( t >= width) = -2 * (t (t >= width) - width) / (1 - width) + 1;
  endif

endfunction
