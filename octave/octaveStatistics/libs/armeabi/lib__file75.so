## Copyright (C) 2009 Soren Hauberg <soren@hauberg.org>
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
## @deftypefn {Function File} @var{theta} = vmpdf (@var{x}, @var{mu}, @var{k})
## Evaluates the Von Mises probability density function.
##
## The Von Mises distribution has probability density function
## @example
## f (@var{x}) = exp (@var{k} * cos (@var{x} - @var{mu})) / @var{Z} ,
## @end example
## where @var{Z} is a normalisation constant. By default, @var{mu} is 0 and
## @var{k} is 1.
## @seealso{vmrnd}
## @end deftypefn

function p = vmpdf (x, mu = 0, k = 1)
  ## Check input
  if (!isreal (x))
    error ("vmpdf: first input must be real");
  endif
  
  if (!isreal (mu))
    error ("vmpdf: second input must be a scalar");
  endif
  
  if (!isreal (k) || k <= 0)
    error ("vmpdf: third input must be a real positive scalar");
  endif
  
  ## Evaluate PDF
  Z = 2 * pi * besseli (0, k);
  p = exp (k * cos (x-mu)) / Z;
endfunction
