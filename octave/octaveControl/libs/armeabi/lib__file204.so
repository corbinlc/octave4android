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
## @deftypefn {Function File} {} sigma (@var{sys})
## @deftypefnx {Function File} {} sigma (@var{sys1}, @var{sys2}, @dots{}, @var{sysN})
## @deftypefnx {Function File} {} sigma (@var{sys1}, @var{sys2}, @dots{}, @var{sysN}, @var{w})
## @deftypefnx {Function File} {} sigma (@var{sys1}, @var{'style1'}, @dots{}, @var{sysN}, @var{'styleN'})
## @deftypefnx{Function File} {[@var{sv}, @var{w}] =} sigma (@var{sys})
## @deftypefnx{Function File} {[@var{sv}, @var{w}] =} sigma (@var{sys}, @var{w})
## @deftypefnx{Function File} {[@var{sv}, @var{w}] =} sigma (@var{sys}, @var{[]}, @var{ptype})
## @deftypefnx{Function File} {[@var{sv}, @var{w}] =} sigma (@var{sys}, @var{w}, @var{ptype})
## Singular values of frequency response.  If no output arguments are given,
## the singular value plot is printed on the screen.
##
## @strong{Inputs}
## @table @var
## @item sys
## LTI system.  Multiple inputs and/or outputs (MIMO systems) make practical sense.
## @item w
## Optional vector of frequency values.  If @var{w} is not specified,
## it is calculated by the zeros and poles of the system.
## Alternatively, the cell @code{@{wmin, wmax@}} specifies a frequency range,
## where @var{wmin} and @var{wmax} denote minimum and maximum frequencies
## in rad/s.
## @item ptype = 0
## Singular values of the frequency response @var{H} of system @var{sys}.  Default Value.
## @item ptype = 1
## Singular values of the frequency response @code{inv(H)}; i.e. inversed system.
## @item ptype = 2
## Singular values of the frequency response @code{I + H}; i.e. inversed sensitivity
## (or return difference) if @code{H = P * C}.
## @item ptype = 3
## Singular values of the frequency response @code{I + inv(H)}; i.e. inversed complementary
## sensitivity if @code{H = P * C}.
## @item 'style'
## Line style and color, e.g. 'r' for a solid red line or '-.k' for a dash-dotted
## black line.  See @command{help plot} for details.
## @end table
##
## @strong{Outputs}
## @table @var
## @item sv
## Array of singular values.  For a system with m inputs and p outputs, the array sv
## has @code{min (m, p)} rows and as many columns as frequency points @code{length (w)}.
## The singular values at the frequency @code{w(k)} are given by @code{sv(:,k)}.
## @item w
## Vector of frequency values used.
## @end table
##
## @seealso{bodemag, svd}
## @end deftypefn

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: May 2009
## Version: 0.7

function [sv_r, w_r] = sigma (varargin)

  if (nargin == 0)
    print_usage ();
  endif
resptype = 0;
  [H, w] = __frequency_response__ (varargin, true, resptype, "std", true);

  sv = cellfun (@(H) cellfun (@svd, H, "uniformoutput", false), H, "uniformoutput", false);
  sv = cellfun (@(sv) horzcat (sv{:}), sv, "uniformoutput", false);

  if (! nargout)  # plot the information

    ## convert to dB for plotting
    sv_db = cellfun (@(sv) 20 * log10 (sv), sv, "uniformoutput", false);

    tmp = cellfun (@isa, varargin, {"lti"});
    sys_idx = find (tmp);
    tmp = cellfun (@ischar, varargin);
    style_idx = find (tmp);

    len = numel (H);  
    plot_args = {};
    legend_args = cell (len, 1);
    colororder = get (gca, "colororder");
    rc = rows (colororder);

    for k = 1:len
      col = colororder(1+rem (k-1, rc), :);
      if (k == len)
        lim = nargin;
      else
        lim = sys_idx(k+1);
      endif
      style = varargin(style_idx(style_idx > sys_idx(k) & style_idx <= lim));
      if (isempty (style))
        plot_args = cat (2, plot_args, w(k), sv_db(k), {"-", "color", col});
      else
        plot_args = cat (2, plot_args, w(k), sv_db(k), style);
      endif
      try
        legend_args{k} = inputname(sys_idx(k));  # watch out for sigma (lticell{:})
      catch
        legend_args{k} = "";
      end_try_catch
    endfor

    ## adjust line colors in legend  
    idx = horzcat (1, cellfun (@rows, sv_db)(1:end-1));
    idx = cumsum (idx);

    ## plot results
    h = semilogx (plot_args{:});
    axis ("tight")
    ylim (__axis_margin__ (ylim))
    grid ("on")
    title ("Singular Values")
    xlabel ("Frequency [rad/s]")
    ylabel ("Singular Values [dB]")
    legend (h(idx), legend_args)
  else            # return values
    sv_r = sv{1};
    w_r = reshape (w{1}, [], 1);
  endif

endfunction


%!shared sv_exp, w_exp, sv_obs, w_obs
%! A = [1, 2; 3, 4];
%! B = [5, 6; 7, 8];
%! C = [4, 3; 2, 1];
%! D = [8, 7; 6, 5];
%! w = [2, 3, 4];
%! sv_exp = [7.9176, 8.6275, 9.4393;
%!           0.6985, 0.6086, 0.5195];
%! w_exp = [2; 3; 4];
%! [sv_obs, w_obs] = sigma (ss (A, B, C, D), w);
%!assert (sv_obs, sv_exp, 1e-4);
%!assert (w_obs, w_exp, 1e-4);
