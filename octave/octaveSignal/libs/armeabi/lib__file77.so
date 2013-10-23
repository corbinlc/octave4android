## Copyright (c) 2007 R.G.H. Eschauzier <reschauzier@yahoo.com>
## Copyright (c) 2011 Carnë Draug <carandraug+dev@gmail.com>
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
## @deftypefn{Function File} {[@var{b_out}, @var{a_out}] =} invimpinvar (@var{b}, @var{a}, @var{fs}, @var{tol})
## @deftypefnx{Function File} {[@var{b_out}, @var{a_out}] =} invimpinvar (@var{b}, @var{a}, @var{fs})
## @deftypefnx{Function File} {[@var{b_out}, @var{a_out}] =} invimpinvar (@var{b}, @var{a})
## Converts digital filter with coefficients @var{b} and @var{a} to analog,
## conserving impulse response.
##
## This function does the inverse of impinvar so that the following example should
## restore the original values of @var{a} and @var{b}.
## @example
## [b, a] = impinvar (b, a);
## [b, a] = invimpinvar (b, a);
## @end example
##
## If @var{fs} is not specificied, or is an empty vector, it defaults to 1Hz.
##
## If @var{tol} is not specified, it defaults to 0.0001 (0.1%)
##
## Reference: Thomas J. Cavicchi (1996) ``Impulse invariance and multiple-order
## poles''. IEEE transactions on signal processing, Vol 40 (9): 2344--2347
##
## @seealso{bilinear, impinvar}
## @end deftypefn

## Impulse invariant conversion from s to z domain
function [b_out, a_out] = invimpinvar (b_in, a_in, fs = 1, tol = 0.0001)

  if (nargin <2)
    print_usage;
  endif

  ## to be compatible with the matlab implementation where an empty vector can
  ## be used to get the default
  if (isempty(fs))
    ts = 1;
  else
    ts = 1/fs; # we should be using sampling frequencies to be compatible with Matlab
  endif

  b_in = [b_in 0]; %so we can calculate in z instead of z^-1

  [r_in, p_in, k_in] = residue(b_in, a_in); % partial fraction expansion

  n = length(r_in); % Number of poles/residues

  if (length(k_in) > 1) % Greater than one means we cannot do impulse invariance
    error("Order numerator > order denominator");
  endif

  r_out  = zeros(1,n); % Residues of H(s)
  sm_out = zeros(1,n); % Poles of H(s)

  i=1;
  while (i<=n)
    m=1;
    first_pole = p_in(i); % Pole in the z-domain
    while (i<n && abs(first_pole-p_in(i+1))<tol) % Multiple poles at p(i)
       i++; % Next residue
       m++; % Next multiplicity
    endwhile
    [r, sm, k]      = inv_z_res(r_in(i-m+1:i), first_pole, ts); % Find s-domain residues
    k_in           -= k;                                        % Just to check, should end up zero for physical system
    sm_out(i-m+1:i) = sm;                                       % Copy s-domain pole(s) to output
    r_out(i-m+1:i)  = r;                                        % Copy s-domain residue(s) to output

    i++; % Next z-domain residue/pole
  endwhile
  [b_out, a_out] = inv_residue(r_out, sm_out , 0, tol);
  a_out          = to_real(a_out);      % Get rid of spurious imaginary part
  b_out          = to_real(b_out);

  b_out          = polyreduce(b_out);

endfunction

## Inverse function of z_res (see impinvar source)
function [r_out sm_out k_out] = inv_z_res (r_in,p_in,ts)

  n    = length(r_in); % multiplicity of the pole
  r_in = r_in.';       % From column vector to row vector

  j=n;
  while (j>1) % Go through residues starting from highest order down
    r_out(j)   = r_in(j) / ((ts * p_in)^j);                   % Back to binomial coefficient for highest order (always 1)
    r_in(1:j) -= r_out(j) * polyrev(h1_z_deriv(j-1,p_in,ts)); % Subtract highest order result, leaving r_in(j) zero
    j--;
  endwhile

  %% Single pole (no multiplicity)
  r_out(1) = r_in(1) / ((ts * p_in));
  k_out    = r_in(1) / p_in;
  sm_out   = log(p_in) / ts;

endfunction


%!function err = ztoserr(bz,az,fs)
%!
%!  % number of time steps
%!  n=100;
%!
%!  % make sure system is realizable (no delays)
%!  bz=prepad(bz,length(az)-1,0,2);
%!
%!  % inverse impulse invariant transform to s-domain
%!  [bs as]=invimpinvar(bz,az,fs);
%!
%!  % create sys object of transfer function
%!  s=tf(bs,as);
%!
%!  % calculate impulse response of continuous time system
%!  % at discrete time intervals 1/fs
%!  ys=impulse(s,(n-1)/fs,1/fs)';
%!
%!  % impulse response of discrete time system
%!  yz=filter(bz,az,[1 zeros(1,n-1)]);
%!
%!  % find rms error
%!  err=sqrt(sum((yz*fs.-ys).^2)/length(ys));
%!  endfunction
%!
%!assert(ztoserr([1],[1 -0.5],0.01),0,0.0001);
%!assert(ztoserr([1],[1 -1 0.25],0.01),0,0.0001);
%!assert(ztoserr([1 1],[1 -1 0.25],0.01),0,0.0001);
%!assert(ztoserr([1],[1 -1.5 0.75 -0.125],0.01),0,0.0001);
%!assert(ztoserr([1 1],[1 -1.5 0.75 -0.125],0.01),0,0.0001);
%!assert(ztoserr([1 1 1],[1 -1.5 0.75 -0.125],0.01),0,0.0001);
%!assert(ztoserr([1],[1 0 0.25],0.01),0,0.0001);
%!assert(ztoserr([1 1],[1 0 0.25],0.01),0,0.0001);
%!assert(ztoserr([1],[1 0 0.5 0 0.0625],0.01),0,0.0001);
%!assert(ztoserr([1 1],[1 0 0.5 0 0.0625],0.01),0,0.0001);
%!assert(ztoserr([1 1 1],[1 0 0.5 0 0.0625],0.01),0,0.0001);
%!assert(ztoserr([1 1 1 1],[1 0 0.5 0 0.0625],0.01),0,0.0001);
