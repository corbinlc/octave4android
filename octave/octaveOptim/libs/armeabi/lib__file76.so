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
## Gives a 2-dim function with strange shape ("ff", defined below).
##
## Sets a ok variable to 1 in case of success, 0 in case of failure
##
## If a variables "verbose" is set, then some comments are output.

1 ;

ok = 0;

if ! exist ("verbose"), verbose = 0; end

if verbose
  printf ("\n   Testing d2_min () on a strange 2-dimensional function\n\n");
end

P = 2;	# Nparams
noise = 0 ;
truep = [0;0] ;
xinit = randn(P,1) ;

if noise, obses = adnois(obses,noise); end

y = nan;


function v = ff (x, y)
  v = x(1)^2 * (1+sin(x(2)*3*pi)^2) + x(2)^2;
endfunction


function [w,dv,d2v] = d2ff (x, y)
  u = x(1); v = x(2);
  w = u^2 * (1+sin(v*3*pi)^2) + v^2;

  dv = [2*u * (1+sin(v*3*pi)^2), u^2 * sin(v*2*3*pi) + 2*v ];

  d2v = [2*(1+sin(v*3*pi)^2), 2*u * sin(v*2*3*pi) ;
	 2*u * sin(v*2*3*pi), u^2 * 2*3*pi* cos(v*2*3*pi) + 2 ];
  d2v = inv (d2v);
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


ctl = nan*zeros(1,5); ctl(5) = 1;

if verbose
  printf ( "Going to call d2_min\n");
end
mytic() ;
[xlev,vlev,nev] = d2_min ("ff", "d2ff", {xinit,y},ctl) ;
tlev = mytic ();

if verbose,
  printf("d2_min should find minv = 0 (plus a little error)\n");
  printf(["d2_min : niter=%-4d  nev=%-4d  nparams=%-4d\n",...
	  "  time=%-8.3g errx=%-8.3g   minv=%-8.3g\n"],...
         nev([2,1]), P, tlev, max (abs (xlev-truep)), vlev);
end

ok = 1;

if max (abs(xlev-truep )) > sqrt (eps),
  if verbose
      printf ( "Error is too big : %-8.3g\n", max (abs (xlev-truep)));
  end
  ok = 0;
end

if verbose && ok
    printf ( "All tests ok\n");
end



