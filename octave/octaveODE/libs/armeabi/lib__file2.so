## Copyright (C) 2008-2012 Carlo de Falco
## 
## This program is free software; you can redistribute it and/or modify
## it under the terms of the GNU General Public License as published by
## the Free Software Foundation; either version 2 of the License, or
## (at your option) any later version.
## 
## This program is distributed in the hope that it will be useful,
## but WITHOUT ANY WARRANTY; without even the implied warranty of
## MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
## GNU General Public License for more details.
## 
## You should have received a copy of the GNU General Public License
## along with this program; if not, see <http://www.gnu.org/licenses/>.

## -*- texinfo -*-
## @deftypefn {Function File} {@var{A}} = bvp4c (@var{odefun}, @var{bcfun}, @var{solinit})
##
## Solves the first order system of non-linear differential equations defined by
## @var{odefun} with the boundary conditions defined in @var{bcfun}.
##
## The structure @var{solinit} defines the grid on which to compute the
## solution (@var{solinit.x}), and an initial guess for the solution (@var{solinit.y}).
## The output @var{sol} is also a structure with the following fields:
## @itemize
## @item  @var{sol.x} list of points where the solution is evaluated
## @item  @var{sol.y} solution evaluated at the points @var{sol.x}
## @item  @var{sol.yp} derivative of the solution evaluated at the
## points @var{sol.x}
## @item  @var{sol.solver} = "bvp4c" for compatibility 
## @end itemize
## @seealso{odpkg}
## @end deftypefn

## Author: Carlo de Falco <carlo@guglielmo.local>
## Created: 2008-09-05


function sol = bvp4c(odefun,bcfun,solinit,options)

  if (isfield(solinit,"x"))
    t = solinit.x;
  else
    error("bvp4c: missing initial mesh solinit.x");
  end

  if (isfield(solinit,"y"))
    u_0 = solinit.y;
  else
    error("bvp4c: missing initial guess");
  end

  if (isfield(solinit,"parameters"))
    error("bvp4c: solving for unknown parameters is not yet supported");
  end

  RelTol = 1e-3;
  AbsTol = 1e-6;
  if ( nargin > 3 )
    if (isfield(options,"RelTol"))
      RelTol = options.RelTol;
    endif
    if (isfield(options,"RelTol"))
      AbsTol = options.AbsTol;
    endif
  endif
  
  Nvar = rows(u_0);
  Nint = length(t)-1;
  s    = 3;
  h    = diff(t);

  AbsErr  = inf;
  RelErr  = inf;
  MaxIt   = 10;

  for iter = 1:MaxIt

    x       = [ u_0(:); zeros(Nvar*Nint*s,1) ];
    x       = __bvp4c_solve__ (t, x, h, odefun, bcfun, Nvar, Nint, s);
    u       = reshape(x(1:Nvar*(Nint+1)),Nvar,Nint+1);

    for kk=1:Nint+1
      du(:,kk) = odefun(t(kk), u(:,kk));
    end

    tm = (t(1:end-1)+t(2:end))/2;
    um = [];
    for nn=1:Nvar
      um(nn,:) = interp1(t,u(nn,:),tm);
    endfor

    f_est = [];
    for kk=1:Nint
      f_est(:,kk) = odefun(tm(kk), um(:,kk));
    end

    du_est = [];
    for nn=1:Nvar
      du_est(nn,:) = diff(u(nn,:))./h;
    end

    err    = max(abs(f_est-du_est)); semilogy(tm,err), pause(.1)
    AbsErr = max(err)
    RelErr = AbsErr/norm(du,inf)

    if    ( (AbsErr >= AbsTol) && (RelErr >= RelTol) )
      ref_int = find( (err >= AbsTol) & (err./max(max(abs(du))) >= RelTol) );
      
      t_add = tm(ref_int);
      t_old = t;
      
      t     = sort([t, t_add]);
      h     = diff(t);
      
      u_0 = [];
      for nn=1:Nvar
	u_0(nn,:) = interp1(t_old, u(nn,:), t);
      end
      Nvar = rows(u_0);
      Nint = length(t)-1
    else
      break
    end

  endfor
  
  ## K    = reshape(x([1:Nvar*Nint*s]+Nvar*(Nint+1)),Nvar,Nint,s);
  ## K1 = reshape(K(:,:,1), Nvar, Nint);
  ## K2 = reshape(K(:,:,2), Nvar, Nint);
  ## K3 = reshape(K(:,:,3), Nvar, Nint);

 

  sol.x = t;
  sol.y = u;
  sol.yp= du;
  sol.parameters = [];
  sol.solver = 'bvp4c';
  
