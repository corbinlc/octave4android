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

## [x,v,nev,h,args] = d2_min(f,d2f,args,ctl,code) - Newton-like minimization
##
## Minimize f(x) using 1st and 2nd derivatives. Any function w/ second
## derivatives can be minimized, as in Newton. f(x) decreases at each
## iteration, as in Levenberg-Marquardt. This function is inspired from the
## Levenberg-Marquardt algorithm found in the book "Numerical Recipes".
##
## ARGUMENTS :
## f    : string : Cost function's name
##
## d2f  : string : Name of function returning the cost (1x1), its
##                 differential (1xN) and its second differential or it's
##                 pseudo-inverse (NxN) (see ctl(5) below) :
##
##                 [v,dv,d2v] = d2f (x).
##
## args : list   : f and d2f's arguments. By default, minimize the 1st
##     or matrix : argument.
##
## ctl  : vector : Control arguments (see below)
##      or struct
##
## code : string : code will be evaluated after each outer loop that
##                 produced some (any) improvement. Variables visible from
##                 "code" include "x", the best parameter found, "v" the
##                 best value and "args", the list of all arguments. All can
##                 be modified. This option can be used to re-parameterize 
##                 the argument space during optimization
##
## CONTROL VARIABLE ctl : (optional). May be a struct or a vector of length
## ---------------------- 5 or less where NaNs are ignored. Default values
##                        are written <value>.
## FIELD  VECTOR
## NAME    POS
##
## ftol, f N/A    : Stop search when value doesn't improve, as tested by
##
##                   f > Deltaf/max(|f(x)|,1)
##
##             where Deltaf is the decrease in f observed in the last
##             iteration.                                     <10*sqrt(eps)>
##
## utol, u N/A    : Stop search when updates are small, as tested by
##
##                   u > max { dx(i)/max(|x(i)|,1) | i in 1..N }
##
##             where  dx is the change in the x that occured in the last
##             iteration.                                              <NaN>
##
## dtol, d N/A    : Stop search when derivative is small, as tested by
## 
##                   d > norm (dv)                                     <eps>
##
## crit, c ctl(1) : Set one stopping criterion, 'ftol' (c=1), 'utol' (c=2)
##                  or 'dtol' (c=3) to the value of by the 'tol' option. <1>
##
## tol, t  ctl(2) : Threshold in termination test chosen by 'crit'  <10*eps>
##
## narg, n ctl(3) : Position of the minimized argument in args           <1>
## maxev,m ctl(4) : Maximum number of function evaluations             <inf>
## maxout,m       : Maximum number of outer loops                      <inf>
## id2f, i ctl(5) : 0 if d2f returns the 2nd derivatives, 1 if           <0>
##                  it returns its pseudo-inverse.
##
## verbose, v N/A : Be more or less verbose (quiet=0)                    <0>

function [xbest,vbest,nev,hbest,args] = d2_min (f,d2f,args,ctl,code)

maxout = inf;
maxinner = 30 ;

tcoeff = 0.5 ;			# Discount on total weight
ncoeff = 0.5 ;			# Discount on weight of newton
ocoeff = 1.5 ;			# Factor for outwards searching

report = 0 ;			# Never report
verbose = 0 ;			# Be quiet
prudent = 1 ;			# Check coherence of d2f and f?

niter = 0 ;

crit = 0;			# Default control variables
ftol = 10 * sqrt (eps);
dtol = eps;
utol = tol = nan;
narg = 1;
maxev = inf;
id2f = 0;

if nargin >= 4			# Read arguments
  if isnumeric (ctl)
    if length (ctl)>=1 && !isnan (ctl(1)), crit  = ctl(1); end
    if length (ctl)>=2 && !isnan (ctl(2)), tol   = ctl(2); end
    if length (ctl)>=3 && !isnan (ctl(3)), narg  = ctl(3); end
    if length (ctl)>=4 && !isnan (ctl(4)), maxev = ctl(4); end
    if length (ctl)>=5 && !isnan (ctl(5)), id2f  = ctl(5); end
  elseif isstruct (ctl)
    if isfield (ctl, "crit")   , crit    = ctl.crit   ; end
    if isfield (ctl, "tol")    , tol     = ctl.tol    ; end
    if isfield (ctl, "narg")   , narg    = ctl.narg   ; end
    if isfield (ctl, "maxev")  , maxev   = ctl.maxev  ; end
    if isfield (ctl, "maxout") , maxout  = ctl.maxout ; end
    if isfield (ctl, "id2f")   , id2f    = ctl.id2f   ; end
    if isfield (ctl, "verbose"), verbose = ctl.verbose; end
    if isfield (ctl, "code")   , code    = ctl.code   ; end
  else 
    error ("The 'ctl' argument should be either a vector or a struct");
  end
