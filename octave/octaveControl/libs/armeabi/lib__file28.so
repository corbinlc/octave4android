## Copyright (C) 2010   Benjamin Fernandez 
## Copyright (C) 2011   Lukas F. Reichlin
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
## @deftypefn{Function File} {[@var{sysbar}, @var{T}, @var{K}] =} ctrbf (@var{sys})
## @deftypefnx{Function File} {[@var{sysbar}, @var{T}, @var{K}] =} ctrbf (@var{sys}, @var{tol})
## @deftypefnx{Function File} {[@var{Abar}, @var{Bbar}, @var{Cbar}, @var{T}, @var{K}] =} ctrbf (@var{A}, @var{B}, @var{C})
## @deftypefnx{Function File} {[@var{Abar}, @var{Bbar}, @var{Cbar}, @var{T}, @var{K}] =} ctrbf (@var{A}, @var{B}, @var{C}, @var{TOL})
## If Co=ctrb(A,B) has rank r <= n = SIZE(A,1), then there is a 
## similarity transformation Tc such that Tc = [t1 t2] where t1
## is the controllable subspace and t2 is orthogonal to t1
##
## @example
## @group
## Abar = Tc \\ A * Tc ,  Bbar = Tc \\ B ,  Cbar = C * Tc
## @end group
## @end example
##
## and the transformed system has the form
##
## @example
## @group
##        | Ac    A12|           | Bc |
## Abar = |----------|,   Bbar = | ---|,  Cbar = [Cc | Cnc].
##        | 0     Anc|           |  0 |
## @end group
## @end example
##                                     
## where (Ac,Bc) is controllable, and Cc(sI-Ac)^(-1)Bc = C(sI-A)^(-1)B.
## and the system is stabilizable if Anc has no eigenvalues in
## the right half plane. The last output K is a vector of length n
## containing the number of controllable states.
## @end deftypefn

## Author: Benjamin Fernandez <mail@benjaminfernandez.info>
## Created: 2010-04-30
## Version: 0.1

function [ac, bc, cc, z, ncont] = ctrbf (a, b = [], c, tol = [])

  if (nargin < 1 || nargin > 4)
    print_usage ();
  endif
  
  islti = isa (a, "lti");
  
  if (islti)
    if (nargin > 2)
      print_usage ();
    endif
    sys = a;
    tol = b;
    [a, b, c] = ssdata (sys);
  else
    if (nargin < 3)
      print_usage ();
    endif
    sys = ss (a, b, c);
    [a, b, c] = ssdata (sys);
  endif

  if (isempty (tol))
    tol = 0;               # default tolerance
  elseif (! is_real_scalar (tol))
    error ("ctrbf: tol must be a real scalar");
  endif

  [ac, bc, cc, z, ncont] = __sl_tb01ud__ (a, b, c, tol);
  
  if (islti)
    ac = set (sys, "a", ac, "b", bc, "c", cc, "scaled", false);
    bc = z;
    cc = ncont;
  endif
  
endfunction

%!shared Ao, Bo, Co, Zo, Ae, Be, Ce, Ze, NCONT
%! A =  [ -1.0   0.0   0.0
%!        -2.0  -2.0  -2.0
%!        -1.0   0.0  -3.0 ];
%!
%! B =  [  1.0   0.0   0.0
%!         0.0   2.0   1.0 ].';
%!
%! C =  [  0.0   2.0   1.0
%!         1.0   0.0   0.0 ];
%!
%! [Ao, Bo, Co, Zo, NCONT] = ctrbf (A, B, C);
%!
%! Ae = [ -3.0000   2.2361
%!         0.0000  -1.0000 ];
%!
%! Be = [  0.0000  -2.2361
%!         1.0000   0.0000 ];
%!
%! Ce = [ -2.2361   0.0000
%!         0.0000   1.0000 ];
%!
%! Ze = [  0.0000   1.0000   0.0000
%!        -0.8944   0.0000  -0.4472
%!        -0.4472   0.0000   0.8944 ];
%!
%!assert (Ao(1:NCONT, 1:NCONT), Ae, 1e-4);
%!assert (Bo(1:NCONT, :), Be, 1e-4);
%!assert (Co(:, 1:NCONT), Ce, 1e-4);
%!assert (Zo, Ze, 1e-4);
