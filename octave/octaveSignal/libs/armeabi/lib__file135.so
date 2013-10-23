## Copyright (C) 2000 Dave Cogdell <cogdelld@asme.org>
## Copyright (C) 2000 Paul Kienzle <pkienzle@users.sf.net>
## Copyright (C) 2012 Carnë Draug <carandraug+dev@gmail.com>
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
## @deftypefn {Function File} {@var{c} =} xcorr2 (@var{a})
## @deftypefnx {Function File} {@var{c} =} xcorr2 (@var{a}, @var{b})
## @deftypefnx {Function File} {@var{c} =} xcorr2 (@dots{}, @var{scale})
## Compute the 2D cross-correlation of matrices @var{a} and @var{b}.
##
## If @var{b} is not specified, computes autocorrelation of @var{a}, i.e.,
## same as @code{xcorr (@var{a}, @var{a})}.
##
## The optional argument @var{scale}, defines the type of scaling applied to the
## cross-correlation matrix. Possible values are:
##
## @table @asis
## @item "none" (default)
## No scaling.
##
## @item "biased"
## Scales the raw cross-correlation by the maximum number of elements of @var{a}
## and @var{b} involved in the generation of any element of @var{c}.
##
## @item "unbiased"
## Scales the raw correlation by dividing each element in the cross-correlation
## matrix by the number of products @var{a} and @var{b} used to generate that
## element.
##
## @item "coeff"
## Returns the normalized cross-correlation.
## @end table
##
## @seealso{conv2, corr2, xcorr}
## @end deftypefn

function c = xcorr2 (a, b, biasflag = "none")

  if (nargin < 1 || nargin > 3)
    print_usage;
  elseif (nargin == 2 && ischar (b))
    biasflag = b;
    b        = a;
  elseif (nargin == 1)
    ## we have to set this case here instead of the function line, because if
    ## someone calls the function with zero argument, since a is never set, we
    ## will fail with "`a' undefined" error rather that print_usage
    b = a;
  endif
  if (ndims (a) != 2 || ndims (b) != 2)
    error ("xcorr2: input matrices must must have only 2 dimensions");
  endif

  ## compute correlation
  [ma,na] = size(a);
  [mb,nb] = size(b);
  c = conv2 (a, conj (b (mb:-1:1, nb:-1:1)));

  ## bias routines by Dave Cogdell (cogdelld@asme.org)
  ## optimized by Paul Kienzle (pkienzle@users.sf.net)
  ## coeff routine by Carnë Draug (carandraug+dev@gmail.com)
  switch lower (biasflag)
    case {"none"}
      ## do nothing, it's all done
    case {"biased"}
      c = c / ( min ([ma, mb]) * min ([na, nb]) );
    case {"unbiased"}
      lo   = min ([na,nb]);
      hi   = max ([na, nb]);
      row  = [ 1:(lo-1), lo*ones(1,hi-lo+1), (lo-1):-1:1 ];

      lo   = min ([ma,mb]);
      hi   = max ([ma, mb]);
      col  = [ 1:(lo-1), lo*ones(1,hi-lo+1), (lo-1):-1:1 ]';

      bias = col*row;
      c    = c./bias;

    case {"coeff"}
      a = double (a);
      b = double (b);
      a = conv2 (a.^2, ones (size (b)));
      b = sumsq (b(:));
      c(:,:) = c(:,:) ./ sqrt (a(:,:) * b);

    otherwise
      error ("xcorr2: invalid type of scale %s", biasflag);
  endswitch
endfunction

%!test  # basic usage
%! a = magic (5);
%! b = [6 13 22; 10 18 23; 8 15 23];
%! c = [391  807  519  391  473  289 120
%!      920 1318 1045  909 1133  702 278
%!      995 1476 1338 1534 2040 1161 426
%!      828 1045 1501 2047 2108 1101 340
%!      571 1219 2074 2155 1896  821 234
%!      473 1006 1643 1457  946  347 108
%!      242  539  850  477  374  129  54];
%! assert (xcorr2 (a, b), c);

%!shared a, b, c, row_shift, col_shift
%! row_shift = 18;
%! col_shift = 20;
%! a = randi (255, 30, 30);
%! b = a(row_shift-10:row_shift, col_shift-7:col_shift);
%! c = xcorr2 (a, b, "coeff");
%!assert (nthargout ([1 2], @find, c == max (c(:))), {row_shift, col_shift});   # should return exact coordinates
%! m = rand (size (b)) > 0.5;
%! b(m)  = b(m)  * 0.95;
%! b(!m) = b(!m) * 1.05;
%! c = xcorr2 (a, b, "coeff");
%!assert (nthargout ([1 2], @find, c == max (c(:))), {row_shift, col_shift});   # even with some small noise, should return exact coordinates

%!test  # coeff of autocorrelation must be same as negavtive of correlation by additive inverse
%! a = 10 * randn (100, 100);
%! auto    = xcorr2 (a, "coeff");
%! add_in  = xcorr2 (a, -a, "coeff");
%! assert ([min(auto(:)), max(auto(:))], -[max(add_in(:)), min(add_in(:))]);
