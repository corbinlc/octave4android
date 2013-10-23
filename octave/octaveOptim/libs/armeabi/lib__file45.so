## Copyright (C) 2012 Olaf Till <i7tiol@t-online.de>
##
## This program is free software; you can redistribute it and/or modify
## it under the terms of the GNU General Public License as published by
## the Free Software Foundation; either version 3 of the License, or
## (at your option) any later version.
##
## This program is distributed in the hope that it will be useful,
## but WITHOUT ANY WARRANTY; without even the implied warranty of
## MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
## GNU General Public License for more details.
##
## You should have received a copy of the GNU General Public License
## along with this program; If not, see <http://www.gnu.org/licenses/>.

## -*- texinfo -*-
## @deftypefn {Function File} {[@var{p}, @var{objf}, @var{cvg}, @var{outp}] =} nonlin_min (@var{f}, @var{pin})
## @deftypefnx {Function File} {[@var{p}, @var{objf}, @var{cvg}, @var{outp}] =} nonlin_min (@var{f}, @var{pin}, @var{settings})
##
## Frontend for constrained nonlinear minimization of a scalar objective
## function. The functions supplied by the user have a minimal
## interface; any additionally needed constants can be supplied by
## wrapping the user functions into anonymous functions.
##
## The following description applies to usage with vector-based
## parameter handling. Differences in usage for structure-based
## parameter handling will be explained in a separate section below.
##
## @var{f}: objective function. It gets a column vector of real
## parameters as argument. In gradient determination, this function may
## be called with an informational second argument, whose content
## depends on the function for gradient determination.
##
## @var{pin}: real column vector of initial parameters.
##
## @var{settings}: structure whose fields stand for optional settings
## referred to below. The fields can be set by @code{optimset()} with
## Octave versions 3.3.55 or greater; with older Octave versions, the
## fields must be set directly as structure-fields in the correct case.
##
## The returned values are the column vector of final parameters
## @var{p}, the final value of the objective function @var{objf}, an
## integer @var{cvg} indicating if and how optimization succeeded or
## failed, and a structure @var{outp} with additional information,
## curently with only one field: @var{niter}, the number of iterations.
## @var{cvg} is greater than zero for success and less than or equal to
## zero for failure; its possible values depend on the used backend and
## currently can be @code{0} (maximum number of iterations exceeded),
## @code{1} (fixed number of iterations completed, e.g. in stochastic
## optimizers), @code{2} (parameter change less than specified precision
## in two consecutive iterations), @code{3} (improvement in objective
## function less than specified), or @code{-4} (algorithm got stuck).
##
## @var{settings}:
##
## @code{Algorithm}: String specifying the backend. Currently available
## are @code{"lm_feasible"} (default) and @code{"siman"}. They are
## described in separate sections below.
##
## @code{objf_grad}: Function computing the gradient of the objective
## function with respect to the parameters, assuming residuals are
## reshaped to a vector. Default: finite differences. Will be called
## with the column vector of parameters and an informational structure
## as arguments. The structure has the fields @code{f}: value of
## objective function for current parameters, @code{fixed}: logical
## vector indicating which parameters are not optimized, so these
## partial derivatives need not be computed and can be set to zero,
## @code{diffp}, @code{diff_onesided}, @code{lbound}, @code{ubound}:
## identical to the user settings of this name, @code{plabels}:
## 1-dimensional cell-array of column-cell-arrays, each column with
## labels for all parameters, the first column contains the numerical
## indices of the parameters. The default gradient function will call
## the objective function with the second argument set with fields
## @code{f}: as the @code{f} passed to the gradient function,
## @code{plabels}: cell-array of 1x1 cell-arrays with the entries of the
## column-cell-arrays of @code{plabels} as passed to the jacobian
## function corresponding to current parameter, @code{side}: @code{0}
## for one-sided interval, @code{1} or @code{2}, respectively, for the
## sides of a two-sided interval, and @code{parallel}: logical scalar
## indicating parallel computation of partial derivatives.
##
## @code{objf_hessian}: Function computing the Hessian of the objective
## function with respect to the parameters. The default is backend
## specific. Will be called with the column vector of parameters as
## argument.
##
## @code{diffp}: column vector of fractional intervals (doubled for
## central intervals) supposed to be used by gradient functions
## performing finite differencing. Default: @code{.001 * ones (size
## (parameters))}. The default gradient function will use these as
## absolute intervals for parameters with value zero.
##
## @code{diff_onesided}: logical column vector indicating that one-sided
## intervals should be used by gradient functions performing finite
## differencing. Default: @code{false (size (parameters))}.
##
## @code{complex_step_derivative_objf},
## @code{complex_step_derivative_inequc},
## @code{complex_step_derivative_equc}: logical scalars, default: false.
## Estimate gradient of objective function, general inequality
## constraints, and general equality constraints, respectively, with
## complex step derivative approximation. Use only if you know that your
## objective function, function of general inequality constraints, or
## function of general equality constraints, respectively, is suitable
## for this. No user function for the respective gradient must be
## specified.
##
## @code{cstep}: scalar step size for complex step derivative
## approximation. Default: 1e-20.
##
## @code{fixed}: logical column vector indicating which parameters
## should not be optimized, but kept to their inital value. Fixing is
## done independently of the backend, but the backend may choose to fix
## additional parameters under certain conditions.
##
## @code{lbound}, @code{ubound}: column vectors of lower and upper
## bounds for parameters. Default: @code{-Inf} and @code{+Inf},
## respectively. The bounds are non-strict, i.e. parameters are allowed
## to be exactly equal to a bound. The default gradient function will
## respect bounds (but no further inequality constraints) in finite
## differencing.
##
## @code{inequc}: Further inequality constraints. Cell-array containing
## up to four entries, two entries for linear inequality constraints
## and/or one or two entries for general inequality constraints. Either
## linear or general constraints may be the first entries, but the two
## entries for linear constraints must be adjacent and, if two entries
## are given for general constraints, they also must be adjacent. The
## two entries for linear constraints are a matrix (say @code{m}) and a
## vector (say @code{v}), specifying linear inequality constraints of
## the form @code{m.' * parameters + v >= 0}. The first entry for
## general constraints must be a differentiable column-vector valued
## function (say @code{h}), specifying general inequality constraints of
## the form @code{h (p[, idx]) >= 0}; @code{p} is the column vector of
## optimized parameters and the optional argument @code{idx} is a
## logical index. @code{h} has to return the values of all constraints
## if @code{idx} is not given. It may choose to return only the indexed
## constraints if @code{idx} is given (so computation of the other
## constraints can be spared); in this case, the additional setting
## @code{inequc_f_idx} has to be set to @code{true}. In gradient
## determination, this function may be called with an informational
## third argument, whose content depends on the function for gradient
## determination. If a second entry for general inequality constraints
## is given, it must be a function computing the jacobian of the
## constraints with respect to the parameters. For this function, the
## description of @code{dfdp} above applies, with 2 exceptions: 1) it is
## called with 3 arguments since it has an additional argument
## @code{idx}, a logical index, at second position, indicating which
## rows of the jacobian must be returned (if the function chooses to
## return only indexed rows, the additional setting @code{inequc_df_idx}
## has to be set to @code{true}). 2) the default jacobian function calls
## @code{h} with 3 arguments, since the argument @code{idx} is also
## supplied. Note that specifying linear constraints as general
## constraints will generally waste performance, even if further,
## non-linear, general constraints are also specified.
##
## @code{equc}: Equality constraints. Specified the same way as
## inequality constraints (see @code{inequc}). The respective additional
## settings are named @code{equc_f_idx} and @code{equc_df_idx}.
##
## @code{cpiv}: Function for complementary pivoting, usable in
## algorithms for constraints. Default: @ cpiv_bard. Only the default
## function is supplied with the package.
##
## @code{TolFun}: Minimum fractional improvement in objective function
## in an iteration (termination criterium). Default: .0001.
##
## @code{MaxIter}: Maximum number of iterations (termination criterium).
## Default: backend-specific.
##
## @code{fract_prec}: Column Vector, minimum fractional change of
## parameters in an iteration (termination criterium if violated in two
## consecutive iterations). Default: backend-specific.
##
## @code{max_fract_change}: Column Vector, enforced maximum fractional
## change in parameters in an iteration. Default: backend-specific.
##
## @code{Display}: String indicating the degree of verbosity. Default:
## @code{"off"}. Possible values are currently @code{"off"} (no
## messages) and @code{"iter"} (some messages after each iteration).
## Support of this setting and its exact interpretation are
## backend-specific.
##
## @code{debug}: Logical scalar, default: @code{false}. Will be passed
## to the backend, which might print debugging information if true.
##
## Structure-based parameter handling
##
## The setting @code{param_order} is a cell-array with names of the
## optimized parameters. If not given, and initial parameters are a
## structure, all parameters in the structure are optimized. If initial
## parameters are a structure, it is an error if @code{param_order} is
## not given and there are any non-structure-based configuration items
## or functions.
##
## The initial parameters @var{pin} can be given as a structure
## containing at least all fields named in @code{param_order}. In this
## case the returned parameters @var{p} will also be a structure.
##
## Each user-supplied function can be called with the argument
## containing the current parameters being a structure instead of a
## column vector. For this, a corresponding setting must be set to
## @code{true}: @code{objf_pstruct} (objective function),
## @code{objf_grad_pstruct} (gradient of objective function),
## @code{objf_hessian_pstruct} (hessian of objective function),
## @code{f_inequc_pstruct} (general inequality constraints),
## @code{df_inequc_pstruct} (jacobian of general inequality
## constraints), @code{f_equc_pstruct} (general equality constraints),
## and @code{df_equc_pstruct} (jacobian of general equality
## constraints). If a gradient (jacobian) function is configured in such
## a way, it must return the entries (columns) of the gradient
## (jacobian) as fields of a structure under the respective parameter
## names. If the hessian function is configured in such a way, it must
## return a structure (say @code{h}) with fields e.g. as
## @code{h.a.b = value} for @code{value} being the 2nd partial derivative
## with respect to @code{a} and @code{b}. There is no need to also
## specify the field @code{h.b.a} in this example.
##
## Similarly, for specifying linear constraints, instead of the matrix
## (called @code{m} above), a structure containing the rows of the
## matrix in fields under the respective parameter names can be given.
## In this case, rows containing only zeros need not be given.
##
## The vector-based settings @code{lbound}, @code{ubound},
## @code{fixed}, @code{diffp}, @code{diff_onesided}, @code{fract_prec},
## and @code{max_fract_change} can be replaced by the setting
## @code{param_config}. It is a structure that can contain fields named
## in @code{param_order}. For each such field, there may be subfields
## with the same names as the above vector-based settings, but
## containing a scalar value for the respective parameter. If
## @code{param_config} is specified, none of the above
## vector/matrix-based settings may be used.
##
## Additionally, named parameters are allowed to be non-scalar real
## arrays. In this case, their dimensions are given by the setting
## @code{param_dims}, a cell-array of dimension vectors, each containing
## at least two dimensions; if not given, dimensions are taken from the
## initial parameters, if these are given in a structure. Any
## vector-based settings or not structure-based linear constraints then
## must correspond to an order of parameters with all parameters
## reshaped to vectors and concatenated in the user-given order of
## parameter names. Structure-based settings or structure-based initial
## parameters must contain arrays with dimensions reshapable to those of
## the respective parameters.
##
## Description of backends
##
## "lm_feasible"
##
## A Levenberg/Marquardt-like optimizer, attempting to honour
## constraints throughout the course of optimization. This means that
## the initial parameters must not violate constraints (to find an
## initial feasible set of parameters, e.g. Octaves @code{sqp} can be
## used, by specifying an objective function which is constant or which
## returns the quadratic distance to the initial values). If the
## constraints need only be honoured in the result of the optimization,
## Octaves @code{sqp} may be preferable. The Hessian is either supplied
## by the user or is approximated by the BFGS algorithm.
##
## Returned value @var{cvg} will be @code{2} or @code{3} for success and
## @code{0} or @code{-4} for failure (see above for meaning).
##
## Backend-specific defaults are: @code{MaxIter}: 20, @code{fract_prec}:
## @code{zeros (size (parameters))}, @code{max_fract_change}: @code{Inf}
## for all parameters.
##
## Interpretation of @code{Display}: if set to @code{"iter"}, currently
## only information on applying @code{max_fract_change} is printed.
##
## "siman"
##
## A simulated annealing (stochastic) optimizer, changing all parameters
## at once in a single step, so being suitable for non-bound
## constraints.
##
## No gradient or hessian of the objective function is used. The
## settings @code{MaxIter}, @code{fract_prec}, @code{TolFun}, and
## @code{max_fract_change} are not honoured.
##
## Accepts the additional settings @code{T_init} (initial temperature,
## default 0.01), @code{T_min} (final temperature, default 1.0e-5),
## @code{mu_T} (factor of temperature decrease, default 1.005),
## @code{iters_fixed_T} (iterations within one temperature step, default
## 10), @code{max_rand_step} (column vector or structure-based
## configuration of maximum random steps for each parameter, default
## 0.005 * @var{pin}), @code{stoch_regain_constr} (if @code{true},
## regain constraints after a random step, otherwise take new random
## value until constraints are met, default false), @code{trace_steps}
## (set field @code{trace} of @var{outp} with a matrix with a row for
## each step, first column iteration number, second column repeat number
## within iteration, third column value of objective function, rest
## columns parameter values, default false), and @code{siman_log} (set
## field @code{log} of @var{outp} with a matrix with a row for each
## iteration, first column temperature, second column value of objective
## function, rest columns numbers of tries with decrease, no decrease
## but accepted, and no decrease and rejected.
##
## Steps with increase @code{diff} of objective function are accepted if
## @code{rand (1) < exp (- diff / T)}, where @code{T} is the temperature
## of the current iteration.
##
## If regaining of constraints failed, optimization will be aborted and
## returned value of @var{cvg} will be @code{0}. Otherwise, @var{cvg}
## will be @code{1}.
##
## Interpretation of @code{Display}: if set to @code{"iter"}, an
## informational line is printed after each iteration.
##
## @end deftypefn

