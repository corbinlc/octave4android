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
## Convert descriptor state-space system into regular state-space form.

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: September 2011
## Version: 0.1

function [a, b, c, d, e] = __dss2ss__ (a, b, c, d, e)

  if (isempty (e))
    return;
  elseif (rcond (e) < eps)  # check for singularity
    error ("ss: dss2ss: descriptor matrice 'e' singular");
  else
    [a, b, c, d] = __sl_sb10jd__ (a, b, c, d, e);
    e = [];
  endif

endfunction