## Copyright (C) 2009   Lukas F. Reichlin
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
## @deftypefn{Function File} {[@var{K}, @var{N}, @var{gamma}, @var{rcond}] =} mixsyn (@var{G}, @var{W1}, @var{W2}, @var{W3}, @dots{})
## Solve stacked S/KS/T H-infinity problem.  Bound the largest singular values
## of @var{S} (for performance), @var{K S} (to penalize large inputs) and
## @var{T} (for robustness and to avoid sensitivity to noise).
## In other words, the inputs r are excited by a harmonic test signal.
## Then the algorithm tries to find a controller @var{K} which minimizes
## the H-infinity norm calculated from the outputs z.
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
## @item @dots{}
## Optional arguments of @command{hinfsyn}.  Type @command{help hinfsyn} for more information.
## @end table
##
## All inputs must be proper/realizable.
## Scalars, vectors and matrices are possible instead of LTI models.
##
## @strong{Outputs}
## @table @var
## @item K
## State-space model of the H-infinity (sub-)optimal controller.
## @item N
## State-space model of the lower LFT of @var{P} and @var{K}.
## @item gamma
## L-infinity norm of @var{N}.
## @item rcond
## Vector @var{rcond} contains estimates of the reciprocal condition
## numbers of the matrices which are to be inverted and
## estimates of the reciprocal condition numbers of the
## Riccati equations which have to be solved during the
## computation of the controller @var{K}.  For details,
## see the description of the corresponding SLICOT algorithm.
## @end table
##
## @strong{Block Diagram}
## @example
## @group
##
##                                     | W1 S   |
## gamma = min||N(K)||             N = | W2 K S | = lft (P, K)
##          K         inf              | W3 T   |
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
## @example
## @group
##                +--------+      
##        r ----->|  N(s)  |-----> z
##                +--------+
## @end group
## @end example
## @example
## @group
## Extended Plant:  P = augw (G, W1, W2, W3)
## Controller:      K = mixsyn (G, W1, W2, W3)
## Entire System:   N = lft (P, K)
## Open Loop:       L = G * K
## Closed Loop:     T = feedback (L)
## @end group
## @end example
##
## @strong{Algorithm}@*
## Relies on commands @command{augw} and @command{hinfsyn},
## which use SLICOT SB10FD and SB10DD by courtesy of
## @uref{http://www.slicot.org, NICONET e.V.}
##
## @strong{References}@*
## [1] Skogestad, S. and Postlethwaite I. (2005)
## @cite{Multivariable Feedback Control: Analysis and Design:
## Second Edition}.  Wiley.
##
## @seealso{hinfsyn, augw}
## @end deftypefn

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: December 2009
## Version: 0.1

function [K, N, gamma, rcond] = mixsyn (G, W1 = [], W2 = [], W3 = [], varargin)

  if (nargin == 0)
    print_usage ();
  endif

  [p, m] = size (G);

  P = augw (G, W1, W2, W3);
  
  [K, N, gamma, rcond] = hinfsyn (P, p, m, varargin{:});

endfunction
