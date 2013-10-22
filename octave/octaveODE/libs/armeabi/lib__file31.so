%# Copyright (C) 2007-2012, Thomas Treichl <treichl@users.sourceforge.net>
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
%# @deftypefn {Function File} {[@var{solution}] =} odepkg_testsuite_chemakzo (@var{@@solver}, @var{reltol})
%#
%# If this function is called with two input arguments and the first input argument @var{@@solver} is a function handle describing an OdePkg solver and the second input argument @var{reltol} is a double scalar describing the relative error tolerance then return a cell array @var{solution} with performance informations about the chemical AKZO Nobel testsuite of differential algebraic equations after solving (DAE--test).
%#
%# Run examples with the command
%# @example
%# demo odepkg_testsuite_chemakzo
%# @end example
%#
%# This function has been ported from the "Test Set for IVP solvers" which is developed by the INdAM Bari unit project group "Codes and Test Problems for Differential Equations", coordinator F. Mazzia.
%# @end deftypefn
%#
%# @seealso{odepkg}

function vret = odepkg_testsuite_chemakzo (vhandle, vrtol)

  if (nargin ~= 2) %# Check number and types of all input arguments
    help  ('odepkg_testsuite_chemakzo');
    error ('OdePkg:InvalidArgument', ...
           'Number of input arguments must be exactly two');
  elseif (~isa (vhandle, 'function_handle') || ~isscalar (vrtol))
    print_usage;
  end

  vret{1} = vhandle; %# The handle for the solver that is used
  vret{2} = vrtol;   %# The value for the realtive tolerance
  vret{3} = vret{2}; %# The value for the absolute tolerance
  vret{4} = vret{2}; %# The value for the first time step
  %# Write a debug message on the screen, because this testsuite function
  %# may be called more than once from a loop over all solvers present
  fprintf (1, ['Testsuite AKZO, testing solver %7s with relative', ...
    ' tolerance %2.0e\n'], func2str (vret{1}), vrtol); fflush (1);

  %# Setting the integration algorithms option values
  vstart = 0.0;   %# The point of time when solving is started
  vstop  = 180.0; %# The point of time when solving is stoped
  vinit  = odepkg_testsuite_chemakzoinit; %# The initial values
  vmass  = odepkg_testsuite_chemakzomass; %# The mass matrix

  vopt = odeset ('Refine', 0, 'RelTol', vret{2}, 'AbsTol', vret{3}, ...
    'InitialStep', vret{4}, 'Stats', 'on', 'NormControl', 'off', ...
    'Jacobian', @odepkg_testsuite_chemakzojac, 'Mass', vmass, ...
    'MaxStep', vstop-vstart); %# , 'OutputFcn', @odeplot);

  %# Calculate the algorithm, start timer and do solving
  tic; vsol = feval (vhandle, @odepkg_testsuite_chemakzofun, ...
    [vstart, vstop], vinit, vopt);
  vret{12} = toc;                      %# The value for the elapsed time
  vref = odepkg_testsuite_chemakzoref; %# Get the reference solution vector
  if (max (size (vsol.y(end,:))) == max (size (vref))), vlst = vsol.y(end,:);
  elseif (max (size (vsol.y(:,end))) == max (size (vref))), vlst = vsol.y(:,end);
  end
  vret{5}  = odepkg_testsuite_calcmescd (vlst, vref, vret{3}, vret{2});
  vret{6}  = odepkg_testsuite_calcscd (vlst, vref, vret{3}, vret{2});
  vret{7}  = vsol.stats.nsteps + vsol.stats.nfailed; %# The value for all evals
  vret{8}  = vsol.stats.nsteps;   %# The value for success evals
  vret{9}  = vsol.stats.nfevals;  %# The value for fun calls
  vret{10} = vsol.stats.npds;     %# The value for partial derivations
  vret{11} = vsol.stats.ndecomps; %# The value for LU decompositions

%# Returns the results for the for the chemical AKZO problem
function f = odepkg_testsuite_chemakzofun (t, y, varargin)
  k1   = 18.7; k2  = 0.58; k3 = 0.09;   k4  = 0.42;
  kbig = 34.4; kla = 3.3;  ks = 115.83; po2 = 0.9;
  hen  = 737;

