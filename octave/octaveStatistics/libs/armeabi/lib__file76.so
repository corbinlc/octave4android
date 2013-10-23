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
## @deftypefn {Function File} @var{theta} = vmrnd (@var{mu}, @var{k})
## @deftypefnx{Function File} @var{theta} = vmrnd (@var{mu}, @var{k}, @var{sz})
## Draw random angles from a Von Mises distribution with mean @var{mu} and
## concentration @var{k}.
##
## The Von Mises distribution has probability density function
## @example
## f (@var{x}) = exp (@var{k} * cos (@var{x} - @var{mu})) / @var{Z} ,
## @end example
## where @var{Z} is a normalisation constant.
##
## The output, @var{theta}, is a matrix of size @var{sz} containing random angles
## drawn from the given Von Mises distribution. By default, @var{mu} is 0
## and @var{k} is 1.
## @seealso{vmpdf}
## @end deftypefn

function theta = vmrnd (mu = 0, k = 1, sz = 1)
  ## Check input
  if (!isreal (mu))
    error ("vmrnd: first input must be a scalar");
  endif
  
  if (!isreal (k) || k <= 0)
    error ("vmrnd: second input must be a real positive scalar");
  endif
  
  if (isscalar (sz))
    sz = [sz, sz];
  elseif (!isvector (sz))
    error ("vmrnd: third input must be a scalar or a vector");
  endif
  
  ## Simulate!
  if (k < 1e-6)
    ## k is small: sample uniformly on circle
    theta = 2 * pi * rand (sz) - pi;
  
  else
    a = 1 + sqrt (1 + 4 * k.^2);
    b = (a - sqrt (2 * a)) / (2 * k);
    r = (1 + b^2) / (2 * b);

    N = prod (sz);
    notdone = true (N, 1);
    while (any (notdone))
      u (:, notdone) = rand (3, N);
      
      z (notdone) = cos (pi * u (1, notdone));
      f (notdone) = (1 + r * z (notdone)) ./ (r + z (notdone));
      c (notdone) = k * (r - f (notdone));
      
      notdone = (u (2, :) >= c .* (2 - c)) & (log (c) - log (u (2, :)) + 1 - c < 0);
      N = sum (notdone);
    endwhile
    
    theta = mu + sign (u (3, :) - 0.5) .* acos (f);
    theta = reshape (theta, sz);
  endif
endfunction
