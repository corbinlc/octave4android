## Copyright (C) 2009   Lukas F. Reichlin
##
## This file is part of LTI Syncope.
##
## LTI Syncope is free software: you can redistribute it and/or modify
## it under the terms of the GNU General Public License as published by
## the Free Software Foundation, either version 3 of the License, or
## (at your option) any later version.
##
## LTI Syncope is distributed in the hope that it will be useful,
## but WITHOUT ANY WARRANTY; without even the implied warranty of
## MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
## GNU General Public License for more details.
##
## You should have received a copy of the GNU General Public License
## along with LTI Syncope.  If not, see <http://www.gnu.org/licenses/>.

## -*- texinfo -*-
## Subtraction of two polynomials.  For internal use only.

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: September 2009
## Version: 0.1

function a = minus (a, b)

  if (! isa (a, "tfpoly"))
    a = tfpoly (a);
  endif

  if (! isa (b, "tfpoly"))
    b = tfpoly (b);
  endif

  [a, b] = __make_equally_long__ (a, b);

  a.poly = a.poly - b.poly;

  a = __remove_leading_zeros__ (a);

endfunction