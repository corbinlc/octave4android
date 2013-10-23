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

## Checks wether the function 'nelder_mead_min' accepts options properly

ok = 1;
cnt = 1;

if ! exist ("verbose"), verbose = 0; end
if ! exist ("inspect"), inspect = 0; end

if verbose,
  printf (["test_nelder_mead_2\n",\
	   "  Check whether nelder_mead_min accepts options properly\n\n"]);
end

N = 2;
x1 = zeros (1,N);
small = 1e-3;
vol = (small^N) / factorial (N);

## Define simple 2D function : [x,y] -> x^2, start from [0,0]
## 

function c = my_func (x)
  c = x(1)^2;
end

######################################################################
## Test using volume #################################################

## Choose vtol and initial simplex so that algo should stop immediately.
ctl = struct ("verbose",verbose, "isz",small, "vtol",vol*1.01, "rst",0);

[x2,v,nev] = nelder_mead_min ("my_func", x1, ctl);

if nev != N+1
  if verbose || inspect, printf ("not ok %i\n",cnt); end
  if inspect, keyboard; end
  ok = 0 ;
else 
  if verbose, 
    printf ("ok %i\n",cnt); 
  end
end
cnt++;

## Choose vtol and initial simplex so that algo should stop after one
## iteration (should be a reflexion and a tentative extension). Total is 5
## evaluations. 
ctl = struct ("verbose",verbose, "isz",small, "vtol",vol*0.99, "rst",0);

x1 = [0,0];

[x2,v,nev] = nelder_mead_min ("my_func", x1, ctl);

if nev != N+3
  if verbose || inspect, printf ("not ok %i\n",cnt); end
  if inspect, keyboard; end
  ok = 0 ;
else 
  if verbose, 
    printf ("ok %i\n",cnt);
  end
end
cnt++;

######################################################################
## Test using radius #################################################

## Choose rtol and initial simplex so that algo stops immediately.
ctl = struct ("verbose",verbose, "isz",small, "rtol",small*2.01, "rst",0);

[x2,v,nev] = nelder_mead_min ("my_func", x1, ctl);

if nev != N+1
  if verbose || inspect, printf ("not ok %i\n",cnt); end
  if inspect, keyboard; end
  ok = 0 ;
else 
  if verbose, 
    printf ("ok %i\n",cnt); 
  end
end
cnt++;

## Choose rtol and initial simplex so that algo does not stop immediately.
ctl = struct ("verbose",verbose, "isz",small, "rtol",small*1.99, "rst",0);

[x2,v,nev] = nelder_mead_min ("my_func", x1, ctl);

if nev <= N+1
  if verbose || inspect, printf ("not ok %i\n",cnt); end
  if inspect, keyboard; end
  ok = 0 ;
else 
  if verbose, 
    printf ("ok %i\n",cnt); 
  end
end
cnt++;

######################################################################
## Test using values #################################################

## Choose rtol and initial simplex so that algo should stop immediately.
ctl = struct ("verbose",verbose, "isz",small, "ftol",1.01*small^2, "rst",0);

[x2,v,nev] = nelder_mead_min ("my_func", x1, ctl);

if nev != N+1
  if verbose || inspect, printf ("not ok %i\n",cnt); end
  if inspect, keyboard; end
  ok = 0 ;
else 
  if verbose, 
    printf ("ok %i\n",cnt); 
  end
end
cnt++;

## Choose rtol and initial simplex so that algo does not stop immediately.
ctl = struct ("verbose",verbose, "isz",small, "ftol",0.99*small^2, "rst",0);

[x2,v,nev] = nelder_mead_min ("my_func", x1, ctl);

if nev <= N+1
  if verbose || inspect, printf ("not ok %i\n",cnt); end
  if inspect, keyboard; end
  ok = 0 ;
else 
  if verbose
    printf ("ok %i\n",cnt); 
  end
end
cnt++;

cnt--;
if verbose && ok
  printf ("All %i tests ok\n", cnt);
end
