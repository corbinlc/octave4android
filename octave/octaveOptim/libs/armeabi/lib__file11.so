## Copyright (C) 2011 Fernando Damian Nieuwveldt <fdnieuwveldt@gmail.com>
## 2012 Adapted by Juan Pablo Carbajal <carbajal@ifi.uzh.ch>
##
## This program is free software; you can redistribute it and/or
## modify it under the terms of the GNU General Public License
## as published by the Free Software Foundation; either version 3
## of the License, or (at your option) any later version.
##
## This program is distributed in the hope that it will be useful,
## MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
## GNU General Public License for more details.

## -*- texinfo -*-
## @deftypefn {Function File} {} cauchy (@var{N}, @var{r}, @var{x}, @var{f} )
## Return the Taylor coefficients and numerical differentiation of a function
## @var{f} for the first @var{N-1} coefficients or derivatives using the fft.
## @var{N} is the number of points to evaluate,
## @var{r} is the radius of convergence, needs to be chosen less then the smallest singularity,
## @var{x} is point to evaluate the Taylor expansion or differentiation. For example,
##
## If @var{x} is a scalar, the function @var{f} is evaluated in a row vector
## of length @var{N}. If @var{x} is a column vector, @var{f} is evaluated in a
## matrix of length(x)-by-N elements and must return a matrix of the same size.
##
## @example
## @group
## d = cauchy(16, 1.5, 0, @@(x) exp(x));
## @result{} d(2) = 1.0000 # first (2-1) derivative of function f (index starts from zero)
## @end group
## @end example
## @end deftypefn

function deriv = cauchy(N, r, x, f)

  if nargin != 4
    print_usage ();
  end

  [nx m]   = size (x);
  if m > 1
    error('cauchy:InvalidArgument', 'The 3rd argument must be a column vector');
  end

  n        = 0:N-1;
  th       = 2*pi*n/N;

  f_p = f (bsxfun (@plus, x, r * exp (i * th) ) );

  evalfft  = real(fft (f_p, [], 2));

  deriv    = bsxfun (@times, evalfft, 1./(N*(r.^n)).* factorial(n)) ;

endfunction

function g = hermite(order,x)
   ## N should be bigger than order+1
   N      = 32;
   r      = 0.5;
   Hnx    = @(t) exp ( bsxfun (@minus, kron(t(:).', x(:)) , t(:).'.^2/2) );
   Hnxfft = cauchy(N, r, 0, Hnx);
   g      = Hnxfft(:, order+1);
endfunction

%!demo
%! # Cauchy integral formula: Application to Hermite polynomials
%! # Author: Fernando Damian Nieuwveldt
%! # Edited by: Juan Pablo Carbajal
%!
%! Hnx     = @(t,x) exp ( bsxfun (@minus, kron(t(:).', x(:)) , t(:).'.^2/2) );
%! hermite = @(order,x) cauchy(32, 0.5, 0, @(t)Hnx(t,x))(:, order+1);
%!
%! t    = linspace(-1,1,30);
%! he2  = hermite(2,t);
%! he2_ = t.^2-1;
%!
%! figure(1)
%! clf
%! plot(t,he2,'bo;Contour integral representation;', t,he2_,'r;Exact;');
%! grid
%! clear all
%!
%! % --------------------------------------------------------------------------
%! % The plots compares the approximation of the Hermite polynomial using the
%! % Cauchy integral (circles) and the corresposind polynomial H_2(x) = x.^2 - 1.
%! % See http://en.wikipedia.org/wiki/Hermite_polynomials#Contour_integral_representation

%!demo
%! # Cauchy integral formula: Application to Hermite polynomials
%! # Author: Fernando Damian Nieuwveldt
%! # Edited by: Juan Pablo Carbajal
%!
%!  xx = sort (rand (100,1));
%!  yy = sin (3*2*pi*xx);
%!
%!  # Exact first derivative derivative
%!  diffy = 6*pi*cos (3*2*pi*xx);
%!
%!  np = [10 15 30 100];
%!
%!  for i =1:4
%!    idx = sort(randperm (100,np(i)));
%!    x   = xx(idx);
%!    y   = yy(idx);
%!
%!    p     = spline (x,y);
%!    yval  = ppval (ppder(p),x);
%!    # Use the cauchy formula for computing the derivatives
%!    deriv =  cauchy (fix (np(i)/4), .1, x, @(x) sin (3*2*pi*x));
%!
%!    subplot(2,2,i)
%!    h = plot(xx,diffy,'-b;Exact;',...
%!             x,yval,'-or;ppder solution;',...
%!             x,deriv(:,2),'-og;Cauchy formula;');
%!    set(h(1),'linewidth',2);
%!    set(h(2:3),'markersize',3);
%!
%!    legend(h, 'Location','Northoutside','Orientation','horizontal');
%!    if i!=1
%!      legend('hide');
%!    end
%!  end
%!
%! % --------------------------------------------------------------------------
%! % The plots compares the derivatives calculated with Cauchy and with ppder.
%! % Each subplot shows the results with increasing number of samples.