## disabled PKG_ADD: __all_opts__ ("nonlin_min");

function [p, objf, cvg, outp] = nonlin_min (f, pin, settings)

  if (compare_versions (version (), "3.3.55", "<"))
    ## optimset mechanism was fixed for option names with underscores
    ## sometime in 3.3.54+, if I remember right
    optimget = @ __optimget__;
  endif

  if (compare_versions (version (), "3.2.4", "<="))
    ## For bug #31484; but Octave 3.6... shows bug #36288 due to this
    ## workaround. Octave 3.7... seems to be all right.
    __dfdp__ = @ __dfdp__;
  endif

  ## some scalar defaults; some defaults are backend specific, so
  ## lacking elements in respective constructed vectors will be set to
  ## NA here in the frontend
  diffp_default = .001;
  stol_default = .0001;
  cstep_default = 1e-20;

  if (nargin == 1 && ischar (f) && strcmp (f, "defaults"))
    p = optimset ("param_config", [], \
		  "param_order", [], \
		  "param_dims", [], \
		  "f_inequc_pstruct", false, \
		  "f_equc_pstruct", false, \
		  "objf_pstruct", false, \
		  "df_inequc_pstruct", false, \
		  "df_equc_pstruct", false, \
		  "objf_grad_pstruct", false, \
		  "objf_hessian_pstruct", false, \
		  "lbound", [], \
		  "ubound", [], \
		  "objf_grad", [], \
		  "objf_hessian", [], \
		  "cpiv", @ cpiv_bard, \
		  "max_fract_change", [], \
		  "fract_prec", [], \
		  "diffp", [], \
		  "diff_onesided", [], \
		  "complex_step_derivative_objf", false, \
		  "complex_step_derivative_inequc", false, \
		  "complex_step_derivative_equc", false, \
		  "cstep", cstep_default, \
		  "fixed", [], \
		  "inequc", [], \
		  "equc", [], \
                  "inequc_f_idx", false, \
                  "inequc_df_idx", false, \
                  "equc_f_idx", false, \
                  "equc_df_idx", false, \
		  "TolFun", stol_default, \
		  "MaxIter", [], \
		  "Display", "off", \
		  "Algorithm", "lm_feasible", \
		  "T_init", .01, \
		  "T_min", 1.0e-5, \
		  "mu_T", 1.005, \
		  "iters_fixed_T", 10, \
		  "max_rand_step", [], \
		  "stoch_regain_constr", false, \
                  "trace_steps", false, \
                  "siman_log", false, \
		  "debug", false);
    return;
  endif

  if (nargin < 2 || nargin > 3)
    print_usage ();
  endif

  if (nargin == 2)
    settings = struct ();
  endif

  if (ischar (f))
    f = str2func (f);
  endif

  if (! (pin_struct = isstruct (pin)))
    if (! isvector (pin) || columns (pin) > 1)
      error ("initial parameters must be either a structure or a column vector");
    endif
  endif

  #### processing of settings and consistency checks

  pconf = optimget (settings, "param_config");
  pord = optimget (settings, "param_order");
  pdims = optimget (settings, "param_dims");
  f_inequc_pstruct = optimget (settings, "f_inequc_pstruct", false);
  f_equc_pstruct = optimget (settings, "f_equc_pstruct", false);
  f_pstruct = optimget (settings, "objf_pstruct", false);
  dfdp_pstruct = optimget (settings, "objf_grad_pstruct", f_pstruct);
  hessian_pstruct = optimget (settings, "objf_hessian_pstruct", f_pstruct);
  df_inequc_pstruct = optimget (settings, "df_inequc_pstruct", \
				f_inequc_pstruct);
  df_equc_pstruct = optimget (settings, "df_equc_pstruct", \
			      f_equc_pstruct);
  lbound = optimget (settings, "lbound");
  ubound = optimget (settings, "ubound");
  dfdp = optimget (settings, "objf_grad");
  if (ischar (dfdp)) dfdp = str2func (dfdp); endif
  hessian = optimget (settings, "objf_hessian");
  max_fract_change = optimget (settings, "max_fract_change");
  fract_prec = optimget (settings, "fract_prec");
  diffp = optimget (settings, "diffp");
  diff_onesided = optimget (settings, "diff_onesided");
  fixed = optimget (settings, "fixed");
  do_cstep = optimget (settings, "complex_step_derivative_objf", false);
  cstep = optimget (settings, "cstep", cstep_default);
  if (do_cstep && ! isempty (dfdp))
    error ("both 'complex_step_derivative_objf' and 'objf_grad' are set");
  endif
  do_cstep_inequc = \
      optimget (settings, "complex_step_derivative_inequc", false);
  do_cstep_equc = optimget (settings, "complex_step_derivative_equc", \
			    false);
  max_rand_step = optimget (settings, "max_rand_step");

  any_vector_conf = ! (isempty (lbound) && isempty (ubound) && \
		       isempty (max_fract_change) && \
		       isempty (fract_prec) && isempty (diffp) && \
		       isempty (diff_onesided) && isempty (fixed) && \
		       isempty (max_rand_step));

  ## collect constraints
  [mc, vc, f_genicstr, df_gencstr, user_df_gencstr] = \
      __collect_constraints__ (optimget (settings, "inequc"), \
			       do_cstep_inequc, "inequality constraints");
  [emc, evc, f_genecstr, df_genecstr, user_df_genecstr] = \
      __collect_constraints__ (optimget (settings, "equc"), \
			       do_cstep_equc, "equality constraints");
  mc_struct = isstruct (mc);
  emc_struct = isstruct (emc);

  ## correct "_pstruct" settings if functions are not supplied, handle
  ## constraint functions not honoring indices
  if (isempty (dfdp)) dfdp_pstruct = false; endif
  if (isempty (hessian)) hessian_pstruct = false; endif
  if (isempty (f_genicstr))
    f_inequc_pstruct = false;
  elseif (! optimget (settings, "inequc_f_idx", false))
    f_genicstr = @ (p, varargin) apply_idx_if_given \
        (f_genicstr (p, varargin{:}), varargin{:});
  endif
  if (isempty (f_genecstr))
    f_equc_pstruct = false;
  elseif (! optimget (settings, "equc_f_idx", false))
    f_genecstr = @ (p, varargin) apply_idx_if_given \
        (f_genecstr (p, varargin{:}), varargin{:});
  endif
  if (user_df_gencstr)
    if (! optimget (settings, "inequc_df_idx", false))
      df_gencstr = @ (varargin) df_gencstr (varargin{:})(varargin{2}, :);
    endif
  else
    df_inequc_pstruct = false;
  endif
  if (user_df_genecstr)
    if (! optimget (settings, "equc_df_idx", false))
      df_genecstr = @ (varargin) df_genecstr (varargin{:})(varargin{2}, :);
    endif
  else
    df_equc_pstruct = false;
  endif

  ## some settings require a parameter order
  if (pin_struct || ! isempty (pconf) || f_inequc_pstruct || \
      f_equc_pstruct || f_pstruct || dfdp_pstruct || \
      hessian_pstruct || df_inequc_pstruct || df_equc_pstruct || \
      mc_struct || emc_struct)
    if (isempty (pord))
      if (pin_struct)
	if (any_vector_conf || \
	    ! (f_pstruct && \
	       (f_inequc_pstruct || isempty (f_genicstr)) && \
	       (f_equc_pstruct || isempty (f_genecstr)) && \
	       (dfdp_pstruct || isempty (dfdp)) && \
	       (hessian_pstruct || isempty (hessian)) && \
	       (df_inequc_pstruct || ! user_df_gencstr) && \
	       (df_equc_pstruct || ! user_df_genecstr) && \
	       (mc_struct || isempty (mc)) && \
	       (emc_struct || isempty (emc))))
	  error ("no parameter order specified and constructing a parameter order from the structure of initial parameters can not be done since not all configuration or given functions are structure based");
	else
	  pord = fieldnames (pin);
	endif
      else
	error ("given settings require specification of parameter order or initial parameters in the form of a structure");
      endif
    endif
    pord = pord(:);
    if (pin_struct && ! all (isfield (pin, pord)))
      error ("some initial parameters lacking");
    endif
    if ((nnames = rows (unique (pord))) < rows (pord))
      error ("duplicate parameter names in 'param_order'");
    endif
    if (isempty (pdims))
      if (pin_struct)
	pdims = cellfun \
	    (@ size, fields2cell (pin, pord), "UniformOutput", false);
      else
	pdims = num2cell (ones (nnames, 2), 2);
      endif
    else
      pdims = pdims(:);
      if (pin_struct && \
	  ! all (cellfun (@ (x, y) prod (size (x)) == prod (y), \
			  struct2cell (pin), pdims)))
	error ("given param_dims and dimensions of initial parameters do not match");
      endif
    endif
    if (nnames != rows (pdims))
      error ("lengths of 'param_order' and 'param_dims' not equal");
    endif
    pnel = cellfun (@ prod, pdims);
    ppartidx = pnel;
    if (any (pnel > 1))
      pnonscalar = true;
      cpnel = num2cell (pnel);
      prepidx = cat (1, cellfun \
		     (@ (x, n) x(ones (1, n), 1), \
		      num2cell ((1:nnames).'), cpnel, \
		      "UniformOutput", false){:});
      epord = pord(prepidx, 1);
      psubidx = cat (1, cellfun \
		     (@ (n) (1:n).', cpnel, \
		      "UniformOutput", false){:});
    else
      pnonscalar = false; # some less expensive interfaces later
      prepidx = (1:nnames).';
      epord = pord;
      psubidx = ones (nnames, 1);
    endif
  else
    pord = []; # spares checks for given but not needed
  endif

  if (pin_struct)
    np = sum (pnel);
  else
    np = length (pin);
    if (! isempty (pord) && np != sum (pnel))
      error ("number of initial parameters not correct");
    endif
  endif

  plabels = num2cell (num2cell ((1:np).'));
  if (! isempty (pord))
    plabels = cat (2, plabels, num2cell (epord), \
		   num2cell (num2cell (psubidx)));
  endif

  ## some useful vectors
  zerosvec = zeros (np, 1);
  NAvec = NA (np, 1);
  Infvec = Inf (np, 1);
  falsevec = false (np, 1);
  sizevec = [np, 1];

  ## collect parameter-related configuration
  if (! isempty (pconf))
    ## use supplied configuration structure

    ## parameter-related configuration is either allowed by a structure
    ## or by vectors
    if (any_vector_conf)
      error ("if param_config is given, its potential items must not \
	  be configured in another way");
    endif

    ## supplement parameter names lacking in param_config
    nidx = ! isfield (pconf, pord);
    pconf = cell2fields ({struct()}(ones (1, sum (nidx))), \
			 pord(nidx), 2, pconf);

    pconf = structcat (1, fields2cell (pconf, pord){:});

    ## in the following, use reshape with explicit dimensions (instead
    ## of x(:)) so that errors are thrown if a configuration item has
    ## incorrect number of elements

    lbound = - Infvec;
    if (isfield (pconf, "lbound"))
      idx = ! fieldempty (pconf, "lbound");
      if (pnonscalar)
	lbound (idx(prepidx), 1) = \
	    cat (1, cellfun (@ (x, n) reshape (x, n, 1), \
			     {pconf(idx).lbound}.', \
			     cpnel(idx), "UniformOutput", false){:});
      else
	lbound(idx, 1) = cat (1, pconf.lbound);
      endif
    endif

    ubound = Infvec;
    if (isfield (pconf, "ubound"))
      idx = ! fieldempty (pconf, "ubound");
      if (pnonscalar)
	ubound (idx(prepidx), 1) = \
	    cat (1, cellfun (@ (x, n) reshape (x, n, 1), \
			     {pconf(idx).ubound}.', \
			     cpnel(idx), "UniformOutput", false){:});
      else
	ubound(idx, 1) = cat (1, pconf.ubound);
      endif
    endif

    max_fract_change = fract_prec = NAvec;

    if (isfield (pconf, "max_fract_change"))
      idx = ! fieldempty (pconf, "max_fract_change");
      if (pnonscalar)
	max_fract_change(idx(prepidx)) = \
	    cat (1, cellfun (@ (x, n) reshape (x, n, 1), \
			     {pconf(idx).max_fract_change}.', \
			     cpnel(idx), \
			     "UniformOutput", false){:});
      else
	max_fract_change(idx) = [pconf.max_fract_change];
      endif
    endif

    if (isfield (pconf, "fract_prec"))
      idx = ! fieldempty (pconf, "fract_prec");
      if (pnonscalar)
	fract_prec(idx(prepidx)) = \
	    cat (1, cellfun (@ (x, n) reshape (x, n, 1), \
			     {pconf(idx).fract_prec}.', cpnel(idx), \
			     "UniformOutput", false){:});
      else
	fract_prec(idx) = [pconf.fract_prec];
      endif
    endif

    diffp = zerosvec;
    diffp(:) = diffp_default;
    if (isfield (pconf, "diffp"))
      idx = ! fieldempty (pconf, "diffp");
      if (pnonscalar)
	diffp(idx(prepidx)) = \
	    cat (1, cellfun (@ (x, n) reshape (x, n, 1), \
			     {pconf(idx).diffp}.', cpnel(idx), \
			     "UniformOutput", false){:});
      else
	diffp(idx) = [pconf.diffp];
      endif
    endif

    diff_onesided = fixed = falsevec;

    if (isfield (pconf, "diff_onesided"))
      idx = ! fieldempty (pconf, "diff_onesided");
      if (pnonscalar)
	diff_onesided(idx(prepidx)) = \
	    logical \
	    (cat (1, cellfun (@ (x, n) reshape (x, n, 1), \
			      {pconf(idx).diff_onesided}.', cpnel(idx), \
			     "UniformOutput", false){:}));
      else
	diff_onesided(idx) = logical ([pconf.diff_onesided]);
      endif
    endif

    if (isfield (pconf, "fixed"))
      idx = ! fieldempty (pconf, "fixed");
      if (pnonscalar)
	fixed(idx(prepidx)) = \
	    logical \
	    (cat (1, cellfun (@ (x, n) reshape (x, n, 1), \
			      {pconf(idx).fixed}.', cpnel(idx), \
			     "UniformOutput", false){:}));
      else
	fixed(idx) = logical ([pconf.fixed]);
      endif
    endif

    max_rand_step = NAvec;

    if (isfield (pconf, "max_rand_step"))
      idx = ! fieldempty (pconf, "max_rand_step");
      if (pnonscalar)
	max_rand_step(idx(prepidx)) = \
	    logical \
	    (cat (1, cellfun (@ (x, n) reshape (x, n, 1), \
			      {pconf(idx).max_rand_step}.',
			      cpnel(idx), \
			      "UniformOutput", false){:}));
      else
	max_rand_step(idx) = logical ([pconf.max_rand_step]);
      endif
    endif

  else
    ## use supplied configuration vectors

    if (isempty (lbound))
      lbound = - Infvec;
    elseif (any (size (lbound) != sizevec))
      error ("bounds: wrong dimensions");
    endif

    if (isempty (ubound))
      ubound = Infvec;
    elseif (any (size (ubound) != sizevec))
      error ("bounds: wrong dimensions");
    endif

    if (isempty (max_fract_change))
      max_fract_change = NAvec;
    elseif (any (size (max_fract_change) != sizevec))
      error ("max_fract_change: wrong dimensions");
    endif

    if (isempty (fract_prec))
      fract_prec = NAvec;
    elseif (any (size (fract_prec) != sizevec))
      error ("fract_prec: wrong dimensions");
    endif

    if (isempty (diffp))
      diffp = zerosvec;
      diffp(:) = diffp_default;
    else
      if (any (size (diffp) != sizevec))
	error ("diffp: wrong dimensions");
      endif
      diffp(isna (diffp)) = diffp_default;
    endif

    if (isempty (diff_onesided))
      diff_onesided = falsevec;
    else
      if (any (size (diff_onesided) != sizevec))
	error ("diff_onesided: wrong dimensions")
      endif
      diff_onesided(isna (diff_onesided)) = false;
      diff_onesided = logical (diff_onesided);
    endif

    if (isempty (fixed))
      fixed = falsevec;
    else
      if (any (size (fixed) != sizevec))
	error ("fixed: wrong dimensions");
      endif
      fixed(isna (fixed)) = false;
      fixed = logical (fixed);
    endif

    if (isempty (max_rand_step))
      max_rand_step = NAvec;
    elseif (any (size (max_rand_step) != sizevec))
      error ("max_rand_step: wrong dimensions");
    endif

  endif

  ## guaranty all (lbound <= ubound)
  if (any (lbound > ubound))
    error ("some lower bounds larger than upper bounds");
  endif

  #### consider whether initial parameters and functions are based on
  #### parameter structures or parameter vectors; wrappers for call to
  #### default function for jacobians

  ## initial parameters
  if (pin_struct)
    if (pnonscalar)
      pin = cat (1, cellfun (@ (x, n) reshape (x, n, 1), \
			     fields2cell (pin, pord), cpnel, \
			     "UniformOutput", false){:});
    else
      pin = cat (1, fields2cell (pin, pord){:});
    endif
  endif

  ## objective function
  if (f_pstruct)
    if (pnonscalar)
      f = @ (p, varargin) \
	  f (cell2struct \
	     (cellfun (@ reshape, mat2cell (p, ppartidx), \
		       pdims, "UniformOutput", false), \
	      pord, 1), varargin{:});
    else
      f = @ (p, varargin) \
	  f (cell2struct (num2cell (p), pord, 1), varargin{:});
    endif
  endif
  f_pin = f (pin);

  ## gradient of objective function
  if (isempty (dfdp))
    if (do_cstep)
      dfdp = @ (p, hook) jacobs (p, f, hook);
    else
      dfdp = @ (p, hook) __dfdp__ (p, f, hook);
    endif
  endif
  if (dfdp_pstruct)
    if (pnonscalar)
      dfdp = @ (p, hook) \
	  cat (2, \
	       fields2cell \
	       (dfdp (cell2struct \
		      (cellfun (@ reshape, mat2cell (p, ppartidx), \
				pdims, "UniformOutput", false), \
		       pord, 1), hook), \
		pord){:});
    else
      dfdp = @ (p, hook) \
	  cat (2, \
	       fields2cell \
	       (dfdp (cell2struct (num2cell (p), pord, 1), hook), \
		pord){:});
    endif
  endif

  ## hessian of objective function
  if (hessian_pstruct)
    if (pnonscalar)
      hessian = @ (p) \
	  hessian_struct2mat \
	  (hessian (cell2struct \
		    (cellfun (@ reshape, mat2cell (p, ppartidx), \
			      pdims, "UniformOutput", false), \
		     pord, 1)), pord);
    else
      hessian = @ (p) \
	  hessian_struct2mat \
	  (hessian (cell2struct (num2cell (p), pord, 1)), pord);
    endif
  endif

  ## function for general inequality constraints
  if (f_inequc_pstruct)
    if (pnonscalar)
      f_genicstr = @ (p, varargin) \
	  f_genicstr (cell2struct \
		      (cellfun (@ reshape, mat2cell (p, ppartidx), \
				pdims, "UniformOutput", false), \
		       pord, 1), varargin{:});
    else
      f_genicstr = @ (p, varargin) \
	  f_genicstr \
	  (cell2struct (num2cell (p), pord, 1), varargin{:});
    endif
  endif

  ## note this stage
  possibly_pstruct_f_genicstr = f_genicstr;

  ## jacobian of general inequality constraints
  if (df_inequc_pstruct)
    if (pnonscalar)
      df_gencstr = @ (p, func, idx, hook) \
	  cat (2, \
	       fields2cell \
	       (df_gencstr \
		(cell2struct \
		 (cellfun (@ reshape, mat2cell (p, ppartidx), \
			   pdims, "UniformOutput", false), pord, 1), \
		 func, idx, hook), \
		pord){:});
    else
      df_gencstr = @ (p, func, idx, hook) \
	  cat (2, \
	       fields2cell \
	       (df_gencstr (cell2struct (num2cell (p), pord, 1), \
			    func, idx, hook), \
		pord){:});
    endif
  endif

  ## function for general equality constraints
  if (f_equc_pstruct)
    if (pnonscalar)
      f_genecstr = @ (p, varargin) \
	  f_genecstr (cell2struct \
		      (cellfun (@ reshape, mat2cell (p, ppartidx), \
				pdims, "UniformOutput", false), \
		       pord, 1), varargin{:});
    else
      f_genecstr = @ (p, varargin) \
	  f_genecstr \
	  (cell2struct (num2cell (p), pord, 1), varargin{:});
    endif
  endif

  ## note this stage
  possibly_pstruct_f_genecstr = f_genecstr;

  ## jacobian of general equality constraints
  if (df_equc_pstruct)
    if (pnonscalar)
      df_genecstr = @ (p, func, idx, hook) \
	  cat (2, \
	       fields2cell \
	       (df_genecstr \
		(cell2struct \
		 (cellfun (@ reshape, mat2cell (p, ppartidx), \
			   pdims, "UniformOutput", false), pord, 1), \
		 func, idx, hook), \
		pord){:});
    else
      df_genecstr = @ (p, func, idx, hook) \
	  cat (2, \
	       fields2cell \
	       (df_genecstr (cell2struct (num2cell (p), pord, 1), \
			     func, idx, hook), \
		pord){:});
    endif
  endif

  ## linear inequality constraints
  if (mc_struct)
    idx = isfield (mc, pord);
    if (rows (fieldnames (mc)) > sum (idx))
      error ("unknown fields in structure of linear inequality constraints");
    endif
    smc = mc;
    mc = zeros (np, rows (vc));
    mc(idx(prepidx), :) = cat (1, fields2cell (smc, pord(idx)){:});
  endif

  ## linear equality constraints
  if (emc_struct)
    idx = isfield (emc, pord);
    if (rows (fieldnames (emc)) > sum (idx))
      error ("unknown fields in structure of linear equality constraints");
    endif
    semc = emc;
    emc = zeros (np, rows (evc));
    emc(idx(prepidx), :) = cat (1, fields2cell (semc, pord(idx)){:});
  endif

  ## parameter-related configuration for jacobian functions
  if (dfdp_pstruct || df_inequc_pstruct || df_equc_pstruct)
    if(pnonscalar)
      s_diffp = cell2struct \
	  (cellfun (@ reshape, mat2cell (diffp, ppartidx), \
		    pdims, "UniformOutput", false), pord, 1);
      s_diff_onesided = cell2struct \
	  (cellfun (@ reshape, mat2cell (diff_onesided, ppartidx), \
		    pdims, "UniformOutput", false), pord, 1);
      s_orig_lbound = cell2struct \
	  (cellfun (@ reshape, mat2cell (lbound, ppartidx), \
		    pdims, "UniformOutput", false), pord, 1);
      s_orig_ubound = cell2struct \
	  (cellfun (@ reshape, mat2cell (ubound, ppartidx), \
		    pdims, "UniformOutput", false), pord, 1);
      s_plabels = cell2struct \
	  (num2cell \
	   (cat (2, cellfun \
		 (@ (x) cellfun \
		  (@ reshape, mat2cell (cat (1, x{:}), ppartidx), \
		   pdims, "UniformOutput", false), \
		  num2cell (plabels, 1), "UniformOutput", false){:}), \
	    2), \
	   pord, 1);
      s_orig_fixed = cell2struct \
	  (cellfun (@ reshape, mat2cell (fixed, ppartidx), \
		    pdims, "UniformOutput", false), pord, 1);
    else
      s_diffp = cell2struct (num2cell (diffp), pord, 1);
      s_diff_onesided = cell2struct (num2cell (diff_onesided), pord, 1);
      s_orig_lbound = cell2struct (num2cell (lbound), pord, 1);
      s_orig_ubound = cell2struct (num2cell (ubound), pord, 1);
      s_plabels = cell2struct (num2cell (plabels, 2), pord, 1);
      s_orig_fixed = cell2struct (num2cell (fixed), pord, 1);
    endif
  endif

  #### some further values and checks

  if (any (fixed & (pin < lbound | pin > ubound)))
    warning ("some fixed parameters outside bounds");
  endif

  if (any (diffp <= 0))
    error ("some elements of 'diffp' non-positive");
  endif

  if (cstep <= 0)
    error ("'cstep' non-positive");
  endif

  if ((hook.TolFun = optimget (settings, "TolFun", stol_default)) < 0)
    error ("'TolFun' negative");
  endif

  if (any (fract_prec < 0))
    error ("some elements of 'fract_prec' negative");
  endif

  if (any (max_fract_change < 0))
    error ("some elements of 'max_fract_change' negative");
  endif

  ## dimensions of linear constraints
  if (isempty (mc))
    mc = zeros (np, 0);
    vc = zeros (0, 1);
  endif
  if (isempty (emc))
    emc = zeros (np, 0);
    evc = zeros (0, 1);
  endif
  [rm, cm] = size (mc);
  [rv, cv] = size (vc);
  if (rm != np || cm != rv || cv != 1)
    error ("linear inequality constraints: wrong dimensions");
  endif
  [erm, ecm] = size (emc);
  [erv, ecv] = size (evc);
  if (erm != np || ecm != erv || ecv != 1)
    error ("linear equality constraints: wrong dimensions");
  endif

  ## note initial values of linear constraits
  pin_cstr.inequ.lin_except_bounds = mc.' * pin + vc;
  pin_cstr.equ.lin = emc.' * pin + evc;

  ## note number and initial values of general constraints
  if (isempty (f_genicstr))
    pin_cstr.inequ.gen = [];
    n_genicstr = 0;
  else
    n_genicstr = length (pin_cstr.inequ.gen = f_genicstr (pin));
  endif
  if (isempty (f_genecstr))
    pin_cstr.equ.gen = [];
    n_genecstr = 0;
  else
    n_genecstr = length (pin_cstr.equ.gen = f_genecstr (pin));
  endif

  #### collect remaining settings
  hook.MaxIter = optimget (settings, "MaxIter");
  if (ischar (hook.cpiv = optimget (settings, "cpiv", @ cpiv_bard)))
    hook.cpiv = str2func (hook.cpiv);
  endif
  hook.Display = optimget (settings, "Display", "off");
  hook.testing = optimget (settings, "debug", false);
  hook.siman.T_init = optimget (settings, "T_init", .01);
  hook.siman.T_min = optimget (settings, "T_min", 1.0e-5);
  hook.siman.mu_T = optimget (settings, "mu_T", 1.005);
  hook.siman.iters_fixed_T = optimget (settings, "iters_fixed_T", 10);
  hook.stoch_regain_constr = \
      optimget (settings, "stoch_regain_constr", false);
  hook.trace_steps = \
      optimget (settings, "trace_steps", false);
  hook.siman_log = \
      optimget (settings, "siman_log", false);
  backend = optimget (settings, "Algorithm", "lm_feasible");
  backend = map_matlab_algorithm_names (backend);
  backend = map_backend (backend);

  #### handle fixing of parameters
  orig_lbound = lbound;
  orig_ubound = ubound;
  orig_fixed = fixed;
  if (all (fixed))
    error ("no free parameters");
  endif

  nonfixed = ! fixed;
  if (any (fixed))
    ## backend (returned values and initial parameters)
    backend = @ (f, pin, hook) \
	backend_wrapper (backend, fixed, f, pin, hook);

    ## objective function
    f = @ (p, varargin) f (assign (pin, nonfixed, p), varargin{:});

    ## gradient of objective function
    dfdp = @ (p, hook) \
	dfdp (assign (pin, nonfixed, p), hook)(nonfixed);

    ## hessian of objective function
    if (! isempty (hessian))
      hessian = @ (p) \
	  hessian (assign (pin, nonfixed, p))(nonfixed, nonfixed);
    endif
    
    ## function for general inequality constraints
    f_genicstr = @ (p, varargin) \
	f_genicstr (assign (pin, nonfixed, p), varargin{:});
    
    ## jacobian of general inequality constraints
    df_gencstr = @ (p, func, idx, hook) \
	df_gencstr (assign (pin, nonfixed, p), func, idx, hook) \
	(:, nonfixed);

    ## function for general equality constraints
    f_genecstr = @ (p, varargin) \
	f_genecstr (assign (pin, nonfixed, p), varargin{:});

    ## jacobian of general equality constraints
    df_genecstr = @ (p, func, idx, hook) \
	df_genecstr (assign (pin, nonfixed, p), func, idx, hook) \
	(:, nonfixed);

    ## linear inequality constraints
    vc += mc(fixed, :).' * (tp = pin(fixed));
    mc = mc(nonfixed, :);

    ## linear equality constraints
    evc += emc(fixed, :).' * tp;
    emc = emc(nonfixed, :);

    ## _last_ of all, vectors of parameter-related configuration,
    ## including "fixed" itself
    lbound = lbound(nonfixed, :);
    ubound = ubound(nonfixed, :);
    max_fract_change = max_fract_change(nonfixed);
    fract_prec = fract_prec(nonfixed);
    max_rand_step = max_rand_step(nonfixed);
    fixed = fixed(nonfixed);
  endif

  #### supplement constants to jacobian functions

  ## gradient of objective function
  if (dfdp_pstruct)
    dfdp = @ (p, hook) \
	dfdp (p, cell2fields \
	      ({s_diffp, s_diff_onesided, s_orig_lbound, \
		s_orig_ubound, s_plabels, \
		cell2fields(num2cell(hook.fixed), pord(nonfixed), \
			    1, s_orig_fixed), cstep}, \
	       {"diffp", "diff_onesided", "lbound", "ubound", \
		"plabels", "fixed", "h"}, \
	       2, hook));
  else
    dfdp = @ (p, hook) \
	dfdp (p, cell2fields \
	      ({diffp, diff_onesided, orig_lbound, orig_ubound, \
		plabels, assign(orig_fixed, nonfixed, hook.fixed), \
		cstep}, \
	       {"diffp", "diff_onesided", "lbound", "ubound", \
		"plabels", "fixed", "h"}, \
	       2, hook));
  endif

  ## jacobian of general inequality constraints
  if (df_inequc_pstruct)
    df_gencstr = @ (p, func, idx, hook) \
	df_gencstr (p, func, idx, cell2fields \
		    ({s_diffp, s_diff_onesided, s_orig_lbound, \
		      s_orig_ubound, s_plabels, \
		      cell2fields(num2cell(hook.fixed), pord(nonfixed), \
				  1, s_orig_fixed), cstep}, \
		     {"diffp", "diff_onesided", "lbound", "ubound", \
		      "plabels", "fixed", "h"}, \
		     2, hook));
  else
    df_gencstr = @ (p, func, idx, hook) \
	df_gencstr (p, func, idx, cell2fields \
		    ({diffp, diff_onesided, orig_lbound, \
		      orig_ubound, plabels, \
		      assign(orig_fixed, nonfixed, hook.fixed), cstep}, \
		     {"diffp", "diff_onesided", "lbound", "ubound", \
		      "plabels", "fixed", "h"}, \
		     2, hook));
  endif

  ## jacobian of general equality constraints
  if (df_equc_pstruct)
    df_genecstr = @ (p, func, idx, hook) \
	df_genecstr (p, func, idx, cell2fields \
		     ({s_diffp, s_diff_onesided, s_orig_lbound, \
		       s_orig_ubound, s_plabels, \
		       cell2fields(num2cell(hook.fixed), pord(nonfixed), \
				   1, s_orig_fixed), cstep}, \
		      {"diffp", "diff_onesided", "lbound", "ubound", \
		       "plabels", "fixed", "h"}, \
		      2, hook));
  else
    df_genecstr = @ (p, func, idx, hook) \
	df_genecstr (p, func, idx, cell2fields \
		     ({diffp, diff_onesided, orig_lbound, \
		       orig_ubound, plabels, \
		       assign(orig_fixed, nonfixed, hook.fixed), cstep}, \
		      {"diffp", "diff_onesided", "lbound", "ubound", \
		       "plabels", "fixed", "h"}, \
		      2, hook));
  endif

  #### interfaces to constraints
  
  ## include bounds into linear inequality constraints
  tp = eye (sum (nonfixed));
  lidx = lbound != - Inf;
  uidx = ubound != Inf;
  mc = cat (2, tp(:, lidx), - tp(:, uidx), mc);
  vc = cat (1, - lbound(lidx, 1), ubound(uidx, 1), vc);

  ## concatenate linear inequality and equality constraints
  mc = cat (2, mc, emc);
  vc = cat (1, vc, evc);
  n_lincstr = rows (vc);

  ## concatenate general inequality and equality constraints
  if (n_genecstr > 0)
    if (n_genicstr > 0)
      nidxi = 1 : n_genicstr;
      nidxe = n_genicstr + 1 : n_genicstr + n_genecstr;
      f_gencstr = @ (p, idx, varargin) \
	  cat (1, \
	       f_genicstr (p, idx(nidxi), varargin{:}), \
	       f_genecstr (p, idx(nidxe), varargin{:}));
      df_gencstr = @ (p, idx, hook) \
	  cat (1, \
	       df_gencstr (p, @ (p, varargin) \
			   possibly_pstruct_f_genicstr \
			   (p, idx(nidxi), varargin{:}), \
			   idx(nidxi), \
			   setfield (hook, "f", \
				     hook.f(nidxi(idx(nidxi))))), \
	       df_genecstr (p, @ (p, varargin) \
			    possibly_pstruct_f_genecstr \
			    (p, idx(nidxe), varargin{:}), \
			    idx(nidxe), \
			    setfield (hook, "f", \
				      hook.f(nidxe(idx(nidxe))))));
    else
      f_gencstr = f_genecstr;
      df_gencstr = @ (p, idx, hook) \
	  df_genecstr (p, \
		       @ (p, varargin) \
		       possibly_pstruct_f_genecstr \
		       (p, idx, varargin{:}), \
		       idx, \
		       setfield (hook, "f", hook.f(idx)));
    endif
  else
    f_gencstr = f_genicstr;
    df_gencstr = @ (p, idx, hook) \
	df_gencstr (p, \
		    @ (p, varargin) \
		    possibly_pstruct_f_genicstr (p, idx, varargin{:}), \
		    idx, \
		    setfield (hook, "f", hook.f(idx)));
  endif    
  n_gencstr = n_genicstr + n_genecstr;

  ## concatenate linear and general constraints, defining the final
  ## function interfaces
  if (n_gencstr > 0)
    nidxl = 1:n_lincstr;
    nidxh = n_lincstr + 1 : n_lincstr + n_gencstr;
    f_cstr = @ (p, idx, varargin) \
	cat (1, \
	     mc(:, idx(nidxl)).' * p + vc(idx(nidxl), 1), \
	     f_gencstr (p, idx(nidxh), varargin{:}));
    df_cstr = @ (p, idx, hook) \
	cat (1, \
	     mc(:, idx(nidxl)).', \
	     df_gencstr (p, idx(nidxh), \
			 setfield (hook, "f", \
				   hook.f(nidxh))));
  else
    f_cstr = @ (p, idx, varargin) mc(:, idx).' * p + vc(idx, 1);
    df_cstr = @ (p, idx, hook) mc(:, idx).';
  endif

  ## define eq_idx (logical index of equality constraints within all
  ## concatenated constraints
  eq_idx = false (n_lincstr + n_gencstr, 1);
  eq_idx(n_lincstr + 1 - rows (evc) : n_lincstr) = true;
  n_cstr = n_lincstr + n_gencstr;
  eq_idx(n_cstr + 1 - n_genecstr : n_cstr) = true;

  #### prepare interface hook

  ## passed constraints
  hook.mc = mc;
  hook.vc = vc;
  hook.f_cstr = f_cstr;
  hook.df_cstr = df_cstr;
  hook.n_gencstr = n_gencstr;
  hook.eq_idx = eq_idx;
  hook.lbound = lbound;
  hook.ubound = ubound;

  ## passed values of constraints for initial parameters
  hook.pin_cstr = pin_cstr;

  ## passed function for gradient of objective function
  hook.dfdp = dfdp;

  ## passed function for hessian of objective function
  hook.hessian = hessian;

  ## passed function for complementary pivoting
  ## hook.cpiv = cpiv; # set before

  ## passed value of objective function for initial parameters
  hook.f_pin = f_pin;

  ## passed options
  hook.max_fract_change = max_fract_change;
  hook.fract_prec = fract_prec;
  ## hook.TolFun = ; # set before
  ## hook.MaxIter = ; # set before
  hook.fixed = fixed;
  hook.max_rand_step = max_rand_step;

  #### call backend

  [p, objf, cvg, outp] = backend (f, pin, hook);

  if (pin_struct)
    if (pnonscalar)
      p = cell2struct \
	  (cellfun (@ reshape, mat2cell (p, ppartidx), \
		    pdims, "UniformOutput", false), \
	   pord, 1);
    else
      p = cell2struct (num2cell (p), pord, 1);
    endif
  endif

endfunction

function backend = map_matlab_algorithm_names (backend)

  ## nothing done here at the moment

endfunction

function backend = map_backend (backend)

  switch (backend)
      ##    case "sqp_infeasible"
      ##      backend = "__sqp__";
      ##    case "sqp"
      ##      backend = "__sqp__";
    case "lm_feasible"
      backend = "__lm_feasible__";
    case "siman"
      backend = "__siman__";
    otherwise
      error ("no backend implemented for algorithm '%s'", backend);
  endswitch

  backend = str2func (backend);

endfunction

function [p, resid, cvg, outp] = backend_wrapper (backend, fixed, f, p, hook)

  [tp, resid, cvg, outp] = backend (f, p(! fixed), hook);

  p(! fixed) = tp;

endfunction

function lval = assign (lval, lidx, rval)

  lval(lidx) = rval;

endfunction

function m = hessian_struct2mat (s, pord)

  m = cell2mat (fields2cell \
		(structcat (1, NA, fields2cell (s, pord){:}), pord));

  idx = isna (m);

  m(idx) = (m.')(idx);

endfunction

function ret = __optimget__ (s, name, default)

  if (isfield (s, name))
    ret = s.(name);
  elseif (nargin > 2)
    ret = default;
  else
    ret = [];
  endif

endfunction

function ret = apply_idx_if_given  (ret, varargin)

  if (nargin > 1)
    ret = ret(varargin{1});
  endif

endfunction

%!demo
%! ## Example for default optimization (Levenberg/Marquardt with
%! ## BFGS), one non-linear equality constraint. Constrained optimum is
%! ## at p = [0; 1].
%! objective_function = @ (p) p(1)^2 + p(2)^2;
%! pin = [-2; 5];
%! constraint_function = @ (p) p(1)^2 + 1 - p(2);
%! [p, objf, cvg, outp] = nonlin_min (objective_function, pin, optimset ("equc", {constraint_function}))

%!demo
%! ## Example for simulated annealing, two parameters, "trace_steps"
%! ## is true;
%! t_init = .2;
%! t_min = .002;
%! mu_t = 1.002;
%! iters_fixed_t = 10;
%! init_p = [2; 2];
%! max_rand_step = [.2; .2];
%! [p, objf, cvg, outp] = nonlin_min (@ (p) (p(1)/10)^2 + (p(2)/10)^2 + .1 * (-cos(4*p(1)) - cos(4*p(2))), init_p, optimset ("algorithm", "siman", "max_rand_step", max_rand_step, "t_init", t_init, "T_min", t_min, "mu_t", mu_t, "iters_fixed_T", iters_fixed_t, "trace_steps", true));
%! p
%! objf
%! x = (outp.trace(:, 1) - 1) * iters_fixed_t + outp.trace(:, 2);
%! x(1) = 0;
%! plot (x, cat (2, outp.trace(:, 3:end), t_init ./ (mu_t .^ outp.trace(:, 1))))
%! legend ({"objective function value", "p(1)", "p(2)", "Temperature"})
%! xlabel ("subiteration")
