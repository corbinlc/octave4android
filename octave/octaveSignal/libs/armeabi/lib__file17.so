## Copyright (C) 2008 David Bateman <adb014@gmail.com>
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
## @deftypefn {Function File} {@var{y} = } buffer (@var{x}, @var{n}, @var{p}, @var{opt})
## @deftypefnx {Function File} {[@var{y}, @var{z}, @var{opt}] = } buffer (@dots{})
## Buffer a signal into a data frame. The arguments to @code{buffer} are
##
## @table @asis
## @item @var{x}
## The data to be buffered. 
##
## @item @var{n}
## The number of rows in the produced data buffer. This is an positive
## integer value and must be supplied.
##
## @item @var{p}
## An integer less than @var{n} that specifies the under- or overlap
## between column in the data frame. The default value of @var{p} is 0.
##
## @item @var{opt}
## In the case of an overlap, @var{opt} can be either a vector of length
## @var{p} or the string 'nodelay'. If @var{opt} is a vector, then the
## first @var{p} entries in @var{y} will be filled with these values. If
## @var{opt} is the string 'nodelay', then the first value of @var{y}
## corresponds to the first value of @var{x}. 
## 
## In the can of an underlap, @var{opt} must be an integer between 0 and
## @code{-@var{p}}. The represents the initial underlap of the first
## column of @var{y}.
##
## The default value for @var{opt} the vector @code{zeros (1, @var{p})}
## in the case of an overlap, or 0 otherwise.
## @end table
##
## In the case of a single output argument, @var{y} will be padded with
## zeros to fill the missing values in the data frame. With two output
## arguments @var{z} is the remaining data that has not been used in the
## current data frame.
##
## Likewise, the output @var{opt} is the overlap, or underlap that might
## be used for a future call to @code{code} to allow continuous buffering.
## @end deftypefn

function [y, z, opt] = buffer (x, n, p, opt)

  if (nargin < 2 || nargin > 4)
    print_usage ();
  endif
  if  (!isscalar (n) || n != floor (n))
    error ("buffer: n must be an inetger");
  endif
  if (nargin < 3)
    p = 0;
  elseif (!isscalar (p) || p != floor (p) || p >= n)
    error ("buffer: p must be an inetger less than n");
  endif
  if (nargin <  4)
    if (p < 0)
      opt = 0;
    else
      opt = zeros (1, p);
    endif
  endif

  if (rows (x) == 1)
    isrowvec = true;
  else
    isrowvec = false;
  endif

  if (p < 0)
    if (isscalar (opt) && opt == floor (opt) && opt >= 0 && opt <= -p)
      lopt = opt;
    else
      error ("buffer: expecting fourth argument to be and integer between 0 and -p");
    endif
  else
    lopt = 0;
  endif

  x = x (:);
  l = length (x);
  m = ceil ((l - lopt) / (n - p));
  y = zeros (n - p, m);
  y (1 : l - lopt) = x (lopt + 1 : end);
  if (p < 0)
    y (end + p + 1 : end, :) = [];
  elseif (p > 0)
    if (ischar (opt))
      if (strcmp (opt, "nodelay"))
        y = [y ; zeros(p, m)]; 
        if (p > n / 2)
          is = n - p + 1;
          in = n - p;
          ie = is + in - 1;
          off = 1;
          while (in > 0)
            y (is : ie, 1 : end - off) = y (1 : in, 1 + off : end);
            off++;
            is = ie + 1;
            ie = ie + in;
            if (ie > n)
              ie = n;
            endif
            in = ie - is + 1;
          endwhile
          [i, j] = ind2sub([n-p, m], l);
          if (all ([i, j] == [n-p, m]))
            off --;
          endif
          y (:, end - off + 2 : end) = [];
        else
          y (end - p + 1 : end, 1 : end - 1) = y (1 : p, 2 : end);
          if (sub2ind([n-p, m], p, m) >= l)
            y (:, end) = [];
          endif
        endif
      else
        error ("buffer: unexpected string argument");
      endif
    elseif (isvector (opt))
      if (length (opt) == p)
        lopt = p;
        y = [zeros(p, m); y]; 
        in = p;
        off = 1;
        while (in > 0)
          y (1 : in, off) = opt(off:end);
          off++;
          in = in - n + p;
        endwhile
        if (p > n / 2)
          in = n - p;
          ie = p;
          is = p - in + 1;
          off = 1;
          while (ie > 0)
            y (is : ie, 1 + off : end) = ...
              y (end - in + 1 : end, 1 : end - off);
            off++;
            ie = is - 1;
            is = is - in;
            if (is < 1)
              is = 1;
            endif
            in = ie - is + 1;
          endwhile
        else
          y (1 : p, 2 : end) = y (end - p + 1 : end, 1 : end - 1);
        endif
      else
        error ("buffer: opt vector must be of length p");
      endif
    else
      error ("buffer: unrecognized fourth argument");
    endif
  endif
  if (nargout > 1)
    if (p >= 0)
      [i, j] = ind2sub (size(y), l + lopt + p * (size (y, 2) - 1));
      if (any ([i, j] != size (y)))
        z = y (1 + p : i, end);
        y (:, end) = [];
      else
        z = zeros (0, 1);
      endif
    else
      [i, j] = ind2sub (size (y) + [-p, 0], l - lopt);
      if (i < size (y, 1))
        z = y (1: i, end);
        y (:, end) = [];
      else
        z = zeros (0, 1);
      endif
    endif
    if (isrowvec)
      z = z.';
    endif
    if (p < 0)
      opt = max(0, size (y, 2) * (n - p) + opt - l);
    elseif (p > 0)
      opt = y(end-p+1:end)(:);
    else
      opt = [];
    endif 
  endif
