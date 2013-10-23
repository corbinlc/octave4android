## Copyright (C) 2011   Lukas F. Reichlin
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
## @deftypefn{Function File} {[@var{K}, @var{N}, @var{gamma}, @var{info}] =} ncfsyn (@var{G}, @var{W1}, @var{W2}, @var{factor})
## Loop shaping H-infinity synthesis.  Compute positive feedback controller using 
## the McFarlane/Glover normalized coprime factor (NCF) loop shaping design procedure.
##
## @strong{Inputs}
## @table @var
## @item G
## LTI model of plant.
## @item W1
## LTI model of precompensator.  Model must be SISO or of appropriate size.
## An identity matrix is taken if @var{W1} is not specified or if an empty model
## @code{[]} is passed.
## @item W2
## LTI model of postcompensator.  Model must be SISO or of appropriate size.
## An identity matrix is taken if @var{W2} is not specified or if an empty model
## @code{[]} is passed.
## @item factor
## @code{factor = 1} implies that an optimal controller is required.
## @code{factor > 1} implies that a suboptimal controller is required,
## achieving a performance that is @var{factor} times less than optimal.
## Default value is 1.
## @end table
##
## @strong{Outputs}
## @table @var
## @item K
## State-space model of the H-infinity loop-shaping controller.
## @item N
## State-space model of the closed loop depicted below.
## @item gamma
## L-infinity norm of @var{N}.  @code{gamma = norm (N, inf)}.
## @item info
## Structure containing additional information.
## @item info.emax
## Nugap robustness.  @code{emax = inv (gamma)}.
## @item info.Gs
## Shaped plant.  @code{Gs = W2 * G * W1}.
## @item info.Ks
## Controller for shaped plant.  @code{Ks = ncfsyn (Gs)}.
## @item info.rcond
## Estimates of the reciprocal condition numbers of the Riccati equations
## and a few other things.  For details, see the description of the
## corresponding SLICOT algorithm.
## @end table
##
## @strong{Block Diagram of N}
## @example
## @group
##
##             ^ z1              ^ z2
##             |                 |
##  w1  +      |   +--------+    |            +--------+
## ----->(+)---+-->|   Ks   |----+--->(+)---->|   Gs   |----+
##        ^ +      +--------+          ^      +--------+    |
##        |                        w2  |                    |
##        |                                                 |
##        +-------------------------------------------------+
## @end group
## @end example
##
## @strong{Algorithm}@*
## Uses SLICOT SB10ID, SB10KD and SB10ZD by courtesy of
## @uref{http://www.slicot.org, NICONET e.V.}
## @end deftypefn

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: July 2011
## Version: 0.1

function [K, varargout] = ncfsyn (G, W1 = [], W2 = [], factor = 1.0)

  if (nargin == 0 || nargin > 4)
    print_usage ();
  endif
  
  if (! isa (G, "lti"))
    error ("ncfsyn: first argument must be an LTI system");
  endif
  
  if (! is_real_scalar (factor) || factor < 1.0)
    error ("ncfsyn: fourth argument invalid");
  endif

  [p, m] = size (G);

  W1 = __adjust_weighting__ (W1, m);
  W2 = __adjust_weighting__ (W2, p);
  
  Gs = W2 * G * W1;            # shaped plant

  [a, b, c, d, tsam] = ssdata (Gs);

  ## synthesis
  if (isct (Gs))               # continuous-time
    [ak, bk, ck, dk, rcond] = __sl_sb10id__ (a, b, c, d, factor);
  elseif (any (d(:)))          # discrete-time, d != 0
    [ak, bk, ck, dk, rcond] = __sl_sb10zd__ (a, b, c, d, factor, 0.0);
  else                         # discrete-time, d == 0
    [ak, bk, ck, dk, rcond] = __sl_sb10kd__ (a, b, c, factor);
  endif

  ## controller
  Ks = ss (ak, bk, ck, dk, tsam);
  
  K = W1 * Ks * W2;

  if (nargout > 1)
    ## FIXME: is this really the same thing as the dark side does?
    N = append (eye (p), Ks, Gs);
    M = [zeros(p,p), zeros(p,m),     eye(p);
             eye(p), zeros(p,m), zeros(p,p);
         zeros(m,p),     eye(m), zeros(m,p)];
    in_idx = [1:p, 2*p+(1:m)];
    out_idx = 1:p+m;
    N = mconnect (N, M, in_idx, out_idx);
    varargout{1} = N;
    if (nargout > 2)
      gamma = norm (N, inf);
      varargout{2} = gamma;
      if (nargout > 3)
        varargout{3} = struct ("emax", inv (gamma), "Gs", Gs, "Ks", Ks, "rcond", rcond);
      endif
    endif
  endif

