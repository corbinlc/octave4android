## Copyright (C) 2008 Bill Denney <bill@denney.ws>
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
## @deftypefn {Function File} {} bolling (@var{asset}, @var{samples})
## @deftypefnx {Function File} {} bolling (@var{asset}, @var{samples}, @var{alpha})
## @deftypefnx {Function File} {} bolling (@var{asset}, @var{samples}, @var{alpha}, @var{width})
## @deftypefnx {Function File} {[@var{movavg}, @var{upperband}, @var{lowerband}] =} bolling (@var{asset}, @var{samples}, ...)
##
## If no output is requested, plot the bollinger bands of the
## @var{asset}. If output is requested, return the values for the
## bollinger bands. If given, @var{alpha} is the weighting power of the
## moving average; 0 (default) is the simple moving average, see
## @code{movavg} for the full definition.  @var{width} is the number of
## standard deviations to plot above and below the moving average
## (default: 2).
##
## @seealso{movavg, candle, dateaxis, highlow, pointfig}
## @end deftypefn

function [varargout] = bolling (asset, samples, alpha, width)

  ## Check input and set the defaults
  if nargin < 2 || nargin > 4
    print_usage ();
  elseif nargin < 3
    alpha = 0;
  endif
  if nargin < 4
    width = 2;
  endif

  if samples > length (asset)
    error ("Samples must be <= the length of the asset")
  endif

  ## the moving average and the standard deviation
  avg = movavg(asset, samples, samples, alpha);
  s   = zeros(size(avg));

  ## Assume that the standard deviation is constant for the first samples
  ## FIXME: is this what matlab assumes
  s(1:samples) = std (asset(1:samples));
  for i = samples+1:length (asset)
    s(i) = std (asset(i - samples + 1:i));
  endfor

  if nargout > 0
    varargout{1} = avg;
  else
    plot((1:length(avg))', [avg(:), avg(:)+s(:), avg(:)-s(:)]);
  endif
  if nargout > 1
    varargout{2} = avg + s;
  endif
  if nargout > 2
    varargout{3} = avg - s;
  endif

endfunction
