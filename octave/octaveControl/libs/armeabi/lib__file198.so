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
## @deftypefn {Function File} {@var{f} =} place (@var{sys}, @var{p})
## @deftypefnx {Function File} {@var{f} =} place (@var{a}, @var{b}, @var{p})
## @deftypefnx {Function File} {[@var{f}, @var{info}] =} place (@var{sys}, @var{p}, @var{alpha})
## @deftypefnx {Function File} {[@var{f}, @var{info}] =} place (@var{a}, @var{b}, @var{p}, @var{alpha})
## Pole assignment for a given matrix pair (@var{A},@var{B}) such that @code{p = eig (A-B*F)}.
## If parameter @var{alpha} is specified, poles with real parts (continuous-time)
## or moduli (discrete-time) below @var{alpha} are left untouched.
##
## @strong{Inputs}
## @table @var
## @item sys
## Continuous- or discrete-time LTI system.
## @item a
## State transition matrix (n-by-n) of a continuous-time system.
## @item b
## Input matrix (n-by-m) of a continuous-time system.
## @item p
## Desired eigenvalues of the closed-loop system state-matrix @var{A-B*F}.
## @code{length (p) <= rows (A)}.
## @item alpha
## Specifies the maximum admissible value, either for real
## parts or for moduli, of the eigenvalues of @var{A} which will
## not be modified by the eigenvalue assignment algorithm.
## @code{alpha >= 0} for discrete-time systems.
## @end table
##
## @strong{Outputs}
## @table @var
## @item f
## State feedback gain matrix.
## @item info
## Structure containing additional information.
## @item info.nfp
## The number of fixed poles, i.e. eigenvalues of @var{A} having
## real parts less than @var{alpha}, or moduli less than @var{alpha}.
## These eigenvalues are not modified by @command{place}.
## @item info.nap
## The number of assigned eigenvalues.  @code{nap = n-nfp-nup}.
## @item info.nup
## The number of uncontrollable eigenvalues detected by the
## eigenvalue assignment algorithm.
## @item info.z
## The orthogonal matrix @var{z} reduces the closed-loop
## system state matrix @code{A + B*F} to upper real Schur form.
## Note the positive sign in @code{A + B*F}.
## @end table
##
## @strong{Note}
## @example
## Place is also suitable to design estimator gains:
## @group
## L = place (A.', C.', p).'
## L = place (sys.', p).'   # useful for discrete-time systems
## @end group
## @end example
##
## @strong{Algorithm}@*
## Uses SLICOT SB01BD by courtesy of
## @uref{http://www.slicot.org, NICONET e.V.}
## @end deftypefn

## Special thanks to Peter Benner from TU Chemnitz for his advice.
## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: December 2009
## Version: 0.5

function [f, info] = place (a, b, p = [], alpha = [], tol = [])

  if (nargin < 2 || nargin > 5)
    print_usage ();
  endif

  if (isa (a, "lti"))              # place (sys, p), place (sys, p, alpha), place (sys, p, alpha, tol)
    if (nargin > 4)                # nargin < 2 already tested
      print_usage ();
    endif
    tol = alpha;
    alpha = p;
    p = b;
    sys = a;
    [a, b] = ssdata (sys);         # descriptor matrice e should be regular
    discrete = ! isct (sys);       # treat tsam = -2 as continuous system
  else                             # place (a, b, p), place (a, b, p, alpha), place (a, b, p, alpha, tol)
    if (nargin < 3)                # nargin > 5 already tested
      print_usage ();
    endif
    if (! is_real_square_matrix (a) || ! is_real_matrix (b) || rows (a) != rows (b))
      error ("place: matrices a and b not conformal");
    endif
    discrete = 0;                  # assume continuous system
  endif

  if (! isnumeric (p) || ! isvector (p) || isempty (p))  # p could be complex
    error ("place: p must be a vector");
  endif
  
  p = sort (reshape (p, [], 1));   # complex conjugate pairs must appear together
  wr = real (p);
  wi = imag (p);
  
  n = rows (a);                    # number of states
  np = length (p);                 # number of given eigenvalues
  
  if (np > n)
    error ("place: at most %d eigenvalues can be assigned for the given matrix a (%dx%d)",
            n, n, n);
  endif

  if (isempty (alpha))
    if (discrete)
      alpha = 0;
    else
      alpha = - norm (a, inf);
    endif
  endif
  
  if (isempty (tol))
    tol = 0;
  endif

  [f, nfp, nap, nup, z] = __sl_sb01bd__ (a, b, wr, wi, discrete, alpha, tol);
  f = -f;                          # A + B*F --> A - B*F

  info = struct ("nfp", nfp, "nap", nap, "nup", nup, "z", z);

endfunction


## Test from "legacy" control package 1.0.*
%!shared A, B, C, P, Kexpected
%! A = [0, 1; 3, 2];
%! B = [0; 1];
%! C = [2, 1];  # C is needed for ss; it doesn't matter what the value of C is
%! P = [-1, -0.5];
%! Kexpected = [3.5, 3.5];
%!assert (place (ss (A, B, C), P), Kexpected, 2*eps);
%!assert (place (A, B, P), Kexpected, 2*eps);

## FIXME: Test from SLICOT example SB01BD fails with 4 eigenvalues in P
%!shared F, F_exp, ev_ol, ev_cl
%! A = [-6.8000   0.0000  -207.0000   0.0000
%!       1.0000   0.0000     0.0000   0.0000
%!      43.2000   0.0000     0.0000  -4.2000
%!       0.0000   0.0000     1.0000   0.0000];
%!
%! B = [ 5.6400   0.0000
%!       0.0000   0.0000
%!       0.0000   1.1800
%!       0.0000   0.0000];
%!
%! P = [-0.5000 + 0.1500i
%!      -0.5000 - 0.1500i];
#%!      -2.0000 + 0.0000i
#%!      -0.4000 + 0.0000i];
%!
%! ALPHA = -0.4;
%! TOL = 1e-8;
%!
%! F = place (A, B, P, ALPHA, TOL);
%!
%! F_exp = - [-0.0876  -4.2138   0.0837 -18.1412
%!            -0.0233  18.2483  -0.4259  -4.8120];
%!
%! ev_ol = sort (eig (A));
%! ev_cl = sort (eig (A - B*F));
%!
%!assert (F, F_exp, 1e-4);
%!assert (ev_ol(3:4), ev_cl(3:4), 1e-4);
