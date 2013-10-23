## Copyright (C) 2009, 2010   Lukas F. Reichlin
## Copyright (C) 2009 Luca Favatella <slackydeb@gmail.com>
##
## This file is part of LTI Syncope.
##
## LTI Syncope is free software: you can redistribute it and/or modify
## it under the terms of the GNU General Public License as published by
## the Free Software Foundation, either version 3 of the License, or
## (at your option) any later version.
##
## LTI Syncope is distributed in the hope that it will be useful,
## but WITHOUT ANY WARRANTY; without even the implied warranty of
## MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
## GNU General Public License for more details.
##
## You should have received a copy of the GNU General Public License
## along with LTI Syncope.  If not, see <http://www.gnu.org/licenses/>.

## -*- texinfo -*-
## @deftypefn {Function File} {@var{ob} =} obsv (@var{sys})
## @deftypefnx {Function File} {@var{ob} =} obsv (@var{a}, @var{c})
## Return observability matrix.
##
## @strong{Inputs}
## @table @var
## @item sys
## LTI model.
## @item a
## State transition matrix (n-by-n).
## @item c
## Measurement matrix (p-by-n).
## @end table
##
## @strong{Outputs}
## @table @var
## @item ob
## Observability matrix.
## @end table
##
## @strong{Equation}
## @iftex
## @tex
## $$ O_b = \\left[ \\matrix{  C       \\cr
##                             CA    \\cr
##                             CA^2  \\cr
##                             \\vdots  \\cr
##                             CA^{n-1} } \\right ] $$
## @end tex
## @end iftex
## @ifnottex
## @example
## @group
##      | C        |
##      | CA       |
## Ob = | CA^2     |
##      | ...      |
##      | CA^(n-1) |
## @end group
## @end example
## @end ifnottex
## @end deftypefn

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: October 2009
## Version: 0.2

function ob = obsv (a, c)

  if (nargin == 1)           # obsv (sys)
    ob = ctrb (a.').';       # transpose is overloaded for lti models
  elseif (nargin == 2)       # obsv (a, c)
    ob = ctrb (a.', c.').';  # size checked inside
  else
    print_usage ();
  endif

endfunction


%!assert (obsv ([1, 0; 0, -0.5], [8, 8]), [8, 8; 8, -4]);
