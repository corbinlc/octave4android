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
## @deftypefn {Function File} {@var{cfConv} =} cfconv (@var{cf}, @var{yield})
## Calculate convexity @var{cfConv} from given fixed-paid cash flow @var{cf} and
## period yield @var{yield}.
## 
## Reference:
##
## [1] http://thismatter.com/money/bonds/duration-convexity.htm
##
## [2] http://en.wikipedia.org/wiki/Bond_convexity
##
## @seealso{cfdur}
## @end deftypefn

function [cfConv] = cfconv (cf, yield)

  if ( nargin != 2 )
    print_usage ();
  elseif ( ! isscalar(yield) )
    error("yield: must be scalar");
  elseif ( rows(cf) != 1 )
    error("Cash Flow: must be 1xN");
  endif

  v_idx = 1:columns(cf);
  t1    = (1+yield) .^ (-v_idx);
  t2    = ((v_idx .^ 2) + v_idx) .* t1;

  cfConv = (cf*t2') / (cf*t1') / (1+yield) / (1+yield);

endfunction

%!demo
%! cf = [2.5 2.5 2.5 2.5 2.5 2.5 2.5 2.5 2.5 102.5];
%! yield = 0.025;
%! cfConv = cfconv( cf, yield )
%! %--------------------------------------------------
%! % Input cash flow and yield, output convexity

%!test
%! cf = [2.5 2.5 2.5 2.5 2.5 2.5 2.5 2.5 2.5 102.5];
%! cfConv = cfconv( cf, 0.025 );
%! errVal = round(cfConv*(1e+4))*(1e-4) - 90.4493;
%! errVal = round(errVal*(1e+10));
%! assert(errVal, 0)
