## Copyright (C) 1996, 1998, 2000, 2003, 2004, 2005, 2006, 2007 Auburn University
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
## @deftypefn {Function File} {[@var{zer}, @var{pol}, @var{k}] =} tf2zp (@var{num}, @var{den})
## Converts transfer functions to poles-and-zero representations.
##
## Returns the zeros and poles of the system defined 
## by @var{num}/@var{den}.
## @var{k} is a gain associated with the system zeros.
## @end deftypefn

## Author: A. S. Hodel <a.s.hodel@eng.auburn.edu>

function [z, p, k] = tf2zp (varargin)

  if (nargin == 0)
    print_usage ();
  endif

  [z, p, k] = zpkdata (tf (varargin{:}), "vector");

endfunction
