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
## @deftypefn {Function File} {@var{crisp_x} =} defuzz (@var{x}, @var{y}, @var{defuzz_method})
## @deftypefnx {Function File} {@var{crisp_x} =} defuzz (@var{[x1 x2 ... xn]}, @var{[y1 y2 ... yn]}, @var{defuzz_method})
##
## For a given domain, set of fuzzy function values, and defuzzification method,
## return the defuzzified (crisp) value of the fuzzy function.
##
## The arguments @var{x} and @var{y} must be either two real numbers or
## two equal-length, non-empty vectors of reals, with the elements of @var{x}
## strictly increasing. @var{defuzz_method} must be a (case-sensitive) string
## corresponding to a defuzzification method. Defuzz handles both built-in
## and custom defuzzification methods. 
##
## The built-in defuzzification methods are:
## @table @samp
## @item centroid
## Return the x-value of the centroid.
## @item bisector
## Return the x-value of the vertical bisector of the area.
## @item mom
## Return the mean x-value of the points with maximum y-values.
## @item som
## Return the smallest (absolute) x-value of the points with maximum y-values.
## @item lom
## Return the largest (absolute) x-value of the points with maximum y-values.
## @item wtaver
## Return the weighted average of the x-values, with the y-values used as
## weights.
## @item wtsum
## Return the weighted sum of the x-values, with the y-values used as weights.
## @end table
## @end deftypefn

## Author:        L. Markowsky
## Keywords:      fuzzy-logic-toolkit fuzzy defuzzification
## Directory:     fuzzy-logic-toolkit/inst/
## Filename:      defuzz.m
## Last-Modified: 18 Aug 2012

##----------------------------------------------------------------------

function crisp_x = defuzz (x, y, defuzz_method)

  ## If the caller did not supply 3 argument values with the correct
  ## types, print an error message and halt.

  if (nargin != 3)
    puts ("Type 'help defuzz' for more information.\n");
    error ("defuzz requires 3 arguments\n");
  elseif (!is_domain (x))
    puts ("Type 'help defuzz' for more information.\n");
    error ("defuzz's first argument must be a valid domain\n");
  elseif (!(isvector (y) && isreal (y) && length (x) == length (y)))
    puts ("Type 'help defuzz' for more information.\n");
    error ("defuzz's 2nd argument must be a real number or vector\n");
  elseif (!is_string (defuzz_method))
    puts ("Type 'help defuzz' for more information.\n");
    error ("defuzz's third argument must be a string\n");
  endif

  ## Calculate and return the defuzzified (crisp_x) value using the
  ## method specified by the argument defuzz_method.

  crisp_x = str2func (defuzz_method) (x, y);

endfunction

##----------------------------------------------------------------------
## Usage: crisp_x = centroid (x, y)
##        crisp_x = centroid ([x1 x2 ... xn], [y1 y2 ... yn])
##
## For a given domain (x or [x1 x2 ... xn]) and corresponding y-values
## (y or [y1 y2 ... yn]), return the x-value of the centroid of the
## region described by the points (xi, yi).
##
## Both arguments are assumed to be reals or non-empty vectors of reals.
## In addition, x is assumed to be strictly increasing, and x and y are
## assumed to be of equal length.
##----------------------------------------------------------------------

function crisp_x = centroid (x, y)

  crisp_x = trapz (x, x.*y) / trapz (x, y);

endfunction

##----------------------------------------------------------------------
## Usage: crisp_x = bisector (x, y)
##        crisp_x = bisector ([x1 x2 ... xn], [y1 y2 ... yn])
##
## For a given domain (x or [x1 x2 ... xn]) and corresponding y-values
## (y or [y1 y2 ... yn]), return the x-value of a bisector of the region
## described by the points (xi, yi).
##
## Both arguments are assumed to be reals or non-empty vectors of reals.
## In addition, x is assumed to be strictly increasing, and x and y are
## assumed to be of equal length.
##----------------------------------------------------------------------

