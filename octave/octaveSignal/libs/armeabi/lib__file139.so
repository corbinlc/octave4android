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
%% @deftypefn {Function File} {[@var{sos}, @var{g}] =} zp2sos (@var{z}, @var{p})
%% Convert filter poles and zeros to second-order sections.
%%
%% INPUTS:@*
%% @itemize
%% @item
%%   @var{z} = column-vector containing the filter zeros@*
%% @item
%%   @var{p} = column-vector containing the filter poles@*
%% @item
%%   @var{g} = overall filter gain factor
%% @end itemize
%%
%% RETURNED:
%% @itemize
%% @item
%% @var{sos} = matrix of series second-order sections, one per row:@*
%% @var{sos} = [@var{B1}.' @var{A1}.'; ...; @var{BN}.' @var{AN}.'], where@*
%% @code{@var{B1}.'==[b0 b1 b2] and @var{A1}.'==[1 a1 a2]} for 
%% section 1, etc.@*
%% b0 must be nonzero for each section.@*
%% See @code{filter()} for documentation of the
%% second-order direct-form filter coefficients @var{B}i and
%% %@var{A}i, i=1:N.
%%
%% @item
%% @var{Bscale} is an overall gain factor that effectively scales
%% any one of the @var{B}i vectors.
%% @end itemize
%% 
%% EXAMPLE:
%% @example
%%   [z,p,g] = tf2zp([1 0 0 0 0 1],[1 0 0 0 0 .9]);
%%   [sos,g] = zp2sos(z,p,g)
%% 
%% sos =
%%    1.0000    0.6180    1.0000    1.0000    0.6051    0.9587
%%    1.0000   -1.6180    1.0000    1.0000   -1.5843    0.9587
%%    1.0000    1.0000         0    1.0000    0.9791         0
%%
%% g =
%%     1
%% @end example
%%
%% @seealso{sos2pz sos2tf tf2sos zp2tf tf2zp}
%% @end deftypefn

function [sos,g] = zp2sos(z,p,g)

  if nargin<3, g=1; end
  if nargin<2, p=[]; end

  [zc,zr] = cplxreal(z);
  [pc,pr] = cplxreal(p);

  % zc,zr,pc,pr

  nzc=length(zc);
  npc=length(pc);

  nzr=length(zr);
  npr=length(pr);

  % Pair up real zeros:
  if nzr
    if mod(nzr,2)==1, zr=[zr;0]; nzr=nzr+1; end
    nzrsec = nzr/2;
    zrms = -zr(1:2:nzr-1)-zr(2:2:nzr);
    zrp = zr(1:2:nzr-1).*zr(2:2:nzr);
  else
    nzrsec = 0;
  end

  % Pair up real poles:
  if npr
    if mod(npr,2)==1, pr=[pr;0]; npr=npr+1; end
    nprsec = npr/2;
    prms = -pr(1:2:npr-1)-pr(2:2:npr);
    prp = pr(1:2:npr-1).*pr(2:2:npr);
  else
    nprsec = 0;
  end

  nsecs = max(nzc+nzrsec,npc+nprsec);

  % Convert complex zeros and poles to real 2nd-order section form:
  zcm2r = -2*real(zc);
  zca2 = abs(zc).^2;
  pcm2r = -2*real(pc);
  pca2 = abs(pc).^2;

  sos = zeros(nsecs,6);
  sos(:,1) = ones(nsecs,1); % all 2nd-order polynomials are monic
  sos(:,4) = ones(nsecs,1);

  nzrl=nzc+nzrsec; % index of last real zero section
  nprl=npc+nprsec; % index of last real pole section

  for i=1:nsecs

    if i<=nzc % lay down a complex zero pair:
      sos(i,2:3) = [zcm2r(i) zca2(i)];
    elseif i<=nzrl % lay down a pair of real zeros:
      sos(i,2:3) = [zrms(i-nzc) zrp(i-nzc)];
    end

    if i<=npc % lay down a complex pole pair:
      sos(i,5:6) = [pcm2r(i) pca2(i)];
    elseif i<=nprl % lay down a pair of real poles:
      sos(i,5:6) = [prms(i-npc) prp(i-npc)];
    end
  end
end

%!test
%! B=[1 0 0 0 0 1]; A=[1 0 0 0 0 .9];
%! [z,p,g] = tf2zp(B,A);
%! [sos,g] = zp2sos(z,p,g);
%! [Bh,Ah] = sos2tf(sos,g);
%! assert({Bh,Ah},{B,A},100*eps);
