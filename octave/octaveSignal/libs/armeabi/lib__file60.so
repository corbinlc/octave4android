## Copyright (C) 1999 Paul Kienzle <pkienzle@users.sf.net>
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

## usage: w = gaussian(n, a)
##
## Generate an n-point gaussian convolution window of the given
## width.  Use larger a for a narrower window.  Use larger n for
## longer tails.
##
##     w = exp ( -(a*x)^2/2 )
##
## for x = linspace ( -(n-1)/2, (n-1)/2, n ).
##
## Width a is measured in frequency units (sample rate/num samples). 
## It should be f when multiplying in the time domain, but 1/f when 
## multiplying in the frequency domain (for use in convolutions).

function x = gaussian(n, w)

  if nargin < 1 || nargin > 2
    print_usage;
  end
  if nargin == 1, w = 1; endif
  x = exp(-0.5*(([0:n-1]'-(n-1)/2)*w).^2);

endfunction
