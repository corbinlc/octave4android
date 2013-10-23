## Copyright (C) 2010, 2011, 2012   Lukas F. Reichlin
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
## @deftypefn {Script File} {} test_control
## Execute all available tests at once.
## The Octave control package is based on the @uref{http://www.slicot.org, @acronym{SLICOT}} library.
## @acronym{SLICOT} needs @acronym{BLAS} and @acronym{LAPACK} libraries which are also prerequisites
## for Octave itself.
## In case of failing tests, it is highly recommended to use
## @uref{http://www.netlib.org/blas/, Netlib's reference @acronym{BLAS}} and
## @uref{http://www.netlib.org/lapack/, @acronym{LAPACK}}
## for building Octave.  Using @acronym{ATLAS} may lead to sign changes
## in some entries of the state-space matrices.
## In general, these sign changes are not 'wrong' and can be regarded as
## the result of state transformations.  Such state transformations
## (but not input/output transformations) have no influence on the
## input-output behaviour of the system.  For better numerics,
## the control package uses such transformations by default when
## calculating the frequency responses and a few other things.
## However, arguments like the Hankel singular Values (@acronym{HSV}) must not change.
## Differing @acronym{HSV}s and failing algorithms are known for using Framework Accelerate
## from Mac OS X 10.7.
## @end deftypefn

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: May 2010
## Version: 0.4

## test collection
test ltimodels

## LTI methods
test @lti/c2d
test @lti/d2c
test @lti/feedback
test @lti/horzcat
test @lti/inv
test @lti/minreal
test @lti/mtimes
test @lti/norm
test @lti/plus
test @lti/prescale
test @lti/sminreal
test @lti/subsref
test @lti/zero

## robust control
test h2syn
test hinfsyn
test ncfsyn

## ARE solvers
test care
test dare
test kalman

## Lyapunov
test covar
test dlyap
## test dlyapchol  # TODO: add tests
test gram
test lyap
test lyapchol

## model order reduction
test bstmodred
test btamodred
test hnamodred
## test spamodred  # TODO: create test case

## controller order reduction
test btaconred
test cfconred
test fwcfconred
## test spaconred  # TODO: create test case

## identification
test fitfrd

## various oct-files
test ctrbf
test hsvd
test place

## various m-files
test ctrb
test filt
test initial
test issample
test margin
test obsv
test sigma

## identification
test @iddata/iddata
test @iddata/cat
test @iddata/detrend
test @iddata/fft
test moen4
