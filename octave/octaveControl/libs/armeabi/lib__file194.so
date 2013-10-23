## Copyright (C) 2011   Lukas F. Reichlin
##
## This file is part of LTI Syncope.
##
## LTI Syncope is free software: you can redistribute it and/or modify
## it under the terms of the GNU General Public License as published by
## the Free Software Foundation, either version 3 of the License, or
## (at your option) any later version.
##
## LTI Syncope is distributed in the hope that it will be useful,
## but WITHOUT ANY WARRANTY; without even the implied warranty of
## MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
## GNU General Public License for more details.
##
## You should have received a copy of the GNU General Public License
## along with LTI Syncope.  If not, see <http://www.gnu.org/licenses/>.

## -*- texinfo -*-
## @deftypefn{Function File} {@var{opt} =} options (@var{"key1"}, @var{value1}, @var{"key2"}, @var{value2}, @dots{})
## Create options struct @var{opt} from a number of key and value pairs.
## For use with order reduction commands.
##
## @strong{Inputs}
## @table @var
## @item key, property
## The name of the property.
## @item value
## The value of the property.
## @end table
##
## @strong{Outputs}
## @table @var
## @item opt
## Struct with fields for each key.
## @end table
##
## @strong{Example}
## @example
## @group
## octave:1> opt = options ("method", "spa", "tol", 1e-6)
## opt =
## 
##   scalar structure containing the fields:
## 
##     method = spa
##     tol =  1.0000e-06
## 
## @end group
## @end example
## @example
## @group
## octave:2> save filename opt
## octave:3> # save the struct 'opt' to file 'filename' for later use
## octave:4> load filename
## octave:5> # load struct 'opt' from file 'filename'
## @end group
## @end example
##
## @end deftypefn

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: November 2011
## Version: 0.1

function opt = options (varargin)

  if (nargin == 0)
    print_usage ();
  endif

  if (rem (nargin, 2))
    error ("options: properties and values must come in pairs");
  endif

  ## alternative: opt = struct (varargin{:});
  
  key = reshape (varargin(1:2:end-1), [], 1);
  val = reshape (varargin(2:2:end), [], 1);

  opt = cell2struct (val, key, 1);
  opt = orderfields (opt);

endfunction