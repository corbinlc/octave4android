## Copyright (C) 2009, 2010, 2012   Lukas F. Reichlin
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
## @deftypefn {Function File} {} set (@var{sys})
## @deftypefnx {Function File} {} set (@var{sys}, @var{"property"}, @var{value}, @dots{})
## @deftypefnx {Function File} {@var{retsys} =} set (@var{sys}, @var{"property"}, @var{value}, @dots{})
## Set or modify properties of LTI objects.
## If no return argument @var{retsys} is specified, the modified LTI object is stored
## in input argument @var{sys}.  @command{set} can handle multiple properties in one call:
## @code{set (sys, 'prop1', val1, 'prop2', val2, 'prop3', val3)}.
## @code{set (sys)} prints a list of the object's property names.
## @end deftypefn

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: October 2009
## Version: 0.4

function retsys = set (sys, varargin)

  if (nargin == 1)       # set (sys), sys = set (sys)

    [props, vals] = __property_names__ (sys);
    nrows = numel (props);

    str = strjust (strvcat (props), "right");
    str = horzcat (repmat ("   ", nrows, 1), str, repmat (":  ", nrows, 1), strvcat (vals));

    disp (str);

    if (nargout != 0)    # function sys = set (sys, varargin)
      retsys = sys;      # would lead to unwanted output when using
    endif                # set (sys)

  else                   # set (sys, "prop1", val1, ...), sys = set (sys, "prop1", val1, ...)

    if (rem (nargin-1, 2))
      error ("lti: set: properties and values must come in pairs");
    endif

    [p, m] = size (sys);

    for k = 1 : 2 : (nargin-1)
      prop = lower (varargin{k});
      val = varargin{k+1};

      switch (prop)
        case {"inname", "inputname"}
          sys.inname = __adjust_labels__ (val, m);

        case {"outname", "outputname"}
          sys.outname = __adjust_labels__ (val, p);

        case {"tsam", "ts"}
          if (issample (val, -1))
            sys.tsam = val;
            warning ("lti: set: use the editing of property '%s' with caution", prop);
            warning ("          it may lead to corrupted models");
          else
            error ("lti: set: invalid sampling time");
          endif
          ## TODO: use of c2d, d2c and d2d if tsam changes?

        case "name"
          if (ischar (val))
            sys.name = val;
          else
            error ("lti: set: property 'name' requires a string");
          endif

        case "notes"
          if (iscellstr (val))
            sys.notes = val;
          elseif (ischar (val))
            sys.notes = {val};
          else
            error ("lti: set: property 'notes' requires string or cell of strings");
          endif

        case "userdata"
          sys.userdata = val;

        otherwise
          sys = __set__ (sys, prop, val);
      endswitch
    endfor

    if (nargout == 0)    # set (sys, "prop1", val1, ...)
      assignin ("caller", inputname (1), sys);
    else                 # sys = set (sys, "prop1", val1, ...)
      retsys = sys;
    endif

  endif

endfunction