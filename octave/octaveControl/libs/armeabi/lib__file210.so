## Copyright (C) 2011, 2012   Lukas F. Reichlin
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
## Convert the discrete SS model into its continuous-time equivalent.

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: September 2011
## Version: 0.3

function sys = __d2c__ (sys, tsam, method = "zoh", w0 = 0)

  switch (method(1))
    case {"z", "s"}                # {"zoh", "std"}
      [sys.a, sys.b, sys.c, sys.d, sys.e] = __dss2ss__ (sys.a, sys.b, sys.c, sys.d, sys.e);
      [n, m] = size (sys.b);       # n: states, m: inputs
      tmp = logm ([sys.a, sys.b; zeros(m,n), eye(m)]) / tsam;
      if (norm (imag (tmp), inf) > sqrt (eps))
        warning ("ss: d2c: possibly inaccurate results");
      endif
      sys.a = real (tmp(1:n, 1:n));
      sys.b = real (tmp(1:n, n+1:n+m));

    case {"t", "b", "p"}           # {"tustin", "bilin", "prewarp"}
      if (method(1) == "p")        # prewarping
        beta = w0 / tan (w0*tsam/2);
      else
        beta = 2/tsam;
      endif
      if (isempty (sys.e))
        [sys.a, sys.b, sys.c, sys.d] = __sl_ab04md__ (sys.a, sys.b, sys.c, sys.d, 1, beta, true);
      else
        [sys.a, sys.b, sys.c, sys.d, sys.e] = __dss_bilin__ (sys.a, sys.b, sys.c, sys.d, sys.e, beta, true);
      endif

    case "m"                       # "matched"
      tmp = ss (d2c (zpk (sys), method));
      sys.e = tmp.e;
      sys.a = tmp.a;
      sys.b = tmp.b;
      sys.c = tmp.c;
      sys.d = tmp.d;

    otherwise
      error ("ss: d2c: '%s' is an invalid or missing method", method);
  endswitch

endfunction
