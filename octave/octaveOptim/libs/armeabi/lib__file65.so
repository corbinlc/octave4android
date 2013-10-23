%% Copyright (C) 2010, 2011 Olaf Till <olaf.till@uni-jena.de>
%%
%% This program is free software; you can redistribute it and/or modify
%% it under the terms of the GNU General Public License as published by
%% the Free Software Foundation; either version 3 of the License, or
%% (at your option) any later version.
%%
%% This program is distributed in the hope that it will be useful,
%% but WITHOUT ANY WARRANTY; without even the implied warranty of
%% MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
%% GNU General Public License for more details.
%%
%% You should have received a copy of the GNU General Public License
%% along with this program; If not, see <http://www.gnu.org/licenses/>.

function __plot_cmds__ (x, y, f)

  persistent lgnd;
  persistent use_x;
  if (nargin == 0)
    %% reset function
    lgnd = [];
    return;
  end

  if (length (size (f)) > 2)
    return;
  end

  if (isempty (lgnd));
    n = size (y, 2);
    if (n == 1)
      lgnd = {'data', 'fit'};
    else
      id = num2str ((1:n).');
      lgnd1 = cat (2, repmat ('data ', n, 1), id);
      lgnd2 = cat (2, repmat ('fit ', n, 1), id);
      lgnd = cat (1, cellstr (lgnd1), cellstr (lgnd2));
    end
    use_x = size (x, 1) == size (y, 1);
  end

  x = x(:, 1);
  if (use_x)
    plot (x, y, 'marker', '+', 'linestyle', 'none', x, f);
  else
    plot (y, 'marker', '+', 'linestyle', 'none', f);
  end
  legend (lgnd);
  drawnow;
