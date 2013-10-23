## Copyright (C) 2012   Lukas F. Reichlin
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
## Conjugate transpose of FRD objects.

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: May 2012
## Version: 0.1

function sys = __ctranspose__ (sys)

  [p, m, l] = size (sys.H);

  H = mat2cell (sys.H, p, m, ones (1, l))(:);

  H = cellfun (@ctranspose, H, "uniformoutput", false);

  sys.H = cat (3, H{:});

endfunction