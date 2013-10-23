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
## @deftypefn {Function File} {@var{return} =} nomrr (@var{rate}, @var{numperiods})
## Compute the nominal rate of return based on a effective @var{rate}
## over a number of periods, @var{numperiods}.
## @seealso{irr, effrr}
## @end deftypefn

function rate = nomrr (rate, numperiods)

  if (nargin != 2)
    print_usage ();
  endif

  rate = numperiods.*((1+rate).^(1./numperiods) - 1);

endfunction

## Tests
%!assert (nomrr (0.0938, 12), 0.09, 0.00005)
