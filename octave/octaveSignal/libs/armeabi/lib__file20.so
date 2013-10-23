## Copyright (C) 1994 Dept of Probability Theory and Statistics TU Wien <Andreas.Weingessel@ci.tuwien.ac.at>
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

## usage:  cceps (x [, correct])
##
## Returns the complex cepstrum of the vector x.
## If the optional argument correct has the value 1, a correction
## method is applied.  The default is not to do this.

## Author: Andreas Weingessel <Andreas.Weingessel@ci.tuwien.ac.at>
## Apr 1, 1994
## Last modifified by AW on Nov 8, 1994

function cep = cceps (x, c)

  if (nargin == 1)
    c = 0;
  elseif (nargin != 2)
    print_usage;
  endif

  [nr, nc] = size (x);
  if (nc != 1)
    if (nr == 1)
      x = x';
      nr = nc;
    else
      error ("cceps: x must be a vector");
    endif
  endif

  bad_signal_message = ["cceps:  bad signal x, ", ...
      "some Fourier coefficients are zero."];
  
  F = fft (x);
  if (min (abs (F)) == 0)
    error (bad_signal_message);
  endif

  # determine if correction necessary
  half = fix (nr / 2);
  cor = 0;
  if (2 * half == nr)
    cor = (c && (real (F (half + 1)) < 0));
    if (cor)
      F = fft (x(1:nr-1))
      if (min (abs (F)) == 0)
        error (bad_signal_message);
      endif
    endif
  endif

  cep = fftshift (ifft (log (F)));

  # make result real
  if (c)
    cep = real (cep);
    if (cor)      
      # make cepstrum of same length as input vector
      cep (nr) = 0;
    endif
  endif

endfunction
