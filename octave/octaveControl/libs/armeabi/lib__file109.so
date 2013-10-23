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
## @deftypefn {Function File} {[@var{l}, @var{p}, @var{e}] =} lqe (@var{sys}, @var{q}, @var{r})
## @deftypefnx {Function File} {[@var{l}, @var{p}, @var{e}] =} lqe (@var{sys}, @var{q}, @var{r}, @var{s})
## @deftypefnx {Function File} {[@var{l}, @var{p}, @var{e}] =} lqe (@var{a}, @var{g}, @var{c}, @var{q}, @var{r})
## @deftypefnx {Function File} {[@var{l}, @var{p}, @var{e}] =} lqe (@var{a}, @var{g}, @var{c}, @var{q}, @var{r}, @var{s})
## @deftypefnx {Function File} {[@var{l}, @var{p}, @var{e}] =} lqe (@var{a}, @var{[]}, @var{c}, @var{q}, @var{r})
## @deftypefnx {Function File} {[@var{l}, @var{p}, @var{e}] =} lqe (@var{a}, @var{[]}, @var{c}, @var{q}, @var{r}, @var{s})
## Kalman filter for continuous-time systems.
##
## @example
## @group
## .
## x = Ax + Bu + Gw   (State equation)
## y = Cx + Du + v    (Measurement Equation)
## E(w) = 0, E(v) = 0, cov(w) = Q, cov(v) = R, cov(w,v) = S
## @end group
## @end example
##
## @strong{Inputs}
## @table @var
## @item sys
## Continuous or discrete-time LTI model (p-by-m, n states).
## @item a
## State transition matrix of continuous-time system (n-by-n).
## @item g
## Process noise matrix of continuous-time system (n-by-g).
## If @var{g} is empty @code{[]}, an identity matrix is assumed.
## @item c
## Measurement matrix of continuous-time system (p-by-n).
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
## @item l
## Kalman filter gain matrix (n-by-p).
## @item p
## Unique stabilizing solution of the continuous-time Riccati equation (n-by-n).
## Symmetric matrix.  If @var{sys} is a discrete-time model, the solution of the
## corresponding discrete-time Riccati equation is returned.
## @item e
## Closed-loop poles (n-by-1).
## @end table
##
## @strong{Equations}
## @example
## @group
## .
## x = Ax + Bu + L(y - Cx -Du)          
##
## E = eig(A - L*C)
## 
## @end group
## @end example
## @seealso{dare, care, dlqr, lqr, dlqe}
## @end deftypefn

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: April 2012
## Version: 0.1

function [l, p, e] = lqe (a, g, c, q = [], r = [], s = [])

  if (nargin < 3 || nargin > 6)
    print_usage ();
  endif

  if (isa (a, "lti"))
    [l, p, e] = lqr (a.', g, c, q);         # lqe (sys, q, r, s), g=I, works like  lqr (sys.', q, r, s).'
  elseif (isempty (g))
    [l, p, e] = lqr (a.', c.', q, r, s);    # lqe (a, [], c, q, r, s), g=I, works like  lqr (a.', c.', q, r, s).'
  elseif (columns (g) != rows (q) || ! issquare (q))
    error ("lqe: matrices g(%dx%d) and q(%dx%d) have incompatible dimensions", \
            rows (g), columns (g), rows (q), columns (q));
  elseif (isempty (s))
    [l, p, e] = lqr (a.', c.', g*q*g.', r);
  elseif (columns (g) != rows (s))
    error ("lqe: matrices g(%dx%d) and s(%dx%d) have incompatible dimensions", \
            rows (g), columns (g), rows (s), columns (s));
  else
    [l, p, e] = lqr (a.', c.', g*q*g.', r, g*s);
  endif

  l = l.';
  
  ## NOTE: for discrete-time sys, the solution L' from DARE
  ##       is different to L from DLQE (a, s)

endfunction
