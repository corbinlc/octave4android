## Copyright (C) 2003 Alberto Terruzzi <t-albert@libero.it>
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
## @deftypefn {Function File} histfit (@var{data}, @var{nbins})
##
## Plot histogram with superimposed fitted normal density.
##
## @code{histfit (@var{data}, @var{nbins})} plots a histogram of the values in
## the vector @var{data} using @var{nbins} bars in the histogram. With one input
## argument, @var{nbins} is set  to the square root of the number of elements in
## data. 
##
## Example
##
## @example
## histfit (randn (100, 1))
## @end example
##
## @seealso{bar,hist, pareto}
## @end deftypefn

## Author: Alberto Terruzzi <t-albert@libero.it>
## Version: 1.0
## Created: 3 March 2004

function histfit (data,nbins)

  if nargin < 1 || nargin > 2
    print_usage;
  endif

  if isvector (data) != 1
    error ("data must be a vector.");
  endif

  row = sum(~isnan(data));

  if nargin < 2
    nbins = ceil(sqrt(row));
  endif

  [n,xbin]=hist(data,nbins);
  if any(abs(diff(xbin,2)) > 10*max(abs(xbin))*eps)
    error("histfit bins must be uniform width");
  endif

  mr = nanmean(data); ## Estimates the parameter, MU, of the normal distribution.
  sr = nanstd(data);  ## Estimates the parameter, SIGMA, of the normal distribution.
  x=(-3*sr+mr:0.1*sr:3*sr+mr)';## Evenly spaced samples of the expected data range.
  [xb,yb] = bar(xbin,n);
  y = normal_pdf(x,mr,sr.^2);
  binwidth = xbin(2)-xbin(1);
  y = row*y*binwidth;   ## Normalization necessary to overplot the histogram.
  plot(xb,yb,";;b",x,y,";;r-");     ## Plots density line over histogram.

endfunction

