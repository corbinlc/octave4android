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
%# @deftypefn {Function File} {[@var{solution}] =} odepkg_testsuite_hires (@var{@@solver}, @var{reltol})
%#
%# If this function is called with two input arguments and the first input argument @var{@@solver} is a function handle describing an OdePkg solver and the second input argument @var{reltol} is a double scalar describing the relative error tolerance then return a cell array @var{solution} with performance informations about the HIRES testsuite of ordinary differential equations after solving (ODE--test).
%#
%# Run examples with the command
%# @example
%# demo odepkg_testsuite_hires
%# @end example
%#
%# This function has been ported from the "Test Set for IVP solvers" which is developed by the INdAM Bari unit project group "Codes and Test Problems for Differential Equations", coordinator F. Mazzia.
%# @end deftypefn
%#
%# @seealso{odepkg}

function vret = odepkg_testsuite_hires (vhandle, vrtol)

  if (nargin ~= 2) %# Check number and types of all input arguments
    help  ('odepkg_testsuite_hires');
    error ('OdePkg:InvalidArgument', ...
      'Number of input arguments must be exactly two');
  elseif (~isa (vhandle, 'function_handle') || ~isscalar (vrtol))
    print_usage;
  end

  vret{1} = vhandle; %# The handle for the solver that is used
  vret{2} = vrtol;   %# The value for the realtive tolerance
  vret{3} = vret{2}; %# The value for the absolute tolerance
  vret{4} = vret{2} * 10^(-2);  %# The value for the first time step
  %# Write a debug message on the screen, because this testsuite function
  %# may be called more than once from a loop over all solvers present
  fprintf (1, ['Testsuite HIRES, testing solver %7s with relative', ...
    ' tolerance %2.0e\n'], func2str (vret{1}), vrtol); fflush (1);

  %# Setting the integration algorithms option values
  vstart = 0.0;      %# The point of time when solving is started
  vstop  = 321.8122; %# The point of time when solving is stoped
  vinit  = odepkg_testsuite_hiresinit; %# The initial values

  vopt = odeset ('Refine', 0, 'RelTol', vret{2}, 'AbsTol', vret{3}, ...
    'InitialStep', vret{4}, 'Stats', 'on', 'NormControl', 'off', ...
    'Jacobian', @odepkg_testsuite_hiresjac, 'MaxStep', vstop-vstart);

  %# Calculate the algorithm, start timer and do solving
  tic; vsol = feval (vhandle, @odepkg_testsuite_hiresfun, ...
    [vstart, vstop], vinit, vopt);
  vret{12} = toc;                   %# The value for the elapsed time
  vref = odepkg_testsuite_hiresref; %# Get the reference solution vector
  if (exist ('OCTAVE_VERSION') ~= 0)
    vlst = vsol.y(end,:);
  else
    vlst = vsol.y(:,end);
  end
  vret{5}  = odepkg_testsuite_calcmescd (vlst, vref, vret{3}, vret{2});
  vret{6}  = odepkg_testsuite_calcscd (vlst, vref, vret{3}, vret{2});
  vret{7}  = vsol.stats.nsteps + vsol.stats.nfailed; %# The value for all evals
  vret{8}  = vsol.stats.nsteps;   %# The value for success evals
  vret{9}  = vsol.stats.nfevals;  %# The value for fun calls
  vret{10} = vsol.stats.npds;     %# The value for partial derivations
  vret{11} = vsol.stats.ndecomps; %# The value for LU decompositions

%# Returns the results for the HIRES problem
function f = odepkg_testsuite_hiresfun (t, y, varargin)
  f(1,1) = -1.71 * y(1) + 0.43 * y(2) + 8.32 * y(3) + 0.0007;
  f(2,1) =  1.71 * y(1) - 8.75 * y(2);
  f(3,1) = -10.03 * y(3) + 0.43 * y(4) + 0.035 * y(5);
  f(4,1) =  8.32 * y(2) + 1.71 * y(3) - 1.12 * y(4);
  f(5,1) = -1.745 * y(5) + 0.43 * (y(6) + y(7));
  f(6,1) = -280 * y(6) * y(8) + 0.69 * y(4) + 1.71 * y(5) - 0.43 * y(6) + 0.69 * y(7);
  f(7,1) =  280 * y(6) * y(8) - 1.81 * y(7);
  f(8,1) = -f(7);

%# Returns the INITIAL values for the HIRES problem
function vinit = odepkg_testsuite_hiresinit ()
  vinit = [1, 0, 0, 0, 0, 0, 0, 0.0057];

%# Returns the JACOBIAN matrix for the HIRES problem
function dfdy = odepkg_testsuite_hiresjac (t, y, varargin)
  dfdy(1,1) = -1.71;
  dfdy(1,2) =  0.43;
  dfdy(1,3) =  8.32;
  dfdy(2,1) =  1.71;
  dfdy(2,2) = -8.75;
  dfdy(3,3) = -10.03;
  dfdy(3,4) =  0.43;
  dfdy(3,5) =  0.035;
  dfdy(4,2) =  8.32;
  dfdy(4,3) =  1.71;
  dfdy(4,4) = -1.12;
  dfdy(5,5) = -1.745;
  dfdy(5,6) =  0.43;
  dfdy(5,7) =  0.43;
  dfdy(6,4) =  0.69;
  dfdy(6,5) =  1.71;
  dfdy(6,6) = -280 * y(8) - 0.43;
  dfdy(6,7) =  0.69;
  dfdy(6,8) = -280 * y(6);
  dfdy(7,6) =  280 * y(8);
  dfdy(7,7) = -1.81;
  dfdy(7,8) =  280 * y(6);
  dfdy(8,6) = -280 * y(8);
  dfdy(8,7) =  1.81;
  dfdy(8,8) = -280 * y(6);

%# Returns the REFERENCE values for the HIRES problem
function y = odepkg_testsuite_hiresref ()
  y(1,1) = 0.73713125733256e-3;
  y(2,1) = 0.14424857263161e-3;
  y(3,1) = 0.58887297409675e-4;
  y(4,1) = 0.11756513432831e-2;
  y(5,1) = 0.23863561988313e-2;
  y(6,1) = 0.62389682527427e-2;
  y(7,1) = 0.28499983951857e-2;
  y(8,1) = 0.28500016048142e-2;

%!demo
%! vsolver = {@ode23, @ode45, @ode54, @ode78, ...
%!   @odebda, @oders, @ode2r, @ode5r, @odesx};
%! for vcnt=1:length (vsolver)
%!   vhires{vcnt,1} = odepkg_testsuite_hires (vsolver{vcnt}, 1e-7);
%! end
%! vhires

%# Local Variables: ***
%# mode: octave ***
%# End: ***
