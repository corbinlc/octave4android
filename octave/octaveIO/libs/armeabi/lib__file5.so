## Copyright (C) 2003 Tomer Altman <taltman@lbl.gov>
##
## This program is free software; you can redistribute it and/or modify it under
## the terms of the GNU General Public License as published by the Free Software
## Foundation; either version 3 of the License, or (at your option) any later
## version.
##
## This program is distributed in the hope that it will be useful, but WITHOUT
## ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
## FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
## details.
##
## You should have received a copy of the GNU General Public License along with
## this program; if not, see <http://www.gnu.org/licenses/>.

## append_save M-file function
##
## Objective: be able to add variables to existing save files. Works for
## all the types of save files that "save" supports.
## 
## Input: 
## 1) A string which specifies the existing save file.
## 2) The options you need to pass to the 'save' function to save to the
## file type that you want.
## 3) A 1x2 cell, with the first element being a string representation
## of the variable/symbol that you're trying to add, followed by the
## actual variable/symbol itself.
## 4) Any number of additional 1x2 cells, following the same format as
## the 3rd argument specified immediately before this one.
##
## Output:
## Currently, none. But there might be some debugging / error-code
## messages in the future.
##
## Example:
## octave> B = ones(2,2);
## octave> append_save( "test.txt", "-binary", {"B", B } )


function [ return_value ] = append_save ( filename,
					  option,
					  var_val_cell,
					  varargin )

  ## Input checking:

  if ( nargin < 3 )
    error("append_save: needs three arguments.");
  elseif ( !ischar(filename) )
    error("append_save: filename must be a string.");
  elseif ( !ischar(option) )
    error("append_save: option must be a string." );
  elseif ( !iscell(var_val_cell) )
    error("append_save: variable-value pairs must be cells.")
  elseif ( nargin > 3 )
    for i=1:(nargin-3)
      current_cell = varargin(i);
      if ( !iscell(current_cell) )
	error("append_save: variable-value pairs must be cells.");
      elseif ( ( columns( current_cell ) != 2 ) 
	        || ( rows( current_cell ) != 1 ) )
	error("append_save: variable-value pairs must be 1x2 cells.")
      elseif ( !ischar(current_cell{1} ) )
	error("append_save: variable in pair must be a string." )
      endif
    endfor
  endif

  ## First step: load into the environment what is already stuffed in
  ## the target file. Then, add their name to the list for "save".

  env1 = who;
  
  eval([ "load -force ", \
	option, " ",    \
	filename ] );

  env2 = who;
  
  num_orig_vars = rows(env1);

				# Not really 'current' env...
  num_current_vars = rows(env2);

  num_new_vars = num_current_vars - num_orig_vars;

  var_str = "";

  ## This double 'for loop' weeds out only the loaded vars for
  ## inclusion.

  if ( num_new_vars )

    for i=1:num_current_vars

      current_var = env2{i,1};

      old_bool = 0;

      for j=1:num_orig_vars

	if ( strcmp( env1{j,1}, env2{i,1} ) 
	     ||
	     strcmp( env2{i,1}, "env1" ) )

	  old_bool = 1;

	endif

      endfor

      if ( old_bool == 0 )

	var_name = env2{i,1};

	var_str = [ var_str, " ", var_name, " " ];

      endif

    endfor

  endif

  
  ## Second step: load into the environment the variable pairs. Then,
  ## add the name to the string for "save".

  var_name = var_val_cell{1};

  var_val = var_val_cell{2};

  temp = var_val;

  eval([ var_name, "=temp;" ]);

  var_str = [ var_str, " ", var_name, " " ];

  ## Third step: do the same as step two, but loop through the possible
  ## variable arguments.

  for i=1:(nargin-3)

    current_cell = varargin(i);

    var_name = current_cell{1};
    
    var_val = current_cell{2};
    
    temp = var_val;
    
    eval([ var_name, "=temp;" ]);
    
    var_str = [ var_str, " ", var_name, " " ];

    var_str

  endfor

  ## Finally, save all of the variables into the target file:

  eval( [ "save ", \
	   option, " ",  \
	   filename, " ", var_str; ] );

endfunction
