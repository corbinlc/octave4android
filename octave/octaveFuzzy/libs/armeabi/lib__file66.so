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
## @deftypefn {Function File} {@var{y} =} is_mf_index (@var{fis}, @var{in_or_out}, @var{var_index}, @var{mf_index})
##
## If @var{in_or_out} == 'input', return 1 if @var{mf_index} is a valid
## membership function index for the input variable with index @var{var_index},
## and return 0 otherwise.
##
## If @var{in_or_out} == 'output', return 1 if @var{mf_index} is a valid
## membership function index for the output variable with index @var{var_index},
## and return 0 otherwise.
##
## is_mf_index is a private function that localizes the test for valid FIS
## membership function indices. The arguments @var{fis}, @var{in_or_out}, and
## @var{var_index} are assumed to be valid.
##
## @end deftypefn

## Author:        L. Markowsky
## Keywords:      fuzzy-logic-toolkit fuzzy private parameter-test
## Directory:     fuzzy-logic-toolkit/inst/private/
## Filename:      is_mf_index.m
## Last-Modified: 20 Aug 2012

function y = is_mf_index (fis, in_or_out, var_index, mf_index)

  y = is_int (mf_index) && (mf_index >= 1);
  if (strcmp (in_or_out, 'input'))
    y = y && (mf_index <= length (fis.input(var_index).mf));
  else
    y = y && (mf_index <= length (fis.output(var_index).mf));
  endif

endfunction
