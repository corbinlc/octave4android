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
## @deftypefn {Function File} {[@var{data}, @var{varnames}, @var{casenames}] =} tblread (@var{filename})
## @deftypefnx {Function File} {[@var{data}, @var{varnames}, @var{casenames}] =} tblread (@var{filename}, @var{delimeter})
## Read tabular data from an ascii file.
##
## @var{data} is read from an ascii data file named @var{filename} with
## an optional @var{delimeter}.  The delimeter may be any single
## character or
## @itemize
## @item "space" " " (default)
## @item "tab" "\t"
## @item "comma" ","
## @item "semi" ";"
## @item "bar" "|"
## @end itemize
##
## The @var{data} is read starting at cell (2,2) where the
## @var{varnames} form a char matrix from the first row (starting at
## (1,2)) vertically concatenated, and the @var{casenames} form a char
## matrix read from the first column (starting at (2,1)) vertically
## concatenated.
## @seealso{tblwrite, csv2cell, cell2csv}
## @end deftypefn

function [data, varnames, casenames] = tblread (f="", d=" ")

  ## Check arguments
  if nargin < 1 || nargin > 2
    print_usage ();
  endif
  if isempty (f)
    ## FIXME: open a file dialog box in this case when a file dialog box
    ## becomes available
    error ("tblread: filename must be given")
  endif
  [d err] = tbl_delim (d);
  if ! isempty (err)
    error ("tblread: %s", err)
  endif

  d = csv2cell (f, d);
  data = cell2mat (d(2:end, 2:end));
  varnames = strvcat (d(1,2:end));
  casenames = strvcat (d(2:end,1));

endfunction

## Tests
%!shared d, v, c
%! d = [1 2;3 4];
%! v = ["a ";"bc"];
%! c = ["de";"f "];
%!test
%! [dt vt ct] = tblread ("tblread-space.dat");
%! assert (dt, d);
%! assert (vt, v);
%! assert (ct, c);
%!test
%! [dt vt ct] = tblread ("tblread-space.dat", " ");
%! assert (dt, d);
%! assert (vt, v);
%! assert (ct, c);
%!test
%! [dt vt ct] = tblread ("tblread-space.dat", "space");
%! assert (dt, d);
%! assert (vt, v);
%! assert (ct, c);
%!test
%! [dt vt ct] = tblread ("tblread-tab.dat", "tab");
%! assert (dt, d);
%! assert (vt, v);
%! assert (ct, c);
%!test
%! [dt vt ct] = tblread ("tblread-tab.dat", "\t");
%! assert (dt, d);
%! assert (vt, v);
%! assert (ct, c);
%!test
%! [dt vt ct] = tblread ("tblread-tab.dat", '\t');
%! assert (dt, d);
%! assert (vt, v);
%! assert (ct, c);
