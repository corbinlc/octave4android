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
## Number of outputs and inputs of transfer function numerator and
## denominator.  For internal use only.

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: October 2009
## Version: 0.1

function [nrows, ncols] = __tf_dim__ (num, den)

  [nrows, ncols] = size (num);
  [drows, dcols] = size (den);

  if (nrows != drows || ncols != dcols)
    error ("tf: num(%dx%d) and den(%dx%d) must have equal dimensions",
            nrows, ncols, drows, dcols);
  endif

endfunction