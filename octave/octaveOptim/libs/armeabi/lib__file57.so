## Copyright (C) 2011 Olaf Till <olaf.till@uni-jena.de>
##
## This program is free software; you can redistribute it and/or modify
## it under the terms of the GNU General Public License as published by
## the Free Software Foundation; either version 3 of the License, or
## (at your option) any later version.
##
## This program is distributed in the hope that it will be useful,
## but WITHOUT ANY WARRANTY; without even the implied warranty of
## MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
## GNU General Public License for more details.
##
## You should have received a copy of the GNU General Public License
## along with this program; If not, see <http://www.gnu.org/licenses/>.

function hook = __covd_wls__ (hook)

  m = hook.nm;
  n = hook.np;

  if (m <= n)
    error ("guessing covariance-matrix of residuals for weighted least squares requires at least one more residual than free parameters");
  endif

  w = hook.weights(:);
  res = hook.residuals(:);

  w2 = w .^ 2;

  hook.covd = diag (res.' * diag (w2) * res / (m - n) ./ w2);

endfunction
