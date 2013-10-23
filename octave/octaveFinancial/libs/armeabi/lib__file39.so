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
## @deftypefn {Function File} {last =} nweekdate (n, weekday, year, month, nextday)
##
## Returns the @var{n}th occurrence of @var{weekday} from the
## @var{month} and @var{year}.  If the optional @var{nextday} argument
## is given, then the week must also contain @var{nextday}.  If @var{n}
## is greater than the number of occurrences of that day in the month, 0
## is returned.
##
## @seealso{eomdate, lweekdate, weekday}
## @end deftypefn

function t = nweekdate (varargin)
  if nargin < 4 || nargin > 5
	error ("4 or 5 input arguments are required")
  elseif nargin == 4
	varargin{5} = 0;
  endif

  ## special handling so that most of this code will not need to be
  ## duplicated in lweekdate
  do_lweekdate = is_lweekdate(varargin{1});
  if do_lweekdate
	varargin{1} = 1;
  endif

  scale = cellfun (@numel, varargin);
  if ~ all (scale == 1 | scale == max(scale));
	error("All inputs must be either scalars or have the same number of elements");
  else
	## make sure that the sizes are the same for any non-scalar inputs
	ind = find (scale > 1);
	if length(ind) > 1
	  for i = 2:length (ind)
		if ndims (varargin{ind(1)}) ~= ndims (varargin{ind(i)})
		  error("Mismatching dimensions on inputs %d and %d", ind(1), ind(i));
		elseif ~ all (size (varargin{ind(1)}) == size (varargin{ind(i)}))
		  error("The sizes of inputs %d and %d do not match", ind(1), ind(i));
		endif
	  endfor
	endif
  endif

  if max(scale) > 1
	t = zeros (size (varargin{ind(1)}));
	for i = 1:numel (varargin{ind(1)})
	  args = cell(5,1);
	  for j = 1:5
		if isscalar (varargin{j})
		  args{j} = varargin{j};
		else
		  args{j} = varargin{j}(i);
		endif
	  endfor
	  if do_lweekdate
		args{1} = "lweekdate";
	  end
	  t(i) = nweekdate (args{:});
	endfor
  else
	## Do the real work.
	n = varargin{1};
	wd = varargin{2};
	y = varargin{3};
	mon = varargin{4};
	nextday = varargin{5};

	## Find the day of the week for the last day of the mon.
	doml = eomday (y, mon);
	dowl = weekday (datenum (y, mon, doml));
	## Make sure that the day is in the weeks for the last then the
	## first week.
	if (wd < nextday) || (dowl < wd)
	  ## adjust the last day
	  adjust = 7;
	else
	  adjust = 0;
	endif
	dom = sort((doml - dowl + wd - adjust):-7:1);
	if nextday && (dom(1) <= wd - nextday)
	  # adjust the first day
	  dom(1) = [];
	endif
	if do_lweekdate
	  t = datenum (y, mon, dom(end));
    elseif n > length(dom)
	  t = 0;
	else
	  t = datenum (y, mon, dom(n));
	end
  endif

endfunction

function v = is_lweekdate(v)
  if ischar(v)
	if strcmp (v, "lweekdate")
	  v = true();
	else
	  error("Invalid input for n")
	endif
  else
	v = false();
  endif
endfunction

# Tests for all calling options
# Find the first Wednesday in Jan 2008
%!assert(nweekdate(1, 4, 2008, 1), datenum(2008, 1, 2))
# Find the second Wednesday in Jan 2008
%!assert(nweekdate(2, 4, 2008, 1), datenum(2008, 1, 9))
# Find the third Wednesday in Jan 2008
%!assert(nweekdate(3, 4, 2008, 1), datenum(2008, 1, 16))
# Find the fourth Wednesday in Jan 2008
%!assert(nweekdate(4, 4, 2008, 1), datenum(2008, 1, 23))
# Find the fifth Wednesday in Jan 2008
%!assert(nweekdate(5, 4, 2008, 1), datenum(2008, 1, 30))
# Find the sixth Wednesday in Jan 2008, it doesn't exist, so return 0
%!assert(nweekdate(6, 4, 2008, 1), 0)
# Find the fifth Friday in Jan 2008, it doesn't exist, so return 0
%!assert(nweekdate(5, 6, 2008, 1), 0)
# Find the first Wednesday in Jan 2008 in the same week as a Monday
# WARNING: it is unclear from the Matlab docs if this should work or if 
# this should be called the second Wednesday in Jan 2008.
%!assert(nweekdate(1, 4, 2008, 1, 2), datenum(2008, 1, 9))
# Find the fifth Wednesday in Jan 2008 in the same week as a Friday.
# It doesn't exist, so return 0
%!assert(nweekdate(5, 4, 2008, 1, 6), 0)
# Try vector arguments
%!assert(nweekdate(1:6, 4, 2008, 1, 6), [datenum(2008, 1, 2:7:23), 0, 0])

# Try the lweekdate operation of this function:
# Find the last Wednesday in Jan 2008
%!assert(nweekdate('lweekdate', 4, 2008, 1), datenum(2008, 1, 30))
# Find the last Wednesday in Jan 2008 with a Friday
%!assert(nweekdate('lweekdate', 4, 2008, 1, 6), datenum(2008, 1, 23))

