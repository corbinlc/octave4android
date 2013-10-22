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
%# @deftypefn {Function File} {[@var{}] =} odepkg ()
%#
%# OdePkg is part of the GNU Octave Repository (the Octave--Forge project). The package includes commands for setting up various options, output functions etc. before solving a set of differential equations with the solver functions that are also included. At this time OdePkg is under development with the main target to make a package that is mostly compatible to proprietary solver products.
%#
%# If this function is called without any input argument then open the OdePkg tutorial in the Octave window. The tutorial can also be opened with the following command
%#
%# @example
%# doc odepkg
%# @end example
%# @end deftypefn

function [] = odepkg (vstr)

  %# Check number and types of all input arguments
  if (nargin == 0)
    doc ('odepkg');
  elseif (nargin > 1)
    error ('Number of input arguments must be zero or one');
  elseif (ischar (vstr))
    feval (vstr);
  else
    error ('Input argument must be a valid string');
  end

function [] = odepkg_validate_mfiles ()

  %# From command line in the 'inst' directory do something like this: 
  %#   octave --quiet --eval "odepkg ('odepkg_validate_mfiles')"

  vfun = {'ode23', 'ode45', 'ode54', 'ode78', ...
          'ode23d', 'ode45d', 'ode54d', 'ode78d', ...
          'odeget', 'odeset', 'odeplot', 'odephas2', 'odephas3', 'odeprint', ...
          'odepkg_structure_check', 'odepkg_event_handle', ...
          'odepkg_testsuite_calcscd', 'odepkg_testsuite_calcmescd'};
  %# vfun = sort (vfun);

  for vcnt=1:length(vfun)
    printf ('Testing function %s ... ', vfun{vcnt});
    test (vfun{vcnt}, 'quiet'); fflush (1);
  end

function [] = odepkg_validate_ccfiles ()

  %# From command line in the 'src' directory do something like this: 
  %#   octave --quiet --eval "odepkg ('odepkg_validate_ccfiles')"

  vfile = {'odepkg_octsolver_mebdfdae.cc', 'odepkg_octsolver_mebdfi.cc', ... 
           'odepkg_octsolver_ddaskr.cc', ...
           'odepkg_octsolver_radau.cc', 'odepkg_octsolver_radau5.cc', ...
           'odepkg_octsolver_rodas.cc', 'odepkg_octsolver_seulex.cc'};
  vsolv = {'odebda', 'odebdi', 'odekdi', ...
           'ode2r', 'ode5r', 'oders', 'odesx'};
  %# vfile = {'odepkg_octsolver_ddaskr.cc'};
  %# vsolv = {'odekdi'};

  for vcnt=1:length(vfile)
    printf ('Testing function %s ... ', vsolv{vcnt});
    autoload (vsolv{vcnt}, which ('dldsolver.oct'));
    test (vfile{vcnt}, 'quiet'); fflush (1);
  end

function [] = odepkg_internal_mhelpextract ()

  %# In the inst directory do
  %#   octave --quiet --eval "odepkg ('odepkg_internal_mhelpextract')"

  vfun = {'odepkg', 'odeget', 'odeset', ...
          'ode23', 'ode45', 'ode54', 'ode78', ...
          'ode23d', 'ode45d', 'ode54d', 'ode78d', ...
          'odebwe', ...
          'odeplot', 'odephas2', 'odephas3', 'odeprint', ...
          'odepkg_structure_check', 'odepkg_event_handle', ...
          'odepkg_testsuite_calcscd', 'odepkg_testsuite_calcmescd', ...
          'odepkg_testsuite_oregonator', 'odepkg_testsuite_pollution', ...
          'odepkg_testsuite_hires', ...
          'odepkg_testsuite_robertson', 'odepkg_testsuite_chemakzo', ...
          'odepkg_testsuite_transistor', ...
          'odepkg_testsuite_implrober', 'odepkg_testsuite_implakzo', ...
          'odepkg_testsuite_imptrans', ...
          'odeexamples', 'odepkg_examples_ode', 'odepkg_examples_dae', ...
          'odepkg_examples_ide', 'odepkg_examples_dde'};
  vfun = sort (vfun);

  [vout, vmsg] = fopen ('../doc/mfunref.texi', 'w');
  if ~(isempty (vmsg)), error (vmsg); end
  for vcnt = 1:length (vfun)
    if (exist (vfun{vcnt}, 'file'))
      [vfid, vmsg] = fopen (which (vfun{vcnt}), 'r');
      if ~(isempty (vmsg)), error (vmsg); end
      while (true)
        vlin = fgets (vfid);
        if ~(ischar (vlin)), break; end
        if (regexp (vlin, '^(%# -\*- texinfo -\*-)'))
          while (~isempty (regexp (vlin, '^(%#)')) && ...
                  isempty (regexp (vlin, '^(%# @end deftypefn)')))
            vlin = fgets (vfid);
            if (length (vlin) > 3), fprintf (vout, '%s', vlin(4:end));
            else fprintf (vout, '%s', vlin(3:end));
            end
          end
          fprintf (vout, '\n');
        end
      end
      fclose (vfid);
    end
  end
  fclose (vout);

