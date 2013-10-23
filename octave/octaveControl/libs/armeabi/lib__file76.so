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
## @deftypefn{Function File} {[@var{Gr}, @var{info}] =} hnamodred (@var{G}, @dots{})
## @deftypefnx{Function File} {[@var{Gr}, @var{info}] =} hnamodred (@var{G}, @var{nr}, @dots{})
## @deftypefnx{Function File} {[@var{Gr}, @var{info}] =} hnamodred (@var{G}, @var{opt}, @dots{})
## @deftypefnx{Function File} {[@var{Gr}, @var{info}] =} hnamodred (@var{G}, @var{nr}, @var{opt}, @dots{})
##
## Model order reduction by frequency weighted optimal Hankel-norm (HNA) method.
## The aim of model reduction is to find an LTI system @var{Gr} of order
## @var{nr} (nr < n) such that the input-output behaviour of @var{Gr}
## approximates the one from original system @var{G}.
##
## HNA is an absolute error method which tries to minimize
## @iftex
## @tex
## $$ || G - G_r ||_H = min $$
## $$ || V \\ (G - G_r) \\ W ||_H = min $$
## @end tex
## @end iftex
## @ifnottex
## @example
## ||G-Gr||  = min
##         H
##
## ||V (G-Gr) W||  = min
##               H
## @end example
## @end ifnottex
## where @var{V} and @var{W} denote output and input weightings.
##
##
## @strong{Inputs}
## @table @var
## @item G
## LTI model to be reduced.
## @item nr
## The desired order of the resulting reduced order system @var{Gr}.
## If not specified, @var{nr} is chosen automatically according
## to the description of key @var{"order"}.
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
## @item Gr
## Reduced order state-space model.
## @item info
## Struct containing additional information.
## @table @var
## @item info.n
## The order of the original system @var{G}.
## @item info.ns
## The order of the @var{alpha}-stable subsystem of the original system @var{G}.
## @item info.hsv
## The Hankel singular values corresponding to the projection @code{op(V)*G1*op(W)},
## where G1 denotes the @var{alpha}-stable part of the original system @var{G}. 
## The @var{ns} Hankel singular values are ordered decreasingly.
## @item info.nu
## The order of the @var{alpha}-unstable subsystem of both the original
## system @var{G} and the reduced-order system @var{Gr}.
## @item info.nr
## The order of the obtained reduced order system @var{Gr}.
## @end table
## @end table
##
##
## @strong{Option Keys and Values}
## @table @var
## @item 'order', 'nr'
## The desired order of the resulting reduced order system @var{Gr}.
## If not specified, @var{nr} is the sum of @var{info.nu} and the number of
## Hankel singular values greater than @code{max(tol1, ns*eps*info.hsv(1)};
##
## @item 'method'
## Specifies the computational approach to be used.
## Valid values corresponding to this key are:
## @table @var
## @item 'descriptor'
## Use the inverse free descriptor system approach.
## @item 'standard'
## Use the inversion based standard approach.
## @item 'auto'
## Switch automatically to the inverse free
## descriptor approach in case of badly conditioned
## feedthrough matrices in V or W.  Default method.
## @end table
##
##
## @item 'left', 'v'
## LTI model of the left/output frequency weighting.
## The weighting must be antistable.
## @iftex
## @math{|| V \\ (G-G_r) \\dots ||_H = min}
## @end iftex
## @ifnottex
## @example
## || V (G-Gr) . ||  = min
##                 H
## @end example
## @end ifnottex
##
## @item 'right', 'w'
## LTI model of the right/input frequency weighting.
## The weighting must be antistable.
## @iftex
## @math{|| \\dots (G-G_r) \\ W ||_H = min}
## @end iftex
## @ifnottex
## @example
## || . (G-Gr) W ||  = min
##                 H
## @end example
## @end ifnottex
##
##
## @item 'left-inv', 'inv-v'
## LTI model of the left/output frequency weighting.
## The weighting must have only antistable zeros.
## @iftex
## @math{|| inv(V) \\ (G-G_r) \\dots ||_H = min}
## @end iftex
## @ifnottex
## @example
## || inv(V) (G-Gr) . ||  = min
##                      H
## @end example
## @end ifnottex
##
## @item 'right-inv', 'inv-w'
## LTI model of the right/input frequency weighting.
## The weighting must have only antistable zeros.
## @iftex
## @math{|| \\dots (G-G_r) \\ inv(W) ||_H = min}
## @end iftex
## @ifnottex
## @example
## || . (G-Gr) inv(W) ||  = min
##                      H
## @end example
## @end ifnottex
##
##
## @item 'left-conj', 'conj-v'
## LTI model of the left/output frequency weighting.
## The weighting must be stable.
## @iftex
## @math{|| conj(V) \\ (G-G_r) \\dots ||_H = min}
## @end iftex
## @ifnottex
## @example
## || V (G-Gr) . ||  = min
##                 H
## @end example
## @end ifnottex
##
## @item 'right-conj', 'conj-w'
## LTI model of the right/input frequency weighting.
## The weighting must be stable.
## @iftex
## @math{|| \\dots (G-G_r) \\ conj(W) ||_H = min}
## @end iftex
## @ifnottex
## @example
## || . (G-Gr) W ||  = min
##                 H
## @end example
## @end ifnottex
##
##
## @item 'left-conj-inv', 'conj-inv-v'
## LTI model of the left/output frequency weighting.
## The weighting must be minimum-phase.
## @iftex
## @math{|| conj(inv(V)) \\ (G-G_r) \\dots ||_H = min}
## @end iftex
## @ifnottex
## @example
## || V (G-Gr) . ||  = min
##                 H
## @end example
## @end ifnottex
##
## @item 'right-conj-inv', 'conj-inv-w'
## LTI model of the right/input frequency weighting.
## The weighting must be minimum-phase.
## @iftex
## @math{|| \\dots (G-G_r) \\ conj(inv(W)) ||_H = min}
## @end iftex
## @ifnottex
## @example
## || . (G-Gr) W ||  = min
##                 H
## @end example
## @end ifnottex
##
##
## @item 'alpha'
## Specifies the ALPHA-stability boundary for the eigenvalues
## of the state dynamics matrix @var{G.A}.  For a continuous-time
## system, ALPHA <= 0 is the boundary value for
## the real parts of eigenvalues, while for a discrete-time
## system, 0 <= ALPHA <= 1 represents the
## boundary value for the moduli of eigenvalues.
## The ALPHA-stability domain does not include the boundary.
## Default value is 0 for continuous-time systems and
## 1 for discrete-time systems.
##
## @item 'tol1'
## If @var{'order'} is not specified, @var{tol1} contains the tolerance for
## determining the order of the reduced model.
## For model reduction, the recommended value of @var{tol1} is
## c*info.hsv(1), where c lies in the interval [0.00001, 0.001].
## @var{tol1} < 1.
## If @var{'order'} is specified, the value of @var{tol1} is ignored.
##
## @item 'tol2'
## The tolerance for determining the order of a minimal
## realization of the ALPHA-stable part of the given
## model.  @var{tol2} <= @var{tol1} < 1.
## If not specified, ns*eps*info.hsv(1) is chosen.
##
## @item 'equil', 'scale'
## Boolean indicating whether equilibration (scaling) should be
## performed on system @var{G} prior to order reduction.
## Default value is true if @code{G.scaled == false} and
## false if @code{G.scaled == true}.
## Note that for @acronym{MIMO} models, proper scaling of both inputs and outputs
## is of utmost importance.  The input and output scaling can @strong{not}
## be done by the equilibration option or the @command{prescale} command
## because these functions perform state transformations only.
## Furthermore, signals should not be scaled simply to a certain range.
## For all inputs (or outputs), a certain change should be of the same
## importance for the model.
## @end table
##
##
## Approximation Properties:
## @itemize @bullet
## @item
## Guaranteed stability of reduced models
## @item
## Lower guaranteed error bound
## @item
## Guaranteed a priori error bound
## @iftex
## @tex
## $$ \\sigma_{r+1} \\leq || (G-G_r) ||_{\\infty} \\leq 2 \\sum_{j=r+1}^{n} \\sigma_j $$
## @end tex
## @end iftex
## @end itemize
##
## @strong{Algorithm}@*
## Uses SLICOT AB09JD by courtesy of
## @uref{http://www.slicot.org, NICONET e.V.}
## @end deftypefn

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: October 2011
## Version: 0.1

