%% Copyright (C) 2005 Julius O. Smith III <jos@ccrma.stanford.edu>
%%
%% This program is free software; you can redistribute it and/or modify it under
%% the terms of the GNU General Public License as published by the Free Software
%% Foundation; either version 3 of the License, or (at your option) any later
%% version.
%%
%% This program is distributed in the hope that it will be useful, but WITHOUT
%% ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
%% FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
%% details.
%%
%% You should have received a copy of the GNU General Public License along with
%% this program; if not, see <http://www.gnu.org/licenses/>.

%% -*- texinfo -*-
%% @deftypefn {Function File} {[@var{zc}, @var{zr}] =} cplxreal (@var{z}, @var{thresh})
%% Split the vector z into its complex (@var{zc}) and real (@var{zr}) elements,
%% eliminating one of each complex-conjugate pair.
%%
%% INPUTS:@*
%%   @itemize
%%   @item
%%   @var{z}      = row- or column-vector of complex numbers@*
%%   @item
%%   @var{thresh} = tolerance threshold for numerical comparisons (default = 100*eps)
%%   @end itemize
%%
%% RETURNED:@*
%%   @itemize
%%   @item
%% @var{zc} = elements of @var{z} having positive imaginary parts@*
%%   @item
%% @var{zr} = elements of @var{z} having zero imaginary part@*
%%   @end itemize
%%
%% Each complex element of @var{z} is assumed to have a complex-conjugate
%% counterpart elsewhere in @var{z} as well.  Elements are declared real
%% if their imaginary parts have magnitude less than @var{thresh}.
%%
%% @seealso{cplxpair}
%% @end deftypefn

function [zc,zr] = cplxreal (z, thresh = 100*eps)

  % interesting for testing: if nargin<2, thresh=1E-3; end

  if isempty(z)
    zc=[];
    zr=[];
  else
    zcp = cplxpair(z); % sort complex pairs, real roots at end
    nz = length(z);
    nzrsec = 0;
    i=nz;
    while i && abs(imag(zcp(i)))<thresh % determine no. of real values
      zcp(i) = real(zcp(i));
      nzrsec = nzrsec+1;
      i=i-1;
    end
    nzsect2 = nz-nzrsec;
    if mod(nzsect2,2)~=0
      error('cplxreal: Odd number of complex values!'); 
    end
    nzsec = nzsect2/2;
    zc = zcp(2:2:nzsect2);
    zr = zcp(nzsect2+1:nz);
  end
endfunction

%!test 
%! [zc,zr] = cplxreal(roots([1 0 0 1]));
%! assert({zc,zr},{0.5+i*sin(pi/3),-1},10*eps);
