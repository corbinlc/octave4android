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
## @deftypefn {Function File} {@var{y} =} psigmf (@var{x}, @var{params})
## @deftypefnx {Function File} {@var{y} =} psigmf (@var{[x1 x2 ... xn]}, @var{[a1 c1 a2 c2]})
##
## For a given domain @var{x} and parameters @var{params} (or
## @var{[a1 c1 a2 c2]}), return the corresponding @var{y} values for the product
## of two sigmoidal membership functions.
##
## The argument @var{x} must be a real number or a non-empty vector of strictly
## increasing real numbers, and @var{a1}, @var{c1}, @var{a2}, and @var{c2} must
## be real numbers. This membership function satisfies the equation:
## @example
## f(x) = (1/(1 + exp(-a1*(x - c1)))) * (1/(1 + exp(-a2*(x - c2))))
## @end example
##
## @noindent
## The function is bounded above by 1 and below by 0.
##
## If @var{a1} is positive, @var{a2} is negative, and @var{c1} and @var{c2} are
## far enough apart with @var{c1} < @var{c2}, then:
## @itemize @w
## @item
## (a1)/4 ~ the rising slope at c1
## @item
## c1 ~ the left inflection point
## @item
## (a2)/4 ~ the falling slope at c2
## @item
## c2 ~ the right inflection point
## @end itemize
##
## @noindent
## and at each inflection point, the value of the function is about 0.5:
## @itemize @w
## @item
## f(c1) ~ f(c2) ~ 0.5.
## @end itemize
##
## @noindent
## (Here, the symbol ~ means "approximately equal".)
##
## @noindent
## To run the demonstration code, type @t{demo('psigmf')} at the Octave prompt.
##
## @seealso{dsigmf, gauss2mf, gaussmf, gbellmf, pimf, sigmf, smf, trapmf, trimf, zmf}
## @end deftypefn

## Author:        L. Markowsky
## Keywords:      fuzzy-logic-toolkit fuzzy membership sigmoidal
## Directory:     fuzzy-logic-toolkit/inst/
## Filename:      psigmf.m
## Last-Modified: 19 Aug 2012

function y = psigmf (x, params)

  ## If the caller did not supply 2 argument values with the correct
  ## types, print an error message and halt.

  if (nargin != 2)
    puts ("Type 'help psigmf' for more information.\n");
    error ("psigmf requires 2 arguments\n");
  elseif (!is_domain (x))
    puts ("Type 'help psigmf' for more information.\n");
    error ("psigmf's first argument must be a valid domain\n");
  elseif (!are_mf_params ('psigmf', params))
    puts ("Type 'help psigmf' for more information.\n");
    error ("psigmf's second argument must be a parameter vector\n");
  endif

  ## Calculate and return the y values of the membership function on
  ## the domain x.

  a1 = params(1);
  c1 = params(2);
  a2 = params(3);
  c2 = params(4);

  y_val = @(x_val) 1 / (1 + exp (-a1 * (x_val - c1))) * ...
                   1 / (1 + exp (-a2 * (x_val - c2)));
  y = arrayfun (y_val, x);

endfunction

%!demo
%! x = 0:100;
%! params = [0.5 20 -0.3 60];
%! y1 = psigmf(x, params);
%! params = [0.3 20 -0.2 60];
%! y2 = psigmf(x, params);
%! params = [0.2 20 -0.1 60];
%! y3 = psigmf(x, params);
%! figure('NumberTitle', 'off', 'Name', 'psigmf demo');
%! plot(x, y1, 'r;params = [0.5 20 -0.3 60];', 'LineWidth', 2)
%! hold on;
%! plot(x, y2, 'b;params = [0.3 20 -0.2 60];', 'LineWidth', 2)
%! hold on;
%! plot(x, y3, 'g;params = [0.2 20 -0.1 60];', 'LineWidth', 2)
%! ylim([-0.1 1.1]);
%! xlabel('Crisp Input Value', 'FontWeight', 'bold');
%! ylabel('Degree of Membership', 'FontWeight', 'bold');
%! grid;
