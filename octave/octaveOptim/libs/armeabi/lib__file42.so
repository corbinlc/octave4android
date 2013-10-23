## Copyright (C) 2002-2008 Etienne Grossmann <etienne@egdn.net>
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

## [x0,v,nev] = nelder_mead_min (f,args,ctl) - Nelder-Mead minimization
##
## Minimize 'f' using the Nelder-Mead algorithm. This function is inspired
## from the that found in the book "Numerical Recipes".
##
## ARGUMENTS
## ---------
## f     : string : Name of function. Must return a real value
## args  : list   : Arguments passed to f.
##      or matrix : f's only argument
## ctl   : vector : (Optional) Control variables, described below
##      or struct
##
## RETURNED VALUES
## ---------------
## x0  : matrix   : Local minimum of f
## v   : real     : Value of f in x0
## nev : number   : Number of function evaluations
## 
## CONTROL VARIABLE : (optional) may be named arguments (i.e. "name",value
## ------------------ pairs), a struct, or a vector of length <= 6, where
##                    NaN's are ignored. Default values are written <value>.
##  OPT.   VECTOR
##  NAME    POS
## ftol,f  N/A    : Stopping criterion : stop search when values at simplex
##                  vertices are all alike, as tested by 
##
##                   f > (max_i (f_i) - min_i (f_i)) /max(max(|f_i|),1)
##
##                  where f_i are the values of f at the vertices.  <10*eps>
##
## rtol,r  N/A    : Stop search when biggest radius of simplex, using
##                  infinity-norm, is small, as tested by :
##
##              ctl(2) > Radius                                     <10*eps>
##
## vtol,v  N/A    : Stop search when volume of simplex is small, tested by
##            
##              ctl(2) > Vol
##
## crit,c ctl(1)  : Set one stopping criterion, 'ftol' (c=1), 'rtol' (c=2)
##                  or 'vtol' (c=3) to the value of the 'tol' option.    <1>
##
## tol, t ctl(2)  : Threshold in termination test chosen by 'crit'  <10*eps>
##
## narg  ctl(3)  : Position of the minimized argument in args            <1>
## maxev ctl(4)  : Maximum number of function evaluations. This number <inf>
##                 may be slightly exceeded.
## isz   ctl(5)  : Size of initial simplex, which is :                   <1>
##
##                { x + e_i | i in 0..N } 
## 
##                Where x == args{narg} is the initial value 
##                 e_0    == zeros (size (x)), 
##                 e_i(j) == 0 if j != i and e_i(i) == ctl(5)
##                 e_i    has same size as x
##
##                Set ctl(5) to the distance you expect between the starting
##                point and the minimum.
##
## rst   ctl(6)   : When a minimum is found the algorithm restarts next to
##                  it until the minimum does not improve anymore. ctl(6) is
##                  the maximum number of restarts. Set ctl(6) to zero if
##                  you know the function is well-behaved or if you don't
##                  mind not getting a true minimum.                     <0>
##
## verbose, v     Be more or less verbose (quiet=0)                      <0>

function [x,v,nev] = nelder_mead_min (f, args, varargin)

verbose = 0;

				# Default control variables
ftol = rtol = 10*eps;		# Stop either by likeness of values or
vtol = nan;                     # radius, but don't care about volume.
crit = 0;			# Stopping criterion            ctl(1)
tol = 10*eps;			# Stopping test's threshold     ctl(2)
narg = 1;			# Position of minimized arg     ctl(3)
maxev = inf;			# Max num of func evaluations   ctl(4)
isz = 1;			# Initial size                  ctl(5)
rst = 0;			# Max # of restarts


