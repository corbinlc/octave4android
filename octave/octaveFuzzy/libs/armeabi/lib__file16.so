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
## @deftypefn {Function File} {@var{y} =} evalmf (@var{x}, @var{param}, @var{mf_type})
## @deftypefnx {Function File} {@var{y} =} evalmf (@var{x}, @var{param}, @var{mf_type}, @var{hedge})
## @deftypefnx {Function File} {@var{y} =} evalmf (@var{x}, @var{param}, @var{mf_type}, @var{hedge}, @var{not_flag})
## @deftypefnx {Function File} {@var{y} =} evalmf (@var{[x1 x2 ... xn]}, @var{[param1 ... ]}, @var{mf_type})
## @deftypefnx {Function File} {@var{y} =} evalmf (@var{[x1 x2 ... xn]}, @var{[param1 ... ]}, @var{mf_type}, @var{hedge})
## @deftypefnx {Function File} {@var{y} =} evalmf (@var{[x1 x2 ... xn]}, @var{[param1 ... ]}, @var{mf_type}, @var{hedge}, @var{not_flag})
##
## For a given domain, set of parameters, membership function type, and
## optional hedge and not_flag, return the corresponding y-values for the
## membership function.
##
## The argument @var{x} must be a real number or a non-empty list of strictly
## increasing real numbers, @var{param} must be a valid parameter or a vector
## of valid parameters for @var{mf_type}, and @var{mf_type} must be a string
## corresponding to a membership function type. Evalmf handles both built-in and
## custom membership functions.
##
## For custom hedges and the four built-in hedges "somewhat", "very",
## "extremely", and "very very", raise the membership function values to
## the power corresponding to the hedge.
##
## @example
## @group
## (fraction == .05) <=>  somewhat x       <=>  mu(x)^0.5  <=>  sqrt(mu(x))
## (fraction == .20) <=>  very x           <=>  mu(x)^2    <=>  sqr(mu(x))
## (fraction == .30) <=>  extremely x      <=>  mu(x)^3    <=>  cube(mu(x))
## (fraction == .40) <=>  very very x      <=>  mu(x)^4
## (fraction == .dd) <=>  <custom hedge> x <=>  mu(x)^(dd/10)
## @end group
## @end example
##
## The @var{not_flag} negates the membership function using:
## @example
## mu(not(x)) = 1 - mu(x)
## @end example
##
## @noindent
## To run the demonstration code, type @t{demo('evalmf')} at the Octave prompt.
## @end deftypefn

## Author:        L. Markowsky
## Keywords:      fuzzy-logic-toolkit fuzzy membership evaluate
## Directory:     fuzzy-logic-toolkit/inst/
## Filename:      evalmf.m
## Last-Modified: 19 Aug 2012

function y = evalmf (x, params, mf_type, hedge = 0, not_flag = false)

  ## If the caller did not supply 3 - 5 argument values with the correct
  ## types, print an error message and halt.

  if ((nargin < 3) || (nargin > 5))
    puts ("Type 'help evalmf' for more information.\n");
    error ("evalmf requires between 3 and 5 arguments\n");
  elseif (!is_domain (x))
    puts ("Type 'help evalmf' for more information.\n");
    error ("evalmf's first argument must be a valid domain\n");
  elseif (!is_string (mf_type))
    puts ("Type 'help evalmf' for more information.\n");
    error ("evalmf's third argument must be a string\n");
  elseif (!is_real (hedge))
    puts ("Type 'help evalmf' for more information.\n");
    error ("evalmf's fourth argument must be a real number\n");
  elseif (!isbool (not_flag))
    puts ("Type 'help evalmf' for more information.\n");
    error ("evalmf's fifth argument must be a Boolean\n");
  endif

  ## Calculate and return the y values of the membership function on
  ## the domain x.

  y = evalmf_private (x, params, mf_type, hedge, not_flag);

endfunction

%!demo
%! x = 0:100;
%! params = [25 50 75];
%! mf_type = 'trimf';
%! y = evalmf(x, params, mf_type);
%! figure('NumberTitle', 'off', 'Name', "evalmf(0:100, [25 50 75], ...
%!        'trimf')");
%! plot(x, y, 'LineWidth', 2)
%! ylim([-0.1 1.1]);
%! xlabel('Crisp Input Value', 'FontWeight', 'bold');
%! ylabel('Degree of Membership', 'FontWeight', 'bold');
%! grid;
