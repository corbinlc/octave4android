## Copyright (C) 1994, 1996, 2000, 2004, 2005, 2007 Auburn University <btenison@eng.auburn.edu>
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
## @deftypefn {Function File} {[@var{num}, @var{den}] =} ss2tf (@var{a}, @var{b}, @var{c}, @var{d})
## Conversion from transfer function to state-space.
## The state space system:
## @iftex
## @tex
## $$ \dot x = Ax + Bu $$
## $$ y = Cx + Du $$
## @end tex
## @end iftex
## @ifinfo
## @example
##       .
##       x = Ax + Bu
##       y = Cx + Du
## @end example
## @end ifinfo
##
## is converted to a transfer function:
## @iftex
## @tex
## $$ G(s) = { { \rm num }(s) \over { \rm den }(s) } $$
## @end tex
## @end iftex
## @ifinfo
## @example
##
##                 num(s)
##           G(s)=-------
##                 den(s)
## @end example
## @end ifinfo
##
## @end deftypefn

## Author: R. Bruce Tenison <btenison@eng.auburn.edu>

function [num, den] = ss2tf (varargin)

  if (nargin == 0)
    print_usage ();
  endif

  [num, den] = tfdata (ss (varargin{:}), "vector");

endfunction
