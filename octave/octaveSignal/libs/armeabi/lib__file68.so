## Author: Paul Kienzle <pkienzle@users.sf.net> (2006)
## This program is granted to the public domain.

## -*- texinfo -*-
## @deftypefn {Function File} {@var{y} =} idst (@var{x})
## @deftypefnx {Function File} {@var{y} =} idst (@var{x}, @var{n})
## Computes the inverse type I discrete sine transform of @var{y}.  If @var{n} is 
## given, then @var{y} is padded or trimmed to length @var{n} before computing 
## the transform.  If @var{y} is a matrix, compute the transform along the 
## columns of the the matrix.
## @seealso{dst}
## @end deftypefn

function x = idst (y, n)

  if (nargin < 1 || nargin > 2)
    print_usage;
  endif

  if nargin == 1,
    n = size(y,1);
    if n==1, n = size(y,2); end
  end
  x = dst(y, n) * 2/(n+1);

endfunction

%!test
%! x = log(gausswin(32));
%! assert(x, idst(dst(x)), 100*eps)
