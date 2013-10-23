## Copyright (C) 2012  Arno Onken
##
## This program is free software: you can redistribute it and/or modify
## it under the terms of the GNU General Public License as published by
## the Free Software Foundation, either version 3 of the License, or
## (at your option) any later version.
##
## This program is distributed in the hope that it will be useful,
## but WITHOUT ANY WARRANTY; without even the implied warranty of
## MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
## GNU General Public License for more details.
##
## You should have received a copy of the GNU General Public License
## along with this program.  If not, see <http://www.gnu.org/licenses/>.

## -*- texinfo -*-
## @deftypefn {Function File} {@var{x} =} copularnd (@var{family}, @var{theta}, @var{n})
## @deftypefnx {Function File} {} copularnd (@var{family}, @var{theta}, @var{n}, @var{d})
## @deftypefnx {Function File} {} copularnd ('t', @var{theta}, @var{nu}, @var{n})
## Generate random samples from a copula family.
##
## @subheading Arguments
##
## @itemize @bullet
## @item
## @var{family} is the copula family name. Currently, @var{family} can be
## @code{'Gaussian'} for the Gaussian family, @code{'t'} for the Student's t
## family, or @code{'Clayton'} for the Clayton family.
##
## @item
## @var{theta} is the parameter of the copula. For the Gaussian and Student's t
## copula, @var{theta} must be a correlation matrix. For bivariate copulas
## @var{theta} can also be a correlation coefficient. For the Clayton family,
## @var{theta} must be a vector with the same number of elements as samples to
## be generated or be scalar.
##
## @item
## @var{nu} is the degrees of freedom for the Student's t family. @var{nu} must
## be a vector with the same number of elements as samples to be generated or
## be scalar.
##
## @item
## @var{n} is the number of rows of the matrix to be generated. @var{n} must be
## a non-negative integer and corresponds to the number of samples to be
## generated.
##
## @item
## @var{d} is the number of columns of the matrix to be generated. @var{d} must
## be a positive integer and corresponds to the dimension of the copula.
## @end itemize
##
## @subheading Return values
##
## @itemize @bullet
## @item
## @var{x} is a matrix of random samples from the copula with @var{n} samples
## of distribution dimension @var{d}.
## @end itemize
##
## @subheading Examples
##
## @example
## @group
## theta = 0.5;
## x = copularnd ("Gaussian", theta);
## @end group
##
## @group
## theta = 0.5;
## nu = 2;
## x = copularnd ("t", theta, nu);
## @end group
##
## @group
## theta = 0.5;
## n = 2;
## x = copularnd ("Clayton", theta, n);
## @end group
## @end example
##
## @subheading References
##
## @enumerate
## @item
## Roger B. Nelsen. @cite{An Introduction to Copulas}. Springer, New York,
## second edition, 2006.
## @end enumerate
## @end deftypefn

## Author: Arno Onken <asnelt@asnelt.org>
## Description: Random samples from a copula family

