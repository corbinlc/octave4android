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
## @deftypefn {Function File} {mos =} months (startdate, enddate)
## @deftypefnx {Function File} {mos =} months (startdate, enddate, endmonthflag)
##
## Return the number of whole months between @var{startdate} and
## @var{enddate}.  @var{endmonthflag} defaults to 1.
##
## If @var{endmonthflag} is true, then if both the @var{startdate} and
## the @var{enddate} are end of month dates and @var{enddate} has fewer
## days in the month than @var{startdate}, @var{endmonthflag} = 1 treats
## @var{enddate} as the end of a month, but @var{endmonthflag} = 0 does
## not.
##
## @seealso{yeardays, yearfrac}
## @end deftypefn

function mos = months (startdate, enddate, endmonthflag = 1)

  if (nargin < 2 || nargin > 3)
    print_usage ();
  endif

  s = datevec (startdate);
  e = datevec (enddate);
  s_eom = (s(:,3) == eomday(s(:,1), s(:,2)));
  e_eom = (e(:,3) == eomday(e(:,1), e(:,2)));

  ## Handle the end of the month correctly
  dayadj = ((s(:,3) > e(:,3)) & endmonthflag & s_eom & e_eom);

  mos = 12*(e(:,1) - s(:,1)) + (e(:,2) - s(:,2)) - (s(:,3) > e(:,3)) + dayadj;

endfunction

## Tests
%!assert(months('may 31 2004', 'jun 30 2004'), 1)
%!assert(months({'may 31 2004' 'may 30 2004'}, 'jun 30 2004'), [1;1])
%!assert(months('may 31 2004', 'jun 30 2004', 1), 1)
%!assert(months({'may 31 2004' 'may 30 2004'}, 'jun 30 2004', 1), [1;1])
%!assert(months('may 31 2004', 'jun 30 2004', 0), 0)
%!assert(months({'may 31 2004' 'may 30 2004'}, 'jun 30 2004', 0), [0;1])
%!assert(months('jun 30 2005', 'june 30 2006'), 12)
%!assert(months('jun 30 2005', 'june 29 2006'), 11)
