## Copyright (C) 2006 Frederick (Rick) A Niles <niles@rickniles.com>
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
## @deftypefn {Function File} {} jsupdf (@var{x}, @var{alpha1}, @var{alpha2})
## For each element of @var{x}, compute the probability density function
## (PDF) at @var{x} of the Johnson SU distribution with shape parameters @var{alpha1}
## and @var{alpha2}.
##
## Default values are @var{alpha1} = 1, @var{alpha2} = 1.
## @end deftypefn

## Author: Frederick (Rick) A Niles <niles@rickniles.com>
## Description: PDF of Johnson SU distribution

## This function is derived from normpdf.m

## This is the TeX equation of this function:
##
## \[ f(x) = \frac{\alpha_2}{\sqrt{x^2+1}} \phi\left(\alpha_1+\alpha_2
## \log{\left(x+\sqrt{x^2+1}\right)}\right) \]
##
## where \[ -\infty < x < \infty ; \alpha_2 > 0 \] and $\phi$ is the
## standard normal probability distribution function.  $\alpha_1$ and
## $\alpha_2$ are shape parameters.

function pdf = jsupdf (x, alpha1, alpha2)

  if (nargin != 1 && nargin != 3)
    print_usage;
  endif

  if (nargin == 1)
    alpha1 = 1;
    alpha2 = 1;
  endif

  if (!isscalar (alpha1) || !isscalar(alpha2))
    [retval, x, alpha1, alpha2] = common_size (x, alpha1, alpha2);
    if (retval > 0)
      error ("normpdf: x, alpha1 and alpha2 must be of common size or scalars");
    endif
  endif

  one = ones(size(x));
  sr = sqrt(x.*x + one);
  pdf = (alpha2 ./ sr) .* stdnormal_pdf (alpha1 .* one +
                                         alpha2 .* log (x + sr));

endfunction
