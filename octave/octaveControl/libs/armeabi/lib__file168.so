## Copyright (C) 2011   Lukas F. Reichlin
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
## @deftypefn {Function File} {[@var{z}, @var{p}, @var{k}, @var{tsam}] =} zpkdata (@var{sys})
## @deftypefnx {Function File} {[@var{z}, @var{p}, @var{k}, @var{tsam}] =} zpkdata (@var{sys}, @var{"v"})
## Access zero-pole-gain data.
##
## @strong{Inputs}
## @table @var
## @item sys
## Any type of LTI model.
## @item "v", "vector"
## For SISO models, return @var{z} and @var{p} directly as column vectors
## instead of cells containing a single column vector.
## @end table
##
## @strong{Outputs}
## @table @var
## @item z
## Cell of column vectors containing the zeros for each channel.
## z@{i,j@} contains the zeros from input j to output i.
## @item p
## Cell of column vectors containing the poles for each channel.
## p@{i,j@} contains the poles from input j to output i.
## @item k
## Matrix containing the gains for each channel.
## k(i,j) contains the gain from input j to output i.
## @item tsam
## Sampling time in seconds.  If @var{sys} is a continuous-time model,
## a zero is returned.
## @end table
## @end deftypefn

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: September 2011
## Version: 0.1

function [z, p, k, tsam] = zpkdata (sys, rtype = "cell")

  [num, den, tsam] = tfdata (sys);

  z = cellfun (@roots, num, "uniformoutput", false);
  p = cellfun (@roots, den, "uniformoutput", false);
  k = cellfun (@(n,d) n(1)/d(1), num, den);

  if (strncmpi (rtype, "v", 1) && issiso (sys))
    z = z{1};
    p = p{1};
  endif

endfunction
