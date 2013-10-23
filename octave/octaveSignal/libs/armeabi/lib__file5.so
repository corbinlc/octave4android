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

%% [a,v,k] = arburg(x,poles,criterion)
%%
%% Calculate coefficients of an autoregressive (AR) model of complex data
%% "x" using the whitening lattice-filter method of Burg (1968).  The inverse
%% of the model is a moving-average filter which reduces "x" to white noise.
%% The power spectrum of the AR model is an estimate of the maximum
%% entropy power spectrum of the data.  The function "ar_psd" calculates the
%% power spectrum of the AR model.
%%
%% ARGUMENTS:
%%   x         %% [vector] sampled data
%%
%%   poles     %% [integer scalar] number of poles in the AR model or
%%             %%       limit to the number of poles if a
%%             %%       valid "stop_crit" is provided.
%%
%%   criterion %% [optional string arg]  model-selection criterion.  Limits
%%             %%       the number of poles so that spurious poles are not 
%%             %%       added when the whitened data has no more information
%%             %%       in it (see Kay & Marple, 1981). Recognised values are
%%             %%  'AKICc' -- approximate corrected Kullback information
%%             %%             criterion (recommended),
%%             %%   'KIC'  -- Kullback information criterion
%%             %%   'AICc' -- corrected Akaike information criterion
%%             %%   'AIC'  -- Akaike information criterion
%%             %%   'FPE'  -- final prediction error" criterion
%%             %% The default is to NOT use a model-selection criterion
%%
%% RETURNED VALUES:
%%   a         %% [polynomial/vector] list of (P+1) autoregression coeffic-
%%             %%       ients; for data input x(n) and  white noise e(n),
%%             %%       the model is
%%             %%                             P+1
%%             %%       x(n) = sqrt(v).e(n) + SUM a(k).x(n-k)
%%             %%                             k=1
%%
%%   v         %% [real scalar] mean square of residual noise from the
%%             %%       whitening operation of the Burg lattice filter.
%%
%%   k         %% [column vector] reflection coefficients defining the
%%             %%       lattice-filter embodiment of the model
%%
%% HINTS:
%%  (1) arburg does not remove the mean from the data.  You should remove
%%      the mean from the data if you want a power spectrum.  A non-zero mean
%%      can produce large errors in a power-spectrum estimate.  See
%%      "help detrend".
%%  (2) If you don't know what the value of "poles" should be, choose the
%%      largest (reasonable) value you could want and use the recommended
%%      value, criterion='AKICc', so that arburg can find it.
%%      E.g. arburg(x,64,'AKICc')
%%      The AKICc has the least bias and best resolution of the available
%%      model-selection criteria.
%%  (3) arburg runs in octave and matlab, does not depend on octave forge
%%      or signal-processing-toolbox functions.
%%  (4) Autoregressive and moving-average filters are stored as polynomials
%%      which, in matlab, are row vectors.
%%
%% NOTE ON SELECTION CRITERION
%%   AIC, AICc, KIC and AKICc are based on information theory.  They  attempt
%%   to balance the complexity (or length) of the model against how well the
%%   model fits the data.  AIC and KIC are biassed estimates of the asymmetric
%%   and the symmetric Kullback-Leibler divergence respectively.  AICc and
%%   AKICc attempt to correct the bias. See reference [4].
%%
%%
%% REFERENCES
%% [1] John Parker Burg (1968)
%%   "A new analysis technique for time series data",
%%   NATO advanced study Institute on Signal Processing with Emphasis on
%%   Underwater Acoustics, Enschede, Netherlands, Aug. 12-23, 1968.
%%
%% [2] Steven M. Kay and Stanley Lawrence Marple Jr.:
%%   "Spectrum analysis -- a modern perspective",
%%   Proceedings of the IEEE, Vol 69, pp 1380-1419, Nov., 1981
%%
%% [3] William H. Press and Saul A. Teukolsky and William T. Vetterling and
%%               Brian P. Flannery
%%   "Numerical recipes in C, The art of scientific computing", 2nd edition,
%%   Cambridge University Press, 2002 --- Section 13.7.
%%
%% [4] Abd-Krim Seghouane and Maiza Bekara
%%   "A small sample model selection criterion based on Kullback's symmetric
%%   divergence", IEEE Transactions on Signal Processing,
%%   Vol. 52(12), pp 3314-3323, Dec. 2004


function [varargout] = arburg( x, poles, criterion )
%%
%% Check arguments
if ( nargin < 2 )
  error( 'arburg(x,poles): Need at least 2 args.' );
elseif ( ~isvector(x) || length(x) < 3 )
  error( 'arburg: arg 1 (x) must be vector of length >2.' );
elseif ( ~isscalar(poles) || ~isreal(poles) || fix(poles)~=poles || poles<=0.5)
  error( 'arburg: arg 2 (poles) must be positive integer.' );
elseif ( poles >= length(x)-2 )
  %% lattice-filter algorithm requires "poles<length(x)"
  %% AKICc and AICc require "length(x)-poles-2">0
  error( 'arburg: arg 2 (poles) must be less than length(x)-2.' );
