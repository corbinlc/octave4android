## Copyright (C) 2008 Alexander Barth <barth.alexander@gmail.com>
##
## This program is free software; you can redistribute it and/or modify
## it under the terms of the GNU General Public License as published by
## the Free Software Foundation; either version 2 of the License, or
## (at your option) any later version.
##
## This program is distributed in the hope that it will be useful,
## but WITHOUT ANY WARRANTY; without even the implied warranty of
## MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
## GNU General Public License for more details.
##
## You should have received a copy of the GNU General Public License
## along with this program; if not, write to the Free Software
## Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA

## -*- texinfo -*-
## @deftypefn {Function File} {[@var{lato},@var{lono}] = } reckon(@var{lat},@var{lon},@var{range},@var{azimuth})
## @deftypefnx {Function File} {[@var{lato},@var{lono}] = } reckon(@var{lat},@var{lon},@var{range},@var{azimuth},@var{units})
## Compute the coordinates of the end-point of a displacement on a 
## sphere. @var{lat},@var{lon} are the coordinates of the starting point, @var{range} 
## is the covered distance of the displacements along a great circle and
## @var{azimuth} is the direction of the displacement relative to the North.
## The units of all input and output parameters can be either 'degrees' (default)
## or 'radians'.
##
## This function can also be used to define a spherical coordinate system
## with rotated poles.
## @end deftypefn

## Author: Alexander Barth <barth.alexander@gmail.com>

function [lato,lono] = reckon(varargin);

  units = "degrees";

  [reg,prop] = parseparams(varargin);
  
  ## Input checking
  if length(reg) != 4
    print_usage ();
  endif

  sz = [1 1];

  for i=1:4
    if !isscalar(reg{i})
      sz = size(reg{i});
      break;
    endif
  endfor

  for i=1:4
    if isscalar(reg{i})
      reg{i} = repmat(reg{i},sz);
    elseif !isequal(size(reg{i}),sz)
      print_usage();
    endif
  endfor

  if length(prop) == 1    
    units = prop{1};
  elseif length(prop) > 1
    error("reckon: wrong number of type of arguments");
  end

  lat = reg{1};
  lon = reg{2};
  range = reg{3};
  azimuth = reg{4};
  
  if strcmp(units,"degrees")
    d = pi/180;
  elseif strcmp(units,"radians")
    d = 1;
  else
    error(["reckon: unknown units: " units]);
  endif

  ## convert to radians

  lat = lat*d;
  lon = lon*d;
  range = range*d;
  azimuth = azimuth*d;

  lato = pi/2 - acos(sin(lat).*cos(range) + cos(lat).*sin(range).*cos(azimuth));

  cos_gamma = (cos(range) - sin(lato).*sin(lat))./(cos(lato).*cos(lat));
  sin_gamma = sin(azimuth).*sin(range)./cos(lato);

  gamma = atan2(sin_gamma,cos_gamma);

  lono = lon + gamma;

  ## bring the lono in the interval [-pi pi[

  lono = mod(lono+pi,2*pi)-pi;

  ## convert to degrees

  lono = lono/d;
  lato = lato/d;

endfunction


%!test
%! [lato,lono] = reckon(30,-80,20,40);
%! assert(lato,44.16661401448592,1e-10)
%! assert(lono,-62.15251496909770,1e-10)

%!test
%! [lato,lono] = reckon(-30,80,[5 10],[40 45]);
%! assert(lato,[-26.12155703039504 -22.70996703614572],1e-10)
%! assert(lono,[83.57732793979254  87.64920016442251],1e-10)

%!test
%! [lato,lono] = reckon([-30 31],[80 81],[5 10],[40 45]);
%! assert(lato,[-26.12155703039504  37.76782079033356],1e-10)
%! assert(lono,[83.57732793979254  89.93590456974810],1e-10)
