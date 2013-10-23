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
## @deftypefn {Script File} {} heart_disease_demo_2
##
## Demonstrate the use of the Octave Fuzzy Logic Toolkit to read and evaluate a
## Sugeno-type FIS stored in a file.
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
## plots the FIS output as a function of the inputs
## @item
## evaluates the Sugeno-type FIS for four inputs
## @end itemize
##
## @seealso{cubic_approx_demo, heart_disease_demo_1, investment_portfolio_demo, linear_tip_demo, mamdani_tip_demo, sugeno_tip_demo}
## @end deftypefn

## Author:        L. Markowsky
## Keywords:      fuzzy-logic-toolkit fuzzy tests demos
## Note:          This example is based on an assignment written by
##                Dr. Bruce Segee (University of Maine Dept. of ECE).
## Directory:     fuzzy-logic-toolkit/inst
## Filename:      heart_disease_demo_2.m
## Last-Modified: 20 Aug 2012

## Read the FIS structure from a file.
## (Alternatively, to select heart_disease_risk.fis using the dialog,
## replace the following line with
##    fis = readfis ();
fis = readfis('heart_disease_risk.fis');

## Plot the input and output membership functions.
plotmf (fis, 'input', 1);
plotmf (fis, 'input', 2);
plotmf (fis, 'output', 1);

## Plot the Heart Disease Risk as a function of LDL-Level and HDL-Level.
gensurf (fis);

## Calculate the Heart Disease Risk for 4 sets of LDL-HDL values: 
puts ("\nFor the following four sets of LDL-HDL values:\n\n");
ldl_hdl = [129 59; 130 60; 90 65; 205 40]
puts ("\nThe Heart Disease Risk is:\n\n");
heart_disease_risk = evalfis (ldl_hdl, fis, 1001)
