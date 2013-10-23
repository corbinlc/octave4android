## Copyright (C) 1999 Paul Kienzle <pkienzle@users.sf.net>
## Copyright (C) 2003 Doug Stewart <dastew@sympatico.ca>
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

## Generate an Chebyshev type I filter with Rp dB of pass band ripple.
## 
## [b, a] = cheby1(n, Rp, Wc)
##    low pass filter with cutoff pi*Wc radians
##
## [b, a] = cheby1(n, Rp, Wc, 'high')
##    high pass filter with cutoff pi*Wc radians
##
## [b, a] = cheby1(n, Rp, [Wl, Wh])
##    band pass filter with edges pi*Wl and pi*Wh radians
##
## [b, a] = cheby1(n, Rp, [Wl, Wh], 'stop')
##    band reject filter with edges pi*Wl and pi*Wh radians
##
## [z, p, g] = cheby1(...)
##    return filter as zero-pole-gain rather than coefficients of the
##    numerator and denominator polynomials.
##
## [...] = cheby1(...,'s')
##     return a Laplace space filter, W can be larger than 1.
## 
## [a,b,c,d] = cheby1(...)
##  return  state-space matrices 
## 
## References: 
##
## Parks & Burrus (1987). Digital Filter Design. New York:
## John Wiley & Sons, Inc.

function [a,b,c,d] = cheby1(n, Rp, W, varargin)

  if (nargin>5 || nargin<3) || (nargout>4 || nargout<2)
    print_usage;
  endif

  ## interpret the input parameters
  if (!(length(n)==1 && n == round(n) && n > 0))
    error ("cheby1: filter order n must be a positive integer");
  endif

  stop = 0;
  digital = 1;  
  for i=1:length(varargin)
    switch varargin{i}
    case 's', digital = 0;
    case 'z', digital = 1;
    case { 'high', 'stop' }, stop = 1;
    case { 'low',  'pass' }, stop = 0;
    otherwise,  error ("cheby1: expected [high|stop] or [s|z]");
    endswitch
  endfor

  [r, c]=size(W);
  if (!(length(W)<=2 && (r==1 || c==1)))
    error ("cheby1: frequency must be given as w0 or [w0, w1]");
  elseif (!(length(W)==1 || length(W) == 2))
    error ("cheby1: only one filter band allowed");
  elseif (length(W)==2 && !(W(1) < W(2)))
    error ("cheby1: first band edge must be smaller than second");
  endif

  if ( digital && !all(W >= 0 & W <= 1))
    error ("cheby1: critical frequencies must be in (0 1)");
  elseif ( !digital && !all(W >= 0 ))
    error ("cheby1: critical frequencies must be in (0 inf)");
  endif

  if (Rp < 0)
    error("cheby1: passband ripple must be positive decibels");
  end

  ## Prewarp to the band edges to s plane
  if digital
    T = 2;       # sampling frequency of 2 Hz
    W = 2/T*tan(pi*W/T);
  endif

  ## Generate splane poles and zeros for the chebyshev type 1 filter
  C = 1; # default cutoff frequency
  epsilon = sqrt(10^(Rp/10) - 1);
  v0 = asinh(1/epsilon)/n;
  pole = exp(1i*pi*[-(n-1):2:(n-1)]/(2*n));
  pole = -sinh(v0)*real(pole) + 1i*cosh(v0)*imag(pole);
  zero = [];

  ## compensate for amplitude at s=0
  gain = prod(-pole);
  ## if n is even, the ripple starts low, but if n is odd the ripple
  ## starts high. We must adjust the s=0 amplitude to compensate.
  if (rem(n,2)==0)
    gain = gain/10^(Rp/20);
  endif

  ## splane frequency transform
  [zero, pole, gain] = sftrans(zero, pole, gain, W, stop);

  ## Use bilinear transform to convert poles to the z plane
  if digital
    [zero, pole, gain] = bilinear(zero, pole, gain, T);
  endif

  ## convert to the correct output form
  if nargout==2, 
    a = real(gain*poly(zero));
    b = real(poly(pole));
  elseif nargout==3,
    a = zero;
    b = pole;
    c = gain;
  else
    ## output ss results 
    [a, b, c, d] = zp2ss (zero, pole, gain);
  endif

endfunction
