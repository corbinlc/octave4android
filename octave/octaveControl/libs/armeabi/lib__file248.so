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
## Inversion of TF models.

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: October 2009
## Version: 0.2

function sys = __sys_inverse__ (sys)

  nvec = size (sys);
  num = sys.num;
  den = sys.den;

  if (all (nvec == 1))      # SISO
    if (num{1,1} == 0)      # catch case num = 0
      sys.num(1,1) = tfpoly (0);
      sys.den(1,1) = tfpoly (1);
    else
      sys.num = den;
      sys.den = num;
    endif
  elseif (all (nvec == 2))  # 2x2 MIMO
    sys.num(1,1) = -den{1,1}*den{1,2}*den{2,1}*num{2,2};
    sys.num(1,2) = den{1,1}*den{2,1}*den{2,2}*num{1,2};
    sys.num(2,1) = den{1,1}*den{1,2}*den{2,2}*num{2,1};
    sys.num(2,2) = -den{1,2}*den{2,1}*den{2,2}*num{1,1};  
    sys.den(:) = den{1,1}*den{2,2}*num{1,2}*num{2,1} - den{1,2}*den{2,1}*num{1,1}*num{2,2};
  else
    ## I've calculated 3x3 systems with sage but the formula is quite long
    [num, den] = tfdata (inv (ss (sys)), "tfpoly");
    sys.num = num;
    sys.den = den;
  endif

endfunction
