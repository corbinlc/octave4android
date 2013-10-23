## Copyright (C) 2008 Jaroslav Hajek <highegg@gmail.com>
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
## @deftypefn{Function File} ex = fexist (file, tspec, aspec)
## Checks whether a file exists.
## @var{file} is the queried file path.
## @var{tspec} is a combination of letters f,d,p,S, corresponding
## to file types:
## @itemize
## @item f: regular file
## @item d: directory
## @item p: named pipe (FIFO special file)
## @item S: socket
## @end itemize
##
## The query is true if the actual file type matches any of 
## the specified options.
##
## @var{aspec} is a combination of letters r,w,x, corresponding
## to queried access privileges to the file. The query is true
## if the current user has all the spefied types of access, either
## through "user", "group" or "other" specs.
##
## @seealso{stat, lstat}
## @end deftypefn

function ex = fexist (file, tspec, aspec)
s = stat (file);
if (isempty (s))
  ex = 0;
else
  ex = 1;
  if (nargin >= 2 && ! isempty (tspec))
    ft = 0;
    for c = tspec
      switch (c)
      case 'f'
	ft |= S_ISREG (s.mode);
      case 'd'
	ft |= S_ISDIR (s.mode);
      case 'p'
	ft |= S_ISFIFO (s.mode);
      case 'S'
	ft |= S_ISSOCK (s.mode);
      otherwise
	error ("invalid file type spec: %s", c)
      endswitch
    endfor
    ex &= ft;
  endif
  if (ex && nargin >= 3 && ! isempty (aspec))
    at = 1;
    mypid = (s.uid == getuid ());
    mygid = (s.gid == getgid ());
    mstr = s.modestr(2:end);
    for c = aspec
      switch (c)
      case 'r'
	at &= (mypid && mstr(1) == c) || (mygid && mstr(4) == c) || mstr(7) == c;
      case 'w'
	at &= (mypid && mstr(2) == c) || (mygid && mstr(5) == c) || mstr(8) == c;
      case 'x'
	at &= (mypid && mstr(3) == c) || (mygid && mstr(6) == c) || mstr(9) == c;
      otherwise
	error ("invalid access type spec: %s", c)
      endswitch
    endfor
    ex &= at;
  endif
endif
