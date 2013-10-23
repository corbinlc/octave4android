## Copyright (C) 2009, 2010, 2011   Lukas F. Reichlin
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
## Set or modify properties of SS objects.

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: October 2009
## Version: 0.3

function sys = __set__ (sys, prop, val)

  switch (prop)      # {<internal name>, <user name>}
    case "a"
      __ss_dim__ (val, sys.b, sys.c, sys.d);
      sys.a = val;

    case "b"
      __ss_dim__ (sys.a, val, sys.c, sys.d);
      sys.b = val;

    case "c"
      __ss_dim__ (sys.a, sys.b, val, sys.d);
      sys.c = val;

    case "d"
      __ss_dim__ (sys.a, sys.b, sys.c, val);
      sys.d = val;

    case "e"
      if (isempty (val))
        sys.e = [];  # avoid [](nx0) or [](0xn)
      else
        __ss_dim__ (sys.a, sys.b, sys.c, sys.d, val);
        sys.e = val;
      endif

    case {"stname", "statename"}
      n = rows (sys.a);
      sys.stname = __adjust_labels__ (val, n);

    case "scaled"
      if (isscalar (val))
        sys.scaled = logical (val);
      else
        error ("ss: set: property 'scaled' must be a logical value");
      endif

    otherwise
      error ("ss: set: invalid property name");

  endswitch

endfunction