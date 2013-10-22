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
%# @deftypefn {Function File} {[@var{}] =} odepkg_examples_ide (@var{})
%# Open the IDE examples menu and allow the user to select a demo that will be evaluated.
%# @end deftypefn

function [] = odepkg_examples_ide ()

  vode = 1; while (vode > 0)
    clc;
    fprintf (1, ...
      ['IDE examples menu:\n', ...
       '==================\n', ...
       '\n', ...
       '   (1) Solve the "Robertson problem" with solver "odebdi"\n', ...
       '   (2) Solve another "Robertson implementation" with solver "odekdi"\n', ...
       '   (3) Solve a stiff "Van der Pol" implementation with solver "odebdi"\n', ...
       '   (4) Solve a stiff "Van der Pol" implementation with solver "odekdi"\n', ...
       '\n', ...
       '   Note: There are further IDE examples available with the OdePkg\n', ...
       '         testsuite functions.\n', ...
       '\n', ...
       '   If you have another interesting IDE example that you would like\n', ...
       '   to share then please modify this file, create a patch and send\n', ...
       '   your patch to the OdePkg developer team.\n', ...
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
%! # Solve the "Robertson problem" that is given as a function handle
%! # to an implicite differential equation implementation.
%!
%! function [vres] = firobertson (vt, vy, vyd, varargin)
%!   vres(1,1) = -0.04*vy(1) + 1e4*vy(2)*vy(3) - vyd(1);
%!   vres(2,1) =  0.04*vy(1) - 1e4*vy(2)*vy(3) - 3e7*vy(2)^2-vyd(2);
%!   vres(3,1) =  vy(1) + vy(2) + vy(3) - 1;
%! endfunction
%!
%! vopt = odeset ("NormControl", "on");
%! vsol = odebdi (@firobertson, [0, 1e5], [1, 0, 0], [-4e-2, 4e-2, 0], vopt);
%! plot (vsol.x, vsol.y);

%!demo
%! # Solve the "Robertson problem" that is given as a function handle
%! # to an implicite differential equation implementation.
%!
%! function [vres] = firobertson (vt, vy, vyd, varargin)
%!   vres(1,1) = -0.04*vy(1) + 1e4*vy(2)*vy(3) - vyd(1);
%!   vres(2,1) =  0.04*vy(1) - 1e4*vy(2)*vy(3) - 3e7*vy(2)^2-vyd(2);
%!   vres(3,1) =  vy(1) + vy(2) + vy(3) - 1;
%! endfunction
%!
%! vopt = odeset ("NormControl", "on");
%! vsol = odekdi (@firobertson, [0, 1e5], [1, 0, 0], [-4e-2, 4e-2, 0], vopt);
%! plot (vsol.x, vsol.y);

%!demo
%! # Solve the "Van der Pol" problem that is given as a function
%! # handle to an implicite differential equation implementation.
%!
%! function [vres] = fvanderpol (vt, vy, vyd, varargin)
%!   mu = varargin{1};
%!   vres = [vy(2) - vyd(1); 
%!           mu * (1 - vy(1)^2) * vy(2) - vy(1) - vyd(2)];
%! endfunction
%!
%! vopt = odeset ("NormControl", "on", "RelTol", 1e-8, "MaxStep", 1e-2);
%! vsol = odebdi (@fvanderpol, [0, 20], [2; 0], [0; -2], vopt, 10);
%! plot (vsol.x, vsol.y);

%!demo
%! # Solve the "Van der Pol" problem that is given as a function
%! # handle to an implicite differential equation implementation.
%!
%! function [vres] = fvanderpol (vt, vy, vyd, varargin)
%!   mu = varargin{1};
%!   vres = [vy(2) - vyd(1); 
%!           mu * (1 - vy(1)^2) * vy(2) - vy(1) - vyd(2)];
%! endfunction
%!
%! vsol = odekdi (@fvanderpol, [0, 1000], [2; 0], [0; -2], 500);
%! plot (vsol.x, vsol.y);

%# Local Variables: ***
%# mode: octave ***
%# End: ***
