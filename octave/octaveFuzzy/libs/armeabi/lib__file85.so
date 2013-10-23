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
## @deftypefn {Function File} {@var{fis} =} rmmf (@var{fis}, @var{in_or_out}, @var{var_index}, @var{mf}, @var{mf_index})
##
## Remove a membership function from an existing FIS
## structure and return the updated FIS.
##
## The types of the arguments are expected to be:
## @itemize @bullet
## @item
## @var{fis} - an FIS structure
## @item
## @var{in_or_out} - either 'input' or 'output' (case-insensitive)
## @item
## @var{var_index} - an FIS input or output variable index
## @item
## @var{mf} - the string 'mf'
## @item
## @var{mf_index} - a string
## @end itemize
##
## Note that rmmf will allow the user to delete membership functions that are
## currently in use by rules in the FIS.
##
## @seealso{addmf, rmvar}
## @end deftypefn

## Author:        L. Markowsky
## Keywords:      fuzzy-logic-toolkit fuzzy membership
## Directory:     fuzzy-logic-toolkit/inst/
## Filename:      rmmf.m
## Last-Modified: 20 Aug 2012

function fis = rmmf (fis, in_or_out, var_index, mf, mf_index)

  ## If the caller did not supply 5 argument values with the correct
  ## types, print an error message and halt.

  if (nargin != 5)
    puts ("Type 'help rmmf' for more information.\n");
    error ("rmmf requires 5 arguments\n");
  elseif (!is_fis (fis))
    puts ("Type 'help rmmf' for more information.\n");
    error ("rmmf's first argument must be an FIS structure\n");
  elseif (!(is_string(in_or_out) && ...
           ismember (tolower (in_or_out), {'input', 'output'})))
    puts ("Type 'help rmmf' for more information.\n");
    error ("rmmf's second argument must be 'input' or 'output'\n");
  elseif (!is_var_index (fis, in_or_out, var_index))
    puts ("Type 'help rmmf' for more information.\n");
    error ("rmmf's third argument must be a variable index\n");
  elseif (!isequal (mf, 'mf'))
    puts ("Type 'help rmmf' for more information.\n");
    error ("rmmf's fourth argument must be the string 'mf'\n");
  elseif (!is_int (mf_index))
    puts ("Type 'help rmmf' for more information.\n");
    error ("rmmf's fifth argument must be an integer\n");
  endif

  ## Delete the membership function struct and update the FIS structure.

  if (strcmp (tolower (in_or_out), 'input'))
    all_mfs = fis.input(var_index).mf;
    fis.input(var_index).mf = [all_mfs(1 : mf_index - 1), ...
                               all_mfs(mf_index + 1 : numel(all_mfs))];
  else
    all_mfs = fis.output(var_index).mf;
    fis.output(var_index).mf = [all_mfs(1 : mf_index - 1), ...
                                all_mfs(mf_index + 1 : numel(all_mfs))];
  endif

endfunction
