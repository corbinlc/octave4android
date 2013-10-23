## Copyright (C) 2002 Alberto Terruzzi <t-albert@libero.it>
## Copyright (C) 2006 Alberto Pose <apose@alu.itba.edu.ar>
## Copyright (C) 2011 Pascal Dupuis <Pascal.Dupuis@worldonline.be>
## Copyright (C) 2012 Juan Pablo Carbajal <carbajal@ifi.uzh.ch>
##
## This program is free software; you can redistribute it and/or modify it under
## the terms of the GNU General Public License as published by the Free Software
## Foundation; either version 3 of the License, or (at your option) any later
## version.
##
## This program is distributed in the hope that it will be useful, but WITHOUT
## ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
## FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
## details.
##
## You should have received a copy of the GNU General Public License along with
## this program; if not, see <http://www.gnu.org/licenses/>.

## -*- texinfo -*-
## @deftypefn {Function File} {@var{s} =} boxplot (@var{data}, @var{notched}, @
## @var{symbol}, @var{vertical}, @var{maxwhisker}, @dots{})
## @deftypefnx {Function File} {[@dots{} @var{h}]=} boxplot (@dots{})
##
## Produce a box plot.
##
## The box plot is a graphical display that simultaneously describes several
## important features of a data set, such as center, spread, departure from
## symmetry, and identification of observations that lie unusually far from
## the bulk of the data.
##
## @var{data} is a matrix with one column for each data set, or data is a cell
## vector with one cell for each data set.
##
## @var{notched} = 1 produces a notched-box plot. Notches represent a robust
## estimate of the uncertainty about the median.
##
## @var{notched} = 0 (default) produces a rectangular box plot.
##
## @var{notched} in (0,1) produces a notch of the specified depth.
## notched values outside (0,1) are amusing if not exactly practical.
##
## @var{symbol} sets the symbol for the outlier values, default symbol for
## points that lie outside 3 times the interquartile range is 'o',
## default symbol for points between 1.5 and 3 times the interquartile
## range is '+'.
##
## @var{symbol} = '.' points between 1.5 and 3 times the IQR is marked with
## '.' and points outside 3 times IQR with 'o'.
##
## @var{symbol} = ['x','*'] points between 1.5 and 3 times the IQR is marked with
## 'x' and points outside 3 times IQR with '*'.
##
## @var{vertical} = 0 makes the boxes horizontal, by default @var{vertical} = 1.
##
## @var{maxwhisker} defines the length of the whiskers as a function of the IQR
## (default = 1.5). If @var{maxwhisker} = 0 then @code{boxplot} displays all data
## values outside the box using the plotting symbol for points that lie
## outside 3 times the IQR.
##
## Supplemental arguments are concatenated and passed to plot.
##
## The returned matrix @var{s} has one column for each data set as follows:
##
## @multitable @columnfractions .1 .8
## @item 1 @tab Minimum
## @item 2 @tab 1st quartile
## @item 3 @tab 2nd quartile (median)
## @item 4 @tab 3rd quartile
## @item 5 @tab Maximum
## @item 6 @tab Lower confidence limit for median
## @item 7 @tab Upper confidence limit for median
## @end multitable
##
## The returned structure @var{h} has hanldes to the plot elements, allowing
## customization of the visualization using set/get functions.
##
## Example
##
## @example
## title ("Grade 3 heights");
## axis ([0,3]);
## tics ("x", 1:2, @{"girls"; "boys"@});
## boxplot (@{randn(10,1)*5+140, randn(13,1)*8+135@});
## @end example
##
## @end deftypefn

## Author: Alberto Terruzzi <t-albert@libero.it>
## Version: 1.4
## Created: 6 January 2002

## Version: 1.4.1
## Author: Alberto Pose <apose@alu.itba.edu.ar>
## Updated: 3 September 2006
## - Replaced deprecated is_nan_or_na(X) with (isnan(X) | isna(X))
## (now works with this software 2.9.7 and foward)

## Version: 1.4.2
## Author: Pascal Dupuis <Pascal.Dupuis@worldonline.be>
## Updated: 14 October 2011
## - Added support for named arguments