endfunction

%!error (buffer(1:10, 4.1))
%!assert (buffer(1:10, 4), reshape([1:10,0,0],[4,3]))
%!assert (buffer(1:10, 4, 1), reshape([0:3,3:6,6:9,9,10,0,0],[4,4]))
%!assert (buffer(1:10, 4, 2), reshape ([0,0:2,1:4,3:6,5:8,7:10],[4,5])) 
%!assert (buffer(1:10, 4, 3), [0,0,0:7;0,0:8;0:9;1:10])
%!error (buffer(1:10, 4, 3.1))
%!error (buffer(1:10, 4, 4))
%!assert (buffer(1:10, 4, -1), reshape([1:4,6:9],[4,2]))
%!assert (buffer(1:10, 4, -2), reshape([1:4,7:10],[4,2]))
%!assert (buffer(1:10, 4, -3), reshape([1:4,8:10,0],[4,2]))
%!assert (buffer(1:10, 4, 1, 11), reshape([11,1:3,3:6,6:9,9,10,0,0],[4,4]))
%!error (buffer(1:10, 4, 1, [10,11]))
%!assert (buffer(1:10, 4, 1, 'nodelay'), reshape([1:4,4:7,7:10],[4,3]))
%!error (buffer(1:10, 4, 1, 'badstring'))
%!assert (buffer(1:10, 4, 2,'nodelay'), reshape ([1:4,3:6,5:8,7:10],[4,4]))
%!assert (buffer(1:10, 4, 3, [11,12,13]),[11,12,13,1:7;12,13,1:8;13,1:9;1:10])
%!assert (buffer(1:10, 4, 3, 'nodelay'),[1:8;2:9;3:10;4:10,0])
%!assert (buffer(1:11,4,-2,1),reshape([2:5,8:11],4,2))

%!test
%! [y, z] = buffer(1:12,4);
%! assert (y, reshape(1:12,4,3));
%! assert (z, zeros (1,0));

%!test
%! [y, z] = buffer(1:11,4);
%! assert (y, reshape(1:8,4,2));
%! assert (z, [9, 10, 11]);

%!test
%! [y, z] = buffer([1:12]',4);
%! assert (y, reshape(1:12,4,3));
%! assert (z, zeros (0,1));

%!test
%! [y, z] = buffer([1:11]',4);
%! assert (y, reshape(1:8,4,2));
%! assert (z, [9; 10; 11]);

%!test
%! [y,z,opt] = buffer(1:15,4,-2,1);
%! assert (y, reshape([2:5,8:11],4,2));
%! assert (z, [14, 15]);
%! assert (opt, 0);

%!test
%! [y,z,opt] = buffer(1:11,4,-2,1);
%! assert (y, reshape([2:5,8:11],4,2));
%! assert (z, zeros (1,0));
%! assert (opt, 2);

%!test
%! [y,z,opt] = buffer([1:15]',4,-2,1);
%! assert (y, reshape([2:5,8:11],4,2));
%! assert (z, [14; 15]);
%! assert (opt, 0);

%!test
%! [y,z,opt] = buffer([1:11]',4,-2,1);
%! assert (y, reshape([2:5,8:11],4,2));
%! assert (z, zeros (0, 1));
%! assert (opt, 2);

%!test 
%! [y,z,opt] = buffer([1:11],5,2,[-1,0]);
%! assert (y, reshape ([-1:3,2:6,5:9],[5,3]));
%! assert (z, [10, 11]);
%! assert (opt, [8; 9]);

%!test 
%! [y,z,opt] = buffer([1:11]',5,2,[-1,0]);
%! assert (y, reshape ([-1:3,2:6,5:9],[5,3]));
%! assert (z, [10; 11]);
%! assert (opt, [8; 9]);
