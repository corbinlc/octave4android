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
## @deftypefn {Function File} {[@var{est}, @var{g}, @var{x}] =} kalman (@var{sys}, @var{q}, @var{r})
## @deftypefnx {Function File} {[@var{est}, @var{g}, @var{x}] =} kalman (@var{sys}, @var{q}, @var{r}, @var{s})
## @deftypefnx {Function File} {[@var{est}, @var{g}, @var{x}] =} kalman (@var{sys}, @var{q}, @var{r}, @var{[]}, @var{sensors}, @var{known})
## @deftypefnx {Function File} {[@var{est}, @var{g}, @var{x}] =} kalman (@var{sys}, @var{q}, @var{r}, @var{s}, @var{sensors}, @var{known})
## Design Kalman estimator for LTI systems.
##
## @strong{Inputs}
## @table @var
## @item sys
## Nominal plant model.
## @item q
## Covariance of white process noise.
## @item r
## Covariance of white measurement noise.
## @item s
## Optional cross term covariance.  Default value is 0.
## @item sensors
## Indices of measured output signals y from @var{sys}.  If omitted, all outputs are measured.
## @item known
## Indices of known input signals u (deterministic) to @var{sys}.  All other inputs to @var{sys}
## are assumed stochastic.  If argument @var{known} is omitted, no inputs u are known.
## @end table
##
## @strong{Outputs}
## @table @var
## @item est
## State-space model of the Kalman estimator.
## @item g
## Estimator gain.
## @item x
## Solution of the Riccati equation.
## @end table
##
## @strong{Block Diagram}
## @example
## @group
##                                  u  +-------+         ^
##       +---------------------------->|       |-------> y
##       |    +-------+     +       y  |  est  |         ^
## u ----+--->|       |----->(+)------>|       |-------> x
##            |  sys  |       ^ +      +-------+
## w -------->|       |       |
##            +-------+       | v
##
## Q = cov (w, w')     R = cov (v, v')     S = cov (w, v')
## @end group
## @end example
##
## @seealso{care, dare, estim, lqr}
## @end deftypefn

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: November 2009
## Version: 0.3

function [est, k, x] = kalman (sys, q, r, s = [], sensors = [], deterministic = [])

  ## TODO: type "current" for discrete-time systems

  if (nargin < 3 || nargin > 6 || ! isa (sys, "lti"))
    print_usage ();
  endif

  [a, b, c, d, e] = dssdata (sys, []);

  if (isempty (sensors))
    sensors = 1 : rows (c);
  endif

  stochastic = setdiff (1 : columns (b), deterministic);

  c = c(sensors, :);
  g = b(:, stochastic);
  h = d(sensors, stochastic);

  if (isempty (s))
    rbar = r + h*q*h.';
    sbar = g * q*h.';
  else
    rbar = r + h*q*h.'+ h*s + s.'*h.'; 
    sbar = g * (q*h.' + s);
  endif

  if (isct (sys))
    [x, l, k] = care (a.', c.', g*q*g.', rbar, sbar, e.');
  else
    [x, l, k] = dare (a.', c.', g*q*g.', rbar, sbar, e.');
  endif

  k = k.';

  est = estim (sys, k, sensors, deterministic);

endfunction


%!shared m, m_exp, g, g_exp, x, x_exp
%! sys = ss (-2, 1, 1, 3);
%! [est, g, x] = kalman (sys, 1, 1, 1);
%! [a, b, c, d] = ssdata (est);
%! m = [a, b; c, d];
%! m_exp = [-2.25, 0.25; 1, 0; 1, 0];
%! g_exp = 0.25;
%! x_exp = 0;
%!assert (m, m_exp, 1e-2);
%!assert (g, g_exp, 1e-2);
%!assert (x, x_exp, 1e-2);