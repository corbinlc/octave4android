## Copyright (C) 2011 Hong Yu <hyu0401@hotmail.com>
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
## @deftypefn {Function File} {[@var{sigma}, @var{corr}] =} cov2corr (@var{cov})
## Convert covariance @var{cov} from input to standard deviation @var{sigma} and
## correlation coefficients @var{corr}.
##
## @seealso{corr2cov, corrcoef, cov, std}
## @end deftypefn

function [sigma, corr] = cov2corr (cov_m)

  if ( nargin != 1 )
    print_usage ();
  elseif ( ndims (cov_m) != 2 || rows(cov_m) != columns(cov_m) )
    error("covariances must be a NxN matrix");
  endif

  sigma = diag(cov_m);
  if ( min(sigma) <= 0 )
    error("covariance: must have all positive values along the diagonal")
  endif

  sigma = sqrt(sigma)';
  corr  = cov_m ./ ( sigma' * sigma );

endfunction

%!demo
%! cov = [ 0.25 -0.5; -0.5 4.0 ];
%! [ sigma, corr ] = cov2corr( cov )
%! %--------------------------------------------------
%! % Input covariance matrix, output standard deviations and correlation 
%! % matrix 

%!test
%! cov = [ 0.25 -0.5; -0.5 4.0 ];
%! [sigma, corr] = cov2corr( cov );
%! assert( sigma, [0.5 2.0] )
%! assert( corr, [1.0 -0.5; -0.5 1.0] );
