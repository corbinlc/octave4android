## Copyright (C) 2012 Olaf Till <i7tiol@t-online.de>
##
## This program is free software; you can redistribute it and/or modify
## it under the terms of the GNU General Public License as published by
## the Free Software Foundation; either version 3 of the License, or
## (at your option) any later version.
##
## This program is distributed in the hope that it will be useful,
## but WITHOUT ANY WARRANTY; without even the implied warranty of
## MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
## GNU General Public License for more details.
##
## You should have received a copy of the GNU General Public License
## along with this program; If not, see <http://www.gnu.org/licenses/>.

## The simulated annealing code is translated and adapted from siman.c,
## written by Mark Galassi, of the GNU Scientific Library.

function [p_res, objf, cvg, outp] = __siman__ (f, pin, hook)

  ## needed for some anonymous functions
  if (exist ("ifelse") != 5)
    ifelse = @ scalar_ifelse;
  endif

  ## passed constraints
  mc = hook.mc; # matrix of linear constraints
  vc = hook.vc; # vector of linear constraints
  f_cstr = hook.f_cstr; # function of all constraints
  df_cstr = hook.df_cstr; # function of derivatives of all constraints
  n_gencstr = hook.n_gencstr; # number of non-linear constraints
  eq_idx = hook.eq_idx; # logical index of equality constraints in all
                                # constraints
  lbound = hook.lbound; # bounds, subset of linear inequality
  ubound = hook.ubound; # constraints in mc and vc

  ## passed values of constraints for initial parameters
  pin_cstr = hook.pin_cstr;

  ## passed return value of f for initial parameters
  f_pin = hook.f_pin;

  ## passed function for complementary pivoting, currently sqp is used
  ## instead
  ##
  ## cpiv = hook.cpiv;

  ## passed simulated annealing parameters
  T_init = hook.siman.T_init;
  T_min = hook.siman.T_min;
  mu_T = hook.siman.mu_T;
  iters_fixed_T = hook.siman.iters_fixed_T;
  max_rand_step = hook.max_rand_step;

  ## passed options
  fixed = hook.fixed;
  verbose = strcmp (hook.Display, "iter");
  regain_constraints = hook.stoch_regain_constr;
  if ((siman_log = hook.siman_log))
    log = zeros (0, 5);
  endif
  if ((trace_steps = hook.trace_steps))
    trace = [0, 0, f_pin, pin.'];
  endif

  ## some useful variables derived from passed variables
  n = length (pin);
  sqp_hessian = 2 * eye (n);
  n_lconstr = length (vc);
  n_bounds = sum (lbound != -Inf) + sum (ubound != Inf);
  bidx = false (n_lconstr + n_gencstr, 1);
  bidx(1 : n_bounds) = true;
  ac_idx = true (n_lconstr + n_gencstr, 1);
  ineq_idx = ! eq_idx;
  leq_idx = eq_idx(1:n_lconstr);
  lineq_idx = ineq_idx(1:n_lconstr);
  lfalse_idx = false(n_lconstr, 1);

  nz = 20 * eps; # This is arbitrary. Accuracy of equality constraints.

  ## backend-specific checking of options and constraints
  ##
  ## equality constraints can not be met by chance
  if ((any (eq_idx) || any (lbound == ubound)) && ! regain_constraints)
    error ("If 'stoch_regain_constr' is not set, equality constraints or identical lower and upper bounds are not allowed by simulated annealing backend.");
  endif
  ##
  if (any (pin < lbound | pin > ubound) ||
      any (pin_cstr.inequ.lin_except_bounds < 0) ||
      any (pin_cstr.inequ.gen < 0) ||
      any (abs (pin_cstr.equ.lin)) >= nz ||
      any (abs (pin_cstr.equ.gen)) >= nz)
    error ("Initial parameters violate constraints.");
  endif
  ##
  if (all (fixed))
    error ("no free parameters");
  endif
  ##
  idx = isna (max_rand_step);
  max_rand_step(idx) = 0.005 * pin(idx);

  ## fill constant fields of hook for derivative-functions; some fields
  ## may be backend-specific
  dfdp_hook.fixed = fixed; # this may be handled by the frontend, but
                                # the backend still may add to it

  ## set up for iterations
  sizep = size (pin);
  p = best_p = pin;
  E = best_E = f_pin;
  T = T_init;
  n_evals = 1; # one has been done by frontend
  n_iter = 0;
  done = false;

  cvg = 1;

  ## simulated annealing
  while (! done)

    n_iter++;

    n_accepts = n_rejects = n_eless = 0;

    for id = 1 : iters_fixed_T

      new_p = p + max_rand_step .* (2 * rand (sizep) - 1);

      ## apply constraints
      if (regain_constraints)
        evidx = (abs ((ac = f_cstr (new_p, ac_idx))(eq_idx)) >= nz);
        ividx = (ac(ineq_idx) < 0);
        if (any (evidx) || any (ividx))
          nv = sum (evidx) + sum (ividx);
          if (sum (lbvidx = (new_p < lbound)) + \
              sum (ubvidx = (new_p > ubound)) == \
              nv)
            ## special case only bounds violated, set back to bound
            new_p(lbvidx) = lbound(lbvidx);
            new_p(ubvidx) = ubound(ubvidx);
          elseif (nv == 1 && \
                  sum (t_eq = (abs (ac(leq_idx)) >= nz)) + \
                  sum (t_inequ = (ac(lineq_idx) < 0)) == 1)
            ## special case only one linear constraint violated, set
            ## back perpendicularly to constraint
            tidx = lfalse_idx;
            tidx(leq_idx) = t_eq;
            tidx(lineq_idx) = t_inequ;
            c = mc(:, tidx);
            d = ac(tidx);
            new_p -= c * (d / (c.' * c));
          else
            ## other cases, set back keeping the distance to original
            ## 'new_p' minimal, using quadratic programming, or
            ## sequential quadratic programming for nonlinear
            ## constraints
            [new_p, discarded, sqp_info] = \
                sqp (new_p, \
                     {@(x)sumsq(x-new_p), \
                      @(x)2*(x-new_p), \
                      @(x)sqp_hessian}, \
                     {@(x)f_cstr(x,eq_idx), \
                      @(x)df_cstr(x,eq_idx, \
                                  setfield(hook,"f", \
                                           f_cstr(x,ac_idx)))}, \
                     {@(x)f_cstr(x,ineq_idx), \
                      @(x)df_cstr(x,ineq_idx, \
                                  setfield(hook,"f", \
                                           f_cstr(x,ac_idx)))});
            if (sqp_info != 101)
              cvg = 0;
              done = true;
              break;
            endif
          endif
        endif
      else
        n_retry_constr = 0;
        while (any (abs ((ac = f_cstr (new_p, ac_idx))(eq_idx)) >= nz) \
               || any (ac(ineq_idx) < 0))
          new_p = p + max_rand_step .* (2 * rand (sizep) - 1);
          n_retry_constr++;
        endwhile
        if (verbose && n_retry_constr)
          printf ("%i additional tries of random step to meet constraints\n",
                  n_retry_constr);
        endif
      endif

      new_E = f (new_p);
      n_evals++;

      if (new_E < best_E)
        best_p = new_p;
        best_E = new_E;
      endif
      if (new_E < E)
        ## take a step
        p = new_p;
        E = new_E;
        n_eless++;
        if (trace_steps)
          trace(end + 1, :) = [n_iter, id, E, p.'];
        endif
      elseif (rand (1) < exp (- (new_E - E) / T))
        ## take a step
        p = new_p;
        E = new_E;
        n_accepts++;
        if (trace_steps)
          trace(end + 1, :) = [n_iter, id, E, p.'];
        endif
      else
        n_rejects++;
      endif

    endfor # iters_fixed_T

    if (verbose)
      printf ("temperature no. %i: %e, energy %e,\n", n_iter, T, E);
      printf ("tries with energy less / not less but accepted / rejected:\n");
      printf ("%i / %i / %i\n", n_eless, n_accepts, n_rejects);
    endif

    if (siman_log)
      log(end + 1, :) = [T, E, n_eless, n_accepts, n_rejects];
    endif

    ## cooling
    T /= mu_T;
    if (T < T_min)
      done = true;
    endif

  endwhile

  ## return result
  p_res = best_p;
  objf = best_E;
  outp.niter = n_iter;
  if (trace_steps)
    outp.trace = trace;
  endif
  if (siman_log)
    outp.log = log;
  endif

endfunction
