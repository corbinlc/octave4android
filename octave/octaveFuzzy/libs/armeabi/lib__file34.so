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
## @deftypefn {Script File} {} mamdani_tip_demo
## Demonstrate the use of the Octave Fuzzy Logic Toolkit to read and evaluate a
## Mamdani-type FIS stored in a file.
##
## The demo:
## @itemize @minus
## @item
## reads the FIS structure from a file
## @item
## plots the input and output membership functions
## @item
## plots each of the two FIS outputs as a function of the inputs
## @item
## plots the output of the 4 individual rules for (Food-Quality, Service) = (4, 6)
## @item
## plots the aggregated fuzzy output and the crisp output for
## (Food-Quality, Service) = (4, 6)
## @item
## displays the FIS rules in symbolic format in the Octave window
## @end itemize
##
## @seealso{cubic_approx_demo, heart_disease_demo_1, heart_disease_demo_2, investment_portfolio_demo, linear_tip_demo, sugeno_tip_demo}
## @end deftypefn

## Author:        L. Markowsky
## Keywords:      fuzzy-logic-toolkit fuzzy tests demos
## Note:          This example is based on an assignment written by
##                Dr. Bruce Segee (University of Maine Dept. of ECE).
## Directory:     fuzzy-logic-toolkit/inst
## Filename:      mamdani_tip_demo.m
## Last-Modified: 19 Aug 2012

## Read the FIS structure from a file.
fis=readfis ('mamdani_tip_calculator');

## Plot the input and output membership functions.
plotmf (fis, 'input', 1);
plotmf (fis, 'input', 2);
plotmf (fis, 'output', 1);
plotmf (fis, 'output', 2);

## Plot the Tip and Check + Tip as functions of Food-Quality
## and Service.
gensurf (fis, [1 2], 1);
gensurf (fis, [1 2], 2);

## Calculate the Tip and Check + Tip using
## (Food-Quality, Service) = (4, 6).
[output, rule_input, rule_output, fuzzy_output] = ...
  evalfis ([4 6], fis, 1001);

## Plot the first output (Tip) of the individual fuzzy rules
## on one set of axes.
x_axis = linspace (fis.output(1).range(1), ...
                   fis.output(1).range(2), 1001);
colors = ['r' 'b' 'm' 'g'];
figure ('NumberTitle', 'off', 'Name', ...
        'Output of Fuzzy Rules 1-4 for Input = (4, 6)');

for i = 1 : 4
    y_label = [colors(i) ";Rule " num2str(i) ";"];
    plot (x_axis, rule_output(:,i), y_label, 'LineWidth', 2);
    hold on;
endfor

ylim ([-0.1, 1.1]);
xlabel ('Tip', 'FontWeight', 'bold');
grid;
hold;

## Plot the first aggregated fuzzy output and the first crisp output
## (Tip) on one set of axes.
figure('NumberTitle', 'off', 'Name', ...
       'Aggregation and Defuzzification for Input = (4, 6)');
plot (x_axis, fuzzy_output(:, 1), "b;Aggregated Fuzzy Output;", ...
      'LineWidth', 2);
hold on;
crisp_output = evalmf(x_axis, output(1), 'constant');
y_label = ["r;Crisp Output = " num2str(output(1)) "%;"];
plot (x_axis, crisp_output, y_label, 'LineWidth', 2);
ylim ([-0.1, 1.1]);
xlabel ('Tip', 'FontWeight', 'bold');
grid;
hold;

## Show the rules in symbolic format.
puts ("\nMamdani Tip Calculator Rules:\n\n");
showrule (fis, 1:columns(fis.rule), 'symbolic');
