## Copyright (C) 2004 Daniel Gunyan
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

## usage y=czt(x, m, w, a)
##
## Chirp z-transform.  Compute the frequency response starting at a and
## stepping by w for m steps.  a is a point in the complex plane, and
## w is the ratio between points in each step (i.e., radius increases
## exponentially, and angle increases linearly).
##
## To evaluate the frequency response for the range f1 to f2 in a signal
## with sampling frequency Fs, use the following:
##     m = 32;                               ## number of points desired
##     w = exp(-j*2*pi*(f2-f1)/((m-1)*Fs));  ## freq. step of f2-f1/m
##     a = exp(j*2*pi*f1/Fs);                ## starting at frequency f1
##     y = czt(x, m, w, a);
##
## If you don't specify them, then the parameters default to a fourier 
## transform:
##     m=length(x), w=exp(-j*2*pi/m), a=1
##
## If x is a matrix, the transform will be performed column-by-column.

## Algorithm (based on Oppenheim and Schafer, "Discrete-Time Signal
## Processing", pp. 623-628):
##   make chirp of length -N+1 to max(N-1,M-1)
##     chirp => w^([-N+1:max(N-1,M-1)]^2/2)
##   multiply x by chirped a and by N-elements of chirp, and call it g
##   convolve g with inverse chirp, and call it gg
##     pad ffts so that multiplication works
##     ifft(fft(g)*fft(1/chirp))
##   multiply gg by M-elements of chirp and call it done

function y = czt(x, m, w, a)
  if nargin < 1 || nargin > 4, print_usage; endif

  [row, col] = size(x);
  if row == 1, x = x(:); col = 1; endif

  if nargin < 2 || isempty(m), m = length(x(:,1)); endif
  if length(m) > 1, error("czt: m must be a single element\n"); endif
  if nargin < 3 || isempty(w), w = exp(-2*j*pi/m); endif
  if nargin < 4 || isempty(a), a = 1; endif
  if length(w) > 1, error("czt: w must be a single element\n"); endif
  if length(a) > 1, error("czt: a must be a single element\n"); endif

  ## indexing to make the statements a little more compact
  n = length(x(:,1));
  N = [0:n-1]'+n;
  NM = [-(n-1):(m-1)]'+n;
  M = [0:m-1]'+n;

  nfft = 2^nextpow2(n+m-1); # fft pad
  W2 = w.^(([-(n-1):max(m-1,n-1)]'.^2)/2); # chirp

  for idx = 1:col
    fg = fft(x(:,idx).*(a.^-(N-n)).*W2(N), nfft);
    fw = fft(1./W2(NM), nfft);
    gg = ifft(fg.*fw, nfft);

    y(:,idx) = gg(M).*W2(M);
  endfor

  if row == 1, y = y.'; endif
endfunction

%!shared x
%! x = [1,2,4,1,2,3,5,2,3,5,6,7,8,4,3,6,3,2,5,1];
%!assert(fft(x),czt(x),10000*eps);
%!assert(fft(x'),czt(x'),10000*eps);
%!assert(fft([x',x']),czt([x',x']),10000*eps);
