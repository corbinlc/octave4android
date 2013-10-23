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
## @deftypefn {Function File} {@var{y} =} zmf (@var{x}, @var{params})
## @deftypefnx {Function File} {@var{y} =} zmf (@var{[x1 x2 ... xn]}, @var{[a b]})
##
## For a given domain @var{x} and parameters @var{params} (or @var{[a b]}),
## return the corresponding @var{y} values for the Z-shaped membership function.
##
## The argument @var{x} must be a real number or a non-empty vector of strictly
## increasing real numbers, and @var{a} and @var{b} must be real numbers, with
## @var{a} < @var{b}. This membership function satisfies:
## @example
## @group
##         1                                if x <= a
## f(x) =  1 - 2 * ((x - a)/(b - a))^2      if a < x <= (a + b)/2
##         2 * ((x - b)/(b - a))^2          if (a + b)/2 < x < b
##         0                                if x >= b
## @end group
## @end example
##
## @noindent
## which always returns values in the range [0, 1].
##
## The parameters a and b specify:
## @itemize @w
## @item
## a == the rightmost point at which f(x) = 1
## @item
## b == the leftmost point at which f(x) = 0
## @end itemize
##
## @noindent
## At the midpoint of the segment [a, b], the function value is 0.5:
## @itemize @w
## @item
## f((a + b)/2) = 0.5
## @end itemize
##
## @noindent
## To run the demonstration code, type @t{demo('zmf')} at the Octave prompt.
##
## @seealso{dsigmf, gauss2mf, gaussmf, gbellmf, pimf, psigmf, sigmf, smf, trapmf, trimf, zmf_demo}
## @end deftypefn

## Author:        L. Markowsky
## Keywords:      fuzzy-logic-toolkit fuzzy membership z-shaped
## Directory:     fuzzy-logic-toolkit/inst/
## Filename:      zmf.m
## Last-Modified: 19 Aug 2012

function y = zmf (x, params)

  ## If the caller did not supply 2 argument values with the correct
  ## types, print an error message and halt.

  if (nargin != 2)
    puts ("Type 'help zmf' for more informaion.\n");
    error ("zmf requires 2 arguments\n");
  elseif (!is_domain (x))
    puts ("Type 'help zmf' for more informaion.\n");
    error ("zmf's first argument must be a valid domain\n");
  elseif (!are_mf_params ('zmf', params))
    puts ("Type 'help zmf' for more informaion.\n");
    error ("zmf's second argument must be a parameter vector\n");
  endif

  ## Calculate and return the y values of the membership function on the
  ## domain x.

  a = params(1);
  b = params(2);
  a_b_ave = (a + b) / 2;
  b_minus_a = b - a;

  y_val = @(x_val) zmf_val (x_val, a, b, a_b_ave, b_minus_a);
  y = arrayfun (y_val, x);

endfunction

##----------------------------------------------------------------------
## Usage: y_val = zmf_val (x_val, a, b, a_b_ave, b_minus_a)
##
## zmf_val returns one value of the Z-shaped membership function, which
## satisfies:
##                1                                if x <= a
##        f(x) =  1 - 2 * ((x - a)/(b - a))^2      if a < x <= (a + b)/2
##                2 * ((x - b)/(b - a))^2          if (a + b)/2 < x < b
##                0                                if x >= b
##
## zmf_val is a private function, called only by zmf. Because zmf_val
## is not intended for general use -- and because the parameters a and b
## are checked for errors in the function zmf (defined above), the
## parameters are not checked for errors again here.
##----------------------------------------------------------------------

function y_val = zmf_val (x_val, a, b, a_b_ave, b_minus_a)

  ## Calculate and return a single y value of the Z-shaped membership
  ## function for the given x value and parameters specified by the
  ## arguments.

  if (x_val <= a)
    y_val = 1;
  elseif (x_val <= a_b_ave)
    y_val = 1 - 2 * ((x_val - a) / b_minus_a)^2;
  elseif (x_val < b)
    y_val = 2 * ((x_val - b) / b_minus_a)^2;
  else
    y_val = 0;
  endif

endfunction

%!demo
%! x = 0:100;
%! params = [40 60];
%! y1 = zmf(x, params);
%! params = [25 75];
%! y2 = zmf(x, params);
%! params = [10 90];
%! y3 = zmf(x, params);
%! figure('NumberTitle', 'off', 'Name', 'zmf demo');
%! plot(x, y1, 'r;params = [40 60];', 'LineWidth', 2)
%! hold on;
%! plot(x, y2, 'b;params = [25 75];', 'LineWidth', 2)
%! hold on;
%! plot(x, y3, 'g;params = [10 90];', 'LineWidth', 2)
%! ylim([-0.1 1.1]);
%! xlabel('Crisp Input Value', 'FontWeight', 'bold');
%! ylabel('Degree of Membership', 'FontWeight', 'bold');
%! grid;
