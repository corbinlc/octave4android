## Copyright (C) 1999 Paul Kienzle <pkienzle@users.sf.net>
## Copyright (C) 2003 Doug Stewart <dastew@sympatico.ca>
## Copyright (C) 2009 Thomas Sailer <t.sailer@alumni.ethz.ch>
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

## Generate a bessel filter.
## Default is a Laplace space (s) filter.
## 
## [b,a] = besself(n, Wc)
##    low pass filter with cutoff pi*Wc radians
##
## [b,a] = besself(n, Wc, 'high')
##    high pass filter with cutoff pi*Wc radians
##
## [b,a] = besself(n, [Wl, Wh])
##    band pass filter with edges pi*Wl and pi*Wh radians
##
## [b,a] = besself(n, [Wl, Wh], 'stop')
##    band reject filter with edges pi*Wl and pi*Wh radians
##
## [z,p,g] = besself(...)
##    return filter as zero-pole-gain rather than coefficients of the
##    numerator and denominator polynomials.
## 
## [...] = besself(...,'z')
##     return a discrete space (Z) filter, W must be less than 1.
## 
## [a,b,c,d] = besself(...)
##  return  state-space matrices 
##
## References: 
##
## Proakis & Manolakis (1992). Digital Signal Processing. New York:
## Macmillan Publishing Company.

function [a, b, c, d] = besself (n, W, varargin)
  
  if (nargin>4 || nargin<2) || (nargout>4 || nargout<2)
    print_usage;
  end

  ## interpret the input parameters
  if (!(length(n)==1 && n == round(n) && n > 0))
    error ("besself: filter order n must be a positive integer");
  end

  stop = 0;
  digital = 0;  
  for i=1:length(varargin)
    switch varargin{i}
    case 's', digital = 0;
    case 'z', digital = 1;
    case { 'high', 'stop' }, stop = 1;
    case { 'low',  'pass' }, stop = 0;
    otherwise,  error ("besself: expected [high|stop] or [s|z]");
    endswitch
  endfor


  [r, c]=size(W);
  if (!(length(W)<=2 && (r==1 || c==1)))
    error ("besself: frequency must be given as w0 or [w0, w1]");
  elseif (!(length(W)==1 || length(W) == 2))
    error ("besself: only one filter band allowed");
  elseif (length(W)==2 && !(W(1) < W(2)))
    error ("besself: first band edge must be smaller than second");
  endif

  if ( digital && !all(W >= 0 & W <= 1))
    error ("besself: critical frequencies must be in (0 1)");
  elseif ( !digital && !all(W >= 0 ))
    error ("besself: critical frequencies must be in (0 inf)");
  endif

  ## Prewarp to the band edges to s plane
  if digital
    T = 2;       # sampling frequency of 2 Hz
    W = 2/T*tan(pi*W/T);
  endif

  ## Generate splane poles for the prototype bessel filter
  [zero, pole, gain] = besselap(n);

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
