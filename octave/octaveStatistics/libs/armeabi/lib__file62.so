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
## @deftypefn {Function File} {@var{y} =} raylpdf (@var{x}, @var{sigma})
## Compute the probability density function of the Rayleigh distribution.
##
## @subheading Arguments
##
## @itemize @bullet
## @item
## @var{x} is the support. The elements of @var{x} must be non-negative.
##
## @item
## @var{sigma} is the parameter of the Rayleigh distribution. The elements
## of @var{sigma} must be positive.
## @end itemize
## @var{x} and @var{sigma} must be of common size or one of them must be
## scalar.
##
## @subheading Return values
##
## @itemize @bullet
## @item
## @var{y} is the probability density of the Rayleigh distribution at each
## element of @var{x} and corresponding parameter @var{sigma}.
## @end itemize
##
## @subheading Examples
##
## @example
## @group
## x = 0:0.5:2.5;
## sigma = 1:6;
## y = raylpdf (x, sigma)
## @end group
##
## @group
## y = raylpdf (x, 0.5)
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
## Description: PDF of the Rayleigh distribution

function y = raylpdf (x, sigma)

  # Check arguments
  if (nargin != 2)
    print_usage ();
  endif

  if (! isempty (x) && ! ismatrix (x))
    error ("raylpdf: x must be a numeric matrix");
  endif
  if (! isempty (sigma) && ! ismatrix (sigma))
    error ("raylpdf: sigma must be a numeric matrix");
  endif

  if (! isscalar (x) || ! isscalar (sigma))
    [retval, x, sigma] = common_size (x, sigma);
    if (retval > 0)
      error ("raylpdf: x and sigma must be of common size or scalar");
    endif
  endif

  # Calculate pdf
  y = x .* exp ((-x .^ 2) ./ (2 .* sigma .^ 2)) ./ (sigma .^ 2);

  # Continue argument check
  k = find (! (x >= 0) | ! (x < Inf) | ! (sigma > 0));
  if (any (k))
    y(k) = NaN;
  endif

endfunction

%!test
%! x = 0:0.5:2.5;
%! sigma = 1:6;
%! y = raylpdf (x, sigma);
%! expected_y = [0.0000, 0.1212, 0.1051, 0.0874, 0.0738, 0.0637];
%! assert (y, expected_y, 0.001);

%!test
%! x = 0:0.5:2.5;
%! y = raylpdf (x, 0.5);
%! expected_y = [0.0000, 1.2131, 0.5413, 0.0667, 0.0027, 0.0000];
%! assert (y, expected_y, 0.001);
