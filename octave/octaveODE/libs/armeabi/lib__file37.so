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
%# @deftypefn {Function File} {[@var{solution}] =} odepkg_testsuite_pollution (@var{@@solver}, @var{reltol})
%#
%# If this function is called with two input arguments and the first input argument @var{@@solver} is a function handle describing an OdePkg solver and the second input argument @var{reltol} is a double scalar describing the relative error tolerance then return the cell array @var{solution} with performance informations about the POLLUTION testsuite of ordinary differential equations after solving (ODE--test).
%#
%# Run examples with the command
%# @example
%# demo odepkg_testsuite_pollution
%# @end example
%#
%# This function has been ported from the "Test Set for IVP solvers" which is developed by the INdAM Bari unit project group "Codes and Test Problems for Differential Equations", coordinator F. Mazzia.
%# @end deftypefn
%#
%# @seealso{odepkg}

function vret = odepkg_testsuite_pollution (vhandle, vrtol)

  if (nargin ~= 2) %# Check number and types of all input arguments
    help  ('odepkg_testsuite_pollution');
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
  fprintf (1, ['Testsuite POLLUTION, testing solver %7s with relative', ...
    ' tolerance %2.0e\n'], func2str (vret{1}), vrtol); fflush (1);

  %# Setting the integration algorithms option values
  vstart = 0.0;   %# The point of time when solving is started
  vstop  = 60.0;  %# The point of time when solving is stoped
  vinit  = odepkg_testsuite_pollutioninit; %# The initial values

  vopt = odeset ('Refine', 0, 'RelTol', vret{2}, 'AbsTol', vret{3}, ...
    'InitialStep', vret{4}, 'Stats', 'on', 'NormControl', 'off', ...
    'Jacobian', @odepkg_testsuite_pollutionjac, 'MaxStep', vstop-vstart);

  %# Calculate the algorithm, start timer and do solving
  tic; vsol = feval (vhandle, @odepkg_testsuite_pollutionfun, ...
    [vstart, vstop], vinit, vopt);
  vret{12} = toc;                       %# The value for the elapsed time
  vref = odepkg_testsuite_pollutionref; %# Get the reference solution vector
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

%# Returns the results for the for the POLLUTION problem
function f = odepkg_testsuite_pollutionfun (t, y, varargin)
  f(01,1) = - 0.350 * y(1) + 0.266e2 * y(2) * y(4) + 0.123e5 * y(2) * y(5) + ...
              0.165e5 * y(2) * y(11) - 0.900e4 * y(1) * y(11) + 0.220e-1 * y(13) + ...
              0.120e5 * y(2) * y(10) - 0.163e5 * y(1) * y(6) + 0.578e1 * y(19) - ...
              0.474e-1 * y(1) * y(4) - 0.178e4 * y(1) * y(19) + 0.312e1 * y(20);
  f(02,1) = + 0.350 * y(1) - 0.266e2 * y(2) * y(4) - 0.123e5 * y(2) * y(5) - ...
              0.165e5 * y(2) * y(11) - 0.120e5 * y(2) * y(10) + 0.210e1 * y(19);
  f(03,1) = + 0.350 * y(1) - 0.480e7 * y(3) + 0.175e-1 * y(4) + 0.444e12 * y(16) + 0.578e1 * y(19);
  f(04,1) = - 0.266e2 * y(2) * y(4) + 0.480e7 * y(3) - 0.350e-3 * y(4) - ...
              0.175e-1 * y(4) - 0.474e-1 * y(1) * y(4);
  f(05,1) = - 0.123e5 * y(2) * y(5) + 2*0.860e-3 * y(7) + 0.150e5 * y(6) * y(7) + ...
              0.130e-3 * y(9) + 0.188e1 * y(14) + 0.124e4 * y(6) * y(17);
  f(06,1) = + 0.123e5 * y(2) * y(5) - 0.150e5 * y(6) * y(7) - 0.240e5 * y(6) * y(9) - ...
              0.163e5 * y(1) * y(6) + 2*0.100e9 * y(16) - 0.124e4 * y(6) * y(17);
  f(07,1) = - 0.860e-3 * y(7) - 0.820e-3 * y(7) - 0.150e5 * y(6) * y(7) + 0.188e1 * y(14);
  f(08,1) = + 0.860e-3 * y(7) + 0.820e-3 * y(7) + 0.150e5 * y(6) * y(7) + 0.130e-3 * y(9);
  f(09,1) = - 0.130e-3 * y(9) - 0.240e5 * y(6) * y(9);
  f(10,1) = + 0.130e-3 * y(9) + 0.165e5 * y(2) * y(11) - 0.120e5 * y(2) * y(10);
  f(11,1) = + 0.240e5 * y(6) * y(9) - 0.165e5 * y(2) * y(11) - 0.900e4 * y(1) * y(11) + ...
              0.220e-1 * y(13);
  f(12,1) = + 0.165e5 * y(2) * y(11);
  f(13,1) = + 0.900e4 * y(1) * y(11) - 0.220e-1 * y(13);
  f(14,1) = + 0.120e5 * y(2) * y(10) - 0.188e1 * y(14);
  f(15,1) = + 0.163e5 * y(1) * y(6);
  f(16,1) = + 0.350e-3 * y(4) - 0.100e9 * y(16) - 0.444e12 * y(16);
  f(17,1) = - 0.124e4 * y(6) * y(17);
  f(18,1) = + 0.124e4 * y(6) * y(17);
  f(19,1) = - 0.210e1 * y(19) - 0.578e1 * y(19) + 0.474e-1 * y(1) * y(4) - ... 
              0.178e4 * y(1) * y(19) + 0.312e1 * y(20);
  f(20,1) = + 0.178e4 * y(1) * y(19) - 0.312e1 * y(20);

