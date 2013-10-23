## Copyright (C) 2001 Paulo Neis <p_neis@yahoo.com.br>
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

## usage: [Zz, Zp, Zg] = ncauer(Rp, Rs, n)
##
## Analog prototype for Cauer filter.
## [z, p, g]=ncauer(Rp, Rs, ws)
## Rp = Passband ripple
## Rs = Stopband ripple
## Ws = Desired order
##
## References: 
##
## - Serra, Celso Penteado, Teoria e Projeto de Filtros, Campinas: CARTGRAF, 
##   1983.
## - Lamar, Marcus Vinicius, Notas de aula da disciplina TE 456 - Circuitos 
##   Analogicos II, UFPR, 2001/2002.

function [zer, pol, T0]=ncauer(Rp, Rs, n)

  ## Cutoff frequency = 1:
  wp=1;

  ## Stop band edge ws:
  ws=__ellip_ws(n, Rp, Rs);

  k=wp/ws;
  k1=sqrt(1-k^2);
  q0=(1/2)*((1-sqrt(k1))/(1+sqrt(k1)));
  q= q0 + 2*q0^5 + 15*q0^9 + 150*q0^13; %(....)
  D=(10^(0.1*Rs)-1)/(10^(0.1*Rp)-1);

  ##Filter order maybe this, but not used now:
  ##n=ceil(log10(16*D)/log10(1/q))

  l=(1/(2*n))*log((10^(0.05*Rp)+1)/(10^(0.05*Rp)-1));
  sig01=0; sig02=0;
  for m=0 : 30
    sig01=sig01+(-1)^m * q^(m*(m+1)) * sinh((2*m+1)*l);
  end
  for m=1 : 30
    sig02=sig02+(-1)^m * q^(m^2) * cosh(2*m*l);
  end
  sig0=abs((2*q^(1/4)*sig01)/(1+2*sig02));

  w=sqrt((1+k*sig0^2)*(1+sig0^2/k));
  #
  if rem(n,2)
    r=(n-1)/2;
  else
    r=n/2;
  end
  #
  wi=zeros(1,r);
  for ii=1 : r
    if rem(n,2)
      mu=ii;
    else
      mu=ii-1/2;
    end
    soma1=0;
    for m=0 : 30
      soma1 = soma1 + 2*q^(1/4) * ((-1)^m * q^(m*(m+1)) * sin(((2*m+1)*pi*mu)/n));
    end
    soma2=0;
    for m=1 : 30
      soma2 = soma2 + 2*((-1)^m * q^(m^2) * cos((2*m*pi*mu)/n));
    end
    wi(ii)=(soma1/(1+soma2));
  end
  #
  Vi=sqrt((1-(k.*(wi.^2))).*(1-(wi.^2)/k));
  A0i=1./(wi.^2);
  sqrA0i=1./(wi);
  B0i=((sig0.*Vi).^2 + (w.*wi).^2)./((1+sig0^2.*wi.^2).^2);
  B1i=(2 * sig0.*Vi)./(1 + sig0^2 * wi.^2);

  ##Gain T0:
  if rem(n,2)
    T0=sig0*prod(B0i./A0i)*sqrt(ws);
  else
    T0=10^(-0.05*Rp)*prod(B0i./A0i);
  end

  ##zeros:
  zer=[i*sqrA0i, -i*sqrA0i];

  ##poles:
  pol=[(-2*sig0*Vi+2*i*wi.*w)./(2*(1+sig0^2*wi.^2)), (-2*sig0*Vi-2*i*wi.*w)./(2*(1+sig0^2*wi.^2))];

  ##If n odd, there is a real pole  -sig0:
  if rem(n,2)
          pol=[pol, -sig0];
  end

  ##
  pol=(sqrt(ws)).*pol;
  zer=(sqrt(ws)).*zer;

endfunction

## usage: ws = __ellip_ws(n, rp, rs)
## Calculate the stop band edge for the Cauer filter.
function ws=__ellip_ws(n, rp, rs)
  kl0 = ((10^(0.1*rp)-1)/(10^(0.1*rs)-1));
  k0  = (1-kl0);
  int = ellipke([kl0 ; k0]);
  ql0 = int(1);
  q0  = int(2);
  x   = n*ql0/q0;
  kl  = fminbnd(@(y) __ellip_ws_min(y,x) ,eps, 1-eps);
  ws  = sqrt(1/kl);
endfunction

## usage: err = __ellip_ws_min(kl, x)
function err=__ellip_ws_min(kl, x)
  int=ellipke([kl; 1-kl]);
  ql=int(1);
  q=int(2);
  err=abs((ql/q)-x);
endfunction
