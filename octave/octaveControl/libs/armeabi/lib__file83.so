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
## @deftypefn {Function File} {@var{dat} =} filter (@var{dat}, @var{sys})
## @deftypefnx {Function File} {@var{dat} =} filter (@var{dat}, @var{b}, @var{a})
## Filter output and input signals of dataset @var{dat}.
## The filter is specified either by @acronym{LTI} system @var{sys}
## or by transfer function polynomials @var{b} and @var{a} as described
## in the help text of the built-in filter command.  Type @code{help filter}
## for more information.
##
## @strong{Inputs}
## @table @var
## @item dat
## iddata identification dataset containing signals in time-domain.
## @item sys
## @acronym{LTI} object containing the discrete-time filter.
## @item b
## Numerator polynomial of the discrete-time filter.
## Must be a row vector containing the coefficients
## of the polynomial in ascending powers of z^-1.
## @item a
## Denominator polynomial of the discrete-time filter.
## Must be a row vector containing the coefficients
## of the polynomial in ascending powers of z^-1.
## @end table
##
## @strong{Outputs}
## @table @var
## @item dat
## iddata identification dataset with filtered 
## output and input signals.
## @end table
##
## @end deftypefn

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: August 2012
## Version: 0.1

function dat = filter (dat, b, a = [], si = [])

  if (nargin < 2 || nargin > 4)
    print_usage ();
  endif
  
  if (! isa (dat, "iddata"))          # there's at least one iddata set, but not as the first argument
    error ("iddata: filter: first argument must be an iddata set");
  endif

  if (! dat.timedomain)
    error ("iddata: filter: require iddata set in time-domain");
  endif

  if (isa (b, "lti"))                 # filter (dat, sys)
    if (nargin > 3)                   # filter (dat, sys, si) has at most 3 inputs
      print_usage ();
    endif
    if (! issiso (b))
      error ("iddata: filter: second argument must be a SISO LTI system");
    endif
    si = a;                           # filter (dat, sys, si)
    if (isct (b))                     # sys is continuous-time
      b = c2d (b, dat.tsam{1});       # does this discretization/tsam make sense?
    endif
    [b, a] = filtdata (b, "vector");  # convert LTI system to transfer function
  elseif (nargin < 3)
    print_usage ();
  endif

  ## use Octave's filter function for each experiment
  ## the fifth argument '1' specifies the dimension in case of datasets with only 1 sample
  dat.y = cellfun (@(y) filter (b, a, y, si, 1), dat.y, "uniformoutput", false);
  dat.u = cellfun (@(u) filter (b, a, u, si, 1), dat.u, "uniformoutput", false);

endfunction


## TODO: adapt test
%!shared DATD, Z
%! DAT = iddata ({[(1:10).', (1:2:20).'], [(10:-1:1).', (20:-2:1).']}, {[(41:50).', (46:55).'], [(61:70).', (-66:-1:-75).']});
%! DATD = detrend (DAT, "linear");
%! Z = zeros (10, 2);
%!assert (DATD.y{1}, Z, 1e-10);
%!assert (DATD.y{2}, Z, 1e-10);
%!assert (DATD.u{1}, Z, 1e-10);
%!assert (DATD.u{2}, Z, 1e-10);
