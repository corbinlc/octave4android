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
## @deftypefn {Function File} {@var{fis} =} setfis (@var{fis}, @var{property}, @var{property_value})
## @deftypefnx {Function File} {@var{fis} =} setfis (@var{fis}, @var{in_or_out}, @var{var_index}, @var{var_property}, @var{var_property_value})
## @deftypefnx {Function File} {@var{fis} =} setfis (@var{fis}, @var{in_or_out}, @var{var_index}, @var{mf}, @var{mf_index}, @var{mf_property}, @var{mf_property_value})
##
## Set a property (field) value of an FIS structure and return the
## updated FIS. There are three forms of setfis:
##
## @table @asis
## @item # Arguments
## Action Taken
## @item 3
## Set a property of the FIS structure. The properties that may
## be set are: name, type, andmethod, ormethod, impmethod,
## addmethod, defuzzmethod, and version.
## @item 5
## Set a property of an input or output variable of the FIS
## structure. The properties that may be set are: name and range.
## @item 7
## Set a property of a membership function. The properties that
## may be set are: name, type, and params.
## @end table
##
## The types of the arguments are expected to be:
## @table @var
## @item fis
## an FIS structure
## @item property
## a string; one of 'name', 'type', 'andmethod',
## 'ormethod', 'impmethod', 'addmethod', 
## 'defuzzmethod', and 'version' (case-insensitive)
## @item property_value
## a number (if property is 'version'); a string (otherwise)
## @item in_or_out
## either 'input' or 'output' (case-insensitive)
## @item var_index
## a valid integer index of an input or output FIS variable
## @item var_property
## a string; either 'name' or 'range'
## @item var_property_value
## a string (if var_property is 'name') or 
## a vector range (if var_property is 'range')
## @item mf
## the string 'mf'
## @item mf_index
## a valid integer index of a membership function 
## @item mf_property
## a string; one of 'name', 'type', or 'params'
## @item mf_property_value
## a string (if mf_property is 'name' or 'type');
## an array (if mf_property is 'params')
## @end table
##
## @noindent
## Note that all of the strings representing properties above are case
## insensitive.
##
## @seealso{newfis, getfis, showfis}
## @end deftypefn

## Author:        L. Markowsky
## Keywords:      fuzzy-logic-toolkit fuzzy inference system fis
## Directory:     fuzzy-logic-toolkit/inst/
## Filename:      setfis.m
## Last-Modified: 20 Aug 2012

##----------------------------------------------------------------------

function fis = setfis (fis, arg2, arg3, arg4 = 'dummy', ...
                       arg5 = 'dummy', arg6 = 'dummy', arg7 = 'dummy')

  switch (nargin)
    case 3  fis = setfis_three_args (fis, arg2, arg3);
    case 5  fis = setfis_five_args (fis, arg2, arg3, arg4, arg5);
    case 7  fis = setfis_six_args (fis, arg2, arg3, arg4, arg5, ...
                                   arg6, arg7);
    otherwise
            puts ("Type 'help setfis' for more information.\n");
            error ("setfis requires 3, 5, or 7 arguments\n");
  endswitch

endfunction

##----------------------------------------------------------------------
## Function: setfis_three_args
## Purpose:  Handle calls to setfis that have 3 arguments. See the
##           comment at the top of this file for more complete info.
##----------------------------------------------------------------------

function fis = setfis_three_args (fis, arg2, arg3)

  ## If not all of the arguments have the correct types, print an error
  ## message and halt.

  if (!is_fis (fis))
    puts ("Type 'help setfis' for more information.\n");
    error ("setfis's first argument must be an FIS structure\n");
  elseif (!(is_string (arg2) && ismember (tolower (arg2), ...
          {'name', 'type', 'andmethod', 'ormethod', 'impmethod', ...
           'aggmethod', 'defuzzmethod', 'version'})))
    puts ("Type 'help setfis' for more information.\n");
    error ("incorrect second argument to setfis\n");
  elseif (strcmp(tolower (arg2), 'version') && !is_real (arg3))
    puts ("Type 'help setfis' for more information.\n");
    error ("the third argument to setfis must be a number\n");
  elseif (!strcmp(tolower (arg2), 'version') && !is_string (arg3))
    puts ("Type 'help setfis' for more information.\n");
    error ("the third argument to setfis must be a string\n");
  endif

  ## Set the property (arg2) of the FIS to the property value (arg3).

  switch (tolower(arg2))
    case 'name'         fis.name = arg3;
    case 'version'      fis.version = arg3;
    case 'type'         fis.type = arg3;
    case 'andmethod'    fis.andMethod = arg3;
    case 'ormethod'     fis.orMethod = arg3;
    case 'impmethod'    fis.impMethod = arg3;
    case 'aggmethod'    fis.aggMethod = arg3;
    case 'defuzzmethod' fis.defuzzMethod = arg3;
  endswitch

