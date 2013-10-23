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
## @deftypefn {Function File} {@var{conn} =} google ()
## @deftypefnx {Function File} {@var{conn} =} google (@var{URL}, @var{ipaddress}, @var{port})
##
## Prepare a Google connection for the fetch command to get Google
## historical quote data.
##
## If given, the @var{URL} must be "http://finance.google.com".  The
## @var{ipaddress} and @var{port} is the proxy ipaddress and port. These
## parameters are currently ignored (with a warning if given).
##
## @seealso{fetch, yahoo}
## @end deftypefn

## FIXME: Actually use the proxy info if given.

function conn = google (url="http://finance.google.com", ipaddr="", port=[])

  if ! strcmpi (url, "http://finance.google.com")
    error ("url must be 'http://finance.google.com'")
  elseif ! (isempty (ipaddr) && isempty (port))
    warning ("Proxy information is currently ignored")
  endif

  conn.url  = url;
  conn.ip   = ipaddr;
  conn.port = port;

endfunction
