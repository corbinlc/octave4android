## Author: Paul Kienzle <pkienzle@users.sf.net> (2006)
## This program is granted to the public domain.

## -*- texinfo -*-
## @deftypefn {Function File} {@var{y} =} dst (@var{x})
## @deftypefnx {Function File} {@var{y} =} dst (@var{x}, @var{n})
## Computes the type I discrete sine transform of @var{x}.  If @var{n} is given, 
## then @var{x} is padded or trimmed to length @var{n} before computing the transform.
## If @var{x} is a matrix, compute the transform along the columns of the
## the matrix.
##
## The discrete sine transform X of x can be defined as follows:
##
## @verbatim
##        N
## X[k] = sum x[n] sin (pi n k / (N+1) ),  k = 1, ..., N
##        n=1
## @end verbatim
##
## @seealso{idst}
## @end deftypefn

function y = dst (x, n)

  if (nargin < 1 || nargin > 2)
    print_usage;
  endif

  transpose = (rows (x) == 1);
  if transpose, x = x (:); endif

  [nr, nc] = size (x);
  if nargin == 1
    n = nr;
  elseif n > nr
    x = [ x ; zeros(n-nr,nc) ];
  elseif n < nr
    x (nr-n+1 : n, :) = [];
  endif

  y = fft ([ zeros(1,nc); x ; zeros(1,nc); -flipud(x) ])/-2j;
  y = y(2:nr+1,:);
  if isreal(x), y = real (y); endif

  ## Compare directly against the slow transform
  # y2 = x;
  # w = pi*[1:n]'/(n+1);
  # for k = 1:n, y2(k) = sum(x(:).*sin(k*w)); end
  # y = [y,y2];

  if transpose, y = y.'; endif

endfunction

%!test
%! x = log(linspace(0.1,1,32));
%! y = dst(x);
%! assert(y(3), sum(x.*sin(3*pi*[1:32]/33)), 100*eps)
