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
## @deftypefn {Function File} {@var{x} =} raylrnd (@var{sigma})
## @deftypefnx {Function File} {@var{x} =} raylrnd (@var{sigma}, @var{sz})
## @deftypefnx {Function File} {@var{x} =} raylrnd (@var{sigma}, @var{r}, @var{c})
## Generate a matrix of random samples from the Rayleigh distribution.
##
## @subheading Arguments
##
## @itemize @bullet
## @item
## @var{sigma} is the parameter of the Rayleigh distribution. The elements
## of @var{sigma} must be positive.
##
## @item
## @var{sz} is the size of the matrix to be generated. @var{sz} must be a
## vector of non-negative integers.
##
## @item
## @var{r} is the number of rows of the matrix to be generated. @var{r} must
## be a non-negative integer.
##
## @item
## @var{c} is the number of columns of the matrix to be generated. @var{c}
## must be a non-negative integer.
## @end itemize
##
## @subheading Return values
##
## @itemize @bullet
## @item
## @var{x} is a matrix of random samples from the Rayleigh distribution with
## corresponding parameter @var{sigma}. If neither @var{sz} nor @var{r} and
## @var{c} are specified, then @var{x} is of the same size as @var{sigma}.
## @end itemize
##
## @subheading Examples
##
## @example
## @group
## sigma = 1:6;
## x = raylrnd (sigma)
## @end group
##
## @group
## sz = [2, 3];
## x = raylrnd (0.5, sz)
## @end group
##
## @group
## r = 2;
## c = 3;
## x = raylrnd (0.5, r, c)
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
## Processes}. pages 104 and 148, McGraw-Hill, New York, second edition,
## 1984.
## @end enumerate
## @end deftypefn

## Author: Arno Onken <asnelt@asnelt.org>
## Description: Random samples from the Rayleigh distribution

function x = raylrnd (sigma, r, c)

  # Check arguments
  if (nargin == 1)
    sz = size (sigma);
  elseif (nargin == 2)
    if (! isvector (r) || any ((r < 0) | round (r) != r))
      error ("raylrnd: sz must be a vector of non-negative integers")
    endif
    sz = r(:)';
    if (! isscalar (sigma) && ! isempty (sigma) && (length (size (sigma)) != length (sz) || any (size (sigma) != sz)))
      error ("raylrnd: sigma must be scalar or of size sz");
    endif
  elseif (nargin == 3)
    if (! isscalar (r) || any ((r < 0) | round (r) != r))
      error ("raylrnd: r must be a non-negative integer")
    endif
    if (! isscalar (c) || any ((c < 0) | round (c) != c))
      error ("raylrnd: c must be a non-negative integer")
    endif
    sz = [r, c];
    if (! isscalar (sigma) && ! isempty (sigma) && (length (size (sigma)) != length (sz) || any (size (sigma) != sz)))
      error ("raylrnd: sigma must be scalar or of size [r, c]");
    endif
  else
    print_usage ();
  endif

  if (! isempty (sigma) && ! ismatrix (sigma))
    error ("raylrnd: sigma must be a numeric matrix");
  endif

  if (isempty (sigma))
    x = [];
  elseif (isscalar (sigma) && ! (sigma > 0))
    x = NaN .* ones (sz); 
  else
    # Draw random samples
    x = sqrt (-2 .* log (1 - rand (sz)) .* sigma .^ 2);

    # Continue argument check
    k = find (! (sigma > 0));
    if (any (k))
      x(k) = NaN;
    endif
  endif

endfunction

%!test
%! sigma = 1:6;
%! x = raylrnd (sigma);
%! assert (size (x), size (sigma));
%! assert (all (x >= 0));

%!test
%! sigma = 0.5;
%! sz = [2, 3];
%! x = raylrnd (sigma, sz);
%! assert (size (x), sz);
%! assert (all (x >= 0));

%!test
%! sigma = 0.5;
%! r = 2;
%! c = 3;
%! x = raylrnd (sigma, r, c);
%! assert (size (x), [r, c]);
%! assert (all (x >= 0));
