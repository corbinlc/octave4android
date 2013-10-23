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
## @deftypefn {Function File} {@var{fis} =} addvar (@var{fis}, @var{in_or_out}, @var{var_name}, @var{var_range})
##
## Add an input or output variable to an existing FIS
## structure and return the updated FIS.
##
## The types of the arguments are expected to be:
## @itemize @w
## @item
## @var{fis} - an FIS structure
## @item
## @var{in_or_out} - either 'input' or 'output' (case-insensitive)
## @item
## @var{var_name} - a string
## @item
## @var{var_range} - a vector [x1 x2] of two real numbers
## @end itemize
##
## The vector components x1 and x2, which must also satisfy x1 <= x2,
## specify the lower and upper bounds of the variable's domain.
##
## @noindent
## To run the demonstration code, type @t{demo('addvar')} at the Octave prompt.
##
## @end deftypefn

## Author:        L. Markowsky
## Keywords:      fuzzy-logic-toolkit fuzzy variable
## Directory:     fuzzy-logic-toolkit/inst/
## Filename:      addvar.m
## Last-Modified: 18 Aug 2012

function fis = addvar (fis, in_or_out, var_name, var_range)

  ## If the caller did not supply 4 argument values with the correct
  ## types, print an error message and halt.

  if (nargin != 4)
    puts ("Type 'help addvar' for more information.\n");
    error ("addvar requires 4 arguments\n");
  elseif (!is_fis (fis))
    puts ("Type 'help addvar' for more information.\n");
    error ("addvar's first argument must be an FIS structure\n");
  elseif (!(is_string (in_or_out) && ...
          ismember (tolower (in_or_out), {'input', 'output'})))
    puts ("Type 'help addvar' for more information.\n");
    error ("addvar's second argument must be 'input' or 'output'\n");
  elseif (!is_string (var_name))
    puts ("Type 'help addvar' for more information.\n");
    error ("addvar's third argument must be a string\n\n");
  elseif (!are_bounds (var_range))
    puts ("Type 'help addvar' for more information.\n");
    error ("addvar's fourth argument must specify variable bounds\n");
  endif

  ## Create a new variable struct and update the FIS input or output
  ## variable list.

  new_variable = struct ('name', var_name, 'range', var_range, ...
                         'mf', []);
  if (strcmp (tolower (in_or_out), 'input'))
    if (length (fis.input) == 0)
      fis.input = new_variable;
    else
      fis.input = [fis.input, new_variable];
    endif
  else
    if (length (fis.output) == 0)
      fis.output = new_variable;
    else
      fis.output = [fis.output, new_variable];
    endif
  endif

endfunction

%!demo
%! a = newfis ('Heart-Disease-Risk', 'sugeno', ...
%!             'min', 'max', 'min', 'max', 'wtaver');
%! a = addvar (a, 'input', 'LDL-Level', [0 300]);
%! getfis (a, 'input', 1);
