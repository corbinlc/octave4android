## Copyright (C) 2007 Sylvain Pelissier <sylvain.pelissier@gmail.com>
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
## @deftypefn {Function File} {[@var{y}] =} diric(@var{x},@var{n})
## Compute the dirichlet function.
## @seealso{sinc,  gauspuls, sawtooth}
## @end deftypefn

function [y] = diric(x,n)
  if (nargin < 2)
    print_usage;
  elseif (n <= 0 || floor(n) ~= n)
    error("n must be an integer strictly positive");
  endif

  y = sin(n.*x./2)./(n.*sin(x./2));
  y(mod(x,2*pi)==0) = (-1).^((n-1).*x(mod(x,2*pi)==0)./(2.*pi));

endfunction
