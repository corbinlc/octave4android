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
## Power of a polynomial.  For internal use only.

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: September 2009
## Version: 0.1

function p = mpower (a, b)

  if (! isa (b, "double") && ! is_real_scalar (b))
    error ("tfpoly: mpower: power must be a natural number");
  endif

  c = uint64 (b);

  if (c != b)
    error ("tfpoly: mpower: power must be a positive integer");
  endif

  if (c == 0)
    p = tfpoly (1);
    return;
  endif

  p = a;

  for k = 1 : (c-1)
    p.poly = conv (p.poly, a.poly);
  endfor

endfunction