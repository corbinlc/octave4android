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
## @deftypefn {Function File} {@var{[mf_index hedge not_flag]} =} get_mf_index_and_hedge (@var{mf_index_and_hedge})
##
## Return the membership function index, hedge, and flag indicating "not"
## indicated by the argument.
##
## The membership function index, @var{mf_index}, is the positive whole number
## portion of the argument. The @var{hedge} is the fractional part of the
## argument, rounded to 2 digits and multiplied by 10. The @var{not_flag},
## a Boolean, is true iff the argument is negative.
##
## Because get_mf_index_and_hedge is a private function, it does no error
## checking of its argument.
##
## @end deftypefn

## Author:        L. Markowsky
## Keywords:      fuzzy-logic-toolkit fuzzy inference system fis
## Directory:     fuzzy-logic-toolkit/inst/private/
## Filename:      get_mf_index_and_hedge.m
## Last-Modified: 20 Aug 2012

function [mf_index hedge not_flag] = ...
  get_mf_index_and_hedge (mf_index_and_hedge)

  ## Set flag to handle "not", indicated by a minus sign in the
  ## antecedent.

  if (mf_index_and_hedge < 0)
    not_flag = true;
    mf_index_and_hedge = -mf_index_and_hedge;
  else
    not_flag = false;
  endif

  ## The membership function index is the positive whole number portion
  ## of an element in the antecedent.

  mf_index = fix (mf_index_and_hedge);

  ## For custom hedges and the four built-in hedges "somewhat", "very",
  ## "extremely", and "very very", return the power to which the
  ## membership value should be raised. The hedges are indicated by the
  ## fractional part of the corresponding rule_matrix entry (rounded to
  ## 2 digits). 

  if (mf_index != 0)
    hedge = round (100 * (mf_index_and_hedge - mf_index)) / 10;
  else
    hedge = 0;
  endif

endfunction
