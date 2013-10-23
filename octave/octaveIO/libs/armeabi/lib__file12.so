## Copyright (C) 2012 Philip Nienhuis <pr.nienhuis at users.sf.net>
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

## (Internal function) Check proper operation of XLS spreadsheet scripts.
## Before running, a character variable 'intf' should be initialized with
## a value of 'com', 'poi', 'jxl', 'oxs', or 'uno'.

## Author: Philip Nienhuis
## Created: 2012-02-25
## Updates:
## 2012-06-06 Adapted to COM implementation for "formulas_as_text" option
## 2012-09-04 Added delay between LibreOffice calls to avoid lockups with UNO


printf ("\nTesting .xls interface %s ...\n", intf);

isuno = false; dly = 0.25;
intf2 = intf;
if (strcmp (lower (intf), 'oxs'))
  printf ("OXS interface has no write support enabled - writing is done with POI.\n");
  intf2 = 'com'; 
elseif (strcmp (lower (intf), 'uno'));
  isuno = true;
endif

## 1. Initialize test arrays
printf ("\n 1. Initialize arrays.\n");
arr1 = [ 1 2; 3 4.5];
arr2 = {'r1c1', '=c2+d2'; '', 'r2c2'; true, -83.4};
opts = struct ("formulas_as_text", 0);

## 2. Insert empty sheet
printf ("\n 2. Insert first empty sheet.\n");
xlswrite ('io-test.xls', {''}, 'EmptySheet', 'b4', intf2); if (isuno); sleep (dly); endif

## 3. Add data to test sheet
printf ("\n 3. Add data to test sheet.\n");
xlswrite ('io-test.xls', arr1, 'Testsheet', 'c2:d3', intf2); if (isuno); sleep (dly); endif
xlswrite ('io-test.xls', arr2, 'Testsheet', 'd4:z20', intf2); if (isuno); sleep (dly); endif

## 4. Insert another sheet
printf ("\n 4. Add another sheet with just one number in A1.\n");
xlswrite ('io-test.xls', [1], 'JustOne', 'A1', intf2); if (isuno); sleep (dly); endif

## 5. Get sheet info & find sheet with data and data range
printf ("\n 5. Explore sheet info.\n");
[~, shts] = xlsfinfo ('io-test.xls', intf); if (isuno); sleep (dly); endif
shnr = strmatch ('Testsheet', shts(:, 1));                      # Note case!
crange = shts{shnr, 2};

## 6. Read data back
printf ("\n 6. Read data back.\n");
[num, txt, raw, lims] = xlsread ('io-test.xls', shnr, crange, intf); if (isuno); sleep (dly); endif

## 7. Here come the tests, part 1
printf ("\n 7. Tests part 1 (basic I/O):\n");
try
  printf ("    ...Numeric array... ");
  assert (num(1:2, 1:3), [1, 2, NaN; 3, 4.5, NaN], 1e-10);
  assert (num(4:5, 1:3), [NaN, NaN, NaN; NaN, 1, -83.4], 1e-10);
  assert (num(3, 1:2), [NaN, NaN], 1e-10);
  # Just check if it's numeric, the value depends too much on cached results
  assert (isnumeric (num(3,3)), true);
  printf ("matches...\n");
catch
  printf ("Hmmm.... error, see 'num'\n"); keyboard
end_try_catch
try
  printf ("    ...Cellstr array... ");
  assert (txt{1, 1}, 'r1c1');
  assert (txt{2, 2}, 'r2c2');
  printf ("matches...\n");
catch
  printf ("Hmmm.... error, see 'txt'\n"); keyboard
end_try_catch
try
  printf ("    ...Boolean... ");
  assert (islogical (raw{5, 2}), true);                         # Fails on COM
  printf ("recovered...\n");
catch
  if (isnumeric (raw{5, 2}))
    printf ("returned as numeric '1' rather than logical TRUE.\n");
  else
    printf ("Hmmm.... error, see 'raw{5, 2}'\n"); keyboard
  endif
end_try_catch

## Check if "formulas_as_text" option works:
printf ("\n 8. Repeat reading, now return formulas as text\n");
opts.formulas_as_text = 1;
xls = xlsopen ('io-test.xls', 0, intf); if (isuno); sleep (dly); endif
raw = xls2oct (xls, shnr, crange, opts); if (isuno); sleep (dly); endif
xls = xlsclose (xls); if (isuno); sleep (dly); endif

## 9. Here come the tests, part 2.
printf ("\n 9. Tests part 2 (read back formula):\n");

try
  # Just check if it contains any string
  assert ( (ischar (raw{3, 3}) && ~isempty (raw(3, 3)) && raw{3, 3}(1) == "="), true); 
  printf ("    ...OK, formula recovered ('%s').\n", raw{3, 3});
catch
  printf ("Hmmm.... error, see 'raw(3, 3)'");
  if (isnumeric (raw{3, 3}))
    printf (" (equals %f, should be a string like '=c2+d2')\n", raw{3, 3}); 
  else
    printf ("\n");
  endif
end_try_catch

## 10. Clean up
printf ("\n10. Cleaning up.....");
delete 'io-test.xls';
clear arr1 arr2 xls num txt raw lims opts shnr shts crange intf2;
printf (" OK\n");
