## Copyright (c) 1998-2011 Andrew V. Knyazev <andrew.knyazev@ucdenver.edu>
## All rights reserved.
##
## Redistribution and use in source and binary forms, with or without",
## modification, are permitted provided that the following conditions are met:
##
##     1 Redistributions of source code must retain the above copyright notice,
##       this list of conditions and the following disclaimer.
##     2 Redistributions in binary form must reproduce the above copyright
##       notice, this list of conditions and the following disclaimer in the
##       documentation and/or other materials provided with the distribution.
##     3 Neither the name of the author nor the names of its contributors may be
##       used to endorse or promote products derived from this software without
##       specific prior written permission.
##
## THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS ''AS IS''
## AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
## IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
## ARE DISCLAIMED. IN NO EVENT SHALL THE AUTHOR OR CONTRIBUTORS BE LIABLE FOR
## ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
## DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
## SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
## CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
## OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
## OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

% function [A,REF,HMAX,H,R,EQUAL] = polyfitinf(M,N,K,X,Y,EPSH,MAXIT,REF0)
%
%   Best polynomial approximation in discrete uniform norm
%
%   INPUT VARIABLES:
%
%   M       : degree of the fitting polynomial
%   N       : number of data points
%   X(N)    : x-coordinates of data points
%   Y(N)    : y-coordinates of data points
%   K       : character of the polynomial:
%                   K = 0 : mixed parity polynomial
%                   K = 1 : odd polynomial  ( X(1) must be >  0 )
%                   K = 2 : even polynomial ( X(1) must be >= 0 )
%   EPSH    : tolerance for leveling. A useful value for 24-bit
%             mantissa is EPSH = 2.0E-7
%   MAXIT   : upper limit for number of exchange steps
%   REF0(M2): initial alternating set ( N-vector ). This is an
%             OPTIONAL argument. The length M2 is given by:
%                   M2 = M + 2                      , if K = 0
%                   M2 = integer part of (M+3)/2    , if K = 1
%                   M2 = 2 + M/2 (M must be even)   , if K = 2
%
%   OUTPUT VARIABLES:
%
%   A       : polynomial coefficients of the best approximation
%             in order of increasing powers:
%                   p*(x) = A(1) + A(2)*x + A(3)*x^2 + ...
%   REF     : selected alternating set of points
%   HMAX    : maximum deviation ( uniform norm of p* - f )
%   H       : pointwise approximation errors
%	R		: total number of iterations
%   EQUAL   : success of failure of algorithm
%                   EQUAL=1 :  succesful
%                   EQUAL=0 :  convergence not acheived
%                   EQUAL=-1:  input error
%                   EQUAL=-2:  algorithm failure
%
%   Relies on function EXCH, provided below.
%
%   Example: 
%   M = 5; N = 10000; K = 0; EPSH = 10^-12; MAXIT = 10;
%   X = linspace(-1,1,N);   % uniformly spaced nodes on [-1,1]
%   k=1; Y = abs(X).^k;     % the function Y to approximate
%   [A,REF,HMAX,H,R,EQUAL] = polyfitinf(M,N,K,X,Y,EPSH,MAXIT);
%   p = polyval(A,X); plot(X,Y,X,p) % p is the best approximation
%
%   Note: using an even value of M, e.g., M=2, in the example above, makes
%   the algorithm to fail with EQUAL=-2, because of collocation, which
%   appears because both the appriximating function and the polynomial are
%   even functions. The way aroung it is to approximate only the right half
%   of the function, setting K = 2 : even polynomial. For example: 
%
% N = 10000; K = 2; EPSH = 10^-12; MAXIT = 10;  X = linspace(0,1,N);
% for i = 1:2
%     k = 2*i-1; Y = abs(X).^k;
%     for j = 1:4
%         M = 2^j;
%         [~,~,HMAX] = polyfitinf(M,N,K,X,Y,EPSH,MAXIT);
%         approxerror(i,j) = HMAX;
%     end
% end
% disp('Table 3.1 from Approximation theory and methods, M.J.D.POWELL, p. 27');
% disp(' ');
% disp('            n          K=1          K=3'); 
% disp(' '); format short g;
% disp([(2.^(1:4))' approxerror']);
%
%   ALGORITHM:
%
%   Computation of the polynomial that best approximates the data (X,Y)
%   in the discrete uniform norm, i.e. the polynomial with the  minimum
%   value of max{ | p(x_i) - y_i | , x_i in X } . That polynomial, also
%   known as minimax polynomial, is obtained by the exchange algorithm,
%   a finite iterative process requiring, at most,
%      n
%    (   ) iterations ( usually p = M + 2. See also function EXCH ).
%      p
%   since this number can be very large , the routine  may not converge
%   within MAXIT iterations . The  other possibility of  failure occurs
%   when there is insufficient floating point precision  for  the input
%   data chosen.
%
%   CREDITS: This routine was developed and modified as 
%   computer assignments in Approximation Theory courses by 
%   Prof. Andrew Knyazev, University of Colorado Denver, USA.
%
%   Team Fall 98 (Revision 1.0):
%           Chanchai Aniwathananon
%           Crhistopher Mehl
%           David A. Duran
%           Saulo P. Oliveira
%
%   Team Spring 11 (Revision 1.1): Manuchehr Aminian
%
%   The algorithm and the comments are based on a FORTRAN code written
%   by Joseph C. Simpson. The code is available on Netlib repository:
%   http://www.netlib.org/toms/501
%   See also: Communications of the ACM, V14, pp.355-356(1971)
%
%   NOTES:
%
%   1) A may contain the collocation polynomial
%   2) If MAXIT is exceeded, REF contains a new reference set
%   3) M, EPSH and REF can be altered during the execution
%   4) To keep consistency to the original code , EPSH can be
%   negative. However, the use of REF0 is *NOT* determined by
%   EPSH< 0, but only by its inclusion as an input parameter.
%
%   Some parts of the code can still take advantage of vectorization.  
%
%   Revision 1.0 from 1998 is a direct human translation of 
%   the FORTRAN code http://www.netlib.org/toms/501
%   Revision 1.1 is a clean-up and technical update.  
%   Tested on MATLAB Version 7.11.0.584 (R2010b) and 
%   GNU Octave Version 3.2.4

