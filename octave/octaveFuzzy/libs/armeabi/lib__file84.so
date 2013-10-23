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
## @deftypefn {Function File} {@var{fis} =} readfis ()
## @deftypefnx {Function File} {@var{fis} =} readfis (@var{filename})
##
## Read the information in an FIS file, and using this information, create and
## return an FIS structure. If called without any arguments or with an empty
## string as an argument, present the user with a file dialog GUI. If called
## with a @var{filename} that does not end with '.fis', append '.fis' to the
## @var{filename}. The @var{filename} is expected to be a string.
##
## Three examples of the input file format:
## @itemize @bullet
## @item
## heart_disease_risk.fis
## @item
## mamdani_tip_calculator.fis
## @item
## sugeno_tip_calculator.fis
## @end itemize
##
## Six example scripts that use readfis:
## @itemize @bullet
## @item
## cubic_approx_demo.m
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
## @seealso{writefis}
## @end deftypefn

## Author:        L. Markowsky
## Keywords:      fuzzy-logic-toolkit fuzzy inference system fis
## Directory:     fuzzy-logic-toolkit/inst/
## Filename:      readfis.m
## Last-Modified: 20 Aug 2012

function fis = readfis (filename = '')

  ## If readfis was not called with 0 or 1 arguments, or if the argument
  ## is not a string, print an error message and halt.

  if (nargin > 1)
    puts ("Type 'help readfis' for more information.\n");
    error ("readfis requires 0 or 1 arguments\n");
  elseif ((nargin == 1) && !is_string (filename))
    puts ("Type 'help readfis' for more information.\n");
    error ("readfis's argument must be a string\n");
  endif

  ## Open the input file.

  fid = open_input_file (filename);

  ## Read the [System], [Input<number>], [Output<number>], and [Rules]
  ## sections of the input file.

  [fis, num_inputs, num_outputs, num_rules, line_num] = ...
    init_fis_struct (fid);
  [fis, line_num] = read_fis_inputs (fid, fis, num_inputs, line_num);
  [fis, line_num] = read_fis_outputs (fid, fis, num_outputs, line_num);
  fis = read_rules (fid, fis, num_inputs, num_outputs, num_rules, ...
                    line_num);

  ## Close the input file.

  fclose (fid);

endfunction

##----------------------------------------------------------------------
## Function: open_input_file
## Purpose:  Open the input file specified by the filename. If the
##           filename does not end with ".fis", then append ".fis" to
##           the filename before opening. Return an fid if successful.
##           Otherwise, print an error message and halt.
##----------------------------------------------------------------------

function fid = open_input_file (filename)

  ##--------------------------------------------------------------------
  ## If the filename is not empty, and if the last four characters of
  ## the filename are not '.fis', append '.fis' to the filename. If the
  ## filename is empty, use a dialog to select the input file.
  ##--------------------------------------------------------------------

  fn_len = length (filename);
  if (fn_len == 0)
    dialog = 1;
  else
    dialog = 0;
  endif
  if (((fn_len >= 4) && ...
       !strcmp(".fis",filename(fn_len-3:fn_len))) || ...
      ((fn_len > 0) && (fn_len < 4)))
    filename = [filename ".fis"];
  elseif (dialog)
    system_command = sprintf ("zenity --file-selection; echo $file", ...
                              filename);
    [dialog_error, filename] = system (file = system_command);
    if (dialog_error)
      puts ("Type 'help readfis' for more information.\n");
      error ("error selecting file using dialog\n");
    endif
    filename = strtrim (filename);
  endif

  ##--------------------------------------------------------------------
  ## Open input file.
  ##--------------------------------------------------------------------

  [fid, msg] = fopen (filename, "r");
  if (fid == -1)
    if (dialog)
      system ('zenity --error --text "Error opening input file."');
    endif
    puts ("Type 'help readfis' for more information.\n");
    printf ("Error opening input file: %s\n", msg);
    error ("error opening input file\n");
  endif

endfunction

##----------------------------------------------------------------------
## Function: init_fis_struct
## Purpose:  Read the [System] section of the input file. Using the
##           strings read from the input file, create a new FIS. If an
##           error in the format of the input file is found, print an
##           error message and halt.
##----------------------------------------------------------------------

