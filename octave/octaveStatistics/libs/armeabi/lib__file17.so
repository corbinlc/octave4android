## Author: Paul Kienzle <pkienzle@users.sf.net>
## This program is granted to the public domain.

## -*- texinfo -*-
## @deftypefn {Function File} fullfact (@var{N})
## Full factorial design.
##
## If @var{N} is a scalar, return the full factorial design with @var{N} binary
## choices, 0 and 1.
##
## If @var{N} is a vector, return the full factorial design with choices 1 
## through @var{n_i} for each factor @var{i}.
##
## @end deftypefn

function A = fullfact(n)
  if length(n) == 1
    % combinatorial design with n either/or choices
    A = fullfact(2*ones(1,n))-1;
  else
    % combinatorial design with n(i) choices per level
    A = [1:n(end)]';
    for i=length(n)-1:-1:1
      A = [kron([1:n(i)]',ones(rows(A),1)), repmat(A,n(i),1)];
    end
  end
endfunction
