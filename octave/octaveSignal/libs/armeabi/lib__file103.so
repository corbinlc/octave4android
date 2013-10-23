## Copyright (C) 2000 Paul Kienzle <pkienzle@users.sf.net>
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

## usage: y = rectpuls(t, w)
##
## Generate a rectangular pulse over the interval [-w/2,w/2), sampled at
## times t.  This is useful with the function pulstran for generating a
## series pulses.
##
## Example
##   fs = 11025;  # arbitrary sample rate
##   f0 = 100;    # pulse train sample rate
##   w = 0.3/f0;  # pulse width 3/10th the distance between pulses
##   auplot(pulstran(0:1/fs:4/f0, 0:1/f0:4/f0, 'rectpuls', w), fs);
##
## See also: pulstran

function y = rectpuls(t, w = 1)

  if nargin<1 || nargin>2,
    print_usage;
  endif

  y = zeros(size(t));
  idx = find(t>=-w/2 & t < w/2);
  try wfi = warning("off", "Octave:fortran-indexing");
  catch wfi = 0;
  end
  unwind_protect
    y(idx) = ones(size(idx));
  unwind_protect_cleanup
    warning(wfi);
  end_unwind_protect
endfunction

%!assert(rectpuls(0:1/100:0.3,.1), rectpuls([0:1/100:0.3]',.1)');
%!assert(isempty(rectpuls([],.1)));
%!demo
%! fs = 11025;  # arbitrary sample rate
%! f0 = 100;    # pulse train sample rate
%! w = 0.3/f0;  # pulse width 1/10th the distance between pulses
%! ylabel("amplitude"); xlabel("time (ms)");
%! title("graph shows 3 ms pulses at 0,10,20,30 and 40 ms");
%! auplot(pulstran(0:1/fs:4/f0, 0:1/f0:4/f0, 'rectpuls', w), fs);
%! title(""); xlabel(""); ylabel("");
