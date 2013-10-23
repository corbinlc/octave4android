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
## @deftypefn {Function File} {@var{obv} =} onbalvol (@var{closeprice}, @var{vol})
## @deftypefnx {Function File} {@var{obv} =} onbalvol ([@var{closeprice} @var{vol}])
##
## Compute the on balance volume of a security based on its closing
## price (@var{closeprice}) and @var{vol}ume.  They may be given as
## separate arguments or as an nx2 matrix.
##
## The output will be a column vector, and the first number in the
## output is always 0.
##
## @seealso{negvolidx, posvolidx}
## @end deftypefn

function obv = onbalvol (c, vol)

  if nargin == 1
    % do nothing
  elseif nargin == 2
    c = [c(:) vol(:)];
  else
    print_usage ();
  endif

  obv = zeros (size (c, 1), 1);
  for i = 2:size (c, 1)
    if c(i,1) > c(i-1,1)
      obv(i) = obv(i-1) + c(i,2);
    elseif c(i,1) < c(i-1,1)
      obv(i) = obv(i-1) - c(i,2);
    else
      obv(i) = obv(i-1);
    endif
  endfor

endfunction

## Tests
%!shared c, v, obv
%! c = [22.44 22.61 22.67 22.88 23.36 23.23 23.08 22.86 23.17 23.69 23.77 23.84 24.32 24.8 24.16 24.1 23.37 23.61 23.21];
%! v = [10 12 23 25 34 12 32 15 15 34 54 12 86 45 32 76 89 13 28];
%! obv = [0 12 35 60 94 82 50 35 50 84 138 150 236 281 249 173 84 97 69]';
%!assert(onbalvol(c, v), obv)
%!assert(onbalvol([c' v']), obv)
