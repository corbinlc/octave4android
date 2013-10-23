## Copyright (C) 2012 Philip Nienhuis <prnienhuis@users.sf.net>
## 
## This program is free software; you can redistribute it and/or modify
## it under the terms of the GNU General Public License as published by
## the Free Software Foundation; either version 3 of the License, or
## (at your option) any later version.
## 
## This program is distributed in the hope that it will be useful,
## but WITHOUT ANY WARRANTY; without even the implied warranty of
## MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
## GNU General Public License for more details.
## 
## You should have received a copy of the GNU General Public License
## along with Octave; see the file COPYING.  If not, see
## <http://www.gnu.org/licenses/>.

## chk_jar_entries - internal function finding Java .jar names in javaclasspath

## Author: Philip Nienhuis <prnienhuis@users.sf.net>
## Created: 2012-10-07
## Updates:
## 2012-10-23 Style fixes
## 2012-12-18 Add option for range of names per required entry
##     ''     Tests added

function [ retval, missing ] = chk_jar_entries (jcp, entries, dbug=0)

  retval = 0;
  missing = zeros (1, numel (entries));
  for jj=1:length (entries)
    found = 0;
    for ii=1:length (jcp)
      ## Get jar (or folder/map/subdir) name from java classpath entry
      jentry = strsplit (lower (jcp{ii}), filesep){end};
      kk = 0;
      while (++kk <= size (char (entries{jj}), 1) && ! found)
        if (~isempty (strfind (jentry, lower (char (entries{jj})(kk, :)))))
          ++retval; 
          found = 1;
          if (dbug > 2)
            fprintf ("  - %s OK\n", jentry); 
          endif
        endif
      endwhile
    endfor
    if (~found)
      if (dbug > 2)
        if (iscellstr (entries{jj}))
          entrtxt = sprintf ("%s/", entries{jj}{:}); entrtxt(end) = "";
        else
          entrtxt = entries{jj};
        endif
        printf ("  %s....jar missing\n", entrtxt);
      endif
      missing(jj) = 1;
    endif
  endfor

endfunction

%!test
%! entries = {"abc", {"def", "ghi"}, "jkl"};
%! jcp1 = {"/usr/lib/java/abcx.jar", "/usr/lib/java/defz.jar", "/usr/lib/java/jkl3.jar"};
%! jcp1 = strrep (jcp1, "/", filesep);
%! assert (chk_jar_entries (jcp1, entries), 3);

%!test
%! entries = {"abc", {"def", "ghi"}, "xyz"};
%! jcp2 = {"/usr/lib/java/abcy.jar", "/usr/lib/java/ghiw.jar", "/usr/lib/java/jkl6.jar"};
%! jcp2 = strrep (jcp2, "/", filesep);
%! [aaa, bbb] = chk_jar_entries (jcp2, entries);
%! assert (aaa, 2);
%! assert (bbb, [0 0 1]);


