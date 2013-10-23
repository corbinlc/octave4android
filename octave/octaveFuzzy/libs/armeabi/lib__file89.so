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
## @deftypefn {Function File} {} showrule (@var{fis})
## @deftypefnx {Function File} {} showrule (@var{fis}, @var{index_list})
## @deftypefnx {Function File} {} showrule (@var{fis}, @var{index_list}, @var{format})
## @deftypefnx {Function File} {} showrule (@var{fis}, @var{index_list}, @var{'verbose'}, @var{language})
## @deftypefnx {Function File} {} showrule (@var{fis}, @var{index_list}, @var{'verbose'}, @var{'custom'}, @var{"and" "or" "If" "then" "is" "isn't" "somewhat" "very" "extremely" "very very"})
##
##
## Show the rules for an FIS structure in verbose, symbolic, or indexed format.
## Built in languages for the 'verbose' format are: English,
## Chinese (or Mandarin, Pinyin), Russian (or Pycckii, Russkij), French (or Francais),
## Spanish (or Espanol), and German (or Deutsch). The names of the languages are
## case-insensitive, Chinese is written in Pinyin, and Russian is transliterated.
##
## To use a custom language, enter 'verbose' and 'custom' for the third and
## fourth parameters, respectively, and a cell array of ten strings (to specify
## the custom language) corresponding to the English @{"and" "or" "If" "then"
## "is" "isn't" "somewhat" "very" "extremely" "very very"@} for the fifth
## parameter.
##
## @noindent
## To run the demonstration code, type @t{demo('showrule')} at the
## Octave prompt.
##
## @seealso{addrule, getfis, showfis}
## @end deftypefn

## Author:        L. Markowsky
## Keywords:      fuzzy-logic-toolkit fuzzy rule
## Directory:     fuzzy-logic-toolkit/inst/
## Filename:      showrule.m
## Last-Modified: 20 Aug 2012

function showrule (fis, index_list = [], format = 'verbose', ...
                   language = 'english', ...
                   verbose_strings = {"and" "or" "If" "then" "is" ...
                                      "isn't" "somewhat" "very" ...
                                      "extremely" "very very"})

  ##--------------------------------------------------------------------
  ## If the caller did not supply between 1 and 5 arguments with the
  ## correct types, print an error message and halt.
  ##--------------------------------------------------------------------

  if (!(nargin >= 1 && nargin <= 5))
    puts ("Type 'help showrule' for more information.\n");
    error ("showrule requires between 1 and 5 arguments\n");
  elseif (!is_fis (fis))
    puts ("Type 'help showrule' for more information.\n");
    error ("showrule's first argument must be an FIS structure\n");
  elseif ((nargin >= 2) && ...
          !is_rule_index_list (index_list, length (fis.rule)))
    puts ("Type 'help showrule' for more information.\n");
    error ("showrule's second arg must be a vector of rule indices\n");
  elseif ((nargin >= 3) && !is_format (format))
    puts ("Type 'help showrule' for more information.\n");
    error ("showrule's third argument must specify the format\n");
  elseif ((nargin == 4) && isequal (tolower (language), "custom"))
    puts ("Type 'help showrule' for more information.\n");
    error ("please specify custom verbose strings in the fifth arg\n");
  elseif ((nargin == 4) && !is_builtin_language (language))
    puts ("Type 'help showrule' for more information.\n");
    error ("showrule's fourth arg must specify a built-in language\n");
  elseif ((nargin == 5) && !isequal (tolower (language), "custom"))
    puts ("Type 'help showrule' for more information.\n");
    error ("use 'custom' for the 4th arg to specify custom strings\n");
  endif

  ##--------------------------------------------------------------------
  ## If showrule was called with only one argument, create the default
  ## index list (all rule indices, in ascending order).
  ##--------------------------------------------------------------------

  if (nargin == 1)
    index_list = 1 : length (fis.rule);
  endif

  ##--------------------------------------------------------------------
  ## Show the rules in indexed, symbolic, or verbose format.
  ##--------------------------------------------------------------------

  switch (tolower (format))
    case 'indexed'
      showrule_indexed_format (fis, index_list);
    case 'symbolic'
      showrule_symbolic_format (fis, index_list);
    case 'verbose'
      showrule_verbose_format (fis, index_list, language, ...
                               verbose_strings);
  endswitch