if nargin >= 3,			# Read control arguments
  va_arg_cnt = 1;
  if nargin > 3, 
          ctl = struct (varargin{:}); 
  else 
          ctl = varargin{va_arg_cnt++}; 
  end
  if isnumeric (ctl)
    if length (ctl)>=1 && !isnan (ctl(1)), crit = ctl(1); end
    if length (ctl)>=2 && !isnan (ctl(2)), tol = ctl(2); end
    if length (ctl)>=3 && !isnan (ctl(3)), narg = ctl(3); end
    if length (ctl)>=4 && !isnan (ctl(4)), maxev = ctl(4); end
    if length (ctl)>=5 && !isnan (ctl(5)), isz = ctl(5); end
    if length (ctl)>=6 && !isnan (ctl(6)), rst = ctl(6); end
  else
    if isfield (ctl, "crit") && ! isnan (ctl.crit ), crit  = ctl.crit ; end
    if isfield (ctl,  "tol") && ! isnan (ctl.tol  ), tol   = ctl.tol  ; end
    if isfield (ctl, "ftol") && ! isnan (ctl.ftol ), ftol  = ctl.ftol ; end
    if isfield (ctl, "rtol") && ! isnan (ctl.rtol ), rtol  = ctl.rtol ; end
    if isfield (ctl, "vtol") && ! isnan (ctl.vtol ), vtol  = ctl.vtol ; end
    if isfield (ctl, "narg") && ! isnan (ctl.narg ), narg  = ctl.narg ; end
    if isfield (ctl,"maxev") && ! isnan (ctl.maxev), maxev = ctl.maxev; end
    if isfield (ctl,  "isz") && ! isnan (ctl.isz  ), isz   = ctl.isz  ; end
    if isfield (ctl,  "rst") && ! isnan (ctl.rst  ), rst   = ctl.rst  ; end
    if isfield(ctl,"verbose")&& !isnan(ctl.verbose),verbose=ctl.verbose;end
  end
end


if     crit == 1, ftol = tol; 
elseif crit == 2, rtol = tol; 
elseif crit == 3, vtol = tol;
elseif crit, error ("crit is %i. Should be 1,2 or 3.\n");
end

if iscell (args)
  x = args{1};
else				# Single argument
  x = args;
  args = {args};
endif

if narg > length (args)		# Check
  error ("nelder_mead_min : narg==%i, length (args)==%i\n",
	 narg, length (args));
end

[R,C] = size (x);
N = R*C;			# Size of argument
x = x(:);
				# Initial simplex
u = isz * eye (N+1,N) + ones(N+1,1)*x';

y = zeros (N+1,1);
for i = 1:N+1,
  y(i) = feval (f, args{1:narg-1},reshape(u(i,:),R,C),args{narg+1:end});
end ;
nev = N+1;

