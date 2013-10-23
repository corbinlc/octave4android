## Copyright (C) 2010, 2011 Olaf Till <olaf.till@uni-jena.de>
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
## @deftypefn {Function File} {[@var{p}, @var{resid}, @var{cvg}, @var{outp}] =} nonlin_residmin (@var{f}, @var{pin})
## @deftypefnx {Function File} {[@var{p}, @var{resid}, @var{cvg}, @var{outp}] =} nonlin_residmin (@var{f}, @var{pin}, @var{settings})
## Frontend for nonlinear minimization of residuals returned by a model
## function.
##
## The functions supplied by the user have a minimal
## interface; any additionally needed constants (e.g. observed values)
## can be supplied by wrapping the user functions into anonymous
## functions.
##
## The following description applies to usage with vector-based
## parameter handling. Differences in usage for structure-based
## parameter handling will be explained in a separate section below.
##
## @var{f}: function returning the array of residuals. It gets a column
## vector of real parameters as argument. In gradient determination,
## this function may be called with an informational second argument,
## whose content depends on the function for gradient determination.
##
## @var{pin}: real column vector of initial parameters.
##
## @var{settings}: structure whose fields stand for optional settings
## referred to below. The fields can be set by @code{optimset()} with
## Octave versions 3.3.55 or greater; with older Octave versions, the
## fields must be set directly as structure-fields in the correct case.
##
## The returned values are the column vector of final parameters
## @var{p}, the final array of residuals @var{resid}, an integer
## @var{cvg} indicating if and how optimization succeeded or failed, and
## a structure @var{outp} with additional information, curently with
## only one field: @var{niter}, the number of iterations. @var{cvg} is
## greater than zero for success and less than or equal to zero for
## failure; its possible values depend on the used backend and currently
## can be @code{0} (maximum number of iterations exceeded), @code{2}
## (parameter change less than specified precision in two consecutive
## iterations), or @code{3} (improvement in objective function -- e.g.
## sum of squares -- less than specified).
##
## @var{settings}:
##
## @code{Algorithm}: String specifying the backend. Default:
## @code{"lm_svd_feasible"}. The latter is currently the only backend
## distributed with this package. It is described in a separate section
## below.
##
## @code{dfdp}: Function computing the jacobian of the residuals with
## respect to the parameters, assuming residuals are reshaped to a
## vector. Default: finite differences. Will be called with the column
## vector of parameters and an informational structure as arguments. The
## structure has the fields @code{f}: value of residuals for current
## parameters, reshaped to a column vector, @code{fixed}: logical vector
## indicating which parameters are not optimized, so these partial
## derivatives need not be computed and can be set to zero,
## @code{diffp}, @code{diff_onesided}, @code{lbound}, @code{ubound}:
## identical to the user settings of this name, @code{plabels}:
## 1-dimensional cell-array of column-cell-arrays, each column with
## labels for all parameters, the first column contains the numerical
## indices of the parameters. The default jacobian function will call
## the model function with the second argument set with fields @code{f}:
## as the @code{f} passed to the jacobian function, @code{plabels}:
## cell-array of 1x1 cell-arrays with the entries of the
## column-cell-arrays of @code{plabels} as passed to the jacobian
## function corresponding to current parameter, @code{side}: @code{0}
## for one-sided interval, @code{1} or @code{2}, respectively, for the
## sides of a two-sided interval, and @code{parallel}: logical scalar
## indicating parallel computation of partial derivatives.
##
## @code{diffp}: column vector of fractional intervals (doubled for
## central intervals) supposed to be used by jacobian functions
## performing finite differencing. Default: @code{.001 * ones (size (parameters))}. The default jacobian function will use these as
## absolute intervals for parameters with value zero.
##
## @code{diff_onesided}: logical column vector indicating that one-sided
## intervals should be used by jacobian functions performing finite
## differencing. Default: @code{false (size (parameters))}.
##
## @code{complex_step_derivative_f},
## @code{complex_step_derivative_inequc},
## @code{complex_step_derivative_equc}: logical scalars, default: false.
## Estimate Jacobian of model function, general inequality constraints,
## and general equality constraints, respectively, with complex step
## derivative approximation. Use only if you know that your model
## function, function of general inequality constraints, or function of
## general equality constraints, respectively, is suitable for this. No
## user function for the respective Jacobian must be specified.
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
## to be exactly equal to a bound. The default jacobian function will
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
## optimized paraters and the optional argument @code{idx} is a logical
## index. @code{h} has to return the values of all constraints if
## @code{idx} is not given. It may choose to return only the indexed
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
## inequality constraints (see @code{inequc}).
##
## @code{cpiv}: Function for complementary pivoting, usable in
## algorithms for constraints. Default: @ cpiv_bard. Only the default
## function is supplied with the package.
##
## @code{weights}: Array of weights for the residuals. Dimensions must
## match.
##
## @code{TolFun}: Minimum fractional improvement in objective function
## (e.g. sum of squares) in an iteration (termination criterium). Default:
## .0001.
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
## @code{plot_cmd}: Function enabling backend to plot results or
## intermediate results. Will be called with current computed
## residuals. Default: plot nothing.
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
## @code{true}: @code{f_pstruct} (model function), @code{dfdp_pstruct}
## (jacobian of model function), @code{f_inequc_pstruct} (general
## inequality constraints), @code{df_inequc_pstruct} (jacobian of
## general inequality constraints), @code{f_equc_pstruct} (general
## equality constraints), and @code{df_equc_pstruct} (jacobian of
## general equality constraints). If a jacobian-function is configured
## in such a way, it must return the columns of the jacobian as fields
## of a structure under the respective parameter names.
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
## Description of backends (currently only one)
##
## "lm_svd_feasible"
##
## A Levenberg/Marquardt algorithm using singular value decomposition
## and featuring constraints which must be met by the initial parameters
## and are attempted to be kept met throughout the optimization.
##
## Parameters with identical lower and upper bounds will be fixed.
##
## Returned value @var{cvg} will be @code{0}, @code{2}, or @code{3}.
##
## Backend-specific defaults are: @code{MaxIter}: 20, @code{fract_prec}:
## @code{zeros (size (parameters))}, @code{max_fract_change}: @code{Inf}
## for all parameters.
##
## Interpretation of @code{Display}: if set to @code{"iter"}, currently
## @code{plot_cmd} is evaluated for each iteration, and some further
## diagnostics may be printed.
##
## Specific option: @code{lm_svd_feasible_alt_s}: if falling back to
## nearly gradient descent, do it more like original Levenberg/Marquardt
## method, with descent in each gradient component; for testing only.
##
## @seealso {nonlin_curvefit}
## @end deftypefn

function [p, resid, cvg, outp] = nonlin_residmin (varargin)

  if (nargin == 1)
    p = __nonlin_residmin__ (varargin{1});
    return;
  endif

  if (nargin < 2 || nargin > 3)
    print_usage ();
  endif

  if (nargin == 2)
    varargin{3} = struct ();
  endif

  varargin{4} = struct ();

  [p, resid, cvg, outp] = __nonlin_residmin__ (varargin{:});

endfunction

%!demo
%!  ## Example for linear inequality constraints
%!  ## (see also the same example in 'demo nonlin_curvefit')
%!
%!  ## independents
%!  indep = 1:5;
%!  ## residual function:
%!  f = @ (p) p(1) * exp (p(2) * indep) - [1, 2, 4, 7, 14];
%!  ## initial values:
%!  init = [.25; .25];
%!  ## linear constraints, A.' * parametervector + B >= 0
%!  A = [1; -1]; B = 0; # p(1) >= p(2);
%!  settings = optimset ("inequc", {A, B});
%!
%!  ## start optimization
%!  [p, residuals, cvg, outp] = nonlin_residmin (f, init, settings)
