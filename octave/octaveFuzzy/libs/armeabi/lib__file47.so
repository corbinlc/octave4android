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
## @deftypefn {Function File} {@var{output} =} defuzzify_output_mamdani (@var{fis}, @var{fuzzy_output})
##
## @noindent
## Given the:
## @itemize @bullet
## @item @var{fis.defuzzMethod}
## the defuzzification method for the given @var{fis}
## @item @var{fuzzy_output}
## a matrix of the aggregated output for each FIS output variable
## @end itemize
##
## @noindent
## Return:
## @itemize @bullet
## @item @var{output}
## a vector of crisp output values
## @end itemize
##
## @var{fuzzy_output} is a @var{num_points} x M matrix, where @var{num_points}
## is the number of points over which fuzzy values are evaluated and M is the 
## number of FIS output variables. Each
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
## The crisp @var{output} values are computed from the corresponding fuzzy
## values using the FIS defuzzification method. The @var{output}
## vector has the form:
##
## @example
## output:  [output_1 output_2 ... output_M]
## @end example
##
## Because defuzzify_output_mamdani is called only by the private
## function evalfis_private, it does no error checking of the argument values.
## @end deftypefn

## Author:        L. Markowsky
## Keywords:      fuzzy-logic-toolkit fuzzy inference system fis
## Directory:     fuzzy-logic-toolkit/inst/private/
## Filename:      defuzzify_output_mamdani.m
## Last-Modified: 20 Aug 2012

function output = defuzzify_output_mamdani (fis, fuzzy_output)

  num_outputs = columns (fis.output);        ## num_outputs == L (above)
  num_points = rows (fuzzy_output);
  output = zeros (1, num_outputs);

  for i = 1 : num_outputs
    range = fis.output(i).range;
    x = linspace (range(1), range(2), num_points);
    y = (fuzzy_output(:, i))';
    output(i) = defuzz (x, y, fis.defuzzMethod);
  endfor

endfunction