%# if (y(2) <= 0)
%#   error ('odepkg_testsuite_chemakzojac: Second input argument is negative');
%#  end

  r1  = k1 * y(1)^4 * sqrt (y(2));
  r2  = k2 * y(3) * y(4);
  r3  = k2 / kbig * y(1) * y(5);
  r4  = k3 * y(1) * y(4)^2;
  r5  = k4 * y(6)^2 * sqrt (y(2));
  fin = kla * (po2 / hen - y(2));

  f(1,1) = -2 * r1 + r2 - r3 - r4;
  f(2,1) = -0.5 * r1 - r4 - 0.5 * r5 + fin;
  f(3,1) = r1 - r2 + r3;
  f(4,1) = - r2 + r3 - 2 * r4;
  f(5,1) = r2 - r3 + r5;
  f(6,1) = ks * y(1) * y(4) - y(6);

%# Returns the INITIAL values for the chemical AKZO problem
function vinit = odepkg_testsuite_chemakzoinit ()
  vinit = [0.444, 0.00123, 0, 0.007, 0, 115.83 * 0.444 * 0.007];

%# Returns the JACOBIAN matrix for the chemical AKZO problem
function dfdy = odepkg_testsuite_chemakzojac (t, y, varargin)
  k1   = 18.7; k2  = 0.58; k3 = 0.09;   k4  = 0.42;
  kbig = 34.4; kla = 3.3;  ks = 115.83; po2 = 0.9;
  hen  = 737;

%#   if (y(2) <= 0)
%#     error ('odepkg_testsuite_chemakzojac: Second input argument is negative');
%#   end
  dfdy = zeros (6, 6);

  r11  = 4 * k1 * y(1)^3 * sqrt (y(2));
  r12  = 0.5 * k1 * y(1)^4 / sqrt (y(2));
  r23  = k2 * y(4);
  r24  = k2 * y(3);
  r31  = (k2 / kbig) * y(5);
  r35  = (k2 / kbig) * y(1);
  r41  = k3 * y(4)^2;
  r44  = 2 * k3 * y(1) * y(4);
  r52  = 0.5 * k4 * y(6)^2 / sqrt (y(2));
  r56  = 2 * k4 * y(6) * sqrt (y(2));
  fin2 = -kla;

  dfdy(1,1) = -2 * r11 - r31 - r41;
  dfdy(1,2) = -2 * r12;
  dfdy(1,3) = r23;
  dfdy(1,4) = r24 - r44;
  dfdy(1,5) = -r35;
  dfdy(2,1) = -0.5 * r11 - r41;
  dfdy(2,2) = -0.5 * r12 - 0.5 * r52 + fin2;
  dfdy(2,4) = -r44;
  dfdy(2,6) = -0.5 * r56;
  dfdy(3,1) = r11 + r31;
  dfdy(3,2) = r12;
  dfdy(3,3) = -r23;
  dfdy(3,4) = -r24;
  dfdy(3,5) = r35;
  dfdy(4,1) = r31 - 2 * r41;
  dfdy(4,3) = -r23;
  dfdy(4,4) = -r24 - 2 * r44;
  dfdy(4,5) = r35;
  dfdy(5,1) = -r31;
  dfdy(5,2) = r52;
  dfdy(5,3) = r23;
  dfdy(5,4) = r24;
  dfdy(5,5) = -r35;
  dfdy(5,6) = r56;
  dfdy(6,1) = ks * y(4);
  dfdy(6,4) = ks * y(1);
  dfdy(6,6) = -1;

%# Returns the MASS matrix for the chemical AKZO problem
function mass = odepkg_testsuite_chemakzomass (t, y, varargin)
  mass =  [ 1, 0, 0, 0, 0, 0;
            0, 1, 0, 0, 0, 0;
            0, 0, 1, 0, 0, 0;
            0, 0, 0, 1, 0, 0;
            0, 0, 0, 0, 1, 0;
            0, 0, 0, 0, 0, 0 ];

%# Returns the REFERENCE values for the chemical AKZO problem
function y = odepkg_testsuite_chemakzoref ()
  y(1,1) = 0.11507949206617e+0;
  y(2,1) = 0.12038314715677e-2;
  y(3,1) = 0.16115628874079e+0;
  y(4,1) = 0.36561564212492e-3;
  y(5,1) = 0.17080108852644e-1;
  y(6,1) = 0.48735313103074e-2;

%!demo
%! vsolver = {@odebda, @oders, @ode2r, @ode5r, @odesx};
%! for vcnt=1:length (vsolver)
%!   vakzo{vcnt,1} = odepkg_testsuite_chemakzo (vsolver{vcnt}, 1e-7);
%! end
%! vakzo

%# Local Variables: ***
%# mode: octave ***
%# End: ***
