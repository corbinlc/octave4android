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
## @deftypefn {Function File} {} plot (@var{dat})
## @deftypefnx {Function File} {} plot (@var{dat}, @var{exp})
## Plot signals of iddata identification datasets on the screen.
## The signals are plotted experiment-wise, either in time- or
## frequency-domain.  For multi-experiment datasets,
## press any key to switch to the next experiment.
## If the plot of a single experiment should be saved by the
## @command{print} command, use @code{plot(dat,exp)},
## where @var{exp} denotes the desired experiment.
## @end deftypefn

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: February 2012
## Version: 0.2

function plot (dat, exp = ":")

  if (nargin > 2)       # nargin == 0  is handled by built-in plot
    print_usage ();
  endif

  if (nargin == 2 && ! is_real_vector (exp))
    error ("iddata: plot: second argument must be a vector of indices");
  endif 

  expname = __labels__ (dat.expname, "exp");
  expname = expname(exp);
  
  idx = substruct ("()", {":", ":", ":", exp});  
  dat = subsref (dat, idx);
  
  [n, p, m, e] = size (dat);

  if (dat.timedomain)
    if (m == 0)         # time series
      for k = 1 : e
        if (k > 1)
          pause
        endif
        plot (dat.y{k})
        title (expname{k})
        legend (__labels__ (dat.outname, "y"){:})
        xlabel ("Time")
        ylabel ("Output Signal")
      endfor
    else                # inputs present
      for k = 1 : e
        if (k > 1)
          pause
        endif
        subplot (2, 1, 1)
        plot (dat.y{k})
        title (expname{k})
        legend (__labels__ (dat.outname, "y"){:})
        ylabel ("Output Signal")
        subplot (2, 1, 2)
        stairs (dat.u{k})
        legend (__labels__ (dat.inname, "u"){:})
        xlabel ("Time")
        ylabel ("Input Signal")
      endfor
    endif
  else                  # frequency domain
    if (m == 0)         # time series
      for k = 1 : e
        if (k > 1)
          pause
        endif
        bar (dat.w{k}, 20*log10 (abs (dat.y{k})))
        xlim ([dat.w{k}(1), dat.w{k}(end)])
        title (expname{k})
        legend (__labels__ (dat.outname, "y"){:})
        xlabel ("Frequency")
        ylabel ("Output Magnitude [dB]")
      endfor
    else                # inputs present
      for k = 1 : e
        if (k > 1)
          pause
        endif
        subplot (2, 1, 1)
        bar (dat.w{k}, 20*log10 (abs (dat.y{k})))
        xlim ([dat.w{k}(1), dat.w{k}(end)])
        title (expname{k})
        legend (__labels__ (dat.outname, "y"){:})
        ylabel ("Output Magnitude [dB]")
        subplot (2, 1, 2)
        bar (dat.w{k}, 20*log10(abs (dat.u{k})))
        xlim ([dat.w{k}(1), dat.w{k}(end)])
        legend (__labels__ (dat.inname, "u"){:})
        xlabel ("Frequency")
        ylabel ("Input Magnitude [dB]")
      endfor
    endif
  endif

  ## TODO: think about the 20*log10 and the bars in general

endfunction
