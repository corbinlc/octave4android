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

## Compute butterworth filter order and cutoff for the desired response
## characteristics. Rp is the allowable decibels of ripple in the pass 
## band. Rs is the minimum attenuation in the stop band.
##
## [n, Wc] = buttord(Wp, Ws, Rp, Rs)
##     Low pass (Wp<Ws) or high pass (Wp>Ws) filter design.  Wp is the
##     pass band edge and Ws is the stop band edge.  Frequencies are
##     normalized to [0,1], corresponding to the range [0,Fs/2].
## 
## [n, Wc] = buttord([Wp1, Wp2], [Ws1, Ws2], Rp, Rs)
##     Band pass (Ws1<Wp1<Wp2<Ws2) or band reject (Wp1<Ws1<Ws2<Wp2)
##     filter design. Wp gives the edges of the pass band, and Ws gives
##     the edges of the stop band.
##
## Theory: |H(W)|^2 = 1/[1+(W/Wc)^(2N)] = 10^(-R/10)
## With some algebra, you can solve simultaneously for Wc and N given
## Ws,Rs and Wp,Rp.  For high pass filters, subtracting the band edges
## from Fs/2, performing the test, and swapping the resulting Wc back
## works beautifully.  For bandpass and bandstop filters this process
## significantly overdesigns.  Artificially dividing N by 2 in this case
## helps a lot, but it still overdesigns.
##
## See also: butter

function [n, Wc] = buttord(Wp, Ws, Rp, Rs)
  if nargin != 4
    print_usage;
  elseif length(Wp) != length(Ws)
    error("buttord: Wp and Ws must have the same length");
  elseif length(Wp) != 1 && length(Wp) != 2
    error("buttord: Wp,Ws must have length 1 or 2");
  elseif length(Wp) == 2 && (all(Wp>Ws) || all(Ws>Wp) || diff(Wp)<=0 || diff(Ws)<=0)
    error("buttord: Wp(1)<Ws(1)<Ws(2)<Wp(2) or Ws(1)<Wp(1)<Wp(2)<Ws(2)");
  end

  if length(Wp) == 2
    warning("buttord: seems to overdesign bandpass and bandreject filters");
  end

  T = 2;
  
  ## if high pass, reverse the sense of the test
  stop = find(Wp > Ws);
  Wp(stop) = 1-Wp(stop); # stop will be at most length 1, so no need to
  Ws(stop) = 1-Ws(stop); # subtract from ones(1,length(stop))
  
  ## warp the target frequencies according to the bilinear transform
  Ws = (2/T)*tan(pi*Ws./T);
  Wp = (2/T)*tan(pi*Wp./T);
  
  ## compute minimum n which satisfies all band edge conditions
  ## the factor 1/length(Wp) is an artificial correction for the
  ## band pass/stop case, which otherwise significantly overdesigns.
  qs = log(10^(Rs/10) - 1);
  qp = log(10^(Rp/10) - 1);
  n = ceil(max(0.5*(qs - qp)./log(Ws./Wp))/length(Wp));

  ## compute -3dB cutoff given Wp, Rp and n
  Wc = exp(log(Wp) - qp/2/n);

  ## unwarp the returned frequency
  Wc = atan(T/2*Wc)*T/pi;
  
  ## if high pass, reverse the sense of the test
  Wc(stop) = 1-Wc(stop);
    
endfunction
