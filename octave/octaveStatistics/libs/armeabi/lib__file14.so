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
## @deftypefn {Function File} {[@var{m}, @var{v}] =} expstat (@var{l})
## Compute mean and variance of the exponential distribution.
##
## @subheading Arguments
##
## @itemize @bullet
## @item
## @var{l} is the parameter of the exponential distribution. The
## elements of @var{l} must be positive
## @end itemize
##
## @subheading Return values
##
## @itemize @bullet
## @item
## @var{m} is the mean of the exponential distribution
##
## @item
## @var{v} is the variance of the exponential distribution
## @end itemize
##
## @subheading Example
##
## @example
## @group
## l = 1:6;
## [m, v] = expstat (l)
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
## Description: Moments of the exponential distribution

function [m, v] = expstat (l)

  # Check arguments
  if (nargin != 1)
    print_usage ();
  endif

  if (! isempty (l) && ! ismatrix (l))
    error ("expstat: l must be a numeric matrix");
  endif

  # Calculate moments
  m = l;
  v = m .^ 2;

  # Continue argument check
  k = find (! (l > 0) | ! (l < Inf));
  if (any (k))
    m(k) = NaN;
    v(k) = NaN;
  endif

endfunction

%!test
%! l = 1:6;
%! [m, v] = expstat (l);
%! assert (m, [1, 2, 3, 4, 5, 6], 0.001);
%! assert (v, [1, 4, 9, 16, 25, 36], 0.001);
