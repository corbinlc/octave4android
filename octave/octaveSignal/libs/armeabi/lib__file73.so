## Copyright (C) 2000 Paul Kienzle <pkienzle@users.sf.net>
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

## usage: y = interp(x, q [, n [, Wc]])
##
## Upsample the signal x by a factor of q, using an order 2*q*n+1 FIR
## filter. Note that q must be an integer for this rate change method.
## n defaults to 4 and Wc defaults to 0.5.
##
## Example
##                                          # Generate a signal.
##    t=0:0.01:2; x=chirp(t,2,.5,10,'quadratic')+sin(2*pi*t*0.4); 
##    y = interp(x(1:4:length(x)),4,4,1);   # interpolate a sub-sample
##    stem(t(1:121)*1000,x(1:121),"-g;Original;"); hold on;
##    stem(t(1:121)*1000,y(1:121),"-r;Interpolated;");
##    stem(t(1:4:121)*1000,x(1:4:121),"-b;Subsampled;"); hold off;
##
## See also: decimate, resample

function y = interp(x, q, n = 4, Wc = 0.5)

  if nargin < 1 || nargin > 4, 
    print_usage;
  endif
  if q != fix(q), error("decimate only works with integer q."); endif

  if rows(x)>1
    y = zeros(length(x)*q+q*n+1,1);
  else
    y = zeros(1,length(x)*q+q*n+1);
  endif
  y(1:q:length(x)*q) = x;
  b = fir1(2*q*n+1, Wc/q);
  y=q*fftfilt(b, y);
  y(1:q*n+1) = [];  # adjust for zero filter delay
endfunction

%!demo
%! ## Generate a signal.
%! t=0:0.01:2; x=chirp(t,2,.5,10,'quadratic')+sin(2*pi*t*0.4); 
%! y = interp(x(1:4:length(x)),4,4,1);   # interpolate a sub-sample
%! plot(t(1:121)*1000,y(1:121),"r-+;Interpolated;"); hold on;
%! stem(t(1:4:121)*1000,x(1:4:121),"ob;Original;"); hold off;
%!
%! % graph shows interpolated signal following through the
%! % sample points of the original signal.
