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
%# along with this program; if not, write to the Free Software
%# Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA

%# -*- texinfo -*-
%# @deftypefn {Function File} {[@var{}] =} odeexamples (@var{})
%# Open the differential equations examples menu and allow the user to select a submenu of ODE, DAE, IDE or DDE examples.
%# @end deftypefn

function [] = odeexamples (varargin)

  vfam = 1; while (vfam > 0)
    clc;
    fprintf (1, ...
      ['OdePkg examples main menu:\n', ...
       '==========================\n', ...
       '\n', ...
       '   (1) Open the ODE examples menu\n', ...
       '   (2) Open the DAE examples menu\n', ...
       '   (3) Open the IDE examples menu\n', ...
       '   (4) Open the DDE examples menu\n', ...
       '\n']);
    vfam = input ('Please choose a number from above or press <Enter> to return: ');
    switch (vfam)
      case 1
        odepkg_examples_ode;
      case 2
        odepkg_examples_dae;
      case 3
        odepkg_examples_ide;
      case 4
        odepkg_examples_dde;
      otherwise
        %# have nothing to do
    end
  end

%# Local Variables: ***
%# mode: octave ***
%# End: ***
