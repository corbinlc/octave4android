## Author: Paul Kienzle <pkienzle@gmail.com>
## This program is granted to the public domain.

## -*- texinfo -*-
## @deftypefn {Function File} {[@var{p}, @var{s}] =} wpolyfit (@var{x}, @var{y}, @var{dy}, @var{n})
## Return the coefficients of a polynomial @var{p}(@var{x}) of degree
## @var{n} that minimizes
## @iftex
## @tex
## $$
## \sum_{i=1}^N (p(x_i) - y_i)^2
## $$
## @end tex
## @end iftex
## @ifinfo
## @code{sumsq (p(x(i)) - y(i))},
## @end ifinfo
## to best fit the data in the least squares sense.  The standard error
## on the observations @var{y} if present are given in @var{dy}.
##
## The returned value @var{p} contains the polynomial coefficients 
## suitable for use in the function polyval.  The structure @var{s} returns
## information necessary to compute uncertainty in the model.
##
## To compute the predicted values of y with uncertainty use
## @example
## [y,dy] = polyconf(p,x,s,'ci');
## @end example
## You can see the effects of different confidence intervals and
## prediction intervals by calling the wpolyfit internal plot
## function with your fit:
## @example
## feval('wpolyfit:plt',x,y,dy,p,s,0.05,'pi')
## @end example
## Use @var{dy}=[] if uncertainty is unknown.
##
## You can use a chi^2 test to reject the polynomial fit:
## @example
## p = 1-chi2cdf(s.normr^2,s.df);
## @end example
## p is the probability of seeing a chi^2 value higher than that which 
## was observed assuming the data are normally distributed around the fit.
## If p < 0.01, you can reject the fit at the 1% level.
##
## You can use an F test to determine if a higher order polynomial 
## improves the fit:
## @example
## [poly1,S1] = wpolyfit(x,y,dy,n);
## [poly2,S2] = wpolyfit(x,y,dy,n+1);
## F = (S1.normr^2 - S2.normr^2)/(S1.df-S2.df)/(S2.normr^2/S2.df);
## p = 1-f_cdf(F,S1.df-S2.df,S2.df);
## @end example
## p is the probability of observing the improvement in chi^2 obtained
## by adding the extra parameter to the fit.  If p < 0.01, you can reject 
## the lower order polynomial at the 1% level.
##
## You can estimate the uncertainty in the polynomial coefficients 
## themselves using
## @example
## dp = sqrt(sumsq(inv(s.R'))'/s.df)*s.normr;
## @end example
## but the high degree of covariance amongst them makes this a questionable
## operation.
##
## @deftypefnx {Function File} {[@var{p}, @var{s}, @var{mu}] =} wpolyfit (...)
##
## If an additional output @code{mu = [mean(x),std(x)]} is requested then 
## the @var{x} values are centered and normalized prior to computing the fit.
## This will give more stable numerical results.  To compute a predicted 
## @var{y} from the returned model use
## @code{y = polyval(p, (x-mu(1))/mu(2)}
##
## @deftypefnx {Function File} wpolyfit (...)
##
## If no output arguments are requested, then wpolyfit plots the data,
## the fitted line and polynomials defining the standard error range.
##
## Example
## @example
## x = linspace(0,4,20);
## dy = (1+rand(size(x)))/2;
## y = polyval([2,3,1],x) + dy.*randn(size(x));
## wpolyfit(x,y,dy,2);
## @end example
##
## @deftypefnx {Function File} wpolyfit (..., 'origin')
##
## If 'origin' is specified, then the fitted polynomial will go through
## the origin.  This is generally ill-advised.  Use with caution.
##
## Hocking, RR (2003). Methods and Applications of Linear Models.
## New Jersey: John Wiley and Sons, Inc.
##
## @end deftypefn
## @seealso{polyfit,polyconf}

