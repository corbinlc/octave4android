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
## @deftypefn {Function File} {[@var{m}, @var{v}] =} chi2stat (@var{n})
## Compute mean and variance of the chi-square distribution.
##
## @subheading Arguments
##
## @itemize @bullet
## @item
## @var{n} is the parameter of the chi-square distribution. The elements
## of @var{n} must be positive
## @end itemize
##
## @subheading Return values
##
## @itemize @bullet
## @item
## @var{m} is the mean of the chi-square distribution
##
## @item
## @var{v} is the variance of the chi-square distribution
## @end itemize
##
## @subheading Example
##
## @example
## @group
## n = 1:6;
## [m, v] = chi2stat (n)
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
## Description: Moments of the chi-square distribution

function [m, v] = chi2stat (n)

  # Check arguments
  if (nargin != 1)
    print_usage ();
  endif

  if (! isempty (n) && ! ismatrix (n))
    error ("chi2stat: n must be a numeric matrix");
  endif

  # Calculate moments
  m = n;
  v = 2 .* n;

  # Continue argument check
  k = find (! (n > 0) | ! (n < Inf));
  if (any (k))
    m(k) = NaN;
    v(k) = NaN;
  endif

endfunction

%!test
%! n = 1:6;
%! [m, v] = chi2stat (n);
%! assert (m, n);
%! assert (v, [2, 4, 6, 8, 10, 12], 0.001);
