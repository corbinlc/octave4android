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
## @deftypefn {Function File} {@var{y} =} Ci (@var{z})
## Compute the cosine integral function defined by:
## @verbatim
##                    Inf
##                   /
##           Ci(x) = | cos(t)/t dt
##                   /
##                   x
## @end verbatim
## @seealso{cosint, Si, sinint, expint, expint_Ei}
## @end deftypefn

function y = Ci(z)
  if (nargin != 1)
    print_usage;
  endif
  y = z;
  y(z == 0) = -Inf; 
  y(real(z) == 0 & imag(z) >0)  = 0.5*(expint_Ei(i.*y(real(z) == 0 & imag(z) >0))+expint_Ei(-i.*y(real(z) == 0 & imag(z) >0)))+ i.*pi./2;
  y(real(z) == 0 & imag(z) <0) = 0.5*(expint_Ei(i.*y(real(z) == 0 & imag(z) <0))+expint_Ei(-i.*y(real(z) == 0 & imag(z) <0)))-i*pi./2;
  y(real(z)>=0) = -0.5.*(expint_E1(i.*y(real(z)>=0) )+expint_E1(-i.*y(real(z)>=0) ));
  y(real(z)<0) = -0.5.*(expint_E1(-i.*y(real(z)<0))+expint_E1(i.*y(real(z)<0)))+i*pi;
endfunction
