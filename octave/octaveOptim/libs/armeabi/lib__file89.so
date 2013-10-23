## Author: Paul Kienzle <pkienzle@gmail.com>
## This program is granted to the public domain.

## [x,s] = wsolve(A,y,dy)
##
## Solve a potentially over-determined system with uncertainty in
## the values. 
##
##     A x = y +/- dy
##
## Use QR decomposition for increased accuracy.  Estimate the 
## uncertainty for the solution from the scatter in the data.
##
## The returned structure s contains
##
##    normr = sqrt( A x - y ), weighted by dy
##    R such that R'R = A'A
##    df = n-p, n = rows of A, p = columns of A
##
## See polyconf for details on how to use s to compute dy.
## The covariance matrix is inv(R'*R).  If you know that the
## parameters are independent, then uncertainty is given by
## the diagonal of the covariance matrix, or 
##
##    dx = sqrt(N*sumsq(inv(s.R'))')
##
## where N = normr^2/df, or N = 1 if df = 0.
##
## Example 1: weighted system
##
##    A=[1,2,3;2,1,3;1,1,1]; xin=[1;2;3]; 
##    dy=[0.2;0.01;0.1]; y=A*xin+randn(size(dy)).*dy;
##    [x,s] = wsolve(A,y,dy);
##    dx = sqrt(sumsq(inv(s.R'))');
##    res = [xin, x, dx]
##
## Example 2: weighted overdetermined system  y = x1 + 2*x2 + 3*x3 + e
##
##    A = fullfact([3,3,3]); xin=[1;2;3];
##    y = A*xin; dy = rand(size(y))/50; y+=dy.*randn(size(y));
##    [x,s] = wsolve(A,y,dy);
##    dx = s.normr*sqrt(sumsq(inv(s.R'))'/s.df);
##    res = [xin, x, dx]
##
## Note there is a counter-intuitive result that scaling the
## uncertainty in the data does not affect the uncertainty in
## the fit.  Indeed, if you perform a monte carlo simulation
## with x,y datasets selected from a normal distribution centered
## on y with width 10*dy instead of dy you will see that the
## variance in the parameters indeed increases by a factor of 100.
## However, if the error bars really do increase by a factor of 10
## you should expect a corresponding increase in the scatter of 
## the data, which will increase the variance computed by the fit.

function [x_out,s]=wsolve(A,y,dy)
  if nargin < 2, usage("[x dx] = wsolve(A,y[,dy])"); end
  if nargin < 3, dy = []; end

  [nr,nc] = size(A);
  if nc > nr, error("underdetermined system"); end

  ## apply weighting term, if it was given
  if prod(size(dy))==1
    A = A ./ dy;
    y = y ./ dy;
  elseif ~isempty(dy)
    A = A ./ (dy * ones (1, columns(A)));
    y = y ./ dy;
  endif

  ## system solution: A x = y => x = inv(A) y
  ## QR decomposition has good numerical properties:
  ##   AP = QR, with P'P = Q'Q = I, and R upper triangular
  ## so
  ##   inv(A) y = P inv(R) inv(Q) y = P inv(R) Q' y = P (R \ (Q' y))
  ## Note that b is usually a vector and Q is matrix, so it will
  ## be faster to compute (y' Q)' than (Q' y).
  [Q,R,p] = qr(A,0);
  x = R\(y'*Q)'; 
  x(p) = x;

  s.R = R;
  s.R(:,p) = R;
  s.df = nr-nc;
  s.normr = norm(y - A*x);

  if nargout == 0,
    cov = s.R'*s.R
    if s.df, normalized_chisq = s.normr^2/s.df, end
    x = x'
  else
    x_out = x;
  endif

## We can show that uncertainty dx = sumsq(inv(R'))' = sqrt(diag(inv(A'A))).
##
## Rather than calculate inv(A'A) directly, we are going to use the QR
## decomposition we have already computed:
##
##    AP = QR, with P'P = Q'Q = I, and R upper triangular
##
## so 
##
##    A'A = PR'Q'QRP' = PR'RP'
##
## and
##
##    inv(A'A) = inv(PR'RP') = inv(P')inv(R'R)inv(P) = P inv(R'R) P'
##
## For a permutation matrix P,
##
##    diag(PXP') = P diag(X)
##
## so
##    diag(inv(A'A)) = diag(P inv(R'R) P') = P diag(inv(R'R))
##
## For R upper triangular, inv(R') = inv(R)' so inv(R'R) = inv(R)inv(R)'.
## Conveniently, for X upper triangular, diag(XX') = sumsq(X')', so
##
##    diag(inv(A'A)) = P sumsq(inv(R)')'
## 
## This is both faster and more accurate than computing inv(A'A)
## directly.
##
## One small problem:  if R is not square then inv(R) does not exist.
## This happens when the system is underdetermined, but in that case
## you shouldn't be using wsolve.
 
