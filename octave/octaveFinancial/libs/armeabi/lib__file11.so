## Copyright (C) 2007 David Bateman
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
## @deftypefn {Function File} {} daysact (@var{d1})
## @deftypefnx {Function File} {} daysact (@var{d1}, @var{d2})
## Calculates the number of days between two dates. If the second date is not
## given, calculate the number of days since 1-Jan-0000. The variables @var{d1}
## and @var{d2} can either be strings or an @var{n}-row string matrix. If both
## @var{d1} and @var{d2} are string matrices, then the number of rows must 
## match. An example of the use of @code{daysact} is
##
## @example
## @group
## daysact ("01-Jan-2007", ["10-Jan-2007"; "23-Feb-2007"; "23-Jul-2007"])
## @result{}      9
##        53
##       203
## @end group
## @end example
## @seealso{datenum}
## @end deftypefn

function days = daysact (d1, d2)
 if (nargin == 1)
   nr = size (d1, 1);
   if (nr != 1)
     days = zeros (nr,1);
     for i = 1 : nr
       days (i) = datenum (d1 (i,:));
     endfor
   else
     days = datenum(d1);
   endif
 elseif (nargin == 2)
   nr1 = size (d1, 1);
   nr2 = size (d2, 1);   
   if (nr1 != nr2 && nr1 != 1 && nr2 != 1)
     error ("daysact: size mismatch");
   endif
   if (nr1 == 1 && nr2 == 1)
     days = datenum (d2) - datenum(d1);
   elseif (nr1 == 1)
     days = zeros (nr2, 1);
     for i = 1 : nr2
       days(i) = datenum (d2 (i,:)) - datenum (d1);
     endfor
   elseif (nr2 == 1)
     days = zeros (nr1, 1);
     for i = 1 : nr1
       days(i) = datenum (d2) - datenum (d1 (i,:));
     endfor
   else
     days = zeros (nr1, 1);
     for i = 1 : nr1
       days(i) = datenum (d2 (i, :)) - datenum (d1 (i,:));
     endfor
   endif
 else
   print_usage();
  endif
endfunction

%!assert (daysact ("01-Jan-2007", ["10-Jan-2007"; "23-Feb-2007"; "23-Jul-2007"]),[9;53;203])
