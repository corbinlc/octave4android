## Copyright (C) 2009, 2010   Lukas F. Reichlin
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
## Block diagonal concatenation of two SS models.
## This file is part of the Model Abstraction Layer.
## For internal use only.

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: September 2009
## Version: 0.2

function retsys = __sys_group__ (sys1, sys2)

  if (! isa (sys1, "ss"))
    sys1 = ss (sys1);
  endif

  if (! isa (sys2, "ss"))
    sys2 = ss (sys2);
  endif

  retsys = ss ();
  retsys.lti = __lti_group__ (sys1.lti, sys2.lti);

  n1 = rows (sys1.a);
  n2 = rows (sys2.a);
  
  [p1, m1] = size (sys1.d);
  [p2, m2] = size (sys2.d);

  retsys.a = [sys1.a, zeros(n1,n2); zeros(n2,n1), sys2.a];
  retsys.b = [sys1.b, zeros(n1,m2); zeros(n2,m1), sys2.b];
  retsys.c = [sys1.c, zeros(p1,n2); zeros(p2,n1), sys2.c];
  retsys.d = [sys1.d, zeros(p1,m2); zeros(p2,m1), sys2.d];

  e1 = ! isempty (sys1.e);
  e2 = ! isempty (sys2.e);

  if (e1 || e2)
    if (e1 && e2)
      retsys.e = [sys1.e, zeros(n1,n2); zeros(n2,n1), sys2.e];
    elseif (e1)
      retsys.e = [sys1.e, zeros(n1,n2); zeros(n2,n1), eye(n2)];
    else
      retsys.e = [eye(n1), zeros(n1,n2); zeros(n2,n1), sys2.e];
    endif
  endif

  retsys.stname = [sys1.stname; sys2.stname];

endfunction