%   $Revision: 1.1 $  $Date: 2011/08/3 $

%       ************************************ beginning of POLYFITINF
function [A,REF,HMAX,H,R,EQUAL] = polyfitinf(M,N,K,X,Y,EPSH,MAXIT,REF0)

    % Preassign output variables A,REF,HMAX,H,R,EQUAL in case of error return
    A = []; REF = []; HMAX = []; H = []; R = 0; EQUAL = -2;
    %%%% end preassignment

    %       Setting M with respect to K
    MOLD = M;

    switch K
        case 1
            K0 = 0;
            K1 = 1;
            Q1 = 1;
            Q2 = 2;
            M =  (M-Q1)/2;
        case 2
            K0 = 0;
            K1 = 0;
            Q1 = 0;
            Q2 = 2;
            
            % If the user has input odd M, but wants an even polynomial,
            % subtract 1 from M to prevent errors later. The outputs should be
            % mathematically equivalent.
            if mod(M,2) == 1
                M = M-1;
            end
            
            M =  (M-Q1)/2;
        otherwise
            if (K ~= 0)
                warning('polyfitinf:MixedParity','Using mixed parity polynomial...');
            end
            K0 = 1;
            K1 = 0;
            Q1 = 0;
            Q2 = 1;
    end

    P = M + 2;

    %       Check input data consistency

    if ( (length(X) ~= N) || (length(Y) ~= N) )
        error('Input Error: check data lengths');
    end

    if (P > N)
        error('Input Error: insufficient data points');
    end

    if (M < 0)
        error('Input Error: insufficient degree');
    end

    if ( (K == 2) && (X(1) < 0) ) || ( (K == 1) && (X(1) <= 0) )
        error('Input Error: X(1) inconsistent with parity');
    end

    if any(diff(X)<0)
        error('Input Error: Abscissae out of order');
    end

    ITEMP = MOLD + 1;

    A = zeros(1,ITEMP);


    ITEMP = P + 2;
    Z = zeros(1,ITEMP);
    Z(1) = 0;
    Z(ITEMP) = N + 1;

    EPSH = abs(EPSH);

    %       Read initial reference set into Z, if available.

    if (nargin == 8)
        J = 0;
        
        Z(2:(P+1))= REF0(1:P);
        
        %	Check if REF is monotonically increasing
        if ( any(diff(REF0) < 0) || any(REF0 > J) )
            error('Input Error : Bad initial reference set');
        end
        
    else
        
        %          Loads Z with the points closest to the Chebychev abscissas
        
        X1 = X(1);
        XE = X(N);
        
        %          Setting parity-dependent parameters
        
        if (K0 == 1)
            XA = XE + X1;
            XE = XE - X1;
            Q = pi/(M + 1.0);
        else
            XA = 0.;
            XE = XE + XE;
            ITEMP = 2*(M+1) + Q1;
            Q = pi/(ITEMP);
        end
        
        %          Calculate the J-th Chebyshev abcissa and load Z(J+1)
        %          with the appropriate index from the data abcissas
        
        for JJ = 1:P
            J = P + 1 - JJ;
            X1 = XA + XE*( cos(Q*(P-J)) );
            ITEMP = J + 2;
            R = Z(ITEMP);
            HIGH = R - 1;
            FLAG = 1;
            if (HIGH >= 2)
                II = 2;
                while ( (II <= HIGH) && (FLAG == 1) )
                    I = HIGH + 2 - II;
                    ITEMP = I - 1;
                    
                    %			If the Chebyschev abscissa is bracketed by
                    % 		     two input abcissas, get out of the while loop
                    
                    if (X(I)+X(ITEMP) <= X1)
                        FLAG = 0;
                    end
                    II = II + 1;
                end
            end
            
            if (FLAG == 1)
                I = 1;
            end
            ITEMP = J + 1;
            
            if (I < R)
                Z(ITEMP) = I;
            else
                Z(ITEMP) = R - 1;
            end
        end
        
        %          If the lower Chebyshev abcissas are less than X(1),
        %          load the lower elements of Z with the lowest points

        IND = find(Z(2:end) >= (1:(length(Z)-1)));
        
        try TEMP = IND(1);	% If IND is empty, do nothing.
        catch exception % The catch will be that IND is an empty array.
            
            if strcmpi(exception.identifier,'MATLAB:badsubscript')
                % This will be the exception. Do nothing.
            end
        end
        
        if TEMP~=1
            Z(2:TEMP) = (1:(TEMP-1))';
        end
        
        
    end

    %       M1 entry. Initialize variables to prepare for exchange iteration

    ITEMP = M + 1;


    %       Zero the AA array


    AA = zeros(1,ITEMP);


    %       Load H with the ordinates and XX(I) with the abscissas if the
    %       polynomial is mixed . If it is even or odd , load XX with the
    %       squares of the abscissas.

    H(1:N) = Y(1:N);
    if (K0 <=0)
        XX(1:N) = X(1:N).^2;
    else
        XX(1:N) = X(1:N);
    end

    B1 = 0;
    B2 = 0;
    B3 = 0;
    R = -1;
    T = 0.;

    %       Iteration entry. R is the iteration index

    C = zeros(1,P);
    D = zeros(1,P);
    DAA = zeros(1,M+1);

    FLAG = 1;
    while ( (R < MAXIT) && (FLAG == 1) )
        
        R = R + 1;   % LABEL 350
        %S = 1.;
        
        %          Computation of div. differences schemes
        
        if (K1 > 0)
            
            %               If the polynomial is mixed or even:
            %for I = 1:P
            %    S = -S;
            %    ITEMP = I + 1;
            %    J = Z(ITEMP);
            %    Q = X(J);
            %    C(I) = (H(J) + S*T)/Q;
            %    D(I) = S/Q;
            %end

	    I = (1:P);
       	S = (-1).^I;
	    ITEMP = I+1;
	    J = Z(ITEMP);
	    C(I) = (H(J) + S*T)./X(J);
	    D(I) = S./Q;
	    clear I ITEMP S J
        
        else
            
            %               If the polynomial is odd:
            %for I = 1:P
            %    S = -S;
            %    ITEMP = I + 1;
            %    ITEMP = Z(ITEMP);
            %    C(I) = H(ITEMP) + S*T;
            %    D(I) = S;
            %end

	    I = (1:P);
       	S = (-1).^I;
	    ITEMP = I+1;
	    C(I) = H( Z(ITEMP) ) + S.*T;
	    D(I) = S;
        clear I ITEMP S
            
        end
        
        for I = 2:P
            for JJ = I:P
                J = P + I - JJ;
                ITEMP = J + 1;
                ITEMP = Z(ITEMP);
                QD = XX(ITEMP);
                ITEMP = 2 + J - I;
                ITEMP = Z(ITEMP);
                QD = QD - XX(ITEMP);
                ITEMP = J - 1;
                C(J) = (C(J)-C(ITEMP))/QD;
                D(J) = (D(J)-D(ITEMP))/QD;
            end
        end
        
        DT = -C(P)/D(P);
        T = T + DT;
        
        %           Computation of polynomial coefficients
        
        HIGH = M + 1;
        for II = 1:HIGH
            I = HIGH - II;
            ITEMP = I + 1;
            DAA(ITEMP) = C(ITEMP) + DT*D(ITEMP);
            ITEMP = I + 2;
            ITEMP = Z(ITEMP);
            QD = XX(ITEMP);
            LOW = I + 1;
            if (M >= LOW)
                DAA(LOW:M) = DAA(LOW:M) - QD*DAA(((LOW:M)+1));
            end
        end
        
        AA(1:HIGH) = AA(1:HIGH) + DAA(1:HIGH);
        
        %	   Evaluation of the polynomial to get the approximation errors
        
        MAXX = 0.;
        H = zeros(1,N);
        for I = 1:N
            SD = AA(HIGH);
            QD = XX(I);
            if (M > 0)
                for J = M:-1:1
                    SD = SD*QD + AA(J);
                end
            end
            if (K1 > 0)
                %		   If the polynomial is odd, multiply SD by X(I)
                SD = SD*X(I);
            end
            
            QD = Y(I) - SD;
            H(I) = Y(I) - SD;
            
            if  (abs(QD) > MAXX)
                %		   Load MAXX with the largest magnitude
                %		   of the approximation array
                MAXX = abs(QD);
            end
        end
        
        %	   Test for alternating signs
        
        ITEMP = Z(2);
        
        if (H(ITEMP) == 0.)
            
            %               This represents a case where the polynomial
            %               exactly predicts a data point
          
            warning('polyfitinf:Collocation','Collocation has occured.');
            if (B3 > 0)
                B3 = -1;
                FLAG = 0;
            else
                B3 = 1;
                if (EPSH < MAXX)
                    warning('polyfitinf:AnotherTry','1 more attempt with middle points');
                    LOW = (N+1)/2 - (P+1)/2 + 1;
                    HIGH = LOW + P;
                    Z(LOW:HIGH) = ( (LOW:HIGH) -1);
                    
                else
                    disp('Normal Exit.');
                    FLAG = 0;
                end
            end
        else
            
            if (H(ITEMP) > 0.)
                J = -1;
            else
                J =  1;
            end
            
            I = 2;
            FLAG2 = 1;
            while ( (I <= P) && (FLAG2 == 1) )
                ITEMP = I + 1;
                ITEMP = Z(ITEMP);
                if (H(ITEMP) == 0.)
                    J = 0;
                    warning('polyfitinf:Collocation','Collocation has occured.');
                    if (B3 > 0)
                        B3 = -1;
                        FLAG = 0;
                    else
                        B3 = 1;
                        if (EPSH < MAXX)
                            warning('polyfitinf:AnotherTry','1 more attempt with middle points');
                            LOW = (N+1)/2 - (P+1)/2 + 1;
                            HIGH = LOW + P;
                            Z(LOW:HIGH) = ( (LOW:HIGH) -1);
                        else
                            disp('Normal Exit.');
                            FLAG = 0;
                        end
                    end
                    FLAG2 = 0;
                else
                    if (H(ITEMP) < 0)
                        JJ = -1;
                    else
                        JJ = 1;
                    end
                    if (J~=JJ)
                        
                        %			   Error entry: bad accuracy for calculation
                        
                        B1 = 1;
                        FLAG2 = 0;
                        FLAG = 0;
                    else
                        J = -J;
                    end
                end
                
                I = I + 1;
                
            end	% end of while
            
            %                  Search for another reference
            
            if (FLAG2*FLAG == 1)
                
                [H,Z,EQUAL] = exch(N, P, EPSH, H, Z);
                if (EQUAL > 0)
                    FLAG = 0;
                else
                    if (R >= MAXIT)
                        B2 = 1;
                        FLAG = 0;
                    end
                end
                
            end
            
        end	  % end of if over H(ITEMP)
        
    end;      % end of iteration loop

    %       M2 entry; load output variables and return

    HIGH = M + 1;

    %       Load the coefficients into A array

    A(Q1 + Q2*(((1:HIGH)-1)) + 1) = AA(1:HIGH);

    %       Load REF with the final reference points

    REF(1:P) = Z((1:P) + 1);

    HMAX = MAXX;

    if (B3 < 0)
        EQUAL = -2;
        warning('polyfitinf:Collocation','polyfitinf terminates');
    end
    if (B1 > 0)
        EQUAL = -2;
        warning('polyfitinf:NoAlternatingSigns','Alternating signs not observed');
    end
    if (B2 > 0)
        EQUAL = 0;
        warning('polyfitinf:MaxIterationsReached','MAXIT was reached, current ref. set saved in REF.');
    end

    % Reverse the order of A to make it compatible with MATLAB'S polyval() function.
    A = A(end:-1:1);


