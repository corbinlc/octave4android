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
## @deftypefn {Function File} {@var{output} =} evalfis (@var{user_input}, @var{fis})
## @deftypefnx {Function File} {@var{output} =} evalfis (@var{user_input}, @var{fis}, @var{num_points})
## @deftypefnx {Function File} {[@var{output}, @var{rule_input}, @var{rule_output}, @var{fuzzy_output}] =} evalfis (@var{user_input}, @var{fis})
## @deftypefnx {Function File} {[@var{output}, @var{rule_input}, @var{rule_output}, @var{fuzzy_output}] =} evalfis (@var{user_input}, @var{fis}, @var{num_points})
##
## Return the crisp output(s) of an FIS for each row in a matrix of crisp input
## values.
## Also, for the last row of @var{user_input}, return the intermediate results:
##
## @table @var
## @item rule_input
## a matrix of the degree to which
## each FIS rule matches each FIS input variable
## @item rule_output
## a matrix of the fuzzy output for each (rule, FIS output) pair
## @item fuzzy_output
## a matrix of the aggregated output for each FIS output variable
## @end table
##
## The optional argument @var{num_points} specifies the number of points over
## which to evaluate the fuzzy values. The default value of @var{num_points} is
## 101.
##
## @noindent
## Argument @var{user_input}:
## 
## @var{user_input} is a matrix of crisp input values. Each row 
## represents one set of crisp FIS input values. For an FIS that has N inputs,
## an input matrix of z sets of input values will have the form:
##
## @example
## @group
## [input_11 input_12 ... input_1N]  <-- 1st row is 1st set of inputs
## [input_21 input_22 ... input_2N]  <-- 2nd row is 2nd set of inputs
## [             ...              ]                 ...
## [input_z1 input_z2 ... input_zN]  <-- zth row is zth set of inputs
## @end group
## @end example
##
## @noindent
## Return value @var{output}:
##
## @var{output} is a matrix of crisp output values. Each row represents
## the set of crisp FIS output values for the corresponding row of
## @var{user_input}. For an FIS that has M outputs, an @var{output} matrix
## corresponding to the preceding input matrix will have the form:
##
## @example
## @group
## [output_11 output_12 ... output_1M]  <-- 1st row is 1st set of outputs
## [output_21 output_22 ... output_2M]  <-- 2nd row is 2nd set of outputs
## [               ...               ]                 ...
## [output_z1 output_z2 ... output_zM]  <-- zth row is zth set of outputs
## @end group
## @end example
##
## @noindent
## The intermediate result @var{rule_input}:
## 
## The matching degree for each (rule, input value) pair is specified by the
## @var{rule_input} matrix. For an FIS that has Q rules and N input variables,
## the matrix will have the form:
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
## @noindent
## Evaluation of hedges and "not":
##
## Each element of each FIS rule antecedent and consequent indicates the
## corresponding membership function, hedge, and whether or not "not" should
## be applied to the result. The index of the membership function to be used is
## given by the positive whole number portion of the antecedent/consequent
## vector entry, the hedge is given by the fractional portion (if any), and
## "not" is indicated by a minus sign. A "0" as the integer portion in any
## position in the rule indicates that the corresponding FIS input or output
## variable is omitted from the rule.
##
## For custom hedges and the four built-in hedges "somewhat," "very,"
## "extremely," and "very very," the membership function value (without the
## hedge or "not") is raised to the power corresponding to the hedge. All
## hedges are rounded to 2 digits.
##
## For example, if "mu(x)" denotes the matching degree of the input to the
## corresponding membership function without a hedge or "not," then the final
## matching degree recorded in @var{rule_input} will be computed by applying
## the hedge and "not" in two steps. First, the hedge is applied:
##
## @example
## @group
## (fraction == .05) <=>  somewhat x       <=>  mu(x)^0.5  <=>  sqrt(mu(x))
## (fraction == .20) <=>  very x           <=>  mu(x)^2    <=>  sqr(mu(x))
## (fraction == .30) <=>  extremely x      <=>  mu(x)^3    <=>  cube(mu(x))
## (fraction == .40) <=>  very very x      <=>  mu(x)^4
## (fraction == .dd) <=>  <custom hedge> x <=>  mu(x)^(dd/10)
## @end group
## @end example
##
## After applying the appropriate hedge, "not" is calculated by:
## @example
## minus sign present           <=> not x         <=> 1 - mu(x)
## minus sign and hedge present <=> not <hedge> x <=> 1 - mu(x)^(dd/10)
## @end example
##
## Hedges and "not" in the consequent are handled similarly.
##
## @noindent
## The intermediate result @var{rule_output}:
##
## For either a Mamdani-type FIS (that is, an FIS that does not have constant or
## linear output membership functions) or a Sugeno-type FIS (that is, an FIS
## that has only constant and linear output membership functions),
## @var{rule_output} specifies the fuzzy output for each (rule, FIS output) pair.
## The format of rule_output depends on the FIS type.
##
## For a Mamdani-type FIS, @var{rule_output} is a @var{num_points} x (Q * M)
## matrix, where Q is the number of rules and M is the number of FIS output
## variables. Each column of this matrix gives the y-values of the fuzzy
## output for a single (rule, FIS output) pair.
##
## @example
## @group
##                  Q cols            Q cols              Q cols 
##             ---------------   ---------------     ---------------
##             out_1 ... out_1   out_2 ... out_2 ... out_M ... out_M
##          1 [                                                     ]
##          2 [                                                     ]
##        ... [                                                     ]
## num_points [                                                     ]
## @end group
## @end example
##
## For a Sugeno-type FIS, @var{rule_output} is a 2 x (Q * M) matrix.
## Each column of this matrix gives the (location, height) pair of the
## singleton output for a single (rule, FIS output) pair.
##
## @example
## @group
##                Q cols            Q cols                  Q cols 
##           ---------------   ---------------         ---------------
##           out_1 ... out_1   out_2 ... out_2   ...   out_M ... out_M
## location [                                                         ]
##   height [                                                         ]
## @end group
## @end example
##
## @noindent
## The intermediate result @var{fuzzy_output}:
##
## The format of @var{fuzzy_output} depends on the FIS type ('mamdani' or
## 'sugeno').
##
## For either a Mamdani-type FIS or a Sugeno-type FIS, @var{fuzzy_output}
## specifies the aggregated fuzzy output for each FIS output.
##
## For a Mamdani-type FIS, the aggregated @var{fuzzy_output} is a
## @var{num_points} x M matrix. Each column of this matrix gives the y-values
## of the fuzzy output for a single FIS output, aggregated over all rules.
##
## @example
## @group
##             out_1  out_2  ...  out_M
##          1 [                        ]
##          2 [                        ]
##        ... [                        ]
## num_points [                        ]
## @end group
## @end example
##
## For a Sugeno-type FIS, the aggregated output for each FIS output is a 2 x L
## matrix, where L is the number of distinct singleton locations in the
## @var{rule_output} for that FIS output:
##
## @example
## @group
##           singleton_1  singleton_2 ... singleton_L
## location [                                        ]
##   height [                                        ]
## @end group
## @end example
##
## Then @var{fuzzy_output} is a vector of M structures, each of which has an index and
## one of these matrices.
##
## @noindent
## Examples:
##
## Seven examples of using evalfis are shown in:
## @itemize @bullet
## @item
## cubic_approx_demo.m
## @item
## heart_disease_demo_1.m
## @item
## heart_disease_demo_2.m
## @item
## investment_portfolio_demo.m
## @item
## linear_tip_demo.m
## @item
## mamdani_tip_demo.m
## @item
## sugeno_tip_demo.m
## @end itemize
##
## @seealso{cubic_approx_demo, heart_disease_demo_1, heart_disease_demo_2, investment_portfolio_demo, linear_tip_demo, mamdani_tip_demo, sugeno_tip_demo}
## @end deftypefn

