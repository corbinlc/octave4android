## Copyright (C) 2009   Lukas F. Reichlin
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
## @deftypefn {Function File} {[@var{props}, @var{vals}] =} __property_names__ (@var{sys})
## @deftypefnx {Function File} {[@var{props}, @var{vals}] =} __property_names__ (@var{sys}, @var{"specific"})
## Return the list of properties as well as the assignable values for a ss object sys.
## @end deftypefn

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: September 2009
## Version: 0.1

function [props, vals] = __property_names__ (sys, flg)

  ## cell vector of ss-specific properties
  props = {"a";
           "b";
           "c";
           "d";
           "e";
           "stname";
           "scaled"};

  ## cell vector of ss-specific assignable values
  vals = {"n-by-n matrix (n = number of states)";
          "n-by-m matrix (m = number of inputs)";
          "p-by-n matrix (p = number of outputs)";
          "p-by-m matrix";
          "n-by-n matrix";
          "n-by-1 cell vector of strings";
          "scalar logical value"};

  if (nargin == 1)
    [ltiprops, ltivals] = __property_names__ (sys.lti);

    props = [props;
             ltiprops];

    vals = [vals;
            ltivals];
  endif

endfunction