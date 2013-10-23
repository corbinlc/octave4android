## Copyright (C) 2007 R.G.H. Eschauzier <reschauzier@yahoo.com>
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

## Adapted by Carnë Draug on 2011 <carandraug+dev@gmail.com>

## This function is necessary for impinvar and invimpinvar of the signal package

## Find {-zd/dz}^n*H1(z). I.e., first differentiate, then multiply by -z, then differentiate, etc.
## The result is (ts^(n+1))*(b(1)*p/(z-p)^1 + b(2)*p^2/(z-p)^2 + b(n+1)*p^(n+1)/(z-p)^(n+1)).
## Works for n>0.
function b = h1_z_deriv(n, p, ts)

  %% Build the vector d that holds coefficients for all the derivatives of H1(z)
  %% The results reads d(n)*z^(1)*(d/dz)^(1)*H1(z) + d(n-1)*z^(2)*(d/dz)^(2)*H1(z) +...+ d(1)*z^(n)*(d/dz)^(n)*H1(z)
  d = (-1)^n; % Vector with the derivatives of H1(z)
  for i= (1:n-1)
    d  = [d 0];                           % Shift result right (multiply by -z)
    d += prepad(polyder(d), i+1, 0, 2); % Add the derivative
  endfor

  %% Build output vector
  b = zeros (1, n + 1);
  for i = (1:n)
    b += d(i) * prepad(h1_deriv(n-i+1), n+1, 0, 2);
  endfor

  b *= ts^(n+1)/factorial(n);

  %% Multiply coefficients with p^i, where i is the index of the coeff.
  b.*=p.^(n+1:-1:1);

endfunction

## Find (z^n)*(d/dz)^n*H1(z), where H1(z)=ts*z/(z-p), ts=sampling period,
## p=exp(sm*ts) and sm is the s-domain pole with multiplicity n+1.
## The result is (ts^(n+1))*(b(1)*p/(z-p)^1 + b(2)*p^2/(z-p)^2 + b(n+1)*p^(n+1)/(z-p)^(n+1)),
## where b(i) is the binomial coefficient bincoeff(n,i) times n!. Works for n>0.
function b = h1_deriv(n)
  b  = factorial(n)*bincoeff(n,0:n); % Binomial coefficients: [1], [1 1], [1 2 1], [1 3 3 1], etc.
  b *= (-1)^n;
endfunction