end

if     crit == 1, ftol = tol;
elseif crit == 2, utol = tol;
elseif crit == 3, dtol = tol;
elseif crit, error ("crit is %i. Should be 1,2 or 3.\n");
end


if nargin < 5, code = "" ; end

if iscell (args)		# List of arguments 
  x = args{narg};
else				# Single argument
  x = args;
  args = {args}; 
end

############################## Checking ##############################
if narg > length (args)
  error ("d2_min : narg==%i, length (args)==%i\n",
	 narg, length (args));
end

if tol <= 0
  printf ("d2_min : tol=%8.3g <= 0\n",tol) ;
end

if !ischar (d2f) || !ischar (f)
  printf ("d2_min : f and d2f should be strings!\n");
end

sz = size (x); N = prod (sz);

v = feval (f, args{:});
nev = [1,0];

if prudent && (! isnumeric (v) || isnan (v) || any (size (v)>1))
  error ("Function '%s' returns inadequate output", f);
end

xbest = x = x(:);
vold = vbest = nan ;		# Values of f
hbest = nan ;			# Inv. Hessian

if verbose
    printf ( "d2_min : Initially, v=%8.3g\n",v);
end

while niter <= maxout
  niter += 1;
  if nev(1) < maxev, break; end;  

  [v,d,h] = feval (d2f, args{1:narg-1},reshape(x,sz),args{narg+1:end}); 
  nev(2)++;

  if prudent && niter <= 1 && \
	(! isnumeric (v) || isnan (v) || any (size (v)>1) || \
	 ! isnumeric (d) || length (d(:)) != N || \
	 ! isnumeric (h) || any (size (h) != N))
    error ("Function '%s' returns inadequate output", d2f);
  end

  if ! id2f, h = pinv (h); end
  d = d(:);

  if prudent
    v2 = feval (f, args{1:narg-1},reshape(x,sz),args{narg+1:end});
    nev(1)++;
    if abs(v2-v) > 0.001 * sqrt(eps) * max (abs(v2), 1)
      printf ("d2_min : f and d2f disagree %8.3g\n",abs(v2-v));
    end
  end

  xbest = x ;
  if ! isnan (vbest)		# Check that v ==vbest 
    if abs (vbest - v) > 1000*eps * max (vbest, 1)
      printf ("d2_min : vbest changed at beginning of outer loop\n");
    end
  end
  vold = vbest = v ;
  hbest = h ;

  if length (code), abest = args; end # Eventually stash all args

  if verbose || (report && rem(niter,max(report,1)) == 1)
    printf ("d2_min : niter=%d, v=%8.3g\n",niter,v );
  end

  if norm (d) < dtol		# Check for small derivative
    if verbose || report 
      printf ("d2_min : Exiting because of low gradient\n");
    end
    break;			# Exit outer loop
  end

  dnewton = -h*d ;		# Newton step
				# Heuristic for negative hessian
  if dnewton'*d > 0, dnewton = -100*d; end
  wn = 1 ;			# Weight of Newton step
  wt = 1 ;			# Total weight
  
  ninner = 0; 
  done_inner = 0;	# 0=not found. 1=Ready to quit inner.
  
				# ##########################################
  while ninner < maxinner,	# Inner loop ###############################
    ninner += 1;
				# Proposed step
    dx = wt*(wn*dnewton - (1-wn)*d) ;
    xnew = x+dx ;

    if verbose
      printf (["Weight : total=%8.3g, newtons's=%8.3g  vbest=%8.3g ",...
	       "Norm:Newton=%8.3g, deriv=%8.3g\n"],...
	      wt,wn,vbest,norm(wt*wn*dnewton),norm(wt*(1-wn)*d));
    end
    if any(isnan(xnew))
      printf ("d2_min : Whoa!! any(isnan(xnew)) (1)\n"); 
    end

    vnew = feval (f, args{1:narg-1},reshape(xnew,sz),args{narg+1:end});
    nev(1)++;

    if vnew<vbest		# Stash best values
      dbest = dx ; 
      vbest = vnew; 
      xbest = xnew; 

      done_inner = 1 ;		# Will go out at next increase
      if verbose
        printf ( "d2_min : Found better value\n");
      end
      
    elseif done_inner == 1	# Time to go out
      if verbose
          printf ( "d2_min : Quitting %d th inner loop\n",niter);
      end
      break;			# out of inner loop
    end
    wt = wt*tcoeff ;		# Reduce norm of proposed step
    wn = wn*ncoeff ;		# And bring it closer to derivative

  end				# End of inner loop ########################
				# ##########################################

  wbest = 0;			# Best coeff for dbest

  if ninner >= maxinner		# There was a problem
    if verbose
      printf ( "d2_min : Too many inner loops (vnew=%8.3g)\n",vnew);
    end

				# ##########################################
  else				# Look for improvement along dbest
    wn = ocoeff ;
    xnew = x+wn*dbest;
    if any(isnan(xnew)),
      printf ("d2_min : Whoa!! any(isnan(xnew)) (2)\n"); 
    end
    vnew = feval (f, args{1:narg-1},reshape(xnew,sz),args{narg+1:end});
    nev(1)++;

    while vnew < vbest,
      vbest = vnew;		# Stash best values
      wbest = wn;
      xbest = xnew; 
      wn = wn*ocoeff ;
      xnew = x+wn*dbest;
      vnew = feval (f, args{1:narg-1},reshape(xnew,sz),args{narg+1:length(args)});
      if verbose
          printf ( "Looking farther : v = %8.3g\n",vnew);
      end
      nev(1)++;
    end
  end				# End of improving along dbest
				# ##########################################

  if verbose || rem(niter,max(report,1)) == 1
    if vold,
      if verbose
	printf ("d2_min : Inner loop : vbest=%8.5g, vbest/vold=%8.5g\n",\
		vbest,vbest/vold);
      end
    else
      if verbose
        printf ( "d2_min : Inner loop : vbest=%8.5g, vold=0\n", vbest);
      end
    end
  end

  if vbest < vold
    ## "improvement found"
    if prudent
      tmpv = feval (f, args{1:narg-1},reshape(xbest,sz),args{2:end});
      nev(1)++;

      if abs (tmpv-vbest) > eps
	printf ("d2_min : Whoa! Value at xbest changed by %g\n",\
		abs(tmpv-vbest));
      end
    end
    v = vbest; x = xbest;
    if ! isempty (code)
      if verbose
        printf ("d2_min : Going to eval (\"%s\")\n",code);
      end

      xstash = xbest;
      astash = abest;
      args = abest;		# Here : added 2001/11/07. Is that right?
      x = xbest;
      eval (code, "printf (\"code fails\\n\");");
      xbest = x; 
      abest = args;
				# Check whether eval (code) changes value
      if prudent
        tmpv = feval (f, args{1:narg-1},reshape(x,sz),args{2:end});
        nev(1)++;
	if abs (tmpv-vbest) > max (min (100*eps,0.00001*abs(vbest)), eps) ,
	  printf ("d2_min : Whoa! Value changes by %g after eval (code)\n",\
		  abs (tmpv-vbest));
	end
      end
    end
  end

  if ! isnan (ftol) && ftol > (vold-vbest)/max(vold,1), 
    if verbose || report
      printf ("d2_min : Quitting, niter=%-3d v=%8.3g, ",niter,v);
      if vold, printf ("v/vold=%8.3g \n",v/vold);
      else     printf ("vold  =0     \n",v);
      end
    end
    break ;    			# out of outer loop
  end
  if ! isnan (utol) && utol > max (abs (wbest*dbest)) / max(abs (xbest),1)
    if verbose || report
      printf ("d2_min : Quitting, niter=%-3d v=%8.3g, ",niter,v);
      if vold, printf ("v/vold=%8.3g \n",v/vold);
      else     printf ("vold  =0     \n",v);
      end
    end
    break ;			# out of outer loop
  end   
end				# End of outer loop ##################

xbest = reshape (xbest, sz);
if length (code) 
  args = abest;
  args(narg) = xbest; 
end

if niter > maxout
  if verbose
    printf ( "d2_min : Outer loop lasts forever\n");
  end
end

				# One last check
if prudent
  err = feval (f, args{1:narg-1},reshape(xbest,sz),args{2:end});
  nev(1)++;

  if abs (err-vbest) > eps,
    printf ("d2_min : Whoa!! xbest does not eval to vbest\n");
    printf ("       : %8.3e - %8.3e = %8.3e != 0\n",err,vbest,err-vbest);
  end
end

