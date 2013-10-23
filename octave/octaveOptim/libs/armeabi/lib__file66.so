## Copyright (C) 2011 Olaf Till <olaf.till@uni-jena.de>
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

## Internal function, called by residmin_stat --- see there --- and
## others. Calling __residmin_stat__ indirectly hides the argument
## "hook", usable by wrappers, from users. Currently, hook can contain
## the field "observations". Since much uf the interface code is taken
## from __nonlin_residmin__, it may be that not everything is ideal for
## the present case; but I think it's allright to leave it so.
##
## Some general considerations while making this function:
##
## Different Functions for optimization statistics should be made for
## mere objective function optimization (to be made yet) and
## residual-derived minimization (this function), since there are
## different computing aspects. Don't put the contained functionality
## (statistics) into the respective optimization functions (or
## backends), since different optimization algorithms can share a way to
## compute statistics (e.g. even stochastic optimizers can mimize
## (weighted) squares of residuals). Also, don't use the same frontend
## for optimization and statistics, since the differences in the
## interface for both uses may be confusing otherwise, also the optimset
## options only partially overlap.

## disabled PKG_ADD: __all_opts__ ("__residmin_stat__");

function ret = __residmin_stat__ (f, pfin, settings, hook)

  if (compare_versions (version (), "3.3.55", "<"))
    ## optimset mechanism was fixed for option names with underscores
    ## sometime in 3.3.54+, if I remember right
    optimget = @ __optimget__;
  endif

  ## scalar defaults
  diffp_default = .001;
  cstep_default = 1e-20;

  if (nargin == 1 && ischar (f) && strcmp (f, "defaults"))
    ret = optimset ("param_config", [], \
		    "param_order", [], \
		    "param_dims", [], \
		    "f_pstruct", false, \
		    "dfdp_pstruct", false, \
		    "dfdp", [], \
		    "diffp", [], \
		    "diff_onesided", [], \
		    "complex_step_derivative", false, \
		    "cstep", cstep_default, \
		    "fixed", [], \
		    "weights", [], \
		    "residuals", [], \
		    "covd", [], \
		    "objf", [], \ # no default, e.g. "wls"
		    "ret_dfdp", false, \
		    "ret_covd", false, \
		    "ret_covp", false, \
		    "ret_corp", false);
    return;
  endif

  assign = @ assign; # Is this faster in repeated calls?

  if (nargin != 4)
    error ("incorrect number of arguments");
  endif

  if (ischar (f))
    f = str2func (f);
  endif

  if (! (p_struct = isstruct (pfin)))
    if (! isempty (pfin) && (! isvector (pfin) || columns (pfin) > 1))
      error ("parameters must be either a structure or a column vector");
    endif
  endif

  #### processing of settings and consistency checks

  pconf = optimget (settings, "param_config");
  pord = optimget (settings, "param_order");
  pdims = optimget (settings, "param_dims");
  f_pstruct = optimget (settings, "f_pstruct", false);
  dfdp_pstruct = optimget (settings, "dfdp_pstruct", f_pstruct);
  dfdp = optimget (settings, "dfdp");
  if (ischar (dfdp)) dfdp = str2func (dfdp); endif
  if (isstruct (dfdp)) dfdp_pstruct = true; endif
  diffp = optimget (settings, "diffp");
  diff_onesided = optimget (settings, "diff_onesided");
  fixed = optimget (settings, "fixed");
  residuals = optimget (settings, "residuals");
  do_cstep = optimget (settings, "complex_step_derivative", false);
  cstep = optimget (settings, "cstep", cstep_default);
  if (do_cstep && ! isempty (dfdp))
    error ("both 'complex_step_derivative' and 'dfdp' are set");
  endif

  any_vector_conf = ! (isempty (diffp) && isempty (diff_onesided) && \
		       isempty (fixed));

  ## correct "_pstruct" settings if functions are not supplied
  if (isempty (dfdp)) dfdp_pstruct = false; endif
  if (isempty (f)) f_pstruct = false; endif

  ## some settings require a parameter order
  if (p_struct || ! isempty (pconf) || f_pstruct || dfdp_pstruct)
    if (isempty (pord))
      if (p_struct)
	if (any_vector_conf || \
	    ! ((f_pstruct || isempty (f)) && \
	       (dfdp_pstruct || isempty (dfdp))))
	  error ("no parameter order specified and constructing a parameter order from the structure of parameters can not be done since not all configuration or given functions are structure based");
	else
	  pord = fieldnames (pfin);
	endif
      else
	error ("given settings require specification of parameter order or parameters in the form of a structure");
      endif
    endif
    pord = pord(:);
    if (p_struct && ! all (isfield (pfin, pord)))
      error ("some parameters lacking");
    endif
    if ((nnames = rows (unique (pord))) < rows (pord))
      error ("duplicate parameter names in 'param_order'");
    endif
    if (isempty (pdims))
      if (p_struct)
	pdims = cellfun \
	    (@ size, fields2cell (pfin, pord), "UniformOutput", false);
      else
	pdims = num2cell (ones (nnames, 2), 2);
      endif
    else
      pdims = pdims(:);
      if (p_struct && \
	  ! all (cellfun (@ (x, y) prod (size (x)) == prod (y), \
			  struct2cell (pfin), pdims)))
	error ("given param_dims and dimensions of parameters do not match");
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

  if (p_struct)
    np = sum (pnel);
  else
    np = length (pfin);
    if (! isempty (pord) && np != sum (pnel))
      error ("number of initial parameters not correct");
    endif
  endif
  if (ismatrix (dfdp) && ! ischar (dfdp) && ! isempty (dfdp) && \
      np == 0)
    np = columns (dfdp);
  endif

  plabels = num2cell (num2cell ((1:np).'));
  if (! isempty (pord))
    plabels = cat (2, plabels, num2cell (epord), \
		   num2cell (num2cell (psubidx)));
  endif

  ## some useful vectors
  zerosvec = zeros (np, 1);
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
  else
    ## use supplied configuration vectors

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
  endif

  #### consider whether parameters and functions are based on parameter
  #### structures or parameter vectors; wrappers for call to default
  #### function for jacobians

  ## parameters
  if (p_struct)
    if (pnonscalar)
      pfin = cat (1, cellfun (@ (x, n) reshape (x, n, 1), \
			      fields2cell (pfin, pord), cpnel, \
			      "UniformOutput", false){:});
    else
      pfin = cat (1, fields2cell (pfin, pord){:});
    endif
  endif

  ## model function
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
  if (isempty (residuals))
    if (isempty (f))
      error ("neither model function nor residuals given");
    endif
    residuals = f (pfin);
  endif
  if (isfield (hook, "observations"))
    if (any (size (residuals) != size (obs = hook.observations)))
      error ("dimensions of observations and values of model function must match");
    endif
    f = @ (p) f (p) - obs;
    residuals -= obs;
  endif

  ## jacobian of model function
  if (isempty (dfdp))
    if (! isempty (f))
      if (do_cstep)
	dfdp = @ (p, hook) jacobs (p, f, hook);
      else
	__dfdp__ = @ __dfdp__; # for bug #31484 (Octave <= 3.2.4)
	dfdp = @ (p, hook) __dfdp__ (p, f, hook);
      endif
    endif
  elseif (! isa (dfdp, "function_handle"))
    if (ismatrix (dfdp))
      if (any (size (dfdp) != [prod(size(residuals)), np]))
	error ("jacobian has wrong size");
      endif
    elseif (! dfdp_pstruct)
      error ("jacobian has wrong type");
    endif
    dfdp = @ (varargin) dfdp; # simply make a function returning it
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

  ## parameter-related configuration for jacobian function
  if (dfdp_pstruct)
    if(pnonscalar)
      s_diffp = cell2struct \
	  (cellfun (@ reshape, mat2cell (diffp, ppartidx), \
		    pdims, "UniformOutput", false), pord, 1);
      s_diff_onesided = cell2struct \
	  (cellfun (@ reshape, mat2cell (diff_onesided, ppartidx), \
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
      s_plabels = cell2struct (num2cell (plabels, 2), pord, 1);
      s_fixed = cell2struct (num2cell (fixed), pord, 1);
    endif
  endif

  #### further values and checks

  ## check weights dimensions
  weights = optimget (settings, "weights", ones (size (residuals)));
  if (any (size (weights) != size (residuals)))
    error ("dimension of weights and residuals must match");
  endif


  #### collect remaining settings
  need_dfdp = false;
  covd = optimget (settings, "covd");
  need_objf_label = false;
  if ((ret_dfdp = optimget (settings, "ret_dfdp", false)))
    need_dfdp = true;
  endif
  if ((ret_covd = optimget (settings, "ret_covd", false)))
    need_objf_label = true;
    if (np == 0)
      error ("number of parameters must be known for 'covd', specify either parameters or a jacobian matrix");
    endif
  endif
  if ((ret_covp = optimget (settings, "ret_covp", false)))
    need_objf_label = true;
    need_dfdp = true;
  endif
  if ((ret_corp = optimget (settings, "ret_corp", false)))
    need_objf_label = true;
    need_dfdp = true;
  endif
  if (need_objf_label)
    if (isempty (objf = optimget (settings, "objf")))
      error ("label of objective function must be specified");
    else
      funs = map_objf (objf);
    endif
  else
    funs = struct ();
  endif
  if (isempty (dfdp) && need_dfdp)
    error ("jacobian required and default function for jacobian requires a model function");
  endif

  ####

  ## Everything which is computed is stored in a hook structure which is
  ## passed to and returned by every backend function. This hook is not
  ## identical to the returned structure, since some more results could
  ## be computed by the way.

  #### handle fixing of parameters

  orig_p = pfin;

  if (all (fixed) && ! isempty (fixed))
    error ("no free parameters");
  endif

  ## The policy should be that everything which is computed is left as
  ## it is up to the end --- since other computations might need it in
  ## this form --- and supplemented with values corresponding to fixed
  ## parameters (mostly NA, probably) not until then.

  nonfixed = ! fixed;

  np_after_fixing = sum (nonfixed);

  if (any (fixed))

    if (! isempty (pfin))
      pfin = pfin(nonfixed);
    endif

    ## model function
    f = @ (p, varargin) f (assign (pfin, nonfixed, p), varargin{:});

    ## jacobian of model function
    if (! isempty (dfdp))
      dfdp = @ (p, hook) \
	  dfdp (assign (pfin, nonfixed, p), hook)(:, nonfixed);
    endif
    
  endif

  #### supplement constants to jacobian function
  if (dfdp_pstruct)
    dfdp = @ (p, hook) \
	dfdp (p, cell2fields \
	      ({s_diffp, s_diff_onesided, s_plabels, s_fixed, cstep}, \
	       {"diffp", "diff_onesided", "plabels", "fixed", "h"}, \
	       2, hook));
  else
    if (! isempty (dfdp))
      dfdp = @ (p, hook) \
	  dfdp (p, cell2fields \
		({diffp, diff_onesided, plabels, fixed, cstep}, \
		 {"diffp", "diff_onesided", "plabels", "fixed", "h"}, \
		 2, hook));
    endif
  endif

  #### prepare interface hook

  ## passed final parameters of an optimization
  hook.pfin = pfin;

  ## passed function for derivative of model function
  hook.dfdp = dfdp;

  ## passed function for complementary pivoting
  ## hook.cpiv = cpiv; # set before

  ## passed value of residual function for initial parameters
  hook.residuals = residuals;

  ## passed weights
  hook.weights = weights;

  ## passed dimensions
  hook.np = np_after_fixing;
  hook.nm = prod (size (residuals));

  ## passed statistics functions
  hook.funs = funs;

  ## passed covariance matrix of data (if given by user)
  if (! isempty (covd))
    covd_dims = size (covd);
    if (length (covd_dims) != 2 || any (covd_dims != hook.nm))
      error ("wrong dimensions of covariance matrix of data");
    endif
    hook.covd = covd;
  endif

  #### do the actual work

  if (ret_dfdp)
    hook.jac = hook.dfdp (hook.pfin, hook);
  endif

  if (ret_covd)
    hook = funs.covd (hook);
  endif

  if (ret_covp || ret_corp)
    hook = funs.covp_corp (hook);
  endif

  #### convert (consider fixing ...) and return results

  ret = struct ();

  if (ret_dfdp)
    ret.dfdp = zeros (hook.nm, np);
    ret.dfdp(:, nonfixed) = hook.jac;
  endif

  if (ret_covd)
    ret.covd = hook.covd;
  endif

  if (ret_covp)
    if (any (fixed))
      ret.covp = NA (np);
      ret.covp(nonfixed, nonfixed) = hook.covp;
    else
      ret.covp = hook.covp;
    endif
  endif

  if (ret_corp)
    if (any (fixed))
      ret.corp = NA (np);
      ret.corp(nonfixed, nonfixed) = hook.corp;
    else
      ret.corp = hook.corp;
    endif
  endif

endfunction

function funs = map_objf (objf)

  switch (objf)
    case "wls" # weighted least squares
      funs.covd = str2func ("__covd_wls__");
      funs.covp_corp = str2func ("__covp_corp_wls__");
    otherwise
      error ("no statistics implemented for objective function '%s'", \
	     objf);
  endswitch

endfunction

function lval = assign (lval, lidx, rval)

  lval(lidx) = rval;

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
