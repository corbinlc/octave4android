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
## @deftypefn {Function File} {@var{y} =} are_input_indices (@var{x}, @var{fis})
##
## Return 1 if @var{x} is a valid input index or a vector of two valid input
## indices for the given FIS structure, and return 0 otherwise. The FIS
## structure @var{fis} is assumed to be valid.
##
## @end deftypefn

## Author:        L. Markowsky
## Keywords:      fuzzy-logic-toolkit fuzzy private parameter-test
## Directory:     fuzzy-logic-toolkit/inst/private/
## Filename:      are_input_indices.m
## Last-Modified: 20 Aug 2012

function y = are_input_indices (x, fis)

  if (!(isreal (x) && isvector (x) && (length (x) <= 2)))
    y = 0;
  else
    y = 1;
    num_inputs = columns (fis.input);
    for next_x = x
      if (!(is_pos_int (next_x) && next_x <= num_inputs))
        y = 0;
      endif
    endfor
  endif

endfunction
