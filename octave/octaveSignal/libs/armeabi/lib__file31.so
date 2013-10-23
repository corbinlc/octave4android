## Copyright (C) 2003 David Bateman <adb014@gmail.com>
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
## @deftypefn {Function File} {} convmtx (@var{a}, @var{n})
## If @var{a} is a column vector and @var{x} is a column vector
## of length @var{n}, then 
##
## @code{convmtx(@var{a}, @var{n}) * @var{x}}
##
## gives the convolution of of @var{a} and @var{x} and is the
## same as @code{conv(@var{a}, @var{x})}. The difference is if
## many vectors are to be convolved with the same vector, then
## this technique is possibly faster.
##
## Similarly, if @var{a} is a row vector and @var{x} is a row 
## vector of length @var{n}, then
##
## @code{@var{x} * convmtx(@var{a}, @var{n})}
##
## is the same as @code{conv(@var{x}, @var{a})}.
## @end deftypefn
## @seealso{conv}

function b = convmtx (a, n)

  if (nargin != 2)
    print_usage;
  endif
   
  [r, c] = size(a);
  
  if ((r != 1) && (c != 1)) || (r*c == 0)
    error("convmtx: expecting vector argument");
  endif
  
  b = toeplitz([a(:); zeros(n-1,1)],[a(1); zeros(n-1,1)]);
  if (c > r)
    b = b.';
  endif

endfunction

%!assert(convmtx([3,4,5],3),[3,4,5,0,0;0,3,4,5,0;0,0,3,4,5])
%!assert(convmtx([3;4;5],3),[3,0,0;4,3,0;5,4,3;0,5,4;0,0,5])
