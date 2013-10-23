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
## @deftypefn {Script File} {} heart_disease_demo_1
##
## Demonstrate the use of newfis, addvar, addmf, addrule, and evalfis
## to build and evaluate an FIS. Also demonstrate the use of the algebraic
## product and sum as the T-norm/S-norm pair, and demonstrate the use of
## hedges in the FIS rules.
##
## The demo:
## @itemize @minus
## @item
## builds an FIS
## @item
## plots the input membership functions
## @item
## plots the constant output functions
## @item
## displays the FIS rules in verbose format in the Octave window
## @item
## plots the FIS output as a function of the inputs
## @end itemize
##
## @seealso{cubic_approx_demo, heart_disease_demo_2, investment_portfolio_demo, linear_tip_demo, mamdani_tip_demo, sugeno_tip_demo}
## @end deftypefn

## Author:        L. Markowsky
## Note:          This example is based on an assignment written by
##                Dr. Bruce Segee (University of Maine Dept. of ECE).
## Keywords:      fuzzy-logic-toolkit fuzzy tests demos
## Directory:     fuzzy-logic-toolkit/inst
## Filename:      heart_disease_demo_1.m
## Last-Modified: 20 Aug 2012

## Create new FIS.
a = newfis ('Heart-Disease-Risk', 'sugeno', ...
            'algebraic_product', 'algebraic_sum', ...
            'min', 'max', 'wtaver');

## Add two inputs and their membership functions.
a = addvar (a, 'input', 'LDL-Level', [0 300]);
a = addmf (a, 'input', 1, 'Low', 'trapmf', [-1 0 90 130]);
a = addmf (a, 'input', 1, 'Moderate', 'trapmf', [90 130 160 200]);
a = addmf (a, 'input', 1, 'High', 'trapmf', [160 200 300 301]);

a = addvar (a, 'input', 'HDL-Level', [0 100]);
a = addmf (a, 'input', 2, 'Low', 'trapmf', [-1 0 35 45]);
a = addmf (a, 'input', 2, 'Moderate', 'trapmf', [35 45 55 65]);
a = addmf (a, 'input', 2, 'High', 'trapmf', [55 65 100 101]);

## Add one output and its membership functions.
a = addvar (a, 'output', 'Heart-Disease-Risk', [-2 12]);
a = addmf (a, 'output', 1, 'Negligible', 'constant', 0);
a = addmf (a, 'output', 1, 'Low', 'constant', 2.5);
a = addmf (a, 'output', 1, 'Medium', 'constant', 5);
a = addmf (a, 'output', 1, 'High', 'constant', 7.5);
a = addmf (a, 'output', 1, 'Extreme', 'constant', 10);

## Plot the input and output membership functions.
plotmf (a, 'input', 1);
plotmf (a, 'input', 2);
plotmf (a, 'output', 1);

## Add 15 rules and display them in verbose format.
a = addrule (a, [1 1 3 1 1; 1 2 2 1 1; 1 3 1 1 1; ...
                 2 1 4 1 1; 2 2 3 1 1; 2 3 2 1 1; ...
                 3 1 5 1 1; 3 2 4 1 1; 3 3 3 1 1; ...
                 1.3 3.3 2 1 2; ...
                 3.05 1.05 4 1 2; ...
                 -3.2 -1.2 3 1 1]);
puts ("\nOutput of showrule(a):\n\n");
showrule (a);

## Plot the output as a function of the two inputs.
gensurf (a);
