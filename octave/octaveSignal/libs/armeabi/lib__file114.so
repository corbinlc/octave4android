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
## @deftypefn {Function File} {[@var{psi,x}] =} shanwavf (@var{lb,ub,n,fb,fc})
## Compute the Complex Shannon wavelet.
## @end deftypefn

function [psi,x] = shanwavf (lb,ub,n,fb,fc)
  if (nargin < 5)
    print_usage;
  elseif (n <= 0 || floor(n) ~= n)
    error("n must be an integer strictly positive");
  elseif (fc <= 0 || fb <= 0)
    error("fc and fb must be strictly positive");
  endif

  x = linspace(lb,ub,n);
  psi = (fb.^0.5).*(sinc(fb.*x).*exp(2.*i.*pi.*fc.*x));
endfunction