## Author:        L. Markowsky
## Keywords:      fuzzy-logic-toolkit fuzzy inference system fis
## Directory:     fuzzy-logic-toolkit/inst/
## Filename:      evalfis.m
## Last-Modified: 20 Aug 2012

function [output, rule_input, rule_output, fuzzy_output] = ...
           evalfis (user_input, fis, num_points = 101)

  ## If evalfis was called with an incorrect number of arguments, or
  ## the arguments do not have the correct type, print an error message
  ## and halt.

  if ((nargin != 2) && (nargin != 3))
    puts ("Type 'help evalfis' for more information.\n");
    error ("evalfis requires 2 or 3 arguments\n");
  elseif (!is_fis (fis))
    puts ("Type 'help evalfis' for more information.\n");
    error ("evalfis's second argument must be an FIS structure\n");
  elseif (!is_input_matrix (user_input, fis))
    puts ("Type 'help evalfis' for more information.\n");
    error ("evalfis's 1st argument must be a matrix of input values\n");
  elseif (!is_pos_int (num_points))
    puts ("Type 'help evalfis' for more information.\n");
    error ("evalfis's third argument must be a positive integer\n");
  endif

  ## Call a private function to compute the output.
  ## (The private function is also called by gensurf.)

  [output, rule_input, rule_output, fuzzy_output] = ...
    evalfis_private (user_input, fis, num_points);

endfunction
