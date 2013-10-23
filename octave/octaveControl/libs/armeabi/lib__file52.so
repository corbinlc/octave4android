## Copyright (C) 2010, 2011   Lukas F. Reichlin
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
## @deftypefn {Function File} {@var{sys} =} frd (@var{sys})
## @deftypefnx {Function File} {@var{sys} =} frd (@var{sys}, @var{w})
## @deftypefnx {Function File} {@var{sys} =} frd (@var{H}, @var{w}, @dots{})
## @deftypefnx {Function File} {@var{sys} =} frd (@var{H}, @var{w}, @var{tsam}, @dots{})
## Create or convert to frequency response data.
##
## @strong{Inputs}
## @table @var
## @item sys
## LTI model to be converted to frequency response data.
## If second argument @var{w} is omitted, the interesting
## frequency range is calculated by the zeros and poles of @var{sys}.
## @item H
## Frequency response array (p-by-m-by-lw).  H(i,j,k) contains the
## response from input j to output i at frequency k.  In the SISO case,
## a vector (lw-by-1) or (1-by-lw) is accepted as well.
## @item w
## Frequency vector (lw-by-1) in radian per second [rad/s].
## Frequencies must be in ascending order.
## @item tsam
## Sampling time in seconds.  If @var{tsam} is not specified,
## a continuous-time model is assumed.
## @item @dots{}
## Optional pairs of properties and values.
## Type @command{set (frd)} for more information.
## @end table
##
## @strong{Outputs}
## @table @var
## @item sys
## Frequency response data object.
## @end table
##
## @strong{Option Keys and Values}
## @table @var
## @item 'H'
## Frequency response array.  See 'Inputs' for details.
##
## @item 'w'
## Frequency vector.  See 'Inputs' for details.
##
## @item 'tsam'
## Sampling time.  See 'Inputs' for details.
##
## @item 'inname'
## The name of the input channels in @var{sys}.
## Cell vector of length m containing strings.
## Default names are @code{@{'u1', 'u2', ...@}}
##
## @item 'outname'
## The name of the output channels in @var{sys}.
## Cell vector of length p containing strings.
## Default names are @code{@{'y1', 'y2', ...@}}
##
## @item 'name'
## String containing the name of the model.
##
## @item 'notes'
## String or cell of string containing comments.
##
## @item 'userdata'
## Any data type.
## @end table
##
## @seealso{dss, ss, tf}
## @end deftypefn

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: February 2010
## Version: 0.1

function sys = frd (H = [], w = [], varargin)

  ## NOTE: * There's no such thing as a static gain
  ##         because FRD objects are measurements,
  ##         not models.
  ##       * If something like  sys1 = frd (5)  existed,
  ##         it would cause troubles in cases like
  ##         sys2 = ss (...), sys = sys1 * sys2
  ##         because sys2 needs to be converted to FRD,
  ##         but sys1 contains no valid frequencies.
  ##       * However, things like  frd (ss (5))  should
  ##         be possible.

  ## model precedence: frd > ss > zpk > tf > double
  superiorto ("ss", "zpk", "tf", "double");
  
  argc = 0;                             # initialize argument count

  switch (nargin)
    case 0                              # empty object  sys = frd ()
      tsam = -2;                        # undefined sampling time

    case 1
      if (isa (H, "frd"))               # already in frd form  sys = frd (frdsys)
        sys = H;
        return;                         # nothing more to do here
      elseif (isa (H, "lti"))           # another lti object  sys = frd (sys)
        [sys, alti] = __sys2frd__ (H);
        sys.lti = alti;                 # preserve lti properties
        return;                         # nothing more to do here
      else                              # sys = frd (H)  *must* fail
        print_usage ();
      endif

    case 2
      if (isa (H, "lti"))               # another lti object  sys = frd (sys, w)
        [sys, alti] = __sys2frd__ (H, w);
        sys.lti = alti;                 # preserve lti properties
        return;                         # nothing more to do here
      else                              # sys = frd (H, w)
        tsam = 0;                       # continuous-time
      endif

    otherwise                           # default case
      argc = numel (varargin);          # number of additional arguments after H and w
      if (issample (varargin{1}, -10))  # sys = frd (H, w, tsam, "prop1", val1, ...)
        tsam = varargin{1};             # sampling time, could be 0 as well
        argc--;                         # tsam is not a property-value pair
        if (argc > 0)                   # if there are any properties and values ...
          varargin = varargin(2:end);   # remove tsam from property-value list
        endif
      else                              # sys = frd (H, w, "prop1", val1, ...)
        tsam = 0;                       # continuous-time
      endif
  endswitch

  [H, w] = __adjust_frd_data__ (H, w);
  [p, m] = __frd_dim__ (H, w);          # determine number of outputs and inputs

  frdata = struct ("H", H, "w", w);     # struct for frd-specific data
  ltisys = lti (p, m, tsam);            # parent class for general lti data

  sys = class (frdata, "frd", ltisys);  # create frd object

  if (argc > 0)                         # if there are any properties and values, ...
    sys = set (sys, varargin{:});       # use the general set function
  endif

endfunction
