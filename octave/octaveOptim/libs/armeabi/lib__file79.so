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

## test_min_2                   - Test that bfgs works
##
## Defines some simple functions and verifies that calling
## 
## bfgs on them returns the correct minimum.
##
## Sets 'ok' to 1 if success, 0 otherwise

if ! exist ("optim_func"), optim_func = "bfgsmin"; end

ok = 1;

if ! exist ("verbose"), verbose = 0; end

P = 15;
R = 20;			# must have R >= P


global obsmat ;
## Make test_min_2 reproducible by using fixed obsmat
## obsmat = randn(R,P) ;
obsmat = zeros (R,P);
obsmat(sub2ind([R,P],1:R,1+rem(0:R-1,P))) = 1:R;

global truep ;

## Make test_min_2 reproducible by using fixed starting point
## truep = randn(P,1) ;
## xinit = randn(P,1) ;
truep = rem (1:P, P/4)';
xinit = truep + 2*(1:P)'/(P);

global obses ;
obses = obsmat*truep ;


function v = ff(x)
  global obsmat;
  global obses;
  v = mean ((obses - obsmat*x).^2) + 1 ;
endfunction


function dv = dff(x)
  global obsmat;
  global obses;
  er = -obses + obsmat*x ;
  dv = 2*er'*obsmat / rows(obses) ;
  ## dv = 2*er'*obsmat ;
endfunction

##       dt = mytic()
##
## Returns the cputime since last call to 'mytic'.

function dt = mytic()
   persistent last_mytic = 0 ;
   [t,u,s] = cputime() ;
   dt = t - last_mytic ;
   last_mytic = t ;
endfunction


if verbose
  printf ("\n   Testing %s on a quadratic problem\n\n", optim_func);

  printf (["     Set 'optim_func' to the name of the optimization\n",\
	   "     function you want to test (must have same synopsis\n",\
	   "     as 'bfgs')\n\n"]);

  printf ("  Nparams = P = %i,  Nobses = R = %i\n",P,R);
  fflush (stdout);
end

ctl.df = "dff";
ctl.ftol = eps;
ctl.dtol = 1e-7;
mytic() ;
if strcmp(optim_func,"bfgsmin")
	ctl = {-1,2,1,1};
	xinit2 = {xinit};
else xinit2 = xinit;	
endif
## [xlev,vlev,nlev] = feval(optim_func, "ff", "dff", xinit) ;
[xlev,vlev,nlev] = feval(optim_func, "ff", xinit2, ctl) ;
tlev = mytic() ;


if max (abs(xlev-truep)) > 1e-4,
  if verbose
    printf ("Error is too big : %8.3g\n", max (abs (xlev-truep)));
  end
  ok = 0;
elseif verbose,  printf ("ok 1\n");
end

if verbose,
  printf ("  Costs :     init=%8.3g, final=%8.3g, best=%8.3g\n",\
	  ff(xinit), vlev, ff(truep));    
end
if verbose
    printf ( "   time : %8.3g\n",tlev);
end
if verbose && ok
  printf ( "All tests ok (there's just one test)\n");
end

