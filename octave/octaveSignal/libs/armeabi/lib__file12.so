## Copyright (C) 2007 Sylvain Pelissier <sylvain.pelissier@gmail.com>
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
## @deftypefn {Function File} {[@var{y} @var{i}] =} bitrevorder(@var{x})
## Reorder x in the bit reversed order
## @seealso{fft,ifft}
## @end deftypefn

function [y i] = bitrevorder(x)

  if(nargin < 1 || nargin >1)
    print_usage;
  elseif(log2(length(x)) ~= floor(log2(length(x))))
    error('x must have a length equal to a power of 2');
  end

  old_ind = 0:length(x)-1;
  new_ind = bi2de(fliplr(de2bi(old_ind)));
  i = new_ind + 1;

  y(old_ind+1) = x(i);

endfunction

## The following functions, de2bi and bi2de, are from the communications package.
## However, the communications package is already dependent on the signal
## package and to avoid circular dependencies their code was copied here. Anyway,
## in the future bitrevorder should be rewritten as to not use this functions
## at all (and pkg can be fixed to support circular dependencies on pkg load
## as it already does for pkg install).

## note that aside copying the code from the communication package, their input
## check was removed since in this context they were always being called with
## nargin == 1

function b = de2bi (d, n, p, f)

  p = 2;
  n = floor ( log (max (max (d), 1)) ./ log (p) ) + 1;
  f = 'right-msb';

  d = d(:);
  if ( any (d < 0) || any (d != floor (d)) )
    error ("de2bi: d must only contain non-negative integers");
  endif

  if (isempty (n))
    n = floor ( log (max (max (d), 1)) ./ log (p) ) + 1;
  endif

  power = ones (length (d), 1) * (p .^ [0 : n-1] );
  d = d * ones (1, n);
  b = floor (rem (d, p*power) ./ power);

  if (strcmp (f, 'left-msb'))
    b = b(:,columns(b):-1:1);
  elseif (!strcmp (f, 'right-msb'))
    error ("de2bi: unrecognized flag");
  endif

endfunction


function d = bi2de (b, p, f)

  p = 2;
  f = 'right-msb';

  if ( any (b(:) < 0) || any (b(:) != floor (b(:))) || any (b(:) > p - 1) )
    error ("bi2de: d must only contain integers in the range [0, p-1]");
  endif

  if (strcmp (f, 'left-msb'))
    b = b(:,size(b,2):-1:1);
  elseif (!strcmp (f, 'right-msb'))
    error ("bi2de: unrecognized flag");
  endif

  if (length (b) == 0)
    d = [];
  else
    d = b * ( p .^ [ 0 : (columns(b)-1) ]' );
  endif

endfunction
