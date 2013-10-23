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
## @deftypefn {Function File} {@var{retval} =} algebraic_product (@var{x})
## @deftypefnx {Function File} {@var{retval} =} algebraic_product (@var{x}, @var{y})
##
## Return the algebraic product of the input.
## The algebraic product of two real scalars x and y is: x * y
##
## For one vector argument, apply the algebraic product to all of elements of
## the vector. (The algebraic product is associative.) For one two-dimensional
## matrix argument, return a vector of the algebraic product of each column.
##
## For two vectors or matrices of identical dimensions, or for one scalar and
## one vector or matrix argument, return the pair-wise product.
##
## @seealso{algebraic_sum, bounded_difference, bounded_sum, drastic_product, drastic_sum, einstein_product, einstein_sum, hamacher_product, hamacher_sum}
## @end deftypefn

## Author:        L. Markowsky
## Keywords:      fuzzy-logic-toolkit fuzzy algebraic_product
## Directory:     fuzzy-logic-toolkit/inst/
## Filename:      algebraic_product.m
## Last-Modified: 18 Aug 2012

function retval = algebraic_product (x, y = 0)
  if (!(isreal (x) && isreal (y)))
    puts ("Arguments to algebraic_product must be real scalars ");
    puts ("or matrices.\n");
    puts ("Type 'help algebraic_product' for more information.\n");
    error ("invalid arguments to function algebraic_product\n");
  elseif (nargin == 2 && ...
          (isscalar (x) || isscalar (y) || ...
           isequal (size (x), size (y))))
    retval = x .* y;
  elseif (nargin == 1 && ndims (x) <= 2)
    retval = prod (x);
  else
    puts ("Type 'help algebraic_product' for more information.\n");
    error ("invalid arguments to function algebraic_product\n");
  endif
endfunction

