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
## @deftypefn {Function File} {@var{y} =} is_mf_struct (@var{x})
##
## Return 1 if the argument @var{x} is a valid FIS (Fuzzy Inference System)
## membership function structure, and return 0 otherwise.
##
## is_mf_struct is a private function that localizes the test for valid FIS
## membership function structs. For efficiency, is_mf_struct only determines if
## the argument @var{x} is a structure with the expected fields, but the types
## of the fields are not verified.
##
## @end deftypefn

## Author:        L. Markowsky
## Keywords:      fuzzy-logic-toolkit fuzzy private parameter-test
## Directory:     fuzzy-logic-toolkit/inst/private/
## Filename:      is_mf_struct.m
## Last-Modified: 20 Aug 2012

function y = is_mf_struct (x)

  y = isstruct (x) && ...
      isfield (x, 'name') && ...
      isfield (x, 'type') && ...
      isfield (x, 'params');

endfunction
