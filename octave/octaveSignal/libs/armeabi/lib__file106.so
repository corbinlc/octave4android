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
%% @deftypefn {Function File} {[@var{r}, @var{p}, @var{f}, @var{m}] =} residued (@var{B}, @var{A})
%% Compute the partial fraction expansion (PFE) of filter
%% @math{H(z) = B(z)/A(z)}.
%% In the usual PFE function @code{residuez},
%% the IIR part (poles @var{p} and residues
%% @var{r}) is driven @emph{in parallel} with the FIR part (@var{f}).
%% In this variant (@code{residued}) the IIR part is driven
%% by the @emph{output} of the FIR part.  This structure can be
%% more accurate in signal modeling applications.
%%
%% INPUTS:
%% @var{B} and @var{A} are vectors specifying the digital filter @math{H(z) = B(z)/A(z)}.
%% Say @code{help filter} for documentation of the @var{B} and @var{A}
%% filter coefficients.
%%
%% RETURNED:
%%   @itemize
%%   @item @var{r} = column vector containing the filter-pole residues@*
%%   @item @var{p} = column vector containing the filter poles@*
%%   @item @var{f} = row vector containing the FIR part, if any@*
%%   @item @var{m} = column vector of pole multiplicities
%%   @end itemize
%%
%% EXAMPLES:
%% @example
%%   Say @code{test residued verbose} to see a number of examples.
%% @end example
%%
%% For the theory of operation, see
%% @indicateurl{http://ccrma.stanford.edu/~jos/filters/residued.html}
%%
%% @seealso{residue residued}
%% @end deftypefn

function [r, p, f, m] = residued(b, a, toler)
  % RESIDUED - return residues, poles, and FIR part of B(z)/A(z)
  %
  % Let nb = length(b), na = length(a), and N=na-1 = no. of poles.
  % If nb<na, then f will be empty, and the returned filter is
  %
  %             r(1)                      r(N)
  % H(z) = ----------------  + ... + ----------------- = R(z)
  %        [ 1-p(1)/z ]^e(1)         [ 1-p(N)/z ]^e(N)
  %
  % This is the same result as returned by RESIDUEZ.
  % Otherwise, the FIR part f will be nonempty,
  % and the returned filter is
  %
  % H(z) = f(1) + f(2)/z + f(3)/z^2 + ... + f(nf)/z^M + R(z)/z^M
  %
  % where R(z) is the parallel one-pole filter bank defined above,
  % and M is the order of F(z) = length(f)-1 = nb-na.
  %
  % Note, in particular, that the impulse-response of the parallel
  % (complex) one-pole filter bank starts AFTER that of the the FIR part.
  % In the result returned by RESIDUEZ, R(z) is not divided by z^M,
  % so its impulse response starts at time 0 in parallel with f(n).
  %
  % J.O. Smith, 9/19/05

  if nargin==3,
    warning("tolerance ignored");
  end
  NUM = b(:)';
  DEN = a(:)';
  nb = length(NUM);
  na = length(DEN);
  f = [];
  if na<=nb
    f = filter(NUM,DEN,[1,zeros(nb-na)]);
    NUM = NUM - conv(DEN,f);
    NUM = NUM(nb-na+2:end);
  end
  [r,p,f2,m] = residuez(NUM,DEN);
  if f2, error('f2 not empty as expected'); end
end

%!test
%! B=1; A=[1 -1];
%! [r,p,f,m] = residued(B,A);
%! assert({r,p,f,m},{1,1,[],1},100*eps);
%! [r2,p2,f2,m2] = residuez(B,A);
%! assert({r,p,f,m},{r2,p2,f2,m2},100*eps);
% residuez and residued should be identical when length(B)<length(A)

%!test
%! B=[1 -2 1]; A=[1 -1];
%! [r,p,f,m] = residued(B,A);
%! assert({r,p,f,m},{0,1,[1 -1],1},100*eps);

%!test
%! B=[1 -2 1]; A=[1 -0.5];
%! [r,p,f,m] = residued(B,A);
%! assert({r,p,f,m},{0.25,0.5,[1 -1.5],1},100*eps);

%!test
%! B=1; A=[1 -0.75 0.125];
%! [r,p,f,m] = residued(B,A);
%! [r2,p2,f2,m2] = residuez(B,A);
%! assert({r,p,f,m},{r2,p2,f2,m2},100*eps);
% residuez and residued should be identical when length(B)<length(A)

%!test
%! B=1; A=[1 -2 1];
%! [r,p,f,m] = residued(B,A);
%! [r2,p2,f2,m2] = residuez(B,A);
%! assert({r,p,f,m},{r2,p2,f2,m2},100*eps);
% residuez and residued should be identical when length(B)<length(A)

%!test
%! B=[6,2]; A=[1 -2 1];
%! [r,p,f,m] = residued(B,A);
%! [r2,p2,f2,m2] = residuez(B,A);
%! assert({r,p,f,m},{r2,p2,f2,m2},100*eps);
% residuez and residued should be identical when length(B)<length(A)

%!test
%! B=[1 1 1]; A=[1 -2 1];
%! [r,p,f,m] = residued(B,A);
%! assert(r,[0;3],1e-7);
%! assert(p,[1;1],1e-8);
%! assert(f,1,100*eps);
%! assert(m,[1;2],100*eps);

%!test
%! B=[2 6 6 2]; A=[1 -2 1];
%! [r,p,f,m] = residued(B,A);
%! assert(r,[8;16],3e-7);
%! assert(p,[1;1],1e-8);
%! assert(f,[2,10],100*eps);
%! assert(m,[1;2],100*eps);

%!test
%! B=[1,6,2]; A=[1 -2 1];
%! [r,p,f,m] = residued(B,A);
%! assert(r,[-1;9],3e-7);
%! assert(p,[1;1],1e-8);
%! assert(f,1,100*eps);
%! assert(m,[1;2],100*eps);

%!test
%! B=[1 0 0 0 1]; A=[1 0 0 0 -1];
%! [r,p,f,m] = residued(B,A);
%! [~,is] = sort(angle(p));
%! assert(r(is),[-1/2;-j/2;1/2;j/2],100*eps);
%! assert(p(is),[-1;-j;1;j],100*eps);
%! assert(f,1,100*eps);
%! assert(m,[1;1;1;1],100*eps);
%  Verified in maxima: ratsimp(%I/2/(1-%I * d) - %I/2/(1+%I * d)); etc.
