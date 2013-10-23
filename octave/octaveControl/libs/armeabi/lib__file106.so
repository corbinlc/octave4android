## Copyright (C) 2010   Lukas F. Reichlin
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
## Determine whether all poles in a vector are stable.

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: December 2010
## Version: 0.1

function bool = __is_stable__ (pol, ct = true, tol = 0)

  if (ct)  # continuous-time
    bool = all (real (pol) < -tol*(1 + abs (pol)));
  else     # discrete-time
    bool = all (abs (pol) < 1 - tol);
  endif

endfunction
