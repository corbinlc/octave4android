## Copyright (C) 2006, 2007 Arno Onken <asnelt@asnelt.org>
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
## @deftypefn {Function File} {@var{x} =} raylinv (@var{p}, @var{sigma})
## Compute the quantile of the Rayleigh distribution. The quantile is the
## inverse of the cumulative distribution function.
##
## @subheading Arguments
##
## @itemize @bullet
## @item
## @var{p} is the cumulative distribution. The elements of @var{p} must be
## probabilities.
##
## @item
## @var{sigma} is the parameter of the Rayleigh distribution. The elements
## of @var{sigma} must be positive.
## @end itemize
## @var{p} and @var{sigma} must be of common size or one of them must be
## scalar.
##
## @subheading Return values
##
## @itemize @bullet
## @item
## @var{x} is the quantile of the Rayleigh distribution at each element of
## @var{p} and corresponding parameter @var{sigma}.
## @end itemize
##
## @subheading Examples
##
## @example
## @group
## p = 0:0.1:0.5;
## sigma = 1:6;
## x = raylinv (p, sigma)
## @end group
##
## @group
## x = raylinv (p, 0.5)
## @end group
## @end example
##
## @subheading References
##
## @enumerate
## @item
## Wendy L. Martinez and Angel R. Martinez. @cite{Computational Statistics
## Handbook with MATLAB}. Appendix E, pages 547-557, Chapman & Hall/CRC,
## 2001.
##
## @item
## Athanasios Papoulis. @cite{Probability, Random Variables, and Stochastic
## Processes}. pages 104 and 148, McGraw-Hill, New York, second edition,
## 1984.
## @end enumerate
## @end deftypefn

## Author: Arno Onken <asnelt@asnelt.org>
## Description: Quantile of the Rayleigh distribution

function x = raylinv (p, sigma)

  # Check arguments
  if (nargin != 2)
    print_usage ();
  endif

  if (! isempty (p) && ! ismatrix (p))
    error ("raylinv: p must be a numeric matrix");
  endif
  if (! isempty (sigma) && ! ismatrix (sigma))
    error ("raylinv: sigma must be a numeric matrix");
  endif

  if (! isscalar (p) || ! isscalar (sigma))
    [retval, p, sigma] = common_size (p, sigma);
    if (retval > 0)
      error ("raylinv: p and sigma must be of common size or scalar");
    endif
  endif

  # Calculate quantile
  x = sqrt (-2 .* log (1 - p) .* sigma .^ 2);

  k = find (p == 1);
  if (any (k))
    x(k) = Inf;
  endif

  # Continue argument check
  k = find (! (p >= 0) | ! (p <= 1) | ! (sigma > 0));
  if (any (k))
    x(k) = NaN;
  endif

endfunction

%!test
%! p = 0:0.1:0.5;
%! sigma = 1:6;
%! x = raylinv (p, sigma);
%! expected_x = [0.0000, 0.9181, 2.0041, 3.3784, 5.0538, 7.0645];
%! assert (x, expected_x, 0.001);

%!test
%! p = 0:0.1:0.5;
%! x = raylinv (p, 0.5);
%! expected_x = [0.0000, 0.2295, 0.3340, 0.4223, 0.5054, 0.5887];
%! assert (x, expected_x, 0.001);
