%% Copyright (C) 2010, 2011 Olaf Till <i7tiol@t-online.de>
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

%% [lb, idx, ridx, mv] = cpiv_bard (v, m[, incl])
%%
%% v: column vector; m: matrix; incl (optional): index. length (v)
%% must equal rows (m). Finds column vectors w and l with w == v + m *
%% l, w >= 0, l >= 0, l.' * w == 0. Chooses idx, w, and l so that
%% l(~idx) == 0, l(idx) == -inv (m(idx, idx)) * v(idx), w(idx) roughly
%% == 0, and w(~idx) == v(~idx) + m(idx, ~idx).' * l(idx). idx indexes
%% at least everything indexed by incl, but l(incl) may be < 0. lb:
%% l(idx) (column vector); idx: logical index, defined above; ridx:
%% ~idx & w roughly == 0; mv: [m, v] after performing a Gauss-Jordan
%% 'sweep' (with gjp.m) on each diagonal element indexed by idx.
%% Except the handling of incl (which enables handling of equality
%% constraints in the calling code), this is called solving the
%% 'complementary pivot problem' (Cottle, R. W. and Dantzig, G. B.,
%% 'Complementary pivot theory of mathematical programming', Linear
%% Algebra and Appl. 1, 102--125. References for the current
%% algorithm: Bard, Y.: Nonlinear Parameter Estimation, p. 147--149,
%% Academic Press, New York and London 1974; Bard, Y., 'An eclectic
%% approach to nonlinear programming', Proc. ANU Sem. Optimization,
%% Canberra, Austral. Nat. Univ.).

function [lb, idx, ridx, m] = cpiv_bard (v, m, incl)

  n = length (v);
  if (n > size (v, 1))
    error ('first argument is no column vector'); % the most typical mistake
  end
  if (nargin < 3)
    incl = [];
  elseif (islogical (incl))
    incl = find (incl);
  end
  nincl = 1:n;
  nincl(incl) = [];
  sgn = ones (n, 1);
  if (length (incl) == n)
    sgn = - sgn;
    m = inv (m);
    m = cat (2, m, m * v);
  else
    m = cat (2, m, v);
    for id = incl(:).'
      sgn(id) = -sgn(id);
      m = gjp (m, id);
    end
  end
  nz = eps; % This is arbitrary; components of w and -l are regarded as
				% non-negative if >= -nz.
  nl = 100 * n; % maximum number of loop repeats, after that give up
  if (isempty (nincl))
    ready = true;
  else
    ready = false;
    while (~ready && nl > 0)
      [vm, idm] = min (sgn(nincl) .* m(nincl, end));
      if (vm >= -nz)
	ready = true;
      else
	idm = nincl(idm);
	sgn(idm) = -sgn(idm);
	m = gjp (m, idm);
	nl = nl - 1;
      end
    end
  end
  if (~ready)
    error ('not successful');
  end
  idx = sgn < 0;
  lb = -m(idx, end);
  ridx = ~idx & abs (m(:, end)) <= nz;
