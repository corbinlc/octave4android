%% Copyright (C) 2010 Olaf Till <olaf.till@uni-jena.de>
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

%% [ret1, ret2] = wrap_f_dfdp (f, dfdp, varargin)
%%
%% f and dftp should be the objective function (or "model function" in
%% curve fitting) and its jacobian, respectively, of an optimization
%% problem. ret1: f (varagin{:}), ret2: dfdp (varargin{:}). ret2 is
%% only computed if more than one output argument is given. This
%% manner of calling f and dfdp is needed by some optimization
%% functions.

function [ret1, ret2] = wrap_f_dfdp (f, dfdp, varargin)

  if (nargin < 3)
    print_usage ();
  end

  if (ischar (f))
    f = str2func (f);
  end

  if (ischar (dfdp))
    dfdp = str2func (dfdp);
  end

  ret1 = f (varargin{:});

  if (nargout > 1)
    ret2 = dfdp (varargin{:});
  end

end
