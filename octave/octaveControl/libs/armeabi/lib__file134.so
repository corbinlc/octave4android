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
## @deftypefn {Function File} {@var{bool} =} isstable (@var{sys})
## @deftypefnx {Function File} {@var{bool} =} isstable (@var{sys}, @var{tol})
## Determine whether LTI system is stable.
##
## @strong{Inputs}
## @table @var
## @item sys
## LTI system.
## @item tol
## Optional tolerance for stability.  Default value is 0.
## @end table
##
## @strong{Outputs}
## @table @var
## @item bool = 0
## System is not stable.
## @item bool = 1
## System is stable.
## @end table
##
## @example
## @group
##   real (p) < -tol*(1 + abs (p))    continuous-time
##   abs (p) < 1 - tol                discrete-time
## @end group
## @end example
## @end deftypefn

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: October 2009
## Version: 0.2

function bool = isstable (sys, tol = 0)

  if (nargin > 2)
    print_usage ();
  endif

  pol = pole (sys);
  ct = isct (sys);

  bool = __is_stable__ (pol, ct, tol);

endfunction
