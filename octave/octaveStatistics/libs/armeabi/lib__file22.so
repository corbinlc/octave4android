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
## @deftypefn {Function File} {[@var{m}, @var{v}] =} geostat (@var{p})
## Compute mean and variance of the geometric distribution.
##
## @subheading Arguments
##
## @itemize @bullet
## @item
## @var{p} is the rate parameter of the geometric distribution. The
## elements of @var{p} must be probabilities
## @end itemize
##
## @subheading Return values
##
## @itemize @bullet
## @item
## @var{m} is the mean of the geometric distribution
##
## @item
## @var{v} is the variance of the geometric distribution
## @end itemize
##
## @subheading Example
##
## @example
## @group
## p = 1 ./ (1:6);
## [m, v] = geostat (p)
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
## Description: Moments of the geometric distribution

function [m, v] = geostat (p)

  # Check arguments
  if (nargin != 1)
    print_usage ();
  endif

  if (! isempty (p) && ! ismatrix (p))
    error ("geostat: p must be a numeric matrix");
  endif

  # Calculate moments
  q = 1 - p;
  m = q ./ p;
  v = q ./ (p .^ 2);

  # Continue argument check
  k = find (! (p >= 0) | ! (p <= 1));
  if (any (k))
    m(k) = NaN;
    v(k) = NaN;
  endif

endfunction

%!test
%! p = 1 ./ (1:6);
%! [m, v] = geostat (p);
%! assert (m, [0, 1, 2, 3, 4, 5], 0.001);
%! assert (v, [0, 2, 6, 12, 20, 30], 0.001);
