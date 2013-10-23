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
## @deftypefn {Function File} {@var{y} =} is_int (@var{x})
##
## Return 1 if @var{x} is an integer-valued real scalar, and return 0 otherwise.
##
## is_int is a private function that localizes the test for integers.
## In Octave, integer constants such as 1 are not represented by ints
## internally: isinteger(1) returns 0.
##
## Examples:
## @example
## @group
## is_int(6)         ==> 1
## is_int(6.2)       ==> 0
## is_int(ones(2))   ==> 0
## is_int(6 + 0i)    ==> 1
## is_int(0)         ==> 1
## @end group
## @end example
##
## @end deftypefn

## Author:        L. Markowsky
## Keywords:      fuzzy-logic-toolkit fuzzy private parameter-test
## Directory:     fuzzy-logic-toolkit/inst/private/
## Filename:      is_int.m
## Last-Modified: 20 Aug 2012

function y = is_int (x)

  y = isscalar (x) && isreal (x) && (fix (x) == x);

endfunction
