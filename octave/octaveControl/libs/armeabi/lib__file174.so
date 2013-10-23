%% -*- texinfo -*-
%% Robust control of a mass-damper-spring system.
%% Type @code{which MDSSystem} to locate,
%% @code{edit MDSSystem} to open and simply
%% @code{MDSSystem} to run the example file.

% ===============================================================================
% Robust Control of a Mass-Damper-Spring System     Lukas Reichlin    August 2011
% ===============================================================================
% Reference: Gu, D.W., Petkov, P.Hr. and Konstantinov, M.M.
%            Robust Control Design with Matlab, Springer 2005
% ===============================================================================

% Tabula Rasa
clear all, close all, clc

% ===============================================================================
% System Model
% ===============================================================================
%                +---------------+  
%                | d_m   0    0  |
%          +-----|  0   d_c   0  |<----+
%      u_m |     |  0    0   d_k |     | y_m
%      u_c |     +---------------+     | y_c
%      u_k |                           | y_k
%          |     +---------------+     |
%          +---->|               |-----+
%                |     G_nom     |
%        u ----->|               |-----> y
%                +---------------+

% Nominal Values
m_nom = 3;   % mass
c_nom = 1;   % damping coefficient
k_nom = 2;   % spring stiffness

% Perturbations
p_m = 0.4;   % 40% uncertainty in the mass
p_c = 0.2;   % 20% uncertainty in the damping coefficient
p_k = 0.3;   % 30% uncertainty in the spring stiffness

% State-Space Representation
A =   [            0,            1
        -k_nom/m_nom, -c_nom/m_nom ];

B1 =  [            0,            0,            0
                -p_m,   -p_c/m_nom,   -p_k/m_nom ];

B2 =  [            0
             1/m_nom ];

C1 =  [ -k_nom/m_nom, -c_nom/m_nom
                   0,        c_nom
               k_nom,            0 ];

C2 =  [            1,            0 ];

D11 = [         -p_m,   -p_c/m_nom,   -p_k/m_nom
                   0,            0,            0
                   0,            0,            0 ];

D12 = [      1/m_nom
                   0
                   0 ];

D21 = [            0,            0,            0 ];

D22 = [            0 ];

inname = {'u_m', 'u_c', 'u_k', 'u'};   % input names
outname = {'y_m', 'y_c', 'y_k', 'y'};  % output names

G_nom = ss (A, [B1, B2], [C1; C2], [D11, D12; D21, D22], ...
            'inputname', inname, 'outputname', outname);

G = G_nom(4, 4);                       % extract output y and input u


% ===============================================================================
% Frequency Analysis of Uncertain System
% ===============================================================================

% Uncertainties: -1 <= delta_m, delta_c, delta_k <= 1
[delta_m, delta_c, delta_k] = ndgrid ([-1, 0, 1], [-1, 0, 1], [-1, 0, 1]);

% Bode Plots of Perturbed Plants
w = logspace (-1, 1, 100);             % frequency vector
Delta = arrayfun (@(m, c, k) diag ([m, c, k]), delta_m(:), delta_c(:), delta_k(:), 'uniformoutput', false);
G_per = cellfun (@lft, Delta, {G_nom}, 'uniformoutput', false);

figure (1)
bode (G_per{:}, w)
legend off


% ===============================================================================
% Mixed Sensitivity H-infinity Controller Design (S over KS Method)
% ===============================================================================
%                                    +-------+
%             +--------------------->|  W_p  |----------> e_p
%             |                      +-------+
%             |                      +-------+
%             |                +---->|  W_u  |----------> e_u
%             |                |     +-------+
%             |                |    +---------+
%             |                |  ->|         |->
%  r   +    e |   +-------+  u |    |  G_nom  |
% ----->(+)---+-->|   K   |----+--->|         |----+----> y
%        ^ -      +-------+         +---------+    |
%        |                                         |
%        +-----------------------------------------+

% Weighting Functions
s = tf ('s');                          % transfer function variable
W_p = 0.95 * (s^2 + 1.8*s + 10) / (s^2 + 8.0*s + 0.01);  % performance weighting
W_u = 10^-2;                           % control weighting

% Synthesis
K_mix = mixsyn (G, W_p, W_u);          % mixed-sensitivity H-infinity synthesis

% Interconnections
L_mix = G * K_mix;                     % open loop
T_mix = feedback (L_mix);              % closed loop


% ===============================================================================
% H-infinity Loop-Shaping Design (Normalized Coprime Factor Perturbations)
% ===============================================================================

% Settings
W1 = 8 * (2*s + 1) / (0.9*s);          % precompensator
W2 = 1;                                % postcompensator
factor = 1.1;                          % suboptimal controller

% Synthesis
K_ncf = ncfsyn (G, W1, W2, factor);    % positive feedback controller

% Interconnections
K_ncf = -K_ncf;                        % negative feedback controller
L_ncf = G * K_ncf;                     % open loop
T_ncf = feedback (L_ncf);              % closed loop


% ===============================================================================
% Plot Results
% ===============================================================================

% Bode Plot
figure (2)
bode (K_mix, K_ncf)

% Step Response
figure (3)
step (T_mix, T_ncf, 10)                % step response for 10 seconds

% ===============================================================================
