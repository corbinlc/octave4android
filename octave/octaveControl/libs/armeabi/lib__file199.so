## Copyright (C) 2009, 2011, 2012   Lukas F. Reichlin
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
## @deftypefn {Function File} {} pzmap (@var{sys})
## @deftypefnx {Function File} {} pzmap (@var{sys1}, @var{sys2}, @dots{}, @var{sysN})
## @deftypefnx {Function File} {} pzmap (@var{sys1}, @var{'style1'}, @dots{}, @var{sysN}, @var{'styleN'})
## @deftypefnx {Function File} {[@var{p}, @var{z}] =} pzmap (@var{sys})
## Plot the poles and zeros of an LTI system in the complex plane.
## If no output arguments are given, the result is plotted on the screen.
## Otherwise, the poles and zeros are computed and returned.
##
## @strong{Inputs}
## @table @var
## @item sys
## LTI model.
## @item 'style'
## Line style and color, e.g. 'r' for a solid red line or '-.k' for a dash-dotted
## black line.  See @command{help plot} for details.
## @end table
##
## @strong{Outputs}
## @table @var
## @item p
## Poles of @var{sys}.
## @item z
## Transmission zeros of @var{sys}.
## @end table
## @end deftypefn

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: November 2009
## Version: 0.2

function [pol_r, zer_r] = pzmap (varargin)

  if (nargin == 0)
    print_usage ();
  endif

  sys_idx = cellfun (@isa, varargin, {"lti"});      # look for LTI models

  pol = cellfun (@pole, varargin(sys_idx), "uniformoutput", false);
  zer = cellfun (@zero, varargin(sys_idx), "uniformoutput", false);

  if (! nargout)
    pol_re = cellfun (@real, pol, "uniformoutput", false);
    pol_im = cellfun (@imag, pol, "uniformoutput", false);
    zer_re = cellfun (@real, zer, "uniformoutput", false);
    zer_im = cellfun (@imag, zer, "uniformoutput", false);
    
    sys_idx = find (sys_idx);
    tmp = cellfun (@ischar, varargin);
    style_idx = find (tmp);

    len = numel (pol);
    pol_args = {};
    zer_args = {};
    legend_args = cell (len, 1);
    colororder = get (gca, "colororder");
    rc = rows (colororder);
    
    for k = 1 : len
      col = colororder(1+rem (k-1, rc), :);
      if (k == len)
        lim = nargin;
      else
        lim = sys_idx(k+1);
      endif
      style = varargin(style_idx(style_idx > sys_idx(k) & style_idx <= lim));
      if (isempty (style))
        pol_args = cat (2, pol_args, pol_re{k}, pol_im{k}, {"x", "color", col});
        zer_args = cat (2, zer_args, zer_re{k}, zer_im{k}, {"o", "color", col});
      else
        pol_args = cat (2, pol_args, pol_re{k}, pol_im{k}, style);
        zer_args = cat (2, zer_args, zer_re{k}, zer_im{k}, style);     
      endif
      try
        legend_args{k} = inputname(sys_idx(k));
      catch
        legend_args{k} = "";
      end_try_catch
    endfor
      
    ## FIXME: try to combine "x", "o" and style for custom colors

    h = plot (pol_args{:}, zer_args{:});
    grid ("on")  
    title ("Pole-Zero Map")
    xlabel ("Real Axis")
    ylabel ("Imaginary Axis")
    legend (h(1:len), legend_args)
  else
    pol_r = pol{1};
    zer_r = zer{1};
  endif
  
endfunction
