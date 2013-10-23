%% Copyright (c) 2011 Juan Pablo Carbajal <carbajal@ifi.uzh.ch>
%%
%% This program is free software; you can redistribute it and/or modify it under
%% the terms of the GNU General Public License as published by the Free Software
%% Foundation; either version 3 of the License, or (at your option) any later
%% version.
%%
%% This program is distributed in the hope that it will be useful, but WITHOUT
%% ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
%% FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
%% details.
%%
%% You should have received a copy of the GNU General Public License along with
%% this program; if not, see <http://www.gnu.org/licenses/>.

%% -*- texinfo -*-
%% @deftypefn {Function File} @var{y} = sigmoid_train(@var{t}, @var{ranges}, @var{rc})
%%
%% Evaluates a train of sigmoid functions at @var{t}.
%%
%% The number and duration of each sigmoid is determined from @var{ranges}. Each
%% row of @var{ranges} represents a real interval, e.g. if sigmod @code{i} starts
%% at @code{t=0.1} and ends at @code{t=0.5}, then @code{@var{ranges}(i,:) = [0.1
%% 0.5]}.
%% The input @var{rc} is a array that defines the rising and falling time
%% constants of each sigmoids. Its size must equal the size of @var{ranges}.
%%
%% Run @code{demo sigmoid_train} to some examples of the use of this function.
%%
%% @end deftypefn

function envelope = sigmoid_train (t, range, timeconstant)

  % number of sigmoids
  nRanges = size (range, 1);

  %% Parse time constants
  if isscalar (timeconstant)
    %% All bumps have the same time constant and are symmetric
    timeconstant = timeconstant * ones (nRanges,2);

  elseif any( size(timeconstant) != [1 1])

    %% All bumps have different time constant but are symmetric
    if length(timeconstant) ~= nRanges
      error('signalError','Length of time constant must equal number of ranges.')
    end
    if isrow (timeconstant)
      timeconstant = timeconstant';
    end
    timeconstant = repmat (timeconstant,1,2);

  end

  %% Make sure t is horizontal
  flag_transposed = false;
  if iscolumn (t)
   t               = t.';
   flag_transposed = true;
  end
  [ncol nrow]     = size (t);

  % Compute arguments of each sigmoid
  T    = repmat (t, nRanges, 1);
  RC1  = repmat (timeconstant(:,1), 1, nrow);
  RC2  = repmat (timeconstant(:,2), 1, nrow);
  a_up = (repmat (range(:,1), 1 ,nrow) - T)./RC1;
  a_dw = (repmat (range(:,2), 1 ,nrow) - T)./RC2;

  % Evaluate the sigmoids and mix them
  Y        = 1 ./ ( 1 + exp (a_up) ) .* (1 - 1 ./ ( 1 + exp (a_dw) ) );
  envelope = max(Y,[],1);

  if flag_transposed
    envelope = envelope.';
  end

end

%!demo
%! % Vectorized
%! t = linspace (0, 2, 500);
%! range = [0.1 0.4; 0.6 0.8; 1 2];
%! rc = [1e-2 1e-2; 1e-3 1e-3; 2e-2 2e-2];
%! y = sigmoid_train (t, range, rc);
%!
%! close all
%! for i=1:3
%!     patch ([range(i,[2 2]) range(i,[1 1])], [0 1 1 0],...
%!               'facecolor', [1 0.8 0.8],'edgecolor','none');
%! end
%! hold on; plot (t, y, 'b;Sigmoid train;','linewidth',2); hold off
%! xlabel('time'); ylabel('S(t)')
%! title ('Vectorized use of sigmoid train')
%! axis tight
%!
%! %-------------------------------------------------------------------------
%! % The colored regions show the limits defined in range.

%!demo
%! % On demand
%! t = linspace(0,2,200).';
%! ran = [0.5 1; 1.5 1.7];
%! rc = 3e-2;
%! dxdt = @(x_,t_) [ x_(2); sigmoid_train(t_, ran, rc) ];
%! y = lsode(dxdt,[0 0],t);
%!
%! close all
%! for i=1:2
%!     patch ([ran(i,[2 2]) ran(i,[1 1])], [0 1 1 0],...
%!               'facecolor', [1 0.8 0.8],'edgecolor','none');
%! end
%! hold on; plot (t, y(:,2), 'b;Speed;','linewidth',2); hold off
%! xlabel('time'); ylabel('V(t)')
%! title ('On demand use of sigmoid train')
%! axis tight
%!
%! %-------------------------------------------------------------------------
%! % The colored regions show periods when the force is active.
