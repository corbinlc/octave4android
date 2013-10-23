## Copyright (C) 2003 Alberto Terruzzi <t-albert@libero.it>
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
## @deftypefn {Function File} {@var{table} =} tabulate (@var{data}, @var{edges})
##
## Compute a frequency table.
##
## For vector data, the function counts the number of
## values in data that fall between the elements in the edges vector
## (which must contain monotonically non-decreasing values). @var{table} is a
## matrix.
## The first column of @var{table} is the number of bin, the second
## is the number of instances in each class (absolute frequency). The
## third column contains the percentage of each value (relative
## frequency) and the fourth column contains the cumulative frequency.
##
## If @var{edges} is missed the width of each class is unitary, if @var{edges}
## is a scalar then represent the number of classes, or you can define the
## width of each bin.
## @var{table}(@var{k}, 2) will count the value @var{data} (@var{i}) if 
## @var{edges} (@var{k}) <= @var{data} (@var{i}) < @var{edges} (@var{k}+1).
## The  last bin will count the value of @var{data} (@var{i}) if
## @var{edges}(@var{k}) <= @var{data} (@var{i}) <=  @var{edges} (@var{k}+1).  
## Values outside the values in @var{edges} are not counted.  Use -inf and inf
## in @var{edges} to include all values. 
## Tabulate with no output arguments returns a formatted table in the
## command window. 
##
## Example
##
## @example
## sphere_radius = [1:0.05:2.5];
## tabulate (sphere_radius)
## @end example
##
## Tabulate returns 2 bins, the first contains the sphere with radius
## between 1 and 2 mm excluded, and the second one contains the sphere with
## radius between 2 and 3 mm.
##
## @example
## tabulate (sphere_radius, 10)
## @end example
##
## Tabulate returns ten bins.
##
## @example
## tabulate (sphere_radius, [1, 1.5, 2, 2.5])
## @end example
##
## Tabulate returns three bins, the first contains the sphere with radius
## between 1 and 1.5 mm excluded, the second one contains the sphere with
## radius between 1.5 and 2 mm excluded, and the third contains the sphere with
## radius between 2 and 2.5 mm. 
##
## @example
## bar (table (:, 1), table (:, 2))
## @end example
##
## draw histogram.
##
## @seealso{bar, pareto}
## @end deftypefn

## Author: Alberto Terruzzi <t-albert@libero.it>
## Version: 1.0
## Created: 13 February 2003

function table = tabulate (varargin)

  if nargin < 1 || nargin > 2
    print_usage;
  endif

  data = varargin{1};
  if isvector (data) != 1
    error ("data must be a vector.");
  endif
  n = length(data);
  m = min(data);
  M = max(data);

  if nargin == 1 edges = 1:1:max(data)+1;
  else edges = varargin{2};
  end 

  if isscalar(edges)
    h=(M-m)/edges;
    edges = [m:h:M];
  end

  # number of classes
  bins=length(edges)-1;
  # initialize freqency table
  freqtable = zeros(bins,4);

  for k=1:1:bins;
    if k != bins
      freqtable(k,2)=length(find (data >= edges(k) & data < edges(k+1)));
    else
      freqtable(k,2)=length(find (data >= edges(k) & data <= edges(k+1)));
    end
    if k == 1 freqtable (k,4) = freqtable(k,2);
    else freqtable(k,4) = freqtable(k-1,4) + freqtable(k,2); 
    end
  end

  freqtable(:,1) = edges(1:end-1)(:);
  freqtable(:,3) = 100*freqtable(:,2)/n;

  if nargout == 0
    disp("     bin     Fa       Fr%        Fc");
    printf("%8g  %5d    %6.2f%%    %5d\n",freqtable');
  else table = freqtable;
  end

endfunction
