## Copyright (C) 2009, 2010, 2012   Lukas F. Reichlin
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
## Common code for the time response functions step, impulse and initial.

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: October 2009
## Version: 0.4

function [y, t, x] = __time_response__ (response, args, sysname, plotflag)

  sys_idx = find (cellfun (@isa, args, {"lti"}));                   # look for LTI models, 'find' needed for plot styles
  sys_cell = cellfun (@ss, args(sys_idx), "uniformoutput", false);  # convert to state-space

  if (! size_equal (sys_cell{:}))
    error ("%s: models must have equal sizes", response);
  endif

  vec_idx = find (cellfun (@is_real_matrix, args));                 # indices of vector arguments
  n_vec = length (vec_idx);                                         # number of vector arguments
  n_sys = length (sys_cell);                                        # number of LTI systems

  tfinal = [];
  dt = [];
  x0 = [];

  ## extract tfinal/t, dt, x0 from args
  if (strcmpi (response, "initial"))
    if (n_vec < 1)
      error ("initial: require initial state vector 'x0'");
    else                                                            # initial state vector x0 specified
      arg = args{vec_idx(1)};
      if (is_real_vector (arg))
        x0 = arg;
      else
        error ("initial: initial state vector 'x0' must be a vector of real values");
      endif
      if (n_vec > 1)                                                # tfinal or time vector t specified
        arg = args{vec_idx(2)};
        if (issample (arg))
          tfinal = arg;
        elseif (isempty (arg))
          ## tfinal = [];                                           # nothing to do here
        elseif (is_real_vector (arg))
          dt = abs (arg(2) - arg(1));                               # assume that t is regularly spaced
          tfinal = arg(end);
        else  
          warning ("initial: argument number %d ignored", vec_idx(2));
        endif
        if (n_vec > 2)                                              # sampling time dt specified
          arg = args{vec_idx(3)};
          if (issample (arg))
            dt = arg;
          else
            warning ("initial: argument number %d ignored", vec_idx(3));
          endif
          if (n_vec > 3)
            warning ("initial: ignored");
          endif
        endif
      endif
    endif 
  else                                                              # step or impulse response
    if (n_vec > 0)                                                  # tfinal or time vector t specified
      arg = args{vec_idx(1)};
      if (issample (arg))
        tfinal = arg;
      elseif (isempty (arg))
        ## tfinal = [];                                             # nothing to do here
      elseif (is_real_vector (arg))
        dt = abs (arg(2) - arg(1));                                 # assume that t is regularly spaced
        tfinal = arg(end);
      else  
        warning ("%s: argument number %d ignored", response, vec_idx(1));
      endif
      if (n_vec > 1)                                                # sampling time dt specified
        arg = args{vec_idx(2)};
        if (issample (arg))
          dt = arg;
        else
          warning ("%s: argument number %d ignored", response, vec_idx(2));
        endif
        if (n_vec > 2)
          warning ("%s: ignored", response);
        endif
      endif
    endif
  endif
  ## TODO: share common code between initial and step/impulse

  [tfinal, dt] = cellfun (@__sim_horizon__, sys_cell, {tfinal}, {dt}, "uniformoutput", false);
  tfinal = max ([tfinal{:}]);

  ct_idx = cellfun (@isct, sys_cell);
  sys_dt_cell = sys_cell;
  tmp = cellfun (@c2d, sys_cell(ct_idx), dt(ct_idx), {"zoh"}, "uniformoutput", false);
  sys_dt_cell(ct_idx) = tmp;

  ## time vector
  t = @cellfun (@(dt) reshape (0 : dt : tfinal, [], 1), dt, "uniformoutput", false);

  ## function [y, x_arr] = __initial_response__ (sys, sys_dt, t, x0)
  ## function [y, x_arr] = __step_response__ (sys_dt, t)
  ## function [y, x_arr] = __impulse_response__ (sys, sys_dt, t)
  ## function [y, x_arr] = __ramp_response__ (sys_dt, t)

  switch (response)
    case "initial"
      [y, x] = cellfun (@__initial_response__, sys_dt_cell, t, {x0}, "uniformoutput", false);
    case "step"
      [y, x] = cellfun (@__step_response__, sys_dt_cell, t, "uniformoutput", false);
    case "impulse"
      [y, x] = cellfun (@__impulse_response__, sys_cell, sys_dt_cell, t, "uniformoutput", false);
    case "ramp"
      [y, x] = cellfun (@__ramp_response__, sys_dt_cell, t, "uniformoutput", false);
    otherwise
      error ("time_response: invalid response type");
  endswitch


  if (plotflag)                                         # display plot
    [p, m] = size (sys_cell{1});
    switch (response)
      case "initial"
        str = "Response to Initial Conditions";
        cols = 1;
        ## yfinal = zeros (p, 1);
      case "step"
        str = "Step Response";
        cols = m;
        ## yfinal = dcgain (sys_cell{1});
      case "impulse"
        str = "Impulse Response";
        cols = m;
        ## yfinal = zeros (p, m);
      case "ramp"
        str = "Ramp Response";
        cols = m;
      otherwise
        error ("time_response: invalid response type");
    endswitch
    
    style_idx = find (cellfun (@ischar, args));
    outname = get (sys_cell{end}, "outname");
    outname = __labels__ (outname, "y");
    colororder = get (gca, "colororder");
    rc = rows (colororder);
  
    for k = 1 : n_sys                                   # for every system
      if (k == n_sys)
        lim = numel (args);
      else
        lim = sys_idx(k+1);
      endif
      style = args(style_idx(style_idx > sys_idx(k) & style_idx <= lim));
      if (isempty (style))
        color = colororder(1+rem (k-1, rc), :);
        style = {"color", color};   
      endif
      if (ct_idx(k))                                    # continuous-time system                                           
        for i = 1 : p                                   # for every output
          for j = 1 : cols                              # for every input (except for initial where cols=1)
            subplot (p, cols, (i-1)*cols+j);
            plot (t{k}, y{k}(:, i, j), style{:});
            hold on;
            grid on;
            if (k == n_sys)
              axis tight
              ylim (__axis_margin__ (ylim))
              if (j == 1)
                ylabel (outname{i});
                if (i == 1)
                  title (str);
                endif
              endif
            endif
          endfor
        endfor
      else                                              # discrete-time system
        for i = 1 : p                                   # for every output
          for j = 1 : cols                              # for every input (except for initial where cols=1)
            subplot (p, cols, (i-1)*cols+j);
            stairs (t{k}, y{k}(:, i, j), style{:});
            hold on;
            grid on;
            if (k == n_sys)
              axis tight;
              ylim (__axis_margin__ (ylim))
              if (j == 1)
                ylabel (outname{i});
                if (i == 1)
                  title (str);
                endif
              endif
            endif
          endfor
        endfor
      endif
    endfor
    xlabel ("Time [s]");
    if (p == 1 && m == 1)
      legend (sysname)
    endif
    hold off;
  endif

