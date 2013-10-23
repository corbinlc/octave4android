## Copyright (C) 2012   Lukas F. Reichlin
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
## @deftypefn {Function File} {@var{bool} =} size_equal (@var{a}, @var{b}, @dots{})
## Return true if LTI models (and matrices) @var{a}, @var{b}, @dots{}
## are of equal size and false otherwise.
## @end deftypefn

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: October 2012
## Version: 0.1

function bool = size_equal (varargin)

  s = cellfun (@size, varargin, "uniformoutput", false);
  
  bool = (nargin == 1 || isequal (s{:}));  # isequal errors out with only 1 argument, nargin==0 handled by built-in size_equal

endfunction
