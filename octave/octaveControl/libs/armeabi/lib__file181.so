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
## check order for model reduction commands

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: November 2011
## Version: 0.1

function [nr, ordsel] = __modred_check_order__ (nr, n)

  if (! issample (nr, 0) || nr != round (nr))
    error ("modred: order of reduced model must be an integer >= 0");
  endif

  if (nr > n)
    error ("modred: order of reduced model (%d) can't be larger than the original one (%d)", \
           nr, n);
  endif
  
  ordsel = 0;

endfunction