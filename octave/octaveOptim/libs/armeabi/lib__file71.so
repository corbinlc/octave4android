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

## Rosenbrock function - used to create example obj. fns.
##
## Function value and gradient vector of the rosenbrock function
## The minimizer is at the vector (1,1,..,1),
## and the minimized value is 0.

function [obj_value, gradient] = rosenbrock(x);
	dimension = length(x);
	obj_value = sum(100*(x(2:dimension)-x(1:dimension-1).^2).^2 + (1-x(1:dimension-1)).^2);
	if nargout > 1
		gradient = zeros(dimension, 1);
		gradient(1:dimension-1) = - 400*x(1:dimension-1).*(x(2:dimension)-x(1:dimension-1).^2) - 2*(1-x(1:dimension-1));
		gradient(2:dimension) = gradient(2:dimension) + 200*(x(2:dimension)-x(1:dimension-1).^2);
	endif
endfunction
