## Copyright (C) 2010, 2011   Lukas F. Reichlin
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
## @deftypefn {Function File} {[@var{a}, @var{b}, @var{c}, @var{d}, @var{e}, @var{tsam}] =} dssdata (@var{sys})
## @deftypefnx {Function File} {[@var{a}, @var{b}, @var{c}, @var{d}, @var{e}, @var{tsam}] =} dssdata (@var{sys}, @var{[]})
## Access descriptor state-space model data.
## Argument @var{sys} is not limited to descriptor state-space models.
## If @var{sys} is not a descriptor state-space model, it is converted automatically.
##
## @strong{Inputs}
## @table @var
## @item sys
## Any type of LTI model.
## @item []
## In case @var{sys} is not a dss model (descriptor matrix @var{e} empty),
## @code{dssdata (sys, [])} returns the empty element @code{e = []} whereas
## @code{dssdata (sys)} returns the identity matrix @code{e = eye (size (a))}.
## @end table
##
## @strong{Outputs}
## @table @var
## @item a
## State transition matrix (n-by-n).
## @item b
## Input matrix (n-by-m).
## @item c
## Measurement matrix (p-by-n).
## @item d
## Feedthrough matrix (p-by-m).
## @item e
## Descriptor matrix (n-by-n).
## @item tsam
## Sampling time in seconds.  If @var{sys} is a continuous-time model,
## a zero is returned.
## @end table
## @end deftypefn

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: September 2010
## Version: 0.2

function [a, b, c, d, e, tsam, scaled] = dssdata (sys, flg = 0)

  ## NOTE: In case sys is not a dss model (matrice e empty),
  ##       dssdata (sys, []) returns e = [] whereas
  ##       dssdata (sys) returns e = eye (size (a))

  if (nargin > 2)
    print_usage ();
  endif

  if (! isa (sys, "ss"))
    sys = ss (sys);
  endif

  [a, b, c, d, e, ~, scaled] = __sys_data__ (sys);

  if (isempty (e) && ! isempty (flg))
    e = eye (size (a));  # return eye for ss models
  endif

  tsam = sys.tsam;

endfunction