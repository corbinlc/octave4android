## Copyright (C) 2009 Thomas Sailer
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

## Return bessel analog filter prototype.
##
## References: 
##
## http://en.wikipedia.org/wiki/Bessel_polynomials

function [zero, pole, gain] = besselap (n)

  if (nargin>1 || nargin<1)
    print_usage;
  end

  ## interpret the input parameters
  if (!(length(n)==1 && n == round(n) && n > 0))
    error ("besselap: filter order n must be a positive integer");
  end

  p0=1;
  p1=[1 1];
  for nn=2:n;
    px=(2*nn-1)*p1;
    py=[p0 0 0];
    px=prepad(px,max(length(px),length(py)),0);
    py=prepad(py,length(px));
    p0=p1;
    p1=px+py;
  endfor;
  % p1 now contains the reverse bessel polynomial for n

  % scale it by replacing s->s/w0 so that the gain becomes 1
  p1=p1.*p1(length(p1)).^((length(p1)-1:-1:0)/(length(p1)-1));

  zero=[];
  pole=roots(p1);
  gain=1;

endfunction
