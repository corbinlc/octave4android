## Copyright (C) 2005, 2006 William Poetra Yoga Hadisoeseno
## Copyright (C) 2011 Nir Krakauer
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

## -*- texinfo -*-
## @deftypefn {Function File} {[@var{b}, @var{bint}, @var{r}, @var{rint}, @var{stats}] =} regress (@var{y}, @var{X}, [@var{alpha}])
## Multiple Linear Regression using Least Squares Fit of @var{y} on @var{X}
## with the model @code{y = X * beta + e}.
##
## Here,
##
## @itemize
## @item
## @code{y} is a column vector of observed values
## @item
## @code{X} is a matrix of regressors, with the first column filled with
## the constant value 1
## @item
## @code{beta} is a column vector of regression parameters
## @item
## @code{e} is a column vector of random errors
## @end itemize
##
## Arguments are
##
## @itemize
## @item
## @var{y} is the @code{y} in the model
## @item
## @var{X} is the @code{X} in the model
## @item
## @var{alpha} is the significance level used to calculate the confidence
## intervals @var{bint} and @var{rint} (see `Return values' below). If not
## specified, ALPHA defaults to 0.05
## @end itemize
##
## Return values are
##
## @itemize
## @item
## @var{b} is the @code{beta} in the model
## @item
## @var{bint} is the confidence interval for @var{b}
## @item
## @var{r} is a column vector of residuals
## @item
## @var{rint} is the confidence interval for @var{r}
## @item
## @var{stats} is a row vector containing:
##
##   @itemize
##   @item The R^2 statistic
##   @item The F statistic
##   @item The p value for the full model
##   @item The estimated error variance
##   @end itemize
## @end itemize
##
## @var{r} and @var{rint} can be passed to @code{rcoplot} to visualize
## the residual intervals and identify outliers.
##
## NaN values in @var{y} and @var{X} are removed before calculation begins.
##
## @end deftypefn

## References:
## - Matlab 7.0 documentation (pdf)
## - 《大学数学实验》 姜启源 等 (textbook)
## - http://www.netnam.vn/unescocourse/statistics/12_5.htm
## - wsolve.m in octave-forge
## - http://www.stanford.edu/class/ee263/ls_ln_matlab.pdf

function [b, bint, r, rint, stats] = regress (y, X, alpha)

  if (nargin < 2 || nargin > 3)
    print_usage;
  endif

  if (! ismatrix (y))
    error ("regress: y must be a numeric matrix");
  endif
  if (! ismatrix (X))
    error ("regress: X must be a numeric matrix");
  endif

  if (columns (y) != 1)
    error ("regress: y must be a column vector");
  endif

  if (rows (y) != rows (X))
    error ("regress: y and X must contain the same number of rows");
  endif

  if (nargin < 3)
    alpha = 0.05;
  elseif (! isscalar (alpha))
    error ("regress: alpha must be a scalar value")
  endif

  notnans = ! logical (sum (isnan ([y X]), 2));
  y = y(notnans);
  X = X(notnans,:);

  [Xq Xr] = qr (X, 0);
  pinv_X = Xr \ Xq';

  b = pinv_X * y;

  if (nargout > 1)

    n = rows (X);
    p = columns (X);
    dof = n - p;
    t_alpha_2 = tinv (alpha / 2, dof);

    r = y - X * b; # added -- Nir
    SSE = sum (r .^ 2);
    v = SSE / dof;

    # c = diag(inv (X' * X)) using (economy) QR decomposition
    # which means that we only have to use Xr
    c = diag (inv (Xr' * Xr));

    db = t_alpha_2 * sqrt (v * c);

    bint = [b + db, b - db];

  endif

  if (nargout > 3)

    dof1 = n - p - 1;
    h = sum(X.*pinv_X', 2); #added -- Nir (same as diag(X*pinv_X), without doing the matrix multiply)

    # From Matlab's documentation on Multiple Linear Regression,
    #   sigmaihat2 = norm (r) ^ 2 / dof1 - r .^ 2 / (dof1 * (1 - h));
    #   dr = -tinv (1 - alpha / 2, dof) * sqrt (sigmaihat2 .* (1 - h));
    # Substitute
    #   norm (r) ^ 2 == sum (r .^ 2) == SSE
    #   -tinv (1 - alpha / 2, dof) == tinv (alpha / 2, dof) == t_alpha_2
    # We get
    #   sigmaihat2 = (SSE - r .^ 2 / (1 - h)) / dof1;
    #   dr = t_alpha_2 * sqrt (sigmaihat2 .* (1 - h));
    # Combine, we get
    #   dr = t_alpha_2 * sqrt ((SSE * (1 - h) - (r .^ 2)) / dof1);

    dr = t_alpha_2 * sqrt ((SSE * (1 - h) - (r .^ 2)) / dof1);

    rint = [r + dr, r - dr];

  endif

  if (nargout > 4)

    R2 = 1 - SSE / sum ((y - mean (y)) .^ 2);
#    F = (R2 / (p - 1)) / ((1 - R2) / dof);
    F = dof / (p - 1) / (1 / R2 - 1);
    pval = 1 - fcdf (F, p - 1, dof);

    stats = [R2 F pval v];

  endif

endfunction


%!test
%! % Longley data from the NIST Statistical Reference Dataset
%! Z = [  60323    83.0   234289   2356     1590    107608  1947
%!        61122    88.5   259426   2325     1456    108632  1948
%!        60171    88.2   258054   3682     1616    109773  1949
%!        61187    89.5   284599   3351     1650    110929  1950
%!        63221    96.2   328975   2099     3099    112075  1951
%!        63639    98.1   346999   1932     3594    113270  1952
%!        64989    99.0   365385   1870     3547    115094  1953
%!        63761   100.0   363112   3578     3350    116219  1954
%!        66019   101.2   397469   2904     3048    117388  1955
%!        67857   104.6   419180   2822     2857    118734  1956
%!        68169   108.4   442769   2936     2798    120445  1957
%!        66513   110.8   444546   4681     2637    121950  1958
%!        68655   112.6   482704   3813     2552    123366  1959
%!        69564   114.2   502601   3931     2514    125368  1960
%!        69331   115.7   518173   4806     2572    127852  1961
%!        70551   116.9   554894   4007     2827    130081  1962 ];
%! % Results certified by NIST using 500 digit arithmetic
%! % b and standard error in b
%! V = [  -3482258.63459582         890420.383607373
%!         15.0618722713733         84.9149257747669
%!        -0.358191792925910E-01    0.334910077722432E-01
%!        -2.02022980381683         0.488399681651699
%!        -1.03322686717359         0.214274163161675
%!        -0.511041056535807E-01    0.226073200069370
%!         1829.15146461355         455.478499142212 ];
%! Rsq = 0.995479004577296;
%! F = 330.285339234588;
%! y = Z(:,1); X = [ones(rows(Z),1), Z(:,2:end)];
%! alpha = 0.05;
%! [b, bint, r, rint, stats] = regress (y, X, alpha);
%! assert(b,V(:,1),3e-6);
%! assert(stats(1),Rsq,1e-12);
%! assert(stats(2),F,3e-8);
%! assert(((bint(:,1)-bint(:,2))/2)/tinv(alpha/2,9),V(:,2),-1.e-5);
