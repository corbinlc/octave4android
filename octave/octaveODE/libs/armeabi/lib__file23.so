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
%# @deftypefn {Function File} {[@var{}] =} odepkg_examples_dae (@var{})
%# Open the DAE examples menu and allow the user to select a demo that will be evaluated.
%# @end deftypefn

function [] = odepkg_examples_dae ()

  vode = 1; while (vode > 0)
    clc;
    fprintf (1, ...
      ['DAE examples menu:\n', ...
       '==================\n', ...
       '\n', ...
       '   (1) Solve the "Robertson problem" with solver "ode2r"\n', ...
       '   (2) Solve another "Robertson implementation" with solver "ode5r"\n', ...
       '\n', ...
       '   Note: There are further DAE examples available with the OdePkg\n', ...
       '         testsuite functions.\n', ...
       '\n', ...
       '   If you have another interesting DAE example that you would like\n', ...
       '   to share then please modify this file, create a patch and send\n', ...
       '   your patch with your added example to the OdePkg developer team.\n', ...
       '\n' ]);
    vode = input ('Please choose a number from above or press <Enter> to return: ');
    clc; if (vode > 0 && vode < 3)
      %# We can't use the function 'demo' directly here because it does
      %# not allow to run other functions within a demo.
      vexa = example (mfilename (), vode);
      disp (vexa); eval (vexa);
      input ('Press <Enter> to continue: ');
    end %# if (vode > 0)
  end %# while (vode > 0)

%!demo
%! # Solve the "Robertson problem" with a Mass function that is given by
%! # a function handle.
%!
%! function [vyd] = frobertson (vt, vy, varargin)
%!   vyd(1,1) = -0.04 * vy(1) + 1e4 * vy(2) * vy(3);
%!   vyd(2,1) =  0.04 * vy(1) - 1e4 * vy(2) * vy(3) - 3e7 * vy(2)^2;
%!   vyd(3,1) =  vy(1) + vy(2) + vy(3) - 1;
%! endfunction
%!
%! function [vmass] = fmass (vt, vy, varargin)
%!   vmass =  [1, 0, 0; 0, 1, 0; 0, 0, 0];
%! endfunction
%!
%! vopt = odeset ('Mass', @fmass, 'NormControl', 'on');
%! vsol = ode2r (@frobertson, [0, 1e5], [1, 0, 0], vopt);
%! plot (vsol.x, vsol.y);

%!demo
%! # Solve the "Robertson problem" with a Mass function that is given by
%! # a constant mass matrix.
%!
%! function [vyd] = frobertson (vt, vy, varargin)
%!   vyd(1,1) = -0.04 * vy(1) + 1e4 * vy(2) * vy(3);
%!   vyd(2,1) =  0.04 * vy(1) - 1e4 * vy(2) * vy(3) - 3e7 * vy(2)^2;
%!   vyd(3,1) =  vy(1) + vy(2) + vy(3) - 1;
%! endfunction
%!
%! vopt = odeset ('Mass', [1, 0, 0; 0, 1, 0; 0, 0, 0], ...
%!                'NormControl', 'on');
%! vsol = ode5r (@frobertson, [0, 1e5], [1, 0, 0], vopt);
%! plot (vsol.x, vsol.y);

%# Local Variables: ***
%# mode: octave ***
%# End: ***
