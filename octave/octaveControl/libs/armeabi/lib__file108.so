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
## Return default labels if cell "name" contains only empty strings.
## If not, check whether individual strings of the cell "name" are
## empty and mark them with "?".  Used by display routines.

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: October 2009
## Version: 0.3

function [name, n] = __labels__ (name, variable = "x")

  n = numel (name);

  if (n == 0 || isequal ("", name{:}))
    name = strseq (variable, 1:n);
  else
    idx = cellfun (@isempty, name);
    name(idx) = "?";
  endif

endfunction