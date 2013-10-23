## Copyright (C) 1999-2001 Paul Kienzle <pkienzle@users.sf.net>
## Copyright (C) 2004 <asbjorn.sabo@broadpark.no>
## Copyright (C) 2008,2010 Peter Lanspeary <peter.lanspeary@.adelaide.edu.au>
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
## @deftypefn {Function File} {[@var{R}, @var{lag}] =} xcorr ( @var{X} )
## @deftypefnx {Function File} {@dots{} =} xcorr ( @var{X}, @var{Y} )
## @deftypefnx {Function File} {@dots{} =} xcorr ( @dots{}, @var{maxlag})
## @deftypefnx {Function File} {@dots{} =} xcorr ( @dots{}, @var{scale})
## Estimates the cross-correlation.
##
## Estimate the cross correlation R_xy(k) of vector arguments @var{X} and @var{Y}
## or, if @var{Y} is omitted, estimate autocorrelation R_xx(k) of vector @var{X},
## for a range of lags k specified by  argument "maxlag".  If @var{X} is a
## matrix, each column of @var{X} is correlated with itself and every other
## column.
##
## The cross-correlation estimate between vectors "x" and "y" (of
## length N) for lag "k" is given by
##
## @iftex
## @tex
## $$   R_{xy}(k) = \sum_{i=1}^{N} x_{i+k} \conj(y_i),
## @end tex
## @end iftex
## @ifnottex
## @example
##            N
## R_xy(k) = sum x_@{i+k@} conj(y_i),
##           i=1
## @end example
## @end ifnottex
##
## where data not provided (for example x(-1), y(N+1)) is zero. Note the
## definition of cross-correlation given above. To compute a
## cross-correlation consistent with the field of statistics, see @command{xcov}.
##
## @strong{ARGUMENTS}
## @table @var
## @item X
## [non-empty; real or complex; vector or matrix] data
##
## @item Y
## [real or complex vector] data
##
## If @var{X} is a matrix (not a vector), @var{Y} must be omitted.
## @var{Y} may be omitted if @var{X} is a vector; in this case xcorr
## estimates the autocorrelation of @var{X}.
##
## @item maxlag
## [integer scalar] maximum correlation lag
## If omitted, the default value is N-1, where N is the
## greater of the lengths of @var{X} and @var{Y} or, if @var{X} is a matrix,
## the number of rows in @var{X}.
##
## @item scale
## [character string] specifies the type of scaling applied
## to the correlation vector (or matrix). is one of:
## @table @samp
## @item none
## return the unscaled correlation, R,
## @item biased
## return the biased average, R/N,
## @item unbiased
## return the unbiassed average, R(k)/(N-|k|),
## @item coeff
## return the correlation coefficient, R/(rms(x).rms(y)),
## where "k" is the lag, and "N" is the length of @var{X}.
## If omitted, the default value is "none".
## If @var{Y} is supplied but does not have the same length as @var{X},
## scale must be "none".
## @end table
## @end table
##
## @strong{RETURNED VARIABLES}
## @table @var
## @item R
## array of correlation estimates
## @item lag
## row vector of correlation lags [-maxlag:maxlag]
## @end table
##
## The array of correlation estimates has one of the following forms:
## (1) Cross-correlation estimate if @var{X} and @var{Y} are vectors.
##
## (2) Autocorrelation estimate if is a vector and @var{Y} is omitted.
##
## (3) If @var{X} is a matrix, R is an matrix containing the cross-correlation
## estimate of each column with every other column. Lag varies with the first
## index so that R has 2*maxlag+1 rows and P^2 columns where P is the number of
## columns in @var{X}.
##
## If Rij(k) is the correlation between columns i and j of @var{X}
##
## @code{R(k+maxlag+1,P*(i-1)+j) == Rij(k)}
##
## for lag k in [-maxlag:maxlag], or
##
## @code{R(:,P*(i-1)+j) == xcorr(X(:,i),X(:,j))}.
##
## @code{reshape(R(k,:),P,P)} is the cross-correlation matrix for @code{X(k,:)}.
##
## @seealso{xcov}
## @end deftypefn

