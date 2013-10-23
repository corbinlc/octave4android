%% Copyright (C) 2006 Peter V. Lanspeary <pvl@mecheng.adelaide.edu.au>
%%
%% This program is free software; you can redistribute it and/or modify it under
%% the terms of the GNU General Public License as published by the Free Software
%% Foundation; either version 3 of the License, or (at your option) any later
%% version.
%%
%% This program is distributed in the hope that it will be useful, but WITHOUT
%% ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
%% FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
%% details.
%%
%% You should have received a copy of the GNU General Public License along with
%% this program; if not, see <http://www.gnu.org/licenses/>.

%% Usage:
%%   [psd,f_out] = ar_psd(a,v,freq,Fs,range,method,plot_type)
%%
%%  Calculate the power spectrum of the autoregressive model
%%
%%                         M
%%  x(n) = sqrt(v).e(n) + SUM a(k).x(n-k)
%%                        k=1
%%  where x(n) is the output of the model and e(n) is white noise.
%%  This function is intended for use with 
%%    [a,v,k] = arburg(x,poles,criterion)
%%  which use the Burg (1968) method to calculate a "maximum entropy"
%%  autoregressive model of "x".  This function runs on octave and matlab.
%%  
%%  If the "freq" argument is a vector (of frequencies) the spectrum is
%%  calculated using the polynomial method and the "method" argument is
%%  ignored.  For scalar "freq", an integer power of 2, or "method='FFT'",
%%  causes the spectrum to be calculated by FFT.  Otherwise, the spectrum
%%  is calculated as a polynomial.  It may be computationally more
%%  efficient to use the FFT method if length of the model is not much
%%  smaller than the number of frequency values. The spectrum is scaled so
%%  that spectral energy (area under spectrum) is the same as the
%%  time-domain energy (mean square of the signal).
%%
%% ARGUMENTS:
%%     All but the first two arguments are optional and may be empty.
%%
%%   a      %% [vector] list of M=(order+1) autoregressive model
%%          %%      coefficients.  The first element of "ar_coeffs" is the
%%          %%      zero-lag coefficient, which always has a value of 1.
%%
%%   v      %% [real scalar] square of the moving-average coefficient of
%%          %%               the AR model.
%%
%%   freq   %% [real vector] frequencies at which power spectral density
%%          %%               is calculated
%%          %% [integer scalar] number of uniformly distributed frequency
%%          %%          values at which spectral density is calculated.
%%          %%          [default=256]
%%
%%   Fs     %% [real scalar] sampling frequency (Hertz) [default=1]
%%
%% CONTROL-STRING ARGUMENTS -- each of these arguments is a character string.
%%   Control-string arguments can be in any order after the other arguments.
%%
%%   range  %% 'half',  'onesided' : frequency range of the spectrum is
%%          %%       from zero up to but not including sample_f/2.  Power
%%          %%       from negative frequencies is added to the positive
%%          %%       side of the spectrum.
%%          %% 'whole', 'twosided' : frequency range of the spectrum is
%%          %%       -sample_f/2 to sample_f/2, with negative frequencies
%%          %%       stored in "wrap around" order after the positive
%%          %%       frequencies; e.g. frequencies for a 10-point 'twosided'
%%          %%       spectrum are 0 0.1 0.2 0.3 0.4 0.5 -0.4 -0.3 -0.2 -0.1
%%          %% 'shift', 'centerdc' : same as 'whole' but with the first half
%%          %%       of the spectrum swapped with second half to put the
%%          %%       zero-frequency value in the middle. (See "help
%%          %%       fftshift". If "freq" is vector, 'shift' is ignored.
%%          %% If model coefficients "ar_coeffs" are real, the default
%%          %% range is 'half', otherwise default range is 'whole'.
%%
%%   method %% 'fft':  use FFT to calculate power spectrum.
%%          %% 'poly': calculate power spectrum as a polynomial of 1/z
%%          %% N.B. this argument is ignored if the "freq" argument is a
%%          %%      vector.  The default is 'poly' unless the "freq"
%%          %%      argument is an integer power of 2.
%%   
%% plot_type%% 'plot', 'semilogx', 'semilogy', 'loglog', 'squared' or 'db':
%%          %% specifies the type of plot.  The default is 'plot', which
%%          %% means linear-linear axes. 'squared' is the same as 'plot'.
%%          %% 'dB' plots "10*log10(psd)".  This argument is ignored and a
%%          %% spectrum is not plotted if the caller requires a returned
%%          %% value.
%%
%% RETURNED VALUES:
%%     If returned values are not required by the caller, the spectrum
%%     is plotted and nothing is returned.
%%   psd    %% [real vector] estimate of power-spectral density
%%   f_out  %% [real vector] frequency values 
%%
%% N.B. arburg runs in octave and matlab, and does not depend on octave-forge
%%      or signal-processing-toolbox functions.
%%
%% REFERENCE
%% [1] Equation 2.28 from Steven M. Kay and Stanley Lawrence Marple Jr.:
%%   "Spectrum analysis -- a modern perspective",
%%   Proceedings of the IEEE, Vol 69, pp 1380-1419, Nov., 1981
%%

function [varargout]=ar_psd(a,v,varargin)
%%
%% Check fixed arguments
if ( nargin < 2 )
  error( 'ar_psd: needs at least 2 args. Use help ar_psd.' );
elseif ( ~isvector(a) || length(a)<2 )
  error( 'ar_psd: arg 1 (a) must be vector, length>=2.' );
elseif ( ~isscalar(v) )
  error( 'ar_psd: arg 2 (v) must be real scalar >0.' );
else
  real_model = isreal(a);
%%
%%  default values for optional areguments
  freq = 256;
  user_freqs = 0;    %% boolean: true for user-specified frequencies
  Fs   = 1.0;
  %%  FFT padding factor (is also frequency range divisor): 1=whole, 2=half.
  pad_fact = 1 + real_model;
  do_shift   = 0;
  force_FFT  = 0;
  force_poly = 0;
  plot_type  = 1;
%%
%%  decode and check optional arguments
%%  end_numeric_args is boolean; becomes true at 1st string arg
  end_numeric_args = 0;
  for iarg = 1:length(varargin)
    arg = varargin{iarg};
    end_numeric_args = end_numeric_args || ischar(arg);
    %% skip empty arguments
    if ( isempty(arg) )
      1; 
    %% numeric optional arguments must be first, cannot follow string args
    %% N.B. older versions of matlab may not have the function "error" so
    %% the user writes "function error(msg); disp(msg); end" and we need
    %% a "return" here.
    elseif ( ~ischar(arg) )
      if ( end_numeric_args )
        error( 'ar_psd: control arg must be string.' );
      %%
      %% first optional numeric arg is "freq"
      elseif ( iarg == 1 )
        user_freqs = isvector(arg) && length(arg)>1;
        if ( ~isscalar(arg) && ~user_freqs )
          error( 'ar_psd: arg 3 (freq) must be vector or scalar.' );
        elseif ( ~user_freqs && ( ~isreal(arg) || ...
                 fix(arg)~=arg || arg <= 2 || arg >= 1048576 ) )
          error('ar_psd: arg 3 (freq) must be integer >=2, <=1048576' );
        elseif ( user_freqs && ~isreal(arg) )
          error( 'ar_psd: arg 3 (freq) vector must be real.' );
        end
        freq = arg(:); % -> column vector
      %%
      %% second optional numeric arg is  "Fs" - sampling frequency
      elseif ( iarg == 2 )
        if ( ~isscalar(arg) || ~isreal(arg) || arg<=0 )
          error( 'ar_psd: arg 4 (Fs) must be real positive scalar.' );
        end
        Fs = arg;
      %%
      else
        error( 'ar_psd: control arg must be string.' );
      end
  %%
  %% decode control-string arguments
    elseif ( strcmp(arg,'plot') || strcmp(arg,'squared') )
      plot_type = 1;
    elseif ( strcmp(arg,'semilogx') )
      plot_type = 2;
    elseif ( strcmp(arg,'semilogy') )
      plot_type = 3;
    elseif ( strcmp(arg,'loglog') )
      plot_type = 4;
    elseif ( strcmp(arg,'dB') )
      plot_type = 5;
    elseif ( strcmp(arg,'fft') )
      force_FFT  = 1;
      force_poly = 0;
    elseif ( strcmp(arg,'poly') )
      force_FFT  = 0;
      force_poly = 1;
    elseif ( strcmp(arg,'half') || strcmp(arg,'onesided') )
      pad_fact = 2;    % FFT zero-padding factor (pad FFT to double length)
      do_shift = 0;
    elseif ( strcmp(arg,'whole') || strcmp(arg,'twosided') )
      pad_fact = 1;    % FFT zero-padding factor (do not pad)
      do_shift = 0;
    elseif ( strcmp(arg,'shift') || strcmp(arg,'centerdc') )
      pad_fact = 1;
      do_shift = 1;
    else
      error( 'ar_psd: string arg: illegal value: %s', arg ); 
    end 
  end
%%  end of decoding and checking args
%%
  if ( user_freqs )
    %% user provides (column) vector of frequencies
    if ( any(abs(freq)>Fs/2) )
      error( 'ar_psd: arg 3 (freq) cannot exceed half sampling frequency.' );
    elseif ( pad_fact==2 && any(freq<0) )
      error( 'ar_psd: arg 3 (freq) must be positive in onesided spectrum' );
    end
    freq_len = length(freq);
    fft_len  = freq_len;
    use_FFT  = 0;
    do_shift = 0;
  else
    %% internally generated frequencies
    freq_len = freq;
    freq = (Fs/pad_fact/freq_len) * [0:freq_len-1]';
    %% decide which method to use (poly or FFT)
    is_power_of_2 = rem(log(freq_len),log(2))<10.*eps;
    use_FFT = ( ~ force_poly && is_power_of_2 ) || force_FFT;
    fft_len = freq_len * pad_fact;
    end
  end
  %%
  %% calculate denominator of Equation 2.28, Kay and Marple, ref [1]Jr.:
  len_coeffs = length(a);
  if ( use_FFT )
    %% FFT method
    fft_out = fft( [ a(:); zeros(fft_len-len_coeffs,1) ] );
  else
    %% polynomial method
    %% complex data on "half" frequency range needs -ve frequency values
    if ( pad_fact==2 && ~real_model )
      freq = [freq; -freq(freq_len:-1:1)];
      fft_len = 2*freq_len;
    end
    fft_out = polyval( a(len_coeffs:-1:1), exp( (-i*2*pi/Fs) * freq ) );
  end
  %%
  %% The power spectrum (PSD) is the scaled squared reciprocal of amplitude
  %% of the FFT/polynomial. This is NOT the reciprocal of the periodogram.
  %% The PSD is a continuous function of frequency.  For uniformly
  %% distributed frequency values, the FFT algorithm might be the most
  %% efficient way of calculating it.
  %%
  psd = ( v / Fs ) ./ ( fft_out .* conj(fft_out) );
  %%
  %% range='half' or 'onesided',
  %%   add PSD at -ve frequencies to PSD at +ve frequencies
  %% N.B. unlike periodogram, PSD at zero frequency _is_ doubled.
  if ( pad_fact==2 )
    freq = freq(1:freq_len);
    if ( real_model )
      %% real data, double the psd
      psd = 2 * psd(1:freq_len);
    elseif ( use_FFT )
      %% complex data, FFT method, internally-generated frequencies
      psd = psd(1:freq_len)+[psd(1); psd(fft_len:-1:freq_len+2)];
    else
      %% complex data, polynomial method
      %%  user-defined and internally-generated frequencies
      psd = psd(1:freq_len)+psd(fft_len:-1:freq_len+1);
    end
  %% 
  %% range='shift'
  %%   disabled for user-supplied frequencies
  %%   Shift zero-frequency to the middle (pad_fact==1)
  elseif ( do_shift )
    len2 = fix((fft_len+1)/2);
    psd  = [psd(len2+1:fft_len); psd(1:len2)];
    freq = [freq(len2+1:fft_len)-Fs; freq(1:len2)];
  end
  %%
  %% Plot the spectrum if there are no return variables.
  if ( nargout >= 2 )
     varargout{1} = psd;
     varargout{2} = freq;
  elseif ( nargout == 1 )
     varargout{1} = psd;
  else
    if ( plot_type == 1 )
      plot(freq,psd);
    elseif ( plot_type == 2 )
      semilogx(freq,psd);
    elseif ( plot_type == 3 )
      semilogy(freq,psd);
    elseif ( plot_type == 4 )
      loglog(freq,psd);
    elseif ( plot_type == 5 )
      plot(freq,10*log10(psd));
    end
  end
end
