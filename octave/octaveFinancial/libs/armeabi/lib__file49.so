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
## @deftypefn {Function File} {@var{rsi} =} rsindex (@var{closeprice})
## @deftypefnx {Function File} {@var{rsi} =} rsindex (@var{closeprice}, @var{nperiods})
##
## Compute the relative strength index (RSI) of an asset from the vector
## of closing prices (@var{closeprice}).  @var{nperiods} defines the
## number of periods that the rsi should be calculated for 
## (default: 14).
##
## The beginning of the @var{rsi} is padded with nans to match the size
## of @var{closeprice}.
##
## @end deftypefn

function rsi = rsindex (cl, n = 14)

  if nargin < 1 || nargin > 2
    print_usage ();
  elseif n > length(cl)
    error ("nperiods must be <= the length of closeprice")
  elseif ! isvector (cl)
    error ("closeprice must be a vector")
  endif

  diff = cl(2:end) - cl(1:end-1);
  rsi  = nan (size (cl));

  for i = n:length (cl)
    changes = diff(i-n+1:i-1);
    downs   = changes < 0;
    ups     = changes > 0;
    if isempty (downs)
      ## prevent division by zero
      rsi(i) = 100;
    elseif isempty (ups)
      rsi(i) = 0;
    else
      ups    = sum(changes(ups));
      downs  = -sum(changes(downs));
      rsi(i) = 100*(1-1/(1+ups/downs));
    endif
  endfor

endfunction

## Tests
%!shared c, r
%! c = [22.44 22.61 22.67 22.88 23.36 23.23 23.08 22.86 23.17 23.69 23.77 23.84 24.32 24.8 24.16 24.1 23.37 23.61 23.21];
%! r = [nan(1, 13) 85.1190 70.235 68.6684 55.6322 53.0414 49.7717];
%!assert(rsindex(c), r, 0.0001)
%!assert(rsindex(c'), r', 0.0001)
