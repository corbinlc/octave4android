## Copyright (C) 2010 Olaf Till <olaf.till@uni-jena.de>
##
## This program is free software; you can redistribute it and/or modify
## it under the terms of the GNU General Public License as published by
## the Free Software Foundation; either version 3 of the License, or
## (at your option) any later version.
##
## This program is distributed in the hope that it will be useful,
## but WITHOUT ANY WARRANTY; without even the implied warranty of
## MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
## GNU General Public License for more details.
##
## You should have received a copy of the GNU General Public License
## along with this program; If not, see <http://www.gnu.org/licenses/>.

## __s2mat__ (s, ord)
##
## Returns a matrix of second derivatives with respect to some
## parameters from the structure-based representation of such a matrix
## in s, using the order of parameter names ord. s has to contain all
## fields named in ord. Each field has some subfields named in ord so
## that each second derivative is represented at least in one of its two
## possible orders. If it is represented differently in both orders, no
## error is returned, but both entries might get into the final matrix
## at symmetric positions.
##
## Should be included as a subfunction of a wrapper for optimization
## functions possibly needing a Hessian.

function ret = __s2mat__ (s, ord)

  if (any (size (s) != [1, 1]))
    error ("structure must be scalar");
  endif

  if (! (iscell (ord) && isvector (ord)))
    error ("ord must be a one-dimensional cell-array");
  endif

  c = fields2cell (structcat (1, fields2cell (s, ord){:}), ord);

  neidx = ! (eidx = cellfun ("isempty", c));

  ret = zeros (length (ord));

  ret(neidx) = [c{neidx}]; # faster than [c{:}] ?

  ret(eidx) = ret.'(eidx);

endfunction