endfunction

function diff_K = __bvp4c_fun_K__ (t, u, Kin, f, h, s, Nint, Nvar)

  %% coefficients
  persistent C = [0      1/2    1 ];
  
  persistent A = [0      0      0;
                  5/24   1/3   -1/24;
                  1/6    2/3    1/6];	

  for jj = 1:s
    for kk = 1:Nint
      Y = repmat(u(:,kk),1,s) + ...
	  (reshape(Kin(:,kk,:),Nvar,s) * A.') * h(kk);
      diff_K(:,kk,jj) = Kin(:,kk,jj) - f (t(kk)+C(jj)*h(kk), Y);
    endfor
  endfor

endfunction

 
function diff_u = __bvp4c_fun_u__ (t, u, K, h, s, Nint, Nvar)
  
  %% coefficients
  persistent B= [1/6 2/3 1/6 ];

  Y = zeros(Nvar, Nint);
  for jj = 1:s
    Y +=  B(jj) * K(:,:,jj);
  endfor
  diff_u = u(:,2:end) - u(:,1:end-1) - repmat(h,Nvar,1) .* Y;

endfunction

function x = __bvp4c_solve__ (t, x, h, odefun, bcfun, Nvar, Nint, s)
  fun = @( x ) ( [__bvp4c_fun_u__(t, 
				  reshape(x(1:Nvar*(Nint+1)),Nvar,(Nint+1)), 
				  reshape(x([1:Nvar*Nint*s]+Nvar*(Nint+1)),Nvar,Nint,s),
				  h,
				  s,
				  Nint,
				  Nvar)(:) ;
		  __bvp4c_fun_K__(t, 
				  reshape(x(1:Nvar*(Nint+1)),Nvar,(Nint+1)), 
				  reshape(x([1:Nvar*Nint*s]+Nvar*(Nint+1)),Nvar,Nint,s),
				  odefun,
				  h,
				  s,
				  Nint,
				  Nvar)(:);
		  bcfun(reshape(x(1:Nvar*(Nint+1)),Nvar,Nint+1)(:,1),
			reshape(x(1:Nvar*(Nint+1)),Nvar,Nint+1)(:,end));
		  ] );
  
  x    = fsolve ( fun, x );
endfunction



%!demo
%! a            = 0; 
%! b            = 4;
%! Nint         = 3;
%! Nvar         = 2;
%! s            = 3;
%! t            = linspace(a,b,Nint+1);
%! h            = diff(t);
%! u_1          = ones(1, Nint+1); 
%! u_2          = 0*u_1;
%! u_0          = [u_1 ; u_2];
%! f            = @(t,u) [ u(2); -abs(u(1)) ];
%! g            = @(ya,yb) [ya(1); yb(1)+2];
%! solinit.x = t; solinit.y=u_0;
%! sol = bvp4c(f,g,solinit);
%! plot (sol.x,sol.y,'x-')

%!demo
%! a            = 0; 
%! b            = 4;
%! Nint         = 2;
%! Nvar         = 2;
%! s            = 3;
%! t            = linspace(a,b,Nint+1);
%! h            = diff(t);
%! u_1          = -ones(1, Nint+1); 
%! u_2          = 0*u_1;
%! u_0          = [u_1 ; u_2];
%! f            = @(t,u) [ u(2); -abs(u(1)) ];
%! g            = @(ya,yb) [ya(1); yb(1)+2];
%! solinit.x = t; solinit.y=u_0;
%! sol = bvp4c(f,g,solinit);
%! plot (sol.x,sol.y,'x-')
