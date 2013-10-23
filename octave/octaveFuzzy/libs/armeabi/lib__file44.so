## Copyright (C) 2011-2012 L. Markowsky <lmarkov@users.sourceforge.net>
##
## This file is part of the fuzzy-logic-toolkit.
##
## The fuzzy-logic-toolkit is free software; you can redistribute it
## and/or modify it under the terms of the GNU General Public License
## as published by the Free Software Foundation; either version 3 of
## the License, or (at your option) any later version.
##
## The fuzzy-logic-toolkit is distributed in the hope that it will be
## useful, but WITHOUT ANY WARRANTY; without even the implied warranty
## of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
## General Public License for more details.
##
## You should have received a copy of the GNU General Public License
## along with the fuzzy-logic-toolkit; see the file COPYING.  If not,
## see <http://www.gnu.org/licenses/>.

## -*- texinfo -*-
## @deftypefn {Function File} {@var{y} =} are_mf_params (@var{type}, @var{params})
##
## Return 0 if @var{type} is a built-in membership function and @var{params}
## are not valid parameters for that type, and return 1 otherwise.
##
## are_mf_params is a private function that localizes the test for validity of
## membership function parameters. Note that for a custom membership function,
## this function always returns 1.
##
## @end deftypefn

## Author:        L. Markowsky
## Keywords:      fuzzy-logic-toolkit fuzzy private parameter-test
## Directory:     fuzzy-logic-toolkit/inst/private/
## Filename:      are_mf_params.m
## Last-Modified: 20 Aug 2012

function y = are_mf_params (type, params)

  switch (type)
    case 'constant' y = real_vector (params);
    case 'dsigmf'   y = four_reals (params);
    case 'gauss2mf' y = four_reals (params);
    case 'gaussmf'  y = two_reals (params);
    case 'gbellmf'  y = gbellmf_params (params);
    case 'linear'   y = real_vector (params);
    case 'pimf'     y = pimf_params (params);
    case 'psigmf'   y = four_reals (params);
    case 'sigmf'    y = two_reals (params);
    case 'smf'      y = smf_params (params);
    case 'trapmf'   y = trapmf_params (params);
    case 'trimf'    y = trimf_params (params);
    case 'zmf'      y = zmf_params (params);
    otherwise       y = 1;
  endswitch

endfunction

##----------------------------------------------------------------------
## Usage: y = real_vector (params)
##
## Return 1 if params is a vector of real numbers, and
## return 0 otherwise.
##----------------------------------------------------------------------

function y = real_vector (params)

  y = isvector (params) && isreal (params);

endfunction

##----------------------------------------------------------------------
## Usage: y = two_reals (params)
##
## Return 1 if params is a vector of 2 real numbers, and
## return 0 otherwise.
##----------------------------------------------------------------------

function y = two_reals (params)

  y = isvector (params) && isreal (params) && (length (params) == 2);

endfunction

##----------------------------------------------------------------------
## Usage: y = three_reals (params)
##
## Return 1 if params is a vector of 3 real numbers, and
## return 0 otherwise.
##----------------------------------------------------------------------

function y = three_reals (params)

  y = isvector (params) && isreal (params) && (length (params) == 3);

endfunction

##----------------------------------------------------------------------
## Usage: y = four_reals (params)
##
## Return 1 if params is a vector of 4 real numbers, and
## return 0 otherwise.
##----------------------------------------------------------------------

function y = four_reals (params)

  y = isvector (params) && isreal (params) && (length (params) == 4);

endfunction

##----------------------------------------------------------------------
## Usage: y = gbellmf_params (params)
##        y = gbellmf_params ([a b c])
##
## Return 1 if params is a vector of 3 real numbers, [a b c], with
## a != 0 and integral-valued b, and return 0 otherwise.
##----------------------------------------------------------------------

function y = gbellmf_params (params)

  y = three_reals (params) && (params(1) != 0) && is_int (params(2));

endfunction

##----------------------------------------------------------------------
## Usage: y = pimf_params (params)
##        y = pimf_params ([a b c d])
##
## Return 1 if params is a vector of 4 real numbers, [a b c d], with
## a < b <= c < d, and return 0 otherwise.
##----------------------------------------------------------------------

function y = pimf_params (params)

  y = four_reals (params) && ...
      (params(1) < params(2)) && ...
      (params(2) <= params(3)) && ...
      (params(3) < params(4));

endfunction

##----------------------------------------------------------------------
## Usage: y = smf_params (params)
##        y = smf_params ([a b])
##
## Return 1 if params is a vector of 2 real numbers, [a b], with a < b,
## and return 0 otherwise.
##----------------------------------------------------------------------

function y = smf_params (params)

  y = two_reals (params) && (params(1) < params(2));

endfunction

##----------------------------------------------------------------------
## Usage: y = trapmf_params (params)
##        y = trapmf_params ([a b c d])
##
## Return 1 if params is a vector of 4 real numbers, [a b c d], with
## a < b <= c < d, and return 0 otherwise.
##----------------------------------------------------------------------

function y = trapmf_params (params)

  y = four_reals (params) && ...
      (params(1) < params(2)) && ...
      (params(2) <= params(3)) && ...
      (params(3) < params(4));

endfunction

##----------------------------------------------------------------------
## Usage: y = trimf_params (params)
##        y = trimf_params ([a b c])
##
## Return 1 if params is a vector of 3 real numbers, [a b c], with
## a < b < c, and return 0 otherwise.
##----------------------------------------------------------------------

function y = trimf_params (params)

  y = three_reals (params) && ...
      (params(1) < params(2)) && ...
      (params(2) < params(3));

endfunction

##----------------------------------------------------------------------
## Usage: y = zmf_params (params)
##        y = zmf_params ([a b])
##
## Return 1 if params is a vector of 2 real numbers, [a b], with a < b,
## and return 0 otherwise.
##----------------------------------------------------------------------

function y = zmf_params (params)

  y = two_reals (params) && (params(1) < params(2));

endfunction
