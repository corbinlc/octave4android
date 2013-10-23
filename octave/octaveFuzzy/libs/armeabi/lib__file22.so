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
## @deftypefn {Function File} {@var{retval} =} getfis (@var{fis})
## @deftypefnx {Function File} {@var{retval} =} getfis (@var{fis}, @var{property})
## @deftypefnx {Function File} {@var{retval} =} getfis (@var{fis}, @var{in_or_out}, @var{var_index})
## @deftypefnx {Function File} {@var{retval} =} getfis (@var{fis}, @var{in_or_out}, @var{var_index}, @var{var_property})
## @deftypefnx {Function File} {@var{retval} =} getfis (@var{fis}, @var{in_or_out}, @var{var_index}, @var{mf}, @var{mf_index})
## @deftypefnx {Function File} {@var{retval} =} getfis (@var{fis}, @var{in_or_out}, @var{var_index}, @var{mf}, @var{mf_index}, @var{mf_property})
##
## Return or print the property (field) values of an FIS structure
## specified by the arguments. There are six forms of getfis:
##
## @table @asis
## @item # Arguments
## Action Taken
## @item 1
## Print (some) properties of an FIS structure on standard output.
## Return the empty set.
## @item 2
## Return a specified property of the FIS structure. The properties
## that may be specified are: name, type, version, numinputs, numoutputs,
## numinputmfs, numoutputmfs, numrules, andmethod, ormethod,
## impmethod, addmethod, defuzzmethod, inlabels, outlabels,
## inrange, outrange, inmfs, outmfs, inmflabels, outmflabels,
## inmftypes, outmftypes, inmfparams, outmfparams, and rulelist.
## @item 3
## Print the properties of a specified input or output variable
## of the FIS structure. Return the empty set.
## @item 4
## Return a specified property of an input or output variable.
## The properties that may be specified are: name, range, nummfs,
## and mflabels.
## @item 5
## Print the properties of a specified membership function of the
## FIS structure. Return the empty set.
## @item 6
## Return a specified property of a membership function. The
## properties that may be specified are: name, type, and params.
## @end table
##
## The types of the arguments are expected to be:
## @table @var
## @item fis
## an FIS structure
## @item property
## a string; one of: 'name', 'type', 'version', 'numinputs',
## 'numoutputs', 'numinputmfs', 'numoutputmfs',
## 'numrules', 'andmethod', 'ormethod', 'impmethod',
## 'addmethod', 'defuzzmethod' 'inlabels', 'outlabels',
## 'inrange', 'outrange', 'inmfs', 'outmfs',
## 'inmflabels', 'outmflabels', 'inmftypes',
## 'outmftypes', 'inmfparams', 'outmfparams', and
## 'rulelist' (case-insensitive)
## @item in_or_out
## either 'input' or 'output' (case-insensitive)
## @item var_index
## a valid integer index of an input or output FIS variable
## @item var_property
## a string; one of: 'name', 'range', 'nummfs', and 'mflabels'
## @item mf
## the string 'mf'
## @item mf_index
## a valid integer index of a membership function 
## @item mf_property
## a string; one of 'name', 'type', or 'params'
## @end table
##
## @noindent
## Note that all of the strings representing properties above are case
## insensitive.
##
## @seealso{setfis, showfis}
## @end deftypefn

## Author:        L. Markowsky
## Keywords:      fuzzy-logic-toolkit fuzzy inference system fis
## Directory:     fuzzy-logic-toolkit/inst/
## Filename:      getfis.m
## Last-Modified: 20 Aug 2012

##----------------------------------------------------------------------

function retval = getfis (fis, arg2 = 'dummy', arg3 = 'dummy', ...
                          arg4 = 'dummy', arg5 = 'dummy', ...
                          arg6 = 'dummy')

  switch (nargin)
    case 1  retval = getfis_one_arg (fis);
    case 2  retval = getfis_two_args (fis, arg2);
    case 3  retval = getfis_three_args (fis, arg2, arg3);
    case 4  retval = getfis_four_args (fis, arg2, arg3, arg4);
    case 5  retval = getfis_five_args (fis, arg2, arg3, arg4, arg5);
    case 6  retval = getfis_six_args (fis, arg2, arg3, arg4, arg5, ...
                                      arg6);
    otherwise
            puts ("Type 'help getfis' for more information.\n");
            error ("getfis requires 1-6 arguments\n");
  endswitch

