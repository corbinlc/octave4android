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
## @deftypefn {Function File} {@var{dat} =} fft (@var{dat})
## @deftypefnx {Function File} {@var{dat} =} fft (@var{dat}, @var{n})
## Transform iddata objects from time to frequency domain
## using a Fast Fourier Transform (FFT) algorithm.
##
## @strong{Inputs}
## @table @var
## @item dat
## iddata set containing signals in time-domain.
## @item n
## Length of the FFT transformations.  If @var{n} does not match
## the signal length, the signals in @var{dat} are shortened or
## padded with zeros.  @var{n} is a vector with as many elements
## as there are experiments in @var{dat} or a scalar with a common
## length for all experiments.
## If not specified, the signal lengths are taken as default values.
## @end table
##
## @strong{Outputs}
## @table @var
## @item dat
## iddata identification dataset in frequency-domain.
## In order to preserve signal power and noise level,
## the FFTs are normalized by dividing each transform
## by the square root of the signal length.
## The frequency values are distributed equally from 0
## to the Nyquist frequency.  The Nyquist frequency is
## only included for even signal lengths.
## @end table
##
## @end deftypefn

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: April 2012
## Version: 0.1

function dat = fft (dat, n = [])

  if (nargin > 2)       # no need to test nargin == 0, this is handled by built-in fft
    print_usage ();
  endif
  
  if (! dat.timedomain)
    return;
  endif

  [x, ~, ~, e] = size (dat);

  if (isempty (n))                                                  # default case, n not specified
    n = num2cell (x(:));
  elseif (is_real_vector (n) && length (n) == e && fix (n) == n)    # individual n for each experiment 
    n = num2cell (n(:));
  elseif (is_real_scalar (n) && fix (n) == n)                       # common n for all experiments
    n = num2cell (repmat (n, e, 1));
  else
    error ("iddata: fft: second argument invalid");
  endif

  dat.y = cellfun (@(y, n) fft (y, n, 1)(1:fix(n/2)+1, :) / sqrt (n), dat.y, n, "uniformoutput", false);
  dat.u = cellfun (@(u, n) fft (u, n, 1)(1:fix(n/2)+1, :) / sqrt (n), dat.u, n, "uniformoutput", false);
  ## fft (x, n, dim=1) because x could be a row vector (n=1)
  
  dat.w = cellfun (@(n, tsam) (0:fix(n/2)).' * (2*pi/abs(tsam)/n), n, dat.tsam, "uniformoutput", false);
  ## abs(tsam) because of -1 for undefined sampling times
  dat.timedomain = false;

endfunction


%!shared DATD, Y, U
%! Y = 1:10;
%! U = 20:-2:1;
%! W = warning ("query", "iddata:transpose");
%! warning ("off", W.identifier);
%! DAT = iddata (Y, U);
%! DATD = fft (DAT);
%! warning (W.identifier, W.state);
%!assert (DATD.y{1}, Y, 1e-10);
%!assert (DATD.u{1}, U, 1e-10);
