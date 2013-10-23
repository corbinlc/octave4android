## Copyright (C) 2002 André Carezia <andre@carezia.eng.br>
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

## Usage:  qp_kaiser (nb, at, linear)
##
## Computes a finite impulse response (FIR) filter for use with a
## quasi-perfect reconstruction polyphase-network filter bank. This
## version utilizes a Kaiser window to shape the frequency response of
## the designed filter. Tha number nb of bands and the desired
## attenuation at in the stop-band are given as parameters.
##
## The Kaiser window is multiplied by the ideal impulse response
## h(n)=a.sinc(a.n) and converted to its minimum-phase version by means
## of a Hilbert transform.
##
## By using a third non-null argument, the minimum-phase calculation is
## ommited at all.

function h = qp_kaiser (nb, at, linear = 0)

  if (nargin < 2)
    print_usage;
  elseif !(isscalar (nb) && (nb == round(nb)) && (nb >= 0))
    error ("qp_kaiser: nb has to be a positive integer");
  elseif !(isscalar (at) && (at == real (at)))
    error ("qp_kaiser: at has to be a real constant");
  endif

				# Bandwidth
  bandwidth = pi/nb;

				# Attenuation correction (empirically
				# determined by M. Gerken
				# <mgk@lcs.poli.usp.br>)
  corr = (1.4+0.6*(at-20)/80)^(20/at);
  at = corr * at;

				# size of window (rounded to next odd
				# integer)
  N = (at - 8) / (2.285*bandwidth);
  M = fix(N/2);
  N = 2*M + 1;

				# Kaiser window
  if (at>50)
    beta = 0.1102 * (at - 8.7);
  elseif (at>21)
    beta = 0.5842 * (at - 21)^0.4 + 0.07886 * (at - 21);
  else
    beta = 0;
  endif
  w = kaiser(N,beta);
				# squared in freq. domain
  wsquared = conv(w,w);

				# multiplied by ideal lowpass filter
  n = -(N-1):(N-1);
  hideal = 1/nb * sinc(n/nb);
  hcomp = wsquared .* hideal;

				# extract square-root of response and
				# compute minimum-phase version
  Ndft = 2^15;
  Hsqr = sqrt(abs(fft(hcomp,Ndft)));
  if (linear)
    h = real(ifft(Hsqr));
    h = h(2:N);
    h = [fliplr(h) h(1) h];
  else
    Hmin = Hsqr .* exp(-j*imag(hilbert(log(Hsqr))));
    h = real(ifft(Hmin));
    h = h(1:N);
  endif
				# truncate and fix amplitude scale
				# (H(0)=1)
  h = h / sum(h);

endfunction
