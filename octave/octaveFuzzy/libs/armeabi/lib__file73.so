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
## @deftypefn {Function File} {@var{y} =} is_ref_input (@var{x}, @var{fis}, @var{graphed_inputs})
##
## Return 1 if @var{x} is a vector of constants for the FIS structure inputs
## that are not included in the list of inputs, and return 0 otherwise.
##
## @end deftypefn

## Author:        L. Markowsky
## Keywords:      fuzzy-logic-toolkit fuzzy private parameter-test
## Directory:     fuzzy-logic-toolkit/inst/private/
## Filename:      is_ref_input.m
## Last-Modified: 20 Aug 2012

function y = is_ref_input (x, fis, graphed_inputs)

  y = 1;
  num_fis_inputs = columns (fis.input);
  num_graphed_inputs = length (graphed_inputs);

  if (!(is_row_vector (x) && (length (x) == num_fis_inputs)))
    y = 0;
  else
    for i = 1 : num_fis_inputs
      range = fis.input(i).range;
      if (!(isreal (x(i)) && isscalar (x(i))))
        y = 0;
      elseif (!ismember (i, graphed_inputs) && ...
             (x(i) < range(1) || x(i) > range(2)))
        y = 0;
      endif
    endfor
  endif

endfunction
