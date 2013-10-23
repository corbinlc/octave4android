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
## @deftypefn {Script File} {} investment_portfolio_demo
## Demonstrate the use of the Octave Fuzzy Logic Toolkit to read and evaluate
## a Mamdani-type FIS stored in a file. Also demonstrate the use of hedges and
## weights in the FIS rules, the use of the Einstein product and sum as the
## T-norm/S-norm pair, and the non-standard use of the Einstein sum as the
## aggregation method.
##
## The demo:
## @itemize @minus
## @item
## reads the FIS structure from a file
## @item
## plots the input and output membership functions
## @item
## plots the FIS output as a function of the inputs
## @item
## plots the output of the 4 individual rules for (Age, Risk-Tolerance) = (40, 7)
## @item
## plots the aggregated fuzzy output and the crisp output for
## (Age, Risk-Tolerance) = (40, 7)
## @item
## shows the rules in verbose format in the Octave window
## @end itemize
##
## @seealso{cubic_approx_demo, heart_disease_demo_1, heart_disease_demo_2, linear_tip_demo, mamdani_tip_demo, sugeno_tip_demo}
## @end deftypefn

## Author:        L. Markowsky
## Keywords:      fuzzy-logic-toolkit fuzzy tests demos
## Directory:     fuzzy-logic-toolkit/inst
## Filename:      investment_portfolio_demo.m
## Last-Modified: 19 Aug 2012

## Read the FIS structure from a file.
fis=readfis ('investment_portfolio');

## Plot the input and output membership functions.
plotmf (fis, 'input', 1);
plotmf (fis, 'input', 2);
plotmf (fis, 'output', 1);

## Plot the Percentage-In-Stocks a function of Age and Risk-Tolerance.
gensurf (fis, [1 2], 1);

## Calculate the Percentage-In-Stocks using
## (Age, Risk-Tolerance) = (40, 7).
[output, rule_input, rule_output, fuzzy_output] = ...
  evalfis ([40 7], fis, 1001);

## Plot the output (Percentage-In-Stocks) of the individual fuzzy rules
## on one set of axes.
x_axis = linspace (fis.output(1).range(1), ...
                   fis.output(1).range(2), 1001);
colors = ['r' 'b' 'm' 'g'];
figure ('NumberTitle', 'off', 'Name', ...
       'Output of Fuzzy Rules 1-4 for (Age, Risk Tolerance) = (40, 7)');

for i = 1 : 4
    y_label = [colors(i) ";Rule " num2str(i) ";"];
    plot (x_axis, rule_output(:,i), y_label, 'LineWidth', 2);
    hold on;
endfor

ylim ([-0.1, 1.1]);
xlabel ('Percentage in Stocks', 'FontWeight', 'bold');
grid;
hold;

## Plot the first aggregated fuzzy output and the crisp output
## (Percentage-In-Stocks) on one set of axes.
figure('NumberTitle', 'off', 'Name', ...
  'Aggregation and Defuzzification for (Age, Risk Tolerace) = (40, 7)');
plot (x_axis, fuzzy_output(:, 1), "b;Aggregated Fuzzy Output;", ...
      'LineWidth', 2);
hold on;
crisp_output = evalmf(x_axis, output(1), 'constant');
y_label = ["r;Crisp Output = " num2str(output(1)) "%;"];
plot (x_axis, crisp_output, y_label, 'LineWidth', 2);
ylim ([-0.1, 1.1]);
xlabel ('Percentage in Stocks', 'FontWeight', 'bold');
grid;
hold;

## Show the rules in English.
puts ("\nInvestment Portfolio Calculator Rules:\n\n");
showrule (fis);
