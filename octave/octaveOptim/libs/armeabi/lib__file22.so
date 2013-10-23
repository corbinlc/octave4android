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

%% function jac = dfxpdp (x, p, func[, hook])
%%
%% Returns Jacobian of func (p, x) with respect to p with finite
%% differencing. The optional argument hook is a structure which can
%% contain the following fields at the moment:
%%
%% hook.f: value of func(p, x) for p and x as given in the arguments
%%
%% hook.diffp: positive vector of fractional steps from given p in
%% finite differencing (actual steps may be smaller if bounds are
%% given). The default is .001 * ones (size (p));
%%
%% hook.diff_onesided: logical vector, indexing elements of p for
%% which only one-sided differences should be computed (faster); even
%% if not one-sided, differences might not be exactly central if
%% bounds are given. The default is false (size (p)).
%%
%% hook.fixed: logical vector, indexing elements of p for which zero
%% should be returned instead of the guessed partial derivatives
%% (useful in optimization if some parameters are not optimized, but
%% are 'fixed').
%%
%% hook.lbound, hook.ubound: vectors of lower and upper parameter
%% bounds (or -Inf or +Inf, respectively) to be respected in finite
%% differencing. The consistency of bounds is not checked.

function ret = dfxpdp (varargin)

  %% This is an interface to __dfdp__.m.

  if (ischar (varargin{3}))
    varargin{3} = @ (p) str2func (varargin{3}) ...
	(p, varargin{1});
  else
    varargin{3} = @ (p) varargin{3} (p, varargin{1});
  end

  ret = __dfdp__ (varargin{2:end});
