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
## @deftypefn {Function File} {@var{retsys} =} __sys_connect__ (@var{sys}, @var{M})
## This function is part of the Model Abstraction Layer.  No argument checking.
## For internal use only.
## @example
## @group
## Problem: Solve the system equations of
## Y(s) = G(s) E(s)
## E(s) = U(s) + M Y(s)
## in order to build
## Y(s) = H(s) U(s)
## Solution:
## Y(s) = G(s) [U(s) + M Y(s)]
## Y(s) = G(s) U(s) + G(s) M Y(s)
## Y(s) = [I - G(s) M]^-1 G(s) U(s)
##        \_______    _______/
##                H(s)
## @end group
## @end example
## @end deftypefn

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: October 2010
## Version: 0.1

function sys = __sys_connect__ (sys, M)

  [p, m, l] = size (sys.H);

  I = eye (p);
  H = mat2cell (sys.H, p, m, ones (1, l))(:);

  H = cellfun (@(x) (I - x*M) \ x, H, "uniformoutput", false);

  sys.H = cat (3, H{:});

endfunction