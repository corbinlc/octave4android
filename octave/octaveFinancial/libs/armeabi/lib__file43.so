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
## @deftypefn {Function File} {@var{pvi} =} posvolidx (@var{closeprice}, @var{vol})
## @deftypefnx {Function File} {@var{pvi} =} posvolidx ([@var{closeprice} @var{vol}])
## @deftypefnx {Function File} {@var{pvi} =} posvolidx (@var{closeprice}, @var{vol}, @var{initpvi})
## @deftypefnx {Function File} {@var{pvi} =} posvolidx ([@var{closeprice} @var{vol}], @var{initpvi})
##
## Compute the positive volume index of a security based on its closing
## price (@var{closeprice}) and @var{vol}ume.  They may be given as
## separate arguments or as an nx2 matrix.  If given, the @var{initpvi}
## is the starting value of the pvi (default: 100).
##
## The @var{pvi} will always be a column vector.
##
## @seealso{onbalvol, negvolidx}
## @end deftypefn

function pvi = posvolidx (c, vol, initpvi)

  default_pvi = 100;
  pvi         = zeros (length (c), 1);
  if isvector (c)
    if nargin < 2
      ## a closing price was given without a volume
      print_usage ();
    elseif isscalar (vol)
      ## probably initpvi was given as the second argument
      print_usage ();
    elseif !isvector (vol)
      print_usage ();
    elseif length (c) != length (vol)
      error ("closeprice and vol must be the same length");
    endif
    c = [c(:) vol(:)];
    if nargin < 3
      pvi(1) = default_pvi;
    else
      pvi(1) = initpvi;
    endif
  elseif size (c, 2) != 2
    error ("If given as a matrix, c must have exactly two columns.")
  elseif size (c, 2) == 2
    if nargin == 2
      pvi(1) = vol;
    else
      pvi(1) = default_pvi;
    endif
  else
    print_usage ();
  endif

  ## Start doing the work
  for i = 2:size (c, 1)
    pvi(i) = pvi(i-1) + (c(i,2) > c(i-1,2))*pvi(i-1)*(c(i,1)-c(i-1,1))/c(i-1,1);
  endfor

endfunction

## Tests
%!shared c, v, pvia, pvib
%! c = [22.44 22.61 22.67 22.88 23.36 23.23 23.08 22.86 23.17 23.69 23.77 23.84 24.32 24.8 24.16 24.1 23.37 23.61 23.21];
%! v = [10 12 23 25 34 12 32 15 15 34 54 12 86 45 32 76 89 13 28];
%! pvia = [100 100.7575758 101.0249554 101.9607843 104.0998217 104.0998217 103.4276318 103.4276318 103.4276318 105.7488389 106.1059477 106.1059477 108.242309 108.242309 108.242309 107.9734953 104.7029289 104.7029289 102.9290546]';
%! pvib = [5 5.037878788 5.051247772 5.098039216 5.204991087 5.204991087 5.171381588 5.171381588 5.171381588 5.287441943 5.305297383 5.305297383 5.412115451 5.412115451 5.412115451 5.398674767 5.235146444 5.235146444 5.14645273]';
%!assert(posvolidx(c, v), pvia, 1e-5)
%!assert(posvolidx([c' v']), pvia, 1e-5)
%!assert(posvolidx(c, v, 5), pvib, 1e-5)
%!assert(posvolidx([c' v'], 5), pvib, 1e-5)