function [fis, num_inputs, num_outputs, num_rules, line_num] = ...
            init_fis_struct (fid)

  ##--------------------------------------------------------------------
  ## Read the [System] section.
  ##--------------------------------------------------------------------

  line_num = 1;
  [line, line_num] = get_line (fid, line_num);
  [line, line_num] = get_line (fid, line_num);
  [fis_name, count] = sscanf (line, "Name = '%s", "C");
  if (count != 1)
    error ("line %d: name of FIS expected\n", --line_num);
  endif
  fis_name = trim_last_char (fis_name);

  [line, line_num] = get_line (fid, line_num);
  [fis_type, count] = sscanf (line, "Type = '%s", "C");
  if (count != 1)
    error ("line %d: type of FIS expected\n", --line_num);
  endif
  fis_type = trim_last_char (fis_type);

  [line, line_num] = get_line (fid, line_num);
  [fis_version, count] = sscanf (line, "Version = %f", "C");
  if (count != 1)
    error ("line %d: version of FIS expected\n", --line_num);
  endif

  [line, line_num] = get_line (fid, line_num);
  [num_inputs, count] = sscanf (line, "NumInputs = %d", "C");
  if (count != 1)
    error ("line %d: number of inputs expected\n", --line_num);
  endif

  [line, line_num] = get_line (fid, line_num);
  [num_outputs, count] = sscanf (line, "NumOutputs = %d", "C");
  if (count != 1)
    error ("line %d: number of oututs expected\n", --line_num);
  endif

  [line, line_num] = get_line (fid, line_num);
  [num_rules, count] = sscanf (line, "NumRules = %d", "C");
  if (count != 1)
    error ("line %d: number of rules expected\n", --line_num);
  endif

  [line, line_num] = get_line (fid, line_num);
  [and_method, count] = sscanf (line, "AndMethod = '%s", "C");
  if (count != 1)
    error ("line %d: and method expected\n", --line_num);
  endif
  and_method = trim_last_char (and_method);

  [line, line_num] = get_line (fid, line_num);
  [or_method, count] = sscanf (line, "OrMethod = '%s", "C");
  if (count != 1)
    error ("line %d: or method expected\n", --line_num);
  endif
  or_method = trim_last_char (or_method);

  [line, line_num] = get_line (fid, line_num);
  [imp_method, count] = sscanf (line, "ImpMethod = '%s", "C");
  if (count != 1)
    error ("line %d: implication method expected\n", --line_num);
  endif
  imp_method = trim_last_char (imp_method);

  [line, line_num] = get_line (fid, line_num);
  [agg_method, count] = sscanf (line, "AggMethod = '%s", "C");
  if (count != 1)
    error ("line %d: aggregation method expected\n", --line_num);
  endif
  agg_method = trim_last_char (agg_method);

  [line, line_num] = get_line (fid, line_num);
  [defuzz_method, count] = sscanf (line, "DefuzzMethod = '%s", "C");
  if (count != 1)
    error ("line %d: defuzzification method expected\n", --line_num);
  endif
  defuzz_method = trim_last_char (defuzz_method);

  ##--------------------------------------------------------------------
  ## Create a new FIS structure using the strings read from the
  ## input file.
  ##--------------------------------------------------------------------

  fis = struct ('name', fis_name, ...
                'type', fis_type, ...
                'version', fis_version, ...
                'andMethod', and_method, ...
                'orMethod', or_method, ...
                'impMethod', imp_method, ...
                'aggMethod', agg_method, ...
                'defuzzMethod', defuzz_method, ...
                'input', [], ...
                'output', [], ...
                'rule', []);

endfunction

##----------------------------------------------------------------------
## Function: read_fis_inputs
## Purpose:  For each FIS input, read the [Input<number>] section from
##           file. Add each new input and its membership functions to
##           the FIS structure.
##----------------------------------------------------------------------

function [fis, line_num] = read_fis_inputs (fid, fis, num_inputs, ...
                                            line_num)

  for i = 1 : num_inputs
    [next_fis_input, num_mfs, line_num] = ...
      get_next_fis_io (fid, line_num, i, 'input');
    if (i == 1)
      fis.input = next_fis_input;
    else
      fis.input = [fis.input, next_fis_input];
    endif

    ##------------------------------------------------------------------
    ## Read membership function info for the current FIS input from
    ## file. Add each new membership function to the FIS struct.
    ##------------------------------------------------------------------

    for j = 1 : num_mfs
      [next_mf, line_num] = get_next_mf (fid, line_num, i, j, 'input');
      if (j == 1)
        fis.input(i).mf = next_mf;
      else
        fis.input(i).mf = [fis.input(i).mf, next_mf];
      endif
    endfor
  endfor

endfunction

##----------------------------------------------------------------------
## Function: read_fis_outputs
## Purpose:  For each FIS output, read the [Output<number>] section from
##           file. Add each new output and its membership functions to
##           the FIS structure.
##----------------------------------------------------------------------

