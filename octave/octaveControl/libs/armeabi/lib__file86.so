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
## @deftypefn{Function File} {@var{dat} =} iddata (@var{y})
## @deftypefnx{Function File} {@var{dat} =} iddata (@var{y}, @var{u})
## @deftypefnx{Function File} {@var{dat} =} iddata (@var{y}, @var{u}, @var{tsam}, @dots{})
## @deftypefnx{Function File} {@var{dat} =} iddata (@var{y}, @var{u}, @var{[]}, @dots{})
## Create identification dataset of output and input signals.
##
## @strong{Inputs}
## @table @var
## @item y
## Real matrix containing the output signal in time-domain.
## For a system with @var{p} outputs and @var{n} samples,
## @var{y} is a n-by-p matrix.
## For data from multiple experiments, @var{y} becomes a
## e-by-1 or 1-by-e cell vector of n(i)-by-p matrices,
## where @var{e} denotes the number of experiments
## and n(i) the individual number of samples for each experiment.
## @item u
## Real matrix containing the input signal in time-domain.
## For a system with @var{m} inputs and @var{n} samples,
## @var{u} is a n-by-m matrix.
## For data from multiple experiments, @var{u} becomes a
## e-by-1 or 1-by-e cell vector of n(i)-by-m matrices,
## where @var{e} denotes the number of experiments
## and n(i) the individual number of samples for each experiment.
## If @var{u} is not specified or an empy element @code{[]} is passed,
## @var{dat} becomes a time series dataset.
## @item tsam
## Sampling time.  If not specified, default value -1 (unspecified) is taken.
## For multi-experiment data, @var{tsam} becomes a
## e-by-1 or 1-by-e cell vector containing individual
## sampling times for each experiment.  If a scalar @var{tsam}
## is provided, then all experiments have the same sampling time.
## @item @dots{}
## Optional pairs of properties and values.
## @end table
##
## @strong{Outputs}
## @table @var
## @item dat
## iddata identification dataset.
## @end table
##
## @strong{Option Keys and Values}
## @table @var
## @item 'expname'
## The name of the experiments in @var{dat}.
## Cell vector of length e containing strings.
## Default names are @code{@{'exp1', 'exp2', ...@}}
##
## @item 'y'
## Output signals.  See 'Inputs' for details.
##
## @item 'outname'
## The name of the output channels in @var{dat}.
## Cell vector of length p containing strings.
## Default names are @code{@{'y1', 'y2', ...@}}
##
## @item 'outunit'
## The units of the output channels in @var{dat}.
## Cell vector of length p containing strings.
##
## @item 'u'
## Input signals.  See 'Inputs' for details.
##
## @item 'inname'
## The name of the input channels in @var{dat}.
## Cell vector of length m containing strings.
## Default names are @code{@{'u1', 'u2', ...@}}
##
## @item 'inunit'
## The units of the input channels in @var{dat}.
## Cell vector of length m containing strings.
##
## @item 'tsam'
## Sampling time.  See 'Inputs' for details.
##
## @item 'timeunit'
## The units of the sampling times in @var{dat}.
## Cell vector of length e containing strings.
##
## @item 'name'
## String containing the name of the dataset.
##
## @item 'notes'
## String or cell of string containing comments.
##
## @item 'userdata'
## Any data type.
## @end table
## @end deftypefn

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: October 2011
## Version: 0.1

function dat = iddata (y = {}, u = {}, tsam = {}, varargin)

  if (nargin == 1 && isa (y, "iddata"))
    dat = y;
    return;
  elseif (nargin < 1)
    print_usage ();
  endif

  [y, u] = __adjust_iddata__ (y, u);
  [p, m, e] = __iddata_dim__ (y, u);
  tsam = __adjust_iddata_tsam__ (tsam, e);

  outname = repmat ({""}, p, 1);
  inname = repmat ({""}, m, 1);
  expname = repmat ({""}, e, 1);

  dat = struct ("y", {y}, "outname", {outname}, "outunit", {outname},
                "u", {u}, "inname", {inname}, "inunit", {inname},
                "tsam", {tsam}, "timeunit", {""},
                "timedomain", true, "w", {{}},
                "expname", {expname},
                "name", "", "notes", {{}}, "userdata", []);

  dat = class (dat, "iddata");
  
  if (nargin > 3)
    dat = set (dat, varargin{:});
  endif

  if (dat.timedomain && ! is_real_matrix (dat.y{:}, dat.u{:}))
    error ("iddata: require real-valued input and output signals for time domain datasets");
  endif

endfunction


%!error (iddata);
%!error (iddata ((1:10).', (1:11).'));
%!warning (iddata (1:10));
%!warning (iddata (1:10, 1:10));


