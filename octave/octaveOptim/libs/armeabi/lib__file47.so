## Copyright (C) 2000 Ben Sapp <bsapp@lanl.gov>
## Copyright (C) 2002 Paul Kienzle <pkienzle@gmail.com>
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

## -*- texinfo -*-
## @deftypefn {Function File} {@var{xmin} =} nrm(@var{f},@var{x0})
## Using @var{x0} as a starting point find a minimum of the scalar
## function @var{f}.  The Newton-Raphson method is used.
## @end deftypefn

## Reference: David G Luenberger's Linear and Nonlinear Programming

function x = nrm(f,x,varargin)
  velocity = 1;
  acceleration = 1;
  
  h = 0.01;
  while(abs(velocity) > 0.0001)
    fx = feval(f,x,varargin{:});
    fxph = feval(f,x+h,varargin{:});
    fxmh = feval(f,x-h,varargin{:});
    velocity = (fxph - fxmh)/(2*h);
    acceleration = (fxph - 2*fx + fxmh)/(h^2);
    x = x - velocity/abs(acceleration);
  endwhile
endfunction
