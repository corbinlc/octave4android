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
## @deftypefn {Function File} {@var{dat} =} diff (@var{dat})
## @deftypefnx {Function File} {@var{dat} =} diff (@var{dat}, @var{k})
## Return @var{k}-th difference of outputs and inputs of dataset @var{dat}.
## If @var{k} is not specified, default value 1 is taken.
## @end deftypefn

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: March 2012
## Version: 0.1

function dat = diff (dat, k = 1)

  if (nargin > 2)       # no need to test nargin == 0, this is handled by built-in diff
    print_usage ();
  endif

  dat.y = cellfun (@diff, dat.y, {k}, {1}, "uniformoutput", false);
  dat.u = cellfun (@diff, dat.u, {k}, {1}, "uniformoutput", false);

endfunction
