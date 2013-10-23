## Copyright (C) 2011-2012 L. Markowsky <lmarkov@users.sourceforge.net>
##
## This file is part of the fuzzy-logic-toolkit.
##
## The fuzzy-logic-toolkit is free software; you can redistribute it
## and/or modify it under the terms of the GNU General Public License
## as published by the Free Software Foundation; either version 3 of
## the License, or (at your option) any later version.
##
## The fuzzy-logic-toolkit is distributed in the hope that it will be
## useful, but WITHOUT ANY WARRANTY; without even the implied warranty
## of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
## General Public License for more details.
##
## You should have received a copy of the GNU General Public License
## along with the fuzzy-logic-toolkit; see the file COPYING.  If not,
## see <http://www.gnu.org/licenses/>.

## -*- texinfo -*-
## @deftypefn {Function File} {@var{y} =} gauss2mf (@var{x}, @var{params})
## @deftypefnx {Function File} {@var{y} =} gauss2mf (@var{[x1 x2 ... xn]}, @var{[sig1 c1 sig2 c2]})
##
## For a given domain @var{x} and parameters @var{params} (or
## @var{[sig1 c1 sig2 c2]}), return the corresponding @var{y} values for the
## two-sided Gaussian composite membership function. This membership function is
## a smooth curve calculated from two Gaussian membership functions as follows:
##
## Given parameters @var{sig1}, @var{c1}, @var{sig2}, and @var{c2}, that define
## two Gaussian membership functions, let:
##
## @example
## @group
## f1(x) = exp((-(x - c1)^2)/(2 * sig1^2))     if x <= c1
##         1                                   otherwise
##
## f2(x) = 1                                   if x <= c2
##         exp((-(x - c2)^2)/(2 * sig2^2))     otherwise
## @end group
## @end example
##
## @noindent
## Then gauss2mf is given by:
##
## @example
## f(x) = f1(x) * f2(x)
## @end example
##
## The argument @var{x} must be a real number or a non-empty vector of strictly
## increasing real numbers, and @var{sig1}, @var{c1}, @var{sig2}, and @var{c2}
## must be real numbers.
## Gauss2mf always returns a continuously differentiable curve with values in
## the range [0, 1]. 
##
## If @var{c1} < @var{c2}, gauss2mf is a normal membership function (has a
## maximum value of 1), with the rising curve identical to that of f1(x) and a
## falling curve identical to that of f2(x), above. If @var{c1} >= @var{c2},
## gauss2mf returns a subnormal membership function (has a maximum value less
## than 1).
##
## @noindent
## To run the demonstration code, type @t{demo('gauss2mf')} at the Octave prompt.
##
## @seealso{dsigmf, gaussmf, gbellmf, pimf, psigmf, sigmf, smf, trapmf, trimf, zmf}
## @end deftypefn

## Author:        L. Markowsky
## Keywords:      fuzzy-logic-toolkit fuzzy membership gaussian
## Directory:     fuzzy-logic-toolkit/inst/
## Filename:      gauss2mf.m
## Last-Modified: 19 Aug 2012

function y = gauss2mf (x, params)

  ## If the caller did not supply 2 argument values with the correct
  ## types, print an error message and halt.

  if (nargin != 2)
    puts ("Type 'help gauss2mf' for more information.\n");
    error ("gauss2mf requires 2 arguments\n");
  elseif (!is_domain (x))
    puts ("Type 'help gauss2mf' for more information.\n");
    error ("gauss2mf's first argument must be a valid domain\n");
  elseif (!are_mf_params ('gauss2mf', params))
    puts ("Type 'help gauss2mf' for more information.\n");
    error ("gauss2mf's second argument must be a parameter vector\n");
  endif

  ## Calculate and return the y values of the membership function on
  ## the domain x according to the definition of gauss2mf given in the
  ## comment above.

  sig1 = params(1);
  c1 = params(2);
  sig2 = params(3);
  c2 = params(4);

  f1_val = @(x_val) (x_val <= c1) * ...
                    exp ((-(x_val - c1)^2)/(2 * sig1^2)) + ...
                    (x_val > c1);

  f2_val = @(x_val) (x_val <= c2) + ... 
                    (x_val > c2) * exp ((-(x_val - c2)^2)/(2 * sig2^2));

  f1 = arrayfun (f1_val, x);
  f2 = arrayfun (f2_val, x);
  y = f1 .* f2;

endfunction

%!demo
%! x = -10:0.2:10;
%! params = [3 0 1.5 2];
%! y1 = gauss2mf(x, params);
%! params = [1.5 0 3 2];
%! y2 = gauss2mf(x, params);
%! params = [1.5 2 3 0];
%! y3 = gauss2mf(x, params);
%! figure('NumberTitle', 'off', 'Name', 'gauss2mf demo');
%! plot(x, y1, 'r;params = [3 0 1.5 2];', 'LineWidth', 2);
%! hold on ;
%! plot(x, y2, 'b;params = [1.5 0 3 2];', 'LineWidth', 2);
%! hold on ;
%! plot(x, y3, 'g;params = [1.5 2 3 0];', 'LineWidth', 2);
%! ylim([-0.1 1.1]);
%! xlabel('Crisp Input Value', 'FontWeight', 'bold');
%! ylabel('Degree of Membership', 'FontWeight', 'bold');
%! grid;
%! hold;
