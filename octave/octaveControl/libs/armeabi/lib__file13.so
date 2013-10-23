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
## @deftypefn{Function File} {@var{P} =} augw (@var{G}, @var{W1}, @var{W2}, @var{W3})
## Extend plant for stacked S/KS/T problem.  Subsequently, the robust control problem
## can be solved by h2syn or hinfsyn.
##
## @strong{Inputs}
## @table @var
## @item G
## LTI model of plant.
## @item W1
## LTI model of performance weight.  Bounds the largest singular values of sensitivity @var{S}.
## Model must be empty @code{[]}, SISO or of appropriate size.
## @item W2
## LTI model to penalize large control inputs.  Bounds the largest singular values of @var{KS}.
## Model must be empty @code{[]}, SISO or of appropriate size.
## @item W3
## LTI model of robustness and noise sensitivity weight.  Bounds the largest singular values of 
## complementary sensitivity @var{T}.  Model must be empty @code{[]}, SISO or of appropriate size.
## @end table
##
## All inputs must be proper/realizable.
## Scalars, vectors and matrices are possible instead of LTI models.
##
## @strong{Outputs}
## @table @var
## @item P
## State-space model of augmented plant.
## @end table
##
## @strong{Block Diagram}
## @example
## @group
##
##     | W1 | -W1*G |     z1 = W1 r  -  W1 G u
##     | 0  |  W2   |     z2 =          W2   u
## P = | 0  |  W3*G |     z3 =          W3 G u
##     |----+-------|
##     | I  |    -G |     e  =    r  -     G u
## @end group
## @end example
## @example
## @group
##                                                       +------+  z1
##             +---------------------------------------->|  W1  |----->
##             |                                         +------+
##             |                                         +------+  z2
##             |                 +---------------------->|  W2  |----->
##             |                 |                       +------+
##  r   +    e |   +--------+  u |   +--------+  y       +------+  z3
## ----->(+)---+-->|  K(s)  |----+-->|  G(s)  |----+---->|  W3  |----->
##        ^ -      +--------+        +--------+    |     +------+
##        |                                        |
##        +----------------------------------------+
## @end group
## @end example
## @example
## @group
##                +--------+
##                |        |-----> z1 (p1x1)          z1 = W1 e
##  r (px1) ----->|  P(s)  |-----> z2 (p2x1)          z2 = W2 u
##                |        |-----> z3 (p3x1)          z3 = W3 y
##  u (mx1) ----->|        |-----> e (px1)            e = r - y
##                +--------+
## @end group
## @end example
## @example
## @group
##                +--------+  
##        r ----->|        |-----> z
##                |  P(s)  |
##        u +---->|        |-----+ e
##          |     +--------+     |
##          |                    |
##          |     +--------+     |
##          +-----|  K(s)  |<----+
##                +--------+
## @end group
## @end example
##
## @strong{References}@*
## [1] Skogestad, S. and Postlethwaite I. (2005)
## @cite{Multivariable Feedback Control: Analysis and Design:
## Second Edition}.  Wiley.
##
## @seealso{h2syn, hinfsyn, mixsyn}
## @end deftypefn

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: December 2009
## Version: 0.2

function P = augw (G, W1 = [], W2 = [], W3 = [])

  if (nargin == 0 || nargin > 4)
    print_usage ();
  endif

  G = ss (G);
  [p, m] = size (G);

  [W1, p1, m1] = __adjust_weighting__ (W1, p);
  [W2, p2, m2] = __adjust_weighting__ (W2, m);
  [W3, p3, m3] = __adjust_weighting__ (W3, p);

  ## Pr = [1; 0; 0; 1];
  ## Pu = [-1; 0; 1; -1]*G + [0; 1; 0; 0];

  Pr = ss ([eye(m1,p)  ;
            zeros(m2,p);
            zeros(m3,p);
            eye(p,p)   ]);

  Pu1 = ss ([-eye(m1,p)  ;
              zeros(m2,p);
              eye(m3,p)  ;
             -eye(p,p)   ]);

  Pu2 = ss ([zeros(m1,m);
             eye(m2,m)  ;
             zeros(m3,m);
             zeros(p,m) ]);

  Pu = Pu1 * G  +  Pu2;

  P = append (W1, W2, W3, eye (p, p)) * [Pr, Pu];

endfunction


function [W, p, m] = __adjust_weighting__ (W, s)

  W = ss (W);
  [p, m] = size (W);

  if (m == 0 || m == s)               # model is empty or has s inputs
    return;
  elseif (m == 1)                     # model is SISO or SIMO
    tmp = W;
    for k = 2 : s
      W = append (W, tmp);            # stack single-input model s times
    endfor
    [p, m] = size (W);                # weighting function now of correct size
  else                                # model is MIMO or MISO
    error ("augw: %s must have 1 or %d inputs", inputname (1), s);
  endif 

endfunction
