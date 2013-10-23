## Copyright (C) 2004 Andrew Collier <abcollier@users.sourceforge.net>
##
## This program is free software; it is distributed in the hope that it
## will be useful, but WITHOUT ANY WARRANTY; without even the implied
## warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See
## the GNU General Public License for more details.
##
## You should have received a copy of the GNU General Public License
## along with this file; see the file COPYING.  If not, see
## <http://www.gnu.org/licenses/>.

## -*- texinfo -*-
## @deftypefn {Function File} {} @var{dist} = distance(@var{pt1}, @var{pt2})
##
## Calculates the distance (in degrees) between @var{pt1} and @var{pt2}.
##
## @var{pt1} and @var{pt2} are two-column matrices of the form [latitude longitude].
##
## @example
## >> distance([37,-76], [37,-9]) 
## ans = 52.309
## >> distance([37,-76], [67,-76])
## ans = 30.000
## @end example
##
## @seealso{azimuth,elevation}
## @end deftypefn

## Author: Andrew Collier <abcollier@users.sourceforge.net>

## Uses "cosine formula".

function dist = distance(pt1, pt2)
  pt1 = deg2rad(pt1);
  pt2 = deg2rad(pt2);

  c = pi / 2 - pt1(1);
  b = pi / 2 - pt2(1);
  A = pt2(2) - pt1(2);

  dist = rad2deg(acos(cos(b) * cos(c) + sin(b) * sin(c) * cos(A)));
endfunction

## http://www.mathworks.com/access/helpdesk/help/toolbox/map/distance.shtml
