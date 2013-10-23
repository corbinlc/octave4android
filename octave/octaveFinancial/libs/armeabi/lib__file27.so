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
## @deftypefn {Function File} {@var{llv} =} llow (@var{data})
## @deftypefnx {Function File} {@var{llv} =} llow (@var{data}, @var{nperiods})
## @deftypefnx {Function File} {@var{llv} =} llow (@var{data}, @var{nperiods}, @var{dim})
##
## Compute the lowest low value of @var{data} for the past
## @var{nperiods} (default: 14) across the dimension, @var{dim}
## (default: 1).
##
## @seealso{hhigh}
## @end deftypefn

function llv = llow (data, nperiods = 14, dim = find (size (data) > 1, 1))

  if nargin < 1 || nargin > 3
    print_usage ();
  elseif ! isvector (data)
    ## FIXME
    error ("cannot yet handle more than one dimensional data")
  elseif dim > ndims (data)
    error ("dim cannot be greater than the number of dimensions in data");
  endif

  sz  = size (data);
  llv = data;
  for i = 1:sz(dim)
    llv(i) = min (data(max (i-nperiods+1, 1):i));
  endfor

endfunction

## Tests
%!shared c, l
%! c = [22.44 22.61 22.67 22.88 23.36 23.23 23.08 22.86 23.17 23.69 23.77 23.84 24.32 24.8 24.16 24.1 23.37 23.61 23.21 25];
%! l = [22.44 22.44 22.44 22.44 22.44 22.44 22.44 22.44 22.44 22.44 22.44 22.44 22.44 22.44 22.61 22.67 22.86 22.86 22.86 22.86];
%!assert(llow(c), l)
%!assert(llow(c'), l')