function crisp_x = bisector (x, y)

  ## Find the bisector using a binary search. To ensure that the
  ## function terminates, add a counter to limit the iterations to the
  ## length of the vectors x and y.

  half_area = trapz (x, y) / 2;
  x_len = length (x);
  upper = x_len;
  lower = 1;
  count = 1;

  while ((lower <= upper) && (count++ < x_len))
    midpoint = round ((lower + upper)/2);
    left_domain = [ones(1, midpoint), zeros(1, x_len-midpoint)];
    left_y_vals = left_domain .* y;
    left_area = trapz (x, left_y_vals);
    error = left_area - half_area;

    if (error > 0)
      upper = midpoint;
    else
      lower = midpoint;
    endif
  endwhile

  crisp_x = midpoint;

endfunction

##----------------------------------------------------------------------
## Usage: crisp_x = mom (x, y)
##        crisp_x = mom ([x1 x2 ... xn], [y1 y2 ... yn])
##
## For a given domain (x or [x1 x2 ... xn]) and corresponding y-values
## (y or [y1 y2 ... yn]), return the "Mean of Maximum"; that is, return
## the average of the x-values corresponding to the maximum y-value
## in y. 
##
## Both arguments are assumed to be reals or non-empty vectors of reals.
## In addition, x is assumed to be strictly increasing, and x and y are
## assumed to be of equal length.
##----------------------------------------------------------------------

function crisp_x = mom (x, y)

  max_y = max (y);
  y_val = @(y_val) if (y_val == max_y) 1 else 0 endif;
  max_y_locations = arrayfun (y_val, y);
  crisp_x = sum (x .* max_y_locations) / sum (max_y_locations);

endfunction

##----------------------------------------------------------------------
## Usage: crisp_x = som (x, y)
##        crisp_x = som ([x1 x2 ... xn], [y1 y2 ... yn])
##
## For a given domain (x or [x1 x2 ... xn]) and corresponding y-values
## (y or [y1 y2 ... yn]), return the "Smallest of Maximum"; that is,
## return the smallest x-value corresponding to the maximum y-value
## in y. 
##
## Both arguments are assumed to be reals or non-empty vectors of reals.
## In addition, x is assumed to be strictly increasing, and x and y are
## assumed to be of equal length.
##----------------------------------------------------------------------

function crisp_x = som (x, y)

  max_y = max (y);
  y_val = @(y_val) if (y_val == max_y) 1 else (NaN) endif;
  max_y_locations = arrayfun (y_val, y);
  crisp_x = min (x .* max_y_locations);

endfunction

##----------------------------------------------------------------------
## Usage: crisp_x = lom (x, y)
##        crisp_x = lom ([x1 x2 ... xn], [y1 y2 ... yn])
##
## For a given domain (x or [x1 x2 ... xn]) and corresponding y-values
## (y or [y1 y2 ... yn]), return the "Largest of Maximum"; that is,
## return the largest x-value corresponding to the maximum y-value in y.
##
## Both arguments are assumed to be reals or non-empty vectors of reals.
## In addition, x is assumed to be strictly increasing, and x and y are
## assumed to be of equal length.
##----------------------------------------------------------------------

function crisp_x = lom (x, y)

  max_y = max (y);
  y_val = @(y_val) if (y_val == max_y) 1 else (NaN) endif;
  max_y_locations = arrayfun (y_val, y);
  crisp_x = max (x .* max_y_locations);

endfunction

##----------------------------------------------------------------------
## Usage: retval = wtaver (values, weights)
##
## Return the weighted average of the values. The parameters are assumed
## to be equal-length vectors of real numbers.
##
## Examples:
##    wtaver ([1 2 3 4], [1 1 1 1])  ==> 2.5
##    wtaver ([1 2 3 4], [1 2 3 4])  ==> 3
##    wtaver ([1 2 3 4], [0 0 1 1])  ==> 3.5
##----------------------------------------------------------------------

function retval = wtaver (values, weights)

  retval = sum (weights .* values) / sum (weights);

endfunction

##----------------------------------------------------------------------
## Usage: retval = wtsum (values, weights)
##
## Return the weighted sum of the values. The parameters are assumed to
## be equal-length vectors of real numbers.
##
## Examples:
##    wtsum ([1 2 3 4], [1 1 1 1])  ==> 10
##    wtsum ([1 2 3 4], [1 2 3 4])  ==> 30
##    wtsum ([1 2 3 4], [0 0 1 1])  ==> 7
##----------------------------------------------------------------------

function retval = wtsum (values, weights)

  retval = sum (weights .* values);

endfunction
