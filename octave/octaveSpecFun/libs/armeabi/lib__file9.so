## Copyright (C) 2006   Sylvain Pelissier   <sylvain.pelissier@gmail.com>
##
## This program is free software; you can redistribute it and/or modify
## it under the terms of the GNU General Public License as published by
## the Free Software Foundation; either version 3 of the License, or
## (at your option) any later version.
##
## This program is distributed in the hope that it will be useful,
## but WITHOUT ANY WARRANTY; without even the implied warranty of
## MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
## GNU General Public License for more details.
##
## You should have received a copy of the GNU General Public License
## along with this program; If not, see <http://www.gnu.org/licenses/>.

## -*- texinfo -*-
## @deftypefn {Function File} {} heaviside(@var{x})
## @deftypefnx{Function File} {} heaviside(@var{x}, @var{zero_value})
## Compute the Heaviside step function.
##
## The Heaviside function is defined as
##
## @example
##   Heaviside (@var{x}) = 1,   @var{x} > 0
##   Heaviside (@var{x}) = 0,   @var{x} < 0
## @end example
##
## @noindent
## The value of the Heaviside function at @var{x} = 0 is by default 0.5,
## but can be changed via the optional second input argument.
## @seealso{dirac}
## @end deftypefn

function y = heaviside (x, zero_value = 0.5)
  if (nargin < 1)
    print_usage ();
  endif

  y = cast (x > 0, class (x));
  y (x == 0) = zero_value;
endfunction
