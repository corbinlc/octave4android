## Copyright (C) 2012   Lukas F. Reichlin
## Copyright (C) 2012   Megan Zagrobelny
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
## @deftypefn {Function File} {[@var{m}, @var{p}, @var{z}, @var{e}] =} dlqe (@var{a}, @var{g}, @var{c}, @var{q}, @var{r})
## @deftypefnx {Function File} {[@var{m}, @var{p}, @var{z}, @var{e}] =} dlqe (@var{a}, @var{g}, @var{c}, @var{q}, @var{r}, @var{s})
## @deftypefnx {Function File} {[@var{m}, @var{p}, @var{z}, @var{e}] =} dlqe (@var{a}, @var{[]}, @var{c}, @var{q}, @var{r})
## @deftypefnx {Function File} {[@var{m}, @var{p}, @var{z}, @var{e}] =} dlqe (@var{a}, @var{[]}, @var{c}, @var{q}, @var{r}, @var{s})
## Kalman filter for discrete-time systems.
##
## @example
## @group
## x[k] = Ax[k] + Bu[k] + Gw[k]   (State equation)
## y[k] = Cx[k] + Du[k] + v[k]    (Measurement Equation)
## E(w) = 0, E(v) = 0, cov(w) = Q, cov(v) = R, cov(w,v) = S
## @end group
## @end example
##
## @strong{Inputs}
## @table @var
## @item a
## State transition matrix of discrete-time system (n-by-n).
## @item g
## Process noise matrix of discrete-time system (n-by-g).
## If @var{g} is empty @code{[]}, an identity matrix is assumed.
## @item c
## Measurement matrix of discrete-time system (p-by-n).
## @item q
## Process noise covariance matrix (g-by-g).
## @item r
## Measurement noise covariance matrix (p-by-p).
## @item s
## Optional cross term covariance matrix (g-by-p), s = cov(w,v).
## If @var{s} is empty @code{[]} or not specified, a zero matrix is assumed.
## @end table
##
## @strong{Outputs}
## @table @var
## @item m
## Kalman filter gain matrix (n-by-p).
## @item p
## Unique stabilizing solution of the discrete-time Riccati equation (n-by-n).
## Symmetric matrix.
## @item z
## Error covariance (n-by-n), cov(x(k|k)-x)
## @item e
## Closed-loop poles (n-by-1).
## @end table
##
## @strong{Equations}
## @example
## @group
## x[k|k] = x[k|k-1] + M(y[k] - Cx[k|k-1] - Du[k])
##
## x[k+1|k] = Ax[k|k] + Bu[k] for S=0
##
## x[k+1|k] = Ax[k|k] + Bu[k] + G*S*(C*P*C' + R)^-1*(y[k] - C*x[k|k-1]) for non-zero S
##          
##
## E = eig(A - A*M*C) for S=0
## 
## E = eig(A - A*M*C - G*S*(C*P*C' + Rv)^-1*C) for non-zero S
##  
## @end group
## @end example
## @seealso{dare, care, dlqr, lqr, lqe}
## @end deftypefn

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: April 2012
## Version: 0.1

function [m, p, z, e] = dlqe (a, g, c, q, r, s = [])

  if (nargin < 5 || nargin > 6)
    print_usage ();
  endif

  if (isempty (g))
    [p, e] = dare (a.', c.', q, r, s);   # dlqe (a, [], c, q, r, s), g=I
  elseif (columns (g) != rows (q) || ! issquare (q))
    error ("dlqe: matrices g(%dx%d) and q(%dx%d) have incompatible dimensions", \
            rows (g), columns (g), rows (q), columns (q));
  elseif (isempty (s))
    [p, e] = dare (a.', c.', g*q*g.', r);
  elseif (columns (g) != rows (s))
    error ("dlqe: matrices g(%dx%d) and s(%dx%d) have incompatible dimensions", \
            rows (g), columns (g), rows (s), columns (s));
  else
    [p, e] = dare (a.', c.', g*q*g.', r, g*s);
  endif
    
  m = p*c.' / (c*p*c.' + r);

  z = p - m*c*p;
  z = (z + z.') / 2;

endfunction
