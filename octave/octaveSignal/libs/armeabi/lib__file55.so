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
## @deftypefn {Function File}  {[@var{y} @var{h}]=} fracshift(@var{x},@var{d})
## @deftypefnx {Function File} {@var{y} =} fracshift(@var{x},@var{d},@var{h})
## Shift the series @var{x} by a (possibly fractional) number of samples @var{d}.
## The interpolator @var{h} is either specified or either designed with a Kaiser-windowed sinecard.
## @end deftypefn
## @seealso{circshift}

## Ref [1] A. V. Oppenheim, R. W. Schafer and J. R. Buck,
## Discrete-time signal processing, Signal processing series,
## Prentice-Hall, 1999
##
## Ref [2] T.I. Laakso, V. Valimaki, M. Karjalainen and U.K. Laine
## Splitting the unit delay, IEEE Signal Processing Magazine,
## vol. 13, no. 1, pp 30--59 Jan 1996

function  [y, h] = fracshift( x, d, h )

  if nargchk(2,3,nargin)
    print_usage;
  endif;

  ## if the delay is an exact integer, use circshift
  if d==fix(d)
    y=circshift(x,d);
    return
  endif;

  ## filter design if required

  if (nargin < 4)

    ## properties of the interpolation filter

    log10_rejection = -3.0;
    stopband_cutoff_f = 1.0 / 2.0;
    roll_off_width = stopband_cutoff_f / 10;

    ## determine filter length
    ## use empirical formula from [1] Chap 7, Eq. (7.63) p 476

    rejection_dB = -20.0*log10_rejection;
    L = ceil((rejection_dB-8.0) / (28.714 * roll_off_width));

    ## ideal sinc filter

    t=(-L:L)';
    ideal_filter=2*stopband_cutoff_f*sinc(2*stopband_cutoff_f*(t-(d-fix(d))));

    ## determine parameter of Kaiser window
    ## use empirical formula from [1] Chap 7, Eq. (7.62) p 474

    if ((rejection_dB>=21) && (rejection_dB<=50))
      beta = 0.5842 * (rejection_dB-21.0)^0.4 + 0.07886 * (rejection_dB-21.0);
    elseif (rejection_dB>50)
      beta = 0.1102 * (rejection_dB-8.7);
    else
      beta = 0.0;
    endif

    ## apodize ideal (sincard) filter response

    m = 2*L;
    t = (0 : m)' - (d-fix(d));
    t = 2 * beta / m * sqrt (t .* (m - t));
    w = besseli (0, t) / besseli (0, beta);
    h = w.*ideal_filter;

  endif

  ## check if input is a row vector
  isrowvector=false;
  if ((rows(x)==1) && (columns(x)>1))
     x=x(:);
     isrowvector=true;
  endif

  ## check if filter is a vector
  if ~isvector(h)
    error("fracshift.m: the filter h should be a vector");
  endif

  Lx = length(x);
  Lh = length(h);
  L = ( Lh - 1 )/2.0;
  Ly = Lx;

  ## pre and postpad filter response
  hpad = prepad(h,Lh);
  offset = floor(L);
  hpad = postpad(hpad,Ly + offset);

  ## filtering
  xfilt = upfirdn(x,hpad,1,1);
  y = xfilt(offset+1:offset+Ly,:);

  y=circshift(y,fix(d));

  if isrowvector,
     y=y.';
  endif

endfunction

%!test
%! N=1024;
%! d=1.5;
%! t=(0:N-1)-N/2;
%! tt=t-d;
%! err=zeros(N/2,1);
%! for n = 0:N/2-1,
%!   phi0=2*pi*rand;
%!   f0=n/N;
%!   sigma=N/4;
%!   x=exp(-t'.^2/(2*sigma)).*sin(2*pi*f0*t' + phi0);
%!   [y,h]=fracshift(x,d);
%!   xx=exp(-tt'.^2/(2*sigma)).*sin(2*pi*f0*tt' + phi0);
%!   err(n+1)=max(abs(y-xx));
%! endfor;
%! rolloff=.1;
%! rejection=10^-3;
%! idx_inband=1:ceil((1-rolloff)*N/2)-1;
%! assert(max(err(idx_inband))<rejection);

%!test
%! N=1024;
%! d=7/6;
%! t=(0:N-1)-N/2;
%! tt=t-d;
%! err=zeros(N/2,1);
%! for n = 0:N/2-1,
%!   phi0=2*pi*rand;
%!   f0=n/N;
%!   sigma=N/4;
%!   x=exp(-t'.^2/(2*sigma)).*sin(2*pi*f0*t' + phi0);
%!   [y,h]=fracshift(x,d);
%!   xx=exp(-tt'.^2/(2*sigma)).*sin(2*pi*f0*tt' + phi0);
%!   err(n+1)=max(abs(y-xx));
%! endfor;
%! rolloff=.1;
%! rejection=10^-3;
%! idx_inband=1:ceil((1-rolloff)*N/2)-1;
%! assert(max(err(idx_inband))<rejection);

%!test
%! N=1024;
%! p=6;
%! q=7;
%! d1=64;
%! d2=d1*p/q;
%! t=128;
%! n=zeros(N,1);
%! n(N/2+(-t:t))=randn(2*t+1,1);
%! [b a]=butter(10,.25);
%! n=filter(b,a,n);
%! n1=fracshift(n,d1);
%! n1=resample(n1,p,q);
%! n2=resample(n,p,q);
%! n2=fracshift(n2,d2);
%! err=abs(n2-n1);
%! rejection=10^-3;
%! assert(max(err)<rejection);