endfunction


function W = __adjust_weighting__ (W, s)

  if (isempty (W))
    W = ss (eye (s));
  else
    W = ss (W);
    ## if (! isstable (W))
    ##   error ("ncfsyn: %s must be stable", inputname (1));
    ## endif
    ## if (! isminimumphase (W))
    ##   error ("ncfsyn: %s must be minimum-phase", inputname (1));
    ## endif
    [p, m] = size (W);
    if (m == s && p == s)      # model is of correct size
      return;
    elseif (m == 1 && p == 1)  # model is SISO
      tmp = W;
      for k = 2 : s
        W = append (W, tmp);   # stack SISO model s times
      endfor
    else                       # model is invalid
      error ("ncfsyn: %s must have 1 or %d inputs and outputs", inputname (1), s);
    endif
  endif

endfunction


## continuous-time case, direct access to sb10id
%!shared AK, BK, CK, DK, RCOND, AKe, BKe, CKe, DKe, RCONDe
%! A = [ -1.0  0.0  4.0  5.0 -3.0 -2.0
%!       -2.0  4.0 -7.0 -2.0  0.0  3.0
%!       -6.0  9.0 -5.0  0.0  2.0 -1.0
%!       -8.0  4.0  7.0 -1.0 -3.0  0.0
%!        2.0  5.0  8.0 -9.0  1.0 -4.0
%!        3.0 -5.0  8.0  0.0  2.0 -6.0 ];
%!   
%! B = [ -3.0 -4.0
%!        2.0  0.0
%!       -5.0 -7.0
%!        4.0 -6.0
%!       -3.0  9.0
%!        1.0 -2.0 ];
%!   
%! C = [  1.0 -1.0  2.0 -4.0  0.0 -3.0
%!       -3.0  0.0  5.0 -1.0  1.0  1.0
%!       -7.0  5.0  0.0 -8.0  2.0 -2.0 ];
%!  
%! D = [  1.0 -2.0
%!        0.0  4.0
%!        5.0 -3.0 ];
%!    
%! FACTOR = 1.0;
%!
%! [AK, BK, CK, DK, RCOND] = __sl_sb10id__ (A, B, C, D, FACTOR);
%!
%! AKe = [ -39.0671    9.9293   22.2322  -27.4113   43.8655
%!          -6.6117    3.0006   11.0878  -11.4130   15.4269
%!          33.6805   -6.6934  -23.9953   14.1438  -33.4358
%!         -32.3191    9.7316   25.4033  -24.0473   42.0517
%!         -44.1655   18.7767   34.8873  -42.4369   50.8437 ];
%!
%! BKe = [ -10.2905  -16.5382  -10.9782
%!          -4.3598   -8.7525   -5.1447
%!           6.5962    1.8975    6.2316
%!          -9.8770  -14.7041  -11.8778
%!          -9.6726  -22.7309  -18.2692 ];
%!
%! CKe = [  -0.6647   -0.0599   -1.0376    0.5619    1.7297
%!          -8.4202    3.9573    7.3094   -7.6283   10.6768 ];
%!
%! DKe = [  0.8466    0.4979   -0.6993
%!         -1.2226   -4.8689   -4.5056 ];
%!
%! RCONDe = [ 0.13861D-01  0.90541D-02 ].';
%!
%!assert (AK, AKe, 1e-4);
%!assert (BK, BKe, 1e-4);
%!assert (CK, CKe, 1e-4);
%!assert (DK, DKe, 1e-4);
%!assert (RCOND, RCONDe, 1e-4);


