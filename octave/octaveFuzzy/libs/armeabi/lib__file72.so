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
## @deftypefn {Function File} {@var{y} =} is_real_matrix (@var{x})
##
## Return 1 if @var{x} is a non-empty matrix of real or integer-valued scalars,
## and return 0 otherwise.
##
## Examples:
## @example
## @group
## is_real_matrix(6)            ==> 0
## is_real_matrix([])           ==> 0
## is_real_matrix([1 2; 3 4])   ==> 1
## is_real_matrix([1 2 3])      ==> 1
## is_real_matrix([i 2 3])      ==> 0
## is_real_matrix("hello")      ==> 0
## @end group
## @end example
##
## @end deftypefn

## Author:        L. Markowsky
## Keywords:      fuzzy-logic-toolkit fuzzy private parameter-test
## Directory:     fuzzy-logic-toolkit/inst/private/
## Filename:      is_real_matrix.m
## Last-Modified: 20 Aug 2012

function y = is_real_matrix (x)

  if (!ismatrix (x))
    y = 0;
  else
    y = 1;
    for i = 1 : numel (x)
      if (!(isnumeric (x(i)) && isscalar (x(i)) && isreal (x(i))))
        y = 0;
      endif
    endfor
  endif

endfunction
