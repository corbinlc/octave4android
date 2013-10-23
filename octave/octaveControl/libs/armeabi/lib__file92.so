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
## @deftypefn {Function File} {@var{dat} =} resample (@var{dat}, @var{p}, @var{q})
## @deftypefnx {Function File} {@var{dat} =} resample (@var{dat}, @var{p}, @var{q}, @var{n})
## @deftypefnx {Function File} {@var{dat} =} resample (@var{dat}, @var{p}, @var{q}, @var{h})
## Change the sample rate of the output and input signals in dataset @var{dat}
## by a factor of @code{p/q}.  This is performed using a polyphase algorithm.
## The anti-aliasing @acronym{FIR} filter can be specified as follows:
## Either by order @var{n} (scalar) with default value 0.  The band edges
## are then chosen automatically.  Or by impulse response @var{h} (vector).
## Requires the signal package to be installed.
##
## @strong{Algorithm}@*
## Uses commands @command{fir1} and @command{resample}
## from the signal package.
##
## @strong{References}@*
## [1] J. G. Proakis and D. G. Manolakis,
## Digital Signal Processing: Principles, Algorithms, and Applications,
## 4th ed., Prentice Hall, 2007. Chap. 6
##
## [2] A. V. Oppenheim, R. W. Schafer and J. R. Buck,
## Discrete-time signal processing, Signal processing series,
## Prentice-Hall, 1999
##
## @end deftypefn

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: June 2012
## Version: 0.1

function dat = resample (dat, p, q, n = 0)

  if (nargin < 3 || nargin > 4)
    print_usage ();
  endif

  ## requires signal package
  try
    pkg load signal;
  catch
    error ("iddata: resample: please install signal package to proceed");
  end_try_catch

  if (is_real_scalar (n))      # fourth scalar argument n is the order of the anti-aliasing filter
    h = fir1 (n, 1/q);
  elseif (is_real_vector (n))  # fourth vector argument is the (impulse response of the) anti-aliasing filter
    h = n;
  else
    error ("iddata: resample: fourth argument invalid");
  endif

  dat.y = cellfun (@resample, dat.y, {p}, {q}, {h}, "uniformoutput", false);
  dat.u = cellfun (@resample, dat.u, {p}, {q}, {h}, "uniformoutput", false);

endfunction
