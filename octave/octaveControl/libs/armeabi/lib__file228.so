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
## Transmission zeros of SS object.
## Uses SLICOT AB08ND by courtesy of
## @uref{http://www.slicot.org, NICONET e.V.}

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: October 2009
## Version: 0.3

function [zer, gain] = __zero__ (sys, argc)

  if (isempty (sys.e))
    [zer, gain] = __sl_ab08nd__ (sys.a, sys.b, sys.c, sys.d, sys.scaled);
  else
    zer = __sl_ag08bd__ (sys.a, sys.e, sys.b, sys.c, sys.d, sys.scaled);
    if (argc > 1 && issiso (sys))
      pol = pole (sys);
      gain = __sl_tg04bx__ (sys.a, sys.e, sys.b, sys.c, sys.d, \
                       real (pol), imag (pol), real (zer), imag (zer));
    else
      gain = [];
    endif
  endif

endfunction