## continuous-time case
%!shared AK, BK, CK, DK, RCOND, AKe, BKe, CKe, DKe, RCONDe
%! A = [ -1.0  0.0  4.0  5.0 -3.0 -2.0
%!       -2.0  4.0 -7.0 -2.0  0.0  3.0
%!       -6.0  9.0 -5.0  0.0  2.0 -1.0
%!       -8.0  4.0  7.0 -1.0 -3.0  0.0
%!        2.0  5.0  8.0 -9.0  1.0 -4.0
%!        3.0 -5.0  8.0  0.0  2.0 -6.0 ];
%!   
%! B = [ -3.0 -4.0
%!        2.0  0.0
%!       -5.0 -7.0
%!        4.0 -6.0
%!       -3.0  9.0
%!        1.0 -2.0 ];
%!   
%! C = [  1.0 -1.0  2.0 -4.0  0.0 -3.0
%!       -3.0  0.0  5.0 -1.0  1.0  1.0
%!       -7.0  5.0  0.0 -8.0  2.0 -2.0 ];
%!  
%! D = [  1.0 -2.0
%!        0.0  4.0
%!        5.0 -3.0 ];
%!    
%! FACTOR = 1.0;
%!
%! G = ss (A, B, C, D);
%! K = ncfsyn (G, [], [], FACTOR);
%! [AK, BK, CK, DK] = ssdata (K);
%!
%! AKe = [ -39.0671    9.9293   22.2322  -27.4113   43.8655
%!          -6.6117    3.0006   11.0878  -11.4130   15.4269
%!          33.6805   -6.6934  -23.9953   14.1438  -33.4358
%!         -32.3191    9.7316   25.4033  -24.0473   42.0517
%!         -44.1655   18.7767   34.8873  -42.4369   50.8437 ];
%!
%! BKe = [ -10.2905  -16.5382  -10.9782
%!          -4.3598   -8.7525   -5.1447
%!           6.5962    1.8975    6.2316
%!          -9.8770  -14.7041  -11.8778
%!          -9.6726  -22.7309  -18.2692 ];
%!
%! CKe = [  -0.6647   -0.0599   -1.0376    0.5619    1.7297
%!          -8.4202    3.9573    7.3094   -7.6283   10.6768 ];
%!
%! DKe = [  0.8466    0.4979   -0.6993
%!         -1.2226   -4.8689   -4.5056 ];
%!
%! RCONDe = [ 0.13861D-01  0.90541D-02 ];
%!
%!assert (AK, AKe, 1e-4);
%!assert (BK, BKe, 1e-4);
%!assert (CK, CKe, 1e-4);
%!assert (DK, DKe, 1e-4);


## discrete-time case D==0, direct access to sb10kd
%!shared AK, BK, CK, DK, RCOND, AKe, BKe, CKe, DKe, RCONDe
%! A = [  0.2  0.0  0.3  0.0 -0.3 -0.1
%!       -0.3  0.2 -0.4 -0.3  0.0  0.0
%!       -0.1  0.1 -0.1  0.0  0.0 -0.3
%!        0.1  0.0  0.0 -0.1 -0.1  0.0
%!        0.0  0.3  0.6  0.2  0.1 -0.4
%!        0.2 -0.4  0.0  0.0  0.2 -0.2 ];
%!   
%! B = [ -1.0 -2.0
%!        1.0  3.0 
%!       -3.0 -4.0 
%!        1.0 -2.0 
%!        0.0  1.0
%!        1.0  5.0 ];
%!   
%! C = [  1.0 -1.0  2.0 -2.0  0.0 -3.0
%!       -3.0  0.0  1.0 -1.0  1.0 -1.0 ];
%!    
%! FACTOR = 1.1;
%!
%! [AK, BK, CK, DK, RCOND] = __sl_sb10kd__ (A, B, C, FACTOR);
%!
%! AKe = [  0.0337   0.0222   0.0858   0.1264  -0.1872   0.1547
%!          0.4457   0.0668  -0.2255  -0.3204  -0.4548  -0.0691
%!         -0.2419  -0.2506  -0.0982  -0.1321  -0.0130  -0.0838
%!         -0.4402   0.3654  -0.0335  -0.2444   0.6366  -0.6469
%!         -0.3623   0.3854   0.4162   0.4502   0.0065   0.1261
%!         -0.0121  -0.4377   0.0604   0.2265  -0.3389   0.4542 ];
%!
%! BKe = [  0.0931  -0.0269
%!         -0.0872   0.1599
%!          0.0956  -0.1469
%!         -0.1728   0.0129
%!          0.2022  -0.1154
%!          0.2419  -0.1737 ];
%!
%! CKe = [ -0.3677   0.2188   0.0403  -0.0854   0.3564  -0.3535
%!          0.1624  -0.0708   0.0058   0.0606  -0.2163   0.1802 ];
%!
%! DKe = [ -0.0857  -0.0246
%!          0.0460   0.0074 ];
%!
%! RCONDe = [ 0.11269D-01  0.17596D-01  0.18225D+00  0.75968D-03 ].';
%!
%!assert (AK, AKe, 1e-4);
%!assert (BK, BKe, 1e-4);
%!assert (CK, CKe, 1e-4);
%!assert (DK, DKe, 1e-4);
%!assert (RCOND, RCONDe, 1e-4);


