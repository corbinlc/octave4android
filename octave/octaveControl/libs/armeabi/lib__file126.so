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
## @deftypefn{Function File} {@var{H} =} freqresp (@var{sys}, @var{w})
## Evaluate frequency response at given frequencies.
##
## @strong{Inputs}
## @table @var
## @item sys
## LTI system.
## @item w
## Vector of frequency values.
## @end table
##
## @strong{Outputs}
## @table @var
## @item H
## Array of frequency response.  For a system with m inputs and p outputs, the array @var{H}
## has dimensions [p, m, length (w)].
## The frequency response at the frequency w(k) is given by H(:,:,k).
## @end table
##
## @seealso{dcgain}
## @end deftypefn

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: October 2009
## Version: 0.2

function H = freqresp (sys, w)

  if (nargin != 2)           # case freqresp () not possible
    print_usage ();
  endif

  if (! is_real_vector (w))  # catches freqresp (sys, sys) and freqresp (w, sys) as well
    error ("freqresp: second argument must be a real vector");
  endif

  H = __freqresp__ (sys, w);

endfunction