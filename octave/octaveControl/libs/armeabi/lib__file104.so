## Copyright (C) 1996, 2000, 2002, 2003, 2004, 2005, 2007
##               Auburn University.  All rights reserved.
##
## This program is free software: you can redistribute it and/or modify
## it under the terms of the GNU General Public License as published by
## the Free Software Foundation, either version 3 of the License, or
## (at your option) any later version.
##
## This program is distributed in the hope that it will be useful,
## but WITHOUT ANY WARRANTY; without even the implied warranty of
## MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
## GNU General Public License for more details.
##
## You should have received a copy of the GNU General Public License
## along with this program. If not, see <http://www.gnu.org/licenses/>.

## -*- texinfo -*-
## @deftypefn {Function File} {@var{bool} =} issample (@var{ts})
## @deftypefnx {Function File} {@var{bool} =} issample (@var{ts}, @var{flg})
## Return true if @var{ts} is a valid sampling time.
##
## @strong{Inputs}
## @table @var
## @item ts
## Alleged sampling time to be tested.
## @item flg = 1
## Accept real scalars @var{ts} > 0.  Default Value.
## @item flg = 0
## Accept real scalars @var{ts} >= 0.
## @item flg = -1
## Accept real scalars @var{ts} > 0 and @var{ts} == -1.
## @item flg = -10
## Accept real scalars @var{ts} >= 0 and @var{ts} == -1.
## @item flg = -2
## Accept real scalars @var{ts} >= 0, @var{ts} == -1 and @var{ts} == -2.
## @end table
##
## @strong{Outputs}
## @table @var
## @item bool
## True if conditions are met and false otherwise.
## @end table
##
## @end deftypefn

## Author: A. S. Hodel <a.s.hodel@eng.auburn.edu>
## Created: July 1995

## Adapted-By: Lukas Reichlin <lukas.reichlin@gmail.com>
## Date: September 2009
## Version: 0.3

function bool = issample (tsam, flg = 1)

  if (nargin < 1 || nargin > 2)
    print_usage (); 
  endif

  switch (flg)
    case 1    # discrete
      bool = is_real_scalar (tsam) && (tsam > 0);
    case 0    # continuous or discrete
      bool = is_real_scalar (tsam) && (tsam >= 0);
    case -1   # discrete, tsam unspecified
      bool = is_real_scalar (tsam) && (tsam > 0 || tsam == -1);
    case -10  # continuous or discrete, tsam unspecified
      bool = is_real_scalar (tsam) && (tsam >= 0 || tsam == -1);
    case -2   # accept static gains
      bool = is_real_scalar (tsam) && (tsam >= 0 || tsam == -1 || tsam == -2);
    otherwise
      print_usage ();
  endswitch

endfunction


## flg == 1
%!assert (issample (1))
%!assert (issample (pi))
%!assert (issample (0), false)
%!assert (issample (-1), false)
%!assert (issample (-1, 1), false)
%!assert (issample ("a"), false)
%!assert (issample (eye (2)), false)
%!assert (issample (2+2i), false)

## flg == 0
%!assert (issample (1, 0))
%!assert (issample (0, 0))
%!assert (issample (-1, 0), false)
%!assert (issample (pi, 0))
%!assert (issample ("b", 0), false)
%!assert (issample (rand (3,2), 0), false)
%!assert (issample (2+2i, 0), false)
%!assert (issample (0+2i, 0), false)

## flg == -1
%!assert (issample (-1, -1))
%!assert (issample (0, -1), false)
%!assert (issample (1, -1))
%!assert (issample (pi, -1))
%!assert (issample (-pi, -1), false)
%!assert (issample ("b", -1), false)
%!assert (issample (rand (3,2), -1), false)
%!assert (issample (-2+2i, -1), false)

## errors
%!error (issample (-1, "ab"))
%!error (issample ())
%!error (issample (-1, -1, -1))
%!error (issample (1, pi))
%!error (issample (5, rand (2,3)))
%!error (issample (0, 1+2i))
