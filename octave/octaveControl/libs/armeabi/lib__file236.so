## Copyright (C) 2009, 2010, 2011, 2012   Lukas F. Reichlin
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
## Display routine for TF objects.

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: September 2009
## Version: 0.4

function display (sys)

  sysname = inputname (1);
  [inname, outname, tsam] = __lti_data__ (sys.lti);

  [inname, m] = __labels__ (inname, "u");
  [outname, p] = __labels__ (outname, "y");

  disp ("");
  
  if (sys.inv)
    [num, den] = filtdata (sys);
  else
    num = sys.num;
    den = sys.den;
  endif

  for nu = 1 : m
    disp (["Transfer function '", sysname, "' from input '", inname{nu}, "' to output ..."]);
    disp ("");
    for ny = 1 : p
      __disp_frac__ (num{ny, nu}, den{ny, nu}, sys.tfvar, outname{ny});
    endfor
  endfor

  display (sys.lti);  # display sampling time

  if (tsam == -2)
    disp ("Static gain.");
  elseif (tsam == 0)
    disp ("Continuous-time model.");
  else
    disp ("Discrete-time model.");
  endif

endfunction


function __disp_frac__ (num, den, tfvar, name)

  MAX_LEN = 12;  # max length of output name
  
  tfp = isa (num, "tfpoly");

  if (num == tfpoly (0))
    str = [" ", name, ":  0"];
  elseif ((tfp && den == 1) || (! tfp && isequal (den, 1)))
  ## elseif (den == tfpoly (1)) doesn't work because it
  ## would mistakingly accept non-tfpoly denominators like [0, 1]
    str = [" ", name, ":  "];
    numstr = tfpoly2str (num, tfvar);
    str = [str, numstr];
  ##elseif (length (den) == 1)  # de-comment for non-development use
  ##  str = [" ", name, ":  "];
  ##  num = num * (1/get (den));
  ##  numstr = tfpoly2str (num, tfvar);
  ##  str = [str, numstr];
  else
    numstr = tfpoly2str (num, tfvar);
    denstr = tfpoly2str (den, tfvar);
    fracstr = repmat ("-", 1, max (length (numstr), length (denstr)));

    str = strjust (strvcat (numstr, fracstr, denstr), "center");

    namestr = name(:, 1 : min (MAX_LEN, end));
    namestr = [namestr, ":  "];
    namestr = strjust (strvcat (" ", namestr, " "), "left");
    namestr = horzcat (repmat (" ", 3, 1), namestr);

    str = [namestr, str];
  endif

  disp (str);
  disp ("");

endfunction

