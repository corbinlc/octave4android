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
## @deftypefn {Function File} {@var{output} =} defuzzify_output_sugeno (@var{fis}, @var{aggregated_output})
##
## @noindent
## Given the:
## @itemize @bullet
## @item @var{fis.defuzzMethod}
## the defuzzification method for the given @var{fis}
## @item @var{aggregated_output}
## a vector of structures containing the aggregated output for each FIS output variable
## @end itemize
##
## @noindent
## Return:
## @itemize @bullet
## @item @var{output}
## a vector of crisp output values
## @end itemize
##
## The @var{aggregated_output} is a vector of M structures, where M is the
## number of FIS output variables. Each structure contains an index i and a
## matrix of singletons that form the aggregated output for the ith FIS output.
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
## The crisp @var{output} values are computed from the corresponding fuzzy
## values using the FIS defuzzification method. The @var{output}
## vector has the form:
##
## @example
## output:  [output_1 output_2 ... output_M]
## @end example
##
## Because defuzzify_output_sugeno is called only by the private
## function evalfis_private, it does no error checking of the argument values.
## @end deftypefn

## Author:        L. Markowsky
## Keywords:      fuzzy-logic-toolkit fuzzy inference system fis
## Directory:     fuzzy-logic-toolkit/inst/private/
## Filename:      defuzzify_output_sugeno.m
## Last-Modified: 20 Aug 2012

function output = defuzzify_output_sugeno (fis, aggregated_output)

  num_outputs = columns (fis.output);
  output = zeros (1, num_outputs);

  for i = 1 : num_outputs
    next_agg_output = aggregated_output(i).aggregated_output;
    x = next_agg_output(1, :);
    y = next_agg_output(2, :);
    output(i) = defuzz (x, y, fis.defuzzMethod);
  endfor

endfunction
