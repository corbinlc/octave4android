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
## @deftypefn {Function File} {[@var{m}, @var{v}] =} unifstat (@var{a}, @var{b})
## Compute mean and variance of the continuous uniform distribution.
##
## @subheading Arguments
##
## @itemize @bullet
## @item
## @var{a} is the first parameter of the continuous uniform distribution
##
## @item
## @var{b} is the second parameter of the continuous uniform distribution
## @end itemize
## @var{a} and @var{b} must be of common size or one of them must be scalar
## and @var{a} must be less than @var{b}
##
## @subheading Return values
##
## @itemize @bullet
## @item
## @var{m} is the mean of the continuous uniform distribution
##
## @item
## @var{v} is the variance of the continuous uniform distribution
## @end itemize
##
## @subheading Examples
##
## @example
## @group
## a = 1:6;
## b = 2:2:12;
## [m, v] = unifstat (a, b)
## @end group
##
## @group
## [m, v] = unifstat (a, 10)
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
## Description: Moments of the continuous uniform distribution

function [m, v] = unifstat (a, b)

  # Check arguments
  if (nargin != 2)
    print_usage ();
  endif

  if (! isempty (a) && ! ismatrix (a))
    error ("unifstat: a must be a numeric matrix");
  endif
  if (! isempty (b) && ! ismatrix (b))
    error ("unifstat: b must be a numeric matrix");
  endif

  if (! isscalar (a) || ! isscalar (b))
    [retval, a, b] = common_size (a, b);
    if (retval > 0)
      error ("unifstat: a and b must be of common size or scalar");
    endif
  endif

  # Calculate moments
  m = (a + b) ./ 2;
  v = ((b - a) .^ 2) ./ 12;

  # Continue argument check
  k = find (! (-Inf < a) | ! (a < b) | ! (b < Inf));
  if (any (k))
    m(k) = NaN;
    v(k) = NaN;
  endif

endfunction

%!test
%! a = 1:6;
%! b = 2:2:12;
%! [m, v] = unifstat (a, b);
%! expected_m = [1.5000, 3.0000, 4.5000, 6.0000, 7.5000, 9.0000];
%! expected_v = [0.0833, 0.3333, 0.7500, 1.3333, 2.0833, 3.0000];
%! assert (m, expected_m, 0.001);
%! assert (v, expected_v, 0.001);

%!test
%! a = 1:6;
%! [m, v] = unifstat (a, 10);
%! expected_m = [5.5000, 6.0000, 6.5000, 7.0000, 7.5000, 8.0000];
%! expected_v = [6.7500, 5.3333, 4.0833, 3.0000, 2.0833, 1.3333];
%! assert (m, expected_m, 0.001);
%! assert (v, expected_v, 0.001);
