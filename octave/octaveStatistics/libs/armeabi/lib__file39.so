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
## @deftypefn {Function File} {@var{p} =} mvncdf (@var{x}, @var{mu}, @var{sigma})
## @deftypefnx {Function File} {} mvncdf (@var{a}, @var{x}, @var{mu}, @var{sigma})
## @deftypefnx {Function File} {[@var{p}, @var{err}] =} mvncdf (@dots{})
## Compute the cumulative distribution function of the multivariate
## normal distribution.
##
## @subheading Arguments
##
## @itemize @bullet
## @item
## @var{x} is the upper limit for integration where each row corresponds
## to an observation.
##
## @item
## @var{mu} is the mean.
##
## @item
## @var{sigma} is the correlation matrix.
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
## mu = [0.5 1.5];
## sigma = [1.0 0.5; 0.5 1.0];
## p = mvncdf (x, mu, sigma)
## @end group
##
## @group
## a = [-inf 0];
## p = mvncdf (a, x, mu, sigma)
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
## Description: CDF of the multivariate normal distribution

function [p, err] = mvncdf (varargin)

  # Monte-Carlo confidence factor for the standard error: 99 %
  gamma = 2.5;
  # Tolerance
  err_eps = 1e-3;

  if (length (varargin) == 1)
    x = varargin{1};
    mu = [];
    sigma = eye (size (x, 2));
    a = -Inf .* ones (size (x));
  elseif (length (varargin) == 3)
    x = varargin{1};
    mu = varargin{2};
    sigma = varargin{3};
    a = -Inf .* ones (size (x));
  elseif (length (varargin) == 4)
    a = varargin{1};
    x = varargin{2};
    mu = varargin{3};
    sigma = varargin{4};
  else
    print_usage ();
  endif

  # Dimension
  q = size (sigma, 1);
  cases = size (x, 1);

  # Default value for mu
  if (isempty (mu))
    mu = zeros (1, q);
  endif

  # Check parameters
  if (size (x, 2) != q)
    error ("mvncdf: x must have the same number of columns as sigma");
  endif

  if (any (size (x) != size (a)))
    error ("mvncdf: a must have the same size as x");
  endif

  if (isscalar (mu))
    mu = ones (1, q) .* mu;
  elseif (! isvector (mu) || size (mu, 2) != q)
    error ("mvncdf: mu must be a scalar or a vector with the same number of columns as x");
  endif

  x = x - repmat (mu, cases, 1);

  if (q < 1 || size (sigma, 2) != q || any (any (sigma != sigma')) || min (eig (sigma)) <= 0)
    error ("mvncdf: sigma must be nonempty symmetric positive definite");
  endif

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

    # Transformation of the multivariate normal integral
    dvev = normcdf ([a(:, 1) / c(1, 1), x(:, 1) / c(1, 1)]);
    dv = dvev(:, 1);
    ev = dvev(:, 2);
    fv = ev - dv;
    y = zeros (cases, q - 1);
    for i = 1:(q - 1)
      y(:, i) = norminv (dv + w(:, i) .* (ev - dv));
      dvev = normcdf ([(a(:, i + 1) - c(i + 1, 1:i) .* y(:, 1:i)) ./ c(i + 1, i + 1), (x(:, i + 1) - c(i + 1, 1:i) .* y(:, 1:i)) ./ c(i + 1, i + 1)]);
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
