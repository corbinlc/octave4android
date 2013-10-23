## Copyright (C) 2002 Etienne Grossmann <etienne@egdn.net>
## Copyright (C) 2009 Levente Torok <TorokLev@gmail.com>
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

##  ex = poly_2_ex (l, f)       - Extremum of a 1-var deg-2 polynomial
##
## l  : 3 : variable values
## f  : 3 : f(i) = value of polynomial at l(i)
## 
## ex : 1 : Value at which f reaches an extremum
## 
## Assuming that f(i) = a*l(i)^2 + b* l(i) + c = P(l(i)) for some a, b, c,
## ex is the extremum of the polynome P.

function ex = __poly_2_extrema (l, f)

### This somewhat helps if solution is very close to one of the points.
[f,i] = sort (f);
l = l(i); 


m = (l(2) - l(1))/(l(3) - l(1));
d = (2*(f(1)*(m-1)+f(2)-f(3)*m));
if abs (d) < eps,
  printf ("poly_2_ex : divisor is small (solution at infinity)\n");
  printf ("%8.3e %8.3e %8.3e, %8.3e %8.3e\n",\
	  f(1), diff (f), diff (sort (l)));

  ex = (2*(l(1)>l(2))-1)*inf;
  ## keyboard
else
  ex  =  ((l(3) - l(1))*((f(1)*(m^2-1) + f(2) - f(3)*m^2))) / d ;

## Not an improvement
#  n = ((l(2)+l(3))*(l(2)-l(3)) + 2*(l(3)-l(2))*l(1)) / (l(3)-l(1))^2 ;
#  ex =  ((l(3) - l(1))*((f(1)*n + f(2) - f(3)*m^2))) / \
#      (2*(f(1)*(m-1)+f(2)-f(3)*m));
#  if ex != ex0,
#    ex -  ex0
#  end
  ex = l(1) + ex;
end
