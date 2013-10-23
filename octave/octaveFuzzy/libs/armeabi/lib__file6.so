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
## @deftypefn {Function File} {@var{retval} =} bounded_sum (@var{x})
## @deftypefnx {Function File} {@var{retval} =} bounded_sum (@var{x}, @var{y})
##
## Return the bounded sum of the input.
## The bounded sum of two real scalars x and y is: min (1, x + y)
##
## For one vector argument, apply the bounded sum to all of elements of
## the vector. (The bounded sum is associative.) For one two-dimensional
## matrix argument, return a vector of the bounded sum of each column.
##
## For two vectors or matrices of identical dimensions, or for one scalar and
## one vector or matrix argument, return the pair-wise bounded sum.
##
## @seealso{algebraic_product, algebraic_sum, bounded_difference, drastic_product, drastic_sum, einstein_product, einstein_sum, hamacher_product, hamacher_sum}
## @end deftypefn

## Author:        L. Markowsky
## Keywords:      fuzzy-logic-toolkit fuzzy bounded_sum
## Directory:     fuzzy-logic-toolkit/inst/
## Filename:      bounded_sum.m
## Last-Modified: 18 Aug 2012

function retval = bounded_sum (x, y = 0)
  if (!(isreal (x) && isreal (y)))
    puts ("Type 'help bounded_sum' for more information.\n");
    error ("bounded_sum requires real scalar or matrix arguments\n");
  elseif (nargin == 2 && ...
          (isscalar (x) || isscalar (y) || ...
           isequal (size (x), size (y))))
    retval = min (1, (x .+ y));
  elseif (nargin == 1 && isvector (x))
    retval = bounded_sum_of_vector (x);
  elseif (nargin == 1 && ndims (x) == 2)
    num_cols = columns (x);
    retval = zeros (1, num_cols);
    for i = 1 : num_cols
      retval(i) = bounded_sum_of_vector (x(:, i));
    endfor
  else
    puts ("Type 'help bounded_sum' for more information.\n");
    error ("invalid arguments to function bounded_sum\n");
  endif
endfunction

function retval = bounded_sum_of_vector (real_vector)
  x = 0;
  for i = 1 : length (real_vector)
    y = real_vector(i);
    x = min (1, (x + y));
  endfor
  retval = x;
endfunction

