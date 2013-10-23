## Copyright (C) 2011 Alan J. Greenberger <alanjg@ptd.net>
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

## IIR Low Pass Filter to Multiband Filter Transformation
##
## [Num,Den,AllpassNum,AllpassDen] = iirlp2mb(B,A,Wo,Wt)
## [Num,Den,AllpassNum,AllpassDen] = iirlp2mb(B,A,Wo,Wt,Pass)
##
## Num,Den:               numerator,denominator of the transformed filter
## AllpassNum,AllpassDen: numerator,denominator of allpass transform,
## B,A:                   numerator,denominator of prototype low pass filter
## Wo:                    normalized_angular_frequency/pi to be transformed
## Wt:                    [phi=normalized_angular_frequencies]/pi target vector
## Pass:                  This parameter may have values 'pass' or 'stop'.  If
##                        not given, it defaults to the value of 'pass'.
##
## With normalized ang. freq. targets 0 < phi(1) <  ... < phi(n) < pi radians
##
## for Pass == 'pass', the target multiband magnitude will be:
##       --------       ----------        -----------...
##      /        \     /          \      /            .
## 0   phi(1) phi(2)  phi(3)   phi(4)   phi(5)   (phi(6))    pi
##
## for Pass == 'stop', the target multiband magnitude will be:
## -------      ---------        ----------...
##        \    /         \      /           .
## 0   phi(1) phi(2)  phi(3)   phi(4)  (phi(5))              pi
##
## Example of use:
## [B, A] = butter(6, 0.5);
## [Num, Den] = iirlp2mb(B, A, 0.5, [.2 .4 .6 .8]);

function [Num,Den,AllpassNum,AllpassDen] = iirlp2mb(varargin)
   usage = sprintf(
    "%s: Usage: [Num,Den,AllpassNum,AllpassDen]=iirlp2mb(B,A,Wo,Wt[,Pass])\n"
    ,mfilename());
   B = varargin{1};  # numerator polynomial of prototype low pass filter
   A = varargin{2};  # denominator polynomial of prototype low pass filter
   Wo = varargin{3}; # (normalized angular frequency)/pi to be transformed
   Wt = varargin{4}; # vector of (norm. angular frequency)/pi transform targets
                     # [phi(1) phi(2) ... ]/pi
   if(nargin < 4 || nargin > 5)
      error("%s",usage)
   endif
   if(nargin == 5)
      Pass = varargin{5};
      switch(Pass)
         case 'pass'
            pass_stop = -1;
         case 'stop'
            pass_stop = 1;
         otherwise
            error("Pass must be 'pass' or 'stop'\n%s",usage)
      endswitch
   else
      pass_stop = -1; # Pass == 'pass' is the default
   endif
   if(Wo <= 0)
      error("Wo is %f <= 0\n%s",Wo,usage);
   endif
   if(Wo >= 1)
      error("Wo is %f >= 1\n%s",Wo,usage);
   endif
   oWt = 0;
   for i = 1 : length(Wt)
      if(Wt(i) <= 0)
         error("Wt(%d) is %f <= 0\n%s",i,Wt(i),usage);
      endif
      if(Wt(i) >= 1)
         error("Wt(%d) is %f >= 1\n%s",i,Wt(i),usage);
      endif
      if(Wt(i) <= oWt)
         error("Wt(%d) = %f, not monotonically increasing\n%s",i,Wt(i),usage);
      else
         oWt = Wt(i);
      endif
   endfor

   #                                                             B(z)
   # Inputs B,A specify the low pass IIR prototype filter G(z) = ---- .
   #                                                             A(z)
   # This module transforms G(z) into a multiband filter using the iterative
   # algorithm from:
   # [FFM] G. Feyh, J. Franchitti, and C. Mullis, "All-Pass Filter
   # Interpolation and Frequency Transformation Problem", Proceedings 20th
   # Asilomar Conference on Signals, Systems and Computers, Nov. 1986, pp.
   # 164-168, IEEE.
   # [FFM] moves the prototype filter position at normalized angular frequency
   # .5*pi to the places specified in the Wt vector times pi.  In this module,
   # a generalization allows the position to be moved on the prototype filter
   # to be specified as Wo*pi instead of being fixed at .5*pi.  This is
   # implemented using two successive allpass transformations.  
   #                                         KK(z)
   # In the first stage, find allpass J(z) = ----  such that
   #                                         K(z)
   #    jWo*pi     -j.5*pi
   # J(e      ) = e                    (low pass to low pass transformation)
   #
   #                                          PP(z) 
   # In the second stage, find allpass H(z) = ----  such that 
   #                                          P(z)
   #    jWt(k)*pi     -j(2k - 1)*.5*pi
   # H(e         ) = e                 (low pass to multiband transformation)
   #
   #                                          ^
   # The variable PP used here corresponds to P in [FFM].
   # len = length(P(z)) == length(PP(z)), the number of polynomial coefficients 
   # 
   #        len      1-i           len       1-i  
   # P(z) = SUM P(i)z   ;  PP(z) = SUM PP(i)z   ; PP(i) == P(len + 1 - i)
   #        i=1                    i=1              (allpass condition)
   # Note: (len - 1) == n in [FFM] eq. 3
   #
   # The first stage computes the denominator of an allpass for translating
   # from a prototype with position .5 to one with a position of Wo. It has the
   # form:
   #          -1
   # K(2)  - z
   # -----------
   #          -1
   # 1 - K(2)z
   #
   # From the low pass to low pass tranformation in Table 7.1 p. 529 of A.
   # Oppenheim and R. Schafer, Discrete-Time Signal Processing 3rd edition,
   # Prentice Hall 2010, one can see that the denominator of an allpass for
   # going in the opposite direction can be obtained by a sign reversal of the
   # second coefficient, K(2), of the vector K (the index 2 not to be confused
   # with a value of z, which is implicit).
   
   # The first stage allpass denominator computation
   K = apd([pi * Wo]);

   # The second stage allpass computation
   phi = pi * Wt; # vector of normalized angular frequencies between 0 and pi
   P = apd(phi);  # calculate denominator of allpass for this target vector
   PP = revco(P); # numerator of allpass has reversed coefficients of P
   
   # The total allpass filter from the two consecutive stages can be written as
   #          PP
   # K(2) -  ---
   #          P         P
   # -----------   *   ---
   #          PP        P
   # 1 - K(2)---
   #          P
   AllpassDen = P - (K(2) * PP);
   AllpassDen /= AllpassDen(1); # normalize
   AllpassNum = pass_stop * revco(AllpassDen);
   [Num,Den] = transform(B,A,AllpassNum,AllpassDen,pass_stop);
