## Copyright (C) 2011   Lukas F. Reichlin
##
## This file is part of LTI Syncope.
##
## LTI Syncope is free software: you can redistribute it and/or modify
## it under the terms of the GNU General Public License as published by
## the Free Software Foundation, either version 3 of the License, or
## (at your option) any later version.
##
## LTI Syncope is distributed in the hope that it will be useful,
## but WITHOUT ANY WARRANTY; without even the implied warranty of
## MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
## GNU General Public License for more details.
##
## You should have received a copy of the GNU General Public License
## along with LTI Syncope.  If not, see <http://www.gnu.org/licenses/>.

## -*- texinfo -*-
## 1.  Discrete -> continuous
## _
## E = alpha*E + A
## _
## A = beta (A - alpha*E)
## _
## B = sqrt(2*alpha*beta) * B
## _                                         -1
## C = sqrt(2*alpha*beta) * C * (alpha*E + A)  * E
## _                        -1
## D = D - C * (alpha*E + A)  * B
## 
## 
## 2.  Continuous -> discrete
## _
## E = beta*E - A
## _
## A = alpha (beta*E + A)
## _
## B = sqrt(2*alpha*beta) * B
## _                                        -1
## C = sqrt(2*alpha*beta) * C * (beta*E - A)  * E
## _                       -1
## D = D + C * (beta*E - A)  * B

## Special thanks to Andras Varga for the formulae.
## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: October 2011
## Version: 0.1

function [Ar, Br, Cr, Dr, Er] = __dss_bilin__ (A, B, C, D, E, beta, discrete)

  if (discrete)
    EpA = E + A;
    s2b = sqrt (2*beta);
    if (rcond (EpA) < eps)
      error ("d2c: E+A singular");
    endif
    CiEpA = C / EpA;
    
    Er = EpA;
    Ar = beta * (A - E);
    Br = s2b * B;
    Cr = s2b * CiEpA * E;
    Dr = D - CiEpA * B;
    
    ## Er = E + A;
    ## Ar = beta * (A - E);
    ## Br = sqrt (2*beta) * B;
    ## Cr = sqrt (2*beta) * C / (E + A) * E;
    ## Dr = D - C / (E + A) * B;
  else
    bEmA = beta*E - A;
    s2b = sqrt (2*beta);
    if (rcond (bEmA) < eps)
      error ("c2d: beta*E-A singular");
    endif
    CibEmA = C / bEmA;
    
    Er = bEmA;
    Ar = beta*E + A;
    Br = s2b * B;
    Cr = s2b * CibEmA * E;
    Dr = D + CibEmA * B;
    
    ## Er = beta*E - A;
    ## Ar = beta*E + A;
    ## Br = sqrt (2*beta) * B;
    ## Cr = sqrt (2*beta) * C / (beta*E - A) * E;
    ## Dr = D + C / (beta*E - A) * B;
  endif

endfunction
