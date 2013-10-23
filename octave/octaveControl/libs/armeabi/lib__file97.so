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
## @deftypefn {Function File} {@var{dat} =} vertcat (@var{dat1}, @var{dat2}, @dots{})
## Vertical concatenation of iddata datasets.
## The samples are concatenated in the following way:
## @code{dat.y@{e@} = [dat1.y@{e@}; dat2.y@{e@}; @dots{}]}
## @code{dat.u@{e@} = [dat1.u@{e@}; dat2.u@{e@}; @dots{}]}
## where @var{e} denotes the experiment.
## The number of experiments, outputs and inputs must be equal for all datasets.
## @end deftypefn

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: March 2012
## Version: 0.1

function dat = vertcat (varargin)

  dat = cat (1, varargin{:});

endfunction
