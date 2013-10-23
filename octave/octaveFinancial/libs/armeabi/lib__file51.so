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
## @deftypefn {Function File} {@var{return} =} taxedrr (@var{pretaxreturn}, @var{taxrate})
## Compute the taxed rate of @var{return} based on a @var{pretaxreturn}
## rate and a @var{taxrate}.
## @seealso{irr, effrr, nomrr, pvvar, xirr}
## @end deftypefn

function rate = taxedrr (pretax, taxrate)

  if (nargin != 2)
    print_usage ();
  elseif (taxrate < 0 || taxrate > 1)
    error ("taxedrr: taxrate must be between 0 and 1")
  endif

  rate = pretax.*(1-taxrate);

endfunction

## Tests
%!assert (taxedrr (0.12, 0.30), 0.084, 10*eps)
%!assert (taxedrr (0.12, 0), 0.12, 10*eps)
%!assert (taxedrr (0.12, 1), 0, 10*eps)
