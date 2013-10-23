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

## b = polystab(a)
##
## Stabalize the polynomial transfer function by replacing all roots
## outside the unit circle with their reflection inside the unit circle.

function b = polystab(a)

   r = roots(a);
   v = find(abs(r)>1);
   r(v) = 1./conj(r(v));
   b = a(1) * poly ( r );
   if isreal(a), b = real(b); endif

endfunction
