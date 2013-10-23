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
## @deftypefn {Function File} {@var{fuzzy_output} =} aggregate_output_sugeno (@var{fis}, @var{rule_output})
##
## @noindent
## Given the:
## @itemize @bullet
## @item @var{fis.aggMethod}
## the aggregation method for the given @var{fis}
## @item @var{rule_output}
## a matrix of the singleton output of each (rule, FIS output) pair
## @end itemize
##
## @noindent
## Return:
## @itemize @bullet
## @item @var{fuzzy_output}
## a vector of structures containing the aggregated output for each FIS output
## @end itemize
##
## @var{rule_output} is a 2 x (Q * M) matrix, where Q is the number of rules
## and M is the number of FIS output variables. Each column of @var{rule_output}
## gives the (location, height) pair of the singleton output for one
## (rule, FIS output) pair:
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
## The return value @var{fuzzy_output} is a vector of M structures,
## each of which has an index i and a matrix of singletons that form the
## aggregated output for the ith FIS output variable.
## For each FIS output variable, the matrix of singletons is a 2 x L matrix
## where L is the number of distinct singleton locations in the fuzzy output
## for that FIS output variable. The first row gives the (distinct) locations,
## and the second gives the (non-zero) heights:
##
## @example
## @group
##           singleton_1  singleton_2 ... singleton_L
## location [                                        ]
##   height [                                        ]
## @end group
## @end example
##
## Because aggregate_output_sugeno is called only by the private
## function evalfis_private, it does no error checking of the argument values.
## @end deftypefn

## Author:        L. Markowsky
## Keywords:      fuzzy-logic-toolkit fuzzy inference system fis
## Directory:     fuzzy-logic-toolkit/inst/private/
## Filename:      aggregate_output_sugeno.m
## Last-Modified: 20 Aug 2012

##----------------------------------------------------------------------

function fuzzy_output = aggregate_output_sugeno (fis, rule_output)

  fuzzy_output = [];
  num_outputs = columns (fis.output);
  num_rules = columns (fis.rule);

  ## For each FIS output, aggregate the slice of the rule_output matrix,
  ## then store the result as a structure in fuzzy_output.

  for i = 1 : num_outputs
    unagg_output = rule_output(:, (i-1)*num_rules+1 : i*num_rules);
    aggregated_output = aggregate_fis_output (fis.aggMethod, ...
                                              unagg_output);
    next_agg_output = struct ('index', i, ...
                              'aggregated_output', aggregated_output);
    if (i == 1)
      fuzzy_output = next_agg_output;
    else
      fuzzy_output = [fuzzy_output, next_agg_output];
    endif
  endfor
endfunction

##----------------------------------------------------------------------
## Function: aggregate_fis_output
## Purpose:  Aggregate the multiple singletons for one FIS output.
##----------------------------------------------------------------------

function mult_singletons = aggregate_fis_output (fis_aggmethod, ...
                                                 rule_output)

  ## Initialize output matrix (multiple_singletons).

  mult_singletons = sortrows (rule_output', 1);

  ## If adjacent rows represent singletons at the same location, then
  ## combine them using the FIS aggregation method.

  for i = 1 : rows (mult_singletons) - 1
    if (mult_singletons(i, 1) == mult_singletons(i+1, 1))
      switch (fis_aggmethod)
        case 'sum'
          mult_singletons(i + 1, 2) = mult_singletons(i, 2) + ...
                                      mult_singletons(i + 1, 2);
        otherwise
          mult_singletons(i + 1, 2) = str2func (fis_aggmethod) ...
                                      (mult_singletons(i, 2), ...
                                       mult_singletons(i + 1, 2));
      endswitch
      mult_singletons(i, 2) = 0;
    endif
  endfor

  ## Return the transpose of the matrix after removing 0-height
  ## singletons.

  mult_singletons = (remove_null_rows (mult_singletons))';
    
endfunction

##----------------------------------------------------------------------
## Function: remove_null_rows
## Purpose:  Return the argument without the rows with a 0 in the
##           second column.
##----------------------------------------------------------------------

function y = remove_null_rows (x)
  y = [];
  for i = 1 : rows (x)
    if (x(i, 2) != 0)
      if (isequal (y, []))
        y = x(i, :);
      else
        y = [y; x(i, :)];
      endif
    endif
  endfor
endfunction
