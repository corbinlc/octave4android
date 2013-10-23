## Copyright (C) 2009, 2010   Lukas F. Reichlin
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
## @deftypefn {Function File} {[@var{x}, @var{l}, @var{g}] =} dare (@var{a}, @var{b}, @var{q}, @var{r})
## @deftypefnx {Function File} {[@var{x}, @var{l}, @var{g}] =} dare (@var{a}, @var{b}, @var{q}, @var{r}, @var{s})
## @deftypefnx {Function File} {[@var{x}, @var{l}, @var{g}] =} dare (@var{a}, @var{b}, @var{q}, @var{r}, @var{[]}, @var{e})
## @deftypefnx {Function File} {[@var{x}, @var{l}, @var{g}] =} dare (@var{a}, @var{b}, @var{q}, @var{r}, @var{s}, @var{e})
## Solve discrete-time algebraic Riccati equation (ARE).
##
## @strong{Inputs}
## @table @var
## @item a
## Real matrix (n-by-n).
## @item b
## Real matrix (n-by-m).
## @item q
## Real matrix (n-by-n).
## @item r
## Real matrix (m-by-m).
## @item s
## Optional real matrix (n-by-m).  If @var{s} is not specified, a zero matrix is assumed.
## @item e
## Optional descriptor matrix (n-by-n).  If @var{e} is not specified, an identity matrix is assumed.
## @end table
##
## @strong{Outputs}
## @table @var
## @item x
## Unique stabilizing solution of the discrete-time Riccati equation (n-by-n).
## @item l
## Closed-loop poles (n-by-1).
## @item g
## Corresponding gain matrix (m-by-n).
## @end table
##
## @strong{Equations}
## @example
## @group
##                           -1
## A'XA - X - A'XB (B'XB + R)   B'XA + Q = 0
##
##                                 -1
## A'XA - X - (A'XB + S) (B'XB + R)   (B'XA + S') + Q = 0
##
##               -1
## G = (B'XB + R)   B'XA
##
##               -1
## G = (B'XB + R)   (B'XA + S')
##
## L = eig (A - B*G)
## @end group
## @end example
## @example
## @group
##                              -1
## A'XA - E'XE - A'XB (B'XB + R)   B'XA + Q = 0
##
##                                    -1
## A'XA - E'XE - (A'XB + S) (B'XB + R)   (B'XA + S') + Q = 0
##
##               -1
## G = (B'XB + R)   B'XA
##
##               -1
## G = (B'XB + R)   (B'XA + S')
##
## L = eig (A - B*G, E)
## @end group
## @end example
##
## @strong{Algorithm}@*
## Uses SLICOT SB02OD and SG02AD by courtesy of
## @uref{http://www.slicot.org, NICONET e.V.}
##
## @seealso{care, lqr, dlqr, kalman}
## @end deftypefn

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: October 2009
## Version: 0.5.1

function [x, l, g] = dare (a, b, q, r, s = [], e = [])

  ## TODO: extract feedback matrix g from SB02OD (and SG02AD)

  if (nargin < 4 || nargin > 6)
    print_usage ();
  endif

  if (! is_real_square_matrix (a, q, r))
    ## error ("dare: a, q, r must be real and square");
    error ("dare: %s, %s, %s must be real and square", \
            inputname (1), inputname (3), inputname (4));
  endif
  
  if (! is_real_matrix (b) || rows (a) != rows (b))
    ## error ("dare: a and b must have the same number of rows");
    error ("dare: %s and %s must have the same number of rows", \
            inputname (1), inputname (2));
  endif
  
  if (columns (r) != columns (b))
    ## error ("dare: b and r must have the same number of columns");
    error ("dare: %s and %s must have the same number of columns", \
            inputname (2), inputname (4));
  endif

  if (! is_real_matrix (s) && ! size_equal (s, b))
    ## error ("dare: s(%dx%d) must be real and identically dimensioned with b(%dx%d)",
    ##         rows (s), columns (s), rows (b), columns (b));
    error ("dare: %s(%dx%d) must be real and identically dimensioned with %s(%dx%d)", \
            inputname (5), rows (s), columns (s), inputname (2), rows (b), columns (b));
  endif

  if (! isempty (e) && (! is_real_square_matrix (e) || ! size_equal (e, a)))
    ## error ("dare: a and e must have the same number of rows");
    error ("dare: %s and %s must have the same number of rows", \
            inputname (1), inputname (6));
  endif

  ## check stabilizability
  if (! isstabilizable (a, b, e, [], 1))
    ## error ("dare: (a, b) not stabilizable");
    error ("dare: (%s, %s) not stabilizable", \
            inputname (1), inputname (2));
  endif

  ## check positive semi-definiteness
  if (isempty (s))
    t = zeros (size (b));
  else
    t = s;
  endif

  m = [q, t; t.', r];

  if (isdefinite (m) < 0)
    ## error ("dare: require [q, s; s.', r] >= 0");
    error ("dare: require [%s, %s; %s.', %s] >= 0", \
            inputname (3), inputname (5), inputname (5), inputname (4));
  endif

  ## solve the riccati equation
  if (isempty (e))
    if (isempty (s))
      [x, l] = __sl_sb02od__ (a, b, q, r, b, true, false);
      g = (r + b.'*x*b) \ (b.'*x*a);        # gain matrix
    else
      [x, l] = __sl_sb02od__ (a, b, q, r, s, true, true);
      g = (r + b.'*x*b) \ (b.'*x*a + s.');  # gain matrix
    endif
  else
    if (isempty (s))
      [x, l] = __sl_sg02ad__ (a, e, b, q, r, b, true, false);
      g = (r + b.'*x*b) \ (b.'*x*a);        # gain matrix
    else
      [x, l] = __sl_sg02ad__ (a, e, b, q, r, s, true, true);
      g = (r + b.'*x*b) \ (b.'*x*a + s.');  # gain matrix
    endif
  endif

endfunction


%!shared x, l, g, xe, le, ge
%! a = [ 0.4   1.7
%!       0.9   3.8];
%!
%! b = [ 0.8
%!       2.1];
%!
%! c = [ 1  -1];
%!
%! r = 3;
%!
%! [x, l, g] = dare (a, b, c.'*c, r);
%!
%! xe = [ 1.5354    1.2623
%!        1.2623   10.5596];
%!
%! le = [-0.0022
%!        0.2454];
%!
%! ge = [ 0.4092    1.7283];
%!
%!assert (x, xe, 1e-4);
%!assert (sort (l), sort (le), 1e-4);
%!assert (g, ge, 1e-4);

## TODO: add more tests (nonempty s and/or e)
