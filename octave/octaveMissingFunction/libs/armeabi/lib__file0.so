## Copyright (C) 2008 Bill Denney
##
## This software is free software; you can redistribute it and/or modify it
## under the terms of the GNU General Public License as published by
## the Free Software Foundation; either version 3 of the License, or (at
## your option) any later version.
##
## This software is distributed in the hope that it will be useful, but
## WITHOUT ANY WARRANTY; without even the implied warranty of
## MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
## General Public License for more details.
##
## You should have received a copy of the GNU General Public License
## along with this software; see the file COPYING.  If not, see
## <http://www.gnu.org/licenses/>.

## -*- texinfo -*-
## @deftypefn {Function File} {@var{status} =} __functionstatus__ (@var{funname})
## Return if a function is present in octave: -1 = not checked, 0 =
## missing, 1 = present
## @end deftypefn

function status = __functionstatus__ (funname)

  if ischar (funname)
    funname = {funname};
  elseif ~ iscellstr (funname)
    print_usage ();
  endif

  status = zeros (size (funname));
  for i = 1:numel (funname)
    if any (! (isalpha (funname{i}) | isdigit (funname{i})))
      ## not checked
      status(i) = -1;
    elseif iskeyword (funname{i}) || which (funname{i})
      ## present
      status(i) = 1;
    else
      ## missing
      status(i) = 0;
    endif
  endfor

endfunction
