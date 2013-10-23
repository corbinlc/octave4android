## Copyright (C) 2002 Etienne Grossmann <etienne@egdn.net>
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

## Test whether d2_min() functions correctly
##
## Gives a simple quadratic programming problem (function ff below).
##
## Sets a ok variable to 1 in case of success, 0 in case of failure
##
## If a variables "verbose" is set, then some comments are output.

1 ;

if ! exist ("verbose"), verbose = 0; end

if verbose
  printf ("\n   Testing d2_min () on a quadratic programming problem\n\n");
end

P = 10+floor(30*rand(1)) ;	# Nparams
R = P+floor(30*rand(1)) ;	# Nobses
noise = 0 ;
global obsmat ;
obsmat = randn(R,P) ;
global truep ;
truep = randn(P,1) ;
xinit = randn(P,1) ;

global obses ;
obses = obsmat*truep ;
if noise, obses = adnois(obses,noise); end



function v = ff(x)
  global obsmat;
  global obses;
  v = msq (obses - obsmat*x ) ;
endfunction

function [v,dv,d2v] = d2ff(x) # Return pseudo-inverse
  global obsmat;
  global obses;
  er = -obses + obsmat*x ;
  dv = er'*obsmat ;
  v = msq(er ) ;
  d2v = pinv (obsmat'*obsmat ) ;
endfunction

function [v,dv,d2v] = d2ff_2(x)	# Return 2nd derivs, not pseudo-inv
  global obsmat;
  global obses;
  er = -obses + obsmat*x ;
  dv = er'*obsmat ;
  v = msq(er ) ;
  d2v = obsmat'*obsmat ;
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

## s = msq(x)                   - Mean squared value, ignoring nans
##
## s == mean(x(:).^2) , but ignores NaN's


function s = msq(x)
try
  s = mean(x(find(!isnan(x))).^2);
catch
  s = nan;
end
endfunction

cnt = 1;
ok = 1;

ctl = nan*zeros(1,5); ctl(5) = 1;

if verbose
  printf ("Going to call d2_min\n");
end
mytic() ;
[xlev,vlev,nev] = d2_min ("ff","d2ff",xinit,ctl);
tlev = mytic() ;

if verbose,
  printf("d2_min should find in one iteration + one more to check\n");
  printf(["d2_min : niter=%-4d  nev=%-4d  nobs=%-4d,nparams=%-4d\n",...
	  "  time=%-8.3g errx=%-8.3g   minv=%-8.3g\n"],...
	 nev([2,1]),R,P,tlev,max(abs(xlev-truep )),vlev);
end



if nev(2) != 2,
  if verbose
      printf ("Too many iterations for this function\n");
  end
  ok = 0;
else 
  if verbose
      printf ("Ok: single iteration (%i)\n",cnt);
  end
end

if max (abs(xlev-truep )) > sqrt (eps),
  if verbose
      printf ("Error is too big : %-8.3g\n", max (abs (xlev-truep)));
  end
  ok = 0;
else 
  if verbose
      printf ("Ok: single error amplitude (%i)\n",cnt);
  end
end

cnt++;

if verbose
  printf ("Going to call d2_min() \n");
end
mytic() ;
[xlev,vlev,nev] = d2_min("ff","d2ff_2",xinit) ;
tlev = mytic() ;

if verbose,
  printf("d2_min should find in one iteration + one more to check\n");
  printf(["d2_min : niter=%-4d  nev=%-4d  nobs=%-4d,nparams=%-4d\n",...
	  "  time=%-8.3g errx=%-8.3g   minv=%-8.3g\n"],...
	 nev([2,1]),R,P,tlev,max(abs(xlev-truep )),vlev);
end


if nev(2) != 2,
  if verbose
      printf ("Too many iterations for this function\n");
  end
  ok = 0;
else 
  if verbose
      printf ("Ok: single iteration (%i)\n",cnt);
  end
end

if max (abs(xlev-truep )) > sqrt (eps),
  if verbose
      printf ("Error is too big : %-8.3g\n", max (abs (xlev-truep)));
  end
  ok = 0;
else 
  if verbose
      printf ("Ok: single error amplitude (%i)\n",cnt);
  end
end

if verbose
  if ok
    printf ("All tests ok\n");
  else
    printf ("Some tests failed\n");
  end
end

