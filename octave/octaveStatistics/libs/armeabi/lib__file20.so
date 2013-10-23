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
## @deftypefn {Function File} {[@var{m}, @var{v}] =} gamstat (@var{a}, @var{b})
## Compute mean and variance of the gamma distribution.
##
## @subheading Arguments
##
## @itemize @bullet
## @item
## @var{a} is the first parameter of the gamma distribution. @var{a} must be
## positive
##
## @item
## @var{b} is the second parameter of the gamma distribution. @var{b} must be
## positive
## @end itemize
## @var{a} and @var{b} must be of common size or one of them must be scalar
##
## @subheading Return values
##
## @itemize @bullet
## @item
## @var{m} is the mean of the gamma distribution
##
## @item
## @var{v} is the variance of the gamma distribution
## @end itemize
##
## @subheading Examples
##
## @example
## @group
## a = 1:6;
## b = 1:0.2:2;
## [m, v] = gamstat (a, b)
## @end group
##
## @group
## [m, v] = gamstat (a, 1.5)
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
## Description: Moments of the gamma distribution

function [m, v] = gamstat (a, b)

  # Check arguments
  if (nargin != 2)
    print_usage ();
  endif

  if (! isempty (a) && ! ismatrix (a))
    error ("gamstat: a must be a numeric matrix");
  endif
  if (! isempty (b) && ! ismatrix (b))
    error ("gamstat: b must be a numeric matrix");
  endif

  if (! isscalar (a) || ! isscalar (b))
    [retval, a, b] = common_size (a, b);
    if (retval > 0)
      error ("gamstat: a and b must be of common size or scalar");
    endif
  endif

  # Calculate moments
  m = a .* b;
  v = a .* (b .^ 2);

  # Continue argument check
  k = find (! (a > 0) | ! (a < Inf) | ! (b > 0) | ! (b < Inf));
  if (any (k))
    m(k) = NaN;
    v(k) = NaN;
  endif

endfunction

%!test
%! a = 1:6;
%! b = 1:0.2:2;
%! [m, v] = gamstat (a, b);
%! expected_m = [1.00, 2.40, 4.20,  6.40,  9.00, 12.00];
%! expected_v = [1.00, 2.88, 5.88, 10.24, 16.20, 24.00];
%! assert (m, expected_m, 0.001);
%! assert (v, expected_v, 0.001);

%!test
%! a = 1:6;
%! [m, v] = gamstat (a, 1.5);
%! expected_m = [1.50, 3.00, 4.50, 6.00,  7.50,  9.00];
%! expected_v = [2.25, 4.50, 6.75, 9.00, 11.25, 13.50];
%! assert (m, expected_m, 0.001);
%! assert (v, expected_v, 0.001);
