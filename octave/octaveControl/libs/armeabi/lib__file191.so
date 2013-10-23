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
## @deftypefn{Function File} {[@var{sysbar}, @var{T}, @var{K}] =} obsvf (@var{sys})
## @deftypefnx{Function File} {[@var{sysbar}, @var{T}, @var{K}] =} obsvf (@var{sys}, @var{tol})
## @deftypefnx{Function File} {[@var{Abar}, @var{Bbar}, @var{Cbar}, @var{T}, @var{K}] =} obsvf (@var{A}, @var{B}, @var{C})
## @deftypefnx{Function File} {[@var{Abar}, @var{Bbar}, @var{Cbar}, @var{T}, @var{K}] =} obsvf (@var{A}, @var{B}, @var{C}, @var{TOL})
## If Ob=obsv(A,C) has rank r <= n = SIZE(A,1), then there is a 
## similarity transformation Tc such that To = [t1;t2] where t1 is c
## and t2 is orthogonal to t1
##
## @example
## @group
## Abar = To \\ A * To ,  Bbar = To \\ B ,  Cbar = C * To
## @end group
## @end example
## 
## and the transformed system has the form
##
## @example
## @group
##        | Ao     0 |           | Bo  |
## Abar = |----------|,   Bbar = | --- |,  Cbar = [Co | 0 ].
##        | A21   Ano|           | Bno |
## @end group
## @end example
##                                                      
## where (Ao,Bo) is observable, and Co(sI-Ao)^(-1)Bo = C(sI-A)^(-1)B. And 
## system is detectable if Ano has no eigenvalues in the right 
## half plane. The last output K is a vector of length n containing the 
## number of observable states.
## @end deftypefn

## Author: Benjamin Fernandez <benjas@benjas-laptop>
## Created: 2010-05-02
## Version: 0.1

function [ac, bc, cc, z, ncont] = obsvf (a, b = [], c, tol = [])

  if (nargin < 1 || nargin > 4)
    print_usage ();
  endif
  
  if (isa (a, "lti"))
    if (nargin > 2)
      print_usage ();
    endif
    [ac, bc, cc] = ctrbf (a.', b);      # [sysbar, z, ncont] = ctrbf (sys.', tol);
    ac = ac.';
    z = ncont = [];
  else
    if (nargin < 3)
      print_usage ();
    endif
    [ac, tmp, cc, z, ncont] = ctrbf (a.', c.', b.', tol);
    ac = ac.';
    bc = cc.';
    cc = tmp.';
  endif

endfunction