function [fis, line_num] = read_fis_outputs (fid, fis, num_outputs, ...
                                             line_num)

  for i = 1 : num_outputs
    [next_fis_output, num_mfs, line_num] = ...
      get_next_fis_io (fid, line_num, i, 'output');
    if (i == 1)
      fis.output = next_fis_output;
    else
      fis.output = [fis.output, next_fis_output];
    endif

    ##------------------------------------------------------------------
    ## Read membership function info for the current FIS output from
    ## file. Add each new membership function to the FIS struct.
    ##------------------------------------------------------------------

    for j = 1 : num_mfs
      [next_mf, line_num] = get_next_mf (fid, line_num, i, j, 'output');
      if (j == 1)
        fis.output(i).mf = next_mf;
      else
        fis.output(i).mf = [fis.output(i).mf, next_mf];
      endif
    endfor
  endfor

endfunction

##----------------------------------------------------------------------
## Function: read_rules
## Purpose:  Read the [Rules] section from file, and add the rules to
##           the FIS.
##----------------------------------------------------------------------

function fis = read_rules (fid, fis, num_inputs, num_outputs, ...
                           num_rules, line_num)

  [line, line_num] = get_line (fid, line_num);
  for i = 1 : num_rules
    [next_rule, line_num] = ...
      get_next_rule (fid, line_num, num_inputs, num_outputs);
    if (i == 1)
      fis.rule = next_rule;
    else
      fis.rule = [fis.rule, next_rule];
    endif
  endfor

endfunction

##----------------------------------------------------------------------
## Function: get_next_fis_io
## Purpose:  Read the next [Input<i>] or [Output<i>] section of the
##           input file. Using the info read from the input file, create
##           a new FIS input or output structure. If an error in the
##           format of the input file is found, print an error message
##           and halt.
##----------------------------------------------------------------------

function [next_fis_io, num_mfs, line_num] = ...
             get_next_fis_io (fid, line_num, i, in_or_out)

  ##--------------------------------------------------------------------
  ## Read [Input<i>] or [Output<i>] section from file.
  ##--------------------------------------------------------------------

  [line, line_num] = get_line (fid, line_num);
  if (strcmp ('input', in_or_out))
    [io_index, count] = sscanf (line, "[Input %d", "C");
  else
    [io_index, count] = sscanf (line, "[Output %d", "C");
  endif
  if ((count != 1) || (io_index != i))
    error ("line %d: next input or output expected\n", --line_num);
  endif

  [line, line_num] = get_line (fid, line_num);
  [var_name, count] = sscanf (line, "Name = '%s", "C");
  if (count != 1)
    error ("line %d: name of %s %d expected\n", --line_num, ...
           in_or_out, i);
  endif
  var_name = trim_last_char (var_name);

  [line, line_num] = get_line (fid, line_num);
  [range_low, range_high, count] = sscanf (line, ...
                                           "Range = [ %f %f ]", "C");
  if ((count != 2) || (range_low > range_high))
    error ("line %d: range for %s %d expected\n", --line_num, ...
           in_or_out, i);
  endif

  [line, line_num] = get_line (fid, line_num);
  [num_mfs, count] = sscanf (line, "NumMFs = %d", "C");
  if (count != 1)
    error ("line %d: number of MFs for %s %d expected\n", ...
           --line_num, in_or_out, i);
  endif

  ##--------------------------------------------------------------------
  ## Create a new FIS input or output structure.
  ##--------------------------------------------------------------------

  next_fis_io = struct ('name', var_name, 'range', ...
                        [range_low, range_high], 'mf', []);

endfunction

##----------------------------------------------------------------------
## Function: get_next_mf
## Purpose:  Read information specifying the jth membership function for
##           Input<i> or Output<i> (if in_or_out is 'input' or 'output',
##           respectively) from the input file. Create a new membership
##           function structure using the info read. If an error in the
##           format of the input file is found, print an error message
##           and halt.
##----------------------------------------------------------------------

function [next_mf, line_num] = get_next_mf (fid, line_num, i, j, ...
                                            in_or_out)
            
  ##--------------------------------------------------------------------
  ## Read membership function info for the new FIS input or output
  ## from file.
  ##--------------------------------------------------------------------

  [line, line_num] = get_line (fid, line_num);
  line_vec = discard_empty_strings (strsplit (line, "=':,[] \t", true));
  mf_index = sscanf (line_vec{1}, "MF %d", "C");
  mf_name = line_vec{2};
  mf_type = line_vec{3};
  if (mf_index != j)
    error ("line %d: next MF for %s %d expected\n", --line_num,
           in_or_out, i);
  endif

  j = 1;
  for i = 4 : length (line_vec)
    [mf_params(j++), count] = sscanf (line_vec{i}, "%f", "C");
    if (count != 1)
      error ("line %d: %s %d MF%d params expected\n", --line_num,
             in_or_out, i, j);
    endif
  endfor

  ##--------------------------------------------------------------------
  ## Create a new membership function structure.
  ##--------------------------------------------------------------------

  next_mf = struct ('name', mf_name, 'type', mf_type, 'params', ...
                    mf_params);

