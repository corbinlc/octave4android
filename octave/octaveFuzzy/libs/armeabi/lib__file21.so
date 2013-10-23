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
## @deftypefn {Function File} {} gensurf (@var{fis})
## @deftypefnx {Function File} {} gensurf (@var{fis}, @var{input_axes})
## @deftypefnx {Function File} {} gensurf (@var{fis}, @var{input_axes}, @var{output_axes})
## @deftypefnx {Function File} {} gensurf (@var{fis}, @var{input_axes}, @var{output_axes}, @var{grids})
## @deftypefnx {Function File} {} gensurf (@var{fis}, @var{input_axes}, @var{output_axes}, @var{grids}, @var{ref_input})
## @deftypefnx {Function File} {} gensurf (@var{fis}, @var{input_axes}, @var{output_axes}, @var{grids}, @var{ref_input}, @var{num_points})
## @deftypefnx {Function File} {@var{[x, y, z]} =} gensurf (...)
##
## Generate and plot a surface (or 2-dimensional curve) showing one FIS output
## as a function of two (or one) of the FIS inputs. The reference input is used
## for all FIS inputs that are not in the input_axes vector.
##
## Grids, which specifies the number of grids to show on the input axes, may be
## a scalar or a vector of length 2. If a scalar, then both axes will use the
## same number of grids. If a vector of length 2, then the grids on the two axes
## are controlled separately.
##
## Num_points specifies the number of points to use when evaluating the FIS.
##
## The final form "[x, y, z] = gensurf(...)" suppresses plotting.
##
## Default values for arguments not supplied are:
## @itemize @bullet
## @item
## input_axes == [1 2]
## @item
## output_axis == 1
## @item
## grids == [15 15]
## @item
## ref_input == []
## @item
## num_points == 101
## @end itemize
##
## Six demo scripts that use gensurf are:
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
## Current limitation:
## The form of gensurf that suppresses plotting (the final form above) is not yet
## implemented.
##
## @seealso{cubic_approx_demo, heart_disease_demo_1, heart_disease_demo_2, investment_portfolio_demo, linear_tip_demo, mamdani_tip_demo, sugeno_tip_demo, plotmf}
## @end deftypefn

## Author:        L. Markowsky
## Keywords:      fuzzy-logic-toolkit fuzzy inference system fis plot
## Directory:     fuzzy-logic-toolkit/inst/
## Filename:      gensurf.m
## Last-Modified: 19 Aug 2012

function [x, y, z] = gensurf (fis, input_axes = [1 2], ...
                              output_axis = 1, grids = [15 15], ...
                              ref_input = [], num_points = 101)

  ## If gensurf was called with an incorrect number of arguments,
  ## or the arguments do not have the correct type, print an error
  ## message and halt.

  if ((nargin < 1) || (nargin > 6))
    puts ("Type 'help gensurf' for more information.\n");
    error ("gensurf requires between 1 and 6 arguments\n");
  elseif (!is_fis (fis))
    puts ("Type 'help gensurf' for more information.\n");
    error ("gensurf's first argument must be an FIS structure\n");
  elseif ((nargin >= 2) && !are_input_indices (input_axes, fis))
    puts ("Type 'help gensurf' for more information.\n");
    error ("gensurf's second argument must be valid input indices\n");
  elseif ((nargin >= 3) && !is_output_index (output_axis, fis))
    puts ("Type 'help gensurf' for more information.\n");
    error ("gensurf's third argument must be a valid output index\n");
  elseif ((nargin >= 4) && !is_grid_spec (grids))
    puts ("Type 'help gensurf' for more information.\n");
    error ("gensurf's 4th argument must be a grid specification\n");
  elseif ((nargin >= 5) && !is_ref_input (ref_input, fis, input_axes))
    puts ("Type 'help gensurf' for more information.\n");
    error ("gensurf's 5th argument must be reference input values\n");
  elseif ((nargin == 6) && ...
          !(is_pos_int (num_points) && (num_points >= 2)))
    puts ("Type 'help gensurf' for more information.\n");
    error ("gensurf's sixth argument must be an integer >= 2\n");
  endif

  if (length (input_axes) == 1 || columns (fis.input) == 1)
    generate_plot (fis, input_axes, output_axis, grids, ...
                   ref_input, num_points);
  else
    generate_surface (fis, input_axes, output_axis, grids, ...
                      ref_input, num_points);
  endif

endfunction

##----------------------------------------------------------------------
## Function: generate_plot
## Purpose:  Generate a plot representing one of the FIS outputs as a
##           function of one of the FIS inputs.
##----------------------------------------------------------------------

function [x, y, z] = generate_plot (fis, input_axis, output_axis, ...
                                    grids, ref_input, num_points)

  ## Create input to FIS using grid points and reference values.

  num_inputs = columns (fis.input);
  num_grid_pts = grids(1);
  fis_input = zeros (num_grid_pts, num_inputs);

  if (num_inputs == 1)
    input_axis = 1;
  endif

  for i = 1 : num_inputs
    if (i == input_axis)
      x_axis = (linspace (fis.input(i).range(1), ...
                          fis.input(i).range(2), ...
                          num_grid_pts))';
      fis_input(:, i) = x_axis;
    else
      fis_input(:, i) = ref_input(i) * ones (num_grid_pts, 1);
    endif
  endfor

  ## Compute and plot the output.

  output = evalfis_private (fis_input, fis, num_points);
  figure ('NumberTitle', 'off', 'Name', fis.name);
  plot (x_axis, output, 'LineWidth', 2);
  xlabel (fis.input(input_axis).name, 'FontWeight', 'bold');
  ylabel (fis.output(output_axis).name, 'FontWeight', 'bold');
  grid;
  hold;

endfunction

##----------------------------------------------------------------------
## Function: generate_surface
## Purpose:  Generate a surface representing one of the FIS outputs as
##           a function of two of the FIS inputs.
##----------------------------------------------------------------------

function [x, y, z] = generate_surface (fis, input_axes, output_axis, ...
                                       grids, ref_input, num_points)

  ## Create input to FIS using grid points and reference values.

  num_inputs = columns (fis.input);
  if (length (grids) == 1)
    grids = [grids grids];
  endif
  num_grid_pts = prod (grids);
  fis_input = zeros (num_grid_pts, num_inputs);

  for i = 1 : num_inputs
    if (i == input_axes(1))
      x_axis = (linspace (fis.input(i).range(1), ...
                          fis.input(i).range(2), ...
                          grids(1)))';
    elseif (i == input_axes(2))
      y_axis = (linspace (fis.input(i).range(1), ...
                          fis.input(i).range(2), ...
                          grids(2)))';
    else
      fis_input(:, i) = ref_input(i) * ones (num_grid_pts, 1);
    endif
  endfor

  [xx, yy] = meshgrid (x_axis, y_axis);

  fis_input(:, input_axes(1)) = xx(:);
  fis_input(:, input_axes(2)) = yy(:);

  ## Compute the output and reshape it to fit the grid.

  output = evalfis_private (fis_input, fis, num_points);
  z_matrix = reshape (output(:, output_axis), length (x_axis), ...
                      length (y_axis));

  ## Plot the surface.

  figure ('NumberTitle', 'off', 'Name', fis.name);
  surf (x_axis, y_axis, z_matrix);
  xlabel (fis.input(input_axes(1)).name);
  ylabel (fis.input(input_axes(2)).name);
  zlabel (fis.output(output_axis).name);

endfunction
