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
## @deftypefn {Function File} {@var{y} =} are_bounds (@var{x})
## @deftypefnx {Function File} {@var{y} =} are_bounds (@var{[x1 x2]})
##
## Return 1 if @var{x} is a vector of 2 real numbers @var{[x1 x2]},
## with @var{x1} <= @var{x2}, and return 0 otherwise.
##
## are_bounds is a private function that localizes the test for validity of
## bounds imposed on FIS input/output domains.
##
## @end deftypefn

## Author:        L. Markowsky
## Keywords:      fuzzy-logic-toolkit fuzzy private parameter-test
## Directory:     fuzzy-logic-toolkit/inst/private/
## Filename:      are_bounds.m
## Last-Modified: 20 Aug 2012

function y = are_bounds (x)

  y = isvector (x) && isreal (x) && (length (x) == 2) && (x(1) <= x(2));

endfunction