function [Gr, info] = hnamodred (G, varargin)

  if (nargin == 0)
    print_usage ();
  endif
  
  if (! isa (G, "lti"))
    error ("hnamodred: first argument must be an LTI system");
  endif

  if (nargin > 1)                                  # hnamodred (G, ...)
    if (is_real_scalar (varargin{1}))              # hnamodred (G, nr)
      varargin = horzcat (varargin(2:end), {"order"}, varargin(1));
    endif
    if (isstruct (varargin{1}))                    # hnamodred (G, opt, ...), hnamodred (G, nr, opt, ...)
      varargin = horzcat (__opt2cell__ (varargin{1}), varargin(2:end));
    endif
    ## order placed at the end such that nr from hnamodred (G, nr, ...)
    ## and hnamodred (G, nr, opt, ...) overrides possible nr's from
    ## key/value-pairs and inside opt struct (later keys override former keys,
    ## nr > key/value > opt)
  endif

  nkv = numel (varargin);                          # number of keys and values

  if (rem (nkv, 2))
    error ("hnamodred: keys and values must come in pairs");
  endif

  [a, b, c, d, tsam, scaled] = ssdata (G);
  [p, m] = size (G);
  dt = isdt (G);
  
  ## default arguments
  alpha = __modred_default_alpha__ (dt);
  av = bv = cv = dv = [];
  jobv = 0;
  aw = bw = cw = dw = [];
  jobw = 0;
  jobinv = 2;
  tol1 = 0; 
  tol2 = 0;
  ordsel = 1;
  nr = 0;

  ## handle keys and values
  for k = 1 : 2 : nkv
    key = lower (varargin{k});
    val = varargin{k+1};
    switch (key)
      case {"left", "v", "wo"}
        [av, bv, cv, dv, jobv] = __modred_check_weight__ (val, dt, p, p);
        ## TODO: correct error messages for non-square weights

      case {"right", "w", "wi"}
        [aw, bw, cw, dw, jobw] = __modred_check_weight__ (val, dt, m, m);

      case {"left-inv", "inv-v"}
        [av, bv, cv, dv] = __modred_check_weight__ (val, dt, p, p);
        jobv = 2;

      case {"right-inv", "inv-w"}
        [aw, bw, cw, dw] = __modred_check_weight__ (val, dt, m, m);
        jobv = 2

      case {"left-conj", "conj-v"}
        [av, bv, cv, dv] = __modred_check_weight__ (val, dt, p, p);
        jobv = 3;

      case {"right-conj", "conj-w"}
        [aw, bw, cw, dw] = __modred_check_weight__ (val, dt, m, m);
        jobv = 3

      case {"left-conj-inv", "conj-inv-v"}
        [av, bv, cv, dv] = __modred_check_weight__ (val, dt, p, p);
        jobv = 4;

      case {"right-conj-inv", "conj-inv-w"}
        [aw, bw, cw, dw] = __modred_check_weight__ (val, dt, m, m);
        jobv = 4

      case {"order", "nr"}
        [nr, ordsel] = __modred_check_order__ (val, rows (a));

      case "tol1"
        tol1 = __modred_check_tol__ (val, "tol1");

      case "tol2"
        tol2 = __modred_check_tol__ (val, "tol2");

      case "alpha"
        alpha = __modred_check_alpha__ (val, dt);

      case "method"
        switch (tolower (val(1)))
          case {"d", "n"}      # "descriptor"
            jobinv = 0;
          case {"s", "i"}      # "standard"
            jobinv = 1;
          case "a"             # {"auto", "automatic"}
            jobinv = 2;
          otherwise
            error ("hnamodred: invalid computational approach");
        endswitch

      case {"equil", "equilibrate", "equilibration", "scale", "scaling"}
        scaled = __modred_check_equil__ (val);

      otherwise
        warning ("hnamodred: invalid property name '%s' ignored", key);
    endswitch
  endfor

  
  ## perform model order reduction
  [ar, br, cr, dr, nr, hsv, ns] = __sl_ab09jd__ (a, b, c, d, dt, scaled, nr, ordsel, alpha, \
                                            jobv, av, bv, cv, dv, \
                                            jobw, aw, bw, cw, dw, \
                                            jobinv, tol1, tol2);

  ## assemble reduced order model
  Gr = ss (ar, br, cr, dr, tsam);

  ## assemble info struct  
  n = rows (a);
  nu = n - ns;
  info = struct ("n", n, "ns", ns, "hsv", hsv, "nu", nu, "nr", nr);

