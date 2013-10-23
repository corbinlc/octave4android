## Copyright (C) 2009, 2010   Lukas F. Reichlin
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
## @deftypefn {Function File} {@var{sys} =} ss (@var{sys})
## @deftypefnx {Function File} {@var{sys} =} ss (@var{d})
## @deftypefnx {Function File} {@var{sys} =} ss (@var{a}, @var{b})
## @deftypefnx {Function File} {@var{sys} =} ss (@var{a}, @var{b}, @var{c})
## @deftypefnx {Function File} {@var{sys} =} ss (@var{a}, @var{b}, @var{c}, @var{d}, @dots{})
## @deftypefnx {Function File} {@var{sys} =} ss (@var{a}, @var{b}, @var{c}, @var{d}, @var{tsam}, @dots{})
## Create or convert to state-space model.
##
## @strong{Inputs}
## @table @var
## @item sys
## LTI model to be converted to state-space.
## @item a
## State matrix (n-by-n).
## @item b
## Input matrix (n-by-m).
## @item c
## Output matrix (p-by-n).
## If @var{c} is empty @code{[]} or not specified, an identity matrix is assumed.
## @item d
## Feedthrough matrix (p-by-m).
## If @var{d} is empty @code{[]} or not specified, a zero matrix is assumed.
## @item tsam
## Sampling time in seconds.  If @var{tsam} is not specified, a continuous-time model is assumed.
## @item @dots{}
## Optional pairs of properties and values.
## Type @command{set (ss)} for more information.
## @end table
##
## @strong{Outputs}
## @table @var
## @item sys
## State-space model.
## @end table
##
## @strong{Option Keys and Values}
## @table @var
## @item 'a', 'b', 'c', 'd', 'e'
## State-space matrices.  See 'Inputs' for details.
##
## @item 'stname'
## The name of the states in @var{sys}.
## Cell vector containing strings for each state.
## Default names are @code{@{'x1', 'x2', ...@}}
##
## @item 'scaled'
## Logical.  If set to true, no automatic scaling is used,
## e.g. for frequency response plots.
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
## @strong{Example}
## @example
## @group
## octave:1> a = [1 2 3; 4 5 6; 7 8 9];
## octave:2> b = [10; 11; 12];
## octave:3> stname = @{"V", "A", "kJ"@};
## octave:4> sys = ss (a, b, [], [], "stname", stname)
## @end group
## @end example
## 
## @example
## @group
## sys.a =
##         V   A  kJ
##    V    1   2   3
##    A    4   5   6
##    kJ   7   8   9
## @end group
## @end example
## 
## @example
## @group
## sys.b =
##        u1
##    V   10
##    A   11
##    kJ  12
## @end group
## @end example
## 
## @example
## @group
## sys.c =
##         V   A  kJ
##    y1   1   0   0
##    y2   0   1   0
##    y3   0   0   1
## @end group
## @end example
## 
## @example
## @group
## sys.d =
##        u1
##    y1   0
##    y2   0
##    y3   0
## 
## Continuous-time model.
## octave:5> 
## @end group
## @end example
##
## @seealso{tf, dss}
## @end deftypefn

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: September 2009
## Version: 0.3

function sys = ss (a = [], b = [], c = [], d = [], varargin)

  ## model precedence: frd > ss > zpk > tf > double
  ## inferiorto ("frd");
  superiorto ("zpk", "tf", "double");

  argc = 0;                             # initialize argument count
  tsam = 0;                             # initialize sampling time

  if (nargin == 1)
    if (isa (a, "ss"))                  # already in ss form  sys = ss (sssys)
      sys = a;
      return;
    elseif (isa (a, "lti"))             # another lti object  sys = ss (sys)
      [sys, alti] = __sys2ss__ (a);
      sys.lti = alti;                   # preserve lti properties
      return;
    elseif (is_real_matrix (a))         # static gain  sys = ss (5), sys = ss (matrix)
      d = a;
      a = [];
    else
      print_usage ();
    endif
  elseif (nargin > 4)                   # default case  sys = ss (a, b, c, d, "prop1", val1, ...)
    argc = numel (varargin);            # number of additional arguments after d
    if (issample (varargin{1}, -10))    # sys = ss (a, b, c, d, tsam, "prop1, "val1", ...)
      tsam = varargin{1};               # sampling time, could be 0 as well
      argc--;                           # tsam is not a property-value pair
      if (argc > 0)                     # if there are any properties and values ...
        varargin = varargin(2:end);     # remove tsam from property-value list
      endif
    endif
  endif                                 # nothing to do for ss (), ss (a, b), ss (a, b, c), ss (a, b, c, d)

  [a, b, c, d, tsam] = __adjust_ss_data__ (a, b, c, d, tsam);
  [p, m, n] = __ss_dim__ (a, b, c, d);  # determine number of outputs, inputs and states

  stname = repmat ({""}, n, 1);         # cell with empty state names

  ssdata = struct ("a", a, "b", b,
                   "c", c, "d", d,
                   "e", [],
                   "stname", {stname},
                   "scaled", false);    # struct for ss-specific data

  ltisys = lti (p, m, tsam);            # parent class for general lti data

  sys = class (ssdata, "ss", ltisys);   # create ss object

  if (argc > 0)                         # if there are any properties and values, ...
    sys = set (sys, varargin{:});       # use the general set function
  endif

endfunction
