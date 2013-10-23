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
## @deftypefn {Function File} {} movavg (@var{asset}, @var{lead}, @var{lag})
## @deftypefnx {Function File} {} movavg (@var{asset}, @var{lead}, @var{lag}, @var{alpha})
## @deftypefnx {Function File} {[@var{short}, @var{long}] =} movavg (@var{asset}, @var{lead}, @var{lag}, @var{alpha})
##
## Calculate the @var{lead}ing and @var{lag}ging moving average of an
## @var{asset}. If given, @var{alpha} is the weighting power of the
## delay; 0 (default) is the simple moving average, 0.5 would be the
## square root weighted moving average, 1 would be linear, 2 would be
## squared, ..., and 'e' is the exponential moving average.
##
## If no output is requested the data is plotted.  The plots are drawn
## in the following order: asset, lag, lead.  If output is requested, no
## plot is generated.
##
## @seealso{bolling, candle, dateaxis, highlow, pointfig}
## @end deftypefn

function [varargout] = movavg (asset, lead, lag, alpha = 0)

  if nargin < 3 || nargin > 4
    print_usage ();
  endif

  if lead > lag
    error ("lead must be <= lag")
  elseif ischar (alpha)
    if ! strcmpi (alpha, "e")
      error ("alpha must be 'e' if it is a char");
    endif
  elseif ! isnumeric (alpha)
    error ("alpha must be numeric or 'e'")
  endif

  ## Compute the weights
  if ischar (alpha)
    lead = exp(1:lead);
    lag  = exp(1:lag);
  else
    lead = (1:lead).^alpha;
    lag  = (1:lag).^alpha;
  endif
  ## Adjust the weights to equal 1
  lead = lead / sum (lead);
  lag  = lag / sum (lag);

  short = asset;
  long  = asset;
  for i = 1:length (asset)
    if i < length (lead)
      ## Compute the run-in period
      r        = length (lead) - i + 1:length(lead);
      short(i) = dot (asset(1:i), lead(r))./sum (lead(r));
    else
      short(i) = dot (asset(i - length(lead) + 1:i), lead);
    endif
    if i < length (lag)
      r       = length (lag) - i + 1:length(lag);
      long(i) = dot (asset(1:i), lag(r))./sum (lag(r));
    else
      long(i) = dot (asset(i - length(lag) + 1:i), lag);
    endif
  endfor

  if nargout > 0
    varargout{1} = short;
  else
    plot((1:length(asset))', [asset(:), long(:), short(:)]);
  endif
  if nargout > 1
    varargout{2} = long;
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