endfunction
%	****************************************** end of POLYFITINF

function [H,Z,EQUAL] = exch(N, P, EPSH, H, Z)
% function [H,Z,EQUAL] = exch(N, P, EPSH, H, Z)
%
%   EXCH: exchange algorithm
%
%   INPUT VARIABLES:
%   N       : number of data points
%   P	: number of reference points
%   EPSH    : tolerance for leveling.
%   Z	: old reference indices
%
%   OUTPUT VARIABLES:
%   H       : pointwise approximation errors
%   Z	: new reference indices
%   EQUAL   :	EQUAL=1 :  normal exchange
%                   EQUAL=0 :  old and new references are equal
%
%   CREDITS: This routine was developed and modified as 
%   computer assignments in Approximation Theory courses by 
%   Prof. Andrew Knyazev, University of Colorado Denver, USA.
%
%   Team Fall 98 (Revision 1.0):
%           Chanchai Aniwathananon
%           Crhistopher Mehl
%           David A. Duran
%           Saulo P. Oliveira
%
%   Team Spring 11 (Revision 1.1): Manuchehr Aminian
%
%   The algorithm and the comments are based on a FORTRAN code written
%   by Joseph C. Simpson. The code is available on Netlib repository:
%   http://www.netlib.org/toms/501
%   See also: Communications of the ACM, V14, pp.355-356(1971)
%
%   Revision 1.0 from 1998 is a direct human translation of 
%   the FORTRAN code http://www.netlib.org/toms/501
%   Revision 1.1 is a clean-up and technical update.  
%   Tested on MATLAB Version 7.11.0.584 (R2010b) and 
%   GNU Octave Version 3.2.4

