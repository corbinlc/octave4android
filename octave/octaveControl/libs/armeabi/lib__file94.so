## Copyright (C) 2011, 2012   Lukas F. Reichlin
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
## @deftypefn {Function File} {@var{nvec} =} size (@var{dat})
## @deftypefnx {Function File} {@var{ndim} =} size (@var{dat}, @var{dim})
## @deftypefnx {Function File} {[@var{n}, @var{p}, @var{m}, @var{e}] =} size (@var{dat})
## Return dimensions of iddata set @var{dat}.
##
## @strong{Inputs}
## @table @var
## @item dat
## iddata set.
## @item dim
## If given a second argument, @command{size} will return the size of the
## corresponding dimension.
## @end table
##
## @strong{Outputs}
## @table @var
## @item nvec
## Row vector.  The first element is the total number of samples (rows of dat.y and dat.u).
## The second element is the number of outputs (columns of dat.y) and the third element
## the number of inputs (columns of dat.u).  The fourth element is the number of experiments.
## @item ndim
## Scalar value.  The size of the dimension @var{dim}.
## @item n
## Row vector containing the number of samples of each experiment.
## @item p
## Number of outputs.
## @item m
## Number of inputs.
## @item e
## Number of experiments.
## @end table
## @end deftypefn

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: October 2011
## Version: 0.1

function [x, p, m, e] = size (dat, dim = 0)

  if (nargin > 2)
    print_usage ();
  endif

  n = cellfun (@rows, dat.y).';     # number of samples
  p = numel (dat.outname);          # number of output channels
  m = numel (dat.inname);           # number of input channels
  e = numel (dat.y);                # number of experiments

  switch (dim)
    case 0                          # ... size (dat)
      switch (nargout)
        case 0                      # size (dat)
          stry = stru = stre = "";
          if (p != 1)
            stry = "s";
          endif          
          if (m != 1)
            stru = "s";
          endif
          if (e != 1)
            stre = "s";
          endif
          printf ("IDDATA set with [%s] samples, %d output%s, %d input%s and %d experiment%s.\n", \
                  num2str (n, "%d "), p, stry, m, stru, e, stre);
        case 1                      # x = size (dat)
          x = [sum(n), p, m, e];
        case {2, 3, 4}              # [n, p, m, e] = size (dat)
          x = n;
        otherwise                   # more than 4 return values
          print_usage ();
      endswitch
    case 1                          # nvec = size (dat, 1)
      x = n;
    case 2                          # p = size (dat, 2)
      x = p;
    case 3                          # m = size (dat, 3)
      x = m;
    case 4                          # e = size (dat, 4)
      x = e;
    otherwise                       # invalid dimension
      print_usage ();
  endswitch

endfunction
