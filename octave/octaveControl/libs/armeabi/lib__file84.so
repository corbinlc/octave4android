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
## @deftypefn {Function File} {} get (@var{dat})
## @deftypefnx {Function File} {@var{value} =} get (@var{dat}, @var{"property"})
## @deftypefnx {Function File} {[@var{val1}, @var{val2}, @dots{}] =} get (@var{dat}, @var{"prop1"}, @var{"prop2"}, @dots{})
## Access property values of iddata objects.
## Type @command{get(dat)} to display a list of available properties.
## @end deftypefn

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: February 2012
## Version: 0.1

function varargout = get (dat, varargin)

  if (nargin == 1)
    [props, vals] = __property_names__ (dat);
    nrows = numel (props);
    str = strjust (strvcat (props), "right");
    str = horzcat (repmat ("   ", nrows, 1), str, repmat (":  ", nrows, 1), strvcat (vals));
    disp (str);
  else
    for k = 1 : (nargin-1)
      prop = lower (varargin{k});

      switch (prop)
        case {"y", "outdata", "outputdata"}
          val = dat.y;
        case {"u", "indata", "inputdata"}
          val = dat.u;
        case {"outname", "outputname"}
          val = dat.outname;
        case {"inname", "inputname"}
          val = dat.inname;
        case {"outunit", "outputunit"}
          val = dat.outunit;
        case {"inunit", "inputunit"}
          val = dat.inunit;
        case {"tsam", "ts"}
          val = dat.tsam;
        case {"timeunit"}
          val = dat.timeunit
        case {"expname", "experimentname"}
          val = dat.expname;
        case "name"
          val = dat.name;
        case "notes"
          val = dat.notes;
        case "userdata"
          val = dat.userdata;
        case {"domain", "timedomain"}
          val = dat.timedomain;
        case {"w", "frequency", "samplinginstants"}
          val = dat.w;
        otherwise
          error ("iddata: get: invalid property name '%s'", varargin{k});
      endswitch

      varargout{k} = val;
    endfor
  endif

endfunction