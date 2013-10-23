## Copyright (C) 2008, 2009 VZLU Prague, a.s.
## Copyright (C) 2010 Olaf Till <olaf.till@uni-jena.de>
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
## @deftypefn  {Function File} {} vfzero (@var{fun}, @var{x0})
## @deftypefnx {Function File} {} vfzero (@var{fun}, @var{x0}, @var{options})
## @deftypefnx {Function File} {[@var{x}, @var{fval}, @var{info}, @var{output}] =} vfzero (@dots{})
## A variant of @code{fzero}. Finds a zero of a vector-valued
## multivariate function where each output element only depends on the
## input element with the same index (so the Jacobian is diagonal).
##
## @var{fun} should be a handle or name of a function returning a column
## vector.  @var{x0} should be a two-column matrix, each row specifying
## two points which bracket a zero of the respective output element of
## @var{fun}.
##
## If @var{x0} is a single-column matrix then several nearby and distant
## values are probed in an attempt to obtain a valid bracketing.  If
## this is not successful, the function fails. @var{options} is a
## structure specifying additional options. Currently, @code{vfzero}
## recognizes these options: @code{"FunValCheck"}, @code{"OutputFcn"},
## @code{"TolX"}, @code{"MaxIter"}, @code{"MaxFunEvals"}. For a
## description of these options, see @ref{doc-optimset,,optimset}.
##
## On exit, the function returns @var{x}, the approximate zero and
## @var{fval}, the function value thereof. @var{info} is a column vector
## of exit flags  that can have these values:
##
## @itemize 
## @item 1 The algorithm converged to a solution.
##
## @item 0 Maximum number of iterations or function evaluations has been
## reached.
##
## @item -1 The algorithm has been terminated from user output function.
##
## @item -5 The algorithm may have converged to a singular point.
## @end itemize
##
## @var{output} is a structure containing runtime information about the
## @code{fzero} algorithm.  Fields in the structure are:
##
## @itemize
## @item iterations Number of iterations through loop.
##
## @item nfev Number of function evaluations.
##
## @item bracketx A two-column matrix with the final bracketing of the
## zero along the x-axis.
##
## @item brackety A two-column matrix with the final bracketing of the
## zero along the y-axis.
## @end itemize
## @seealso{optimset, fsolve}
## @end deftypefn

## This is essentially the ACM algorithm 748: Enclosing Zeros of
## Continuous Functions due to Alefeld, Potra and Shi, ACM Transactions
## on Mathematical Software, Vol. 21, No. 3, September 1995. Although
## the workflow should be the same, the structure of the algorithm has
## been transformed non-trivially; instead of the authors' approach of
## sequentially calling building blocks subprograms we implement here a
## FSM version using one interior point determination and one bracketing
## per iteration, thus reducing the number of temporary variables and
## simplifying the algorithm structure. Further, this approach reduces
## the need for external functions and error handling. The algorithm has
## also been slightly modified.

## Author: Jaroslav Hajek <highegg@gmail.com>

## PKG_ADD: __all_opts__ ("vfzero");

