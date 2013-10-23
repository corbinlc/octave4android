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
## @deftypefn {Function File} {[@var{scaledsys}, @var{info}] =} prescale (@var{sys})
## Scale state-space model.  The scaled model @var{scaledsys} is equivalent to
## @var{sys}, but the state vector is scaled by diagonal transformation matrices
## in order to increase the accuracy of subsequent numerical computations.
## Frequency response commands perform automatic scaling unless model property
## @var{scaled} is set to @var{true}.
##
## @strong{Inputs}
## @table @var
## @item sys
## @acronym{LTI} model.
## @end table
##
## @strong{Outputs}
## @table @var
## @item scaledsys
## Scaled state-space model.
## @item info
## Structure containing additional information.
## @item info.SL
## Left scaling factors.  @code{Tl = diag (info.SL)}.
## @item info.SR
## Right scaling factors.  @code{Tr = diag (info.SR)}.
## @end table
##
## @strong{Equations}
## @example
## @group
## Es = Tl * E * Tr
## As = Tl * A * Tr
## Bs = Tl * B
## Cs =      C * Tr
## Ds =      D
## @end group
## @end example
##
## For proper state-space models, @var{Tl} and @var{Tr} are inverse of each other.
##
## @strong{Algorithm}@*
## Uses SLICOT TB01ID and TG01AD by courtesy of
## @uref{http://www.slicot.org, NICONET e.V.}.
## @end deftypefn

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: June 2011
## Version: 0.2

function [retsys, varargout] = prescale (sys)

  if (nargin != 1)
    print_usage ();
  endif

  if (! isa (sys, "ss"))
    warning ("prescale: system not in state-space form");
    sys = ss (sys);
  endif

  [retsys, lscale, rscale] = __prescale__ (sys);
  
  if (nargout > 1)
    varargout{1} = struct ("SL", lscale, "SR", rscale);
  endif

endfunction


