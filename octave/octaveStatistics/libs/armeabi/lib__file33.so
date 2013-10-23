## Copyright (C) 2008 Francesco Potortì <pot@gnu.org>
##
## This program is free software; you can redistribute it and/or modify it under
## the terms of the GNU General Public License as published by the Free Software
## Foundation; either version 3 of the License, or (at your option) any later
## version.
##
## This program is distributed in the hope that it will be useful, but WITHOUT
## ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
## FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
## details.
##
## You should have received a copy of the GNU General Public License along with
## this program; if not, see <http://www.gnu.org/licenses/>.

## -*- texinfo -*-
## @deftypefn {Function File} {@var{y} =} linkage (@var{d})
## @deftypefnx {Function File} {@var{y} =} linkage (@var{d}, @var{method})
## @deftypefnx {Function File} @
##   {@var{y} =} linkage (@var{x}, @var{method}, @var{metric})
## @deftypefnx {Function File} @
##   {@var{y} =} linkage (@var{x}, @var{method}, @var{arglist})
##
## Produce a hierarchical clustering dendrogram
##
## @var{d} is the dissimilarity matrix relative to @var{n} observations,
## formatted as a @math{(n-1)*n/2}x1 vector as produced by @code{pdist}.
## Alternatively, @var{x} contains data formatted for input to
## @code{pdist}, @var{metric} is a metric for @code{pdist} and
## @var{arglist} is a cell array containing arguments that are passed to
## @code{pdist}.
##
## @code{linkage} starts by putting each observation into a singleton
## cluster and numbering those from 1 to @var{n}.  Then it merges two
## clusters, chosen according to @var{method}, to create a new cluster
## numbered @var{n+1}, and so on until all observations are grouped into
## a single cluster numbered @var{2*n-1}.  Row @var{m} of the
## @math{m-1}x3 output matrix relates to cluster @math{n+m}: the first
## two columns are the numbers of the two component clusters and column
## 3 contains their distance.
##
## @var{method} defines the way the distance between two clusters is
## computed and how they are recomputed when two clusters are merged:
##
## @table @samp
## @item "single" (default)
## Distance between two clusters is the minimum distance between two
## elements belonging each to one cluster.  Produces a cluster tree
## known as minimum spanning tree.
##
## @item "complete"
## Furthest distance between two elements belonging each to one cluster.
##
## @item "average"
## Unweighted pair group method with averaging (UPGMA).
## The mean distance between all pair of elements each belonging to one
## cluster.
##
## @item "weighted"
## Weighted pair group method with averaging (WPGMA).
## When two clusters A and B are joined together, the new distance to a
## cluster C is the mean between distances A-C and B-C.
##
## @item "centroid"
## Unweighted Pair-Group Method using Centroids (UPGMC).
## Assumes Euclidean metric.  The distance between cluster centroids,
## each centroid being the center of mass of a cluster.
##
## @item "median"
## Weighted pair-group method using centroids (WPGMC).
## Assumes Euclidean metric.  Distance between cluster centroids.  When
## two clusters are joined together, the new centroid is the midpoint
## between the joined centroids.
##
## @item "ward"
## Ward's sum of squared deviations about the group mean (ESS).
## Also known as minimum variance or inner squared distance.
## Assumes Euclidean metric.  How much the moment of inertia of the
## merged cluster exceeds the sum of those of the individual clusters.
## @end table
##
## @strong{Reference}
## Ward, J. H. Hierarchical Grouping to Optimize an Objective Function
## J. Am. Statist. Assoc. 1963, 58, 236-244,
## @url{http://iv.slis.indiana.edu/sw/data/ward.pdf}.
## @end deftypefn
##
## @seealso{pdist,squareform}

## Author: Francesco Potortì  <pot@gnu.org>

