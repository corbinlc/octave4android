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
## Subscripted reference for iddata objects.
## Used by Octave for "dat = dat(2:4, :)" or "val = dat.prop".

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: February 2012
## Version: 0.2

function a = subsref (a, s)

  if (numel (s) == 0)
    return;
  endif

  switch (s(1).type)
    case "()"
      idx = s(1).subs;
      if (numel (idx) > 4)
        error ("iddata: subsref: need four or less indices");
      else
        a = __dat_prune__ (a, idx{:}); 
      endif
    case "."
      fld = s(1).subs;
      a = get (a, fld);
    otherwise
      error ("iddata: subsref: invalid subscript type");
  endswitch

  a = subsref (a, s(2:end));

endfunction


function dat = __dat_prune__ (dat, spl_idx = ":", out_idx = ":", in_idx = ":", exp_idx = ":")

  dat.y = dat.y(exp_idx);
  dat.y = cellfun (@(y) y(spl_idx, out_idx), dat.y, "uniformoutput", false);
  dat.outname = dat.outname(out_idx);
  dat.outunit = dat.outunit(out_idx);

  if (! isempty (dat.u))
    dat.u = dat.u(exp_idx);
    dat.u = cellfun (@(u) u(spl_idx, in_idx), dat.u, "uniformoutput", false);
    dat.inname = dat.inname(in_idx);
    dat.inunit = dat.inunit(in_idx);
  endif

  dat.expname = dat.expname(exp_idx);
  dat.tsam = dat.tsam(exp_idx);

endfunction
