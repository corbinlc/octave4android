## Copyright (C) 2012  Arno Onken <asnelt@asnelt.org>
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
## @deftypefn {Function File} {@var{x} =} mvtrnd (@var{sigma}, @var{nu})
## @deftypefnx {Function File} {@var{x} =} mvtrnd (@var{sigma}, @var{nu}, @var{n})
## Generate random samples from the multivariate t-distribution.
##
## @subheading Arguments
##
## @itemize @bullet
## @item
## @var{sigma} is the matrix of correlation coefficients. If there are any
## non-unit diagonal elements then @var{sigma} will be normalized.
##
## @item
## @var{nu} is the degrees of freedom for the multivariate t-distribution.
## @var{nu} must be a vector with the same number of elements as samples to be
## generated or be scalar.
##
## @item
## @var{n} is the number of rows of the matrix to be generated. @var{n} must be
## a non-negative integer and corresponds to the number of samples to be
## generated.
## @end itemize
##
## @subheading Return values
##
## @itemize @bullet
## @item
## @var{x} is a matrix of random samples from the multivariate t-distribution
## with @var{n} row samples.
## @end itemize
##
## @subheading Examples
##
## @example
## @group
## sigma = [1, 0.5; 0.5, 1];
## nu = 3;
## n = 10;
## x = mvtrnd (sigma, nu, n);
## @end group
##
## @group
## sigma = [1, 0.5; 0.5, 1];
## nu = [2; 3];
## n = 2;
## x = mvtrnd (sigma, nu, 2);
## @end group
## @end example
##
## @subheading References
##
## @enumerate
## @item
## Wendy L. Martinez and Angel R. Martinez. @cite{Computational Statistics
## Handbook with MATLAB}. Appendix E, pages 547-557, Chapman & Hall/CRC, 2001.
##
## @item
## Samuel Kotz and Saralees Nadarajah. @cite{Multivariate t Distributions and
## Their Applications}. Cambridge University Press, Cambridge, 2004.
## @end enumerate
## @end deftypefn

## Author: Arno Onken <asnelt@asnelt.org>
## Description: Random samples from the multivariate t-distribution

function x = mvtrnd (sigma, nu, n)

  # Check arguments
  if (nargin < 2)
    print_usage ();
  endif

  if (! ismatrix (sigma) || any (any (sigma != sigma')) || min (eig (sigma)) <= 0)
    error ("mvtrnd: sigma must be a positive definite matrix");
  endif

  if (!isvector (nu) || any (nu <= 0))
    error ("mvtrnd: nu must be a positive scalar or vector");
  endif
  nu = nu(:);

  if (nargin > 2)
    if (! isscalar (n) || n < 0 | round (n) != n)
      error ("mvtrnd: n must be a non-negative integer")
    endif
    if (isscalar (nu))
      nu = nu * ones (n, 1);
    else
      if (length (nu) != n)
        error ("mvtrnd: n must match the length of nu")
      endif
    endif
  else
    n = length (nu);
  endif

  # Normalize sigma
  if (any (diag (sigma) != 1))
    sigma = sigma ./ sqrt (diag (sigma) * diag (sigma)');
  endif

  # Dimension
  d = size (sigma, 1);
  # Draw samples
  y = mvnrnd (zeros (1, d), sigma, n);
  u = repmat (chi2rnd (nu), 1, d);
  x = y .* sqrt (repmat (nu, 1, d) ./ u);
endfunction

%!test
%! sigma = [1, 0.5; 0.5, 1];
%! nu = 3;
%! n = 10;
%! x = mvtrnd (sigma, nu, n);
%! assert (size (x), [10, 2]);

%!test
%! sigma = [1, 0.5; 0.5, 1];
%! nu = [2; 3];
%! n = 2;
%! x = mvtrnd (sigma, nu, 2);
%! assert (size (x), [2, 2]);