function dgram = linkage (d, method = "single", distarg)

  ## check the input
  if (nargin < 1) || (nargin > 3)
    print_usage ();
  endif

  if (isempty (d))
    error ("linkage: d cannot be empty");
  elseif ( nargin < 3 && ~ isvector (d))
    error ("linkage: d must be a vector");
  endif

  methods = struct ...
  ("name", { "single"; "complete"; "average"; "weighted";
            "centroid"; "median"; "ward" },
   "distfunc", {(@(x) min(x))                                # single
                (@(x) max(x))                                # complete
                (@(x,i,j,w) sum(diag(q=w([i,j]))*x)/sum(q))  # average
                (@(x) mean(x))                               # weighted
                (@massdist)                                  # centroid
                (@(x,i) massdist(x,i))                       # median
                (@inertialdist)                              # ward
   });
  mask = strcmp (lower (method), {methods.name});
  if (! any (mask))
    error ("linkage: %s: unknown method", method);
  endif
  dist = {methods.distfunc}{mask};

  if (nargin == 3)
    if (ischar (distarg))
      d = pdist (d, distarg);
    elseif (iscell (distarg))
      d = pdist (d, distarg{:});
    else
      print_usage ();
    endif
  endif

  d = squareform (d, "tomatrix");      # dissimilarity NxN matrix
  n = rows (d);                        # the number of observations
  diagidx = sub2ind ([n,n], 1:n, 1:n); # indices of diagonal elements
  d(diagidx) = Inf;     # consider a cluster as far from itself
  ## For equal-distance nodes, the order in which clusters are
  ## merged is arbitrary.  Rotating the initial matrix produces an
  ## ordering similar to Matlab's.
  cname = n:-1:1;               # cluster names in d
  d = rot90 (d, 2);             # exchange low and high cluster numbers
  weight = ones (1, n);         # cluster weights
  dgram = zeros (n-1, 3);       # clusters from n+1 to 2*n-1
  for cluster = n+1:2*n-1
    ## Find the two nearest clusters
    [m midx] = min (d(:));
    [r, c] = ind2sub (size (d), midx);
    ## Here is the new cluster
    dgram(cluster-n, :) = [cname(r) cname(c) d(r, c)];
    ## Put it in place of the first one and remove the second
    cname(r) = cluster;
    cname(c) = [];
    ## Compute the new distances
    newd = dist (d([r c], :), r, c, weight);
    newd(r) = Inf;              # take care of the diagonal element
    ## Put distances in place of the first ones, remove the second ones
    d(r,:) = newd;
    d(:,r) = newd';
    d(c,:) = [];
    d(:,c) = [];
    ## The new weight is the sum of the components' weights
    weight(r) += weight(c);
    weight(c) = [];
  endfor
  ## Sort the cluster numbers, as Matlab does
  dgram(:,1:2) = sort (dgram(:,1:2), 2);

  ## Check that distances are monotonically increasing
  if (any (diff (dgram(:,3)) < 0))
    warning ("clustering",
             "linkage: cluster distances do not monotonically increase\n\
        you should probably use a method different from \"%s\"", method);
  endif

endfunction

## Take two row vectors, which are the Euclidean distances of clusters I
## and J from the others.  Column I of second row contains the distance
## between clusters I and J.  The centre of gravity of the new cluster
## is on the segment joining the old ones. W are the weights of all
## clusters. Use the law of cosines to find the distances of the new
## cluster from all the others.
function y = massdist (x, i, j, w)
  x .^= 2;                      # squared Euclidean distances
  if (nargin == 2)              # median distance
    qi = 0.5;                   # equal weights ("weighted")
  else                          # centroid distance
    qi = 1 / (1 + w(j) / w(i)); # proportional weights ("unweighted")
  endif
  y = sqrt (qi*x(1,:) + (1-qi)*(x(2,:) - qi*x(2,i)));
endfunction

## Take two row vectors, which are the inertial distances of clusters I
## and J from the others.  Column I of second row contains the inertial
## distance between clusters I and J. The centre of gravity of the new
## cluster K is on the segment joining I and J.  W are the weights of
## all clusters.  Convert inertial to Euclidean distances, then use the
## law of cosines to find the Euclidean distances of K from all the
## other clusters, convert them back to inertial distances and return
## them.
function y = inertialdist (x, i, j, w)
  wi = w(i); wj = w(j); # the cluster weights
  s = [wi + w; wj + w]; # sum of weights for all cluster pairs
  p = [wi * w; wj * w]; # product of weights for all cluster pairs
  x = x.^2 .* s ./ p;   # convert inertial dist. to squared Eucl.
  sij = wi + wj;        # sum of weights of I and J
  qi = wi/sij;          # normalise the weight of I
  ## Squared Euclidean distances between all clusters and new cluster K
  x = qi*x(1,:) + (1-qi)*(x(2,:) - qi*x(2,i));
  y = sqrt (x * sij .* w ./ (sij + w)); # convert Eucl. dist. to inertial
endfunction

%!shared x, t
%! x = reshape(mod(magic(6),5),[],3);
%! t = 1e-6;
%!assert (cond (linkage (pdist (x))),              34.119045,t);
%!assert (cond (linkage (pdist (x), "complete")),  21.793345,t);
%!assert (cond (linkage (pdist (x), "average")),   27.045012,t);
%!assert (cond (linkage (pdist (x), "weighted")),  27.412889,t);
%! lastwarn(); # Clear last warning before the test
%!warning <monotonically> linkage (pdist (x), "centroid");
%!test warning off clustering
%! assert (cond (linkage (pdist (x), "centroid")), 27.457477,t);
%! warning on clustering
%!warning <monotonically> linkage (pdist (x), "median");
%!test warning off clustering
%! assert (cond (linkage (pdist (x), "median")),   27.683325,t);
%! warning on clustering
%!assert (cond (linkage (pdist (x), "ward")),      17.195198,t);
%!assert (cond (linkage(x,"ward","euclidean")),    17.195198,t);
%!assert (cond (linkage(x,"ward",{"euclidean"})),  17.195198,t);
%!assert (cond (linkage(x,"ward",{"minkowski",2})),17.195198,t);
