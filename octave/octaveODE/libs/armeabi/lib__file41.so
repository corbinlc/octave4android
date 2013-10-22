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
%# @deftypefn {Function File} {[@var{ret}] =} odeprint (@var{t}, @var{y}, @var{flag})
%#
%# Display the results of the set of differential equations in the Octave window while solving. The first column of the screen output shows the actual time stamp that is given with the input arguemtn @var{t}, the following columns show the results from the function evaluation that are given by the column vector @var{y}. The types and the values of the input parameter @var{t} and the output parameter @var{ret} depend on the input value @var{flag} that is of type string. If @var{flag} is
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
%# For example, solve an anonymous implementation of the "Van der Pol" equation and print the results while solving
%# @example
%# fvdb = @@(vt,vy) [vy(2); (1 - vy(1)^2) * vy(2) - vy(1)];
%# 
%# vopt = odeset ('OutputFcn', @@odeprint, 'RelTol', 1e-6);
%# vsol = ode45 (fvdb, [0 20], [2 0], vopt);
%# @end example
%# @end deftypefn
%#
%# @seealso{odepkg}

function [varargout] = odeprint (vt, vy, vflag, varargin)

  %# No input argument check is done for a higher processing speed
  %# vt and vy are always column vectors, see also function odeplot,
  %# odephas2 and odephas3 for another implementation. vflag either
  %# is "init", [] or "done".

  if (strcmp (vflag, 'init'))
    fprintf (1, '%f%s\n', vt (1,1), sprintf (' %f', vy) );
    fflush (1);

  elseif (isempty (vflag)) %# Return value varargout{1} needed
    fprintf (1, '%f%s\n', vt (1,1), sprintf (' %f', vy) );
    fflush (1); varargout{1} = false; 

  elseif (strcmp (vflag, 'done')) 
    %# Cleanup could be done, but nothing to do in this function

  end

%# Local Variables: ***
%# mode: octave ***
%# End: ***