endfunction

##----------------------------------------------------------------------
## Function: getfis_one_arg
## Purpose:  Handle calls to getfis that have 1 argument. See the
##           comment at the top of this file for more complete info.
##----------------------------------------------------------------------

function retval = getfis_one_arg (fis)

  ## If the argument does not have the correct type, print an error
  ## message and halt.

  if (!is_fis (fis))
    puts ("Type 'help getfis' for more information.\n");
    error ("the first argument to getfis must be an FIS structure\n");
  endif

  ## Print (some) properties of the FIS structure. Return the empty set.

  printf ("Name = %s\n", fis.name);
  printf ("Type = %s\n", fis.type);
  printf ("NumInputs = %d\n", columns(fis.input));
  printf ("InLabels = \n");
  for i = 1 : columns (fis.input)
    printf ("\t%s\n", fis.input(i).name);
  endfor
  printf ("NumOutputs = %d\n", columns(fis.output));
  printf ("OutLabels = \n");
  for i = 1 : columns (fis.output)
    printf ("\t%s\n", fis.output(i).name);
  endfor
  printf ("NumRules = %d\n", columns(fis.rule));
  printf ("AndMethod = %s\n", fis.andMethod);
  printf ("OrMethod = %s\n", fis.orMethod);
  printf ("ImpMethod = %s\n", fis.impMethod);
  printf ("AggMethod = %s\n", fis.aggMethod);
  printf ("DefuzzMethod = %s\n", fis.defuzzMethod);
  retval = [];

endfunction

##----------------------------------------------------------------------
## Function: getfis_two_args
## Purpose:  Handle calls to getfis that have 2 arguments. See the
##           comment at the top of this file for more complete info.
##----------------------------------------------------------------------