function [p_out, s, mu] = wpolyfit (varargin)

  ## strip 'origin' of the end
  args = length(varargin);
  if args>0 && ischar(varargin{args})
    origin = varargin{args};
    args--;
  else
    origin='';
  endif
  ## strip polynomial order off the end
  if args>0
    n = varargin{args};
    args--;
  else
    n = [];
  end
  ## interpret the remainder as x,y or x,y,dy or [x,y] or [x,y,dy]
  if args == 3
    x = varargin{1};
    y = varargin{2};
    dy = varargin{3};
  elseif args == 2
    x = varargin{1};
    y = varargin{2};
    dy = [];
  elseif args == 1
    A = varargin{1};
    [nr,nc]=size(A);
    if all(nc!=[2,3])
      error("wpolyfit expects vectors x,y,dy or matrix [x,y,dy]");
    endif
    dy = [];
    if nc == 3, dy = A(:,3); endif
    y = A(:,2);
    x = A(:,1);
  else
    usage ("wpolyfit (x, y [, dy], n [, 'origin'])");
  end

  if (length(origin) == 0)
    through_origin = 0;
  elseif strcmp(origin,'origin')
    through_origin = 1;
  else
    error ("wpolyfit: expected 'origin' but found '%s'", origin)
  endif

  if any(size (x) != size (y))
    error ("wpolyfit: x and y must be vectors of the same size");
  endif
  if length(dy)>1 && length(y) != length(dy)
    error ("wpolyfit: dy must be a vector the same length as y");
  endif

  if (! (isscalar (n) && n >= 0 && ! isinf (n) && n == round (n)))
    error ("wpolyfit: n must be a nonnegative integer");
  endif

  if nargout == 3
    mu = [mean(x), std(x)];
    x = (x - mu(1))/mu(2);
  endif

  k = length (x);

  ## observation matrix
  if through_origin
    ## polynomial through the origin y = ax + bx^2 + cx^3 + ...
    A = (x(:) * ones(1,n)) .^ (ones(k,1) * (n:-1:1));
  else
    ## polynomial least squares y = a + bx + cx^2 + dx^3 + ...
    A = (x(:) * ones (1, n+1)) .^ (ones (k, 1) * (n:-1:0));
  endif

  [p,s] = wsolve(A,y(:),dy(:));

  if through_origin
    p(n+1) = 0;
  endif

  if nargout == 0
    good_fit = 1-chi2cdf(s.normr^2,s.df);
    printf("Polynomial: %s  [ p(chi^2>observed)=%.2f%% ]\n", polyout(p,'x'), good_fit*100);
    plt(x,y,dy,p,s,'ci');
  else
    p_out = p';
  endif

function plt(x,y,dy,p,s,varargin)

  if iscomplex(p)
    # XXX FIXME XXX how to plot complex valued functions?
    # Maybe using hue for phase and saturation for magnitude
    # e.g., Frank Farris (Santa Cruz University) has this:
    # http://www.maa.org/pubs/amm_complements/complex.html
    # Could also look at the book
    #   Visual Complex Analysis by Tristan Needham, Oxford Univ. Press
    # but for now we punt
    return
  end

  ## decorate the graph
  grid('on');
  xlabel('abscissa X'); ylabel('data Y');
  title('Least-squares Polynomial Fit with Error Bounds');

  ## draw fit with estimated error bounds
  xf = linspace(min(x),max(x),150)';
  [yf,dyf] = polyconf(p,xf,s,varargin{:});
  plot(xf,yf+dyf,"g.;;", xf,yf-dyf,"g.;;", xf,yf,"g-;fit;");

  ## plot the data
  hold on;
  if (isempty(dy))
    plot(x,y,"x;data;");
  else
    if isscalar(dy), dy = ones(size(y))*dy; end
    errorbar (x, y, dy, "~;data;");
  endif
  hold off;

  if strcmp(deblank(input('See residuals? [y,n] ','s')),'y')
    clf;
    if (isempty(dy))
      plot(x,y-polyval(p,x),"x;data;");
    else
      errorbar(x,y-polyval(p,x),dy, '~;data;');
    endif
    hold on;
    grid on;
    ylabel('Residuals');
    xlabel('abscissa X'); 
    plot(xf,dyf,'g.;;',xf,-dyf,'g.;;');
    hold off;
  endif

%!demo % #1  
%! x = linspace(0,4,20);
%! dy = (1+rand(size(x)))/2;
%! y = polyval([2,3,1],x) + dy.*randn(size(x));
%! wpolyfit(x,y,dy,2);
  
%!demo % #2
%! x = linspace(-i,+2i,20);
%! noise = ( randn(size(x)) + i*randn(size(x)) )/10;
%! P = [2-i,3,1+i];
%! y = polyval(P,x) + noise;
%! wpolyfit(x,y,2)

%!demo
%! pin = [3; -1; 2];
%! x = -3:0.1:3;
%! y = polyval (pin, x);
%!
%! ## Poisson weights
%! # dy = sqrt (abs (y));
%! ## Uniform weights in [0.5,1]
%! dy = 0.5 + 0.5 * rand (size (y));
%!
%! y = y + randn (size (y)) .* dy;
%! printf ("Original polynomial: %s\n", polyout (pin, 'x'));
%! wpolyfit (x, y, dy, length (pin)-1);


