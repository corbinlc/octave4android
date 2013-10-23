## Copyright (C) 2009, 2010, 2011, 2012   Lukas F. Reichlin
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
## Frequency response of SS models.

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: October 2009
## Version: 0.5

function H = __freqresp__ (sys, w, resptype = 0, cellflag = false)

  if (sys.scaled == false)
    sys = prescale (sys);
  endif

  [a, b, c, d, e, tsam] = dssdata (sys);

  if (resptype != 0 && columns (b) != rows (c))
    error ("ss: freqresp: system must be square for response type %d", resptype);
  endif

  if (isct (sys))  # continuous system
    s = i * w;
  else             # discrete system
    s = exp (i * w * abs (tsam));
  endif

  switch (resptype)
    case 0         # default system
      H = arrayfun (@(x) c/(x*e - a)*b + d, s, "uniformoutput", false);

    case 1         # inversed system
      H = arrayfun (@(x) inv (c/(x*e - a)*b + d), s, "uniformoutput", false);

    case 2         # inversed sensitivity
      j = eye (columns (b));
      H = arrayfun (@(x) j + c/(x*e - a)*b + d, s, "uniformoutput", false);

    case 3         # inversed complementary sensitivity
      j = eye (columns (b));
      H = arrayfun (@(x) j + inv (c/(x*e - a)*b + d), s, "uniformoutput", false);

    otherwise
      error ("ss: freqresp: invalid response type");
  endswitch

  if (! cellflag)
    H = cat (3, H{:});
  endif

endfunction