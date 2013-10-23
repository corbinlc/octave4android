## Copyright (C) 2009 Levente Torok <TorokLev@gmail.com>
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
##
## @deftypefn {Function File} {@var{CL} =} cl_multinom (@var{x}, @var{N}, @var{b}, @var{calculation_type} ) - Confidence level of multinomial portions
##    Returns confidence level of multinomial parameters estimated @math{ p = x / sum(x) } with predefined confidence interval @var{b}.
##    Finite population is also considered.
##
## This function calculates the level of confidence at which the samples represent the true distribution
## given that there is a predefined tolerance (confidence interval).
## This is the upside down case of the typical excercises at which we want to get the confidence interval
## given the confidence level (and the estimated parameters of the underlying distribution).
## But once we accept (lets say at elections) that we have a standard predefined
## maximal acceptable error rate (e.g. @var{b}=0.02 ) in the estimation and we just want to know that how sure we
## can be that the measured proportions are the same as in the
## entire population (ie. the expected value and mean of the samples are roghly the same) we need to use this function.
##
## @subheading Arguments
## @itemize @bullet
## @item @var{x}  : int vector  : sample frequencies bins
## @item @var{N}  : int         : Population size that was sampled by x. If N<sum(x), infinite number assumed
## @item @var{b}  : real, vector :  confidence interval
##            if vector, it should be the size of x containing confence interval for each cells
##            if scalar, each cell will have the same value of b unless it is zero or -1
##            if value is 0, b=.02 is assumed which is standard choice at elections
##            otherwise it is calculated in a way that one sample in a cell alteration defines the confidence interval
## @item @var{calculation_type}  : string    : (Optional), described below
##           "bromaghin"     (default) - do not change it unless you have a good reason to do so
##           "cochran"
##           "agresti_cull"  this is not exactly the solution at reference given below but an adjustment of the solutions above
## @end itemize
##
## @subheading Returns
##   Confidence level.
##
## @subheading Example
##   CL = cl_multinom( [27;43;19;11], 10000, 0.05 )
##     returns 0.69 confidence level.
##
## @subheading References
##
## "bromaghin" calculation type (default) is based on
## is based on the article
##   Jeffrey F. Bromaghin, "Sample Size Determination for Interval Estimation of Multinomial Probabilities", The American Statistician  vol 47, 1993, pp 203-206.
##
## "cochran" calculation type
## is based on article
##   Robert T. Tortora, "A Note on Sample Size Estimation for Multinomial Populations", The American Statistician, , Vol 32. 1978,  pp 100-102.
##
## "agresti_cull" calculation type
## is based on article in which Quesenberry Hurst and Goodman result is combined
##   A. Agresti and B.A. Coull, "Approximate is better than \"exact\" for interval estimation of binomial portions", The American Statistician, Vol. 52, 1998, pp 119-126
##
## @end deftypefn

function CL = cl_multinom( x, N, b = .05, calculation_type = "bromaghin")

    if (nargin < 2 || nargin > 4)
        print_usage;
    elseif (!ischar (calculation_type))
        error ("Argument calculation_type must be a string");
    endif

    k = rows(x);
    nn = sum(x);
    p = x / nn;

    if (isscalar( b ))
        if (b==0) b=0.02; endif
        b = ones( rows(x), 1 ) * b;

        if (b<0)  b=1 ./ max( x, 1 ); endif
    endif
    bb = b .* b;

    if (N==nn)
        CL = 1;
        return;
    endif

    if (N<nn)
        fpc = 1;
    else
        fpc = (N-1) / (N-nn); # finite population correction tag
    endif

    beta = p.*(1-p);

    switch calculation_type
      case {"cochran"}
        t = sqrt( fpc * nn * bb ./ beta )
        alpha = ( 1 - normcdf( t )) * 2

      case {"bromaghin"}
        t = sqrt(  fpc * (nn * 2 * bb )./ ( beta - 2 * bb + sqrt( beta .* beta - bb .* ( 4*beta - 1 ))) );
        alpha = ( 1 - normcdf( t )) * 2;

      case {"agresti_cull"}
        ts = fpc * nn * bb ./ beta ;
        if ( k<=2 )
          alpha = 1 - chi2cdf( ts, k-1 ); % adjusted Wilson interval
        else
          alpha = 1 - chi2cdf( ts/k, 1 ); % Goodman interval with Bonferroni argument
        endif
      otherwise
        error ("Unknown calculation type '%s'", calculation_type);
    endswitch

    CL = 1 - max( alpha );

endfunction