function x = copularnd (family, theta, nu, n)

  # Check arguments
  if (nargin < 2)
    print_usage ();
  endif

  if (! ischar (family))
    error ("copularnd: family must be one of 'Gaussian', 't', and 'Clayton'");
  endif

  lower_family = lower (family);

  # Check family and copula parameters
  switch (lower_family)

    case {"gaussian"}
      # Gaussian family
      if (isscalar (theta))
        # Expand a scalar to a correlation matrix
        theta = [1, theta; theta, 1];
      endif
      if (! ismatrix (theta) || any (diag (theta) != 1) || any (any (theta != theta')) || min (eig (theta)) <= 0)
        error ("copularnd: theta must be a correlation matrix");
      endif
      if (nargin > 3)
        d = n;
        if (! isscalar (d) || d != size (theta, 1))
          error ("copularnd: d must correspond to dimension of theta");
        endif
      else
        d = size (theta, 1);
      endif
      if (nargin < 3)
        n = 1;
      else
        n = nu;
        if (! isscalar (n) || (n < 0) || round (n) != n)
          error ("copularnd: n must be a non-negative integer");
        endif
      endif

    case {"t"}
      # Student's t family
      if (nargin < 3)
        print_usage ();
      endif
      if (isscalar (theta))
        # Expand a scalar to a correlation matrix
        theta = [1, theta; theta, 1];
      endif
      if (! ismatrix (theta) || any (diag (theta) != 1) || any (any (theta != theta')) || min (eig (theta)) <= 0)
        error ("copularnd: theta must be a correlation matrix");
      endif
      if (! isscalar (nu) && (! isvector (nu) || length (nu) != n))
        error ("copularnd: nu must be a vector with the same number of rows as x or be scalar");
      endif
      nu = nu(:);
      if (nargin < 4)
        n = 1;
      else
        if (! isscalar (n) || (n < 0) || round (n) != n)
          error ("copularnd: n must be a non-negative integer");
        endif
      endif

    case {"clayton"}
      # Archimedian one parameter family
      if (nargin < 4)
        # Default is bivariate
        d = 2;
      else
        d = n;
        if (! isscalar (d) || (d < 2) || round (d) != d)
          error ("copularnd: d must be an integer greater than 1");
        endif
      endif
      if (nargin < 3)
        # Default is one sample
        n = 1;
      else
        n = nu;
        if (! isscalar (n) || (n < 0) || round (n) != n)
          error ("copularnd: n must be a non-negative integer");
        endif
      endif
      if (! isvector (theta) || (! isscalar (theta) && size (theta, 1) != n))
        error ("copularnd: theta must be a column vector with the number of rows equal to n or be scalar");
      endif
      if (n > 1 && isscalar (theta))
        theta = repmat (theta, n, 1);
      endif

    otherwise
      error ("copularnd: unknown copula family '%s'", family);

  endswitch

  if (n == 0)
    # Input is empty
    x = zeros (0, d);
  else

    # Draw random samples according to family
    switch (lower_family)

      case {"gaussian"}
        # The Gaussian family
        x = normcdf (mvnrnd (zeros (1, d), theta, n), 0, 1);
        # No parameter bounds check
        k = [];

      case {"t"}
        # The Student's t family
        x = tcdf (mvtrnd (theta, nu, n), nu);
        # No parameter bounds check
        k = [];

      case {"clayton"}
        # The Clayton family
        u = rand (n, d);
        if (d == 2)
          x = zeros (n, 2);
          # Conditional distribution method for the bivariate case which also
          # works for theta < 0
          x(:, 1) = u(:, 1);
          x(:, 2) = (1 + u(:, 1) .^ (-theta) .* (u(:, 2) .^ (-theta ./ (1 + theta)) - 1)) .^ (-1 ./ theta);
        else
          # Apply the algorithm by Marshall and Olkin:
          # Frailty distribution for Clayton copula is gamma
          y = randg (1 ./ theta, n, 1);
          x = (1 - log (u) ./ repmat (y, 1, d)) .^ (-1 ./ repmat (theta, 1, d));
        endif
        k = find (theta == 0);
        if (any (k))
          # Produkt copula at columns k
          x(k, :) = u(k, :);
        endif
        # Continue argument check
        if (d == 2)
          k = find (! (theta >= -1) | ! (theta < inf));
        else
          k = find (! (theta >= 0) | ! (theta < inf));
        endif

    endswitch

    # Out of bounds parameters
    if (any (k))
      x(k, :) = NaN;
    endif

  endif

endfunction

%!test
%! theta = 0.5;
%! x = copularnd ("Gaussian", theta);
%! assert (size (x), [1, 2]);
%! assert (all ((x >= 0) & (x <= 1)));

%!test
%! theta = 0.5;
%! nu = 2;
%! x = copularnd ("t", theta, nu);
%! assert (size (x), [1, 2]);
%! assert (all ((x >= 0) & (x <= 1)));

%!test
%! theta = 0.5;
%! x = copularnd ("Clayton", theta);
%! assert (size (x), [1, 2]);
%! assert (all ((x >= 0) & (x <= 1)));

%!test
%! theta = 0.5;
%! n = 2;
%! x = copularnd ("Clayton", theta, n);
%! assert (size (x), [n, 2]);
%! assert (all ((x >= 0) & (x <= 1)));

%!test
%! theta = [1; 2];
%! n = 2;
%! d = 3;
%! x = copularnd ("Clayton", theta, n, d);
%! assert (size (x), [n, d]);
%! assert (all ((x >= 0) & (x <= 1)));
