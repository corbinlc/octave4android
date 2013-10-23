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
## @deftypefn {Function File} {@var{convergence_criterion} =} compute_cluster_convergence (@var{V}, @var{V_previous})
##
## Compute the sum of the changes in position (using the Euclidean
## distance) of the cluster prototypes.
##
## @seealso{fcm, gustafson_kessel, init_cluster_prototypes, update_cluster_membership, update_cluster_prototypes, compute_cluster_obj_fcn}
##
## @end deftypefn

## Author:        L. Markowsky
## Keywords:      fuzzy-logic-toolkit fuzzy partition clustering
## Directory:     fuzzy-logic-toolkit/inst/private/
## Filename:      compute_cluster_convergence.m
## Last-Modified: 2 Sep 2012

function convergence_criterion = ...
  compute_cluster_convergence (V, V_previous)

  V_delta = V - V_previous;
  convergence_criterion = sum (sqrt (sum (V_delta .* V_delta)'));

endfunction
