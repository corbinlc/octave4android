## Copyright (C) 1999-2001 Paul Kienzle <pkienzle@users.sf.net>
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

## usage: [S [, f [, t]]] = specgram(x [, n [, Fs [, window [, overlap]]]])
##
## Generate a spectrogram for the signal. This chops the signal into
## overlapping slices, windows each slice and applies a Fourier
## transform to determine the frequency components at that slice.
##
## x: vector of samples
## n: size of fourier transform window, or [] for default=256
## Fs: sample rate, or [] for default=2 Hz
## window: shape of the fourier transform window, or [] for default=hanning(n)
##    Note: window length can be specified instead, in which case
##    window=hanning(length)
## overlap: overlap with previous window, or [] for default=length(window)/2
##
## Return values
##    S is complex output of the FFT, one row per slice
##    f is the frequency indices corresponding to the rows of S.
##    t is the time indices corresponding to the columns of S.
##    If no return value is requested, the spectrogram is displayed instead.
##
## Example
##    x = chirp([0:0.001:2],0,2,500);  # freq. sweep from 0-500 over 2 sec.
##    Fs=1000;                  # sampled every 0.001 sec so rate is 1 kHz
##    step=ceil(20*Fs/1000);    # one spectral slice every 20 ms
##    window=ceil(100*Fs/1000); # 100 ms data window
##    specgram(x, 2^nextpow2(window), Fs, window, window-step);
##
##    ## Speech spectrogram
##    [x, Fs] = auload(file_in_loadpath("sample.wav")); # audio file
##    step = fix(5*Fs/1000);     # one spectral slice every 5 ms
##    window = fix(40*Fs/1000);  # 40 ms data window
##    fftn = 2^nextpow2(window); # next highest power of 2
##    [S, f, t] = specgram(x, fftn, Fs, window, window-step);
##    S = abs(S(2:fftn*4000/Fs,:)); # magnitude in range 0<f<=4000 Hz.
##    S = S/max(S(:));           # normalize magnitude so that max is 0 dB.
##    S = max(S, 10^(-40/10));   # clip below -40 dB.
##    S = min(S, 10^(-3/10));    # clip above -3 dB.
##    imagesc(t, f, flipud(log(S)));   # display in log scale
##
## The choice of window defines the time-frequency resolution.  In
## speech for example, a wide window shows more harmonic detail while a
## narrow window averages over the harmonic detail and shows more
## formant structure. The shape of the window is not so critical so long
## as it goes gradually to zero on the ends.
##
## Step size (which is window length minus overlap) controls the
## horizontal scale of the spectrogram. Decrease it to stretch, or
## increase it to compress. Increasing step size will reduce time
## resolution, but decreasing it will not improve it much beyond the
## limits imposed by the window size (you do gain a little bit,
## depending on the shape of your window, as the peak of the window
## slides over peaks in the signal energy).  The range 1-5 msec is good
## for speech.
##
## FFT length controls the vertical scale.  Selecting an FFT length
## greater than the window length does not add any information to the
## spectrum, but it is a good way to interpolate between frequency
## points which can make for prettier spectrograms.
##
## After you have generated the spectral slices, there are a number of
## decisions for displaying them.  First the phase information is
## discarded and the energy normalized:
##
##     S = abs(S); S = S/max(S(:));
##
## Then the dynamic range of the signal is chosen.  Since information in
## speech is well above the noise floor, it makes sense to eliminate any
## dynamic range at the bottom end.  This is done by taking the max of
## the magnitude and some minimum energy such as minE=-40dB. Similarly,
## there is not much information in the very top of the range, so
## clipping to a maximum energy such as maxE=-3dB makes sense:
##
##     S = max(S, 10^(minE/10)); S = min(S, 10^(maxE/10));
##
## The frequency range of the FFT is from 0 to the Nyquist frequency of
## one half the sampling rate.  If the signal of interest is band
## limited, you do not need to display the entire frequency range. In
## speech for example, most of the signal is below 4 kHz, so there is no
## reason to display up to the Nyquist frequency of 10 kHz for a 20 kHz
## sampling rate.  In this case you will want to keep only the first 40%
## of the rows of the returned S and f.  More generally, to display the
## frequency range [minF, maxF], you could use the following row index:
##
##     idx = (f >= minF & f <= maxF);
##
## Then there is the choice of colormap.  A brightness varying colormap
## such as copper or bone gives good shape to the ridges and valleys. A
## hue varying colormap such as jet or hsv gives an indication of the
## steepness of the slopes.  The final spectrogram is displayed in log
## energy scale and by convention has low frequencies on the bottom of
## the image:
##
##     imagesc(t, f, flipud(log(S(idx,:))));

