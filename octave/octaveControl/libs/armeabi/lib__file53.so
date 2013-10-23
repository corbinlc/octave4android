## Copyright (C) 2010, 2012   Lukas F. Reichlin
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
## Frequency response of FRD models :-)

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: October 2010
## Version: 0.2

function H = __freqresp__ (sys, w, resptype = 0, cellflag = false)

  [H, w_sys, tsam] = frdata (sys, "array");

  if (! isempty (w))     # freqresp (frdsys, w), sigma (frdsys, w), ...
    tol = sqrt (eps);
    w_idx = arrayfun (@(x) find (abs (w_sys - x) < tol), w, "uniformoutput", false);
    w_idx = vertcat (w_idx{:});

    ## NOTE: There are problems when cellfun uses "uniformoutput", true
    ##       and find returns an empty matrix,    

    if (length (w_idx) != numel (w))
      error ("frd: freqresp: some frequencies are not within tolerance %g", tol);
    endif

    H = H(:, :, w_idx);
  endif

  [p, m, l] = size (H);

  if (resptype == 0)

    if (cellflag)
      H = mat2cell (H, p, m, ones (1, l))(:);
    endif

  else

    if (m != p)
      error ("tf: freqresp: system must be square for response type %d", resptype);
    endif

    H = mat2cell (H, p, m, ones (1, l))(:);
    j = eye (p);

    switch (resptype)
      case 1             # inversed system
        H = cellfun (@inv, H, "uniformoutput", false);

      case 2             # inversed sensitivity
        H = cellfun (@(x) j + x, H, "uniformoutput", false);

      case 3             # inversed complementary sensitivity
        H = cellfun (@(x) j + inv (x), H, "uniformoutput", false);

      otherwise
        error ("frd: freqresp: invalid response type");
    endswitch

    if (! cellflag)
      H = cat (3, H{:});
    endif

  endif

endfunction