function [] = odepkg_internal_octhelpextract ()

  %# In the src directory do
  %#   octave --quiet --eval "odepkg ('odepkg_internal_octhelpextract')"

  vfiles = {'../src/odepkg_octsolver_mebdfdae.cc', ...
            '../src/odepkg_octsolver_mebdfi.cc', ...
            '../src/odepkg_octsolver_ddaskr.cc', ...
            '../src/odepkg_octsolver_radau.cc', ...
            '../src/odepkg_octsolver_radau5.cc', ...
            '../src/odepkg_octsolver_rodas.cc', ...
            '../src/odepkg_octsolver_seulex.cc', ...
           };
  %# vfiles = sort (vfiles); Don't sort these files

  [vout, vmsg] = fopen ('../doc/dldfunref.texi', 'w');
  if ~(isempty (vmsg)), error (vmsg); end
  for vcnt = 1:length (vfiles)
    if (exist (vfiles{vcnt}, 'file'))
      [vfid, vmsg] = fopen (vfiles{vcnt}, 'r');
      if ~(isempty (vmsg)), error (vmsg); end
      while (true)
        vlin = fgets (vfid);
        if ~(ischar (vlin)), break; end
          if (regexp (vlin, '^("-\*- texinfo -\*-\\n\\)')) %#"
          vlin = ' * '; %# Needed for the first call of while()
          while (isempty (regexp (vlin, '^(@end deftypefn)')) && ischar (vlin))
            vlin = fgets (vfid);
            vlin = sprintf (regexprep (vlin, '\\n\\', ''));
            vlin = regexprep (vlin, '\\"', '"');
            fprintf (vout, '%s', vlin);
          end
          fprintf (vout, '\n');
        end
      end
      fclose (vfid);
    end
  end
  fclose (vout);

function [] = odepkg_performance_mathires ()
  vfun = {@ode113, @ode23, @ode45, ...
          @ode15s, @ode23s, @ode23t, @ode23tb};
  for vcnt=1:length(vfun)
    vsol{vcnt, 1} = odepkg_testsuite_hires (vfun{vcnt}, 1e-7);
  end
  odepkg_testsuite_write (vsol);

function [] = odepkg_performance_octavehires ()
  vfun = {@ode23, @ode45, @ode54, @ode78, ...
          @ode2r, @ode5r, @oders, @odesx, @odebda};
  for vcnt=1:length(vfun)
    vsol{vcnt, 1} = odepkg_testsuite_hires (vfun{vcnt}, 1e-7);
  end
  odepkg_testsuite_write (vsol);

function [] = odepkg_performance_matchemakzo ()
  vfun = {@ode23, @ode45, ...
          @ode15s, @ode23s, @ode23t, @ode23tb};
  for vcnt=1:length(vfun)
    vsol{vcnt, 1} = odepkg_testsuite_chemakzo (vfun{vcnt}, 1e-7);
  end
  odepkg_testsuite_write (vsol);

function [] = odepkg_performance_octavechemakzo ()
  vfun = {@ode23, @ode45, @ode54, @ode78, ...
          @ode2r, @ode5r, @oders, @odesx, @odebda};
  for vcnt=1:length(vfun)
    vsol{vcnt, 1} = odepkg_testsuite_chemakzo (vfun{vcnt}, 1e-7);
  end
  odepkg_testsuite_write (vsol);

function [] = odepkg_testsuite_write (vsol)

  fprintf (1, ['-----------------------------------------------------------------------------------------\n']);
  fprintf (1, [' Solver  RelTol  AbsTol   Init   Mescd    Scd  Steps  Accept  FEval  JEval  LUdec    Time\n']);
  fprintf (1, ['-----------------------------------------------------------------------------------------\n']);

  [vlin, vcol] = size (vsol);
  for vcntlin = 1:vlin
    if (isempty (vsol{vcntlin}{9})), vsol{vcntlin}{9} = 0; end
    %# if (vcntlin > 1) %# Delete solver name if it is the same as before
    %#   if (strcmp (func2str (vsol{vcntlin}{1}), func2str (vsol{vcntlin-1}{1})))
    %#     vsol{vcntlin}{1} = ' ';
    %#   end
    %# end
    fprintf (1, ['%7s  %6.0g  %6.0g  %6.0g  %5.2f  %5.2f  %5.0d  %6.0d  %5.0d  %5.0d  %5.0d  %6.3f\n'], ...
             func2str (vsol{vcntlin}{1}), vsol{vcntlin}{2:12});
  end
  fprintf (1, ['-----------------------------------------------------------------------------------------\n']);

function [] = odepkg_internal_demos ()

  vfun = {'odepkg', 'odeget', 'odeset', ...
          'ode23', 'ode45', 'ode54', 'ode78', ...
          'ode23d', 'ode45d', 'ode54d', 'ode78d', ...
          'odeplot', 'odephas2', 'odephas3', 'odeprint', ...
          'odepkg_structure_check', 'odepkg_event_handle'};
  vfun = sort (vfun);

  for vcnt = 1:length (vfun), demo (vfun{vcnt}); end

%# Local Variables: ***
%# mode: octave ***
%# End: ***
