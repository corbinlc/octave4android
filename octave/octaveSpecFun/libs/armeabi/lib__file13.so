%% Copyright (c) 2011 Jordi Gutiérrez Hermoso <jordigh@octave.org>
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
%% @deftypefn {Function File} {[@var{c} @var{alpha}] =} multinom_coeff (@var{m}, @var{n})
%% @deftypefnx {Function File} {[@var{c} @var{alpha}] =} multinom_coeff (@var{m}, @var{n},@var{order})
%% Produces the coefficients of the multinomial expansion
%% @tex
%% $$
%% (x1 + x2 + ... + xm).^n
%% $$
%% @end tex
%% @ifnottex
%%
%% @example
%% (x1 + x2 + ... + xm).^n
%% @end example
%%
%% @end ifnottex
%%
%% For example, for m=3, n=3 the expansion is 
%% @tex
%% $$
%% (x1+x2+x3)^3 = 
%%         = x1^3 + x2^3 + x3^3 + 3 x1^2 x2 + 3 x1^2 x3 + 3 x2^2 x1 + 3 x2^2 x3 + 
%%         + 3 x3^2 x1 + 3 x3^2 x2 + 6 x1 x2 x3
%% $$
%% @end tex
%% @ifnottex
%%
%% @example
%% @group
%% (x1+x2+x3)^3 = 
%%         = x1^3 + x2^3 + x3^3 +
%%         +  3 x1^2 x2 + 3 x1^2 x3 + 3 x2^2 x1 + 3 x2^2 x3 + 
%%         + 3 x3^2 x1 + 3 x3^2 x2 + 6 x1 x2 x3
%% @end group
%% @end example
%%
%% @end ifnottex
%%
%% and the coefficients are [6 3 3 3 3 3 3 1 1 1].
%%
%% The order of the coefficients is defined by the optinal argument @var{order}. 
%%  It is passed to the function @code{multion_exp}. See the help of that 
%% function for explanation.
%% The multinomial coefficients are generated using
%% @tex
%% $$
%%  {n \choose k} = \frac{n!}{k(1)!k(2)! \cdots k(\text{end})!}
%% $$
%% @end tex
%% @ifnottex
%%
%% @example
%% @group
%%  /   \
%%  | n |                n!
%%  |   |  = ------------------------
%%  | k |     k(1)!k(2)! @dots{} k(end)!
%%  \   /
%% @end group
%% @end example
%%
%% @end ifnottex
%%
%% @seealso{multinom,multinom_exp}
%%
%% @end deftypefn

function [c, alpha] = multinom_coeff(m,n,sortmethod)

    if nargin > 2
        alpha = multinom_exp(m,n,sortmethod);
    else
        alpha = multinom_exp(m,n);
    end

    %% Multinomial coefficients
    %% number of ways of depositing n distinct objects into m distinct bins,
    %% with k1 objects in the first bin, k2 objects in the second bin, and so on
    %% JPi: I guess it can be improved
    %% Simplification thanks to Jordi G. H.
    c = factorial(n)./prod(factorial(alpha),2);

end

%!demo
%! multinom_coeff(3,3)
%! multinom_coeff(3,3,'ascend')
%! multinom_coeff(3,3,'descend')