function retval = getfis_two_args (fis, arg2)

  ## If not all of the arguments have the correct types, print an error
  ## message and halt.

  if (!is_fis (fis))
    puts ("Type 'help getfis' for more information.\n");
    error ("the first argument to getfis must be an FIS structure\n");
  elseif (!(is_string (arg2) && ismember (tolower (arg2), {'name', ...
           'type', 'version', 'numinputs', 'numoutputs', ...
           'numinputmfs', 'numoutputmfs', 'numrules', 'andmethod', ...
           'ormethod', 'impmethod', 'aggmethod', 'defuzzmethod', ...
           'inlabels', 'outlabels', 'inrange', 'outrange', 'inmfs', ...
           'outmfs', 'inmflabels', 'outmflabels', 'inmftypes', ...
           'outmftypes', 'inmfparams', 'outmfparams', 'rulelist'})))
    puts ("Type 'help getfis' for more information.\n");
    error ("unknown second argument to getfis\n");
  endif

  ## Return the specified property of the FIS structure.

  switch (tolower (arg2))
    case 'name'         retval = fis.name;
    case 'type'         retval = fis.type;
    case 'version'      retval = fis.version;
    case 'numinputs'    retval = columns (fis.input);
    case 'numoutputs'   retval = columns (fis.output);
    case 'numrules'     retval = columns(fis.rule);
    case 'andmethod'    retval = fis.andMethod;
    case 'ormethod'     retval = fis.orMethod;
    case 'impmethod'    retval = fis.impMethod;
    case 'aggmethod'    retval = fis.aggMethod;
    case 'defuzzmethod' retval = fis.defuzzMethod;

    case 'numinputmfs'
      retval = [];
      for i = 1 : columns (fis.input)
        if (i == 1)
          retval = columns(fis.input(i).mf);
        else
          retval = [retval columns(fis.input(i).mf)];
        endif
      endfor

    case 'numoutputmfs'
      retval = [];
      for i = 1 : columns (fis.output)
        if (i == 1)
          retval = columns(fis.output(i).mf);
        else
          retval = [retval columns(fis.output(i).mf)];
        endif
      endfor

    case 'inlabels'
      retval = [];
      for i = 1 : columns (fis.input)
        if (i == 1)
          retval = fis.input(i).name;
        else
          retval = [retval; fis.input(i).name];
        endif
      endfor

    case 'outlabels'
      retval = [];
      for i = 1 : columns (fis.output)
        if (i == 1)
          retval = fis.output(i).name;
        else
          retval = [retval; fis.output(i).name];
        endif
      endfor

    case 'inrange'
      retval = [];
      for i = 1 : columns (fis.input)
        if (i == 1)
          retval = fis.input(i).range;
        else
          retval = [retval; fis.input(i).range];
        endif
      endfor

    case 'outrange'
      retval = [];
      for i = 1 : columns (fis.output)
        if (i == 1)
          retval = fis.output(i).range;
        else
          retval = [retval; fis.output(i).range];
        endif
      endfor

    case 'inmfs'
      retval = [];
      for i = 1 : columns (fis.input)
        if (i == 1)
          retval = columns(fis.input(i).mf);
        else
          retval = [retval columns(fis.input(i).mf)];
        endif
      endfor

    case 'outmfs'
      retval = [];
      for i = 1 : columns (fis.output)
        if (i == 1)
          retval = columns(fis.output(i).mf);
        else
          retval = [retval columns(fis.output(i).mf)];
        endif
      endfor

    case 'inmflabels'
      retval = [];
      for i = 1 : columns (fis.input)
        for j = 1 : columns (fis.input(i).mf)
          if (i == 1 && y == 1)
            retval = fis.input(i).mf(j).name;
          else
            retval = [retval; fis.input(i).mf(j).name];
          endif
        endfor
      endfor

    case 'outmflabels'
      retval = [];
      for i = 1 : columns (fis.output)
        for j = 1 : columns (fis.output(i).mf)
          if (i == 1 && y == 1)
            retval = fis.output(i).mf(j).name;
          else
            retval = [retval; fis.output(i).mf(j).name];
          endif
        endfor
      endfor

    case 'inmftypes'
      retval = [];
      for i = 1 : columns (fis.input)
        for j = 1 : columns (fis.input(i).mf)
          if (i == 1 && y == 1)
            retval = fis.input(i).mf(j).type;
          else
            retval = [retval; fis.input(i).mf(j).type];
          endif
        endfor
      endfor

    case 'outmftypes'
      retval = [];
      for i = 1 : columns (fis.output)
        for j = 1 : columns (fis.output(i).mf)
          if (i == 1 && y == 1)
            retval = fis.output(i).mf(j).type;
          else
            retval = [retval; fis.output(i).mf(j).type];
          endif
        endfor
      endfor

    case 'inmfparams'
      ## Determine the dimensions of the matrix to return.
      max_len = 0;
      num_inputs = columns (fis.input);
      num_mfs = 0;
      for i = 1 : num_inputs
        num_var_i_mfs = columns (fis.input(i).mf);
        num_mfs += num_var_i_mfs;
        for j = 1 : num_var_i_mfs
          max_len = max (max_len, length (fis.input(i).mf(j).params));
        endfor
      endfor

      ## Assemble the matrix of params to return. Pad with zeros.
      retval = zeros (num_mfs, max_len);
      for i = 1 : num_inputs
        for j = 1 : columns (fis.input(i).mf)
          next_row_index = (i - 1) * max_len + j;
          next_row = fis.input(i).mf(j).params;
          retval(next_row_index, 1 : length (next_row)) = next_row;
        endfor
      endfor

    case 'outmfparams'
      ## Determine the dimensions of the matrix to return.
      max_len = 0;
      num_outputs = columns (fis.output);
      num_mfs = 0;
      for i = 1 : num_outputs
        num_var_i_mfs = columns (fis.output(i).mf);
        num_mfs += num_var_i_mfs;
        for j = 1 : num_var_i_mfs
          max_len = max (max_len, length (fis.output(i).mf(j).params));
        endfor
      endfor

      ## Assemble the matrix of params to return. Pad with zeros.
      retval = zeros (num_mfs, max_len);
      for i = 1 : num_outputs
        for j = 1 : columns (fis.output(i).mf)
          next_row_index = (i - 1) * max_len + j;
          next_row = fis.output(i).mf(j).params;
          retval(next_row_index, 1 : length (next_row)) = next_row;
        endfor
      endfor

    case 'rulelist'
      ## Determine the dimensions of the matrix to return.
      num_inputs = columns (fis.input);
      num_outputs = columns (fis.output);
      num_rules = columns (fis.rule);

      ## Assemble the matrix of rules to return.
      retval = zeros (num_rules, num_inputs + num_outputs + 2);
      for i = 1 : num_rules
        retval(i, 1:num_inputs) = fis.rule(i).antecedent;
        retval(i, num_inputs+1:num_inputs+num_outputs) = ...
          fis.rule(i).consequent;
        retval(i, num_inputs+num_outputs+1) = fis.rule(i).weight;
        retval(i, num_inputs+num_outputs+2) = fis.rule(i).connection;
      endfor

    otherwise
      error ("internal error in getfis_two_args");
  endswitch
