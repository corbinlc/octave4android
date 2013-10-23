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
## @deftypefn {Function File} {[@var{funlist}, @var{funloc}, @var{catlist}, @var{catloc}, @var{catlvl}] =} __matlabfunctionlist__ (@var{url})
## Return a list of matlab functions, @var{funlist}, from the @var{url}.
## It will also return the list of locations of the functions within the
## file, the categories in the url (@var{catlist}), the location of the
## categories within the url (@var{catloc}), and the level of the
## categories (@var{catlvl}, 2 = h2, 3 = h3, ...).  Note, the locations
## may not be sorted or unique.
## @end deftypefn

function [funlist, funloc, catlist, catloc, catlvl] = __matlabfunctionlist__ (url = "http://www.mathworks.com/access/helpdesk/help/techdoc/ref/f16-6011.html")

  [raw, success, message] = urlread(url);
  if (! success)
    warning ("__matlabfunctionlist__:url",
             "__matlabfunctionlist__: Could not read\n%s\n%s", url, message);
    funlist = {};
    funloc = [];
    catlist = {};
    catloc = [];
    catlvl = [];
    return
  endif
  ## Building up a really rough single-purpose XML parser

  ## Tags
  tagopen = find (raw == "<");
  tagclose = find (raw == ">");
  ## categories
  h2loc = findstr (raw, "<h2");
  ## sub-categories
  h3loc = findstr (raw, "<h3");
  ## sub-sub categories (optional)
  h4loc = findstr (raw, "<h4");

  ## Find the names of each section
  h2name = getname (raw, h2loc, tagopen, tagclose);
  h3name = getname (raw, h3loc, tagopen, tagclose);
  h4name = getname (raw, h4loc, tagopen, tagclose);

  catlist = [h2name(:);h3name(:);h4name(:)];
  catloc = [h2loc(:);h3loc(:);h4loc(:)];
  catlvl = [2*ones(numel(h2name),1);
            3*ones(numel(h3name),1);
            4*ones(numel(h4name),1)];

  tmpfunloc = findstr (raw, "<tr valign=\"top\"><td width=\"150\"><a");
  ## this is not quite right, but it is the minimum size required.
  funlist = cell (numel (tmpfunloc), 1);
  funloc = zeros (numel (tmpfunloc), 1);
  idx = 0;
  for i = 1:numel (tmpfunloc)
    tmpfunname = raw(tagclose(find (tagclose > tmpfunloc(i), 3)(3))+1:
                          tagopen(find (tagopen > tmpfunloc(i), 3)(3))-1);
    ## convert all whitespace to actual spaces
    tmpfunname(isspace (tmpfunname)) = " ";
    if (numel (tmpfunname) > 2)
      tmpfunname = split (tmpfunname, ", ");
    endif
    for j = 1:rows (tmpfunname)
      idx++;
      funlist{idx} = strtrim (tmpfunname(j,:));
      funloc(idx) = tmpfunloc(i);
    endfor
  endfor

endfunction

function name = getname (raw, loc, tagopen, tagclose)

  name = cell (size (loc));
  for i = 1:numel (loc)
    name{i} = raw(tagclose(find (tagclose > loc(i), 1))+1:tagopen(find (tagopen > loc(i), 1))-1);
  endfor

endfunction
