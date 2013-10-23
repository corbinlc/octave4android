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
## Matrix power of LTI objects.  The exponent must be an integer.
## Used by Octave for "sys^int".

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: October 2009
## Version: 0.1

function retsys = mpower (sys, e)

  if (nargin != 2)       # prevent sys = mpower (a, b, c, ...)
    error ("lti: mpower: this is a binary operator");
  endif

  if (! is_real_scalar (e) || e != round (e))
    error ("lti: mpower: exponent must be an integer");
  endif

  [p, m] = size (sys);
  
  if (p != m)
    error ("lti: mpower: system must be square");
  endif

  ex = round (abs (e));  # make sure ex is a positive integer

  switch (sign (e))
    case -1              # lti^-ex
      sys = inv (sys);
      retsys = sys;
    case 0               # lti^0
      retsys = eye (p);
      return;
    case 1               # lti^ex
      retsys = sys;
  endswitch
    
  for k = 2 : ex
    retsys = retsys * sys;
  endfor

endfunction
