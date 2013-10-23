## Copyright (C) 2008 Arno Onken <asnelt@asnelt.org>
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
## @deftypefn {Function File} {@var{p} =} mvtcdf (@var{x}, @var{sigma}, @var{nu})
## @deftypefnx {Function File} {} mvtcdf (@var{a}, @var{x}, @var{sigma}, @var{nu})
## @deftypefnx {Function File} {[@var{p}, @var{err}] =} mvtcdf (@dots{})
## Compute the cumulative distribution function of the multivariate
## Student's t distribution.
##
## @subheading Arguments
##
## @itemize @bullet
## @item
## @var{x} is the upper limit for integration where each row corresponds
## to an observation.
##
## @item
## @var{sigma} is the correlation matrix.
##
## @item
## @var{nu} is the degrees of freedom.
##
## @item
## @var{a} is the lower limit for integration where each row corresponds
## to an observation. @var{a} must have the same size as @var{x}.
## @end itemize
##
## @subheading Return values
##
## @itemize @bullet
## @item
## @var{p} is the cumulative distribution at each row of @var{x} and
## @var{a}.
##
## @item
## @var{err} is the estimated error.
## @end itemize
##
## @subheading Examples
##
## @example
## @group
## x = [1 2];
## sigma = [1.0 0.5; 0.5 1.0];
## nu = 4;
## p = mvtcdf (x, sigma, nu)
## @end group
##
## @group
## a = [-inf 0];
## p = mvtcdf (a, x, sigma, nu)
## @end group
## @end example
##
## @subheading References
##
## @enumerate
## @item
## Alan Genz and Frank Bretz. Numerical Computation of Multivariate
## t-Probabilities with Application to Power Calculation of Multiple
## Constrasts. @cite{Journal of Statistical Computation and Simulation},
## 63, pages 361-378, 1999.
## @end enumerate
## @end deftypefn

## Author: Arno Onken <asnelt@asnelt.org>
## Description: CDF of the multivariate Student's t distribution

function [p, err] = mvtcdf (varargin)

  # Monte-Carlo confidence factor for the standard error: 99 %
  gamma = 2.5;
  # Tolerance
  err_eps = 1e-3;

  if (length (varargin) == 3)
    x = varargin{1};
    sigma = varargin{2};
    nu = varargin{3};
    a = -Inf .* ones (size (x));
  elseif (length (varargin) == 4)
    a = varargin{1};
    x = varargin{2};
    sigma = varargin{3};
    nu = varargin{4};
  else
    print_usage ();
  endif

  # Dimension
  q = size (sigma, 1);
  cases = size (x, 1);

  # Check parameters
  if (size (x, 2) != q)
    error ("mvtcdf: x must have the same number of columns as sigma");
  endif

  if (any (size (x) != size (a)))
    error ("mvtcdf: a must have the same size as x");
  endif

  if (! isscalar (nu) && (! isvector (nu) || length (nu) != cases))
    error ("mvtcdf: nu must be a scalar or a vector with the same number of rows as x");
  endif

  # Convert to correlation matrix if necessary
  if (any (diag (sigma) != 1))
    svar = repmat (diag (sigma), 1, q);
    sigma = sigma ./ sqrt (svar .* svar');
  endif
  if (q < 1 || size (sigma, 2) != q || any (any (sigma != sigma')) || min (eig (sigma)) <= 0)
    error ("mvtcdf: sigma must be nonempty symmetric positive definite");
  endif

  nu = nu(:);
  c = chol (sigma)';

  # Number of integral transformations
  n = 1;

  p = zeros (cases, 1);
  varsum = zeros (cases, 1);

  err = ones (cases, 1) .* err_eps;
  # Apply crude Monte-Carlo estimation
  while any (err >= err_eps)
    # Sample from q-1 dimensional unit hypercube
    w = rand (cases, q - 1);

    # Transformation of the multivariate t-integral
    dvev = tcdf ([a(:, 1) / c(1, 1), x(:, 1) / c(1, 1)], nu);
    dv = dvev(:, 1);
    ev = dvev(:, 2);
    fv = ev - dv;
    y = zeros (cases, q - 1);
    for i = 1:(q - 1)
      y(:, i) = tinv (dv + w(:, i) .* (ev - dv), nu + i - 1) .* sqrt ((nu + sum (y(:, 1:(i-1)) .^ 2, 2)) ./ (nu + i - 1));
      tf = (sqrt ((nu + i) ./ (nu + sum (y(:, 1:i) .^ 2, 2)))) ./ c(i + 1, i + 1);
      dvev = tcdf ([(a(:, i + 1) - c(i + 1, 1:i) .* y(:, 1:i)) .* tf, (x(:, i + 1) - c(i + 1, 1:i) .* y(:, 1:i)) .* tf], nu + i);
      dv = dvev(:, 1);
      ev = dvev(:, 2);
      fv = (ev - dv) .* fv;
    endfor

    n++;
    # Estimate standard error
    varsum += (n - 1) .* ((fv - p) .^ 2) ./ n;
    err = gamma .* sqrt (varsum ./ (n .* (n - 1)));
    p += (fv - p) ./ n;
  endwhile

endfunction
