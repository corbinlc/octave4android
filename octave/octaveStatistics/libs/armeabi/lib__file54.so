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
## @deftypefn {Function File} {[@var{mn}, @var{v}] =} normstat (@var{m}, @var{s})
## Compute mean and variance of the normal distribution.
##
## @subheading Arguments
##
## @itemize @bullet
## @item
## @var{m} is the mean of the normal distribution
##
## @item
## @var{s} is the standard deviation of the normal distribution.
## @var{s} must be positive
## @end itemize
## @var{m} and @var{s} must be of common size or one of them must be
## scalar
##
## @subheading Return values
##
## @itemize @bullet
## @item
## @var{mn} is the mean of the normal distribution
##
## @item
## @var{v} is the variance of the normal distribution
## @end itemize
##
## @subheading Examples
##
## @example
## @group
## m = 1:6;
## s = 0:0.2:1;
## [mn, v] = normstat (m, s)
## @end group
##
## @group
## [mn, v] = normstat (0, s)
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
## Description: Moments of the normal distribution

function [mn, v] = normstat (m, s)

  # Check arguments
  if (nargin != 2)
    print_usage ();
  endif

  if (! isempty (m) && ! ismatrix (m))
    error ("normstat: m must be a numeric matrix");
  endif
  if (! isempty (s) && ! ismatrix (s))
    error ("normstat: s must be a numeric matrix");
  endif

  if (! isscalar (m) || ! isscalar (s))
    [retval, m, s] = common_size (m, s);
    if (retval > 0)
      error ("normstat: m and s must be of common size or scalar");
    endif
  endif

  # Set moments
  mn = m;
  v = s .* s;

  # Continue argument check
  k = find (! (s > 0) | ! (s < Inf));
  if (any (k))
    mn(k) = NaN;
    v(k) = NaN;
  endif

endfunction

%!test
%! m = 1:6;
%! s = 0.2:0.2:1.2;
%! [mn, v] = normstat (m, s);
%! expected_v = [0.0400, 0.1600, 0.3600, 0.6400, 1.0000, 1.4400];
%! assert (mn, m);
%! assert (v, expected_v, 0.001);

%!test
%! s = 0.2:0.2:1.2;
%! [mn, v] = normstat (0, s);
%! expected_mn = [0, 0, 0, 0, 0, 0];
%! expected_v = [0.0400, 0.1600, 0.3600, 0.6400, 1.0000, 1.4400];
%! assert (mn, expected_mn, 0.001);
%! assert (v, expected_v, 0.001);
