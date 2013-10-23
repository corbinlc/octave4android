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
## @deftypefn {Function File} {@var{y} =} evalmf_private (@var{x}, @var{param}, @var{mf_type})
## @deftypefnx {Function File} {@var{y} =} evalmf_private (@var{x}, @var{param}, @var{mf_type}, @var{hedge})
## @deftypefnx {Function File} {@var{y} =} evalmf_private (@var{x}, @var{param}, @var{mf_type}, @var{hedge}, @var{not_flag})
## @deftypefnx {Function File} {@var{y} =} evalmf_private (@var{[x1 x2 ... xn]}, @var{[param1 ... ]}, '<@var{mf_type}>')
## @deftypefnx {Function File} {@var{y} =} evalmf_private (@var{[x1 x2 ... xn]}, @var{[param1 ... ]}, '<@var{mf_type}>', @var{hedge})
## @deftypefnx {Function File} {@var{y} =} evalmf_private (@var{[x1 x2 ... xn]}, @var{[param1 ... ]}, '<@var{mf_type}>', @var{hedge}, @var{not_flag})
##
## This function localizes the membership function evaluation without the
## parameter tests. It is called by evalmf and plotmf. For more information,
## see the comment at the top of evalmf.m.
##
## @end deftypefn

## Author:        L. Markowsky
## Keywords:      fuzzy-logic-toolkit fuzzy membership-function evaluate
## Directory:     fuzzy-logic-toolkit/inst/private/
## Filename:      evalmf_private.m
## Last-Modified: 3 Sep 2012

function y = evalmf_private (x, params, mf_type, hedge = 0, ...
                             not_flag = false)

  ## Calculate and return the y values of the membership function on
  ## the domain x. First, get the value of the membership function
  ## without correcting for the hedge and not_flag. Then, for non-linear
  ## functions, adjust the function values for non-zero hedge and
  ## not_flag.

  switch (mf_type)
    case 'constant'
      y = eval_constant (x, params);
      if (not_flag)
        y = 1 - y;
      endif

    case 'linear'
      y = eval_linear (x, params);

    otherwise
      y = str2func (mf_type) (x, params);
      if (hedge != 0)
        y = y .^ hedge;
      endif
      if (not_flag)
        y = 1 - y;
      endif
  endswitch

endfunction

##----------------------------------------------------------------------
## Function: eval_constant
## Purpose:  Return the y-values corresponding to the x-values in
##           the domain for the constant function specified by the
##           parameter c.
##----------------------------------------------------------------------

function y = eval_constant (x, c)
  y = zeros (length (x));
  delta = x(2) - x(1);
  y_val = @(x_val) ((abs (c - x_val) < delta) * 1);
  y = arrayfun (y_val, x);
endfunction

##----------------------------------------------------------------------
## Function: eval_linear
## Purpose:  For the parameters [a ... c]), return the y-values
##           corresponding to the linear function y = a*x + c, where x
##           takes on the the x-values in the domain. The remaining
##           coefficients in the parameter list are not used -- this
##           creates a two-dimensional intersection of the linear output
##           membership function suitable for display together with
##           other membership functions, but does not fully represent
##           the output membership function.
##----------------------------------------------------------------------

function y = eval_linear (x, params)
  if (length (params) == 1)
    a = 0;
    c = params;
  else
    a = params(1);
    c = params(length (params));
  endif

  y = zeros (length (x));
  y_val = @(x_val) (a * x_val + c);
  y = arrayfun (y_val, x);
endfunction