endfunction

##----------------------------------------------------------------------
## Function: getfis_three_args
## Purpose:  Handle calls to getfis that have 3 arguments. See the
##           comment at the top of this file for more complete info.
##----------------------------------------------------------------------

function retval = getfis_three_args (fis, arg2, arg3)

  ## If not all of the arguments have the correct types, print an error
  ## message and halt.

  if (!is_fis (fis))
    puts ("Type 'help getfis' for more information.\n");
    error ("the first argument to getfis must be an FIS structure\n");
  elseif (!(is_string (arg2) && ...
            ismember (tolower (arg2), {'input','output'})))
    puts ("Type 'help getfis' for more information.\n");
    error ("incorrect second argument to getfis\n");
  elseif (!is_var_index (fis, arg2, arg3))
    puts ("Type 'help getfis' for more information.\n");
    error ("incorrect third argument to getfis\n");
  endif

  ## Print the properties of a specified input or output variable of the
  ## FIS structure. Return the empty set.

  var_str = ["fis." tolower(arg2) "(" num2str(arg3) ")"];
  var_mf_str = [var_str ".mf"];
  num_mfs = columns (eval (var_mf_str));
  printf ("Name = %s\n", eval ([var_str ".name"]));
  printf ("NumMFs = %d\n", num_mfs);
  printf ("MFLabels = \n");
  for i = 1 : num_mfs
    printf ("\t%s\n", eval ([var_mf_str "(" num2str(i) ").name"]));
  endfor
  printf ("Range = %s\n", mat2str (eval ([var_str ".range"])));
  retval = [];

endfunction

##----------------------------------------------------------------------
## Function: getfis_four_args
## Purpose:  Handle calls to getfis that have 4 arguments. See the
##           comment at the top of this file for more complete info.
##----------------------------------------------------------------------

function retval = getfis_four_args (fis, arg2, arg3, arg4)

  ## If not all of the arguments have the correct types, print an error
  ## message and halt.

  if (!is_fis (fis))
    puts ("Type 'help getfis' for more information.\n");
    error ("the first argument to getfis must be an FIS structure\n");
  elseif (!(is_string (arg2) && ...
            ismember (tolower (arg2), {'input','output'})))
    puts ("Type 'help getfis' for more information.\n");
    error ("incorrect second argument to getfis\n");
  elseif (!is_var_index (fis, arg2, arg3))
    puts ("Type 'help getfis' for more information.\n");
    error ("incorrect third argument to getfis\n");
  elseif (!(is_string (arg4) && ismember (tolower (arg4), ...
           {'name', 'range', 'nummfs', 'mflabels'})))
    puts ("Type 'help getfis' for more information.\n");
    error ("incorrect fourth argument to getfis\n");
  endif

  ## Return the specified property of the FIS input or output variable.

  arg2 = tolower (arg2);
  arg4 = tolower (arg4);
  if (ismember (arg4, {'name', 'range'}))
    retval = eval (["fis." arg2 "(" num2str(arg3) ")." arg4]);
  elseif (strcmp (arg4, 'nummfs'))
    retval = columns (eval (["fis." arg2 "(" num2str(arg3) ").mf"]));
  elseif (strcmp (arg2, 'input') && strcmp (arg4, 'mflabels'))
    retval = [];
    for i = 1 : columns (fis.input)
      for j = 1 : columns (fis.input(i).mf)
        retval = [retval; fis.input(i).mf(j).name];
      endfor
    endfor
  elseif (strcmp (arg2, 'output') && strcmp (arg4, 'mflabels'))
    retval = [];
    for i = 1 : columns (fis.output)
      for j = 1 : columns (fis.output(i).mf)
        retval = [retval; fis.output(i).mf(j).name];
      endfor
    endfor
  endif

