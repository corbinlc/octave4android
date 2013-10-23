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
## @deftypefn {Function File} {@var{retsys} =} __sys_connect__ (@var{sys}, @var{M})
## This function is part of the Model Abstraction Layer.  No argument checking.
## For internal use only.
## @example
## @group
## Problem: Solve the system equations of
##   .
## E x(t) = A x(t) + B e(t)
##
##   y(t) = C x(t) + D e(t)
##
##   e(t) = u(t) + M y(t)
##
## in order to build
##   .
## K x(t) = F x(t) + G u(t)
##
##   y(t) = H x(t) + J u(t)
##
## Solution: Laplace Transformation
## E s X(s) = A X(s) + B U(s) + B M Y(s)                     [1]
##
##     Y(s) = C X(s) + D U(s) + D M Y(s)                     [2]
##
## solve [2] for Y(s)
## Y(s) = [I - D M]^(-1) C X(s)  +  [I - D M]^(-1) D U(s)
##
## substitute Z = [I - D M]^(-1)
## Y(s) = Z C X(s) + Z D U(s)                                [3]
##
## insert [3] in [1], solve for X(s)
## X(s) = [s E - (A + B M Z C)]^(-1) (B + B M Z D) U(s)      [4]
##
## inserting [4] in [3] finally yields
## Y(s) = Z C [s E - (A + B M Z C)]^(-1) (B + B M Z D) U(s)  +  Z D U(s)
##        \ /    |   \_____ _____/       \_____ _____/          \ /
##         H     K         F                   G                 J
## @end group
## @end example
## @end deftypefn

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: September 2009
## Version: 0.2

function sys = __sys_connect__ (sys, m)

  a = sys.a;
  b = sys.b;
  c = sys.c;
  d = sys.d;

  z = eye (rows (d)) - d*m;

  if (rcond (z) < eps)  # check for singularity
    error ("ss: sys_connect: (I - D*M) singular");
  endif

  z = inv (z);

  sys.a = a + b*m*z*c;  # F
  sys.b = b + b*m*z*d;  # G
  sys.c = z*c;          # H
  sys.d = z*d;          # J

  ## sys.e remains constant: [] for ss models, e for dss models

endfunction