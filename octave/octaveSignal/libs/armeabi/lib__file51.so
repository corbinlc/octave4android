## Copyright (C) 2000 Paul Kienzle <pkienzle@users.sf.net>
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

## usage: b = fir1(n, w [, type] [, window] [, noscale])
##
## Produce an order n FIR filter with the given frequency cutoff,
## returning the n+1 filter coefficients in b.  
##
## n: order of the filter (1 less than the length of the filter)
## w: band edges
##    strictly increasing vector in range [0, 1]
##    singleton for highpass or lowpass, vector pair for bandpass or
##    bandstop, or vector for alternating pass/stop filter.
## type: choose between pass and stop bands
##    'high' for highpass filter, cutoff at w
##    'stop' for bandstop filter, edges at w = [lo, hi]
##    'DC-0' for bandstop as first band of multiband filter
##    'DC-1' for bandpass as first band of multiband filter
## window: smoothing window
##    defaults to hamming(n+1) row vector
##    returned filter is the same shape as the smoothing window
## noscale: choose whether to normalize or not
##    'scale': set the magnitude of the center of the first passband to 1
##    'noscale': don't normalize
##
## To apply the filter, use the return vector b:
##       y=filter(b,1,x);
##
## Examples:
##   freqz(fir1(40,0.3));
##   freqz(fir1(15,[0.2, 0.5], 'stop'));  # note the zero-crossing at 0.1
##   freqz(fir1(15,[0.2, 0.5], 'stop', 'noscale'));

## TODO: Consider using exact expression (in terms of sinc) for the
## TODO:    impulse response rather than relying on fir2.
## TODO: Find reference to the requirement that order be even for
## TODO:    filters that end high.  Figure out what to do with the
## TODO:    window in these cases
function b = fir1(n, w, varargin)

  if nargin < 2 || nargin > 5
    print_usage;
  endif
  
  ## Assign default window, filter type and scale.
  ## If single band edge, the first band defaults to a pass band to 
  ## create a lowpass filter.  If multiple band edges, the first band 
  ## defaults to a stop band so that the two band case defaults to a 
  ## band pass filter.  Ick.
  window  = [];
  scale   = 1;
  ftype   = (length(w)==1);

  ## sort arglist, normalize any string
  for i=1:length(varargin)
    arg = varargin{i}; 
    if ischar(arg), arg=lower(arg);end
    if isempty(arg) continue; end  # octave bug---can't switch on []
    switch arg
      case {'low','stop','dc-1'},             ftype  = 1;
      case {'high','pass','bandpass','dc-0'}, ftype  = 0;
      case {'scale'},                         scale  = 1;
      case {'noscale'},                       scale  = 0;
      otherwise                               window = arg;
    end
  endfor

  ## build response function according to fir2 requirements
  bands = length(w)+1;
  f = zeros(1,2*bands);
  f(1) = 0; f(2*bands)=1;
  f(2:2:2*bands-1) = w;
  f(3:2:2*bands-1) = w;
  m = zeros(1,2*bands);
  m(1:2:2*bands) = rem([1:bands]-(1-ftype),2);
  m(2:2:2*bands) = m(1:2:2*bands);

  ## Increment the order if the final band is a pass band.  Something
  ## about having a nyquist frequency of zero causing problems.
  if rem(n,2)==1 && m(2*bands)==1, 
    warning("n must be even for highpass and bandstop filters. Incrementing.");
    n = n+1;
    if isvector(window) && isreal(window) && !ischar(window)
      ## Extend the window using interpolation
      M = length(window);
      if M == 1,
        window = [window; window];
      elseif M < 4
        window = interp1(linspace(0,1,M),window,linspace(0,1,M+1),'linear');
      else
        window = interp1(linspace(0,1,M),window,linspace(0,1,M+1),'spline');
      endif
    endif
  endif

  ## compute the filter
  b = fir2(n, f, m, 512, 2, window);

  ## normalize filter magnitude
  if scale == 1
    ## find the middle of the first band edge
    ## find the frequency of the normalizing gain
    if m(1) == 1
      ## if the first band is a passband, use DC gain
      w_o = 0;
    elseif f(4) == 1
      ## for a highpass filter,
      ## use the gain at half the sample frequency
      w_o = 1;
    else
      ## otherwise, use the gain at the center
      ## frequency of the first passband
      w_o = f(3) + (f(4)-f(3))/2;
    endif

    ## compute |h(w_o)|^-1
    renorm = 1/abs(polyval(b, exp(-1i*pi*w_o)));

    ## normalize the filter
    b = renorm*b;
  endif
endfunction

%!demo
%! freqz(fir1(40,0.3));
%!demo
%! freqz(fir1(15,[0.2, 0.5], 'stop'));  # note the zero-crossing at 0.1
%!demo
%! freqz(fir1(15,[0.2, 0.5], 'stop', 'noscale'));

%!assert(fir1(2, .5, 'low', @hanning, 'scale'), [0 1 0]);
%!assert(fir1(2, .5, 'low', "hanning", 'scale'), [0 1 0]);
%!assert(fir1(2, .5, 'low', hanning(3), 'scale'), [0 1 0]);

%!assert(fir1(10,.5,'noscale'), fir1(10,.5,'low','hamming','noscale'));
%!assert(fir1(10,.5,'high'), fir1(10,.5,'high','hamming','scale'));
%!assert(fir1(10,.5,'boxcar'), fir1(10,.5,'low','boxcar','scale'));
%!assert(fir1(10,.5,'hanning','scale'), fir1(10,.5,'scale','hanning','low'));
%!assert(fir1(10,.5,'haNNing','NOscale'), fir1(10,.5,'noscale','Hanning','LOW'));
%!assert(fir1(10,.5,'boxcar',[]), fir1(10,.5,'boxcar'));
