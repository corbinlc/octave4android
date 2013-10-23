## Copyright (C) 2011 Nir Krakauer <nkrakauer@ccny.cuny.edu>
## Copyright (C) 2011 Carnë Draug <carandraug+dev@gmail.com>
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
## @deftypefn {Function File} {@var{yy} =} monotone_smooth (@var{x}, @var{y}, @var{h})
## Produce a smooth monotone increasing approximation to a sampled functional
## dependence y(x) using a kernel method (an Epanechnikov smoothing kernel is
## applied to y(x); this is integrated to yield the monotone increasing form.
## See Reference 1 for details.)
##
## @subheading Arguments
##
## @itemize @bullet
## @item
## @var{x} is a vector of values of the independent variable.
##
## @item
## @var{y} is a vector of values of the dependent variable, of the same size as
## @var{x}. For best performance, it is recommended that the @var{y} already be
## fairly smooth, e.g. by applying a kernel smoothing to the original values if
## they are noisy.
##
## @item
## @var{h} is the kernel bandwidth to use. If @var{h} is not given, a "reasonable"
## value is computed.
##
## @end itemize
##
## @subheading Return values
##
## @itemize @bullet
## @item
## @var{yy} is the vector of smooth monotone increasing function values at @var{x}.
##
## @end itemize
##
## @subheading Examples
##
## @example
## @group
## x = 0:0.1:10;
## y = (x .^ 2) + 3 * randn(size(x)); %typically non-monotonic from the added noise
## ys = ([y(1) y(1:(end-1))] + y + [y(2:end) y(end)])/3; %crudely smoothed via
## moving average, but still typically non-monotonic
## yy = monotone_smooth(x, ys); %yy is monotone increasing in x
## plot(x, y, '+', x, ys, x, yy)
## @end group
## @end example
##
## @subheading References
##
## @enumerate
## @item
## Holger Dette, Natalie Neumeyer and Kay F. Pilz (2006), A simple nonparametric
## estimator of a strictly monotone regression function, @cite{Bernoulli}, 12:469-490
## @item
## Regine Scheder (2007), R Package 'monoProc', Version 1.0-6,
## @url{http://cran.r-project.org/web/packages/monoProc/monoProc.pdf} (The
## implementation here is based on the monoProc function mono.1d)
## @end enumerate
## @end deftypefn

## Author: Nir Krakauer <nkrakauer@ccny.cuny.edu>
## Description: Nonparametric monotone increasing regression

function yy = monotone_smooth (x, y, h)

  if (nargin < 2 || nargin > 3)
    print_usage ();
  elseif (!isnumeric (x) || !isvector (x))
    error ("first argument x must be a numeric vector")
  elseif (!isnumeric (y) || !isvector (y))
    error ("second argument y must be a numeric vector")
  elseif (numel (x) != numel (y))
    error ("x and y must have the same number of elements")
  elseif (nargin == 3 && (!isscalar (h) || !isnumeric (h)))
    error ("third argument 'h' (kernel bandwith) must a numeric scalar")
  endif

  n = numel(x);

  %set filter bandwidth at a reasonable default value, if not specified
  if (nargin != 3)
    s = std(x);
    h = s / (n^0.2);
  end

  x_min = min(x);
  x_max = max(x);

  y_min = min(y);
  y_max = max(y);

  %transform range of x to [0, 1]
  xl = (x - x_min) / (x_max - x_min);

  yy = ones(size(y));

  %Epanechnikov smoothing kernel (with finite support)
  %K_epanech_kernel = @(z) (3/4) * ((1 - z).^2) .* (abs(z) < 1);

  K_epanech_int = @(z) mean(((abs(z) < 1)/2) - (3/4) * (z .* (abs(z) < 1) - (1/3) * (z.^3) .* (abs(z) < 1)) + (z < -1));

  %integral of kernels up to t
  monotone_inverse = @(t) K_epanech_int((y - t) / h);

  %find the value of the monotone smooth function at each point in x
  niter_max = 150; %maximum number of iterations for estimating each value (should not be reached in most cases) 
  for l = 1:n

    tmax = y_max;
    tmin = y_min;
    wmin = monotone_inverse(tmin);
    wmax = monotone_inverse(tmax);
    if (wmax == wmin)
      yy(l) = tmin;
    else
      wt = xl(l);
      iter_max_reached = 1;
      for i = 1:niter_max
        wt_scaled = (wt - wmin) / (wmax - wmin);
        tn        = tmin + wt_scaled * (tmax - tmin) ;
        wn        = monotone_inverse(tn);
        wn_scaled = (wn - wmin) / (wmax - wmin);

        %if (abs(wt-wn) < 1E-4) || (tn < (y_min-0.1)) || (tn > (y_max+0.1))
        %% criterion for break in the R code -- replaced by the following line to
        %% hopefully be less dependent on the scale of y
        if (abs(wt_scaled-wn_scaled) < 1E-4) || (wt_scaled < -0.1) || (wt_scaled > 1.1)
          iter_max_reached = 0;
          break
        endif
        if wn > wt
          tmax = tn;
          wmax = wn;
        else
          tmin = tn;
          wmin = wn;
        endif
      endfor
      if iter_max_reached
        warning("at x = %g, maximum number of iterations %d reached without convergence; approximation may not be optimal", x(l), niter_max)
      endif
      yy(l) = tmin + (wt - wmin) * (tmax - tmin) / (wmax - wmin);
    endif
  endfor
endfunction