endfunction

##----------------------------------------------------------------------
## Function: get_next_rule
## Purpose:  Read the next rule from the input file. Create a struct for
##           the new rule. If an error in the format of the input file
##           is found, print an error message and halt.
##----------------------------------------------------------------------

function [next_rule, line_num] = get_next_rule (fid, line_num, ...
                                                num_inputs, num_outputs)

  [line, line_num] = get_line (fid, line_num);
  line_vec = strsplit (line, ",():", true);

  ##--------------------------------------------------------------------
  ## Read antecedent.
  ##--------------------------------------------------------------------
  format_str = "";
  for j = 1 : num_inputs
    format_str = [format_str " %f"];
  endfor
  [antecedent, count] = sscanf (line_vec{1}, format_str, ...
                                [1, num_inputs]);
  if (length (antecedent) != num_inputs)
    error ("Line %d: Rule antecedent expected.\n", line_num);
  endif

  ##--------------------------------------------------------------------
  ## Read consequent.
  ##--------------------------------------------------------------------
  format_str = "";
  for j = 1 : num_outputs
    format_str = [format_str " %f"];
  endfor
  [consequent, count] = sscanf (line_vec{2}, format_str, ...
                                [1, num_outputs]);
  if (length (consequent) != num_outputs)
    error ("Line %d: Rule consequent expected.\n", line_num);
  endif

  ##--------------------------------------------------------------------
  ## Read weight.
  ##--------------------------------------------------------------------
  [weight, count] = sscanf (line_vec{3}, "%f", "C");
  if (count != 1)
    error ("Line %d: Rule weight expected.\n", line_num);
  endif

  ##--------------------------------------------------------------------
  ## Read connection.
  ##--------------------------------------------------------------------
  [connection, count] = sscanf (line_vec{5}, "%d", "C");
  if ((count != 1) || (connection < 1) || (connection > 2))
    error ("Line %d: Antecedent connection expected.\n", line_num);
  endif

  ##--------------------------------------------------------------------
  ## Create a new rule struct.
  ##--------------------------------------------------------------------
  next_rule = struct ('antecedent', antecedent, ...
                      'consequent', consequent, ...
                      'weight', weight, ...
                      'connection', connection);

endfunction

##----------------------------------------------------------------------
## Function: get_line
## Purpose:  Read the next line of the input file (without the newline)
##           into line. Print an error message and halt on eof.
##----------------------------------------------------------------------

function [line, line_num] = get_line (fid, line_num)

  do
    line = fgetl (fid);
    if (isequal (line, -1))
      error ("unexpected end of file at line %d", line_num);
    endif
    line = trim_leading_whitespace (line);
    line_num++;
  until (!comment_or_empty (line))

endfunction

##----------------------------------------------------------------------
## Function: discard_empty_strings
## Purpose:  Return a copy of the input cell array without any 
##           empty string elements.
##----------------------------------------------------------------------

function ret_val =  discard_empty_strings (cell_array)

  ret_val = {};
  j = 1;
  for i = 1 : length (cell_array)
    if (!strcmp (cell_array{i}, ""))
      ret_val{j++} = cell_array{i};
    endif
  endfor

endfunction

##----------------------------------------------------------------------
## Function: trim_last_char
## Purpose:  Return a copy of the input string without its final
##           character.
##----------------------------------------------------------------------

function str = trim_last_char (str)

  str = str(1 : length (str) - 1);

endfunction

##----------------------------------------------------------------------
## Function: trim_leading_whitespace
## Purpose:  Return a copy of the input string without leading
##           whitespace.
##----------------------------------------------------------------------

function str = trim_leading_whitespace (str)
  str_length = length (str);
  i = 1;
  while (i <= str_length && ...
         (str (i) == ' ' || str (i) == '\t' || str (i) == '\n' || ...
          str (i) == '\f' || str (i) == '\r' || str (i) == '\v'))
    i++;
  endwhile
  if (i > str_length)
    str = "";
  else
    str = str (i : str_length);
  endif
endfunction

##----------------------------------------------------------------------
## Function: comment_or_empty
## Purpose:  Return true if the line is a comment (that is, it begins
##           with '#' or '%') or an empty line, and return false
##           otherwise. It is assumed that leading whitespace has been
##           removed from the input line.
##----------------------------------------------------------------------

function ret_val = comment_or_empty (line)
  ret_val = (length (line) == 0) || (line (1) == '#') || ...
            (line (1) == '%');
endfunction
