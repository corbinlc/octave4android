%% Copyright (C) 1986, 2000, 2003 Julius O. Smith III <jos@ccrma.stanford.edu>
%% Copyright (C) 2007 Rolf Schirmacher <Rolf.Schirmacher@MuellerBBM.de>
%% Copyright (C) 2003 Andrew Fitting
%% Copyright (C) 2010 Pascal Dupuis <Pascal.Dupuis@uclouvain.be>
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

%% usage: [B,A] = invfreq(H,F,nB,nA)
%%        [B,A] = invfreq(H,F,nB,nA,W)
%%        [B,A] = invfreq(H,F,nB,nA,W,[],[],plane)
%%        [B,A] = invfreq(H,F,nB,nA,W,iter,tol,plane)
%%
%% Fit filter B(z)/A(z) or B(s)/A(s) to complex frequency response at 
%% frequency points F. A and B are real polynomial coefficients of order 
%% nA and nB respectively.  Optionally, the fit-errors can be weighted vs 
%% frequency according to the weights W. Also, the transform plane can be
%% specified as either 's' for continuous time or 'z' for discrete time. 'z'
%% is chosen by default.  Eventually, Steiglitz-McBride iterations will be
%% specified by iter and tol.
%%
%% H: desired complex frequency response
%%     It is assumed that A and B are real polynomials, hence H is one-sided.
%% F: vector of frequency samples in radians
%% nA: order of denominator polynomial A
%% nB: order of numerator polynomial B
%% plane='z': F on unit circle (discrete-time spectra, z-plane design)
%% plane='s': F on jw axis     (continuous-time spectra, s-plane design)
%% H(k) = spectral samples of filter frequency response at points zk,
%%  where zk=exp(sqrt(-1)*F(k)) when plane='z' (F(k) in [0,.5])
%%     and zk=(sqrt(-1)*F(k)) when plane='s' (F(k) nonnegative)
%% Example:
%%     [B,A] = butter(12,1/4);
%%     [H,w] = freqz(B,A,128);
%%     [Bh,Ah] = invfreq(H,F,4,4);
%%     Hh = freqz(Bh,Ah);
%%     disp(sprintf('||frequency response error|| = %f',norm(H-Hh)));
%%
%% References: J. O. Smith, "Techniques for Digital Filter Design and System 
%%  	Identification with Application to the Violin, Ph.D. Dissertation, 
%% 	Elec. Eng. Dept., Stanford University, June 1983, page 50; or,
%%
%% http://ccrma.stanford.edu/~jos/filters/FFT_Based_Equation_Error_Method.html

%% TODO: implement Steiglitz-McBride iterations
%% TODO: improve numerical stability for high order filters (matlab is a bit better)
%% TODO: modify to accept more argument configurations

