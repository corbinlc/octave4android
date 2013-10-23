## Copyright (C) 2011-2012 L. Markowsky <lmarkov@users.sourceforge.net>
##
## This file is part of the fuzzy-logic-toolkit.
##
## The fuzzy-logic-toolkit is free software; you can redistribute it
## and/or modify it under the terms of the GNU General Public License
## as published by the Free Software Foundation; either version 3 of
## the License, or (at your option) any later version.
##
## The fuzzy-logic-toolkit is distributed in the hope that it will be
## useful, but WITHOUT ANY WARRANTY; without even the implied warranty
## of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
## General Public License for more details.
##
## You should have received a copy of the GNU General Public License
## along with the fuzzy-logic-toolkit; see the file COPYING.  If not,
## see <http://www.gnu.org/licenses/>.

## -*- texinfo -*-
## @deftypefn {Function File} {@var{vpe} =} partition_entropy (@var{soft_partition}, @var{a})
##
## Return the partition entropy for a given soft partition.
##
## The arguments to partition_entropy are:
## @itemize @w
## @item
## @var{soft_partition} - the membership degree of each input data point in each cluster
## @item
## @var{a} - the log base to use in the calculation; must be a real number a > 1
## @end itemize
##
## The return value is:
## @itemize @w
## @item
## @var{vpe} - the partition entropy for the given soft partition
## @end itemize
##
## For demos of this function, please type:
## @example
## demo 'fcm'
## demo 'gustafson_kessel'
## @end example
##
## For more information about the @var{soft_partition} matrix, please see the
## documentation for function fcm.
##
## @seealso{fcm, gustafson_kessel, partition_coeff, xie_beni_index}
##
## @end deftypefn

## Author:        L. Markowsky
## Keywords:      fuzzy-logic-toolkit partition entropy cluster
## Directory:     fuzzy-logic-toolkit/inst/
## Filename:      partition_entropy.m
## Last-Modified: 4 Sep 2012

##----------------------------------------------------------------------
## Note: This function is an implementation of Equation 13.10 in
##       Fuzzy Logic: Intelligence, Control and Information, by J. Yen
##       and R. Langari, Prentice Hall, 1999, page 384 (International
##       Edition). 
##----------------------------------------------------------------------

function vpe = partition_entropy (soft_partition, a)

  ## If partition_entropy was called with an incorrect number of
  ## arguments, or the argument does not have the correct type, print an
  ## error message and halt.

  if (nargin != 2)
    puts ("Type 'help partition_entropy' for more information.\n");
    error ("partition_entropy requires 2 arguments\n");
  elseif (!(is_real_matrix (soft_partition) &&
            (min (min (soft_partition)) >= 0) &&
            (max (max (soft_partition)) <= 1)))
    puts ("Type 'help partition_entropy' for more information.\n");
    puts ("partition_entropy's first argument must be a matrix of ");
    puts ("real numbers mu, with 0 <= mu <= 1\n");
    error ("invalid first argument to partition_entropy\n");
  elseif (!(is_real (a) && a > 1))
    puts ("Type 'help partition_entropy' for more information.\n");
    puts ("partition_entropy's second argument argument must be a ");
    puts ("real number a > 1\n");
    error ("invalid second argument to partition_entropy\n");
  endif

  ## Compute and return the partition entropy.

  n = columns (soft_partition);
  Mu = soft_partition;
  log_a_Mu = log (Mu) / log (a);
  vpe = -(sum (sum (Mu .* log_a_Mu))) / n;

endfunction
