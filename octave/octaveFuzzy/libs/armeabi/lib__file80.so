## Copyright (C) 2011-2012 L. Markowsky <lmarkov@users.sourceforge.net>
##
## This file is part of the fuzzy-logic-toolkit.
##
## The fuzzy-logic-toolkit is free software; you can redistribute it
## and/or modify it under the terms of the GNU General Public License
## as published by the Free Software Foundation; either version 3 of
## the License, or (at your option) any later version.
##
## The fuzzy-logic-toolkit is distributed in the hope that it will be
## useful, but WITHOUT ANY WARRANTY; without even the implied warranty
## of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
## General Public License for more details.
##
## You should have received a copy of the GNU General Public License
## along with the fuzzy-logic-toolkit; see the file COPYING.  If not,
## see <http://www.gnu.org/licenses/>.

## -*- texinfo -*-
## @deftypefn {Function File} {@var{sqr_dist} =} square_distance_matrix (@var{X}, @var{V})
##
## Return a k x n matrix of ||x - v||^2 values (the squares of the
## distances between input data points x and cluster centers v), where
## k is the number of cluster centers and n is the number of data points.
##
## The element sqr_dist(i, j) will contain the square of the distance
## between the cluster center V(i, :) and the data point X(j, :).
##
## @end deftypefn

## Author:        L. Markowsky
## Keywords:      fuzzy-logic-toolkit fuzzy private
## Directory:     fuzzy-logic-toolkit/inst/private/
## Filename:      square_dist_matrix.m
## Last-Modified: 20 Aug 2012

function sqr_dist = square_distance_matrix (X, V)

  k = rows (V);
  n = rows (X);
  sqr_dist = zeros (k, n);
  for i = 1 : k
    Vi = V(i, :);
    for j = 1 : n
      Vi_to_Xj = X(j, :) - Vi;
      sqr_dist(i, j) = sum (Vi_to_Xj .* Vi_to_Xj);
    endfor
  endfor

endfunction

