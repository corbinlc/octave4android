## Copyright (C) 2008 Bill Denney
##
## This software is free software; you can redistribute it and/or modify it
## under the terms of the GNU General Public License as published by
## the Free Software Foundation; either version 3 of the License, or (at
## your option) any later version.
##
## This software is distributed in the hope that it will be useful, but
## WITHOUT ANY WARRANTY; without even the implied warranty of
## MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
## General Public License for more details.
##
## You should have received a copy of the GNU General Public License
## along with this software; see the file COPYING.  If not, see
## <http://www.gnu.org/licenses/>.

## -*- texinfo -*-
## @deftypefn {Function File} {} __missingmatlab2txt__ (@var{funlist}, @var{funloc}, @var{funstat}, @var{catlist}, @var{catloc}, @var{catlvl}, @var{outformat})
## Convert the function and category information into text.  The
## @var{outformat} can be one of "wiki" or "html".
## @end deftypefn

function output = __missingmatlab2txt__ (funlist, funloc, funstat, catlist, catloc, catlvl, outformat = "wiki")

  ## this will be to keep track of what we are currently looking at,
  ## functions will be considered at category level 99.
  funlvl = 99;
  statstr = {"Not Checked" "Missing" "Present"};

  allloc = [catloc(:);funloc(:)];
  [alloc, sidx] = sort (allloc);
  allname = [catlist(:);funlist(:)](sidx);
  allstat = [zeros(numel(catlist), 1);funstat(:)](sidx);
  alllvl = [catlvl(:);funlvl*ones(numel(funlist), 1)](sidx);

  switch lower (outformat)
    case "wiki"
      output = __towiki__ (alllvl, allname, allstat, funlvl, statstr);
    case "html"
      output = __tohtml__ (alllvl, allname, allstat, funlvl, statstr);
    otherwise
      error ("missingmatlab2txt: invalid outformat (%s)", outformat);
  endswitch

endfunction

function output = __towiki__ (alllvl, allname, allstat, funlvl, statstr)

  output = cell (size (allname));
  for i = 1:numel (alllvl)
    if (alllvl(i) == funlvl)
      output{i} = sprintf ("||%s||%s||\n", allname{i}, statstr{allstat(i)+2});
    else
      hstring = char ("="*ones (1, alllvl(i)));
      output{i} = sprintf ("\n%s%s%s\n", hstring, allname{i}, hstring)
    endif
  endfor

endfunction

function output = __tohtml__ (alllvl, allname, allstat, funlvl, statstr)

  output = cell (size (allname));

  lastwasfun = false ();
  idx = 1;
  output{idx} = "<html xmlns=\"http://www.w3.org/1999/xhtml\"><head><title>Matlab Functions Missing from Octave</title><link rel=\"stylesheet\" type=\"text/css\" href=\"missingmatlab.css\"/></head><body>\n";

  for i = 1:numel (alllvl)
    if (alllvl(i) == funlvl)
      if (! lastwasfun)
        output{++idx} = "<table>\n";
      endif

      thisclass = statstr{allstat(i)+2};
      thisclass(isspace (thisclass)) = [];
      output{++idx} = sprintf ("<tr class=\"%s\"><td class=\"funname\">%s</td><td class=\"funstatus\">%s</td></tr>\n",
                               thisclass, allname{i}, statstr{allstat(i)+2});
      lastwasfun = true ();
    else
      if lastwasfun
        output{++idx} = "</table>\n";
      endif
      hstring = sprintf ("h%d", alllvl(i));
      output{++idx} = sprintf ("\n<%s>%s</%s>\n", hstring, allname{i}, hstring);
      lastwasfun = false ();
    endif
  endfor

  if lastwasfun
    output{++idx} = "</table>\n";
  endif
  output{++idx} = "</body></html>";

endfunction

