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
## @deftypefn {Function File} {@var{p} =} copulapdf (@var{family}, @var{x}, @var{theta})
## Compute the probability density function of a copula family.
##
## @subheading Arguments
##
## @itemize @bullet
## @item
## @var{family} is the copula family name. Currently, @var{family} can
## be @code{'Clayton'} for the Clayton family, @code{'Gumbel'} for the
## Gumbel-Hougaard family, @code{'Frank'} for the Frank family, or
## @code{'AMH'} for the Ali-Mikhail-Haq family.
##
## @item
## @var{x} is the support where each row corresponds to an observation.
##
## @item
## @var{theta} is the parameter of the copula. The elements of
## @var{theta} must be greater than or equal to @code{-1} for the
## Clayton family, greater than or equal to @code{1} for the
## Gumbel-Hougaard family, arbitrary for the Frank family, and greater
## than or equal to @code{-1} and lower than @code{1} for the
## Ali-Mikhail-Haq family. Moreover, @var{theta} must be non-negative
## for dimensions greater than @code{2}. @var{theta} must be a column
## vector with the same number of rows as @var{x} or be scalar.
## @end itemize
##
## @subheading Return values
##
## @itemize @bullet
## @item
## @var{p} is the probability density of the copula at each row of
## @var{x} and corresponding parameter @var{theta}.
## @end itemize
##
## @subheading Examples
##
## @example
## @group
## x = [0.2:0.2:0.6; 0.2:0.2:0.6];
## theta = [1; 2];
## p = copulapdf ("Clayton", x, theta)
## @end group
##
## @group
## p = copulapdf ("Gumbel", x, 2)
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
## Description: PDF of a copula family

function p = copulapdf (family, x, theta)

  # Check arguments
  if (nargin != 3)
    print_usage ();
  endif

  if (! ischar (family))
    error ("copulapdf: family must be one of 'Clayton', 'Gumbel', 'Frank', and 'AMH'");
  endif

  if (! isempty (x) && ! ismatrix (x))
    error ("copulapdf: x must be a numeric matrix");
  endif

  [n, d] = size (x);

  if (! isvector (theta) || (! isscalar (theta) && size (theta, 1) != n))
    error ("copulapdf: theta must be a column vector with the same number of rows as x or be scalar");
  endif

  if (n == 0)
    # Input is empty
    p = zeros (0, 1);
  else
    if (n > 1 && isscalar (theta))
      theta = repmat (theta, n, 1);
    endif

    # Truncate input to unit hypercube
    x(x < 0) = 0;
    x(x > 1) = 1;

    # Compute the cumulative distribution function according to family
    lowerarg = lower (family);

    if (strcmp (lowerarg, "clayton"))
      # The Clayton family
      log_cdf = -log (max (sum (x .^ (repmat (-theta, 1, d)), 2) - d + 1, 0)) ./ theta;
      p = prod (repmat (theta, 1, d) .* repmat (0:(d - 1), n, 1) + 1, 2) .* exp ((1 + theta .* d) .* log_cdf - (theta + 1) .* sum (log (x), 2));
      # Product copula at columns where theta == 0
      k = find (theta == 0);
      if (any (k))
        p(k) = 1;
      endif
      # Check theta
      if (d > 2)
        k = find (! (theta >= 0) | ! (theta < inf));
      else
        k = find (! (theta >= -1) | ! (theta < inf));
      endif
    elseif (strcmp (lowerarg, "gumbel"))
      # The Gumbel-Hougaard family
      g = sum ((-log (x)) .^ repmat (theta, 1, d), 2);
      c = exp (-g .^ (1 ./ theta));
      p = ((prod (-log (x), 2)) .^ (theta - 1)) ./ prod (x, 2) .* c .* (g .^ (2 ./ theta - 2) + (theta - 1) .* g .^ (1 ./ theta - 2));
      # Check theta
      k = find (! (theta >= 1) | ! (theta < inf));
    elseif (strcmp (lowerarg, "frank"))
      # The Frank family
      if (d != 2)
        error ("copulapdf: Frank copula PDF implemented as bivariate only");
      endif
      p = (theta .* exp (theta .* (1 + sum (x, 2))) .* (exp (theta) - 1))./ (exp (theta) - exp (theta + theta .* x(:, 1)) + exp (theta .* sum (x, 2)) - exp (theta + theta .* x(:, 2))) .^ 2;
      # Product copula at columns where theta == 0
      k = find (theta == 0);
      if (any (k))
        p(k) = 1;
      endif
      # Check theta
      k = find (! (theta > -inf) | ! (theta < inf));
    elseif (strcmp (lowerarg, "amh"))
      # The Ali-Mikhail-Haq family
      if (d != 2)
        error ("copulapdf: Ali-Mikhail-Haq copula PDF implemented as bivariate only");
      endif
      z = theta .* prod (x - 1, 2) - 1;
      p = (theta .* (1 - sum (x, 2) - prod (x, 2) - z) - 1) ./ (z .^ 3);
      # Check theta
      k = find (! (theta >= -1) | ! (theta < 1));
    else
      error ("copulapdf: unknown copula family '%s'", family);
    endif

    if (any (k))
      p(k) = NaN;
    endif

  endif

endfunction

%!test
%! x = [0.2:0.2:0.6; 0.2:0.2:0.6];
%! theta = [1; 2];
%! p = copulapdf ("Clayton", x, theta);
%! expected_p = [0.9872; 0.7295];
%! assert (p, expected_p, 0.001);

%!test
%! x = [0.2:0.2:0.6; 0.2:0.2:0.6];
%! p = copulapdf ("Gumbel", x, 2);
%! expected_p = [0.9468; 0.9468];
%! assert (p, expected_p, 0.001);

%!test
%! x = [0.2, 0.6; 0.2, 0.6];
%! theta = [1; 2];
%! p = copulapdf ("Frank", x, theta);
%! expected_p = [0.9378; 0.8678];
%! assert (p, expected_p, 0.001);

%!test
%! x = [0.2, 0.6; 0.2, 0.6];
%! theta = [0.3; 0.7];
%! p = copulapdf ("AMH", x, theta);
%! expected_p = [0.9540; 0.8577];
%! assert (p, expected_p, 0.001);
