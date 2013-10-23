%% Copyright (C) 1986,2003 Julius O. Smith III <jos@ccrma.stanford.edu>
%% Copyright (C) 2003 Andrew Fitting
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

%% usage: [B,A] = invfreqz(H,F,nB,nA)
%%        [B,A] = invfreqz(H,F,nB,nA,W)
%%        [B,A] = invfreqz(H,F,nB,nA,W,iter,tol,'trace')
%%
%% Fit filter B(z)/A(z)to the complex frequency response H at frequency
%% points F.  A and B are real polynomial coefficients of order nA and nB.
%% Optionally, the fit-errors can be weighted vs frequency according to
%% the weights W.
%% Note: all the guts are in invfreq.m
%%
%% H: desired complex frequency response
%% F: normalized frequncy (0 to pi) (must be same length as H)
%% nA: order of the denominator polynomial A
%% nB: order of the numerator polynomial B
%% W: vector of weights (must be same length as F)
%%
%% Example:
%%     [B,A] = butter(4,1/4);
%%     [H,F] = freqz(B,A);
%%     [Bh,Ah] = invfreq(H,F,4,4);
%%     Hh = freqz(Bh,Ah);
%%     disp(sprintf('||frequency response error|| = %f',norm(H-Hh)));

%% TODO: check invfreq.m for todo's

function [B, A, SigN] = invfreqz(H, F, nB, nA, W, iter, tol, tr, varargin)

if nargin < 9
  varargin = {};
  if nargin < 8
    tr = '';
    if nargin < 7
        tol = [];
        if nargin < 6 
            iter = [];
            if nargin < 5
                W = ones(1,length(F));
            end
        end
    end
  end
end


% now for the real work
[B, A, SigN] = invfreq(H, F, nB, nA, W, iter, tol, tr, 'z', varargin{:});

endfunction

%!demo
%! order = 9; % order of test filter
%! % going to 10 or above leads to numerical instabilities and large errors
%! fc = 1/2;   % sampling rate / 4
%! n = 128;    % frequency grid size
%! [B0, A0] = butter(order, fc);
%! [H0, w] = freqz(B0, A0, n);
%! Nn = (randn(size(w))+j*randn(size(w)))/sqrt(2);
%! [Bh, Ah, Sig0] = invfreqz(H0, w, order, order);
%! [Hh, wh] = freqz(Bh, Ah, n);
%! [BLS, ALS, SigLS] = invfreqz(H0+1e-5*Nn, w, order, order, [], [], [], [], "method", "LS");
%! HLS = freqz(BLS, ALS, n);
%! [BTLS, ATLS, SigTLS] = invfreqz(H0+1e-5*Nn, w, order, order, [], [], [], [], "method", "TLS");
%! HTLS = freqz(BTLS, ATLS, n);
%! [BMLS, AMLS, SigMLS] = invfreqz(H0+1e-5*Nn, w, order, order, [], [], [], [], "method", "QR");
%! HMLS = freqz(BMLS, AMLS, n);
%! xlabel("Frequency (rad/sample)");
%! ylabel("Magnitude");
%! plot(w,[abs(H0) abs(Hh)])
%! legend('Original','Measured');
%! err = norm(H0-Hh);
%! disp(sprintf('L2 norm of frequency response error = %f',err));
