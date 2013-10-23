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
## Evaluate polynomial.  For internal use only.

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: September 2009
## Version: 0.1

function b = subsref (a, s)

  if (isempty (s))
    error ("tfpoly: missing index");
  endif

  switch (s(1).type)
    case "()"
      idx = s(1).subs;
      if (numel (idx) == 1)
        b = polyval (a.poly, idx{1});
      else
        error ("tfpoly: need exactly one index");
      endif

    otherwise
      error ("tfpoly: invalid subscript type");

  endswitch

endfunction