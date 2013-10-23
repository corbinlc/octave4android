## Copyright (C) 2009, 2010, 2012   Lukas F. Reichlin
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
## @deftypefn {Function File} {@var{bool} =} isstabilizable (@var{sys})
## @deftypefnx {Function File} {@var{bool} =} isstabilizable (@var{sys}, @var{tol})
## @deftypefnx {Function File} {@var{bool} =} isstabilizable (@var{a}, @var{b})
## @deftypefnx {Function File} {@var{bool} =} isstabilizable (@var{a}, @var{b}, @var{e})
## @deftypefnx {Function File} {@var{bool} =} isstabilizable (@var{a}, @var{b}, @var{[]}, @var{tol})
## @deftypefnx {Function File} {@var{bool} =} isstabilizable (@var{a}, @var{b}, @var{e}, @var{tol})
## @deftypefnx {Function File} {@var{bool} =} isstabilizable (@var{a}, @var{b}, @var{[]}, @var{[]}, @var{dflg})
## @deftypefnx {Function File} {@var{bool} =} isstabilizable (@var{a}, @var{b}, @var{e}, @var{[]}, @var{dflg})
## @deftypefnx {Function File} {@var{bool} =} isstabilizable (@var{a}, @var{b}, @var{[]}, @var{tol}, @var{dflg})
## @deftypefnx {Function File} {@var{bool} =} isstabilizable (@var{a}, @var{b}, @var{e}, @var{tol}, @var{dflg})
## Logical check for system stabilizability.
## All unstable modes must be controllable or all uncontrollable states must be stable.
##
## @strong{Inputs}
## @table @var
## @item sys
## LTI system.
## @item a
## State transition matrix.
## @item b
## Input matrix.
## @item e
## Descriptor matrix.
## If @var{e} is empty @code{[]} or not specified, an identity matrix is assumed.
## @item tol
## Optional tolerance for stability.  Default value is 0.
## @item dflg = 0
## Matrices (@var{a}, @var{b}) are part of a continuous-time system.  Default Value.
## @item dflg = 1
## Matrices (@var{a}, @var{b}) are part of a discrete-time system.
## @end table
##
## @strong{Outputs}
## @table @var
## @item bool = 0
## System is not stabilizable.
## @item bool = 1
## System is stabilizable.
## @end table
##
## @strong{Algorithm}@*
## Uses SLICOT AB01OD and TG01HD by courtesy of
## @uref{http://www.slicot.org, NICONET e.V.}
## @example
## @group
## * Calculate staircase form (SLICOT AB01OD)
## * Extract unobservable part of state transition matrix
## * Calculate eigenvalues of unobservable part
## * Check whether
##   real (ev) < -tol*(1 + abs (ev))   continuous-time
##   abs (ev) < 1 - tol                discrete-time
## @end group
## @end example
## @seealso{isdetectable, isstable, isctrb, isobsv}
## @end deftypefn

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: October 2009
## Version: 0.4

function bool = isstabilizable (a, b = [], e = [], tol = [], dflg = 0)

  if (nargin < 1 || nargin > 5)
    print_usage ();
  elseif (isa (a, "lti"))  # isstabilizable (sys), isstabilizable (sys, tol)
    if (nargin > 2)
      print_usage ();
    endif
    tol = b;
    dflg = ! isct (a);
    [a, b, c, d, e] = dssdata (a, []);
  elseif (nargin == 1)     # isstabilizable (a, b, ...)
    print_usage ();
  elseif (! is_real_square_matrix (a) || rows (a) != rows (b))
    error ("isstabilizable: a must be square and conformal to b");
  elseif (! isempty (e) && (! is_real_square_matrix (e) || ! size_equal (a, e)))
    error ("isstabilizable: e must be square and conformal to a");
  endif

  if (isempty (tol))
    tol = 0;               # default tolerance
  elseif (! is_real_scalar (tol))
    error ("isstabilizable: tol must be a real scalar");
  endif

  if (isempty (e))
    ## controllability staircase form
    [ac, ~, ~, ncont] = __sl_ab01od__ (a, b, tol);

    ## extract uncontrollable part of staircase form
    uncont_idx = ncont+1 : rows (a);
    auncont = ac(uncont_idx, uncont_idx);

    ## calculate poles of uncontrollable part
    pol = eig (auncont);
  else
    ## controllability staircase form - output matrix c has no influence
    [ac, ec, ~, ~, ~, ~, ncont] = __sl_tg01hd__ (a, e, b, zeros (1, columns (a)), tol);

    ## extract uncontrollable part of staircase form
    uncont_idx = ncont+1 : rows (a);
    auncont = ac(uncont_idx, uncont_idx);
    euncont = ec(uncont_idx, uncont_idx);

    ## calculate poles of uncontrollable part
    pol = eig (auncont, euncont);
    
    ## remove infinite poles
    tolinf = norm ([auncont, euncont], 2);
    idx = find (abs (pol) < tolinf/eps);
    pol = pol(idx);
  endif

  ## check whether uncontrollable poles are stable
  bool = __is_stable__ (pol, ! dflg, tol);

endfunction
