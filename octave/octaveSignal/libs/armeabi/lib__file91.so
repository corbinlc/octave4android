## Copyright (C) 2011 Alexander Klein <alexander.klein@math.uni-giessen.de>
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
## @deftypefn{Function File} { [ @var{b}, @var{a} ] } = pei_tseng_notch ( @var{frequencies}, @var{bandwidths}
## Return coefficients for an IIR notch-filter with one or more filter frequencies and according (very narrow) bandwidths
## to be used with @code{filter} or @code{filtfilt}.
## The filter construction is based on an allpass which performs a reversal of phase at the filter frequencies.
## Thus, the mean of the phase-distorted and the original signal has the respective frequencies removed.
## See the demo for an illustration.
##
## Original source:
## Pei, Soo-Chang, and Chien-Cheng Tseng
## "IIR Multiple Notch Filter Design Based on Allpass Filter"
## 1996 IEEE Tencon
## doi: 10.1109/TENCON.1996.608814)
## @end deftypefn

## TODO: Implement Laplace-space frequencies and bandwidths, and perhaps better range checking for bandwidths?

function [ b, a ] = pei_tseng_notch ( frequencies, bandwidths )

  err = nargchk ( 2, 2, nargin, "string" );

  if ( err )
    error ( err );
  elseif ( !isvector ( frequencies ) || !isvector ( bandwidths ) )
    error ( "All arguments must be vectors!" )
  elseif ( length ( frequencies ) != length ( bandwidths ) )
    error ( "All arguments must be of equal length!" )
  elseif ( !all ( frequencies > 0 && frequencies < 1 ) )
    error ( "All frequencies must be in (0, 1)!" )
  elseif ( !all ( bandwidths > 0 && bandwidths < 1 ) )
    error ( "All bandwidths must be in (0, 1)!" )
  endif

  ##Ensure row vectors
  frequencies = frequencies (:)';
  bandwidths  = bandwidths (:)';


  ##Normalise appropriately
  frequencies *= pi;
  bandwidths  *= pi;
  M2           = 2 * length ( frequencies );


  ##Splice centre and offset frequencies ( Equation 11 )
  omega = vec ( [ frequencies - bandwidths / 2; frequencies ] );

  ##Splice centre and offset phases ( Equations 12 )
  factors = ( 1 : 2 : M2 );
  phi     = vec ( [ -pi * factors + pi / 2; -pi * factors ] );

  ##Create linear equation
  t_beta = tan ( ( phi + M2 * omega ) / 2 );

  Q = zeros ( M2 );

  for k = 1 : M2
    Q ( : ,k ) = sin ( k .* omega ) - t_beta .* cos ( k .* omega );
  endfor

  ##Compute coefficients of system function ( Equations 19, 20 ) ...
  h_a   = ( Q \ t_beta )' ;
  denom = [ 1, h_a ];
  num   = [ fliplr( h_a ), 1 ];

  ##... and transform them to coefficients for difference equations
  a = denom;
  b = ( num + denom ) / 2;

endfunction

%!test
%! ##2Hz bandwidth
%! sf = 800; sf2 = sf/2;
%! data=[sinetone(49,sf,10,1),sinetone(50,sf,10,1),sinetone(51,sf,10,1)];
%! [b, a] = pei_tseng_notch ( 50 / sf2, 2 / sf2 );
%! filtered = filter ( b, a, data );
%! damp_db = 20 * log10 ( max ( filtered ( end - 1000 : end, : ) ) );
%! assert ( damp_db, [ -3 -251.9 -3 ], -0.1 )

%!test
%! ##1Hz bandwidth
%! sf = 800; sf2 = sf/2;
%! data=[sinetone(49.5,sf,10,1),sinetone(50,sf,10,1),sinetone(50.5,sf,10,1)];
%! [b, a] = pei_tseng_notch ( 50 / sf2, 1 / sf2 );
%! filtered = filter ( b, a, data );
%! damp_db = 20 * log10 ( max ( filtered ( end - 1000 : end, : ) ) );
%! assert ( damp_db, [ -3 -240.4 -3 ], -0.1 )

%!demo
%! sf = 800; sf2 = sf/2;
%! data=[[1;zeros(sf-1,1)],sinetone(49,sf,1,1),sinetone(50,sf,1,1),sinetone(51,sf,1,1)];
%! [b,a]=pei_tseng_notch ( 50 / sf2, 2/sf2 );
%! filtered = filter(b,a,data);
%!
%! clf
%! subplot ( columns ( filtered ), 1, 1) 
%! plot(filtered(:,1),";Impulse response;")
%! subplot ( columns ( filtered ), 1, 2 ) 
%! plot(filtered(:,2),";49Hz response;")
%! subplot ( columns ( filtered ), 1, 3 ) 
%! plot(filtered(:,3),";50Hz response;")
%! subplot ( columns ( filtered ), 1, 4 ) 
%! plot(filtered(:,4),";51Hz response;")
