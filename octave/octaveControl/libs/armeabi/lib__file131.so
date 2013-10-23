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
## @deftypefn {Function File} {@var{bool} =} isdt (@var{sys})
## Determine whether LTI model is a discrete-time system.
##
## @strong{Inputs}
## @table @var
## @item sys
## LTI system.
## @end table
##
## @strong{Outputs}
## @table @var
## @item bool = 0
## @var{sys} is a continuous-time system.
## @item bool = 1
## @var{sys} is a discrete-time system or a static gain.
## @end table
## @end deftypefn

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: September 2009
## Version: 0.1

function bool = isdt (ltisys)

  if (nargin != 1)
    print_usage ();
  endif

  bool = (ltisys.tsam != 0);

endfunction
