## Copyright (C) 2010,2011 Philip Nienhuis <prnienhuis@users.sf.net>
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

## -*- texinfo -*-
## @deftypefn {Function File} [ @var{type-array} ] = spsh_prstype ( @var{iarray}, @var{rowsize}, @var{colsize}, @var{celltypes}, @var{options})
## (Internal function) Return rectangular array with codes for cell types in rectangular input cell array @var{iarray}.
## Codes are contained in input vector in order of Numeric, Boolean, Text, Formula and Empty, resp.
##
## spsh_prstype should not be invoked directly but rather through oct2xls or oct2ods.
##
## Example:
##
## @example
##   typarr = spsh_chkrange (cellarray, nr, nc, ctypes, options);
## @end example
##
## @end deftypefn

## Author: Philip Nienhuis, <prnienhuis@users.sf.net>
## Created: 2010-08-02
## Updates:
## 2010-08-25 Corrected help text (square -> rectangular; stressed "internal" use)
## 2011-04-21 Formulas now don't need closing ")" (e.g., =A1+B1 is OK as well)
##     ''     Formula ptrs in output arg now OK (cellfun(@(x).... skips empty cells)
## 2012-10-23 Style fixes

function [ typearr ] = spsh_prstype (obj, nrows, ncols, ctype, spsh_opts)

	## ctype index:
	## 1 = numeric
	## 2 = boolean
	## 3 = text
	## 4 = formula 
	## 5 = error / NaN / empty

	typearr = ctype(5) * ones (nrows, ncols);		## type "EMPTY", provisionally
	obj2 = cell (size (obj));							      ## Temporary storage for strings

	txtptr = cellfun ('isclass', obj, 'char');  ## type "STRING" replaced by "NUMERIC"
	obj2(txtptr) = obj(txtptr); 
  obj(txtptr) = ctype(3);	                    ## Save strings in a safe place

	emptr = cellfun ("isempty", obj);
	obj(emptr) = ctype(5);								      ## Set empty cells to NUMERIC

	lptr = cellfun ("islogical" , obj);		      ## Find logicals...
	obj2(lptr) = obj(lptr);								      ## .. and set them to BOOLEAN

	ptr = cellfun ("isnan", obj);					      ## Find NaNs & set to BLANK
	typearr(ptr) = ctype(5); 
  typearr(~ptr) = ctype(1);	                  ## All other cells are now numeric

	obj(txtptr) = obj2(txtptr);						      ## Copy strings back into place
	obj(lptr) = obj2(lptr);								      ## Same for logicals
	obj(emptr) = -1;									          ## Add in a filler value for empty cells

	typearr(txtptr) = ctype(3);						      ## ...and clean up 
	typearr(emptr) = ctype(5);						      ## EMPTY
	typearr(lptr) = ctype(2);							      ## BOOLEAN

	if ~(spsh_opts.formulas_as_text)
		## Find formulas (designated by a string starting with "=" and ending in ")")
		## fptr = cellfun (@(x) ischar (x) && strncmp (x, "=", 1) 
    ##                                 && strncmp (x(end:end), ")", 1), obj);
		## Find formulas (designated by a string starting with "=")
		fptr = cellfun (@(x) ischar (x) && strncmp (x, "=", 1), obj);
		typearr(fptr) = ctype(4);						      ## FORMULA
	endif

endfunction

%!test
%! tstobj = {1.5, true, []; 'Text1', '=A1+B1', '=SQRT(A1)'; NaN, {}, 0};
%! typarr = spsh_prstype (tstobj, 3, 3, [1 2 3 4 5], struct ("formulas_as_text", 0));
%! assert (typarr, [1 2 5; 3 4 4; 5 5 1]);

%!test
%! tstobj = {1.5, true, []; 'Text1', '=A1+B1', '=SQRT(A1)'; NaN, {}, 0};
%! typarr = spsh_prstype (tstobj, 3, 3, [1 2 3 4 5], struct ("formulas_as_text", 1));
%! assert (typarr, [1 2 5; 3 3 3; 5 5 1]);
