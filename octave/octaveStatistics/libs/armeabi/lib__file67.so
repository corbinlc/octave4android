## Copyright (C) 2006, 2008 Bill Denney <bill@denney.ws>
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
## @deftypefn {Function File} {@var{y} =} squareform (@var{x})
## @deftypefnx {Function File} {@var{y} =} squareform (@var{x}, @
## "tovector")
## @deftypefnx {Function File} {@var{y} =} squareform (@var{x}, @
## "tomatrix")
## Convert a vector from the pdist function into a square matrix or from
## a square matrix back to the vector form.
##
## The second argument is used to specify the output type in case there
## is a single element.
## @seealso{pdist}
## @end deftypefn

## Author: Bill Denney <bill@denney.ws>

function y = squareform (x, method)

  if nargin < 1
    print_usage ();
  elseif nargin < 2
    if isscalar (x) || isvector (x)
      method = "tomatrix";
    elseif issquare (x)
      method = "tovector";
    else
      error ("squareform: cannot deal with a nonsquare, nonvector \
       input");
    endif
  endif
  method = lower (method);

  if ! strcmp ({"tovector" "tomatrix"}, method)
    error ("squareform: method must be either \"tovector\" or \
       \"tomatrix\"");
  endif

  if strcmp ("tovector", method)
    if ! issquare (x)
      error ("squareform: x is not a square matrix");
    endif

    sx = size (x, 1);
    y = zeros ((sx-1)*sx/2, 1);
    idx = 1;
    for i = 2:sx
      newidx = idx + sx - i;
      y(idx:newidx) = x(i:sx,i-1);
      idx = newidx + 1;
    endfor
  else
    ## we're converting to a matrix

    ## make sure that x is a column
    x = x(:);

    ## the dimensions of y are the solution to the quadratic formula
    ## for:
    ## length(x) = (sy-1)*(sy/2)
    sy = (1 + sqrt (1+ 8*length (x)))/2;
    y = zeros (sy);
    for i = 1:sy-1
      step = sy - i;
      y((sy-step+1):sy,i) = x(1:step);
      x(1:step) = [];
    endfor
    y = y + y';
  endif

endfunction

## make sure that it can go both directions automatically
%!assert(squareform(1:6), [0 1 2 3;1 0 4 5;2 4 0 6;3 5 6 0])
%!assert(squareform([0 1 2 3;1 0 4 5;2 4 0 6;3 5 6 0]), [1:6]')

## make sure that the command arguments force the correct behavior
%!assert(squareform(1), [0 1;1 0])
%!assert(squareform(1, "tomatrix"), [0 1;1 0])
%!assert(squareform(1, "tovector"), zeros(0,1))
