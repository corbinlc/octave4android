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

%% function prt = dcdp (f, p, dp, func[, bounds])
%%
%% This is an interface to __dfdp__.m, similar to dfdp.m, but for
%% functions only of parameters 'p', not of independents 'x'. See
%% dfdp.m.
%%
%% dfpdp is more general and is meant to be used instead of dcdp in
%% optimization.

function prt = dcdp (f, p, dp, func, bounds)

  if (ischar (func))
    func = str2func (func);
  end

  hook.f = f;

  if (nargin > 4)
    hook.lbounds = bounds(:, 1);
    hook.ubounds = bounds(:, 2);
  end

  hook.diffp = abs (dp);
  hook.fixed = dp == 0;
  hook.diff_onesided = dp < 0;

  prt = __dfdp__ (p, func, hook);
