## Copyright (C) 1994, 1996, 2000, 2004, 2005, 2006, 2007 Auburn University
## Copyright (C) 2012 Lukas F. Reichlin <lukas.reichlin@gmail.com>
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
## @deftypefn {Function File} {[@var{pol}, @var{zer}, @var{k}] =} ss2zp (@var{a}, @var{b}, @var{c}, @var{d})
## Converts a state space representation to a set of poles and zeros;
## @var{k} is a gain associated with the zeros.
##
## @end deftypefn

## Author: David Clem

function [z, p, k] = ss2zp (varargin)

  if (nargin == 0)
    print_usage ();
  endif

  [z, p, k] = zpkdata (ss (varargin{:}), "vector");

endfunction
