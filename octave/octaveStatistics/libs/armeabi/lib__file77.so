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
## @deftypefn {Function File} {[@var{m}, @var{v}] =} wblstat (@var{scale}, @var{shape})
## Compute mean and variance of the Weibull distribution.
##
## @subheading Arguments
##
## @itemize @bullet
## @item
## @var{scale} is the scale parameter of the Weibull distribution.
## @var{scale} must be positive
##
## @item
## @var{shape} is the shape parameter of the Weibull distribution.
## @var{shape} must be positive
## @end itemize
## @var{scale} and @var{shape} must be of common size or one of them must be
## scalar
##
## @subheading Return values
##
## @itemize @bullet
## @item
## @var{m} is the mean of the Weibull distribution
##
## @item
## @var{v} is the variance of the Weibull distribution
## @end itemize
##
## @subheading Examples
##
## @example
## @group
## scale = 3:8;
## shape = 1:6;
## [m, v] = wblstat (scale, shape)
## @end group
##
## @group
## [m, v] = wblstat (6, shape)
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
## Description: Moments of the Weibull distribution

function [m, v] = wblstat (scale, shape)

  # Check arguments
  if (nargin != 2)
    print_usage ();
  endif

  if (! isempty (scale) && ! ismatrix (scale))
    error ("wblstat: scale must be a numeric matrix");
  endif
  if (! isempty (shape) && ! ismatrix (shape))
    error ("wblstat: shape must be a numeric matrix");
  endif

  if (! isscalar (scale) || ! isscalar (shape))
    [retval, scale, shape] = common_size (scale, shape);
    if (retval > 0)
      error ("wblstat: scale and shape must be of common size or scalar");
    endif
  endif

  # Calculate moments
  m = scale .* gamma (1 + 1 ./ shape);
  v = (scale .^ 2) .* gamma (1 + 2 ./ shape) - m .^ 2;

  # Continue argument check
  k = find (! (scale > 0) | ! (scale < Inf) | ! (shape > 0) | ! (shape < Inf));
  if (any (k))
    m(k) = NaN;
    v(k) = NaN;
  endif

endfunction

%!test
%! scale = 3:8;
%! shape = 1:6;
%! [m, v] = wblstat (scale, shape);
%! expected_m = [3.0000, 3.5449, 4.4649, 5.4384, 6.4272, 7.4218];
%! expected_v = [9.0000, 3.4336, 2.6333, 2.3278, 2.1673, 2.0682];
%! assert (m, expected_m, 0.001);
%! assert (v, expected_v, 0.001);

%!test
%! shape = 1:6;
%! [m, v] = wblstat (6, shape);
%! expected_m = [ 6.0000, 5.3174, 5.3579, 5.4384, 5.5090, 5.5663];
%! expected_v = [36.0000, 7.7257, 3.7920, 2.3278, 1.5923, 1.1634];
%! assert (m, expected_m, 0.001);
%! assert (v, expected_v, 0.001);
