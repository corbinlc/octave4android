## Copyright (C) 1999, 2001 Paul Kienzle <pkienzle@users.sf.net>
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
## @deftypefn {Function File} {[@var{R}, @var{lag}] =} xcov ( @var{X} )
## @deftypefnx {Function File} {@dots{} =} xcov ( @var{X}, @var{Y} )
## @deftypefnx {Function File} {@dots{} =} xcov ( @dots{}, @var{maxlag})
## @deftypefnx {Function File} {@dots{} =} xcov ( @dots{}, @var{scale})
## Compute covariance at various lags [=correlation(x-mean(x),y-mean(y))].
##
## @table @var
## @item X
## input vector
## @item Y
## if specified, compute cross-covariance between X and Y,
## otherwise compute autocovariance of X.
## @item maxlag
## is specified, use lag range [-maxlag:maxlag],
## otherwise use range [-n+1:n-1].
## @item scale:
## @table @samp
## @item biased
## for covariance=raw/N,
## @item unbiased
## for covariance=raw/(N-|lag|),
## @item coeff
## for covariance=raw/(covariance at lag 0),
## @item none
## for covariance=raw
## @item none
## is the default.
## @end table
## @end table
##
## Returns the covariance for each lag in the range, plus an
## optional vector of lags.
##
## @seealso{xcorr}
## @end deftypefn

function [retval, lags] = xcov (X, Y, maxlag, scale)

  if (nargin < 1 || nargin > 4)
    print_usage;
  endif

  if nargin==1
    Y=[]; maxlag=[]; scale=[];
  elseif nargin==2
    maxlag=[]; scale=[];
    if ischar(Y), scale=Y; Y=[];
    elseif isscalar(Y), maxlag=Y; Y=[];
    endif
  elseif nargin==3
    scale=[];
    if ischar(maxlag), scale=maxlag; maxlag=[]; endif
    if isscalar(Y), maxlag=Y; Y=[]; endif
  endif

  ## XXX FIXME XXX --- should let center(Y) deal with []
  ## [retval, lags] = xcorr(center(X), center(Y), maxlag, scale);
  if (!isempty(Y))
    [retval, lags] = xcorr(center(X), center(Y), maxlag, scale);
  else
    [retval, lags] = xcorr(center(X), maxlag, scale);
  endif

endfunction
