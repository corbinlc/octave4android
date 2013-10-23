## Copyright (C) 2002 Etienne Grossmann <etienne@egdn.net>
## Copyright (C) 2009 Levente Torok <TorokLev@gmail.com>
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
## @deftypefn {Function File} {[@var{x0},@var{v},@var{nev}]} cg_min ( @var{f},@var{df},@var{args},@var{ctl} )
## NonLinear Conjugate Gradient method to minimize function @var{f}.
##
## @subheading Arguments
## @itemize @bullet
## @item @var{f}   : string   : Name of function. Return a real value 
## @item @var{df}  : string   : Name of f's derivative. Returns a (R*C) x 1 vector 
## @item @var{args}: cell     : Arguments passed to f.@*
## @item @var{ctl}   : 5-vec    : (Optional) Control variables, described below
## @end itemize
##
## @subheading Returned values
## @itemize @bullet
## @item @var{x0}    : matrix   : Local minimum of f
## @item @var{v}     : real     : Value of f in x0
## @item @var{nev}   : 1 x 2    : Number of evaluations of f and of df
## @end itemize
##
## @subheading Control Variables
## @itemize @bullet
## @item @var{ctl}(1)       : 1 or 2 : Select stopping criterion amongst :
## @item @var{ctl}(1)==0    : Default value
## @item @var{ctl}(1)==1    : Stopping criterion : Stop search when value doesn't
## improve, as tested by @math{ ctl(2) > Deltaf/max(|f(x)|,1) }
## where Deltaf is the decrease in f observed in the last iteration
## (each iteration consists R*C line searches).
## @item @var{ctl}(1)==2    : Stopping criterion : Stop search when updates are small,
## as tested by @math{ ctl(2) > max @{ dx(i)/max(|x(i)|,1) | i in 1..N @}}
## where  dx is the change in the x that occured in the last iteration.
## @item @var{ctl}(2)       : Threshold used in stopping tests.           Default=10*eps
## @item @var{ctl}(2)==0    : Default value
## @item @var{ctl}(3)       : Position of the minimized argument in args  Default=1
## @item @var{ctl}(3)==0    : Default value
## @item @var{ctl}(4)       : Maximum number of function evaluations      Default=inf
## @item @var{ctl}(4)==0    : Default value
## @item @var{ctl}(5)       : Type of optimization:
## @item @var{ctl}(5)==1    : "Fletcher-Reves" method
## @item @var{ctl}(5)==2    : "Polak-Ribiere" (Default)
## @item @var{ctl}(5)==3    : "Hestenes-Stiefel" method
## @end itemize
##
## @var{ctl} may have length smaller than 4. Default values will be used if ctl is
## not passed or if nan values are given.
## @subheading Example:
##
## function r=df( l )  b=[1;0;-1]; r = -( 2*l@{1@} - 2*b + rand(size(l@{1@}))); endfunction @*
## function r=ff( l )  b=[1;0;-1]; r = (l@{1@}-b)' * (l@{1@}-b); endfunction @*
## ll = @{ [10; 2; 3] @}; @*
## ctl(5) = 3; @*
## [x0,v,nev]=cg_min( "ff", "df", ll, ctl ) @*
## 
## Comment:  In general, BFGS method seems to be better performin in many cases but requires more computation per iteration
## @seealso{ bfgsmin, http://en.wikipedia.org/wiki/Nonlinear_conjugate_gradient }
## @end deftypefn

function [x,v,nev] = cg_min (f, dfn, args, ctl)

verbose = 0;

crit = 1;			# Default control variables
tol = 10*eps;
narg = 1;
maxev = inf;
method = 2;

if nargin >= 4,			# Read arguments
  if                    !isnan (ctl(1)) && ctl(1) ~= 0, crit  = ctl(1); end
  if length (ctl)>=2 && !isnan (ctl(2)) && ctl(2) ~= 0, tol   = ctl(2); end
  if length (ctl)>=3 && !isnan (ctl(3)) && ctl(3) ~= 0, narg  = ctl(3); end
  if length (ctl)>=4 && !isnan (ctl(4)) && ctl(4) ~= 0, maxev = ctl(4); end
  if length (ctl)>=5 && !isnan (ctl(5)) && ctl(5) ~= 0, method= ctl(5); end
end

if iscell (args),		# List of arguments 
  x = args{narg};
else					# Single argument
  x = args;
  args = {args};
end

if narg > length (args),	# Check
  error ("cg_min : narg==%i, length (args)==%i\n",
	 narg, length (args));
end

[R, C] = size(x);
N = R*C;
x = reshape (x,N,1) ;

nev = [0, 0];

v = feval (f, args);
nev(1)++;

dxn = lxn = dxn_1 = -feval( dfn, args );
nev(2)++;

done = 0;

## TEMP
## tb = ts = zeros (1,100);

				# Control params for line search
ctlb = [10*sqrt(eps), narg, maxev];
if crit == 2, ctlb(1) = tol; end

x0 = x;
v0 = v;

