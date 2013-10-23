## Copyright (c) 2010 Juan Pablo Carbajal <carbajal@ifi.uzh.ch>
##
## This program is free software; you can redistribute it and/or modify
## it under the terms of the GNU General Public License as published by
## the Free Software Foundation; either version 3 of the License, or
## (at your option) any later version.
##
## This program is distributed in the hope that it will be useful,
## but WITHOUT ANY WARRANTY; without even the implied warranty of
## MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
## GNU General Public License for more details.
##
## You should have received a copy of the GNU General Public License
## along with this program; if not, see <http://www.gnu.org/licenses/>.

%% contRange = clustersegment(xhi)
% The function calculates the initial and end index values of the sequences of
% 1's in the rows of xhi. The result is returned in a cell of size 1xNp, with Np
% the numer of rows in xhi. Each element of the cell has two rows; the first row
% is the inital index of a sequence of 1's and the second row is the end index
% of that sequence.

function contRange = clustersegment(xhi)

  % Find discontinuities
  bool_discon = diff(xhi,1,2);
  [Np Na] = size(xhi);
  contRange = cell(1,Np);

  for i = 1:Np
      idxUp = find(bool_discon(i,:)>0)+1;
      idxDwn = find(bool_discon(i,:)<0);
      tLen = length(idxUp) + length(idxDwn);

      if xhi(i,1)==1
      % first event was down
          contRange{i}(1) = 1;
          contRange{i}(2:2:tLen+1) = idxDwn;
          contRange{i}(3:2:tLen+1) = idxUp;
      else
      % first event was up
          contRange{i}(1:2:tLen) = idxUp;
          contRange{i}(2:2:tLen) = idxDwn;
      end

      if xhi(i,end)==1
      % last event was up
         contRange{i}(end+1) = Na;
      end

      tLen = length(contRange{i});
      if tLen ~=0
        contRange{i}=reshape(contRange{i},2,tLen/2);
      end

  end

endfunction

%!demo
%! xhi = [0 0 1 1 1 0 0 1 0 0 0 1 1];
%! ranges = clustersegment(xhi)
%!
%! % The first sequence of 1's in xhi is
%!  xhi(ranges{1}(1,:))

%!demo
%! xhi = rand(3,10)>0.4
%! ranges = clustersegment(xhi)
