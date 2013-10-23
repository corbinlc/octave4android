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
## @deftypefn {Function File} {@var{return} =} mirr (@var{cashflow}, @var{finrate}, @var{reinvestrate})
## Compute the modified internal rate of return.
## Take periodic @var{cashflow}s as a vector and the finance rate,
## @var{finrate}, for negative cash flows and a reinvestment rate,
## @var{reinvestrate}, for positive cash flows.
## @seealso{irr, effrr, nomrr, pvvar, xirr}
## @end deftypefn

## Algorithm from
## http://en.wikipedia.org/wiki/Modified_Internal_Rate_of_Return

function rate = mirr (flow, finrate, reinvestrate)

  if (nargin != 3)
    print_usage ();
  endif

  posflow = zeros (size (flow));
  negflow = zeros (size (flow));
  mask    = flow >= 0;
  posflow(mask)  = flow(mask);
  negflow(!mask) = flow(!mask);

  n = numel (flow);

  rate = (-npv (reinvestrate, posflow)*(1+reinvestrate)^n/
          (npv (finrate, negflow)*(1+finrate)))^(1/(n-1))-1;

endfunction

## Tests
%!assert (mirr ([-100000 20000 -10000 30000 38000 50000], 0.09, 0.12), 0.0832, 0.00005)
