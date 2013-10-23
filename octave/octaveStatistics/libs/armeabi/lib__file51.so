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
## @deftypefn {Function File} {[@var{m}, @var{v}] =} nbinstat (@var{n}, @var{p})
## Compute mean and variance of the negative binomial distribution.
##
## @subheading Arguments
##
## @itemize @bullet
## @item
## @var{n} is the first parameter of the negative binomial distribution. The elements
## of @var{n} must be natural numbers
##
## @item
## @var{p} is the second parameter of the negative binomial distribution. The
## elements of @var{p} must be probabilities
## @end itemize
## @var{n} and @var{p} must be of common size or one of them must be scalar
##
## @subheading Return values
##
## @itemize @bullet
## @item
## @var{m} is the mean of the negative binomial distribution
##
## @item
## @var{v} is the variance of the negative binomial distribution
## @end itemize
##
## @subheading Examples
##
## @example
## @group
## n = 1:4;
## p = 0.2:0.2:0.8;
## [m, v] = nbinstat (n, p)
## @end group
##
## @group
## [m, v] = nbinstat (n, 0.5)
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
## Description: Moments of the negative binomial distribution

function [m, v] = nbinstat (n, p)

  # Check arguments
  if (nargin != 2)
    print_usage ();
  endif

  if (! isempty (n) && ! ismatrix (n))
    error ("nbinstat: n must be a numeric matrix");
  endif
  if (! isempty (p) && ! ismatrix (p))
    error ("nbinstat: p must be a numeric matrix");
  endif

  if (! isscalar (n) || ! isscalar (p))
    [retval, n, p] = common_size (n, p);
    if (retval > 0)
      error ("nbinstat: n and p must be of common size or scalar");
    endif
  endif

  # Calculate moments
  q = 1 - p;
  m = n .* q ./ p;
  v = n .* q ./ (p .^ 2);

  # Continue argument check
  k = find (! (n > 0) | ! (n < Inf) | ! (p > 0) | ! (p < 1));
  if (any (k))
    m(k) = NaN;
    v(k) = NaN;
  endif

endfunction

%!test
%! n = 1:4;
%! p = 0.2:0.2:0.8;
%! [m, v] = nbinstat (n, p);
%! expected_m = [ 4.0000, 3.0000, 2.0000, 1.0000];
%! expected_v = [20.0000, 7.5000, 3.3333, 1.2500];
%! assert (m, expected_m, 0.001);
%! assert (v, expected_v, 0.001);

%!test
%! n = 1:4;
%! [m, v] = nbinstat (n, 0.5);
%! expected_m = [1, 2, 3, 4];
%! expected_v = [2, 4, 6, 8];
%! assert (m, expected_m, 0.001);
%! assert (v, expected_v, 0.001);
