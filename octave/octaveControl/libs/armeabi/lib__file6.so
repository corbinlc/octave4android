## Copyright (C) 2010   Lukas F. Reichlin
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
## Common code for adjusting FRD model data.
## Used by @frd/frd.m and @frd/__set__.m

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: October 2010
## Version: 0.1

function [H, w] = __adjust_frd_data__ (H, w);

  w = reshape (w, [], 1);
  lw = length (w);

  if (ndims (H) != 3 && ! isempty (H))
    if (isscalar (H))
      H = reshape (H, 1, 1, []);
      if (lw > 1)
        H = repmat (H, [1, 1, lw]);            # needed for "frd1 + scalar2" or "scalar1 * frd2) 
      endif
    elseif (isvector (H) && length (H) == lw)  # SISO system (H is a vector)
      H = reshape (H, 1, 1, []);
    elseif (ismatrix (H))
      H = reshape (H, rows (H), []);
      if (lw > 1)
        H = repmat (H, [1, 1, lw]);            # needed for "frd1 + matrix2" or "matrix1 * frd2) 
      endif
    else
      error ("frd: first argument H invalid");
    endif
  elseif (isempty (H))
    H = zeros (0, 0, 0);
  endif

endfunction