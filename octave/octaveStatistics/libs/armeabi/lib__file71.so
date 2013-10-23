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
## @deftypefn {Function File} {@var{a} =} trimmean (@var{x}, @var{p})
##
## Compute the trimmed mean.
##
## The trimmed mean of @var{x} is defined as the mean of @var{x} excluding the
## highest and lowest @var{p} percent of the data.
##
## For example
##
## @example
## mean ([-inf, 1:9, inf])
## @end example
##
## is NaN, while
##
## @example
## trimmean ([-inf, 1:9, inf], 10)
## @end example
##
## excludes the infinite values, which make the result 5.
##
## @seealso{mean}
## @end deftypefn

function a = trimmean(x, p, varargin)
  if (nargin != 2 && nargin != 3)
    print_usage;
  endif
  y = sort(x, varargin{:});
  sz = size(x);
  if nargin < 3
    dim = min(find(sz>1));
    if isempty(dim), dim=1; endif;
  else
    dim = varargin{1};
  endif
  idx = cell (0);
  for i=1:length(sz), idx{i} = 1:sz(i); end;
  trim = round(sz(dim)*p*0.01);
  idx{dim} = 1+trim : sz(dim)-trim;
  a = mean (y (idx{:}), varargin{:});
endfunction