endfunction

function [Num,Den] = transform(B,A,PP,P,pass_stop)
   # Given G(Z) = B(Z)/A(Z) and allpass H(z) = PP(z)/P(z), compute G(H(z))
   # For Pass = 'pass', transformed filter is:
   #                          2                   nb-1
   # B1 + B2(PP/P) + B3(PP/P)^  + ... + Bnb(PP/P)^
   # -------------------------------------------------
   #                          2                   na-1
   # A1 + A2(PP/P) + A3(PP/P)^  + ... + Ana(PP/P)^
   # For Pass = 'stop', use powers of (-PP/P)
   #
   na = length(A);  # the number of coefficients in A
   nb = length(B);  # the number of coefficients in B
   # common low pass iir filters have na == nb but in general might not
   n  = max(na,nb); # the greater of the number of coefficients
   #                              n-1
   # Multiply top and bottom by P^   yields:
   #
   #      n-1             n-2          2    n-3                 nb-1    n-nb
   # B1(P^   ) + B2(PP)(P^   ) + B3(PP^ )(P^   ) + ... + Bnb(PP^    )(P^    )
   # ---------------------------------------------------------------------
   #      n-1             n-2          2    n-3                 na-1    n-na
   # A1(P^   ) + A2(PP)(P^   ) + A3(PP^ )(P^   ) + ... + Ana(PP^    )(P^    )

   # Compute and store powers of P as a matrix of coefficients because we will
   # need to use them in descending power order
   global Ppower; # to hold coefficients of powers of P, access inside ppower()
   np = length(P);
   powcols = np + (np-1)*(n-2); # number of coefficients in P^(n-1)
   # initialize to "Not Available" with n-1 rows for powers 1 to (n-1) and
   # the number of columns needed to hold coefficients for P^(n-1)
   Ppower = NA(n-1,powcols);
   Ptemp = P;                   # start with P to the 1st power
   for i = 1 : n-1              # i is the power
      for j = 1 : length(Ptemp) # j is the coefficient index for this power
         Ppower(i,j)  = Ptemp(j);
      endfor
      Ptemp = conv(Ptemp,P);    # increase power of P by one
   endfor

   # Compute numerator and denominator of transformed filter
   Num = [];
   Den = [];
   for i = 1 : n
      #              n-i
      # Regenerate P^    (p_pownmi)
      if((n-i) == 0)
         p_pownmi = [1];
      else
         p_pownmi = ppower(n-i,powcols);
      endif
      #               i-1
      # Regenerate PP^   (pp_powim1)
      if(i == 1)
         pp_powim1 = [1];
      else
         pp_powim1 = revco(ppower(i-1,powcols));
      endif
      if(i <= nb)
         Bterm = (pass_stop^(i-1))*B(i)*conv(pp_powim1,p_pownmi);
         Num = polysum(Num,Bterm);
      endif
      if(i <= na)
         Aterm = (pass_stop^(i-1))*A(i)*conv(pp_powim1,p_pownmi);
         Den = polysum(Den,Aterm);
      endif
   endfor
   # Scale both numerator and denominator to have Den(1) = 1
   temp = Den(1);
   for i = 1 : length(Den)
      Den(i) = Den(i) / temp;
   endfor
   for i = 1 : length(Num)
      Num(i) = Num(i) / temp;
   endfor
