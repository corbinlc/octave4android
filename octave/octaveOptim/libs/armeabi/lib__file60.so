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

function [p_res, objf, cvg, outp] = __lm_feasible__ (f, pin, hook)

  ## some backend specific defaults
  fract_prec_default = 0;
  max_fract_step_default = Inf;

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
    A = eye (n);
  else
    user_hessian = true;
  endif

  ## passed function for complementary pivoting
  cpiv = hook.cpiv;

  ## passed options
  ftol = hook.TolFun;
  if (isempty (niter = hook.MaxIter)) niter = 20; endif
  fixed = hook.fixed;
  maxstep = hook.max_fract_change;
  maxstep(isna (maxstep)) = max_fract_step_default;
  pprec = hook.fract_prec;
  pprec(isna (pprec)) = fract_prec_default;
  ## keep absolute precision positive for non-null relative precision;
  ## arbitrary value, added to parameters before multiplying with
  ## relative precision
  add_pprec = zeros (n, 1);
  add_pprec(pprec > 0) = sqrt (eps);
  ##
  verbose = strcmp (hook.Display, "iter");

  ## some useful variables derived from passed variables
  n_lcstr = size (vc, 1);
  have_constraints_except_bounds = \
      n_lcstr + n_gencstr > \
      sum (lbound != -Inf) + sum (ubound != Inf);
  ac_idx = true (n_lcstr + n_gencstr, 1); # index of all constraints
  nc_idx = false (n_lcstr + n_gencstr, 1); # none of all constraints
  gc_idx = cat (1, false (n_lcstr, 1), true (n_gencstr, 1)); # gen. constr.

  nz = 20 * eps; # This is arbitrary. Accuracy of equality constraints.

  ## backend-specific checking of options and constraints
  ##
  if (any (pin < lbound | pin > ubound) ||
      any (pin_cstr.inequ.lin_except_bounds < 0) ||
      any (pin_cstr.inequ.gen < 0) ||
      any (abs (pin_cstr.equ.lin) >= nz) ||
      any (abs (pin_cstr.equ.gen) >= nz))
    error ("Initial parameters violate constraints.");
  endif
  ##
  idx = lbound == ubound;
  if (any (idx))
    warning ("lower and upper bounds identical for some parameters, fixing the respective parameters");
    fixed(idx) = true;
  endif
  if (all (fixed))
    error ("no free parameters");
  endif
  if (n_gencstr > 0 && any (! isinf (maxstep)))
    warning ("setting both a maximum fractional step change of parameters and general constraints may result in inefficiency and failure");
  endif

  ## fill constant fields of hook for derivative-functions; some fields
  ## may be backend-specific
  dfdp_hook.fixed = fixed; # this may be handled by the frontend, but
                                # the backend still may add to it

  ## set up for iterations
  p = pbest = pin;
  vf = fbest = f_pin;
  iter = 0;
  done = false;
  ll = 1;
  ltab = [.1, 1, 1e2, 1e4, 1e6];
  chgprev = Inf (n, 1);
  df = [];
  c_act = false (n, 1);
  dca = zeros (n, 0);

  while (! done)

    iter++;

    ## gradient of objective function
    old_df = df;
    df = grad_f (p, setfield (dfdp_hook, "f", vf))(:);

    ## constraints, preparation of some constants
    v_cstr = f_cstr (p, ac_idx);
    old_c_act = c_act;
    old_dca = dca;
    c_act =  v_cstr < nz | eq_idx; # index of active constraints
    if (any (c_act))

      if (n_gencstr)
        ## full gradient is needed later
        dct = df_cstr (p, ac_idx, setfield (dfdp_hook, "f", v_cstr));
        dct(:, fixed) = 0; # for user supplied dfdp; necessary?
        dcat = dct(c_act, :);
      else
        dcat = df_cstr (p, c_act, setfield (dfdp_hook, "f", v_cstr));
        dcat(:, fixed) = 0; # for user supplied dfdp; necessary?
      endif

      dca = dcat.';

      a_eq_idx = eq_idx(c_act);

    else

      dca = zeros (n, 0);

    endif

    ## hessian of objectiv function
    if (user_hessian)

      A = hessian (p);
      idx = isnan (A);
      A(idx) = A.'(idx);
      if (any (isnan (A(:))))
        error ("some second derivatives undefined by user function");
      endif
      if (! isreal (A))
        error ("second derivatives given by user function not real");
      endif
      if (! issymmetric (A))
        error ("Hessian returned by user function not symmetric");
      endif

    elseif (iter > 1)

      if (any (chg))

        ## approximate Hessian of Lagrangian

        ## I wonder if this hassle here and above with accounting for
        ## changing active sets is indeed better than just approximating
        ## the Hessian only of the objective function.
        ##
        ## index, over all constraints, of constraints active both
        ## previously and currently
        s_c_act = old_c_act & c_act;
        ## index, over currently active constraints, of constraints
        ## active both previously and currently
        id_new = s_c_act(c_act);
        ## index, over previously active constraints, of constraints
        ## active both previously and currently
        id_old = s_c_act(old_c_act);
        ## gradients of currently active constraints which were also
        ## active previously
        dca_new_id = dca(:, id_new);
        ## gradients of previously active constraints which are also
        ## active currently
        dca_old_id = old_dca(:, id_old);
        ## index, over constraints active both previously and currently,
        ## of (old) non-zero multipliers (bidx set below previously)
        bidx_old_id = bidx(id_old);
        ## index, over (old) non-zero multipliers, of constraints active
        ## both previously and currently (bidx set below previously)
        old_l_idx = id_old(bidx);

        ## difference of derivatives of new and old active constraints,
        ## multiplied by multipliers, as used for BFGS update (lb set
        ## below previously)
        dch = (dca_new_id(:, bidx_old_id) - \
               dca_old_id(:, bidx_old_id)) * \
            lb(old_l_idx);
      
        y = df - old_df - dch;

        ## Damped BFGS according to Nocedal & Wright, 2nd edition,
        ## procedure 18.2.
        chgt = chg.';
        sAs = chgt * A * chg;
        cy = chgt * y;
        if (cy >= .2 * sAs)
          th = 1;
        else
          if ((den1 = sAs - cy) == 0)
            cvg = -4;
            break;
          endif
          th = .8 * sAs / den1;
        endif
        Ac = A * chg;
        r = th * y + (1 - th) * Ac;

        if ((den2 = chgt * r) == 0 || sAs == 0)
          cvg = -4;
          break;
        endif
        A += r * r.' / den2 - Ac * Ac.' / sAs;

      endif

    endif

    ## Inverse scaled decomposition A = G * (1 ./ L) * G.'
    ##
    ## make matrix Binv for scaling
    Binv = diag (A);
    nidx = ! (idx = Binv == 0);
    Binv(nidx) = 1 ./ sqrt (abs (Binv(nidx)));
    Binv(idx) = 1;
    Binv = diag (Binv);
    ## eigendecomposition of scaled A
    [V, L] = eig (Binv * A * Binv);
    L = diag (L);
    ## A is symmetric, so V and L are real, delete any imaginary parts,
    ## which might occur due to inaccuracy
    V = real (V);
    L = real (L);
    ##
    nminL = - min (L) * 1.1 / ltab(1);
    G = Binv * V;

    ## Levenberg/Marquardt
    fgoal = vf - (abs (vf) + sqrt (eps)) * ftol;
    for l = ltab

      ll = max (ll, nminL);
      l = max (1e-7, ll * l);

      R = G * diag (1 ./ (L + l)) * G.';

      ## step computation
      if (any (c_act))

        ## some constraints are active, quadratic programming

        tp = dcat * R;
        [lb, bidx, ridx, tbl] = cpiv (- tp * df, tp * dca, a_eq_idx);
        chg = R * (dca(:, bidx) * lb - df); # step direction

        ## indices for different types of constraints
        c_inact = ! c_act; # inactive constraints
        c_binding = c_unbinding = nc_idx;
        c_binding(c_act) = bidx; # constraints selected binding
        c_unbinding(c_act) = ridx; # constraints unselected binding
        c_nonbinding = c_act & ! (c_binding | c_unbinding); #
                                #constraints selected non-binding

      else

        ## no constraints are active, chg is the Levenberg/Marquardt step

        chg = - R * df; # step direction

        lb = zeros (0, 1);
        bidx = false (0, 1);

        ## indices for different types of constraints (meaning see above)
        c_inact = ac_idx;
        c_binding = nc_idx;
        c_unbinding = nc_idx;
        c_nonbinding = nc_idx;

      endif

      ## apply inactive and non-binding constraints to step width
      ##
      ## linear constraints
      k = 1;
      c_tp = c_inact(1:n_lcstr);
      mcit = mc(:, c_tp).';
      vci = vc(c_tp);
      hstep = mcit * chg;
      idx = hstep < 0;
      if (any (idx))
        k = min (1, min (- (vci(idx) + mcit(idx, :) * p) ./ \
                         hstep(idx)));
      endif
      ##
      ## general constraints
      if (n_gencstr)
        c_tp = gc_idx & (c_nonbinding | c_inact);
        if (any (c_tp) && any (f_cstr (p + k * chg, c_tp) < 0))
          [k, fval, info] = \
              fzero (@ (x) min (cat (1, \
                                     f_cstr (p + x * chg, c_tp), \
                                     k - x, \
                                     ifelse (x < 0, -Inf, Inf))), \
                     0);
          if (info != 1 || abs (fval) >= nz)
            error ("could not find stepwidth to satisfy inactive and non-binding general inequality constraints");
          endif
        endif
      endif
      ##
      chg = k * chg;

      ## if necessary, regain binding constraints and one of the
      ## possibly active previously inactive or non-binding constraints
      if (any (gc_idx & c_binding)) # none selected binding => none
                                # unselected binding
        ptp1 = p + chg;

        tp = true;
        nt_nosuc = true;
        lim = 20;
        while (nt_nosuc && lim >= 0)
          ## we keep d_p.' * inv (R) * d_p minimal in each step of the
          ## inner loop
          c_tp0 = c_inact | c_nonbinding;
          c_tp1 = c_inact | (gc_idx & c_nonbinding);
          btbl = tbl(bidx, bidx);
          c_tp2 = c_binding;
          ## once (any(tp)==false), it would not get true again even
          ## with the following assignment
          if (any (tp) && \
              any (tp = f_cstr (ptp1, c_tp1) < nz))
            ## keep only the first true entry in tp
            tp(tp) = logical (cat (1, 1, zeros (sum (tp) - 1, 1)));
            ## supplement binding index with one (the first) getting
            ## binding in c_tp1
            c_tp2(c_tp1) = tp;
            ## gradient of this added constraint
            caddt = dct(c_tp2 & ! c_binding, :);
            cadd = caddt.';
            C = dct(c_binding, :) * R * cadd;
            Ct = C.';
            T = [btbl, btbl * C; \
                 -Ct * btbl, caddt * R * cadd - Ct * btbl * C];
            btbl = gjp (T, size (T, 1));
          endif
          dcbt = dct(c_tp2, :);
          mfc = - R * dcbt.' * btbl;

          ptp2 = ptp1;
          nt_niter = nt_niter_start = 100;
          while (nt_nosuc && nt_niter >= 0)
            hv = f_cstr (ptp2, c_tp2);
            if (all (abs (hv) < nz))
              nt_nosuc = false;
              chg = ptp2 - p;
            else
              ptp2 = ptp2 + mfc * hv; # step should be zero for each
                                # component for which the parameter is
                                # "fixed"
            endif
            nt_niter--;
          endwhile

          if (nt_nosuc || \
              any (abs (chg) > abs (p .* maxstep)) || \
              any (f_cstr (ptp2, c_tp0) < -nz))
            ## if (nt_nosuc), regaining did not converge, else,
            ## regaining violated type 3 and 4.
            nt_nosuc = true;
            ptp1 = (p + ptp1) / 2;
          endif
          if (! nt_nosuc && \
              any ((tp = f_cstr (ptp2, c_unbinding)) < 0))
            [discarded, id] = min(tp);
            tid = find (ridx);
            id = tid(id); # index within active constraints
            unsuccessful_exchange = false;
            if (abs (tbl(id, id)) < nz) # Bard: not absolute value
              ## exchange this unselected binding constraint against a
              ## binding constraint, but not against an equality
              ## constraint
              tbidx = bidx & ! a_eq_idx;
              if (! any (tbidx))
                unsuccessful_exchange = true;
              else
                [discarded, idm] = max (abs (tbl(tbidx, id)));
                tid = find (tbidx);
                idm = tid(idm); # -> index within active constraints
                tbl = gjp (tbl, idm);
                bidx(idm) = false;
                ridx(idm) = true;
              endif
            endif
            if (unsuccessful_exchange)
              ## It probably doesn't look good now; this desperate last
              ## attempt is not in the original algortithm, since that
              ## didn't account for equality constraints.
              ptp1 = (p + ptp1) / 2;
            else
              tbl = gjp (tbl, id);
              bidx(id) = true;
              ridx(id) = false;
              c_binding = nc_idx;
              c_binding(c_act) = bidx;
              c_unbinding = nc_idx;
              c_unbinding(c_act) = ridx;
            endif
            ## regaining violated type 2 constraints
            nt_nosuc = true;
          endif
          lim--;
        endwhile
        if (nt_nosuc)
          error ("could not regain binding constraints");
        endif
      else
        ## check the maximal stepwidth and apply as necessary
        ochg = chg;
        idx = ! isinf (maxstep);
        limit = abs (maxstep(idx) .* p(idx));
        chg(idx) = min (max (chg(idx), - limit), limit);
        if (verbose && any (ochg != chg))
          printf ("Change in parameter(s): %s:maximal fractional stepwidth enforced", \
                  sprintf ("%d ", find (ochg != chg)));
        endif
      endif # regaining

      aprec = pprec .* (abs (pbest) + add_pprec);
      if (any (abs (chg) > 0.1 * aprec)) # only worth evaluating
                                # function if there is some
                                # non-miniscule change
        skipped = false;
        p_chg = p + chg;
        ## since the projection method may have slightly violated
        ## constraints due to inaccuracy, correct parameters to bounds
        ## --- but only if no further constraints are given, otherwise
        ## the inaccuracy in honoring them might increase by this
        if (! have_constraints_except_bounds)
          lidx = p_chg < lbound;
          uidx = p_chg > ubound;
          p_chg(lidx, 1) = lbound(lidx, 1);
          p_chg(uidx, 1) = ubound(uidx, 1);
          chg(lidx, 1) = p_chg(lidx, 1) - p(lidx, 1);
          chg(uidx, 1) = p_chg(uidx, 1) - p(uidx, 1);
        endif
        ##
        if (! isreal (vf_chg = f (p_chg)))
          error ("objective function not real");
        endif
        if (vf_chg < fbest)
          pbest = p_chg;
          fbest = vf_chg;
        endif
        if (vf_chg < fgoal) # <, not <=, since fgoal can be equal to vf
                                # if TolFun <= eps
          p = p_chg;
          vf = vf_chg;
          break;
        endif
      else
        skipped = true;
        break;
      endif
    endfor

    ll = l;

    aprec = pprec .* (abs (pbest) + add_pprec);
    if (skipped)
      cvg = 2;
      done = true;
    elseif (vf_chg >= fgoal) # >=, not >, since fgoal can be equal to vf
                                # if TolFun <= eps
      cvg = 3;
      done = true;
    elseif (all (abs (chg) <= aprec) && all (abs (chgprev) <= aprec))
      cvg = 2;
      done = true;
    elseif (iter == niter)
      cvg = 0;
      done = true;
    else
      chgprev = chg;
    endif

  endwhile

  ## return result
  p_res = pbest;
  objf = fbest;
  outp.niter = iter;

endfunction
