## Copyright (C) 2011 Soren Hauberg <soren@hauberg.org>
## Copyright (C) 2012 Daniel Ward <dwa012@gmail.com>
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
## @deftypefn {Function File} {[@var{idx}, @var{centers}] =} kmeans (@var{data}, @var{k}, @var{param1}, @var{value1}, @dots{})
## K-means clustering.
##
## @seealso{linkage}
## @end deftypefn

function [classes, centers, sumd, D] = kmeans (data, k, varargin)
  [reg, prop] = parseparams (varargin);

  ## defaults for options
  emptyaction = "error";
  start       = "sample";

  #used for getting the number of samples
  nRows = rows (data);

  ## used to hold the distances from each sample to each class
  D = zeros (nRows, k);
  
  #used for convergence of the centroids
  err = 1;
  
  #initial sum of distances
  sumd = Inf;
  
  ## Input checking, validate the matrix and k
  if (!isnumeric (data) || !ismatrix (data) || !isreal (data))
    error ("kmeans: first input argument must be a DxN real data matrix");
  elseif (!isscalar (k))
    error ("kmeans: second input argument must be a scalar");
  endif
  
  if (length (varargin) > 0)
    ## check for the 'emptyaction' property
    found = find (strcmpi (prop, "emptyaction") == 1);
    switch (lower (prop{found+1}))
      case "singleton"
        emptyaction = "singleton";
      otherwise
        error ("kmeans: unsupported empty cluster action parameter");
    endswitch
  endif
  
  ## check for the 'start' property
  switch (lower (start))
    case "sample"
      idx = randperm (nRows) (1:k);
      centers = data (idx, :);
    otherwise
      error ("kmeans: unsupported initial clustering parameter");
  endswitch
  
  ## Run the algorithm
  while err > .001
    ## Compute distances
    for i = 1:k
      D (:, i) = sumsq (data - repmat (centers(i, :), nRows, 1), 2);
    endfor
    
    ## Classify
    [tmp, classes] = min (D, [], 2);
    
    ## Calcualte new centroids
    for i = 1:k
      ## Check for empty clusters
      if (sum (classes == i) ==0 || length (mean (data(classes == i, :))) == 0)
      
        switch emptyaction
          ## if 'singleton', then find the point that is the
          ## farthest and add it to the empty cluster
          case 'singleton'
            classes(maxCostSampleIndex (data, centers(i,:))) = i;
         ## if 'error' then throw the error
          otherwise
            error ("kmeans: empty cluster created");
        endswitch
     endif ## end check for empty clusters

      ## update the centroids
      centers(i, :) = mean (data(classes == i, :));
    endfor

    ## calculate the differnece in the sum of distances
    err  = sumd - objCost (data, classes, centers);
    ## update the current sum of distances
    sumd = objCost (data, classes, centers);
  endwhile
endfunction

## calculate the sum of distances
function obj = objCost (data, classes, centers)
  obj = 0;
    for i=1:rows (data)
      obj = obj + sumsq (data(i,:) - centers(classes(i),:));
    endfor
endfunction

function index = maxCostSampleIndex (data, centers)
  cost = 0;
  for index = 1:rows (data)
    if cost < sumsq (data(index,:) - centers)
      cost = sumsq (data(index,:) - centers);
    endif
  endfor
endfunction

%!demo
%! ## Generate a two-cluster problem
%! C1 = randn (100, 2) + 1;
%! C2 = randn (100, 2) - 1;
%! data = [C1; C2];
%!
%! ## Perform clustering
%! [idx, centers] = kmeans (data, 2);
%!
%! ## Plot the result
%! figure
%! plot (data (idx==1, 1), data (idx==1, 2), 'ro');
%! hold on
%! plot (data (idx==2, 1), data (idx==2, 2), 'bs');
%! plot (centers (:, 1), centers (:, 2), 'kv', 'markersize', 10);
%! hold off
