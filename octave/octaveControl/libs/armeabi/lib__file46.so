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
## @deftypefn {Function File} {@var{sys} =} filt (@var{num}, @var{den}, @dots{})
## @deftypefnx {Function File} {@var{sys} =} filt (@var{num}, @var{den}, @var{tsam}, @dots{})
## Create discrete-time transfer function model from data in DSP format.
##
## @strong{Inputs}
## @table @var
## @item num
## Numerator or cell of numerators.  Each numerator must be a row vector
## containing the coefficients of the polynomial in ascending powers of z^-1.
## num@{i,j@} contains the numerator polynomial from input j to output i.
## In the SISO case, a single vector is accepted as well.
## @item den
## Denominator or cell of denominators.  Each denominator must be a row vector
## containing the coefficients of the polynomial in ascending powers of z^-1.
## den@{i,j@} contains the denominator polynomial from input j to output i.
## In the SISO case, a single vector is accepted as well.
## @item tsam
## Sampling time in seconds.  If @var{tsam} is not specified,
## default value -1 (unspecified) is taken.
## @item @dots{}
## Optional pairs of properties and values.
## Type @command{set (filt)} for more information.
## @end table
##
## @strong{Outputs}
## @table @var
## @item sys
## Discrete-time transfer function model.
## @end table
##
## @strong{Option Keys and Values}
## @table @var
## @item 'num'
## Numerator.  See 'Inputs' for details.
##
## @item 'den'
## Denominator.  See 'Inputs' for details.
##
## @item 'tfvar'
## String containing the transfer function variable.
##
## @item 'inv'
## Logical.  True for negative powers of the transfer function variable.
##
## @item 'tsam'
## Sampling time.  See 'Inputs' for details.
##
## @item 'inname'
## The name of the input channels in @var{sys}.
## Cell vector of length m containing strings.
## Default names are @code{@{'u1', 'u2', ...@}}
##
## @item 'outname'
## The name of the output channels in @var{sys}.
## Cell vector of length p containing strings.
## Default names are @code{@{'y1', 'y2', ...@}}
##
## @item 'name'
## String containing the name of the model.
##
## @item 'notes'
## String or cell of string containing comments.
##
## @item 'userdata'
## Any data type.
## @end table
##
## @strong{Example}
## @example
## @group
##                 3 z^-1     
## H(z^-1) = -------------------
##           1 + 4 z^-1 + 2 z^-2
##
## octave:1> H = filt ([0, 3], [1, 4, 2])
## 
## Transfer function 'H' from input 'u1' to output ...
## 
##             3 z^-1       
##  y1:  -------------------
##       1 + 4 z^-1 + 2 z^-2
## 
## Sampling time: unspecified
## Discrete-time model.
## @end group
## @end example
##
## @seealso{tf}
## @end deftypefn

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: April 2012
## Version: 0.1

function sys = filt (num = {}, den = {}, tsam = -1, varargin)

  switch (nargin)
    case 0              # filt ()
      sys = tf ();
      ## sys.inv = true;
      return;

    case 1              # filt (sys), filt (matrix)
      if (isa (num, "lti") || is_real_matrix (num))
        sys = tf (num);
        ## sys.inv = true;  # would be a problem for continuous-time LTI models
        return;
      else
        print_usage ();
      endif

    otherwise           # filt (num, den, ...)
      if (! iscell (num))
        num = {num};
      endif
      if (! iscell (den))
        den = {den};
      endif

      ## convert from z^-1 to z
      ## expand each channel by z^x, where x is the largest exponent of z^-1 (z^-x)

      ## remove trailing zeros
      ## such that polynomials are as short as possible
      num = cellfun (@__remove_trailing_zeros__, num, "uniformoutput", false);
      den = cellfun (@__remove_trailing_zeros__, den, "uniformoutput", false);

      ## make numerator and denominator polynomials equally long
      ## by adding trailing zeros
      lnum = cellfun (@length, num, "uniformoutput", false);
      lden = cellfun (@length, den, "uniformoutput", false);

      lmax = cellfun (@max, lnum, lden, "uniformoutput", false);

      num = cellfun (@postpad, num, lmax, "uniformoutput", false);
      den = cellfun (@postpad, den, lmax, "uniformoutput", false);

      ## use standard tf constructor
      ## sys is stored in standard z form, not z^-1
      ## so we can mix it with regular transfer function models
      ## property "inv", true displays sys in z^-1 form
      sys = tf (num, den, tsam, "inv", true, varargin{:});
  endswitch

endfunction

%!shared num, den, n1, d1, n2, d2, n2e, d2e
%! num = [0, 3];
%! den = [1, 4, 2];
%! sys = filt (num, den);
%! [n1, d1] = filtdata (sys, "vector");
%! [n2, d2] = tfdata (sys, "vector");
%! n2e = [3, 0];
%! d2e = [1, 4, 2];
%!assert (n1, num, 1e-4);
%!assert (d1, den, 1e-4);
%!assert (n2, n2e, 1e-4);
%!assert (d2, d2e, 1e-4);
