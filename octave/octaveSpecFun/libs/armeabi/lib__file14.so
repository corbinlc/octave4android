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
%% @deftypefn {Function File} {@var{alpha} =} multinom_exp (@var{m}, @var{n})
%% @deftypefnx {Function File} {@var{alpha} =} multinom_exp (@var{m}, @var{n},@var{sort})
%% Returns the exponents of the terms in the multinomial expansion
%% @tex
%% $$
%% (x_1 + x_2 + ... + x_m).^n
%% $$
%% @end tex
%% @ifnottex
%%
%% @example
%% (x1 + x2 + ... + xm).^@var{n}
%% @end example
%%
%% @end ifnottex
%%
%% For example, for m=2, n=3 the expansion has the terms
%% @tex
%% $$
%% x1^3, x2^3, x1^2*x2, x1*x2^2
%% $$
%% @end tex
%% @ifnottex
%%
%% @example
%% x1^3, x2^3, x1^2*x2, x1*x2^2
%% @end example
%%
%% @end ifnottex
%%
%% then @code{alpha = [3 0; 2 1; 1 2; 0 3]};
%%
%% The optional argument @var{sort} is passed to function @code{sort} to
%% sort the exponents by the maximum degree.
%% The example above calling @code{ multinom(m,n,"ascend")} produces
%%
%% @code{alpha = [2 1; 1 2; 3 0; 0 3]};
%%
%% calling @code{ multinom(m,n,"descend")} produces
%%
%% @code{alpha = [3 0; 0 3; 2 1; 1 2]};
%%
%% @seealso{multinom, multinom_coeff, sort}
%% @end deftypefn

function alpha = multinom_exp(m, n, sortmethod)

     %% This is standard stars and bars.
     numsymbols = m+n-1;
     stars = nchoosek (1:numsymbols, n);

     %% Star labels minus their consecutive position becomes their index
     %% position!
     idx  = bsxfun (@minus, stars, [0:n-1]);

     %% Manipulate indices into the proper shape for accumarray.
     nr   = size (idx, 1);
     a    = repmat ([1:nr], n, 1);
     b    = idx';
     idx  = [a(:), b(:)];

     alpha = accumarray (idx, 1);

     if nargin > 2
        [~, idx] = sort (max (alpha, [], 2), 1, sortmethod);
        alpha    = alpha(idx, :);
     end
end

%!demo
%! m=2;
%! n=3;
%! alpha = multinom_exp(m,n)
%! alpha_asc = multinom_exp(m,n,'ascend')
%! alpha_dec = multinom_exp(m,n,'descend')
