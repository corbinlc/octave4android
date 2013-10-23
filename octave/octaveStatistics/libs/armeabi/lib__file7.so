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
## @deftypefn {Function File} {} casewrite (@var{strmat}, @var{filename})
## Write case names to an ascii file.
##
## Essentially, this writes all lines from @var{strmat} to
## @var{filename} (after deblanking them).
## @seealso{caseread, tblread, tblwrite, csv2cell, cell2csv, fopen}
## @end deftypefn

## Author: Bill Denney <bill@denney.ws>
## Description: Write strings from a file

function names = casewrite (s="", f="")

  ## Check arguments
  if nargin != 2
    print_usage ();
  endif
  if isempty (f)
    ## FIXME: open a file dialog box in this case when a file dialog box
    ## becomes available
    error ("casewrite: filename must be given")
  endif
  if isempty (s)
    error ("casewrite: strmat must be given")
  elseif ! ischar (s)
    error ("casewrite: strmat must be a character matrix")
  elseif ndims (s) != 2
    error ("casewrite: strmat must be two dimensional")
  endif

  [fid msg] = fopen (f, "wt");
  if fid < 0 || (! isempty (msg))
    error ("casewrite: cannot open %s for writing: %s", f, msg);
  endif

  for i = 1:rows (s)
    status = fputs (fid, sprintf ("%s\n", deblank (s(i,:))));
  endfor
  if (fclose (fid) < 0)
    error ("casewrite: error closing f")
  endif

endfunction

## Tests
%!shared s
%! s = ["a  ";"bcd";"ef "];
%!test
%! casewrite (s, "casewrite.dat")
%! assert(caseread ("casewrite.dat"), s);
