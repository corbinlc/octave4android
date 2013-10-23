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
## @deftypefn{Function File} {[@var{sys}, @var{n}] =} fitfrd (@var{dat}, @var{n})
## @deftypefnx{Function File} {[@var{sys}, @var{n}] =} fitfrd (@var{dat}, @var{n}, @var{flag})
## Fit frequency response data with a state-space system.
## If requested, the returned system is stable and minimum-phase.
##
## @strong{Inputs}
## @table @var
## @item dat
## LTI model containing frequency response data of a SISO system.
## @item n
## The desired order of the system to be fitted.  @code{n <= length(dat.w)}.
## @item flag
## The flag controls whether the returned system is stable and minimum-phase.
## @table @var
## @item 0
## The system zeros and poles are not constrained.  Default value.
## @item 1
## The system zeros and poles will have negative real parts in the
## continuous-time case, or moduli less than 1 in the discrete-time case.
## @end table
## @end table
##
## @strong{Outputs}
## @table @var
## @item sys
## State-space model of order @var{n}, fitted to frequency response data @var{dat}.
## @item n
## The order of the obtained system.  The value of @var{n}
## could only be modified if inputs @code{n > 0} and @code{flag = 1}.
## @end table
##
## @strong{Algorithm}@*
## Uses SLICOT SB10YD by courtesy of
## @uref{http://www.slicot.org, NICONET e.V.}
## @end deftypefn

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: October 2011
## Version: 0.1

function [sys, n] = fitfrd (dat, n, flag = 0)

  if (nargin == 0 || nargin > 3)
    print_usage ();
  endif
  
  if (! isa (dat, "frd"))
    dat = frd (dat);
  endif

  if (! issiso (dat))
    error ("fitfrd: require SISO system");
  endif

  if (! issample (n, 0) || n != round (n))
    error ("fitfrd: second argument must be an integer >= 0");
  endif

  [H, w, tsam] = frdata (dat, "vector");
  dt = isdt (dat);
  
  if (n > length (w))
    error ("fitfrd: require n <= length (dat.w)");
  endif
  
  [a, b, c, d, n] = __sl_sb10yd__ (real (H), imag (H), w, n, dt, logical (flag));
  
  sys = ss (a, b, c, d, tsam);

endfunction


%!shared Yo, Ye
%! SYS = ss (-1, 1, 1, 0);
%! T = 0:0.1:50;
%! Ye = step (SYS, T);
%! W = logspace (-2, 2, 100);
%! FR = frd (SYS, W);
%! N = 1;
%! SYSID = fitfrd (FR, N, 1);
%! Yo = step (SYSID, T);
%!assert (Yo, Ye, 1e-2);