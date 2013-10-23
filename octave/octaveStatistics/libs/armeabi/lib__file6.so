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
## @deftypefn {Function File} {@var{names} =} caseread (@var{filename})
## Read case names from an ascii file.
##
## Essentially, this reads all lines from a file as text and returns
## them in a string matrix.
## @seealso{casewrite, tblread, tblwrite, csv2cell, cell2csv, fopen}
## @end deftypefn

## Author: Bill Denney <bill@denney.ws>
## Description: Read strings from a file

function names = caseread (f="")

  ## Check arguments
  if nargin != 1
    print_usage ();
  endif
  if isempty (f)
    ## FIXME: open a file dialog box in this case when a file dialog box
    ## becomes available
    error ("caseread: filename must be given")
  endif

  [fid msg] = fopen (f, "rt");
  if fid < 0 || (! isempty (msg))
    error ("caseread: cannot open %s: %s", f, msg);
  endif

  names = {};
  t = fgetl (fid);
  while ischar (t)
    names{end+1} = t;
    t = fgetl (fid);
  endwhile
  if (fclose (fid) < 0)
    error ("caseread: error closing f")
  endif
  names = strvcat (names);

endfunction

## Tests
%!shared n
%! n = ["a  ";"bcd";"ef "];
%!assert (caseread ("caseread.dat"), n);
