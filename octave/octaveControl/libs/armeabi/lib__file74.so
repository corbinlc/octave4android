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
## @deftypefn{Function File} {[@var{K}, @var{N}, @var{gamma}, @var{rcond}] =} h2syn (@var{P}, @var{nmeas}, @var{ncon})
## H-2 control synthesis for LTI plant.
##
## @strong{Inputs}
## @table @var
## @item P
## Generalized plant.  Must be a proper/realizable LTI model.
## @item nmeas
## Number of measured outputs v.  The last @var{nmeas} outputs of @var{P} are connected to the
## inputs of controller @var{K}.  The remaining outputs z (indices 1 to p-nmeas) are used
## to calculate the H-2 norm.
## @item ncon
## Number of controlled inputs u.  The last @var{ncon} inputs of @var{P} are connected to the
## outputs of controller @var{K}.  The remaining inputs w (indices 1 to m-ncon) are excited
## by a harmonic test signal.
## @end table
##
## @strong{Outputs}
## @table @var
## @item K
## State-space model of the H-2 optimal controller.
## @item N
## State-space model of the lower LFT of @var{P} and @var{K}.
## @item gamma
## H-2 norm of @var{N}.
## @item rcond
## Vector @var{rcond} contains estimates of the reciprocal condition
## numbers of the matrices which are to be inverted and
## estimates of the reciprocal condition numbers of the
## Riccati equations which have to be solved during the
## computation of the controller @var{K}.  For details,
## see the description of the corresponding SLICOT algorithm.
## @end table
##
## @strong{Block Diagram}
## @example
## @group
##
## gamma = min||N(K)||             N = lft (P, K)
##          K         2
##
##                +--------+  
##        w ----->|        |-----> z
##                |  P(s)  |
##        u +---->|        |-----+ v
##          |     +--------+     |
##          |                    |
##          |     +--------+     |
##          +-----|  K(s)  |<----+
##                +--------+
##
##                +--------+      
##        w ----->|  N(s)  |-----> z
##                +--------+
## @end group
## @end example
##
## @strong{Algorithm}@*
## Uses SLICOT SB10HD and SB10ED by courtesy of
## @uref{http://www.slicot.org, NICONET e.V.}
##
## @seealso{augw, lqr, dlqr, kalman}
## @end deftypefn

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: December 2009
## Version: 0.1

function [K, varargout] = h2syn (P, nmeas, ncon)

  ## check input arguments
  if (nargin != 3)
    print_usage ();
  endif
  
  if (! isa (P, "lti"))
    error ("h2syn: first argument must be an LTI system");
  endif
  
  if (! is_real_scalar (nmeas))
    error ("h2syn: second argument invalid");
  endif
  
  if (! is_real_scalar (ncon))
    error ("h2syn: third argument invalid");
  endif

  [a, b, c, d, tsam] = ssdata (P);
  
  ## check assumptions A1 - A3
  m = columns (b);
  p = rows (c);
  
  m1 = m - ncon;
  p1 = p - nmeas;
  
  d11 = d(1:p1, 1:m1);
  
  if (isct (P) && any (d11(:)))
    warning ("h2syn: setting matrice D11 to zero");
    d(1:p1, 1:m1) = 0;
  endif
  
  if (! isstabilizable (P(:, m1+1:m)))
    error ("h2syn: (A, B2) must be stabilizable");
  endif
  
  if (! isdetectable (P(p1+1:p, :)))
    error ("h2syn: (C2, A) must be detectable");
  endif

  ## H-2 synthesis
  if (isct (P))             # continuous plant
    [ak, bk, ck, dk, rcond] = __sl_sb10hd__ (a, b, c, d, ncon, nmeas);
  else                      # discrete plant
    [ak, bk, ck, dk, rcond] = __sl_sb10ed__ (a, b, c, d, ncon, nmeas);
  endif
  
  ## controller
  K = ss (ak, bk, ck, dk, tsam);
  
  if (nargout > 1)
    N = lft (P, K);
    varargout{1} = N;
    if (nargout > 2)
      varargout{2} = norm (N, 2);
      if (nargout > 3)
        varargout{3} = rcond;
      endif
    endif
  endif

endfunction


