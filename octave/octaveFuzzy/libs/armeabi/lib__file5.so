## Copyright (C) 2011-2012 L. Markowsky <lmarkov@users.sourceforge.net>
##
## This file is part of the fuzzy-logic-toolkit.
##
## The fuzzy-logic-toolkit is free software; you can redistribute it
## and/or modify it under the terms of the GNU General Public License
## as published by the Free Software Foundation; either version 3 of
## the License, or (at your option) any later version.
##
## The fuzzy-logic-toolkit is distributed in the hope that it will be
## useful, but WITHOUT ANY WARRANTY; without even the implied warranty
## of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
## General Public License for more details.
##
## You should have received a copy of the GNU General Public License
## along with the fuzzy-logic-toolkit; see the file COPYING.  If not,
## see <http://www.gnu.org/licenses/>.

## -*- texinfo -*-
## @deftypefn {Function File} {@var{retval} =} bounded_difference (@var{x})
## @deftypefnx {Function File} {@var{retval} =} bounded_difference (@var{x}, @var{y})
##
## Return the bounded difference of the input.
## The bounded difference of two real scalars x and y is: max (0, x + y - 1)
##
## For one vector argument, apply the bounded difference to all of the elements
## of the vector. (The bounded difference is associative.) For one
## two-dimensional matrix argument, return a vector of the bounded difference
## of each column.
##
## For two vectors or matrices of identical dimensions, or for one scalar and
## one vector or matrix argument, return the pair-wise bounded difference.
##
## @seealso{algebraic_product, algebraic_sum, bounded_sum, drastic_product, drastic_sum, einstein_product, einstein_sum, hamacher_product, hamacher_sum}
## @end deftypefn

## Author:        L. Markowsky
## Keywords:      fuzzy-logic-toolkit fuzzy bounded_difference
## Directory:     fuzzy-logic-toolkit/inst/
## Filename:      bounded_difference.m
## Last-Modified: 18 Aug 2012

function retval = bounded_difference (x, y = 0)
  if (!(isreal (x) && isreal (y)))
    puts ("Function 'bounded_difference' requires real scalar ");
    puts ("or matrix arguments.\n");
    puts ("Type 'help bounded_difference' for more information.\n");
    error ("invalid arguments to function bounded_difference\n");
  elseif (nargin == 2 && ...
          (isscalar (x) || isscalar (y) || ...
           isequal (size (x), size (y))))
    retval = max (0, (x .+ y - 1));
  elseif (nargin == 1 && isvector (x))
    retval = bounded_difference_of_vector (x);
  elseif (nargin == 1 && ndims (x) == 2)
    num_cols = columns (x);
    retval = zeros (1, num_cols);
    for i = 1 : num_cols
      retval(i) = bounded_difference_of_vector (x(:, i));
    endfor
  else
    puts ("Type 'help bounded_difference' for more information.\n");
    error ("invalid arguments to function bounded_difference\n");
  endif
endfunction

function retval = bounded_difference_of_vector (real_vector)
  x = 1;
  for i = 1 : length (real_vector)
    y = real_vector(i);
    x = max (0, (x + y - 1));
  endfor
  retval = x;
endfunction

