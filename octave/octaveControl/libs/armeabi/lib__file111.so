## Copyright (C) 2009, 2010, 2011, 2012   Lukas F. Reichlin
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
## @deftypefn{Function File} {} lsim (@var{sys}, @var{u})
## @deftypefnx{Function File} {} lsim (@var{sys1}, @var{sys2}, @dots{}, @var{sysN}, @var{u})
## @deftypefnx{Function File} {} lsim (@var{sys1}, @var{'style1'}, @dots{}, @var{sysN}, @var{'styleN'}, @var{u})
## @deftypefnx{Function File} {} lsim (@var{sys1}, @dots{}, @var{u}, @var{t})
## @deftypefnx{Function File} {} lsim (@var{sys1}, @dots{}, @var{u}, @var{t}, @var{x0})
## @deftypefnx{Function File} {[@var{y}, @var{t}, @var{x}] =} lsim (@var{sys}, @var{u})
## @deftypefnx{Function File} {[@var{y}, @var{t}, @var{x}] =} lsim (@var{sys}, @var{u}, @var{t})
## @deftypefnx{Function File} {[@var{y}, @var{t}, @var{x}] =} lsim (@var{sys}, @var{u}, @var{t}, @var{x0})
## Simulate LTI model response to arbitrary inputs.  If no output arguments are given,
## the system response is plotted on the screen.
##
## @strong{Inputs}
## @table @var
## @item sys
## LTI model.  System must be proper, i.e. it must not have more zeros than poles.
## @item u
## Vector or array of input signal.  Needs @code{length(t)} rows and as many columns
## as there are inputs.  If @var{sys} is a single-input system, row vectors @var{u}
## of length @code{length(t)} are accepted as well.
## @item t
## Time vector.  Should be evenly spaced.  If @var{sys} is a continuous-time system
## and @var{t} is a real scalar, @var{sys} is discretized with sampling time
## @code{tsam = t/(rows(u)-1)}.  If @var{sys} is a discrete-time system and @var{t}
## is not specified, vector @var{t} is assumed to be @code{0 : tsam : tsam*(rows(u)-1)}.
## @item x0
## Vector of initial conditions for each state.  If not specified, a zero vector is assumed.
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
## Time row vector.  It is always evenly spaced.
## @item x
## State trajectories array.  Has @code{length (t)} rows and as many columns as states.
## @end table
##
## @seealso{impulse, initial, step}
## @end deftypefn

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: October 2009
## Version: 0.4