## Scaling of state-space models, direct access to TB01ID
%!shared Ao, Bo, Co, SCALEo, MAXREDo, Ae, Be, Ce, SCALEe, MAXREDe
%! A = [          0.0  1.0000e+000          0.0          0.0          0.0
%!       -1.5800e+006 -1.2570e+003          0.0          0.0          0.0
%!        3.5410e+014          0.0 -1.4340e+003          0.0 -5.3300e+011
%!                0.0          0.0          0.0          0.0  1.0000e+000
%!                0.0          0.0          0.0 -1.8630e+004 -1.4820e+000 ];
%!
%! B = [          0.0          0.0
%!        1.1030e+002          0.0
%!                0.0          0.0
%!                0.0          0.0
%!                0.0  8.3330e-003 ];
%!
%! C = [  1.0000e+000          0.0          0.0          0.0          0.0
%!                0.0          0.0  1.0000e+000          0.0          0.0
%!                0.0          0.0          0.0  1.0000e+000          0.0
%!        6.6640e-001          0.0 -6.2000e-013          0.0          0.0
%!                0.0          0.0 -1.0000e-003  1.8960e+006  1.5080e+002 ];
%!
%! MAXRED = 0.0;
%!
%! [Ao, Bo, Co, MAXREDo, SCALEo] = __sl_tb01id__ (A, B, C, MAXRED);
%!
%! Ae = [    0.0000000D+00  0.1000000D+05  0.0000000D+00  0.0000000D+00  0.0000000D+00
%!          -0.1580000D+03 -0.1257000D+04  0.0000000D+00  0.0000000D+00  0.0000000D+00
%!           0.3541000D+05  0.0000000D+00 -0.1434000D+04  0.0000000D+00 -0.5330000D+03
%!           0.0000000D+00  0.0000000D+00  0.0000000D+00  0.0000000D+00  0.1000000D+03
%!           0.0000000D+00  0.0000000D+00  0.0000000D+00 -0.1863000D+03 -0.1482000D+01 ];
%!
%! Be = [    0.0000000D+00  0.0000000D+00
%!           0.1103000D+04  0.0000000D+00
%!           0.0000000D+00  0.0000000D+00
%!           0.0000000D+00  0.0000000D+00
%!           0.0000000D+00  0.8333000D+02 ];
%!
%! Ce = [    0.1000000D-04  0.0000000D+00  0.0000000D+00  0.0000000D+00  0.0000000D+00
%!           0.0000000D+00  0.0000000D+00  0.1000000D+06  0.0000000D+00  0.0000000D+00
%!           0.0000000D+00  0.0000000D+00  0.0000000D+00  0.1000000D-05  0.0000000D+00
%!           0.6664000D-05  0.0000000D+00 -0.6200000D-07  0.0000000D+00  0.0000000D+00
%!           0.0000000D+00  0.0000000D+00 -0.1000000D+03  0.1896000D+01  0.1508000D-01 ];
%!
%! SCALEe = [0.1000000D-04  0.1000000D+00  0.1000000D+06  0.1000000D-05  0.1000000D-03 ];
%!
%! MAXREDe = 0.3488E+10;
%!
%!assert (Ao, Ae, 1e-4);
%!assert (Bo, Be, 1e-4);
%!assert (Co, Ce, 1e-4);
%!assert (MAXREDo, MAXREDe, 1e6);
%!assert (SCALEo, SCALEe.', 1e-4);


## Scaling of descriptor state-space models, direct access to TG01AD
%!shared Ao, Eo, Bo, Co, LSCALEo, RSCALEo, Ae, Ee, Be, Ce, LSCALEe, RSCALEe
%! A = [   -1         0         0    0.003
%!          0         0    0.1000    0.02
%!        100        10         0    0.4
%!          0         0         0    0.0];
%!
%! E = [    1       0.2         0    0.0
%!          0         1         0    0.01
%!        300        90         6    0.3
%!          0         0        20    0.0];
%!
%! B = [   10         0
%!          0         0
%!          0      1000
%!      10000     10000];
%!
%! C = [ -0.1      0.0    0.001    0.0
%!        0.0      0.01  -0.001    0.0001];
%!
%! TRESH = 0.0;
%!
%! [Ao, Eo, Bo, Co, LSCALEo, RSCALEo] = __sl_tg01ad__ (A, E, B, C, TRESH);
%!
%! Ae = [ -1.0000    0.0000    0.0000    0.3000
%!         0.0000    0.0000    1.0000    2.0000
%!         1.0000    0.1000    0.0000    0.4000
%!         0.0000    0.0000    0.0000    0.0000];
%!
%! Ee = [  1.0000    0.2000    0.0000    0.0000
%!         0.0000    1.0000    0.0000    1.0000
%!         3.0000    0.9000    0.6000    0.3000
%!         0.0000    0.0000    0.2000    0.0000 ];
%!
%! Be = [100.0000    0.0000
%!         0.0000    0.0000
%!         0.0000  100.0000
%!       100.0000  100.0000 ];
%!
%! Ce = [ -0.0100    0.0000    0.0010    0.0000
%!         0.0000    0.0010   -0.0010    0.0010];
%!
%! LSCALEe = [ 10.0000   10.0000    0.1000    0.0100 ];
%!
%! RSCALEe = [  0.1000    0.1000    1.0000   10.0000 ];
%!
%!assert (Ao, Ae, 1e-4);
%!assert (Eo, Ee, 1e-4);
%!assert (Bo, Be, 1e-4);
%!assert (Co, Ce, 1e-4);
%!assert (LSCALEo, LSCALEe.', 1e-4);
%!assert (RSCALEo, RSCALEe.', 1e-4);


## Scaling of state-space models, user function
%!shared Ao, Bo, Co, INFOo, Ae, Be, Ce, SCALEe
%! A = [          0.0  1.0000e+000          0.0          0.0          0.0
%!       -1.5800e+006 -1.2570e+003          0.0          0.0          0.0
%!        3.5410e+014          0.0 -1.4340e+003          0.0 -5.3300e+011
%!                0.0          0.0          0.0          0.0  1.0000e+000
%!                0.0          0.0          0.0 -1.8630e+004 -1.4820e+000 ];
%!
%! B = [          0.0          0.0
%!        1.1030e+002          0.0
%!                0.0          0.0
%!                0.0          0.0
%!                0.0  8.3330e-003 ];
%!
%! C = [  1.0000e+000          0.0          0.0          0.0          0.0
%!                0.0          0.0  1.0000e+000          0.0          0.0
%!                0.0          0.0          0.0  1.0000e+000          0.0
%!        6.6640e-001          0.0 -6.2000e-013          0.0          0.0
%!                0.0          0.0 -1.0000e-003  1.8960e+006  1.5080e+002 ];
%!
%! SYS = ss (A, B, C);
%!
%! [SYSo, INFOo] = prescale (SYS);
%!
%! [Ao, Bo, Co] = ssdata (SYSo);
%!
%! Ae = [    0.0000000D+00  0.1000000D+05  0.0000000D+00  0.0000000D+00  0.0000000D+00
%!          -0.1580000D+03 -0.1257000D+04  0.0000000D+00  0.0000000D+00  0.0000000D+00
%!           0.3541000D+05  0.0000000D+00 -0.1434000D+04  0.0000000D+00 -0.5330000D+03
%!           0.0000000D+00  0.0000000D+00  0.0000000D+00  0.0000000D+00  0.1000000D+03
%!           0.0000000D+00  0.0000000D+00  0.0000000D+00 -0.1863000D+03 -0.1482000D+01 ];
%!
%! Be = [    0.0000000D+00  0.0000000D+00
%!           0.1103000D+04  0.0000000D+00
%!           0.0000000D+00  0.0000000D+00
%!           0.0000000D+00  0.0000000D+00
%!           0.0000000D+00  0.8333000D+02 ];
%!
%! Ce = [    0.1000000D-04  0.0000000D+00  0.0000000D+00  0.0000000D+00  0.0000000D+00
%!           0.0000000D+00  0.0000000D+00  0.1000000D+06  0.0000000D+00  0.0000000D+00
%!           0.0000000D+00  0.0000000D+00  0.0000000D+00  0.1000000D-05  0.0000000D+00
%!           0.6664000D-05  0.0000000D+00 -0.6200000D-07  0.0000000D+00  0.0000000D+00
%!           0.0000000D+00  0.0000000D+00 -0.1000000D+03  0.1896000D+01  0.1508000D-01 ];
%!
%! SCALEe = [0.1000000D-04  0.1000000D+00  0.1000000D+06  0.1000000D-05  0.1000000D-03 ];
%!
%!assert (Ao, Ae, 1e-4);
%!assert (Bo, Be, 1e-4);
%!assert (Co, Ce, 1e-4);
%!assert (INFOo.SL.^-1, SCALEe.', 1e-4);
%!assert (INFOo.SR, SCALEe.', 1e-4);


## Scaling of descriptor state-space models, user function
%!shared Ao, Eo, Bo, Co, INFOo, Ae, Ee, Be, Ce, LSCALEe, RSCALEe
%! A = [   -1         0         0    0.003
%!          0         0    0.1000    0.02
%!        100        10         0    0.4
%!          0         0         0    0.0];
%!
%! E = [    1       0.2         0    0.0
%!          0         1         0    0.01
%!        300        90         6    0.3
%!          0         0        20    0.0];
%!
%! B = [   10         0
%!          0         0
%!          0      1000
%!      10000     10000];
%!
%! C = [ -0.1      0.0    0.001    0.0
%!        0.0      0.01  -0.001    0.0001];
%!
%! SYS = dss (A, B, C, [], E);
%!
%! [SYSo, INFOo] = prescale (SYS);
%!
%! [Ao, Bo, Co, ~, Eo] = dssdata (SYSo); 
%!
%! Ae = [ -1.0000    0.0000    0.0000    0.3000
%!         0.0000    0.0000    1.0000    2.0000
%!         1.0000    0.1000    0.0000    0.4000
%!         0.0000    0.0000    0.0000    0.0000];
%!
%! Ee = [  1.0000    0.2000    0.0000    0.0000
%!         0.0000    1.0000    0.0000    1.0000
%!         3.0000    0.9000    0.6000    0.3000
%!         0.0000    0.0000    0.2000    0.0000 ];
%!
%! Be = [100.0000    0.0000
%!         0.0000    0.0000
%!         0.0000  100.0000
%!       100.0000  100.0000 ];
%!
%! Ce = [ -0.0100    0.0000    0.0010    0.0000
%!         0.0000    0.0010   -0.0010    0.0010];
%!
%! LSCALEe = [ 10.0000   10.0000    0.1000    0.0100 ];
%!
%! RSCALEe = [  0.1000    0.1000    1.0000   10.0000 ];
%!
%!assert (Ao, Ae, 1e-4);
%!assert (Eo, Ee, 1e-4);
%!assert (Bo, Be, 1e-4);
%!assert (Co, Ce, 1e-4);
%!assert (INFOo.SL, LSCALEe.', 1e-4);
%!assert (INFOo.SR, RSCALEe.', 1e-4);
