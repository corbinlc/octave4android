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
## @deftypefn {Function File} {[@var{psi,x}] =} mexihat(@var{lb,ub,n})
## Compute the Mexican hat wavelet.
## @end deftypefn

function [psi,x] = mexihat(lb,ub,n)
  if (nargin < 3); print_usage; end

  if (n <= 0)
    error("n must be strictly positive");
  endif
  x = linspace(lb,ub,n);
  psi = (1-x.^2).*(2/(sqrt(3)*pi^0.25)) .* exp(-x.^2/2)  ;
endfunction
