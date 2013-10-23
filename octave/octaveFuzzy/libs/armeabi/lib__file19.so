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
## @deftypefn {Function File} {@var{y} =} gaussmf (@var{x}, @var{params})
## @deftypefnx {Function File} {@var{y} =} gaussmf (@var{[x1 x2 ... xn]}, @var{[sig c]})
##
## For a given domain @var{x} and parameters @var{params} (or @var{[sig c]}),
## return the corresponding @var{y} values for the Gaussian membership
## function. This membership function is shaped like the Gaussian (normal)
## distribution, but scaled to have a maximum value of 1. By contrast, the 
## area under the Gaussian distribution curve is 1.
##
## The argument @var{x} must be a real number or a non-empty vector of strictly
## increasing real numbers, and @var{sig} and @var{c} must be real numbers.
## This membership function satisfies the equation:
## @example
## f(x) = exp((-(x - c)^2)/(2 * sig^2))
## @end example
##
## @noindent
## which always returns values in the range [0, 1].
##
## Just as for the Gaussian (normal) distribution, the parameters @var{sig} and
## @var{c} represent:
## @itemize @w
## @item
## sig^2 == the variance (a measure of the width of the curve)
## @item
## c == the center (the mean; the x value of the peak)
## @end itemize
##
## @noindent
## For larger values of @var{sig}, the curve is flatter, and for smaller values
## of sig, the curve is narrower. The @var{y} value at the center is always 1:
## @itemize @w
## @item
## f(c) == 1
## @end itemize
##
## @noindent
## To run the demonstration code, type @t{demo('gaussmf')} at the Octave prompt.
##
## @seealso{dsigmf, gauss2mf, gbellmf, pimf, psigmf, sigmf, smf, trapmf, trimf, zmf}
## @end deftypefn

## Author:        L. Markowsky
## Keywords:      fuzzy-logic-toolkit fuzzy membership gaussian
## Directory:     fuzzy-logic-toolkit/inst/
## Filename:      gaussmf.m
## Last-Modified: 19 Aug 2012

function y = gaussmf (x, params)

  ## If the caller did not supply 2 argument values with the correct
  ## types, print an error message and halt.

  if (nargin != 2)
    puts ("Type 'help gaussmf' for more information.\n");
    error ("gaussmf requires 2 arguments\n");
  elseif (!is_domain (x))
    puts ("Type 'help gaussmf' for more information.\n");
    error ("gaussmf's first argument must be a valid domain\n");
  elseif (!are_mf_params ('gaussmf', params))
    puts ("Type 'help gaussmf' for more information.\n");
    error ("gaussmf's second argument must be a parameter vector\n");
  endif

  ## Calculate and return the y values of the membership function on the
  ## domain x.

  sig = params(1);
  c = params(2);

  y_val = @(x_val) exp ((-(x_val - c)^2)/(2 * sig^2));
  y = arrayfun (y_val, x);

endfunction

%!demo
%! x = -5:0.1:5;
%! params = [0.5 0];
%! y1 = gaussmf(x, params);
%! params = [1 0];
%! y2 = gaussmf(x, params);
%! params = [2 0];
%! y3 = gaussmf(x, params);
%! figure('NumberTitle', 'off', 'Name', 'gaussmf demo');
%! plot(x, y1, 'r;params = [0.5 0];', 'LineWidth', 2);
%! hold on ;
%! plot(x, y2, 'b;params = [1 0];', 'LineWidth', 2);
%! hold on ;
%! plot(x, y3, 'g;params = [2 0];', 'LineWidth', 2);
%! ylim([-0.1 1.1]);
%! xlabel('Crisp Input Value');
%! ylabel('Degree of Membership');
%! grid;
%! hold;
