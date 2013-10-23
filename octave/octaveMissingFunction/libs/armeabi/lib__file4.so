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
## @deftypefn {Function File} {} matlabfunctionlist (@var{outfile}, @var{outformat}, @var{url})
## Show or write to @var{outfile} the missing Matlab functions listed at
## the category index @var{url}.  @var{outformat} is the output format
## to show or write and it may be one of "wiki" (default) or "html".
## @seealso{missingfunctionstatus}
## @end deftypefn

function missingmatlabfunctions (outfile = "", outformat = "", url = "http://www.mathworks.com/access/helpdesk/help/techdoc/ref/f16-6011.html")

  [funlist, funloc, catlist, catloc, catlvl] = __matlabfunctionlist__ (url);
  funstat = __functionstatus__ (funlist);

  ## guess the output format defaulting to wiki.
  if isempty (outformat)
    if isempty (outfile)
      outformat = "wiki";
    else
      [dirname, filename, ext] = fileparts (outfile);
      if (! isempty (findstr (ext, "htm")))
        outformat = "html";
      else
        outformat = "wiki";
      endif
    endif
  endif

  output = __missingmatlab2txt__ (funlist, funloc, funstat,
                                  catlist, catloc, catlvl, outformat);
  if isempty (outfile)
    printf ("%s", output{:});
  else
    [fid msg] = fopen (outfile, "wt");
    if (fid < 0)
      error ("missingmatlabfunctions: error opening %s, %s", outfile, msg);
    endif
    fprintf (fid, "%s", output{:});
    fclose (fid);
  endif

endfunction
