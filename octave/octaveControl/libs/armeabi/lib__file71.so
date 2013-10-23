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
## @deftypefn{Function File} {[@var{Kr}, @var{info}] =} fwcfconred (@var{G}, @var{F}, @var{L}, @dots{})
## @deftypefnx{Function File} {[@var{Kr}, @var{info}] =} fwcfconred (@var{G}, @var{F}, @var{L}, @var{ncr}, @dots{})
## @deftypefnx{Function File} {[@var{Kr}, @var{info}] =} fwcfconred (@var{G}, @var{F}, @var{L}, @var{opt}, @dots{})
## @deftypefnx{Function File} {[@var{Kr}, @var{info}] =} fwcfconred (@var{G}, @var{F}, @var{L}, @var{ncr}, @var{opt}, @dots{})
##
## Reduction of state-feedback-observer based controller by frequency-weighted coprime factorization (FW CF). 
## Given a plant @var{G}, state feedback gain @var{F} and full observer gain @var{L},
## determine a reduced order controller @var{Kr} by using stability enforcing frequency weights.
##
## @strong{Inputs}
## @table @var
## @item G
## LTI model of the open-loop plant (A,B,C,D).
## It has m inputs, p outputs and n states.
## @item F
## Stabilizing state feedback matrix (m-by-n).
## @item L
## Stabilizing observer gain matrix (n-by-p).
## @item ncr
## The desired order of the resulting reduced order controller @var{Kr}.
## If not specified, @var{ncr} is chosen automatically according
## to the description of key @var{'order'}.
## @item @dots{}
## Optional pairs of keys and values.  @code{"key1", value1, "key2", value2}.
## @item opt
## Optional struct with keys as field names.
## Struct @var{opt} can be created directly or
## by command @command{options}.  @code{opt.key1 = value1, opt.key2 = value2}.
## @end table
##
## @strong{Outputs}
## @table @var
## @item Kr
## State-space model of reduced order controller.
## @item info
## Struct containing additional information.
## @table @var
## @item info.hsv
## The Hankel singular values of the extended system?!?.
## The @var{n} Hankel singular values are ordered decreasingly.
## @item info.ncr
## The order of the obtained reduced order controller @var{Kr}.
## @end table
## @end table
##
## @strong{Option Keys and Values}
## @table @var
## @item 'order', 'ncr'
## The desired order of the resulting reduced order controller @var{Kr}.
## If not specified, @var{ncr} is chosen automatically such that states with
## Hankel singular values @var{info.hsv} > @var{tol1} are retained.
##
## @item 'method'
## Order reduction approach to be used as follows:
## @table @var
## @item 'sr', 'b'
## Use the square-root Balance & Truncate method.
## @item 'bfsr', 'f'
## Use the balancing-free square-root Balance & Truncate method.  Default method.
## @end table
##
## @item 'cf'
## Specifies whether left or right coprime factorization is
## to be used as follows:
## @table @var
## @item 'left', 'l'
## Use left coprime factorization.
## @item 'right', 'r'
## Use right coprime factorization.  Default method.
## @end table
##
## @item 'feedback'
## Specifies whether @var{F} and @var{L} are fed back positively or negatively:
## @table @var
## @item '+'
## A+BK and A+LC are both Hurwitz matrices.
## @item '-'
## A-BK and A-LC are both Hurwitz matrices.  Default value.
## @end table
##
## @item 'tol1'
## If @var{'order'} is not specified, @var{tol1} contains the tolerance for
## determining the order of the reduced system.
## For model reduction, the recommended value of @var{tol1} is
## c*info.hsv(1), where c lies in the interval [0.00001, 0.001].
## Default value is n*eps*info.hsv(1).
## If @var{'order'} is specified, the value of @var{tol1} is ignored.
## @end table
##
## @strong{Algorithm}@*
## Uses SLICOT SB16CD by courtesy of
## @uref{http://www.slicot.org, NICONET e.V.}
## @end deftypefn

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: December 2011
## Version: 0.1

