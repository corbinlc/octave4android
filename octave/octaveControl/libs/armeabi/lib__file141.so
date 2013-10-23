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
## @deftypefn {Function File} {@var{sys} =} minreal (@var{sys})
## @deftypefnx {Function File} {@var{sys} =} minreal (@var{sys}, @var{tol})
## Minimal realization or zero-pole cancellation of LTI models.
## @end deftypefn

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: October 2009
## Version: 0.3

function sys = minreal (sys, tol = "def")

  if (nargin > 2)  # nargin == 0 not possible because minreal is inside @lti
    print_usage ();
  endif

  if (! is_real_scalar (tol) && tol != "def")
    error ("minreal: second argument must be a real scalar");
  endif

  sys = __minreal__ (sys, tol);

endfunction


## ss: minreal (SLICOT TB01PD)
%!shared C, D
%!
%! A = ss (-2, 3, 4, 5);
%! B = A / A;
%! C = minreal (B, 1e-15);
%! D = ss (1);
%!
%!assert (C.a, D.a);
%!assert (C.b, D.b);
%!assert (C.c, D.c);
%!assert (C.d, D.d);

%!shared M, Me
%! A = [ 1.0   2.0   0.0
%!       4.0  -1.0   0.0
%!       0.0   0.0   1.0 ];
%!
%! B = [ 1.0
%!       0.0
%!       1.0 ];
%!
%! C = [ 0.0   1.0  -1.0
%!       0.0   0.0   1.0 ];
%!
%! D = zeros (2, 1);
%!
%! sys = ss (A, B, C, D, "scaled", true);
%! sysmin = minreal (sys, 0.0);
%! [Ar, Br, Cr, Dr] = ssdata (sysmin);
%! M = [Ar, Br; Cr, Dr];
%!
%! Ae = [ 1.0000  -1.4142   1.4142
%!       -2.8284   0.0000   1.0000
%!        2.8284   1.0000   0.0000 ];
%!
%! Be = [-1.0000
%!        0.7071
%!        0.7071 ];
%!
%! Ce = [ 0.0000   0.0000  -1.4142
%!        0.0000   0.7071   0.7071 ];
%!
%! De = zeros (2, 1);
%!
%! Me = [Ae, Be; Ce, De];
%!
%!assert (M, Me, 1e-4);


## dss: minreal (SLICOT TG01JD)
## FIXME: Test fails with larger ldwork in sltg01jd.cc
%!shared Ar, Br, Cr, Dr, Er, Ae, Be, Ce, De, Ee
%! A = [ -2    -3     0     0     0     0     0     0     0
%!        1     0     0     0     0     0     0     0     0
%!        0     0    -2    -3     0     0     0     0     0
%!        0     0     1     0     0     0     0     0     0
%!        0     0     0     0     1     0     0     0     0
%!        0     0     0     0     0     1     0     0     0
%!        0     0     0     0     0     0     1     0     0
%!        0     0     0     0     0     0     0     1     0
%!        0     0     0     0     0     0     0     0     1 ];
%!
%! E = [  1     0     0     0     0     0     0     0     0
%!        0     1     0     0     0     0     0     0     0
%!        0     0     1     0     0     0     0     0     0
%!        0     0     0     1     0     0     0     0     0
%!        0     0     0     0     0     0     0     0     0
%!        0     0     0     0     1     0     0     0     0
%!        0     0     0     0     0     0     0     0     0
%!        0     0     0     0     0     0     1     0     0
%!        0     0     0     0     0     0     0     1     0 ];
%!
%! B = [  1     0
%!        0     0
%!        0     1
%!        0     0
%!       -1     0
%!        0     0
%!        0    -1
%!        0     0
%!        0     0 ];
%!
%! C = [  1     0     1    -3     0     1     0     2     0
%!        0     1     1     3     0     1     0     0     1 ];
%!
%! D = zeros (2, 2);
%!
%! sys = dss (A, B, C, D, E, "scaled", true);
%! sysmin = minreal (sys, 0.0);
%! [Ar, Br, Cr, Dr, Er] = dssdata (sysmin);
%!
%! Ae = [  1.0000  -0.0393  -0.0980  -0.1066   0.0781  -0.2330   0.0777
%!         0.0000   1.0312   0.2717   0.2609  -0.1533   0.6758  -0.3553
%!         0.0000   0.0000   1.3887   0.6699  -0.4281   1.6389  -0.7615
%!         0.0000   0.0000   0.0000  -1.2147   0.2423  -0.9792   0.4788
%!         0.0000   0.0000   0.0000   0.0000  -1.0545   0.5035  -0.2788
%!         0.0000   0.0000   0.0000   0.0000   0.0000   1.6355  -0.4323
%!         0.0000   0.0000   0.0000   0.0000   0.0000   0.0000   1.0000 ];
%!
%! Ee = [  0.4100   0.2590   0.5080  -0.3109   0.0705   0.1429  -0.1477
%!        -0.7629  -0.3464   0.0992  -0.3007   0.0619   0.2483  -0.0152
%!         0.1120  -0.2124  -0.4184  -0.1288   0.0569  -0.4213  -0.6182
%!         0.0000   0.1122  -0.0039   0.2771  -0.0758   0.0975   0.3923
%!         0.0000   0.0000   0.3708  -0.4290   0.1006   0.1402  -0.2699
%!         0.0000   0.0000   0.0000   0.0000   0.9458  -0.2211   0.2378
%!         0.0000   0.0000   0.0000   0.5711   0.2648   0.5948  -0.5000 ];
%!
%! Be = [ -0.5597   0.2363
%!        -0.4843  -0.0498
%!        -0.4727  -0.1491
%!         0.1802   1.1574
%!         0.5995   0.1556
%!        -0.1729  -0.3999
%!         0.0000   0.2500 ];
%!
%! Ce = [  0.0000   0.0000   0.0000   0.0000   0.0000   0.0000   4.0000
%!         0.0000   0.0000   0.0000   0.0000   0.0000   3.1524  -1.7500 ];
%!
%! De = zeros (2, 2);
%!
%!assert (Ar, Ae, 1e-4);
%!assert (Br, Be, 1e-4);
%!assert (Cr, Ce, 1e-4);
%!assert (Dr, De, 1e-4);
%!assert (Er, Ee, 1e-4);


## tf: minreal
%!shared a, b, c, d
%! s = tf ("s");
%! G1 = (s+1)*s*5/(s+1)/(s^2+s+1);
%! G2 = tf ([1, 1, 1], [2, 2, 2]);
%! G1min = minreal (G1);
%! G2min = minreal (G2);
%! a = G1min.num{1, 1};
%! b = G1min.den{1, 1};
%! c = G2min.num{1, 1};
%! d = G2min.den{1, 1};
%!assert (a, [5, 0], 1e-4);
%!assert (b, [1, 1, 1], 1e-4);
%!assert (c, 0.5, 1e-4);
%!assert (d, 1, 1e-4);
