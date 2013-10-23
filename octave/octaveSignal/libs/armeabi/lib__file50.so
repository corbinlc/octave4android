## Copyright (C) 2004 David Billinghurst <David.Billinghurst@riotinto.com>
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

## Set initial condition vector for filter function
## The vector zf has the same values that would be obtained 
## from function filter given past inputs x and outputs y
##
## The vectors x and y contain the most recent inputs and outputs
## respectively, with the newest values first:
##
## x = [x(-1) x(-2) ... x(-nb)], nb = length(b)-1
## y = [y(-1) y(-2) ... y(-na)], na = length(a)-a
##
## If length(x)<nb then it is zero padded
## If length(y)<na then it is zero padded
##
## zf = filtic(b, a, y)
##    Initial conditions for filter with coefficients a and b
##    and output vector y, assuming input vector x is zero
##
## zf = filtic(b, a, y, x)
##    Initial conditions for filter with coefficients a and b
##    input vector x and output vector y

function zf = filtic(b,a,y,x)

  if (nargin>4 || nargin<3) || (nargout>1)
    print_usage;
  endif
  if nargin < 4, x = []; endif

  nz = max(length(a)-1,length(b)-1);
  zf=zeros(nz,1);

  # Pad arrays a and b to length nz+1 if required
  if length(a)<(nz+1)
     a(length(a)+1:nz+1)=0;
  endif
  if length(b)<(nz+1)
     b(length(b)+1:nz+1)=0;
  endif
  # Pad arrays x and y to length nz if required
  if length(x) < nz
     x(length(x)+1:nz)=0;
  endif
  if length(y) < nz
     y(length(y)+1:nz)=0;
  endif

  for i=nz:-1:1
    for j=i:nz-1
      zf(j) = b(j+1)*x(i) - a(j+1)*y(i)+zf(j+1);
    endfor
    zf(nz)=b(nz+1)*x(i)-a(nz+1)*y(i);
  endfor

endfunction

%!test
%! ## Simple low pass filter
%! b=[0.25 0.25];
%! a=[1.0 -0.5];
%! zf_ref=0.75;
%! zf=filtic(b,a,[1.0],[1.0]);
%! assert(zf,zf_ref,8*eps);
%!
%!test
%! ## Simple high pass filter
%! b=[0.25 -0.25]; 
%! a=[1.0 0.5];
%! zf_ref = [-0.25];
%! zf=filtic(b,a,[0.0],[1.0]);
%! assert(zf,zf_ref,8*eps);
%!
%!test
%! ## Second order cases
%! [b,a]=butter(2,0.4); 
%! N=1000; ## Long enough for filter to settle
%! xx=ones(1,N); 
%! [yy,zf_ref] = filter(b,a,xx);
%! x=xx(N:-1:N-1);
%! y=yy(N:-1:N-1);
%! zf = filtic(b,a,y,x);
%! assert(zf,zf_ref,8*eps);
%!
%! xx = cos(2*pi*linspace(0,N-1,N)/8);
%! [yy,zf_ref] = filter(b,a,xx);
%! x=xx(N:-1:N-1);
%! y=yy(N:-1:N-1);
%! zf = filtic(b,a,y,x);
%! assert(zf,zf_ref,8*eps);
%!
%!test
%! ## Third order filter - takes longer to settle
%! N=10000;
%! [b,a]=cheby1(3,10,0.5);
%! xx=ones(1,N);
%! [yy,zf_ref] = filter(b,a,xx);
%! x=xx(N:-1:N-2);
%! y=yy(N:-1:N-2);
%! zf = filtic(b,a,y,x);
%! assert(zf,zf_ref,8*eps);
%!
%!test
%! ## Eight order high pass filter
%! N=10000;
%! [b,a]=butter(8,0.2);
%! xx = cos(2*pi*linspace(0,N-1,N)/8);
%! [yy,zf_ref] = filter(b,a,xx);
%! x=xx(N:-1:N-7);
%! y=yy(N:-1:N-7);
%! zf = filtic(b,a,y,x);
%! assert(zf,zf_ref,8*eps);
%!
%!test
%! ## Case with 3 args
%! [b,a]=butter(2,0.4);
%! N=100;
%! xx=[ones(1,N) zeros(1,2)];
%! [yy,zf_ref] = filter(b,a,xx);
%! y=[yy(N+2) yy(N+1)];
%! zf=filtic(b,a,y);
%! assert(zf,zf_ref,8*eps);

