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
## @deftypefn {Function File} {@var{y} =} is_domain (@var{x})
## @deftypefnx {Function File} {@var{y} =} is_domain (@var{[x1 x2 ... xn]})
##
## Return 1 if @var{x} is a real number of a vector of strictly increasing real
## numbers, and return 0 otherwise.
##
## is_domain is a private function that localizes the test for validity of FIS
## input variable domains.
##
## @end deftypefn

## Author:        L. Markowsky
## Keywords:      fuzzy-logic-toolkit fuzzy private parameter-test
## Directory:     fuzzy-logic-toolkit/inst/private/
## Filename:      is_domain.m
## Last-Modified: 20 Aug 2012

function y = is_domain (x)

  y = 1;
  if (!(isvector (x) && isreal (x)))
    y = 0;
  elseif (length(x) > 1)
    for i = 1 : length (x) - 1
      if (x(i) >= x(i + 1))
        y = 0;
      endif
    endfor
  endif

endfunction
