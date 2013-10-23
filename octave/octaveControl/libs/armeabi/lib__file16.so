## -*- texinfo -*-
## @deftypefn{Function File} {@var{sys} =} BMWengine ()
## @deftypefnx{Function File} {@var{sys} =} BMWengine (@var{"scaled"})
## @deftypefnx{Function File} {@var{sys} =} BMWengine (@var{"unscaled"})
## Model of the BMW 4-cylinder engine at ETH Zurich's control laboratory.
## @example
## @group
## OPERATING POINT
## Drosselklappenstellung     alpha_DK = 10.3 Grad
## Saugrohrdruck              p_s = 0.48 bar
## Motordrehzahl              n = 860 U/min
## Lambda-Messwert            lambda = 1.000
## Relativer Wandfilminhalt   nu = 1
## @end group
## @end example
## @example
## @group
## INPUTS
## U_1 Sollsignal Drosselklappenstellung   [Grad]
## U_2 Relative Einspritzmenge             [-]
## U_3 Zuendzeitpunkt                      [Grad KW]
## M_L Lastdrehmoment                      [Nm]
## @end group
## @end example
## @example
## @group
## STATES
## X_1 Drosselklappenstellung     [Grad]
## X_2 Saugrohrdruck              [bar]
## X_3 Motordrehzahl              [U/min]
## X_4 Messwert Lamba-Sonde       [-]
## X_5 Relativer Wandfilminhalt   [-]
## @end group
## @end example
## @example
## @group
## OUTPUTS
## Y_1 Motordrehzahl              [U/min]    
## Y_2 Messwert Lambda-Sonde      [-]
## @end group
## @end example
## @example
## @group
## SCALING
## U_1N, X_1N   1 Grad
## U_2N, X_4N, X_5N, Y_2N   0.05
## U_3N   1.6 Grad KW
## X_2N   0.05 bar
## X_3N, Y_1N   200 U/min
## @end group
## @end example
## @end deftypefn

## Author: Lukas Reichlin <lukas.reichlin@gmail.com>
## Created: January 2010
## Version: 0.1.1

## TODO: translate German terminology 

function sys = BMWengine (flg = "scaled")

  if (nargin > 1)
    print_usage ();
  endif

  switch (tolower (flg))
    case "unscaled"  ## Linearisiertes Modell, nicht skaliert

      Apu = [ -40.0000     0          0          0          0
                0.1683    -2.9471    -0.0016     0          0
               26.6088   920.3932    -0.1756     0        259.1700
               -0.5852    14.1941     0.0061    -5.7000    -5.7000
                0.6600    -1.1732    -0.0052     0        -15.0000 ];

      Bpu = [  40.0000     0          0
                0          0          0
                0        181.4190     1.5646
                0         -3.9900     0
                0          4.5000     0      ];

      Bdpu = [  0
                0
              -15.9000
                0
                0      ];

      Cpu = [   0          0          1          0          0
                0          0          0          1          0 ];

      sys = ss (Apu, [Bpu, Bdpu], Cpu);

    case "scaled"  ## Skaliertes Zustandsraummodell

      Ap = [  -40.0000     0          0          0          0
                3.3659    -2.9471    -6.5157     0          0
                0.1330     0.2301    -0.1756     0          0.0648
              -11.7043    14.1941    24.3930    -5.7000    -5.7000
               13.2003    -1.1732   -20.9844     0        -15.0000 ];

      Bp = [   40.0000     0          0
                0          0          0
                0          0.0454     0.0125
                0         -3.9900     0
                0          4.5000     0      ];

      Bdp = [   0
                0
               -1.5900
                0
                0      ];

      Cp = [    0          0          1          0          0
                0          0          0          1          0 ];

      sys = ss (Ap, [Bp, Bdp], Cp, [], "scaled", true);

    otherwise
      print_usage ();

  endswitch

endfunction
