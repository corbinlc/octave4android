## Copyright (C) 1996, 2000, 2003, 2004, 2005, 2007
##               Auburn University. All rights reserved.
##
##
## This program is free software; you can redistribute it and/or modify it
## under the terms of the GNU General Public License as published by
## the Free Software Foundation; either version 3 of the License, or (at
## your option) any later version.
##
## This program is distributed in the hope that it will be useful, but
## WITHOUT ANY WARRANTY; without even the implied warranty of
## MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
## General Public License for more details.
##
## You should have received a copy of the GNU General Public License
## along with this program; see the file COPYING.  If not, see
## <http://www.gnu.org/licenses/>.

## -*- texinfo -*-
## @deftypefn {Function File} {@var{W} =} gram (@var{sys}, @var{mode})
## @deftypefnx {Function File} {@var{Wc} =} gram (@var{a}, @var{b})
## @code{gram (@var{sys}, "c")} returns the controllability gramian of
## the (continuous- or discrete-time) system @var{sys}.
## @code{gram (@var{sys}, "o")} returns the observability gramian of the
## (continuous- or discrete-time) system @var{sys}.
## @code{gram (@var{a}, @var{b})} returns the controllability gramian
## @var{Wc} of the continuous-time system @math{dx/dt = a x + b u};
## i.e., @var{Wc} satisfies @math{a Wc + m Wc' + b b' = 0}.
##
## @end deftypefn

## Author: A. S. Hodel <a.s.hodel@eng.auburn.edu>

## Adapted-By: Lukas Reichlin <lukas.reichlin@gmail.com>
## Date: October 2009
## Version: 0.2

function W = gram (argin1, argin2)

  if (nargin != 2)
    print_usage ();
  endif

  if (ischar (argin2))     # the function was called as "gram (sys, mode)"
    sys = argin1;

    if (! isa (sys, "lti"))
      error ("gram: first argument must be an LTI model");
    endif

    [a, b, c] = ssdata (sys);

    if (strncmpi (argin2, "o", 1))
      a = a.';
      b = c.';
    elseif (! strncmpi (argin2, "c", 1))
      print_usage ();
    endif
  else                     # the function was called as "gram (a, b)"
    a = argin1;
    b = argin2;

    ## assume that a and b are matrices of continuous-time system
    ## values of b, c and d are irrelevant for system stability
    sys = ss (a, b, zeros (1, columns (a)), zeros (1, columns (b)));
  endif

  if (! isstable (sys))
    error ("gram: system matrix a must be stable");
  endif

  if (isct (sys))
    W = lyap (a, b*b.');   # let lyap do the error checking about dimensions
  else  # discrete-time system
    W = dlyap (a, b*b.');  # let dlyap do the error checking about dimensions
  endif

endfunction


%!test
%! a = [-1 0 0; 1/2 -1 0; 1/2 0 -1];
%! b = [1 0; 0 -1; 0 1];
%! c = [0 0 1; 1 1 0]; ## it doesn't matter what the value of c is
%! Wc = gram (ss (a, b, c), "c");
%! assert (a * Wc + Wc * a.' + b * b.', zeros (size (a)))

%!test
%! a = [-1 0 0; 1/2 -1 0; 1/2 0 -1];
%! b = [1 0; 0 -1; 0 1]; ## it doesn't matter what the value of b is
%! c = [0 0 1; 1 1 0];
%! Wo = gram (ss (a, b, c), "o");
%! assert (a.' * Wo + Wo * a + c.' * c, zeros (size (a)))

%!test
%! a = [-1 0 0; 1/2 -1 0; 1/2 0 -1];
%! b = [1 0; 0 -1; 0 1];
%! Wc = gram (a, b);
%! assert (a * Wc + Wc * a.' + b * b.', zeros (size (a)))

%!test
%! a = [-1 0 0; 1/2 1 0; 1/2 0 -1] / 2;
%! b = [1 0; 0 -1; 0 1];
%! c = [0 0 1; 1 1 0]; ## it doesn't matter what the value of c is
%! d = zeros (rows (c), columns (b)); ## it doesn't matter what the value of d is
%! Ts = 0.1; ## Ts != 0
%! Wc = gram (ss (a, b, c, d, Ts), "c");
%! assert (a * Wc * a.' - Wc + b * b.', zeros (size (a)), 1e-12)

%!test
%! a = [-1 0 0; 1/2 1 0; 1/2 0 -1] / 2;
%! b = [1 0; 0 -1; 0 1]; ## it doesn't matter what the value of b is
%! c = [0 0 1; 1 1 0];
%! d = zeros (rows (c), columns (b)); ## it doesn't matter what the value of d is
%! Ts = 0.1; ## Ts != 0
%! Wo = gram (ss (a, b, c, d, Ts), "o");
%! assert (a.' * Wo * a - Wo + c.' * c, zeros (size (a)), 1e-12)