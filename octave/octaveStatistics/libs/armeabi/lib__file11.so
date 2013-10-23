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
## @deftypefn {Function File} {@var{p} =} copulacdf (@var{family}, @var{x}, @var{theta})
## @deftypefnx {Function File} {} copulacdf ('t', @var{x}, @var{theta}, @var{nu})
## Compute the cumulative distribution function of a copula family.
##
## @subheading Arguments
##
## @itemize @bullet
## @item
## @var{family} is the copula family name. Currently, @var{family} can
## be @code{'Gaussian'} for the Gaussian family, @code{'t'} for the
## Student's t family, @code{'Clayton'} for the Clayton family,
## @code{'Gumbel'} for the Gumbel-Hougaard family, @code{'Frank'} for
## the Frank family, @code{'AMH'} for the Ali-Mikhail-Haq family, or
## @code{'FGM'} for the Farlie-Gumbel-Morgenstern family.
##
## @item
## @var{x} is the support where each row corresponds to an observation.
##
## @item
## @var{theta} is the parameter of the copula. For the Gaussian and
## Student's t copula, @var{theta} must be a correlation matrix. For
## bivariate copulas @var{theta} can also be a correlation coefficient.
## For the Clayton family, the Gumbel-Hougaard family, the Frank family,
## and the Ali-Mikhail-Haq family, @var{theta} must be a vector with the
## same number of elements as observations in @var{x} or be scalar. For
## the Farlie-Gumbel-Morgenstern family, @var{theta} must be a matrix of
## coefficients for the Farlie-Gumbel-Morgenstern polynomial where each
## row corresponds to one set of coefficients for an observation in
## @var{x}. A single row is expanded. The coefficients are in binary
## order.
##
## @item
## @var{nu} is the degrees of freedom for the Student's t family.
## @var{nu} must be a vector with the same number of elements as
## observations in @var{x} or be scalar.
## @end itemize
##
## @subheading Return values
##
## @itemize @bullet
## @item
## @var{p} is the cumulative distribution of the copula at each row of
## @var{x} and corresponding parameter @var{theta}.
## @end itemize
##
## @subheading Examples
##
## @example
## @group
## x = [0.2:0.2:0.6; 0.2:0.2:0.6];
## theta = [1; 2];
## p = copulacdf ("Clayton", x, theta)
## @end group
##
## @group
## x = [0.2:0.2:0.6; 0.2:0.1:0.4];
## theta = [0.2, 0.1, 0.1, 0.05];
## p = copulacdf ("FGM", x, theta)
## @end group
## @end example
##
## @subheading References
##
## @enumerate
## @item
## Roger B. Nelsen. @cite{An Introduction to Copulas}. Springer,
## New York, second edition, 2006.
## @end enumerate
## @end deftypefn

## Author: Arno Onken <asnelt@asnelt.org>
## Description: CDF of a copula family

