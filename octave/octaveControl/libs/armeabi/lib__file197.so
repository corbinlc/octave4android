%% -*- texinfo -*-
%% Numerical optimization of a PID controller using an objective function.
%% The objective function is located in the file @command{optiPIDfun}.
%% Type @code{which optiPID} to locate, @code{edit optiPID} to open
%% and simply @code{optiPID} to run the example file.

% ===============================================================================
% optiPID                          Lukas Reichlin                       July 2009
% ===============================================================================
% Numerical Optimization of an A/H PID Controller
% Reference: Guzzella, L. (2007) Analysis and Synthesis of SISO Control Systems.
%            vdf Hochschulverlag, Zurich
% Required OCTAVE Packages: control, optim (and its dependencies)
% Required MATLAB Toolboxes: Control, Optimization
% ===============================================================================

% Tabula Rasa
clear all, close all, clc;

% Global Variables
global P t dt mu_1 mu_2 mu_3

% Plant
numP = [1];
denP = conv ([1, 1, 1], [1, 4, 6, 4, 1]);
P = tf (numP, denP);

% Relative Weighting Factors: PLAY AROUND WITH THESE!
mu_1 = 1;               % Minimize ITAE Criterion
mu_2 = 10;              % Minimize Max Overshoot
mu_3 = 20;              % Minimize Sensitivity Ms

% Simulation Settings: PLANT-DEPENDENT!
t_sim = 30;             % Simulation Time [s]
dt = 0.05;              % Sampling Time [s]
t = 0 : dt : t_sim;     % Time Vector [s]

% A/H PID Controller: Ms = 2.0
[gamma, phi, w_gamma, w_phi] = margin (P);

ku = gamma;
Tu = 2*pi / w_gamma;
kappa = inv (dcgain (P));

disp ('optiPID: Astrom/Hagglund PID controller parameters:');
kp_AH = ku * 0.72 * exp ( -1.60 * kappa  +  1.20 * kappa^2 )
Ti_AH = Tu * 0.59 * exp ( -1.30 * kappa  +  0.38 * kappa^2 )
Td_AH = Tu * 0.15 * exp ( -1.40 * kappa  +  0.56 * kappa^2 )

C_AH = optiPIDctrl (kp_AH, Ti_AH, Td_AH);

% Initial Values
C_par_0 = [kp_AH; Ti_AH; Td_AH];

% Optimization
if (exist ('fminsearch'))
  warning ('optiPID: optimization starts, please be patient ...');
else
  error ('optiPID: please load/install optim package to proceed');
end

C_par_opt = fminsearch (@optiPIDfun, C_par_0);

% Optimized Controller
disp ('optiPID: optimized PID controller parameters:');
kp_opt = C_par_opt(1)
Ti_opt = C_par_opt(2)
Td_opt = C_par_opt(3)

C_opt = optiPIDctrl (kp_opt, Ti_opt, Td_opt);

% Open Loop
L_AH = P * C_AH;
L_opt = P * C_opt;

% Closed Loop
T_AH = feedback (L_AH, 1);
T_opt = feedback (L_opt, 1);

% A Posteriori Stability Check
disp ('optiPID: closed-loop stability check:');
st_AH = isstable (T_AH)
st_opt = isstable (T_opt)

% Stability Margins
disp ('optiPID: gain margin gamma [-] and phase margin phi [deg]:');
[gamma_AH, phi_AH] = margin (L_AH)
[gamma_opt, phi_opt] = margin (L_opt)

% Plot Step Response
figure (1)
step (T_AH, 'b', T_opt, 'r', t)
legend ('Astroem/Haegglund PID', 'Optimized PID', 'Location', 'SouthEast')

% ===============================================================================
