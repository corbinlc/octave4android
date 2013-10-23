## Copyright (C) 1999 Paul Kienzle <pkienzle@users.sf.net>
## Copyright (C) 2006 Peter Lanspeary
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

## usage:  [a, v, k] = aryule (x, p)
## 
## fits an AR (p)-model with Yule-Walker estimates.
## x = data vector to estimate
## a: AR coefficients
## v: variance of white noise
## k: reflection coeffients for use in lattice filter 
##
## The power spectrum of the resulting filter can be plotted with
## pyulear(x, p), or you can plot it directly with ar_psd(a,v,...).
##
## See also:
## pyulear, power, freqz, impz -- for observing characteristics of the model
## arburg -- for alternative spectral estimators
##
## Example: Use example from arburg, but substitute aryule for arburg.
##
## Note: Orphanidis '85 claims lattice filters are more tolerant of 
## truncation errors, which is why you might want to use them.  However,
## lacking a lattice filter processor, I haven't tested that the lattice
## filter coefficients are reasonable.

function [a, v, k] = aryule (x, p)
  if ( nargin~=2 )
    print_usage;
  elseif ( ~isvector(x) || length(x)<3 )
    error( 'aryule: arg 1 (x) must be vector of length >2' );
  elseif ( ~isscalar(p) || fix(p)~=p || p > length(x)-2 )
    error( 'aryule: arg 2 (p) must be an integer >0 and <length(x)-1' );
  endif

  c = xcorr(x, p+1, 'biased');
  c(1:p+1) = [];     # remove negative autocorrelation lags
  c(1) = real(c(1)); # levinson/toeplitz requires exactly c(1)==conj(c(1))
  if nargout <= 1
    a = levinson(c, p);
  elseif nargout == 2
    [a, v] = levinson(c, p);
  else
    [a, v, k] = levinson(c, p);
  endif
endfunction

%!demo
%! % use demo('pyulear')
