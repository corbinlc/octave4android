## Copyright (C) 2009,2010,2011,2012 Philip Nienhuis <pr.nienhuis at users.sf.net>
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
## @deftypefn {Function File} @var{rstatus} = odswrite (@var{filename}, @var{arr})
## @deftypefnx {Function File} @var{rstatus} = odswrite (@var{filename}, @var{arr}, @var{wsh})
## @deftypefnx {Function File} @var{rstatus} = odswrite (@var{filename}, @var{arr}, @var{wsh}, @var{range})
## @deftypefnx {Function File} @var{rstatus} = odswrite (@var{filename}, @var{arr}, @var{wsh}, @var{range}, @var{reqintf})
## Add data in 1D/2D array @var{arr} into sheet @var{wsh} in
## OpenOffice_org Calc spreadsheet file @var{filename} in cell range @var{range}.
##
## @var{rstatus} returns 1 if write succeeded, 0 otherwise.
##
## @var{filename} must be a valid .ods OpenOffice.org file name (including
## file name extension). If @var{filename} does not contain any directory
## path, the file is saved in the current directory.
##
## @var{arr} can be any 1D or 2D array containing numerical or character
## data (cellstr) except complex. Mixed numeric/text arrays can only be
## cell arrays.
##
## @var{wsh} can be a number or string. In case of a not yet existing
## OpenOffice.org spreadsheet, the first sheet will be used & named
## according to @var{wsh} - no extra empty sheets are created.
## In case of existing files, some checks are made for existing sheet
## names or numbers, or whether @var{wsh} refers to an existing sheet with
## a type other than sheet (e.g., chart).
## When new sheets are to be added to the spreadsheet file, they are
## inserted to the right of all existing sheets. The pointer to the
## "active" sheet (shown when OpenOffice.org Calc opens the file) remains
## untouched.
##
## @var{range} is expected to be a regular spreadsheet range.
## Data is added to the sheet; existing data in the requested
## range will be overwritten.
## Array @var{arr} will be clipped at the right and/or bottom if its size
## is bigger than can be accommodated in @var{range}.
## If @var{arr} is smaller than the @var{range} allows, it is placed
## in the top left rectangle of @var{range} and cell values outside that
## rectangle will be untouched.
##
## If @var{range} contains merged cells, only the elements of @var{arr}
## corresponding to the top or left Calc cells of those merged cells
## will be written, other array cells corresponding to that cell will be
## ignored.
##
## The optional last argument @var{reqintf} can be used to override 
## the automatic selection by odswrite of one interface out of the
## supported ones: Java/ODFtooolkit ('OTK'), Java/jOpenDocument ('JOD'),
## or Java/OpenOffice.org ('UNO').
##
## odswrite is a mere wrapper for various scripts which find out what
## ODS interface to use (ODF toolkit or jOpenDocument) plus code to mimic
## the other brand's syntax. For each call to odswrite such an interface
## must be started and possibly an ODS file loaded. When writing to multiple
## ranges and/or worksheets in the same ODS file, a speed bonus can be
## obtained by invoking those scripts (odsopen / octods / .... / odsclose)
## directly.
##
## Example:
##
## @example
##   status = odswrite ('test4.ods', 'arr', 'Eight_sheet', 'C3:AB40');
##   (which adds the contents of array arr (any type) to range C3:AB40 
##   in sheet 'Eight_sheet' in file test4.ods and returns a logical 
##   True (= numerical 1) in status if al went well) 
## @end example
##
## @seealso {odsread, oct2ods, ods2oct, odsopen, odsclose, odsfinfo}
##
## @end deftypefn

## Author: Philip Nienhuis
## Created: 2009-12-14
## Updates:
## 2010-01-14 Finalized write support tru ODS toolkit
## 2010-01-15 Added texinfo help
## 2010-08-25 Removed text about 31 char limit for sheet names (invalid)
## 2010-11-13 Added note about required file extension in help text
## 2010-11-13 Added some input arg checks
## 2011-09-08 Minor filename error text adaptation
## 2012-01-26 Fixed "seealso" help string
## 2012-02-20 Fixed range parameter to be default empty string rather than empty numeral
## 2010-03-07 Updated texinfo help text
## 2012-06-08 Tabs replaced by double space
## 2012-10-24 Style fixes

function [ rstatus ] = odswrite (filename, data, wsh=1, crange="", reqintf=[])

  ## Input validity checks
  if (nargin < 2)
    usage ("Insufficient arguments - see 'help odswrite'");
  elseif (~ischar (filename) || isempty (findstr (".ods", tolower (filename))))
    error ("First argument must be a filename (incl. .ods suffix for OTK & JOD)");
  endif

  ods = odsopen (filename, 1, reqintf);

  if (~isempty (ods)) 
    [ods, rstatus] = oct2ods (data, ods, wsh, crange);

    ## If rstatus was not OK, reset change indicator in ods pointer
    if (~rstatus)
      ods.changed = rstatus;
      warning ("odswrite: data transfer errors, file not rewritten");
    endif

    ods = odsclose (ods);

  endif

endfunction
