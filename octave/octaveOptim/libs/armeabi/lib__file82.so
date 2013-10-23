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

## ok = test_minimize           - Test that minimize works

ok = 1;				# Remains set if all ok. Set to 0 otherwise
cnt = 0;			# Test counter
page_screen_output (0);
page_output_immediately (1);

if ! exist ("verbose"), verbose = 0; end

N = 2;

x0 = randn(N,1) ;
y0 = randn(N,1) ;

## Return value
function v = ff(x,y,t)
  A = [1 -1;1 1]; M = A'*diag([100,1])*A;
  v = (x(1:2) - y(1:2))'*M*(x(1:2)-y(1:2)) + 1;
endfunction

## Return differential
function dv = dff(x,y,t)
  if nargin < 3, t = 1; end
  if t == 1, N = length (x); else N = length (y); end
  A = [1 -1;1 1]; M = A'*diag([100,1])*A;
  dv = 2*(x(1:2)-y(1:2))'*M;
  if N>2, dv = [dv, zeros(1,N-2)]; end
  if t == 2, dv = -dv; end
endfunction

## Return value, diff and 2nd diff
function [v,dv,d2v] = d2ff(x,y,t)
  if nargin < 3, t = 1; end
  if t == 1, N = length (x); else N = length (y); end
  A = [1 -1;1 1]; M = A'*diag([100,1])*A;
  v = (x(1:2) - y(1:2))'*M*(x(1:2)-y(1:2)) + 1;
  dv = 2*(x(1:2)-y(1:2))'*M;
  d2v = zeros (N); d2v(1:2,1:2) = 2*M;
  if N>2, dv = [dv, zeros(1,N-2)]; end
  if t == 2, dv = -dv; end
endfunction

## Return value, diff and inv of 2nd diff
function [v,dv,d2v] = d2iff(x,y,t)
  if nargin < 3, t = 1; end
  if t == 1, N = length (x); else N = length (y); end
  A = [1 -1;1 1]; M = A'*diag([100,1])*A;
  v = (x(1:2) - y(1:2))'*M*(x(1:2)-y(1:2)) + 1;
  dv = 2*(x(1:2)-y(1:2))'*M;
  d2v = zeros (N); d2v(1:2,1:2) = inv (2*M);
  if N>2, dv = [dv, zeros(1,N-2)]; end
  if t == 2, dv = -dv; end
endfunction

## PRint Now
function prn (varargin), printf (varargin{:}); fflush (stdout); end


if verbose
  prn ("\n   Testing that minimize() works as it should\n\n");
  prn ("  Nparams = N = %i\n",N);
  fflush (stdout);
end

## Plain run, just to make sure ######################################
## Minimum wrt 'x' is y0
[xlev,vlev,nlev] = minimize ("ff",{x0,y0,1});

cnt++;
if max (abs (xlev-y0)) > 100*sqrt (eps)
  if verbose
    prn ("Error is too big : %8.3g\n", max (abs (xlev-y0)));
  end
  ok = 0;
elseif verbose,  prn ("ok %i\n",cnt);
end

## See what 'backend' gives in that last case ########################
[method,ctl] = minimize ("ff",{x0,y0,1},"order",0,"backend");

cnt++;
if ! ischar (method) || ! strcmp (method,"nelder_mead_min")
  if verbose
    if ischar (method)
      prn ("Wrong method '%s' != 'nelder_mead_min' was chosen\n", method);
    else
      prn ("minimize pretends to use a method that isn't a string\n");
    end
    return
  end
  ok = 0;
elseif verbose,  prn ("ok %i\n",cnt);
end

[xle2,vle2,nle2] = feval (method, "ff", {x0,y0,1}, ctl);
cnt++;
				# nelder_mead_min is not very repeatable
				# because of restarts from random positions
if max (abs (xlev-xle2)) > 100*sqrt (eps)
  if verbose
    prn ("Error is too big : %8.3g\n", max (abs (xlev-xle2)));
  end
  ok = 0;
elseif verbose,  prn ("ok %i\n",cnt);
end


## Run, w/ differential, just to make sure ###########################
## Minimum wrt 'x' is y0

# [xlev,vlev,nlev] = minimize ("ff",{x0,y0,1},"df","dff");

# cnt++;
# if max (abs (xlev-y0)) > 100*sqrt (eps)
#   if verbose
#     prn ("Error is too big : %8.3g\n", max (abs (xlev-y0)));
#   end
#   ok = 0;
# elseif verbose,  prn ("ok %i\n",cnt);
# en

