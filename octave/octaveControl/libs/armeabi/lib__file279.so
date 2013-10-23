## -*- texinfo -*-
## @deftypefn{Function File} {@var{sys} =} WestlandLynx ()
## Model of the Westland Lynx Helicopter about hover.
## @example
## @group
## INPUTS
## main rotor collective
## longitudinal cyclic
## lateral cyclic
## tail rotor collective
## @end group
## @end example
## @example
## @group
## STATES
## pitch attitude           theta       [rad]
## roll attitude            phi         [rad]
## roll rate (body-axis)    p           [rad/s]
## pitch rate (body-axis)   q           [rad/s]
## yaw rate                 xi          [rad/s]
## forward velocity         v_x         [ft/s]
## lateral velocity         v_y         [ft/s]
## vertical velocity        v_z         [ft/s]
## @end group
## @end example
## @example
## @group
## OUTPUTS
## heave velocity           H_dot       [ft/s]
## pitch attitude           theta       [rad]
## roll attitude            phi         [rad]
## heading rate             psi_dot     [rad/s]
## roll rate                p           [rad/s]
## pitch rate               q           [rad/s]
## @end group
## @end example
##
## @strong{References}@*
## [1] Skogestad, S. and Postlethwaite I. (2005)
## @cite{Multivariable Feedback Control: Analysis and Design:
## Second Edition}.  Wiley.
## @url{http://www.nt.ntnu.no/users/skoge/book/2nd_edition/matlab_m/matfiles.html}
##
## @end deftypefn

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: January 2010
## Version: 0.1

function sys = WestlandLynx ()

  if (nargin)
    print_usage ();
  endif

  a01 = [                 0                  0                  0   0.99857378005981;
                          0                  0   1.00000000000000  -0.00318221934140;
                          0                  0 -11.57049560546880  -2.54463768005371;
                          0                  0   0.43935656547546  -1.99818229675293;
                          0                  0  -2.04089546203613  -0.45899915695190;
         -32.10360717773440                  0  -0.50335502624512   2.29785919189453;
           0.10216116905212  32.05783081054690  -2.34721755981445  -0.50361156463623;
          -1.91097259521484   1.71382904052734  -0.00400543212891  -0.05741119384766];

  a02 = [  0.05338427424431                  0                  0                  0;
           0.05952465534210                  0                  0                  0;
          -0.06360262632370   0.10678052902222  -0.09491866827011   0.00710757449269;
                          0   0.01665188372135   0.01846204698086  -0.00118747074157;
          -0.73502779006958   0.01925575733185  -0.00459562242031   0.00212036073208;
                          0  -0.02121581137180  -0.02116791903973   0.01581159234047;
           0.83494758605957   0.02122657001019  -0.03787973523140   0.00035400385968;
                          0   0.01398963481188  -0.00090675335377  -0.29051351547241];

  a0 = [a01 a02];

  b0 = [                  0                  0                  0                  0;
                          0                  0                  0                  0;
           0.12433505058289   0.08278584480286  -2.75247764587402  -0.01788876950741;
          -0.03635892271996   0.47509527206421   0.01429074257612                  0;
           0.30449151992798   0.01495801657438  -0.49651837348938  -0.20674192905426;
           0.28773546218872  -0.54450607299805  -0.01637935638428                  0;
          -0.01907348632812   0.01636743545532  -0.54453611373901   0.23484230041504;
          -4.82063293457031  -0.00038146972656                  0                  0];

  c0 = [  0        0         0         0         0    0.0595   0.05329  -0.9968;
        1.0        0         0         0         0         0         0        0;
          0      1.0         0         0         0         0         0        0;
          0        0         0  -0.05348       1.0         0         0        0;
          0        0       1.0         0         0         0         0        0;
          0        0         0       1.0         0         0         0       0];

  d0 = zeros (6, 4);

  inname = {"main rotor collective", "longitudinal cyclic", "lateral cyclic", "tail rotor collective"};
  stname = {"theta", "phi", "p", "q", "xi", "v_x", "v_y", "v_z"};
  outname = {"H_dot", "theta", "phi", "psi_dot", "p", "q"};

  sys = ss (a0, b0, c0, d0, "inname", inname, "stname", stname, "outname", outname);

endfunction
