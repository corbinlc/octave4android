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
## Convert the continuous TF model into its discrete-time equivalent.

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: October 2009
## Version: 0.3

function sys = __c2d__ (sys, tsam, method = "zoh", w0 = 0)

  if (strncmpi (method, "m", 1))    # "matched"
    ## TODO: move this code to  @zpk/__c2d__.m  once ZPK models are implemented
    
    if (! issiso (sys))
      error ("tf: c2d: require SISO system for matched pole/zero method");
    endif

    [z_c, p_c, k_c] = zpkdata (sys, "vector");
    p_d = exp (p_c * tsam);    
    z_d = exp (z_c * tsam);

    if (any (! isfinite (p_d)) || any (! isfinite (z_d)))
      error ("tf: c2d: discrete-time poles and zeros are not finite");
    endif

    ## continuous-time zeros at infinity are mapped to -1 in discrete-time
    ## except for one.  for non-proper transfer functions, no zeros at -1 are added.
    np = length (p_c);              # number of poles
    nz = length (z_c);              # number of finite zeros, np-nz number of infinite zeros
    z_d = vertcat (z_d, repmat (-1, np-nz-1, 1));

    ## the discrete-time gain k_d is matched at a certain frequency (w_c, w_d)
    ## to continuous-time gain k_c.  the dc gain is taken (w_c=0, w_d=1) unless
    ## there are continuous-time poles/zeros near 0.  then w_c=1/tsam is taken.
    w_c = 0;                        # dc gain
    tol = sqrt (eps);               # poles/zeros below tol are assumed to be zero
    while (any (abs ([p_c; z_c] - w_c) < tol))
      w_c += 0.1 / tsam;
    endwhile
    w_d = exp (w_c * tsam);
    k_d = real (k_c * prod (w_c - z_c) / prod (w_c - p_c) * prod (w_d - p_d) / prod (w_d - z_d));

    tmp = zpk (z_d, p_d, k_d, tsam);
    sys.num = tmp.num;
    sys.den = tmp.den;

  else
    [p, m] = size (sys);

    for i = 1 : p
      for j = 1 : m
        idx = substruct ("()", {i, j});
        tmp = subsref (sys, idx);
        tmp = c2d (ss (tmp), tsam, method, w0);
        [num, den] = tfdata (tmp, "tfpoly");
        sys.num(i, j) = num;
        sys.den(i, j) = den;
      endfor
    endfor
  endif

  sys.tfvar = "z";

endfunction
