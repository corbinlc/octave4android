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
## Inversion of LTI objects.

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: October 2009
## Version: 0.2

function retsys = inv (sys)

  if (nargin != 1)  # prevent sys = inv (sys1, sys2, sys3, ...)
    error ("lti: inv: this is an unary operator");
  endif

  [p, m] = size (sys);

  if (p != m)
    error ("lti: inv: system must be square");
  endif

  retsys = __sys_inverse__ (sys);

  ## TODO: handle i/o names

endfunction


## inverse of state-space models
## test from SLICOT AB07ND
## result differs intentionally from a commercial
## implementation of an octave-like language
%!shared M, Me
%! A = [ 1.0   2.0   0.0
%!       4.0  -1.0   0.0
%!       0.0   0.0   1.0 ];
%!
%! B = [ 1.0   0.0
%!       0.0   1.0
%!       1.0   0.0 ];
%!
%! C = [ 0.0   1.0  -1.0
%!       0.0   0.0   1.0 ];
%!
%! D = [ 4.0   0.0
%!       0.0   1.0 ];
%!
%! sys = ss (A, B, C, D);
%! sysinv = inv (sys);
%! [Ai, Bi, Ci, Di] = ssdata (sysinv);
%! M = [Ai, Bi; Ci, Di];
%!
%! Ae = [ 1.0000   1.7500   0.2500
%!        4.0000  -1.0000  -1.0000
%!        0.0000  -0.2500   1.2500 ];
%!
%! Be = [-0.2500   0.0000
%!        0.0000  -1.0000
%!       -0.2500   0.0000 ];
%!
%! Ce = [ 0.0000   0.2500  -0.2500
%!        0.0000   0.0000   1.0000 ];
%!
%! De = [ 0.2500   0.0000
%!        0.0000   1.0000 ];
%!
%! Me = [Ae, Be; Ce, De];  # Me = [Ae, -Be; -Ce, De];
%!
%!assert (M, Me, 1e-4);
