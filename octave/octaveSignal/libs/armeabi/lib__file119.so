## Author: Paul Kienzle <paulkienzle@Avocado.local> (2006)
## This program is granted to the public domain.

## -*- texinfo -*-
## @deftypefn {Function File} {@var{s} =} square(@var{t}, @var{duty})
## @deftypefnx {Function File} {@var{s} =} square(@var{t})
## Generate a square wave of period 2 pi with limits +1/-1.
##
## If @var{duty} is specified, the square wave is +1 for
## that portion of the time.
##
## @verbatim
##                     on time
##    duty cycle = ------------------
##                 on time + off time
## @end verbatim
##
## @seealso{cos, sawtooth, sin, tripuls}
## @end deftypefn

function v = square (t, duty = 0.5)

  if (nargin < 1 || nargin > 2)
    print_usage;
  endif

  t /= 2*pi;
  v = ones(size(t));
  v(t-floor(t) >= duty) = -1;

endfunction