nline = 0;
while nev(1) <= maxev ,
  ## xprev = x ;
  ctlb(3) = maxev - nev(1);	# Update # of evals


  ## wiki alg 4.
  [alpha, vnew, nev0] = brent_line_min (f, dxn, args, ctlb);

  nev += nev0;
  ## wiki alg 5.
  x = x + alpha * dxn;

  if nline >= N,
    if crit == 1,
      done = tol > (v0 - vnew) / max (1, abs (v0));
    else
      done = tol > norm ((x-x0)(:));
    end
    nline = 1;
    x0 = x;
    v0 = vnew;
  else
    nline++;
  end
  if done || nev(1) >= maxev,  return  end
  
  if vnew > v + eps ,
    printf("cg_min: step increased cost function\n");
    keyboard
  end
  
  # if abs(1-(x-xprev)'*dxn/norm(dxn)/norm(x-xprev))>1000*eps,
  #  printf("cg_min: step is not in the right direction\n");
  #  keyboard
  # end
  
  # update x at the narg'th position of args cellarray
  args{narg} = reshape (x, R, C);

  v = feval (f, args);
  nev(1)++;

  if verbose, printf("cg_min : nev=%4i, v=%8.3g\n",nev(1),v) ; end

  ## wiki alg 1:
  dxn = -feval (dfn, args);
  nev(2)++;

  # wiki alg 2:
  switch method
  
    case 1 # Fletcher-Reenves method
	  nu = dxn' * dxn;
      de  = dxn_1' * dxn_1;

    case 2 # Polak-Ribiere method
      nu = (dxn-dxn_1)' * dxn;
      de  = dxn_1' * dxn_1;

    case 3 # Hestenes-Stiefel method
      nu = (dxn-dxn_1)' * dxn;
	  de  = (dxn-dxn_1)' * lxn;

	otherwise
      error("No method like this");

  endswitch

  if nu == 0,
  	return
  endif
  
  if de == 0,
    error("Numerical instability!");
  endif
  beta = nu / de;
  beta = max( 0, beta );
  ## wiki alg 3.   update dxn, lxn, point 
  dxn_1 = dxn;
  dxn = lxn = dxn_1 + beta*lxn ;

end

if verbose, printf ("cg_min: Too many evaluatiosn!\n"); end

endfunction

%!demo
%! P = 15; # Number of parameters
%! R = 20; # Number of observations (must have R >= P)
%! 
%! obsmat = randn (R, P);
%! truep = randn (P, 1);
%! xinit = randn (P, 1);
%! obses = obsmat * truep;
%! 
%! msq = @(x) mean (x (!isnan(x)).^2);
%! ff  = @(x) msq (obses - obsmat * x{1}) + 1;
%! dff = @(x) 2 / rows (obses) * obsmat.' * (-obses + obsmat * x{1});
%! 
%! tic;
%! [xlev,vlev,nlev] = cg_min (ff, dff, xinit) ;
%! toc;
%! 
%! printf ("  Costs :     init=%8.3g, final=%8.3g, best=%8.3g\n", ...
%!         ff ({xinit}), vlev, ff ({truep}));
%! 
%! if (max (abs (xlev-truep)) > 100*sqrt (eps))
%!   printf ("Error is too big : %8.3g\n", max (abs (xlev-truep)));
%! else
%!   printf ("All tests ok\n");
%! endif

%!demo
%! N = 1 + floor (30 * rand ());
%! truemin = randn (N, 1);
%! offset  = 100 * randn ();
%! metric = randn (2 * N, N); 
%! metric = metric.' * metric;
%! 
%! if (N > 1)
%!   [u,d,v] = svd (metric);
%!   d = (0.1+[0:(1/(N-1)):1]).^2;
%!   metric = u * diag (d) * u.';
%! endif
%! 
%! testfunc = @(x) sum((x{1}-truemin)'*metric*(x{1}-truemin)) + offset;
%! dtestf = @(x) metric' * 2*(x{1}-truemin);
%! 
%! xinit = 10 * randn (N, 1);
%! 
%! [x, v, niter] = cg_min (testfunc, dtestf, xinit);
%! 
%! if (any (abs (x-truemin) > 100 * sqrt(eps)))
%!   printf ("NOT OK 1\n");
%! else
%!   printf ("OK 1\n");
%! endif
%! 
%! if (v-offset > 1e-8)
%!   printf ("NOT OK 2\n");
%! else
%!   printf ("OK 2\n");
%! endif
%! 
%! printf ("nev=%d  N=%d  errx=%8.3g   errv=%8.3g\n",...
%!         niter (1), N, max (abs (x-truemin)), v-offset);

%!demo
%! P = 2; # Number of parameters
%! R = 3; # Number of observations
%! 
%! obsmat = randn (R, P);
%! truep  = randn (P, 1);
%! xinit  = randn (P, 1);
%! 
%! obses = obsmat * truep;
%! 
%! msq = @(x) mean (x (!isnan(x)).^2);
%! ff = @(xx) msq (xx{3} - xx{2} * xx{1}) + 1;
%! dff = @(xx) 2 / rows(xx{3}) * xx{2}.' * (-xx{3} + xx{2}*xx{1});
%! 
%! tic;
%! x = {xinit, obsmat, obses};
%! [xlev, vlev, nlev] = cg_min (ff, dff, x);
%! toc;
%! 
%! xinit_ = {xinit, obsmat, obses};
%! xtrue_ = {truep, obsmat, obses};
%! printf ("  Costs :     init=%8.3g, final=%8.3g, best=%8.3g\n", ...
%!         ff (xinit_), vlev, ff (xtrue_));
%! 
%! if (max (abs(xlev-truep)) > 100*sqrt (eps))
%!   printf ("Error is too big : %8.3g\n", max (abs (xlev-truep)));
%! else
%!   printf ("All tests ok\n");
%! endif

