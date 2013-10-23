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
## @deftypefn {Function File} {@var{bdates} =} busdays (@var{sdate}, @var{edate})
## @deftypefnx {Function File} {@var{bdates} =} busdays (@var{sdate}, @var{edate}, @var{bdmode})
## @deftypefnx {Function File} {@var{bdates} =} busdays (@var{sdate}, @var{edate}, @var{bdmode}, @var{holvec})
## Generate a list of business dates at the end of the periods defined
## between (including) @var{sdate} and @var{edate}.
##
## @var{sdate} is the starting date, @var{edate} is the ending date,
## both are in serial date format (see datenum).  @var{bdmode} is the
## business day frequency ("daily", "weekly", "monthly", "quarterly",
## "semiannual", or "annual"); these can be abbreviated by the first
## letter and they may also use an integer corresponding to the order in
## the above list (i.e. "daily" = 1).  @var{holvec} is an optional list
## of holidays.  If the holidays are not given, then the holidays
## function is used.
## @seealso{holidays, busdate, lbusdate, isbusday, fbusdate, datenum}
## @end deftypefn

function bd = busdays (sd, ed, mode=1, hol=[])

  if nargin < 2 || nargin > 4
    print_usage ();
  endif
  if ~isnumeric (sd)
	sd = datenum (sd);
  endif
  if ~isnumeric (ed)
    ed = datenum (ed);
  endif
  if ed < sd
    error ("busdays: the start date must be less than the end date")
  endif
  if isempty (hol)
    ## make the holidays take into account the whole ending year because
    ## the day may extend beyond the actual ending date
    edtmp = datevec (ed);
    edtmp(2:3) = [12 31];
    hol = holidays (sd, datenum (edtmp));
  endif
  ## Convert the mode to the numeric
  modestr = "dwmqsa";
  if ischar (mode)
    mode = find (lower (mode(1)) == modestr);
    if isempty (mode)
      error ("busdays: mode must be one of '%s'", modestr)
    endif
  elseif isnumeric (mode)
    if mode < 1 || mode > length (modestr)
      error ("busdays: mode must be between 1 and %d", length (modestr))
    endif
  else
    error ("busdays: mode must be a number or string")
  endif

  ## do the computation
  if mode == 1
    ## daily
    bd = (sd:ed)'(isbusday (sd:ed, hol));
  elseif mode < 6
    if mode == 2
      ## weekly make the start and end dates Fridays and then move back
      ## from there
      wd = weekday ([sd;ed]);
      d = [sd;ed] - wd + 7;
      ## there are generally not more than one week of holidays at a
      ## time, but the call to unique will make certain of that.
      bd = unique (busdate ([d(1):7:d(2)]', -1, hol));
    else
      d = datevec ([sd:ed]);
      ## unique year and month list within the date range
      ym = unique (d(:,1:2), "rows");
      if mode == 3
        ## monthly, do nothing to the ym list
      elseif mode == 4
        ## quarterly
        if mod (ym(end), 3) != 0
          ## make the last month an end of quarter month
          ym(end) = ym(end) + 3 - mod (ym(end), 3);
        endif
        ym(mod (ym(:,2), 3) != 0, :) = [];
      elseif mode == 5
        ## semi-annually
        if mod (ym(end), 6) != 0
        ## make the last month an end of semi-annual month (6, 12)
        ym(end) = ym(end) + 6 - mod (ym(end), 6);
        endif
        ym(mod (ym(:,2), 6) != 0, :) = [];
      endif
      bd = lbusdate (ym(:,1), ym(:,2), hol);
    endif
  elseif mode == 6
    ## annual
    d = datevec ([sd;ed]);
    bd = lbusdate ((d(1,1):d(2,1))', 12, hol);
  else
    ## this should have been caught before now
    error ("busdays: invalid mode")
  endif

endfunction

## Tests
%!assert (busdays (datenum (2008, 1, 1), datenum (2008, 1, 12)), datenum (2008, 1, [2;3;4;7;8;9;10;11]))
%!assert (busdays (datenum (2008, 1, 1), datenum (2008, 1, 12), "d"), datenum (2008, 1, [2;3;4;7;8;9;10;11]))
%!assert (busdays (datenum (2001, 1, 2), datenum (2001, 1, 9), "w"), datenum (2001, 1, [5;12]))
%!assert (busdays (datenum (2008, 1, 1), datenum (2008, 1, 2), "m"), datenum (2008, 1, 31))
%!assert (busdays (datenum (2008, 1, 1), datenum (2010, 5, 2), "m"), lbusdate ([2008*ones(12,1);2009*ones(12,1);2010*ones(5,1)], [1:12 1:12 1:5]'))
%!assert (busdays (datenum (2008, 1, 1), datenum (2008, 1, 2), "q"), datenum (2008, 3, 31))
%!assert (busdays (datenum (2008, 1, 1), datenum (2010, 5, 2), "q"), lbusdate ([2008*ones(4,1);2009*ones(4,1);2010*ones(2,1)], [3:3:12 3:3:12 3 6]'))
%!assert (busdays (datenum (2008, 1, 1), datenum (2008, 1, 2), "s"), datenum (2008, 6, 30))
%!assert (busdays (datenum (2008, 1, 1), datenum (2010, 5, 2), "s"), lbusdate ([2008;2008;2009;2009;2010], [6 12 6 12 6]'))
%!assert (busdays (datenum (2008, 1, 1), datenum (2011, 1, 2), "a"), datenum ([2008;2009;2010;2011], [12;12;12;12], [31;31;30;30]))
