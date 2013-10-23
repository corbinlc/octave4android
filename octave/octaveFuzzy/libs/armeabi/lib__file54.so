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
## @deftypefn {Function File} {@var{rule_input} =} fuzzify_input (@var{fis}, @var{user_input})
##
## Return the matching degree for each (rule, input value) pair.
## For an FIS that has Q rules and N FIS input variables, the return value
## will be a Q x N matrix.
##
## @noindent
## The crisp input values are given by a row vector:
##
## @example
## user_input:  [input_1 input_2 ... input_N]
## @end example
##
## @noindent
## The rule antecedents are stored in the FIS structure as row vectors:
##
## @example
## @group
## rule 1 antecedent: [in_11 in_12 ... in_1N]
## rule 2 antecedent: [in_21 in_22 ... in_2N]
##        ...                 ...
## rule Q antecedent: [in_Q1 in_Q2 ... in_QN]
## @end group
## @end example
##
## @noindent
## Finally, the output of the function gives the matching degree
## for each (rule, input value) pair as an Q x N matrix:
##
## @example
## @group
##          in_1  in_2 ...  in_N
## rule_1 [mu_11 mu_12 ... mu_1N]
## rule_2 [mu_21 mu_22 ... mu_2N]
##        [            ...      ]
## rule_Q [mu_Q1 mu_Q2 ... mu_QN]
## @end group
## @end example
##
## Because fuzzify_input is called only by the private function
## evalfis_private, it does no error checking of the argument values.
##
## @end deftypefn

## Author:        L. Markowsky
## Keywords:      fuzzy-logic-toolkit fuzzy inference system fis
## Directory:     fuzzy-logic-toolkit/inst/private/
## Filename:      fuzzify_input.m
## Last-Modified: 20 Aug 2012

function rule_input = fuzzify_input (fis, user_input)

  num_rules = columns (fis.rule);             ## num_rules  == Q (above)
  num_inputs = columns (fis.input);           ## num_inputs == N
  rule_input = zeros (num_rules, num_inputs); ## to prevent resizing

  ## For each rule i and each input j, compute the value of mu
  ## in the result.

  for i = 1 : num_rules
    antecedent = fis.rule(i).antecedent;
    for j = 1 : num_inputs
      mu = 0;
      crisp_x = user_input(j);

      ## Get the value of mu (with adjustment for the hedge
      ## and not_flag).

      [mf_index hedge not_flag] = ...
        get_mf_index_and_hedge (antecedent(j));
      if (mf_index != 0)
        mf = fis.input(j).mf(mf_index);
        mu = evalmf (crisp_x, mf.params, mf.type, hedge, not_flag);
      endif

      ## Store the fuzzified input in rule_input.

      rule_input(i, j) = mu;
    endfor
  endfor

endfunction
