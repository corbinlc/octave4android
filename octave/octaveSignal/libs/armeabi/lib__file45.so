## Copyright (C) 2008 Sylvain Pelissier <sylvain.pelissier@gmail.com>
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
## @deftypefn {Function File} {[@var{ca} @var{cd}] =} dwt(@var{x,lo_d,hi_d})
## Comupte de discrete wavelet transform of x with one level.
## @end deftypefn

function [ca cd] = dwt(x,lo_d,hi_d)
  if (nargin < 3|| nargin > 3)
    print_usage;
  elseif(~isvector(x)  || ~isvector(lo_d) || ~isvector(hi_d))
    error('x, hi_d and lo_d must be vectors');
  end

  h = filter(hi_d,1,x);
  g = filter(lo_d,1,x);

  cd = downsample(h,2);
  ca = downsample(g,2);
endfunction
