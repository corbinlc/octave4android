## Copyright (C) 2010   Lukas F. Reichlin
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
## @deftypefn{Function File} {@var{u} =} lyapchol (@var{a}, @var{b})
## @deftypefnx{Function File} {@var{u} =} lyapchol (@var{a}, @var{b}, @var{e})
## Compute Cholesky factor of continuous-time Lyapunov equations.
##
## @strong{Equations}
## @example
## @group
## A U' U  +  U' U A'  +  B B'  =  0           (Lyapunov Equation)
##
## A U' U E'  +  E U' U A'  +  B B'  =  0      (Generalized Lyapunov Equation)
## @end group
## @end example
##
## @strong{Algorithm}@*
## Uses SLICOT SB03OD and SG03BD by courtesy of
## @uref{http://www.slicot.org, NICONET e.V.}
##
## @seealso{lyap, dlyap, dlyapchol}
## @end deftypefn

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: January 2010
## Version: 0.2.1

function [u, scale] = lyapchol (a, b, e)

  switch (nargin)
    case 2
      
      if (! is_real_square_matrix (a))
        ## error ("lyapchol: a must be real and square");
        error ("lyapchol: %s must be real and square", \
                inputname (1));
      endif

      if (! is_real_matrix (b))
        ## error ("lyapchol: b must be real")
        error ("lyapchol: %s must be real", \
                inputname (2))
      endif
  
      if (rows (a) != rows (b))
        ## error ("lyapchol: a and b must have the same number of rows");
        error ("lyapchol: %s and %s must have the same number of rows", \
                inputname (1), inputname (2));
      endif

      [u, scale] = __sl_sb03od__ (a.', b.', false);

      ## NOTE: TRANS = 'T' not suitable because we need U' U, not U U'

    case 3

      if (! is_real_square_matrix (a, e))
        ## error ("lyapchol: a, e must be real and square");
        error ("lyapchol: %s, %s must be real and square", \
                inputname (1), inputname (3));
      endif

      if (! is_real_matrix (b))
        ## error ("lyapchol: b must be real");
        error ("lyapchol: %s must be real", \
                inputname (2));
      endif

      if (rows (b) != rows (a) || rows (e) != rows (a))
        ## error ("lyapchol: a, b, e must have the same number of rows");
        error ("lyapchol: %s, %s, %s must have the same number of rows", \
                inputname (1), inputname (2), inputname (3));
      endif

      [u, scale] = __sl_sg03bd__ (a.', e.', b.', false);

      ## NOTE: TRANS = 'T' not suitable because we need U' U, not U U'

    otherwise
      print_usage ();

  endswitch

  if (scale < 1)
    warning ("lyapchol: solution scaled by %g to prevent overflow", scale);
  endif

endfunction


%!shared U, U_exp, X, X_exp
%!
%! A = [ -1.0  37.0 -12.0 -12.0
%!       -1.0 -10.0   0.0   4.0
%!        2.0  -4.0   7.0  -6.0
%!        2.0   2.0   7.0  -9.0 ].';
%!
%! B = [  1.0   2.5   1.0   3.5
%!        0.0   1.0   0.0   1.0
%!       -1.0  -2.5  -1.0  -1.5
%!        1.0   2.5   4.0  -5.5
%!       -1.0  -2.5  -4.0   3.5 ].';
%!
%! U = lyapchol (A, B);
%!
%! X = U.' * U;  # use lyap at home!
%!
%! U_exp = [  1.0000   0.0000   0.0000   0.0000
%!            3.0000   1.0000   0.0000   0.0000
%!            2.0000  -1.0000   1.0000   0.0000
%!           -1.0000   1.0000  -2.0000   1.0000 ].';
%!
%! X_exp = [  1.0000   3.0000   2.0000  -1.0000
%!            3.0000  10.0000   5.0000  -2.0000
%!            2.0000   5.0000   6.0000  -5.0000
%!           -1.0000  -2.0000  -5.0000   7.0000 ];
%!
%!assert (U, U_exp, 1e-4);
%!assert (X, X_exp, 1e-4);

%!shared U, U_exp, X, X_exp
%!
%! A = [ -1.0    3.0   -4.0
%!        0.0    5.0   -2.0
%!       -4.0    4.0    1.0 ].';
%!
%! E = [  2.0    1.0    3.0
%!        2.0    0.0    1.0
%!        4.0    5.0    1.0 ].';
%!
%! B = [  2.0   -1.0    7.0 ].';
%!
%! U = lyapchol (A, B, E);
%!
%! U_exp = [  1.6003  -0.4418  -0.1523
%!            0.0000   0.6795  -0.2499
%!            0.0000   0.0000   0.2041 ];
%!
%!assert (U, U_exp, 1e-4);
