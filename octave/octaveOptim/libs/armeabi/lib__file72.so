## Copyright (C) 2004 Michael Creel <michael.creel@uab.es>
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

# samin_example: example script that contains examples of how to call
# samin for minimization using simulated annealing.
# Edit the script to see how samin may be used.
#
# usage: samin_example

1; # this is a script file

# Example objective function
# remember that cos(0)=1, so
# "a" has a local minimum at 0 (each dimension)
# "b" makes the function value 0 at min
# "c" adds some curvature to make the min
# 	at (0,0,...,0) global.
# the closer is "curvature" to zero the more alike are
# the local mins, so the harder the global min is to find

function f = obj(theta, curvature);
	dim = rows(theta);
	a = sum(exp(-cos(theta)));
	b =  - dim*exp(-1);
	c = sum(curvature*theta .^ 2);
	f = a + b + c;
endfunction

k = 5; # dimensionality
theta = rand(k,1)*10 - 5; # random start value

# if you set "curvature" very small,
# you will need to increase nt, ns, and rt
# to minimize sucessfully
curvature = 0.01;


# SA controls
ub = 10*ones(rows(theta),1);
lb = -ub;
# setting ub and lb to same value restricts that parameter, and the algorithm does not search
ub(1,:) = 0;
lb(1,:) = 0;
theta(1,:) = 0; # must satisfy restriction

nt = 20;
ns = 5;
rt = 0.5; # careful - this is too low for many problems
maxevals = 1e10;
neps = 5;
functol = 1e-10;
paramtol = 1e-3;
verbosity = 1; # only final results. Inc
minarg = 1;
control = { lb, ub, nt, ns, rt, maxevals, neps, functol, paramtol, verbosity, 1};


# do sa
t=cputime();
[theta, obj_value, convergence] = samin("obj", {theta, curvature}, control);
t = cputime() - t;
printf("Elapsed time = %f\n\n\n",t);

