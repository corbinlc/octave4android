## Copyright (C) 2006, 2007 Arno Onken <asnelt@asnelt.org>
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
## @deftypefn {Function File} {[@var{sequence}, @var{states}] =} hmmgenerate (@var{len}, @var{transprob}, @var{outprob})
## @deftypefnx {Function File} {} hmmgenerate (@dots{}, 'symbols', @var{symbols})
## @deftypefnx {Function File} {} hmmgenerate (@dots{}, 'statenames', @var{statenames})
## Generate an output sequence and hidden states of a hidden Markov model.
## The model starts in state @code{1} at step @code{0} but will not include
## step @code{0} in the generated states and sequence.
##
## @subheading Arguments
##
## @itemize @bullet
## @item
## @var{len} is the number of steps to generate. @var{sequence} and
## @var{states} will have @var{len} entries each.
##
## @item
## @var{transprob} is the matrix of transition probabilities of the states.
## @code{transprob(i, j)} is the probability of a transition to state
## @code{j} given state @code{i}.
##
## @item
## @var{outprob} is the matrix of output probabilities.
## @code{outprob(i, j)} is the probability of generating output @code{j}
## given state @code{i}.
## @end itemize
##
## @subheading Return values
##
## @itemize @bullet
## @item
## @var{sequence} is a vector of length @var{len} of the generated
## outputs. The outputs are integers ranging from @code{1} to
## @code{columns (outprob)}.
##
## @item
## @var{states} is a vector of length @var{len} of the generated hidden
## states. The states are integers ranging from @code{1} to
## @code{columns (transprob)}.
## @end itemize
##
## If @code{'symbols'} is specified, then the elements of @var{symbols} are
## used for the output sequence instead of integers ranging from @code{1} to
## @code{columns (outprob)}. @var{symbols} can be a cell array.
##
## If @code{'statenames'} is specified, then the elements of
## @var{statenames} are used for the states instead of integers ranging from
## @code{1} to @code{columns (transprob)}. @var{statenames} can be a cell
## array.
##
## @subheading Examples
##
## @example
## @group
## transprob = [0.8, 0.2; 0.4, 0.6];
## outprob = [0.2, 0.4, 0.4; 0.7, 0.2, 0.1];
## [sequence, states] = hmmgenerate (25, transprob, outprob)
## @end group
##
## @group
## symbols = @{'A', 'B', 'C'@};
## statenames = @{'One', 'Two'@};
## [sequence, states] = hmmgenerate (25, transprob, outprob,
##                      'symbols', symbols, 'statenames', statenames)
## @end group
## @end example
##
## @subheading References
##
## @enumerate
## @item
## Wendy L. Martinez and Angel R. Martinez. @cite{Computational Statistics
## Handbook with MATLAB}. Appendix E, pages 547-557, Chapman & Hall/CRC,
## 2001.
##
## @item
## Lawrence R. Rabiner. A Tutorial on Hidden Markov Models and Selected
## Applications in Speech Recognition. @cite{Proceedings of the IEEE},
## 77(2), pages 257-286, February 1989.
## @end enumerate
## @end deftypefn

## Author: Arno Onken <asnelt@asnelt.org>
## Description: Output sequence and hidden states of a hidden Markov model

