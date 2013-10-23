## Copyright (C) 2009, 2010   Lukas F. Reichlin
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
## Poles of SS object.

## Special thanks to Peter Benner for his advice.
## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: October 2009
## Version: 0.3

function pol = __pole__ (sys)

  if (isempty (sys.e))
    pol = eig (sys.a);
  else
    pol = eig (sys.a, sys.e);
    tol = norm ([sys.a, sys.e], 2);
    idx = find (abs (pol) < tol/eps);
    pol = pol(idx);
  endif

endfunction


## sys = ss (-2, 3, 4, 0)
## sysi = inv (sys)  # singular e
## p = pole (sysi)
## infinite poles correct?