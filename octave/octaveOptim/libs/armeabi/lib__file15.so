## Copyright (C) 2011 Olaf Till <olaf.till@uni-jena.de>
##
## This program is free software; you can redistribute it and/or modify it under
## the terms of the GNU General Public License as published by the Free Software
## Foundation; either version 3 of the License, or (at your option) any later
## version.
##
## This program is distributed in the hope that it will be useful, but WITHOUT
## ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
## FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
## details.
##
## You should have received a copy of the GNU General Public License along with
## this program; if not, see <http://www.gnu.org/licenses/>.

## -*- texinfo -*-
## @deftypefn {Function File} {@var{info} =} residmin_stat (@var{f}, @var{p}, @var{x}, @var{y}, @var{settings})
##
## Frontend for computation of statistics for fitting of values,
## computed by a model function, to observed values.
##
## Please refer to the description of @code{residmin_stat}. The only
## differences to @code{residmin_stat} are the additional arguments
## @var{x} (independent values) and @var{y} (observations), that the
## model function @var{f}, if provided, has a second obligatory argument
## which will be set to @var{x} and is supposed to return guesses for
## the observations (with the same dimensions), and that the possibly
## user-supplied function for the jacobian of the model function has
## also a second obligatory argument which will be set to @var{x}.
##
## @seealso {residmin_stat}
## @end deftypefn

function ret = curvefit_stat (f, pfin, x, y, settings)

  if (nargin == 1)
    ret = __residmin_stat__ (f);
    return;
  endif

  if (nargin != 5)
    print_usage ()
  endif

  if (compare_versions (version (), "3.3.55", "<"))
    ## optimset mechanism was fixed for option names with underscores
    ## sometime in 3.3.54+, if I remember right
    optimget = @ __optimget__;
  endif
  if (! isempty (dfdp = optimget (settings, "dfdp")) && \
      ! (ismatrix (dfdp) && ! ischar (dfdp)))
    if (ischar (dfdp))
      dfdp = str2func (dfdp);
    endif
    settings.dfdp = @ (p, varargin) dfdp (p, x, varargin{:});
  endif
  if (! isempty (f))
    f = @ (p) f (p, x);
  endif

  ret = __residmin_stat__ \
      (f, pfin, settings, struct ("observations", y));

endfunction

function ret = __optimget__ (s, name, default)

  if (isfield (s, name))
    ret = s.(name);
  elseif (nargin > 2)
    ret = default;
  else
    ret = [];
  endif

endfunction
