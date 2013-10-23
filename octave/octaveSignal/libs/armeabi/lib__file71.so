## Copyright (c) 2007 R.G.H. Eschauzier <reschauzier@yahoo.com>
## Copyright (c) 2011 Carnë Draug <carandraug+dev@gmail.com>
## Copyright (c) 2011 Juan Pablo Carbajal <carbajal@ifi.uzh.ch>
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
## @deftypefn{Function File} {[@var{b_out}, @var{a_out}] =} impinvar (@var{b}, @var{a}, @var{fs}, @var{tol})
## @deftypefnx{Function File} {[@var{b_out}, @var{a_out}] =} impinvar (@var{b}, @var{a}, @var{fs})
## @deftypefnx{Function File} {[@var{b_out}, @var{a_out}] =} impinvar (@var{b}, @var{a})
## Converts analog filter with coefficients @var{b} and @var{a} to digital,
## conserving impulse response.
##
## If @var{fs} is not specificied, or is an empty vector, it defaults to 1Hz.
##
## If @var{tol} is not specified, it defaults to 0.0001 (0.1%)
## This function does the inverse of impinvar so that the following example should
## restore the original values of @var{a} and @var{b}.
##
## @command{invimpinvar} implements the reverse of this function.
## @example
## [b, a] = impinvar (b, a);
## [b, a] = invimpinvar (b, a);
## @end example
##
## Reference: Thomas J. Cavicchi (1996) ``Impulse invariance and multiple-order
## poles''. IEEE transactions on signal processing, Vol 40 (9): 2344--2347
##
## @seealso{bilinear, invimpinvar}
## @end deftypefn

function [b_out, a_out] = impinvar (b_in, a_in, fs = 1, tol = 0.0001)

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

  [r_in, p_in, k_in] = residue(b_in, a_in); % partial fraction expansion

  n = length(r_in); % Number of poles/residues

  if (length(k_in)>0) % Greater than zero means we cannot do impulse invariance
    error("Order numerator >= order denominator");
  endif

  r_out = zeros(1,n); % Residues of H(z)
  p_out = zeros(1,n); % Poles of H(z)
  k_out = 0;          % Contstant term of H(z)

  i=1;
  while (i<=n)
    m = 1;
    first_pole = p_in(i); % Pole in the s-domain
    while (i<n && abs(first_pole-p_in(i+1))<tol) % Multiple poles at p(i)
       i++; % Next residue
       m++; % Next multiplicity
    endwhile
    [r, p, k]        = z_res(r_in(i-m+1:i), first_pole, ts); % Find z-domain residues
    k_out           += k;                                    % Add direct term to output
    p_out(i-m+1:i)   = p;                                    % Copy z-domain pole(s) to output
    r_out(i-m+1:i)   = r;                                    % Copy z-domain residue(s) to output

    i++; % Next s-domain residue/pole
  endwhile

  [b_out, a_out] = inv_residue(r_out, p_out, k_out, tol);
  a_out          = to_real(a_out); % Get rid of spurious imaginary part
  b_out          = to_real(b_out);

  % Shift results right to account for calculating in z instead of z^-1
  b_out(end)=[];

endfunction

## Convert residue vector for single and multiple poles in s-domain (located at sm) to
## residue vector in z-domain. The variable k is the direct term of the result.
function [r_out, p_out, k_out] = z_res (r_in, sm, ts)

  p_out = exp(ts * sm); % z-domain pole
  n     = length(r_in); % Multiplicity of the pole
  r_out = zeros(1,n);   % Residue vector

  %% First pole (no multiplicity)
  k_out    = r_in(1) * ts;         % PFE of z/(z-p) = p/(z-p)+1; direct part
  r_out(1) = r_in(1) * ts * p_out; % pole part of PFE

  for i=(2:n) % Go through s-domain residues for multiple pole
    r_out(1:i) += r_in(i) * polyrev(h1_z_deriv(i-1, p_out, ts)); % Add z-domain residues
  endfor

endfunction


%!function err = stozerr(bs,as,fs)
%!
%!  % number of time steps
%!  n=100;
%!
%!  % impulse invariant transform to z-domain
%!  [bz az]=impinvar(bs,as,fs);
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
%!assert(stozerr([1],[1 1],100),0,0.0001);
%!assert(stozerr([1],[1 2 1],100),0,0.0001);
%!assert(stozerr([1 1],[1 2 1],100),0,0.0001);
%!assert(stozerr([1],[1 3 3 1],100),0,0.0001);
%!assert(stozerr([1 1],[1 3 3 1],100),0,0.0001);
%!assert(stozerr([1 1 1],[1 3 3 1],100),0,0.0001);
%!assert(stozerr([1],[1 0 1],100),0,0.0001);
%!assert(stozerr([1 1],[1 0 1],100),0,0.0001);
%!assert(stozerr([1],[1 0 2 0 1],100),0,0.0001);
%!assert(stozerr([1 1],[1 0 2 0 1],100),0,0.0001);
%!assert(stozerr([1 1 1],[1 0 2 0 1],100),0,0.0001);
%!assert(stozerr([1 1 1 1],[1 0 2 0 1],100),0,0.0001);
