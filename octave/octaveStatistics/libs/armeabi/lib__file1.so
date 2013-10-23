## Author: Paul Kienzle <pkienzle@users.sf.net>
## This program is granted to the public domain.

## -*- texinfo -*-
## @deftypefn {Function File} {[@var{q}, @var{Asq}, @var{info}] = } = @
## anderson_darling_test (@var{x}, @var{distribution})
##
## Test the hypothesis that @var{x} is selected from the given distribution
## using the Anderson-Darling test.  If the returned @var{q} is small, reject
## the hypothesis at the @var{q}*100% level.
##
## The Anderson-Darling @math{@var{A}^2} statistic is calculated as follows:
##
## @example
## @iftex
## A^2_n = -n - \sum_{i=1}^n (2i-1)/n log(z_i (1-z_{n-i+1}))
## @end iftex
## @ifnottex
##               n
## A^2_n = -n - SUM (2i-1)/n log(@math{z_i} (1 - @math{z_@{n-i+1@}}))
##              i=1
## @end ifnottex
## @end example
##
## where @math{z_i} is the ordered position of the @var{x}'s in the CDF of the 
## distribution.  Unlike the Kolmogorov-Smirnov statistic, the 
## Anderson-Darling statistic is sensitive to the tails of the 
## distribution.
##
## The @var{distribution} argument must be a either @t{"uniform"}, @t{"normal"},
## or @t{"exponential"}.
##
## For @t{"normal"}' and @t{"exponential"} distributions, estimate the 
## distribution parameters from the data, convert the values 
## to CDF values, and compare the result to tabluated critical 
## values.  This includes an correction for small @var{n} which 
## works well enough for @var{n} >= 8, but less so from smaller @var{n}.  The
## returned @code{info.Asq_corrected} contains the adjusted statistic.
##
## For @t{"uniform"}, assume the values are uniformly distributed
## in (0,1), compute @math{@var{A}^2} and return the corresponding @math{p}-value from
## @code{1-anderson_darling_cdf(A^2,n)}.
## 
## If you are selecting from a known distribution, convert your 
## values into CDF values for the distribution and use @t{"uniform"}.
## Do not use @t{"uniform"} if the distribution parameters are estimated 
## from the data itself, as this sharply biases the @math{A^2} statistic
## toward smaller values.
##
## [1] Stephens, MA; (1986), "Tests based on EDF statistics", in
## D'Agostino, RB; Stephens, MA; (eds.) Goodness-of-fit Techinques.
## New York: Dekker.
##
## @seealso{anderson_darling_cdf}
## @end deftypefn

function [q,Asq,info] = anderson_darling_test(x,dist)

  if size(x,1) == 1, x=x(:); end
  x = sort(x);
  n = size(x,1);
  use_cdf = 0;

  # Compute adjustment and critical values to use for stats.
  switch dist
    case 'normal',
      # This expression for adj is used in R.
      # Note that the values from NIST dataplot don't work nearly as well.
      adj = 1 + (.75 + 2.25/n)/n;
      qvals = [ 0.1, 0.05, 0.025, 0.01 ];
      Acrit = [ 0.631, 0.752, 0.873, 1.035];
      x = stdnormal_cdf(zscore(x));

    case 'uniform',
      ## Put invalid data at the limits of the distribution
      ## This will drive the statistic to infinity.
      x(x<0) = 0;
      x(x>1) = 1;
      adj = 1.;
      qvals = [ 0.1, 0.05, 0.025, 0.01 ];
      Acrit = [ 1.933, 2.492, 3.070, 3.857 ];
      use_cdf = 1;

    case 'XXXweibull',
      adj = 1 + 0.2/sqrt(n);
      qvals = [ 0.1, 0.05, 0.025, 0.01 ];
      Acrit = [ 0.637, 0.757, 0.877, 1.038];
      ## XXX FIXME XXX how to fit alpha and sigma?
      x = wblcdf (x, ones(n,1)*sigma, ones(n,1)*alpha);

    case 'exponential',
      adj = 1 + 0.6/n;
      qvals = [ 0.1, 0.05, 0.025, 0.01 ];
      # Critical values depend on n.  Choose the appropriate critical set.
      # These values come from NIST dataplot/src/dp8.f.
      Acritn = [
                  0, 1.022, 1.265, 1.515, 1.888
                 11, 1.045, 1.300, 1.556, 1.927;
                 21, 1.062, 1.323, 1.582, 1.945;
                 51, 1.070, 1.330, 1.595, 1.951;
                101, 1.078, 1.341, 1.606, 1.957;
                ];
      # FIXME: consider interpolating in the critical value table.
      Acrit = Acritn(lookup(Acritn(:,1),n),2:5);

      lambda = 1./mean(x);  # exponential parameter estimation
      x = expcdf(x, 1./(ones(n,1)*lambda));

    otherwise
      # FIXME consider implementing more of distributions; a number
      # of them are defined in NIST dataplot/src/dp8.f.
      error("Anderson-Darling test for %s not implemented", dist);
  endswitch

  if any(x<0 | x>1)
    error('Anderson-Darling test requires data in CDF form');
  endif

  i = [1:n]'*ones(1,size(x,2));
  Asq = -n - sum( (2*i-1) .* (log(x) + log(1-x(n:-1:1,:))) )/n;

  # Lookup adjusted critical value in the cdf (if uniform) or in the
  # the critical table.
  if use_cdf
    q = 1-anderson_darling_cdf(Asq*adj, n);
  else
    idx = lookup([-Inf,Acrit],Asq*adj);
    q = [1,qvals](idx); 
  endif

  if nargout > 2,
    info.Asq = Asq;
    info.Asq_corrected = Asq*adj;
    info.Asq_critical = [100*(1-qvals); Acrit]';
    info.p = 1-q;
    info.p_is_precise = use_cdf;
  endif
endfunction

%!demo
%! c = anderson_darling_test(10*rande(12,10000),'exponential');
%! tabulate(100*c,100*[unique(c),1]);
%! % The Fc column should report 100, 250, 500, 1000, 10000 more or less.

%!demo
%! c = anderson_darling_test(randn(12,10000),'normal');
%! tabulate(100*c,100*[unique(c),1]);
%! % The Fc column should report 100, 250, 500, 1000, 10000 more or less.

%!demo
%! c = anderson_darling_test(rand(12,10000),'uniform');
%! hist(100*c,1:2:99);
%! % The histogram should be flat more or less.
