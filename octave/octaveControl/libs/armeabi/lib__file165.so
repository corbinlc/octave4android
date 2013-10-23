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
## Vertical concatenation of LTI objects.  If necessary, object conversion
## is done by sys_group.  Used by Octave for "[sys1; sys2]".

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: September 2009
## Version: 0.1

function sys = vertcat (sys, varargin)

  for k = 1 : (nargin-1)

    sys1 = sys;
    sys2 = varargin{k};

    [p1, m1] = size (sys1);
    [p2, m2] = size (sys2);

    if (m1 != m2)
      error ("lti: vertcat: number of system inputs incompatible: [(%dx%d); (%dx%d)]",
              p1, m1, p2, m2);
    endif

    sys = __sys_group__ (sys1, sys2);

    in_scl = [eye(m1); eye(m2)];

    sys = sys * in_scl;

  endfor

endfunction
