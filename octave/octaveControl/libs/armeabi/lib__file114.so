## Copyright (C) 2009, 2011   Lukas F. Reichlin
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
## @deftypefn {Function File} {@var{sys} =} c2d (@var{sys}, @var{tsam})
## @deftypefnx {Function File} {@var{sys} =} c2d (@var{sys}, @var{tsam}, @var{method})
## @deftypefnx {Function File} {@var{sys} =} c2d (@var{sys}, @var{tsam}, @var{'prewarp'}, @var{w0})
## Convert the continuous @acronym{LTI} model into its discrete-time equivalent.
##
## @strong{Inputs}
## @table @var
## @item sys
## Continuous-time @acronym{LTI} model.
## @item tsam
## Sampling time in seconds.
## @item method
## Optional conversion method.  If not specified, default method @var{"zoh"}
## is taken.
## @table @var
## @item 'zoh'
## Zero-order hold or matrix exponential.
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
## Discrete-time @acronym{LTI} model.
## @end table
## @end deftypefn

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: October 2009
## Version: 0.3

function sys = c2d (sys, tsam, method = "std", w0 = 0)

  if (nargin < 2 || nargin > 4)
    print_usage ();
  endif

  if (! isa (sys, "lti"))
    error ("c2d: first argument is not an LTI model");
  endif

  if (isdt (sys))
    error ("c2d: system is already discrete-time");
  endif

  if (! issample (tsam))
    error ("c2d: second argument is not a valid sample time");
  endif

  if (! ischar (method))
    error ("c2d: third argument is not a string");
  endif

  if (! issample (w0, 0))
    error ("c2d: fourth argument is not a valid pre-warping frequency");
  endif

  sys = __c2d__ (sys, tsam, lower (method), w0);
  sys.tsam = tsam;

endfunction


## bilinear transformation
## using oct-file directly
%!shared Mo, Me
%! A = [  1.0  0.5
%!        0.5  1.0 ].';
%!
%! B = [  0.0 -1.0
%!        1.0  0.0 ].';
%!
%! C = [ -1.0  0.0
%!        0.0  1.0 ].';
%!
%! D = [  1.0  0.0
%!        0.0 -1.0 ].';
%!
%! [Ao, Bo, Co, Do] = __sl_ab04md__ (A, B, C, D, 1.0, 1.0, false);
%!
%! Ae = [ -1.0000  -4.0000
%!        -4.0000  -1.0000 ];
%!
%! Be = [  2.8284   0.0000
%!         0.0000  -2.8284 ];
%!
%! Ce = [  0.0000   2.8284
%!        -2.8284   0.0000 ];
%!
%! De = [ -1.0000   0.0000
%!         0.0000  -3.0000 ];
%!
%! Mo = [Ao, Bo; Co, Do];
%! Me = [Ae, Be; Ce, De];
%!
%!assert (Mo, Me, 1e-4);


## bilinear transformation
## user function
%!shared Mo, Me
%! A = [  1.0  0.5
%!        0.5  1.0 ].';
%!
%! B = [  0.0 -1.0
%!        1.0  0.0 ].';
%!
%! C = [ -1.0  0.0
%!        0.0  1.0 ].';
%!
%! D = [  1.0  0.0
%!        0.0 -1.0 ].';
%!
%! [Ao, Bo, Co, Do] = ssdata (c2d (ss (A, B, C, D), 2, "tustin"));
%!
%! Ae = [ -1.0000  -4.0000
%!        -4.0000  -1.0000 ];
%!
%! Be = [  2.8284   0.0000
%!         0.0000  -2.8284 ];
%!
%! Ce = [  0.0000   2.8284
%!        -2.8284   0.0000 ];
%!
%! De = [ -1.0000   0.0000
%!         0.0000  -3.0000 ];
%!
%! Mo = [Ao, Bo; Co, Do];
%! Me = [Ae, Be; Ce, De];
%!
%!assert (Mo, Me, 1e-4);


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
