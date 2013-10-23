## Copyright (C) 2009   Lukas F. Reichlin
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
## @deftypefn{Function File} {@var{sys} =} parallel (@var{sys1}, @var{sys2})
## Parallel connection of two LTI systems.
##
## @strong{Block Diagram}
## @example
## @group
##     ..........................
##     :      +--------+        :
##     :  +-->|  sys1  |---+    :
##  u  :  |   +--------+   | +  :  y
## -------+                O--------->
##     :  |   +--------+   | +  :
##     :  +-->|  sys2  |---+    :
##     :      +--------+        :
##     :.........sys............:
##
## sys = parallel (sys1, sys2)
## @end group
## @end example
## @end deftypefn

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: September 2009
## Version: 0.1

function sys = parallel (sys1, sys2)

  if (nargin == 2)
    sys = sys1 + sys2;
  ## elseif (nargin == 6)

  ## TODO: implement "complicated" case sys = parallel (sys1, sys2, in1, in2, out1, out2)

  else
    print_usage ();
  endif

endfunction