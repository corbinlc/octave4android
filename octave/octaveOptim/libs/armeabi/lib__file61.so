%% Copyright (C) 1992-1994 Richard Shrager
%% Copyright (C) 1992-1994 Arthur Jutan
%% Copyright (C) 1992-1994 Ray Muzic
%% Copyright (C) 2010, 2011 Olaf Till <olaf.till@uni-jena.de>
%%
%% This program is free software; you can redistribute it and/or modify it under
%% the terms of the GNU General Public License as published by the Free Software
%% Foundation; either version 3 of the License, or (at your option) any later
%% version.
%%
%% This program is distributed in the hope that it will be useful, but WITHOUT
%% ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
%% FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
%% details.
%%
%% You should have received a copy of the GNU General Public License along with
%% this program; if not, see <http://www.gnu.org/licenses/>.

function [p, resid, cvg, outp] = __lm_svd__ (F, pin, hook)

  %% This is a backend for optimization. This code was originally
  %% contained in leasqr.m, which is now a frontend.

  %% some backend specific defaults
  fract_prec_default = 0;
  max_fract_step_default = Inf;

  %% needed for some anonymous functions
  if (exist ('ifelse') ~= 5)
    ifelse = @ scalar_ifelse;
  end

  n = length (pin);

  %% passed constraints
  mc = hook.mc; % matrix of linear constraints
  vc = hook.vc; % vector of linear constraints
  f_cstr = hook.f_cstr; % function of all constraints
  df_cstr = hook.df_cstr; % function of derivatives of all constraints
  n_gencstr = hook.n_gencstr; % number of non-linear constraints
  eq_idx = hook.eq_idx; % logical index of equality constraints in all
                                % constraints
  lbound = hook.lbound; % bounds, subset of linear inequality
  ubound = hook.ubound; % constraints in mc and vc

  %% passed values of constraints for initial parameters
  pin_cstr = hook.pin_cstr;

  %% passed return value of F for initial parameters
  f_pin = hook.f_pin;

  %% passed derivative of residual function
  dfdp = hook.dfdp;

  %% passed function for complementary pivoting
  cpiv = hook.cpiv;

  %% passed options
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
  stol = hook.TolFun;
  niter = hook.MaxIter;
  if (isempty (niter)) niter = 20; end
  wt = hook.weights;
  fixed = hook.fixed;
  verbose = strcmp (hook.Display, 'iter');

  %% only preliminary, for testing
  if (isfield (hook, 'testing'))
    testing = hook.testing;
  else
    testing = false;
  end
  if (isfield (hook, 'new_s'))
    new_s = hook.new_s;
  else
    new_s = false;
  end

  %% some useful variables derived from passed variables
  n_lcstr = size (vc, 1);
  have_constraints_except_bounds = ...
      n_lcstr + n_gencstr > ...
      sum (lbound ~= -Inf) + sum (ubound ~= Inf);
  wtl = wt(:);

  nz = 20 * eps; % This is arbitrary. Constraint function will be
                                % regarded as <= zero if less than nz.

  %% backend-specific checking of options and constraints
  if (have_constraints_except_bounds)
    if (any (pin_cstr.inequ.lin_except_bounds < 0) || ...
        (n_gencstr > 0 && any (pin_cstr.inequ.gen < 0)))
      warning ('initial parameters violate inequality constraints');
    end
    if (any (abs (pin_cstr.equ.lin) >= nz) || ...
        (n_gencstr > 0 && any (abs (pin_cstr.equ.gen) >= nz)))
      warning ('initial parameters violate equality constraints');
    end
  end
  idx = lbound == ubound;
  if (any (idx))
    warning ('lower and upper bounds identical for some parameters, fixing the respective parameters');
    fixed(idx) = true;
  end
  if (all (fixed))
    error ('no free parameters');
  end
  lidx = pin < lbound;
  uidx = pin > ubound;
  if (any (lidx | uidx) && have_constraints_except_bounds)
    warning ('initial parameters outside bounds, not corrected since other constraints are given');
  else
    if (any (lidx))
      warning ('some initial parameters set to lower bound');
      pin(lidx, 1) = lbound(lidx, 1);
    end
    if (any (uidx))
      warning ('some initial parameters set to upper bound');
      pin(uidx, 1) = ubound(uidx, 1);
    end
  end
  if (n_gencstr > 0 && any (~isinf (maxstep)))
    warning ('setting both a maximum fractional step change of parameters and general constraints may result in inefficiency and failure');
  end

  %% fill constant fields of hook for derivative-functions; some fields
  %% may be backend-specific
  dfdp_hook.fixed = fixed; % this may be handled by the frontend, but
                                % the backend still may add to it

  %% set up for iterations
  %%
  p = pin;
  f = f_pin; fbest=f; pbest=p;
  m = prod (size (f));
  r = wt .* f;
  r = r(:);
  if (~isreal (r)) error ('weighted residuals are not real'); end
  ss = r.' * r;
  sbest=ss;
  chgprev=Inf*ones(n,1);
  cvg=0;
  epsLlast=1;
  epstab=[.1, 1, 1e2, 1e4, 1e6];
  ac_idx = true (n_lcstr + n_gencstr, 1); % all constraints
  nc_idx = false (n_lcstr + n_gencstr, 1); % none of all constraints
  gc_idx = cat (1, false (n_lcstr, 1), true (n_gencstr, 1)); % gen. constr.
  lc_idx = ~gc_idx;

  %% do iterations
  %%
  for iter = 1:niter
    deb_printf (testing, '\nstart outer iteration\n');
    v_cstr = f_cstr (p, ac_idx);
    %% index of active constraints
    c_act =  v_cstr < nz | eq_idx; # equality constraints might be
                                # violated at start
    if (any (c_act))
      if (n_gencstr > 0)
        %% full gradient is needed later
        dct = df_cstr (p, ac_idx, ...
                       setfield (dfdp_hook, 'f', v_cstr));
        dct(:, fixed) = 0; % for user supplied dfdp; necessary?
        dcat = dct(c_act, :);
      else
        dcat = df_cstr (p, c_act, ...
                        setfield (dfdp_hook, 'f', v_cstr));
        dcat(:, fixed) = 0; % for user supplied dfdp; necessary?
      end
      dca = dcat.';
    end
    nrm = zeros (1, n);
    pprev=pbest;
    prt = dfdp (p, setfield (dfdp_hook, 'f', fbest(:)));
    prt(:, fixed) = 0; % for user supplied dfdp; necessary?
    r = wt .* -fbest;
    r = r(:);
    if (~isreal (r)) error ('weighted residuals are not real'); end
    sprev=sbest;
    sgoal=(1-stol)*sprev;
    msk = ~fixed;
    prt(:, msk) = prt(:, msk) .* wtl(:, ones (1, sum (msk)));
    nrm(msk) = sumsq (prt(:, msk), 1);
    msk = nrm > 0;
    nrm(msk) = 1 ./ sqrt (nrm(msk));
    prt = prt .* nrm(ones (1, m), :);
    nrm = nrm.';
    [prt,s,v]=svd(prt,0);
    s=diag(s);
    g = prt.' * r;
    for jjj=1:length(epstab)
      deb_printf (testing, '\nstart inner iteration\n');
      epsL = max(epsLlast*epstab(jjj),1e-7);
      %% printf ('epsL: %e\n', epsL); % for testing

      %% Usage of this 'ser' later is equivalent to pre-multiplying the
      %% gradient with a positive-definit matrix, but not with a
      %% diagonal matrix, at epsL -> Inf; so there is a fallback to
      %% gradient descent, but not in general to descent for each
      %% gradient component. Using the commented-out 'ser' ((1 / (1 +
      %% epsL^2)) * (1 ./ se + epsL * s)) would be equivalent to using
      %% Marquardts diagonal of the Hessian-approximation for epsL ->
      %% Inf, but currently this gives no advantages in tests, even with
      %% constraints.