## discrete-time case D==0
%!shared AK, BK, CK, DK, RCOND, AKe, BKe, CKe, DKe, RCONDe
%! A = [  0.2  0.0  0.3  0.0 -0.3 -0.1
%!       -0.3  0.2 -0.4 -0.3  0.0  0.0
%!       -0.1  0.1 -0.1  0.0  0.0 -0.3
%!        0.1  0.0  0.0 -0.1 -0.1  0.0
%!        0.0  0.3  0.6  0.2  0.1 -0.4
%!        0.2 -0.4  0.0  0.0  0.2 -0.2 ];
%!   
%! B = [ -1.0 -2.0
%!        1.0  3.0 
%!       -3.0 -4.0 
%!        1.0 -2.0 
%!        0.0  1.0
%!        1.0  5.0 ];
%!   
%! C = [  1.0 -1.0  2.0 -2.0  0.0 -3.0
%!       -3.0  0.0  1.0 -1.0  1.0 -1.0 ];
%!    
%! FACTOR = 1.1;
%!
%! G = ss (A, B, C, [], 1);  # value of sampling time doesn't matter
%! K = ncfsyn (G, [], [], FACTOR);
%! [AK, BK, CK, DK] = ssdata (K);
%!
%! AKe = [  0.0337   0.0222   0.0858   0.1264  -0.1872   0.1547
%!          0.4457   0.0668  -0.2255  -0.3204  -0.4548  -0.0691
%!         -0.2419  -0.2506  -0.0982  -0.1321  -0.0130  -0.0838
%!         -0.4402   0.3654  -0.0335  -0.2444   0.6366  -0.6469
%!         -0.3623   0.3854   0.4162   0.4502   0.0065   0.1261
%!         -0.0121  -0.4377   0.0604   0.2265  -0.3389   0.4542 ];
%!
%! BKe = [  0.0931  -0.0269
%!         -0.0872   0.1599
%!          0.0956  -0.1469
%!         -0.1728   0.0129
%!          0.2022  -0.1154
%!          0.2419  -0.1737 ];
%!
%! CKe = [ -0.3677   0.2188   0.0403  -0.0854   0.3564  -0.3535
%!          0.1624  -0.0708   0.0058   0.0606  -0.2163   0.1802 ];
%!
%! DKe = [ -0.0857  -0.0246
%!          0.0460   0.0074 ];
%!
%! RCONDe = [ 0.11269D-01  0.17596D-01  0.18225D+00  0.75968D-03 ].';
%!
%!assert (AK, AKe, 1e-4);
%!assert (BK, BKe, 1e-4);
%!assert (CK, CKe, 1e-4);
%!assert (DK, DKe, 1e-4);


## discrete-time case D!=0, direct access to sb10zd
%!shared AK, BK, CK, DK, RCOND, AKe, BKe, CKe, DKe, RCONDe
%! A = [  0.2  0.0  3.0  0.0 -0.3 -0.1
%!       -3.0  0.2 -0.4 -0.3  0.0  0.0
%!       -0.1  0.1 -1.0  0.0  0.0 -3.0
%!        1.0  0.0  0.0 -1.0 -1.0  0.0
%!        0.0  0.3  0.6  2.0  0.1 -0.4
%!        0.2 -4.0  0.0  0.0  0.2 -2.0 ];
%!   
%! B = [ -1.0 -2.0
%!        1.0  3.0 
%!       -3.0 -4.0 
%!        1.0 -2.0 
%!        0.0  1.0
%!        1.0  5.0 ];
%!   
%! C = [  1.0 -1.0  2.0 -2.0  0.0 -3.0
%!       -3.0  0.0  1.0 -1.0  1.0 -1.0
%!        2.0  4.0 -3.0  0.0  5.0  1.0 ];
%!  
%! D = [ 10.0 -6.0
%!       -7.0  8.0
%!        2.0 -4.0 ];
%!    
%! FACTOR = 1.1;
%!
%! [AK, BK, CK, DK, RCOND] = __sl_sb10zd__ (A, B, C, D, FACTOR, 0.0);
%!
%! AKe = [  1.0128   0.5101  -0.1546   1.1300   3.3759   0.4911
%!         -2.1257  -1.4517  -0.4486   0.3493  -1.5506  -1.4296
%!         -1.0930  -0.6026  -0.1344   0.2253  -1.5625  -0.6762
%!          0.3207   0.1698   0.2376  -1.1781  -0.8705   0.2896
%!          0.5017   0.9006   0.0668   2.3613   0.2049   0.3703
%!          1.0787   0.6703   0.2783  -0.7213   0.4918   0.7435 ];
%!
%! BKe = [  0.4132   0.3112  -0.8077
%!          0.2140   0.4253   0.1811
%!         -0.0710   0.0807   0.3558
%!         -0.0121  -0.2019   0.0249
%!          0.1047   0.1399  -0.0457
%!         -0.2542  -0.3472   0.0523 ];
%!
%! CKe = [ -0.0372  -0.0456  -0.0040   0.0962  -0.2059  -0.0571
%!          0.1999   0.2994   0.1335  -0.0251  -0.3108   0.2048 ];
%!
%! DKe = [  0.0629  -0.0022   0.0363
%!         -0.0228   0.0195   0.0600 ];
%!
%! RCONDe = [ 0.27949D-03  0.66679D-03  0.45677D-01  0.23433D-07  0.68495D-01  0.76854D-01 ].';
%!
%!assert (AK, AKe, 1e-4);
%!assert (BK, BKe, 1e-4);
%!assert (CK, CKe, 1e-4);
%!assert (DK, DKe, 1e-4);
%!assert (RCOND, RCONDe, 1e-4);


