## Copyright (C) 2009, 2011   Lukas F. Reichlin
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
## Binary addition of LTI objects.  If necessary, object conversion
## is done by sys_group.  Used by Octave for "sys1 + sys2".
## Operation is also known as "parallel connection".

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: September 2009
## Version: 0.2

function sys = plus (sys1, sys2)

  if (nargin != 2)  # prevent sys = plus (sys1, sys2, sys3, ...)
    error ("lti: plus: this is a binary operator");
  endif

  [p1, m1] = size (sys1);
  [p2, m2] = size (sys2);

  if (p1 != p2 || m1 != m2)
    error ("lti: plus: system dimensions incompatible: (%dx%d) + (%dx%d)",
            p1, m1, p2, m2);
  endif

  sys = __sys_group__ (sys1, sys2);

  in_scl = [eye(m1); eye(m2)];
  out_scl = [eye(p1), eye(p2)];

  sys = out_scl * sys * in_scl;

endfunction


## Parallel inter-connection of two systems in state-space form
## Test from SLICOT AB05PD
%!shared M, Me
%! A1 = [ 1.0   0.0  -1.0
%!        0.0  -1.0   1.0
%!        1.0   1.0   2.0 ];
%!
%! B1 = [ 1.0   1.0   0.0
%!        2.0   0.0   1.0 ].';
%!
%! C1 = [ 3.0  -2.0   1.0
%!        0.0   1.0   0.0 ];
%!
%! D1 = [ 1.0   0.0
%!        0.0   1.0 ];
%!
%! A2 = [-3.0   0.0   0.0
%!        1.0   0.0   1.0
%!        0.0  -1.0   2.0 ];
%!
%! B2 = [ 0.0  -1.0   0.0
%!        1.0   0.0   2.0 ].';
%!
%! C2 = [ 1.0   1.0   0.0
%!        1.0   1.0  -1.0 ];
%!
%! D2 = [ 1.0   1.0
%!        0.0   1.0 ];
%!
%! sys1 = ss (A1, B1, C1, D1);
%! sys2 = ss (A2, B2, C2, D2);
%! sys = sys1 + sys2;
%! [A, B, C, D] = ssdata (sys);
%! M = [A, B; C, D];
%!
%! Ae = [ 1.0000   0.0000  -1.0000   0.0000   0.0000   0.0000
%!        0.0000  -1.0000   1.0000   0.0000   0.0000   0.0000
%!        1.0000   1.0000   2.0000   0.0000   0.0000   0.0000
%!        0.0000   0.0000   0.0000  -3.0000   0.0000   0.0000
%!        0.0000   0.0000   0.0000   1.0000   0.0000   1.0000
%!        0.0000   0.0000   0.0000   0.0000  -1.0000   2.0000 ];
%!
%! Be = [ 1.0000   2.0000
%!        1.0000   0.0000
%!        0.0000   1.0000
%!        0.0000   1.0000
%!       -1.0000   0.0000
%!        0.0000   2.0000 ];
%!
%! Ce = [ 3.0000  -2.0000   1.0000   1.0000   1.0000   0.0000
%!        0.0000   1.0000   0.0000   1.0000   1.0000  -1.0000 ];
%!
%! De = [ 2.0000   1.0000
%!        0.0000   2.0000 ];
%!
%! Me = [Ae, Be; Ce, De];
%!
%!assert (M, Me, 1e-4);
