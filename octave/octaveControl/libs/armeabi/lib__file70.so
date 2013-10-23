## Copyright (C) 1996, 2000, 2004, 2005, 2006, 2007
##               Auburn University. All rights reserved.
## Copyright (C) 2009 - 2012   Lukas F. Reichlin
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
## @deftypefn {Function File} {@var{w} =} __frequency_vector__ (@var{sys})
## Get default range of frequencies based on cutoff frequencies of system
## poles and zeros.
## Frequency range is the interval
## @iftex
## @tex
## $ [ 10^{w_{min}}, 10^{w_{max}} ] $
## @end tex
## @end iftex
## @ifnottex
## [10^@var{wmin}, 10^@var{wmax}]
## @end ifnottex
##
## Used by @command{__frequency_response__}
## @end deftypefn

## Adapted-By: Lukas Reichlin <lukas.reichlin@gmail.com>
## Date: October 2009
## Version: 0.4

function w = __frequency_vector__ (sys_cell, wbounds = "std", wmin, wmax)

  isc = iscell (sys_cell);

  if (! isc)                                    # __sys2frd__ methods pass LTI models not in cells
    sys_cell = {sys_cell};
  endif

  idx = cellfun (@(x) isa (x, "lti"), sys_cell);
  sys_cell = sys_cell(idx);
  len = numel (sys_cell);
  
  [dec_min, dec_max, zp] = cellfun (@__frequency_range__, sys_cell, {wbounds}, "uniformoutput", false);

  if (strcmpi (wbounds, "std"))                 # plots with explicit frequencies

    if (nargin == 4)                        # w = {wmin, wmax}  
      dec_min = log10 (wmin);
      dec_max = log10 (wmax);
    else
      dec_min = min (cell2mat (dec_min));
      dec_max = max (cell2mat (dec_max));
    endif

    zp = horzcat (zp{:});

    ## include zeros and poles for nice peaks in plots
    idx = find (zp > 10^dec_min & zp < 10^dec_max);
    zp = zp(idx);

    w = logspace (dec_min, dec_max, 500);
    w = unique ([w, zp]);                       # unique also sorts frequency vector

    w = repmat ({w}, 1, len);                   # return cell of frequency vectors

  elseif (strcmpi (wbounds, "ext"))             # plots with implicit frequencies

    if (nargin == 4)
      dec_min = repmat ({log10 (wmin)}, 1, len);
      dec_max = repmat ({log10 (wmax)}, 1, len);
    endif

    idx = cellfun (@(zp, dec_min, dec_max) find (zp > 10^dec_min & zp < 10^dec_max), \
                   zp, dec_min, dec_max, "uniformoutput", false);
    zp = cellfun (@(zp, idx) zp(idx), zp, idx, "uniformoutput", false);

    w = cellfun (@logspace, dec_min, dec_max, {500}, "uniformoutput", false);
    w = cellfun (@(w, zp) unique ([w, zp]), w, zp, "uniformoutput", false);
    ## unique also sorts frequency vector

  else
    error ("frequency_vector: invalid argument 'wbounds'");
  endif

  if (! isc)                                    # for __sys2frd__ methods 
    w = w{1};
  endif

endfunction


function [dec_min, dec_max, zp] = __frequency_range__ (sys, wbounds = "std")

  if (isa (sys, "frd"))
    w = get (sys, "w");
    dec_min = log10 (w(1));
    dec_max = log10 (w(end));
    zp = [];
    return;
  endif
    
  zer = zero (sys);
  pol = pole (sys);
  tsam = abs (get (sys, "tsam"));               # tsam could be -1
  discrete = ! isct (sys);                      # static gains (tsam = -2) are assumed continuous
  
  ## make sure zer, pol are row vectors
  pol = reshape (pol, 1, []);
  zer = reshape (zer, 1, []);

  ## check for natural frequencies away from omega = 0
  if (discrete)
    ## The 2nd conditions prevents log(0) in the next log command
    iiz = find (abs(zer-1) > norm(zer)*eps && abs(zer) > norm(zer)*eps);
    iip = find (abs(pol-1) > norm(pol)*eps && abs(pol) > norm(pol)*eps);

    ## avoid dividing empty matrices, it would work but looks nasty
    if (! isempty (iiz))
      czer = log (zer(iiz))/tsam;
    else
      czer = [];
    endif

    if (! isempty (iip))
      cpol = log (pol(iip))/tsam;
    else
      cpol = [];
    endif
  else
    ## continuous
    iip = find (abs(pol) > norm(pol)*eps);
    iiz = find (abs(zer) > norm(zer)*eps);

    if (! isempty (zer))
      czer = zer(iiz);
    else
      czer = [];
    endif
    if (! isempty (pol))
      cpol = pol(iip);
    else
      cpol = [];
    endif
  endif
  
  if (isempty (iip) && isempty (iiz))
    ## no poles/zeros away from omega = 0; pick defaults
    dec_min = 0;                                # -1
    dec_max = 2;                                # 3
  else
    dec_min = floor (log10 (min (abs ([cpol, czer]))));
    dec_max = ceil (log10 (max (abs ([cpol, czer]))));
  endif

  ## expand to show the entirety of the "interesting" portion of the plot
  switch (wbounds)
    case "std"                                  # standard
      if (dec_min == dec_max)
        dec_min -= 2;
        dec_max += 2;
      else
        dec_min--;
        dec_max++;
      endif
    case "ext"                                  # extended (for nyquist)
      if (any (abs (pol) < sqrt (eps)))         # look for integrators
        ## dec_min -= 0.5;
        dec_max += 2;
      else 
        dec_min -= 2;
        dec_max += 2;
      endif
    otherwise
      error ("frequency_range: second argument invalid");
  endswitch

  ## run discrete frequency all the way to pi
  if (discrete)
    dec_max = log10 (pi/tsam);
  endif

  ## include zeros and poles for nice peaks in plots
  zp = [abs(zer), abs(pol)];

endfunction
