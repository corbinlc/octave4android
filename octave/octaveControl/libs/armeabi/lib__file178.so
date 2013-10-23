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
## check alpha for model reduction commands

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: November 2011
## Version: 0.1

function alpha = __modred_check_alpha__ (alpha, dt)

  if (! is_real_scalar (alpha))
    error ("modred: argument alpha must be a real scalar");
  endif
  if (dt)  # discrete-time
    if (alpha < 0 || alpha > 1)
      error ("modred: require 0 <= ALPHA <= 1");
    endif
  else     # continuous-time
    if (alpha > 0)
      error ("modred: require ALPHA <= 0");
    endif
  endif

endfunction
