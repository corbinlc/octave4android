## Copyright (C) 2009, 2011   Lukas F. Reichlin
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
## @deftypefn {Function File} {@var{nvec} =} size (@var{sys})
## @deftypefnx {Function File} {@var{n} =} size (@var{sys}, @var{dim})
## @deftypefnx {Function File} {[@var{p}, @var{m}] =} size (@var{sys})
## LTI model size, i.e. number of outputs and inputs.
##
## @strong{Inputs}
## @table @var
## @item sys
## LTI system.
## @item dim
## If given a second argument, @command{size} will return the size of the
## corresponding dimension.
## @end table
##
## @strong{Outputs}
## @table @var
## @item nvec
## Row vector.  The first element is the number of outputs (rows) and the second
## element the number of inputs (columns).
## @item n
## Scalar value.  The size of the dimension @var{dim}.
## @item p
## Number of outputs.
## @item m
## Number of inputs.
## @end table
## @end deftypefn

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: September 2009
## Version: 0.2

function [n, varargout] = size (sys, dim = 0)

  if (nargin > 2)
    print_usage ();
  endif

  p = numel (sys.outname);  # WARNING: system matrices may change without
  m = numel (sys.inname);   #          being noticed by the i/o names!

  switch (dim)
    case 0
      switch (nargout)
        case 0
          if (p == 1)
            stry = "";
          else
            stry = "s";
          endif          
          if (m == 1)
            stru = "";
          else
            stru = "s";
          endif
          disp (sprintf ("LTI model with %d output%s and %d input%s.", p, stry, m, stru));
        case 1
          n = [p, m];
        case 2
          n = p;
          varargout{1} = m;
        otherwise
          print_usage ();
      endswitch
    case 1
      n = p;
    case 2
      n = m;
    otherwise
      print_usage ();
  endswitch

endfunction
