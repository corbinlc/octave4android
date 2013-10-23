## Copyright (C) 1999-2000 Paul Kienzle <pkienzle@users.sf.net>
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

## usage: y = chirp(t [, f0 [, t1 [, f1 [, form [, phase]]]]])
##
## Evaluate a chirp signal at time t.  A chirp signal is a frequency
## swept cosine wave.
##
## t: vector of times to evaluate the chirp signal
## f0: frequency at time t=0 [ 0 Hz ]
## t1: time t1 [ 1 sec ]
## f1: frequency at time t=t1 [ 100 Hz ]
## form: shape of frequency sweep
##    'linear'      f(t) = (f1-f0)*(t/t1) + f0
##    'quadratic'   f(t) = (f1-f0)*(t/t1)^2 + f0
##    'logarithmic' f(t) = (f1-f0)^(t/t1) + f0
## phase: phase shift at t=0
##
## Example
##    specgram(chirp([0:0.001:5])); # linear, 0-100Hz in 1 sec
##    specgram(chirp([-2:0.001:15], 400, 10, 100, 'quadratic'));
##    soundsc(chirp([0:1/8000:5], 200, 2, 500, "logarithmic"),8000);
##
## If you want a different sweep shape f(t), use the following:
##    y = cos(2*pi*integral(f(t)) + 2*pi*f0*t + phase);

function y = chirp(t, f0, t1, f1, form, phase)

  if nargin < 1 || nargin > 6
    print_usage;
  endif
  if nargin < 2, f0 = []; endif
  if nargin < 3, t1 = []; endif
  if nargin < 4, f1 = []; endif
  if nargin < 5, form = []; endif
  if nargin < 6, phase = []; endif

  if isempty(f0), f0 = 0; endif
  if isempty(t1), t1 = 1; endif
  if isempty(f1), f1 = 100; endif
  if isempty(form), form = "linear"; endif
  if isempty(phase), phase = 0; endif

  phase = 2*pi*phase/360;

  if strcmp(form, "linear")
    a = pi*(f1 - f0)/t1;
    b = 2*pi*f0;
    y = cos(a*t.^2 + b*t + phase);
  elseif strcmp(form, "quadratic")
    a = (2/3*pi*(f1-f0)/t1/t1);
    b = 2*pi*f0;
    y = cos(a*t.^3 + b*t + phase);
  elseif strcmp(form, "logarithmic")
    a = 2*pi*t1/log(f1-f0);
    b = 2*pi*f0;
    x = (f1-f0)^(1/t1);
    y = cos(a*x.^t + b*t + phase);
  else
    error("chirp doesn't understand '%s'",form);
  endif

endfunction

%!demo
%! specgram(chirp([0:0.001:5]),[],1000); # linear, 0-100Hz in 1 sec
%! %------------------------------------------------------------
%! % Shows linear sweep of 100 Hz/sec starting at zero for 5 sec
%! % since the sample rate is 1000 Hz, this should be a diagonal
%! % from bottom left to top right.

%!demo
%! specgram(chirp([-2:0.001:15], 400, 10, 100, 'quadratic'));
%! %------------------------------------------------------------
%! % Shows a quadratic chirp of 400 Hz at t=0 and 100 Hz at t=10
%! % Time goes from -2 to 15 seconds.

%!demo
%! specgram(chirp([0:1/8000:5], 200, 2, 500, "logarithmic"),[],8000);
%! %------------------------------------------------------------
%! % Shows a logarithmic chirp of 200 Hz at t=0 and 500 Hz at t=2
%! % Time goes from 0 to 5 seconds at 8000 Hz.
