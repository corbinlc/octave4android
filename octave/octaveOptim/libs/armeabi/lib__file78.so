## Copyright (C) 2002 Etienne Grossmann <etienne@egdn.net>
## Copyright (C) 2004 Michael Creel <michael.creel@uab.es>
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

## Test an optimization function with the same synopsis as bfgs.m 

if ! exist ("optim_func"), optim_func = "bfgsmin"; end

ok = 1;

if ! exist ("verbose"), verbose = 0; end

if verbose
  printf ("\n   Testing '%s' on a quadratic programming problem\n\n",\
	  optim_func);
  printf (["     Set 'optim_func' to the name of the optimization\n",\
	   "     function you want to test (must have same synopsis\n",\
	   "     as 'bfgs')\n\n"]);
end



N = 1+floor(30*rand(1)) ;
global truemin ;
truemin = randn(N,1) ;

global offset ;
offset  = 10*randn(1) ;

global metric ;
metric = randn(2*N,N) ; 
metric = metric'*metric ;

if N>1,
  [u,d,v] = svd(metric);
  d = (0.1+[0:(1/(N-1)):1]).^2 ;
  metric = u*diag(d)*u' ;
end

function v = testfunc(x)
  global offset ;
  global truemin ;
  global metric ;
  v = sum((x-truemin)'*metric*(x-truemin))+offset ;
end

function df = dtestf(x)
  global truemin ;
  global metric ;
  df = 2*(x-truemin)'*metric ;
end

xinit = 10*randn(N,1) ;

if verbose,
  printf (["   Dimension is %i\n",\
	   "   Condition is %f\n"],\
	  N, cond (metric));
  fflush (stdout);
end

## [x,v,niter] = feval (optim_func, "testfunc","dtestf", xinit);
ctl.df = "dtestf";
if strcmp(optim_func,"bfgsmin")
	ctl = {-1,2,1,1};
	xinit2 = {xinit};
else xinit2 = xinit;	
endif
[x,v,niter] = feval (optim_func, "testfunc", xinit2, ctl);

if verbose 
  printf ("nev=%d  N=%d  errx=%8.3g   errv=%8.3g\n",\
	  niter(1),N,max(abs( x-truemin )),v-offset);
end

if any (abs (x-truemin) > 1e-4)
  ok = 0;
  if verbose, printf ("not ok 1 (best argument is wrong)\n"); end
elseif verbose, printf ("ok 1\n");
end

if  v-offset  > 1e-8
  ok = 0;
  if verbose, printf ("not ok 2 (best function value is wrong)\n"); end
elseif verbose, printf ("ok 2\n");
end

if verbose
  if ok, printf ("All tests ok\n");
  else   printf ("Whoa!! Some test(s) failed\n");
  end
end
