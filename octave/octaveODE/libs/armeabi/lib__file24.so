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
%# @deftypefn {Function File} {[@var{}] =} odepkg_examples_dde (@var{})
%# Open the DDE examples menu and allow the user to select a demo that will be evaluated.
%# @end deftypefn

function [] = odepkg_examples_dde ()

  vode = 1; while (vode > 0)
    clc;
    fprintf (1, ...
      ['DDE examples menu:\n', ...
       '==================\n', ...
       '\n', ...
       '   (1) Solve a simple "exp(...)" example with solver "ode23d"\n', ...
       '   (2) Solve an example from Wille and Baker with solver "ode45d"\n', ...
       '   (3) Solve an example from Hu and Wang with solver "ode54d"\n', ...
       '   (4) Solve the "infectious disease model" with solver "ode78d"\n', ...
       '\n', ...
       '   Note: There are further DDE examples available with the OdePkg\n', ...
       '         testsuite functions.\n', ...
       '\n', ...
       '   If you have another interesting DDE example that you would like\n', ...
       '   to share then please modify this file, create a patch and send\n', ...
       '   your patch with your added example to the OdePkg developer team.\n', ...
       '\n' ]);
    vode = input ('Please choose a number from above or press <Enter> to return: ');
    clc; if (vode > 0 && vode < 5)
      %# We can't use the function 'demo' directly here because it does
      %# not allow to run other functions within a demo.
      vexa = example (mfilename (), vode);
      disp (vexa); eval (vexa);
      input ('Press <Enter> to continue: ');
    end %# if (vode > 0)
  end %# while (vode > 0)

%!demo
%! # Solves a simple example where the delay differential equation is
%! # of the form yd = e^(-lambda*t) - y(t-tau).
%! 
%! function [vyd] = fexp (vt, vy, vz, varargin)
%!   vlambda = varargin{1};
%!   vyd = exp (- vlambda * vt) - vz(1);
%! endfunction
%!
%! vtslot = [0, 15]; vlambda = 1; vinit = 10;
%! vopt = odeset ('NormControl', 'on', 'RelTol', 1e-4, 'AbsTol', 1e-4);
%! vsol = ode23d (@fexp, vtslot, vinit, vlambda, vinit, vopt, vlambda);
%! plot (vsol.x, vsol.y);

%!demo
%! # Solves the example 3 from the publication 'DELSOL - a numerical
%! # code for the solution of systems of delay-differential equations'
%! # from the authors David Wille and Christopher Baker.
%!
%! function [vyd] = fdelsol (vt, vy, vz, varargin)
%!   %# vy is a column vector of size (3,1)
%!   %# vz is the history of size (3,2)
%!   vyd = [vz(1,1); vz(1,1) + vz(2,2); vy(2,1)];
%! endfunction
%!
%! vopt = odeset ('NormControl', 'on', 'MaxStep', 0.1, 'InitialStep', 0.01);
%! vsol = ode45d (@fdelsol, [0, 5], [1, 1, 1], [1, 0.2], ones(3,2), vopt);
%! plot (vsol.x, vsol.y);

%!demo
%! # Solves the examples 2.3.1 and 2.3.2 from the book 'Dynamics of
%! # Controlled Mechanical Systems with Delayed Feedback' from the
%! # authors Haiyan Hu and Zaihua Wang.
%!
%! function [vyd] = fhuwang1 (vt, vy, vz, varargin)
%!   %# vy is of size (1,1), vz is of size (1,1)
%!   vyd = (vz(1,1) - varargin{1})^(1/3);
%! endfunction
%!
%! function [vyd] = fhuwang2 (vt, vy, vz, varargin)
%!   %# vy is of size (1,1), vz is of size (1,1)
%!   vyd = (vy - vz)^(1/3);
%! endfunction
%!
%! vtslot = [0, 10]; vK = 1; vinit = 1; vhist = 0;
%! vopt = odeset ('NormControl', 'on', 'RelTol', 1e-6, 'InitialStep', 0.1);
%!
%! vsol = ode54d (@fhuwang1, vtslot, vK, vinit, vhist, vopt, vK);
%! plot (vsol.x, vsol.y, 'ko-', 'markersize', 1); hold;
%!
%! vsol = ode54d (@fhuwang2, vtslot, vK, vinit, vhist, vopt, vK);
%! plot (vsol.x, vsol.y, 'bx-', 'markersize', 1);

%!demo
%! # Solves the infectious disease model from the book 'Solving Ordinary
%! # Differential Equations 1' from the authors Ernst Hairer and Gerhard
%! # Wanner.
%!
%! function [vyd] = finfect (vx, vy, vz, varargin)
%!   %# vy is of size (3,1), vz is of size (3,2)
%!   vyd = [ - vy(1) * vz(2,1) + vz(2,2);
%!            vy(1) * vz(2,1) - vy(2);
%!            vy(2) - vz(2,2) ];
%! endfunction
%!
%! function [vval, vtrm, vdir] = fevent (vx, vy, vz, varargin)
%!   %# vy is of size (3,1), vz is of size (3,2)
%!   vfec = finfect (vx, vy, vz);
%!   vval = vfec(2:3);  %# Have a look at component two + three
%!   vtrm = zeros(1,2); %# Don't stop if an event is found
%!   vdir = -ones(1,2); %# Check only for falling direction
%! endfunction
%!
%! vopt = odeset ('InitialStep', 1e-3, 'Events', @fevent);
%! vsol = ode78d (@finfect, [0, 40], [5, 0.1, 1], [1, 10], ...
%!                [5, 5; 0.1, 0.1; 1, 1], vopt);
%! plot (vsol.x, vsol.y, 'k-', vsol.xe, vsol.ye, 'ro');

%# Local Variables: ***
%# mode: octave ***
%# End: ***
