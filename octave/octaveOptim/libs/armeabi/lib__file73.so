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

## [a, b, ga, gb, nev] = semi_bracket (f, dx, a, narg, args)
##
## Find an interval containing a local minimum of the function 
## g : h in [a, inf[ ---> f (x+h*dx) where x = args{narg}
##
## The local minimum may be in a.
## a < b.
## nev is the number of function evaluations.

function [a,b,ga,gb,n] = __semi_bracket (f, dx, a, narg, args)

step = 1;

x = args{narg};
args{narg} =  x+a*dx; ga = feval (f, args );
b = a + step;
args{narg} =  x+b*dx; gb = feval (f, args );
n = 2;

if gb >= ga, return ; end

while 1,

  c = b + step;
  args{narg} = x+c*dx; gc = feval( f, args );
  n++;

  if gc >= gb,			# ga >= gb <= gc
    gb = gc; b = c;
    return;
  end
  step *= 2;
  a = b; b = c; ga = gb; gb = gc;
end
