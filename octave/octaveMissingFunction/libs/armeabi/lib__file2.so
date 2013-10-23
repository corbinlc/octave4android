## Copyright (C) 2008 Bill Denney
##
## This software is free software; you can redistribute it and/or modify it
## under the terms of the GNU General Public License as published by
## the Free Software Foundation; either version 3 of the License, or (at
## your option) any later version.
##
## This software is distributed in the hope that it will be useful, but
## WITHOUT ANY WARRANTY; without even the implied warranty of
## MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
## General Public License for more details.
##
## You should have received a copy of the GNU General Public License
## along with this software; see the file COPYING.  If not, see
## <http://www.gnu.org/licenses/>.

## -*- texinfo -*-
## @deftypefn {Function File} {} missingfunctionstatus (@var{outdir})
## Write out all the current Matlab functions and toolboxes to
## @var{outdir} in html format.
## @seealso{missingmatlabfunctions}
## @end deftypefn

function missingfunctionstatus (outdir = ".")

  urlbase = "http://www.mathworks.com/access/helpdesk/help/";

  toolboxes = {"Matlab" "techdoc/ref/f16-6011.html";
               "Aerospace" "toolbox/aerotbx/ug/bqj21qj.html";
               "Bioinformatics" "toolbox/bioinfo/ref/a1052308804.html";
               "Communications" "toolbox/comm/ug/a1037894415.html";
               "Control System" "toolbox/control/ref/f2-1014412.html";
               "Curve Fitting" "toolbox/curvefit/f2-17602.html";
               "Data Acquisition" "toolbox/daq/f14-17602.html";
               "Database" "toolbox/database/ug/f4-6010.html";
               "Datafeed" "toolbox/datafeed/bp_usto-1.html";
               "Filter Design" "toolbox/filterdesign/ref/f11-35125.html";
               "Financial" "toolbox/finance/f6-213137.html";
               "Financial Derivatives" "toolbox/finderiv/f0-23501.html";
               "Fixed-Income" "toolbox/finfixed/f5-6010.html";
               "Fixed-Point" "toolbox/fixedpoint/ref/f20333.html";
               "Fuzzy Logic" "toolbox/fuzzy/fp4856.html";
               "GARCH" "toolbox/garch/f9-21078.html";
               "Genetic Algorithm and Direct Search" "toolbox/gads/bqe0w5v.html";
               "Image Acquisition" "toolbox/imaq/f14-17602.html";
               "Image Processing" "toolbox/images/f3-23960.html";
               "Instrument Control" "toolbox/instrument/f9-42439.html";
               "Mapping" "toolbox/map/f3-12193.html";
               "Mapping (Projections)" "toolbox/map/f4-4154.html";
               "Model Predictive Control" "toolbox/mpc/chmpcrefintro.html";
               "Neural Network" "toolbox/nnet/function.html";
               "OPC" "toolbox/opc/ug/f7-6010.html";
               "Optimization" "toolbox/optim/ug/bqnk0r0.html";
               "Partial Differential Equation" "toolbox/pde/ug/f7498.html";
               "RF" "toolbox/rf/bq33b0t.html";
               "Robust Control" "toolbox/robust/refintro.html";
               "Signal Processing" "toolbox/signal/f9-131178c.html";
               "Spline" "toolbox/splines/refer1_html.html";
               "Spreadsheet Link EX" "toolbox/exlink/f4-6010.html";
               "Statistics" "toolbox/stats/bq_w_hm.html";
               "Symbolic Math" "toolbox/symbolic/f3-157665.html";
               "System Identification" "toolbox/ident/ref/f3-8911.html";
               "Virtual Reality" "toolbox/vr/f0-6010.html";
               "Wavelet" "toolbox/wavelet/ref_open.html"};

  filenames = cell (rows (toolboxes), 1);
  for i = 1:rows (toolboxes)
    basename = toolboxes{i,1};
    basename(isspace (basename)) = "_";
    filenames{i} = [basename ".html"];
    missingmatlabfunctions (fullfile (outdir, filenames{i}), "",
                            [urlbase toolboxes{i,2}])
  endfor

  ## make the index page
  [fid msg] = fopen (fullfile (outdir, "index.html"), "wt");
  if (fid < 0)
    error ("missingfunctionstatus: could not open index.html, %s", msg)
  endif
  fprintf (fid, "<html xmlns=\"http://www.w3.org/1999/xhtml\"><head><title>Matlab Functions Missing from Octave</title><link rel=\"stylesheet\" type=\"text/css\" href=\"missingmatlab.css\"/></head><body>\n");
  for i = 1:rows (toolboxes)
    fprintf (fid, "<a href=\"%s\">%s</a><br/>",
             filenames{i}, toolboxes{i,1});
  endfor
  fprintf (fid, "</body></html>")
  fclose (fid);

endfunction
