## Copyright (C) 1994, 1996, 2000, 2002, 2003, 2004, 2005, 2007 Auburn University
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
## @deftypefn {Function File} {[@var{a}, @var{b}, @var{c}, @var{d}] =} zp2ss (@var{zer}, @var{pol}, @var{k})
## Conversion from zero / pole to state space.
##
## @strong{Inputs}
## @table @var
## @item zer
## @itemx pol
## Vectors of (possibly) complex poles and zeros of a transfer
## function. Complex values must come in conjugate pairs
## (i.e., @math{x+jy} in @var{zer} means that @math{x-jy} is also in @var{zer}).
## @item k
## Real scalar (leading coefficient).
## @end table
##
## @strong{Outputs}
## @table @var
## @item @var{a}
## @itemx @var{b}
## @itemx @var{c}
## @itemx @var{d}
## The state space system, in the form:
## @iftex
## @tex
## $$ \dot x = Ax + Bu $$
## $$ y = Cx + Du $$
## @end tex
## @end iftex
## @ifinfo
## @example
##      .
##      x = Ax + Bu
##      y = Cx + Du
## @end example
## @end ifinfo
## @end table
## @end deftypefn

## Author: David Clem

function [a, b, c, d, e] = zp2ss (varargin)

  if (nargin == 0)
    print_usage ();
  endif

  [a, b, c, d, e] = dssdata (zpk (varargin{:}), []);

endfunction
