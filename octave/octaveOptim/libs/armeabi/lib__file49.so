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

## opt = optimset_compat (...)         - manipulate m*tlab-style options structure
## 
## This function returns a m*tlab-style options structure that can be used
## with the fminunc() function. 'optimset_compat' has been deprecated in
## favor of 'optimset', which is now part of core Octave. This function
## will possibly be removed from future versions of the 'optim' package.
##
## INPUT : Input consist in one or more structs followed by option-value
## pairs. The option that can be passed are those of m*tlab's 'optimset'.
## Whether fminunc() accepts them is another question (see fminunc()).
## 
## Two extra options are supported which indicate how to use directly octave
## optimization tools (such as minimize() and other backends):
##
## "MinEquiv", [on|off] : Tell 'fminunc()' not to minimize 'fun', but
##                        instead return the option passed to minimize().
##
## "Backend", [on|off] : Tell 'fminunc()' not to minimize 'fun', but
##                       instead return the [backend, opt], the name of the
##                       backend optimization function that is used and the
##                       optional arguments that will be passed to it. See
##                       the 'backend' option of minimize().

function opt = optimset_compat (varargin)

## Diagnostics  , ["on"|{"off"}] : 
## DiffMaxChange, [scalar>0]     : N/A (I don't know what it does)
## DiffMinChange, [scalar>0]     : N/A (I don't know what it does)
## Display      , ["off","iter","notify","final"] 
##                               : N/A

  persistent warned = false;
  if (! warned)
    warned = true;
    warning ("Octave:deprecated-function",
             "`optimset_compat' has been deprecated, and will be removed in the future. Use `optimset' from Octave core instead.");
  endif

args = varargin;

opt = struct ();

				# Integrate all leading structs

while length (args) && isstruct (o = args{1})

  args = args(2:length(args)); 	# Remove 1st element of args
				# Add key/value pairs
  for [v,k] = o, opt = setfield (opt,k,v); end    
end

## All the option
op1 = [" DerivativeCheck Diagnostics DiffMaxChange DiffMinChange",\
       " Display GoalsExactAchieve GradConstr GradObj Hessian HessMult",\
       " HessPattern HessUpdate Jacobian JacobMult JacobPattern",\
       " LargeScale LevenbergMarquardt LineSearchType MaxFunEvals MaxIter",\
       " MaxPCGIter MeritFunction MinAbsMax PrecondBandWidth TolCon",\
       " TolFun TolPCG TolX TypicalX ",\
       " MinEquiv Backend "];

opt = read_options (args, "op1",op1, "default",opt,"prefix",1,"nocase",1);
