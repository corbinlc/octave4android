## Copyright (C) 2010   Lukas F. Reichlin
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
## @deftypefn{Function File} {[@var{p}, @var{q}] =} covar (@var{sys}, @var{w})
## Return the steady-state covariance.
##
## @strong{Inputs}
## @table @var
## @item sys
## LTI model.
## @item w
## Intensity of Gaussian white noise inputs which drive @var{sys}.
## @end table
##
## @strong{Outputs}
## @table @var
## @item p
## Output covariance.
## @item q
## State covariance.
## @end table
##
## @seealso{lyap, dlyap}
## @end deftypefn

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: January 2010
## Version: 0.1

function [p, q] = covar (sys, w)

  if (nargin != 2)
    print_usage ();
  endif
  
  if (! isa (sys, "lti"))
    error ("covar: first argument must be an LTI model");
  endif
  
  if (! isstable (sys))
    error ("covar: system must be stable");
  endif
  
  [a, b, c, d] = ssdata (sys);
  
  if (isct (sys))
    if (any (d(:)))
      error ("covar: system is not strictly proper");
    endif
    
    q = lyap (a, b*w*b.');
    p = c*q*c.';
  else
    q = dlyap (a, b*w*b.');
    p = c*q*c.' + d*w*d.';
  endif

endfunction

## continuous-time
%!shared p, q, p_exp, q_exp
%! sys = ss (-1, 1, 1, 0);
%! [p, q] = covar (sys, 5);
%! p_exp = 2.5000;
%! q_exp = 2.5000;
%!assert (p, p_exp, 1e-4);
%!assert (q, q_exp, 1e-4);

## discrete-time
%!shared p, q, p_exp, q_exp
%! sys = ss ([-0.2, -0.5; 1, 0], [2; 0], [1, 0.5], [0], 0.1);
%! [p, q] = covar (sys, 5);
%! p_exp = 30.3167;
%! q_exp = [27.1493, -3.6199; -3.6199, 27.1493];
%!assert (p, p_exp, 1e-4);
%!assert (q, q_exp, 1e-4);