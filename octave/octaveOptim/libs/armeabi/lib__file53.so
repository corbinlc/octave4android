## Author: Paul Kienzle <pkienzle@gmail.com>
## This program is granted to the public domain.

## [y,dy] = polyconf(p,x,s)
##
##   Produce prediction intervals for the fitted y. The vector p 
##   and structure s are returned from polyfit or wpolyfit. The 
##   x values are where you want to compute the prediction interval.
##
## polyconf(...,['ci'|'pi'])
##
##   Produce a confidence interval (range of likely values for the
##   mean at x) or a prediction interval (range of likely values 
##   seen when measuring at x).  The prediction interval tells
##   you the width of the distribution at x.  This should be the same
##   regardless of the number of measurements you have for the value
##   at x.  The confidence interval tells you how well you know the
##   mean at x.  It should get smaller as you increase the number of
##   measurements.  Error bars in the physical sciences usually show 
##   a 1-alpha confidence value of erfc(1/sqrt(2)), representing
##   one standandard deviation of uncertainty in the mean.
##
## polyconf(...,1-alpha)
##
##   Control the width of the interval. If asking for the prediction
##   interval 'pi', the default is .05 for the 95% prediction interval.
##   If asking for the confidence interval 'ci', the default is
##   erfc(1/sqrt(2)) for a one standard deviation confidence interval.
##
## Example:
##  [p,s] = polyfit(x,y,1);
##  xf = linspace(x(1),x(end),150);
##  [yf,dyf] = polyconf(p,xf,s,'ci');
##  plot(xf,yf,'g-;fit;',xf,yf+dyf,'g.;;',xf,yf-dyf,'g.;;',x,y,'xr;data;');
##  plot(x,y-polyval(p,x),';residuals;',xf,dyf,'g-;;',xf,-dyf,'g-;;');

function [y,dy] = polyconf(p,x,varargin)
  alpha = s = [];
  typestr = 'pi';
  for i=1:length(varargin)
    v = varargin{i};
    if isstruct(v), s = v;
    elseif ischar(v), typestr = v;
    elseif isscalar(v), alpha = v;
    else s = [];
    end
  end
  if (nargout>1 && (isempty(s)||nargin<3)) || nargin < 2
    print_usage;
  end

  if isempty(s)
    y = polyval(p,x);

  else
    ## For a polynomial fit, x is the set of powers ( x^n ; ... ; 1 ).
    n=length(p)-1;
    k=length(x(:));
    if columns(s.R) == n, ## fit through origin
      A = (x(:) * ones (1, n)) .^ (ones (k, 1) * (n:-1:1));
      p = p(1:n);
    else
      A = (x(:) * ones (1, n+1)) .^ (ones (k, 1) * (n:-1:0));
    endif
    y = dy = x;
    [y(:),dy(:)] = confidence(A,p,s,alpha,typestr);

  end
end

%!test
%! # data from Hocking, RR, "Methods and Applications of Linear Models"
%! temperature=[40;40;40;45;45;45;50;50;50;55;55;55;60;60;60;65;65;65];
%! strength=[66.3;64.84;64.36;69.70;66.26;72.06;73.23;71.4;68.85;75.78;72.57;76.64;78.87;77.37;75.94;78.82;77.13;77.09];
%! [p,s] = polyfit(temperature,strength,1);
%! [y,dy] = polyconf(p,40,s,0.05,'ci');
%! assert([y,dy],[66.15396825396826,1.71702862681486],200*eps);
%! [y,dy] = polyconf(p,40,s,0.05,'pi');
%! assert(dy,4.45345484470743,200*eps);

## [y,dy] = confidence(A,p,s)
##
##   Produce prediction intervals for the fitted y. The vector p
##   and structure s are returned from wsolve. The matrix A is
##   the set of observation values at which to evaluate the
##   confidence interval.
##
## confidence(...,['ci'|'pi'])
##
##   Produce a confidence interval (range of likely values for the
##   mean at x) or a prediction interval (range of likely values 
##   seen when measuring at x).  The prediction interval tells
##   you the width of the distribution at x.  This should be the same
##   regardless of the number of measurements you have for the value
##   at x.  The confidence interval tells you how well you know the
##   mean at x.  It should get smaller as you increase the number of
##   measurements.  Error bars in the physical sciences usually show 
##   a 1-alpha confidence value of erfc(1/sqrt(2)), representing
##   one standandard deviation of uncertainty in the mean.
##
## confidence(...,1-alpha)
##
##   Control the width of the interval. If asking for the prediction
##   interval 'pi', the default is .05 for the 95% prediction interval.
##   If asking for the confidence interval 'ci', the default is
##   erfc(1/sqrt(2)) for a one standard deviation confidence interval.
##
## Confidence intervals for linear system are given by:
##    x' p +/- sqrt( Finv(1-a,1,df) var(x' p) )
## where for confidence intervals,
##    var(x' p) = sigma^2 (x' inv(A'A) x)
## and for prediction intervals,
##    var(x' p) = sigma^2 (1 + x' inv(A'A) x)
##
## Rather than A'A we have R from the QR decomposition of A, but
## R'R equals A'A.  Note that R is not upper triangular since we
## have already multiplied it by the permutation matrix, but it
## is invertible.  Rather than forming the product R'R which is
## ill-conditioned, we can rewrite x' inv(A'A) x as the equivalent
##    x' inv(R) inv(R') x = t t', for t = x' inv(R)
## Since x is a vector, t t' is the inner product sumsq(t).
## Note that LAPACK allows us to do this simultaneously for many
## different x using sqrt(sumsq(X/R,2)), with each x on a different row.
##
## Note: sqrt(F(1-a;1,df)) = T(1-a/2;df)
##
## For non-linear systems, use x = dy/dp and ignore the y output.
function [y,dy] = confidence(A,p,S,alpha,typestr)
  if nargin < 4, alpha = []; end
  if nargin < 5, typestr = 'ci'; end
  y = A*p(:);
  switch typestr, 
    case 'ci', pred = 0; default_alpha=erfc(1/sqrt(2));
    case 'pi', pred = 1; default_alpha=0.05;
    otherwise, error("use 'ci' or 'pi' for interval type");
  end
  if isempty(alpha), alpha = default_alpha; end
  s = tinv(1-alpha/2,S.df)*S.normr/sqrt(S.df);
  dy = s*sqrt(pred+sumsq(A/S.R,2));
end
