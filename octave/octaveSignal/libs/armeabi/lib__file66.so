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

## y = idct2 (x)
##   Computes the inverse 2-D discrete cosine transform of matrix x
##
## y = idct2 (x, m, n) or y = idct2 (x, [m n])
##   Computes the 2-D inverse DCT of x after padding or trimming rows to m and
##   columns to n.

function y = idct2 (x, m, n)

  if (nargin < 1 || nargin > 3)
    print_usage;
  endif

  if nargin == 1
    [m, n] = size (x);
  elseif (nargin == 2)
    n = m (2);
    m = m (1);
  endif

  if m == 1
    y = idct (x.', n).';
  elseif n == 1
    y = idct (x, m);
  else
    y = idct (idct (x, m).', n).';
  endif

endfunction
