## Copyright (C) 2012   Lukas F. Reichlin
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
## @deftypefn{Function File} {} ramp (@var{sys})
## @deftypefnx{Function File} {} ramp (@var{sys1}, @var{sys2}, @dots{}, @var{sysN})
## @deftypefnx{Function File} {} ramp (@var{sys1}, @var{'style1'}, @dots{}, @var{sysN}, @var{'styleN'})
## @deftypefnx{Function File} {} ramp (@var{sys1}, @dots{}, @var{t})
## @deftypefnx{Function File} {} ramp (@var{sys1}, @dots{}, @var{tfinal})
## @deftypefnx{Function File} {} ramp (@var{sys1}, @dots{}, @var{tfinal}, @var{dt})
## @deftypefnx{Function File} {[@var{y}, @var{t}, @var{x}] =} ramp (@var{sys})
## @deftypefnx{Function File} {[@var{y}, @var{t}, @var{x}] =} ramp (@var{sys}, @var{t})
## @deftypefnx{Function File} {[@var{y}, @var{t}, @var{x}] =} ramp (@var{sys}, @var{tfinal})
## @deftypefnx{Function File} {[@var{y}, @var{t}, @var{x}] =} ramp (@var{sys}, @var{tfinal}, @var{dt})
## Ramp response of LTI system.
## If no output arguments are given, the response is printed on the screen.
## @iftex
## @tex
## $$ r(t) = t \\, \\cdot \\, h(t) $$
## @end tex
## @end iftex
## @ifnottex
##
## @example
## r(t) = t * h(t)
## @end example
##
## @end ifnottex
##
## @strong{Inputs}
## @table @var
## @item sys
## LTI model.
## @item t
## Time vector.  Should be evenly spaced.  If not specified, it is calculated by
## the poles of the system to reflect adequately the response transients.
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
## @seealso{impulse, initial, lsim, step}
## @end deftypefn

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: October 2012
## Version: 0.1

function [y_r, t_r, x_r] = ramp (varargin)

  if (nargin == 0)
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

  [y, t, x] = __time_response__ ("ramp", varargin, sysname, ! nargout);

  if (nargout)
    y_r = y{1};
    t_r = t{1};
    x_r = x{1};
  endif

endfunction
