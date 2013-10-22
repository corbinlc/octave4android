%# Copyright (C) 2006-2012, Thomas Treichl <treichl@users.sourceforge.net>
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
%# @deftypefn {Function File} {[@var{ret}] =} odephas2 (@var{t}, @var{y}, @var{flag})
%#
%# Open a new figure window and plot the first result from the variable @var{y} that is of type double column vector over the second result from the variable @var{y} while solving. The types and the values of the input parameter @var{t} and the output parameter @var{ret} depend on the input value @var{flag} that is of type string. If @var{flag} is
%# @table @option
%# @item  @code{"init"}
%# then @var{t} must be a double column vector of length 2 with the first and the last time step and nothing is returned from this function,
%# @item  @code{""}
%# then @var{t} must be a double scalar specifying the actual time step and the return value is false (resp. value 0) for 'not stop solving',
%# @item  @code{"done"}
%# then @var{t} must be a double scalar specifying the last time step and nothing is returned from this function.
%# @end table
%#
%# This function is called by a OdePkg solver function if it was specified in an OdePkg options structure with the @command{odeset}. This function is an OdePkg internal helper function therefore it should never be necessary that this function is called directly by a user. There is only little error detection implemented in this function file to achieve the highest performance.
%#
%# For example, solve an anonymous implementation of the "Van der Pol" equation and display the results while solving in a 2D plane
%# @example
%# fvdb = @@(vt,vy) [vy(2); (1 - vy(1)^2) * vy(2) - vy(1)];
%# 
%# vopt = odeset ('OutputFcn', @@odephas2, 'RelTol', 1e-6);
%# vsol = ode45 (fvdb, [0 20], [2 0], vopt);
%# @end example
%# @end deftypefn
%#
%# @seealso{odepkg}

function [varargout] = odephas2 (vt, vy, vflag)

  %# No input argument check is done for a higher processing speed
  persistent vfigure; persistent vyold; persistent vcounter;

  if (strcmp (vflag, 'init'))
    %# Nothing to return, vt is either the time slot [tstart tstop]
    %# or [t0, t1, ..., tn], vy is the inital value vector 'vinit'
    vfigure = figure; vyold = vy(:,1); vcounter = 1;

  elseif (isempty (vflag))
    %# Return something in varargout{1}, either false for 'not stopping
    %# the integration' or true for 'stopping the integration'
    vcounter = vcounter + 1; figure (vfigure);
    vyold(:,vcounter) = vy(:,1); 
    plot (vyold(1,:), vyold(2,:), '-o', 'markersize', 1);
    drawnow; varargout{1} = false; 

  elseif (strcmp (vflag, 'done'))
    %# Cleanup has to be done, clear the persistent variables because
    %# we don't need them anymore
    clear ('vfigure', 'vyold', 'vcounter');

  end

%# Local Variables: ***
%# mode: octave ***
%# End: ***
