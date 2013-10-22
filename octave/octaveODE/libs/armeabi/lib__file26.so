%# Copyright (C) 2008-2012, Thomas Treichl <treichl@users.sourceforge.net>
%# OdePkg - A package for solving ordinary differential equations and more
%#
%# This program is free software; you can redistribute it and/or modify
%# it under the terms of the GNU General Public License as published by
%# the Free Software Foundation; either version 2 of the License, or
%# (at your option) any later version.
%#
%# This program is distributed in the hope that it will be useful,
%# but WITHOUT ANY WARRANTY; without even the implied warranty of
%# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
%# GNU General Public License for more details.
%#
%# You should have received a copy of the GNU General Public License
%# along with this program; If not, see <http://www.gnu.org/licenses/>.

%# -*- texinfo -*-
%# @deftypefn {Function File} {[@var{}] =} odepkg_examples_ode (@var{})
%# Open the ODE examples menu and allow the user to select a demo that will be evaluated.
%# @end deftypefn

function [] = odepkg_examples_ode ()

  vode = 1; while (vode > 0)
    clc;
    fprintf (1, ...
      ['ODE examples menu:\n', ...
       '==================\n', ...
       '\n', ...
       '   (1) Solve a non-stiff "Van der Pol" example with solver "ode78"\n', ...
       '   (2) Solve a "Van der Pol" example backward with solver "ode23"\n', ...
       '   (3) Solve a "Pendulous" example with solver "ode45"\n', ...
       '   (4) Solve the "Lorenz attractor" with solver "ode54"\n', ...
       '   (5) Solve the "Roessler equation" with solver "ode78"\n', ...
       '\n', ...
       '   Note: There are further ODE examples available with the OdePkg\n', ...
       '         testsuite functions.\n', ...
       '\n', ...
       '   If you have another interesting ODE example that you would like\n', ...
       '   to share then please modify this file, create a patch and send\n', ...
       '   your patch with your added example to the OdePkg developer team.\n', ...
       '\n' ]);
    vode = input ('Please choose a number from above or press <Enter> to return: ');
    clc; if (vode > 0 && vode < 6)
      %# We can't use the function 'demo' directly here because it does
      %# not allow to run other functions within a demo.
      vexa = example (mfilename (), vode);
      disp (vexa); eval (vexa);
      input ('Press <Enter> to continue: ');
    end %# if (vode > 0)
  end %# while (vode > 0)

%!demo
%! # In this example the non-stiff "Van der Pol" equation (mu = 1) is
%! # solved and the results are displayed in a figure while solving.
%! # Read about the Van der Pol oscillator at
%! #   http://en.wikipedia.org/wiki/Van_der_Pol_oscillator.
%!
%! function [vyd] = fvanderpol (vt, vy, varargin)
%!   mu = varargin{1};
%!   vyd = [vy(2); mu * (1 - vy(1)^2) * vy(2) - vy(1)];
%! endfunction
%!
%! vopt = odeset ('RelTol', 1e-8);
%! ode78 (@fvanderpol, [0 20], [2 0], vopt, 1);

%!demo
%! # In this example the non-stiff "Van der Pol" equation (mu = 1) is
%! # solved in forward and backward direction and the results are 
%! # displayed in a figure after solving. Read about the Van der Pol
%! # oscillator at http://en.wikipedia.org/wiki/Van_der_Pol_oscillator.
%!
%! function [ydot] = fpol (vt, vy, varargin)
%!   ydot = [vy(2); (1 - vy(1)^2) * vy(2) - vy(1)];
%! endfunction
%!
%! vopt = odeset ('NormControl', 'on');
%! vsol = ode23 (@fpol, [0, 20], [2, 0], vopt);
%! subplot (2, 3, 1); plot (vsol.x, vsol.y);
%! vsol = ode23 (@fpol, [0:0.1:20], [2, 0], vopt);
%! subplot (2, 3, 2); plot (vsol.x, vsol.y);
%! vsol = ode23 (@fpol, [-20, 20], [-1.1222e-3, -0.2305e-3], vopt);
%! subplot (2, 3, 3); plot (vsol.x, vsol.y);
%!
%! vopt = odeset ('NormControl', 'on');
%! vsol = ode23 (@fpol, [0:-0.1:-20], [2, 0], vopt);
%! subplot (2, 3, 4); plot (vsol.x, vsol.y);
%! vsol = ode23 (@fpol, [0, -20], [2, 0], vopt);
%! subplot (2, 3, 5); plot (vsol.x, vsol.y);
%! vsol = ode23 (@fpol, [20:-0.1:-20], [-2.0080, 0.0462], vopt);
%! subplot (2, 3, 6); plot (vsol.x, vsol.y);

