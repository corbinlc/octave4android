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

function prt = __dfdp__ (p, func, hook)

  %% Meant to be called by interfaces 'dfxpdp.m' and 'dfpdp.m', see there.

  
  if (nargin > 2 && isfield (hook, 'f'))
    f = hook.f;
  else
    f = func (p);
    f = f(:);
  end

  m = length (f);
  n = length (p);

  if (nargin > 2)

    if (isfield (hook, 'fixed'))
      fixed = hook.fixed;
    else
      fixed = false (n, 1);
    end

    if (isfield (hook, 'diffp'))
      diffp = hook.diffp;
    else
      diffp = .001 * ones (n, 1);
    end

    if (isfield (hook, 'diff_onesided'))
      diff_onesided = hook.diff_onesided;
    else
      diff_onesided = false (n, 1);
    end

    if (isfield (hook, 'lbound'))
      lbound = hook.lbound;
    else
      lbound = - Inf (n, 1);
    end

    if (isfield (hook, 'ubound'))
      ubound = hook.ubound;
    else
      ubound = Inf (n, 1);
    end

    if (isfield (hook, 'plabels'))
      plabels = hook.plabels;
    else
      plabels = num2cell (num2cell ((1:n).'));
    end

  else
    fixed = false (n, 1);
    diff_onesided = fixed;
    diffp = .001 * ones (n, 1);
    lbound = - Inf (n, 1);
    ubound = Inf (n, 1);
    plabels = num2cell (num2cell ((1:n).'));
  end    

  prt = zeros (m, n); % initialise Jacobian to Zero
  del = diffp .* p;
  idxa = p == 0;
  del(idxa) = diffp(idxa);
  del(diff_onesided) = - del(diff_onesided); % keep course of
                                % optimization of previous versions
  absdel = abs (del);
  idxd = ~(diff_onesided | fixed); % double sided interval
  p1 = zeros (n, 1);
  p2 = p1;
  idxvs = false (n, 1);
  idx1g2w = idxvs;
  idx1le2w = idxvs;

  %% p may be slightly out of bounds due to inaccuracy, or exactly at
  %% the bound -> single sided interval
  idxvl = p <= lbound;
  idxvg = p >= ubound;
  p1(idxvl) = min (p(idxvl, 1) + absdel(idxvl, 1), ubound(idxvl, 1));
  idxd(idxvl) = false;
  p1(idxvg) = max (p(idxvg, 1) - absdel(idxvg, 1), lbound(idxvg, 1));
  idxd(idxvg) = false;
  idxs = ~(fixed | idxd); % single sided interval

  idxnv = ~(idxvl | idxvg); % current paramters within bounds
  idxnvs = idxs & idxnv; % within bounds, single sided interval
  idxnvd = idxd & idxnv; % within bounds, double sided interval
  %% remaining single sided intervals
  p1(idxnvs) = p(idxnvs) + del(idxnvs); % don't take absdel, this could
                                % change course of optimization without
                                % bounds with respect to previous
                                % versions
  %% remaining single sided intervals, violating a bound -> take largest
  %% possible direction of single sided interval
  idxvs(idxnvs) = p1(idxnvs, 1) < lbound(idxnvs, 1) | ...
      p1(idxnvs, 1) > ubound(idxnvs, 1);
  del1 = p(idxvs, 1) - lbound(idxvs, 1);
  del2 = ubound(idxvs, 1) - p(idxvs, 1);
  idx1g2 = del1 > del2;
  idx1g2w(idxvs) = idx1g2;
  idx1le2w(idxvs) = ~idx1g2;
  p1(idx1g2w) = max (p(idx1g2w, 1) - absdel(idx1g2w, 1), ...
                     lbound(idx1g2w, 1));
  p1(idx1le2w) = min (p(idx1le2w, 1) + absdel(idx1le2w, 1), ...
                      ubound(idx1le2w, 1));
  %% double sided interval
  p1(idxnvd) = min (p(idxnvd, 1) + absdel(idxnvd, 1), ...
                    ubound(idxnvd, 1));
  p2(idxnvd) = max (p(idxnvd, 1) - absdel(idxnvd, 1), ...
                    lbound(idxnvd, 1));

  del(idxs) = p1(idxs) - p(idxs);
  del(idxd) = p1(idxd) - p2(idxd);

  info.f = f;
  info.parallel = false;

  for j = 1:n
    if (~fixed(j))
      info.plabels = plabels(j, :);
      ps = p;
      ps(j) = p1(j);
      if (idxs(j))
        info.side = 0; % onesided interval
        tp1 = func (ps, info);
        prt(:, j) = (tp1(:) - f) / del(j);
      else
        info.side = 1; % centered interval, side 1
        tp1 = func (ps, info);
        ps(j) = p2(j);
        info.side = 2; % centered interval, side 2
        tp2 = func (ps, info);
        prt(:, j) = (tp1(:) - tp2(:)) / del(j);
      end
    end
  end
