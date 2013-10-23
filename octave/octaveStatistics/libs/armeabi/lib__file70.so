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
## @deftypefn {Function File} {} tblwrite (@var{data}, @var{varnames}, @var{casenames}, @var{filename})
## @deftypefnx {Function File} {} tblwrite (@var{data}, @var{varnames}, @var{casenames}, @var{filename}, @var{delimeter})
## Write tabular data to an ascii file.
##
## @var{data} is written to an ascii data file named @var{filename} with
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
## The @var{data} is written starting at cell (2,2) where the
## @var{varnames} are a char matrix or cell vector written to the first
## row (starting at (1,2)), and the @var{casenames} are a char matrix
## (or cell vector) written to the first column (starting at (2,1)).
## @seealso{tblread, csv2cell, cell2csv}
## @end deftypefn

function tblwrite (data, varnames, casenames, f="", d=" ")

  ## Check arguments
  if nargin < 4 || nargin > 5
    print_usage ();
  endif
  varnames = __makecell__ (varnames, "varnames");
  casenames = __makecell__ (casenames, "varnames");
  if numel (varnames) != columns (data)
    error ("tblwrite: the number of rows (or cells) in varnames must equal the number of columns in data")
  endif
  if numel (varnames) != rows (data)
    error ("tblwrite: the number of rows (or cells) in casenames must equal the number of rows in data")
  endif

  if isempty (f)
    ## FIXME: open a file dialog box in this case when a file dialog box
    ## becomes available
    error ("tblread: filename must be given")
  endif
  [d err] = tbl_delim (d);
  if ! isempty (err)
    error ("tblwrite: %s", err)
  endif

  dat = cell (size (data) + 1);
  dat(1,2:end) = varnames;
  dat(2:end,1) = casenames;
  dat(2:end,2:end) = mat2cell (data,
                               ones (rows (data), 1),
                               ones (columns (data), 1));;
  cell2csv (f, dat, d);

endfunction

function x = __makecell__ (x, name)
  ## force x into a cell matrix
  if ! iscell (x)
    if ischar (x)
      ## convert varnames into a cell
      x = mat2cell (x, ones (rows (x), 1));
    else
      error ("tblwrite: %s must be either a char or a cell", name)
    endif
  endif
endfunction

## Tests
%!shared d, v, c
%! d = [1 2;3 4];
%! v = ["a ";"bc"];
%! c = ["de";"f "];
%!test
%! tblwrite (d, v, c, "tblwrite-space.dat");
%! [dt vt ct] = tblread ("tblwrite-space.dat", " ");
%! assert (dt, d);
%! assert (vt, v);
%! assert (ct, c);
%!test
%! tblwrite (d, v, c, "tblwrite-space.dat", " ");
%! [dt vt ct] = tblread ("tblwrite-space.dat", " ");
%! assert (dt, d);
%! assert (vt, v);
%! assert (ct, c);
%!test
%! tblwrite (d, v, c, "tblwrite-space.dat", "space");
%! [dt vt ct] = tblread ("tblwrite-space.dat");
%! assert (dt, d);
%! assert (vt, v);
%! assert (ct, c);
%!test
%! tblwrite (d, v, c, "tblwrite-tab.dat", "tab");
%! [dt vt ct] = tblread ("tblwrite-tab.dat", "tab");
%! assert (dt, d);
%! assert (vt, v);
%! assert (ct, c);
%!test
%! tblwrite (d, v, c, "tblwrite-tab.dat", "\t");
%! [dt vt ct] = tblread ("tblwrite-tab.dat", "\t");
%! assert (dt, d);
%! assert (vt, v);
%! assert (ct, c);
%!test
%! tblwrite (d, v, c, "tblwrite-tab.dat", '\t');
%! [dt vt ct] = tblread ("tblwrite-tab.dat", '\t');
%! assert (dt, d);
%! assert (vt, v);
%! assert (ct, c);
