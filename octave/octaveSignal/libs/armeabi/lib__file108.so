## Copyright (C) 2009 Muthiah Annamalai <muthiah.annamalai@uta.edu>
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

## Usage:
## 
## xt = sampled2continuous( xn , T, t )
## 
## Calculate the x(t) reconstructed
## from samples x[n] sampled at a rate 1/T samples
## per unit time.
## 
## t is all the instants of time when you need x(t) 
## from x[n]; this time is relative to x[0] and not
## an absolute time.
## 
## This function can be used to calculate sampling rate
## effects on aliasing, actual signal reconstruction
## from discrete samples.

function xt = sampled2continuous( xn , T, t )
  if ( nargin < 3 )
    print_usage()
  endif
  
  N = length( xn );
  xn = reshape( xn, N, 1 );
  [TT,tt]= meshgrid(T*(0:N-1)',t);
  S = sinc((tt -TT)./T);
  xt = S*xn;
  return
end
