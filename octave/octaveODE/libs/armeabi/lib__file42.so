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
%# @deftypefn  {Function File} {[@var{odestruct}] =} odeset ()
%# @deftypefnx {Command}  {[@var{odestruct}] =} odeset (@var{"field1"}, @var{value1}, @var{"field2"}, @var{value2}, @dots{})
%# @deftypefnx {Command}  {[@var{odestruct}] =} odeset (@var{oldstruct}, @var{"field1"}, @var{value1}, @var{"field2"}, @var{value2}, @dots{})
%# @deftypefnx {Command}  {[@var{odestruct}] =} odeset (@var{oldstruct}, @var{newstruct})
%#
%# If this function is called without an input argument then return a new OdePkg options structure array that contains all the necessary fields and sets the values of all fields to default values.
%#
%# If this function is called with string input arguments @var{"field1"}, @var{"field2"}, @dots{} identifying valid OdePkg options then return a new OdePkg options structure with all necessary fields and set the values of the fields @var{"field1"}, @var{"field2"}, @dots{} to the values @var{value1}, @var{value2}, @dots{}
%#
%# If this function is called with a first input argument @var{oldstruct} of type structure array then overwrite all values of the options @var{"field1"}, @var{"field2"}, @dots{} of the structure @var{oldstruct} with new values @var{value1}, @var{value2}, @dots{} and return the modified structure array.
%#
%# If this function is called with two input argumnets @var{oldstruct} and @var{newstruct} of type structure array then overwrite all values in the fields from the structure @var{oldstruct} with new values of the fields from the structure @var{newstruct}. Empty values of @var{newstruct} will not overwrite values in @var{oldstruct}.
%#
%# For a detailed explanation about valid fields and field values in an OdePkg structure aaray have a look at the @file{odepkg.pdf}, Section 'ODE/DAE/IDE/DDE options' or run the command @command{doc odepkg} to open the tutorial.
%#
%# Run examples with the command
%# @example
%# demo odeset
%# @end example
%# @end deftypefn
%#
%# @seealso{odepkg}

function [vret] = odeset (varargin)

  %# Create a template OdePkg structure
  vtemplate = struct ...
    ('RelTol', [], ...
     'AbsTol', [], ...
     'NormControl', 'off', ...
     'NonNegative', [], ...
     'OutputFcn', [], ...
     'OutputSel', [], ...
     'OutputSave',[],...
     'Refine', 0, ...
     'Stats', 'off', ...
     'InitialStep', [], ...
     'MaxStep', [], ...
     'Events', [], ...
     'Jacobian', [], ...
     'JPattern', [], ...
     'Vectorized', 'off', ...
     'Mass', [], ...
     'MStateDependence', 'weak', ...
     'MvPattern', [], ...
     'MassSingular', 'maybe', ...
     'InitialSlope', [], ...
     'MaxOrder', [], ...
     'BDF', [], ...
     'NewtonTol', [], ...
     'MaxNewtonIterations', []);

  %# Check number and types of all input arguments
  if (nargin == 0 && nargout == 1)
    vret = odepkg_structure_check (vtemplate);
    return;

  elseif (nargin == 0)
    help ('odeset');
    error ('OdePkg:InvalidArgument', ...
      'Number of input arguments must be greater than zero');

  elseif (length (varargin) < 2)
    usage ('odeset ("field1", "value1", ...)');

  elseif (ischar (varargin{1}) && mod (length (varargin), 2) == 0)
    %# Check if there is an odd number of input arguments. If this is
    %# true then save all the structure names in varg and its values in
    %# vval and increment vnmb for every option that is found.
    vnmb = 1;
    for vcntarg = 1:2:length (varargin)
      if (ischar (varargin{vcntarg}))
        varg{vnmb} = varargin{vcntarg};
        vval{vnmb} = varargin{vcntarg+1};
        vnmb = vnmb + 1;
      else
        error ('OdePkg:InvalidArgument', ...
          'Input argument number %d is no valid string', vcntarg);
      end
    end

    %# Create and return a new OdePkg structure and fill up all new
    %# field values that have been found.
    for vcntarg = 1:(vnmb-1)
      vtemplate.(varg{vcntarg}) = vval{vcntarg};
    end
    vret = odepkg_structure_check (vtemplate);

  elseif (isstruct (varargin{1}) && ischar (varargin{2}) && ...
    mod (length (varargin), 2) == 1)
    %# Check if there is an even number of input arguments. If this is
    %# true then the first input argument also must be a valid OdePkg
    %# structure. Save all the structure names in varg and its values in
    %# vval and increment the vnmb counter for every option that is
    %# found.
    vnmb = 1;
    for vcntarg = 2:2:length (varargin)
      if (ischar (varargin{vcntarg}))
        varg{vnmb} = varargin{vcntarg};
        vval{vnmb} = varargin{vcntarg+1};
        vnmb = vnmb + 1;
      else
        error ('OdePkg:InvalidArgument', ...
          'Input argument number %d is no valid string', vcntarg);
      end
    end

    %# Use the old OdePkg structure and fill up all new field values
    %# that have been found.
    vret = odepkg_structure_check (varargin{1});
    for vcntarg = 1:(vnmb-1)
      vret.(varg{vcntarg}) = vval{vcntarg};
    end
    vret = odepkg_structure_check (vret);

  elseif (isstruct (varargin{1}) && isstruct (varargin{2}) && ...
    length (varargin) == 2)
    %# Check if the two input arguments are valid OdePkg structures and
    %# also check if there does not exist any other input argument.
    vret = odepkg_structure_check (varargin{1});
    vnew = odepkg_structure_check (varargin{2});
    vfld = fieldnames (vnew);
    vlen = length (vfld);
    for vcntfld = 1:vlen
      if (~isempty (vnew.(vfld{vcntfld})))
        vret.(vfld{vcntfld}) = vnew.(vfld{vcntfld});
      end
    end
    vret = odepkg_structure_check (vret);

  else
    error ('OdePkg:InvalidArgument', ...
      'Check types and number of all input arguments');
  end
end

%# All tests that are needed to check if a correct resp. valid option
%# has been set are implemented in odepkg_structure_check.m.
%!test odeoptA = odeset ();
%!test odeoptB = odeset ('AbsTol', 1e-2, 'RelTol', 1e-1);
%!     if (odeoptB.AbsTol ~= 1e-2), error; end
%!     if (odeoptB.RelTol ~= 1e-1), error; end
%!test odeoptB = odeset ('AbsTol', 1e-2, 'RelTol', 1e-1);
%!     odeoptC = odeset (odeoptB, 'NormControl', 'on');
%!test odeoptB = odeset ('AbsTol', 1e-2, 'RelTol', 1e-1);
%!     odeoptC = odeset (odeoptB, 'NormControl', 'on');
%!     odeoptD = odeset (odeoptC, odeoptB);

%!demo
%! # A new OdePkg options structure with default values is created.
%!
%! odeoptA = odeset ();
%!
%!demo
%! # A new OdePkg options structure with manually set options 
%! # "AbsTol" and "RelTol" is created.
%!
%! odeoptB = odeset ('AbsTol', 1e-2, 'RelTol', 1e-1);
%!
%!demo
%! # A new OdePkg options structure from odeoptB is created with
%! # a modified value for option "NormControl".
%!
%! odeoptB = odeset ('AbsTol', 1e-2, 'RelTol', 1e-1);
%! odeoptC = odeset (odeoptB, 'NormControl', 'on');

%# Local Variables: ***
%# mode: octave ***
%# End: ***
