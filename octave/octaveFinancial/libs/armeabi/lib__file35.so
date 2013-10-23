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
## @deftypefn {Function File} {@var{nvi} =} negvolidx (@var{closeprice}, @var{vol})
## @deftypefnx {Function File} {@var{nvi} =} negvolidx ([@var{closeprice} @var{vol}])
## @deftypefnx {Function File} {@var{nvi} =} negvolidx (@var{closeprice}, @var{vol}, @var{initnvi})
## @deftypefnx {Function File} {@var{nvi} =} negvolidx ([@var{closeprice} @var{vol}], @var{initnvi})
##
## Compute the negative volume index of a security based on its closing
## price (@var{closeprice}) and @var{vol}ume.  They may be given as
## separate arguments or as an nx2 matrix.  If given, the @var{initnvi}
## is the starting value of the nvi (default: 100).
##
## The @var{nvi} will always be a column vector.
##
## @seealso{onbalvol, posvolidx}
## @end deftypefn

function nvi = negvolidx (c, vol, initnvi)

  default_nvi = 100;
  nvi         = zeros (length (c), 1);
  if isvector (c)
    if nargin < 2
      ## a closing price was given without a volume
      print_usage ();
    elseif isscalar (vol)
      ## probably initnvi was given as the second argument
      print_usage ();
    elseif !isvector (vol)
      print_usage ();
    elseif length (c) != length (vol)
      error ("closeprice and vol must be the same length");
    endif
    c = [c(:) vol(:)];
    if nargin < 3
      nvi(1) = default_nvi;
    else
      nvi(1) = initnvi;
    endif
  elseif size (c, 2) != 2
    error ("If given as a matrix, c must have exactly two columns.")
  elseif size (c, 2) == 2
    if nargin == 2
      nvi(1) = vol;
    else
      nvi(1) = default_nvi;
    endif
  else
    print_usage ();
  endif

  ## Start doing the work
  for i = 2:size (c, 1)
    nvi(i) = nvi(i-1) + (c(i,2) < c(i-1,2))*nvi(i-1)*(c(i,1)-c(i-1,1))/c(i-1,1);
  endfor

endfunction

## Tests
%!shared c, v, nvia, nvib
%! c = [22.44 22.61 22.67 22.88 23.36 23.23 23.08 22.86 23.17 23.69 23.77 23.84 24.32 24.8 24.16 24.1 23.37 23.61 23.21];
%! v = [10 12 23 25 34 12 32 15 15 34 54 12 86 45 32 76 89 13 28];
%! nvia = [100 100 100 100 100 99.44349315 99.44349315 98.49559157 98.49559157 98.49559157 98.49559157 98.78565011 98.78565011 100.7353669 98.13574451 98.13574451 98.13574451 99.14355704 99.14355704]';
%! nvib = [5 5 5 5 5 4.972174658 4.972174658 4.924779578 4.924779578 4.924779578 4.924779578 4.939282505 4.939282505 5.036768344 4.906787226 4.906787226 4.906787226 4.957177852 4.957177852]';
%!assert(negvolidx(c, v), nvia, 1e-5)
%!assert(negvolidx([c' v']), nvia, 1e-5)
%!assert(negvolidx(c, v, 5), nvib, 1e-5)
%!assert(negvolidx([c' v'], 5), nvib, 1e-5)
