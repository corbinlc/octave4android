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

## [x,v,nev,...] = minimize (f,args,...) - Minimize f
##
## ARGUMENTS
## f    : string  : Name of function. Must return a real value
## args : list or : List of arguments to f (by default, minimize the first)
##        matrix  : f's only argument
##
## RETURNED VALUES
## x   : matrix  : Local minimum of f. Let's suppose x is M-by-N.
## v   : real    : Value of f in x0
## nev : integer : Number of function evaluations 
##     or 1 x 2  : Number of function and derivative evaluations (if
##                 derivatives are used)
## 
##
## Extra arguments are either a succession of option-value pairs or a single
## list or struct of option-value pairs (for unary options, the value in the
## struct is ignored).
## 
## OPTIONS : DERIVATIVES   Derivatives may be used if one of these options
## ---------------------   uesd. Otherwise, the Nelder-Mean (see
##                         nelder_mead_min) method is used.
## 
## 'd2f', d2f     : Name of a function that returns the value of f, of its
##                  1st and 2nd derivatives : [fx,dfx,d2fx] = feval (d2f, x)
##                  where fx is a real number, dfx is 1x(M*N) and d2fx is
##                  (M*N)x(M*N). A Newton-like method (d2_min) will be used.
##
## 'hess'         : Use [fx,dfx,d2fx] = leval (f, args) to compute 1st and
##                  2nd derivatives, and use a Newton-like method (d2_min).
##
## 'd2i', d2i     : Name of a function that returns the value of f, of its
##                  1st and pseudo-inverse of second derivatives : 
##                  [fx,dfx,id2fx] = feval (d2i, x) where fx is a real
##                  number, dfx is 1x(M*N) and d2ix is (M*N)x(M*N).
##                  A Newton-like method will be used (see d2_min).
##
## 'ihess'        : Use [fx,dfx,id2fx] = leval (f, args) to compute 1st
##                  derivative and the pseudo-inverse of 2nd derivatives,
##                  and use a Newton-like method (d2_min).
##
##            NOTE : df, d2f or d2i take the same arguments as f.
## 
## 'order', n     : Use derivatives of order n. If the n'th order derivative
##                  is not specified by 'df', 'd2f' or 'd2i', it will be
##                  computed numerically. Currently, only order 1 works.
## 
## 'ndiff'        : Use a variable metric method (bfgs) using numerical
##                  differentiation.
##
## OPTIONS : STOPPING CRITERIA  Default is to use 'tol'
## ---------------------------
## 'ftol', ftol   : Stop search when value doesn't improve, as tested by
##
##              ftol > Deltaf/max(|f(x)|,1)
##
##                 where Deltaf is the decrease in f observed in the last
##                 iteration.                                 Default=10*eps
##
## 'utol', utol   : Stop search when updates are small, as tested by
##
##              tol > max { dx(i)/max(|x(i)|,1) | i in 1..N }
##
##                 where  dx is the change in the x that occured in the last
##                 iteration.
##
## 'dtol',dtol    : Stop search when derivatives are small, as tested by
##
##              dtol > max { df(i)*max(|x(i)|,1)/max(v,1) | i in 1..N }
##
##                 where x is the current minimum, v is func(x) and df is
##                 the derivative of f in x. This option is ignored if
##                 derivatives are not used in optimization.
##
## MISC. OPTIONS
## -------------
## 'maxev', m     : Maximum number of function evaluations             <inf>
##
## 'narg' , narg  : Position of the minimized argument in args           <1>
## 'isz'  , step  : Initial step size (only for 0 and 1st order method)  <1>
##                  Should correspond to expected distance to minimum
## 'verbose'      : Display messages during execution
##
## 'backend'      : Instead of performing the minimization itself, return
##                  [backend, control], the name and control argument of the
##                  backend used by minimize(). Minimimzation can then be
##                  obtained without the overhead of minimize by calling, if
##                  a 0 or 1st order method is used :
##
##              [x,v,nev] = feval (backend, args, control)
##                   
##                  or, if a 2nd order method is used :
##
##              [x,v,nev] = feval (backend, control.d2f, args, control)

function [x,v,nev,varargout] = minimize (f,args,varargin)

## Oldies
##
## 'df' , df      : Name of a function that returns the derivatives of f
##                  in x : dfx = feval (df, x) where dfx is 1x(M*N). A
##                  variable metric method (see bfgs) will be used.
##
## 'jac'          : Use [fx, dfx] = leval(f, args) to compute derivatives
##                  and use a variable metric method (bfgs).
##


# ####################################################################
# Read the options ###################################################
# ####################################################################
# Options with a value
op1 = "ftol utol dtol df d2f d2i order narg maxev isz";
# Boolean options 
op0 = "verbose backend jac hess ihess ndiff" ;

default = struct ("backend",0,"verbose",0,\
		    "df","",  "df", "","d2f","","d2i","",  \
		    "hess", 0,  "ihess", 0,  "jac", 0,"ndiff", 0,  \
		    "ftol" ,nan, "utol",nan, "dtol", nan,\
		    "order",nan, "narg",nan, "maxev",nan,\
		    "isz",  nan);

if nargin == 3			# Accomodation to struct and list optional
  tmp = varargin{1};

  if isstruct (tmp)
    opls = {};
    for [v,k] = tmp		# Treat separately unary and binary opts
      if findstr ([" ",k," "],op0)
	opls (end+1) = {k};        # append k 
      else
	opls (end+[1:2]) = {k, v}; # append k and v 
      end
    end
  elseif iscell (tmp)
    opls = tmp;
  else
    opls = {tmp};
  end
