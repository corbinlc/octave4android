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
## @deftypefn {Function File} {@var{dat} =} nkshift (@var{dat}, @var{nk})
## @deftypefnx {Function File} {@var{dat} =} nkshift (@var{dat}, @var{nk}, @var{'append'})
## Shift input channels of dataset @var{dat} according to integer @var{nk}.
## A positive value of @var{nk} means that the input channels are delayed
## @var{nk} samples.  By default, both input and output signals are shortened
## by @var{nk} samples.
## If a third argument @var{'append'} is passed, the output signals are left
## untouched while @var{nk} zeros are appended to the (shortened) input signals
## such that the number of samples in @var{dat} remains constant.
## @end deftypefn

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: July 2012
## Version: 0.1

function dat = nkshift (dat, nk = 0)

  if (nargin > 3)
    print_usage ();
  endif

  if (! is_real_scalar (nk))
    error ("iddata: nkshift: 'nk' must be a scalar integer");
  endif

  ## TODO: - nk per inputs
  ##       - frequency-domain data
  
  snk = sign (nk);
  nk = abs (nk);

  if (nargin == 2)      # default: shortening y and u by nk
    if (snk >= 0)
      dat.y = cellfun (@(y) y(nk+1:end, :), dat.y, "uniformoutput", false);
      dat.u = cellfun (@(u) u(1:end-nk, :), dat.u, "uniformoutput", false);
    else
      dat.y = cellfun (@(y) y(1:end-nk, :), dat.y, "uniformoutput", false);
      dat.u = cellfun (@(u) u(nk+1:end, :), dat.u, "uniformoutput", false);
    endif
  else                  # append: keep y, padding u with nk zeros
    [~, ~, m] = size (dat);
    if (snk >= 0)
      dat.u = cellfun (@(u) [zeros(nk, m), u(1:end-nk, :)], dat.u, "uniformoutput", false);
    else
      dat.u = cellfun (@(u) [u(nk+1:end, :), zeros(nk, m)], dat.u, "uniformoutput", false);
    endif
  endif

endfunction
