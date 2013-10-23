# Copyright (C) 2006   Sylvain Pelissier   <sylvain.pelissier@gmail.com>
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
## @deftypefn {Function File} {@var{y} = } psi (@var{x})
## Compute the psi function, for each value of @var{x}.
##
## @verbatim
##             d
##    psi(x) = __ log(gamma(x))
##             dx
## @end verbatim
##
## @seealso{gamma, gammainc, gammaln}
## @end deftypefn

function [y] = psi (x)

  if (nargin != 1)
    print_usage;
  elseif (imag(x) != zeros(size(x)))
    error("unable to handle complex arguments");
  endif

  h = 1e-9;
  y = x;
  y(x == 0) = -Inf;
  y(x>0) = (gammaln(y(x>0)+h)-gammaln(y(x>0)-h))./(2.*h);
  y(x<0) = (gammaln((1-y(x<0))+h)-gammaln((1-y(x<0))-h))./(2.*h) + pi.*cot(pi.*(1-y(x<0)));

endfunction
