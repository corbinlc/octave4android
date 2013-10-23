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
## @deftypefn {Function File} {@var{retsys} =} __sys_connect__ (@var{sys}, @var{M})
## This function is part of the Model Abstraction Layer.  No argument checking.
## For internal use only.
## @example
## @group
## Problem: Solve the system equations of
## Y(s) = G(s) E(s)
## E(s) = U(s) + M Y(s)
## in order to build
## Y(s) = H(s) U(s)
## Solution:
## Y(s) = G(s) [I - M G(s)]^-1 U(s)
##      = [I - G(s) M]^-1 G(s) U(s)
## @end group
## @end example
## @end deftypefn

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: October 2009
## Version: 0.2

function sys = __sys_connect__ (sys, M)

  [p, m] = size (sys);

  num = sys.num;
  den = sys.den;

  ## TODO: Implementation for MIMO models.  There are three possibilities:
  ##       1. An _algebraic_ solution of the inversion problem in order
  ##          to not introduce unwanted zero/pole pairs.  Difficult.
  ##       2. A numeric solution of the inversion problem.  Afterwards,
  ##          elimination of _all_ zero/pole pairs by minreal.  Bad.
  ##       3. Conversion to state-space, solving the problem there and
  ##          converting back to transfer function.  Easier, but obviously,
  ##          this way needs MIMO __sys2ss__ and __sys2tf__ implementations
  ##          as described in Thomas Kailath's classic "Linear Systems".
  ##          Possibly this is the way to go, but it works for proper systems
  ##          only unless descriptor state-space models are implemented.

  ## WARNING: The code below is a cheap hack to quickly enable SISO TF connections.

  ## TODO: Check for den = 0, e.g. in feedback (tf (1), tf (-1))

  if (p == 2 && m == 2 && num{1,2} == 0 && num{2,1} == 0 \
      && M(1,1) == 0 && M(2,2) == 0)
    ## mtimes, feedback
    sys.num(1,1) = num{1,1} * den{2,2};
    sys.num(1,2) = M(1,2) * num{1,1} * num{2,2};
    sys.num(2,1) = M(2,1) * num{1,1} * num{2,2};
    sys.num(2,2) = num{2,2} * den{1,1};

    sys.den(:) = den{1,1} * den{2,2}  -  M(1,2) * M(2,1) * num{1,1} * num{2,2};

  elseif (p == 3 && m == 4 && num{1,3} == 0 && num{1,4} == 0 \
          && num{2,1} == 0 && num{2,2} == 0 && num{2,4} == 0 \
          && num{3,1} == 0 && num{3,2} == 0 && num{3,3} == 0 \
          && M == [0, 1, 0; 0, 0, 1; 0, 0, 0; 0, 0, 0])
    ## horzcat [sys1, sys2], plus, minus
    sys.num(:) = tfpoly (0);
    sys.den(:) = tfpoly (1);

    sys.num(1,1) = num{1,1};
    sys.num(1,2) = num{1,2};
    sys.num(1,3) = num{1,1} * num{2,3};
    sys.num(1,4) = num{1,2} * num{3,4};
    sys.num(2,3) = num{2,3};
    sys.num(3,4) = num{3,4};

    sys.den(1,3) = den{2,3};
    sys.den(1,4) = den{3,4};
    sys.den(2,3) = den{2,3};
    sys.den(3,4) = den{3,4};

  elseif (p == 3 && m == 3 && num{1,3} == 0 \
          && num{2,1} == 0 && num{2,2} == 0 && num{2,3} == 1 \
          && num{3,1} == 0 && num{3,2} == 0 && num{3,3} == 1 \
          && M == [0, 1, 0; 0, 0, 1; 0, 0, 0])
    ## plus, minus
    sys.num(1,3) = num{1,1} * den{1,2}  +  num{1,2} * den{1,1};
    sys.den(1,3) = den{1,1} * den{1,2};

  elseif (p == 4 && m == 3 && num{1,2} == 0 && num{1,3} == 0 \
          && num{2,1} == 0 && num{2,3} == 0 \
          && num{3,1} == 0 && num{3,2} == 0 && num{3,3} == 1 \
          && num{4,1} == 0 && num{4,2} == 0 && num{4,3} == 1 \
          && M == [0, 0, 1, 0; 0, 0, 0, 1; 0, 0, 0, 0])
    ## vertcat [sys1; sys2]
    sys.num(1,3) = num{1,1};
    sys.num(2,3) = num{2,2};
    
    sys.den(1,3) = den{1,1};
    sys.den(2,3) = den{2,2};

  else
    ## MIMO case, convert to state-space and back.
    warning ("tf: converting to minimal state-space for MIMO TF interconnections");
    sys = tf (__sys_connect__ (ss (sys), M));
    
  endif

endfunction