function [B, A, SigN] = invfreq(H, F, nB, nA, W, iter, tol, tr, plane, varargin)
  if length(nB) > 1, zB = nB(2); nB = nB(1); else zB = 0; end
  n = max(nA, nB);
  m = n+1; mA = nA+1; mB = nB+1;
  nF = length(F);
  if nF ~= length(H), disp('invfreqz: length of H and F must be the same'); end;
  if nargin < 5 || isempty(W), W = ones(1, nF); end;
  if nargin < 6, iter = []; end
  if nargin < 7  tol = []; end
  if nargin < 8 || isempty(tr), tr = ''; end
  if nargin < 9, plane = 'z'; end
  if nargin < 10, varargin = {}; end
  if iter~=[], disp('no implementation for iter yet'),end
  if tol ~=[], disp('no implementation for tol yet'),end
  if (plane ~= 'z' && plane ~= 's'), disp('invfreqz: Error in plane argument'), end

  [reg, prop ] = parseparams(varargin);
  %# should we normalise freqs to avoid matrices with rank deficiency ?
  norm = false; 
  %# by default, use Ordinary Least Square to solve normal equations
  method = 'LS';
  if length(prop) > 0
    indi = 1; while indi <= length(prop)
      switch prop{indi}
        case 'norm'
          if indi < length(prop) && ~ischar(prop{indi+1}),
            norm = logical(prop{indi+1});
            prop(indi:indi+1) = [];
            continue
          else
            norm = true; prop(indi) = []; 
            continue
           end
         case 'method'
           if indi < length(prop) && ischar(prop{indi+1}),
             method = prop{indi+1};
             prop(indi:indi+1) = [];
            continue
           else
             error('invfreq.m: incorrect/missing method argument');
           end
         otherwise %# FIXME: just skip it for now
           disp(sprintf("Ignoring unkown argument %s", varargin{indi}));
           indi = indi + 1;
      end
    end
  end

  Ruu = zeros(mB, mB); Ryy = zeros(nA, nA); Ryu = zeros(nA, mB);
  Pu = zeros(mB, 1);   Py = zeros(nA,1);
  if strcmp(tr,'trace')
      disp(' ')
      disp('Computing nonuniformly sampled, equation-error, rational filter.');
      disp(['plane = ',plane]);
      disp(' ')
  end

  s = sqrt(-1)*F;
  switch plane
    case 'z'
      if max(F) > pi || min(F) < 0
      disp('hey, you frequency is outside the range 0 to pi, making my own')
        F = linspace(0, pi, length(H));
      s = sqrt(-1)*F;
    end
    s = exp(-s);
    case 's'
      if max(F) > 1e6 && n > 5,
        if ~norm,
          disp('Be carefull, there are risks of generating singular matrices');
          disp('Call invfreqs as (..., "norm", true) to avoid it');
        else
          Fmax = max(F); s = sqrt(-1)*F/Fmax;
        end
      end
  end
  
  for k=1:nF,
    Zk = (s(k).^[0:n]).';
    Hk = H(k);
    aHks = Hk*conj(Hk);
    Rk = (W(k)*Zk)*Zk';
    rRk = real(Rk);
    Ruu = Ruu + rRk(1:mB, 1:mB);
    Ryy = Ryy + aHks*rRk(2:mA, 2:mA);
    Ryu = Ryu + real(Hk*Rk(2:mA, 1:mB));
    Pu = Pu + W(k)*real(conj(Hk)*Zk(1:mB));
    Py = Py + (W(k)*aHks)*real(Zk(2:mA));
  end;
  Rr = ones(length(s), mB+nA); Zk = s;
  for k = 1:min(nA, nB),
    Rr(:, 1+k) = Zk;
    Rr(:, mB+k) = -Zk.*H;
    Zk = Zk.*s;
  end
  for k = 1+min(nA, nB):max(nA, nB)-1,
    if k <= nB, Rr(:, 1+k) = Zk; end
    if k <= nA, Rr(:, mB+k) = -Zk.*H; end
    Zk = Zk.*s;
  end
  k = k+1;
  if k <= nB, Rr(:, 1+k) = Zk; end
  if k <= nA, Rr(:, mB+k) = -Zk.*H; end
  
  %# complex to real equation system -- this ensures real solution
  Rr = Rr(:, 1+zB:end);
  Rr = [real(Rr); imag(Rr)]; Pr = [real(H(:)); imag(H(:))];
  %# normal equations -- keep for ref
  %# Rn= [Ruu(1+zB:mB, 1+zB:mB), -Ryu(:, 1+zB:mB)';  -Ryu(:, 1+zB:mB), Ryy];
  %# Pn= [Pu(1+zB:mB); -Py];

  switch method
    case {'ls' 'LS'}
      %# avoid scaling errors with Theta = R\P; 
      %# [Q, R] = qr([Rn Pn]); Theta = R(1:end, 1:end-1)\R(1:end, end);
      [Q, R] = qr([Rr Pr], 0); Theta = R(1:end-1, 1:end-1)\R(1:end-1, end);
      %# SigN = R(end, end-1);
      SigN = R(end, end);
    case {'tls' 'TLS'}
      % [U, S, V] = svd([Rn Pn]);
      % SigN = S(end, end-1);
      % Theta =  -V(1:end-1, end)/V(end, end);
      [U, S, V] = svd([Rr Pr], 0);
      SigN = S(end, end);
      Theta =  -V(1:end-1, end)/V(end, end);
    case {'mls' 'MLS' 'qr' 'QR'}
      % [Q, R] = qr([Rn Pn], 0);
      %# solve the noised part -- DO NOT USE ECONOMY SIZE !
      % [U, S, V] = svd(R(nA+1:end, nA+1:end));
      % SigN = S(end, end-1);
      % Theta = -V(1:end-1, end)/V(end, end);
      %# unnoised part -- remove B contribution and back-substitute
      % Theta = [R(1:nA, 1:nA)\(R(1:nA, end) - R(1:nA, nA+1:end-1)*Theta)
      %         Theta];
      %# solve the noised part -- economy size OK as #rows > #columns
      [Q, R] = qr([Rr Pr], 0);
      eB = mB-zB; sA = eB+1;
      [U, S, V] = svd(R(sA:end, sA:end));
      %# noised (A) coefficients
      Theta = -V(1:end-1, end)/V(end, end);
      %# unnoised (B) part -- remove A contribution and back-substitute
      Theta = [R(1:eB, 1:eB)\(R(1:eB, end) - R(1:eB, sA:end-1)*Theta)
              Theta];
      SigN = S(end, end);
    otherwise
      error("invfreq: unknown method %s", method);
  end

  B = [zeros(zB, 1); Theta(1:mB-zB)].';
  A = [1; Theta(mB-zB+(1:nA))].';

  if strcmp(plane,'s')
    B = B(mB:-1:1);
    A = A(mA:-1:1);
    if norm, %# Frequencies were normalised -- unscale coefficients
      Zk = Fmax.^[n:-1:0].';
      for k = nB:-1:1+zB, B(k) = B(k)/Zk(k); end
      for k = nA:-1:1, A(k) = A(k)/Zk(k); end
    end
  end
endfunction

%!demo
%! order = 6; % order of test filter
%! fc = 1/2;   % sampling rate / 4
%! n = 128;    % frequency grid size
%! [B, A] = butter(order,fc);
%! [H, w] = freqz(B,A,n);
%! [Bh, Ah] = invfreq(H,w,order,order);
%! [Hh, wh] = freqz(Bh,Ah,n);
%! xlabel("Frequency (rad/sample)");
%! ylabel("Magnitude");
%! plot(w,[abs(H), abs(Hh)])
%! legend('Original','Measured');
%! err = norm(H-Hh);
%! disp(sprintf('L2 norm of frequency response error = %f',err));
