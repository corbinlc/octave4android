## Copyright (C) 2004,2005,2006 Michael Creel <michael.creel@uab.es>
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

# usage: bfgsmin_example (to run) or edit bfgsmin_example (to examine)
##
# Shows how to call bfgsmin. There are two objective functions, the first
# supplies the analytic gradient, and the second does not. The true minimizer
# is at "location", a 50x1 vector (0.00, 0.02, 0.04 ..., 1.00).
# Note that limited memory bfgs is faster when the dimension is high.
# Also note that supplying analytic derivatives gives a speedup.
##
# Six examples are given:
# Example 1: regular bfgs, analytic gradient
# Example 2: same as Example 1, but verbose
# Example 3: limited memory bfgs, analytic gradient
# Example 4: regular bfgs, numeric gradient
# Example 5: limited memory bfgs, numeric gradient
# Example 6: regular bfgs, analytic gradient, minimize wrt second argument
1;
# example obj. fn.: supplies analytic gradient
function [obj_value, gradient] = objective(theta, location)
	x = theta - location + ones(rows(theta),1); # move minimizer to "location"
	[obj_value, gradient] = rosenbrock(x);
endfunction

# example obj. fn.: no gradient
function obj_value = objective2(theta, location)
	x = theta - location + ones(rows(theta),1); # move minimizer to "location"
	obj_value = rosenbrock(x);
endfunction

# initial values
dim = 20; # dimension of Rosenbrock function
theta0 = zeros(dim+1,1);  # starting values
location = (0:dim)/dim; # true values
location = location';

printf("EXAMPLE 1: Ordinary BFGS, using analytic gradient\n");
t=cputime();
control = {Inf,1};  # maxiters, verbosity
[theta, obj_value, convergence] = bfgsmin("objective", {theta0, location}, control);
fflush(1);
t1 = cputime() - t;
conv = norm(theta-location, 'inf');
test1 = conv < 1e-5;

printf("EXAMPLE 2: Same as Example 1, but verbose\n");
t=cputime();
control = {-1;3};  # maxiters, verbosity
[theta, obj_value, convergence] = bfgsmin("objective", {theta0, location}, control);
fflush(1);
t2 = cputime() - t;
conv = norm(theta-location, 'inf');
test2 = conv < 1e-5;

printf("EXAMPLE 3: Limited memory BFGS, using analytic gradient\n");
t=cputime();
control = {-1;1;1;1;3};  # maxiters, verbosity, conv. requirement., arg_to_min, lbfgs memory
[theta, obj_value, convergence] = bfgsmin("objective", {theta0, location}, control);
fflush(1);
t3 = cputime() - t;
conv = norm(theta-location, 'inf');
test3 = conv < 1e-5;

printf("EXAMPLE 4: Ordinary BFGS, using numeric gradient\n");
t=cputime();
control = {-1;1};  # maxiters, verbosity
[theta, obj_value, convergence] = bfgsmin("objective2", {theta0, location}, control);
fflush(1);
t4 = cputime() - t;
conv = norm(theta-location, 'inf');
test4 = conv < 1e-5;

printf("EXAMPLE 5: Limited memory BFGS, using numeric gradient\n");
t=cputime();
control = {-1;1;1;1;3};  # maxiters, verbosity, conv. reg., arg_to_min, lbfgs memory
[theta, obj_value, convergence] = bfgsmin("objective2", {theta0, location}, control);
fflush(1);
t5 = cputime() - t;
conv = norm(theta-location, 'inf');
test5 = conv < 1e-5;


printf("EXAMPLE 6: Funny case: minimize w.r.t. second argument, Ordinary BFGS, using numeric gradient\n");
t=cputime();
control = {-1;1;1;2};  # maxiters, verbosity, conv. reg., arg_to_min
[theta, obj_value, convergence] = bfgsmin("objective2", {location, theta0}, control);
fflush(1);
t6 = cputime() - t;
conv = norm(theta-location, 'inf');
test6 = conv < 1e-5;

printf("EXAMPLE 7: Ordinary BFGS, using numeric gradient, using non-default tolerances\n");
t=cputime();
control = {-1;1;1;1;0;1e-6;1e-2;1e-2};  # maxiters, verbosity, conv. reg., arg_to_min, lbfgs memory, fun. tol., param. tol., gradient tol.
[theta, obj_value, convergence] = bfgsmin("objective2", {theta0, location}, control);
fflush(1);
t7 = cputime() - t;
conv = norm(theta-location, 'inf');
test7 = conv < 1e-2;


printf("EXAMPLE 1: Ordinary BFGS, using analytic gradient\n");
if test1
	printf("Success!! :-)\n");
else
	printf("Failure?! :-(\n");
endif
printf("Elapsed time = %f\n\n\n\n",t1);

printf("EXAMPLE 2: Same as Example 1, but verbose\n");
if test2
	printf("Success!! :-)\n");
else
	printf("Failure?! :-(\n");
endif
printf("Elapsed time = %f\n\n\n\n",t2);

printf("EXAMPLE 3: Limited memory BFGS, using analytic gradient\n");
if test3
	printf("Success!! :-)\n");
else
	printf("Failure?! :-(\n");
endif
printf("Elapsed time = %f\n\n\n\n",t3);

printf("EXAMPLE 4: Ordinary BFGS, using numeric gradient\n");
if test4
	printf("Success!! :-)\n");
else
	printf("Failure?! :-(\n");
endif
printf("Elapsed time = %f\n\n\n\n",t4);

printf("EXAMPLE 5: Limited memory BFGS, using numeric gradient\n");
if test5
	printf("Success!! :-)\n");
else
	printf("Failure?! :-(\n");
endif
printf("Elapsed time = %f\n\n\n\n",t5);

printf("EXAMPLE 6: Funny case: minimize w.r.t. second argument, Ordinary BFGS, using numeric gradient\n");
if test6
	printf("Success!! :-)\n");
else
	printf("Failure?! :-(\n");
endif
printf("Elapsed time = %f\n\n\n\n",t6);

printf("EXAMPLE 7: Ordinary BFGS, using numeric gradient, using non-default tolerances\n");
if test7
	printf("Success!! :-)\n");
else
	printf("Failure?! :-(\n");
endif
printf("Elapsed time = %f\n\n\n\n",t7);

