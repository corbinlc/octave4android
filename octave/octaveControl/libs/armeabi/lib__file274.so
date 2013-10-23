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
## @deftypefn {Function File} {@var{str} =} tfpoly2str (@var{p})
## @deftypefnx {Function File} {@var{str} =} tfpoly2str (@var{p}, @var{tfvar})
## Return the string of a polynomial with string @var{tfvar} as variable.
## @end deftypefn

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: May 2012
## Version: 0.1

function str = tfpoly2str (p, tfvar = "x")

  ## TODO: simplify this ugly code

  str = "";

  lp = numel (p);

  if (lp > 0)               # first element (lowest order)
    idx = find (p);         # first non-zero element
    if (isempty (idx))
      str = "0";
      return;
    else
      idx = idx(1);
    endif
    a = p(idx);

    if (a < 0)
      cs = "-";
    else
      cs = "";
    endif
    
    if (idx == 1)
      str = [cs, num2str(abs (a), 4)];
    else
      if (abs (a) == 1)
        str = [cs, __variable__(tfvar, idx-1)];
      else
        str = [cs, __coefficient__(a), " ", __variable__(tfvar, idx-1)];
      endif
    endif

    if (lp > idx)           # remaining elements of higher order
      for k = idx+1 : lp
        a = p(k);

        if (a != 0)
          if (a < 0)
            cs = " - ";
          else
            cs = " + ";
          endif

          if (abs (a) == 1)
            str = [str, cs, __variable__(tfvar, k-1)];
          else
            str = [str, cs, __coefficient__(a), " ", __variable__(tfvar, k-1)];
          endif
        endif
      endfor

    endif
  endif

endfunction


function str = __coefficient__ (a)

  b = abs (a);  

  if (b == 1)
    str = "";
  else
    str = num2str (b, 4);
  endif

endfunction


function str = __variable__ (tfvar, n)

  str = [tfvar, "^-", num2str(n)];

endfunction
