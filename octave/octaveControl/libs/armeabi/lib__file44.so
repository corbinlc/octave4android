## Copyright (C) 2010   Lukas F. Reichlin
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
## @deftypefn {Function File} {@var{sys} =} dss (@var{sys})
## @deftypefnx {Function File} {@var{sys} =} dss (@var{d})
## @deftypefnx {Function File} {@var{sys} =} dss (@var{a}, @var{b}, @var{c}, @var{d}, @var{e}, @dots{})
## @deftypefnx {Function File} {@var{sys} =} dss (@var{a}, @var{b}, @var{c}, @var{d}, @var{e}, @var{tsam}, @dots{})
## Create or convert to descriptor state-space model.
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
## @item d
## Feedthrough matrix (p-by-m).
## @item e
## Descriptor matrix (n-by-n).
## @item tsam
## Sampling time in seconds.  If @var{tsam} is not specified, 
## a continuous-time model is assumed.
## @item @dots{}
## Optional pairs of properties and values.
## Type @command{set (dss)} for more information.
## @end table
##
## @strong{Outputs}
## @table @var
## @item sys
## Descriptor state-space model.
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
## @strong{Equations}
## @example
## @group
##   .
## E x = A x + B u
##   y = C x + D u
## @end group
## @end example
##
## @seealso{ss, tf}
## @end deftypefn

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: September 2010
## Version: 0.1

function sys = dss (varargin)

  switch (nargin)
    case {0, 1}  # static gain (dss (5)) or empty (useful for "set (dss)")
      sys = ss (varargin{:});

    case {2, 3, 4}
      print_usage ();

    otherwise    # general case
      sys = ss (varargin{[1:4, 6:end]}, "e", varargin{5});
  endswitch

endfunction

## NOTE: The author prefers "dss (e, a, b, c, d)" since we write
##         .
##       E x = A x + B u,     y = C x + D u
##
##       but this would break compatibility to a widespread
##       commercial implementation of the octave language.
##       There's no way to tell e and d apart if n = m = p.
