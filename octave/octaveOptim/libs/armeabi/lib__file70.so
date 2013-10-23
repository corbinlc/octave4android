## Copyright (C) 2011 Olaf Till <olaf.till@uni-jena.de>
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
## @deftypefn {Function File} {@var{info} =} residmin_stat (@var{f}, @var{p}, @var{settings})
## Frontend for computation of statistics for a residual-based
## minimization.
##
## @var{settings} is a structure whose fields can be set by
## @code{optimset} with Octave versions 3.3.55 or greater; with older
## Octave versions, the fields must be set directly and in the correct
## case. With @var{settings} the computation of certain statistics is
## requested by setting the fields @code{ret_<name_of_statistic>} to
## @code{true}. The respective statistics will be returned in a
## structure as fields with name @code{<name_of_statistic>}. Depending
## on the requested statistic and on the additional information provided
## in @var{settings}, @var{f} and @var{p} may be empty. Otherwise,
## @var{f} is the model function of an optimization (the interface of
## @var{f} is described e.g. in @code{nonlin_residmin}, please see
## there), and @var{p} is a real column vector with parameters resulting
## from the same optimization.
##
## Currently, the following statistics (or general information) can be
## requested:
##
## @code{dfdp}: Jacobian of model function with respect to parameters.
##
## @code{covd}: Covariance matrix of data (typically guessed by applying
## a factor to the covariance matrix of the residuals).
##
## @code{covp}: Covariance matrix of final parameters.
##
## @code{corp}: Correlation matrix of final parameters.
##
## Further @var{settings}
##
## The functionality of the interface is similar to
## @code{nonlin_residmin}. In particular, structure-based, possibly
## non-scalar, parameters and flagging parameters as fixed are possible.
## The following settings have the same meaning as in
## @code{nonlin_residmin} (please refer to there): @code{param_order},
## @code{param_dims}, @code{f_pstruct}, @code{dfdp_pstruct},
## @code{diffp}, @code{diff_onesided}, @code{complex_step_derivative},
## @code{cstep}, @code{fixed}, and @code{weights}. Similarly,
## @code{param_config} can be used, but only with fields corresponding
## to the settings @code{fixed}, @code{diffp}, and @code{diff_onesided}.
##
## @code{dfdp} can be set in the same way as in @code{nonlin_residmin},
## but alternatively may already contain the computed Jacobian of the
## model function at the final parameters in matrix- or structure-form.
## Users may pass information on the result of the optimization in
## @code{residuals} (self-explaining) and @code{covd} (covariance matrix
## of data). In many cases the type of objective function of the
## optimization must be specified in @code{objf}; currently, there is
## only a backend for the type "wls" (weighted least squares).
##
## Backend-specific information
##
## The backend for @code{objf == "wls"} (currently the only backend)
## computes @code{cord} (due to user request or as a prerequisite for
## @code{covp} and @code{corp}) as a diagonal matrix by assuming that
## the variances of data points are proportional to the reciprocal of
## the squared @code{weights} and guessing the factor of proportionality
## from the residuals. If @code{covp} is not defined (e.g. because the
## Jacobian has no full rank), it makes an attempt to still compute its
## uniquely defined elements, if any, and to find the additional defined
## elements (being @code{1} or @code{-1}), if any, in @code{corp}.
##
## @seealso {curvefit_stat}
## @end deftypefn

function ret = residmin_stat (varargin)

  if (nargin == 1)
    ret = __residmin_stat__ (varargin{1});
    return;
  endif

  if (nargin != 3)
    print_usage ();
  endif

  varargin{4} = struct ();

  ret = __residmin_stat__ (varargin{:});

endfunction
