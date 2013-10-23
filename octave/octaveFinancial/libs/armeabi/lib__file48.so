## Copyright (C) 1995-1998, 2000, 2002, 2004-2007 Kurt Hornik <Kurt.Hornik@wu-wien.ac.at>
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
## @deftypefn {Function File} {@var{r} =} rate (@var{n}, @var{p}, @var{v})
## @deftypefnx {Function File} {@var{r} =} rate (@var{n}, @var{p}, @var{v}, @var{l})
## @deftypefnx {Function File} {@var{r} =} rate (@var{n}, @var{p}, @var{v}, @var{l}, @var{method})
## @deftypefnx {Function File} {@var{r} =} rate (@var{n}, @var{p}, @var{v}, @var{method})
## Return the rate of return @var{r} on an investment of present value @var{v}
## which pays @var{p} in @var{n} consecutive periods.
##
## The optional argument @var{l} may be used to specify an additional
## lump-sum payment made at the end of @var{n} periods.
##
## The optional string argument @var{method} may be used to specify
## whether payments are made at the end (@code{"e"}, default) or at the
## beginning (@code{"b"}) of each period.
## @seealso{pv, pmt, nper, npv}
## @end deftypefn

function r = rate (n, p, v, l = 0, m = "e")

  if (nargin < 3 || nargin > 5)
    print_usage ();
  elseif (!isnumeric (n) || !isscalar (n) || n <= 0)
    error ("number of consecutive periods `n' must be a positive scalar");
  elseif (!isnumeric (p) || !isscalar (p))
    error ("second argument `p' must be a numeric scalar");
  elseif (!isnumeric (v) || !isscalar (v))
    error ("present value `v' must be a numeric scalar");

  ## the following checks is to allow using default value for `l' while specifying `m'
  elseif (nargin == 5)
    if (!isnumeric (l) || !isscalar (l))
      error ("value of additional lump-sum payment `l' must be numeric scalar");
    elseif (!ischar (m))
      error ("`method' must be a string")
    endif
  elseif (nargin == 4)
    if (ischar (l))
      m = l;
      l = 0;   # default value for `l' again
    elseif (!isnumeric (l) || !isscalar (l))
      error ("fourth argument must either be a numeric scalar for lump-sum payment `l' or a string for `method'");
    endif
  endif

  if (!any (strcmpi (l, {"e","b"})))
    error ("`method' must either be `e' or `b")
  endif

  f = @(x) pv (x, n, p, l, m) - v;
  r = fsolve (f, 0);

endfunction
