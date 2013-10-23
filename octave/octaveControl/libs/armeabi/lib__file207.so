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
## @deftypefn{Function File} {[@var{Gr}, @var{info}] =} spamodred (@var{G}, @dots{})
## @deftypefnx{Function File} {[@var{Gr}, @var{info}] =} spamodred (@var{G}, @var{nr}, @dots{})
## @deftypefnx{Function File} {[@var{Gr}, @var{info}] =} spamodred (@var{G}, @var{opt}, @dots{})
## @deftypefnx{Function File} {[@var{Gr}, @var{info}] =} spamodred (@var{G}, @var{nr}, @var{opt}, @dots{})
##
## Model order reduction by frequency weighted Singular Perturbation Approximation (SPA).
## The aim of model reduction is to find an LTI system @var{Gr} of order
## @var{nr} (nr < n) such that the input-output behaviour of @var{Gr}
## approximates the one from original system @var{G}.
##
## SPA is an absolute error method which tries to minimize
## @iftex
## @tex
## $$ || G - G_r ||_{\\infty} = min $$
## $$ || V \\ (G - G_r) \\ W ||_{\\infty} = min $$
## @end tex
## @end iftex
## @ifnottex
## @example
## ||G-Gr||    = min
##         inf
##
## ||V (G-Gr) W||    = min
##               inf
## @end example
## @end ifnottex
## where @var{V} and @var{W} denote output and input weightings.
##
##
## @strong{Inputs}
## @table @var
## @item G
## LTI model to be reduced.
## @item nr
## The desired order of the resulting reduced order system @var{Gr}.
## If not specified, @var{nr} is chosen automatically according
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
## @item Gr
## Reduced order state-space model.
## @item info
## Struct containing additional information.
## @table @var
## @item info.n
## The order of the original system @var{G}.
## @item info.ns
## The order of the @var{alpha}-stable subsystem of the original system @var{G}.
## @item info.hsv
## The Hankel singular values of the @var{alpha}-stable part of
## the original system @var{G}, ordered decreasingly.
## @item info.nu
## The order of the @var{alpha}-unstable subsystem of both the original
## system @var{G} and the reduced-order system @var{Gr}.
## @item info.nr
## The order of the obtained reduced order system @var{Gr}.
## @end table
## @end table
##
##
## @strong{Option Keys and Values}
## @table @var
## @item 'order', 'nr'
## The desired order of the resulting reduced order system @var{Gr}.
## If not specified, @var{nr} is chosen automatically such that states with
## Hankel singular values @var{info.hsv} > @var{tol1} are retained.
##
## @item 'left', 'output'
## LTI model of the left/output frequency weighting @var{V}.
## Default value is an identity matrix.
##
## @item 'right', 'input'
## LTI model of the right/input frequency weighting @var{W}.
## Default value is an identity matrix.
##
## @item 'method'
## Approximation method for the L-infinity norm to be used as follows:
## @table @var
## @item 'sr', 's'
## Use the square-root Singular Perturbation Approximation method.
## @item 'bfsr', 'p'
## Use the balancing-free square-root Singular Perturbation Approximation method.  Default method.
## @end table
##
## @item 'alpha'
## Specifies the ALPHA-stability boundary for the eigenvalues
## of the state dynamics matrix @var{G.A}.  For a continuous-time
## system, ALPHA <= 0 is the boundary value for
## the real parts of eigenvalues, while for a discrete-time
## system, 0 <= ALPHA <= 1 represents the
## boundary value for the moduli of eigenvalues.
## The ALPHA-stability domain does not include the boundary.
## Default value is 0 for continuous-time systems and
## 1 for discrete-time systems.
##
## @item 'tol1'
## If @var{'order'} is not specified, @var{tol1} contains the tolerance for
## determining the order of the reduced model.
## For model reduction, the recommended value of @var{tol1} is
## c*info.hsv(1), where c lies in the interval [0.00001, 0.001].
## Default value is info.ns*eps*info.hsv(1).
## If @var{'order'} is specified, the value of @var{tol1} is ignored.
##
## @item 'tol2'
## The tolerance for determining the order of a minimal
## realization of the ALPHA-stable part of the given
## model.  TOL2 <= TOL1.
## If not specified, ns*eps*info.hsv(1) is chosen.
##
## @item 'gram-ctrb'
## Specifies the choice of frequency-weighted controllability
## Grammian as follows:
## @table @var
## @item 'standard'
## Choice corresponding to a combination method [4]
## of the approaches of Enns [1] and Lin-Chiu [2,3].  Default method.
## @item 'enhanced'
## Choice corresponding to the stability enhanced
## modified combination method of [4].
## @end table
##
## @item 'gram-obsv'
## Specifies the choice of frequency-weighted observability
## Grammian as follows:
## @table @var
## @item 'standard'
## Choice corresponding to a combination method [4]
## of the approaches of Enns [1] and Lin-Chiu [2,3].  Default method.
## @item 'enhanced'
## Choice corresponding to the stability enhanced
## modified combination method of [4].
## @end table
##
## @item 'alpha-ctrb'
## Combination method parameter for defining the
## frequency-weighted controllability Grammian.
## abs(alphac) <= 1.
## If alphac = 0, the choice of
## Grammian corresponds to the method of Enns [1], while if
## alphac = 1, the choice of Grammian corresponds
## to the method of Lin and Chiu [2,3].
## Default value is 0.
##
## @item 'alpha-obsv'
## Combination method parameter for defining the
## frequency-weighted observability Grammian.
## abs(alphao) <= 1.
## If alphao = 0, the choice of
## Grammian corresponds to the method of Enns [1], while if
## alphao = 1, the choice of Grammian corresponds
## to the method of Lin and Chiu [2,3].
## Default value is 0.
##
## @item 'equil', 'scale'
## Boolean indicating whether equilibration (scaling) should be
## performed on system @var{G} prior to order reduction.
## Default value is true if @code{G.scaled == false} and
## false if @code{G.scaled == true}.
## Note that for @acronym{MIMO} models, proper scaling of both inputs and outputs
## is of utmost importance.  The input and output scaling can @strong{not}
## be done by the equilibration option or the @command{prescale} command
## because these functions perform state transformations only.
## Furthermore, signals should not be scaled simply to a certain range.
## For all inputs (or outputs), a certain change should be of the same
## importance for the model.
## @end table
##
##
## @strong{References}@*
## [1] Enns, D.
## @cite{Model reduction with balanced realizations: An error bound
## and a frequency weighted generalization}.
## Proc. 23-th CDC, Las Vegas, pp. 127-132, 1984.
##
## [2] Lin, C.-A. and Chiu, T.-Y.
## @cite{Model reduction via frequency-weighted balanced realization}.
## Control Theory and Advanced Technology, vol. 8,
## pp. 341-351, 1992.
##
## [3] Sreeram, V., Anderson, B.D.O and Madievski, A.G.
## @cite{New results on frequency weighted balanced reduction
## technique}.
## Proc. ACC, Seattle, Washington, pp. 4004-4009, 1995.
##
## [4] Varga, A. and Anderson, B.D.O.
## @cite{Square-root balancing-free methods for the frequency-weighted
## balancing related model reduction}.
## (report in preparation)
##
##
## @strong{Algorithm}@*
## Uses SLICOT AB09ID by courtesy of
## @uref{http://www.slicot.org, NICONET e.V.}
## @end deftypefn

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: November 2011
## Version: 0.1

function [Gr, info] = spamodred (varargin)

  [Gr, info] = __modred_ab09id__ ("spa", varargin{:});

endfunction

## TODO: add a test