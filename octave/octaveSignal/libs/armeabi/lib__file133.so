## Copyright (C) 2008 Sylvain Pelissier <sylvain.pelissier@gmail.com>
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
## @deftypefn {Function File} {[@var{y}] =} wkeep(@var{x,l,opt})
## Extract the elements of x of size l from the center, the right or the left.
## @end deftypefn

function y = wkeep(x,l,opt = 'c')

  if (nargin < 2|| nargin > 3); print_usage; end
  if(isvector(x))

    if(l > length(x))
      error('l must be or equal the size of x');
    end

    if(opt=='c')
      s = (length(x)-l)./2;
      y = x(1+floor(s):end-ceil(s));

    elseif(opt=='l')
      y=x(1:l);

    elseif(opt=='r')
      y = x(end-l+1:end);

    else
      error('opt must be equal to c, l or r');
    end
  else
    if(length(l) == 2)
      s1 = (length(x)-l(1))./2;
      s2 = (length(x)-l(2))./2;
    else
      error('For a matrix l must be a 1x2 vector');
    end

    if(nargin==2)
      y = x(1+floor(s1):end-ceil(s1),1+floor(s2):end-ceil(s2));
    else
      if(length(opt) == 2)
        firstr=opt(1);
        firstc=opt(2);
      else
        error('For a matrix l must be a 1x2 vector');
      end

      y=x(firstr:firstr+l(1)-1,firstc:firstc+l(2)-1);
    end

  end
end
