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
## @deftypefn {Function File} {} showfis (@var{fis})
##
## Print all of the property (field) values of the FIS structure and its
## substructures.
##
## @seealso{getfis, showrule}
## @end deftypefn

## Author:        L. Markowsky
## Keywords:      fuzzy-logic-toolkit fuzzy inference system fis
## Directory:     fuzzy-logic-toolkit/inst/
## Filename:      showfis.m
## Last-Modified: 20 Aug 2012

function showfis (fis)

  ## If getfis was called with an incorrect number of arguments,
  ## or the argument does not have the correct type, print an error
  ## message and halt.

  if (nargin != 1)
    puts ("Type 'help showfis' for more information.\n");
    error ("showfis requires 1 argument\n");
  elseif (!is_fis (fis))
    puts ("Type 'help showfis' for more information.\n");
    error ("showfis's argument must be an FIS structure\n");
  endif

  ## Print properties of the FIS structure.

  ## Determine:
  ##     the number of input variables
  ##     number of output variables
  ##     number of rules
  ##     input membership function names
  ##     input membership function types
  ##     input membership functions parameters
  ##     number of input membership functions
  ##     output membership function names
  ##     output membership function types
  ##     output membership function parameters
  ##     number of output membership functions

  num_inputs = columns(fis.input);
  num_outputs = columns(fis.output);
  num_rules = columns(fis.rule);

  k = 1;
  in_mf_labels = {};
  in_mf_types = {};
  in_mf_params{k} = [];
  for i = 1 : num_inputs
    for j = 1 : columns (fis.input(i).mf)
      in_mf_labels{k} = fis.input(i).mf(j).name;
      in_mf_types{k} = fis.input(i).mf(j).type;
      in_mf_params{k++} = fis.input(i).mf(j).params;
    endfor
  endfor

  num_input_mf = k - 1;

  k = 1;
  out_mf_labels = {};
  out_mf_types = {};
  out_mf_params{k} = [];
  for i = 1 : num_outputs
    for j = 1 : columns (fis.output(i).mf)
      out_mf_labels{k} = fis.output(i).mf(j).name;
      out_mf_types{k} = fis.output(i).mf(j).type;
      out_mf_params{k++} = fis.output(i).mf(j).params;
    endfor
  endfor

  num_output_mf = k - 1;

  ## Print the name, type, and number of inputs/outputs.

  line = 1;
  printf ("%d.  Name             %s\n", line++, fis.name);
  printf ("%d.  Type             %s\n", line++, fis.type);
  printf ("%d.  Inputs/Outputs   [%d %d]\n", line++, num_inputs, ...
          num_outputs);

  ## Print the number of input membership functions.

  printf ("%d.  NumInputMFs      ", line++);
  if (num_inputs == 0)
    printf ("0\n");
  elseif (num_inputs == 1)
    printf ("%d\n", columns(fis.input(1).mf));
  else
    printf("[");
    for i = 1 : num_inputs-1
      printf ("%d ", columns(fis.input(i).mf));
    endfor
    printf ("%d]\n", columns(fis.input(num_inputs).mf));
  endif

  ## Print the number of output membership functions.

  printf ("%d.  NumOutputMFs     ", line++);
  if (num_outputs == 0)
    printf("0\n");
  elseif (num_outputs == 1)
    printf ("%d\n", columns(fis.output(1).mf));
  else
    printf ("[");
    for i = 1 : num_outputs - 1
      printf ("%d ", columns (fis.output(i).mf));
    endfor
    printf ("%d]\n", columns (fis.output(num_outputs).mf));
  endif

  ## Print the number of rules, 'And' method, 'Or' method, 'Implication'
  ## method, 'Aggregation' method, and 'Defuzzification' method.

  printf ("%d.  NumRules         %d\n", line++, num_rules);
  printf ("%d.  AndMethod        %s\n", line++, fis.andMethod);
  printf ("%d.  OrMethod         %s\n", line++, fis.orMethod);
  printf ("%d.  ImpMethod        %s\n", line++, fis.impMethod);
  printf ("%d. AggMethod        %s\n", line++, fis.aggMethod);
  printf ("%d. DefuzzMethod     %s\n", line++, fis.defuzzMethod);

  ## Print the input variable names (labels).

  printf ("%d. InLabels         ", line++);
  if (num_inputs == 0)
    printf ("\n");
  else
    printf ("%s\n", fis.input(1).name);
    for i = 2 : num_inputs
      printf ("%d.                  %s\n", line++, fis.input(i).name);
    endfor
  endif

  ## Print the output variable names (labels).

  printf ("%d. OutLabels        ", line++);
  if (num_outputs == 0)
    printf ("\n");
  else
    printf ("%s\n", fis.output(1).name);
    for i = 2 : num_outputs
      printf ("%d.                  %s\n", line++, fis.output(i).name);
    endfor
  endif

  ## Print the ranges of the input variables.

  printf ("%d. InRange          ", line++);
  if (num_inputs == 0)
    printf ("\n");
  else
    printf ("%s\n", mat2str(fis.input(1).range));
    for i = 2 : num_inputs
      printf ("%d.                  ", line++);
      printf ("%s\n", mat2str(fis.input(i).range));
    endfor
  endif

  ## Print the ranges of the output variables.

  printf ("%d. OutRange         ", line++);
  if (num_outputs == 0)
    printf ("\n");
  else
    printf ("%s\n", mat2str(fis.output(1).range));
    for i = 2 : num_outputs
      printf ("%d.                  ", line++);
      printf ("%s\n", mat2str (fis.output(i).range));
    endfor
  endif

  ## Print the input variables' membership function labels.

  printf ("%d. InMFLabels       ", line++);
  if (num_input_mf == 0)
    printf ("\n");
  else
    printf ("%s\n", in_mf_labels{1});
    for i = 2 : num_input_mf
      printf ("%d.                  %s\n", line++, in_mf_labels{i});
    endfor
  endif

  ## Print the output variables' membership function labels.

  printf ("%d. OutMFLabels      ", line++);
  if (num_output_mf == 0)
    printf ("\n");
  else
    printf ("%s\n", out_mf_labels{1});
    for i = 2 : num_output_mf
      printf ("%d.                  %s\n", line++, out_mf_labels{i});
    endfor
  endif

  ## Print the input variables' membership function types.

  printf ("%d. InMFTypes        ", line++);
  if (num_input_mf == 0)
    printf ("\n");
  else
    printf ("%s\n", in_mf_types{1});
    for i = 2 : num_input_mf
      printf ("%d.                  %s\n", line++, in_mf_types{i});
    endfor
  endif

  ## Print the output variables' membership function types.

  printf ("%d. OutMFTypes       ", line++);
  if (num_output_mf == 0)
    printf ("\n");
  else
    printf ("%s\n", out_mf_types{1});
    for i = 2 : num_output_mf
      printf ("%d.                  %s\n", line++, out_mf_types{i});
    endfor
  endif

  ## Print the input variables' membership function parameters.

  printf ("%d. InMFParams       ", line++);
  if (num_input_mf == 0)
    printf ("\n");
  else
    printf ("%s\n", mat2str(in_mf_params{1}));
    for i = 2 : num_input_mf
      printf ("%d.                  ", line++);
      printf ("%s\n", mat2str (in_mf_params{i}));
    endfor
  endif

  ## Print the output variables' membership function parameters.

  printf ("%d. OutMFParams      ", line++);
  if (num_output_mf == 0)
    printf ("\n");
  else
    printf ("%s\n", mat2str (out_mf_params{1}));
    for i = 2 : num_output_mf
      printf ("%d.                  ", line++);
      printf ("%s\n", mat2str (out_mf_params{i}));
    endfor
  endif

  ## Print the rule antecedents.

  printf("%d. Rule Antecedent  ", line++);
  if (num_rules == 0)
    printf ("\n");
  else
    printf ("%s\n", mat2str (fis.rule(1).antecedent));
    for i = 2 : num_rules
      printf ("%d.                  ", line++);
      printf ("%s\n", mat2str (fis.rule(i).antecedent));
    endfor
  endif

  ## Print the rule consequents.

  printf ("%d. Rule Consequent  ", line++);
  if (num_rules == 0)
    printf ("\n");
  else
    printf ("%s\n", mat2str (fis.rule(1).consequent));
    for i = 2 : num_rules
      printf ("%d.                  ", line++);
      printf ("%s\n", mat2str (fis.rule(i).consequent));
    endfor
  endif

  ## Print the rule weights.

  printf("%d. Rule Weight      ", line++);
  if (num_rules == 0)
    printf ("\n");
  else
    printf ("%d\n", fis.rule(1).weight);
    for i = 2 : num_rules
      printf ("%d.                  %d\n", line++, fis.rule(i).weight);
    endfor
  endif

  ## Print the rule connections.

  printf ("%d. Rule Connection  ", line++);
  if (num_rules == 0)
    printf ("\n");
  else
    printf ("%d\n", fis.rule(1).connection);
    for i = 2 : num_rules
      printf ("%d.                  %d\n", line++, ...
              fis.rule(i).connection);
    endfor
  endif

endfunction
