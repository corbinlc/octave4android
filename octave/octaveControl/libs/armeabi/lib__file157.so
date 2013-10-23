## Copyright (C) 2009, 2010, 2011   Lukas F. Reichlin
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
## @deftypefn {Function File} {@var{sys} =} sminreal (@var{sys})
## @deftypefnx {Function File} {@var{sys} =} sminreal (@var{sys}, @var{tol})
## Perform state-space model reduction based on structure.
## Remove states which have no influence on the input-output behaviour.
## The physical meaning of the states is retained.
##
## @strong{Inputs}
## @table @var
## @item sys
## State-space model.
## @item tol
## Optional tolerance for controllability and observability. 
## Entries of the state-space matrices whose moduli are less or equal to @var{tol}
## are assumed to be zero.  Default value is 0.
## @end table
##
## @strong{Outputs}
## @table @var
## @item sys
## Reduced state-space model.
## @end table
##
## @seealso{minreal}
## @end deftypefn

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: October 2009
## Version: 0.4

function sys = sminreal (sys, tol = 0)

  if (nargin > 2)            # sminreal () not possible (inside @lti)
    print_usage ();
  endif

  if (! isa (sys, "ss"))
    warning ("sminreal: system not in state-space form");
    sys = ss (sys);          # needed by __sys_prune__
  endif

  if (! (is_real_scalar (tol) && tol >= 0))
    error ("sminreal: second argument is not a valid tolerance");
  endif

  [a, b, c, d, e] = dssdata (sys, []);

  a = abs (a) > tol;
  b = abs (b) > tol;
  c = abs (c) > tol;

  if (! isempty (e))
    e = abs (e) > tol;
    a = a | e;
  endif

  co_idx = __controllable_states__ (a, b);
  ob_idx = __controllable_states__ (a.', c.');

  st_idx = intersect (co_idx, ob_idx);

  sys = __sys_prune__ (sys, ":", ":", st_idx);

endfunction


function c_idx = __controllable_states__ (a, b)

  n = rows (a);              # number of states
  a = a & ! eye (n);         # set diagonal entries to zero

  c_vec = any (b, 2);        # states directly controllable
  c_idx = find (c_vec);      # indices of directly controllable states
  c_idx_new = 0;             # any vector of length > 0 possible

  while (all (length (c_idx) != [0, n]) && length(c_idx_new) != 0)

    u_idx = find (! c_vec);  # indices of uncontrollable states

    #{
    ## debug code
    a(u_idx, :)
    repmat (c_vec.', length (u_idx), 1)
    a(u_idx, :) & repmat (c_vec.', length (u_idx), 1)
    any (a(u_idx, :) & repmat (c_vec.', length (u_idx), 1), 2)
    find (any (a(u_idx, :) & repmat (c_vec.', length (u_idx), 1), 2))
    #}

    c_idx_new = u_idx (find (any (a(u_idx, :) & repmat (c_vec.', length (u_idx), 1), 2)));
    c_idx = union (c_idx, c_idx_new);
    c_vec(c_idx_new) = 1;

  endwhile

endfunction


## ss: sminreal
%!shared B, C
%!
%! A = ss (-2, 3, 4, 5);
%! B = A / A;
%! C = sminreal (B);  # no states should be removed
%!
%!assert (C.a, B.a);
%!assert (C.b, B.b);
%!assert (C.c, B.c);
%!assert (C.d, B.d);

%!shared A, B, D, E
%!
%! A = ss (-1, 1, 1, 0);
%! B = ss (-2, 3, 4, 5);
%! C = [A, B];
%! D = sminreal (C(:, 1));
%! E = sminreal (C(:, 2));
%!
%!assert (D.a, A.a);
%!assert (D.b, A.b);
%!assert (D.c, A.c);
%!assert (D.d, A.d);
%!assert (E.a, B.a);
%!assert (E.b, B.b);
%!assert (E.c, B.c);
%!assert (E.d, B.d);
