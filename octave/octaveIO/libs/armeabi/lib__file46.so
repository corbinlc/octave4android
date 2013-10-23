## Copyright (C) 2010,2011,2012 Philip Nienhuis, pr.nienhuis -at- users.sf.net
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

## __OTK_getusedrange__ - get used range from ODS spreadsheet using ODF Toolkit

## Author: Philip Nienhuis <philip@JVC741>
## Created: 2010-03-18 (First usable version)
## Updates:
## 2010-08-24 Support for odfdom (ODF Toolkit) 0.8.6 checked; we still need 
##            => TableTable API because 0.8.6 is fooled by empty lowermost
##            filler rows comprising number-rows-repeated table attribute :-(
##     ''     Indentation changed from tab to double space
## 2010-11-13 Catched jOpenDocument bug (1.2bx) where string cells have no office:value-type
##            attrib set (by JOD). Somehow OTK is more robust as it catches these cells;
##            Currently this fix is just commented.
## 2011-06-06 Fixed wrong if clause for finding last filler cells (L.160 & L.176)
## 2011-09-12 Support for odfdom-0.8.7 added (API change for XPATH)
## 2012-06-08 Support for odsfdom-0.8.8-incubator
## 2012-10-12 Renamed & moved into ./private
## 2012-10-24 Style fixes

function [ trow, lrow, lcol, rcol ] = __OTK_getusedrange__ (ods, ii)

  odfcont = ods.workbook;  ## Local copy just in case

  if (isfield (ods, "odfvsn"))
    if (strcmp (ods.odfvsn, "0.8.6") || strcmp (ods.odfvsn, "0.7.5"))
      xpath = ods.app.getXPath;
    else
      ## API changed in odfdom-0.8.7
      xpath = ods.workbook.getXPath;
    endif
  else
    error ("ODS file ptr struct for OTK interface seems broken.");
  endif
  
  ## Create an instance of type NODESET for use in subsequent statement
  NODESET = java_get ("javax.xml.xpath.XPathConstants", "NODESET");
  ## Get table-rows in sheet no. wsh. Sheet count = 1-based (!)
  str = sprintf ("//table:table[%d]/table:table-row", ii);
  sh = xpath.evaluate (str, odfcont, NODESET);
  nr_of_trows = sh.getLength();

  jj = 0;                  ## Table row counter
  trow = 0; drows = 0;     ## Top data row, actual data row range
  nrows = 0; reprows = 0;  ## Scratch counter
  rcol = 0; lcol = 1024;   ## Rightmost and leftmost data column

  while jj < nr_of_trows
    row = sh.item(jj);
    ## Check for data rows
    rw_char = char (row) (1:min(500, length (char (row))));
    if (findstr ("office:value-type", rw_char) || findstr ("<text:", rw_char))
      ++drows;
      ## Check for uppermost data row
      if (~trow) 
        trow = nrows + 1;
        nrows = 0;
      else
        drows = drows + reprows;
        reprows = 0;
      endif

      ## Get leftmost cell column number
      lcell = row.getFirstChild ();
      cl_char = char (lcell);
      ## Swap the following lines into comment to catch a jOpenDocument bug which foobars OTK
      ## (JOD doesn't set <office:value-type='string'> attribute when writing strings
      ##if  (isempty (findstr ("office:value-type", cl_char)) ...
      ##  || isempty (findstr ("<text:", cl_char)))
      if (isempty (findstr ("office:value-type", cl_char)))
        lcol = min (lcol, lcell.getTableNumberColumnsRepeatedAttribute () + 1);
      else
        lcol = 1;
      endif

      ## if rcol is already 1024 no more exploring for rightmost column is needed
      if ~(rcol == 1024)
        ## Get rightmost cell column number by counting....
        rc = 0;
        for kk=1:row.getLength()
          lcell = row.item(kk - 1);
          rc = rc + lcell.getTableNumberColumnsRepeatedAttribute ();
        endfor
        ## Watch out for filler tablecells
        if (isempty (findstr ("office:value-type", char (lcell)))... 
            || isempty (findstr ("<text:", char (lcell))))
          rc = rc - lcell.getTableNumberColumnsRepeatedAttribute ();
        endif
        rcol = max (rcol, rc);
      endif
    else
      ## Check for repeated tablerows
      nrows = nrows + row.getTableNumberRowsRepeatedAttribute ();
      if (trow)
        reprows = reprows + row.getTableNumberRowsRepeatedAttribute ();
      endif
    endif
    ++jj;
  endwhile

  if (trow)
    lrow = trow + drows - 1;
  else
    ## Empty sheet
    lrow = 0; lcol = 0; rcol = 0;
  endif

endfunction
