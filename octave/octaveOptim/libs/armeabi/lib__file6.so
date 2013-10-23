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

## battery.m: repeatedly call bfgs using a battery of 
## start values, to attempt to find global min
## of a nonconvex function
##
## INPUTS:
## func: function to mimimize
## args: args of function
## minarg: argument to minimize w.r.t. (usually = 1)
## startvals: kxp matrix of values to try for sure (don't include all zeros, that's automatic)
## max iters per start value
## number of additional random start values to try
##
# OUTPUT: theta - the best value found - NOT iterated to convergence

function theta = battery(func, args, minarg, startvals, maxiters)

# setup
[k,trials] = size(startvals);
bestobj = inf;
besttheta = zeros(k,1);
bfgscontrol = {maxiters,0,0,1};
# now try the supplied start values, and optionally the random start values
for i = 1:trials
	args{minarg} = startvals(:,i);
	[theta, obj_value, convergence] = bfgsmin (func, args, bfgscontrol);
	
	if obj_value < bestobj
		besttheta = theta;
		bestobj = obj_value;
	endif
endfor
	
theta = besttheta;
endfunction
