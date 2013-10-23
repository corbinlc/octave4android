## Copyright (C) 2009, 2010   Lukas F. Reichlin
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
## @deftypefn {Function File} {[@var{bool}, @var{ncon}] =} isctrb (@var{sys})
## @deftypefnx {Function File} {[@var{bool}, @var{ncon}] =} isctrb (@var{sys}, @var{tol})
## @deftypefnx {Function File} {[@var{bool}, @var{ncon}] =} isctrb (@var{a}, @var{b})
## @deftypefnx {Function File} {[@var{bool}, @var{ncon}] =} isctrb (@var{a}, @var{b}, @var{e})
## @deftypefnx {Function File} {[@var{bool}, @var{ncon}] =} isctrb (@var{a}, @var{b}, @var{[]}, @var{tol})
## @deftypefnx {Function File} {[@var{bool}, @var{ncon}] =} isctrb (@var{a}, @var{b}, @var{e}, @var{tol})
## Logical check for system controllability.
## For numerical reasons, @code{isctrb (sys)}
## should be used instead of @code{rank (ctrb (sys))}.
##
## @strong{Inputs}
## @table @var
## @item sys
## LTI model.  Descriptor state-space models are possible.
## @item a
## State transition matrix.
## @item b
## Input matrix.
## @item e
## Descriptor matrix.
## If @var{e} is empty @code{[]} or not specified, an identity matrix is assumed.
## @item tol
## Optional roundoff parameter.  Default value is 0.
## @end table
##
## @strong{Outputs}
## @table @var
## @item bool = 0
## System is not controllable.
## @item bool = 1
## System is controllable.
## @item ncon
## Number of controllable states.
## @end table
##
## @strong{Algorithm}@*
## Uses SLICOT AB01OD and TG01HD by courtesy of
## @uref{http://www.slicot.org, NICONET e.V.}
##
## @seealso{isobsv}
## @end deftypefn

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: October 2009
## Version: 0.4

function [bool, ncont] = isctrb (a, b = [], e = [], tol = [])

  if (nargin < 1 || nargin > 4)
    print_usage ();
  elseif (isa (a, "lti"))  # isctrb (sys), isctrb (sys, tol)
    if (nargin > 2)
      print_usage ();
    endif
    tol = b;
    [a, b, c, d, e] = dssdata (a, []);
  elseif (nargin < 2)      # isctrb (a, b), isctrb (a, b, tol)
    print_usage ();
  elseif (! is_real_square_matrix (a) || ! is_real_matrix (b) || rows (a) != rows (b))
    error ("isctrb: a(%dx%d), b(%dx%d) not conformal",
            rows (a), columns (a), rows (b), columns (b));
  elseif (! isempty (e) && (! is_real_square_matrix (e) || ! size_equal (e, a)))
    error ("isctrb: a(%dx%d), e(%dx%d) not conformal",
            rows (a), columns (a), rows (e), columns (e));
  endif

  if (isempty (tol))
    tol = 0;               # default tolerance
  elseif (! is_real_scalar (tol))
    error ("isctrb: tol must be a real scalar");
  endif

  if (isempty (e))
    [~, ~, ~, ncont] = __sl_ab01od__ (a, b, tol);
  else
    [~, ~, ~, ~, ~, ~, ncont] = __sl_tg01hd__ (a, e, b, zeros (1, columns (a)), tol);
  endif

  bool = (ncont == rows (a));

endfunction