%%% ser = 1 ./ sqrt((s.*s)+epsL);
      se = sqrt ((s.*s) + epsL);
      if (new_s)
        %% for testing
        ser = (1 / (1 + epsL^2)) * (1 ./ se + epsL * s);
      else
        ser = 1 ./ se;
      end
      tp1 = (v * (g .* ser)) .* nrm;
      if (any (c_act))
        deb_printf (testing, 'constraints are active:\n');
        deb_printf (testing, '%i\n', c_act);
        %% calculate chg by 'quadratic programming'
        nrme= diag (nrm);
        ser2 = diag (ser .* ser);
        mfc1 = nrme * v * ser2 * v.' * nrme;
        tp2 = mfc1 * dca;
        a_eq_idx = eq_idx(c_act);
        [lb, bidx, ridx, tbl] = cpiv (dcat * tp1, dcat * tp2, a_eq_idx);
        chg = tp1 + tp2(:, bidx) * lb; % if a parameter is 'fixed',
                                % the respective component of chg should
                                % be zero too, even here (with active
                                % constraints)
        deb_printf (testing, 'change:\n');
        deb_printf (testing, '%e\n', chg);
        deb_printf (testing, '\n');
        %% indices for different types of constraints
        c_inact = ~c_act; % inactive constraints
        c_binding = nc_idx; 
        c_binding(c_act) = bidx; % constraints selected binding
        c_unbinding = nc_idx;
        c_unbinding(c_act) = ridx; % constraints unselected binding
        c_nonbinding = c_act & ~(c_binding | c_unbinding); % constraints
                                % selected non-binding
      else
        %% chg is the Levenberg/Marquardt step
        chg = tp1;
        %% indices for different types of constraints
        c_inact = ac_idx; % inactive constraints consist of all
                                % constraints
        c_binding = nc_idx;
        c_unbinding = nc_idx;
        c_nonbinding = nc_idx;
      end
      %% apply constraints to step width (since this is a
      %% Levenberg/Marquardt algorithm, no line-search is performed
      %% here)
      k = 1;
      c_tp = c_inact(1:n_lcstr);
      mcit = mc(:, c_tp).';
      vci = vc(c_tp);
      hstep = mcit * chg;
      idx = hstep < 0;
      if (any (idx))
        k = min (1, min (- (vci(idx) + mcit(idx, :) * pprev) ./ ...
                         hstep(idx)));
      end
      if (k < 1)
        deb_printf (testing, 'stepwidth: linear constraints\n');
      end
      if (n_gencstr > 0)
        c_tp = gc_idx & (c_nonbinding | c_inact);
        if (any (c_tp) && any (f_cstr (pprev + k * chg, c_tp) < 0))
          [k, fval, info] = ...
              fzero (@ (x) min (cat (1, ...
                                     f_cstr (pprev + x * chg, c_tp), ...
                                     k - x, ...
                                     ifelse (x < 0, -Inf, Inf))), ...
                     0);
          if (info ~= 1 || abs (fval) >= nz)
            error ('could not find stepwidth to satisfy inactive and non-binding general inequality constraints');
          end
          deb_printf (testing, 'general constraints limit stepwidth\n');
        end
      end
      chg = k * chg;

      if (any (gc_idx & c_binding)) % none selected binding =>
                                % none unselected binding
        deb_printf (testing, 'general binding constraints must be regained:\n');
        %% regain binding constraints and one of the possibly active
        %% previously inactive or non-binding constraints
        ptp1 = pprev + chg;

        tp = true;
        nt_nosuc = true;
        lim = 20;
        while (nt_nosuc && lim >= 0)
          deb_printf (testing, 'starting from new value of p in regaining:\n');
          deb_printf (testing, '%e\n', ptp1);
          %% we keep d_p.' * inv (mfc1) * d_p minimal in each step of
          %% the inner loop; this is both sensible (this metric
          %% considers a guess of curvature of sum of squared residuals)
          %% and convenient (we have useful matrices available for it)
          c_tp0 = c_inact | c_nonbinding;
          c_tp1 = c_inact | (gc_idx & c_nonbinding);
          btbl = tbl(bidx, bidx);
          c_tp2 = c_binding;
          if (any (tp)) % if none before, does not get true again
            tp = f_cstr (ptp1, c_tp1) < nz;
            if (any (tp)) % could be less clumsy, but ml-compatibility..
              %% keep only the first true entry in tp
              tp(tp) = logical (cat (1, 1, zeros (sum (tp) - 1, 1)));
              %% supplement binding index with one (the first) getting
              %% binding in c_tp1
              c_tp2(c_tp1) = tp;
              %% gradient of this added constraint
              caddt = dct(c_tp2 & ~c_binding, :);
              cadd = caddt.';
              C = dct(c_binding, :) * mfc1 * cadd;
              Ct = C.';
              G = [btbl, btbl * C; ...
                   -Ct * btbl, caddt * mfc1 * cadd - Ct * btbl * C];
              btbl = gjp (G, size (G, 1));
            end
          end
          dcbt = dct(c_tp2, :);
          mfc = - mfc1 * dcbt.' * btbl;
          deb_printf (testing, 'constraints to regain:\n');
          deb_printf (testing, '%i\n', c_tp2);

          ptp2 = ptp1;
          nt_niter_start = 100;
          nt_niter = nt_niter_start;
          while (nt_nosuc && nt_niter >= 0)
            hv = f_cstr (ptp2, c_tp2);
            if (all (abs (hv) < nz))
              nt_nosuc = false;
              chg = ptp2 - pprev;
            else
              ptp2 = ptp2 + mfc * hv; % step should be zero for each
                                % component for which the parameter is
                                % 'fixed'
            end
            nt_niter = nt_niter - 1;
          end
          deb_printf (testing, 'constraints after regaining:\n');
          deb_printf (testing, '%e\n', hv);
          if (nt_nosuc || ...
              any (abs (chg) > abs (pprev .* maxstep)) || ...
              any (f_cstr (ptp2, c_tp0) < -nz))
            if (nt_nosuc)
              deb_printf (testing, 'regaining did not converge\n');
            else
              deb_printf (testing, 'regaining violated type 3 and 4\n');
            end
            nt_nosuc = true;
            ptp1 = (pprev + ptp1) / 2;
          end
          if (~nt_nosuc)
            tp = f_cstr (ptp2, c_unbinding);
            if (any (tp) < 0) % again ml-compatibility clumsyness..
              [discarded, id] = min(tp);
              tid = find (ridx);
              id = tid(id); % index within active constraints
              unsuccessful_exchange = false;
              if (abs (tbl(id, id)) < nz) % Bard: not absolute value
                %% exchange this unselected binding constraint against a
                %% binding constraint, but not against an equality
                %% constraint
                tbidx = bidx & ~a_eq_idx;
                if (~any (tbidx))
                  unsuccessful_exchange = true;
                else
                  [discarded, idm] = max (abs (tbl(tbidx, id)));
                  tid = find (tbidx);
                  idm = tid(idm); % -> index within active constraints
                  tbl = gjp (tbl, idm);
                  bidx(idm) = false;
                  ridx(idm) = true;
                end
              end
              if (unsuccessful_exchange)
                %% It probably doesn't look good now; this desperate
                %% last attempt is not in the original algortithm, since
                %% that didn't account for equality constraints.
                ptp1 = (pprev + ptp1) / 2;
              else
                tbl = gjp (tbl, id);
                bidx(id) = true;
                ridx(id) = false;
                c_binding = nc_idx;
                c_binding(c_act) = bidx;
                c_unbinding = nc_idx;
                c_unbinding(c_act) = ridx;
              end
              nt_nosuc = true;
              deb_printf (testing, 'regaining violated type 2\n');
            end
          end
          if (~nt_nosuc)
            deb_printf (testing, 'regaining successful, converged with %i iterations:\n', ...
            nt_niter_start - nt_niter);
            deb_printf (testing, '%e\n', ptp2);
          end
          lim = lim - 1;
        end
        if (nt_nosuc)
          error ('could not regain binding constraints');
        end
      else
        %% check the maximal stepwidth and apply as necessary
        ochg=chg;
        idx = ~isinf(maxstep);
        limit = abs(maxstep(idx).*pprev(idx));
        chg(idx) = min(max(chg(idx),-limit),limit);
        if (verbose && any(ochg ~= chg))
          disp(['Change in parameter(s): ', ...
                sprintf('%d ',find(ochg ~= chg)), 'maximal fractional stepwidth enforced']);
        end
      end
      aprec = pprec .* (abs (pbest) + add_pprec);
      %% ss=scalar sum of squares=sum((wt.*f)^2).
      if (any(abs(chg) > 0.1*aprec))%---  % only worth evaluating
                                % function if there is some non-miniscule
                                % change
        %% In the code of the outer loop before the inner loop pbest is
        %% actually identical to p, since once they deviate, the outer
        %% loop will not be repeated. Though the inner loop can still be
        %% repeated in this case, pbest is not used in it. Since pprev
        %% is set from pbest in the outer loop before the inner loop, it
        %% is also identical to p up to here.
        p=chg+pprev;
        %% since the projection method may have slightly violated
        %% constraints due to inaccuracy, correct parameters to bounds
        %% --- but only if no further constraints are given, otherwise
        %% the inaccuracy in honoring them might increase by this
        skipped = false;
        if (~have_constraints_except_bounds)
          lidx = p < lbound;
          uidx = p > ubound;
          p(lidx, 1) = lbound(lidx, 1);
          p(uidx, 1) = ubound(uidx, 1);
          chg(lidx, 1) = p(lidx, 1) - pprev(lidx, 1);
          chg(uidx, 1) = p(uidx, 1) - pprev(uidx, 1);
        end
        %%
        f = F (p);
        r = wt .* f;
        r = r(:);
        if (~isreal (r))
          error ('weighted residuals are not real');
        end
        ss = r.' * r;
        deb_printf (testing, 'sbest: %.16e\n', sbest);
        deb_printf (testing, 'sgoal: %.16e\n', sgoal);
        deb_printf (testing, '   ss: %.16e\n', ss);
        if (ss<sbest)
          pbest=p;
          fbest=f;
          sbest=ss;
        end
        if (ss < sgoal) # <, not <=, since sgoal can be equal to sprev
                                # if TolFun <= eps
          break;
        end
      else
        skipped = true;
        break;
      end                          %---
    end
    %% printf ('epsL no.: %i\n', jjj); % for testing
    epsLlast = epsL;
    if (verbose)
      hook.plot_cmd (f);
    end
    if (skipped)
      cvg = 2;
      break;
    end
    if (ss < eps) % in this case ss == sbest
      cvg = 3; % there is no more suitable flag for this
      break;
    end
    if (ss >= sgoal) # >=, not >, since sgoal can be equal to sprev if
                                # TolFun <= eps
      cvg = 3;
      break;
    end
    aprec = pprec .* (abs (pbest) + add_pprec);
    %% [aprec, chg, chgprev]
    if (all(abs(chg) <= aprec) && all(abs(chgprev) <= aprec))
      cvg = 2;
      if (verbose)
        fprintf('Parameter changes converged to specified precision\n');
      end
      break;
    else
      chgprev=chg;
    end
  end

  %% set further return values
  %%
  p = pbest;
  resid = fbest;
  outp.niter = iter;

function deb_printf (do_printf, varargin)

  %% for testing

  if (do_printf)
    printf (varargin{:})
  end

function fval = scalar_ifelse (cond, tval, fval)

  %% needed for some anonymous functions, builtin ifelse only available
  %% in Octave > 3.2; we need only the scalar case here

  if (cond)
    fval = tval;
  end
