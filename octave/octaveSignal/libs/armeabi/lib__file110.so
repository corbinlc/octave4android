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
## @deftypefn {Function File} {[@var{rmsx},@var{w}] =} schtrig (@var{x},@var{lvl},@var{rst}=1)
## Implements a multisignal Schmitt trigger with levels @var{lvl}.
##
## The triger works along the first dimension of the array @var{x}. When @code{@var{rst}==1}
## the state of the trigger for all signals is set to the low state (i.e. 0).
##
## Run @code{demo schtrig} to see an example.
##
## @seealso{clustersegment}
## @end deftypefn

function v = schtrig (x, lvl, rst = 1)

  persistent st0;

  if length(lvl) == 1
    lvl = abs (lvl)*[1 -1];
  else
    lvl = sort(lvl,'descend');
  end

  [nT nc] = size(x);

  v = NA (nT, nc);

  if rst || isempty(st0)
    st0 = zeros(1,nc);
    printf ("Trigger initialized!\n");
    flush (stdout);
  end

  v(1,:) = st0;

  % Signal is above up level
  up    = x > lvl(1);
  v(up) = 1;

  % Signal is below down level
  dw    = x < lvl(2);
  v(dw) = 0;

  % Resolve intermediate states
  % Find data between the levels
  idx = isnan (v);
  ranges = clustersegment (idx');

  for i=1:nc
    % Record the state at the begining of the interval between levels
    if !isempty (ranges{i})
      prev         = ranges{i}(1,:)-1;
      prev(prev<1) = 1;
      st0          = v(prev,i);

      % Copy the initial state to the interval
      ini_idx = ranges{i}(1,:);
      end_idx = ranges{i}(2,:);
      for j =1:length(ini_idx)
        v(ini_idx(j):end_idx(j),i) = st0(j);
      end
    end
  end

  st0 = v(end,:);

endfunction

%!demo
%! t = linspace(0,1,100)';
%! x = sin (2*pi*2*t) + sin (2*pi*5*t).*[0.8 0.3];
%!
%! lvl = [0.8 0.25]';
%! v   = schtrig (x,lvl);
%!
%! h = plot(t,x,t,v);
%! set (h([1 3]),'color','b');
%! set (h([2 4]),'color',[0 1 0.5]);
%! set (h,'linewidth',2);
%! line([0; 1],lvl([1; 1]),'color','r');
%! line([0;1],lvl([2;2]),'color','b')
