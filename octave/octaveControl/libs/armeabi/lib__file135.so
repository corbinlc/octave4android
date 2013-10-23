## Copyright (C) 2009   Lukas F. Reichlin
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
## @deftypefn {Function File} {@var{sys} =} lft (@var{sys1}, @var{sys2})
## @deftypefnx {Function File} {@var{sys} =} lft (@var{sys1}, @var{sys2}, @var{nu}, @var{ny})
## Linear fractional tranformation, also known as Redheffer star product.
##
## @strong{Inputs}
## @table @var
## @item sys1
## Upper LTI model.
## @item sys2
## Lower LTI model.
## @item nu
## The last nu inputs of @var{sys1} are connected with the first nu outputs of @var{sys2}.
## If not specified, @code{min (m1, p2)} is taken.
## @item ny
## The last ny outputs of @var{sys1} are connected with the first ny inputs of @var{sys2}.
## If not specified, @code{min (p1, m2)} is taken.
## @end table
##
## @strong{Outputs}
## @table @var
## @item sys
## Resulting LTI model.
## @end table
##
## @strong{Block Diagram}
## @example
## @group
##       .............sys..............
##       :         +--------+         :
## w1 ------------>|        |------------> z1
##       :         |  sys1  |         :
##       : u +---->|        |-----+ y :
##       :   |     +--------+     |   :          Lower LFT
##       :   |                    |   :
##       :   |     +--------+     |   :          lft (sys1, sys2)
##       :   +-----|  sys2  |<----+   :
##       :         +--------+         :
##       :............................:
## @end group
## @end example
## @example
## @group
##       .............sys..............
##       :         +--------+         :
##       : u +---->|  sys1  |-----+ y :
##       :   |     +--------+     |   :          Upper LFT
##       :   |                    |   :
##       :   |     +--------+     |   :          lft (sys1, sys2)
##       :   +-----|        |<----+   :
##       :         |  sys2  |         :
## z2 <------------|        |<------------ w2
##       :         +--------+         :
##       :............................:
## @end group
## @end example
## @example
## @group
##       .............sys..............
##       :         +--------+         :
## w1 ------------>|        |------------> z1
##       :         |  sys1  |         :
##       : u +---->|        |-----+ y :
##       :   |     +--------+     |   :
##       :   |                    |   :          lft (sys1, sys2, nu, ny)
##       :   |     +--------+     |   :
##       :   +-----|        |<----+   :
##       :         |  sys2  |         :
## z2 <------------|        |<------------ w2
##       :         +--------+         :
##       :............................: 
## @end group
## @end example
## @end deftypefn

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: October 2009
## Version: 0.2

function sys = lft (sys1, sys2, nu, ny)

  if (nargin != 2 && nargin != 4)
    print_usage ();
  endif

  ## object conversion done by sys_group if necessary

  [p1, m1] = size (sys1);
  [p2, m2] = size (sys2);

  nu_max = min (m1, p2);
  ny_max = min (m2, p1);

  if (nargin == 2)  # sys = lft (sys1, sys2)
    nu = nu_max;
    ny = ny_max;
  else              # sys = lft (sys1, sys2, nu, ny)
    if (! is_real_scalar (nu) || nu < 0)
      error ("lft: argument nu must be a positive integer");
    endif

    if (! is_real_scalar (ny) || ny < 0)
      error ("lft: argument ny must be a positive integer");
    endif

    if (nu > nu_max)
      error ("lft: argument nu (%d) must be at most %d", nu, nu_max);
    endif

    if (ny > ny_max)
      error ("lft: argument ny (%d) must be at most %d", ny, ny_max);
    endif
  endif

  M11 = zeros (m1, p1);
  M12 = [zeros(m1-nu, p2); eye(nu), zeros(nu, p2-nu)];
  M21 = [zeros(ny, p1-ny), eye(ny); zeros(m2-ny, p1)];
  M22 = zeros (m2, p2);

  M = [M11, M12; M21, M22];

  in_idx = [1 : (m1-nu), m1 + (ny+1 : m2)];
  out_idx = [1 : (p1-ny), p1 + (nu+1 : p2)];

  sys = __sys_group__ (sys1, sys2);
  sys = __sys_connect__ (sys, M);
  sys = __sys_prune__ (sys, out_idx, in_idx);

  [p, m] = size (sys);

  if (m == 0)
    warning ("lft: resulting system has no inputs");
  endif

  if (p == 0)
    warning ("lft: resulting system has no outputs");
  endif

endfunction