## The cross-correlation estimate is calculated by a "spectral" method
## in which the FFT of the first vector is multiplied element-by-element
## with the FFT of second vector.  The computational effort depends on
## the length N of the vectors and is independent of the number of lags
## requested.  If you only need a few lags, the "direct sum" method may
## be faster:
##
## Ref: Stearns, SD and David, RA (1988). Signal Processing Algorithms.
##      New Jersey: Prentice-Hall.

## unbiased:
##  ( hankel(x(1:k),[x(k:N); zeros(k-1,1)]) * y ) ./ [N:-1:N-k+1](:)
## biased:
##  ( hankel(x(1:k),[x(k:N); zeros(k-1,1)]) * y ) ./ N
##
## If length(x) == length(y) + k, then you can use the simpler
##    ( hankel(x(1:k),x(k:N-k)) * y ) ./ N

function [R, lags] = xcorr (X, Y, maxlag, scale)

  if (nargin < 1 || nargin > 4)
    print_usage;
  endif

  ## assign arguments that are missing from the list
  ## or reassign (right shift) them according to data type
  if nargin==1
    Y=[]; maxlag=[]; scale=[];
  elseif nargin==2
    maxlag=[]; scale=[];
    if ischar(Y), scale=Y; Y=[];
    elseif isscalar(Y), maxlag=Y; Y=[];
    endif
  elseif nargin==3
    scale=[];
    if ischar(maxlag), scale=maxlag; maxlag=[]; endif
    if isscalar(Y), maxlag=Y; Y=[]; endif
  endif

  ## assign defaults to missing arguments
  if isvector(X)
    ## if isempty(Y), Y=X; endif  ## this line disables code for autocorr'n
    N = max(length(X),length(Y));
  else
    N = rows(X);
  endif
  if isempty(maxlag), maxlag=N-1; endif
  if isempty(scale), scale='none'; endif

  ## check argument values
  if isempty(X) || isscalar(X) || ischar(Y) || ! ismatrix(X)
    error("xcorr: X must be a vector or matrix");
  endif
  if isscalar(Y) || ischar(Y) || (!isempty(Y) && !isvector(Y))
    error("xcorr: Y must be a vector");
  endif
  if !isempty(Y) && !isvector(X)
    error("xcorr: X must be a vector if Y is specified");
  endif
  if !isscalar(maxlag) || !isreal(maxlag) || maxlag<0 || fix(maxlag)!=maxlag
    error("xcorr: maxlag must be a single non-negative integer");
  endif
  ##
  ## sanity check on number of requested lags
  ##   Correlations for lags in excess of +/-(N-1)
  ##    (a) are not calculated by the FFT algorithm,
  ##    (b) are all zero; so provide them by padding
  ##        the results (with zeros) before returning.
  if (maxlag > N-1)
    pad_result = maxlag - (N - 1);
    maxlag = N - 1;
    %error("xcorr: maxlag must be less than length(X)");
  else
    pad_result = 0;
  endif
  if isvector(X) && isvector(Y) && length(X) != length(Y) && \
	!strcmp(scale,'none')
    error("xcorr: scale must be 'none' if length(X) != length(Y)")
  endif

  P = columns(X);
  M = 2^nextpow2(N + maxlag);
  if !isvector(X)
    ## For matrix X, correlate each column "i" with all other "j" columns
    R = zeros(2*maxlag+1,P^2);

    ## do FFTs of padded column vectors
    pre = fft (postpad (prepad (X, N+maxlag), M) );
    post = conj (fft (postpad (X, M)));

    ## do autocorrelations (each column with itself)
    ##  -- if result R is reshaped to 3D matrix (i.e. R=reshape(R,M,P,P))
    ##     the autocorrelations are on leading diagonal columns of R,
    ##     where i==j in R(:,i,j)
    cor = ifft (post .* pre);
    R(:, 1:P+1:P^2) = cor (1:2*maxlag+1,:);

    ## do the cross correlations
    ##   -- these are the off-diagonal colummn of the reshaped 3D result
    ##      matrix -- i!=j in R(:,i,j)
    for i=1:P-1
      j = i+1:P;
      cor = ifft( pre(:,i*ones(length(j),1)) .* post(:,j) );
      R(:,(i-1)*P+j) = cor(1:2*maxlag+1,:);
      R(:,(j-1)*P+i) = conj( flipud( cor(1:2*maxlag+1,:) ) );
    endfor
  elseif isempty(Y)
    ## compute autocorrelation of a single vector
    post = fft( postpad(X(:),M) );
    cor = ifft( post .* conj(post) );
    R = [ conj(cor(maxlag+1:-1:2)) ; cor(1:maxlag+1) ];
  else
    ## compute cross-correlation of X and Y
    ##  If one of X & Y is a row vector, the other can be a column vector.
    pre  = fft( postpad( prepad( X(:), length(X)+maxlag ), M) );
    post = fft( postpad( Y(:), M ) );
    cor = ifft( pre .* conj(post) );
    R = cor(1:2*maxlag+1);
  endif

  ## if inputs are real, outputs should be real, so ignore the
  ## insignificant complex portion left over from the FFT
  if isreal(X) && (isempty(Y) || isreal(Y))
    R=real(R);
  endif

  ## correct for bias
  if strcmp(scale, 'biased')
    R = R ./ N;
  elseif strcmp(scale, 'unbiased')
    R = R ./ ( [ N-maxlag:N-1, N, N-1:-1:N-maxlag ]' * ones(1,columns(R)) );
  elseif strcmp(scale, 'coeff')
    ## R = R ./ R(maxlag+1) works only for autocorrelation
    ## For cross correlation coeff, divide by rms(X)*rms(Y).
    if !isvector(X)
      ## for matrix (more than 1 column) X
      rms = sqrt( sumsq(X) );
      R = R ./ ( ones(rows(R),1) * reshape(rms.'*rms,1,[]) );
    elseif isempty(Y)
      ##  for autocorrelation, R(zero-lag) is the mean square.
      R = R / R(maxlag+1);
    else
      ##  for vectors X and Y
      R = R / sqrt( sumsq(X)*sumsq(Y) );
    endif
  elseif !strcmp(scale, 'none')
    error("xcorr: scale must be 'biased', 'unbiased', 'coeff' or 'none'");
  endif

  ## Pad result if necessary
  ##  (most likely is not required, use "if" to avoid uncessary code)
  ## At this point, lag varies with the first index in R;
  ##  so pad **before** the transpose.
  if pad_result
    R_pad = zeros(pad_result,columns(R));
    R = [R_pad; R; R_pad];
  endif
  ## Correct the shape (transpose) so it is the same as the first input vector
  if isvector(X) && P > 1
    R = R.';
  endif

  ## return the lag indices if desired
  if nargout == 2
    maxlag += pad_result;
    lags = [-maxlag:maxlag];
  endif

endfunction

##------------ Use brute force to compute the correlation -------
##if !isvector(X)
##  ## For matrix X, compute cross-correlation of all columns
##  R = zeros(2*maxlag+1,P^2);
##  for i=1:P
##    for j=i:P
##      idx = (i-1)*P+j;
##      R(maxlag+1,idx) = X(i)*X(j)';
##      for k = 1:maxlag
##  	    R(maxlag+1-k,idx) = X(k+1:N,i) * X(1:N-k,j)';
##  	    R(maxlag+1+k,idx) = X(k:N-k,i) * X(k+1:N,j)';
##      endfor
##	if (i!=j), R(:,(j-1)*P+i) = conj(flipud(R(:,idx))); endif
##    endfor
##  endfor
##elseif isempty(Y)
##  ## reshape X so that dot product comes out right
##  X = reshape(X, 1, N);
##
##  ## compute autocorrelation for 0:maxlag
##  R = zeros (2*maxlag + 1, 1);
##  for k=0:maxlag
##  	R(maxlag+1+k) = X(1:N-k) * X(k+1:N)';
##  endfor
##
##  ## use symmetry for -maxlag:-1
##  R(1:maxlag) = conj(R(2*maxlag+1:-1:maxlag+2));
##else
##  ## reshape and pad so X and Y are the same length
##  X = reshape(postpad(X,N), 1, N);
##  Y = reshape(postpad(Y,N), 1, N)';
##
##  ## compute cross-correlation
##  R = zeros (2*maxlag + 1, 1);
##  R(maxlag+1) = X*Y;
##  for k=1:maxlag
##  	R(maxlag+1-k) = X(k+1:N) * Y(1:N-k);
##  	R(maxlag+1+k) = X(k:N-k) * Y(k+1:N);
##  endfor
##endif
##--------------------------------------------------------------
