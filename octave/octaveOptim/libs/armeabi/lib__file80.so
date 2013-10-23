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

## ok                     - Test that bfgs works with extra
##                          arguments 
##
## Defines some simple functions and verifies that calling
## bfgs on them returns the correct minimum.
##
## Sets 'ok' to 1 if success, 0 otherwise

if ! exist ("optim_func"), optim_func = "bfgsmin"; end

ok = 1;

if ! exist ("verbose"), verbose = 0; end

P = 2;
R = 3;

## Make tests reproducible
## obsmat = randn(R,P) ;
obsmat = zeros (R,P);
obsmat(sub2ind([R,P],1:R,1+rem(0:R-1,P))) = 1:R;

## Make test_min_2 repeatable by using fixed starting point
## truep = randn(P,1) ;
## xinit = randn(P,1) ;
truep = rem (1:P, P/4)';
xinit = truep + 2*(1:P)'/(P);


## global obses ;
obses = obsmat*truep ;

extra = {obsmat, obses};


function v = ff(x, obsmat, obses)
  v = mean ( (obses - obsmat*x)(:).^2 ) + 1 ;
endfunction


function dv = dff(x, obsmat, obses)
  er = -obses + obsmat*x ;
  dv = 2*er'*obsmat / rows(obses) ;
  ## dv = 2*er'*obsmat ;
endfunction



if verbose
  printf ("   Checking that extra arguments are accepted\n\n");

  printf (["     Set 'optim_func' to the name of the optimization\n",\
	   "     function you want to test (must have same synopsis\n",\
	   "     as 'bfgs')\n\n"]);

  printf ("   Tested function : %s\n",optim_func);
  printf ("   Nparams = P = %i,  Nobses = R = %i\n",P,R);
  fflush (stdout);
end
function dt = mytic()
   persistent last_mytic = 0 ;
   [t,u,s] = cputime() ;
   dt = t - last_mytic ;
   last_mytic = t ;
endfunction

ctl.df = "dff";
mytic() ;
## [xlev,vlev,nlev] = feval (optim_func, "ff", "dff", xinit, "extra", extra) ;
## [xlev,vlev,nlev] = feval \
##     (optim_func, "ff", "dff", list (xinit, obsmat, obses));
if strcmp(optim_func,"bfgsmin")
	ctl = {-1,2,1,1};
endif
[xlev,vlev,nlev] = feval \
    (optim_func, "ff", {xinit, obsmat, obses}, ctl);
tlev = mytic() ;


if max (abs(xlev-truep)) > 1e-4,
  if verbose, 
    printf ("Error is too big : %8.3g\n", max (abs (xlev-truep)));
  end
  ok = 0;
end
if verbose,
  printf ("  Costs :     init=%8.3g, final=%8.3g, best=%8.3g\n",\
	  ff(xinit,obsmat,obses), vlev, ff(truep,obsmat,obses));    
end
if verbose
    printf ( "   time : %8.3g\n",tlev);
end
if verbose && ok
    printf ( "All tests ok\n");
end

