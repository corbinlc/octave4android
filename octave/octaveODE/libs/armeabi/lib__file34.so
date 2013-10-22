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
%# @deftypefn {Function File} {[@var{solution}] =} odepkg_testsuite_implrober (@var{@@solver}, @var{reltol})
%#
%# If this function is called with two input arguments and the first input argument @var{@@solver} is a function handle describing an OdePkg solver and the second input argument @var{reltol} is a double scalar describing the relative error tolerance then return a cell array @var{solution} with performance informations about the implicit form of the modified ROBERTSON testsuite of implicit differential algebraic equations after solving (IDE--test).
%#
%# Run examples with the command
%# @example
%# demo odepkg_testsuite_implrober
%# @end example
%#
%# This function has been ported from the "Test Set for IVP solvers" which is developed by the INdAM Bari unit project group "Codes and Test Problems for Differential Equations", coordinator F. Mazzia.
%# @end deftypefn
%#
%# @seealso{odepkg}

function vret = odepkg_testsuite_implrober (vhandle, vrtol)

  %# Check number and types of all input arguments
  if (nargin ~= 2)
    help  ('odepkg_testsuite_implrober');
    error ('OdePkg:InvalidArgument', ...
           'Number of input arguments must be exactly two');
  elseif (~isa (vhandle, 'function_handle') || ~isscalar (vrtol))
    print_usage;
  end

  vret{1} = vhandle; %# The handle for the solver that is used
  vret{2} = vrtol;   %# The value for the realtive tolerance
  vret{3} = vret{2} * 1e-2; %# The value for the absolute tolerance
  vret{4} = vret{2}; %# The value for the first time step
  %# Write a debug message on the screen, because this testsuite function
  %# may be called more than once from a loop over all present solvers
  fprintf (1, ['Testsuite implicit ROBERTSON, testing solver %7s with relative', ...
     ' tolerance %2.0e\n'], func2str (vret{1}), vrtol); fflush (1);

  %# Setting the integration algorithm options
  vstart = 0;    %# The point of time when solving is started
  vstop  = 1e11; %# The point of time when solving is stoped
  [vinity, vinityd] = odepkg_testsuite_implroberinit; %# The initial values

  vopt = odeset ('Refine', 0, 'RelTol', vret{2}, 'AbsTol', vret{3}, ...
    'InitialStep', vret{4}, 'Stats', 'on', 'NormControl', 'off', ...
    'Jacobian', @odepkg_testsuite_implroberjac, 'MaxStep', vstop-vstart);
    %# 'OutputFcn', @odeplot);

  %# Calculate the algorithm, start timer and do solving
  tic; vsol = feval (vhandle, @odepkg_testsuite_implroberfun, ...
    [vstart, vstop], vinity, vinityd', vopt);
  vret{12} = toc;                       %# The value for the elapsed time
  vref = odepkg_testsuite_implroberref; %# Get the reference solution vector
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

%#function odepkg_testsuite_implrober ()
%#  A = odeset ('RelTol', 1e-4, ... %# proprietary ode15i needs 1e-6 to be stable
%#             'AbsTol', [1e-6, 1e-10, 1e-6], ...
%#             'Jacobian', @odepkg_testsuite_implroberjac);
%#  [y0, yd0] = odepkg_testsuite_implroberinit;
%#  odebdi (@odepkg_testsuite_implroberfun, [0, 1e11], y0, yd0', A)
%#  [y0, yd0] = odepkg_testsuite_implroberinit;
%#  odebdi (@odepkg_testsuite_implroberfun, [0, 1e11], y0, yd0')

%# Return the results for the for the implicit ROBERTSON problem
function res = odepkg_testsuite_implroberfun (t, y, yd, varargin)
  res(1,1) = -0.04 * y(1) + 1e4 * y(2) * y(3) - yd(1);
  res(2,1) =  0.04 * y(1) - 1e4 * y(2) * y(3) - 3e7 * y(2)^2 - yd(2);
  res(3,1) =  y(1) + y(2) + y(3) - 1;

%# Return the INITIAL values for the implicit ROBERTSON problem
function [y0, yd0] = odepkg_testsuite_implroberinit ()
  y0 = [1, 0, 0];
  yd0 = [-4e-2, 4e-2, 0];

%# Return the JACOBIAN matrix for the implicit ROBERTSON problem
function [dfdy, dfdyd] = odepkg_testsuite_implroberjac (t, y, yd, varargin)
  dfdy(1,1)  = -0.04;
  dfdy(1,2)  =  1e4 * y(3);
  dfdy(1,3)  =  1e4 * y(2);
  dfdy(2,1)  =  0.04;
  dfdy(2,2)  = -1e4 * y(3) - 6e7 * y(2);
  dfdy(2,3)  = -1e4 * y(2);
  dfdy(3,1)  =  1;
  dfdy(3,2)  =  1;
  dfdy(3,3)  =  1;

  dfdyd(1,1) = -1;
  dfdyd(2,2) = -1;
  dfdyd(3,3) =  0;

%# For the implicit form of the Robertson problem a mass matrix is not
%# allowed. This mass matrix is only needed if the Robertson problem
%# is formulated in explicit form (cf. odepkg_testsuite_implrober.m).
%# function mass = odepkg_testsuite_implrobermass (t, y, varargin)
%#   mass =  [1, 0, 0; 0, 1, 0; 0, 0, 0];

%# Return the REFERENCE values for the implicit ROBERTSON problem
function y = odepkg_testsuite_implroberref ()
  y(1) =  0.20833401497012e-07;
  y(2) =  0.83333607703347e-13;
  y(3) =  0.99999997916650e+00;

%!demo
%! vsolver = {@odebdi};
%! for vcnt=1:length (vsolver)
%!   virob{vcnt,1} = odepkg_testsuite_implrober (vsolver{vcnt}, 1e-7);
%! end
%! virob

%# Local Variables: ***
%# mode: octave ***
%# End: ***
