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
## Matrix left division of LTI objects.  If necessary, object conversion
## is done by sys_group in mtimes.  Used by Octave for "sys1 \\ sys2".

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: October 2009
## Version: 0.1

function sys = mldivide (sys1, sys2)

  if (nargin != 2)    # prevent sys = mldivide (sys1, sys2, sys3, ...)
    error ("lti: mldivide: this is a binary operator");
  endif

  sys1 = inv (sys1);  # let octave decide which inv() it uses

  [p1, m1] = size (sys1);
  [p2, m2] = size (sys2);

  if (m2 != p1)
    error ("lti: mldivide: system dimensions incompatible: (%dx%d) \ (%dx%d)",
            p1, m1, p2, m2);
  endif

  sys = sys1 * sys2;

endfunction

