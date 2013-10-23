%% -*- texinfo -*-
%% Frequency-weighted controller reduction.

% ===============================================================================
% Frequency Weighted Controller Reduction       Lukas Reichlin      December 2011
% ===============================================================================
% Reference: Madievski, A.G. and Anderson, B.D.O.
%            Sampled-Data Controller Reduction Procedure
%            IEEE Transactions of Automatic Control
%            Vol. 40, No. 11, November 1995
% ===============================================================================

% Tabula Rasa
clear all, close all, clc

% Plant
Ap1 = [  0.0         1.0
         0.0         0.0     ];

Ap2 = [ -0.015       0.765
        -0.765      -0.015   ];

Ap3 = [ -0.028       1.410
        -1.410      -0.028   ];

Ap4 = [ -0.04        1.85
        -1.85       -0.04    ];

Ap = blkdiag (Ap1, Ap2, Ap3, Ap4);

Bp = [   0.026
        -0.251
         0.033
        -0.886
        -4.017
         0.145
         3.604
         0.280   ];

Cp = [  -0.996      -0.105       0.261       0.009      -0.001      -0.043       0.002      -0.026   ];

Dp = [   0.0     ];

P = ss (Ap, Bp, Cp, Dp);

% Controller
Ac = [  -0.4077      0.9741      0.1073      0.0131      0.0023     -0.0186     -0.0003     -0.0098
        -0.0977     -0.1750      0.0215     -0.0896     -0.0260      0.0057      0.0109     -0.0105
         0.0011      0.0218     -0.0148      0.7769      0.0034     -0.0013     -0.0014      0.0011
        -0.0361     -0.5853     -0.7701     -0.3341     -0.0915      0.0334      0.0378     -0.0290
        -0.1716     -2.6546     -0.0210     -1.4467     -0.4428      1.5611      0.1715     -0.1318
        -0.0020      0.0950      0.0029      0.0523     -1.3950     -0.0338     -0.0062      0.0045
         0.1607      2.3824      0.0170      1.2979      0.3721     -0.1353     -0.1938      1.9685
        -0.0006      0.1837      0.0048      0.1010      0.0289     -0.0111     -1.8619     -0.0311  ];

Bc = [  -0.4105
        -0.0868
        -0.0004
         0.0036
         0.0081
        -0.0085
        -0.0004
        -0.0132  ];

Cc = [  -0.0447     -0.6611     -0.0047     -0.3601     -0.1033      0.0375      0.0427     -0.0329  ];

Dc = [   0.0     ];

K = ss (Ac, Bc, Cc, Dc);

% Controller Reduction
Kr4 = spaconred (P, K, 4, 'feedback', '-')
Kr2 = spaconred (P, K, 2, 'feedback', '-')

% Open Loop
L = P * K;
Lr4 = P * Kr4;
Lr2 = P * Kr2;

% Closed Loop
T = feedback (L);
Tr4 = feedback (Lr4);
Tr2 = feedback (Lr2);

% Frequency Range
w = {1e-2, 1e1};

% Bode Plot of Controller
figure (1)
bode (K, Kr4, Kr2, w)
legend ('K (8 states)', 'Kr (4 states)', 'Kr (2 states)', 'Location', 'SouthWest')
    
% Step Response of Closed Loop
figure (2)
step (T, Tr4, Tr2, 100)
legend ('K (8 states)', 'Kr (4 states)', 'Kr (2 states)', 'Location', 'SouthEast')
