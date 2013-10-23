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
## @deftypefn {Function File} {@var{V} =} update_cluster_prototypes (@var{Mu_m}, @var{X}, @var{k})
##
## Update the cluster centers to correspond to the given membership
## function values.
##
## @seealso{fcm, gustafson_kessel, init_cluster_prototypes, update_cluster_membership, compute_cluster_obj_fcn, compute_cluster_convergence}
##
## @end deftypefn

## Author:        L. Markowsky
## Keywords:      fuzzy-logic-toolkit fuzzy partition clustering
## Directory:     fuzzy-logic-toolkit/inst/private/
## Filename:      update_cluster_prototypes.m
## Last-Modified: 2 Sep 2012

##----------------------------------------------------------------------
## Note:     This function is an implementation of Equation 13.5 in
##           Fuzzy Logic: Intelligence, Control and Information, by
##           J. Yen and R. Langari, Prentice Hall, 1999, page 380
##           (International Edition). 
##----------------------------------------------------------------------

function V = update_cluster_prototypes (Mu_m, X, k)

  V = Mu_m * X;
  sum_Mu_m = sum (Mu_m');

  if (prod (sum_Mu_m) == 0)
    error ("division by 0 in function update_cluster_prototypes\n");
  endif

  for i = 1 : k
    V(i, :) /= sum_Mu_m(i);
  endfor

endfunction
