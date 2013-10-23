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
## @deftypefn {Function File} {@var{fuzzy_output} =} aggregate_output_mamdani (@var{fis}, @var{rule_output})
##
## @noindent
## Given the:
## @itemize @bullet
## @item @var{fis.aggMethod}
## the aggregation method for the given @var{fis}
## @item @var{rule_output}
## a matrix of the fuzzy output for each (rule, FIS output) pair
## @end itemize
##
## @noindent
## Return:
## @itemize @bullet
## @item @var{fuzzy_output}
## a matrix of the aggregated output for each FIS output variable
## @end itemize
##
## @var{rule_output} is a @var{num_points} x (Q * M) matrix, where
## @var{num_points} is the number of points over which the fuzzy
## values are evaluated, Q is the number of rules and M is the number
## of FIS output variables. Each column of @var{rule_output} gives
## the y-values of the fuzzy output for a single (rule, FIS output)
## pair:
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
## The return value @var{fuzzy_output} is a @var{num_points} x M matrix. Each
## column of @var{fuzzy_output} gives the y-values of the fuzzy output for a
## single FIS output variable, aggregated over all rules:
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
## Because aggregate_output_mamdani is called only by the private
## function evalfis_private, it does no error checking of the argument values.
## @end deftypefn

## Author:        L. Markowsky
## Keywords:      fuzzy-logic-toolkit fuzzy inference system fis
## Directory:     fuzzy-logic-toolkit/inst/private/
## Filename:      aggregate_output_mamdani.m
## Last-Modified: 20 Aug 2012

function fuzzy_output = aggregate_output_mamdani (fis, rule_output)

  num_rules = columns (fis.rule);            ## num_rules   == Q (above)
  num_outputs = columns (fis.output);        ## num_outputs == L
  num_points = rows (rule_output);

  ## Initialize output matrix to prevent inefficient resizing.
  fuzzy_output = zeros (num_points, num_outputs);

  ## Compute the ith fuzzy output values, then store the values in the
  ## ith column of the fuzzy_output matrix.
  for i = 1 : num_outputs
    indiv_fuzzy_out = ...
      rule_output(:, (i - 1) * num_rules + 1 : i * num_rules);
    agg_fuzzy_out = (str2func (fis.aggMethod) (indiv_fuzzy_out'))';
    fuzzy_output(:, i) = agg_fuzzy_out;
  endfor

endfunction
