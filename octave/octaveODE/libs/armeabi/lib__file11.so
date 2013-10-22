%# Copyright (C) 2008-2012, Thomas Treichl <treichl@users.sourceforge.net>
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
%# @deftypefn  {Function File} {[@var{}] =} ode45d (@var{@@fun}, @var{slot}, @var{init}, @var{lags}, @var{hist}, [@var{opt}], [@var{par1}, @var{par2}, @dots{}])
%# @deftypefnx {Command} {[@var{sol}] =} ode45d (@var{@@fun}, @var{slot}, @var{init}, @var{lags}, @var{hist}, [@var{opt}], [@var{par1}, @var{par2}, @dots{}])
%# @deftypefnx {Command} {[@var{t}, @var{y}, [@var{xe}, @var{ye}, @var{ie}]] =} ode45d (@var{@@fun}, @var{slot}, @var{init}, @var{lags}, @var{hist}, [@var{opt}], [@var{par1}, @var{par2}, @dots{}])
%#
%# This function file can be used to solve a set of non--stiff delay differential equations (non--stiff DDEs) with a modified version of the well known explicit Runge--Kutta method of order (4,5).
%#
%# If this function is called with no return argument then plot the solution over time in a figure window while solving the set of DDEs that are defined in a function and specified by the function handle @var{@@fun}. The second input argument @var{slot} is a double vector that defines the time slot, @var{init} is a double vector that defines the initial values of the states, @var{lags} is a double vector that describes the lags of time, @var{hist} is a double matrix and describes the history of the DDEs, @var{opt} can optionally be a structure array that keeps the options created with the command @command{odeset} and @var{par1}, @var{par2}, @dots{} can optionally be other input arguments of any type that have to be passed to the function defined by @var{@@fun}.
%#
%# In other words, this function will solve a problem of the form
%# @example
%# dy/dt = fun (t, y(t), y(t-lags(1), y(t-lags(2), @dots{})))
%# y(slot(1)) = init
%# y(slot(1)-lags(1)) = hist(1), y(slot(1)-lags(2)) = hist(2), @dots{} 
%# @end example
%#
%# If this function is called with one return argument then return the solution @var{sol} of type structure array after solving the set of DDEs. The solution @var{sol} has the fields @var{x} of type double column vector for the steps chosen by the solver, @var{y} of type double column vector for the solutions at each time step of @var{x}, @var{solver} of type string for the solver name and optionally the extended time stamp information @var{xe}, the extended solution information @var{ye} and the extended index information @var{ie} all of type double column vector that keep the informations of the event function if an event function handle is set in the option argument @var{opt}.
%#
%# If this function is called with more than one return argument then return the time stamps @var{t}, the solution values @var{y} and optionally the extended time stamp information @var{xe}, the extended solution information @var{ye} and the extended index information @var{ie} all of type double column vector.
%#
%# For example:
%# @itemize @minus
%# @item
%# the following code solves an anonymous implementation of a chaotic behavior
%#
%# @example
%# fcao = @@(vt, vy, vz) [2 * vz / (1 + vz^9.65) - vy];
%#
%# vopt = odeset ("NormControl", "on", "RelTol", 1e-3);
%# vsol = ode45d (fcao, [0, 100], 0.5, 2, 0.5, vopt);
%#
%# vlag = interp1 (vsol.x, vsol.y, vsol.x - 2);
%# plot (vsol.y, vlag); legend ("fcao (t,y,z)");
%# @end example
%#
%# @item
%# to solve the following problem with two delayed state variables
%#
%# @example
%# d y1(t)/dt = -y1(t)
%# d y2(t)/dt = -y2(t) + y1(t-5)
%# d y3(t)/dt = -y3(t) + y2(t-10)*y1(t-10)
%# @end example
%#
%# one might do the following
%#
%# @example
%# function f = fun (t, y, yd)
%# f(1) = -y(1);                   %% y1' = -y1(t)
%# f(2) = -y(2) + yd(1,1);         %% y2' = -y2(t) + y1(t-lags(1))
%# f(3) = -y(3) + yd(2,2)*yd(1,2); %% y3' = -y3(t) + y2(t-lags(2))*y1(t-lags(2))
%# endfunction
%# T = [0,20]
%# res = ode45d (@@fun, T, [1;1;1], [5, 10], ones (3,2));
%# @end example
%#
%# @end itemize
%# @end deftypefn
%#
%# @seealso{odepkg}