endfunction

##----------------------------------------------------------------------
## Function: get_verbose_hedge
## Purpose:  For no hedge, return the empty string.
##           For the built-in hedges, return the verbose string in the
##           language used in the cell array verbose_strings (the second
##           parameter). For custom hedges, return the power (rounded to
##           two digits) to which the membership function matching value
##           will be raised.
##----------------------------------------------------------------------

function hedge = get_verbose_hedge (mf_index_and_hedge, verbose_strings)

  mf_index_and_hedge = abs (mf_index_and_hedge);
  mf_index = fix (mf_index_and_hedge);
  hedge_num = round (100 * (mf_index_and_hedge - mf_index));

  switch (hedge_num)
    case 0                         ## .00 <=> no hedge <=> mu(x)
      hedge = "";
    case 5                         ## .05 <=> somewhat x <=> mu(x)^0.5
      hedge = verbose_strings{7};
    case 20                        ## .20 <=> very x <=> mu(x)^2
      hedge = verbose_strings{8};
    case 30                        ## .30 <=> extremely x <=> mu(x)^3
      hedge = verbose_strings{9};
    case 40                        ## .40 <=> very very x <=> mu(x)^4
      hedge = verbose_strings{10};
    otherwise                      ## For custom hedge, return the
      hedge = hedge_num / 10;      ## power dd/10. That is:
  endswitch                        ##   .dd <=> <custom hedge> x
                                   ##       <=> mu(x)^(dd/10)
endfunction

##----------------------------------------------------------------------
## Function: get_is_or_isnt
## Purpose:  Return the verbose string for "is" or "isn't" for the given 
##           membership function value. If the membership function value
##           is 0, return the empty string.
##----------------------------------------------------------------------

function is_or_isnt = get_is_or_isnt (mem_fcn_value, verbose_strings)

  if (mem_fcn_value > 0)
    is_or_isnt = verbose_strings{5};
  elseif (mem_fcn_value < 0)
    is_or_isnt = verbose_strings{6};
  else
    is_or_isnt = "";
  endif

endfunction

##----------------------------------------------------------------------
## Function: get_mf_name
## Purpose:  Return the specified membership function name.
##----------------------------------------------------------------------

function mf_name = get_mf_name (mem_fcn_value, fis_input_or_output)

  mf_name = fis_input_or_output.mf(abs(fix(mem_fcn_value))).name;

endfunction

##----------------------------------------------------------------------
## Function: get_verbose_strings
## Purpose:  Return a cell array of ten strings corresponding to:
##              {"and" "or" "If" "then" "is" "isn't" ...
##               "somewhat" "very" "extremely" "very very"}
##           for the (built-in) language specified by the argument.
##           Custom verbose strings are specified by an argument to
##           showrule -- they are not handled by this function.
##----------------------------------------------------------------------

function str = get_verbose_strings (language)

  switch (language)
    case 'english'
      str = {"and" "or" "If" "then" "is" "isn't" ...
             "somewhat" "very" "extremely" "very very"};
    case {'chinese' 'mandarin' 'pinyin'}
      str = {"he" "huo" "Ruguo" "name" "shi" "bu shi" ...
             "youdian" "hen" "feichang" "feichang feichang"};
    case {'russian' 'russkij' 'pycckii'}
      str = {"i" "ili" "ecli" "togda" "" "ne" ...
             "nemnogo" "ochen" "prevoshodnoye" "ochen ochen"};
    case {'spanish' 'espanol'}
      str = {"y" "o" "Si" "entonces" "es" "no es" ...
             "un poco" "muy" "extremadamente" "muy muy"};
    case {'francais' 'french'}
      str = {"et" "ou" "Si" "alors" "est" "n'est pas" ...
             "un peu" "tres" "extremement" "tres tres"};
    case {'deutsch' 'german'}
      str = {"und" "oder" "Wenn" "dann" "ist" "ist nicht" ...
             "ein wenig" "sehr" "auBerst" "sehr sehr"};
  endswitch

