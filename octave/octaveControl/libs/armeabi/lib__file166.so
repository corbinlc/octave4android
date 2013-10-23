## Copyright (C) 2009   Lukas F. Reichlin
##
## This file is part of LTI Syncope.
##
## LTI Syncope is free software: you can redistribute it and/or modify
## it under the terms of the GNU General Public License as published by
## the Free Software Foundation, either version 3 of the License, or
## (at your option) any later version.
##
## LTI Syncope is distributed in the hope that it will be useful,
## but WITHOUT ANY WARRANTY; without even the implied warranty of
## MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
## GNU General Public License for more details.
##
## You should have received a copy of the GNU General Public License
## along with LTI Syncope.  If not, see <http://www.gnu.org/licenses/>.

## -*- texinfo -*-
## @deftypefn {Function File} {@var{sys} =} xperm (@var{sys}, @var{st_idx})
## Reorder states in state-space models.
## @end deftypefn

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: November 2009
## Version: 0.1

function sys = xperm (sys, st_idx)

  if (nargin != 2)
    print_usage ();
  endif

  if (! is_real_vector (st_idx))
    error ("xperm: second argument invalid");
  endif

  if (! isa (sys, "ss"))
    warning ("xperm: system not in state-space form");
    sys = ss (sys);
  endif

  sys = __sys_prune__ (sys, ":", ":", st_idx);

endfunction