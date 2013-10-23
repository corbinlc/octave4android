## Copyright (C) 2000 Gert Van den Eynde <na.gvandeneynde@na-net.ornl.gov>
## Copyright (C) 2002 Rolf Fabian <fabian@tu-cottbus.de>
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

## USAGE  [alpha,c,rms] = expfit( deg, x1, h, y )
##
## Prony's method for non-linear exponential fitting
##
## Fit function:   \sum_1^{deg} c(i)*exp(alpha(i)*x)
##
## Elements of data vector y must correspond to
## equidistant x-values starting at x1 with stepsize h
##
## The method is fully compatible with complex linear
## coefficients c, complex nonlinear coefficients alpha
## and complex input arguments y, x1, non-zero h .
## Fit-order deg  must be a real positive integer.
##
## Returns linear coefficients c, nonlinear coefficients
## alpha and root mean square error rms. This method is
## known to be more stable than 'brute-force' non-linear
## least squares fitting.
##
## Example
##    x0 = 0; step = 0.05; xend = 5; x = x0:step:xend;
##    y = 2*exp(1.3*x)-0.5*exp(2*x);
##    error = (rand(1,length(y))-0.5)*1e-4;
##    [alpha,c,rms] = expfit(2,x0,step,y+error)
##
##  alpha =
##    2.0000
##    1.3000
##  c =
##    -0.50000
##     2.00000
##  rms = 0.00028461
##
## The fit is very sensitive to the number of data points.
## It doesn't perform very well for small data sets.
## Theoretically, you need at least 2*deg data points, but
## if there are errors on the data, you certainly need more.
##
## Be aware that this is a very (very,very) ill-posed problem.
## By the way, this algorithm relies heavily on computing the
## roots of a polynomial. I used 'roots.m', if there is
## something better please use that code.
##
## Demo for a complex fit-function:
## deg= 2; N= 20; x1= -(1+i), x= linspace(x1,1+i/2,N).';
## h = x(2) - x(1)
## y= (2+i)*exp( (-1-2i)*x ) + (-1+3i)*exp( (2+3i)*x );
## A= 5e-2; y+= A*(randn(N,1)+randn(N,1)*i); % add complex noise
## [alpha,c,rms]= expfit( deg, x1, h, y )

function [a,c,rms] = expfit(deg,x1,h,y)

% Check input
if deg<1, error('expfit: deg must be >= 1');     end
if  ~h,   error('expfit: vanishing stepsize h'); end

if ( N=length( y=y(:) ) ) < 2*deg
   error('expfit: less than %d samples',2*deg);
end

% Solve for polynomial coefficients
A = hankel( y(1:N-deg),y(N-deg:N) );
s = - A(:,1:deg) \ A(:,deg+1);

% Compose polynomial
p = flipud([s;1]);

% Compute roots
u = roots(p);

% nonlinear coefficients
a = log(u)/h;

% Compose second matrix A(i,j) = u(j)^(i-1)
A = ( ones(N,1) * u(:).' ) .^ ( [0:N-1]' * ones(1,deg) );

% Solve linear system
f = A\y;

% linear coefficients
c = f./exp( a*x1 );

% Compute rms of y - approx
% where approx(i) = sum_k ( c(k) * exp(x(i)*a(k)) )
if nargout > 2
   x = x1+h*[0:N-1];
   approx = exp( x(:) * a(:).' ) * c(:);
   rms = sqrt( sumsq(approx - y) );
end

endfunction

%!demo	 % same as in help - part
%! deg= 2; N= 20; x1= -(1+i), x= linspace(x1,1+i/2,N).';
%! h = x(2) - x(1)
%! y= (2+i)*exp( (-1-2i)*x ) + (-1+3i)*exp( (2+3i)*x );
%! A= 5e-2; y+= A*(randn(N,1)+randn(N,1)*i); % add complex noise
%! [alpha,c,rms]= expfit( deg, x1, h, y )

%!demo	 % demo for stepsize with negative real part
%! deg= 2; N= 20; x1= +3*(1+i), x= linspace(x1,1+i/3,N).';
%! h = x(2) - x(1)
%! y= (2+i)*exp( (-1-2i)*x ) + (-1+3i)*exp( (2+3i)*x );
%! A= 5e-2; y+= A*(randn(N,1)+randn(N,1)*i); % add complex noise
%! [alpha,c,rms]= expfit( deg, x1, h, y )

%!demo
%! x0 = 1.5; step = 0.05; xend = 5;
%! a = [1.3, 2]';
%! c = [2, -0.5]';
%! v = 1e-4;
%! 
%! x = x0:step:xend;
%! y = exp (x(:) * a(:).') * c(:);
%! err = randn (size (y)) * v;
%! plot (x, y + err);
%! 
%! [a_out, c_out, rms] = expfit (2, x0, step, y+err)


