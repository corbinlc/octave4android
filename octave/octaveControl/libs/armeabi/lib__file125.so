## Copyright (C) 2010, 2011, 2012   Lukas F. Reichlin
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
## @deftypefn {Function File} {[@var{H}, @var{w}, @var{tsam}] =} frdata (@var{sys})
## @deftypefnx {Function File} {[@var{H}, @var{w}, @var{tsam}] =} frdata (@var{sys}, @var{"vector"})
## Access frequency response data.
## Argument @var{sys} is not limited to frequency response data objects.
## If @var{sys} is not a frd object, it is converted automatically.
##
## @strong{Inputs}
## @table @var
## @item sys
## Any type of LTI model.
## @item "v", "vector"
## In case @var{sys} is a SISO model, this option returns the frequency response
## as a column vector (lw-by-1) instead of an array (p-by-m-by-lw).
## @end table
##
## @strong{Outputs}
## @table @var
## @item H
## Frequency response array (p-by-m-by-lw).  H(i,j,k) contains the
## response from input j to output i at frequency k.  In the SISO case,
## a vector (lw-by-1) is possible as well.
## @item w
## Frequency vector (lw-by-1) in radian per second [rad/s].
## Frequencies are in ascending order.
## @item tsam
## Sampling time in seconds.  If @var{sys} is a continuous-time model,
## a zero is returned.
## @end table
## @end deftypefn

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: October 2010
## Version: 0.3

function [H, w, tsam] = frdata (sys, rtype = "array")

  if (! isa (sys, "frd"))
    sys = frd (sys);
  endif

  [H, w] = __sys_data__ (sys); 

  tsam = sys.tsam;

  if (strncmpi (rtype, "v", 1) && issiso (sys))
    H = reshape (H, [], 1);
  endif

endfunction
