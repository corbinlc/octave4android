## Copyright (C) 2010 Soren Hauberg <soren@hauberg.org>
##
## This program is free software; you can redistribute it and/or modify it under
## the terms of the GNU General Public License as published by the Free Software
## Foundation; either version 3 of the License, or (at your option) any later
## version.
##
## This program is distributed in the hope that it will be useful, but WITHOUT
## ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
## FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
## details.
##
## You should have received a copy of the GNU General Public License along with
## this program; if not, see <http://www.gnu.org/licenses/>.

## -*- texinfo -*-
## @deftypefn {Function File} {@var{c} =} combnk (@var{data}, @var{k})
## Return all combinations of @var{k} elements in @var{data}.
## @end deftypefn

function retval = combnk (data, k)
  ## Check input
  if (nargin != 2)
    print_usage;
  elseif (! isvector (data))
    error ("combnk: first input argument must be a vector");
  elseif (!isreal (k) || k != round (k) || k < 0)
    error ("combnk: second input argument must be a non-negative integer");
  endif

  ## Simple checks
  n = numel (data);
  if (k == 0 || k > n)
    retval = resize (data, 0, k);
  elseif (k == n)
    retval = data (:).';
  else
    retval = __combnk__ (data, k);
  endif

  ## For some odd reason Matlab seems to treat strings differently compared to other data-types...
  if (ischar (data))
     retval = flipud (retval);
  endif
endfunction

function retval = __combnk__ (data, k)
  ## Recursion stopping criteria
  if (k == 1)
    retval = data (:);
  else
    ## Process data
    n = numel (data);
    if iscell (data)
      retval = {};
    else
      retval = [];
    endif
    for j = 1:n
      C = __combnk__ (data ((j+1):end), k-1);
      C = cat (2, repmat (data (j), rows (C), 1), C);
      if (!isempty (C))
        retval = [retval; C];
      endif
    endfor
  endif
endfunction

%!demo
%! c = combnk (1:5, 2);
%! disp ("All pairs of integers between 1 and 5:");
%! disp (c);

%!test
%! c = combnk (1:3, 2);
%! assert (c, [1, 2; 1, 3; 2, 3]);

%!test
%! c = combnk (1:3, 6);
%! assert (isempty (c));

%!test
%! c = combnk ({1, 2, 3}, 2);
%! assert (c, {1, 2; 1, 3; 2, 3});

%!test
%! c = combnk ("hello", 2);
%! assert (c, ["lo"; "lo"; "ll"; "eo"; "el"; "el"; "ho"; "hl"; "hl"; "he"]);
