## Copyright (C) 2009, 2010, 2012   Lukas F. Reichlin
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
## Inversion of SS models.

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: October 2009
## Version: 0.3

function sys = __sys_inverse__ (sys)

  a = sys.a;
  b = sys.b;
  c = sys.c;
  d = sys.d;
  e = sys.e;

  if (! isempty (e) || rcond (d) < eps)  # dss or strictly proper ss

    n = rows (a);
    m = columns (b);                     # p = m (square system)

    if (isempty (e))                     # avoid testing twice?
      e = eye (n);
    endif

    sys.a = [a, b; c, d];
    sys.b = [zeros(n, m); -eye(m)];
    sys.c = [zeros(m, n), eye(m)];
    sys.d = zeros (m);
    sys.e = [e, zeros(n, m); zeros(m, n+m)];

    sys.stname = repmat ({""}, n+m, 1);

  else                                   # proper ss

    bid = b / d;

    sys.a = a - bid * c;
    sys.b = -bid;
    sys.c = d \ c;
    sys.d = inv (d);

  endif

endfunction