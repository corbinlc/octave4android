## Copyright (C) 2012   Lukas F. Reichlin
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
## Conjugate transpose of SS models.

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: May 2012
## Version: 0.1

function sys = __ctranspose__ (sys, ct)

  a = sys.a;
  b = sys.b;
  c = sys.c;
  d = sys.d;
  e = sys.e;

  if (ct)       # continuous-time
    sys.a = -a.';
    sys.b = -c.';
    sys.c = b.';
    sys.d = d.';
    sys.e = e.';
    sys.stname = repmat ({""}, rows (a), 1);
  else          # discrete-time
    [n, m] = size (b);
    p = rows (c);
    if (isempty (e))
      e = eye (n);
    endif
    sys.a = blkdiag (e.', eye (p));
    sys.b = [zeros(n, p); -eye(p)];
    sys.c = [b.', zeros(m, p)];
    sys.d = d.';
    sys.e = [a.', c.'; zeros(p, n+p)];
    sys.stname = repmat ({""}, n+p, 1);
  endif

endfunction
