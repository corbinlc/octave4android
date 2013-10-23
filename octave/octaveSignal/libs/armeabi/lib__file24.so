## Copyright (C) 2002 André Carezia <acarezia@uol.com.br>
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

## Usage:  chebwin (L, at)
##
## Returns the filter coefficients of the L-point Dolph-Chebyshev window
## with at dB of attenuation in the stop-band of the corresponding
## Fourier transform.
##
## For the definition of the Chebyshev window, see
##
## * Peter Lynch, "The Dolph-Chebyshev Window: A Simple Optimal Filter",
##   Monthly Weather Review, Vol. 125, pp. 655-660, April 1997.
##   (http://www.maths.tcd.ie/~plynch/Publications/Dolph.pdf)
##
## * C. Dolph, "A current distribution for broadside arrays which
##   optimizes the relationship between beam width and side-lobe level",
##   Proc. IEEE, 34, pp. 335-348.
##
## The window is described in frequency domain by the expression:
##
##          Cheb(L-1, beta * cos(pi * k/L))
##   W(k) = -------------------------------
##                 Cheb(L-1, beta)
##
## with
##
##   beta = cosh(1/(L-1) * acosh(10^(at/20))
##
## and Cheb(m,x) denoting the m-th order Chebyshev polynomial calculated
## at the point x.
##
## Note that the denominator in W(k) above is not computed, and after
## the inverse Fourier transform the window is scaled by making its
## maximum value unitary.
##
## See also: kaiser

function w = chebwin (L, at)

  if (nargin != 2)
    print_usage;
  elseif !(isscalar (L) && (L == round(L)) && (L > 0))
    error ("chebwin: L has to be a positive integer");
  elseif !(isscalar (at) && (at == real (at)))
    error ("chebwin: at has to be a real scalar");
  endif
  
  if (L == 1)
    w = 1;
  else
				# beta calculation
    gamma = 10^(-at/20);
    beta = cosh(1/(L-1) * acosh(1/gamma));
				# freq. scale
    k = (0:L-1);
    x = beta*cos(pi*k/L);
				# Chebyshev window (freq. domain)
    p = cheb(L-1, x);
				# inverse Fourier transform
    if (rem(L,2))
      w = real(fft(p));
      M = (L+1)/2;
      w = w(1:M)/w(1);
      w = [w(M:-1:2) w]';
    else
				# half-sample delay (even order)
      p = p.*exp(j*pi/L * (0:L-1));
      w = real(fft(p));
      M = L/2+1;
      w = w/w(2);
      w = [w(M:-1:2) w(2:M)]';
    endif
  endif 
  
  w = w ./ max (w (:)); 
endfunction
