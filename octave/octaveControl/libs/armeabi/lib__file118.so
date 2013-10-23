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
## @deftypefn {Function File} {@var{k} =} dcgain (@var{sys})
## DC gain of LTI model.
##
## @strong{Inputs}
## @table @var
## @item sys
## LTI system.
## @end table
##
## @strong{Outputs}
## @table @var
## @item k
## DC gain matrice.  For a system with m inputs and p outputs, the array @var{k}
## has dimensions [p, m].
## @end table
##
## @seealso{freqresp}
## @end deftypefn

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: October 2009
## Version: 0.1

function gain = dcgain (sys)

  if (nargin != 1)  # sys is always an LTI model
    print_usage ();
  endif

  gain = __freqresp__ (sys, 0);

endfunction