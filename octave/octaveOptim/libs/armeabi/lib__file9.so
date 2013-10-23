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
## g : h in reals ---> f (x+h*dx) where x = args{narg}
##
## a < b.
## nev is the number of function evaluations

function [a, b, ga, gb, n] = __bracket_min (f, dx, narg, args)

    [a,b, ga,gb, n] = __semi_bracket (f, dx, 0, narg, args);

    if a != 0, return; end

    [a2,b2, ga2,gb2, n2] = __semi_bracket (f, -dx, 0, narg, args);

    n += n2;

    if a2 == 0,
        a = -b2; ga = gb2;
    else
    a = -b2;
    b = -a2;
    ga = gb2;
    gb = ga2;
end
