## Copyright (C) 2011 Hong Yu <hyu0401@hotmail.com>
##
## This program is free software; you can redistribute it and/or modify it under
## the terms of the GNU General Public License as published by the Free Software
## Foundation; either version 3 of the License, or (at your option) any later
## version.
##
## This program is distributed in the hope that it will be useful, but WITHOUT
## ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
## FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
## details.
##
## You should have received a copy of the GNU General Public License along with
## this program; if not, see <http://www.gnu.org/licenses/>.

## -*- texinfo -*-
## @deftypefn {Function File} {[@var{dur}, @var{mod_dur}] =} cfdur (@var{cf}, @var{yield})
## Calculate duration @var{dur} and modified duration @var{mod_dur}, from given
## fixed-paid cash flow @var{cf} and period yield @var{yield}.
##
## Reference:
## http://en.wikipedia.org/wiki/Bond_duration
## Using periodic compounding instead of continuous compounding.
##
## @seealso{cfconv}
## @end deftypefn

function [dur, modDur] = cfdur (cf, yield)

  if ( nargin != 2 )
    print_usage ();
  elseif ( ! isscalar(yield) )
    error("input yield must be a scalar");
  endif

  if ( rows(1) != 1 )
    error("input cash flow must be a 1xN matrix");
  endif

  v_idx   = 1:columns(cf);
  t1      = (1+yield) .^ (-v_idx);
  t2      = v_idx .* t1;

  dur     = (cf*t2') / (cf*t1');
  modDur  = dur / (1+yield);

endfunction

%!demo
%! cf = [2.5 2.5 2.5 2.5 2.5 2.5 2.5 2.5 2.5 102.5];
%! yield = 0.025;
%! [ duration, modDuration ] = cfdur( cf, yield )
%! %--------------------------------------------------
%! % Input cash flow and yield, output duration and modified duration

%!test
%! cf = [2.5 2.5 2.5 2.5 2.5 2.5 2.5 2.5 2.5 102.5];
%! [dur modDur] = cfdur( cf, 0.025 );
%! errVal1 = round(dur*(1e+4))*(1e-4) - 8.9709;
%! errVal2 = round(modDur*(1e+4))*(1e-4) - 8.7521;
%! assert( errVal1, 0 )
%! assert( errVal2, 0 )