function [x, fval, info, output] = vfzero (fun, x0, options = struct ())

  ## Get default options if requested.
  if (nargin == 1 && ischar (fun) && strcmp (fun, 'defaults'))
    x = optimset ("MaxIter", Inf, "MaxFunEvals", Inf, "TolX", 1e-8, \
    "OutputFcn", [], "FunValCheck", "off");
    return;
  endif

  if (nargin < 2 || nargin > 3)
    print_usage ();
  endif

  if (ischar (fun))
    fun = str2func (fun, "global");
  endif

  ## TODO
  ## displev = optimget (options, "Display", "notify");
  funvalchk = strcmpi (optimget (options, "FunValCheck", "off"), "on");
  outfcn = optimget (options, "OutputFcn");
  tolx = optimget (options, "TolX", 1e-8);
  maxiter = optimget (options, "MaxIter", Inf);
  maxfev = optimget (options, "MaxFunEvals", Inf);
  nx = rows (x0);
  ## fun may assume a certain length of x, so we will always call it
  ## with the full-length x, even if only some elements are needed

  persistent mu = 0.5;

  if (funvalchk)
    ## Replace fun with a guarded version.
    fun = @(x) guarded_eval (fun, x);
  endif

  ## The default exit flag if exceeded number of iterations.
  info = zeros (nx, 1);
  niter = 0;
  nfev = 0;

  x = fval = fc = a = fa = b = fb = aa = c = u = fu = NaN (nx, 1);
  bracket_ready = false (nx, 1);
  eps = eps (class (x0));

  ## Prepare...
  a = x0(:, 1);
  fa = fun (a)(:); 
  nfev = 1;
  if (columns (x0) > 1)
    b = x0(:, 2);
    fb = fun (b)(:);
    nfev += 1;
  else
    ## Try to get b.
    aa(idx = a == 0) = 1;
    aa(! idx) = a(! idx);
    for tb = [0.9*aa, 1.1*aa, aa-1, aa+1, 0.5*aa 1.5*aa, -aa, 2*aa, -10*aa, 10*aa]
      tfb = fun (tb)(:); nfev += 1;
      idx = ! bracket_ready & sign (fa) .* sign (tfb) <= 0;
      bracket_ready |= idx;
      b(idx) = tb(idx);
      fb(idx) = tfb(idx);
      if (all (bracket_ready))
        break;
      endif
    endfor
  endif

  tp = a(idx = b < a);
  a(idx) = b(idx);
  b(idx) = tp;

  tp = fa(idx);
  fa(idx) = fb(idx);
  fb(idx) = tp;

  if (! all (sign (fa) .* sign (fb) <= 0))
    error ("fzero:bracket", "vfzero: not a valid initial bracketing");
  endif

  slope0 = (fb - fa) ./ (b - a);

  idx = fa == 0;
  b(idx) = a(idx);
  fb(idx) = fa(idx);

  idx = (! idx & fb == 0);
  a(idx) = b(idx);
  fa(idx) = fb(idx);

  itype = ones (nx, 1);

  idx = abs (fa) < abs (fb);
  u(idx) = a(idx); fu(idx) = fa(idx);
  u(! idx) = b(! idx); fu(! idx) = fb(! idx);

  d = e = u;
  fd = fe = fu;
  mba = mu * (b - a);
  not_ready = true (nx, 1);
  while (niter < maxiter && nfev < maxfev && any (not_ready))

    ## itype == 1
    type1idx = not_ready & itype == 1;
    ## The initial test.
    idx = b - a <= 2*(2 * eps * abs (u) + tolx) & type1idx;
    x(idx) = u(idx); fval(idx) = fu(idx);
    info(idx) = 1;
    not_ready(idx) = false;
    type1idx &= not_ready;
    exclidx = type1idx;
    ## Secant step.
    idx = type1idx & \
	(tidx = abs (fa) <= 1e3*abs (fb) & abs (fb) <= 1e3*abs (fa));
    c(idx) = u(idx) - (a(idx) - b(idx)) ./ (fa(idx) - fb(idx)) .* fu(idx);
    ## Bisection step.
    idx = type1idx & ! tidx;
    c(idx) = 0.5*(a(idx) + b(idx));
    d(type1idx) = u(type1idx); fd(type1idx) = fu(type1idx);
    itype(type1idx) = 5;

    ## itype == 2 or 3
    type23idx = not_ready & ! exclidx & (itype == 2 | itype == 3);
    exclidx |= type23idx;
    uidx = cellfun (@ (x) length (unique (x)), \
		    num2cell ([fa, fb, fd, fe], 2)) == 4;
    oidx = sign (c - a) .* sign (c - b) > 0;
    ## Inverse cubic interpolation.
    idx = type23idx & (uidx & ! oidx);
    q11 = (d(idx) - e(idx)) .* fd(idx) ./ (fe(idx) - fd(idx));
    q21 = (b(idx) - d(idx)) .* fb(idx) ./ (fd(idx) - fb(idx));
    q31 = (a(idx) - b(idx)) .* fa(idx) ./ (fb(idx) - fa(idx));
    d21 = (b(idx) - d(idx)) .* fd(idx) ./ (fd(idx) - fb(idx));
    d31 = (a(idx) - b(idx)) .* fb(idx) ./ (fb(idx) - fa(idx));
    q22 = (d21 - q11) .* fb(idx) ./ (fe(idx) - fb(idx));
    q32 = (d31 - q21) .* fa(idx) ./ (fd(idx) - fa(idx));
    d32 = (d31 - q21) .* fd(idx) ./ (fd(idx) - fa(idx));
    q33 = (d32 - q22) .* fa(idx) ./ (fe(idx) - fa(idx));
    c(idx) = a(idx) + q31 + q32 + q33;
    ## Quadratic interpolation + newton.
    idx = type23idx & (oidx | ! uidx);
    a0 = fa(idx);
    a1 = (fb(idx) - fa(idx))./(b(idx) - a(idx));
    a2 = ((fd(idx) - fb(idx))./(d(idx) - b(idx)) - a1) ./ (d(idx) - a(idx));
    ## Modification 1: this is simpler and does not seem to be worse.
    c(idx) = a(idx) - a0./a1;
    taidx = a2 != 0;
    tidx = idx;
    tidx(tidx) = taidx;
    c(tidx) = a(tidx)(:) - (a0(taidx)./a1(taidx))(:);
    for i = 1:3
      tidx &= i <= itype;
      taidx = tidx(idx);
      pc = a0(taidx)(:) + (a1(taidx)(:) + \
			   a2(taidx)(:).*(c(tidx) - b(tidx))(:)) \
	  .*(c(tidx) - a(tidx))(:);
      pdc = a1(taidx)(:) + a2(taidx)(:).*(2*c(tidx) - a(tidx) - b(tidx))(:);
      tidx0 = tidx;
      tidx0(tidx0, 1) &= (p0idx = pdc == 0);
      taidx0 = tidx0(idx);
      tidx(tidx, 1) &= ! p0idx;
      c(tidx0) = a(tidx0)(:) - (a0(taidx0)./a1(taidx0))(:);
      c(tidx) = c(tidx)(:) - (pc(! p0idx)./pdc(! p0idx))(:);
    endfor
    itype(type23idx) += 1; 

    ## itype == 4
    type4idx = not_ready & ! exclidx & itype == 4;
    exclidx |= type4idx;
    ## Double secant step.
    idx = type4idx;
    c(idx) = u(idx) - 2*(b(idx) - a(idx))./(fb(idx) - fa(idx)).*fu(idx);
    ## Bisect if too far.
    idx = type4idx & abs (c - u) > 0.5*(b - a);
    c(idx) = 0.5 * (b(idx) + a(idx));
    itype(type4idx) = 5;

    ## itype == 5
    type5idx = not_ready & ! exclidx & itype == 5;
    ## Bisection step.
    idx = type5idx;
    c(idx) = 0.5 * (b(idx) + a(idx));
    itype(type5idx) = 2;

    ## Don't let c come too close to a or b.
    delta = 2*0.7*(2 * eps * abs (u) + tolx);
    nidx = not_ready & ! (idx = b - a <= 2*delta);
    idx &= not_ready;
    c(idx) = (a(idx) + b(idx))/2;
    c(nidx) = max (a(nidx) + delta(nidx), \
		   min (b(nidx) - delta(nidx), c(nidx)));

    ## Calculate new point.
    idx = not_ready;
    x(idx, 1) = c(idx, 1);
    if (any (idx))
      c(! idx) = u(! idx); # to have some working place-holders since
				# fun() might expect full-length
				# argument
      fval(idx, 1) = fc(idx, 1) = fun (c)(:)(idx, 1);
      niter ++; nfev ++;
    endif

    ## Modification 2: skip inverse cubic interpolation if
    ## nonmonotonicity is detected.
    nidx = not_ready & ! (idx = sign (fc - fa) .* sign (fc - fb) >= 0);
    idx &= not_ready;
    ## The new point broke monotonicity. 
    ## Disable inverse cubic.
    fe(idx) = fc(idx);
    ##
    e(nidx) = d(nidx); fe(nidx) = fd(nidx);

    ## Bracketing.
    idx1 = not_ready & sign (fa) .* sign (fc) < 0;
    idx2 = not_ready & ! idx1 & sign (fb) .* sign (fc) < 0;
    idx3 = not_ready & ! (idx1 | idx2) & fc == 0;
    d(idx1) = b(idx1); fd(idx1) = fb(idx1);
    b(idx1) = c(idx1); fb(idx1) = fc(idx1);
    d(idx2) = a(idx2); fd(idx2) = fa(idx2);
    a(idx2) = c(idx2); fa(idx2) = fc(idx2);
    a(idx3) = b(idx3) = c(idx3); fa(idx3) = fb(idx3) = fc(idx3);
    info(idx3) = 1;
    not_ready(idx3) = false;
    if (any (not_ready & ! (idx1 | idx2 | idx3)))
      ## This should never happen.
      error ("fzero:bracket", "vfzero: zero point is not bracketed");
    endif

    ## If there's an output function, use it now.
    if (! isempty (outfcn))
      optv.funccount = nfev;
      optv.fval = fval;
      optv.iteration = niter;
      idx = not_ready & outfcn (x, optv, "iter");
      info(idx) = -1;
      not_ready(idx) = false;
    endif

    nidx = not_ready & ! (idx = abs (fa) < abs (fb));
    idx &= not_ready;
    u(idx) = a(idx); fu(idx) = fa(idx);
    u(nidx) = b(nidx); fu(nidx) = fb(nidx);
    idx = not_ready & b - a <= 2*(2 * eps * abs (u) + tolx);
    info(idx) = 1;
    not_ready(idx) = false;

    ## Skip bisection step if successful reduction.
    itype(not_ready & itype == 5 & (b - a) <= mba) = 2;
    idx = not_ready & itype == 2;
    mba(idx) = mu * (b(idx) - a(idx));
  endwhile

  ## Check solution for a singularity by examining slope
  idx = not_ready & info == 1 & (b - a) != 0;
  idx(idx, 1) &= \
      abs ((fb(idx, 1) - fa(idx, 1))./(b(idx, 1) - a(idx, 1)) \
	   ./ slope0(idx, 1)) > max (1e6, 0.5/(eps+tolx));
  info(idx) = - 5;

  output.iterations = niter;
  output.funcCount = nfev;
  output.bracketx = [a, b];
  output.brackety = [fa, fb];

endfunction

## An assistant function that evaluates a function handle and checks for
## bad results.
function fx = guarded_eval (fun, x)
  fx = fun (x);
  if (! isreal (fx))
    error ("fzero:notreal", "vfzero: non-real value encountered"); 
  elseif (any (isnan (fx)))
    error ("fzero:isnan", "vfzero: NaN value encountered"); 
  endif
endfunction

%!shared opt0
%! opt0 = optimset ("tolx", 0);
%!assert(vfzero(@cos, [0, 3], opt0), pi/2, 10*eps)
%!assert(vfzero(@(x) x^(1/3) - 1e-8, [0,1], opt0), 1e-24, 1e-22*eps)
