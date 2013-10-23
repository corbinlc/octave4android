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

## Compute chebyshev type II filter order and cutoff for the desired response
## characteristics. Rp is the allowable decibels of ripple in the pass 
## band. Rs is the minimum attenuation in the stop band.
##
## [n, Wc] = cheb2ord(Wp, Ws, Rp, Rs)
##     Low pass (Wp<Ws) or high pass (Wp>Ws) filter design.  Wp is the
##     pass band edge and Ws is the stop band edge.  Frequencies are
##     normalized to [0,1], corresponding to the range [0,Fs/2].
## 
## [n, Wc] = cheb2ord([Wp1, Wp2], [Ws1, Ws2], Rp, Rs)
##     Band pass (Ws1<Wp1<Wp2<Ws2) or band reject (Wp1<Ws1<Ws2<Wp2)
##     filter design. Wp gives the edges of the pass band, and Ws gives
##     the edges of the stop band.
##
## Theory: 
##
## See also: cheby2

function [n, Wc] = cheb2ord(Wp, Ws, Rp, Rs)

  if nargin != 4
    print_usage;
  elseif length(Wp) != length(Ws)
    error("cheb2ord: Wp and Ws must have the same length");
  elseif length(Wp) != 1 && length(Wp) != 2
    error("cheb2ord: Wp,Ws must have length 1 or 2");
  elseif length(Wp) == 2 && ...
          (all(Wp>Ws) || all(Ws>Wp) || diff(Wp)<=0 || diff(Ws)<=0)
    error("cheb2ord: Wp(1)<Ws(1)<Ws(2)<Wp(2) or Ws(1)<Wp(1)<Wp(2)<Ws(2)");
  end

  T = 2;

  ## returned frequency is the same as the input frequency
  Wc = Ws;

  ## warp the target frequencies according to the bilinear transform
  Ws = (2/T)*tan(pi*Ws./T);
  Wp = (2/T)*tan(pi*Wp./T);

  if (Wp(1) < Ws(1))
    ## low pass
    if (length(Wp) == 1)
      Wa = Wp/Ws;
    else
      ## band reject
      error ("band reject is not implement yet.");
    endif;
  else
   ## if high pass, reverse the sense of the test
   if (length(Wp) == 1)
      Wa = Ws/Wp;
    else
      ## band pass 
      Wa=(Wp.^2 - Ws(1)*Ws(2))./(Wp*(Ws(1)-Ws(2)));
    endif;
  endif;
  Wa = min(abs(Wa));

  ## compute minimum n which satisfies all band edge conditions
  stop_atten = 10^(abs(Rs)/10);
  pass_atten = 10^(abs(Rp)/10);
  n = ceil(acosh(sqrt((stop_atten-1)/(pass_atten-1)))/acosh(1/Wa));
    
endfunction

%!demo
%! Fs = 10000; 
%! [n, Wc] = cheb2ord (1000/(Fs/2), 1200/(Fs/2), 0.5, 29);
%!
%! subplot (221);
%! plot ([0, 1000, 1000, 0, 0], [0, 0, -0.5, -0.5, 0], ";;");
%! hold on;
%! grid;
%! title("Pass band Wp=1000 Rp=0.0");
%! xlabel("Frequency (Hz)");
%! ylabel("Attenuation (dB)");
%! [b, a] = cheby2 (n, 29, Wc);
%! [h, w] = freqz (b, a, [], Fs);
%! plot (w, 20*log10(abs(h)), ";;");
%! axis ([ 0, 1500, -1, 0]);
%! hold off;
%!
%! subplot (222);
%! plot ([1200, Fs/2, Fs/2, 1200, 1200], [-29, -29, -500, -500, -29], ";;");
%! hold on;
%! axis ([ 0, Fs/2, -250, 0]);
%! title("Stop band Ws=1200 Rs=29");
%! xlabel("Frequency (Hz)");
%! ylabel("Attenuation (dB)");
%! grid;
%! [b, a] = cheby2 (n, 29, Wc);
%! [h, w] = freqz (b, a, [], Fs);
%! plot (w, 20*log10(abs(h)), ";;");
%! hold off;
%!
%! subplot (223);
%! plot ([0, 1000, 1000, 0, 0], [0, 0, -0.5, -0.5, 0], ";;");
%! hold on;
%! axis ([ 800, 1010, -0.6, -0.0]);
%! title("Pass band detail Wp=1000 Rp=0.5");
%! xlabel("Frequency (Hz)");
%! ylabel("Attenuation (dB)");
%! grid;
%! [b, a] = cheby2 (n, 29, Wc);
%! [h, w] = freqz (b, a, [800:1010], Fs);
%! plot (w, 20*log10(abs(h)), "r;filter n;");
%! [b, a] = cheby2 (n-1, 29, Wc);
%! [h, w] = freqz (b, a, [800:1010], Fs);
%! plot (w, 20*log10(abs(h)), "b;filter n-1;");
%! [b, a] = cheby2 (n+1, 29, Wc);
%! [h, w] = freqz (b, a, [800:1010], Fs);
%! plot (w, 20*log10(abs(h)), "g;filter n+1;");
%! hold off;
%!
%! subplot (224);
%! plot ([1200, Fs/2, Fs/2, 1200, 1200], [-29, -29, -500, -500, -29], ";;");
%! hold on;
%! axis ([ 1190, 1210, -40, -20]);
%! title("Stop band detail Wp=1200 Rp=29");
%! xlabel("Frequency (Hz)");
%! ylabel("Attenuation (dB)");
%! grid;
%! [b, a] = cheby2 (n, 29, Wc);
%! [h, w] = freqz (b, a, [1190:1210], Fs);
%! plot (w, 20*log10(abs(h)), "r;filter n;");
%! [b, a] = cheby2 (n-1, 29, Wc);
%! [h, w] = freqz (b, a, [1190:1210], Fs);
%! plot (w, 20*log10(abs(h)), "b;filter n-1;");
%! [b, a] = cheby2 (n+1, 29, Wc);
%! [h, w] = freqz (b, a, [1190:1210], Fs);
%! plot (w, 20*log10(abs(h)), "g;filter n+1;");
%! hold off;
