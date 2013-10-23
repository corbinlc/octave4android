## Copyright (C) 2011 Fotios Kasolis <fotios.kasolis@gmail.com>
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
## @deftypefn {Function File} {Df =} jacobs (@var{x}, @var{f})
## @deftypefnx {Function File} {Df =} jacobs (@var{x}, @var{f}, @var{hook})
## Calculate the jacobian of a function using the complex step method.
##
## Let @var{f} be a user-supplied function. Given a point @var{x} at
## which we seek for the Jacobian, the function @command{jacobs} returns
## the Jacobian matrix @code{d(f(1), @dots{}, df(end))/d(x(1), @dots{},
## x(n))}. The function uses the complex step method and thus can be
## applied to real analytic functions.
##
## The optional argument @var{hook} is a structure with additional options. @var{hook}
## can have the following fields:
## @itemize @bullet
## @item
## @code{h} - can be used to define the magnitude of the complex step and defaults
## to 1e-20; steps larger than 1e-3 are not allowed.
## @item
## @code{fixed} - is a logical vector internally usable by some optimization
## functions; it indicates for which elements of @var{x} no gradient should be
## computed, but zero should be returned.
## @end itemize
##
## For example:
## 
## @example
## @group
## f = @@(x) [x(1)^2 + x(2); x(2)*exp(x(1))];
## Df = jacobs ([1, 2], f)
## @end group
## @end example
## @end deftypefn

function Df = jacobs (x, f, hook)

  if ( (nargin < 2) || (nargin > 3) )
    print_usage ();
  endif

  if (ischar (f))
    f = str2func (f, "global");
  endif

  n  = numel (x);

  default_h = 1e-20;
  max_h = 1e-3; # must be positive

  if (nargin > 2)

    if (isfield (hook, "h"))
      if (! (isscalar (hook.h)))
        error ("complex step magnitude must be a scalar");
      endif
      if (abs (hook.h) > max_h)
        warning ("complex step magnitude larger than allowed, set to %e", ...
                 max_h)
        h = max_h;
      else
        h = hook.h;
      endif
    else
      h = default_h;
    endif

    if (isfield (hook, "fixed"))
      if (numel (hook.fixed) != n)
        error ("index of fixed parameters has wrong dimensions");
      endif
      fixed = hook.fixed;
    else
      fixed = false (n, 1);
    endif

  else

    h = default_h;
    fixed = false (n, 1);

  endif

  if (all (fixed))
    error ("all elements of 'x' are fixed");
  endif

  x = repmat (x(:), 1, n) + h * 1i * eye (n);

  idx = find (! fixed);

  ## after first evaluation, dimensionness of 'f' is known
  t_Df = imag (f (x(:, idx(1)))(:));
  dim = numel (t_Df);

  Df = zeros (dim, n);

  Df(:, idx(1)) = t_Df;

  for count = idx(2:end)
    Df(:, count) = imag (f (x(:, count))(:));
  endfor

  Df /=  h;

endfunction

%!assert (jacobs (1, @(x) x), 1)
%!assert (jacobs (6, @(x) x^2), 12)
%!assert (jacobs ([1; 1], @(x) [x(1)^2; x(1)*x(2)]), [2, 0; 1, 1])
%!assert (jacobs ([1; 2], @(x) [x(1)^2 + x(2); x(2)*exp(x(1))]), [2, 1; 2*exp(1), exp(1)])

%% Test input validation
%!error jacobs ()
%!error jacobs (1)
%!error jacobs (1, 2, 3, 4)
%!error jacobs (@sin, 1, [1, 1])
%!error jacobs (@sin, 1, ones(2, 2))

%!demo
%! # Relative error against several h-values
%! k = 3:20; h = 10 .^ (-k); x = 0.3*pi;
%! err = zeros (1, numel (k));
%! for count = 1 : numel (k)
%!   err(count) = abs (jacobs (x, @sin,	struct ("h", h(count))) - cos (x)) / abs (cos (x)) + eps;
%! endfor
%! loglog (h, err); grid minor;
%! xlabel ("h"); ylabel ("|Df(x) - cos(x)| / |cos(x)|")
%! title ("f(x)=sin(x), f'(x)=cos(x) at x = 0.3pi")
