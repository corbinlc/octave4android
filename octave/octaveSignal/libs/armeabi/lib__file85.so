## Copyright (c) 2012 Juan Pablo Carbajal <carbajal@ifi.uzh.ch>
##
## This program is free software; you can redistribute it and/or modify
## it under the terms of the GNU General Public License as published by
## the Free Software Foundation; either version 3 of the License, or
## (at your option) any later version.
##
## This program is distributed in the hope that it will be useful,
## but WITHOUT ANY WARRANTY; without even the implied warranty of
## MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
## GNU General Public License for more details.
##
## You should have received a copy of the GNU General Public License
## along with this program; if not, see <http://www.gnu.org/licenses/>.

## -*- texinfo -*-
## @deftypefn {Function File} {[@var{rmsx},@var{w}] =} movingrms (@var{x},@var{w},@var{rc},@var{Fs}=1)
## Calculates moving RMS value of the signal in @var{x}.
##
## The signla is convoluted against a sigmoid window of width @var{w} and risetime
## @var{rc}. The units of these to parameters are relative ot the vlaue of the sampling
## frequency given in @var{Fs} (Default value = 1).
##
## Run @code{demo movingrms} to see an example.
##
## @seealso{sigmoid_train}
## @end deftypefn

function [rmsx w]= movingrms (x,width, risetime, Fs=1)

  [N nc] = size (x);
  if width*Fs > N/2
    idx = [1 N];
    w    = ones(N,1);
  else
    idx   = round ((N + width*Fs*[-1 1])/2);
    w     = sigmoid_train ((1:N)', idx, risetime*Fs);
  end
  fw    = fft (w.^2);
  fx    = fft (x.^2);
  rmsx  = real(ifft (fx.*fw)/(N-1));

  rmsx (rmsx < eps*max(rmsx(:))) = 0;

  rmsx  = circshift (sqrt (rmsx), round(mean(idx)));
%  w     = circshift (w, -idx(1));

endfunction

%!demo
%! N = 128;
%! t = linspace(0,1,N)';
%! x = sigmoid_train (t,[0.4 inf],1e-2).*(2*rand(size(t))-1);
%!
%! Fs    = 1/diff(t(1:2));
%! width = 0.05;
%! rc = 5e-3;
%! [wx w] = movingrms (zscore (x),width,rc,Fs);
%!
%! close all
%! figure ()
%!
%! area (t,wx,'facecolor',[0.85 0.85 1],'edgecolor','b','linewidth',2);
%! hold on;
%! h = plot (t,x,'r-;Data;',t,w,'g-;Window;');
%! set (h, 'linewidth', 2);
%! hold off
%!
%! % ---------------------------------------------------------------------------
%! % The shaded plot shows the local RMS of the Data: white noise with onset at
%! % aprox. t== 0.4.
%! % The observation window is also shown.

%!demo
%! N = 128;
%! t = linspace(0,1,N)';
%! x = exp(-((t-0.5)/0.1).^2) + 0.1*rand(N,1);
%!
%! Fs    = 1/diff(t(1:2));
%! width = 0.1;
%! rc = 2e-3;
%! [wx w] = movingrms (zscore (x),width,rc,Fs);
%!
%! close all
%! figure ()
%!
%! area (t,wx,'facecolor',[0.85 0.85 1],'edgecolor','b','linewidth',2);
%! hold on;
%! h = plot (t,x,'r-;Data;',t,w,'g-;Window;');
%! set (h, 'linewidth', 2);
%! hold off
%!
%! % ---------------------------------------------------------------------------
%! % The shaded plot shows the local RMS of the Data: Gausian with centered at
%! % aprox. t== 0.5.
%! % The observation window is also shown.
