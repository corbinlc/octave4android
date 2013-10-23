## Copyright (C) 2007 Soren Hauberg <soren@hauberg.org>
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
## @deftypefn {Function File} @var{r} = random(@var{name}, @var{arg1})
## @deftypefnx{Function File} @var{r} = random(@var{name}, @var{arg1}, @var{arg2})
## @deftypefnx{Function File} @var{r} = random(@var{name}, @var{arg1}, @var{arg2}, @var{arg3})
## @deftypefnx{Function File} @var{r} = random(@var{name}, ..., @var{s1}, ...)
## Generates pseudo-random numbers from a given one-, two-, or three-parameter
## distribution.
##
## The variable @var{name} must be a string that names the distribution from
## which to sample.  If this distribution is a one-parameter distribution @var{arg1}
## should be supplied, if it is a two-paramter distribution @var{arg2} must also
## be supplied, and if it is a three-parameter distribution @var{arg3} must also
## be present.  Any arguments following the distribution paramters will determine
## the size of the result.
##
## As an example, the following code generates a 10 by 20 matrix containing
## random numbers from a normal distribution with mean 5 and standard deviation
## 2.
## @example
## R = random("normal", 5, 2, [10, 20]);
## @end example
##
## The variable @var{name} can be one of the following strings
## 
## @table @asis
## @item  "beta"
## @itemx "beta distribution"
## Samples are drawn from the Beta distribution.
## @item  "bino"
## @itemx "binomial"
## @itemx "binomial distribution"
## Samples are drawn from the Binomial distribution.
## @item  "chi2"
## @itemx "chi-square"
## @itemx "chi-square distribution"
## Samples are drawn from the Chi-Square distribution.
## @item  "exp"
## @itemx "exponential"
## @itemx "exponential distribution"
## Samples are drawn from the Exponential distribution.
## @item  "f"
## @itemx "f distribution"
## Samples are drawn from the F distribution.
## @item  "gam"
## @itemx "gamma"
## @itemx "gamma distribution"
## Samples are drawn from the Gamma distribution.
## @item  "geo"
## @itemx "geometric"
## @itemx "geometric distribution"
## Samples are drawn from the Geometric distribution.
## @item  "hyge"
## @itemx "hypergeometric"
## @itemx "hypergeometric distribution"
## Samples are drawn from the Hypergeometric distribution.
## @item  "logn"
## @itemx "lognormal"
## @itemx "lognormal distribution"
## Samples are drawn from the Log-Normal distribution.
## @item  "nbin"
## @itemx "negative binomial"
## @itemx "negative binomial distribution"
## Samples are drawn from the Negative Binomial distribution.
## @item  "norm"
## @itemx "normal"
## @itemx "normal distribution"
## Samples are drawn from the Normal distribution.
## @item  "poiss"
## @itemx "poisson"
## @itemx "poisson distribution"
## Samples are drawn from the Poisson distribution.
## @item  "rayl"
## @itemx "rayleigh"
## @itemx "rayleigh distribution"
## Samples are drawn from the Rayleigh distribution.
## @item  "t"
## @itemx "t distribution"
## Samples are drawn from the T distribution.
## @item  "unif"
## @itemx "uniform"
## @itemx "uniform distribution"
## Samples are drawn from the Uniform distribution.
## @item  "unid"
## @itemx "discrete uniform"
## @itemx "discrete uniform distribution"
## Samples are drawn from the Uniform Discrete distribution.
## @item  "wbl"
## @itemx "weibull"
## @itemx "weibull distribution"
## Samples are drawn from the Weibull distribution.
## @end table
## @seealso{rand, betarnd, binornd, chi2rnd, exprnd, frnd, gamrnd, geornd, hygernd,
## lognrnd, nbinrnd, normrnd, poissrnd, raylrnd, trnd, unifrnd, unidrnd, wblrnd}
## @end deftypefn

function retval = random(name, varargin)
  ## General input checking
  if (nargin < 2)
    print_usage();
  endif
  if (!ischar(name))
    error("random: first input argument must be a string");
  endif
  
  ## Select distribution
  switch (lower(name))
    case {"beta", "beta distribution"}
      retval = betarnd(varargin{:});
    case {"bino", "binomial", "binomial distribution"}
      retval = binornd(varargin{:});
    case {"chi2", "chi-square", "chi-square distribution"}
      retval = chi2rnd(varargin{:});
    case {"exp", "exponential", "exponential distribution"}
      retval = exprnd(varargin{:});
    case {"ev", "extreme value", "extreme value distribution"}
      error("random: distribution type '%s' is not yet implemented", name);
    case {"f", "f distribution"}
      retval = frnd(varargin{:});
    case {"gam", "gamma", "gamma distribution"}
     retval = gamrnd(varargin{:});
    case {"gev", "generalized extreme value", "generalized extreme value distribution"}
      error("random: distribution type '%s' is not yet implemented", name);
    case {"gp", "generalized pareto", "generalized pareto distribution"}
      error("random: distribution type '%s' is not yet implemented", name);
    case {"geo", "geometric", "geometric distribution"}
      retval = geornd(varargin{:});
    case {"hyge", "hypergeometric", "hypergeometric distribution"}
      retval = hygernd(varargin{:});
    case {"logn", "lognormal", "lognormal distribution"}
      retval = lognrnd(varargin{:});
    case {"nbin", "negative binomial", "negative binomial distribution"}
      retval = nbinrnd(varargin{:});
    case {"ncf", "noncentral f", "noncentral f distribution"}
      error("random: distribution type '%s' is not yet implemented", name);
    case {"nct", "noncentral t", "noncentral t distribution"}
      error("random: distribution type '%s' is not yet implemented", name);
    case {"ncx2", "noncentral chi-square", "noncentral chi-square distribution"}
      error("random: distribution type '%s' is not yet implemented", name);
    case {"norm", "normal", "normal distribution"}
      retval = normrnd(varargin{:});
    case {"poiss", "poisson", "poisson distribution"}
      retval = poissrnd(varargin{:});
    case {"rayl", "rayleigh", "rayleigh distribution"}
      retval = raylrnd(varargin{:});
    case {"t", "t distribution"}
      retval = trnd(varargin{:});
    case {"unif", "uniform", "uniform distribution"}
      retval = unifrnd(varargin{:});
    case {"unid", "discrete uniform", "discrete uniform distribution"}
      retval = unidrnd(varargin{:});
    case {"wbl", "weibull", "weibull distribution"}
      retval = wblrnd(varargin{:});
    otherwise
      error("random: unsupported distribution type '%s'", name);
  endswitch
endfunction
