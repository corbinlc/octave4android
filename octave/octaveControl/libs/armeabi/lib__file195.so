% ===============================================================================
% optiPIDctrl                      Lukas Reichlin                   February 2012
% ===============================================================================
% Return PID controller with roll-off for given parameters Kp, Ti and Td.
% ===============================================================================

function C = optiPIDctrl (Kp, Ti, Td)

  tau = Td / 10;    % roll-off

  num = Kp * [Ti*Td, Ti, 1];
  den = conv ([Ti, 0], [tau^2, 2*tau, 1]);
  
  C = tf (num, den);

end

% ===============================================================================