function [y_r, t_r, x_r] = lsim (varargin)

  ## TODO: individual initial state vectors 'x0' for each system
  ##       there would be conflicts with other arguments,
  ##       maybe a cell {x0_1, x0_2, ..., x0_N} would be a solution?

  if (nargin < 2)
    print_usage ();
  endif

  sys_idx = find (cellfun (@isa, varargin, {"lti"}));                   # look for LTI models, 'find' needed for plot styles
  sys_cell = cellfun (@ss, varargin(sys_idx), "uniformoutput", false);  # convert to state-space

  if (! size_equal (sys_cell{:}))
    error ("lsim: models must have equal sizes");
  endif

  mat_idx = find (cellfun (@is_real_matrix, varargin));                 # indices of matrix arguments
  n_mat = length (mat_idx);                                         # number of vector arguments
  n_sys = length (sys_cell);                                        # number of LTI systems

  t = [];
  x0 = [];

  if (n_mat < 1)
    error ("lsim: require input signal 'u'");
  else
    arg = varargin{mat_idx(1)};
    if (is_real_vector (arg))
      u = reshape (arg, [], 1);                     # allow row vectors for single-input systems
    elseif (is_real_matrix (arg));
      u = arg;
    else
      error ("lsim: input signal 'u' must be an array of real numbers");
    endif
    if (n_mat > 1)                                  # time vector t
      arg = varargin{mat_idx(2)};
      if (is_real_vector (arg) || isempty (arg))
        t = arg;
      else
        error ("lsim: time vector 't' must be real-valued or empty");
      endif
      if (n_mat > 2)                                # initial state vector x0
        arg = varargin{mat_idx(3)};
        if (is_real_vector (arg))
          x0 = arg;
        else
          error ("lsim: initial state vector 'x0' must be a real-valued vector");
        endif
        if (n_mat > 3)
          warning ("lsim: ignored");
        endif
      endif
    endif
  endif

  ## function [y, t, x_arr] = __linear_simulation__ (sys, u, t, x0)
  
  [y, t, x] = cellfun (@__linear_simulation__, sys_cell, {u}, {t}, {x0}, "uniformoutput", false);


  if (nargout == 0)                             # plot information
    [p, m] = size (sys_cell{1});
    style_idx = find (cellfun (@ischar, varargin));
    ct_idx = cellfun (@isct, sys_cell);
    str = "Linear Simulation Results";
    outname = get (sys_cell{end}, "outname");
    outname = __labels__ (outname, "y");
    colororder = get (gca, "colororder");
    rc = rows (colororder);

    sysname = cell (n_sys, 1);

    for k = 1 : n_sys                                   # for every system
      if (k == n_sys)
        lim = nargin;
      else
        lim = sys_idx(k+1);
      endif
      style = varargin(style_idx(style_idx > sys_idx(k) & style_idx <= lim));
      if (isempty (style))
        color = colororder(1+rem (k-1, rc), :);
        style = {"color", color};   
      endif
      try
        sysname{k} = inputname(sys_idx(k));
      catch
        sysname{k} = "";
      end_try_catch
      if (ct_idx(k))                                    # continuous-time system                                           
        for i = 1 : p                                   # for every output
          subplot (p, 1, i);
          plot (t{k}, y{k}(:, i), style{:});
          hold on;
          grid on;
          if (k == n_sys)
            axis tight
            ylim (__axis_margin__ (ylim))
            ylabel (outname{i});
            if (i == 1)
              title (str);
            endif
          endif
        endfor
      else                                              # discrete-time system
        for i = 1 : p                                   # for every output
          subplot (p, 1, i);
          stairs (t{k}, y{k}(:, i), style{:});
          hold on;
          grid on;
          if (k == n_sys)
            axis tight;
            ylim (__axis_margin__ (ylim))
            ylabel (outname{i});
            if (i == 1)
              title (str);
            endif
          endif
        endfor
      endif
    endfor
    xlabel ("Time [s]");
    if (p == 1 && m == 1)
      legend (sysname)
    endif
    hold off;
  else                  # return values
    y_r = y{1};
    t_r = t{1};
    x_r = x{1};
  endif
  
endfunction


function [y, t, x_arr] = __linear_simulation__ (sys, u, t, x0)

  method = "zoh";
  [urows, ucols] = size (u);

  if (isct (sys))                               # continuous-time system
    if (isempty (t))                            # lsim (sys, u, [], ...)
      error ("lsim: invalid time vector");
    elseif (length (t) == 1)                    # lsim (sys, u, tfinal, ...)
      dt = t / (urows - 1);
      tinitial = 0;
      tfinal = t;
    else                                        # lsim (sys, u, t, ...)
      dt = t(2) - t(1);                         # assume that t is regularly spaced
      tinitial = t(1);
      tfinal = t(end);
    endif
    sys = c2d (sys, dt, method);                # convert to discrete-time model
  else                                          # discrete-time system
    dt = abs (get (sys, "tsam"));               # use 1 second as default if tsam is unspecified (-1)
    if (isempty (t))                            # lsim (sys, u)
      tinitial = 0;
      tfinal = dt * (urows - 1);
    elseif (length (t) == 1)                    # lsim (sys, u, tfinal)
      tinitial = 0;
      tfinal = t;
    else                                        # lsim (sys, u, t, ...)
      warning ("lsim: spacing of time vector has no effect on sampling time of discrete-time system");
      tinitial = t(1);
      tfinal = t(end);
    endif
  endif

  [A, B, C, D] = ssdata (sys);
  [p, m] = size (D);                            # number of outputs and inputs
  n = rows (A);                                 # number of states

  ## time vector
  t = reshape (tinitial : dt : tfinal, [], 1);
  len_t = length (t);

  if (urows != len_t)
    error ("lsim: input vector u must have %d rows", len_t);
  endif

  if (ucols != m)
    error ("lsim: input vector u must have %d columns", m);
  endif

  ## preallocate memory
  y = zeros (len_t, p);
  x_arr = zeros (len_t, n);

  ## initial conditions
  if (isempty (x0))
    x0 = zeros (n, 1);
  elseif (n != length (x0) || ! is_real_vector (x0))
    error ("lsim: x0 must be a vector with %d elements", n);
  endif

  x = reshape (x0, [], 1);                      # make sure that x is a column vector

  ## simulation
  for k = 1 : len_t
    y(k, :) = C * x  +  D * u(k, :).';
    x_arr(k, :) = x;
    x = A * x  +  B * u(k, :).';
  endfor

endfunction


## TODO: add test cases
