## Copyright (C) 2008 Bill Denney <bill@denney.ws>
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

## -*- texinfo -*-
## @deftypefn {Function File} {} pointfig (@var{asset})
##
## Plot the point figure chart of an @var{asset}.  Upward price
## movements are plotted as Xs and downward movements are plotted as Os.
##
## @seealso{bolling, candle, dateaxis, highlow, movavg}
## @end deftypefn

function pointfig (asset)

  if nargin != 1
    print_usage ();
  endif

  upmask    = asset(2:end) > asset(1:end-1);
  # if the data is equal, it will not change the trend
  equalmask = asset(2:end) == asset(1:end-1);
  downmask  = asset(2:end) < asset(1:end-1);

  lx        = 0;
  ly        = 0;
  direction = 0;
  up        = zeros(0,2);
  down      = zeros(0,2);

  for i = 1:length (upmask)
    if direction > 0 && (upmask(i) || equalmask(i))
      ## moving in the same direction as previously: up
      ly         += 1;
      up(end+1,:) = [lx ly];
    elseif direction < 0 && (downmask(i) || equalmask(i))
      ## moving in the same direction as previously: down
      ly           -= 1;
      down(end+1,:) = [lx ly];
    else
      ## moving in a different direction than previously
      lx += 1;
      if upmask(i)
        up(end+1,:) = [lx ly];
        direction   = 1;
      else
        down(end+1,:) = [lx ly];
        direction     = -1;
      endif
    endif
  endfor

  hstat = ishold();
  hold("on");
  plot(up(:,1), up(:,2), "x", "color", [0 0 1]);
  plot(down(:,1), down(:,2), "o", "color", [1 0 0]);
  if ! hstat
    hold("off");
  endif

endfunction

## Tests
%!shared a
%! a = [1 2 3 2 4 2 1];
%!test
%! [s l] = movavg(a, 2, 4);
%! assert(s, [1 1.5 2.5 2.5 3 3 1.5])
%! assert(l, [1 1.5 2 2 2.75 2.75 2.25])
%!test
%! [s l] = movavg(a', 2, 4);
%! assert(s, [1;1.5;2.5;2.5;3;3;1.5])
%! assert(l, [1;1.5;2;2;2.75;2.75;2.25])
%!test
%! [s l] = movavg(a, 3, 4, 1);
%! assert(s, [3 4.8 7 7 9.5 8 5.5]./3, 10*eps)
%! assert(l, [1 11/7 20/9 2.2 3 2.7 2], 10*eps)
