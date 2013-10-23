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
##  fetch_google (@var{conn}, @var{symbol}, @var{fromdate}, @var{todate}, @var{period})
##
## Download stock data from google. (Helper for fetch.)
##
## @var{fields} are the data fields returned by Google.
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
## @end itemize
##
## @seealso{google, fetch}
## @end deftypefn

## FIXME: Actually use the proxy info if given in the connection.
## FIXME: Do not ignore the fields input.

function [data fields] = fetch_google (conn=[], symbol="",
                                          fromdate, todate, period="d")

  periods = struct("d", "daily", "w", "weekly");
  if strcmpi (conn.url, "http://finance.google.com")
    fromdatestr = datestr (fromdate);
    todatestr   = datestr (todate);
    ## http://finance.google.com/finance/historical?q=T&startdate=Sep+1%2C+2007&enddate=Aug+31%2C+2008&histperiod=weekly&output=csv
    geturl = sprintf (["http://finance.google.com/finance/" ...
                       "historical?" ...
                       "q=%s&startdate=%s&enddate=%s&" ...
                       "histperiod=%s&output=csv"],
                      symbol, fromdatestr, todatestr, periods.(period));
    ## FIXME: This would be more efficient if csv2cell could work on
    ## strings instead of files.
    [f, success, msg] = urlwrite (geturl, tmpnam ());
    if ! success
      error (["Could not write Google data to tmp file:" ...
              "\n%s\nURL was:\n%s"], msg, geturl)
    endif
    d = csv2cell (f);
    d{1,1} = d{1,1}(4:end); # Remove byte order mark (BOM)
    unlink(f);
    ## Pull off the header
    fields = d(1,:);
    d(1,:) = [];
    ## Put the dates into datenum format
    data = [datenum(datevec(d(:,1), "dd-mmm-yy")), \
            cell2mat(d(:,2:end))];
    ## Note that google appears to have an off-by-one error in
    ## returning historical data, so make sure that we only return the
    ## requested data and not what Google sent.
    data((data(:,1) < fromdate) | (data(:,1) > todate), :) = [];
  else
    error ("Non-google connection passed to google fetch")
  endif

endfunction

%!shared fgood, dgood, wgood
%! fgood = {"Date", "Open", "High", "Low", "Close", "Volume"};
%! dgood = [732501,34.77,34.87,34.25,34.62,15296900;
%!          732500,33.87,34.77,33.72,34.63,16265900;
%!          732499,34.64,34.97,34.03,34.12,13451500;
%!          732498,34.25,35.08,34.20,34.60,15845100;
%!          732494,34.76,34.85,34.22,34.44,9740300];
%! wgood = [732501,34.25,35.08,33.72,34.62,60859400;
%!          732494,35.88,36.24,34.22,34.44,67132100];
%!test
%! [d f] = fetch_google (google(), "yhoo", 732494, 732501, "d");
%! assert(d, dgood, eps);
%! assert(f, fgood, eps);
## test that the automatic period works
%!test
%! [d f] = fetch_google (google(), "yhoo", 732494, 732501);
%! assert(d, dgood, eps);
%! assert(f, fgood, eps);
## Test that weekly works
%!test
%! [d f] = fetch_google (google(), "yhoo", 732494, 732501, "w");
%! assert(d, wgood, eps);
%! assert(f, fgood, eps);
