% ===============================================================================
% optiPIDfun                       Lukas Reichlin                       July 2009
% ===============================================================================
% Objective Function
% Reference: Guzzella, L. (2007) Analysis and Synthesis of SISO Control Systems.
%            vdf Hochschulverlag, Zurich
% ===============================================================================

function J = optiPIDfun (C_par)

  % Global Variables
  global P t dt mu_1 mu_2 mu_3

  % Function Argument -> Controller Parameters
  kp = C_par(1);
  Ti = C_par(2);
  Td = C_par(3);

  % PID Controller with Roll-Off
  C = optiPIDctrl (kp, Ti, Td);

  % Open Loop
  L = P * C;

  % Sum Block: e = r - y
  SUM = ss ([1, -1]);  % Matlab converts to SS (and back) for MIMO TF connections

  % Group Sum Block and Open Loop
  SUML = append (SUM, L);

  % Build System Interconnections
  CM = [3, 1;          % Controller Input with Sum Block Output 
        2, 2];         % Sum Block Negative Input with Plant Output

  inputs = [1];        % Input 1: reference r(t)
  outputs = [1, 2];    % Output 1: error e(t), Output 2: output y(t)

  SUML = connect (SUML, CM, inputs, outputs);

  % Simulation
  [y, t_y] = step (SUML, t);

  % ITAE Criterion
  itae = dt * (t_y.' * abs (y(:, 1)));

  % Sensitivity
  S = inv (1 + L);
  Ms = norm (S, inf);

  % Objective Function
  J = mu_1 * itae  +  mu_2 * (max (y(:, 2)) - 1)  +  mu_3 * Ms;

end  % function

% ===============================================================================

