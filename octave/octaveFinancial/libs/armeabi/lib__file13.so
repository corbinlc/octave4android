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
## @deftypefn {Function File} {@var{return} =} effrr (@var{rate}, @var{numperiods})
## Compute the effective rate of return based on a nominal @var{rate}
## over a number of periods, @var{numperiods}.
## @seealso{irr, nomrr}
## @end deftypefn

function rate = effrr (rate, numperiods)

  if (nargin != 2)
    print_usage ();
  endif

  rate = (1+rate./numperiods).^numperiods - 1;

endfunction

## Tests
%!assert (effrr (0.09, 12), 0.0938, 0.00005)