## continuous-time case
%!shared M, M_exp
%! A = [-1.0  0.0  4.0  5.0 -3.0 -2.0
%!      -2.0  4.0 -7.0 -2.0  0.0  3.0
%!      -6.0  9.0 -5.0  0.0  2.0 -1.0
%!      -8.0  4.0  7.0 -1.0 -3.0  0.0
%!       2.0  5.0  8.0 -9.0  1.0 -4.0
%!       3.0 -5.0  8.0  0.0  2.0 -6.0];
%!
%! B = [-3.0 -4.0 -2.0  1.0  0.0
%!       2.0  0.0  1.0 -5.0  2.0
%!      -5.0 -7.0  0.0  7.0 -2.0
%!       4.0 -6.0  1.0  1.0 -2.0
%!      -3.0  9.0 -8.0  0.0  5.0
%!       1.0 -2.0  3.0 -6.0 -2.0];
%!
%! C = [ 1.0 -1.0  2.0 -4.0  0.0 -3.0
%!      -3.0  0.0  5.0 -1.0  1.0  1.0
%!      -7.0  5.0  0.0 -8.0  2.0 -2.0
%!       9.0 -3.0  4.0  0.0  3.0  7.0
%!       0.0  1.0 -2.0  1.0 -6.0 -2.0];
%!
%! D = [ 0.0  0.0  0.0 -4.0 -1.0
%!       0.0  0.0  0.0  1.0  0.0
%!       0.0  0.0  0.0  0.0  1.0
%!       3.0  1.0  0.0  1.0 -3.0
%!      -2.0  0.0  1.0  7.0  1.0];
%!
%! P = ss (A, B, C, D);
%! K = h2syn (P, 2, 2);
%! M = [K.A, K.B; K.C, K.D];
%!
%! KA = [  88.0015  -145.7298   -46.2424    82.2168   -45.2996   -31.1407
%!         25.7489   -31.4642   -12.4198     9.4625    -3.5182     2.7056
%!         54.3008  -102.4013   -41.4968    50.8412   -20.1286   -26.7191
%!        108.1006  -198.0785   -45.4333    70.3962   -25.8591   -37.2741
%!       -115.8900   226.1843    47.2549   -47.8435   -12.5004    34.7474
%!         59.0362  -101.8471   -20.1052    36.7834   -16.1063   -26.4309];
%!
%! KB = [   3.7345     3.4758
%!         -0.3020     0.6530
%!          3.4735     4.0499
%!          4.3198     7.2755
%!         -3.9424   -10.5942
%!          2.1784     2.5048];
%!
%! KC = [  -2.3346     3.2556     0.7150    -0.9724     0.6962     0.4074
%!          7.6899    -8.4558    -2.9642     7.0365    -4.2844     0.1390];
%!
%! KD = [   0.0000     0.0000
%!          0.0000     0.0000];
%!
%! M_exp = [KA, KB; KC, KD];
%!
%!assert (M, M_exp, 1e-4);


## discrete-time case
%!shared M, M_exp
%! A = [-0.7  0.0  0.3  0.0 -0.5 -0.1
%!      -0.6  0.2 -0.4 -0.3  0.0  0.0
%!      -0.5  0.7 -0.1  0.0  0.0 -0.8
%!      -0.7  0.0  0.0 -0.5 -1.0  0.0
%!       0.0  0.3  0.6 -0.9  0.1 -0.4
%!       0.5 -0.8  0.0  0.0  0.2 -0.9];
%!
%! B = [-1.0 -2.0 -2.0  1.0  0.0
%!       1.0  0.0  1.0 -2.0  1.0
%!      -3.0 -4.0  0.0  2.0 -2.0
%!       1.0 -2.0  1.0  0.0 -1.0
%!       0.0  1.0 -2.0  0.0  3.0
%!       1.0  0.0  3.0 -1.0 -2.0];
%!
%! C = [ 1.0 -1.0  2.0 -2.0  0.0 -3.0
%!      -3.0  0.0  1.0 -1.0  1.0  0.0
%!       0.0  2.0  0.0 -4.0  0.0 -2.0
%!       1.0 -3.0  0.0  0.0  3.0  1.0
%!       0.0  1.0 -2.0  1.0  0.0 -2.0];
%!
%! D = [ 1.0 -1.0 -2.0  0.0  0.0
%!       0.0  1.0  0.0  1.0  0.0
%!       2.0 -1.0 -3.0  0.0  1.0
%!       0.0  1.0  0.0  1.0 -1.0
%!       0.0  0.0  1.0  2.0  1.0];
%!
%! P = ss (A, B, C, D, 1);  # value of sampling time doesn't matter
%! K = h2syn (P, 2, 2);
%! M = [K.A, K.B; K.C, K.D];
%!
%! KA = [-0.0551  -2.1891  -0.6607  -0.2532   0.6674  -1.0044
%!       -1.0379   2.3804   0.5031   0.3960  -0.6605   1.2673
%!       -0.0876  -2.1320  -0.4701  -1.1461   1.2927  -1.5116
%!       -0.1358  -2.1237  -0.9560  -0.7144   0.6673  -0.7957
%!        0.4900   0.0895   0.2634  -0.2354   0.1623  -0.2663
%!        0.1672  -0.4163   0.2871  -0.1983   0.4944  -0.6967];
%!
%! KB = [-0.5985  -0.5464
%!        0.5285   0.6087
%!       -0.7600  -0.4472
%!       -0.7288  -0.6090
%!        0.0532   0.0658
%!       -0.0663   0.0059];
%!
%! KC = [ 0.2500  -1.0200  -0.3371  -0.2733   0.2747  -0.4444
%!        0.0654   0.2095   0.0632   0.2089  -0.1895   0.1834];
%!
%! KD = [-0.2181  -0.2070
%!        0.1094   0.1159];
%!
%! M_exp = [KA, KB; KC, KD];
%!
%!assert (M, M_exp, 1e-4);