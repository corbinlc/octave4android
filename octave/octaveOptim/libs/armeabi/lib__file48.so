%% Copyright (C) 2007 Paul Kienzle (sort-based lookup in ODE solver)
%% Copyright (C) 2009 Thomas Treichl <thomas.treichl@gmx.net> (ode23 code)
%% Copyright (C) 2010 Olaf Till <i7tiol@t-online.de>
%%
%% This program is free software; you can redistribute it and/or modify it under
%% the terms of the GNU General Public License as published by the Free Software
%% Foundation; either version 3 of the License, or (at your option) any later
%% version.
%%
%% This program is distributed in the hope that it will be useful, but WITHOUT
%% ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
%% FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
%% details.
%%
%% You should have received a copy of the GNU General Public License along with
%% this program; if not, see <http://www.gnu.org/licenses/>.

%% Problems for testing optimizers. Documentation is in the code.

function ret = optim_problems ()

  %% As little external code as possible is called. This leads to some
  %% duplication of external code. The advantages are that thus these
  %% problems do not change with evolving external code, and that
  %% optimization results in Octave can be compared with those in Matlab
  %% without influence of differences in external code (e.g. ODE
  %% solvers). Even calling 'interp1 (..., ..., ..., 'linear')' is
  %% avoided by using an internal subfunction, although this is possibly
  %% too cautious.
  %%
  %% For cross-program comparison of optimizers, the code of these
  %% problems is intended to be Matlab compatible.
  %%
  %% External data may be loaded, which should be supplied in the
  %% 'private/' subdirectory. Use the variable 'ddir', which contains
  %% the path to this directory.

  %% Note: The difficulty of problems with dynamic models often
  %% decisively depends on the the accuracy of the used ODE(DAE)-solver.

  %% Description of the returned structure
  %%
  %% According to 3 classes of problems, there are (or should be) three
  %% fields: 'curve' (curve fitting), 'general' (general optimization),
  %% and 'zero' (zero finding). The subfields are labels for the
  %% particular problems.
  %%
  %% Under the label fields, there are subfields mostly identical
  %% between the 3 classes of problems (some may contain empty values):
  %%
  %% .f: handle of an internally defined objective function (argument:
  %% column vector of parameters), meant for minimization, or to a
  %% 'model function' (arguments: independents, column vector of
  %% parameters) in the case of curve fitting, where .f should return a
  %% matrix of equal dimensions as .data.y below.
  %%
  %% .dfdp: handle of internally defined function for jacobian of
  %% objective function or 'model function', respectively.
  %%
  %% .init_p: initial parameters, column vector
  %%
  %% possibly .init_p_b: two column matrix of ranges to choose initial
  %% parameters from
  %%
  %% possibly .init_p_f: handle of internally defined function which
  %% returns a column vector of initial parameters unique to the index
  %% given as function argument; given '0' as function argument,
  %% .init_p_f returns the maximum index
  %%
  %% .result.p: parameters of best known result
  %%
  %% possibly .result.obj: value of objective function for .result.p (or
  %% sum of squared residuals in curve fitting).
  %%
  %% .data.x: matrix of independents (curve fitting)
  %%
  %% .data.y: matrix of observations, dimensions may be independent of
  %% .data.x (curve fitting)
  %%
  %% .data.wt: matrix of weights, same dimensions as .data.y (curve
  %% fitting)
  %%
  %% .data.cov: covariance matrix of .data.y(:) (not necessarily a
  %% diagonal matrix, which could be expressed in .data.wt)
  %%
  %% .strict_inequc, .non_strict_inequc, .equc: 'strict' inequality
  %% constraints (<, >), 'non-strict' inequality constraints (<=, >=),
  %% and equality constraints, respectively. Subfields are: .bounds
  %% (except in equality constraints): two-column matrix of ranges;
  %% .linear: cell-array {m, v}, meaning linear constraints m.' *
  %% parameters + v >|>=|== 0; .general: handle of internally defined
  %% function h with h (p) >|>=|== 0; possibly .general_dcdp: handle of
  %% internally defined function (argument: parameters) returning the
  %% jacobian of the constraints given in .general. For the sake of
  %% optimizers which can exploit this, the function in subfield
  %% .general may accept a logical index vector as an optional second
  %% argument, returning only the indexed constraint values.


  %% Please keep the following list of problems current.
  %%
  %% .curve.p_1, .curve.p_2, .curve.p_3_d2: from 'Comparison of gradient
  %% methods for the solution of nonlinear parameter estimation
  %% problems' (1970), Yonathan Bard, Siam Journal on Numerical Analysis
  %% 7(1), 157--186. The numbering of problems is the same as in the
  %% article. Since Bard used strict bounds, testing optimizers which
  %% used penalization for bounds, the bounds are changed here to allow
  %% testing with non-strict bounds (<= or >=). .curve.p_3_d2 involves
  %% dynamic modeling. These are not necessarily difficult problems.
  %%
  %% .curve.p_3_d2_noweights: problem .curve.p_3_d2 equivalently
  %% re-formulated without weights.
  %%
  %% .curve.p_r: A seemingly more difficult 'real life' problem with
  %% dynamic modeling. To assess optimizers, .init_p_f should be used
  %% with 1:64. There should be two groups of results, indicating the
  %% presence of two local minima. Olaf Till <olaf.till@uni-jena.de>
  %%
  %% .....schittkowski_...: Klaus Schittkowski: 'More test examples for
  %% nonlinear programming codes.' Lecture Notes in Economics and
  %% Mathematical Systems 282, Berlin 1987. The published problems are
  %% numbered from 201 to 395 and may appear here under the fields
  %% .curve, .general, or .zero.
  %%
  %% .general.schittkowski_281: 10 parameters, unconstrained.
  %%
  %% .general.schittkowski_289: 30 parameters, unconstrained.
  %%
  %% .general.schittkowski_327 and
  %%
  %% .curve.schittkowski_327: Two parameters, one general inequality
  %% constraint, two bounds. The best solution given in the publication
  %% seems not very good (it probably has been achieved with general
  %% minimization, not curve fitting) and has been replaced here by a
  %% better (leasqr).
  %%
  %% .curve.schittkowski_372 and
  %%
  %% .general.schittkowski_372: 9 parameters, 12 general inequality
  %% constraints, 6 bounds. Infeasible initial parameters
  %% (.curve.schittkowski_372.init_p_f(1) provides a set of more or less
  %% feasible parameters). leasqr sticks at the (feasible) initial
  %% values. sqp has no problems.
  %%
  %% .curve.schittkowski_373: 9 parameters, 6 equality constraints.
  %% Infeasible initial parameters (.curve.schittkowski_373.init_p_f(1)
  %% provides a set of more or less feasible parameters). leasqr sticks
  %% at the (feasible) initial values. sqp has no problems.
  %%
  %% .general.schittkowski_391: 30 parameters, unconstrained. The best
  %% solution given in the publication seems not very good, obviously
  %% the used routine had not managed to get very far from the starting
  %% parameters; it has been replaced here by a better (Octaves
  %% fminunc). The result still varies widely (without much changes in
  %% objective function) with changes of starting values. Maybe not a
  %% very good test problem, no well defined minimum ...

  %% needed for some anonymous functions
  if (exist ('ifelse') ~= 5)
    ifelse = @ scalar_ifelse;
  end

  if (~exist ('OCTAVE_VERSION'))
    NA = NaN;
  end

  %% determine the directory of this functions file
  fdir = fileparts (mfilename ('fullpath'));
  %% data directory
  ddir = sprintf ('%s%sprivate%s', fdir, filesep, filesep);

  ret.curve.p_1.dfdp = [];
  ret.curve.p_1.init_p = [1; 1; 1];
  ret.curve.p_1.data.x = cat (2, ...
			      (1:15).', ...
			      (15:-1:1).', ...
			      [(1:8).'; (7:-1:1).']);
  ret.curve.p_1.data.y = [.14; .18; .22; .25; .29; .32; .35; .39; ...
			  .37; .58; .73; .96; 1.34; 2.10; 4.39];
  ret.curve.p_1.data.wt = [];
  ret.curve.p_1.data.cov = [];
  ret.curve.p_1.result.p = [.08241040; 1.133033; 2.343697];
  ret.curve.p_1.strict_inequc.bounds = [0, 100; 0, 100; 0, 100];
  ret.curve.p_1.strict_inequc.linear = [];
  ret.curve.p_1.strict_inequc.general = [];
  ret.curve.p_1.non_strict_inequc.bounds = ...
      [eps, 100; eps, 100; eps, 100];
  ret.curve.p_1.non_strict_inequc.linear = [];
  ret.curve.p_1.non_strict_inequc.general = [];
  ret.curve.p_1.equc.linear = [];
  ret.curve.p_1.equc.general = [];
  ret.curve.p_1.f = @ f_1;

  ret.curve.p_2.dfdp = [];
  ret.curve.p_2.init_p = [0; 0; 0; 0; 0];
  ret.curve.p_2.data.x = [.871, .643, .550; ...
			  .228, .669, .854; ...
			  .528, .229, .170; ...
			  .110, .354, .337; ...
			  .911, .056, .493; ...
			  .476, .154, .918; ...
			  .655, .421, .077; ...
			  .649, .140, .199; ...
			  .995, .045,   NA; ...
			  .130, .016, .195; ...
			  .823, .690, .690; ...
			  .768, .992, .389; ...
			  .203, .740, .120; ...
			  .302, .519, .221; ...
			  .991, .450, .249; ...
			  .224, .030, .502; ...
			  .428, .127, .772; ...
			  .552, .494, .110; ...
			  .461, .824, .714; ...
			  .799, .494, .295];
  ret.curve.p_2.data.y = zeros (20, 3);
  ret.curve.p_2.data.wt = [];
  ret.curve.p_2.data.cov = [];
  ret.curve.p_2.data.misc = [4.36, 5.21, 5.35; ...
			     4.99, 3.30, 3.10; ...
			     1.67,   NA, 2.75; ...
			     2.17, 1.48, 1.49; ...
			     2.98, 4.69, 4.23; ...
			     4.46, 3.87, 3.15; ...
			     1.79, 3.18, 3.57; ...
			     1.71, 3.13, 3.07; ...
			     3.07, 5.01, 4.58; ...
			     0.94, 0.93, 0.74; ...
			     4.97, 5.37, 5.35; ...
			     4.32, 4.85, 5.46; ...
			     2.17, 1.78, 2.43; ...
			     2.22, 2.18, 2.44; ...
			     2.88, 4.90, 5.11; ...
			     2.29, 1.94, 1.46; ...
			     3.76, 3.39, 2.71; ...
			     1.99, 2.93, 3.31; ...
			     4.95, 4.08, 4.19; ...
			     2.96, 4.26, 4.48];
  ret.curve.p_2.result.p = [.9925145; 2.005293; 3.999732; ...
			    2.680371; .4977683]; % from maximum
				% likelyhood optimization
  ret.curve.p_2.strict_inequc.bounds = [];
  ret.curve.p_2.strict_inequc.linear = [];
  ret.curve.p_2.strict_inequc.general = [];
  ret.curve.p_2.non_strict_inequc.bounds = [];
  ret.curve.p_2.non_strict_inequc.linear = [];
  ret.curve.p_2.non_strict_inequc.general = [];
  ret.curve.p_2.equc.linear = [];
  ret.curve.p_2.equc.general = [];
  ret.curve.p_2.f = @ (x, p) f_2 (x, p, ret.curve.p_2.data.misc);



  ret.curve.p_3_d2.dfdp = [];
  ret.curve.p_3_d2.init_p = [.01; .01; .001; .001; .02; .001];
  ret.curve.p_3_d2.data.x = [0; 12.5; 25; 37.5; 50; ...
			     62.5; 75; 87.5; 100];
  ret.curve.p_3_d2.data.y=[1       1       0       0       0      ; ...
			   .945757 .961201 .494861 .154976 .111485; ...
			   .926486 .928762 .690492 .314501 .236263; ...
			   .917668 .915966 .751806 .709300 .311747; ...
			   .928987 .917542 .771559 1.19224 .333096; ...
			   .927782 .920075 .780903 1.68815 .340324; ...
			   .925304 .912330 .790539 2.19539 .356787; ...
			   .925083 .917684 .783933 2.74211 .358283; ...
			   .917277 .907529 .779259 3.20025 .361969];
  ret.curve.p_3_d2.data.y(:, 3) = ...
      ret.curve.p_3_d2.data.y(:, 3) / 10;
  ret.curve.p_3_d2.data.y(:, 4:5) = ...
      ret.curve.p_3_d2.data.y(:, 4:5) / 1000;
  ret.curve.p_3_d2.data.wt = repmat ([.1, .1, 1, 10, 100], 9, 1);
  ret.curve.p_3_d2.data.cov = [];
  ret.curve.p_3_d2.result.p = [.6358247e-2; ...
			       .6774551e-1; ...
			       .5914274e-4; ...
			       .4944010e-3; ...
			       .1018828; ...
			       .4210526e-3];
  ret.curve.p_3_d2.strict_inequc.bounds = [0, 1; ...
					   0, 1; ...
					   0, .1; ...
					   0, .1; ...
					   0, 2; ...
					   0, .1];
  ret.curve.p_3_d2.strict_inequc.linear = [];
  ret.curve.p_3_d2.strict_inequc.general = [];
  ret.curve.p_3_d2.non_strict_inequc.bounds = [eps, 1; ...
					       eps, 1; ...
					       eps, .1; ...
					       eps, .1; ...
					       eps, 2; ...
					       eps, .1];
  ret.curve.p_3_d2.non_strict_inequc.linear = [];
  ret.curve.p_3_d2.non_strict_inequc.general = [];
  ret.curve.p_3_d2.equc.linear = [];
  ret.curve.p_3_d2.equc.general = [];
  ret.curve.p_3_d2.f = @ f_3;

  ret.curve.p_3_d2_noweights = ret.curve.p_3_d2;
  ret.curve.p_3_d2_noweights.data.wt = [];
  ret.curve.p_3_d2_noweights.data.y(:, 1:2) = ...
      ret.curve.p_3_d2_noweights.data.y(:, 1:2) * .1;
  ret.curve.p_3_d2_noweights.data.y(:, 4) = ...
      ret.curve.p_3_d2_noweights.data.y(:, 4) * 10;
  ret.curve.p_3_d2_noweights.data.y(:, 5) = ...
      ret.curve.p_3_d2_noweights.data.y(:, 5) * 100;
  ret.curve.p_3_d2_noweights.f = @ f_3_noweights;

  ret.curve.p_r.dfdp = [];
  ret.curve.p_r.init_p = [.3; .03; .003; .7; 1000; .0205];
  ret.curve.p_r.init_p_b = [.3, .5; ...
			    .03, .05; ...
			    .003, .005; ...
			    .7, .9; ...
			    1000, 1300; ...
			    .0205, .023];
  ret.curve.p_r.init_p_f = @ (id) pc2 (ret.curve.p_r.init_p_b, id);
  hook.ns = [84; 84; 85; 86; 84; 84; 84; 84];
  xb = [0.2, 0.8640; ...
	0.2, 0.5320; ...
	0.2, 0.4856; ...
	0.2, 0.4210; ...
	0.2, 0.3328; ...
	0.2, 0.2996; ...
	0.2, 0.2664; ...
	0.2, 0.2498];
  ns = cat (1, 0, cumsum (hook.ns));
  x = zeros (ns(end), 1);
  for id = 1:8
    x(ns(id) + 1 : ns(id + 1)) = ...
	linspace (xb(id, 1), xb(id, 2), hook.ns(id)).';
  end
  hook.t = x;
  ret.curve.p_r.data.x = x;
  ret.curve.p_r.data.y = ...
      load (sprintf ('%soptim_problems_p_r_y.data', ddir));
  ret.curve.p_r.data.wt = [];
  ret.curve.p_r.data.cov = [];
  ret.curve.p_r.result.p = [4.742909e-01; ...
			    3.837951e-02; ...
			    3.652570e-03; ...
			    7.725986e-01; ...
			    1.180967e+03; ...
			    2.107000e-02];
  ret.curve.p_r.result.obj = 0.2043396;
  ret.curve.p_r.strict_inequc.bounds = [];
  ret.curve.p_r.strict_inequc.linear = [];
  ret.curve.p_r.strict_inequc.general = [];
  ret.curve.p_r.non_strict_inequc.bounds = [];
  ret.curve.p_r.non_strict_inequc.linear = [];
  ret.curve.p_r.non_strict_inequc.general = [];
  ret.curve.p_r.equc.linear = [];
  ret.curve.p_r.equc.general = [];
  hook.mc = [2.0019999999999999e-01, 1.9939999999999999e-01, ...
	     1.9939999999999999e-01, 1.9780000000000000e-01, ...
	     2.0080000000000001e-01, 1.9960000000000000e-01, ...
	     1.9960000000000000e-01, 1.9980000000000001e-01; ...
	     ...
	     2.0060000000000000e-01, 2.0160000000000000e-01, ...
	     2.0200000000000001e-01, 2.0200000000000001e-01, ...
	     2.0180000000000001e-01, 2.0899999999999999e-01, ...
	     2.0860000000000001e-01, 2.0820000000000000e-01; ...
	     ...
	     2.1999144799999999e-02, 2.1998803099999999e-02, ...
	     2.2000449599999999e-02, 2.2000024399999998e-02, ...
	     2.1998160999999999e-02, 2.1999289000000002e-02, ...
	     2.1998038800000001e-02, 2.2000270999999998e-02; ...
	     ...
	     -6.8806551999999986e-03, -1.3768898999999999e-02, ...
	     -1.6065479000000001e-02, -2.0657919600000001e-02, ...
	     -3.4479971099999999e-02, -4.5934394099999998e-02, ...
	     -6.9011619100000005e-02, -9.1971348400000000e-02; ...
	     ...
	     2.3383865100000002e-02, 2.4768462500000001e-02, ...
	     2.5231915899999999e-02, 2.6155515300000001e-02, ...
	     2.8933514200000000e-02, 3.1235568599999999e-02, ...
	     3.5874086299999997e-02, 4.0490560699999997e-02; ...
	     ...
	     -1.8240616806039459e+05, -1.6895474269973661e+03, ...
	     -8.1072652464694931e+02, -7.0113302985566395e+02, ...
	     1.0929964862867249e+04, 3.5665776039585688e+02, ...
	     5.7400262910547769e+02, 9.1737316974342252e+02; ...
	     ...
	     1.0965398741890911e+05, 1.0131334821116490e+03, ...
	     4.8504892529762208e+02, 4.1801020186158411e+02, ...
	     -6.6178457662355086e+03, -2.2103886018172699e+02, ...
	     -3.5529578864017282e+02, -5.6690686490678263e+02; ...
	     ...
	     -2.1972917026209168e+04, -2.0250659086265861e+02, ...
	     -9.6733175964156985e+01, -8.3069683020988421e+01, ...
	     1.3356173243752210e+03, 4.5610806266307627e+01, ...
	     7.3229009073208331e+01, 1.1667126232349770e+02; ...
	     ...
	     1.4676952576063929e+03, 1.3514357622838521e+01, ...
	     6.4524906786197480e+00, 5.5245948033669476e+00, ...
	     -8.9827382090060922e+01, -3.1118708128841241e+00, ...
	     -5.0039950796246986e+00, -7.9749636293721071e+00];
  ret.curve.p_r.f = @ (x, p) f_r (x, p, hook);

  ret.general.schittkowski_281.dfdp = ...
      @ (p) schittkowski_281_dfdp (p);
  ret.general.schittkowski_281.init_p = zeros (10, 1);
  ret.general.schittkowski_281.result.p = ones (10, 1); % 'theoretically'
  ret.general.schittkowski_281.result.obj = 0; % 'theoretically'
  ret.general.schittkowski_281.strict_inequc.bounds = [];
  ret.general.schittkowski_281.strict_inequc.linear = [];
  ret.general.schittkowski_281.strict_inequc.general = [];
  ret.general.schittkowski_281.non_strict_inequc.bounds = [];
  ret.general.schittkowski_281.non_strict_inequc.linear = [];
  ret.general.schittkowski_281.non_strict_inequc.general = [];
  ret.general.schittkowski_281.equc.linear = [];
  ret.general.schittkowski_281.equc.general = [];
  ret.general.schittkowski_281.f = ...
      @ (p) (sum (((1:10).') .^ 3 .* (p - 1) .^ 2)) ^ (1 / 3);

  ret.general.schittkowski_289.dfdp = ...
      @ (p) exp (- sum (p .^ 2) / 60) / 30 * p;
  ret.general.schittkowski_289.init_p = [-1.03; 1.07; -1.10; 1.13; ...
					 -1.17; 1.20; -1.23; 1.27; ...
					 -1.30; 1.33; -1.37; 1.40; ...
					 -1.43; 1.47; -1.50; 1.53; ...
					 -1.57; 1.60; -1.63; 1.67; ...
					 -1.70; 1.73; -1.77; 1.80; ...
					 -1.83; 1.87; -1.90; 1.93; ...
					 -1.97; 2.00];
  ret.general.schittkowski_289.result.p = zeros (30, 1); % 'theoretically'
  ret.general.schittkowski_289.result.obj = 0; % 'theoretically'
  ret.general.schittkowski_289.strict_inequc.bounds = [];
  ret.general.schittkowski_289.strict_inequc.linear = [];
  ret.general.schittkowski_289.strict_inequc.general = [];
  ret.general.schittkowski_289.non_strict_inequc.bounds = [];
  ret.general.schittkowski_289.non_strict_inequc.linear = [];
  ret.general.schittkowski_289.non_strict_inequc.general = [];
  ret.general.schittkowski_289.equc.linear = [];
  ret.general.schittkowski_289.equc.general = [];
  ret.general.schittkowski_289.f = @ (p) 1 - exp (- sum (p .^ 2) / 60);

  ret.curve.schittkowski_327.dfdp = ...
      @ (x, p) [1 + exp(-p(2) * (x - 8)), ...
		(p(1) + .49) * (8 - x) .* exp (-p(2) * (x - 8))];
  ret.curve.schittkowski_327.init_p = [.42; 5];
  ret.curve.schittkowski_327.data.x = ...
      [8; 8; 10; 10; 10; 10; 12; 12; 12; 12; 14; 14; 14; 16; 16; 16; ...
       18; 18; 20; 20; 20; 22; 22; 22; 24; 24; 24; 26; 26; 26; 28; ...
       28; 30; 30; 30; 32; 32; 34; 36; 36; 38; 38; 40; 42];
  ret.curve.schittkowski_327.data.y= ...
      [.49; .49; .48; .47; .48; .47; .46; .46; .45; .43; .45; .43; ...
       .43; .44; .43; .43; .46; .45; .42; .42; .43; .41; .41; .40; ...
       .42; .40; .40; .41; .40; .41; .41; .40; .40; .40; .38; .41; ...
       .40; .40; .41; .38; .40; .40; .39; .39];
  ret.curve.schittkowski_327.data.wt = [];
  ret.curve.schittkowski_327.data.cov = [];
  %% This result was given by Schittkowski. No constraint is active
  %% here. The second parameter is unchanged from initial value.
  %%
  %% ret.curve.schittkowski_327.result.p = [.4219; 5];
  %% ret.curve.schittkowski_327.result.obj = .0307986;
  %%
  %% This is the result of leasqr of Octave Forge. The general
  %% constraint is active here. Both parameters are different from
  %% initial value. The value of the objective function is better.
  %%
  ret.curve.schittkowski_327.result.p = [.4199227; 1.2842958];
  ret.curve.schittkowski_327.result.obj = .0284597;
  ret.curve.schittkowski_327.strict_inequc.bounds = [];
  ret.curve.schittkowski_327.strict_inequc.linear = [];
  ret.curve.schittkowski_327.strict_inequc.general = [];
  ret.curve.schittkowski_327.non_strict_inequc.bounds = [.4, Inf; ...
							 .4, Inf];
  ret.curve.schittkowski_327.non_strict_inequc.linear = [];
  ret.curve.schittkowski_327.non_strict_inequc.general = ...
      @ (p, varargin) apply_idx_if_given ...
      (-.09 - p(1) * p(2) + .49 * p(2), varargin{:});
  ret.curve.schittkowski_327.equc.linear = [];
  ret.curve.schittkowski_327.equc.general = [];
  ret.curve.schittkowski_327.f = ...
      @ (x, p) p(1) + (.49 - p(1)) * exp (-p(2) * (x - 8));

  ret.general.schittkowski_327.init_p = [.42; 5];
  ret.general.schittkowski_327.data.x = ...
      [8; 8; 10; 10; 10; 10; 12; 12; 12; 12; 14; 14; 14; 16; 16; 16; ...
       18; 18; 20; 20; 20; 22; 22; 22; 24; 24; 24; 26; 26; 26; 28; ...
       28; 30; 30; 30; 32; 32; 34; 36; 36; 38; 38; 40; 42];
  ret.general.schittkowski_327.data.y= ...
      [.49; .49; .48; .47; .48; .47; .46; .46; .45; .43; .45; .43; ...
       .43; .44; .43; .43; .46; .45; .42; .42; .43; .41; .41; .40; ...
       .42; .40; .40; .41; .40; .41; .41; .40; .40; .40; .38; .41; ...
       .40; .40; .41; .38; .40; .40; .39; .39];
  x = ret.general.schittkowski_327.data.x;
  y = ret.general.schittkowski_327.data.y;
  ret.general.schittkowski_327.dfdp = ...
      @ (p) cat (2, ...
		 2 * sum ((exp (-p(2 * x - 8)) - 1) * ...
			  (y + (p(1) - .49) * ...
			   exp (-p(2) * (x - 8)) - p1)), ...
		 2 * (p(1) - .49) * ...
		 sum ((8 - x) * exp (-p(2 * x - 8)) * ...
		      (y + (p(1) - .49) * ...
		       exp (-p(2) * (x - 8)) - p1)));
  %% This result was given by Schittkowski. No constraint is active
  %% here. The second parameter is unchanged from initial value.
  %%
  %% ret.general.schittkowski_327.result.p = [.4219; 5];
  %% ret.general.schittkowski_327.result.obj = .0307986;
  %%
  %% This is the result of leasqr of Octave Forge. The general
  %% constraint is active here. Both parameters are different from
  %% initial value. The value of the objective function is better. sqp
  %% gives a similar result.
  ret.general.schittkowski_327.result.p = [.4199227; 1.2842958];
  ret.general.schittkowski_327.result.obj = .0284597;
  ret.general.schittkowski_327.strict_inequc.bounds = [];
  ret.general.schittkowski_327.strict_inequc.linear = [];
  ret.general.schittkowski_327.strict_inequc.general = [];
  ret.general.schittkowski_327.non_strict_inequc.bounds = [.4, Inf; ...
							 .4, Inf];
  ret.general.schittkowski_327.non_strict_inequc.linear = [];
  ret.general.schittkowski_327.non_strict_inequc.general = ...
      @ (p, varargin) apply_idx_if_given ...
      (-.09 - p(1) * p(2) + .49 * p(2), varargin{:});
  ret.general.schittkowski_327.equc.linear = [];
  ret.general.schittkowski_327.equc.general = [];
  ret.general.schittkowski_327.f = ...
      @ (p) sumsq (y - p(1) - (.49 - p(1)) * exp (-p(2) * (x - 8)));

  ret.curve.schittkowski_372.dfdp = ...
      @ (x, p) cat (2, zeros (6, 3), eye (6));
  %% given by Schittkowski, not feasible
  ret.curve.schittkowski_372.init_p = [300; -100; -.1997; -127; ...
				       -151; 379; 421; 460; 426];
  %% computed with sqp and a constant objective function, (almost)
  %% feasible
  ret.curve.schittkowski_372.init_p_f = @ (id) ...
      ifelse (id == 0, 1, [2.951277e+02; ...
			   -1.058720e+02; ...
			   -9.535824e-02; ...
			   2.421108e+00; ...
			   3.191822e+00; ...
			   3.790000e+02; ...
			   4.210000e+02; ...
			   4.600000e+02; ...
			   4.260000e+02]);
  ret.curve.schittkowski_372.data.x = (1:6).'; % any different numbers
  ret.curve.schittkowski_372.data.y= zeros (6, 1);
  ret.curve.schittkowski_372.data.wt = [];
  ret.curve.schittkowski_372.data.cov = [];
  %% recomputed with sqp (i.e. not with curve fitting)
  ret.curve.schittkowski_372.result.p = [5.2330557804078126e+02; ...
					 -1.5694790476454301e+02; ...
					 -1.9966450018535931e-01; ...
					 2.9607990282984435e+01; ...
					 8.6615541706550545e+01; ...
					 4.7326722338555498e+01; ...
					 2.6235616534580515e+01; ...
					 2.2915996663200740e+01; ...
					 3.9470733973874445e+01];
  ret.curve.schittkowski_372.result.obj = 13390.1;
  ret.curve.schittkowski_372.strict_inequc.bounds = [];
  ret.curve.schittkowski_372.strict_inequc.linear = [];
  ret.curve.schittkowski_372.strict_inequc.general = [];
  ret.curve.schittkowski_372.non_strict_inequc.bounds = [-Inf, Inf; ...
							 -Inf, Inf; ...
							 -Inf, Inf; ...
							 0, Inf; ...
							 0, Inf; ...
							 0, Inf; ...
							 0, Inf; ...
							 0, Inf; ...
							 0, Inf];
  ret.curve.schittkowski_372.non_strict_inequc.linear = [];
  ret.curve.schittkowski_372.non_strict_inequc.general = ...
      @ (p, varargin) apply_idx_if_given ...
      (cat (1, p(1) + p(2) * exp (-5 * p(3)) + p(4) - 127, ...
	    p(1) + p(2) * exp (-3 * p(3)) + p(5) - 151, ...
	    p(1) + p(2) * exp (-p(3)) + p(6) - 379, ...
	    p(1) + p(2) * exp (p(3)) + p(7) - 421, ...
	    p(1) + p(2) * exp (3 * p(3)) + p(8) - 460, ...
	    p(1) + p(2) * exp (5 * p(3)) + p(9) - 426, ...
	    -p(1) - p(2) * exp (-5 * p(3)) + p(4) + 127, ...
	    -p(1) - p(2) * exp (-3 * p(3)) + p(5) + 151, ...
	    -p(1) - p(2) * exp (-p(3)) + p(6) + 379, ...
	    -p(1) - p(2) * exp (p(3)) + p(7) + 421, ...
	    -p(1) - p(2) * exp (3 * p(3)) + p(8) + 460, ...
	    -p(1) - p(2) * exp (5 * p(3)) + p(9) + 426), ...
       varargin{:});
  ret.curve.schittkowski_372.equc.linear = [];
  ret.curve.schittkowski_372.equc.general = [];
  ret.curve.schittkowski_372.f = @ (x, p) p(4:9);

  ret.curve.schittkowski_373.dfdp = ...
      @ (x, p) cat (2, zeros (6, 3), eye (6));
  %% not feasible
  ret.curve.schittkowski_373.init_p = [300; -100; -.1997; -127; ...
				       -151; 379; 421; 460; 426];
  %% feasible
  ret.curve.schittkowski_373.init_p_f = @ (id) ...
      ifelse (id == 0, 1, [2.5722721227695763e+02; ...
			   -1.5126681606092043e+02; ...
			   8.3101871447778766e-02; ...
			   -3.0390506000425454e+01; ...
			   1.1661334225083069e+01; ...
			   2.6097719374430665e+02; ...
			   3.2814725183082305e+02; ...
			   3.9686840023267564e+02; ...
			   3.9796353824451995e+02]);
  ret.curve.schittkowski_373.data.x = (1:6).'; % any different numbers
  ret.curve.schittkowski_373.data.y= zeros (6, 1);
  ret.curve.schittkowski_373.data.wt = [];
  ret.curve.schittkowski_373.data.cov = [];
  ret.curve.schittkowski_373.result.p = [523.31; ...
					 -156.95; ...
					 -.2; ...
					 29.61; ...
					 -86.62; ...
					 47.33; ...
					 26.24; ...
					 22.92; ...
					 -39.47];
  ret.curve.schittkowski_373.result.obj = 13390.1;
  ret.curve.schittkowski_373.strict_inequc.bounds = [];
  ret.curve.schittkowski_373.strict_inequc.linear = [];
  ret.curve.schittkowski_373.strict_inequc.general = [];
  ret.curve.schittkowski_373.non_strict_inequc.bounds = [];
  ret.curve.schittkowski_373.non_strict_inequc.linear = [];
  ret.curve.schittkowski_373.non_strict_inequc.general = [];
  ret.curve.schittkowski_373.equc.linear = [];
  ret.curve.schittkowski_373.equc.general =  ...
      @ (p, varargin) apply_idx_if_given ...
      (cat (1, p(1) + p(2) * exp (-5 * p(3)) + p(4) - 127, ...
	    p(1) + p(2) * exp (-3 * p(3)) + p(5) - 151, ...
	    p(1) + p(2) * exp (-p(3)) + p(6) - 379, ...
	    p(1) + p(2) * exp (p(3)) + p(7) - 421, ...
	    p(1) + p(2) * exp (3 * p(3)) + p(8) - 460, ...
	    p(1) + p(2) * exp (5 * p(3)) + p(9) - 426), ...
       varargin{:});
  ret.curve.schittkowski_373.f = @ (x, p) p(4:9);

  ret.general.schittkowski_372.dfdp = ...
      @ (p) cat (2, zeros (1, 3), 2 * p(4:9));
  %% not feasible
  ret.general.schittkowski_372.init_p = [300; -100; -.1997; -127; ...
				       -151; 379; 421; 460; 426];
  %% recomputed with sqp
  ret.general.schittkowski_372.result.p = [5.2330557804078126e+02; ...
					 -1.5694790476454301e+02; ...
					 -1.9966450018535931e-01; ...
					 2.9607990282984435e+01; ...
					 8.6615541706550545e+01; ...
					 4.7326722338555498e+01; ...
					 2.6235616534580515e+01; ...
					 2.2915996663200740e+01; ...
					 3.9470733973874445e+01];
  ret.general.schittkowski_372.result.obj = 13390.1;
  ret.general.schittkowski_372.strict_inequc.bounds = [];
  ret.general.schittkowski_372.strict_inequc.linear = [];
  ret.general.schittkowski_372.strict_inequc.general = [];
  ret.general.schittkowski_372.non_strict_inequc.bounds = [-Inf, Inf; ...
							 -Inf, Inf; ...
							 -Inf, Inf; ...
							 0, Inf; ...
							 0, Inf; ...
							 0, Inf; ...
							 0, Inf; ...
							 0, Inf; ...
							 0, Inf];
  ret.general.schittkowski_372.non_strict_inequc.linear = [];
  ret.general.schittkowski_372.non_strict_inequc.general = ...
      @ (p, varargin) apply_idx_if_given ...
      (cat (1, p(1) + p(2) * exp (-5 * p(3)) + p(4) - 127, ...
	    p(1) + p(2) * exp (-3 * p(3)) + p(5) - 151, ...
	    p(1) + p(2) * exp (-p(3)) + p(6) - 379, ...
	    p(1) + p(2) * exp (p(3)) + p(7) - 421, ...
	    p(1) + p(2) * exp (3 * p(3)) + p(8) - 460, ...
	    p(1) + p(2) * exp (5 * p(3)) + p(9) - 426, ...
	    -p(1) - p(2) * exp (-5 * p(3)) + p(4) + 127, ...
	    -p(1) - p(2) * exp (-3 * p(3)) + p(5) + 151, ...
	    -p(1) - p(2) * exp (-p(3)) + p(6) + 379, ...
	    -p(1) - p(2) * exp (p(3)) + p(7) + 421, ...
	    -p(1) - p(2) * exp (3 * p(3)) + p(8) + 460, ...
	    -p(1) - p(2) * exp (5 * p(3)) + p(9) + 426), ...
       varargin{:});
  ret.general.schittkowski_372.equc.linear = [];
  ret.general.schittkowski_372.equc.general = [];
  ret.general.schittkowski_372.f = @ (p) sumsq (p(4:9));

  ret.general.schittkowski_391.dfdp = [];
  ret.general.schittkowski_391.init_p = ...
      -2.8742711 * alpha_391 (zeros (30, 1), 1:30);
  %% computed with fminunc (Octave)
  ret.general.schittkowski_391.result.p = [-1.1986682e+18; ...
					   -1.1474574e+07; ...
					   -1.3715802e+07; ...
					   -1.0772255e+07; ...
					   -1.0634232e+07; ...
					   -1.0622915e+07; ...
					   -8.8775399e+06; ...
					   -8.8201496e+06; ...
					   -9.7729975e+06; ...
					   -1.0431808e+07; ...
					   -1.0415089e+07; ...
					   -1.0350400e+07; ...
					   -1.0325094e+07; ...
					   -1.0278561e+07; ...
					   -1.0275751e+07; ...
					   -1.0276546e+07; ...
					   -1.0292584e+07; ...
					   -1.0289350e+07; ...
					   -1.0192566e+07; ...
					   -1.0058577e+07; ...
					   -1.0096341e+07; ...
					   -1.0242386e+07; ...
					   -1.0615831e+07; ...
					   -1.1142096e+07; ...
					   -1.1617283e+07; ...
					   -1.2005738e+07; ...
					   -1.2282117e+07; ...
					   -1.2301260e+07; ...
					   -1.2051365e+07; ...
					   -1.1704693e+07];
  ret.general.schittkowski_391.result.obj = -5.1615468e+20;
  ret.general.schittkowski_391.strict_inequc.bounds = [];
  ret.general.schittkowski_391.strict_inequc.linear = [];
  ret.general.schittkowski_391.strict_inequc.general = [];
  ret.general.schittkowski_391.non_strict_inequc.bounds = [];
  ret.general.schittkowski_391.non_strict_inequc.linear = [];
  ret.general.schittkowski_391.non_strict_inequc.general = [];
  ret.general.schittkowski_391.equc.linear = [];
  ret.general.schittkowski_391.equc.general = [];
  ret.general.schittkowski_391.f = @ (p) sum (alpha_391 (p, 1:30));

  function ret = f_1 (x, p)

    ret = p(1) + x(:, 1) ./ (p(2) * x(:, 2) + p(3) * x(:, 3));

  function ret = f_2 (x, p, y)

    y(3, 2) = p(4);
    x(9, 3) = p(5);
    p = p(:);
    mp = cat (2, p([1, 2, 3]), p([3, 1, 2]), p([3, 2, 1]));
    ret = x * mp - y;

  function ret = f_3 (x, p)

    ret = fixed_step_rk4 (x.', [1, 1, 0, 0, 0], 1, ...
			  @ (x, t) f_3_xdot (x, t, p));
    ret = ret.';

  function ret = f_3_noweights (x, p)

    ret = fixed_step_rk4 (x.', [.1, .1, 0, 0, 0], .2, ...
			  @ (x, t) f_3_xdot_noweights (x, t, p));
    ret = ret.';

  function ret = f_3_xdot (x, t, p)

    ret = zeros (5, 1);
    tp = p(2) * x(3) - p(1) * x(1) * x(2);
    ret(1) = tp;
    ret(2) = tp - p(4) * x(2) * x(3) + p(5) * x(5) - p(6) * x(2) * x(4);
    ret(3) = - tp - p(3) * x(3) - p(4) * x(2) * x(3);
    ret(4) = p(3) * x(3) + p(5) * x(5) - p(6) * x(2) * x(4);
    ret(5) = p(4) * x(2) * x(3) - p(5) * x(5) + p(6) * x(2) * x(4);

  function ret = f_3_xdot_noweights (x, t, p)

    x(1:2) = x(1:2) / .1;
    x(4) = x(4) / 10;
    x(5) = x(5) / 100;
    ret = f_3_xdot (x, t, p);
    ret(1:2) = ret(1:2) * .1;
    ret(4) = ret(4) * 10;
    ret(5) = ret(5) * 100;

  function ret = f_r (x, p, hook)

    n = size (hook.mc, 2);
    ns = cat (1, 0, cumsum (hook.ns));
    xdhook.p = p;
    ret = zeros (1, ns(end));
    %% temporary variables
    dls = p(3) ^ 2;
    dmhp = p(5) * dls / p(4);
    mhp = dmhp / 2;
    %%
    for id = 1:n
      xdhook.c = hook.mc(:, id);
      l = xdhook.c(3);
      x0 = mhp - sqrt (max (0, mhp ^ 2 + dls + (p(6) - l) * dmhp));
      ids = ns(id) + 1;
      ide = ns(id + 1);

      tp = odeset ();
      %% necessary in Matlab (7.1)
      tp.OutputSave = [];
      tp.Refine = 0;
      %%
      tp.RelTol = 1e-7;
      tp.AbsTol = 1e-7;
      [cx, Xcx] = essential_ode23 (@ (t, X) f_r_xdot (X, t, xdhook), ...
				   x([ids, ide]).', x0, tp);
      X = lin_interp (cx.', Xcx.', x(ids:ide).');

      X = X.';
      [discarded, lr] = ...
	  f_r_xdot (X, hook.t(ids:ide), xdhook);
      ret(ids:ide) = max (0, lr - p(6) - X) * p(5);
    end
    ret = ret.';

  function [ret, l] = f_r_xdot (x, t, hook)

    %% keep this working with non-scalar x and t

    p = hook.p;
    c = hook.c;
    idl = t <= c(1);
    idg = t >= c(2);
    idb = ~ (idl | idg);
    l = zeros (size (t));
    l(idl) = c(3);
    l(idg) = c(4) * t(idg) + c(5);
    l(idb) = polyval (c(6:9), t(idb));
    dls = max (1e-6, l - p(6) - x);
    tf = x / p(3);
    ido = tf >= 1;
    idx = ~ido;
    ret(ido) = 0;
    ret(idx) = - ((p(4) + p(1)) * p(2)) ./ ...
	((p(5) * dls(idx)) ./ (1 - tf(idx) .^ 2) + p(1)) + p(2);

  function ret = alpha_391 (p, id)

    %% for .general.schittkowski_391; id is a numeric index(-vector)
    %% into p

    p = p(:);
    n = size (p, 1);

    nid = length (id);
    id = reshape (id, 1, nid);

    v = sqrt (repmat (p .^ 2, 1, nid) + 1 ./ ((1:n).') * id);

    log_v = log (v);

    ret = 420 * p(id) + (id(:) - 15) .^ 3 + ...
	sum (v .* (sin (log_v) .^ 5 + cos (log_v) .^ 5)).';

  function ret = schittkowski_281_dfdp (p)

    tp = (sum (((1:10).') .^ 3 .* (p - 1) .^ 2)) ^ (- 2 / 3) / 3;

    ret = 2 * ((1:10).') .^ 3 .* (p - 1) * tp;

  function state = fixed_step_rk4 (t, x0, step, f)

    %% minimalistic fourth order ODE-solver, as said to be a popular one
    %% by Wikipedia (to make these optimization tests self-contained;
    %% for the same reason 'lookup' and even 'interp1' are not used
    %% here)

    n = ceil ((t(end) - t(1)) / step) + 1;
    m = length (x0);
    tstate = zeros (m, n);
    tstate(:, 1) = x0;
    tt = linspace (t(1), t(1) + step * (n - 1), n);
    for id = 1 : n - 1
      k1 = f (tstate(:, id), tt(id));
      k2 = f (tstate(:, id) + .5 * step * k1, tt(id) + .5 * step);
      k3 = f (tstate(:, id) + .5 * step * k2, tt(id) + .5 * step);
      k4 = f (tstate(:, id) + step * k3, tt(id + 1));
      tstate(:, id + 1) = tstate(:, id) + ...
	  (step / 6) * (k1 + 2 * k2 + 2 * k3 + k4);
    end
    state = lin_interp (tt, tstate, t);

  function ret = pc2 (p, id)
    %% a combination out of 2 possible values for each parameter
    r = size (p, 1);
    n = 2 ^ r;
    if (id < 0 || id > n)
      error ('no parameter set for this index');
    end
    if (id == 0) % return maximum id
      ret = n;
      return;
    end
    idx = dec2bin (id - 1, r) == '1';
    nidx = ~idx;
    ret = zeros (r, 1);
    ret(nidx) = p(nidx, 1);
    ret(idx) = p(idx, 2);

  function [varargout] = essential_ode23 (vfun, vslot, vinit, vodeoptions)

    %% This code is taken from the ode23 solver of Thomas Treichl
    %% <thomas.treichl@gmx.net>, some flexibility of the
    %% interface has been removed. The idea behind this duplication is
    %% to have a fixed version of the solver here which runs both in
    %% Octave and Matlab.

    %% Some of the option treatment has been left out.
    if (length (vslot) > 2)
      vstepsizefixed = true;
    else
      vstepsizefixed = false;
    end
    if (strcmp (vodeoptions.NormControl, 'on'))
      vnormcontrol = true;
    else
      vnormcontrol = false;
    end
    if (~isempty (vodeoptions.NonNegative))
      if (isempty (vodeoptions.Mass))
	vhavenonnegative = true;
      else
	vhavenonnegative = false;
      end
    else
      vhavenonnegative = false;
    end
    if (isempty (vodeoptions.OutputFcn) && nargout == 0)
      vodeoptions.OutputFcn = @odeplot;
      vhaveoutputfunction = true;
    elseif (isempty (vodeoptions.OutputFcn))
      vhaveoutputfunction = false;
    else
      vhaveoutputfunction = true;
    end
    if (~isempty (vodeoptions.OutputSel))
      vhaveoutputselection = true;
    else
      vhaveoutputselection = false;
    end
    if (isempty (vodeoptions.OutputSave))
      vodeoptions.OutputSave = 1;
    end
    if (vodeoptions.Refine > 0)
      vhaverefine = true;
    else
      vhaverefine = false;
    end
    if (isempty (vodeoptions.InitialStep) && ~vstepsizefixed)
      vodeoptions.InitialStep = (vslot(1,2) - vslot(1,1)) / 10;
      vodeoptions.InitialStep = vodeoptions.InitialStep / ...
	  10^vodeoptions.Refine;
    end
    if (isempty (vodeoptions.MaxStep) && ~vstepsizefixed)
      vodeoptions.MaxStep = (vslot(1,2) - vslot(1,1)) / 10;
    end
    if (~isempty (vodeoptions.Events))
      vhaveeventfunction = true;
    else
      vhaveeventfunction = false;
    end
    if (~isempty (vodeoptions.Mass) && ismatrix (vodeoptions.Mass))
      vhavemasshandle = false;
      vmass = vodeoptions.Mass;
    elseif (isa (vodeoptions.Mass, 'function_handle'))
      vhavemasshandle = true;
    else
      vhavemasshandle = false;
    end
    if (strcmp (vodeoptions.MStateDependence, 'none'))
      vmassdependence = false;
    else
      vmassdependence = true;
    end

    %% Starting the initialisation of the core solver ode23
    vtimestamp  = vslot(1,1);           %% timestamp = start time
    vtimelength = length (vslot);       %% length needed if fixed steps
    vtimestop   = vslot(1,vtimelength); %% stop time = last value
    vdirection  = sign (vtimestop);     %% Flag for direction to solve

    if (~vstepsizefixed)
      vstepsize = vodeoptions.InitialStep;
      vminstepsize = (vtimestop - vtimestamp) / (1/eps);
    else %% If step size is given then use the fixed time steps
      vstepsize = vslot(1,2) - vslot(1,1);
      vminstepsize = sign (vstepsize) * eps;
    end

    vretvaltime = vtimestamp; %% first timestamp output
    vretvalresult = vinit;    %% first solution output

    %% Initialize the OutputFcn
    if (vhaveoutputfunction)
      if (vhaveoutputselection) vretout = ...
	    vretvalresult(vodeoptions.OutputSel);
      else
	vretout = vretvalresult;
      end
      feval (vodeoptions.OutputFcn, vslot.', ...
	     vretout.', 'init');
    end

    %% Initialize the EventFcn
    if (vhaveeventfunction)
      odepkg_event_handle (vodeoptions.Events, vtimestamp, ...
			   vretvalresult.', 'init');
    end

    vpow = 1/3;            %% 20071016, reported by Luis Randez
    va = [  0, 0, 0;       %% The Runge-Kutta-Fehlberg 2(3) coefficients
          1/2, 0, 0;       %% Coefficients proved on 20060827
          -1, 2, 0];      %% See p.91 in Ascher & Petzold
    vb2 = [0; 1; 0];       %% 2nd and 3rd order
    vb3 = [1/6; 2/3; 1/6]; %% b-coefficients
    vc = sum (va, 2);

    %% The solver main loop - stop if the endpoint has been reached
    vcntloop = 2; vcntcycles = 1; vu = vinit; vk = vu.' * zeros(1,3);
    vcntiter = 0; vunhandledtermination = true; vcntsave = 2;
    while ((vdirection * (vtimestamp) < vdirection * (vtimestop)) && ...
           (vdirection * (vstepsize) >= vdirection * (vminstepsize)))

      %% Hit the endpoint of the time slot exactely
      if ((vtimestamp + vstepsize) > vdirection * vtimestop)
	%% if (((vtimestamp + vstepsize) > vtimestop) || ...
	%% (abs(vtimestamp + vstepsize - vtimestop) < eps))
	vstepsize = vtimestop - vdirection * vtimestamp;
      end

      %% Estimate the three results when using this solver
      for j = 1:3
	vthetime  = vtimestamp + vc(j,1) * vstepsize;
	vtheinput = vu.' + vstepsize * vk(:,1:j-1) * va(j,1:j-1).';
	if (vhavemasshandle)   %% Handle only the dynamic mass matrix,
          if (vmassdependence) %% constant mass matrices have already
            vmass = feval ...  %% been set before (if any)
		(vodeoptions.Mass, vthetime, vtheinput);
          else                 %% if (vmassdependence == false)
            vmass = feval ...  %% then we only have the time argument
		(vodeoptions.Mass, vthetime);
          end
          vk(:,j) = vmass \ feval ...
              (vfun, vthetime, vtheinput);
	else
          vk(:,j) = feval ...
              (vfun, vthetime, vtheinput);
	end
      end

      %% Compute the 2nd and the 3rd order estimation
      y2 = vu.' + vstepsize * (vk * vb2);
      y3 = vu.' + vstepsize * (vk * vb3);
      if (vhavenonnegative)
	vu(vodeoptions.NonNegative) = abs (vu(vodeoptions.NonNegative));
	y2(vodeoptions.NonNegative) = abs (y2(vodeoptions.NonNegative));
	y3(vodeoptions.NonNegative) = abs (y3(vodeoptions.NonNegative));
      end
      vSaveVUForRefine = vu;

      %% Calculate the absolute local truncation error and the
      %% acceptable error
      if (~vstepsizefixed)
	if (~vnormcontrol)
          vdelta = abs (y3 - y2);
          vtau = max (vodeoptions.RelTol * abs (vu.'), ...
		      vodeoptions.AbsTol);
	else
          vdelta = norm (y3 - y2, Inf);
          vtau = max (vodeoptions.RelTol * max (norm (vu.', Inf), ...
						1.0), ...
                      vodeoptions.AbsTol);
	end
      else %% if (vstepsizefixed == true)
	vdelta = 1; vtau = 2;
      end

      %% If the error is acceptable then update the vretval variables
      if (all (vdelta <= vtau))
	vtimestamp = vtimestamp + vstepsize;
	vu = y3.'; %% MC2001: the higher order estimation as 'local
	%% extrapolation' Save the solution every vodeoptions.OutputSave
	%% steps
	if (mod (vcntloop-1,vodeoptions.OutputSave) == 0)
          vretvaltime(vcntsave,:) = vtimestamp;
          vretvalresult(vcntsave,:) = vu;
          vcntsave = vcntsave + 1;
	end
	vcntloop = vcntloop + 1; vcntiter = 0;

	%% Call plot only if a valid result has been found, therefore
	%% this code fragment has moved here. Stop integration if plot
	%% function returns false
	if (vhaveoutputfunction)
          for vcnt = 0:vodeoptions.Refine %% Approximation between told
	    %% and t
            if (vhaverefine)              %% Do interpolation
              vapproxtime = (vcnt + 1) * vstepsize / ...
		  (vodeoptions.Refine + 2);
              vapproxvals = vSaveVUForRefine.' + vapproxtime * (vk * ...
								vb3);
              vapproxtime = (vtimestamp - vstepsize) + vapproxtime;
            else
              vapproxvals = vu.';
              vapproxtime = vtimestamp;
            end
            if (vhaveoutputselection)
              vapproxvals = vapproxvals(vodeoptions.OutputSel);
            end
            vpltret = feval (vodeoptions.OutputFcn, vapproxtime, ...
			     vapproxvals, []);
            if vpltret %% Leave refinement loop
              break;
            end
          end
          if (vpltret) %% Leave main loop
            vunhandledtermination = false;
            break;
          end
	end

	%% Call event only if a valid result has been found, therefore
	%% this code fragment has moved here. Stop integration if
	%% veventbreak is true
	if (vhaveeventfunction)
          vevent = ...
              odepkg_event_handle (vodeoptions.Events, vtimestamp, ...
				   vu(:), []);
          if (~isempty (vevent{1}) && vevent{1} == 1)
            vretvaltime(vcntloop-1,:) = vevent{3}(end,:);
            vretvalresult(vcntloop-1,:) = vevent{4}(end,:);
            vunhandledtermination = false; break;
          end
	end
      end %% If the error is acceptable ...

      %% Update the step size for the next integration step
      if (~vstepsizefixed)
	%% 20080425, reported by Marco Caliari vdelta cannot be negative
	%% (because of the absolute value that has been introduced) but
	%% it could be 0, then replace the zeros with the maximum value
	%% of vdelta
	vdelta(find (vdelta == 0)) = max (vdelta);
	%% It could happen that max (vdelta) == 0 (ie. that the original
	%% vdelta was 0), in that case we double the previous vstepsize
	vdelta(find (vdelta == 0)) = max (vtau) .* (0.4 ^ (1 / vpow));

	if (vdirection == 1)
          vstepsize = min (vodeoptions.MaxStep, ...
			   min (0.8 * vstepsize * (vtau ./ vdelta) .^ ...
				vpow));
	else
          vstepsize = max (vodeoptions.MaxStep, ...
			   max (0.8 * vstepsize * (vtau ./ vdelta) .^ ...
				vpow));
	end
      else %% if (vstepsizefixed)
	if (vcntloop <= vtimelength)
          vstepsize = vslot(vcntloop) - vslot(vcntloop-1);
	else %% Get out of the main integration loop
          break;
	end
      end

      %% Update counters that count the number of iteration cycles
      vcntcycles = vcntcycles + 1; %% Needed for cost statistics
      vcntiter = vcntiter + 1;     %% Needed to find iteration problems

      %% Stop solving because the last 1000 steps no successful valid
      %% value has been found
      if (vcntiter >= 5000)
	error (['Solving has not been successful. The iterative', ...
	' integration loop exited at time t = %f before endpoint at', ...
	' tend = %f was reached. This happened because the iterative', ...
	' integration loop does not find a valid solution at this time', ...
	' stamp. Try to reduce the value of ''InitialStep'' and/or', ...
	' ''MaxStep'' with the command ''odeset''.\n'], vtimestamp, vtimestop);
      end

    end %% The main loop

    %% Check if integration of the ode has been successful
    if (vdirection * vtimestamp < vdirection * vtimestop)
      if (vunhandledtermination == true)
	error ('OdePkg:InvalidArgument', ...
       ['Solving has not been successful. The iterative', ...
	' integration loop exited at time t = %f', ...
	' before endpoint at tend = %f was reached. This may', ...
	' happen if the stepsize grows smaller than defined in', ...
	' vminstepsize. Try to reduce the value of ''InitialStep'' and/or', ...
	' ''MaxStep'' with the command ''odeset''.\n'], vtimestamp, vtimestop);
      else
	warning ('OdePkg:InvalidArgument', ...
	 ['Solver has been stopped by a call of ''break'' in', ...
	  ' the main iteration loop at time t = %f before endpoint at', ...
	  ' tend = %f was reached. This may happen because the @odeplot', ...
	  ' function returned ''true'' or the @event function returned ''true''.'], ...
	 vtimestamp, vtimestop);
      end
    end

    %% Postprocessing, do whatever when terminating integration
    %% algorithm
    if (vhaveoutputfunction) %% Cleanup plotter
      feval (vodeoptions.OutputFcn, vtimestamp, ...
	     vu.', 'done');
    end
    if (vhaveeventfunction)  %% Cleanup event function handling
      odepkg_event_handle (vodeoptions.Events, vtimestamp, ...
			   vu.', 'done');
    end
    %% Save the last step, if not already saved
    if (mod (vcntloop-2,vodeoptions.OutputSave) ~= 0)
      vretvaltime(vcntsave,:) = vtimestamp;
      vretvalresult(vcntsave,:) = vu;
    end


    varargout{1} = vretvaltime;     %% Time stamps are first output argument
    varargout{2} = vretvalresult;   %% Results are second output argument

  function yi = lin_interp (x, y, xi)

    %% Actually interp1 with 'linear' should behave equally in Octave
    %% and Matlab, but having this subset of functionality here is being
    %% on the safe side.

    n = size (x, 2);
    m = size (y, 1);
    %% This elegant lookup is from an older version of 'lookup' by Paul
    %% Kienzle, and had been suggested by Kai Habel <kai.habel@gmx.de>.
    [v, p] = sort ([x, xi]);
    idx(p) = cumsum (p <= n);
    idx = idx(n + 1 : n + size (xi, 2));
    %%
    idx(idx == n) = n - 1;
    yi = y(:, idx) + ...
	repmat (xi - x(idx), m, 1) .* ...
	(y(:, idx + 1) - y(:, idx)) ./ ...
	repmat (x(idx + 1) - x(idx), m, 1);

  function ret = apply_idx_if_given  (ret, idx)

    if (nargin > 1)
      ret = ret(idx);
    end

  function fval = scalar_ifelse (cond, tval, fval)

    %% needed for some anonymous functions, builtin ifelse only available
    %% in Octave > 3.2; we need only the scalar case here

    if (cond)
      fval = tval;
    end

%!demo
%! p_t = optim_problems ().curve.p_1;
%! global verbose;
%! verbose = false;
%! [cy, cp, cvg, iter] = leasqr (p_t.data.x, p_t.data.y, p_t.init_p, p_t.f)
%! disp (p_t.result.p)
%! sumsq (cy - p_t.data.y)
