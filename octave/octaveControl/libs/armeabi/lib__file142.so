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
## Binary subtraction of LTI objects.  If necessary, object conversion
## is done by sys_group.  Used by Octave for "sys1 - sys2".

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: September 2009
## Version: 0.1

function sys = minus (sys1, sys2)

  if (nargin != 2)  # prevent sys = minus (sys1, sys2, sys3, ...)
    error ("lti: minus: this is a binary operator");
  endif

  [p1, m1] = size (sys1);
  [p2, m2] = size (sys2);

  if (p1 != p2 || m1 != m2)
    error ("lti: minus: system dimensions incompatible: (%dx%d) - (%dx%d)",
            p1, m1, p2, m2);
  endif

  sys = __sys_group__ (sys1, sys2);

  in_scl = [eye(m1); eye(m2)];
  out_scl = [eye(p1), -eye(p2)];

  sys = out_scl * sys * in_scl;

endfunction
