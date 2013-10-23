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
## Convert the discrete TF model into its continuous-time equivalent.

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: September 2011
## Version: 0.2

function sys = __d2c__ (sys, tsam, method = "zoh", w0 = 0)

  if (strncmpi (method, "m", 1))    # "matched"
    ## TODO: move this code to  @zpk/__d2c__.m  once ZPK models are implemented

    if (! issiso (sys))
      error ("tf: d2c: require SISO system for matched pole/zero method");
    endif

    [z_d, p_d, k_d] = zpkdata (sys, "vector");
    
    if (any (abs (p_d) < eps) || any (abs (z_d) < eps))
      error ("tf: d2c: discrete-time poles and zeros at 0 not supported because log(0) is -Inf");
    endif

    z_d_orig = z_d;
    z_d(abs (z_d+1) < sqrt (eps)) = [];

    p_c = log (p_d) / tsam;    
    z_c = log (z_d) / tsam;

    w_c = 0;
    w_d = 1;
    tol = sqrt (eps);
    while (any (abs ([p_d; z_d_orig] - w_d) < tol))
      w_c += 0.1 / tsam;
    endwhile
    w_d = exp (w_c * tsam);
    k_c = real (k_d * prod (w_d - z_d_orig) / prod (w_d - p_d) * prod (w_c - p_c) / prod (w_c - z_c));

    tmp = zpk (z_c, p_c, k_c);
    sys.num = tmp.num;
    sys.den = tmp.den;

  else
    [p, m] = size (sys);

    for i = 1 : p
      for j = 1 : m
        idx = substruct ("()", {i, j});
        tmp = subsref (sys, idx);
        tmp = d2c (ss (tmp), method, w0);
        [num, den] = tfdata (tmp, "tfpoly");
        sys.num(i, j) = num;
        sys.den(i, j) = den;
      endfor
    endfor
  endif

  sys.tfvar = "s";

endfunction
