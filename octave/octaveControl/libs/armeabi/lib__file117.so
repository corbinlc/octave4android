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
## @deftypefn {Function File} {@var{sys} =} d2c (@var{sys})
## @deftypefnx {Function File} {@var{sys} =} d2c (@var{sys}, @var{method})
## @deftypefnx {Function File} {@var{sys} =} d2c (@var{sys}, @var{'prewarp'}, @var{w0})
## Convert the discrete @acronym{LTI} model into its continuous-time equivalent.
##
## @strong{Inputs}
## @table @var
## @item sys
## Discrete-time @acronym{LTI} model.
## @item method
## Optional conversion method.  If not specified, default method @var{"zoh"}
## is taken.
## @table @var
## @item 'zoh'
## Zero-order hold or matrix logarithm.
## @item 'tustin', 'bilin'
## Bilinear transformation or Tustin approximation.
## @item 'prewarp'
## Bilinear transformation with pre-warping at frequency @var{w0}.
## @item 'matched'
## Matched pole/zero method.
## @end table
## @end table
##
## @strong{Outputs}
## @table @var
## @item sys
## Continuous-time @acronym{LTI} model.
## @end table
## @end deftypefn

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: September 2011
## Version: 0.2

function sys = d2c (sys, method = "std", w0 = 0)

  if (nargin == 0 || nargin > 3)
    print_usage ();
  endif

  if (! isa (sys, "lti"))
    error ("d2c: first argument is not an LTI model");
  endif

  if (isct (sys))
    error ("d2c: system is already continuous-time");
  endif

  if (! ischar (method))
    error ("d2c: second argument is not a string");
  endif

  if (! issample (w0, 0))
    error ("d2c: third argument is not a valid pre-warping frequency");
  endif

  sys = __d2c__ (sys, sys.tsam, lower (method), w0);
  sys.tsam = 0;

endfunction


## bilinear transformation
## both directions
%!shared Mo, Me
%! A = [  1.0  0.5
%!        0.5  1.0 ];
%!
%! B = [  0.0 -1.0
%!        1.0  0.0 ];
%!
%! C = [ -1.0  0.0
%!        0.0  1.0 ];
%!
%! D = [  1.0  0.0
%!        0.0 -1.0 ];
%!
%! [Ao, Bo, Co, Do] = ssdata (d2c (c2d (ss (A, B, C, D), 2, "tustin"), "tustin"));
%!
%! Mo = [Ao, Bo; Co, Do];
%! Me = [A, B; C, D];
%!
%!assert (Mo, Me, 1e-4);


## zero-order hold
## both directions
%!shared Mo, Me
%! A = [  1.0  0.5
%!        0.5  1.0 ];
%!
%! B = [  0.0 -1.0
%!        1.0  0.0 ];
%!
%! C = [ -1.0  0.0
%!        0.0  1.0 ];
%!
%! D = [  1.0  0.0
%!        0.0 -1.0 ];
%!
%! [Ao, Bo, Co, Do] = ssdata (d2c (c2d (ss (A, B, C, D), 2, "zoh"), "zoh"));
%!
%! Mo = [Ao, Bo; Co, Do];
%! Me = [A, B; C, D];
%!
%!assert (Mo, Me, 1e-4);


## bilinear transformation with pre-warping
## both directions
%!shared Mo, Me
%! A = [  1.0  0.5
%!        0.5  1.0 ];
%!
%! B = [  0.0 -1.0
%!        1.0  0.0 ];
%!
%! C = [ -1.0  0.0
%!        0.0  1.0 ];
%!
%! D = [  1.0  0.0
%!        0.0 -1.0 ];
%!
%! [Ao, Bo, Co, Do] = ssdata (d2c (c2d (ss (A, B, C, D), 2, "prewarp", 1000), "prewarp", 1000));
%!
%! Mo = [Ao, Bo; Co, Do];
%! Me = [A, B; C, D];
%!
%!assert (Mo, Me, 1e-4);
