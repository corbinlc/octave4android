## Copyright (C) 2002 André Carezia <acarezia@uol.com.br>
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

## Usage:  cheb (n, x)
##
## Returns the value of the nth-order Chebyshev polynomial calculated at
## the point x. The Chebyshev polynomials are defined by the equations:
##
##           / cos(n acos(x),    |x| <= 1
##   Tn(x) = |
##           \ cosh(n acosh(x),  |x| > 1
##
## If x is a vector, the output is a vector of the same size, where each
## element is calculated as y(i) = Tn(x(i)).

function T = cheb (n, x)
  if (nargin != 2)
    print_usage;
  elseif !(isscalar (n) && (n == round(n)) && (n >= 0))
    error ("cheb: n has to be a positive integer");
  endif

  if (max(size(x)) == 0)
    T = [];
  endif
        # avoid resizing latencies
  T = zeros(size(x));
  ind = abs (x) <= 1;
  if (max(size(ind)))
    T(ind) = cos(n*acos(x(ind)));
  endif

  ind = abs (x) > 1;
  if (max(size(ind)))
    T(ind) = cosh(n*acosh(x(ind)));
  endif

  T = real(T);
endfunction
