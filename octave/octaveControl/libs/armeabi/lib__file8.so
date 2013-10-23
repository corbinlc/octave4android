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
## Check whether tsam is a e-by-1 cell array of valid sampling times.
## If not, it tries to convert tsam accordingly.
## Empty tsam are filled with default value -1.

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: February 2012
## Version: 0.1

function tsam = __adjust_iddata_tsam__ (tsam, e)

  if (isempty (tsam))
    tsam = num2cell (-ones (e, 1));
  elseif (iscell (tsam))
    tsam = reshape (tsam, [], 1);
  else
    tsam = {tsam};
  endif

  tmp = cellfun (@issample, tsam, {-1});
  
  if (any (! tmp))
    error ("iddata: invalid sampling time");
  endif

  nt = numel (tsam);

  if (nt == 1 && e > 1)
    tsam = repmat (tsam, e, 1);
  elseif (nt != e)
    error ("iddata: there are %d experiments, but only %d sampling times", \
           e, nt);
  endif

endfunction
