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
## Set or modify properties of FRD objects.

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: October 2010
## Version: 0.1

function sys = __set__ (sys, prop, val)

  switch (prop)  # {<internal name>, <user name>}
    case {"h", "r", "resp", "response"}
      val = __adjust_frd_data__ (val, sys.w);
      __frd_dim__ (val, sys.w);
      sys.H = val;

    case {"w", "f", "freq", "frequency"}
      [~, val] = __adjust_frd_data__ (sys.H, val);
      __frd_dim__ (sys.H, val);
      sys.w = val;

    otherwise
      error ("frd: set: invalid property name");

  endswitch

endfunction