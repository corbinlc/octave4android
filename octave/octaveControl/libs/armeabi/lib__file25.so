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
## check the feedback sign.

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: December 2011
## Version: 0.1

function negfb = __conred_check_feedback_sign__ (fbsign, key = "feedback")

  if (! ischar (fbsign))
    error ("conred: key '%s' requires string value", key);
  endif

  switch (fbsign)
    case "+"
      negfb = false;
    case "-"
      negfb = true;
    otherwise
      error ("conred: key '%s' has an invalid value", key);
  endswitch

endfunction