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
## @deftypefn {Function File} {@var{y} =} is_fis (@var{x})
##
## Return 1 if the argument @var{x} is a valid FIS (Fuzzy Inference System)
## structure, and return 0 otherwise.
##
## is_fis is a private function that localizes the test for valid FIS structs.
## For efficiency, is_fis only determines if the argument @var{x} is a structure
## with the expected fields, and that these fields have the expected types.
##
## Examples:
## @example
## @group
## fis = newfis('FIS');
## is_fis(fis)            ==> 1
## @end group
## @end example
##
## @example
## @group
## x = pi;
## is_fis(x)              ==> 0
## @end group
## @end example
##
## @end deftypefn

## Author:        L. Markowsky
## Keywords:      fuzzy-logic-toolkit fuzzy private parameter-test
## Directory:     fuzzy-logic-toolkit/inst/private/
## Filename:      is_fis.m
## Last-Modified: 20 Aug 2012

function y = is_fis (x)

  y = isstruct (x) && ...
      isfield (x, 'name') && is_string (x.name) && ...
      isfield (x, 'type') && is_string (x.type) && ...
      isfield (x, 'andMethod') && is_string (x.andMethod) && ...
      isfield (x, 'orMethod') && is_string (x.orMethod) && ...
      isfield (x, 'impMethod') && is_string (x.impMethod) && ...
      isfield (x, 'aggMethod') && is_string (x.aggMethod) && ...
      isfield (x, 'defuzzMethod') && is_string (x.defuzzMethod) && ...
      isfield (x, 'input') && is_io_vector (x.input) && ...
      isfield (x, 'output') && is_io_vector (x.output) && ...
      isfield (x, 'rule') && is_rule_vector (x.rule);

endfunction
