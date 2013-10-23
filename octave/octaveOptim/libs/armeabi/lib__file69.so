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

function [p_res, objf, cvg, outp] = __sqp__ (f, pin, hook)

  ## needed for some anonymous functions
  if (exist ("ifelse") != 5)
    ifelse = @ scalar_ifelse;
  endif

  n = length (pin);

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

  ## passed function for gradient of objective function
  grad_f = hook.dfdp;

  ## passed function for hessian of objective function
  if (isempty (hessian = hook.hessian))
    user_hessian = false;
    R = eye (n);
  else
    user_hessian = true;
  endif

  ## passed function for complementary pivoting
  cpiv = hook.cpiv;

  ## passed options
  ftol = hook.TolFun;
  niter = hook.MaxIter;
  if (isempty (niter)) niter = 20; endif
  fixed = hook.fixed;

  ## some useful variables derived from passed variables
  ##
  ## ...

  nz = 20 * eps; # This is arbitrary. Accuracy of equality constraints.

  ## backend-specific checking of options and constraints
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

  ## fill constant fields of hook for derivative-functions; some fields
  ## may be backend-specific
  dfdp_hook.fixed = fixed; # this may be handled by the frontend, but
                                # the backend still may add to it

  ## set up for iterations
  p = pin;
  f = f_pin;
  n_iter = 0;
  done = false;

  while (! done)

    niter++;

    if (user_hessian)

      H = hessian (p);
      idx = isnan (H);
      H(idx) = H.'(idx);
      if (any (isnan (H(:))))
        error ("some second derivatives undefined by user function");
      endif
      if (! isreal (H))
        error ("second derivatives given by user function not real");
      endif
      if (! issymmetric (H))
        error ("Hessian returned by user function not symmetric");
      endif

      R = directional_discrimination (H);

    endif


  endwhile

  ## return result

endfunction

function R = directional_discrimination (A)

  ## A is expected to be real and symmetric without checking
  ##
  ## "Directional discrimination" (Bard, Nonlinear Parameter Estimation,
  ## Academic Press, 1974). Compute R, which is "similar" to computing
  ## inv(H), but succeeds even if H is singular; R is positive definite
  ## and is tuned to avoid large steps of one parameter with respect to
  ## the steps of the others.

  ## make matrix Binv for scaling
  Binv = diag (A);
  nidx = ! (idx = Binv == 0);
  Binv(nidx) = 1 ./ sqrt (abs (Binv(nidx)));
  Binv(idx) = 1;
  Binv = diag (Binv);

  ## eigendecomposition of scaled hessian
  [V, L] = eig (Binv * A * Binv);

  ## A is symmetric, so V and L are real, delete any imaginary parts,
  ## which might occur due to inaccuracy
  V = real (V);
  L = real (L);

  ## actual directional discrimination, does not exactly follow Bard
  L = abs (diag (L)); # R should get positive definite
  L = max (L, .001 * max (L)); # avoids relatively large steps of
                               # parameters

  G = Binv * V;

  R = G * diag (1 ./ L) * G.';

endfunction
