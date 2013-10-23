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
## @deftypefn {Function File} {@var{y} =} sigmf (@var{x}, @var{params})
## @deftypefnx {Function File} {@var{y} =} sigmf (@var{[x1 x2 ... xn]}, @var{[a c]})
##
## For a given domain @var{x} and parameters @var{params} (or @var{[a c]}),
## return the corresponding @var{y} values for the sigmoidal membership
## function.
##
## The argument @var{x} must be a real number or a non-empty vector of strictly
## increasing real numbers, and @var{a} and @var{c} must be real numbers. This
## membership function satisfies the equation:
## @itemize @w
## @item
## f(x) = 1/(1 + exp(-a*(x - c)))
## @end itemize
##
## @noindent
## which always returns values in the range [0, 1].
##
## The parameters a and c specify:
## @itemize @w
## @item
## a == the slope at c
## @item
## c == the inflection point
## @end itemize
##
## @noindent
## and at the inflection point, the value of the function is 0.5:
## @itemize @w
## @item
## f(c) == 0.5.
## @end itemize
##
## @noindent
## To run the demonstration code, type @t{demo('sigmf')} at the Octave prompt.
##
## @seealso{dsigmf, gauss2mf, gaussmf, gbellmf, pimf, psigmf, smf, trapmf, trimf, zmf}
## @end deftypefn

## Author:        L. Markowsky
## Keywords:      fuzzy-logic-toolkit fuzzy membership sigmoidal
## Directory:     fuzzy-logic-toolkit/inst/
## Filename:      sigmf.m
## Last-Modified: 19 Aug 2012

function y = sigmf (x, params)

  ## If the caller did not supply 2 argument values with the correct
  ## types, print an error message and halt.

  if (nargin != 2)
    puts ("Type 'help sigmf' for more information.\n");
    error ("sigmf requires 2 arguments\n");
  elseif (!is_domain (x))
    puts ("Type 'help sigmf' for more information.\n");
    error ("sigmf's first argument must be a valid domain\n");
  elseif (!are_mf_params ('sigmf', params))
    puts ("Type 'help sigmf' for more information.\n");
    error ("sigmf's second argument must be a parameter vector\n");
  endif

  ## Calculate and return the y values of the membership function on the
  ## domain x.

  a = params(1);
  c = params(2);

  y_val = @(x_val) 1 / (1 + exp (-a * (x_val - c)));
  y = arrayfun (y_val, x);

endfunction

%!demo
%! x = 0:100;
%! params = [0.3 40];
%! y1 = sigmf(x, params);
%! params = [0.2 40];
%! y2 = sigmf(x, params);
%! params = [0.1 40];
%! y3 = sigmf(x, params);
%! figure('NumberTitle', 'off', 'Name', 'sigmf demo');
%! plot(x, y1, 'r;params = [0.3 40];', 'LineWidth', 2)
%! hold on;
%! plot(x, y2, 'b;params = [0.2 40];', 'LineWidth', 2)
%! hold on;
%! plot(x, y3, 'g;params = [0.1 40];', 'LineWidth', 2)
%! ylim([-0.1 1.2]);
%! xlabel('Crisp Input Value', 'FontWeight', 'bold');
%! ylabel('Degree of Membership', 'FontWeight', 'bold');
%! grid;
