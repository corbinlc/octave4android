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
## Matrix multiplication of LTI objects.  If necessary, object conversion
## is done by sys_group.  Used by Octave for "sys1 * sys2".

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: September 2009
## Version: 0.2

function sys = mtimes (sys2, sys1)

  if (nargin != 2)  # prevent sys = mtimes (sys1, sys2, sys3, ...)
    error ("lti: mtimes: this is a binary operator");
  endif

  [p1, m1] = size (sys1);
  [p2, m2] = size (sys2);

  if (m2 != p1)
    error ("lti: mtimes: system dimensions incompatible: (%dx%d) * (%dx%d)",
            p2, m2, p1, m1);
  endif

  M22 = zeros (m2, p2);
  M21 = eye (m2, p1);
  M12 = zeros (m1, p2);
  M11 = zeros (m1, p1);

  M = [M22, M21;
       M12, M11];

  out_idx = 1 : p2;
  in_idx = m2 + (1 : m1);

  sys = __sys_group__ (sys2, sys1);
  sys = __sys_connect__ (sys, M);
  sys = __sys_prune__ (sys, out_idx, in_idx);

endfunction


## Alternative code: consistency vs. compatibility
#{
  M11 = zeros (m1, p1);
  M12 = zeros (m1, p2);
  M21 = eye (m2, p1);
  M22 = zeros (m2, p2);
  

  M = [M11, M12;
       M21, M22];

  out_idx = p1 + (1 : p2);
  in_idx = 1 : m1;

  sys = __sys_group__ (sys1, sys2);
#}
## Don't forget to adapt @tf/__sys_connect__.m draft code


## mtimes
%!shared sysmat, sysmat_exp
%! sys1 = ss ([0, 1; -3, -2], [0; 1], [-5, 1], [2]);
%! sys2 = ss ([-10], [1], [-40], [5]);
%! sys3 = sys2 * sys1;
%! [A, B, C, D] = ssdata (sys3);
%! sysmat = [A, B; C, D];
%! A_exp = [ -10   -5    1
%!             0    0    1
%!             0   -3   -2 ];
%! B_exp = [   2
%!             0
%!             1 ];
%! C_exp = [ -40  -25    5 ];
%! D_exp = [  10 ];
%! sysmat_exp = [A_exp, B_exp; C_exp, D_exp];
%!assert (sysmat, sysmat_exp)


## Cascade inter-connection of two systems in state-space form
## Test from SLICOT AB05MD
## TODO: order of united state vector: consistency vs. compatibility?
#%!shared M, Me
#%! A1 = [ 1.0   0.0  -1.0
#%!        0.0  -1.0   1.0
#%!        1.0   1.0   2.0 ];
#%!
#%! B1 = [ 1.0   1.0   0.0
#%!        2.0   0.0   1.0 ].';
#%!
#%! C1 = [ 3.0  -2.0   1.0
#%!        0.0   1.0   0.0 ];
#%!
#%! D1 = [ 1.0   0.0
#%!        0.0   1.0 ];
#%!
#%! A2 = [-3.0   0.0   0.0
#%!        1.0   0.0   1.0
#%!        0.0  -1.0   2.0 ];
#%!
#%! B2 = [ 0.0  -1.0   0.0
#%!        1.0   0.0   2.0 ].';
#%!
#%! C2 = [ 1.0   1.0   0.0
#%!        1.0   1.0  -1.0 ];
#%!
#%! D2 = [ 1.0   1.0
#%!        0.0   1.0 ];
#%!
#%! sys1 = ss (A1, B1, C1, D1);
#%! sys2 = ss (A2, B2, C2, D2);
#%! sys = sys2 * sys1;
#%! [A, B, C, D] = ssdata (sys);
#%! M = [A, B; C, D];
#%!
#%! Ae = [ 1.0000   0.0000  -1.0000   0.0000   0.0000   0.0000
#%!        0.0000  -1.0000   1.0000   0.0000   0.0000   0.0000
#%!        1.0000   1.0000   2.0000   0.0000   0.0000   0.0000
#%!        0.0000   1.0000   0.0000  -3.0000   0.0000   0.0000
#%!       -3.0000   2.0000  -1.0000   1.0000   0.0000   1.0000
#%!        0.0000   2.0000   0.0000   0.0000  -1.0000   2.0000 ];
#%!
#%! Be = [ 1.0000   2.0000
#%!        1.0000   0.0000
#%!        0.0000   1.0000
#%!        0.0000   1.0000
#%!       -1.0000   0.0000
#%!        0.0000   2.0000 ];
#%!
#%! Ce = [ 3.0000  -1.0000   1.0000   1.0000   1.0000   0.0000
#%!        0.0000   1.0000   0.0000   1.0000   1.0000  -1.0000 ];
#%!
#%! De = [ 1.0000   1.0000
#%!        0.0000   1.0000 ];
#%!
#%! Me = [Ae, Be; Ce, De];
#%!
#%!assert (M, Me, 1e-4);