endfunction


function [y, x_arr] = __initial_response__ (sys_dt, t, x0)

  [F, G, C, D] = ssdata (sys_dt);                       # system must be proper

  n = rows (F);                                         # number of states
  m = columns (G);                                      # number of inputs
  p = rows (C);                                         # number of outputs
  l_t = length (t);

  ## preallocate memory
  y = zeros (l_t, p);
  x_arr = zeros (l_t, n);

  ## initial conditions
  x = reshape (x0, [], 1);                              # make sure that x is a column vector

  if (n != length (x0) || ! is_real_vector (x0))
    error ("initial: x0 must be a real vector with %d elements", n);
  endif

  ## simulation
  for k = 1 : l_t
    y(k, :) = C * x;
    x_arr(k, :) = x;
    x = F * x;
  endfor

endfunction
  

function [y, x_arr] = __step_response__ (sys_dt, t)

  [F, G, C, D] = ssdata (sys_dt);       # system must be proper

  n = rows (F);                                         # number of states
  m = columns (G);                                      # number of inputs
  p = rows (C);                                         # number of outputs
  l_t = length (t);

  ## preallocate memory
  y = zeros (l_t, p, m);
  x_arr = zeros (l_t, n, m);

  for j = 1 : m                                         # for every input channel
    ## initial conditions
    x = zeros (n, 1);
    u = zeros (m, 1);
    u(j) = 1;

    ## simulation
    for k = 1 : l_t
      y(k, :, j) = C * x + D * u;
      x_arr(k, :, j) = x;
      x = F * x + G * u;
    endfor
  endfor

