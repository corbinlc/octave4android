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
## @deftypefn {Function File} {@var{y} =} is_input_matrix (@var{x}, @var{fis})
##
## Return 1 if @var{x} is a valid matrix of input values for the given FIS
## structure, and return 0 otherwise. The FIS structure @var{fis} is assumed
## to be valid.
##
## @end deftypefn

## Author:        L. Markowsky
## Keywords:      fuzzy-logic-toolkit fuzzy private parameter-test
## Directory:     fuzzy-logic-toolkit/inst/private/
## Filename:      is_input_matrix.m
## Last-Modified: 20 Aug 2012

function y = is_input_matrix (x, fis)

  if (!(ismatrix (x) && isreal (x) && ...
        (columns (x) == columns (fis.input))))
    y = 0;
  else
    y = 1;
    for j = 1 : columns (x)
      range = fis.input(j).range;
      for i = 1 : rows(x)
        if (!(isscalar (x(i, j)) && ...
             x(i,j) >= range(1) && ...
             x(i,j) <= range(2)))
          y = 0;
        endif
      endfor
    endfor
  endif

endfunction
