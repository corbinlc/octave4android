%% Copyright (C) 2010, 2011 Olaf Till <i7tiol@t-online.de>
%%
%% This program is free software; you can redistribute it and/or modify it under
%% the terms of the GNU General Public License as published by the Free Software
%% Foundation; either version 3 of the License, or (at your option) any later
%% version.
%%
%% This program is distributed in the hope that it will be useful, but WITHOUT
%% ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
%% FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
%% details.
%%
%% You should have received a copy of the GNU General Public License along with
%% this program; if not, see <http://www.gnu.org/licenses/>.

%% m = gjp (m, k[, l])
%%
%% m: matrix; k, l: row- and column-index of pivot, l defaults to k.
%%
%% Gauss-Jordon pivot as defined in Bard, Y.: Nonlinear Parameter
%% Estimation, p. 296, Academic Press, New York and London 1974. In
%% the pivot column, this seems not quite the same as the usual
%% Gauss-Jordan(-Clasen) pivot. Bard gives Beaton, A. E., 'The use of
%% special matrix operators in statistical calculus' Research Bulletin
%% RB-64-51 (1964), Educational Testing Service, Princeton, New Jersey
%% as a reference, but this article is not easily accessible. Another
%% reference, whose definition of gjp differs from Bards by some
%% signs, is Clarke, R. B., 'Algorithm AS 178: The Gauss-Jordan sweep
%% operator with detection of collinearity', Journal of the Royal
%% Statistical Society, Series C (Applied Statistics) (1982), 31(2),
%% 166--168.

function m = gjp (m, k, l)

  if (nargin < 3)
    l = k;
  end

  p = m(k, l);

  if (p == 0)
    error ('pivot is zero');
  end

  %% This is a case where I really hate to remain Matlab compatible,
  %% giving so many indices twice.
  m(k, [1:l-1, l+1:end]) = m(k, [1:l-1, l+1:end]) / p; % pivot row
  m([1:k-1, k+1:end], [1:l-1, l+1:end]) = ... % except pivot row and col
      m([1:k-1, k+1:end], [1:l-1, l+1:end]) - ...
      m([1:k-1, k+1:end], l) * m(k, [1:l-1, l+1:end]);
  m([1:k-1, k+1:end], l) = - m([1:k-1, k+1:end], l) / p; % pivot column
  m(k, l) = 1 / p;