[ymin,imin] = min(y);
ymin0 = ymin;
## y
nextprint = 0 ;
v = nan;
while nev <= maxev,

  ## ymin, ymax, ymx2 : lowest, highest and 2nd highest function values
  ## imin, imax, imx2 : indices of vertices with these values
  [ymin,imin] = min(y);  [ymax,imax] = max(y) ;
  y(imax) = ymin ;  
  [ymx2,imx2] = max(y) ;  
  y(imax) = ymax ;
  
  ## ymin may be > ymin0 after restarting
  ## if ymin > ymin0 ,
  ## "nelder-mead : Whoa 'downsimplex' Should be renamed 'upsimplex'"
  ## keyboard
  ## end
  
				# Compute stopping criterion
  done = 0;
  if ! isnan (ftol), 
     done |= ((max(y)-min(y)) / max(1,max(abs(y))) < ftol); 
  end
  if ! isnan (rtol), 
     done |= (2*max (max (u) - min (u)) < rtol); 
  end
  if ! isnan (vtol)
    done |= (abs (det (u(1:N,:)-ones(N,1)*u(N+1,:)))/factorial(N) < vtol);
  end
  ## [ 2*max (max (u) - min (u)), abs (det (u(1:N,:)-ones(N,1)*u(N+1,:)))/factorial(N);\
  ##  rtol, vtol]
  
				# Eventually print some info
  if verbose && nev > nextprint && ! done 

    printf("nev=%-5d   imin=%-3d   ymin=%-8.3g  done=%i\n",\
	   nev,imin,ymin,done) ;

    nextprint = nextprint + 100 ;
  end
  
  if done			# Termination test
    if (rst > 0) && (isnan (v) || v > ymin)
      rst--;
      if verbose
	if isnan (v),
	  printf ("Restarting next to minimum %10.3e\n",ymin); 
	else
	  printf ("Restarting next to minimum %10.3e\n",ymin-v); 
	end
      end
				# Keep best minimum
      x = reshape (u(imin,:), R, C) ;
      v = ymin ;
    
      jumplen = 10 * max (max (u) - min (u));
      
      u += jumplen * randn (size (u));
      for i = 1:N+1, y(i) = \
	    feval (f, args{1:narg-1},reshape(u(i,:),R,C),args{narg+1:length(args)});
      end
      nev += N+1;
      [ymin,imin] = min(y);  [ymax,imax] = max(y);
      y(imax) = ymin;
      [ymx2,imx2] = max(y);
      y(imax) = ymax ;
    else
      if isnan (v),
	x = reshape (u(imin,:), R, C) ;
	v = ymin ;
      end
      if verbose,
	printf("nev=%-5d   imin=%-3d   ymin=%-8.3g  done=%i. Done\n",\
	       nev,imin,ymin,done) ;
      end
      return
    end

  end
  ##   [ y' u ]

  tra = 0 ;			# 'trace' debug var contains flags
  if verbose > 1, str = sprintf (" %i : %10.3e --",done,ymin); end

				# Look for a new point
  xsum = sum(u) ;		# Consider reflection of worst vertice
				# around centroid.
  ## f1 = (1-(-1))/N = 2/N;
  ## f2 = f1 - (-1)  = 2/N + 1 = (N+2)/N
  xnew = (2*xsum - (N+2)*u(imax,:)) / N;
  ## xnew = (2*xsum - N*u(imax,:)) / N;
  ynew = feval (f, args{1:narg-1},reshape(xnew,R,C),args{narg+1:length(args)});
  nev++;
  
  if ynew <= ymin ,		# Reflection is good
    
    tra += 1 ;
    if verbose > 1
      str = [str,sprintf(" %3i : %10.3e good refl >>",nev,ynew-ymin)];
    end
    y(imax) = ynew; u(imax,:) = xnew ;
    ## ymin = ynew;
    ## imin = imax;
    xsum = sum(u) ;
    
    ## f1 = (1-2)/N = -1/N
    ## f2 = f1 - 2  = -1/N - 2 = -(2*N+1)/N
    xnew = ( -xsum + (2*N+1)*u(imax,:) ) / N;
    ynew = feval (f, args{1:narg-1},reshape(xnew,R,C),args{narg+1:length(args)});
    nev++;
      
    if ynew <= ymin ,		# expansion improves
      tra += 2 ;
      ##      'expanded reflection'
      y(imax) = ynew ; u(imax,:) = xnew ;
      xsum = sum(u) ;
      if verbose > 1
	str = [str,sprintf(" %3i : %10.3e expd refl",nev,ynew-ymin)];
      end
    else
      tra += 4 ;
      ##      'plain reflection'
      ## Updating of y and u has already been done
      if verbose > 1
	str = [str,sprintf(" %3i : %10.3e plain ref",nev,ynew-ymin)];
      end
    end
				# Reflexion is really bad
  elseif ynew >= ymax ,
    
    tra += 8 ;
    if verbose > 1
      str = [str,sprintf(" %3i : %10.3e intermedt >>",nev,ynew-ymin)];
    end
    ## look for intermediate point
				# Bring worst point closer to centroid
    ## f1 = (1-0.5)/N = 0.5/N
    ## f2 = f1 - 0.5  = 0.5*(1 - N)/N
    xnew = 0.5*(xsum + (N-1)*u(imax,:)) / N;
    ynew = feval (f, args{1:narg-1},reshape(xnew,R,C),args{narg+1:length(args)});
    nev++;

    if ynew >= ymax ,		# New point is even worse. Contract whole
				# simplex

      nev += N + 1 ;
      ## u0 = u;
      u = (u + ones(N+1,1)*u(imin,:)) / 2;
      ## keyboard

      ## Code that doesn't care about value of empty_list_elements_ok
      if     imin == 1  , ii = 2:N+1; 
      elseif imin == N+1, ii = 1:N;
      else                ii = [1:imin-1,imin+1:N+1]; end
      for i = ii
	y(i) = \
	    ynew = feval (f, args{1:narg-1},reshape(u(i,:),R,C),args{narg+1:length(args)});
      end
      ##      'contraction'
      tra += 16 ;
      if verbose > 1
	str = [str,sprintf(" %3i contractn",nev)];
      end
    else				# Replace highest point
      y(imax) = ynew ; u(imax,:) = xnew ;
      xsum = sum(u) ; 
      ##      'intermediate'
      tra += 32 ;
      if verbose > 1
	str = [str,sprintf(" %3i : %10.3e intermedt",nev,ynew-ymin)];
      end
    end

  else				# Reflexion is neither good nor bad
    y(imax) = ynew ; u(imax,:) = xnew ;
    xsum = sum(u) ; 
    ##      'plain reflection (2)'
    tra += 64 ;
    if verbose > 1
      str = [str,sprintf(" %3i : %10.3e keep refl",nev,ynew-ymin)];
    end
  end
  if verbose > 1, printf ("%s\n",str); end
end

if verbose >= 0
  printf ("nelder_mead : Too many iterations. Returning\n");
end

if isnan (v) || v > ymin,
  x = reshape (u(imin,:), R, C) ;
  v = ymin ;
end
