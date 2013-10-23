## Copyright (C) 2011   Lukas F. Reichlin
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
## @deftypefn{Function File} {[@var{Kr}, @var{info}] =} spaconred (@var{G}, @var{K}, @dots{})
## @deftypefnx{Function File} {[@var{Kr}, @var{info}] =} spaconred (@var{G}, @var{K}, @var{ncr}, @dots{})
## @deftypefnx{Function File} {[@var{Kr}, @var{info}] =} spaconred (@var{G}, @var{K}, @var{opt}, @dots{})
## @deftypefnx{Function File} {[@var{Kr}, @var{info}] =} spaconred (@var{G}, @var{K}, @var{ncr}, @var{opt}, @dots{})
##
## Controller reduction by frequency-weighted Singular Perturbation Approximation (SPA).
## Given a plant @var{G} and a stabilizing controller @var{K}, determine a reduced
## order controller @var{Kr} such that the closed-loop system is stable and closed-loop
## performance is retained.
##
## The algorithm tries to minimize the frequency-weighted error
## @iftex
## @tex
## $$ || V \\ (K - K_r) \\ W ||_{\\infty} = min $$
## @end tex
## @end iftex
## @ifnottex
## @example
## ||V (K-Kr) W||    = min
##               inf
## @end example
## @end ifnottex
## where @var{V} and @var{W} denote output and input weightings.
##
##
## @strong{Inputs}
## @table @var
## @item G
## LTI model of the plant.
## It has m inputs, p outputs and n states.
## @item K
## LTI model of the controller.
## It has p inputs, m outputs and nc states.
## @item ncr
## The desired order of the resulting reduced order controller @var{Kr}.
## If not specified, @var{ncr} is chosen automatically according
## to the description of key @var{'order'}.
## @item @dots{}
## Optional pairs of keys and values.  @code{"key1", value1, "key2", value2}.
## @item opt
## Optional struct with keys as field names.
## Struct @var{opt} can be created directly or
## by command @command{options}.  @code{opt.key1 = value1, opt.key2 = value2}.
## @end table
##
## @strong{Outputs}
## @table @var
## @item Kr
## State-space model of reduced order controller.
## @item info
## Struct containing additional information.
## @table @var
## @item info.ncr
## The order of the obtained reduced order controller @var{Kr}.
## @item info.ncs
## The order of the alpha-stable part of original controller @var{K}.
## @item info.hsvc
## The Hankel singular values of the alpha-stable part of @var{K}.
## The @var{ncs} Hankel singular values are ordered decreasingly.
## @end table
## @end table
##
## @strong{Option Keys and Values}
## @table @var
## @item 'order', 'ncr'
## The desired order of the resulting reduced order controller @var{Kr}.
## If not specified, @var{ncr} is chosen automatically such that states with
## Hankel singular values @var{info.hsvc} > @var{tol1} are retained.
##
## @item 'method'
## Order reduction approach to be used as follows:
## @table @var
## @item 'sr', 's'
## Use the square-root Singular Perturbation Approximation method.
## @item 'bfsr', 'p'
## Use the balancing-free square-root Singular Perturbation Approximation method.  Default method.
## @end table
##
## @item 'weight'
## Specifies the type of frequency-weighting as follows:
## @table @var
## @item 'none'
## No weightings are used (V = I, W = I).
##
## @item 'left', 'output'
## Use stability enforcing left (output) weighting
## @iftex
## @tex
## $$ V = (I - G K)^{-1} G,  \\qquad W = I $$
## @end tex
## @end iftex
## @ifnottex
## @example
##           -1
## V = (I-G*K) *G ,  W = I
## @end example         
## @end ifnottex
##
## @item 'right', 'input'
## Use stability enforcing right (input) weighting
## @iftex
## @tex
## $$ V = I,  \\qquad W = (I - G K)^{-1} G  $$
## @end tex
## @end iftex
## @ifnottex
## @example
##                    -1
## V = I ,  W = (I-G*K) *G
## @end example                    
## @end ifnottex
##
## @item 'both', 'performance'
## Use stability and performance enforcing weightings
## @iftex
## @tex
## $$ V = (I - G K)^{-1} G,  \\qquad W = (I - G K)^{-1}  $$
## @end tex
## @end iftex
## @ifnottex
## @example
##           -1                -1
## V = (I-G*K) *G ,  W = (I-G*K)
## @end example
## @end ifnottex
## Default value.
## @end table
##
## @item 'feedback'
## Specifies whether @var{K} is a positive or negative feedback controller:
## @table @var
## @item '+'
## Use positive feedback controller.  Default value.
## @item '-'
## Use negative feedback controller.
## @end table
##
## @item 'alpha'
## Specifies the ALPHA-stability boundary for the eigenvalues
## of the state dynamics matrix @var{K.A}.  For a continuous-time
## controller, ALPHA <= 0 is the boundary value for
## the real parts of eigenvalues, while for a discrete-time
## controller, 0 <= ALPHA <= 1 represents the
## boundary value for the moduli of eigenvalues.
## The ALPHA-stability domain does not include the boundary.
## Default value is 0 for continuous-time controllers and
## 1 for discrete-time controllers.
##
## @item 'tol1'
## If @var{'order'} is not specified, @var{tol1} contains the tolerance for
## determining the order of the reduced controller.
## For model reduction, the recommended value of @var{tol1} is
## c*info.hsvc(1), where c lies in the interval [0.00001, 0.001].
## Default value is info.ncs*eps*info.hsvc(1).
## If @var{'order'} is specified, the value of @var{tol1} is ignored.
##
## @item 'tol2'
## The tolerance for determining the order of a minimal
## realization of the ALPHA-stable part of the given
## controller.  TOL2 <= TOL1.
## If not specified, ncs*eps*info.hsvc(1) is chosen.
##
## @item 'gram-ctrb'
## Specifies the choice of frequency-weighted controllability
## Grammian as follows:
## @table @var
## @item 'standard'
## Choice corresponding to standard Enns' method [1].  Default method.
## @item 'enhanced'
## Choice corresponding to the stability enhanced
## modified Enns' method of [2].
## @end table
##
## @item 'gram-obsv'
## Specifies the choice of frequency-weighted observability
## Grammian as follows:
## @table @var
## @item 'standard'
## Choice corresponding to standard Enns' method [1].  Default method.
## @item 'enhanced'
## Choice corresponding to the stability enhanced
## modified Enns' method of [2].
## @end table
##
## @item 'equil', 'scale'
## Boolean indicating whether equilibration (scaling) should be
## performed on @var{G} and @var{K} prior to order reduction.
## Default value is false if both @code{G.scaled == true, K.scaled == true}
## and true otherwise.
## Note that for @acronym{MIMO} models, proper scaling of both inputs and outputs
## is of utmost importance.  The input and output scaling can @strong{not}
## be done by the equilibration option or the @command{prescale} command
## because these functions perform state transformations only.
## Furthermore, signals should not be scaled simply to a certain range.
## For all inputs (or outputs), a certain change should be of the same
## importance for the model.
## @end table
##
## @strong{Algorithm}@*
## Uses SLICOT SB16AD by courtesy of
## @uref{http://www.slicot.org, NICONET e.V.}
## @end deftypefn

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: December 2011
## Version: 0.1

function [Kr, info] = spaconred (varargin)

  [Kr, info] = __conred_sb16ad__ ("spa", varargin{:});

endfunction

## TODO: add a test