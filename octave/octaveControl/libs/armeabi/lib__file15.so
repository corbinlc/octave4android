## Copyright (C) 1998, 2000, 2004, 2005, 2007
##               Auburn University.  All rights reserved.
##
##
## This program is free software; you can redistribute it and/or modify it
## under the terms of the GNU General Public License as published by
## the Free Software Foundation; either version 3 of the License, or (at
## your option) any later version.
##
## This program is distributed in the hope that it will be useful, but
## WITHOUT ANY WARRANTY; without even the implied warranty of
## MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
## General Public License for more details.
##
## You should have received a copy of the GNU General Public License
## along with this program; see the file COPYING.  If not, see
## <http://www.gnu.org/licenses/>.

## -*- texinfo -*-
## @deftypefn {Function File} {} __axis_margin__ (@var{axdata})
## Determine axis limits for 2-D data (column vectors); leaves a 10%
## margin around the plots.
## Inserts margins of +/- 0.1 if data is one-dimensional 
## (or a single point).
##
## @strong{Input}
## @table @var
## @item axdata
## @var{n} by 2 matrix of data [@var{x}, @var{y}].
## @end table
##
## @strong{Output}
## @table @var
## @item axvec
## Vector of axis limits appropriate for call to @command{axis} function.
## @end table
## @end deftypefn

function axvec = __axis_margin__ (axdata)

  ## compute axis limits
  minv = axdata(1);
  maxv = axdata(2);
  delv = (maxv-minv)/2;             # breadth of the plot
  midv = (minv + maxv)/2;           # midpoint of the plot
  axmid = [midv, midv];
  axdel = [-0.1, 0.1];              # default plot width (if less than 2-d data)
  
  if (delv == 0)
    if (midv != 0)
      axdel = [-0.1*midv, 0.1*midv];
    endif
  else
    ## they're at least one-dimensional
    tolv = max(1e-8, 1e-8*abs(midv));
    if (abs (delv) >= tolv)
      axdel = 1.1*[-delv,delv];
    endif
  endif
  
  axvec = axmid + axdel;

endfunction
