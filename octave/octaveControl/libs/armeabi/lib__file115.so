## Copyright (C) 2009   Lukas F. Reichlin
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
## @deftypefn {Function File} {@var{sys} =} connect (@var{sys}, @var{cm}, @var{inputs}, @var{outputs})
## Arbitrary interconnections between the inputs and outputs of an LTI model.
## @end deftypefn

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: October 2009
## Version: 0.1

function sys = connect (sys, cm, in_idx, out_idx)

  if (nargin != 4)
    print_usage ();
  endif

  [p, m] = size (sys);
  [cmrows, cmcols] = size (cm);

  ## TODO: proper argument checking
  ## TODO: name-based interconnections
  ## TODO: replace nested for-if statement

  if (! is_real_matrix (cm))
    error ("connect: second argument must be a matrix with real coefficients");
  endif

  M = zeros (m, p);
  in = cm(:, 1);
  out = cm(:, 2:cmcols);

  for a = 1 : cmrows
    for b = 1 : cmcols-1
      if (out(a, b) != 0)
        M(in(a, 1), abs (out(a, b))) = sign (out(a, b));
      endif
    endfor
  endfor

  sys = __sys_connect__ (sys, M);
  sys = __sys_prune__ (sys, out_idx, in_idx);

endfunction