endfunction


%!shared Mo, Me, Info, HSVe
%! A =  [ -3.8637   -7.4641   -9.1416   -7.4641   -3.8637   -1.0000
%!         1.0000,         0         0         0         0         0
%!              0    1.0000         0         0         0         0
%!              0         0    1.0000         0         0         0
%!              0         0         0    1.0000         0         0
%!              0         0         0         0    1.0000         0 ];
%!
%! B =  [       1
%!              0
%!              0
%!              0
%!              0
%!              0 ];
%!
%! C =  [       0         0         0         0         0         1 ];
%!
%! D =  [       0 ];
%!
%! G = ss (A, B, C, D);  # "scaled", false
%!
%! AV = [  0.2000   -1.0000
%!         1.0000         0 ];
%!
%! BV = [       1
%!              0 ];
%!
%! CV = [ -1.8000         0 ];
%!
%! DV = [       1 ];
%!
%! V = ss (AV, BV, CV, DV);
%!
%! [Gr, Info] = hnamodred (G, "left", V, "tol1", 1e-1, "tol2", 1e-14);
%! [Ao, Bo, Co, Do] = ssdata (Gr);
%!
%! Ae = [ -0.2391   0.3072   1.1630   1.1967
%!        -2.9709  -0.2391   2.6270   3.1027
%!         0.0000   0.0000  -0.5137  -1.2842
%!         0.0000   0.0000   0.1519  -0.5137 ];
%!
%! Be = [ -1.0497
%!        -3.7052
%!         0.8223
%!         0.7435 ];
%!
%! Ce = [ -0.4466   0.0143  -0.4780  -0.2013 ];
%!
%! De = [  0.0219 ];
%!
%! HSVe = [  2.6790   2.1589   0.8424   0.1929   0.0219   0.0011 ].';
%!
%! Mo = [Ao, Bo; Co, Do];
%! Me = [Ae, Be; Ce, De];
%!
%!assert (Mo, Me, 1e-4);
%!assert (Info.hsv, HSVe, 1e-4);
