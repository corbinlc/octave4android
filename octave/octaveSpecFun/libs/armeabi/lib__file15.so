%% Copyright (c) 2011 Juan Pablo Carbajal <carbajal@ifi.uzh.ch>
%%
%%    This program is free software: you can redistribute it and/or modify
%%    it under the terms of the GNU General Public License as published by
%%    the Free Software Foundation, either version 3 of the License, or
%%    any later version.
%%
%%    This program is distributed in the hope that it will be useful,
%%    but WITHOUT ANY WARRANTY; without even the implied warranty of
%%    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
%%    GNU General Public License for more details.
%%
%%    You should have received a copy of the GNU General Public License
%%    along with this program. If not, see <http://www.gnu.org/licenses/>.

%% -*- texinfo -*-
%% @deftypefn {Function File} {[@var{y} @var{alpha}] =} multinom (@var{x}, @var{n})
%% @deftypefnx {Function File} {[@var{y} @var{alpha}] =} multinom (@var{x}, @var{n},@var{sort})
%%
%% Returns the terms (monomials) of the multinomial expansion of degree n.
%% @tex
%% $$
%% (x_1 + x_2 + ... + x_m)^N
%% $$
%% @end tex
%% @ifnottex
%%
%% @example
%% (x1 + x2 + ... + xm)^@var{n}
%% @end example
%%
%% @end ifnottex
%%
%% @var{x} is a nT-by-m matrix where each column represents a different variable, the
%% output @var{y} has the same format.
%% The order of the terms is inherited from multinom_exp and can be controlled
%% through the optional argument @var{sort} and is passed to the function @code{sort}.
%% The exponents are returned in @var{alpha}.
%%
%% @seealso{multinom_exp, multinom_coeff, sort}
%% @end deftypefn

function [y, alpha] = multinom(x,n,sortmethod)

    [nT, m]  = size(x);
    if nargin > 2
        alpha = multinom_exp(m,n,sortmethod);
    else
        alpha = multinom_exp(m,n);
    end
    na      = size(alpha,1);

    y = prod(repmat(x,na,1).^kron(alpha,ones(nT,1)),2);
    y = reshape(y,nT,na);

end

%!demo
%! n = 3;
%! t = linspace(-1,1,10).';
%! x = [t-1/2, t];
%! y = multinom(x,n,'descend');
%! y_shouldbe = [x(:,1).^3 x(:,2).^3 x(:,1).^2.*x(:,2) x(:,1).*x(:,2).^2 ];
%! plot(t,y_shouldbe); hold on; plot(t,y,'s'); hold off;
%! legend('x_1^3','x_2^3','x_1^2x_2','x_1x_2^2','location','southoutside',...
%! 'orientation','horizontal');
%! title('Terms of the expansion of (x_1 + x_2)^3 (colors should match)');
