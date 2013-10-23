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

%% usage:
%%    [psd,f_out] = pburg(x,poles,freq,Fs,range,method,plot_type,criterion)
%%
%% Calculate Burg maximum-entropy power spectral density.
%% The functions "arburg" and "ar_psd" do all the work.
%% See "help arburg" and "help ar_psd" for further details.
%%
%% ARGUMENTS:
%%     All but the first two arguments are optional and may be empty.
%%   x       %% [vector] sampled data
%%
%%   poles   %% [integer scalar] required number of poles of the AR model
%%
%%   freq    %% [real vector] frequencies at which power spectral density
%%           %%               is calculated
%%           %% [integer scalar] number of uniformly distributed frequency
%%           %%          values at which spectral density is calculated.
%%           %%          [default=256]
%%
%%   Fs      %% [real scalar] sampling frequency (Hertz) [default=1]
%%
%%
%% CONTROL-STRING ARGUMENTS -- each of these arguments is a character string.
%%   Control-string arguments can be in any order after the other arguments.
%%
%%
%%   range   %% 'half',  'onesided' : frequency range of the spectrum is
%%           %%       from zero up to but not including sample_f/2.  Power
%%           %%       from negative frequencies is added to the positive
%%           %%       side of the spectrum.
%%           %% 'whole', 'twosided' : frequency range of the spectrum is
%%           %%       -sample_f/2 to sample_f/2, with negative frequencies
%%           %%       stored in "wrap around" order after the positive
%%           %%       frequencies; e.g. frequencies for a 10-point 'twosided'
%%           %%       spectrum are 0 0.1 0.2 0.3 0.4 0.5 -0.4 -0.3 -0.2 -0.1
%%           %% 'shift', 'centerdc' : same as 'whole' but with the first half
%%           %%       of the spectrum swapped with second half to put the
%%           %%       zero-frequency value in the middle. (See "help
%%           %%       fftshift". If "freq" is vector, 'shift' is ignored.
%%           %% If model coefficients "ar_coeffs" are real, the default
%%           %% range is 'half', otherwise default range is 'whole'.
%%
%%   method  %% 'fft':  use FFT to calculate power spectral density.
%%           %% 'poly': calculate spectral density as a polynomial of 1/z
%%           %% N.B. this argument is ignored if the "freq" argument is a
%%           %%      vector.  The default is 'poly' unless the "freq"
%%           %%      argument is an integer power of 2.
%%   
%% plot_type %% 'plot', 'semilogx', 'semilogy', 'loglog', 'squared' or 'db':
%%           %% specifies the type of plot.  The default is 'plot', which
%%           %% means linear-linear axes. 'squared' is the same as 'plot'.
%%           %% 'dB' plots "10*log10(psd)".  This argument is ignored and a
%%           %% spectrum is not plotted if the caller requires a returned
%%           %% value.
%%
%% criterion %% [optional string arg]  model-selection criterion.  Limits
%%           %%       the number of poles so that spurious poles are not 
%%           %%       added when the whitened data has no more information
%%           %%       in it (see Kay & Marple, 1981). Recognised values are
%%           %%  'AKICc' -- approximate corrected Kullback information
%%           %%             criterion (recommended),
%%           %%   'KIC'  -- Kullback information criterion
%%           %%   'AICc' -- corrected Akaike information criterion
%%           %%   'AIC'  -- Akaike information criterion
%%           %%   'FPE'  -- final prediction error" criterion
%%           %% The default is to NOT use a model-selection criterion
%%
%% RETURNED VALUES:
%%     If return values are not required by the caller, the spectrum
%%     is plotted and nothing is returned.
%%   psd       %% [real vector] power-spectral density estimate 
%%   f_out     %% [real vector] frequency values 
%%
%% HINTS
%%   This function is a wrapper for arburg and ar_psd.
%%   See "help arburg", "help ar_psd".

function [psd,f_out]=pburg(x,poles,varargin)
  %%
  if ( nargin<2 )
    error( 'pburg: need at least 2 args. Use "help pburg"' );
  end
  nvarargin=length(varargin);
  criterion=[];
  %%
  %% Search for a "criterion" arg. If found, remove it
  %% from "varargin" list and feed it to arburg instead.
  for iarg = 1: nvarargin
    arrgh = varargin{iarg};
    if ( ischar(arrgh) && ( strcmp(arrgh,'AKICc') ||...
         strcmp(arrgh,'KIC') || strcmp(arrgh,'AICc') ||...
         strcmp(arrgh,'AIC') || strcmp(arrgh,'FPE') ) )
      criterion=arrgh;
      if ( nvarargin>1 )
        varargin{iarg}= [];
      else
        varargin={};
        end
      end
    end
  %%
  [ar_coeffs,residual]=arburg(x,poles,criterion);
  if ( nargout==0 )
    ar_psd(ar_coeffs,residual,varargin{:});
  elseif ( nargout==1 )
    psd = ar_psd(ar_coeffs,residual,varargin{:});
  elseif ( nargout>=2 )
    [psd,f_out] = ar_psd(ar_coeffs,residual,varargin{:});
  end
end

%!demo
%! fflush(stdout);
%! rand('seed',2038014164);
%! a = [ 1.0 -1.6216505 1.1102795 -0.4621741 0.2075552 -0.018756746 ];
%! signal = detrend(filter(0.70181,a,rand(1,16384)));
%! % frequency shift by modulating with exp(j.omega.t) 
%! skewed = signal.*exp(2*pi*i*2/25*[1:16384]);
%! Fs = 25;
%! hold on
%! pburg(signal,3,[],Fs);
%! input('Onesided 3-pole spectrum. Press ENTER', 's' );
%! pburg(signal,4,[],Fs,'whole');
%! input('Twosided 4-pole spectrum of same data. Press ENTER', 's' );
%! pburg(signal,5,128,Fs,'shift', 'semilogy');
%! input('Twosided, centred zero-frequency, 5-pole. Press ENTER', 's' );
%! pburg(skewed,7,128,Fs,'AKICc','shift','semilogy');
%! input('Complex data, AKICc chooses no. of poles. Press ENTER', 's' );
%! user_freq=[-0.2:0.02:0.2]*Fs;
%! pburg(skewed,7,user_freq,Fs,'AKICc','semilogy');
%! input('User-specified frequency values. Press ENTER', 's' );
%! hold off
%! clf