function p = copulacdf (family, x, theta, nu)

  # Check arguments
  if (nargin != 3 && (nargin != 4 || ! strcmpi (family, "t")))
    print_usage ();
  endif

  if (! ischar (family))
    error ("copulacdf: family must be one of 'Gaussian', 't', 'Clayton', 'Gumbel', 'Frank', 'AMH', and 'FGM'");
  endif

  if (! isempty (x) && ! ismatrix (x))
    error ("copulacdf: x must be a numeric matrix");
  endif

  [n, d] = size (x);

  lower_family = lower (family);

  # Check family and copula parameters
  switch (lower_family)

    case {"gaussian", "t"}
      # Family with a covariance matrix
      if (d == 2 && isscalar (theta))
        # Expand a scalar to a correlation matrix
        theta = [1, theta; theta, 1];
      endif
      if (any (size (theta) != [d, d]) || any (diag (theta) != 1) || any (any (theta != theta')) || min (eig (theta)) <= 0)
        error ("copulacdf: theta must be a correlation matrix");
      endif
      if (nargin == 4)
        # Student's t family
        if (! isscalar (nu) && (! isvector (nu) || length (nu) != n))
          error ("copulacdf: nu must be a vector with the same number of rows as x or be scalar");
        endif
        nu = nu(:);
      endif

    case {"clayton", "gumbel", "frank", "amh"}
      # Archimedian one parameter family
      if (! isvector (theta) || (! isscalar (theta) && length (theta) != n))
        error ("copulacdf: theta must be a vector with the same number of rows as x or be scalar");
      endif
      theta = theta(:);
      if (n > 1 && isscalar (theta))
        theta = repmat (theta, n, 1);
      endif

    case {"fgm"}
      # Exponential number of parameters
      if (! ismatrix (theta) || size (theta, 2) != (2 .^ d - d - 1) || (size (theta, 1) != 1 && size (theta, 1) != n))
        error ("copulacdf: theta must be a row vector of length 2^d-d-1 or a matrix of size n x (2^d-d-1)");
      endif
      if (n > 1 && size (theta, 1) == 1)
        theta = repmat (theta, n, 1);
      endif

    otherwise
      error ("copulacdf: unknown copula family '%s'", family);

  endswitch

  if (n == 0)
    # Input is empty
    p = zeros (0, 1);
  else
    # Truncate input to unit hypercube
    x(x < 0) = 0;
    x(x > 1) = 1;

    # Compute the cumulative distribution function according to family
    switch (lower_family)

      case {"gaussian"}
        # The Gaussian family
        p = mvncdf (norminv (x), zeros (1, d), theta);
        # No parameter bounds check
        k = [];

      case {"t"}
        # The Student's t family
        p = mvtcdf (tinv (x, nu), theta, nu);
        # No parameter bounds check
        k = [];

      case {"clayton"}
        # The Clayton family
        p = exp (-log (max (sum (x .^ (repmat (-theta, 1, d)), 2) - d + 1, 0)) ./ theta);
        # Product copula at columns where theta == 0
        k = find (theta == 0);
        if (any (k))
          p(k) = prod (x(k, :), 2);
        endif
        # Check bounds
        if (d > 2)
          k = find (! (theta >= 0) | ! (theta < inf));
        else
          k = find (! (theta >= -1) | ! (theta < inf));
        endif

      case {"gumbel"}
        # The Gumbel-Hougaard family
        p = exp (-(sum ((-log (x)) .^ repmat (theta, 1, d), 2)) .^ (1 ./ theta));
        # Check bounds
        k = find (! (theta >= 1) | ! (theta < inf));

      case {"frank"}
        # The Frank family
        p = -log (1 + (prod (expm1 (repmat (-theta, 1, d) .* x), 2)) ./ (expm1 (-theta) .^ (d - 1))) ./ theta;
        # Product copula at columns where theta == 0
        k = find (theta == 0);
        if (any (k))
          p(k) = prod (x(k, :), 2);
        endif
        # Check bounds
        if (d > 2)
          k = find (! (theta > 0) | ! (theta < inf));
        else
          k = find (! (theta > -inf) | ! (theta < inf));
        endif

      case {"amh"}
        # The Ali-Mikhail-Haq family
        p = (theta - 1) ./ (theta - prod ((1 + repmat (theta, 1, d) .* (x - 1)) ./ x, 2));
        # Check bounds
        if (d > 2)
          k = find (! (theta >= 0) | ! (theta < 1));
        else
          k = find (! (theta >= -1) | ! (theta < 1));
        endif

      case {"fgm"}
        # The Farlie-Gumbel-Morgenstern family
        # All binary combinations
        bcomb = logical (floor (mod (((0:(2 .^ d - 1))' * 2 .^ ((1 - d):0)), 2)));
        ecomb = ones (size (bcomb));
        ecomb(bcomb) = -1;
        # Summation over all combinations of order >= 2
        bcomb = bcomb(sum (bcomb, 2) >= 2, end:-1:1);
        # Linear constraints matrix
        ac = zeros (size (ecomb, 1), size (bcomb, 1));
        # Matrix to compute p
        ap = zeros (size (x, 1), size (bcomb, 1));
        for i = 1:size (bcomb, 1)
          ac(:, i) = -prod (ecomb(:, bcomb(i, :)), 2);
          ap(:, i) = prod (1 - x(:, bcomb(i, :)), 2);
        endfor
        p = prod (x, 2) .* (1 + sum (ap .* theta, 2));
        # Check linear constraints
        k = false (n, 1);
        for i = 1:n
          k(i) = any (ac * theta(i, :)' > 1);
        endfor

    endswitch

    # Out of bounds parameters
    if (any (k))
      p(k) = NaN;
    endif

  endif

endfunction

%!test
%! x = [0.2:0.2:0.6; 0.2:0.2:0.6];
%! theta = [1; 2];
%! p = copulacdf ("Clayton", x, theta);
%! expected_p = [0.1395; 0.1767];
%! assert (p, expected_p, 0.001);

%!test
%! x = [0.2:0.2:0.6; 0.2:0.2:0.6];
%! p = copulacdf ("Gumbel", x, 2);
%! expected_p = [0.1464; 0.1464];
%! assert (p, expected_p, 0.001);

%!test
%! x = [0.2:0.2:0.6; 0.2:0.2:0.6];
%! theta = [1; 2];
%! p = copulacdf ("Frank", x, theta);
%! expected_p = [0.0699; 0.0930];
%! assert (p, expected_p, 0.001);

%!test
%! x = [0.2:0.2:0.6; 0.2:0.2:0.6];
%! theta = [0.3; 0.7];
%! p = copulacdf ("AMH", x, theta);
%! expected_p = [0.0629; 0.0959];
%! assert (p, expected_p, 0.001);

%!test
%! x = [0.2:0.2:0.6; 0.2:0.1:0.4];
%! theta = [0.2, 0.1, 0.1, 0.05];
%! p = copulacdf ("FGM", x, theta);
%! expected_p = [0.0558; 0.0293];
%! assert (p, expected_p, 0.001);
