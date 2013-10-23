## Copyright (C) 2003 Julius O. Smith III <jos@ccrma.stanford.edu>
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

## Usage: H = freqs(B,A,W);
##
## Compute the s-plane frequency response of the IIR filter B(s)/A(s) as 
## H = polyval(B,j*W)./polyval(A,j*W).  If called with no output
## argument, a plot of magnitude and phase are displayed.
##
## Example:
##    B = [1 2]; A = [1 1];
##    w = linspace(0,4,128);
##    freqs(B,A,w);

function [H] = freqs(B,A,W)

  if (nargin ~= 3 || nargout>1)
    print_usage;
  end

  H = polyval(B,j*W)./polyval(A,j*W);

  if nargout<1
    freqs_plot(W,H);
  end

endfunction

%!demo
%! B = [1 2];
%! A = [1 1];
%! w = linspace(0,4,128);
%! freqs(B,A,w);
