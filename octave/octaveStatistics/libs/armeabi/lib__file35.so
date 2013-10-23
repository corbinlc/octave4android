## Copyright (C) 2001 Paul Kienzle <pkienzle@users.sf.net>
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
## @deftypefn {Function File} mad (@var{x})
## @deftypefnx{Function File} mad (@var{x}, @var{flag})
## @deftypefnx{Function File} mad (@var{x}, @var{flag}, @var{dim})
## Compute the mean/median absolute deviation of @var{x}.
##
## The mean absolute deviation is computed as
##
## @example
## mean (abs (@var{x} - mean (@var{x})))
## @end example
##
## and the median absolute deviation is computed as
##
## @example
## median (abs (@var{x} - median (@var{x})))
## @end example
##
## Elements of @var{x} containing NaN or NA values are ignored during computations.
##
## If @var{flag} is 0, the absolute mean deviation is computed, and if @var{flag}
## is 1, the absolute median deviation is computed. By default @var{flag} is 0.
##
## This is done along the dimension @var{dim} of @var{x}. If this variable is not
## given, the mean/median absolute deviation s computed along the smallest dimension of
## @var{x}.
##
## @seealso{std}
## @end deftypefn

function a = mad (X, flag = 0, dim = [])
  ## Check input
  if (nargin < 1)
    print_usage ();
  endif
  if (nargin > 3)
    error ("mad: too many input arguments");
  endif
  
  if (!isnumeric (X))
    error ("mad: first input must be numeric");
  endif
  
  if (isempty (dim))
    dim = min (find (size (X) > 1));
    if (isempty(dim))
      dim = 1;
    endif
  endif
  
  if (!isscalar (flag))
    error ("mad: second input argument must be a scalar");
  endif
  if (!isscalar (dim))
    error ("mad: dimension argument must be a scalar");
  endif
  
  if (flag == 0)
    f = @nanmean;
  else
    f = @nanmedian;
  endif
  
  ## Compute the mad
  if (prod(size(X)) != size(X,dim))
    sz = ones (1, length (size (X)));
    sz (dim) = size (X,dim);
    a = f (abs (X - repmat (f (X, dim), sz)), dim);
  elseif (all (size (X) > 1))
    a = f (abs (X - ones (size(X, 1), 1) * f (X, dim)), dim);
  else
    a = f (abs (X - f(X, dim)), dim);
  endif
endfunction

## Tests

%!assert (mad(1), 0);
%!test
%! X = eye(3); abs_mean = [4/9, 4/9, 4/9]; abs_median=[0,0,0];
%! assert(mad(X), abs_mean, eps);
%! assert(mad(X, 0), abs_mean, eps);
%! assert(mad(X,1), abs_median);
