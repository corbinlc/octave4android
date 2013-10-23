## Copyright (C) 2008 Muthiah Annamalai <muthiah.annamalai@uta.edu>
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
## @deftypefn{Function File} {m = } fht ( d, n, dim )
## @cindex linear algebra 
##  The function fht calculates  Fast Hartley Transform
##  where @var{d} is the real input vector (matrix), and @var{m}
## is the real-transform vector. For matrices the hartley transform
## is calculated along the columns by default. The options
## @var{n},and @var{dim} are similar to the options of FFT function.
## 
## The forward and inverse hartley transforms are the same (except for a
## scale factor of 1/N for the inverse hartley transform), but
## implemented using different functions .
##
## The definition of the forward hartley transform for vector d,
## @math{
## m[K] = \sum_{i=0}^{N-1} d[i]*(cos[K*2*pi*i/N] + sin[K*2*pi*i/N]), for  0 <= K < N.
## m[K] = \sum_{i=0}^{N-1} d[i]*CAS[K*i], for  0 <= K < N. }
## 
## @example
## fht(1:4)
## @end example
## @seealso{ifht,fft}
## @end deftypefn

function m = fht( d, n, dim )
  
  if ( nargin < 1 )
    print_usage();
  end

  if ( nargin == 3 )
    Y = fft(d,n,dim);
  elseif ( nargin == 2 )
    Y = fft(d,n);
  else 
    Y = fft(d);
  end
  
  m = real(Y) - imag(Y);

#   -- Traditional --
#   N = length(d);
#   for K = 1:N
#     i = 0:N-1;
#     t = 2*pi*(K-1).*i/N;
#     ker = (cos(t) + sin(t));
#     val = dot(d,ker);
#     m(K) = val;
#   end

end
%!
%!assert( fht([1 2 3 4]),[10 -4 -2 0] )
%!
