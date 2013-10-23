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

## c = cdiff (func,wrt,N,dfunc,stack,dx) - Code for num. differentiation
##   = "function df = dfunc (var1,..,dvar,..,varN) .. endfunction
## 
## Returns a string of octave code that defines a function 'dfunc' that
## returns the derivative of 'func' with respect to it's 'wrt'th
## argument.
##
## The derivatives are obtained by symmetric finite difference.
##
## dfunc()'s return value is in the same format as that of  ndiff()
##
## func  : string : name of the function to differentiate
##
## wrt   : int    : position, in argument list, of the differentiation
##                  variable.                                Default:1
##
## N     : int    : total number of arguments taken by 'func'. 
##                  If N=inf, dfunc will take variable argument list.
##                                                         Default:wrt
##
## dfunc : string : Name of the octave function that returns the
##                   derivatives.                   Default:['d',func]
##
## stack : string : Indicates whether 'func' accepts vertically
##                  (stack="rstack") or horizontally (stack="cstack")
##                  arguments. Any other string indicates that 'func'
##                  does not allow stacking.                Default:''
##
## dx    : real   : Step used in the symmetric difference scheme.
##                                                  Default:10*sqrt(eps)
##
## See also : ndiff, eval, todisk

function c = cdiff (func,wrt,nargs,dfunc,stack,dx)

if nargin<2,
  wrt = 1 ;
end
if nargin<3,
  nargs = wrt ;
end
if nargin<4 || strcmp(dfunc,""), 
  dfunc = ["d",func] ; 
  if exist(dfunc)>=2,
    printf(["cdiff : Warning : name of derivative not specified\n",\
	    "        and canonic name '%s' is already taken\n"],\
	   dfunc);
    ## keyboard
  end
end
if nargin<5, stack = "" ; end
if nargin<6, dx = 10*sqrt(eps)  ; end

## verbose = 0 ;
## build argstr = "var1,..,dvar,...var_nargs"
if isfinite (nargs)
  argstr = sprintf("var%i,",1:nargs);
else
  argstr = [sprintf("var%i,",1:wrt),"...,"];
end

argstr = strrep(argstr,sprintf("var%i",wrt),"dvar") ;
argstr = argstr(1:length(argstr)-1) ;

if strcmp("cstack",stack) ,	# Horizontal stacking ################
  
  calstr = "reshape (kron(ones(1,2*ps), dvar(:))+[-dx*eye(ps),dx*eye(ps)], sz.*[1,2*ps])";
  calstr = strrep(argstr,"dvar",calstr) ;
  calstr = sprintf("%s(%s)",func,calstr) ;

  calstr = sprintf(strcat("  res = %s;\n",
			  "  pr = prod (size(res)) / (2*ps);\n",
			  "  res = reshape (res,pr,2*ps);\n",
			  "  df = (res(:,ps+1:2*ps)-res(:,1:ps)) / (2*dx);\n"),
		   calstr) ;
    

elseif strcmp("rstack",stack),	# Vertical stacking ##################

  calstr = "kron(ones(2*ps,1),dvar)+dx*[-dv;dv]" ;
  calstr = strrep(argstr,"dvar",calstr) ;
  calstr = sprintf("%s(%s)",func,calstr) ;

  calstr = sprintf(strcat("  dv = kron (eye(sz(2)), eye(sz(1))(:));\n",\
			  "  res = %s;\n",\
			  "  sr = size(res)./[2*ps,1];\n",\
			  "  pr = prod (sr);\n",\
			  "  df = (res(sr(1)*ps+1:2*sr(1)*ps,:)-res(1:sr(1)*ps,:))/(2*dx);\n",\
			  "  scramble = reshape (1:pr,sr(2),sr(1))';\n",\
			  "  df = reshape (df',pr,ps)(scramble(:),:);\n"),\
		   calstr) ;
  ## sayif(verbose,"cdiff : calstr='%s'\n",calstr) ;
else				# No stacking ########################
  calstr = sprintf("%s (%s)",func,argstr) ;
  ## "func(var1,dvar%sdv(:,%d:%d),...,varN),"
  ## calstr = strrep(calstr,"dvar","dvar%sdv(:,(i-1)*sz(2)+1:i*sz(2))")(:)';

  calstr = strrep(calstr,"dvar","dvar%sdv")(:)';

  ## func(..,dvar+dv(:,1:sz(2)),..) - func(..)
  calstr = strcat(calstr,"-",calstr) ; ## strcat(calstr,"-",calstr) ;
  calstr = sprintf(calstr,"+","-") ;
  tmp = calstr ;
  ## sayif(verbose,"cdiff : calstr='%s'\n",calstr) ;
  calstr = sprintf(strcat("  dv = zeros (sz); dv(1) = dx;\n",\
			  "  df0 = %s;\n",\
			  "  sr = size (df0);\n",\
			  "  df = zeros(prod (sr),ps); df(:,1) = df0(:);\n",\
			  "  for i = 2:ps,\n",\
			  "     dv(i) = dx; dv(i-1) = 0;\n",\
			  "     df(:,i) = (%s)(:);\n",\ 
			  "  end;\n",\
			  "  df ./= 2*dx;\n"
			  ),
		   calstr, tmp) ;
		   

  ## sayif(verbose,"cdiff : calstr='%s'\n",calstr) ;

  ## "func(var1,reshape(dvar(1:NV,1),SZ1,SZ2),...,varN)," , 
  ## "func(var1,reshape(dvar(1:NV,2),SZ1,SZ2),...,varN)," , ...
  ## "func(var1,reshape(dvar(1:NV,NP),SZ1,SZ2),...,varN)"
  ## sayif(verbose,"cdiff : calstr='%s'\n",calstr) ;
end
argstr = strrep (argstr, "...", "varargin");
calstr = strrep (calstr, "...", "varargin{:}");

c = sprintf(strcat("function df = %s (%s)\n",\
		   "  ## Numerical differentiation of '%s' wrt to it's %d'th argument\n",\
		   "  ## This function has been written by 'cdiff()'\n",\
		   "  dx = %e;\n",\
		   "  sz = size (dvar);\n",\
		   "  ps = prod (sz);\n",\
		   "%s",\
		   "endfunction\n"),\
	    dfunc,argstr,\
	    func,wrt,\
	    dx,\
	    calstr) ;
	    