else
  opls = varargin;
end
ops = read_options (opls,\
		    "op0",op0, "op1",op1, "default",default);

backend=ops.backend; verbose=ops.verbose; 
df=ops.df; d2f=ops.d2f; d2i=ops.d2i; 
hess=ops.hess; ihess=ops.ihess; jac=ops.jac; 
ftol=ops.ftol; utol=ops.utol; dtol=ops.dtol;
order=ops.order; narg=ops.narg; maxev=ops.maxev; 
isz=ops.isz; ndiff=ops.ndiff;

if length (df), error ("Option 'df' doesn't exist any more. Sorry.\n");end
if jac, error ("Option 'jac' doesn't exist any more. Sorry.\n");end

				# Basic coherence checks #############

ws = "";			# Warning string
es = "";			# Error string

				# Warn if more than 1 differential is given
if !!length (df) + !!length (d2f) + !!length (d2i) + jac + hess + ihess + \
      ndiff > 1
				# Order of preference of 
  if length (d2i), ws = [ws,"d2i='",d2i,"', "]; end
  if length (d2f), ws = [ws,"d2f='",d2f,"', "]; end
  if length (df),  ws = [ws,"df='",df,"', "]; end
  if hess       ,  ws = [ws,"hess, "]; end
  if ihess      ,  ws = [ws,"ihess, "]; end
  if jac        ,  ws = [ws,"jac, "]; end
  if ndiff      ,  ws = [ws,"ndiff, "]; end
  ws = ws(1:length(ws)-2);
  ws = ["Options ",ws," were passed. Only one will be used\n"]
end

				# Check that enough args are passed to call
				# f(), unless backend is specified, in which
				# case I don't need to call f()
if ! isnan (narg) && ! backend
  if narg > 1
    es = [es,sprintf("narg=%i, but a single argument was passed\n",narg)];
  end
end

if length (ws), warn (ws); end
if length (es), error (es); end	# EOF Basic coherence checks #########


op = 0;				# Set if any option is passed and should be
				# passed to backend

if ! isnan (ftol)   , ctls.ftol    = ftol;  op = 1; end
if ! isnan (utol)   , ctls.utol    = utol;  op = 1; end
if ! isnan (dtol)   , ctls.dtol    = dtol;  op = 1; end
if ! isnan (maxev)  , ctls.maxev   = maxev; op = 1; end
if ! isnan (narg)   , ctls.narg    = narg;  op = 1; end
if ! isnan (isz)    , ctls.isz     = isz;   op = 1; end
if         verbose  , ctls.verbose = 1;     op = 1; end

				# defaults That are used in this function :
if isnan (narg), narg = 1; end

				# ##########################################
				# Choose one optimization method ###########
				# Choose according to available derivatives 
if     ihess, d2f = f;  ctls.id2f = 1; op = 1;
elseif hess,  d2f = f;
end
  

if     length (d2i), method = "d2_min"; ctls.id2f = 1; op = 1; d2f = d2i;
elseif length (d2f), method = "d2_min";
### elseif length (df) , method = "bfgsmin"; ctls.df  = df; op = 1;
### elseif jac         , method = "bfgsmin"; ctls.jac = 1 ; op = 1;
  ## else                 method = "nelder_mead_min";
  ## end
				# Choose method because ndiff is passed ####
elseif ndiff       , method = "bfgsmin";

				# Choose method by specifying order ########
elseif ! isnan (order)

  if     order == 0, method = "nelder_mead_min";
  elseif order == 1
    method = "bfgsmin";

  elseif order == 2
    if ! (length (d2f) || length (d2i))
      error ("minimize(): 'order' is 2, but 2nd differential is missing\n");
    end
  else
    error ("minimize(): 'order' option only implemented for order<=2\n");
  end
else				# Default is nelder_mead_min
  method = "nelder_mead_min";
end				# EOF choose method ########################

if verbose
  printf ("minimize(): Using '%s' as back-end\n",method);
end

				# More checks ##############################
ws = "";
if !isnan (isz) && strcmp (method,"d2_min")
  ws = [ws,"option 'isz' is passed to method that doesn't use it"];
end
if length (ws), warn (ws); end
				# EOF More checks ##########################

if     strcmp (method, "d2_min"), all_args = {f, d2f, args};
elseif strcmp (method, "bfgsmin"),all_args = {f, args};
else                              all_args = {f, args};
end
				# Eventually add ctls to argument list
if op, all_args{end+1} = ctls; end

if ! backend			# Call the backend ###################
  if strcmp (method, "d2_min"),
    [x,v,nev,h] = d2_min(all_args{:});
				# Eventually return inverse of Hessian
    if nargout > 3, varargout{1} = h; vr_val_cnt=2; end 
  elseif strcmp (method, "bfgsmin")
    nev = nan;
    if !iscell(args), args = {args}; end
    if isnan (ftol), ftol = 1e-12; end # Use bfgsmin's defaults
    if isnan (utol), utol = 1e-6; end
    if isnan (dtol), dtol = 1e-5; end
    if isnan (maxev), maxev = inf; end
    [x, v, okcv] = bfgsmin (f, args, {maxev,verbose,1,narg,0,ftol,utol,dtol});
  else
    [x,v,nev] = feval (method, all_args{:});
  end

else				# Don't call backend, just return its name
				# and arguments. 

  x = method;
  if op, v = ctls; else v = []; end
end


