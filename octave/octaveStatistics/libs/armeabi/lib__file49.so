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
## @deftypefn {Function File} {@var{v} =} nansum (@var{X})
## @deftypefnx{Function File} {@var{v} =} nansum (@var{X}, @var{dim})
## Compute the sum while ignoring NaN values.
##
## @code{nansum} is identical to the @code{sum} function except that NaN values are
## treated as 0 and so ignored.  If all values are NaN, the sum is 
## returned as 0.
##
## @seealso{sum, nanmin, nanmax, nanmean, nanmedian}
## @end deftypefn

function v = nansum (X, varargin)
  if nargin < 1
    print_usage;
  else
    X(isnan(X)) = 0;
    v = sum (X, varargin{:});
  endif
endfunction
