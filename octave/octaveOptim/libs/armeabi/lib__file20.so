%% Copyright (C) 1992-1994 Richard Shrager
%% Copyright (C) 1992-1994 Arthur Jutan
%% Copyright (C) 1992-1994 Ray Muzic
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

%% function prt = dfdp (x, f, p, dp, func[, bounds])
%% numerical partial derivatives (Jacobian) df/dp for use with leasqr
%% --------INPUT VARIABLES---------
%% x=vec or matrix of indep var(used as arg to func) x=[x0 x1 ....]
%% f=func(x,p) vector initialsed by user before each call to dfdp
%% p= vec of current parameter values
%% dp= fractional increment of p for numerical derivatives
%%      dp(j)>0 central differences calculated
%%      dp(j)<0 one sided differences calculated
%%      dp(j)=0 sets corresponding partials to zero; i.e. holds p(j) fixed
%% func=function (string or handle) to calculate the Jacobian for,
%%      e.g. to calc Jacobian for function expsum prt=dfdp(x,f,p,dp,'expsum')
%% bounds=two-column-matrix of lower and upper bounds for parameters
%%      If no 'bounds' options is specified to leasqr, it will call
%%      dfdp without the 'bounds' argument.
%%----------OUTPUT VARIABLES-------
%% prt= Jacobian Matrix prt(i,j)=df(i)/dp(j)
%%================================
%%
%% dfxpdp is more general and is meant to be used instead of dfdp in
%% optimization.

function prt = dfdp (x, f, p, dp, func, bounds)

  %% This is just an interface. The original code has been moved to
  %% __dfdp__.m, which is used with two different interfaces by
  %% leasqr.m.

  %% if (ischar (varargin{5}))
  %%   varargin{5} = @ (p) str2func (varargin{5}) (varargin{1}, p);
  %% else
  %%   varargin{5} = @ (p) varargin{5} (varargin{1}, p);
  %% end

  if (ischar (func))
    func = @ (p) str2func (func) (x, p);
  else
    func = @ (p) func (x, p);
  end

  hook.f = f;

  if (nargin > 5)
    hook.lbound = bounds(:, 1);
    hook.ubound = bounds(:, 2);
  end

  hook.diffp = abs (dp);
  hook.fixed = dp == 0;
  hook.diff_onesided = dp < 0;

  prt = __dfdp__ (p, func, hook);