%   License: BSD
%   Copyright 1998-2011 Andrew V. Knyazev
%   $Revision: 1.1 $  $Date: 2011/05/17 $

%       ************************************ beginning of exch

    EQUAL = 0;
    L = 0;
    ITEMP =  Z(2);

    %	SIG is arbitrarily chosen equal to the sign of the input
    %	point. This will be adjusted later if necessary.

    if (H(ITEMP) <= 0)
        SIG = 1.;
    else
        SIG = -1.;
    end

    %	The next loop prescans Z to insure it is a proper choice, i.e
    %	resets Z if necessary so that maximum error points are chosen,
    %	given the sign convention mentioned above. In order to work
    %	properly, this section requires Z(1) = 0 and Z(P+2) = N + 1 .

    for I = 1:P
        MAXX = 0.;
        SIG = -SIG;
        ITEMP = I + 2;
        ZE  =  Z(ITEMP) - 1;
        LOW =  Z(I) + 1;
        
        %	   Scan the open point interval containing only the 1th initial
        %	   reference point. In the interval pick the point with largest
        %	   magnitude and correct sign. Most of the sorting occurs in
        %	   this section. SIG contains the sign assumed for H(I).
        
        for J = LOW:ZE
            if (SIG*(H(J)-MAXX) > 0)
                MAXX = H(J);
                INDEX = J;
            end
        end
        ITEMP = I + 1;
        ITEMP =  Z(ITEMP);
        MAXL = abs(MAXX);
        
        %	   If the MAX error is significantly greater than the
        %	   input point, switch to this point.
        
        if (abs( MAXX - H(ITEMP) )/MAXL > EPSH)
            ITEMP = I + 1;
            Z(ITEMP) = INDEX;
            L = 1;
        end
    end
    %
    MAXL = 0.;
    MAXR = 0.;
    ITEMP = P + 1;
    LOW =  Z(ITEMP) + 1;
    %
    if (LOW <= N)
        
        %	   Find the error with largest abs value and proper sign
        %	   from among the points above the last reference point.
        %	   This section is necessary because the set of points
        %	   chosen may begin with the wrong sign alternation.
        
        for J = LOW:N
            if (SIG*(MAXR-H(J)) > 0)
                MAXR = H(J);
                INDR = J;
            end
        end
    end

    %	Find the error with largest abs value and proper sign
    %	from among the points below  the 1st reference  point.
    %	This section is necessary by the same reason as above.

    ITEMP =  Z(2);
    HZ1 = H(ITEMP);
    HIGH = ITEMP -1;
    if (HIGH > 0)
        if (HZ1 < 0)
            SIG = -1.;
        elseif (HZ1 == 0)
            SIG = 0.;
        else
            SIG = 1.;
        end
        
        for J = 1:HIGH
            if (SIG*(MAXL-H(J)) > 0)
                MAXL = H(J);
                INDL = J;
            end
        end
        
    end

    %	MAXL and MAXR contain the magnitude of the significant
    %	errors outside the reference point set. If either is
    %	zero, the reference point set extends to the end point
    %	on that side of the interval.

    MAXL = abs(MAXL);
    MAXR = abs(MAXR);
    HZ1 = abs(HZ1);
    ITEMP = P + 1;
    ITEMP =  Z(ITEMP);
    HZP = abs(H(ITEMP));

    %	L = 0 implies that the previous prescan did not change
    %	any points. If L = 0 and MAXL, MAXR are not significant
    %	if compared with upper and lower reference point errors,
    %	respectively, use the EQUAL exit.

    FLAG1 = 1;
    if (L == 0)
        if ( (MAXL == 0) || (EPSH >= (MAXL-HZP)/MAXL) )
            if ( (MAXR == 0) || (EPSH >= (MAXR-HZ1)/MAXR) )
                FLAG1 = 0;
                EQUAL = 1;
            end
        end
    end

    if ( (MAXL == 0) && (MAXR == 0) )
        FLAG1 = 0;
    end

    if ( (MAXL > MAXR) && (MAXL <= HZP) && (MAXR < HZ1) )
        FLAG1 = 0;
    end

    if ( (MAXL <= MAXR) && (MAXR <= HZ1) && (MAXL < HZP) )
        FLAG1 = 0;
    end

    %	If a point outside the present reference set must be
    %	included, (i.e. the sign of the 1st point previously
    %	assumed is wrong) shift to the side of largest
    %	relative error first.

    if (FLAG1 == 1)
        
        FLAG2 = 1;
        
        if ( (MAXL > MAXR) && (MAXL > HZP) )
            FLAG2 = 0;
        end
        
        if ( (MAXL <= MAXR) && (MAXR <= HZ1) )
            FLAG2 = 0;
        end
        
        if (FLAG2 == 1)
            
            %		SHR entry. This section inserts a point from
            %		above the prescan point set
            
            INDEX = Z(2);
            
            %		shift point set down, dropping the lowest point
            
            Z(2:P) = Z((2:P)+1);
            
            ITEMP = P + 1;
            
            %		add the next high point
            Z(ITEMP)=INDR;
            
            %		if MAXL > 0 replace reference points from the left,
            %		stopping the 1st time the candidate for replacement
            %		is greater than in magnitude than the prospective
            %		replacee. Alternation of signs is preserved because
            %		non-replacement immediately terminates the process.
            
            if (MAXL > 0)
                I = 2;
                FLAG3 = 1;
                while ( (I <= P) && (FLAG3 == 1) )
                    ITEMP = Z(I);
                    if ( abs(H(INDL)) >= abs(H(ITEMP)) )
                        J = Z(I);
                        Z(I) = INDL;
                        INDL = INDEX;
                        INDEX = J;
                    else
                        FLAG3 = 0;
                    end
                    I = I + 1;
                end
            end
            
        else
            
            %	   SHL entry. This section inserts a point from below the
            %	   prescan point set.
            
            ITEMP = P + 1 ;
            INDEX = Z(ITEMP);
            
            Z((2:P)+1) = Z(2:P);
            
            %		store lowest point in Z(2)
            Z(2) = INDL;
            
            %		if MAXR > 0 replace reference points from the right,
            %		stopping the 1st time the candidate for replacement
            %		is greater than in magnitude than the prospective
            %		replacee.
            
            if (MAXR > 0)
                II = 2;
                FLAG3 = 1;
                while ( (II <= P) && (FLAG3 == 1) )
                    I = P + 2 - II;
                    ITEMP = I + 1;
                    HIGH = Z(ITEMP);
                    if ( abs(H(INDR)) >= abs(H(HIGH)) )
                        J = Z(ITEMP);
                        Z(ITEMP) = INDR;
                        INDR = INDEX;
                        INDEX = J;
                    else
                        FLAG3 = 0;
                    end
                    II = II + 1;
                end
            end
            
        end
        
    end

endfunction
%	****************************************** end of exch
