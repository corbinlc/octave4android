## Copyright (C) 2008 Eric Chassande-Mottin, CNRS (France) <ecm@apc.univ-paris7.fr>
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
## @deftypefn {Function File}  {[@var{y} @var{h}]=} resample(@var{x},@var{p},@var{q})
## @deftypefnx {Function File} {@var{y} =} resample(@var{x},@var{p},@var{q},@var{h})
## Change the sample rate of @var{x} by a factor of @var{p}/@var{q}. This is
## performed using a polyphase algorithm. The impulse response @var{h} of the antialiasing
## filter is either specified or either designed with a Kaiser-windowed sinecard.
##
## Ref [1] J. G. Proakis and D. G. Manolakis,
## Digital Signal Processing: Principles, Algorithms, and Applications,
## 4th ed., Prentice Hall, 2007. Chap. 6
##
## Ref [2] A. V. Oppenheim, R. W. Schafer and J. R. Buck,
## Discrete-time signal processing, Signal processing series,
## Prentice-Hall, 1999
## @end deftypefn

function  [y, h] = resample( x, p, q, h )

  if nargchk(3,4,nargin)
    print_usage;
  elseif any([p q]<=0) || any([p q]~=floor([p q])),
    error("resample.m: p and q must be positive integers");
  endif

  ## simplify decimation and interpolation factors

  great_common_divisor=gcd(p,q);
  if (great_common_divisor>1)
    p=p/great_common_divisor;
    q=q/great_common_divisor;
  endif

  ## filter design if required

  if (nargin < 4)

    ## properties of the antialiasing filter

    log10_rejection = -3.0;
    stopband_cutoff_f = 1.0/(2.0 * max(p,q));
    roll_off_width = stopband_cutoff_f / 10.0;

    ## determine filter length
    ## use empirical formula from [2] Chap 7, Eq. (7.63) p 476

    rejection_dB = -20.0*log10_rejection;
    L = ceil((rejection_dB-8.0) / (28.714 * roll_off_width));

    ## ideal sinc filter

    t=(-L:L)';
    ideal_filter=2*p*stopband_cutoff_f*sinc(2*stopband_cutoff_f*t);

    ## determine parameter of Kaiser window
    ## use empirical formula from [2] Chap 7, Eq. (7.62) p 474

    if ((rejection_dB>=21) && (rejection_dB<=50))
      beta = 0.5842 * (rejection_dB-21.0)^0.4 + 0.07886 * (rejection_dB-21.0);
    elseif (rejection_dB>50)
      beta = 0.1102 * (rejection_dB-8.7);
    else
      beta = 0.0;
    endif

    ## apodize ideal filter response

    h=kaiser(2*L+1,beta).*ideal_filter;

  endif

  ## check if input is a row vector
  isrowvector=false;
  if ((rows(x)==1) && (columns(x)>1))
     x=x(:);
     isrowvector=true;
  endif

  ## check if filter is a vector
  if ~isvector(h)
    error("resample.m: the filter h should be a vector");
  endif

  Lx = rows(x);
  Lh = length(h);
  L = ( Lh - 1 )/2.0;
  Ly = ceil(Lx*p/q);

  ## pre and postpad filter response

  nz_pre = floor(q-mod(L,q));
  hpad = prepad(h,Lh+nz_pre);

  offset = floor((L+nz_pre)/q);
  nz_post = 0;
  while ceil( ( (Lx-1)*p + nz_pre + Lh + nz_post )/q ) - offset < Ly
      nz_post++;
  endwhile
  hpad = postpad(hpad,Lh + nz_pre + nz_post);

  ## filtering
  xfilt = upfirdn(x,hpad,p,q);
  y = xfilt(offset+1:offset+Ly,:);

  if isrowvector,
     y=y.';
  endif

endfunction

%!test
%! N=512;
%! p=3; q=5;
%! r=p/q;
%! NN=ceil(r*N);
%! t=0:N-1;
%! tt=0:NN-1;
%! err=zeros(N/2,1);
%! for n = 0:N/2-1,
%!   phi0=2*pi*rand;
%!   f0=n/N;
%!   x=sin(2*pi*f0*t' + phi0);
%!   [y,h]=resample(x,p,q);
%!   xx=sin(2*pi*f0/r*tt' + phi0);
%!   t0=ceil((length(h)-1)/2/q);
%!   idx=t0+1:NN-t0;
%!   err(n+1)=max(abs(y(idx)-xx(idx)));
%! endfor;
%! rolloff=.1;
%! rejection=10^-3;
%! idx_inband=1:ceil((1-rolloff/2)*r*N/2)-1;
%! assert(max(err(idx_inband))<rejection);

%!test
%! N=512;
%! p=3; q=5;
%! r=p/q;
%! NN=ceil(r*N);
%! t=0:N-1;
%! tt=0:NN-1;
%! reject=zeros(N/2,1);
%! for n = 0:N/2-1,
%!   phi0=2*pi*rand;
%!   f0=n/N;
%!   x=sin(2*pi*f0*t' + phi0);
%!   [y,h]=resample(x,p,q);
%!   xx=sin(2*pi*f0/r*tt' + phi0);
%!   t0=ceil((length(h)-1)/2/q);
%!   idx=t0+1:NN-t0;
%!   reject(n+1)=max(abs(y(idx)));
%! endfor;
%! rolloff=.1;
%! rejection=10^-3;
%! idx_stopband=ceil((1+rolloff/2)*r*N/2)+1:N/2;
%! assert(max(reject(idx_stopband))<=rejection);

%!test
%! N=1024;
%! p=2; q=7;
%! r=p/q;
%! NN=ceil(r*N);
%! t=0:N-1;
%! tt=0:NN-1;
%! err=zeros(N/2,1);
%! for n = 0:N/2-1,
%!   phi0=2*pi*rand;
%!   f0=n/N;
%!   x=sin(2*pi*f0*t' + phi0);
%!   [y,h]=resample(x,p,q);
%!   xx=sin(2*pi*f0/r*tt' + phi0);
%!   t0=ceil((length(h)-1)/2/q);
%!   idx=t0+1:NN-t0;
%!   err(n+1)=max(abs(y(idx)-xx(idx)));
%! endfor;
%! rolloff=.1;
%! rejection=10^-3;
%! idx_inband=1:ceil((1-rolloff/2)*r*N/2)-1;
%! assert(max(err(idx_inband))<rejection);