function [Kr, info] = fwcfconred (G, F, L, varargin)

  if (nargin < 3)
    print_usage ();
  endif

  if (! isa (G, "lti"))
    error ("fwcfconred: first argument must be an LTI system");
  endif

  if (! is_real_matrix (F))
    error ("fwcfconred: second argument must be a real matrix");
  endif
  
  if (! is_real_matrix (L))
    error ("fwcfconred: third argument must be a real matrix");
  endif

  if (nargin > 3)                                  # fwcfconred (G, F, L, ...)
    if (is_real_scalar (varargin{1}))              # fwcfconred (G, F, L, nr)
      varargin = horzcat (varargin(2:end), {"order"}, varargin(1));
    endif
    if (isstruct (varargin{1}))                    # fwcfconred (G, F, L, opt, ...), fwcfconred (G, F, L, nr, opt, ...)
      varargin = horzcat (__opt2cell__ (varargin{1}), varargin(2:end));
    endif
    ## order placed at the end such that nr from fwcfconred (G, F, L, nr, ...)
    ## and fwcfconred (G, F, L, nr, opt, ...) overrides possible nr's from
    ## key/value-pairs and inside opt struct (later keys override former keys,
    ## nr > key/value > opt)
  endif

  nkv = numel (varargin);                          # number of keys and values

  if (rem (nkv, 2))
    error ("fwcfconred: keys and values must come in pairs");
  endif

  [a, b, c, d, tsam] = ssdata (G);
  [p, m] = size (G);
  n = rows (a);
  [mf, nf] = size (F);
  [nl, pl] = size (L);
  dt = isdt (G);
  jobd = any (d(:));

  if (mf != m || nf != n)
    error ("fwcfconred: dimensions of state-feedback matrix (%dx%d) and plant (%dx%d, %d states) don't match", \
           mf, nf, p, m, n);
  endif

  if (nl != n || pl != p)
    error ("fwcfconred: dimensions of observer matrix (%dx%d) and plant (%dx%d, %d states) don't match", \
           nl, pl, p, m, n);
  endif

  ## default arguments
  tol1 = 0.0;
  jobcf = 1;
  jobmr = 1;                                       # balancing-free BTA
  ordsel = 1;
  ncr = 0;
  negfb = true;                                    # A-BK, A-LC Hurwitz


  ## handle keys and values
  for k = 1 : 2 : nkv
    key = lower (varargin{k});
    val = varargin{k+1};
    switch (key)
      case {"order", "ncr", "nr"}
        [ncr, ordsel] = __modred_check_order__ (val, n);

      case {"tol1", "tol"}
        tol1 = __modred_check_tol__ (val, "tol1");

      case "cf"
        switch (lower (val(1)))
          case "l"
            jobcf = 0;
          case "r"
            jobcf = 1;
          otherwise
            error ("cfconred: '%s' is an invalid coprime factorization", val);
        endswitch

      case "method"                                # approximation method
        switch (tolower (val))
          case {"sr-bta", "b", "sr"}               # 'B':  use the square-root Balance & Truncate method
            jobmr = 0;
          case {"bfsr-bta", "f", "bfsr"}           # 'F':  use the balancing-free square-root Balance & Truncate method
            jobmr = 1;
          otherwise
            error ("cfconred: '%s' is an invalid approach", val);
        endswitch

      case "feedback"
        negfb = __conred_check_feedback_sign__ (val);

      otherwise
        warning ("fwcfconred: invalid property name '%s' ignored", key);
    endswitch
  endfor


  ## A - B*F --> A + B*F  ;    A - L*C --> A + L*C
  if (negfb)
    F = -F;
    L = -L;
  endif

  ## perform model order reduction
  [acr, bcr, ccr, ncr, hsv] = __sl_sb16cd__ (a, b, c, d, dt, ncr, ordsel, jobd, jobmr, \
                                             F, L, jobcf, tol1);

  ## assemble reduced order controller
  Kr = ss (acr, bcr, ccr, [], tsam);

  ## assemble info struct  
  info = struct ("ncr", ncr, "hsv", hsv);

endfunction


%!shared Mo, Me, Info, HSVe
%! A =  [       0    1.0000         0         0         0         0         0        0
%!              0         0         0         0         0         0         0        0
%!              0         0   -0.0150    0.7650         0         0         0        0
%!              0         0   -0.7650   -0.0150         0         0         0        0
%!              0         0         0         0   -0.0280    1.4100         0        0
%!              0         0         0         0   -1.4100   -0.0280         0        0
%!              0         0         0         0         0         0   -0.0400    1.850
%!              0         0         0         0         0         0   -1.8500   -0.040 ];
%!
%! B =  [  0.0260
%!        -0.2510
%!         0.0330
%!        -0.8860
%!        -4.0170
%!         0.1450
%!         3.6040
%!         0.2800 ];
%!
%! C =  [  -.996 -.105 0.261 .009 -.001 -.043 0.002 -0.026 ];
%!
%! D =  [  0.0 ];
%!
%! G = ss (A, B, C, D);  % "scaled", false
%!
%! F =  [  4.472135954999638e-002    6.610515358414598e-001    4.698598960657579e-003  3.601363251422058e-001    1.032530880771415e-001   -3.754055214487997e-002  -4.268536964759344e-002    3.287284547842979e-002 ];
%!
%! L =  [  4.108939884667451e-001
%!         8.684600000000012e-002
%!         3.852317308197148e-004
%!        -3.619366874815911e-003
%!        -8.803722876359955e-003
%!         8.420521094001852e-003
%!         1.234944428038507e-003
%!         4.263205617645322e-003 ];
%!
%! [Kr, Info] = fwcfconred (G, F, L, 2, "method", "bfsr", "cf", "right", "feedback", "+");
%! [Ao, Bo, Co, Do] = ssdata (Kr);
%!
%! Ae = [  -0.4334   0.4884
%!         -0.1950  -0.1093 ];
%!
%! Be = [  -0.4231
%!         -0.1785 ];
%!
%! Ce = [  -0.0326  -0.2307 ];
%!
%! De = [  0.0000 ];
%!
%! HSVe = [  3.3073   0.7274   0.1124   0.0784   0.0242   0.0182   0.0101   0.0094 ].';
%!
%! Mo = [Ao, Bo; Co, Do];
%! Me = [Ae, Be; Ce, De];
%!
%!assert (Mo, Me, 1e-4);
%!assert (Info.hsv, HSVe, 1e-4);