endfunction


function [y, x_arr] = __impulse_response__ (sys, sys_dt, t)

  [~, B] = ssdata (sys);
  [F, G, C, D, dt] = ssdata (sys_dt);                   # system must be proper
  dt = abs (dt);                                        # use 1 second if tsam is unspecified (-1)
  discrete = ! isct (sys);

  n = rows (F);                                         # number of states
  m = columns (G);                                      # number of inputs
  p = rows (C);                                         # number of outputs
  l_t = length (t);

  ## preallocate memory
  y = zeros (l_t, p, m);
  x_arr = zeros (l_t, n, m);

  for j = 1 : m                                         # for every input channel
    ## initial conditions
    u = zeros (m, 1);
    u(j) = 1;

    if (discrete)
      x = zeros (n, 1);                                 # zero by definition 
      y(1, :, j) = D * u / dt;
      x_arr(1, :, j) = x;
      x = G * u / dt;
    else
      x = B * u;                                        # B, not G!
      y(1, :, j) = C * x;
      x_arr(1, :, j) = x;
      x = F * x;
    endif

    ## simulation
    for k = 2 : l_t
      y (k, :, j) = C * x;
      x_arr(k, :, j) = x;
      x = F * x;
    endfor
  endfor

  if (discrete)
    y *= dt;
    x_arr *= dt;
  endif

endfunction


function [y, x_arr] = __ramp_response__ (sys_dt, t)

  [F, G, C, D] = ssdata (sys_dt);       # system must be proper

  n = rows (F);                                         # number of states
  m = columns (G);                                      # number of inputs
  p = rows (C);                                         # number of outputs
  l_t = length (t);

  ## preallocate memory
  y = zeros (l_t, p, m);
  x_arr = zeros (l_t, n, m);

  for j = 1 : m                                         # for every input channel
    ## initial conditions
    x = zeros (n, 1);
    u = zeros (m, l_t);
    u(j, :) = t;

    ## simulation
    for k = 1 : l_t
      y(k, :, j) = C * x + D * u(:, k);
      x_arr(k, :, j) = x;
      x = F * x + G * u(:, k);
    endfor
  endfor

endfunction


function [tfinal, dt] = __sim_horizon__ (sys, tfinal, Ts)

  ## code based on __stepimp__.m of Kai P. Mueller and A. Scottedward Hodel

  TOL = 1.0e-10;                                        # values below TOL are assumed to be zero
  N_MIN = 50;                                           # min number of points
  N_MAX = 2000;                                         # max number of points
  N_DEF = 1000;                                         # default number of points
  T_DEF = 10;                                           # default simulation time

  ev = pole (sys);
  n = length (ev);                                      # number of states/poles
  continuous = isct (sys);
  discrete = ! continuous;

  if (discrete)
    dt = Ts = abs (get (sys, "tsam"));
    ## perform bilinear transformation on poles in z
    for k = 1 : n
      pol = ev(k);
      if (abs (pol + 1) < TOL)
        ev(k) = 0;
      else
        ev(k) = 2 / Ts * (pol - 1) / (pol + 1);
      endif
    endfor
  endif

  ## remove poles near zero from eigenvalue array ev
  nk = n;
  for k = 1 : n
    if (abs (real (ev(k))) < TOL)
      ev(k) = 0;
      nk -= 1;
    endif
  endfor

  if (nk == 0)
    if (isempty (tfinal))
      tfinal = T_DEF;
    endif

    if (continuous)
      dt = tfinal / N_DEF;
    endif
  else
    ev = ev(find (ev));
    ev_max = max (abs (ev));

    if (continuous)
      dt = 0.2 * pi / ev_max;
    endif

    if (isempty (tfinal))
      ev_min = min (abs (real (ev)));
      tfinal = 5.0 / ev_min;

      ## round up
      yy = 10^(ceil (log10 (tfinal)) - 1);
      tfinal = yy * ceil (tfinal / yy);
    endif

    if (continuous)
      N = tfinal / dt;

      if (N < N_MIN)
        dt = tfinal / N_MIN;
      endif

      if (N > N_MAX)
        dt = tfinal / N_MAX;
      endif
    endif
  endif

  if (continuous && ! isempty (Ts))                     # catch case cont. system with dt specified
    dt = Ts;
  endif

endfunction
