## Copyright (C) 2009,2010,2011,2012 by Philip Nienhuis <prnienhuis@users.sf.net>
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
## @deftypefn {Function File} [@var{xlso}, @var{status}] = __COM_oct2spsh__ (@var{obj}, @var{xlsi})
## @deftypefnx {Function File} [@var{xlso}, @var{status}] = __COM_oct2spsh__ (@var{obj}, @var{xlsi}, @var{wsh})
## @deftypefnx {Function File} [@var{xlso}, @var{status}] = __COM_oct2spsh__ (@var{obj}, @var{xlsi}, @var{wsh}, @var{top_left_cell})
## Save matrix @var{obj} into worksheet @var{wsh} in Excel file pointed
## to in struct @var{xlsi}. All elements of @var{obj} are converted into
## Excel cells, starting at cell @var{top_left_cell}. Return argument
## @var{xlso} is @var{xlsi} with updated fields.
##
## __COM_oct2spsh__ should not be invoked directly but rather through oct2xls.
##
## Excel invocations may be left running invisibly in case of COM errors.
##
## Example:
##
## @example
##   xls = __COM_oct2spsh__ (rand (10, 15), xls, 'Third_sheet', 'BF24');
## @end example
##
## @seealso {oct2xls, xls2oct, xlsopen, xlsclose, xlswrite, xlsread, xls2com2oct}
##
## @end deftypefn

## Author: Philip Nienhuis  (originally based on mat2xls by Michael Goffioul)
## Rewritten: 2009-09-26
## Updates:
## 2009-12-11
## 2010-01-12 Fixed typearr sorting out (was only 1-dim & braces rather than parens))
##            Set cells corresponding to empty array cells empty (cf. Matlab)
## 2010-01-13 Removed an extraneous statement used for debugging 
##            I plan look at it when octave v.3.4 is about to arrive.
## 2010-08-01 Added checks for input array size vs check on capacity
##     ''     Changed topleft arg into range arg (just topleft still recognized)
##     ''     Some code cleanup
##     ''     Added option for formula input as text string
## 2010-08-01 Added range vs. array size vs. capacity checks
## 2010-08-03 Moved range checks and type array parsing to separate functions
## 2010-10-20 Bug fix removing new empty sheets in new workbook that haven't been 
##            created in the first place due to Excel setting (thanks Ian Journeaux)
##     ''     Changed range use in COM transfer call
## 2010-10-21 Improved file change tracking (var xls.changed)
## 2010-10-24 Fixed bug introduced in above fix: for loops have no stride param,
##     ''     replaced by while loop
##     ''     Added check for "live" ActiveX server
## 2010-11-12 Moved ptr struct check into main func
## 2012-01-26 Fixed "seealso" help string
## 2012-02-27 Copyright strings updated
## 2012-10-12 Renamed & moved into ./private
## 2012-10-24 Style fixes
## 2012-12-21 Search for exact match when searching sheet names

