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
## @deftypefn {Function File} {@var{Mu} =} update_cluster_membership (@var{V}, @var{X}, @var{m}, @var{k}, @var{n}, @var{sqr_dist})
##
## Compute Mu for each (cluster center, input point) pair.
##
## @seealso{fcm, gustafson_kessel, init_cluster_prototypes, update_cluster_prototypes, compute_cluster_obj_fcn, compute_cluster_convergence}
##
## @end deftypefn

## Author:        L. Markowsky
## Keywords:      fuzzy-logic-toolkit fuzzy partition clustering
## Directory:     fuzzy-logic-toolkit/inst/private/
## Filename:      update_cluster_membership.m
## Last-Modified: 2 Sep 2012

##----------------------------------------------------------------------
## Note:     This function is an implementation of Equation 13.4 in
##           Fuzzy Logic: Intelligence, Control and Information, by
##           J. Yen and R. Langari, Prentice Hall, 1999, page 380
##           (International Edition) and Step 3 of Algorithm 4.1 in
##           Fuzzy and Neural Control, by Robert Babuska, November 2009,
##           p. 63.
##----------------------------------------------------------------------

function Mu = update_cluster_membership (V, X, m, k, n, sqr_dist)

  Mu = zeros (k, n);

  if (min (min (sqr_dist)) > 0)
    exponent = 1.0 / (m - 1);
    for i = 1 : k
      for j = 1 : n
        summation = 0.0;
        for l = 1 : k
          summation += (sqr_dist(i, j) / sqr_dist(l, j))^exponent;
        endfor
        if (summation != 0)
          Mu(i, j) = 1.0 / summation;
        else
          error ("division by 0 in update_cluster_membership'\n");
        endif
      endfor
    endfor

  else
    num_zeros = 0;
    for i = 1 : k
      for j = 1 : n
        if (sqr_dist(i, j) == 0)
          num_zeros++;
          Mu(i, j) = 1.0;
        endif
      endfor
    endfor
    Mu = Mu / num_zeros;
  endif

endfunction
