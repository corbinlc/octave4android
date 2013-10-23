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
## Return frequency response H and frequency vector w.
## If w is empty, it will be calculated by __frequency_vector__.

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: November 2009
## Version: 0.5

function [H, w] = __frequency_response__ (args, mimoflag = 0, resptype = 0, wbounds = "std", cellflag = false)

  isc = iscell (args);

  if (! isc)
    args = {args};
  endif

  sys_idx = cellfun (@isa, args, {"lti"});      # look for LTI models
  w_idx = cellfun (@(x) is_real_vector (x) && length (x) > 1, args);  # look for frequency vectors
  r_idx = cellfun (@iscell, args);              # look for frequency ranges {wmin, wmax}
  
  sys_cell = args(sys_idx);                     # extract LTI models
  frd_idx = cellfun (@isa, sys_cell, {"frd"});  # look for FRD models

  ## check arguments
  if (! mimoflag && ! all (cellfun (@issiso, sys_cell)))
    error ("frequency_response: require SISO systems");
  endif

  ## determine frequencies
  if (any (r_idx))                              # if there are frequency ranges
    r = args(r_idx){end};                       # take the last one
    if (numel (r) == 2 && issample (r{1}) && issample (r{2}))
      w = __frequency_vector__ (sys_cell, wbounds, r{1}, r{2});
    else
      error ("frequency_response: invalid cell");
    endif
  elseif (any (w_idx))                          # are there any frequency vectors?
    w = args(w_idx){end};
    w = repmat ({w}, 1, numel (sys_cell));
  else                                          # there are neither frequency ranges nor vectors
    w = __frequency_vector__ (sys_cell, wbounds);
  endif

  w_frd = w(frd_idx);                           # temporarily save frequency vectors of FRD models
  w(frd_idx) = {[]};                            # freqresp returns all frequencies of FRD models for w=[]

  ## compute frequency response H for all LTI models
  H = cellfun (@__freqresp__, sys_cell, w, {resptype}, {cellflag}, "uniformoutput", false);

  ## restore frequency vectors of FRD models in w
  w(frd_idx) = w_frd;

  if (! isc)                                    # for old non-multiplot functions
    H = H{1};
    w = w{1};
  endif

endfunction
