## Copyright (C) 2012   Lukas F. Reichlin
##
## This file is part of LTI Syncope.
##
## LTI Syncope is free software: you can redistribute it and/or modify
## it under the terms of the GNU General Public License as published by
## the Free Software Foundation, either version 3 of the License, or
## (at your option) any later version.
##
## LTI Syncope is distributed in the hope that it will be useful,
## but WITHOUT ANY WARRANTY; without even the implied warranty of
## MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
## GNU General Public License for more details.
##
## You should have received a copy of the GNU General Public License
## along with LTI Syncope.  If not, see <http://www.gnu.org/licenses/>.

## -*- texinfo -*-
## @deftypefn {Function File} {[@var{sys}, @var{x0}, @var{info}] =} moesp (@var{dat}, @dots{})
## @deftypefnx {Function File} {[@var{sys}, @var{x0}, @var{info}] =} moesp (@var{dat}, @var{n}, @dots{})
## @deftypefnx {Function File} {[@var{sys}, @var{x0}, @var{info}] =} moesp (@var{dat}, @var{opt}, @dots{})
## @deftypefnx {Function File} {[@var{sys}, @var{x0}, @var{info}] =} moesp (@var{dat}, @var{n}, @var{opt}, @dots{})
## Estimate state-space model using @acronym{MOESP} algorithm.
## @acronym{MOESP}: Multivariable Output Error State sPace.
## If no output arguments are given, the singular values are
## plotted on the screen in order to estimate the system order.
##
## @strong{Inputs}
## @table @var
## @item dat
## iddata set containing the measurements, i.e. time-domain signals.
## @item n
## The desired order of the resulting state-space system @var{sys}.
## If not specified, @var{n} is chosen automatically according
## to the singular values and tolerances.
## @item @dots{}
## Optional pairs of keys and values.  @code{'key1', value1, 'key2', value2}.
## @item opt
## Optional struct with keys as field names.
## Struct @var{opt} can be created directly or
## by command @command{options}.  @code{opt.key1 = value1, opt.key2 = value2}.
## @end table
##
##
## @strong{Outputs}
## @table @var
## @item sys
## Discrete-time state-space model.
## @item x0
## Initial state vector.  If @var{dat} is a multi-experiment dataset,
## @var{x0} becomes a cell vector containing an initial state vector
## for each experiment.
## @item info
## Struct containing additional information.
## @table @var
## @item info.K
## Kalman gain matrix.
## @item info.Q
## State covariance matrix.
## @item info.Ry
## Output covariance matrix.
## @item info.S
## State-output cross-covariance matrix.
## @item info.L
## Noise variance matrix factor. LL'=Ry.
## @end table
## @end table
##
##
##
## @strong{Option Keys and Values}
## @table @var
## @item 'n'
## The desired order of the resulting state-space system @var{sys}.
## @var{s} > @var{n} > 0.
##
## @item 's'
## The number of block rows @var{s} in the input and output
## block Hankel matrices to be processed.  @var{s} > 0.
## In the MOESP theory, @var{s} should be larger than @var{n},
## the estimated dimension of state vector.
##
## @item 'alg', 'algorithm'
## Specifies the algorithm for computing the triangular
## factor R, as follows:
## @table @var
## @item 'C'
## Cholesky algorithm applied to the correlation
## matrix of the input-output data.  Default method.
## @item 'F'
## Fast QR algorithm.
## @item 'Q'
## QR algorithm applied to the concatenated block
## Hankel matrices.
## @end table
##
## @item 'tol'
## Absolute tolerance used for determining an estimate of
## the system order.  If  @var{tol} >= 0,  the estimate is
## indicated by the index of the last singular value greater
## than or equal to @var{tol}.  (Singular values less than @var{tol}
## are considered as zero.)  When  @var{tol} = 0,  an internally
## computed default value,  @var{tol} = @var{s}*@var{eps}*SV(1),  is used,
## where  SV(1)  is the maximal singular value, and @var{eps} is
## the relative machine precision.
## When @var{tol} < 0,  the estimate is indicated by the
## index of the singular value that has the largest
## logarithmic gap to its successor.  Default value is 0.
##
## @item 'rcond'
## The tolerance to be used for estimating the rank of
## matrices. If the user sets @var{rcond} > 0,  the given value
## of @var{rcond} is used as a lower bound for the reciprocal
## condition number;  an m-by-n matrix whose estimated
## condition number is less than  1/@var{rcond}  is considered to
## be of full rank.  If the user sets @var{rcond} <= 0,  then an
## implicitly computed, default tolerance, defined by
## @var{rcond} = m*n*@var{eps},  is used instead, where @var{eps} is the
## relative machine precision.  Default value is 0.
##
## @item 'confirm'
## Specifies whether or not the user's confirmation of the
## system order estimate is desired, as follows:
## @table @var
## @item true
## User's confirmation.
## @item false
## No confirmation.  Default value.
## @end table
##
## @item 'noiseinput'
## The desired type of noise input channels.
## @table @var
## @item 'n'
## No error inputs.  Default value.
## @iftex
## @tex
## $$ x_{k+1} = A x_k + B u_k $$
## $$ y_k = C x_k + D u_k $$
## @end tex
## @end iftex
## @ifnottex
## @example
## x[k+1] = A x[k] + B u[k]
## y[k]   = C x[k] + D u[k]
## @end example
## @end ifnottex
##
## @item 'e'
## Return @var{sys} as a (p-by-m+p) state-space model with
## both measured input channels u and noise channels e
## with covariance matrix @var{Ry}.
## @iftex
## @tex
## $$ x_{k+1} = A x_k + B u_k + K e_k $$
## $$ y_k = C x_k + D u_k + e_k $$
## @end tex
## @end iftex
## @ifnottex
## @example
## x[k+1] = A x[k] + B u[k] + K e[k]
## y[k]   = C x[k] + D u[k] +   e[k]
## @end example
## @end ifnottex
##
## @item 'v'
## Return @var{sys} as a (p-by-m+p) state-space model with
## both measured input channels u and white noise channels v
## with identity covariance matrix.
## @iftex
## @tex
## $$ x_{k+1} = A x_k + B u_k + K L v_k $$
## $$ y_k = C x_k + D u_k + L v_k $$
## $$ e = L v, \\ L L^T = R_y $$
## @end tex
## @end iftex
## @ifnottex
## @example
## x[k+1] = A x[k] + B u[k] + K L v[k]
## y[k]   = C x[k] + D u[k] +   L v[k]
## e = L v,  L L' = Ry
## @end example
## @end ifnottex
##
## @item 'k'
## Return @var{sys} as a Kalman predictor for simulation.
## @iftex
## @tex
## $$ \\widehat{x}_{k+1} = A \\widehat{x}_k + B u_k + K (y_k - \\widehat{y}_k) $$
## $$ \\widehat{y}_k = C \\widehat{x}_k + D u_k $$
## @end tex
## @end iftex
## @ifnottex
## @example
## ^          ^                        ^
## x[k+1] = A x[k] + B u[k] + K(y[k] - y[k])
## ^          ^
## y[k]   = C x[k] + D u[k]
## @end example
## @end ifnottex
##
## @iftex
## @tex
## $$ \\widehat{x}_{k+1} = (A-KC) \\widehat{x}_k + (B-KD) u_k + K y_k $$
## $$ \\widehat{y}_k = C \\widehat{x}_k + D u_k + 0 y_k $$
## @end tex
## @end iftex
## @ifnottex
## @example
## ^               ^
## x[k+1] = (A-KC) x[k] + (B-KD) u[k] + K y[k]
## ^          ^
## y[k]   = C x[k] + D u[k] + 0 y[k]
## @end example
## @end ifnottex
## @end table
## @end table
##
##
## @strong{Algorithm}@*
## Uses SLICOT IB01AD, IB01BD and IB01CD by courtesy of
## @uref{http://www.slicot.org, NICONET e.V.}
##
## @end deftypefn

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: May 2012
## Version: 0.1

function [sys, x0, info] = moesp (varargin)

  if (nargin == 0)
    print_usage ();
  endif

  if (nargout == 0)
    __slicot_identification__ ("moesp", nargout, varargin{:});
  else
    [sys, x0, info] = __slicot_identification__ ("moesp", nargout, varargin{:});
  endif

endfunction
