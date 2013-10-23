## Copyright (C) 2006 Sylvain Pelissier <sylvain.pelissier@gmail.com>
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
## @deftypefn {Function File} {@var{x} =} fminsearch (@var{fun}, @var{x0})
## @deftypefnx {Function File} {[@var{x}, @var{fval}] =} fminsearch (@var{fun}, @var{x0}, @var{options}, @var{grad}, @var{P1}, @var{P2}, @dots{})
##
## Find the minimum of a funtion of several variables.
## By default the method used is the Nelder&Mead Simplex algorithm.
## @seealso{fmin,fmins,nmsmax}
## @end deftypefn

## TODO: describe arguments in texinfo help string

function [x, fval] = fminsearch (fun, x0, options = [], grad = [], varargin)

  if (nargin < 2)
    print_usage ();
  endif

  x = fmins (fun, x0, options, grad, varargin{:});
  fval = feval (fun, x, varargin{:});

endfunction