endfunction

##----------------------------------------------------------------------
## Function: setfis_five_args
## Purpose:  Handle calls to setfis that have 5 arguments. See the
##           comment at the top of this file for more complete info.
##----------------------------------------------------------------------

function fis = setfis_five_args (fis, arg2, arg3, arg4, arg5)

  ## If not all of the arguments have the correct types, print an error
  ## message and halt.

  if (!is_fis (fis))
    puts ("Type 'help setfis' for more information.\n");
    error ("setfis's first argument must be an FIS structure\n");
  elseif (!(is_string (arg2) && ...
            ismember (tolower (arg2), {'input','output'})))
    puts ("Type 'help setfis' for more information.\n");
    error ("setfis's second argument must be 'input' or 'output'\n");
  elseif (!is_var_index (fis, arg2, arg3))
    puts ("Type 'help setfis' for more information.\n");
    error ("setfis's third argument must be a variable index\n");
  elseif (!(is_string (arg4) && ...
            ismember (tolower (arg4), {'name', 'range'})))
    puts ("Type 'help setfis' for more information.\n");
    error ("setfis's fourth argument must be 'name' or 'range'\n");
  elseif (strcmp (arg4, 'name') && !is_string (arg5))
    puts ("Type 'help setfis' for more information.\n");
    error ("incorrect fifth argument to setfis\n");
  elseif (strcmp (arg4, 'range') && !is_real_matrix (arg5))
    puts ("Type 'help setfis' for more information.\n");
    error ("incorrect fifth argument to setfis\n");
  endif

  ## Set the input or output variable property (arg4) to the
  ## value (arg5).

  switch (tolower (arg2))
    case 'input'
      switch (tolower (arg4))
        case 'name'  fis.input(arg3).name = arg5;
        case 'range' fis.input(arg3).range = arg5;
      endswitch
    case 'output'
      switch (tolower (arg4))
        case 'name'  fis.output(arg3).name = arg5;
        case 'range' fis.output(arg3).range = arg5;
      endswitch
  endswitch

endfunction

##----------------------------------------------------------------------
## Function: setfis_seven_args
## Purpose:  Handle calls to setfis that have 7 arguments. See the
##           comment at the top of this file for more complete info.
##----------------------------------------------------------------------

function fis = setfis_seven_args (fis, arg2, arg3, arg4, arg5, ...
                                  arg6, arg7)

  ## If not all of the arguments have the correct types, print an error
  ## message and halt.

  if (!is_fis (fis))
    puts ("Type 'help setfis' for more information.\n");
    error ("setfis's first argument must be an FIS structure\n");
  elseif (!(is_string (arg2) && ...
            ismember (tolower (arg2), {'input','output'})))
    puts ("Type 'help setfis' for more information.\n");
    error ("setfis's second argument must be 'input' or 'output'\n");
  elseif (!is_var_index (fis, arg2, arg3))
    puts ("Type 'help setfis' for more information.\n");
    error ("setfis's third argument must be a variable index\n");
  elseif (!(is_string (arg4) && strcmp (tolower (arg4), 'mf')))
    puts ("Type 'help setfis' for more information.\n");
    error ("setfis's fourth argument must be 'mf'\n");
  elseif (!is_mf_index (fis, arg2, arg3, arg5))
    puts ("Type 'help setfis' for more information.\n");
    error ("setfis's fifth arg must be a membership function index\n");
  elseif (!(is_string (arg6) && ismember (tolower(arg6), ...
           {'name', 'type', 'params'})))
    puts ("Type 'help setfis' for more information.\n");
    error ("incorrect sixth argument to setfis\n");
  elseif (ismember (tolower (arg6), {'name', 'type'}) && ...
          !is_string (arg7))
    puts ("Type 'help setfis' for more information.\n");
    error ("incorrect seventh argument to setfis\n");
  elseif (strcmp (tolower (arg6), 'params') && !is_real_matrix (arg7))
    puts ("Type 'help setfis' for more information.\n");
    error ("incorrect seventh argument to setfis\n");
  endif

  ## Set the membership function property (arg6) to the value (arg7).

  switch (tolower (arg2))
    case 'input'
      switch (tolower (arg6))
        case 'name'   fis.input(arg3).mf(arg5).name = arg7;
        case 'type'   fis.input(arg3).mf(arg5).type = arg7;
        case 'params' fis.input(arg3).mf(arg5).params = arg7;
      endswitch
    case 'output'
      switch (tolower (arg6))
        case 'name'   fis.output(arg3).mf(arg5).name = arg7;
        case 'type'   fis.output(arg3).mf(arg5).type = arg7;
        case 'params' fis.output(arg3).mf(arg5).params = arg7;
      endswitch
  endswitch

endfunction
