## Copyright (C) 2009, 2012   Lukas F. Reichlin
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
## Block diagonal concatenation of two TF models.
## This file is part of the Model Abstraction Layer.
## For internal use only.

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: September 2009
## Version: 0.2

function retsys = __sys_group__ (sys1, sys2)

  if (! isa (sys1, "tf"))
    sys1 = tf (sys1);
  endif

  if (! isa (sys2, "tf"))
    sys2 = tf (sys2);
  endif

  retsys = tf ();

  retsys.lti = __lti_group__ (sys1.lti, sys2.lti);

  [p1, m1] = size (sys1);
  [p2, m2] = size (sys2);

  empty12 = tfpolyzeros (p1, m2);
  empty21 = tfpolyzeros (p2, m1);

  retsys.num = [sys1.num, empty12 ;
                empty21,  sys2.num];

  empty12 = tfpolyones (p1, m2);
  empty21 = tfpolyones (p2, m1);

  retsys.den = [sys1.den, empty12 ;
                empty21,  sys2.den];

  if (sys1.tfvar == sys2.tfvar)
    retsys.tfvar = sys1.tfvar;
  elseif (sys1.tfvar == "x")
    retsys.tfvar = sys2.tfvar;
  else
    retsys.tfvar = sys1.tfvar;
  endif

  if (sys1.inv || sys2.inv)
    retsys.inv = true;
  endif

endfunction