%# Returns the INITIAL values for the POLLUTION problem
function vinit = odepkg_testsuite_pollutioninit ()
  vinit = [0, 0.2, 0, 0.04, 0, 0, 0.1, 0.3, 0.01, ...
           0, 0, 0, 0, 0, 0, 0, 0.007, 0, 0, 0];

%# Returns the JACOBIAN matrix for the POLLUTION problem
function dfdy = odepkg_testsuite_pollutionjac (t, y)
  k1  = 0.35e0;   k2  = 0.266e2; k3  = 0.123e5;  k4  = 0.86e-3;
  k5  = 0.82e-3;  k6  = 0.15e5;  k7  = 0.13e-3;  k8  = 0.24e5;
  k9  = 0.165e5;  k10 = 0.9e4;   k11 = 0.22e-1;  k12 = 0.12e5;
  k13 = 0.188e1;  k14 = 0.163e5; k15 = 0.48e7;   k16 = 0.35e-3;
  k17 = 0.175e-1; k18 = 0.1e9;   k19 = 0.444e12; k20 = 0.124e4;
  k21 = 0.21e1;   k22 = 0.578e1; k23 = 0.474e-1; k24 = 0.178e4;
  k25 = 0.312e1;

  dfdy(1,1)   = -k1 - k10 * y(11) - k14 * y(6) - k23 * y(4) - k24 * y(19);
  dfdy(1,11)  = -k10 * y(1) + k9 * y(2);
  dfdy(1,6)   = -k14 * y(1);
  dfdy(1,4)   = -k23 * y(1) + k2 * y(2);
  dfdy(1,19)  = -k24 * y(1) + k22;
  dfdy(1,2)   =  k2 * y(4) + k9 * y(11) + k3 * y(5) + k12 * y(10);
  dfdy(1,13)  =  k11;
  dfdy(1,20)  =  k25;
  dfdy(1,5)   =  k3 * y(2);
  dfdy(1,10)  =  k12 * y(2);

  dfdy(2,4)   = -k2 * y(2);
  dfdy(2,5)   = -k3 * y(2);
  dfdy(2,11)  = -k9 * y(2);
  dfdy(2,10)  = -k12 * y(2);
  dfdy(2,19)  =  k21;
  dfdy(2,1)   =  k1;
  dfdy(2,2)   = -k2 * y(4) - k3 * y(5) - k9 * y(11) - k12 * y(10);

  dfdy(3,1)   =  k1;
  dfdy(3,4)   =  k17;
  dfdy(3,16)  =  k19;
  dfdy(3,19)  =  k22;
  dfdy(3,3)   = -k15;

  dfdy(4,4)   = -k2 * y(2) - k16 - k17 - k23 * y(1);
  dfdy(4,2)   = -k2 * y(4);
  dfdy(4,1)   = -k23 * y(4);
  dfdy(4,3)   =  k15;

  dfdy(5,5)   = -k3 * y(2);
  dfdy(5,2)   = -k3 * y(5);
  dfdy(5,7)   =  2d0 * k4 + k6 * y(6);
  dfdy(5,6)   =  k6 * y(7) + k20 * y(17);
  dfdy(5,9)   =  k7;
  dfdy(5,14)  =  k13;
  dfdy(5,17)  =  k20 * y(6);

  dfdy(6,6)   = -k6 * y(7) - k8 * y(9) - k14 * y(1) - k20 * y(17);
  dfdy(6,7)   = -k6 * y(6);
  dfdy(6,9)   = -k8 * y(6);
  dfdy(6,1)   = -k14 * y(6);
  dfdy(6,17)  = -k20 * y(6);
  dfdy(6,2)   =  k3 * y(5);
  dfdy(6,5)   =  k3 * y(2);
  dfdy(6,16)  =  2d0 * k18;

  dfdy(7,7)   = -k4 - k5 - k6 * y(6);
  dfdy(7,6)   = -k6 * y(7);
  dfdy(7,14)  =  k13;

  dfdy(8,7)   =  k4 + k5 + k6 * y(6);
  dfdy(8,6)   =  k6 * y(7);
  dfdy(8,9)   =  k7;

  dfdy(9,9)   = -k7 - k8 * y(6);
  dfdy(9,6)   = -k8 * y(9);

  dfdy(10,10) = -k12 * y(2);
  dfdy(10,2)  = -k12 * y(10) + k9 * y(11);
  dfdy(10,9)  =  k7;
  dfdy(10,11) =  k9 * y(2);

  dfdy(11,11) = -k9 * y(2) - k10 * y(1);
  dfdy(11,2)  = -k9 * y(11);
  dfdy(11,1)  = -k10 * y(11);
  dfdy(11,9)  =  k8 * y(6);
  dfdy(11,6)  =  k8 * y(9);
  dfdy(11,13) =  k11;

  dfdy(12,11) =  k9 * y(2);
  dfdy(12,2)  =  k9 * y(11);

  dfdy(13,13) = -k11;
  dfdy(13,11) =  k10 * y(1);
  dfdy(13,1)  =  k10 * y(11);

  dfdy(14,14) = -k13;
  dfdy(14,10) =  k12 * y(2);
  dfdy(14,2)  =  k12 * y(10);

  dfdy(15,1)  =  k14 * y(6);
  dfdy(15,6)  =  k14 * y(1);

  dfdy(16,16) = -k18 - k19;
  dfdy(16,4)  =  k16;

  dfdy(17,17) = -k20 * y(6);
  dfdy(17,6)  = -k20 * y(17);

  dfdy(18,17) =  k20 * y(6);
  dfdy(18,6)  =  k20 * y(17);

  dfdy(19,19) = -k21 - k22 - k24 * y(1);
  dfdy(19,1)  = -k24 * y(19) + k23 * y(4);
  dfdy(19,4)  =  k23 * y(1);
  dfdy(19,20) =  k25;

  dfdy(20,20) = -k25;
  dfdy(20,1)  =  k24 * y(19);
  dfdy(20,19) =  k24 * y(1);

