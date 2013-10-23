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
## Frequency response of TF models.

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: October 2009
## Version: 0.4

function H = __freqresp__ (sys, w, resptype = 0, cellflag = false)

  [num, den, tsam] = tfdata (sys, "vector");

  if (isct (sys))  # continuous system
    s = i * w;
  else             # discrete system
    s = exp (i * w * abs (tsam));
  endif
  
  s = reshape (s, 1, 1, []);

  if (issiso (sys))
    H = polyval (num, s) ./ polyval (den, s);
  else
    H = cellfun (@(x, y) polyval (x, s) ./ polyval (y, s), num, den, "uniformoutput", false);
    H = cell2mat (H);
  endif

  if (resptype)
    [p, m] = size (sys);
    l = length (s); 
    if (m != p)
      error ("tf: freqresp: system must be square for response type %d", resptype);
    endif 
    H = mat2cell (H, p, m, ones (1, l))(:);
    j = eye (p);
    switch (resptype)
      case 1     # inversed system
        H = cellfun (@inv, H, "uniformoutput", false);
      case 2     # inversed sensitivity
        H = cellfun (@(x) j + x, H, "uniformoutput", false);
      case 3     # inversed complementary sensitivity
        H = cellfun (@(x) j + inv (x), H, "uniformoutput", false);
      otherwise
        error ("tf: freqresp: invalid response type");
    endswitch
    if (! cellflag)
      H = cat (3, H{:});
    endif
  elseif (cellflag)
    [p, m] = size (sys);
    l = length (s);
    H = mat2cell (H, p, m, ones (1, l))(:);
  endif

endfunction