## Version: 1.4.2
## Author: Juan Pablo Carbajal <carbajal@ifi.uzh.ch>
## Updated: 01 March 2012
## - Returns structure with handles to plot elements
## - Added example as demo

%# function s = boxplot (data,notched,symbol,vertical,maxwhisker)
function [s hs] = boxplot (data, varargin)

  ## assign parameter defaults
  if (nargin < 1)
    print_usage;
  endif

  %# default values
  maxwhisker = 1.5;
  vertical = 1;
  symbol = ['+', 'o'];
  notched = 0;
  plot_opts = {};

  %# Optional arguments analysis
  numarg = nargin - 1;
  option_args = ['Notch'; 'Symbol'; 'Vertical'; 'Maxwhisker'];
  indopt = 1;
  while (numarg)
    dummy = varargin{indopt++};
    if (!ischar (dummy))
      %# old way: positional argument
      switch indopt
        case 2
          notched = dummy;
        case 4
          vertical = dummy;
        case 5
          maxwhisker = dummy;
        otherwise
          error("No positional argument allowed at position %d", --indopt);
      endswitch
      numarg--; continue;
    else
      if (3 == indopt && length (dummy) <= 2)
        symbol = dummy;  numarg--; continue;
      else
        tt = strmatch(dummy, option_args);
        switch (tt)
          case 1
            notched = varargin{indopt};
          case 2
            symbol = varargin{indopt};
          case 3
            vertical = varargin{indopt};
          case 4
            maxwhisker = varargin{indopt};
          otherwise
            %# take two args and append them to plot_opts
            plot_opts(1, end+1:end+2) = {dummy,  varargin{indopt}};
        endswitch
      endif
      indopt++; numarg -= 2;
    endif
  endwhile

  if (1 == length (symbol)) symbol(2) = symbol(1); endif

  if (1 == notched) notched = 0.25; endif
  a = 1-notched;

  ## figure out how many data sets we have
  if (iscell (data))
    nc = length (data);
  else
    if (isvector (data)) data = data(:); endif
    nc = columns (data);
  endif

  ## compute statistics
  ## s will contain
  ##    1,5    min and max
  ##    2,3,4  1st, 2nd and 3rd quartile
  ##    6,7    lower and upper confidence intervals for median
  s = zeros (7,nc);
  box = zeros (1,nc);
  whisker_x = ones (2,1)*[1:nc,1:nc];
  whisker_y = zeros (2,2*nc);
  outliers_x = [];
  outliers_y = [];
  outliers2_x = [];
  outliers2_y = [];

  for indi = (1:nc)
    ## Get the next data set from the array or cell array
    if (iscell (data))
      col = data{indi}(:);
    else
      col = data(:, indi);
    endif
    ## Skip missing data
    col(isnan (col) | isna (col)) = [];
    ## Remember the data length
    nd = length (col);
    box(indi) = nd;
    if (nd > 1)
      ## min,max and quartiles
      s(1:5, indi) = statistics (col)(1:5);
      ## confidence interval for the median
      est = 1.57*(s(4, indi)-s(2, indi))/sqrt (nd);
      s(6, indi) = max ([s(3, indi)-est, s(2, indi)]);
      s(7, indi) = min ([s(3, indi)+est, s(4, indi)]);
      ## whiskers out to the last point within the desired inter-quartile range
      IQR = maxwhisker*(s(4, indi)-s(2, indi));
      whisker_y(:, indi) = [min(col(col >= s(2, indi)-IQR)); s(2, indi)];
      whisker_y(:,nc+indi) = [max(col(col <= s(4, indi)+IQR)); s(4, indi)];
      ## outliers beyond 1 and 2 inter-quartile ranges
      outliers = col((col < s(2, indi)-IQR & col >= s(2, indi)-2*IQR) | (col > s(4, indi)+IQR & col <= s(4, indi)+2*IQR));
      outliers2 = col(col < s(2, indi)-2*IQR | col > s(4, indi)+2*IQR);
      outliers_x = [outliers_x; indi*ones(size(outliers))];
      outliers_y = [outliers_y; outliers];
      outliers2_x = [outliers2_x; indi*ones(size(outliers2))];
      outliers2_y = [outliers2_y; outliers2];
    elseif (1 == nd)
      ## all statistics collapse to the value of the point
      s(:, indi) = col;
      ## single point data sets are plotted as outliers.
      outliers_x = [outliers_x; indi];
      outliers_y = [outliers_y; col];
    else
      ## no statistics if no points
      s(:, indi) = NaN;
    end
  end

  ## Note which boxes don't have enough stats
  chop = find (box <= 1);

  ## Draw a box around the quartiles, with width proportional to the number of
  ## items in the box. Draw notches if desired.
  box *= 0.4/max (box);
  quartile_x = ones (11,1)*[1:nc] + [-a;-1;-1;1;1;a;1;1;-1;-1;-a]*box;
  quartile_y = s([3,7,4,4,7,3,6,2,2,6,3],:);

  ## Draw a line through the median
  median_x = ones (2,1)*[1:nc] + [-a;+a]*box;
  median_y = s([3,3],:);

  ## Chop all boxes which don't have enough stats
  quartile_x(:, chop) = [];
  quartile_y(:, chop) = [];
  whisker_x(:,[chop, chop+nc]) = [];
  whisker_y(:,[chop, chop+nc]) = [];
  median_x(:, chop) = [];
  median_y(:, chop) = [];

  ## Add caps to the remaining whiskers
  cap_x = whisker_x;
  cap_x(1, :) -= 0.05;
  cap_x(2, :) += 0.05;
  cap_y = whisker_y([1, 1], :);

  #quartile_x,quartile_y
  #whisker_x,whisker_y
  #median_x,median_y
  #cap_x,cap_y

  ## Do the plot
  if (vertical)
    if (isempty (plot_opts))
     h = plot (quartile_x, quartile_y, "b;;",
            whisker_x, whisker_y, "b;;",
            cap_x, cap_y, "b;;",
            median_x, median_y, "r;;",
            outliers_x, outliers_y, [symbol(1), "r;;"],
            outliers2_x, outliers2_y, [symbol(2), "r;;"]);
    else
    h = plot (quartile_x, quartile_y, "b;;",
          whisker_x, whisker_y, "b;;",
          cap_x, cap_y, "b;;",
          median_x, median_y, "r;;",
            outliers_x, outliers_y, [symbol(1), "r;;"],
            outliers2_x, outliers2_y, [symbol(2), "r;;"], plot_opts{:});
    endif
  else
    if (isempty (plot_opts))
     h = plot (quartile_y, quartile_x, "b;;",
            whisker_y, whisker_x, "b;;",
            cap_y, cap_x, "b;;",
            median_y, median_x, "r;;",
            outliers_y, outliers_x, [symbol(1), "r;;"],
            outliers2_y, outliers2_x, [symbol(2), "r;;"]);
    else
    h = plot (quartile_y, quartile_x, "b;;",
          whisker_y, whisker_x, "b;;",
          cap_y, cap_x, "b;;",
          median_y, median_x, "r;;",
            outliers_y, outliers_x, [symbol(1), "r;;"],
            outliers2_y, outliers2_x, [symbol(2), "r;;"], plot_opts{:});
    endif
  endif

  % Distribute handles
  nq = 1:size(quartile_x,2);
  hs.box = h(nq);
  nw = nq(end) + [1:2*size(whisker_x,2)];
  hs.whisker = h(nw);
  nm = nw(end)+ [1:size(median_x,2)];
  hs.median = h(nm);

  if ~isempty (outliers_y)
    no = nm(end) + [1:size(outliers_y,2)];
    hs.outliers = h(no);
  end
  if ~isempty (outliers2_y)
    no2 = no(end) + [1:size(outliers2_y,2)];
    hs.outliers2 = h(no2);
  end

endfunction

%!demo
%! title ("Grade 3 heights");
%! axis ([0,3]);
%! tics ("x", 1:2, {"girls"; "boys"});
%! boxplot ({randn(10,1)*5+140, randn(13,1)*8+135});
