## Copyright (C) 2001 Paul Kienzle <pkienzle@users.sf.net>
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
## @deftypefn {Function File} @var{v} = nanmedian (@var{x})
## @deftypefnx{Function File} @var{v} = nanmedian (@var{x}, @var{dim})
## Compute the median of data while ignoring NaN values.
##
## This function is identical to the @code{median} function except that NaN values
## are ignored.  If all values are NaN, the median is returned as NaN. 
##
## @seealso{median, nanmin, nanmax, nansum, nanmean}
## @end deftypefn

function v = nanmedian (X, varargin)
  if nargin < 1 || nargin > 2
    print_usage;
  endif
  if nargin < 2
    dim = min(find(size(X)>1));
    if isempty(dim), dim=1; endif;
  else
    dim = varargin{:};
  endif

  sz = size (X);
  if (prod (sz) > 1)
      ## Find lengths of datasets after excluding NaNs; valid datasets
      ## are those that are not empty after you remove all the NaNs
      n = sz(dim) - sum (isnan(X),varargin{:});

      ## When n is equal to zero, force it to one, so that median
      ## picks up a NaN value below
      n (n==0) = 1;

      ## Sort the datasets, with the NaN going to the end of the data
      X = sort (X, varargin{:});

      ## Determine the offset for each column in single index mode
      colidx = reshape((0:(prod(sz) / sz(dim) - 1)), size(n)); 
      colidx = floor(colidx / prod(sz(1:dim-1))) * prod(sz(1:dim)) + ...
          mod(colidx,prod(sz(1:dim-1)));
      stride = prod(sz(1:dim-1));

      ## Average the two central values of the sorted list to compute
      ## the median, but only do so for valid rows.  If the dataset
      ## is odd length, the single central value will be used twice.
      ## E.g., 
      ##   for n==5, ceil(2.5+0.5) is 3 and floor(2.5+0.5) is also 3
      ##   for n==6, ceil(3.0+0.5) is 4 and floor(3.0+0.5) is 3
      ## correction made for stride of data "stride*ceil(2.5-0.5)+1"
      v = (X(colidx + stride*ceil(n./2-0.5) + 1)  + ...
           X(colidx + stride*floor(n./2-0.5) + 1)) ./ 2;
  else
    error ("nanmedian: invalid matrix argument");
  endif
endfunction
