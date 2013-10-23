## Copyright (C) 1999 Paul Kienzle <pkienzle@users.sf.net>
## Copyright (C) 2003 Doug Stewart <dastew@sympatico.ca>
## Copyright (C) 2011 Alexander Klein <alexander.klein@math.uni-giessen.de>
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

## Generate a butterworth filter.
## Default is a discrete space (Z) filter.
## 
## [b,a] = butter(n, Wc)
##    low pass filter with cutoff pi*Wc radians
##
## [b,a] = butter(n, Wc, 'high')
##    high pass filter with cutoff pi*Wc radians
##
## [b,a] = butter(n, [Wl, Wh])
##    band pass filter with edges pi*Wl and pi*Wh radians
##
## [b,a] = butter(n, [Wl, Wh], 'stop')
##    band reject filter with edges pi*Wl and pi*Wh radians
##
## [z,p,g] = butter(...)
##    return filter as zero-pole-gain rather than coefficients of the
##    numerator and denominator polynomials.
## 
## [...] = butter(...,'s')
##     return a Laplace space filter, W can be larger than 1.
## 
## [a,b,c,d] = butter(...)
##  return  state-space matrices 
##
## References: 
##
## Proakis & Manolakis (1992). Digital Signal Processing. New York:
## Macmillan Publishing Company.

function [a, b, c, d] = butter (n, W, varargin)
  
  if (nargin>4 || nargin<2) || (nargout>4 || nargout<2)
    print_usage;
  end

  ## interpret the input parameters
  if (!(length(n)==1 && n == round(n) && n > 0))
    error ("butter: filter order n must be a positive integer");
  end

  stop = 0;
  digital = 1;
  for i=1:length(varargin)
    switch varargin{i}
    case 's', digital = 0;
    case 'z', digital = 1;
    case { 'high', 'stop' }, stop = 1;
    case { 'low',  'pass' }, stop = 0;
    otherwise,  error ("butter: expected [high|stop] or [s|z]");
    endswitch
  endfor


  [r, c]=size(W);
  if (!(length(W)<=2 && (r==1 || c==1)))
    error ("butter: frequency must be given as w0 or [w0, w1]");
  elseif (!(length(W)==1 || length(W) == 2))
    error ("butter: only one filter band allowed");
  elseif (length(W)==2 && !(W(1) < W(2)))
    error ("butter: first band edge must be smaller than second");
  endif

  if ( digital && !all(W >= 0 & W <= 1))
    error ("butter: critical frequencies must be in (0 1)");
  elseif ( !digital && !all(W >= 0 ))
    error ("butter: critical frequencies must be in (0 inf)");
  endif

  ## Prewarp to the band edges to s plane
  if digital
    T = 2;       # sampling frequency of 2 Hz
    W = 2/T*tan(pi*W/T);
  endif

  ## Generate splane poles for the prototype butterworth filter
  ## source: Kuc
  C = 1; # default cutoff frequency
  pole = C*exp(1i*pi*(2*[1:n] + n - 1)/(2*n));
  if mod(n,2) == 1, pole((n+1)/2) = -1; end  # pure real value at exp(i*pi)
  zero = [];
  gain = C^n;

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

%!shared sf, sf2, off_db
%! off_db = 0.5;
%! ##Sampling frequency must be that high to make the low pass filters pass.
%! sf = 6000; sf2 = sf/2;
%! data=[sinetone(5,sf,10,1),sinetone(10,sf,10,1),sinetone(50,sf,10,1),sinetone(200,sf,10,1),sinetone(400,sf,10,1)];

%!test
%! ##Test low pass order 1 with 3dB @ 50Hz
%! data=[sinetone(5,sf,10,1),sinetone(10,sf,10,1),sinetone(50,sf,10,1),sinetone(200,sf,10,1),sinetone(400,sf,10,1)];
%! [b, a] = butter ( 1, 50 / sf2 );
%! filtered = filter ( b, a, data );
%! damp_db = 20 * log10 ( max ( filtered ( end - sf : end, : ) ) );
%! assert ( [ damp_db( 4 ) - damp_db( 5 ), damp_db( 1 : 3 ) ], [ 6 0 0 -3 ], off_db )

%!test
%! ##Test low pass order 4 with 3dB @ 50Hz
%! data=[sinetone(5,sf,10,1),sinetone(10,sf,10,1),sinetone(50,sf,10,1),sinetone(200,sf,10,1),sinetone(400,sf,10,1)];
%! [b, a] = butter ( 4, 50 / sf2 );
%! filtered = filter ( b, a, data );
%! damp_db = 20 * log10 ( max ( filtered ( end - sf : end, : ) ) );
%! assert ( [ damp_db( 4 ) - damp_db( 5 ), damp_db( 1 : 3 ) ], [ 24 0 0 -3 ], off_db )

%!test
%! ##Test high pass order 1 with 3dB @ 50Hz
%! data=[sinetone(5,sf,10,1),sinetone(10,sf,10,1),sinetone(50,sf,10,1),sinetone(200,sf,10,1),sinetone(400,sf,10,1)];
%! [b, a] = butter ( 1, 50 / sf2, "high" );
%! filtered = filter ( b, a, data );
%! damp_db = 20 * log10 ( max ( filtered ( end - sf : end, : ) ) );
%! assert ( [ damp_db( 2 ) - damp_db( 1 ), damp_db( 3 : end ) ], [ 6 -3 0 0 ], off_db )

%!test
%! ##Test high pass order 4 with 3dB @ 50Hz
%! data=[sinetone(5,sf,10,1),sinetone(10,sf,10,1),sinetone(50,sf,10,1),sinetone(200,sf,10,1),sinetone(400,sf,10,1)];
%! [b, a] = butter ( 4, 50 / sf2, "high" );
%! filtered = filter ( b, a, data );
%! damp_db = 20 * log10 ( max ( filtered ( end - sf : end, : ) ) );
%! assert ( [ damp_db( 2 ) - damp_db( 1 ), damp_db( 3 : end ) ], [ 24 -3 0 0 ], off_db )

%!demo
%! sf = 800; sf2 = sf/2;
%! data=[[1;zeros(sf-1,1)],sinetone(25,sf,1,1),sinetone(50,sf,1,1),sinetone(100,sf,1,1)];
%! [b,a]=butter ( 1, 50 / sf2 );
%! filtered = filter(b,a,data);
%!
%! clf
%! subplot ( columns ( filtered ), 1, 1) 
%! plot(filtered(:,1),";Impulse response;")
%! subplot ( columns ( filtered ), 1, 2 ) 
%! plot(filtered(:,2),";25Hz response;")
%! subplot ( columns ( filtered ), 1, 3 ) 
%! plot(filtered(:,3),";50Hz response;")
%! subplot ( columns ( filtered ), 1, 4 ) 
%! plot(filtered(:,4),";100Hz response;")
