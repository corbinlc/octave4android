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
## @deftypefn {Function File} {@var{y} =} is_io_struct (@var{x})
##
## Return 1 if the argument @var{x} is a valid input or output structure for an
## FIS (Fuzzy Inference System), and return 0 otherwise.
##
## is_io_struct is a private function that localizes the test for valid input
## and output structs. For efficiency, is_io_struct only determines if the
## argument @var{x} is a structure with the expected fields, and that these
## fields have the expected types.
##
## @end deftypefn

## Author:        L. Markowsky
## Keywords:      fuzzy-logic-toolkit fuzzy private parameter-test
## Directory:     fuzzy-logic-toolkit/inst/private/
## Filename:      is_io_struct.m
## Last-Modified: 20 Aug 2012

function y = is_io_struct (x)

  y = isstruct (x) && ...
      isfield (x, 'name') && is_string (x.name) && ...
      isfield (x, 'range') && are_bounds (x.range) && ...
      isfield (x, 'mf') && is_mf_vector (x.mf);

endfunction
