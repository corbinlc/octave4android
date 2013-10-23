## Copyright (C) 2012   Lukas F. Reichlin
##
## This file is part of LTI Syncope.
##
## LTI Syncope is free software: you can redistribute it and/or modify
## it under the terms of the GNU General Public License as published by
## the Free Software Foundation, either version 3 of the License, or
## (at your option) any later version.
##
## LTI Syncope is distributed in the hope that it will be useful,
## but WITHOUT ANY WARRANTY; without even the implied warranty of
## MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
## GNU General Public License for more details.
##
## You should have received a copy of the GNU General Public License
## along with LTI Syncope.  If not, see <http://www.gnu.org/licenses/>.

## -*- texinfo -*-
## @deftypefn {Function File} {@var{dat} =} detrend (@var{dat})
## @deftypefnx {Function File} {@var{dat} =} detrend (@var{dat}, @var{ord})
## Detrend outputs and inputs of dataset @var{dat} by
## removing the best fit of a polynomial of order @var{ord}.
## If @var{ord} is not specified, default value 0 is taken.
## This corresponds to removing a constant.
## @end deftypefn

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: April 2012
## Version: 0.1

function dat = detrend (dat, ord = 0)

  if (nargin > 2)       # no need to test nargin == 0, this is handled by built-in detrend
    print_usage ();
  endif

  if ((! is_real_scalar (ord) || fix (ord) != ord) && ! ischar (ord))   # chars are handled by built-in detrend
    error ("iddata: detrend: second argument must be a positve integer");
  endif

  [n, p, m] = size (dat);

  dat.y = cellfun (@detrend, dat.y, {ord}, "uniformoutput", false);
  dat.u = cellfun (@detrend, dat.u, {ord}, "uniformoutput", false);

  ## if a MIMO experiment has only 1 sample, detrend works
  ## row-wisely instead of column-wisely
  ## therefore we set these experiments to zero
  idx = (n == 1);
  dat.y(idx) = zeros (1, p);
  dat.u(idx) = zeros (1, m);

endfunction


%!shared DATD, Z
%! DAT = iddata ({[(1:10).', (1:2:20).'], [(10:-1:1).', (20:-2:1).']}, {[(41:50).', (46:55).'], [(61:70).', (-66:-1:-75).']});
%! DATD = detrend (DAT, "linear");
%! Z = zeros (10, 2);
%!assert (DATD.y{1}, Z, 1e-10);
%!assert (DATD.y{2}, Z, 1e-10);
%!assert (DATD.u{1}, Z, 1e-10);
%!assert (DATD.u{2}, Z, 1e-10);
