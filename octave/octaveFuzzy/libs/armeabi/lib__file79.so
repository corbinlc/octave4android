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
## @deftypefn {Function File} {@var{y} =} is_var_index (@var{fis}, @var{in_or_out}, @var{var_index})
##
## If @var{in_or_out} == 'input', return 1 if @var{var_index} is a valid input
## variable index for the given FIS structure, and return 0 otherwise.
##
## If @var{in_or_out} == 'output', return 1 if @var{var_index} is a valid output
## variable index for the given FIS structure, and return 0 otherwise.
##
## is_var_index is a private function that localizes the test for valid FIS
## input and output variable indices. The arguments @var{fis} and
## @var{in_or_out} are assumed to be valid.
##
## @end deftypefn

## Author:        L. Markowsky
## Keywords:      fuzzy-logic-toolkit fuzzy private parameter-test
## Directory:     fuzzy-logic-toolkit/inst/private/
## Filename:      is_var_index.m
## Last-Modified: 20 Aug 2012

function y = is_var_index (fis, in_or_out, var_index)

  y = is_int (var_index) && (var_index >= 1);
  if (strcmp (in_or_out, 'input'))
    y = y && (var_index <= length (fis.input));
  else
    y = y && (var_index <= length (fis.output));
  endif

endfunction
