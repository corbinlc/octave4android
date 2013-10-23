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
## @deftypefn {Function File} {last =} lweekdate (weekday, year, month, nextday)
##
## Returns the last occurrence of @var{weekday} from the @var{month} and
## @var{year}.  If the optional @var{nextday} argument is given, then
## the week must also contain @var{nextday}.
##
## @seealso{eomdate, nweekdate, weekday}
## @end deftypefn

function t = lweekdate (varargin)
  if nargin < 3 || nargin > 4
	error ("3 or 4 input arguments are required")
  elseif nargin == 3
	varargin{4} = 0;
  endif

  t = nweekdate ("lweekdate", varargin{:});

endfunction

## Tests are in nweekdate
