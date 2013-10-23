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
## Block diagonal concatenation of two FRD models.
## This file is part of the Model Abstraction Layer.
## For internal use only.

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: October 2010
## Version: 0.2

function retsys = __sys_group__ (sys1, sys2)

  if (! isa (sys1, "frd"))
    sys1 = frd (sys1, sys2.w);
  endif

  if (! isa (sys2, "frd"))
    sys2 = frd (sys2, sys1.w);
  endif

  retsys = frd ();
  retsys.lti = __lti_group__ (sys1.lti, sys2.lti);

  lw1 = length (sys1.w);
  lw2 = length (sys2.w);

  [p1, m1, l1] = size (sys1.H);
  [p2, m2, l2] = size (sys2.H);

  ## TODO: tolerances for frequencies, i.e. don't check for equality

  ## find intersection of frequency vectors
  if (lw1 == lw2 && all (sys1.w == sys2.w))  # identical frequency vectors
    retsys.w = sys1.w;
    H1 = sys1.H;
    H2 = sys2.H;
  else                                       # differing frequency vectors
    ## find common frequencies
    retsys.w = w = intersect (sys1.w, sys2.w);

    ## indices of common frequencies
    w1_idx = arrayfun (@(x) find (sys1.w == x), w);
    w2_idx = arrayfun (@(x) find (sys2.w == x), w);

    ## extract common responses
    H1 = sys1.H(:, :, w1_idx);
    H2 = sys2.H(:, :, w2_idx);
  endif

  ## block-diagonal concatenation
  lw = length (retsys.w);
  z12 = zeros (p1, m2);
  z21 = zeros (p2, m1);
  H1 = mat2cell (H1, p1, m1, ones (1, lw))(:);
  H2 = mat2cell (H2, p2, m2, ones (1, lw))(:);

  H = cellfun (@(x, y) [x, z12; z21, y], H1, H2, "uniformoutput", false);

  retsys.H = cat (3, H{:});

endfunction