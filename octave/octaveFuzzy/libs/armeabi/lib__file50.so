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
## @deftypefn {Function File} {@var{output} =} evalfis_private (@var{input}, @var{fis})
## @deftypefnx {Function File} {@var{output} =} evalfis_private (@var{input}, @var{fis}, @var{num_points})
## @deftypefnx {Function File} {[@var{output}, @var{rule_input}, @var{rule_output}, @var{fuzzy_output}] =} evalfis_private (@var{input}, @var{fis})
## @deftypefnx {Function File} {[@var{output}, @var{rule_input}, @var{rule_output}, @var{fuzzy_output}] =} evalfis_private (@var{input}, @var{fis}, @var{num_points})
##
## This function localizes the FIS evaluation common to the public functions
## evalfis and gensurf. All of the arguments to evalfis_private are assumed to
## be valid (limiting the inefficiency of the tests to the calling function).
##
## For more information, see the comments at the top of evalfis.m and gensurf.m.
##
## @end deftypefn

## Author:        L. Markowsky
## Keywords:      fuzzy-logic-toolkit fuzzy inference system fis
## Directory:     fuzzy-logic-toolkit/inst/private/
## Filename:      evalfis_private.m
## Last-Modified: 20 Aug 2012

function [output, rule_input, rule_output, fuzzy_output] = ...
           evalfis_private (user_input, fis, num_points = 101)

  ## Initialize output matrix (to prevent repeated resizing).

  output = zeros (rows (user_input), columns (fis.output));

  ## Process one set of inputs at a time. For each row of crisp input
  ## values in the input matrix, add a row of crisp output values to the
  ## output matrix.

  for i = 1 : rows (user_input)
    rule_input = fuzzify_input (fis, user_input(i, :));
    firing_strength = eval_firing_strength (fis, rule_input);
    if (strcmp (fis.type, 'mamdani'))
      rule_output = eval_rules_mamdani (fis, firing_strength, ...
                                        num_points);
      fuzzy_output = aggregate_output_mamdani (fis, rule_output);
      output(i, :) = defuzzify_output_mamdani (fis, fuzzy_output);
    else
      rule_output = eval_rules_sugeno (fis, firing_strength, ...
                                       user_input(i, :));
      fuzzy_output = aggregate_output_sugeno (fis, rule_output);
      output(i, :) = defuzzify_output_sugeno (fis, fuzzy_output);
    endif
  endfor

endfunction
