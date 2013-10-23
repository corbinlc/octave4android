## Copyright (C) 2009, 2012   Lukas F. Reichlin
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
## @deftypefn{Function File} {} initial (@var{sys}, @var{x0})
## @deftypefnx{Function File} {} initial (@var{sys1}, @var{sys2}, @dots{}, @var{sysN}, @var{x0})
## @deftypefnx{Function File} {} initial (@var{sys1}, @var{'style1'}, @dots{}, @var{sysN}, @var{'styleN'}, @var{x0})
## @deftypefnx{Function File} {} initial (@var{sys1}, @dots{}, @var{x0}, @var{t})
## @deftypefnx{Function File} {} initial (@var{sys1}, @dots{}, @var{x0}, @var{tfinal})
## @deftypefnx{Function File} {} initial (@var{sys1}, @dots{}, @var{x0}, @var{tfinal}, @var{dt})
## @deftypefnx{Function File} {[@var{y}, @var{t}, @var{x}] =} initial (@var{sys}, @var{x0})
## @deftypefnx{Function File} {[@var{y}, @var{t}, @var{x}] =} initial (@var{sys}, @var{x0}, @var{t})
## @deftypefnx{Function File} {[@var{y}, @var{t}, @var{x}] =} initial (@var{sys}, @var{x0}, @var{tfinal})
## @deftypefnx{Function File} {[@var{y}, @var{t}, @var{x}] =} initial (@var{sys}, @var{x0}, @var{tfinal}, @var{dt})
## Initial condition response of state-space model.
## If no output arguments are given, the response is printed on the screen.
##
## @strong{Inputs}
## @table @var
## @item sys
## State-space model.
## @item x0
## Vector of initial conditions for each state.
## @item t
## Optional time vector.  Should be evenly spaced.  If not specified, it is calculated
## by the poles of the system to reflect adequately the response transients.
## @item tfinal
## Optional simulation horizon.  If not specified, it is calculated by
## the poles of the system to reflect adequately the response transients.
## @item dt
## Optional sampling time.  Be sure to choose it small enough to capture transient
## phenomena.  If not specified, it is calculated by the poles of the system.
## @item 'style'
## Line style and color, e.g. 'r' for a solid red line or '-.k' for a dash-dotted
## black line.  See @command{help plot} for details.
## @end table
##
## @strong{Outputs}
## @table @var
## @item y
## Output response array.  Has as many rows as time samples (length of t)
## and as many columns as outputs.
## @item t
## Time row vector.
## @item x
## State trajectories array.  Has @code{length (t)} rows and as many columns as states.
## @end table
##
## @strong{Example}
## @example
## @group
##                    .
## Continuous Time:   x = A x ,   y = C x ,   x(0) = x0
##
## Discrete Time:   x[k+1] = A x[k] ,   y[k] = C x[k] ,   x[0] = x0
## @end group
## @end example
##
## @seealso{impulse, lsim, step}
## @end deftypefn

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: October 2009
## Version: 0.2

function [y_r, t_r, x_r] = initial (varargin)

  if (nargin < 2)
    print_usage ();
  endif

  if (nargout)
    sysname = {};
  else  
    sys_idx = find (cellfun (@isa, varargin, {"lti"}));
    len = length (sys_idx);
    sysname = cell (len, 1);
    for k = 1 : len
      try
        sysname{k} = inputname(sys_idx(k));
      catch
        sysname{k} = "";
      end_try_catch
    endfor
  endif

  [y, t, x] = __time_response__ ("initial", varargin, sysname, ! nargout);

  if (nargout)
    y_r = y{1};
    t_r = t{1};
    x_r = x{1};
  endif

endfunction


%!shared initial_c, initial_c_exp, initial_d, initial_d_exp
%!
%! A = [ -2.8    2.0   -1.8
%!       -2.4   -2.0    0.8
%!        1.1    1.7   -1.0 ];
%!
%! B = [ -0.8    0.5    0
%!        0      0.7    2.3
%!       -0.3   -0.1    0.5 ];
%!
%! C = [ -0.1    0     -0.3
%!        0.9    0.5    1.2
%!        0.1   -0.1    1.9 ];
%!
%! D = [ -0.5    0      0
%!        0.1    0      0.3
%!       -0.8    0      0   ];
%!
%! x_0 = [1, 2, 3];
%!
%! sysc = ss (A, B, C, D);
%!
%! [yc, tc, xc] = initial (sysc, x_0, 0.2, 0.1);
%! initial_c = [yc, tc, xc];
%!
%! sysd = c2d (sysc, 2);
%!
%! [yd, td, xd] = initial (sysd, x_0, 4);
%! initial_d = [yd, td, xd];
%!
%! ## expected values computed by the "dark side"
%!
%! yc_exp = [ -1.0000    5.5000    5.6000
%!            -0.9872    5.0898    5.7671
%!            -0.9536    4.6931    5.7598 ];
%!
%! tc_exp = [  0.0000
%!             0.1000
%!             0.2000 ];
%!
%! xc_exp = [  1.0000    2.0000    3.0000
%!             0.5937    1.6879    3.0929
%!             0.2390    1.5187    3.0988 ];
%!
%! initial_c_exp = [yc_exp, tc_exp, xc_exp];
%!
%! yd_exp = [ -1.0000    5.5000    5.6000
%!            -0.6550    3.1673    4.2228
%!            -0.5421    2.6186    3.4968 ];
%!
%! td_exp = [  0
%!             2
%!             4 ];
%!
%! xd_exp = [  1.0000    2.0000    3.0000
%!            -0.4247    1.5194    2.3249
%!            -0.3538    1.2540    1.9250 ];
%!
%! initial_d_exp = [yd_exp, td_exp, xd_exp];
%!
%!assert (initial_c, initial_c_exp, 1e-4)
%!assert (initial_d, initial_d_exp, 1e-4)
