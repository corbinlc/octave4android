## Copyright (C) 2010   Lukas F. Reichlin
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
## Transpose of SS models.

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: February 2010
## Version: 0.2

function sys = __transpose__ (sys)

  a = sys.a;
  b = sys.b;
  c = sys.c;
  d = sys.d;
  e = sys.e;
  
  sys.stname = repmat ({""}, rows (a), 1);

  sys.a = a.';
  sys.b = c.';
  sys.c = b.';
  sys.d = d.';
  sys.e = e.';

endfunction