function [sequence, states] = hmmgenerate (len, transprob, outprob, varargin)

  # Check arguments
  if (nargin < 3 || mod (length (varargin), 2) != 0)
    print_usage ();
  endif

  if (! isscalar (len) || len < 0 || round (len) != len)
    error ("hmmgenerate: len must be a non-negative scalar integer")
  endif

  if (! ismatrix (transprob))
    error ("hmmgenerate: transprob must be a non-empty numeric matrix");
  endif
  if (! ismatrix (outprob))
    error ("hmmgenerate: outprob must be a non-empty numeric matrix");
  endif

  # nstate is the number of states of the hidden Markov model
  nstate = rows (transprob);
  # noutput is the number of different outputs that the hidden Markov model
  # can generate
  noutput = columns (outprob);

  # Check whether transprob and outprob are feasible for a hidden Markov
  # model
  if (columns (transprob) != nstate)
    error ("hmmgenerate: transprob must be a square matrix");
  endif
  if (rows (outprob) != nstate)
    error ("hmmgenerate: outprob must have the same number of rows as transprob");
  endif

  # Flag for symbols
  usesym = false;
  # Flag for statenames
  usesn = false;

  # Process varargin
  for i = 1:2:length (varargin)
    # There must be an identifier: 'symbols' or 'statenames'
    if (! ischar (varargin{i}))
      print_usage ();
    endif
    # Upper case is also fine
    lowerarg = lower (varargin{i});
    if (strcmp (lowerarg, 'symbols'))
      if (length (varargin{i + 1}) != noutput)
        error ("hmmgenerate: number of symbols does not match number of possible outputs");
      endif
      usesym = true;
      # Use the following argument as symbols
      symbols = varargin{i + 1};
    # The same for statenames
    elseif (strcmp (lowerarg, 'statenames'))
      if (length (varargin{i + 1}) != nstate)
        error ("hmmgenerate: number of statenames does not match number of states");
      endif
      usesn = true;
      # Use the following argument as statenames
      statenames = varargin{i + 1};
    else
      error ("hmmgenerate: expected 'symbols' or 'statenames' but found '%s'", varargin{i});
    endif
  endfor

  # Each row in transprob and outprob should contain probabilities
  # => scale so that the sum is 1
  # A zero row remains zero
  # - for transprob
  s = sum (transprob, 2);
  s(s == 0) = 1;
  transprob = transprob ./ repmat (s, 1, nstate);
  # - for outprob
  s = sum (outprob, 2);
  s(s == 0) = 1;
  outprob = outprob ./ repmat (s, 1, noutput);

  # Generate sequences of uniformly distributed random numbers between 0 and
  # 1
  # - for the state transitions
  transdraw = rand (1, len);
  # - for the outputs
  outdraw = rand (1, len);

  # Generate the return vectors
  # They remain unchanged if the according probability row of transprob
  # and outprob contain, respectively, only zeros
  sequence = ones (1, len);
  states = ones (1, len);

  if (len > 0)
    # Calculate cumulated probabilities backwards for easy comparison with
    # the generated random numbers
    # Cumulated probability in first column must always be 1
    # We might have a zero row
    # - for transprob
    transprob(:, end:-1:1) = cumsum (transprob(:, end:-1:1), 2);
    transprob(:, 1) = 1;
    # - for outprob
    outprob(:, end:-1:1) = cumsum (outprob(:, end:-1:1), 2);
    outprob(:, 1) = 1;

    # cstate is the current state
    # Start in state 1 but do not include it in the states vector
    cstate = 1;
    for i = 1:len
      # Compare the randon number i of transdraw to the cumulated
      # probability of the state transition and set the transition
      # accordingly
      states(i) = sum (transdraw(i) <= transprob(cstate, :));
      cstate = states(i);
    endfor

    # Compare the random numbers of outdraw to the cumulated probabilities
    # of the outputs and set the sequence vector accordingly
    sequence = sum (repmat (outdraw, noutput, 1) <= outprob(states, :)', 1);

    # Transform default matrices into symbols/statenames if requested
    if (usesym)
      sequence = reshape (symbols(sequence), 1, len);
    endif
    if (usesn)
      states = reshape (statenames(states), 1, len);
    endif
  endif

endfunction

%!test
%! len = 25;
%! transprob = [0.8, 0.2; 0.4, 0.6];
%! outprob = [0.2, 0.4, 0.4; 0.7, 0.2, 0.1];
%! [sequence, states] = hmmgenerate (len, transprob, outprob);
%! assert (length (sequence), len);
%! assert (length (states), len);
%! assert (min (sequence) >= 1);
%! assert (max (sequence) <= columns (outprob));
%! assert (min (states) >= 1);
%! assert (max (states) <= rows (transprob));

%!test
%! len = 25;
%! transprob = [0.8, 0.2; 0.4, 0.6];
%! outprob = [0.2, 0.4, 0.4; 0.7, 0.2, 0.1];
%! symbols = {'A', 'B', 'C'};
%! statenames = {'One', 'Two'};
%! [sequence, states] = hmmgenerate (len, transprob, outprob, 'symbols', symbols, 'statenames', statenames);
%! assert (length (sequence), len);
%! assert (length (states), len);
%! assert (strcmp (sequence, 'A') + strcmp (sequence, 'B') + strcmp (sequence, 'C') == ones (1, len));
%! assert (strcmp (states, 'One') + strcmp (states, 'Two') == ones (1, len));
