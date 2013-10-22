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
%# @deftypefn  {Function File} {[@var{value}] =} odeget (@var{odestruct}, @var{option}, [@var{default}])
%# @deftypefnx {Command} {[@var{values}] =} odeget (@var{odestruct}, @{@var{opt1}, @var{opt2}, @dots{}@}, [@{@var{def1}, @var{def2}, @dots{}@}])
%#
%# If this function is called with two input arguments and the first input argument @var{odestruct} is of type structure array and the second input argument @var{option} is of type string then return the option value @var{value} that is specified by the option name @var{option} in the OdePkg option structure @var{odestruct}. Optionally if this function is called with a third input argument then return the default value @var{default} if @var{option} is not set in the structure @var{odestruct}.
%#
%# If this function is called with two input arguments and the first input argument @var{odestruct} is of type structure array and the second input argument @var{option} is of type cell array of strings then return the option values @var{values} that are specified by the option names @var{opt1}, @var{opt2}, @dots{} in the OdePkg option structure @var{odestruct}. Optionally if this function is called with a third input argument of type cell array then return the default value @var{def1} if @var{opt1} is not set in the structure @var{odestruct}, @var{def2} if @var{opt2} is not set in the structure @var{odestruct}, @dots{}
%#
%# Run examples with the command
%# @example
%# demo odeget
%# @end example
%# @end deftypefn
%#
%# @seealso{odepkg}

%# Note: 20061022, Thomas Treichl
%#   We cannot create a function of the form odeget (@var{odestruct},
%#   @var{name1}, @var{name2}) because we would get a mismatch with
%#   the function form 1 like described above.

function [vret] = odeget (varargin)

  if (nargin == 0) %# Check number and types of input arguments
    vmsg = sprintf ('Number of input arguments must be greater than zero');
    help ('odeget'); error (vmsg);
  elseif (isstruct (varargin{1}) == true) %# Check first input argument
    vint.odestruct = odepkg_structure_check (varargin{1});
    vint.otemplate = odeset; %# Create default odepkg otpions structure
  else
    vmsg = sprintf ('First input argument must be a valid odepkg otpions structure');
    error (vmsg);
  end

  %# Check number and types of other input arguments
  if (length (varargin) < 2 || length (varargin) > 3)
    vmsg = sprintf ('odeget (odestruct, "option", default) or\n       odeget (odestruct, {"opt1", "opt2", ...}, {def1, def2, ...})');
    usage (vmsg);
  elseif (ischar (varargin{2}) == true && isempty (varargin{2}) == false)
    vint.arguments = {varargin{2}};
    vint.lengtharg = 1;
  elseif (iscellstr (varargin{2}) == true && isempty (varargin{2}) == false)
    vint.arguments = varargin{2};
    vint.lengtharg = length (vint.arguments);
  end

  if (nargin == 3) %# Check if third input argument is valid
    if (iscell (varargin{3}) == true)
      vint.defaults = varargin{3};
      vint.lengthdf = length (vint.defaults);
    else
      vint.defaults = {varargin{3}};
      vint.lengthdf = 1;
    end
    if (vint.lengtharg ~= vint.lengthdf)
      vmsg = sprintf ('If third argument is given then sizes of argument 2 and argument 3 must be the equal');
      error (vmsg);
    end
  end

  %# Run through number of input arguments given
  for vcntarg = 1:vint.lengtharg
    if ((...
         isempty(vint.odestruct.(vint.arguments{vcntarg}))
        )||( ...
         ischar(vint.odestruct.(vint.arguments{vcntarg})) && ...
         strcmp(vint.odestruct.(vint.arguments{vcntarg}),vint.otemplate.(vint.arguments{vcntarg}))...
        )||(...
         ~ischar(vint.odestruct.(vint.arguments{vcntarg})) && ...
         vint.odestruct.(vint.arguments{vcntarg}) == vint.otemplate.(vint.arguments{vcntarg}) ...
        ))
      if (nargin == 3), vint.returnval{vcntarg} = vint.defaults{vcntarg};
      else, vint.returnval{vcntarg} = vint.odestruct.(vint.arguments{vcntarg}); end
    else, vint.returnval{vcntarg} = vint.odestruct.(vint.arguments{vcntarg});
    end
  end

  %# Postprocessing, store results in the vret variable
  if (vint.lengtharg == 1), vret = vint.returnval{1};
  else, vret = vint.returnval; end

%!test assert (odeget (odeset (), 'RelTol'), []);
%!test assert (odeget (odeset (), 'RelTol', 10), 10);
%!test assert (odeget (odeset (), {'RelTol', 'AbsTol'}), {[] []})
%!test assert (odeget (odeset (), {'RelTol', 'AbsTol'}, {10 20}), {10 20});
%!test assert (odeget (odeset (), 'Stats'), 'off');
%!test assert (odeget (odeset (), 'Stats', 'on'), 'on');

%!demo
%! # Return the manually changed value RelTol of the OdePkg options
%! # strutcure A. If RelTol wouldn't have been changed then an
%! # empty matrix value would have been returned.
%!
%! A = odeset ('RelTol', 1e-1, 'AbsTol', 1e-2);
%! odeget (A, 'RelTol', [])

%!demo
%! # Return the manually changed value of RelTol and the value 1e-4
%! # for AbsTol of the OdePkg options structure A.
%!
%! A = odeset ('RelTol', 1e-1);
%! odeget (A, {'RelTol', 'AbsTol'}, {1e-2, 1e-4})

%# Local Variables: ***
%# mode: octave ***
%# End: ***