## discrete-time case D!=0
%!shared AK, BK, CK, DK, RCOND, AKe, BKe, CKe, DKe, RCONDe
%! A = [  0.2  0.0  3.0  0.0 -0.3 -0.1
%!       -3.0  0.2 -0.4 -0.3  0.0  0.0
%!       -0.1  0.1 -1.0  0.0  0.0 -3.0
%!        1.0  0.0  0.0 -1.0 -1.0  0.0
%!        0.0  0.3  0.6  2.0  0.1 -0.4
%!        0.2 -4.0  0.0  0.0  0.2 -2.0 ];
%!   
%! B = [ -1.0 -2.0
%!        1.0  3.0 
%!       -3.0 -4.0 
%!        1.0 -2.0 
%!        0.0  1.0
%!        1.0  5.0 ];
%!   
%! C = [  1.0 -1.0  2.0 -2.0  0.0 -3.0
%!       -3.0  0.0  1.0 -1.0  1.0 -1.0
%!        2.0  4.0 -3.0  0.0  5.0  1.0 ];
%!  
%! D = [ 10.0 -6.0
%!       -7.0  8.0
%!        2.0 -4.0 ];
%!    
%! FACTOR = 1.1;
%!
%! G = ss (A, B, C, D, 1);  # value of sampling time doesn't matter
%! K = ncfsyn (G, [], [], FACTOR);
%! [AK, BK, CK, DK] = ssdata (K);
%!
%! AKe = [  1.0128   0.5101  -0.1546   1.1300   3.3759   0.4911
%!         -2.1257  -1.4517  -0.4486   0.3493  -1.5506  -1.4296
%!         -1.0930  -0.6026  -0.1344   0.2253  -1.5625  -0.6762
%!          0.3207   0.1698   0.2376  -1.1781  -0.8705   0.2896
%!          0.5017   0.9006   0.0668   2.3613   0.2049   0.3703
%!          1.0787   0.6703   0.2783  -0.7213   0.4918   0.7435 ];
%!
%! BKe = [  0.4132   0.3112  -0.8077
%!          0.2140   0.4253   0.1811
%!         -0.0710   0.0807   0.3558
%!         -0.0121  -0.2019   0.0249
%!          0.1047   0.1399  -0.0457
%!         -0.2542  -0.3472   0.0523 ];
%!
%! CKe = [ -0.0372  -0.0456  -0.0040   0.0962  -0.2059  -0.0571
%!          0.1999   0.2994   0.1335  -0.0251  -0.3108   0.2048 ];
%!
%! DKe = [  0.0629  -0.0022   0.0363
%!         -0.0228   0.0195   0.0600 ];
%!
%! RCONDe = [ 0.27949D-03  0.66679D-03  0.45677D-01  0.23433D-07  0.68495D-01  0.76854D-01 ].';
%!
%!assert (AK, AKe, 1e-4);
%!assert (BK, BKe, 1e-4);
%!assert (CK, CKe, 1e-4);
%!assert (DK, DKe, 1e-4);
