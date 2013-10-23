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

## This is based on Bard, Nonlinear Parameter Estimation, Academic
## Press, 1974, section 7-5, but also on own re-calculations for the
## specific case of weighted least squares. The part with only certain
## elements of covp or corp being defined is self-made, I don't know a
## reference.

function hook = __covp_corp_wls__ (hook)

  ## compute jacobian, if not already done
  if (! isfield (hook, "jac"))
    hook.jac = hook.dfdp (hook.pfin, hook);
  endif
  jact = (jac = hook.jac).';

  ## compute guessed covariance matrix of data, if not already done
  if (! isfield (hook, "covd"))
    hook = hook.funs.covd (hook);
  endif
  covd_inv = inv (covd = hook.covd);

  if (rcond (A = jact * covd_inv * jac) > eps)

    covp = hook.covp = inv (A);

    d = sqrt (diag (covp));

    hook.corp = covp ./ (d * d.');

  else

    n = hook.np;

    covp = NA (n);

    ## Now we have the equation "A * covp * A.' == A".

    ## Find a particular solution for "covp * A.'".
    part_covp_At = A \ A;

    ## Find a particular solution for "covp". Only uniquely defined
    ## elements (identified later) will be further used.
    part_covp = A \ part_covp_At.';

    ## Find a basis for the nullspace of A.
    if (true) # test for Octave version once submitted patch is applied
				# to Octave (bug #33503)
      null = @ __null_optim__;
    endif
    if (isempty (basis = null (A)))
      error ("internal error, singularity assumed, but null-space computed to be zero-dimensional");
    endif

    ## Find an index (applied to both row and column) of uniquely
    ## defined elements of covp.
    idun = all (basis == 0, 2);

    ## Fill in these elements.
    covp(idun, idun) = part_covp(idun, idun);

    ## Compute corp as far as possible at the moment.
    d = sqrt (diag (covp));
    corp = covp ./ (d * d.');

    ## All diagonal elements of corp should be one, even those as yet
    ## NA.
    corp(1 : n + 1 : n * n) = 1;

    ## If there are indices, applied to both row and column, so that
    ## indexed elements within one row or one column are determined up
    ## to a multiple of a vector, find both these vectors and the
    ## respective indices. In the same run, use them to further fill in
    ## corp as described below.
    for id = 1 : (cb = columns (basis))
      if (any (idx = \
	       all (basis(:, (1 : cb) != id) == 0, 2) & \
	       basis(:, id) != 0))
	vec = sign (basis(idx, id));

	## Depending on "vec", single coefficients of correlation
	## indexed by "idx" are either +1 or -1.
	corp(idx, idx) = vec * vec.';
      endif
    endfor

    hook.covp = covp;
    hook.corp = corp;

  endif

endfunction
