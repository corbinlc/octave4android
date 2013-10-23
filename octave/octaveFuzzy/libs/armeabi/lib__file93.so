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
## @deftypefn {Script File} {} sugeno_tip_demo
##
## Demonstrate the use of the Octave Fuzzy Logic Toolkit to read and
## evaluate a Sugeno-type FIS with multiple outputs stored in a text
## file. Also demonstrate the use of hedges in the FIS rules and the
## Einstein product and sum as the T-norm/S-norm pair.
##
## The demo:
## @itemize @minus
## @item
## reads the FIS structure from a file
## @item
## plots the input membership functions
## @item
## plots the (constant) output functions
## @item
## plots each of the three FIS outputs as a function of the inputs
## @item
## displays the FIS rules in verbose format in the Octave window
## @item
## evaluates the Sugeno-type FIS for six inputs
## @end itemize
##
## @seealso{cubic_approx_demo, heart_disease_demo_1, heart_disease_demo_2, investment_portfolio_demo, linear_tip_demo, mamdani_tip_demo}
## @end deftypefn

## Author:        L. Markowsky
## Keywords:      fuzzy-logic-toolkit fuzzy tests demos
## Note:          This example is based on an assignment written by
##                Dr. Bruce Segee (University of Maine Dept. of ECE).
## Directory:     fuzzy-logic-toolkit/inst
## Filename:      sugeno_tip_demo.m
## Last-Modified: 20 Aug 2012

## Read the FIS structure from a file.
fis = readfis ('sugeno_tip_calculator.fis');

## Plot the input and output membership functions.
plotmf (fis, 'input', 1);
plotmf (fis, 'input', 2);
plotmf (fis, 'output', 1);
plotmf (fis, 'output', 2);
plotmf (fis, 'output', 3);

## Plot the cheap, average, and generous tips as a function of
## Food-Quality and Service.
gensurf (fis, [1 2], 1);
gensurf (fis, [1 2], 2);
gensurf (fis, [1 2], 3);

## Demonstrate showrule with hedges.
showrule (fis);

## Calculate the Tip for 6 sets of input values: 
puts ("\nFor the following values of (Food Quality, Service):\n\n");
food_service = [1 1; 5 5; 10 10; 4 6; 6 4; 7 4]
puts ("\nThe cheap, average, and generous tips are:\n\n");
tip = evalfis (food_service, fis, 1001)
