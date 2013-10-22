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
%# @deftypefn {Function File} {[@var{solution}] =} odepkg_testsuite_oregonator (@var{@@solver}, @var{reltol})
%#
%# If this function is called with two input arguments and the first input argument @var{@@solver} is a function handle describing an OdePkg solver and the second input argument @var{reltol} is a double scalar describing the relative error tolerance then return a cell array @var{solution} with performance informations about the OREGONATOR testsuite of ordinary differential equations after solving (ODE--test).
%#
%# Run examples with the command
%# @example
%# demo odepkg_testsuite_oregonator
%# @end example
%#
%# This function has been ported from the "Test Set for IVP solvers" which is developed by the INdAM Bari unit project group "Codes and Test Problems for Differential Equations", coordinator F. Mazzia.
%# @end deftypefn
%#
%# @seealso{odepkg}

function vret = odepkg_testsuite_oregonator (vhandle, vrtol)

  if (nargin ~= 2) %# Check number and types of all input arguments
    help  ('odepkg_testsuite_oregonator');
    error ('OdePkg:InvalidArgument', ...
      'Number of input arguments must be exactly two');
  elseif (~isa (vhandle, 'function_handle') || ~isscalar (vrtol))
    usage ('odepkg_testsuite_oregonator (@solver, reltol)');
  end

  vret{1} = vhandle; %# The name for the solver that is used
  vret{2} = vrtol;   %# The value for the realtive tolerance
  vret{3} = vret{2}; %# The value for the absolute tolerance
  vret{4} = vret{2} * 10^(-2);  %# The value for the first time step
  %# Write a debug message on the screen, because this testsuite function
  %# may be called more than once from a loop over all solvers present
  fprintf (1, ['Testsuite OREGONATOR, testing solver %7s with relative', ...
    ' tolerance %2.0e\n'], func2str (vret{1}), vrtol); fflush (1);

  %# Setting the integration algorithms option values
  vstart = 0.0;   %# The point of time when solving is started
  vstop  = 360.0; %# The point of time when solving is stoped
  vinit  = odepkg_testsuite_oregonatorinit; %# The initial values

  vopt = odeset ('Refine', 0, 'RelTol', vret{2}, 'AbsTol', vret{3}, ...
    'InitialStep', vret{4}, 'Stats', 'on', 'NormControl', 'off', ...
    'Jacobian', @odepkg_testsuite_oregonatorjac, 'MaxStep', vstop-vstart);

  %# Calculate the algorithm, start timer and do solving
  tic; vsol = feval (vhandle, @odepkg_testsuite_oregonatorfun, ...
    [vstart, vstop], vinit, vopt);
  vret{12} = toc;                        %# The value for the elapsed time
  vref = odepkg_testsuite_oregonatorref; %# Get the reference solution vector
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

%# Returns the results for the OREGONATOR problem
function f = odepkg_testsuite_oregonatorfun (t, y, varargin)
  f(1,1) = 77.27 * (y(2) + y(1) * (1.0 - 8.375e-6 * y(1) - y(2)));
  f(2,1) = (y(3) - (1.0 + y(1)) * y(2)) / 77.27;
  f(3,1) = 0.161 * (y(1) - y(3));

%# Returns the INITIAL values for the OREGONATOR problem
function vinit = odepkg_testsuite_oregonatorinit ()
  vinit = [1, 2, 3];

%# Returns the JACOBIAN matrix for the OREGONATOR problem
function dfdy = odepkg_testsuite_oregonatorjac (t, y, varargin)
  dfdy(1,1) =  77.27 * (1.0 - 2.0 * 8.375e-6 * y(1) - y(2));
  dfdy(1,2) =  77.27 * (1.0 - y(1));
  dfdy(1,3) =  0.0;
  dfdy(2,1) = -y(2) / 77.27;
  dfdy(2,2) = -(1.0 + y(1)) / 77.27;
  dfdy(2,3) =  1.0 / 77.27;
  dfdy(3,1) =  0.161;
  dfdy(3,2) =  0.0;
  dfdy(3,3) = -0.161;

%# Returns the REFERENCE values for the OREGONATOR problem
function y = odepkg_testsuite_oregonatorref ()
  y(1,1) = 0.10008148703185e+1;
  y(1,2) = 0.12281785215499e+4;
  y(1,3) = 0.13205549428467e+3;

%!demo
%! %% vsolver = {@ode23, @ode45, @ode54, @ode78, ...
%! %%   @odebda, @oders, @ode2r, @ode5r, @odesx};
%! vsolver = {@odebda, @oders, @ode2r, @ode5r, @odesx};
%! for vcnt=1:length (vsolver)
%!   voreg{vcnt,1} = odepkg_testsuite_oregonator (vsolver{vcnt}, 1e-7);
%! end
%! voreg

%# Local Variables: ***
%# mode: octave ***
%# End: ***
