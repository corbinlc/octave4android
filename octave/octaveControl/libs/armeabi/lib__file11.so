%% -*- texinfo -*-
%% Frequency-weighted coprime factorization controller reduction.

% ===============================================================================
% Coprime Factorization Controller Reduction     Lukas Reichlin     December 2011
% ===============================================================================
% Reference: Anderson, B.D.O.: Controller Reduction: Concepts and Approaches
%            IEEE Transactions of Automatic Control, Vol. 34, No. 8, August 1989
% ===============================================================================

% Tabula Rasa
clear all, close all, clc

% Plant
A = [ -0.161      -6.004      -0.58215    -9.9835     -0.40727    -3.982       0.0         0.0
       1.0         0.0         0.0         0.0         0.0         0.0         0.0         0.0
       0.0         1.0         0.0         0.0         0.0         0.0         0.0         0.0
       0.0         0.0         1.0         0.0         0.0         0.0         0.0         0.0
       0.0         0.0         0.0         1.0         0.0         0.0         0.0         0.0
       0.0         0.0         0.0         0.0         1.0         0.0         0.0         0.0
       0.0         0.0         0.0         0.0         0.0         1.0         0.0         0.0
       0.0         0.0         0.0         0.0         0.0         0.0         1.0         0.0     ];

B = [  1.0
       0.0  
       0.0  
       0.0  
       0.0  
       0.0  
       0.0  
       0.0 ];

C = [  0.0         0.0         6.4432e-3   2.3196e-3   7.1252e-2   1.0002      0.10455     0.99551 ];

G = ss (A, B, C);

% LQG Design
H = [  0.0         0.0         0.0         0.0         0.55       11.0         1.32       18.0     ];

q1 = 1e-6;
q2 = 100;   % [100, 1000, 2000]

Q = q1 * H.' * H;
R = 1;

W = q2 * B * B.';
V = 1;

F = lqr (G, Q, R)
L = lqe (G, W, V)

% Coprime Factorization using Balanced Truncation Approximation
Kr = arrayfun (@(k) cfconred (G, F, L, k), 8:-1:2, 'uniformoutput', false);  % 'method', 'bfsr-bta'
T = cellfun (@(Kr) feedback (G*Kr), Kr, 'uniformoutput', false);

figure (1)
step (T{:}, 200)

% Coprime Factorization using Singular Perturbation Approximation
Kr = arrayfun (@(k) cfconred (G, F, L, k, 'method', 'bfsr-spa'), 8:-1:2, 'uniformoutput', false);
T = cellfun (@(Kr) feedback (G*Kr), Kr, 'uniformoutput', false);

figure (2)
step (T{:}, 200)

% Frequency-Weighted Coprime Factorization using BTA
Kr = arrayfun (@(k) fwcfconred (G, F, L, k), 8:-1:2, 'uniformoutput', false);
T = cellfun (@(Kr) feedback (G*Kr), Kr, 'uniformoutput', false);

figure (3)
step (T{:}, 300)