%# Returns the REFERENCE values for the POLLUTION problem
function y = odepkg_testsuite_pollutionref ()
  y(01,1) = 0.56462554800227 * 10^(-1);
  y(02,1) = 0.13424841304223 * 10^(+0);
  y(03,1) = 0.41397343310994 * 10^(-8);
  y(04,1) = 0.55231402074843 * 10^(-2);
  y(05,1) = 0.20189772623021 * 10^(-6);
  y(06,1) = 0.20189772623021 * 10^(-6);
  y(07,1) = 0.77842491189979 * 10^(-1);
  y(08,1) = 0.77842491189979 * 10^(+0);
  y(09,1) = 0.74940133838804 * 10^(-2);
  y(10,1) = 0.16222931573015 * 10^(-7);
  y(11,1) = 0.11358638332570 * 10^(-7);
  y(12,1) = 0.22305059757213 * 10^(-2);
  y(13,1) = 0.20871628827986 * 10^(-3);
  y(14,1) = 0.13969210168401 * 10^(-4);
  y(15,1) = 0.89648848568982 * 10^(-2);
  y(16,1) = 0.43528463693301 * 10^(-17);
  y(17,1) = 0.68992196962634 * 10^(-2);
  y(18,1) = 0.10078030373659 * 10^(-3);
  y(19,1) = 0.17721465139699 * 10^(-5);
  y(20,1) = 0.56829432923163 * 10^(-4);

%!demo
%! %% vsolver = {@ode23, @ode45, @ode54, @ode78, ...
%! %%   @odebda, @oders, @ode2r, @ode5r, @odesx};
%! vsolver = {@odebda, @oders, @ode2r, @ode5r, @odesx};
%! for vcnt=1:length (vsolver)
%!   poll{vcnt,1} = odepkg_testsuite_pollution (vsolver{vcnt}, 1e-7);
%! end
%! poll

%# Local Variables: ***
%# mode: octave ***
%# End: ***
