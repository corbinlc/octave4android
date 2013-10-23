## Copyright (C) 2001 Paul Kienzle <pkienzle@users.sf.net>
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

## usage: y = tripuls(t, w, skew)
##
## Generate a triangular pulse over the interval [-w/2,w/2), sampled at
## times t.  This is useful with the function pulstran for generating a
## series pulses.
##
## skew is a value between -1 and 1, indicating the relative placement
## of the peak within the width.  -1 indicates that the peak should be
## at -w/2, and 1 indicates that the peak should be at w/2.
##
## Example
##   fs = 11025;  # arbitrary sample rate
##   f0 = 100;    # pulse train sample rate
##   w = 0.3/f0;  # pulse width 3/10th the distance between pulses
##   auplot(pulstran(0:1/fs:4/f0, 0:1/f0:4/f0, 'tripuls', w), fs);
##
## See also: pulstran

function y = tripuls (t, w = 1, skew = 0)

  if nargin<1 || nargin>3,
    print_usage;
  endif

  y = zeros(size(t));
  peak = skew*w/2;
  try wfi = warning("off", "Octave:fortran-indexing");
  catch wfi = 0;
  end
  unwind_protect
    idx = find(t>=-w/2 & t <= peak);
    if (idx) y(idx) = ( t(idx) + w/2 ) / ( peak + w/2 ); endif
    idx = find(t>peak & t < w/2);
    if (idx) y(idx) = ( t(idx) - w/2 ) / ( peak - w/2 ); endif
  unwind_protect_cleanup
    warning(wfi);
  end_unwind_protect
endfunction

%!assert(tripuls(0:1/100:0.3,.1), tripuls([0:1/100:0.3]',.1)');
%!assert(isempty(tripuls([],.1)));
%!demo
%! fs = 11025;  # arbitrary sample rate
%! f0 = 100;    # pulse train sample rate
%! w = 0.5/f0;  # pulse width 1/10th the distance between pulses
%! subplot(211); ylabel("amplitude"); xlabel("time (ms)");
%! title("graph shows 5 ms pulses at 0,10,20,30 and 40 ms");
%! auplot(pulstran(0:1/fs:4/f0, 0:1/f0:4/f0, 'tripuls', w), fs);
%! subplot(212);
%! title("graph shows 5 ms pulses at 0,10,20,30 and 40 ms, skew -0.5");
%! auplot(pulstran(0:1/fs:4/f0, 0:1/f0:4/f0, 'tripuls', w, -0.5), fs);
%! title(""); xlabel(""); ylabel("");