function [S_r, f_r, t_r] = specgram(x, n = min(256, length(x)), Fs = 2, window = hanning(n), overlap = ceil(length(window)/2))

  if nargin < 1 || nargin > 5
    print_usage;
  ## make sure x is a vector
  elseif columns(x) != 1 && rows(x) != 1
    error ("specgram data must be a vector");
  end
  if columns(x) != 1, x = x'; end

  ## if only the window length is given, generate hanning window
  if length(window) == 1, window = hanning(window); end

  ## should be extended to accept a vector of frequencies at which to
  ## evaluate the fourier transform (via filterbank or chirp
  ## z-transform)
  if length(n)>1, 
    error("specgram doesn't handle frequency vectors yet"); 
  endif

  ## compute window offsets
  win_size = length(window);
  if (win_size > n)
    n = win_size;
    warning ("specgram fft size adjusted to %d", n);
  end
  step = win_size - overlap;

  ## build matrix of windowed data slices
  offset = [ 1 : step : length(x)-win_size ];
  S = zeros (n, length(offset));
  for i=1:length(offset)
    S(1:win_size, i) = x(offset(i):offset(i)+win_size-1) .* window;
  endfor

  ## compute fourier transform
  S = fft (S);

  ## extract the positive frequency components
  if rem(n,2)==1
    ret_n = (n+1)/2;
  else
    ret_n = n/2;
  end
  S = S(1:ret_n, :);

  f = [0:ret_n-1]*Fs/n;
  t = offset/Fs;
  if nargout==0
    imagesc(t, f, 20*log10(abs(S)));
    set (gca (), "ydir", "normal");
    xlabel ("Time")
    ylabel ("Frequency")
  endif
  if nargout>0, S_r = S; endif
  if nargout>1, f_r = f; endif
  if nargout>2, t_r = t; endif

endfunction

%!shared S,f,t,x
%! Fs=1000;
%! x = chirp([0:1/Fs:2],0,2,500);  # freq. sweep from 0-500 over 2 sec.
%! step=ceil(20*Fs/1000);    # one spectral slice every 20 ms
%! window=ceil(100*Fs/1000); # 100 ms data window
%! [S, f, t] = specgram(x);

%! ## test of returned shape
%!assert (rows(S), 128)
%!assert (columns(f), rows(S))
%!assert (columns(t), columns(S))
%!test [S, f, t] = specgram(x');
%!assert (rows(S), 128)
%!assert (columns(f), rows(S));
%!assert (columns(t), columns(S));
%!error (isempty(specgram([])));
%!error (isempty(specgram([1, 2 ; 3, 4])));
%!error (specgram)

%!demo
%! Fs=1000;
%! x = chirp([0:1/Fs:2],0,2,500);  # freq. sweep from 0-500 over 2 sec.
%! step=ceil(20*Fs/1000);    # one spectral slice every 20 ms
%! window=ceil(100*Fs/1000); # 100 ms data window
%!
%! ## test of automatic plot
%! [S, f, t] = specgram(x);
%! specgram(x, 2^nextpow2(window), Fs, window, window-step);
%! disp("shows a diagonal from bottom left to top right");
%! input("press enter:","s");
%!
%! ## test of returned values
%! S = specgram(x, 2^nextpow2(window), Fs, window, window-step);
%! imagesc(20*log10(flipud(abs(S))));
%! disp("same again, but this time using returned value");

%!demo
%! ## Speech spectrogram
%! [x, Fs] = auload(file_in_loadpath("sample.wav")); # audio file
%! step = fix(5*Fs/1000);     # one spectral slice every 5 ms
%! window = fix(40*Fs/1000);  # 40 ms data window
%! fftn = 2^nextpow2(window); # next highest power of 2
%! [S, f, t] = specgram(x, fftn, Fs, window, window-step);
%! S = abs(S(2:fftn*4000/Fs,:)); # magnitude in range 0<f<=4000 Hz.
%! S = S/max(max(S));         # normalize magnitude so that max is 0 dB.
%! S = max(S, 10^(-40/10));   # clip below -40 dB.
%! S = min(S, 10^(-3/10));    # clip above -3 dB.
%! imagesc(flipud(20*log10(S)));
%!
%! % The image contains a spectrogram of 'sample.wav'
