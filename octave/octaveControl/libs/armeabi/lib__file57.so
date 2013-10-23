## Copyright (C) 2010   Lukas F. Reichlin
##
## This program is free software: you can redistribute it and/or modify
## it under the terms of the GNU General Public License as published by
## the Free Software Foundation, either version 3 of the License, or
## (at your option) any later version.
##
## This program is distributed in the hope that it will be useful,
## but WITHOUT ANY WARRANTY; without even the implied warranty of
## MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
## GNU General Public License for more details.
##
## You should have received a copy of the GNU General Public License
## along with this program. If not, see <http://www.gnu.org/licenses/>.

## -*- texinfo -*-
## @deftypefn {Function File} {[@var{props}, @var{vals}] =} __property_names__ (@var{sys})
## @deftypefnx {Function File} {[@var{props}, @var{vals}] =} __property_names__ (@var{sys}, @var{"specific"})
## Return the list of properties as well as the assignable values for a frd object sys.
## @end deftypefn

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: October 2010
## Version: 0.1

function [props, vals] = __property_names__ (sys, flg)

  ## cell vector of tf-specific properties
  props = {"H";
           "w"};

  ## cell vector of frd-specific assignable values
  vals = {"p-by-m-by-l array of complex frequency responses";
          "l-by-1 vector of real frequencies (l = length (w))"};

  if (nargin == 1)
    [ltiprops, ltivals] = __property_names__ (sys.lti);

    props = [props;
             ltiprops];

    vals = [vals;
            ltivals];
  endif

endfunction