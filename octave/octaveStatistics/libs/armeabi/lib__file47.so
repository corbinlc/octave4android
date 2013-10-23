## Copyright (C) 2001 Paul Kienzle <pkienzle@users.sf.net>
## Copyright (C) 2003 Alois Schloegl <alois.schloegl@ist.ac.at>
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
## @deftypefn {Function File} {[@var{v}, @var{idx}] =} nanmin (@var{X})
## @deftypefnx{Function File} {[@var{v}, @var{idx}] =} nanmin (@var{X}, @var{Y})
## Find the minimal element while ignoring NaN values.
##
## @code{nanmin} is identical to the @code{min} function except that NaN values
## are ignored.  If all values in a column are NaN, the minimum is 
## returned as NaN rather than []. 
##
## @seealso{min, nansum, nanmax, nanmean, nanmedian}
## @end deftypefn

function [v, idx] = nanmin (X, Y, DIM) 
  if nargin < 1 || nargin > 3
    print_usage;
  elseif nargin == 1 || (nargin == 2 && isempty(Y))
    nanvals = isnan(X);
    X(nanvals) = Inf;
    v = min (X);
    v(all(nanvals)) = NaN;
  elseif (nargin == 3 && isempty(Y))
    nanvals = isnan(X);
    X(nanvals) = Inf;
    v = min (X,[],DIM);
    v(all(nanvals,DIM)) = NaN;
  else
    Xnan = isnan(X);
    Ynan = isnan(Y);
    X(Xnan) = Inf;
    Y(Ynan) = Inf;
    if (nargin == 3)
      v = min(X,Y,DIM);
    else
      v = min(X,Y);
    endif
    v(Xnan & Ynan) = NaN;
  endif
endfunction
