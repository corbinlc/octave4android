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
## @deftypefn {Function File} {[@var{mn}, @var{v}] =} fstat (@var{m}, @var{n})
## Compute mean and variance of the F distribution.
##
## @subheading Arguments
##
## @itemize @bullet
## @item
## @var{m} is the first parameter of the F distribution. The elements
## of @var{m} must be positive
##
## @item
## @var{n} is the second parameter of the F distribution. The
## elements of @var{n} must be positive
## @end itemize
## @var{m} and @var{n} must be of common size or one of them must be scalar
##
## @subheading Return values
##
## @itemize @bullet
## @item
## @var{mn} is the mean of the F distribution. The mean is undefined for
## @var{n} not greater than 2
##
## @item
## @var{v} is the variance of the F distribution. The variance is undefined
## for @var{n} not greater than 4
## @end itemize
##
## @subheading Examples
##
## @example
## @group
## m = 1:6;
## n = 5:10;
## [mn, v] = fstat (m, n)
## @end group
##
## @group
## [mn, v] = fstat (m, 5)
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
## Processes}. McGraw-Hill, New York, second edition, 1984.
## @end enumerate
## @end deftypefn

## Author: Arno Onken <asnelt@asnelt.org>
## Description: Moments of the F distribution

function [mn, v] = fstat (m, n)

  # Check arguments
  if (nargin != 2)
    print_usage ();
  endif

  if (! isempty (m) && ! ismatrix (m))
    error ("fstat: m must be a numeric matrix");
  endif
  if (! isempty (n) && ! ismatrix (n))
    error ("fstat: n must be a numeric matrix");
  endif

  if (! isscalar (m) || ! isscalar (n))
    [retval, m, n] = common_size (m, n);
    if (retval > 0)
      error ("fstat: m and n must be of common size or scalar");
    endif
  endif

  # Calculate moments
  mn = n ./ (n - 2);
  v = (2 .* (n .^ 2) .* (m + n - 2)) ./ (m .* ((n - 2) .^ 2) .* (n - 4));

  # Continue argument check
  k = find (! (m > 0) | ! (m < Inf) | ! (n > 2) | ! (n < Inf));
  if (any (k))
    mn(k) = NaN;
    v(k) = NaN;
  endif

  k = find (! (n > 4));
  if (any (k))
    v(k) = NaN;
  endif

endfunction

%!test
%! m = 1:6;
%! n = 5:10;
%! [mn, v] = fstat (m, n);
%! expected_mn = [1.6667, 1.5000, 1.4000, 1.3333, 1.2857, 1.2500];
%! expected_v = [22.2222, 6.7500, 3.4844, 2.2222, 1.5869, 1.2153];
%! assert (mn, expected_mn, 0.001);
%! assert (v, expected_v, 0.001);

%!test
%! m = 1:6;
%! [mn, v] = fstat (m, 5);
%! expected_mn = [1.6667, 1.6667, 1.6667, 1.6667, 1.6667, 1.6667];
%! expected_v = [22.2222, 13.8889, 11.1111, 9.7222, 8.8889, 8.3333];
%! assert (mn, expected_mn, 0.001);
%! assert (v, expected_v, 0.001);