endfunction

##----------------------------------------------------------------------
## Function: getfis_five_args
## Purpose:  Handle calls to getfis that have 5 arguments. See the
##           comment at the top of this file for more complete info.
##----------------------------------------------------------------------

function retval = getfis_five_args (fis, arg2, arg3, arg4, arg5)

  ## If not all of the arguments have the correct types, print an error
  ## message and halt.

  if (!is_fis (fis))
    puts ("Type 'help getfis' for more information.\n");
    error ("the first argument to getfis must be an FIS structure\n");
  elseif (!(is_string(arg2) && ...
            ismember(tolower(arg2), {'input','output'})))
    puts ("Type 'help getfis' for more information.\n");
    error ("incorrect second argument to getfis\n");
  elseif (!is_var_index(fis, arg2, arg3))
    puts ("Type 'help getfis' for more information.\n");
    error ("incorrect third argument to getfis\n");
  elseif (!(is_string(arg4) && isequal(tolower(arg4), 'mf')))
    puts ("Type 'help getfis' for more information.\n");
    error ("incorrect fourth argument to getfis\n");
  elseif (!is_mf_index(fis, arg2, arg3, arg5))
    puts ("Type 'help getfis' for more information.\n");
    error ("incorrect fifth argument to getfis\n");
  endif

  ## Print the properties of a specified membership function of the
  ## FIS structure. Return the empty set.

  var_mf_str = ["fis." tolower(arg2) "(" num2str(arg3) ").mf(" ...
                 num2str(arg5) ")"];
  printf ("Name = %s\n", eval ([var_mf_str ".name"]));
  printf ("Type = %s\n", eval ([var_mf_str ".type"]));
  printf ("Params = ");
  disp (eval ([var_mf_str ".params"]));
  retval = [];

endfunction

##----------------------------------------------------------------------
## Function: getfis_six_args
## Purpose:  Handle calls to getfis that have 6 arguments. See the
##           comment at the top of this file for more complete info.
##----------------------------------------------------------------------

function retval = getfis_six_args (fis, arg2, arg3, arg4, arg5, arg6)

  ## If not all of the arguments have the correct types, print an error
  ## message and halt.

  if (!is_fis (fis))
    puts ("Type 'help getfis' for more information.\n");
    error ("the first argument to getfis must be an FIS structure\n");
  elseif (!(is_string (arg2) && ...
            ismember (tolower (arg2), {'input','output'})))
    puts ("Type 'help getfis' for more information.\n");
    error ("incorrect second argument to getfis\n");
  elseif (!is_var_index (fis, arg2, arg3))
    puts ("Type 'help getfis' for more information.\n");
    error ("incorrect third argument to getfis\n");
  elseif (!(is_string (arg4) && isequal (tolower (arg4), 'mf')))
    puts ("Type 'help getfis' for more information.\n");
    error ("incorrect fourth argument to getfis\n");
  elseif (!is_mf_index (fis, arg2, arg3, arg5))
    puts ("Type 'help getfis' for more information.\n");
    error ("incorrect fifth argument to getfis\n");
  elseif (!(is_string (arg6) && ismember (tolower (arg6), ...
           {'name', 'type', 'params'})))
    puts ("Type 'help getfis' for more information.\n");
    error ("incorrect sixth argument to getfis\n");
  endif

  ## Return the specified membership function property.

  retval = eval (["fis." tolower(arg2) "(" num2str(arg3) ").mf(" ...
                  num2str(arg5) ")." tolower(arg6)]);

endfunction