endfunction

##----------------------------------------------------------------------
## Function: showrule_indexed_format
## Purpose:  Show the rules in indexed format.
##----------------------------------------------------------------------

function showrule_indexed_format (fis, index_list)

  num_inputs = columns (fis.input);
  num_outputs = columns (fis.output);

  for i = 1 : length (index_list)
    current_ant = fis.rule(index_list(i)).antecedent;
    current_con = fis.rule(index_list(i)).consequent;
    current_wt = fis.rule(index_list(i)).weight;
    current_connect = fis.rule(index_list(i)).connection;

    ##------------------------------------------------------------------
    ## Print membership functions for the inputs.
    ##------------------------------------------------------------------
    for j = 1 : num_inputs
      if (is_int (current_ant(j)))
        printf ("%d", current_ant(j));
      else
        printf ("%.2f", current_ant(j));
      endif
      if (j == num_inputs)
        puts (",");
      endif
      puts (" ");
    endfor

    ##------------------------------------------------------------------
    ## Print membership functions for the outputs.
    ##------------------------------------------------------------------
    for j = 1 : num_outputs
      if (is_int (current_con(j)))
        printf ("%d", current_con(j));
      else
        printf ("%.2f", current_con(j));
      endif
      if (j < num_outputs)
        puts (" ");
      endif
    endfor

    ##------------------------------------------------------------------
    ## Print the weight in parens.
    ##------------------------------------------------------------------
    if (is_int (current_wt))
      printf (" (%d) : ", current_wt);
    else
      printf (" (%.4f) : ", current_wt);
    endif

    ##------------------------------------------------------------------
    ## Print the connection and a newline.
    ##------------------------------------------------------------------
    printf ("%d\n", current_connect);
  endfor

endfunction

##----------------------------------------------------------------------
## Function: showrule_symbolic_format
## Purpose:  Show the rules in symbolic format.
##----------------------------------------------------------------------

function showrule_symbolic_format (fis, index_list)

  verbose_strings = {"&&"  "||"  ""  "=>"  "=="  "!="  ...
                     0.5  2.0  3.0  4.0};
  showrule_verbose_format (fis, index_list, "custom", ...
                           verbose_strings, true);

endfunction

##----------------------------------------------------------------------
## Function: showrule_verbose_format
## Purpose:  Show the rules in verbose format.
##----------------------------------------------------------------------