## Run, w/ differential returned by function ('jac' option) ##########
## Minimum wrt 'x' is y0
# [xlev,vlev,nlev] = minimize ("d2ff",{x0,y0,1},"jac");

# cnt++;
# if max (abs (xlev-y0)) > 100*sqrt (eps)
#   if verbose
#     prn ("Error is too big : %8.3g\n", max (abs (xlev-y0)));
#   end
#   ok = 0;
# elseif verbose,  prn ("ok %i\n",cnt);
# end

## Run, w/ 2nd differential, just to make sure #######################
## Minimum wrt 'x' is y0
[xlev,vlev,nlev] = minimize ("ff",{x0,y0,1},"d2f","d2ff");

cnt++;
if max (abs (xlev-y0)) > 100*sqrt (eps)
  if verbose
    prn ("Error is too big : %8.3g\n", max (abs (xlev-y0)));
  end
  ok = 0;
elseif verbose,  prn ("ok %i\n",cnt);
end

## Use the 'hess' option, when f can return 2nd differential #########
## Minimum wrt 'x' is y0
[xlev,vlev,nlev] = minimize ("d2ff", {x0,y0,1},"hess");

cnt++;
if max (abs (xlev-y0)) > 100*sqrt (eps)
  if verbose
    prn ("Error is too big : %8.3g\n", max (abs (xlev-y0)));
  end
  ok = 0;
elseif verbose,  prn ("ok %i\n",cnt);
end

## Run, w/ inverse of 2nd differential, just to make sure ############
## Minimum wrt 'x' is y0
[xlev,vlev,nlev] = minimize ("ff", {x0,y0,1},"d2i","d2iff");

cnt++;
if max (abs (xlev-y0)) > 100*sqrt (eps)
  if verbose
    prn ("Error is too big : %8.3g\n", max (abs (xlev-y0)));
  end
  ok = 0;
elseif verbose,  prn ("ok %i\n",cnt);
end

## Use the 'ihess' option, when f can return pinv of 2nd differential 
## Minimum wrt 'x' is y0
[xlev,vlev,nlev] = minimize ("d2iff", {x0,y0,1},"ihess");

cnt++;
if max (abs (xlev-y0)) > 100*sqrt (eps)
  if verbose
    prn ("Error is too big : %8.3g\n", max (abs (xlev-y0)));
  end
  ok = 0;
elseif verbose,  prn ("ok %i\n",cnt);
end

## Run, w/ numerical differential ####################################
## Minimum wrt 'x' is y0
[xlev,vlev,nlev] = minimize ("ff",{x0,y0,1},"ndiff");

cnt++;
if max (abs (xlev-y0)) > 100*sqrt (eps)
  if verbose
    prn ("Error is too big : %8.3g\n", max (abs (xlev-y0)));
  end
  ok = 0;
elseif verbose,  prn ("ok %i\n",cnt);
end

## Run, w/ numerical differential, specified by "order" ##############
## Minimum wrt 'x' is y0
[xlev,vlev,nlev] = minimize ("ff",{x0,y0,1},"order",1);

cnt++;
if max (abs (xlev-y0)) > 100*sqrt (eps)
  if verbose
    prn ("Error is too big : %8.3g\n", max (abs (xlev-y0)));
  end
  ok = 0;
elseif verbose,  prn ("ok %i\n",cnt);
end

# ## See what 'backend' gives in that last case ########################
# [method,ctl] = minimize ("ff",{x0,y0,1},"order",1,"backend");

# cnt++;
# if ! strcmp (method,"bfgsmin")
#   if verbose
#     prn ("Wrong method '%s' != 'bfgsmin' was chosen\n", method);
#   end
#   ok = 0;
# elseif verbose,  prn ("ok %i\n",cnt);
# end

## [xle2,vle2,nle2] = feval (method, "ff",{x0,y0,1}, ctl);
[xle2,vle2,nle2] = minimize ("ff",{x0,y0,1},"order",1);
cnt++;
if max (abs (xlev-xle2)) > 100*eps
  if verbose
    prn ("Error is too big : %8.3g\n", max (abs (xlev-y0)));
  end
  ok = 0;
elseif verbose,  prn ("ok %i\n",cnt);
end


if verbose && ok
  prn ( "All tests ok\n");
end