function [varargout] = ode45d (vfun, vslot, vinit, vlags, vhist, varargin)

  if (nargin == 0) %# Check number and types of all input arguments
    help ('ode45d');
    error ('OdePkg:InvalidArgument', ...
      'Number of input arguments must be greater than zero');

  elseif (nargin < 5)
    print_usage;

  elseif (~isa (vfun, 'function_handle'))
    error ('OdePkg:InvalidArgument', ...
      'First input argument must be a valid function handle');

  elseif (~isvector (vslot) || length (vslot) < 2)
    error ('OdePkg:InvalidArgument', ...
      'Second input argument must be a valid vector');

  elseif (~isvector (vinit) || ~isnumeric (vinit))
    error ('OdePkg:InvalidArgument', ...
      'Third input argument must be a valid numerical value');

  elseif (~isvector (vlags) || ~isnumeric (vlags))
    error ('OdePkg:InvalidArgument', ...
      'Fourth input argument must be a valid numerical value');

  elseif ~(isnumeric (vhist) || isa (vhist, 'function_handle'))
    error ('OdePkg:InvalidArgument', ...
      'Fifth input argument must either be numeric or a function handle');

  elseif (nargin >= 6)

    if (~isstruct (varargin{1}))
      %# varargin{1:len} are parameters for vfun
      vodeoptions = odeset;
      vfunarguments = varargin;

    elseif (length (varargin) > 1)
      %# varargin{1} is an OdePkg options structure vopt
      vodeoptions = odepkg_structure_check (varargin{1}, 'ode45d');
      vfunarguments = {varargin{2:length(varargin)}};

    else %# if (isstruct (varargin{1}))
      vodeoptions = odepkg_structure_check (varargin{1}, 'ode45d');
      vfunarguments = {};

    end

  else %# if (nargin == 5)
    vodeoptions = odeset; 
    vfunarguments = {};
  end

  %# Start preprocessing, have a look which options have been set in
  %# vodeoptions. Check if an invalid or unused option has been set and
  %# print warnings.
  vslot = vslot(:)'; %# Create a row vector
  vinit = vinit(:)'; %# Create a row vector
  vlags = vlags(:)'; %# Create a row vector

  %# Check if the user has given fixed points of time
  if (length (vslot) > 2), vstepsizegiven = true; %# Step size checking
  else vstepsizegiven = false; end  

  %# Get the default options that can be set with 'odeset' temporarily
  vodetemp = odeset;

  %# Implementation of the option RelTol has been finished. This option
  %# can be set by the user to another value than default value.
  if (isempty (vodeoptions.RelTol) && ~vstepsizegiven)
    vodeoptions.RelTol = 1e-6;
    warning ('OdePkg:InvalidOption', ...
      'Option "RelTol" not set, new value %f is used', vodeoptions.RelTol);
  elseif (~isempty (vodeoptions.RelTol) && vstepsizegiven)
    warning ('OdePkg:InvalidOption', ...
      'Option "RelTol" will be ignored if fixed time stamps are given');
  %# This implementation has been added to odepkg_structure_check.m
  %# elseif (~isscalar (vodeoptions.RelTol) && ~vstepsizegiven)
  %# error ('OdePkg:InvalidOption', ...
  %#   'Option "RelTol" must be set to a scalar value for this solver');
  end

  %# Implementation of the option AbsTol has been finished. This option
  %# can be set by the user to another value than default value.
  if (isempty (vodeoptions.AbsTol) && ~vstepsizegiven)
    vodeoptions.AbsTol = 1e-6;
    warning ('OdePkg:InvalidOption', ...
      'Option "AbsTol" not set, new value %f is used', vodeoptions.AbsTol);
  elseif (~isempty (vodeoptions.AbsTol) && vstepsizegiven)
    warning ('OdePkg:InvalidOption', ...
      'Option "AbsTol" will be ignored if fixed time stamps are given');
  else %# create column vector
    vodeoptions.AbsTol = vodeoptions.AbsTol(:);
  end

  %# Implementation of the option NormControl has been finished. This
  %# option can be set by the user to another value than default value.
  if (strcmp (vodeoptions.NormControl, 'on')), vnormcontrol = true;
  else vnormcontrol = false;
  end

  %# Implementation of the option NonNegative has been finished. This
  %# option can be set by the user to another value than default value.
  if (~isempty (vodeoptions.NonNegative))
    if (isempty (vodeoptions.Mass)), vhavenonnegative = true;
    else
      vhavenonnegative = false;
      warning ('OdePkg:InvalidOption', ...
        'Option "NonNegative" will be ignored if mass matrix is set');
    end
  else vhavenonnegative = false;
  end

  %# Implementation of the option OutputFcn has been finished. This
  %# option can be set by the user to another value than default value.
  if (isempty (vodeoptions.OutputFcn) && nargout == 0)
    vodeoptions.OutputFcn = @odeplot;
    vhaveoutputfunction = true;
  elseif (isempty (vodeoptions.OutputFcn)), vhaveoutputfunction = false;
  else vhaveoutputfunction = true;
  end

  %# Implementation of the option OutputSel has been finished. This
  %# option can be set by the user to another value than default value.
  if (~isempty (vodeoptions.OutputSel)), vhaveoutputselection = true;
  else vhaveoutputselection = false; end

  %# Implementation of the option Refine has been finished. This option
  %# can be set by the user to another value than default value.
  if (isequal (vodeoptions.Refine, vodetemp.Refine)), vhaverefine = true;
  else vhaverefine = false; end

  %# Implementation of the option Stats has been finished. This option
  %# can be set by the user to another value than default value.

  %# Implementation of the option InitialStep has been finished. This
  %# option can be set by the user to another value than default value.
  if (isempty (vodeoptions.InitialStep) && ~vstepsizegiven)
    vodeoptions.InitialStep = abs (vslot(1,1) - vslot(1,2)) / 10;
    vodeoptions.InitialStep = vodeoptions.InitialStep / 10^vodeoptions.Refine;
    warning ('OdePkg:InvalidOption', ...
      'Option "InitialStep" not set, new value %f is used', vodeoptions.InitialStep);
  end

  %# Implementation of the option MaxStep has been finished. This option
  %# can be set by the user to another value than default value.
  if (isempty (vodeoptions.MaxStep) && ~vstepsizegiven)
    vodeoptions.MaxStep = abs (vslot(1,1) - vslot(1,length (vslot))) / 10;
    %# vodeoptions.MaxStep = vodeoptions.MaxStep / 10^vodeoptions.Refine;
    warning ('OdePkg:InvalidOption', ...
      'Option "MaxStep" not set, new value %f is used', vodeoptions.MaxStep);
  end

  %# Implementation of the option Events has been finished. This option
  %# can be set by the user to another value than default value.
  if (~isempty (vodeoptions.Events)), vhaveeventfunction = true;
  else vhaveeventfunction = false; end

  %# The options 'Jacobian', 'JPattern' and 'Vectorized' will be ignored
  %# by this solver because this solver uses an explicit Runge-Kutta
  %# method and therefore no Jacobian calculation is necessary
  if (~isequal (vodeoptions.Jacobian, vodetemp.Jacobian))
    warning ('OdePkg:InvalidOption', ...
      'Option "Jacobian" will be ignored by this solver');
  end
  if (~isequal (vodeoptions.JPattern, vodetemp.JPattern))
    warning ('OdePkg:InvalidOption', ...
      'Option "JPattern" will be ignored by this solver');
  end
  if (~isequal (vodeoptions.Vectorized, vodetemp.Vectorized))
    warning ('OdePkg:InvalidOption', ...
      'Option "Vectorized" will be ignored by this solver');
  end
  if (~isequal (vodeoptions.NewtonTol, vodetemp.NewtonTol))
    warning ('OdePkg:InvalidArgument', ...
      'Option "NewtonTol" will be ignored by this solver');
  end
  if (~isequal (vodeoptions.MaxNewtonIterations,...
                vodetemp.MaxNewtonIterations))
    warning ('OdePkg:InvalidArgument', ...
      'Option "MaxNewtonIterations" will be ignored by this solver');
  end

  %# Implementation of the option Mass has been finished. This option
  %# can be set by the user to another value than default value.
  if (~isempty (vodeoptions.Mass) && isnumeric (vodeoptions.Mass))
    vhavemasshandle = false; vmass = vodeoptions.Mass; %# constant mass
  elseif (isa (vodeoptions.Mass, 'function_handle'))
    vhavemasshandle = true; %# mass defined by a function handle
  else %# no mass matrix - creating a diag-matrix of ones for mass
    vhavemasshandle = false; %# vmass = diag (ones (length (vinit), 1), 0);
  end

  %# Implementation of the option MStateDependence has been finished.
  %# This option can be set by the user to another value than default
  %# value. 
  if (strcmp (vodeoptions.MStateDependence, 'none'))
    vmassdependence = false;
  else vmassdependence = true;
  end

  %# Other options that are not used by this solver. Print a warning
  %# message to tell the user that the option(s) is/are ignored.
  if (~isequal (vodeoptions.MvPattern, vodetemp.MvPattern))
    warning ('OdePkg:InvalidOption', ...
      'Option "MvPattern" will be ignored by this solver');
  end
  if (~isequal (vodeoptions.MassSingular, vodetemp.MassSingular))
    warning ('OdePkg:InvalidOption', ...
      'Option "MassSingular" will be ignored by this solver');
  end
  if (~isequal (vodeoptions.InitialSlope, vodetemp.InitialSlope))
    warning ('OdePkg:InvalidOption', ...
      'Option "InitialSlope" will be ignored by this solver');
  end
  if (~isequal (vodeoptions.MaxOrder, vodetemp.MaxOrder))
    warning ('OdePkg:InvalidOption', ...
      'Option "MaxOrder" will be ignored by this solver');
  end
  if (~isequal (vodeoptions.BDF, vodetemp.BDF))
    warning ('OdePkg:InvalidOption', ...
      'Option "BDF" will be ignored by this solver');
  end

  %# Starting the initialisation of the core solver ode45d 
  vtimestamp  = vslot(1,1);           %# timestamp = start time
  vtimelength = length (vslot);       %# length needed if fixed steps
  vtimestop   = vslot(1,vtimelength); %# stop time = last value

  if (~vstepsizegiven)
    vstepsize = vodeoptions.InitialStep;
    vminstepsize = (vtimestop - vtimestamp) / (1/eps);
  else %# If step size is given then use the fixed time steps
    vstepsize = abs (vslot(1,1) - vslot(1,2));
    vminstepsize = eps; %# vslot(1,2) - vslot(1,1) - eps;
  end

  vretvaltime = vtimestamp; %# first timestamp output
  if (vhaveoutputselection) %# first solution output
    vretvalresult = vinit(vodeoptions.OutputSel);
  else vretvalresult = vinit;
  end

  %# Initialize the OutputFcn
  if (vhaveoutputfunction)
    feval (vodeoptions.OutputFcn, vslot', ...
      vretvalresult', 'init', vfunarguments{:});
  end

  %# Initialize the History
  if (isnumeric (vhist))
    vhmat = vhist;
    vhavehistnumeric = true;
  else %# it must be a function handle
    for vcnt = 1:length (vlags);
      vhmat(:,vcnt) = feval (vhist, (vslot(1)-vlags(vcnt)), vfunarguments{:});
    end
    vhavehistnumeric = false;
  end

  %# Initialize DDE variables for history calculation
  vsaveddetime = [vtimestamp - vlags, vtimestamp]';
  vsaveddeinput = [vhmat, vinit']';
  vsavedderesult = [vhmat, vinit']';

  %# Initialize the EventFcn
  if (vhaveeventfunction)
    odepkg_event_handle (vodeoptions.Events, vtimestamp, ...
      {vretvalresult', vhmat}, 'init', vfunarguments{:});
  end

  vpow = 1/5;                %# 20071016, reported by Luis Randez
  va = [0, 0, 0, 0, 0;       %# The Runge-Kutta-Fehlberg 4(5) coefficients
        1/4, 0, 0, 0, 0;     %# Coefficients proved on 20060827
        3/32, 9/32, 0, 0, 0; %# See p.91 in Ascher & Petzold
        1932/2197, -7200/2197, 7296/2197, 0, 0;
        439/216, -8, 3680/513, -845/4104, 0;
        -8/27, 2, -3544/2565, 1859/4104, -11/40];
  %# 4th and 5th order b-coefficients
  vb4 = [25/216; 0; 1408/2565; 2197/4104; -1/5; 0];
  vb5 = [16/135; 0; 6656/12825; 28561/56430; -9/50; 2/55];
  vc = sum (va, 2);

  %# The solver main loop - stop if endpoint has been reached
  vcntloop = 2; vcntcycles = 1; vu = vinit; vk = vu' * zeros(1,6);
  vcntiter = 0; vunhandledtermination = true;
  while ((vtimestamp < vtimestop && vstepsize >= vminstepsize))

    %# Hit the endpoint of the time slot exactely
    if ((vtimestamp + vstepsize) > vtimestop)
      vstepsize = vtimestop - vtimestamp; end

    %# Estimate the six results when using this solver
    for j = 1:6
      vthetime  = vtimestamp + vc(j,1) * vstepsize;
      vtheinput = vu' + vstepsize * vk(:,1:j-1) * va(j,1:j-1)';
      %# Claculate the history values (or get them from an external
      %# function) that are needed for the next step of solving
      if (vhavehistnumeric)
        for vcnt = 1:length (vlags)
          %# Direct implementation of a 'quadrature cubic Hermite interpolation'
          %# found at the Faculty for Mathematics of the University of Stuttgart
          %# http://mo.mathematik.uni-stuttgart.de/inhalt/aussage/aussage1269
          vnumb = find (vthetime - vlags(vcnt) >= vsaveddetime);
          velem = min (vnumb(end), length (vsaveddetime) - 1);
          vstep = vsaveddetime(velem+1) - vsaveddetime(velem);
          vdiff = (vthetime - vlags(vcnt) - vsaveddetime(velem)) / vstep;
          vsubs = 1 - vdiff;
          %# Calculation of the coefficients for the interpolation algorithm
          vua = (1 + 2 * vdiff) * vsubs^2;
          vub = (3 - 2 * vdiff) * vdiff^2;
          vva = vstep * vdiff * vsubs^2;
          vvb = -vstep * vsubs * vdiff^2;
          vhmat(:,vcnt) = vua * vsaveddeinput(velem,:)' + ...
              vub * vsaveddeinput(velem+1,:)' + ...
              vva * vsavedderesult(velem,:)' + ...
              vvb * vsavedderesult(velem+1,:)';
        end
      else %# the history must be a function handle
        for vcnt = 1:length (vlags)
          vhmat(:,vcnt) = feval ...
            (vhist, vthetime - vlags(vcnt), vfunarguments{:});
        end
      end

      if (vhavemasshandle)   %# Handle only the dynamic mass matrix,
        if (vmassdependence) %# constant mass matrices have already
          vmass = feval ...  %# been set before (if any)
            (vodeoptions.Mass, vthetime, vtheinput, vfunarguments{:});
        else                 %# if (vmassdependence == false)
          vmass = feval ...  %# then we only have the time argument
            (vodeoptions.Mass, vthetime, vfunarguments{:});
        end
        vk(:,j) = vmass \ feval ...
          (vfun, vthetime, vtheinput, vhmat, vfunarguments{:});
      else
        vk(:,j) = feval ...
          (vfun, vthetime, vtheinput, vhmat, vfunarguments{:});
      end
    end

    %# Compute the 4th and the 5th order estimation
    y4 = vu' + vstepsize * (vk * vb4);
    y5 = vu' + vstepsize * (vk * vb5);
    if (vhavenonnegative)
      vu(vodeoptions.NonNegative) = abs (vu(vodeoptions.NonNegative));
      y4(vodeoptions.NonNegative) = abs (y4(vodeoptions.NonNegative));
      y5(vodeoptions.NonNegative) = abs (y5(vodeoptions.NonNegative));
    end
    vSaveVUForRefine = vu;

    %# Calculate the absolute local truncation error and the acceptable error
    if (~vstepsizegiven)
      if (~vnormcontrol)
        vdelta = y5 - y4;
        vtau = max (vodeoptions.RelTol * vu', vodeoptions.AbsTol);
      else
        vdelta = norm (y5 - y4, Inf);
        vtau = max (vodeoptions.RelTol * max (norm (vu', Inf), 1.0), ...
                    vodeoptions.AbsTol);
      end
    else %# if (vstepsizegiven == true)
      vdelta = 1; vtau = 2;
    end

    %# If the error is acceptable then update the vretval variables
    if (all (vdelta <= vtau))
      vtimestamp = vtimestamp + vstepsize;
      vu = y5'; %# MC2001: the higher order estimation as "local extrapolation"
      vretvaltime(vcntloop,:) = vtimestamp;
      if (vhaveoutputselection)
        vretvalresult(vcntloop,:) = vu(vodeoptions.OutputSel);
      else
        vretvalresult(vcntloop,:) = vu;
      end
      vcntloop = vcntloop + 1; vcntiter = 0;

      %# Update DDE values for next history calculation      
      vsaveddetime(end+1) = vtimestamp;
      vsaveddeinput(end+1,:) = vtheinput';
      vsavedderesult(end+1,:) = vu;

      %# Call plot only if a valid result has been found, therefore this
      %# code fragment has moved here. Stop integration if plot function
      %# returns false
      if (vhaveoutputfunction)
        if (vhaverefine)                  %# Do interpolation
          for vcnt = 0:vodeoptions.Refine %# Approximation between told and t
            vapproxtime = (vcnt + 1) * vstepsize / (vodeoptions.Refine + 2);
            vapproxvals = vSaveVUForRefine' + vapproxtime * (vk * vb5);
            if (vhaveoutputselection)
              vapproxvals = vapproxvals(vodeoptions.OutputSel);
            end
            feval (vodeoptions.OutputFcn, (vtimestamp - vstepsize) + vapproxtime, ...
              vapproxvals, [], vfunarguments{:});
          end
        end
        vpltret = feval (vodeoptions.OutputFcn, vtimestamp, ...
          vretvalresult(vcntloop-1,:)', [], vfunarguments{:});
        if (vpltret), vunhandledtermination = false; break; end
      end

      %# Call event only if a valid result has been found, therefore this
      %# code fragment has moved here. Stop integration if veventbreak is
      %# true
      if (vhaveeventfunction)
        vevent = ...
          odepkg_event_handle (vodeoptions.Events, vtimestamp, ...
            {vu(:), vhmat}, [], vfunarguments{:});
        if (~isempty (vevent{1}) && vevent{1} == 1)
          vretvaltime(vcntloop-1,:) = vevent{3}(end,:);
          vretvalresult(vcntloop-1,:) = vevent{4}(end,:);
          vunhandledtermination = false; break;
        end
      end
    end %# If the error is acceptable ...

    %# Update the step size for the next integration step
    if (~vstepsizegiven)
      %# vdelta may be 0 or even negative - could be an iteration problem
      vdelta = max (vdelta, eps); 
      vstepsize = min (vodeoptions.MaxStep, ...
        min (0.8 * vstepsize * (vtau ./ vdelta) .^ vpow));
    elseif (vstepsizegiven)
      if (vcntloop < vtimelength)
        vstepsize = vslot(1,vcntloop-1) - vslot(1,vcntloop-2);
      end
    end

    %# Update counters that count the number of iteration cycles
    vcntcycles = vcntcycles + 1; %# Needed for postprocessing
    vcntiter = vcntiter + 1;     %# Needed to find iteration problems

    %# Stop solving because the last 1000 steps no successful valid
    %# value has been found
    if (vcntiter >= 5000)
      error (['Solving has not been successful. The iterative', ...
        ' integration loop exited at time t = %f before endpoint at', ...
        ' tend = %f was reached. This happened because the iterative', ...
        ' integration loop does not find a valid solution at this time', ...
        ' stamp. Try to reduce the value of "InitialStep" and/or', ...
        ' "MaxStep" with the command "odeset".\n'], vtimestamp, vtimestop);
    end

  end %# The main loop

  %# Check if integration of the ode has been successful
  if (vtimestamp < vtimestop)
    if (vunhandledtermination == true)
      error (['Solving has not been successful. The iterative', ...
        ' integration loop exited at time t = %f', ...
        ' before endpoint at tend = %f was reached. This may', ...
        ' happen if the stepsize grows smaller than defined in', ...
        ' vminstepsize. Try to reduce the value of "InitialStep" and/or', ...
        ' "MaxStep" with the command "odeset".\n'], vtimestamp, vtimestop);
    else
      warning ('OdePkg:HideWarning', ...
        ['Solver has been stopped by a call of "break" in', ...
         ' the main iteration loop at time t = %f before endpoint at', ...
         ' tend = %f was reached. This may happen because the @odeplot', ...
         ' function returned "true" or the @event function returned "true".'], ...
         vtimestamp, vtimestop);
    end
  end

  %# Postprocessing, do whatever when terminating integration algorithm
  if (vhaveoutputfunction) %# Cleanup plotter
    feval (vodeoptions.OutputFcn, vtimestamp, ...
      vretvalresult(vcntloop-1,:)', 'done', vfunarguments{:});
  end
  if (vhaveeventfunction)  %# Cleanup event function handling
    odepkg_event_handle (vodeoptions.Events, vtimestamp, ...
      {vretvalresult(vcntloop-1,:), vhmat}, 'done', vfunarguments{:});
  end

  %# Print additional information if option Stats is set
  if (strcmp (vodeoptions.Stats, 'on'))
    vhavestats = true;
    vnsteps    = vcntloop-2;                    %# vcntloop from 2..end
    vnfailed   = (vcntcycles-1)-(vcntloop-2)+1; %# vcntcycl from 1..end
    vnfevals   = 6*(vcntcycles-1);              %# number of ode evaluations
    vndecomps  = 0;                             %# number of LU decompositions
    vnpds      = 0;                             %# number of partial derivatives
    vnlinsols  = 0;                             %# no. of solutions of linear systems
    %# Print cost statistics if no output argument is given
    if (nargout == 0)
      vmsg = fprintf (1, 'Number of successful steps: %d', vnsteps);
      vmsg = fprintf (1, 'Number of failed attempts:  %d', vnfailed);
      vmsg = fprintf (1, 'Number of function calls:   %d', vnfevals);
    end
  else vhavestats = false;
  end

  if (nargout == 1)                 %# Sort output variables, depends on nargout
    varargout{1}.x = vretvaltime;   %# Time stamps are saved in field x
    varargout{1}.y = vretvalresult; %# Results are saved in field y
    varargout{1}.solver = 'ode45d'; %# Solver name is saved in field solver
    if (vhaveeventfunction) 
      varargout{1}.ie = vevent{2};  %# Index info which event occured
      varargout{1}.xe = vevent{3};  %# Time info when an event occured
      varargout{1}.ye = vevent{4};  %# Results when an event occured
    end
    if (vhavestats)
      varargout{1}.stats = struct;
      varargout{1}.stats.nsteps   = vnsteps;
      varargout{1}.stats.nfailed  = vnfailed;
      varargout{1}.stats.nfevals  = vnfevals;
      varargout{1}.stats.npds     = vnpds;
      varargout{1}.stats.ndecomps = vndecomps;
      varargout{1}.stats.nlinsols = vnlinsols;
    end
  elseif (nargout == 2)
    varargout{1} = vretvaltime;     %# Time stamps are first output argument
    varargout{2} = vretvalresult;   %# Results are second output argument
  elseif (nargout == 5)
    varargout{1} = vretvaltime;     %# Same as (nargout == 2)
    varargout{2} = vretvalresult;   %# Same as (nargout == 2)
    varargout{3} = [];              %# LabMat doesn't accept lines like
    varargout{4} = [];              %# varargout{3} = varargout{4} = [];
    varargout{5} = [];
    if (vhaveeventfunction) 
      varargout{3} = vevent{3};     %# Time info when an event occured
      varargout{4} = vevent{4};     %# Results when an event occured
      varargout{5} = vevent{2};     %# Index info which event occured
    end
  %# else nothing will be returned, varargout{1} undefined
  end

%! # We are using a "pseudo-DDE" implementation for all tests that
%! # are done for this function. We also define an Events and a
%! # pseudo-Mass implementation. For further tests we also define a
%! # reference solution (computed at high accuracy) and an OutputFcn.
%!function [vyd] = fexp (vt, vy, vz, varargin)
%!  vyd(1,1) = exp (- vt) - vz(1); %# The DDEs that are
%!  vyd(2,1) = vy(1) - vz(2);      %# used for all examples
%!function [vval, vtrm, vdir] = feve (vt, vy, vz, varargin)
%!  vval = fexp (vt, vy, vz); %# We use the derivatives
%!  vtrm = zeros (2,1);       %# don't stop solving here
%!  vdir = ones (2,1);        %# in positive direction
%!function [vval, vtrm, vdir] = fevn (vt, vy, vz, varargin)
%!  vval = fexp (vt, vy, vz); %# We use the derivatives
%!  vtrm = ones (2,1);        %# stop solving here
%!  vdir = ones (2,1);        %# in positive direction
%!function [vmas] = fmas (vt, vy, vz, varargin)
%!  vmas =  [1, 0; 0, 1];     %# Dummy mass matrix for tests
%!function [vmas] = fmsa (vt, vy, vz, varargin)
%!  vmas = sparse ([1, 0; 0, 1]); %# A dummy sparse matrix
%!function [vref] = fref ()       %# The reference solution
%!  vref = [0.12194462133618, 0.01652432423938];
%!function [vout] = fout (vt, vy, vflag, varargin)
%!  if (regexp (char (vflag), 'init') == 1)
%!    if (any (size (vt) ~= [2, 1])) error ('"fout" step "init"'); end
%!  elseif (isempty (vflag))
%!    if (any (size (vt) ~= [1, 1])) error ('"fout" step "calc"'); end
%!    vout = false;
%!  elseif (regexp (char (vflag), 'done') == 1)
%!    if (any (size (vt) ~= [1, 1])) error ('"fout" step "done"'); end
%!  else error ('"fout" invalid vflag');
%!  end
%!
%! %# Turn off output of warning messages for all tests, turn them on
%! %# again if the last test is called
%!error %# input argument number one
%!  warning ('off', 'OdePkg:InvalidOption');
%!  B = ode45d (1, [0 5], [1; 0], 1, [1; 0]);
%!error %# input argument number two
%!  B = ode45d (@fexp, 1, [1; 0], 1, [1; 0]);
%!error %# input argument number three
%!  B = ode45d (@fexp, [0 5], 1, 1, [1; 0]);
%!error %# input argument number four
%!  B = ode45d (@fexp, [0 5], [1; 0], [1; 1], [1; 0]);
%!error %# input argument number five
%!  B = ode45d (@fexp, [0 5], [1; 0], 1, 1);
%!test %# one output argument
%!  vsol = ode45d (@fexp, [0 5], [1; 0], 1, [1; 0]);
%!  assert ([vsol.x(end), vsol.y(end,:)], [5, fref], 0.5);
%!  assert (isfield (vsol, 'solver'));
%!  assert (vsol.solver, 'ode45d');
%!test %# two output arguments
%!  [vt, vy] = ode45d (@fexp, [0 5], [1; 0], 1, [1; 0]);
%!  assert ([vt(end), vy(end,:)], [5, fref], 0.5);
%!test %# five output arguments and no Events
%!  [vt, vy, vxe, vye, vie] = ode45d (@fexp, [0 5], [1; 0], 1, [1; 0]);
%!  assert ([vt(end), vy(end,:)], [5, fref], 0.5);
%!  assert ([vie, vxe, vye], []);
%!test %# anonymous function instead of real function
%!  faym = @(vt, vy, vz) [exp(-vt) - vz(1); vy(1) - vz(2)];
%!  vsol = ode45d (faym, [0 5], [1; 0], 1, [1; 0]);
%!  assert ([vsol.x(end), vsol.y(end,:)], [5, fref], 0.5);
%!test %# extra input arguments passed trhough
%!  vsol = ode45d (@fexp, [0 5], [1; 0], 1, [1; 0], 'KL');
%!  assert ([vsol.x(end), vsol.y(end,:)], [5, fref], 0.5);
%!test %# empty OdePkg structure *but* extra input arguments
%!  vopt = odeset;
%!  vsol = ode45d (@fexp, [0 5], [1; 0], 1, [1; 0], vopt, 12, 13, 'KL');
%!  assert ([vsol.x(end), vsol.y(end,:)], [5, fref], 0.5);
%!error %# strange OdePkg structure
%!  vopt = struct ('foo', 1);
%!  vsol = ode45d (@fexp, [0 5], [1; 0], 1, [1; 0], vopt);
%!test %# AbsTol option
%!  vopt = odeset ('AbsTol', 1e-5);
%!  vsol = ode45d (@fexp, [0 5], [1; 0], 1, [1; 0], vopt);
%!  assert ([vsol.x(end), vsol.y(end,:)], [5, fref], 0.5);
%!test %# AbsTol and RelTol option
%!  vopt = odeset ('AbsTol', 1e-7, 'RelTol', 1e-7);
%!  vsol = ode45d (@fexp, [0 5], [1; 0], 1, [1; 0], vopt);
%!  assert ([vsol.x(end), vsol.y(end,:)], [5, fref], 0.5);
%!test %# RelTol and NormControl option
%!  vopt = odeset ('AbsTol', 1e-7, 'NormControl', 'on');
%!  vsol = ode45d (@fexp, [0 5], [1; 0], 1, [1; 0], vopt);
%!  assert ([vsol.x(end), vsol.y(end,:)], [5, fref], .5e-1);
%!test %# NonNegative for second component
%!  vopt = odeset ('NonNegative', 1);
%!  vsol = ode45d (@fexp, [0 2.5], [1; 0], 1, [1; 0], vopt);
%!  assert ([vsol.x(end), vsol.y(end,:)], [2.5, 0.001, 0.237], 0.5);
%!test %# Details of OutputSel and Refine can't be tested
%!  vopt = odeset ('OutputFcn', @fout, 'OutputSel', 1, 'Refine', 5);
%!  vsol = ode45d (@fexp, [0 2.5], [1; 0], 1, [1; 0], vopt);
%!test %# Stats must add further elements in vsol
%!  vopt = odeset ('Stats', 'on');
%!  vsol = ode45d (@fexp, [0 2.5], [1; 0], 1, [1; 0], vopt);
%!  assert (isfield (vsol, 'stats'));
%!  assert (isfield (vsol.stats, 'nsteps'));
%!test %# InitialStep option
%!  vopt = odeset ('InitialStep', 1e-8);
%!  vsol = ode45d (@fexp, [0 5], [1; 0], 1, [1; 0], vopt);
%!  assert ([vsol.x(end), vsol.y(end,:)], [5, fref], 0.5);
%!test %# MaxStep option
%!  vopt = odeset ('MaxStep', 1e-2);
%!  vsol = ode45d (@fexp, [0 5], [1; 0], 1, [1; 0], vopt);
%!  assert ([vsol.x(end), vsol.y(end,:)], [5, fref], 0.5);
%!test %# Events option add further elements in vsol
%!  vopt = odeset ('Events', @feve);
%!  vsol = ode45d (@fexp, [0 5], [1; 0], 1, [1; 0], vopt);
%!  assert (isfield (vsol, 'ie'));
%!  assert (vsol.ie, [1; 1]);
%!  assert (isfield (vsol, 'xe'));
%!  assert (isfield (vsol, 'ye'));
%!test %# Events option, now stop integration
%!  warning ('off', 'OdePkg:HideWarning');
%!  vopt = odeset ('Events', @fevn, 'NormControl', 'on');
%!  vsol = ode45d (@fexp, [0 5], [1; 0], 1, [1; 0], vopt);
%!  assert ([vsol.ie, vsol.xe, vsol.ye], ...
%!    [1.0000, 2.9219, -0.2127, -0.2671], 0.5);
%!test %# Events option, five output arguments
%!  vopt = odeset ('Events', @fevn, 'NormControl', 'on');
%!  [vt, vy, vxe, vye, vie] = ode45d (@fexp, [0 5], [1; 0], 1, [1; 0], vopt);
%!  assert ([vie, vxe, vye], ...
%!    [1.0000, 2.9219, -0.2127, -0.2671], 0.5);
%!
%! %# test for Jacobian option is missing
%! %# test for Jacobian (being a sparse matrix) is missing
%! %# test for JPattern option is missing
%! %# test for Vectorized option is missing
%! %# test for NewtonTol option is missing
%! %# test for MaxNewtonIterations option is missing
%!
%!test %# Mass option as function
%!  vopt = odeset ('Mass', eye (2,2));
%!  vsol = ode45d (@fexp, [0 5], [1; 0], 1, [1; 0], vopt);
%!  assert ([vsol.x(end), vsol.y(end,:)], [5, fref], 0.5);
%!test %# Mass option as matrix
%!  vopt = odeset ('Mass', eye (2,2));
%!  vsol = ode45d (@fexp, [0 5], [1; 0], 1, [1; 0], vopt);
%!  assert ([vsol.x(end), vsol.y(end,:)], [5, fref], 0.5);
%!test %# Mass option as sparse matrix
%!  vopt = odeset ('Mass', sparse (eye (2,2)));
%!  vsol = ode45d (@fexp, [0 5], [1; 0], 1, [1; 0], vopt);
%!  assert ([vsol.x(end), vsol.y(end,:)], [5, fref], 0.5);
%!test %# Mass option as function and sparse matrix
%!  vopt = odeset ('Mass', @fmsa);
%!  vsol = ode45d (@fexp, [0 5], [1; 0], 1, [1; 0], vopt);
%!  assert ([vsol.x(end), vsol.y(end,:)], [5, fref], 0.5);
%!test %# Mass option as function and MStateDependence
%!  vopt = odeset ('Mass', @fmas, 'MStateDependence', 'strong');
%!  vsol = ode45d (@fexp, [0 5], [1; 0], 1, [1; 0], vopt);
%!  assert ([vsol.x(end), vsol.y(end,:)], [5, fref], 0.5);
%!test %# Set BDF option to something else than default
%!  vopt = odeset ('BDF', 'on');
%!  [vt, vy] = ode45d (@fexp, [0 5], [1; 0], 1, [1; 0], vopt);
%!  assert ([vt(end), vy(end,:)], [5, fref], 0.5);
%!
%! %# test for MvPattern option is missing
%! %# test for InitialSlope option is missing
%! %# test for MaxOrder option is missing
%!
%!  warning ('on', 'OdePkg:InvalidOption');

%# Local Variables: ***
%# mode: octave ***
%# End: ***
