## Author: Paul Kienzle <pkienzle@users.sf.net>
## This program is granted to the public domain.

## -*- texinfo -*-
## @deftypefn {Function File} {[@var{pc}, @var{z}, @var{w}, @var{Tsq}] =} princomp (@var{X})
##
## Compute principal components of @var{X}.
##
## The first output argument @var{pc} is the principal components of @var{X}.
## The second @var{z} is the transformed data, and @var{w} is the eigenvalues of
## the covariance matrix of @var{X}. @var{Tsq} is the Hotelling's @math{T^2}
## statistic for the transformed data.
## @end deftypefn

function [pc,z,w,Tsq] = princomp(X)
  C = cov(X);
  [U,D,pc] = svd(C,1);
  if nargout>1, z = center(X)*pc; end
  if nargout>2, w = diag(D); end
  if nargout>3, Tsq = sumsq(zscore(z),2); 
    warning('XXX FIXME XXX Tsq return from princomp fails some tests'); 
  end
endfunction
%!shared pc,z,w,Tsq,m,x

%!test
%! x=[1,2,3;2,1,3]';
%! [pc,z,w,Tsq]=princomp(x);
%! m=[sqrt(2),sqrt(2);sqrt(2),-sqrt(2);-2*sqrt(2),0]/2;
%! m(:,1) = m(:,1)*sign(pc(1,1));
%! m(:,2) = m(:,2)*sign(pc(1,2));

%!assert(pc,m(1:2,:),10*eps);
%!assert(z,-m,10*eps);
%!assert(w,[1.5;.5],10*eps);
%!assert(Tsq,[4;4;4]/3,10*eps);

%!test
%! x=x';
%! [pc,z,w,Tsq]=princomp(x);
%! m=[sqrt(2),sqrt(2),0;-sqrt(2),sqrt(2),0;0,0,2]/2;
%! m(:,1) = m(:,1)*sign(pc(1,1));
%! m(:,2) = m(:,2)*sign(pc(1,2));
%! m(:,3) = m(:,3)*sign(pc(3,3));

%!assert(pc,m,10*eps);
%!assert(z(:,1),-m(1:2,1),10*eps);
%!assert(z(:,2:3),zeros(2),10*eps);
%!assert(w,[1;0;0],10*eps);
%!xtest
%! assert(Tsq,1,10*eps);
