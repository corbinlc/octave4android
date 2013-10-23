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

## [x,v,flag,out,df,d2f] = fminunc_compat (f,x,opt,...) - M*tlab-like optimization
##
## Imitation of m*tlab's fminunc(). The optional 'opt' argument is a struct,
## e.g. produced by 'optimset()'. 'fminunc_compat' has been deprecated in
## favor of 'fminunc', which is now part of core Octave. This function
## will possibly be removed from future versions of the 'optim' package.
##
## Supported options
## -----------------
## Diagnostics, [off|on] : Be verbose
## Display    , [off|iter|notify|final]
##                       : Be verbose unless value is "off"
## GradObj    , [off|on] : Function's 2nd return value is derivatives
## Hessian    , [off|on] : Function's 2nd and 3rd return value are
##                         derivatives and Hessian.
## TolFun     , scalar   : Termination criterion (see 'ftol' in minimize())
## TolX       , scalar   : Termination criterion (see 'utol' in minimize())
## MaxFunEvals, int      : Max. number of function evaluations
## MaxIter    , int      : Max. number of algorithm iterations
##
## These non-m*tlab are provided to facilitate porting code to octave:
## -----------------------
## "MinEquiv" , [off|on] : Don't minimize 'fun', but instead return the
##                         option passed to minimize().
##
## "Backend"  , [off|on] : Don't minimize 'fun', but instead return
##                         [backend, opt], the name of the backend
##                         optimization function that is used and the
##                         optional arguments that will be passed to it. See
##                         the 'backend' option of minimize().
##
## This function is a front-end to minimize().

function [x,fval,flag,out,df,d2f] = fminunc_compat (fun,x0,opt,varargin)

  persistent warned = false;
  if (! warned)
    warned = true;
    warning ("Octave:deprecated-function",
             "`fminunc_compat' has been deprecated, and will be removed in the future. Use `fminunc' from Octave core instead.");
  endif

if nargin < 3, opt = struct (); end
if nargin > 3, 
  args = {x0, varargin{:}};
else 
  args = {x0};
end

## Do some checks ####################################################
ws = es = "";

## Check for unknown options
## All known options
opn = [" DerivativeCheck Diagnostics DiffMaxChange DiffMinChange",\
       " Display GoalsExactAchieve GradConstr GradObj Hessian HessMult",\
       " HessPattern HessUpdate Jacobian JacobMult JacobPattern",\
       " LargeScale LevenbergMarquardt LineSearchType MaxFunEvals MaxIter",\
       " MaxPCGIter MeritFunction MinAbsMax PrecondBandWidth TolCon",\
       " TolFun TolPCG TolX TypicalX ",\
       " MinEquiv Backend "];

for [v,k] = opt
  if ! findstr ([" ",k," "],opn)
    es = [es,sprintf("Unknown option '%s'\n",k)];
  end
end
## Check for ignored options
## All ignored options
iop = [" DerivativeCheck DiffMaxChange DiffMinChange",\
       " Display GoalsExactAchieve GradConstr HessMult",\
       " HessPattern HessUpdate JacobMult JacobPattern",\
       " LargeScale LevenbergMarquardt LineSearchType",\
       " MaxPCGIter MeritFunction MinAbsMax PrecondBandWidth TolCon",\
       " TolPCG TypicalX "];
for [v,k] = opt
  if ! findstr ([" ",k," "],iop)
    ws = [ws,sprintf("Ignoring option '%s'\n",k)];
  end
end

if length (ws) && ! length (es), warning (ws);
elseif              length (es), error ([ws,es]);
end

## Transform fminunc options into minimize() options

opm = struct();		# minimize() options

equiv = struct ("TolX"       , "utol"   , "TolFun"     , "ftol",\
		"MaxFunEvals", "maxev"  , "MaxIter"    , "maxit",\
		"GradObj"    , "jac"    , "Hessian"    , "hess",\
		"Display"    , "verbose", "Diagnostics", "verbose",\
		"Backend"    , "backend");

for [v,k] = equiv
  if isfield (opt,k)
    opm = setfield (opm, getfield(equiv,k), getfield(opt,k));
  end
end

				# Transform "off" into 0, other strings into
				# 1.
for [v,k] = opm
  if ischar (v)
    if strcmp (v,"off")
      opm = setfield (opm, k,0);
    else
      opm = setfield (opm, k,1);
    end
  end
end

unary_opt = " hess jac backend verbose ";
opml = {};
for [v,k] = opm
  if findstr ([" ",k," "], unary_opt)
    opml(end+1) = {k};          # append k 
  else
    opml(end+[1,2]) = {k,v};    # append k and v 
  end
end
				# Return only options to minimize() ##
if isfield (opt, "MinEquiv")
  x = opml;			
  if nargout > 1
    warning ("Only 1 return value is defined with the 'MinEquiv' option");
  end
  return
				# Use the backend option #############
elseif isfield (opm, "backend")
  [x,fval] = minimize (fun, args, opml);
  if nargout > 2
    warning ("Only 2 return values are defined with the 'Backend' option");
  end
  return
else  				# Do the minimization ################
  [x,fval,out] = minimize (fun, args, opml);
  
  if isfield (opm, "maxev")
    flag = out(1) < getfield(opm,"maxev");
  else
    flag = 1;
  end
  
  if nargout > 4
    [dummy,df,d2f] = feval (fun, x, args{2:end});
  elseif nargout > 3
    [dummy,df] = feval (fun, x, args{2:end});
  end
end
