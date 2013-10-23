## Copyright (C) 2009, 2011, 2012   Lukas F. Reichlin
##
## This file is part of LTI Syncope.
##
## LTI Syncope is free software: you can redistribute it and/or modify
## it under the terms of the GNU General Public License as published by
## the Free Software Foundation, either version 3 of the License, or
## (at your option) any later version.
##
## LTI Syncope is distributed in the hope that it will be useful,
## but WITHOUT ANY WARRANTY; without even the implied warranty of
## MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
## GNU General Public License for more details.
##
## You should have received a copy of the GNU General Public License
## along with LTI Syncope.  If not, see <http://www.gnu.org/licenses/>.

## -*- texinfo -*-
## TF to SS conversion.
## Reference:
## Varga, A.: Computation of irreducible generalized state-space realizations. 
## Kybernetika, 26:89-106, 1990

## Special thanks to Vasile Sima and Andras Varga for their advice.
## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: October 2009
## Version: 0.4.1

function [retsys, retlti] = __sys2ss__ (sys)

  ## TODO: determine appropriate tolerance from number of inputs
  ##       (since we multiply all denominators in a row), index, ...
  ##       default tolerance from TB01UD is TOLDEF = N*N*EPS 

  ## SECRET WISH: a routine which accepts individual denominators for
  ##              each channel and which supports descriptor systems

  [p, m] = size (sys);
  [num, den] = tfdata (sys);

  len_num = cellfun (@length, num);
  len_den = cellfun (@length, den);

  ## check for properness  
  ## tfpoly ensures that there are no leading zeros
  tmp = len_num > len_den;
  if (any (tmp(:)))       # non-proper transfer function
    ## separation into strictly proper and polynomial part
    [numq, numr] = cellfun (@deconv, num, den, "uniformoutput", false);
    numq = cellfun (@__remove_leading_zeros__, numq, "uniformoutput", false);
    numr = cellfun (@__remove_leading_zeros__, numr, "uniformoutput", false);

    ## minimal state-space realization for the proper part
    [a1, b1, c1] = __proper_tf2ss__ (numr, den, p, m);
    e1 = eye (size (a1));

    ## minimal realization for the polynomial part   
    [e2, a2, b2, c2] = __polynomial_tf2ss__ (numq, p, m);

    ## assemble irreducible descriptor realization
    e = blkdiag (e1, e2);
    a = blkdiag (a1, a2);
    b = vertcat (b1, b2);
    c = horzcat (c1, c2);
    retsys = dss (a, b, c, [], e);
  else                    # proper transfer function
    [a, b, c, d] = __proper_tf2ss__ (num, den, p, m);
    retsys = ss (a, b, c, d);
  endif

  retlti = sys.lti;       # preserve lti properties such as tsam

endfunction


## transfer function to state-space conversion for proper models
function [a, b, c, d] = __proper_tf2ss__ (num, den, p, m)

  ## new cells for the TF of same row denominators
  numc = cell (p, m);
  denc = cell (p, 1);
  
  ## multiply all denominators in a row and
  ## update each numerator accordingly
  ## except for single-input models and those
  ## with equal denominators in a row
  for i = 1 : p
    if (m == 1 || isequal (den{i,:}))
      denc(i) = den{i,1};
      numc(i,:) = num(i,:);
    else
      denc(i) = __conv__ (den{i,:});
      for j = 1 : m
        idx = setdiff (1:m, j);
        numc(i,j) = __conv__ (num{i,j}, den{i,idx});
      endfor
    endif
  endfor

  len_numc = cellfun (@length, numc);
  len_denc = cellfun (@length, denc);

  ## check for properness  
  ## tfpoly ensures that there are no leading zeros
  ## tmp = len_numc > repmat (len_denc, 1, m);
  ## if (any (tmp(:)))
  ##   error ("tf: tf2ss: system must be proper");
  ## endif

  ## create arrays and fill in the data
  ## in a way that Slicot TD04AD can use
  max_len_denc = max (len_denc(:));
  ucoeff = zeros (p, m, max_len_denc);
  dcoeff = zeros (p, max_len_denc);
  index = len_denc-1;

  for i = 1 : p
    len = len_denc(i);
    dcoeff(i, 1:len) = denc{i};
    for j = 1 : m
      ucoeff(i, j, len-len_numc(i,j)+1 : len) = numc{i,j};
    endfor
  endfor

  tol = min (sqrt (eps), eps*prod (index));
  [a, b, c, d] = __sl_td04ad__ (ucoeff, dcoeff, index, tol);
  
endfunction


## realization of the polynomial part according to Andras' paper
function [e2, a2, b2, c2] = __polynomial_tf2ss__ (numq, p, m)

  len_numq = cellfun (@length, numq);
  max_len_numq = max (len_numq(:));
  numq = cellfun (@(x) prepad (x, max_len_numq, 0, 2), numq, "uniformoutput", false);
  f = @(y) cellfun (@(x) x(y), numq);
  s = 1 : max_len_numq;
  D = arrayfun (f, s, "uniformoutput", false);

  e2 = diag (ones (p*(max_len_numq-1), 1), -p);
  a2 = eye (p*max_len_numq);
  b2 = vertcat (D{:});
  c2 = horzcat (zeros (p, p*(max_len_numq-1)), -eye (p));

  ## remove uncontrollable part
  [a2, e2, b2, c2] = __sl_tg01jd__ (a2, e2, b2, c2, 0.0, true, 1, 2);

endfunction


## convolution for more than two arguments
function vec = __conv__ (vec, varargin)

  if (nargin == 1)
    return;
  else
    for k = 1 : nargin-1
      vec = conv (vec, varargin{k});
    endfor
  endif

endfunction


## remove leading zeros from polynomial vector
function p = __remove_leading_zeros__ (p)

  idx = find (p != 0);

  if (isempty (idx))
    p = 0;
  else
    p = p(idx(1) : end);  # p(idx) would remove all zeros
  endif

endfunction