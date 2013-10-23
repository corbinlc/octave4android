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
## @deftypefn {[@var{data} @var{fields}] =}
##  fetch_yahoo (@var{conn}, @var{symbol}, @var{fromdate}, @var{todate}, @var{period})
##
## Download stock data from yahoo. (Helper for fetch.)
##
## @var{fields} are the data fields returned by Yahoo.
##
## @var{fromdate} and @var{todate} is the date datenum for the requested
## date range.  If you enter today's date, you will get yesterday's
## data.
##
## @var{period} (default: "d") allows you to select the period for the
## data which can be any of
## @itemize @bullet
## @item 'd': daily
## @item 'w': weekly
## @item 'm': monthly
## @item 'v': dividends
## @end itemize
##
## @seealso{yahoo, fetch}
## @end deftypefn

## FIXME: Actually use the proxy info if given in the connection.
## FIXME: Do not ignore the fields input.

function [data fields] = fetch_yahoo (conn=[], symbol="",
                                          fromdate, todate, period="d")

  if strcmpi (conn.url, "http://quote.yahoo.com")
    fromdate = datevec (fromdate);
    todate   = datevec (todate);
    geturl   = sprintf (["http://ichart.finance.yahoo.com/table.csv" ...
                         "?s=%s&d=%d&e=%d&f=%d&g=%s&a=%d&b=%d&c=%d&" ...
                         "ignore=.csv"],
                         symbol, todate(2)-1, todate(3), todate(1),
                         period,
                         fromdate(2)-1, fromdate(3), fromdate(1));
    ## FIXME: This would be more efficient if csv2cell could work on
    ## strings instead of files.
    [f, success, msg] = urlwrite (geturl, tmpnam ());
    if ! success
      error ("Could not write Yahoo data to tmp file:\n%s", msg)
    endif
    d = csv2cell (f);
    unlink(f);
    ## Pull off the header
    fields = d(1,:);
    d(1,:) = [];
    dates  = strvcat (d(:,1));
    dates  = datenum(str2num(dates(:,1:4)),
                     str2num(dates(:,6:7)),
                     str2num(dates(:,9:10)));
    data   = [dates, cell2mat(d(:,2:end))];
  else
    error ("Non-yahoo connection passed to yahoo fetch")
  endif

endfunction

%!shared fgood, dgood
%! fgood = {"Date", "Open", "High", "Low", "Close", "Volume", "Adj Close"};
%! dgood = [732501,34.77,34.87,34.25,34.62,15515400,34.62;
%!          732500,33.87,34.77,33.72,34.63,16354300,34.63;
%!          732499,34.64,34.97,34.03,34.12,13585700,34.12;
%!          732498,34.25,35.08,34.20,34.60,16086700,34.60;
%!          732494,34.76,34.85,34.22,34.44,9861600,34.44];
%!test
%! [d f] = fetch_yahoo (yahoo(), "yhoo", 732494, 732501, "d");
%! assert(d, dgood, eps);
%! assert(f, fgood, eps);
## test that the automatic period works
%!test
%! [d f] = fetch_yahoo (yahoo(), "yhoo", 732494, 732501);
%! assert(d, dgood, eps);
%! assert(f, fgood, eps);

## The test below fails because yahoo gives a different volume on 732498
##%!xtest
##%! [d f] = fetch(yahoo(), "yhoo", "01-Jul-2005", "10-Jul-2005", "w");
##%! assert(d, dgood(4:5,:), eps);
##%! assert(f, fgood, eps);