%!demo
%! # In this example a simple "pendulum with damping" is solved and the
%! # results are displayed in a figure while solving. Read about the 
%! # pendulum with damping at
%! #   http://en.wikipedia.org/wiki/Pendulum
%!
%! function [vyd] = fpendulum (vt, vy)
%!   m = 1;    %# The pendulum mass in kg
%!   g = 9.81; %# The gravity in m/s^2
%!   l = 1;    %# The pendulum length in m
%!   b = 0.7;  %# The damping factor in kgm^2/s
%!   vyd = [vy(2,1); ...
%!          1 / (1/3 * m * l^2) * (-b * vy(2,1) - m * g * l/2 * sin (vy(1,1)))];
%! endfunction
%!
%! vopt = odeset ('RelTol', 1e-3, 'OutputFcn', @odeplot);
%! ode45 (@fpendulum, [0 5], [30*pi/180, 0], vopt);

%!demo
%! # In this example the "Lorenz attractor" implementation is solved
%! # and the results are plot in a figure after solving. Read about
%! # the Lorenz attractor at
%! #   http://en.wikipedia.org/wiki/Lorenz_equation
%! # 
%! # The upper left subfigure shows the three results of the integration
%! # over time. The upper right subfigure shows the force f in a two
%! # dimensional (x,y) plane as well as the lower left subfigure shows
%! # the force in the (y,z) plane. The three dimensional force is plot
%! # in the lower right subfigure.
%!
%! function [vyd] = florenz (vt, vy)
%!   vyd = [10 * (vy(2) - vy(1));
%!          vy(1) * (28 - vy(3));
%!          vy(1) * vy(2) - 8/3 * vy(3)];
%! endfunction
%!
%! A = odeset ('InitialStep', 1e-3, 'MaxStep', 1e-1);
%! [t, y] = ode54 (@florenz, [0 25], [3 15 1], A);
%!
%! subplot (2, 2, 1); grid ('on'); 
%!   plot (t, y(:,1), '-b', t, y(:,2), '-g', t, y(:,3), '-r');
%!   legend ('f_x(t)', 'f_y(t)', 'f_z(t)');
%! subplot (2, 2, 2); grid ('on'); 
%!   plot (y(:,1), y(:,2), '-b');
%!   legend ('f_{xyz}(x, y)');
%! subplot (2, 2, 3); grid ('on'); 
%!   plot (y(:,2), y(:,3), '-b');
%!   legend ('f_{xyz}(y, z)');
%! subplot (2, 2, 4); grid ('on');
%!   plot3 (y(:,1), y(:,2), y(:,3), '-b');
%!   legend ('f_{xyz}(x, y, z)');

%!demo
%! # In this example the "Roessler attractor" implementation is solved
%! # and the results are plot in a figure after solving. Read about
%! # the Roessler attractor at
%! #   http://en.wikipedia.org/wiki/R%C3%B6ssler_attractor
%! #
%! # The upper left subfigure shows the three results of the integration
%! # over time. The upper right subfigure shows the force f in a two 
%! # dimensional (x,y) plane as well as the lower left subfigure shows
%! # the force in the (y,z) plane. The three dimensional force is plot
%! # in the lower right subfigure.
%!
%! function [vyd] = froessler (vt, vx)
%!   vyd = [- ( vx(2) + vx(3) );
%!          vx(1) + 0.2 * vx(2);
%!          0.2 + vx(1) * vx(3) - 5.7 * vx(3)];
%! endfunction
%!
%! A = odeset ('MaxStep', 1e-1);
%! [t, y] = ode78 (@froessler, [0 70], [0.1 0.3 0.1], A);
%!
%! subplot (2, 2, 1); grid ('on'); 
%!   plot (t, y(:,1), '-b;f_x(t);', t, y(:,2), '-g;f_y(t);', \
%!         t, y(:,3), '-r;f_z(t);');
%! subplot (2, 2, 2); grid ('on'); 
%!   plot (y(:,1), y(:,2), '-b;f_{xyz}(x, y);');
%! subplot (2, 2, 3); grid ('on'); 
%!   plot (y(:,2), y(:,3), '-b;f_{xyz}(y, z);');
%! subplot (2, 2, 4); grid ('on'); 
%!   plot3 (y(:,1), y(:,2), y(:,3), '-b;f_{xyz}(x, y, z);');

%# Local Variables: ***
%# mode: octave ***
%# End: ***