endfunction

function P = apd(phi) # all pass denominator
   # Given phi, a vector of normalized angular frequency transformation targets,
   # return P, the denominator of an allpass H(z)
   lenphi = length(phi);
   Pkm1 = 1; # P0 initial condition from [FFM] eq. 22
   for k = 1 : lenphi
      P = pk(Pkm1, k, phi(k)); # iterate
      Pkm1 = P;
   endfor
endfunction

function Pk = pk(Pkm1, k, phik) # kth iteration of P(z)
   # Given Pkminus1, k, and phi(k) in radians , return Pk
   #
   # From [FFM] eq. 19 :                     k
   # Pk =     (z+1  )sin(phi(k)/2)Pkm1 - (-1) (z-1  )cos(phi(k)/2)PPkm1
   # Factoring out z
   #              -1                         k    -1
   #    =   z((1+z  )sin(phi(k)/2)Pkm1 - (-1) (1-z  )cos(phi(k)/2)PPkm1)
   # PPk can also have z factored out.  In H=PP/P, z in PPk will cancel z in Pk,
   # so just leave out.  Use
   #              -1                         k    -1
   # PK =     (1+z  )sin(phi(k)/2)Pkm1 - (-1) (1-z  )cos(phi(k)/2)PPkm1
   # (expand)                                k
   #    =            sin(phi(k)/2)Pkm1 - (-1)        cos(phi(k)/2)PPkm1
   #
   #              -1                         k   -1
   #           + z   sin(phi(k)/2)Pkm1 + (-1)    z   cos(phi(k)/2)PPkm1
   Pk = zeros(1,k+1); # there are k+1 coefficients in Pk
   sin_k = sin(phik/2);
   cos_k = cos(phik/2);
   for i = 1 : k
      Pk(i)   += sin_k * Pkm1(i) - ((-1)^k * cos_k * Pkm1(k+1-i));
      #
      #                    -1
      # Multiplication by z   just shifts by one coefficient
      Pk(i+1) += sin_k * Pkm1(i) + ((-1)^k * cos_k * Pkm1(k+1-i));
   endfor
   # now normalize to Pk(1) = 1 (again will cancel with same factor in PPk)
   Pk1 = Pk(1);
   for i = 1 : k+1
      Pk(i) = Pk(i) / Pk1;
   endfor
endfunction

function PP = revco(p) # reverse components of vector
   l = length(p);
   for i = 1 : l
      PP(l + 1 - i) = p(i);
   endfor
endfunction

function p = ppower(i,powcols) # Regenerate ith power of P from stored PPower
   global Ppower
   if(i == 0)
      p  = 1;
   else
      p  = [];
      for j = 1 : powcols
         if(isna(Ppower(i,j)))
            break;
         endif
         p =  horzcat(p, Ppower(i,j));
      endfor
   endif
endfunction

function poly = polysum(p1,p2) # add polynomials of possibly different length
   n1 = length(p1);
   n2 = length(p2);
   if(n1 > n2)
      # pad p2
      p2 = horzcat(p2, zeros(1,n1-n2));
   elseif(n2 > n1)
      # pad p1
      p1 = horzcat(p1, zeros(1,n2-n1));
   endif
   poly = p1 + p2;
endfunction
