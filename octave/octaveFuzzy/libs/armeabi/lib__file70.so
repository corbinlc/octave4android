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
## @deftypefn {Function File} {@var{y} =} is_pos_int (@var{x})
##
## Return 1 if @var{x} is a positive integer-valued real scalar, and return 0
## otherwise.
##
## Examples:
## @example
## @group
## is_pos_int(6)         ==> 1
## is_pos_int(6.2)       ==> 0
## is_pos_int(ones(2))   ==> 0
## is_pos_int(6 + 0i)    ==> 1
## is_pos_int(0)         ==> 0
## @end group
## @end example
##
## @end deftypefn

## Author:        L. Markowsky
## Keywords:      fuzzy-logic-toolkit fuzzy private parameter-test
## Directory:     fuzzy-logic-toolkit/inst/private/
## Filename:      is_pos_int.m
## Last-Modified: 20 Aug 2012

function y = is_pos_int (x)

  y = is_int (x) && (x > 0);

endfunction
