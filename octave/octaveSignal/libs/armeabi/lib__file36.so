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
%% @deftypefn {Function File} {[@var{fhandle}, @var{fullname}] = } data2fun (@var{ti}, @var{yi})
%% @deftypefnx {Function File} {[ @dots{} ] = } data2fun (@var{ti}, @var{yi},@var{property},@var{value})
%% Creates a vectorized function based on data samples using interpolation.
%%
%% The values given in @var{yi} (N-by-k matrix) correspond to evaluations of the
%% function y(t) at the points @var{ti} (N-by-1 matrix).
%% The data is interpolated and the function handle to the generated interpolant
%% is returned.
%%
%% The function accepts property-value pairs described below.
%%
%% @table @samp
%% @item file
%% Code is generated and .m file is created. The @var{value} contains the name
%% of the function. The returned function handle is a handle to that file. If
%% @var{value} is empty, then a name is automatically generated using
%% @code{tmpnam} and the file is created in the current directory. @var{value}
%% must not have an extension, since .m will be appended.
%% Numerical value used in the function are stored in a .mat file with the same
%% name as the function.
%%
%% @item interp
%% Type of interpolation. See @code{interp1}.
%%
%% @end table
%%
%% @seealso{interp1}
%% @end deftypefn

function [fhandle fullfname] = data2fun( t, y, varargin)

  %% Check input arguments
  interp_args = {"spline"};
  given = struct("file",false);
  if ~isempty(varargin)
      % Arguments
      interp_args = varargin;

      opt_args = fieldnames (given);
      [tf idx] = ismember( opt_args, varargin);
      for i=1:numel(opt_args)
        given.(opt_args{i}) = tf(i);
      end

      if given.file
        %% TODO: check that file will be in the path. Otherwise fhabdle(0) fails.

        if !isempty(varargin{idx(1)+1})

          [DIR fname] = fileparts(varargin{idx(1)+1});

        else

          [DIR fname] = fileparts (tmpnam (pwd (),"agen_"));

        end

        interp_args(idx(1)+[0 1]) = [];
      end

      if isempty(interp_args)

        interp_args = {"spline"};

      end
  end

  pp = interp1 (t, y, interp_args{end}, 'pp');

  if given.file
    fullfname = fullfile (DIR,[fname ".m"]);
    save("-binary",[fullfname(1:end-2) ".mat"],"pp");

    bodystr = ["  persistent pp\n" ...
                   "  if isempty(pp)\n" ...
                   "    pp = load([mfilename()" ' ".mat"' "]).pp;\n"...
                   "  end\n\n" ...
                   "  z = ppval(pp, x);"];

    strfunc = generate_function_str(fname, {"z"}, {"x"}, bodystr);

    fid = fopen ( fullfile (DIR,[fname ".m"]), "w");
    fprintf (fid, "%s", strfunc);
    fclose (fid);

    disp(["Function generated: " fullfname ]);
    fhandle = eval(["@" fname]);

  else
    fullfname = "";
    fhandle = @(t_) ppval (pp, t_);

  end

endfunction

function str = generate_function_str(name, oargs, iargs, bodystr)

  striargs = cell2mat ( cellfun (@(x) [x ", "], iargs, "UniformOutput", false));
  striargs = striargs(1:end-2);

  stroargs = cell2mat ( cellfun (@(x) [x ", "], oargs, "UniformOutput", false));
  stroargs = stroargs(1:end-2);

  if !isempty (stroargs)
    str = ["function [" stroargs "] = " name "(" striargs ")\n\n" bodystr ...
           "\n\nendfunction"];
  else
    str = ["function " name "(" striargs ")\n\n" bodystr ...
           "\n\nendfunction"];
  end

endfunction

%!shared t, y
%! t = linspace(0,1,10);
%! y = t.^2 - 2*t + 1;

%!test
%! fhandle = data2fun(t,y);
%! assert(y,fhandle(t));

%!test
%! [fhandle fname] = data2fun(t,y,"file","testdata2fun");
%! yt = testdata2fun(t);
%!
%! assert(y,yt);
%! assert(y,fhandle(t));
%!
%! delete(fname);
%! delete([fname(1:end-2) ".mat"]);

%!test
%! [fhandle fname] = data2fun(t,y,"file","");
%! yt = testdata2fun(t);
%!
%! assert(y,yt);
%! assert(y,fhandle(t));
%!
%! delete(fname);
%! delete([fname(1:end-2) ".mat"]);

%!test
%! [fhandle fname] = data2fun(t,y,"file","testdata2fun","interp","linear");
%! yt = testdata2fun(t);
%!
%! assert(y,yt);
%! assert(y,fhandle(t));
%!
%! delete(fname);
%! delete([fname(1:end-2) ".mat"]);
