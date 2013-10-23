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
## @deftypefn {Function File} {@var{h} =} highlow (@var{high}, @var{low}, @var{close})
## @deftypefnx {Function File} {@var{h} =} highlow (@var{high}, @var{low}, @var{close}, @var{open})
## @deftypefnx {Function File} {@var{h} =} highlow (@var{high}, @var{low}, @var{close}, @var{open}, @var{color})
##
## Plot the @var{high}, @var{low}, and @var{close} of a security.  The
## @var{close} is plotted as a tick to the right, and if @var{open} is
## given and non-empty, it is plotted as a tick to the left.  The color
## can override the default color for the plot.
##
## @seealso{bolling, candle, dateaxis, movavg, pointfig}
## @end deftypefn

function h = highlow (high, low, close, open = [], color)

  if nargin < 3 || nargin > 5
    print_usage ();
  elseif nargin < 5
    plotargs = {};
  else
    plotargs = {"color", color};
  endif

  if isempty (high) || isempty (low) || isempty (close)
    error ("high, low, and close may not be empty")
  elseif ~(isvector (high) && isvector (low) && isvector (close))
    error ("high, low, and close must be vectors")
  elseif ( (numel (high) != numel (low)) || (numel (high) != numel (close)) )
    error ("high, low, and close must have the same number of elements")
  elseif ( !isempty (open) && (numel (high) != numel (open)) )
    error ("open must have the same number of elements as high, low, and close")
  endif

  holdstat = ishold ();
  ## h = hggroup ();
  ## plotargs(end+1:end+2) = {"parent", h};
  hold on;
  x = (1:length(high)) + 0.5;
  x = reshape([x;x;nan(size(x))], [], 1);
  y = reshape([high(:)'; low(:)'; nan(1, length(high))], [], 1);
  plot(x, y, plotargs{:});
  x = 1:length(high);
  x = reshape([x+0.5;x+1;nan(size(x))], [], 1);
  y = reshape([close(:)';close(:)';nan(1, length(close))], [], 1);
  plot(x, y, plotargs{:});
  if ! isempty(open)
    x -= 0.5;
    y = reshape([open(:)';open(:)';nan(1, length(open))], [], 1);
    plot(x, y, plotargs{:});
  endif

  if !holdstat
    hold off;
  endif

endfunction
