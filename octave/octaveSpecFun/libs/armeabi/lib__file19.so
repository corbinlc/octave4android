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
## @deftypefn {Function File} {@var{z} =} zeta (@var{t})
## Compute the Riemann's Zeta function.
## @seealso{Si}
## @end deftypefn

function z = zeta(t)
  if (nargin != 1)
    print_usage;
  endif
  z = zeros(size(t));
  for j = 1:prod(size(t))
    if(real(t(j)) >= 0)
      if(imag(t(j)) == 0 && real(t(j)) > 1)
        F= @(x) 1./(gamma(t(j))).*x.^(t(j)-1)./(exp(x)-1);
        z(j) = quad(F,0,Inf);
      elseif(t(j) == 0)
        z(j) = -0.5;
      elseif(t(j) == 1)
        z(j) = Inf;
      else
        for k = 1:100
          z(j) += (-1).^(k-1)./(k.^t(j));
        endfor
        z(j) = 1./(1-2.^(1-t(j))).*z(j);
      endif
    else
      z(j) = 2.^t(j).*pi.^(t(j)-1).*sin(pi.*t(j)./2).*gamma(1-t(j)).*zeta(1-t(j));
    endif
  endfor
endfunction