function [ xls, status ] = __COM_oct2spsh__ (obj, xls, wsh, crange, spsh_opts)

	## Preliminary sanity checks
  ## FIXME - Excel can write to much more than .xls/.xlsx (but not to all
  ##         formats with multiple sheets):
  ##         .wk1 .wk3 .wk4 .csv .pxl .html .dbf .txt .prn .wks .wq1 .dif
	if (~strmatch (lower (xls.filename(end-4:end)), '.xls'))
		error ("COM interface can only write to Excel .xls or .xlsx files")
	endif
	if (isnumeric (wsh))
		if (wsh < 1) error ("Illegal worksheet number: %i\n", wsh); endif
	elseif (size (wsh, 2) > 31) 
		error ("Illegal worksheet name - too long")
	endif
	## Check to see if ActiveX is still alive
	try
		wb_cnt = xls.workbook.Worksheets.count;
	catch
		error ("ActiveX invocation in file ptr struct seems non-functional");
	end_try_catch

	## define some constants not yet in __COM__.cc
	xlWorksheet = -4167; ## xlChart= 4;
	## scratch vars
	status = 0;

	## Parse date ranges  
	[nr, nc] = size (obj);
	[topleft, nrows, ncols, trow, lcol] = ...
                    spsh_chkrange (crange, nr, nc, xls.xtype, xls.filename);
	lowerright = calccelladdress (trow + nrows - 1, lcol + ncols - 1);
	crange = [topleft ":" lowerright];
	if (nrows < nr || ncols < nc)
		warning ("Array truncated to fit in range");
		obj = obj(1:nrows, 1:ncols);
	endif
	
	## Cleanup NaNs. Find where they are and mark as empty
	ctype = [0 1 2 3 4];							## Numeric Boolean Text Formula Empty
	typearr = spsh_prstype (obj, nrows, ncols, ctype, spsh_opts);
	## Make cells now indicated to be empty, empty
	fptr = ~(4 * (ones (size (typearr))) .- typearr);
	obj(fptr) = cellfun (@(x) [], obj(fptr), "Uniformoutput",  false);

	if (spsh_opts.formulas_as_text)
		## find formulas (designated by a string starting with "=" and ending in ")")
		fptr = cellfun (@(x) ischar (x) && strncmp (x, "=", 1) ...
                                    && strncmp (x(end:end), ")", 1), obj);
		## ... and add leading "'" character
		obj(fptr) = cellfun (@(x) ["'" x], obj(fptr), "Uniformoutput", false); 
	endif
	clear fptr;

	if (xls.changed < 3) 
		## Existing file OR a new file with data added in a previous oct2xls call.
		## Some involved investigation is needed to preserve
		## existing data that shouldn't be touched.
		##
		## See if desired *sheet* name exists. 
		old_sh = 0;
		ws_cnt = xls.workbook.Sheets.count;
		if (isnumeric (wsh))
			if (wsh <= ws_cnt)
				## Here we check for sheet *position* in the sheet stack
				## rather than a name like "Sheet<Number>" 
				old_sh = wsh;
			else
				## wsh > nr of sheets; proposed new sheet name.
				## This sheet name can already exist to the left in the sheet stack!
				shnm = sprintf ("Sheet%d", wsh); shnm1 = shnm;
			endif
		endif
		if (~old_sh)
			## Check if the requested (or proposed) sheet already exists
			## COM objects are not OO (yet?), so we need a WHILE loop 
			ii = 1; jj = 1;
			while ((ii <= ws_cnt) && ~old_sh)
				## Get existing sheet names one by one
				sh_name = xls.workbook.Sheets(ii).name;
				if (~isnumeric (wsh) && strcmp (sh_name, wsh))
					## ...and check with requested sheet *name*...
					old_sh = ii;
				elseif (isnumeric (wsh) && strcmp (sh_name, shnm))
					## ... or proposed new sheet name (corresp. to requested sheet *number*)
					shnm = [shnm "_"];
					ii = 0;			## Also check if this new augmented sheet name exists...
					if (strmatch (shnm1, sh_name, "exact")), jj++; endif
					if (jj > 5) 	## ... but not unlimited times...
						error (sprintf ...
                (" > 5 sheets named [_]Sheet%d already present!", wsh));
					endif
				endif
				++ii;
			endwhile
		endif

		if (old_sh) 
			## Requested sheet exists. Check if it is a *work*sheet
			if ~(xls.workbook.Sheets(old_sh).Type == xlWorksheet)
				## Error as you can't write data to Chart sheet
				error (sprintf ("Existing sheet '%s' is not type worksheet.", wsh));
			else
				## Simply point to the relevant sheet
				sh = xls.workbook.Worksheets (old_sh);
			endif
		else
			## Add a new worksheet. Earlier it was checked whether this is safe
			try
				sh = xls.workbook.Worksheets.Add ();
			catch
				error (sprintf ("Cannot add new worksheet to file %s\n", xls.filename));
			end_try_catch
			if (~isnumeric (wsh)) 
				sh.Name = wsh;
			else
				sh.Name = shnm;
				printf ("Writing to worksheet %s\n", shnm);
			endif
			## Prepare to move new sheet to right of the worksheet stack anyway
			ws_cnt = xls.workbook.Worksheets.count;			## New count needed
			## Find where Excel has left it. We have to, depends on Excel version :-(

			ii = 1;
			while ((ii < ws_cnt+1) && ~strcmp (sh.Name, xls.workbook.Worksheets(ii).Name) == 1)
				++ii;
			endwhile
			## Excel can't move it beyond the current last one, so we need a trick.
			## First move it to just before the last one....
			xls.workbook.Worksheets(ii).Move (before = xls.workbook.Worksheets(ws_cnt));
			## ....then move the last one before the new sheet.
			xls.workbook.Worksheets (ws_cnt).Move (before = xls.workbook.Worksheets(ws_cnt - 1));
		endif

	else
		## The easy case: a new Excel file. Workbook was created in xlsopen. 

		## Delete empty non-used sheets, last one first
		xls.app.Application.DisplayAlerts = 0;
		ii = xls.workbook.Sheets.count;
		while (ii > 1)
			xls.workbook.Worksheets(ii).Delete();
			--ii;
		endwhile
		xls.app.Application.DisplayAlerts = 1;

		## Write to first worksheet:
		sh = xls.workbook.Worksheets (1);
		## Rename the sheet
		if (isnumeric (wsh))
			sh.Name = sprintf ("Sheet%i", wsh);
		else
			sh.Name = wsh;
		endif
		xls.changed = 2;			## 3 => 2
	endif

	## MG's original part.
	## Save object in Excel sheet, starting at cell top_left_cell
	if (~isempty(obj))
		r = sh.Range (crange);
		try
			r.Value = obj;
		catch
			error (sprintf ("Cannot add data to worksheet %s in file %s\n",...
                      sh.Name, xls.filename));
		end_try_catch
		delete (r);
	endif

	## If we get here, all went OK
	status = 1;
	xls.changed = max (xls.changed, 1);			## If it was 2, preserve it.
	
endfunction
