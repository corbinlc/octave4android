## Copyright (C) 1999 Paul Kienzle <pkienzle@users.sf.net>
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

## usage:  [P, w] = __power (b, a, [, nfft [, Fs]] [, range] [, units])
## 
## Plot the power spectrum of the given ARMA model.
##
## b, a: filter coefficients (b=numerator, a=denominator)
## nfft is number of points at which to sample the power spectrum
## Fs is the sampling frequency of x
## range is 'half' (default) or 'whole'
## units is  'squared' or 'db' (default)
## range and units may be specified any time after the filter, in either
## order
##
## Returns P, the magnitude vector, and w, the frequencies at which it
## is sampled.  If there are no return values requested, then plot the power
## spectrum and don't return anything.

## TODO: consider folding this into freqz --- just one more parameter to
## TODO:    distinguish between 'linear', 'log', 'logsquared' and 'squared'

function [varargout] = __power (b, a, varargin)
  if (nargin < 2 || nargin > 6) print_usage; endif

  nfft = [];
  Fs = [];
  range = [];
  range_fact = 1.0;
  units = [];

  pos = 0;
  for i=1:length(varargin)
    arg = varargin{i};
    if strcmp(arg, 'squared') || strcmp(arg, 'db')
      units = arg;
    elseif strcmp(arg, 'whole')
      range = arg;
      range_fact = 1.0;
    elseif strcmp(arg, 'half')
      range = arg;
      range_fact = 2.0;
    elseif ischar(arg)
      usage(usagestr);
    elseif pos == 0
      nfft = arg;
      pos++;
    elseif pos == 1
      Fs = arg;
      pos++;
    else
      usage(usagestr);
    endif
  endfor
  
  if isempty(nfft); nfft = 256; endif
  if isempty(Fs); Fs = 2; endif
  if isempty(range)
    range = 'half';
    range_fact = 2.0;
    endif
  
  [P, w] = freqz(b, a, nfft, range, Fs);

  P = (range_fact/Fs)*(P.*conj(P));
  if nargout == 0,
    if strcmp(units, 'squared')
      plot(w, P, ";;");
    else
      plot(w, 10.0*log10(abs(P)), ";;");
    endif
  endif
  if nargout >= 1, varargout{1} = P; endif
  if nargout >= 2, varargout{2} = w; endif

endfunction

