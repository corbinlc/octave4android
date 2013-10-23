## Copyright (C) 2009, 2011, 2012   Lukas F. Reichlin
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
## Convert the continuous SS model into its discrete-time equivalent.

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: October 2009
## Version: 0.4

function sys = __c2d__ (sys, tsam, method = "zoh", w0 = 0)

  switch (method(1))
    case {"z", "s"}                # {"zoh", "std"}
      [sys.a, sys.b, sys.c, sys.d, sys.e] = __dss2ss__ (sys.a, sys.b, sys.c, sys.d, sys.e);
      [n, m] = size (sys.b);       # n: states, m: inputs
      ## TODO: use SLICOT MB05OD
      tmp = expm ([sys.a*tsam, sys.b*tsam; zeros(m, n+m)]);
      sys.a = tmp (1:n, 1:n);      # F
      sys.b = tmp (1:n, n+(1:m));  # G

    case {"t", "b", "p"}           # {"tustin", "bilin", "prewarp"}
      if (method(1) == "p")        # prewarping
        beta = w0 / tan (w0*tsam/2);
      else
        beta = 2/tsam;
      endif
      if (isempty (sys.e))
        [sys.a, sys.b, sys.c, sys.d] = __sl_ab04md__ (sys.a, sys.b, sys.c, sys.d, 1, beta, false);
      else
        [sys.a, sys.b, sys.c, sys.d, sys.e] = __dss_bilin__ (sys.a, sys.b, sys.c, sys.d, sys.e, beta, false);
      endif

    case "m"                       # "matched"
      tmp = ss (c2d (zpk (sys), tsam, method));
      sys.e = tmp.e;
      sys.a = tmp.a;
      sys.b = tmp.b;
      sys.c = tmp.c;
      sys.d = tmp.d;

    otherwise
      error ("ss: c2d: '%s' is an invalid or missing method", method);
  endswitch

endfunction
