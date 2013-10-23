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
## @deftypefn {Function File} {} writefis (@var{fis})
## @deftypefnx {Function File} {} writefis (@var{fis}, @var{filename})
## @deftypefnx {Function File} {} writefis (@var{fis}, @var{filename}, @var{dialog})
##
## Save the specified FIS currently in the Octave workspace to a file
## named by the user. There are three forms of writefis:
##
## @table @asis
## @item # Arguments
## Action Taken
## @item 1
## Open a dialog GUI to help the user choose a directory and name
## for the output file.
## @item 2
## Do not open a dialog GUI. Save the FIS to a file in the
## current directory with the specified @var{filename}. If the
## specified @var{filename} does not end in '.fis', append '.fis'
## to the @var{filename}.
## @item 3
## Open a dialog GUI with the specified @var{filename} in the
## 'filename' textbox of the GUI. If the specified @var{filename}
## does not end in '.fis', append '.fis' to the @var{filename}.
## @end table
##
## The types of the arguments are expected to be:
## @table @var
## @item fis
## an FIS structure satisfying is_fis (see private/is_fis.m)
## @item filename
## a string; if the string does not already end with the extension
## ".fis", then ".fis" is added
## @item dialog
## the string 'dialog' (case insensitive)
## @end table
##
## @noindent
## Note:
## The GUI dialog requires zenity to be installed on the system.
##
## @noindent
## Known error:
## When using the file dialog, if the user clicks "Cancel" instead of
## saving the file, an error message is generated.
##
## @seealso{readfis}
## @end deftypefn

## Author:        L. Markowsky
## Keywords:      fuzzy-logic-toolkit fuzzy inference system fis
## Directory:     fuzzy-logic-toolkit/inst/
## Filename:      writefis.m
## Last-Modified: 20 Aug 2012

function writefis (fis, filename = 'filename.fis', dialog = 'dummy')

  ## If writefis was not called with between 1 and 3 arguments, or if
  ## the argument values were of the wrong type, print an error message
  ## and halt.

  if (!(nargin >= 1 && nargin <= 3))
    puts ("Type 'help writefis' for more information.\n");
    error ("writefis requires between 1 and 3 arguments\n");
  elseif (!is_fis (fis))
    puts ("Type 'help writefis' for more information.\n");
    error ("writefis's first argument must be an FIS structure\n");
  elseif ((nargin >= 2) && !is_string (filename))
    puts ("Type 'help writefis' for more information.\n");
    error ("writefis's second argument must be a string\n");
  elseif ((nargin == 3) && ...
          !(is_string (dialog) && strcmpi (dialog, 'dialog')))
    puts ("Type 'help writefis' for more information.\n");
    error ("writefis's third argument must the string 'dialog'\n");
  endif

  ## Open the output file.

  use_gui = (nargin != 2);
  fid = open_output_file (filename, use_gui);

  ## Write the [System], [Input<number>], [Output<number>], and [Rules]
  ## sections of the output file.

  write_system_section (fid, fis);
  write_input_sections (fid, fis);
  write_output_sections (fid, fis);
  write_rules_section (fid, fis);

  ## Close the output file.

  fclose (fid);

endfunction

##----------------------------------------------------------------------
## Function: open_output_file
## Purpose:  Open the output file. Return the fid if successful.
##           Otherwise, print an error message and halt.
##----------------------------------------------------------------------

