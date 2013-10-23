## Copyright (C) 2009-2011 Philip Nienhuis <pr.nienhuis at users.sf.net>
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

## Parse a string representing a range of cells for a spreadsheet
## into nr of rows and nr of columns and also extract top left
## cell address + top row + left column. Some error checks are implemented.

## Author: Philip Nienhuis
## Created: 2009-06-20
## Latest update 2010-01-13

function [topleft, nrows, ncols, toprow, lcol] = parse_sp_range (range_org)

  range = deblank (upper (range_org));
  range_error = 0; nrows = 0; ncols = 0;

  # Basic checks
  if (index (range, ':') == 0)
    if (isempty (range))
      range_error = 0;
      leftcol = 'A';
      rightcol = 'A';
    else
      # Only upperleft cell given, just complete range to 1x1
      # (needed for some routines)
      range = [range ":" range];
    endif
  endif

  # Split up both sides of the range
  [topleft, lowerright] = strtok (range, ':');

  # Get toprow and clean up left column
  [st, en] = regexp (topleft, '\d+');
  toprow = str2num (topleft(st:en));
  leftcol = deblank (topleft(1:st-1));
  [st, en1] = regexp (leftcol, '\s+');
  if (isempty (en1)) 
    en1=0  ; 
  endif
  [st, en2] = regexp (leftcol,'\D+');
  leftcol = leftcol(en1+1:en2);

  # Get bottom row and clean up right column
  [st, en] = regexp (lowerright, '\d+');
  bottomrow = str2num (lowerright(st:en));
  rightcol = deblank (lowerright(2:st-1));
  [st, en1] = regexp (rightcol, '\s+');
  if (isempty (en1)) 
    en1 = 0; 
  endif
  [st, en2] = regexp (rightcol, '\D+');
  rightcol = rightcol(en1+1:en2);

  # Check nr. of rows
  nrows = bottomrow - toprow + 1; 
  if (nrows < 1) 
    range_error = 1; 
  endif

  if (range_error == 0) 
    # Get left column nr.
    [st, en] = regexp (leftcol, '\D+');
    lcol = (leftcol(st:st) - 'A' + 1);
    while (++st <= en)
      lcol = lcol * 26 + (leftcol(st:st) - 'A' + 1);
    endwhile

    # Get right column nr.
    [st, en] = regexp (rightcol, '\D+');
    rcol = (rightcol(st:st) - 'A' + 1);
    while (++st <= en)
      rcol = rcol * 26 + (rightcol(st:st) - 'A' + 1);
    endwhile

    # Check
    ncols = rcol - lcol + 1;
    if (ncols < 1) 
      range_error = 1; 
    endif
  endif

  if (range_error > 0) 
    ncols = 0; nrows = 0;
    error ("Spreadsheet range error!");
  endif
  
endfunction

%!test
%! [a b c d e] = parse_sp_range ('A1:B2');
%! assert ([a b c d e], ['A1', 2, 2, 1, 1]);

%!test
%! [a b c d e] = parse_sp_range ('A1:AB200');
%! assert ([a b c d e], ['A1', 200, 28, 1, 1]);

%!test
%! [a b c d e] = parse_sp_range ('cd230:iY65536');
%! assert ([a b c d e], ['CD230', 65307, 178, 230, 82]);

%!test
%! [a b c d e] = parse_sp_range ('BvV12798 : xFd1054786');
%! assert ([b c d e], [1041989, 14439, 12798, 1946]);
