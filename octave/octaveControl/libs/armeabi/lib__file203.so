## Copyright (C) 2012   Lukas F. Reichlin
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
## @deftypefn{Function File} {[@var{Ms}, @var{ws}] =} sensitivity (@var{L})
## @deftypefnx{Function File} {[@var{Ms}, @var{ws}] =} sensitivity (@var{P}, @var{C})
## @deftypefnx{Function File} {[@var{Ms}, @var{ws}] =} sensitivity (@var{P}, @var{C1}, @var{C2}, @dots{})
## Return sensitivity margin @var{Ms}.
## The quantity @var{Ms} is simply the inverse of the shortest
## distance from the Nyquist curve to the critical point -1.
## Reasonable values of @var{Ms} are in the range from 1.3 to 2.
## @iftex
## @tex
## $$ M_s = ||S(j\\omega)||_{\\infty} $$
## @end tex
## @end iftex
## @ifnottex
##
## @example
## Ms = ||S(jw)||
##               inf
## @end example
##
## @end ifnottex
## If no output arguments are given, the critical distance 1/Ms
## is plotted on a Nyquist diagram.
## In contrast to gain and phase margin as computed by command
## @command{margin}, the sensitivity @var{Ms} is a more robust
## criterion to assess the stability of a feedback system.
##
## @strong{Inputs}
## @table @var
## @item L
## Open loop transfer function.
## @var{L} can be any type of LTI system, but it must be square.
## @item P
## Plant model.  Any type of LTI system.
## @item C
## Controller model.  Any type of LTI system.
## @item C1, C2, @dots{}
## If several controllers are specified, command @command{sensitivity}
## computes the sensitivity @var{Ms} for each of them in combination
## with plant @var{P}.
## @end table
##
## @strong{Outputs}
## @table @var
## @item Ms
## Sensitivity margin @var{Ms} as defined in [1].
## Scalar value.
## If several controllers are specified, @var{Ms} becomes
## a row vector with as many entries as controllers.
## @item ws
## The frequency [rad/s] corresponding to the sensitivity peak.
## Scalar value.
## If several controllers are specified, @var{ws} becomes
## a row vector with as many entries as controllers.
## @end table
##
## @strong{Algorithm}@*
## Uses SLICOT AB13DD by courtesy of
## @uref{http://www.slicot.org, NICONET e.V.}
## to calculate the infinity norm of the sensitivity function.
##
## @strong{References}@*
## [1] Astr@"om, K. and H@"agglund, T. (1995)
## PID Controllers:
## Theory, Design and Tuning,
## Second Edition.
## Instrument Society of America.
##
## @end deftypefn

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: August 2012
## Version: 0.1

function [ret, ws] = sensitivity (G, varargin)

  if (nargin == 0)
    print_usage ();
  elseif (nargin == 1)              # L := G
    L = G;
    I = eye (size (L));
    S = feedback (I, L);            # S = inv (I + L),  S = feedback (I, L*-I, "+")
    [Ms, ws] = norm (S, inf);
  else                              # P := G,  C := varargin
    L = cellfun (@(C) G*C, varargin, "uniformoutput", false);
    I = cellfun (@(L) eye (size (L)), L, "uniformoutput", false);
    S = cellfun (@feedback, I, L, "uniformoutput", false);
    [Ms, ws] = cellfun (@(S) norm (S, inf), S);
  endif

  if (nargout == 0)
    ## TODO: don't show entire Nyquist curve if critical distance becomes small on plot
    if (length (Ms) > 1)
      error ("sensitivity: plotting only works for a single controller");
    endif
    if (iscell (L))
      L = L{1};
    endif
    if (! issiso (L))
      error ("sensitivity: Nyquist plot requires SISO systems");
    endif

    [H, w] = __frequency_response__ (L, false, 0, "ext");
    H = H(:);
    re = real (H);
    im = imag (H);
    Hs = freqresp (L, ws);
    res = real (Hs);
    ims = imag (Hs);
    
    plot (re, im, "b", [-1, res], [0, ims], "r")
    axis ("equal")
    xlim (__axis_margin__ (xlim))
    ylim (__axis_margin__ (ylim))
    grid ("on")
    title (sprintf ("Sensitivity Ms = %g (at %g rad/s)", Ms, ws))
    xlabel ("Real Axis")
    ylabel ("Imaginary Axis")
  else
    ret = Ms;
  endif

endfunction
