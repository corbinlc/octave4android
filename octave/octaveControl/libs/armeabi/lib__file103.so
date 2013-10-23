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
## @deftypefn {Function File} {[@var{bool}, @var{nobs}] =} isobsv (@var{sys})
## @deftypefnx {Function File} {[@var{bool}, @var{nobs}] =} isobsv (@var{sys}, @var{tol})
## @deftypefnx {Function File} {[@var{bool}, @var{nobs}] =} isobsv (@var{a}, @var{c})
## @deftypefnx {Function File} {[@var{bool}, @var{nobs}] =} isobsv (@var{a}, @var{c}, @var{e})
## @deftypefnx {Function File} {[@var{bool}, @var{nobs}] =} isobsv (@var{a}, @var{c}, @var{[]}, @var{tol})
## @deftypefnx {Function File} {[@var{bool}, @var{nobs}] =} isobsv (@var{a}, @var{c}, @var{e}, @var{tol})
## Logical check for system observability.
## For numerical reasons, @code{isobsv (sys)}
## should be used instead of @code{rank (obsv (sys))}.
##
## @strong{Inputs}
## @table @var
## @item sys
## LTI model.  Descriptor state-space models are possible.
## @item a
## State transition matrix.
## @item c
## Measurement matrix.
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
## System is not observable.
## @item bool = 1
## System is observable.
## @item nobs
## Number of observable states.
## @end table
##
## @strong{Algorithm}@*
## Uses SLICOT AB01OD and TG01HD by courtesy of
## @uref{http://www.slicot.org, NICONET e.V.}
##
## @seealso{isctrb}
## @end deftypefn

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: October 2009
## Version: 0.4

function [bool, nobs] = isobsv (a, c = [], e = [], tol = [])

  if (nargin == 0)
    print_usage ();
  elseif (isa (a, "lti"))            # isobsv (sys), isobsv (sys, tol)
    if (nargin > 2)
      print_usage ();
    endif
    [bool, nobs] = isctrb (a.', c);  # transpose is overloaded
  elseif (nargin < 2 || nargin > 4)
    print_usage ();
  else                               # isobsv (a, c), isobsv (a, c, e), ...
    [bool, nobs] = isctrb (a.', c.', e.', tol);
  endif

endfunction

