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
## @deftypefn {Function File} {[@var{m}, @var{v}] =} lognstat (@var{mu}, @var{sigma})
## Compute mean and variance of the lognormal distribution.
##
## @subheading Arguments
##
## @itemize @bullet
## @item
## @var{mu} is the first parameter of the lognormal distribution
##
## @item
## @var{sigma} is the second parameter of the lognormal distribution.
## @var{sigma} must be positive or zero
## @end itemize
## @var{mu} and @var{sigma} must be of common size or one of them must be
## scalar
##
## @subheading Return values
##
## @itemize @bullet
## @item
## @var{m} is the mean of the lognormal distribution
##
## @item
## @var{v} is the variance of the lognormal distribution
## @end itemize
##
## @subheading Examples
##
## @example
## @group
## mu = 0:0.2:1;
## sigma = 0.2:0.2:1.2;
## [m, v] = lognstat (mu, sigma)
## @end group
##
## @group
## [m, v] = lognstat (0, sigma)
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
## Description: Moments of the lognormal distribution

function [m, v] = lognstat (mu, sigma)

  # Check arguments
  if (nargin != 2)
    print_usage ();
  endif

  if (! isempty (mu) && ! ismatrix (mu))
    error ("lognstat: mu must be a numeric matrix");
  endif
  if (! isempty (sigma) && ! ismatrix (sigma))
    error ("lognstat: sigma must be a numeric matrix");
  endif

  if (! isscalar (mu) || ! isscalar (sigma))
    [retval, mu, sigma] = common_size (mu, sigma);
    if (retval > 0)
      error ("lognstat: mu and sigma must be of common size or scalar");
    endif
  endif

  # Calculate moments
  m = exp (mu + (sigma .^ 2) ./ 2);
  v = (exp (sigma .^ 2) - 1) .* exp (2 .* mu + sigma .^ 2);

  # Continue argument check
  k = find (! (sigma >= 0) | ! (sigma < Inf));
  if (any (k))
    m(k) = NaN;
    v(k) = NaN;
  endif

endfunction

%!test
%! mu = 0:0.2:1;
%! sigma = 0.2:0.2:1.2;
%! [m, v] = lognstat (mu, sigma);
%! expected_m = [1.0202, 1.3231, 1.7860, 2.5093,  3.6693,   5.5845];
%! expected_v = [0.0425, 0.3038, 1.3823, 5.6447, 23.1345, 100.4437];
%! assert (m, expected_m, 0.001);
%! assert (v, expected_v, 0.001);

%!test
%! sigma = 0.2:0.2:1.2;
%! [m, v] = lognstat (0, sigma);
%! expected_m = [1.0202, 1.0833, 1.1972, 1.3771, 1.6487,  2.0544];
%! expected_v = [0.0425, 0.2036, 0.6211, 1.7002, 4.6708, 13.5936];
%! assert (m, expected_m, 0.001);
%! assert (v, expected_v, 0.001);
