## Copyright (C) 2007 Andreas Stahel <Andreas.Stahel@bfh.ch>
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

## general linear regression
##
## [p,y_var,r,p_var]=LinearRegression(F,y)
## [p,y_var,r,p_var]=LinearRegression(F,y,weight)
##
## determine the parameters p_j  (j=1,2,...,m) such that the function
## f(x) = sum_(i=1,...,m) p_j*f_j(x) fits as good as possible to the 
## given values y_i = f(x_i)
##
## parameters
## F  n*m matrix with the values of the basis functions at the support points 
##    in column j give the values of f_j at the points x_i  (i=1,2,...,n)
## y  n column vector of given values
## weight  n column vector of given weights
##
## return values
## p     m vector with the estimated values of the parameters
## y_var estimated variance of the error
## r     weighted norm of residual
## p_var estimated variance of the parameters p_j

function [p,y_var,r,p_var]=LinearRegression(F,y,weight)

if (nargin < 2 || nargin >= 4)
 usage('wrong number of arguments in [p,y_var,r,p_var]=LinearRegression(F,y)');
end

[rF, cF] = size(F);  [ry, cy] =size(y);
if (rF ~= ry || cy > 1)
  error ('LinearRegression: incorrect matrix dimensions');
end

if (nargin==2)  % set uniform weights if not provided
  weight=ones(size(y));
end

%% Fw=diag(weight)*F;
wF=F;
for j=1:cF
  wF(:,j)=weight.*F(:,j);
end

[Q,R]=qr(wF,0);                  % estimate the values of the parameters
p=R\(Q'*(weight.*y));


residual=F*p-y;                  % compute the residual vector
r=norm(weight.*residual);   % and its weighted norm
				 % variance of the weighted y-errors
y_var= sum((residual.^2).*(weight.^4))/(rF-cF);  

if nargout>3    % compute variance of parameters only if needed
%%  M=inv(R)*Q'*diag(weight);
  M=inv(R)*Q';
  for j=1:cF
    M(j,:)=M(j,:).*(weight');
  end
  M=M.*M;                        % square each entry in the matrix M
  p_var=M*(y_var./(weight.^4));  % variance of the parameters
end

