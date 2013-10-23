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

## Checks wether the function 'nelder_mead_min' works, by making it minimize a
## quadratic function.

ok = 1;
cnt = 1;

if ! exist ("verbose"), verbose = 0; end
if verbose, printf ("  test_nelder_mead : \n"); end

if ! exist ("inspect"), inspect = 0; end

tol = 100*sqrt (eps);

R = 3 ;
C = 2;

if verbose,
  printf ("  optimization problem has dimension %i\n",R*C);
end

function c = my_quad_func (x,y,z)
  c = 1 + sum (vec(x-y)'*z*(vec(x-y)));
end

function c = non_quad_func_1 (x,y,z)
  tmp = sum (vec(x-y)'*z*(vec(x-y)));
  c = 1 + 1.1*tmp + sin (sqrt(tmp));
end

function c = non_quad_func_2 (x,y,z)
  tmp1 = sum (vec(x-y)'*z*(vec(x-y)));
  tmp2 = max (abs (vec(x-y)))^2;
  c = 1 + 1.1*tmp1 + tmp2 ;
end

##       dt = mytic()
##
## Returns the cputime since last call to 'mytic'.

function dt = mytic()
   persistent last_mytic = 0 ;
   [t,u,s] = cputime() ;
   dt = t - last_mytic ;
   last_mytic = t ;
endfunction

fnames = { "my_quad_func", "non_quad_func_1", "non_quad_func_2"};

x0 = randn(R,C) ;
x1 = x0 + randn(R,C) ;
z = randn (R*C); z = z*z';

for i = 1:length (fnames)
  fname = fnames{i};
  if verbose, 
    printf ("trying to minimize '%s'\n", fname);
  end
  ctl = nan*zeros (1,6);

  mytic ();
  [x2,v,nf] = nelder_mead_min (fname, {x1,x0,z}, ctl) ;
  t0 = mytic ();

  if any (abs (x2-x0)(:) > 100*tol),
    if verbose || inspect, printf ("not ok %i\n",cnt); end
    [max(abs (x2-x0)(:)), 100*tol]
    if inspect, keyboard; end
    ok = 0 ;
  else 
    if verbose, 
      printf ("ok %i\n  function evaluations = %i\n",cnt,nf); 
    end
  end
  cnt++;

				# Use vanilla nelder_mead_min
  mytic ();
  [x2,v,nf] = nelder_mead_min (fname, {x1,x0,z}) ;
  t1 = mytic ();

  if any (abs (x2-x0)(:) > 100*tol),
    if verbose || inspect, printf ("not ok %i\n",cnt); end
    [max(abs (x2-x0)(:)), 100*tol]
    if inspect, keyboard; end
    ok = 0 ;
  else 
    if verbose, 
      printf ("ok %i\n  function evaluations = %i\n",cnt,nf); 
    end
  end
  cnt++;


				# Optimize wrt 2nd arg. 
  ctl = nan * zeros (1,6);
  ctl(6) = 0;
  ctl(3) = 2;

  mytic ();
  [x2,v,nf] = nelder_mead_min (fname, {x1,x0,z}, ctl) ;
  t0 = mytic ();

  if any (abs (x2-x1)(:) > 100*tol),
    if verbose || inspect, printf ("not ok %i\n",cnt); end
    [max(abs (x2-x0)(:)), 100*tol]
    if inspect, keyboard; end
    ok = 0 ;
  else 
    if verbose, 
      printf ("ok %i\n  function evaluations = %i\n",cnt,nf); 
    end
  end
  cnt++;

				# Optimize wrt 2nd arg. 
  ctl = nan * zeros (1,6);
  ctl(3) = 2;

  mytic ();
  [x2,v,nf] = nelder_mead_min (fname, {x1,x0,z}, ctl) ;
  t1 = mytic ();

  if any (abs (x2-x1)(:) > tol),
    if verbose || inspect, printf ("not ok %i\n",cnt); end
    [max(abs (x2-x0)(:)), 100*tol]
    if inspect, keyboard; end
    ok = 0 ;
  else 
    if verbose, 
      printf ("ok %i\n  function evaluations = %i\n",cnt,nf); 
    end
  end
  cnt++;
  if 0
				# Check with struct control variable
    ctls = struct ("narg", 2);
    [x2bis,vbis,nfbis] = nelder_mead_min (fname, {x1,x0,z}, ctls) ;
    t1 = mytic ();
    ## [nf,nfbis]
    if any ((x2-x2bis)(:))
      if verbose || inspect, printf ("not ok %i\n",cnt); end
      printf ("  struct ctl : x2 - x2bis -> %g\n", max(abs (x2-x2bis)(:)));
      if inspect, keyboard; end
      ok = 0 ;
    else 
      if verbose, 
	printf ("ok %i\n  function evaluations = %i\n",cnt,nfbis); 
      end
    end
    cnt++;
    
				# Check with named args
    [x2bis,vbis,nfbis] = nelder_mead_min (fname, {x1,x0,z}, "narg", 2) ;
    t1 = mytic ();
    ## [nf,nfbis]
    if any ((x2-x2bis)(:))
      if verbose || inspect, printf ("not ok %i\n",cnt); end
      printf ("  named arg  : x2 - x2bis -> %g\n", max(abs (x2-x2bis)(:)));
      if inspect, keyboard; end
      ok = 0 ;
    else 
      if verbose, 
	printf ("ok %i\n  function evaluations = %i\n",cnt,nfbis); 
      end
    end
    cnt++;
  end
end

if verbose && ok
  printf ("All tests ok\n");
end


