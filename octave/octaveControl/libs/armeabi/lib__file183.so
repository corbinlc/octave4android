## Copyright (C) 2011   Lukas F. Reichlin
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
## check weightings for model reduction commands

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: November 2011
## Version: 0.1

function [a, b, c, d, job] = __modred_check_weight__ (sys, dt, p = [], m = [])

  sys = ss (sys);  # could be non-lti, therefore ssdata would fail

  if (dt != isdt (sys))
    error ("modred: ct/dt");  # TODO: error message
  endif

  [pw, mw] = size (sys);
  
  if (! isempty (p) && mw != p)
    error ("modred: left weight requires %d inputs", p);
  endif
  
  if (! isempty (m) && pw != m)
    error ("modred: right weight requires %d outputs", m);
  endif

  [a, b, c, d] = ssdata (sys);
  
  job = 1;
  
  ## TODO: check system size

endfunction
