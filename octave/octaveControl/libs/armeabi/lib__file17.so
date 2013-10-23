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
## @deftypefn {Function File} {} bode (@var{sys})
## @deftypefnx {Function File} {} bode (@var{sys1}, @var{sys2}, @dots{}, @var{sysN})
## @deftypefnx {Function File} {} bode (@var{sys1}, @var{sys2}, @dots{}, @var{sysN}, @var{w})
## @deftypefnx {Function File} {} bode (@var{sys1}, @var{'style1'}, @dots{}, @var{sysN}, @var{'styleN'})
## @deftypefnx {Function File} {[@var{mag}, @var{pha}, @var{w}] =} bode (@var{sys})
## @deftypefnx {Function File} {[@var{mag}, @var{pha}, @var{w}] =} bode (@var{sys}, @var{w})
## Bode diagram of frequency response.  If no output arguments are given,
## the response is printed on the screen.
##
## @strong{Inputs}
## @table @var
## @item sys
## LTI system.  Must be a single-input and single-output (SISO) system.
## @item w
## Optional vector of frequency values.  If @var{w} is not specified,
## it is calculated by the zeros and poles of the system.
## Alternatively, the cell @code{@{wmin, wmax@}} specifies a frequency range,
## where @var{wmin} and @var{wmax} denote minimum and maximum frequencies
## in rad/s.
## @item 'style'
## Line style and color, e.g. 'r' for a solid red line or '-.k' for a dash-dotted
## black line.  See @command{help plot} for details.
## @end table
##
## @strong{Outputs}
## @table @var
## @item mag
## Vector of magnitude.  Has length of frequency vector @var{w}.
## @item pha
## Vector of phase.  Has length of frequency vector @var{w}.
## @item w
## Vector of frequency values used.
## @end table
##
## @seealso{nichols, nyquist, sigma}
## @end deftypefn

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: November 2009
## Version: 0.5

function [mag_r, pha_r, w_r] = bode (varargin)

  if (nargin == 0)
    print_usage ();
  endif

  [H, w] = __frequency_response__ (varargin, false, 0, "std", false);
  
  H = cellfun (@reshape, H, {[]}, {1}, "uniformoutput", false);
  mag = cellfun (@abs, H, "uniformoutput", false);
  pha = cellfun (@(H) unwrap (arg (H)) * 180 / pi, H, "uniformoutput", false);

  if (! nargout)
    mag_db = cellfun (@(mag) 20 * log10 (mag), mag, "uniformoutput", false);

    tmp = cellfun (@isa, varargin, {"lti"});
    sys_idx = find (tmp);
    tmp = cellfun (@ischar, varargin);
    style_idx = find (tmp);

    len = numel (H);  
    mag_args = {};
    pha_args = {};
    legend_args = {};

    for k = 1:len
      if (k == len)
        lim = nargin;
      else
        lim = sys_idx(k+1);
      endif
      style = varargin(style_idx(style_idx > sys_idx(k) & style_idx <= lim));
      mag_args = cat (2, mag_args, w(k), mag_db(k), style);
      pha_args = cat (2, pha_args, w(k), pha(k), style);
      try
        legend_args = cat (2, legend_args, inputname(sys_idx(k)));  # watch out for bode (lticell{:})
      end_try_catch
    endfor

    subplot (2, 1, 1)
    semilogx (mag_args{:})
    axis ("tight")
    ylim (__axis_margin__ (ylim))
    grid ("on")
    title ("Bode Diagram")
    ylabel ("Magnitude [dB]")

    subplot (2, 1, 2)
    semilogx (pha_args{:})
    axis ("tight")
    ylim (__axis_margin__ (ylim))
    grid ("on")
    xlabel ("Frequency [rad/s]")
    ylabel ("Phase [deg]")
    legend (legend_args)
  else
    mag_r = mag{1};
    pha_r = pha{1};
    w_r = w{1};
  endif

endfunction
