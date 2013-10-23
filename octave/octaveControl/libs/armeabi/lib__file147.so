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
## @deftypefn {Function File} {@var{gain} =} norm (@var{sys}, @var{2})
## @deftypefnx {Function File} {[@var{gain}, @var{wpeak}] =} norm (@var{sys}, @var{inf})
## @deftypefnx {Function File} {[@var{gain}, @var{wpeak}] =} norm (@var{sys}, @var{inf}, @var{tol})
## Return H-2 or L-inf norm of LTI model.
##
## @strong{Algorithm}@*
## Uses SLICOT AB13BD and AB13DD by courtesy of
## @uref{http://www.slicot.org, NICONET e.V.}
## @end deftypefn

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: November 2009
## Version: 0.5

function [gain, varargout] = norm (sys, ntype = "2", tol = 0.01)

  if (nargin > 3)                     # norm () is caught by built-in function
    print_usage ();
  endif

  if (is_real_scalar (ntype))
    if (ntype == 2)
      ntype = "2";
    elseif (isinf (ntype))
      ntype = "inf";
    else
      error ("lti: norm: invalid norm type");
    endif
  elseif (ischar (ntype))
    ntype = lower (ntype);
  else
    error ("lti: norm: invalid norm type");
  endif

  switch (ntype)
    case "2"
      gain = h2norm (sys);

    case "inf"
      [gain, varargout{1}] = linfnorm (sys, tol);

    otherwise
      error ("lti: norm: invalid norm type");
  endswitch

endfunction


function gain = h2norm (sys)

  if (isstable (sys))
    [a, b, c, d] = ssdata (sys);
    discrete = ! isct (sys);
    if (! discrete && any (d(:)))     # continuous and non-zero feedthrough
      gain = inf;
    else
      gain = __sl_ab13bd__ (a, b, c, d, discrete);
    endif
  else
    gain = inf;
  endif

endfunction


function [gain, wpeak] = linfnorm (sys, tol = 0.01)

  [a, b, c, d, e, tsam, scaled] = dssdata (sys, []);
  discrete = ! isct (sys);
  tol = max (tol, 100*eps);
  
  if (isempty (e))
    [fpeak, gpeak] = __sl_ab13dd__ (a, a, b, c, d, discrete, false, tol, scaled);  # TODO: avoid dummy argument
  else
    if (rcond (e) < eps)
      gain = inf;
      wpeak = inf;
      return;
    else
      [fpeak, gpeak] = __sl_ab13dd__ (a, e, b, c, d, discrete, true, tol, scaled);
    endif
  endif
  
  if (fpeak(2) > 0)
    if (discrete)
      wpeak = fpeak(1) / abs (tsam);  # tsam could be -1
    else
      wpeak = fpeak(1);
    endif
  else
    wpeak = inf;
  endif
  
  if (gpeak(2) > 0)
    gain = gpeak(1);
  else
    gain = inf;
  endif

endfunction


## norm ct
%!shared H2, Hinf
%! sys = ss (-1, 1, 1, 0);
%! H2 = norm (sys, 2);
%! Hinf = norm (sys, inf);
%!assert (H2, 0.7071, 1.5e-5);
%!assert (Hinf, 1, 5e-4);


## norm dt
%!shared H2, Hinf
%! a = [ 2.417   -1.002    0.5488
%!           2        0         0
%!           0      0.5         0 ];
%! b = [     1
%!           0
%!           0 ];
%! c = [-0.424    0.436   -0.4552 ];
%! d = [     1 ];
%! sys = ss (a, b, c, d, 0.1);
%! H2 = norm (sys, 2);
%! Hinf = norm (sys, inf);
%!assert (H2, 1.2527, 1.5e-5);
%!assert (Hinf, 2.7, 0.1);
