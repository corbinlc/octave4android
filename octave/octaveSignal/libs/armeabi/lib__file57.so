## Copyright (C) 2003 Julius O. Smith III <jos@ccrma.stanford.edu>
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
## @deftypefn {Function File} freqs_plot (@var{w}, @var{h})
## Plot the amplitude and phase of the vector @var{h}.
##
## @end deftypefn

function freqs_plot(w,h)
    n = length(w);
    mag = 20*log10(abs(h));
    phase = unwrap(arg(h));
    maxmag = max(mag);

    subplot(211);
    plot(w, mag, ";Magnitude (dB);");
    title('Frequency response plot by freqs');
    axis("labely");
    ylabel("dB");
    xlabel("");
    grid("on");
    if (maxmag - min(mag) > 100) % make 100 a parameter?
      axis([w(1), w(n), maxmag-100, maxmag]);
    else
      axis("autoy");
    endif

    subplot(212);
    plot(w, phase/(2*pi), ";Phase (radians/2pi);");
    axis("label");
    title("");
    grid("on");
    axis("autoy");
    xlabel("Frequency (rad/sec)");
    ylabel("Cycles");
    axis([w(1), w(n)]);
endfunction
