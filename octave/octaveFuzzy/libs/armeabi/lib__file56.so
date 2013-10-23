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
## @deftypefn {Function File} {@var{V} =} init_cluster_prototypes (@var{X}, @var{k})
##
## Initialize k cluster centers to random locations in the ranges
## given by the min/max values of each feature of the dataset.
##
## @seealso{fcm, gustafson_kessel, update_cluster_membership, update_cluster_prototypes, compute_cluster_obj_fcn, compute_cluster_convergence}
##
## @end deftypefn

## Author:        L. Markowsky
## Keywords:      fuzzy-logic-toolkit fuzzy partition clustering
## Directory:     fuzzy-logic-toolkit/inst/private/
## Filename:      init_cluster_prototypes.m
## Last-Modified: 2 Sep 2012

function V = init_cluster_prototypes (X, k)

  num_features = columns (X);
  min_feature_value = min (X);
  max_feature_value = max (X);
  V = rand (k, num_features);

  for i = 1 : num_features
    V(:, i) = (max_feature_value(i) - min_feature_value(i)) * ...
                V(:, i) + min_feature_value(i);
  endfor

endfunction