function fid = open_output_file (filename, use_gui)

  ## If the filename is not empty, and if the last four characters of
  ## the filename are not '.fis', append '.fis' to the filename.

  fn_len = length (filename);
  if (((fn_len >= 4) && ...
       !strcmp(".fis",filename(fn_len-3:fn_len))) || ...
      ((fn_len > 0) && (fn_len < 4)))
    filename = [filename ".fis"];
  endif

  ## If writefis was called with 1 or 3 arguments, use a dialog to
  ## choose an output filename.

  if (use_gui)
    system_command = sprintf ("zenity --file-selection --filename=%s ...
                              --save --confirm-overwrite; ...
                              echo $file", filename);
    [dialog_error, filename] = system (file=system_command);
    if (dialog_error)
      puts ("Type 'help writefis' for more information.\n");
      error ("error selecting file using dialog\n");
    endif
    filename = strtrim (filename);
  endif

  ## Open output file. 

  [fid, msg] = fopen (filename, "w");
  if (fid == -1)
    if (use_gui)
      system ('zenity --error --text "Error opening output file."');
    endif
    puts ("Type 'help writefis' for more information.\n");
    error ("error opening output file: %s\n", msg);
  endif

endfunction

##----------------------------------------------------------------------
## Function: write_system_section
## Purpose:  Write [System] section of the output file.
##----------------------------------------------------------------------

function write_system_section (fid, fis)

  fprintf (fid, "[System]\n");
  fprintf (fid, "Name='%s'\n", fis.name);
  fprintf (fid, "Type='%s'\n", fis.type);
  fprintf (fid, "Version=%.1f\n", fis.version);
  fprintf (fid, "NumInputs=%d\n", columns(fis.input));
  fprintf (fid, "NumOutputs=%d\n", columns(fis.output));
  fprintf (fid, "NumRules=%d\n", columns(fis.rule));
  fprintf (fid, "AndMethod='%s'\n", fis.andMethod);
  fprintf (fid, "OrMethod='%s'\n", fis.orMethod);
  fprintf (fid, "ImpMethod='%s'\n", fis.impMethod);
  fprintf (fid, "AggMethod='%s'\n", fis.aggMethod);
  fprintf (fid, "DefuzzMethod='%s'\n", fis.defuzzMethod);

endfunction

##----------------------------------------------------------------------
## Function: write_input_sections
## Purpose:  For each FIS input, write [Input<number>] section to
##           output file.
##----------------------------------------------------------------------

function write_input_sections (fid, fis)

  num_inputs = columns (fis.input);

  for i = 1 : num_inputs
    num_mfs = columns (fis.input(i).mf);

    fprintf (fid, "\n[Input%d]\n", i);
    fprintf (fid, "Name='%s'\n", fis.input(i).name);
    fprintf (fid, "Range=%s\n", ...
             strrep (mat2str (fis.input(i).range),","," "));
    fprintf (fid, "NumMFs=%d\n", num_mfs);
    for j = 1 : num_mfs
      fprintf (fid, "MF%d='%s':'%s',%s\n", j, ...
               fis.input(i).mf(j).name, fis.input(i).mf(j).type, ...
               params2str (fis.input(i).mf(j).params));
    endfor
  endfor

endfunction

##----------------------------------------------------------------------
## Function: write_output_sections
## Purpose:  For each FIS output, write [Output<number>] section to
##           output file.
##----------------------------------------------------------------------

function write_output_sections (fid, fis)

  num_outputs = columns (fis.output);

  for i = 1 : num_outputs
    num_mfs = columns (fis.output(i).mf);

    fprintf (fid, "\n[Output%d]\n", i);
    fprintf (fid, "Name='%s'\n", fis.output(i).name);
    fprintf (fid, "Range=%s\n", ...
             strrep(mat2str(fis.output(i).range),","," "));
    fprintf (fid, "NumMFs=%d\n", num_mfs);
    for j = 1 : num_mfs
      fprintf (fid, "MF%d='%s':'%s',%s\n", j, ...
               fis.output(i).mf(j).name, fis.output(i).mf(j).type, ...
               params2str (fis.output(i).mf(j).params));
    endfor
  endfor

endfunction

##----------------------------------------------------------------------
## Function: write_rules_section
## Purpose:  Write [Rules] section to output file.
##----------------------------------------------------------------------

function write_rules_section (fid, fis)

  num_inputs = columns (fis.input);
  num_outputs = columns (fis.output);
  num_rules = columns (fis.rule);

  fprintf (fid, "\n[Rules]\n");

  for i = 1 : num_rules
    next_ant = fis.rule(i).antecedent;
    next_con = fis.rule(i).consequent;
    next_wt = fis.rule(i).weight;
    next_connect = fis.rule(i).connection;

    ## Print membership functions for the inputs.
    if (num_inputs > 0)
      if (is_int (next_ant(1)))
        fprintf (fid, "%d", next_ant(1));
      else
        fprintf (fid, "%.2f", next_ant(1));
      endif
    endif
    for j = 2 : num_inputs
      if (is_int (next_ant(j)))
        fprintf (fid, " %d", next_ant(j));
      else
        fprintf (fid, " %.2f", next_ant(j));
      endif
    endfor
    fprintf(fid, ", ");

    ## Print membership functions for the outputs.
    for j = 1 : num_outputs
      if (is_int (next_con(j)))
        fprintf (fid, "%d ", next_con(j));
      else
        fprintf (fid, "%.2f ", next_con(j));
      endif
    endfor

    ## Print the weight in parens.
    if (is_int (next_wt))
      fprintf (fid, "(%d) : ", next_wt);
    else
      fprintf (fid, "(%.4f) : ", next_wt);
    endif

    ## Print the connection and a newline.
    fprintf (fid, "%d\n", next_connect);
  endfor

endfunction

##----------------------------------------------------------------------
## Function: params2str
## Purpose:  Convert membership function parameters to string
##           representation.
##----------------------------------------------------------------------

function str = params2str (params)
  if (length (params) < 2)
    str = ['[' num2str(params) ']'];
  else
    str = strrep (mat2str (params), ",", " ");
  endif
endfunction