elseif ( nargin>2 && ~isempty(criterion) && ...
         (~ischar(criterion) || size(criterion,1)~=1 ) )
  error( 'arburg: arg 3 (criterion) must be string.' );
else
  %%
  %%  Set the model-selection-criterion flags.
  %%  is_AKICc, isa_KIC and is_corrected are short-circuit flags
  if ( nargin > 2 && ~isempty(criterion) )
    is_AKICc = strcmp(criterion,'AKICc');                 %% AKICc
    isa_KIC  = is_AKICc || strcmp(criterion,'KIC');       %% KIC or AKICc
    is_corrected = is_AKICc || strcmp(criterion,'AICc');  %% AKICc or AICc
    use_inf_crit = is_corrected || isa_KIC || strcmp(criterion,'AIC');
    use_FPE = strcmp(criterion,'FPE');
    if ( ~use_inf_crit && ~use_FPE )
      error( 'arburg: value of arg 3 (criterion) not recognised' );
    end
  else
    use_inf_crit = 0;
    use_FPE = 0;
  end
  %%
  %% f(n) = forward prediction error
  %% b(n) = backward prediction error
  %% Storage of f(n) and b(n) is a little tricky. Because f(n) is always
  %% combined with b(n-1), f(1) and b(N) are never used, and therefore are
  %% not stored.  Not storing unused data makes the calculation of the
  %% reflection coefficient look much cleaner :)
  %% N.B. {initial v} = {error for zero-order model} =
  %%      {zero-lag autocorrelation} =  E(x*conj(x)) = x*x'/N
  %%      E = expectation operator
  N = length(x);
  k = [];
  if ( size(x,1) > 1 ) % if x is column vector
    f = x(2:N);    
    b = x(1:N-1);
    v = real(x'*x) / N;
  else                 % if x is row vector
    f = x(2:N).';
    b = x(1:N-1).';
    v = real(x*x') / N;
  end
  %% new_crit/old_crit is the mode-selection criterion
  new_crit = abs(v);
  old_crit = 2 * new_crit;
  for p = 1:poles
    %%
    %% new reflection coeff = -2* E(f.conj(b)) / ( E(f^2)+E(b(^2) )
    last_k= -2 * (b' * f) / ( f' * f + b' * b);
    %%  Levinson-Durbin recursion for residual
    new_v = v * ( 1.0 - real(last_k * conj(last_k)) );
    if ( p > 1 )
      %%
      %% Apply the model-selection criterion and break out of loop if it
      %% increases (rather than decreases).
      %% Do it before we update the old model "a" and "v".
      %%
      %% * Information Criterion (AKICc, KIC, AICc, AIC)
      if ( use_inf_crit )
        old_crit = new_crit;
        %% AKICc = log(new_v)+p/N/(N-p)+(3-(p+2)/N)*(p+1)/(N-p-2);
        %% KIC   = log(new_v)+           3         *(p+1)/N;
        %% AICc  = log(new_v)+           2         *(p+1)/(N-p-2);
        %% AIC   = log(new_v)+           2         *(p+1)/N;
	%% -- Calculate KIC, AICc & AIC by using is_AKICc, is_KIC and
        %%    is_corrected to "short circuit" the AKICc calculation.
        %%    The extra 4--12 scalar arithmetic ops should be quicker than
        %%    doing if...elseif...elseif...elseif...elseif.  
        new_crit = log(new_v) + is_AKICc*p/N/(N-p) + ...
          (2+isa_KIC-is_AKICc*(p+2)/N) * (p+1) / (N-is_corrected*(p+2));
        if ( new_crit > old_crit )
          break;
        end
      %%
      %% (FPE) Final prediction error
      elseif ( use_FPE )
        old_crit = new_crit;
        new_crit = new_v * (N+p+1)/(N-p-1);
        if ( new_crit > old_crit )
          break;
        end
      end
      %% Update model "a" and "v".
      %% Use Levinson-Durbin recursion formula (for complex data).
      a = [ prev_a + last_k .* conj(prev_a(p-1:-1:1))  last_k ];
    else %% if( p==1 )
      a = last_k;
    end
    k = [ k; last_k ];
    v = new_v;
    if ( p < poles )
      prev_a = a;
      %%  calculate new prediction errors (by recursion):
      %%  f(p,n) = f(p-1,n)   + k * b(p-1,n-1)        n=2,3,...n
      %%  b(p,n) = b(p-1,n-1) + conj(k) * f(p-1,n)    n=2,3,...n
      %%  remember f(p,1) is not stored, so don't calculate it; make f(p,2)
      %%  the first element in f.  b(p,n) isn't calculated either.
      nn = N-p;
      new_f = f(2:nn) + last_k * b(2:nn);
      b = b(1:nn-1) + conj(last_k) * f(1:nn-1);
      f = new_f;
    end
  end
  %% end of for loop
  %%
  varargout{1} = [1 a];
  varargout{2} = v;
  if ( nargout>=3 )
    varargout{3} = k;
  end
end 
end 

%% 
