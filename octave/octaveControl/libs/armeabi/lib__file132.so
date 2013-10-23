## Copyright (C) 2011   Lukas F. Reichlin
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
## @deftypefn {Function File} {@var{bool} =} isminimumphase (@var{sys})
## @deftypefnx {Function File} {@var{bool} =} isminimumphase (@var{sys}, @var{tol})
## Determine whether LTI system is minimum phase.
## The zeros must lie in the left complex half-plane.
## The name minimum-phase refers to the fact that such a system has the
## minimum possible phase lag for the given magnitude response |sys(jw)|.
##
## @strong{Inputs}
## @table @var
## @item sys
## LTI system.
## @item tol
## Optional tolerance.  Default value is 0.
## @end table
##
## @strong{Outputs}
## @table @var
## @item bool = 0
## System is not minimum phase.
## @item bool = 1
## System is minimum phase.
## @end table
##
## @example
## @group
##   real (z) < -tol*(1 + abs (z))    continuous-time
##   abs (z) < 1 - tol                discrete-time
## @end group
## @end example
## @end deftypefn

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: January 2011
## Version: 0.1

function bool = isminimumphase (sys, tol = 0)

  if (nargin > 2)
    print_usage ();
  endif

  z = zero (sys);
  ct = isct (sys);

  bool = __is_stable__ (z, ct, tol);

endfunction