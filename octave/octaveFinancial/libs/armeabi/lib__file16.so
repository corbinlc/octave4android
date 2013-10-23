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
## @deftypefn {@var{data} =} fetch (@var{conn}, @var{symbol})
## @deftypefnx {@var{data} =} fetch (@dots{}, @var{fields})
## @deftypefnx {@var{data} =} fetch (@dots{}, @var{date})
## @deftypefnx {@var{data} =} fetch (@dots{}, @var{fromdate}, @var{todate})
## @deftypefnx {@var{data} =} fetch (@dots{}, @var{period})
## @deftypefnx {[@var{data}, @var{fields}] =} fetch (@dots{})
##
## Download stock data from a connection.
##
## @var{fields} are the data fields to download and must come from the
## set
## @itemize @bullet
## @item "Symbol"
## @item "Last"
## @item "Date"
## @item "Time"
## @item "Change"
## @item "Open"
## @item "High",
## @item "Low"
## @item "Volume"
## @end itemize
##
## As an output, @var{fields} may be different than your request.  This
## is because there is mapping of field names from the data source to
## the output, and what is returned is the source mapping to allow
## validation.
##
## @var{date} is the date string or datenum for the requested data.  If
## you enter today's date, you will get yesterday's data. @var{fromdate}
## and @var{todate} allow you to specify a date range for the data.
##
## @var{period} (default: "d") allows you to select the period for the
## data which can be any of the below as long as they are supported by
## the associated backend.
## @itemize @bullet
## @item 'd': daily
## @item 'w': weekly
## @item 'm': monthly (Yahoo only)
## @item 'v': dividends (Yahoo only)
## @end itemize
##
## @seealso{yahoo, google}
## @end deftypefn

## FIXME: Actually use the proxy info if given in the connection.
## FIXME: Do not ignore the fields input.

function [data fields] = fetch (conn=[], symbol="", varargin)

  fields   = {"Symbol", "Last", "Date", "Time", "Change", "Open", ...
              "High", "Low", "Volume"};
  fromdate = [];
  todate   = [];
  period   = "d";

  firstdate = datenum (1900, 1, 1);
  lastdate  = today ();

  if isempty (conn)
    ## By default, use yahoo now since it's the only connection
    ## currently available.
    conn = yahoo ();
  endif
  if isempty (symbol)
    error ("The ticker symbol must be given")
  elseif ! ischar (symbol)
    error ("The symbol must be either a string")
  endif
  for i = 1:numel (varargin)
    if ischar (varargin{i}) && (length (varargin{i}) == 1)
      period = varargin{i};
    elseif iscellstr (varargin{i}) || ischar (varargin{i})
      ## if it's a character and it's a valid date, make it into our
      ## dates
      if ischar (varargin{i})
        thisdate = [];
        try
          thisdate = datenum (varargin{i});
          if isempty (fromdate)
            fromdate = thisdate;
          endif
          todate = thisdate;
        end_try_catch
      endif
      if isempty (thisdate)
        fields = varargin{i};
        warning ("Fields are currently ignored and all data is returned")
      endif
      thisdate = [];
    elseif isnumeric (varargin{i})
      ## it must be our dates
      if isempty (fromdate)
        fromdate = varargin{i};
      endif
      todate = varargin{i};
    else
      error ("Invalid input for argument %d", i + 2)
    endif
  endfor

  if isempty (fromdate)
    fromdate = firstdate;
    todate   = lastdate;
  endif

  if strcmpi (conn.url, "http://quote.yahoo.com")
    [data fields] = fetch_yahoo (conn, symbol, fromdate, todate, period);
  elseif strcmpi (conn.url, "http://finance.google.com")
    [data fields] = fetch_google (conn, symbol, fromdate, todate, period);
  else
    error ("Unrecgonized connection type")
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
%! [d f] = fetch(yahoo(), "yhoo", "01-Jul-2005", "10-Jul-2005");
%! assert(d, dgood, eps);
%! assert(f, fgood, eps);

## The test below fails because yahoo gives a different volume on 732498
##%!xtest
##%! [d f] = fetch(yahoo(), "yhoo", "01-Jul-2005", "10-Jul-2005", "w");
##%! assert(d, dgood(4:5,:), eps);
##%! assert(f, fgood, eps);
