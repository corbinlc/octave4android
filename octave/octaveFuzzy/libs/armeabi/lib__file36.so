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
## @deftypefn {Function File} {@var{vpc} =} partition_coeff (@var{soft_partition})
##
## Return the partition coefficient for a given soft partition.
##
## The argument to partition_coeff is:
## @itemize @w
## @item
## @var{soft_partition} - the membership degree of each input data point in each cluster
## @end itemize
##
## The return value is:
## @itemize @w
## @item
## @var{vpc} - the partition coefficient for the given soft partition
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
## @seealso{fcm, gustafson_kessel, partition_entropy, xie_beni_index}
##
## @end deftypefn

## Author:        L. Markowsky
## Keywords:      fuzzy-logic-toolkit partition coefficient cluster
## Directory:     fuzzy-logic-toolkit/inst/
## Filename:      partition_coeff.m
## Last-Modified: 4 Sep 2012

##----------------------------------------------------------------------
## Note: This function is an implementation of Equation 13.9 (corrected
##       -- the equation in the book omits the exponent 2) in
##       Fuzzy Logic: Intelligence, Control and Information, by J. Yen
##       and R. Langari, Prentice Hall, 1999, page 384 (International
##       Edition). 
##----------------------------------------------------------------------

function vpc = partition_coeff (soft_partition)

  ## If partition_coeff was called with an incorrect number of
  ## arguments, or the argument does not have the correct type,
  ## print an error message and halt.

  if (nargin != 1)
    puts ("Type 'help partition_coeff' for more information.\n");
    error ("partition_coeff requires 1 argument\n");
  elseif (!(is_real_matrix (soft_partition) &&
            (min (min (soft_partition)) >= 0) &&
            (max (max (soft_partition)) <= 1)))
    puts ("Type 'help partition_coeff' for more information.\n");
    puts ("partition_coeff's argument must be a matrix of real ");
    puts ("numbers mu, with 0 <= mu <= 1\n");
    error ("invalid argument to partition_coeff\n");
  endif

  ## Compute and return the partition coefficient.

  soft_part_sqr = soft_partition .* soft_partition;
  vpc = (sum (sum (soft_part_sqr))) / columns (soft_partition);

endfunction
