## Copyright (C) 1996, 1998, 2000, 2002, 2004, 2005, 2007 Auburn University
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
## @deftypefn {Function File} {[@var{num}, @var{den}] =} zp2tf (@var{zer}, @var{pol}, @var{k})
## Converts zeros / poles to a transfer function.
##
## @strong{Inputs}
## @table @var
## @item zer
## @itemx pol
## Vectors of (possibly complex) poles and zeros of a transfer
## function.  Complex values must appear in conjugate pairs.
## @item k
## Real scalar (leading coefficient).
## @end table
## @end deftypefn

## Author: A. S. Hodel <a.s.hodel@eng.auburn.edu>
## (With help from students Ingram, McGowan.)

function [num, den] = zp2tf (varargin)

  if (nargin == 0)
    print_usage ();
  endif

  [num, den] = tfdata (zpk (varargin{:}), "vector");

endfunction
