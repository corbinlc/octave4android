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
## @deftypefn {Function File} {[begindate, enddate]} = thirdwednesday (month, year)
##
## Find the third Wednesday of the month specified by the @var{month}
## and @var{year}.  The @var{begindate} is the third Wednesday of the
## month, and the @var{enddate} is three months after that.  Outputs are
## in the form of datenums.
##
## The third Wednesday is used for Eurodollar futures.
##
## @seealso{nweekdate, datenum}
## @end deftypefn

function [wednesdays, enddate] = thirdwednesday (month, year)

  if nargin ~= 2
	print_usage ();
  elseif ~ ((numel(year) == 1) ||
            (numel(month) == 1) ||
            ~isequal (size (month), size (year)))
    error("month and year must have the same size or one of them must be a scalar")
  endif

  if numel (year) == 1
    sz = size (month);
  else
    sz = size (year);
  endif

  wednesdays = nweekdate (3, 4, year, month);
  dates = datevec (wednesdays);
  ## adjust the year when the date will wrap
  dates(:,1) += dates (:,2) > 9;
  ## adjust the month by three
  dates(:,2) = mod (dates(:,2) + 2, 12) + 1;
  enddate = reshape (datenum (dates), sz);

endfunction

## Tests
%!shared m, y, bt, et
%! m = (1:12)';
%! y = 2008;
%! bt = datenum(2008, m, [16;20;19;16;21;18;16;20;17;15;19;17]);
%! et = datenum([2008*ones(9,1);2009*ones(3,1)], [4:12 1:3]', [16;20;19;16;21;18;16;20;17;15;19;17]);
%!test
%! [b e] = thirdwednesday (m, y);
%! assert(b, bt)
%! assert(e, et)
%!test
%! [b e] = thirdwednesday (m', y);
%! assert(b, bt')
%! assert(e, et')
