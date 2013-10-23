## Author: Paul Kienzle <pkienzle@users.sf.net>
## This program is granted to the public domain.

## -*- texinfo -*-
## @deftypefn {Function File} {@var{y} =} mvnpdf (@var{x})
## @deftypefnx{Function File} {@var{y} =} mvnpdf (@var{x}, @var{mu})
## @deftypefnx{Function File} {@var{y} =} mvnpdf (@var{x}, @var{mu}, @var{sigma})
## Compute multivariate normal pdf for @var{x} given mean @var{mu} and covariance matrix 
## @var{sigma}.  The dimension of @var{x} is @var{d} x @var{p}, @var{mu} is
## @var{1} x @var{p} and @var{sigma} is @var{p} x @var{p}. The normal pdf is
## defined as
##
## @example
## @iftex
## @tex
## $$ 1/y^2 = (2 pi)^p |\Sigma| \exp \{ (x-\mu)^T \Sigma^{-1} (x-\mu) \} $$
## @end tex
## @end iftex
## @ifnottex
## 1/@var{y}^2 = (2 pi)^@var{p} |@var{Sigma}| exp @{ (@var{x}-@var{mu})' inv(@var{Sigma})@
## (@var{x}-@var{mu}) @}
## @end ifnottex
## @end example
##
## @strong{References}
## 
## NIST Engineering Statistics Handbook 6.5.4.2
## http://www.itl.nist.gov/div898/handbook/pmc/section5/pmc542.htm
##
## @strong{Algorithm}
##
## Using Cholesky factorization on the positive definite covariance matrix:
##
## @example
## @var{r} = chol (@var{sigma});
## @end example
##
## where @var{r}'*@var{r} = @var{sigma}. Being upper triangular, the determinant
## of @var{r} is  trivially the product of the diagonal, and the determinant of
## @var{sigma} is the square of this:
##
## @example
## @var{det} = prod (diag (@var{r}))^2;
## @end example
##
## The formula asks for the square root of the determinant, so no need to 
## square it.
##
## The exponential argument @var{A} = @var{x}' * inv (@var{sigma}) * @var{x}
##
## @example
## @var{A} = @var{x}' * inv (@var{sigma}) * @var{x}
##   = @var{x}' * inv (@var{r}' * @var{r}) * @var{x}
##   = @var{x}' * inv (@var{r}) * inv(@var{r}') * @var{x}
## @end example
##
## Given that inv (@var{r}') == inv(@var{r})', at least in theory if not numerically,
##
## @example
## @var{A}  = (@var{x}' / @var{r}) * (@var{x}'/@var{r})' = sumsq (@var{x}'/@var{r})
## @end example
##
## The interface takes the parameters to the multivariate normal in columns rather than 
## rows, so we are actually dealing with the transpose:
##
## @example
## @var{A} = sumsq (@var{x}/r)
## @end example
##
## and the final result is:
##
## @example
## @var{r} = chol (@var{sigma})
## @var{y} = (2*pi)^(-@var{p}/2) * exp (-sumsq ((@var{x}-@var{mu})/@var{r}, 2)/2) / prod (diag (@var{r}))
## @end example
##
## @seealso{mvncdf, mvnrnd}
## @end deftypefn

function pdf = mvnpdf (x, mu = 0, sigma = 1)
  ## Check input
  if (!ismatrix (x))
    error ("mvnpdf: first input must be a matrix");
  endif
  
  if (!isvector (mu) && !isscalar (mu))
    error ("mvnpdf: second input must be a real scalar or vector");
  endif
  
  if (!ismatrix (sigma) || !issquare (sigma))
    error ("mvnpdf: third input must be a square matrix");
  endif
  
  [ps, ps] = size (sigma);
  [d, p] = size (x);
  if (p != ps)
    error ("mvnpdf: dimensions of data and covariance matrix does not match");
  endif
  
  if (numel (mu) != p && numel (mu) != 1)
    error ("mvnpdf: dimensions of data does not match dimensions of mean value");
  endif

  mu = mu (:).';
  if (all (size (mu) == [1, p]))
    mu = repmat (mu, [d, 1]);
  endif
  
  if (nargin < 3)
    pdf = (2*pi)^(-p/2) * exp (-sumsq (x-mu, 2)/2);
  else
    r = chol (sigma);
    pdf = (2*pi)^(-p/2) * exp (-sumsq ((x-mu)/r, 2)/2) / prod (diag (r));
  endif
endfunction

%!demo
%! mu = [0, 0];
%! sigma = [1, 0.1; 0.1, 0.5];
%! [X, Y] = meshgrid (linspace (-3, 3, 25));
%! XY = [X(:), Y(:)];
%! Z = mvnpdf (XY, mu, sigma);
%! mesh (X, Y, reshape (Z, size (X)));
%! colormap jet

%!test
%! mu = [1,-1];
%! sigma = [.9 .4; .4 .3];
%! x = [ 0.5 -1.2; -0.5 -1.4; 0 -1.5];
%! p = [   0.41680003660313; 0.10278162359708; 0.27187267524566 ];
%! q = mvnpdf (x, mu, sigma);
%! assert (p, q, 10*eps);
