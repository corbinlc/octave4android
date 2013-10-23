## Copyright (C) 2009, 2010, 2011   Lukas F. Reichlin
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
## @deftypefn {Function File} {[@var{a}, @var{b}, @var{c}, @var{d}, @var{tsam}] =} ssdata (@var{sys})
## Access state-space model data.
## Argument @var{sys} is not limited to state-space models.
## If @var{sys} is not a state-space model, it is converted automatically.
##
## @strong{Inputs}
## @table @var
## @item sys
## Any type of LTI model.
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
## @item tsam
## Sampling time in seconds.  If @var{sys} is a continuous-time model,
## a zero is returned.
## @end table
## @end deftypefn

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: September 2009
## Version: 0.4

function [a, b, c, d, tsam, scaled] = ssdata (sys)

  if (! isa (sys, "ss"))
    sys = ss (sys);
  endif

  [a, b, c, d, e, ~, scaled] = __sys_data__ (sys);

  [a, b, c, d, e] = __dss2ss__ (a, b, c, d, e);

  tsam = sys.tsam;

endfunction