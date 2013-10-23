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
## @deftypefn {Function File} {} plotmf (@var{fis}, @var{in_or_out}, @var{var_index})
## @deftypefnx {Function File} {} plotmf (@var{fis}, @var{in_or_out}, @var{var_index}, @var{y_lower_limit})
## @deftypefnx {Function File} {} plotmf (@var{fis}, @var{in_or_out}, @var{var_index}, @var{y_lower_limit}, @var{y_upper_limit})
##
## Plot the membership functions defined for the specified FIS input or output
## variable on a single set of axes. Fuzzy output membership functions are
## represented by the [0, 1]-valued fuzzy functions, and constant output
## membership functions are represented by unit-valued singleton spikes.
## Linear output membership functions, however, are represented by
## two-dimensional lines y = ax + c, regardless of how many dimensions the
## linear function is defined to have. In effect, all of the other dimensions
## of the linear function are set to 0.
##
## If both constant and linear membership functions are used for a single FIS
## output, then two sets of axes are used: one for the constant membership
## functions, and another for the linear membership functions. To plot both
## constant and linear membership functions together, or to plot constant
## membership functions as horizontal lines instead of unit-valued spikes,
## represent the constant membership functions using 'linear' functions, with
## 0 for all except the last parameter, and with the desired constant value as
## the last parameter.
##
## The types of the arguments are expected to be:
## @itemize @bullet
## @item
## @var{fis} - an FIS structure
## @item
## @var{in_or_out} - either 'input' or 'output' (case-insensitive)
## @item
## @var{var_index} - an FIS input or output variable index
## @item
## @var{y_lower_limit} - a real scalar (default value = -0.1)
## @item
## @var{y_upper_limit} - a real scalar (default value = 1.1)
## @end itemize
##
## Six examples that use plotmf are:
## @itemize @bullet
## @item
## cubic_approx_demo.m
## @item
## heart_disease_demo_1.m
## @item
## heart_disease_demo_2.m
## @item
## investment_portfolio_demo.m
## @item
## linear_tip_demo.m
## @item
## mamdani_tip_demo.m
## @item
## sugeno_tip_demo.m
## @end itemize
##
## @seealso{gensurf}
## @end deftypefn

## Author:        L. Markowsky
## Keywords:      fuzzy-logic-toolkit fuzzy membership plot
## Directory:     fuzzy-logic-toolkit/inst/
## Filename:      plotmf.m
## Last-Modified: 19 Aug 2012

function plotmf (fis, in_or_out, var_index, ...
                 y_lower_limit = -0.1, y_upper_limit = 1.1)

  ## If the caller did not supply 3 argument values with the correct
  ## types, print an error message and halt.

  if ((nargin < 3) || (nargin > 5))
    puts ("Type 'help plotmf' for more information.\n");
    error ("plotmf requires 3 - 5 arguments\n");
  elseif (!is_fis (fis))
    puts ("Type 'help plotmf' for more information.\n");
    error ("plotmf's first argument must be an FIS structure\n");
  elseif (!(is_string (in_or_out) && ...
           ismember (tolower (in_or_out), {'input', 'output'})))
    puts ("Type 'help plotmf' for more information.\n");
    error ("plotmf's second argument must be 'input' or 'output'\n");
  elseif (!is_var_index (fis, in_or_out, var_index))
    puts ("Type 'help plotmf' for more information.\n");
    error ("plotmf's third argument must be a variable index\n");
  elseif (!(is_real (y_lower_limit) && is_real (y_upper_limit)))
    puts ("Type 'help plotmf' for more information.\n");
    error ("plotmf's 4th and 5th arguments must be real scalars\n");
  endif

  ## Select specified variable and construct the window title.

  if (strcmpi (in_or_out, 'input'))
    var = fis.input(var_index);
    window_title = [' Input ' num2str(var_index) ' Term Set'];
  else
    var = fis.output(var_index);
    window_title = [' Output ' num2str(var_index) ' Term Set'];
  endif

  ## Plot the membership functions for the specified variable.
  ## Cycle through the five colors: red, blue, green, magenta, cyan.
  ## Display the membership function names in a legend.

  colors = ["r" "b" "g" "m" "c"];
  x = linspace (var.range(1), var.range(2), 1001); 
  num_mfs = columns (var.mf);

  ## Define vectors to keep track of linear and non-linear mfs.

  linear_mfs = zeros (1, num_mfs);
  for i = 1 : num_mfs
    if (strcmp ('linear', var.mf(i).type))
      linear_mfs(i) = 1;
    endif
  endfor
  fuzzy_and_constant_mfs = 1 - linear_mfs;

  ## Plot the fuzzy or constant membership functions together on a set
  ## of axes.

  if (sum (fuzzy_and_constant_mfs))
    figure ('NumberTitle', 'off', 'Name', window_title);

    ## Plot the mfs.
    for i = 1 : num_mfs
      if (fuzzy_and_constant_mfs(i))
        y = evalmf_private (x, var.mf(i).params, var.mf(i).type);
        y_label = [colors(mod(i-1,5)+1) ";" var.mf(i).name ";"];
        plot (x, y, y_label, 'LineWidth', 2);
        hold on;
      endif
    endfor

    ## Adjust the y-axis, label both axes, and display a dotted grid.
    ylim ([y_lower_limit y_upper_limit]);
    xlabel (var.name, 'FontWeight', 'bold');
    ylabel ('Degree of Membership', 'FontWeight', 'bold');
    grid;
    hold;
  endif

  ## Plot the linear membership functions together on a separate set
  ## of axes.

  if (sum (linear_mfs))
    figure ('NumberTitle', 'off', 'Name', window_title);

    ## Plot the mfs.
    for i = 1 : num_mfs
      if (linear_mfs(i))
        y = evalmf_private (x, var.mf(i).params, var.mf(i).type);
        y_label = [colors(mod(i-1,5)+1) ";" var.mf(i).name ";"];
        plot (x, y, y_label, 'LineWidth', 2);
        hold on;
      endif
    endfor

    ## Adjust the y-axis, label both axes, and display a dotted grid.
    ylim ([y_lower_limit y_upper_limit]);
    xlabel ('X', 'FontWeight', 'bold');
    ylabel (var.name, 'FontWeight', 'bold');
    grid;
    hold;
  endif


endfunction
