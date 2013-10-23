## Copyright (C) 2009, 2010   Lukas F. Reichlin
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
## Number of outputs (p), inputs (m) and states (n) of state space matrices.
## For internal use only.

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: September 2009
## Version: 0.2

function [p, m, n] = __ss_dim__ (a, b, c, d, e = [])

  ## TODO: create oct-file?

  if (! is_real_matrix (a, b, c, d, e))
    error ("ss: system matrices must be real");
  endif

  [arows, acols] = size (a);
  [brows, bcols] = size (b);
  [crows, ccols] = size (c);
  [drows, dcols] = size (d);

  m = bcols;  # = dcols
  n = arows;  # = acols
  p = crows;  # = drows

  if (arows != acols)
    error ("ss: system matrix a(%dx%d) is not square", arows, acols);
  endif

  if (brows != arows)
    error ("ss: system matrices a(%dx%d) and b(%dx%d) are incompatible",
            arows, acols, brows, bcols);
  endif

  if (ccols != acols)
    error ("ss: system matrices a(%dx%d) and c(%dx%d) are incompatible",
            arows, acols, crows, ccols);
  endif

  if (bcols != dcols)
    error ("ss: system matrices b(%dx%d) and d(%dx%d) are incompatible",
            brows, bcols, drows, dcols);
  endif

  if (crows != drows)
    error ("ss: system matrices c(%dx%d) and d(%dx%d) are incompatible",
            crows, ccols, drows, dcols);
  endif

  if (! isempty (e) && ! size_equal (e, a))
    error ("ss: system matrices a(%dx%d) and e(%dx%d) are incompatible",
            arows, acols, rows (e), columns (e));
  endif

endfunction
