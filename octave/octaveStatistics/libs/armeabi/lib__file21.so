## Copyright (C) 2001 Paul Kienzle <pkienzle@users.sf.net>
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
## @deftypefn {Function File} geomean (@var{x})
## @deftypefnx{Function File} geomean (@var{x}, @var{dim})
## Compute the geometric mean.
##
## This function does the same as @code{mean (x, "g")}.
##
## @seealso{mean}
## @end deftypefn

function a = geomean(x, dim)
  if (nargin == 1)
    a = mean(x, "g");
  elseif (nargin == 2)
    a = mean(x, "g", dim);
  else
    print_usage;
  endif
endfunction
