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
## @deftypefn {Function File} {@var{bool} =} isdetectable (@var{sys})
## @deftypefnx {Function File} {@var{bool} =} isdetectable (@var{sys}, @var{tol})
## @deftypefnx {Function File} {@var{bool} =} isdetectable (@var{a}, @var{c})
## @deftypefnx {Function File} {@var{bool} =} isdetectable (@var{a}, @var{c}, @var{e})
## @deftypefnx {Function File} {@var{bool} =} isdetectable (@var{a}, @var{c}, @var{[]}, @var{tol})
## @deftypefnx {Function File} {@var{bool} =} isdetectable (@var{a}, @var{c}, @var{e}, @var{tol})
## @deftypefnx {Function File} {@var{bool} =} isdetectable (@var{a}, @var{c}, @var{[]}, @var{[]}, @var{dflg})
## @deftypefnx {Function File} {@var{bool} =} isdetectable (@var{a}, @var{c}, @var{e}, @var{[]}, @var{dflg})
## @deftypefnx {Function File} {@var{bool} =} isdetectable (@var{a}, @var{c}, @var{[]}, @var{tol}, @var{dflg})
## @deftypefnx {Function File} {@var{bool} =} isdetectable (@var{a}, @var{c}, @var{e}, @var{tol}, @var{dflg})
## Logical test for system detectability.
## All unstable modes must be observable or all unobservable states must be stable.
##
## @strong{Inputs}
## @table @var
## @item sys
## LTI system.
## @item a
## State transition matrix.
## @item c
## Measurement matrix.
## @item e
## Descriptor matrix.
## If @var{e} is empty @code{[]} or not specified, an identity matrix is assumed.
## @item tol
## Optional tolerance for stability.  Default value is 0.
## @item dflg = 0
## Matrices (@var{a}, @var{c}) are part of a continuous-time system.  Default Value.
## @item dflg = 1
## Matrices (@var{a}, @var{c}) are part of a discrete-time system.
## @end table
##
## @strong{Outputs}
## @table @var
## @item bool = 0
## System is not detectable.
## @item bool = 1
## System is detectable.
## @end table
##
##
## @strong{Algorithm}@*
## Uses SLICOT AB01OD and TG01HD by courtesy of
## @uref{http://www.slicot.org, NICONET e.V.}
## See @command{isstabilizable} for description of computational method.
## @seealso{isstabilizable, isstable, isctrb, isobsv}
## @end deftypefn

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: October 2009
## Version: 0.3

function bool = isdetectable (a, c = [], e = [], tol = [], dflg = 0)

  if (nargin == 0)
    print_usage ();
  elseif (isa (a, "lti"))                              # isdetectable (sys), isdetectable (sys, tol)
    if (nargin > 2)
      print_usage ();
    endif
    bool = isstabilizable (a.', c);                    # transpose is overloaded
  elseif (nargin < 2 || nargin > 5)
    print_usage ();
  else                                                 # isdetectable (a, c, ...)
    bool = isstabilizable (a.', c.', e.', tol, dflg);  # arguments checked inside
  endif

endfunction

