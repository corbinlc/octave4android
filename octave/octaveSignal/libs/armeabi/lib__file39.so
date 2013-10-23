## Copyright (C) 2001 Paul Kienzle <pkienzle@users.sf.net>
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

## T = dctmtx (n)
## Return the DCT transformation matrix of size n x n.
##
## If A is an n x n matrix, then the following are true:
##     T*A    == dct(A),  T'*A   == idct(A)
##     T*A*T' == dct2(A), T'*A*T == idct2(A)
##
## A dct transformation matrix is useful for doing things like jpeg
## image compression, in which an 8x8 dct matrix is applied to
## non-overlapping blocks throughout an image and only a subblock on the
## top left of each block is kept.  During restoration, the remainder of
## the block is filled with zeros and the inverse transform is applied
## to the block.
##
## See also: dct, idct, dct2, idct2

function T = dctmtx(n)
  if nargin != 1
    print_usage;
  endif

  if n > 1
    T = [ sqrt(1/n)*ones(1,n) ; \
	 sqrt(2/n)*cos((pi/2/n)*([1:n-1]'*[1:2:2*n])) ];
  elseif n == 1
    T = 1;
  else
    error ("dctmtx: n must be at least 1");
  endif

endfunction
