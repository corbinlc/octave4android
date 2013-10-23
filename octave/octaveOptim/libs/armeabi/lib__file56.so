## Copyright (C) 2010, 2011 Olaf Till <olaf.till@uni-jena.de>
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

function [mc, vc, f_gencstr, df_gencstr, user_df] = \
      __collect_constraints__ (cstr, do_cstep, context)

  mc = vc = f_gencstr = df_gencstr = [];
  user_df = false;

  if (isempty (cstr)) return; endif

  for id = 1 : length (cstr)
    if (ischar (cstr{id}))
      cstr{id} = str2func (cstr{id});
    endif
  endfor

  if (ismatrix (tp = cstr{1}) || isstruct (tp))
    mc = tp;
    vc = cstr{2};
    if ((tp = length (cstr)) > 2)
      f_genstr = cstr{3};
      if (tp > 3)
	df_gencstr = cstr{4};
	user_df = true;
      endif
    endif
  else
    lid = 0; # no linear constraints
    f_gencstr = cstr{1};
    if ((len = length (cstr)) > 1)
      if (ismatrix (c = cstr{2}) || isstruct (c))
	lid = 2;
      else
	df_gencstr = c;
	user_df = true;
	if (len > 2)
	  lid = 3;
	endif
      endif
    endif
    if (lid)
      mc = cstr{lid};
      vc = cstr{lid + 1};
    endif
  endif

  if (! isempty (f_gencstr))
    if (ischar (f_gencstr))
      f_gencstr = str2func (f_gencstr);
    endif
    f_gencstr = @ (varargin) \
	tf_gencstr (f_gencstr, varargin{:});

    if (user_df)
      if (do_cstep)
	error ("both complex step derivative chosen and user Jacobian function specified for %s", context);
      endif
      if (ischar (df_gencstr))
	df_gencstr = str2func (df_gencstr);
      endif
      df_gencstr = @ (p, func, idx, hook) \
	  df_gencstr (p, idx, hook);
    else
      if (do_cstep)
	df_gencstr = @ (p, func, idx, hook) jacobs (p, func, hook);
      else
	__dfdp__ = @ __dfdp__; # for bug #31484 (Octave <= 3.2.4)
	df_gencstr = @ (p, func, idx, hook) __dfdp__ (p, func, hook);
      endif
    endif
  endif

endfunction

function ret = tf_gencstr (f, varargin) # varargin: p[, idx[, info]]

  ## necessary since user function f_gencstr might return [] or a row
  ## vector

  if (isempty (ret = f (varargin{:})))
    ret = zeros (0, 1);
  elseif (columns (ret) > 1)
    ret = ret(:);
  endif

endfunction
