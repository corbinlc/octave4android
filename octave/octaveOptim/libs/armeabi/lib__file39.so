## Copyright (C) 2009 Luca Favatella <slackydeb@gmail.com>
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
## @deftypefn{Function File} {@var{x} =} linprog (@var{f}, @var{A}, @var{b})
## @deftypefnx{Function File} {@var{x} =} linprog (@var{f}, @var{A}, @var{b}, @var{Aeq}, @var{beq})
## @deftypefnx{Function File} {@var{x} =} linprog (@var{f}, @var{A}, @var{b}, @var{Aeq}, @var{beq}, @var{lb}, @var{ub})
## @deftypefnx{Function File} {[@var{x}, @var{fval}] =} linprog (@dots{})
## Solve a linear problem.
##
## Finds
##
## @example
## min (f' * x)
## @end example
##
## (both f and x are column vectors) subject to
##
## @example
## @group
## A   * x <= b
## Aeq * x  = beq
## lb <= x <= ub
## @end group
## @end example
##
## If not specified, @var{Aeq} and @var{beq} default to empty matrices.
##
## If not specified, the lower bound @var{lb} defaults to minus infinite
## and the upper bound @var{ub} defaults to infinite.
##
## @seealso{glpk}
## @end deftypefn

function [x fval] = linprog (f, A, b,
                             Aeq = [], beq = [],
                             lb = [], ub = [])

  if (((nargin != 3) && (nargin != 5) && (nargin != 7)) ||
      (nargout > 2))
    print_usage ();
  endif

  nr_f = rows(f);

  # Sanitize A and b
  if (isempty (A) && isempty (b))
    A = zeros (0, nr_f);
    b = zeros (rows (A), 1);
  endif

  nr_A = rows (A);

  if (columns (f) != 1)
    error ("f must be a column vector");
  elseif (columns (A) != nr_f)
    error ("columns (A) != rows (f)");
  elseif (size (b) != [nr_A 1])
    error ("size (b) != [(rows (A)) 1]");
  else

    ## Sanitize Aeq
    if (isempty (Aeq))
      Aeq = zeros (0, nr_f);
    endif
    if (columns (Aeq) != nr_f)
      error ("columns (Aeq) != rows (f)");
    endif

    ## Sanitize beq
    if (isempty (beq))
      beq = zeros (0, 1);
    endif
    nr_Aeq = rows (Aeq);
    if (size (beq) != [nr_Aeq 1])
      error ("size (beq) != [(rows (Aeq)) 1]");
    endif

    ## Sanitize lb
    if (isempty (lb))
      lb = - Inf (nr_f, 1);
    endif
    if (size (lb) != [nr_f 1])
      error ("size (lb) != [(rows (f)) 1]");
    endif

    ## Sanitize ub
    if (isempty (ub))
      ub = Inf (nr_f, 1);
    endif
    if (size (ub) != [nr_f 1])
      error ("size (ub) != [(rows (f)) 1]");
    endif


    ## Call glpk
    ctype = [(repmat ("U", nr_A, 1));
             (repmat ("S", nr_Aeq, 1))];
    [x(1:nr_f, 1) fval(1, 1)] = glpk (f, [A; Aeq], [b; beq], lb, ub, ctype);

  endif

endfunction

%!test
%! f = [1; -1];
%! A = [];
%! b = [];
%! Aeq = [1, 0];
%! beq = [2];
%! lb = [0; Inf];
%! ub = [-Inf; 0];
%! x_exp = [2; 0];
%! assert (linprog (f, A, b, Aeq, beq, lb, ub), x_exp);

%!shared f, A, b, lb, ub, x_exp, fval_exp
%! f  = [21 25 31 34  23 19 32  36 27 25 19]';
%!
%! A1 = [ 1  0  0  0   1  0  0   1  0  0  0;
%!        0  1  0  0   0  1  0   0  1  0  0;
%!        0  0  1  0   0  0  0   0  0  1  0;
%!        0  0  0  1   0  0  1   0  0  0  1];
%! A2 = [ 1  1  1  1   0  0  0   0  0  0  0;
%!        0  0  0  0   1  1  1   0  0  0  0;
%!        0  0  0  0   0  0  0   1  1  1  1];
%! A  = [-A1; A2];
%!
%! b1 = [40; 50; 50; 70];
%! b2 = [100; 60; 50];
%! b  = [-b1; b2];
%!
%! lb = zeros (rows (f), 1);
%! ub =   Inf (rows (f), 1);
%!
%! x_exp    = [40 0 50 10 0 50 10 0 0 0 50]';
%! fval_exp = f' * x_exp;
%!
%!test
%! Aeq = [];
%! beq = [];
%! [x_obs fval_obs] = linprog (f, A, b, Aeq, beq, lb, ub);
%! assert ([x_obs; fval_obs], [x_exp; fval_exp]);
%!
%!test
%! Aeq = zeros (1, rows (f));
%! beq = 0;
%! assert (linprog (f, A, b, Aeq, beq, lb, ub), x_exp);
