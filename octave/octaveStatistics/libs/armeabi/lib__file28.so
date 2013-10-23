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
## @deftypefn {Function File} {[@var{mn}, @var{v}] =} hygestat (@var{t}, @var{m}, @var{n})
## Compute mean and variance of the hypergeometric distribution.
##
## @subheading Arguments
##
## @itemize @bullet
## @item
## @var{t} is the total size of the population of the hypergeometric
## distribution. The elements of @var{t} must be positive natural numbers
##
## @item
## @var{m} is the number of marked items of the hypergeometric distribution.
## The elements of @var{m} must be natural numbers
##
## @item
## @var{n} is the size of the drawn sample of the hypergeometric
## distribution. The elements of @var{n} must be positive natural numbers
## @end itemize
## @var{t}, @var{m}, and @var{n} must be of common size or scalar
##
## @subheading Return values
##
## @itemize @bullet
## @item
## @var{mn} is the mean of the hypergeometric distribution
##
## @item
## @var{v} is the variance of the hypergeometric distribution
## @end itemize
##
## @subheading Examples
##
## @example
## @group
## t = 4:9;
## m = 0:5;
## n = 1:6;
## [mn, v] = hygestat (t, m, n)
## @end group
##
## @group
## [mn, v] = hygestat (t, m, 2)
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
## Description: Moments of the hypergeometric distribution

function [mn, v] = hygestat (t, m, n)

  # Check arguments
  if (nargin != 3)
    print_usage ();
  endif

  if (! isempty (t) && ! ismatrix (t))
    error ("hygestat: t must be a numeric matrix");
  endif
  if (! isempty (m) && ! ismatrix (m))
    error ("hygestat: m must be a numeric matrix");
  endif
  if (! isempty (n) && ! ismatrix (n))
    error ("hygestat: n must be a numeric matrix");
  endif

  if (! isscalar (t) || ! isscalar (m) || ! isscalar (n))
    [retval, t, m, n] = common_size (t, m, n);
    if (retval > 0)
      error ("hygestat: t, m and n must be of common size or scalar");
    endif
  endif

  # Calculate moments
  mn = (n .* m) ./ t;
  v = (n .* (m ./ t) .* (1 - m ./ t) .* (t - n)) ./ (t - 1);

  # Continue argument check
  k = find (! (t >= 0) | ! (m >= 0) | ! (n > 0) | ! (t == round (t)) | ! (m == round (m)) | ! (n == round (n)) | ! (m <= t) | ! (n <= t));
  if (any (k))
    mn(k) = NaN;
    v(k) = NaN;
  endif

endfunction

%!test
%! t = 4:9;
%! m = 0:5;
%! n = 1:6;
%! [mn, v] = hygestat (t, m, n);
%! expected_mn = [0.0000, 0.4000, 1.0000, 1.7143, 2.5000, 3.3333];
%! expected_v = [0.0000, 0.2400, 0.4000, 0.4898, 0.5357, 0.5556];
%! assert (mn, expected_mn, 0.001);
%! assert (v, expected_v, 0.001);

%!test
%! t = 4:9;
%! m = 0:5;
%! [mn, v] = hygestat (t, m, 2);
%! expected_mn = [0.0000, 0.4000, 0.6667, 0.8571, 1.0000, 1.1111];
%! expected_v = [0.0000, 0.2400, 0.3556, 0.4082, 0.4286, 0.4321];
%! assert (mn, expected_mn, 0.001);
%! assert (v, expected_v, 0.001);