function showrule_verbose_format (fis, index_list, language, ...
                                  verbose_strings, ...
                                  suppress_comma = false)

  num_inputs = columns (fis.input);
  num_outputs = columns (fis.output);

  ##--------------------------------------------------------------------
  ## Get verbose strings in the (built-in) language specified. Note
  ## that the strings for custom languages are supplied by the user.
  ##--------------------------------------------------------------------

  language = tolower (language);
  if (isequal ("custom", language))
    str = verbose_strings;
  else
    str = get_verbose_strings (language);
  endif

  and_str = str{1};
  if_str = str{3};
  then_str = str{4};

  ##--------------------------------------------------------------------
  ## For each index in the index_list, print the index number, the rule,
  ## and the weight.
  ##--------------------------------------------------------------------

  for i = 1 : length (index_list)

    connect_str = str{fis.rule(index_list(i)).connection};
    current_ant = fis.rule(index_list(i)).antecedent;
    current_con = fis.rule(index_list(i)).consequent;
    current_wt = fis.rule(index_list(i)).weight;

    ##------------------------------------------------------------------
    ## For j = 1, print:
    ##     <rule num>. If (<var name> <is or isn't> [<hedge>] <mf name>)
    ## and for 2 <= j <= num_inputs, print:
    ##     <connection> (<var name> <is or isn't> [<hedge>] <mf name>)
    ## in the specified language. Custom hedges are printed in the form:
    ##     <connection> (<var name> <is or isn't> <mf name>^<hedge>)
    ##------------------------------------------------------------------

    first_input_printed = true;
    for j = 1 : num_inputs

      if (j == 1)
        printf ("%d.", index_list(i));
      endif

      input_name = fis.input(j).name;
      is_or_isnt = get_is_or_isnt (current_ant(j), str);

      if (!isempty (is_or_isnt))
        hedge = get_verbose_hedge (current_ant(j), str);
        mf_name = get_mf_name (current_ant(j), fis.input(j));

        if (first_input_printed)
          first_input_printed = false;
          printf (" %s", if_str);
        else
          printf (" %s", connect_str);
        endif

        if (isempty (hedge))
          printf (" (%s %s %s)", input_name, is_or_isnt, mf_name);
        elseif (ischar (hedge))
          printf (" (%s %s %s %s)", input_name, is_or_isnt, hedge, ...
                  mf_name);
        else
          printf (" (%s %s %s^%3.1f)", input_name, is_or_isnt, ...
                  mf_name, hedge);
        endif
      endif

    endfor

    ##------------------------------------------------------------------
    ## Print the consequent in the form:
    ##     ", then (output-name is [hedge] mem-fcn-name) and
    ##             (output-name is [hedge] mem-fcn-name) and 
    ##             ...
    ##             (output-name is [hedge] mem-fcn-name)"
    ##
    ## Only the outputs for which the membership function index is
    ## non-zero are printed. Negative membership function indices
    ## indicate "isn't" instead of "is", and the fractional part of
    ## the membership function index indicates a hedge, which is also
    ## printed.
    ##
    ## For non-numeric and empty hedges, print each of the outputs
    ## using the form:
    ##     <and> (<var name> <is or isn't> [<hedge>] <mf name>)
    ## For custom and numeric hedges, use the form:
    ##     <and> (<var name> <is or isn't> <mf name>^<hedge>)
    ##
    ## The comma may be suppressed (as it is for symbolic output) by
    ## calling the function with suppress_comma == true.
    ##------------------------------------------------------------------

    first_output_printed = true;
    for j = 1 : num_outputs

      output_name = fis.output(j).name;
      is_or_isnt = get_is_or_isnt (current_con(j), str);

      if (!isempty (is_or_isnt))
        hedge = get_verbose_hedge (current_con(j), str);
        mf_name = get_mf_name (current_con(j), fis.output(j));

        if (first_output_printed)
          first_output_printed = false;
          if (suppress_comma)
            printf (" %s", then_str);
          else
            printf (", %s", then_str);
          endif
        else
          printf (" %s", and_str);
        endif

        if (isempty (hedge))
          printf (" (%s %s %s)", output_name, is_or_isnt, mf_name);
        elseif (ischar (hedge))
          printf (" (%s %s %s %s)", output_name, is_or_isnt, hedge, ...
                  mf_name);
        else
          printf (" (%s %s %s^%3.1f)", output_name, is_or_isnt, ...
                  mf_name, hedge);
        endif
      endif

    endfor

    ##------------------------------------------------------------------
    ## Finally, print the weight in parens and a newline:
    ##     " (<weight>)"
    ##------------------------------------------------------------------

    if is_int (current_wt)
      printf (" (%d)\n", current_wt);
    else
      printf (" (%.4f)\n", current_wt);
    endif
  endfor

endfunction

##----------------------------------------------------------------------
## Embedded Demos
##----------------------------------------------------------------------

%!demo
%! fis = readfis ('sugeno_tip_calculator.fis');
%! puts ("Output of: showrule(fis)\n");
%! showrule (fis)
%! puts ("\n");

%!demo
%! fis = readfis ('sugeno_tip_calculator.fis');
%! puts ("Output of: showrule(fis, [2 4], 'symbolic')\n");
%! showrule (fis, [2 4], 'symbolic')
%! puts ("\n");

%!demo
%! fis = readfis ('sugeno_tip_calculator.fis');
%! puts ("Output of: showrule(fis, 1:4, 'indexed')\n");
%! showrule (fis, 1:4, 'indexed')
%! puts ("\n");

%!demo
%! fis = readfis ('sugeno_tip_calculator.fis');
%! puts ("Output of: showrule(fis, 1, 'verbose', 'francais')\n");
%! showrule (fis, 1, 'verbose', 'francais')
%! puts ("\n");
