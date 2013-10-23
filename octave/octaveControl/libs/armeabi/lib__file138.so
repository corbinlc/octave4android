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
## Constructor for LTI objects.  For internal use only.

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: September 2009
## Version: 0.2

function ltisys = lti (p = 0, m = 0, tsam = -2)

  inname = repmat ({""}, m, 1);
  outname = repmat ({""}, p, 1);

  ltisys = struct ("tsam", tsam,
                   "inname", {inname},
                   "outname", {outname},
                   "name", "",
                   "notes", {{}},
                   "userdata", []);

  ltisys = class (ltisys, "lti");

endfunction