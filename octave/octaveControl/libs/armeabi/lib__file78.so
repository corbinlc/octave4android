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
## @deftypefn {Function File} {@var{dat} =} cat (@var{dim}, @var{dat1}, @var{dat2}, @dots{})
## Concatenate iddata sets along dimension @var{dim}.
##
## @strong{Inputs}
## @table @var
## @item dim
## Dimension along which the concatenation takes place.
## @table @var
## @item 1
## Concatenate samples.
## The samples are concatenated in the following way:
## @code{dat.y@{e@} = [dat1.y@{e@}; dat2.y@{e@}; @dots{}]}
## @code{dat.u@{e@} = [dat1.u@{e@}; dat2.u@{e@}; @dots{}]}
## where @var{e} denotes the experiment.
## The number of experiments, outputs and inputs must be equal for all datasets.
## Equivalent to @command{vertcat}.
##
## @item 2
## Concatenate inputs and outputs.
## The outputs and inputs are concatenated in the following way:
## @code{dat.y@{e@} = [dat1.y@{e@}, dat2.y@{e@}, @dots{}]}
## @code{dat.u@{e@} = [dat1.u@{e@}, dat2.u@{e@}, @dots{}]}
## where @var{e} denotes the experiment.
## The number of experiments and samples must be equal for all datasets.
## Equivalent to @command{horzcat}.
##
## @item 3
## Concatenate experiments.
## The experiments are concatenated in the following way:
## @code{dat.y = [dat1.y; dat2.y; @dots{}]}
## @code{dat.u = [dat1.u; dat2.u; @dots{}]}
## The number of outputs and inputs must be equal for all datasets.
## Equivalent to @command{merge}.
## @end table
##
## @item dat1, dat2, @dots{}
## iddata sets to be concatenated.
## @end table
##
## @strong{Outputs}
## @table @var
## @item dat
## Concatenated iddata set.
## @end table
##
## @seealso{horzcat, merge, vertcat}
## @end deftypefn

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: March 2012
## Version: 0.1

function dat = cat (dim, varargin)

  ## I think this code is pretty elegant because it works for
  ## any number of arguments and without a single for-loop :-)

  ## if this overloaded cat method is called, it is guaranteed that
  ## * nargin > 0
  ## * at least one argument is an iddata object
  if (! is_real_scalar (dim))
    print_usage ();
  endif

  ## store all datasets in a single struct 'tmp'
  ## tmp is not a valid iddata set anymore,
  ## but it doesn't matter, we want just a
  ## temporary struct containing all the data
  tmp = cellfun (@iddata, varargin);
  [n, p, m, e] = cellfun (@size, varargin, "uniformoutput", false);
  
  ## TODO: dat = iddata (ones (100, 3));
  ##       dat = cat (1, dat, zeros (4, 3), dat)

  ## default values for metadata
  ## some of them are overwritten in the switch statement below
  tsam = tmp(1).tsam;
  expname = tmp(1).expname;
  outname = tmp(1).outname;
  outunit = tmp(1).outunit;
  inname = tmp(1).inname;
  inunit = tmp(1).inunit;
  
  check_domain (tmp, e);

  switch (dim)
    case 1                                          # vertcat - catenate samples
      check_experiments (tmp, e);
      check_outputs (tmp, p);
      check_inputs (tmp, m);
    
      y = cellfun (@vertcat, tmp.y, "uniformoutput", false);
      u = cellfun (@vertcat, tmp.u, "uniformoutput", false);
      ## note that this also works for time series (u = {})
    
    case 2                                          # horzcat - catenate channels
      check_experiments (tmp, e);
      check_samples (n);

      y = cellfun (@horzcat, tmp.y, "uniformoutput", false);
      u = cellfun (@horzcat, tmp.u, "uniformoutput", false);

      outname = vertcat (tmp.outname);
      outunit = vertcat (tmp.outunit);
      inname = vertcat (tmp.inname);
      inunit = vertcat (tmp.inunit);

    case 3                                          # merge - catenate experiments
      check_outputs (tmp, p);
      check_inputs (tmp, m);

      y = vertcat (tmp.y);
      u = vertcat (tmp.u);

      tsam = vertcat (tmp.tsam);
      expname = vertcat (tmp.expname);

    otherwise
      error ("iddata: cat: '%d' is an invalid dimension", dim);
  endswitch
  
  dat = iddata (y, u, tsam);

  ## copy metadata
  dat.expname = expname;  
  dat.outname = outname;
  dat.outunit = outunit;
  dat.inname = inname;
  dat.inunit = inunit;
  
  % TODO: handle w

endfunction


function check_experiments (tmp, e)

  if (numel (e) > 1 && ! isequal (e{:}))            # isequal doesn't work with less than 2 arguments
    error ("iddata: cat: number of experiments don't match [%s]", \
           num2str (cell2mat (e), "%d "));
  endif
  
  if (! compare_strings (tmp.expname))
    warning ("iddata: cat: experiment names don't match")
  endif

  if (numel (e) > 1 && ! isequal (tmp.tsam))
    warning ("iddata: cat: sampling times don't match");
  endif

endfunction


function check_outputs (tmp, p)

  if (numel (p) > 1 && ! isequal (p{:}))
    error ("iddata: cat: number of outputs don't match [%s]", \
           num2str (cell2mat (p), "%d "));
  endif
  
  if (! compare_strings (tmp.outname))
    warning ("iddata: cat: output names don't match")
  endif

  if (! compare_strings (tmp.outunit))
    warning ("iddata: cat: output units don't match")
  endif

endfunction


function check_inputs (tmp, m)

  if (numel (m) > 1 && ! isequal (m{:}))
    error ("iddata: cat: number of inputs don't match [%s]", \
           num2str (cell2mat (m), "%d "));
  endif

  if (! compare_strings (tmp.inname))
    warning ("iddata: cat: input names don't match")
  endif

  if (! compare_strings (tmp.inunit))
    warning ("iddata: cat: input units don't match")
  endif

endfunction


function check_samples (n)

  if (numel (n) > 1 && ! isequal (n{:}))
    error ("iddata: cat: number of samples don't match %s", \
           mat2str (vertcat (n{:}), 10));
  endif

endfunction


function check_domain (tmp, e)

  if (numel (e) > 1 && ! isequal (tmp.timedomain))  # isequal doesn't work with less than 2 arguments
    error ("iddata: cat: can't mix time- and frequency-domain datasets");
  endif

endfunction


## kind of strcmp for more than two arguments
## return true if all cells of strings are equal
## and false otherwise
function bool = compare_strings (str, varargin)

  if (nargin > 1)
    ## compare n-th string of first cell with n-th string of remaining cells
    tmp = cellfun (@strcmp, {str}, varargin, "uniformoutput", false);
    ## check whether all strings of each pair are equal
    tmp = cellfun (@all, tmp);
    ## check whether all pairs are equal
    bool = all (tmp);
  else
    ## one or no cell at all is always equal to itself
    bool = true;
  endif

endfunction


%!error (cat (1, iddata (1, 1), iddata ({2, 3}, {2, 3})));
