## Copyright (C) 2008 Carlo de Falco <carlo.defalco@gmail.com>
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
## @deftypefn {Function File} {@var{x0}} = zerocrossing (@var{x}, @
## @var{y})
## Estimates the points at which a given waveform y=y(x) crosses the
## x-axis using linear interpolation.
## @seealso{fzero, roots}
## @end deftypefn

function  retval  = zerocrossing (x,y)

  x = x(:);y = y(:);
  crossing_intervals = (y(1:end-1).*y(2:end) <= 0);
  left_ends = (x(1:end-1))(crossing_intervals);
  right_ends = (x(2:end))(crossing_intervals);
  left_vals = (y(1:end-1))(crossing_intervals);
  right_vals = (y(2:end))(crossing_intervals);
  mid_points = (left_ends+right_ends)/2;
  zero_intervals = find(left_vals==right_vals);
  retval1 = mid_points(zero_intervals);
  left_ends(zero_intervals) = [];
  right_ends(zero_intervals) = [];
  left_vals(zero_intervals) = [];
  right_vals(zero_intervals) = [];
  retval2=left_ends-(right_ends-left_ends).*left_vals./(right_vals-left_vals);
  retval = union(retval1,retval2);

endfunction

%!test
%! x = linspace(0,1,100);
%! y = rand(1,100)-0.5;
%! x0= zerocrossing(x,y);
%! y0 = interp1(x,y,x0);
%! assert(norm(y0,inf), 0, 100*eps)

%!test
%! x = linspace(0,1,100);
%! y = rand(1,100)-0.5;
%! y(10:20) = 0;
%! x0= zerocrossing(x,y);
%! y0 = interp1(x,y,x0);
%! assert(norm(y0,inf), 0, 100*eps)

%!demo
%! x = linspace(0,1,100);
%! y = rand(1,100)-0.5;
%! x0= zerocrossing(x,y);
%! y0 = interp1(x,y,x0);
%! plot(x,y,x0,y0,'x')

%!demo
%! x = linspace(0,1,100);
%! y = rand(1,100)-0.5;
%! y(10:20) = 0;
%! x0= zerocrossing(x,y);
%! y0 = interp1(x,y,x0);
%! plot(x,y,x0,y0,'x')
