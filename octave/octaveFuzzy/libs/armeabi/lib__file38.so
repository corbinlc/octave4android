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
## @deftypefn {Function File} {@var{y} =} pimf (@var{x}, @var{params})
## @deftypefnx {Function File} {@var{y} =} pimf (@var{[x1 x2 ... xn]}, @var{[a b c d]})
##
## For a given domain @var{x} and parameters @var{params} (or @var{[a b c d]}),
## return the corresponding @var{y} values for the pi-shaped membership
## function.
##
## The argument @var{x} must be a real number or a non-empty vector of real
## numbers, and @var{a}, @var{b}, @var{c}, and @var{d} must be real numbers,
## with @var{a} < @var{b} <= @var{c} < @var{d}. This membership function
## satisfies:
## @example
## @group
##         0                             if x <= a
##         2 * ((x - a)/(b - a))^2       if a < x <= (a + b)/2
##         1 - 2 * ((x - b)/(b - a))^2   if (a + b)/2 < x < b
## f(x) =  1                             if b <= x <= c
##         1 - 2 * ((x - c)/(d - c))^2   if c < x <= (c + d)/2
##         2 * ((x - d)/(d - c))^2       if (c + d)/2 < x < d
##         0                             if x >= d
## @end group
## @end example
##
## @noindent
## which always returns values in the range [0, 1].
##
## @noindent
## To run the demonstration code, type @t{demo('pimf')} at the Octave prompt.
##
## @seealso{dsigmf, gauss2mf, gaussmf, gbellmf, psigmf, sigmf, smf, trapmf, trimf, zmf}
## @end deftypefn

## Author:        L. Markowsky
## Keywords:      fuzzy-logic-toolkit fuzzy membership pi-shaped pi
## Directory:     fuzzy-logic-toolkit/inst/
## Filename:      pimf.m
## Last-Modified: 19 August 2012

function y = pimf (x, params)

  ## If the caller did not supply 2 argument values with the correct
  ## types, print an error message and halt.

  if ((nargin != 2))
    puts ("Type 'help pimf' for more information.\n");
    error ("pimf requires 2 arguments\n");
  elseif (!is_domain (x))
    puts ("Type 'help pimf' for more information.\n");
    error ("pimf's first argument must be a valid domain\n");
  elseif (!are_mf_params ('pimf', params))
    puts ("Type 'help pimf' for more information.\n");
    error ("pimf's second argument must be a parameter vector\n");
  endif

  ## Calculate and return the y values of the membership function on the
  ## domain x.

  a = params(1);
  b = params(2);
  c = params(3);
  d = params(4);

  a_b_ave = (a + b) / 2;
  b_minus_a = b - a;
  c_d_ave = (c + d) / 2;
  d_minus_c = d - c;

  y_val = @(x_val) pimf_val (x_val, a, b, c, d, a_b_ave, b_minus_a, ...
                             c_d_ave, d_minus_c);
  y = arrayfun (y_val, x);

endfunction

##----------------------------------------------------------------------
## Usage: y = pimf_val (x_val, a, b, c, d, a_b_ave, b_minus_a, c_d_ave,
##                      d_minus_c)
##
## pimf_val returns one value of the S-shaped membership function, which
## satisfies:
##             0                                if x <= a
##             2 * ((x - a)/(b - a))^2          if a < x <= (a + b)/2
##             1 - 2 * ((x - b)/(b - a))^2      if (a + b)/2 < x < b
##     f(x) =  1                                if b <= x <= c
##             1 - 2 * ((x - c)/(d - c))^2      if c < x <= (c + d)/2
##             2 * ((x - d)/(d - c))^2          if (c + d)/2 < x < d
##             0                                if x >= d
##
## pimf_val is a private function, called only by pimf. Because pimf_val
## is not intended for general use -- and because the parameters a, b,
## c, and d are checked for errors in the function pimf (defined above),
## the parameters are not checked for errors again here.
##----------------------------------------------------------------------

function y_val = pimf_val (x_val, a, b, c, d, a_b_ave, b_minus_a, ...
                           c_d_ave, d_minus_c)

  ## Calculate and return a single y value of the pi-shaped membership
  ## function for the given x value and parameters specified by the
  ## arguments.

  if (x_val <= a)
    y_val = 0;
  elseif (x_val <= a_b_ave)
    y_val = 2 * ((x_val - a)/b_minus_a)^2;
  elseif (x_val < b)
    y_val = 1 - 2 * ((x_val - b) / b_minus_a)^2;
  elseif (x_val <= c)
    y_val = 1;
  elseif (x_val <= c_d_ave)
    y_val = 1 - 2 * ((x_val - c) / d_minus_c)^2;
  elseif (x_val < d)
    y_val = 2 * ((x_val - d) / d_minus_c)^2;
  else
    y_val = 0;
  endif

endfunction

%!demo
%! x = 0:255;
%! params = [70 80 100 140];
%! y1 = pimf(x, params);
%! params = [50 75 105 175];
%! y2 = pimf(x, params);
%! params = [30 70 110 200];
%! y3 = pimf(x, params);
%! figure('NumberTitle', 'off', 'Name', 'pimf demo');
%! plot(x, y1, 'r;params = [70 80 100 140];', 'LineWidth', 2)
%! hold on;
%! plot(x, y2, 'b;params = [50 75 105 175];', 'LineWidth', 2)
%! hold on;
%! plot(x, y3, 'g;params = [30 70 110 200];', 'LineWidth', 2)
%! ylim([-0.1 1.1]);
%! xlabel('Crisp Input Value', 'FontWeight', 'bold');
%! ylabel('Degree of Membership', 'FontWeight', 'bold');
%! grid;
