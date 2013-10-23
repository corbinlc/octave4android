## Copyright (C) 1996, 1997 R. Storn
## Copyright (C) 2009-2010 Christian Fischer <cfischer@itm.uni-stuttgart.de>
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

## de_min: global optimisation using differential evolution
##
## Usage: [x, obj_value, nfeval, convergence] = de_min(fcn, control)
##
## minimization of a user-supplied function with respect to x(1:D),
## using the differential evolution (DE) method based on an algorithm
## by  Rainer Storn (http://www.icsi.berkeley.edu/~storn/code.html)
## See: http://www.softcomputing.net/tevc2009_1.pdf
##
##
## Arguments:  
## ---------------
## fcn        string : Name of function. Must return a real value
## control    vector : (Optional) Control variables, described below
##         or struct
##
## Returned values:
## ----------------
## x          vector : parameter vector of best solution
## obj_value  scalar : objective function value of best solution
## nfeval     scalar : number of function evaluations
## convergence       : 1 = best below value to reach (VTR)
##                     0 = population has reached defined quality (tol)
##                    -1 = some values are close to constraints/boundaries
##                    -2 = max number of iterations reached (maxiter)
##                    -3 = max number of functions evaluations reached (maxnfe)
##
## Control variable:   (optional) may be named arguments (i.e. "name",value
## ----------------    pairs), a struct, or a vector, where
##                     NaN's are ignored.
##
## XVmin        : vector of lower bounds of initial population
##                *** note: by default these are no constraints ***
## XVmax        : vector of upper bounds of initial population
## constr       : 1 -> enforce the bounds not just for the initial population
## const        : data vector (remains fixed during the minimization)
## NP           : number of population members
## F            : difference factor from interval [0, 2]
## CR           : crossover probability constant from interval [0, 1]
## strategy     : 1 --> DE/best/1/exp           7 --> DE/best/1/bin
##                2 --> DE/rand/1/exp           8 --> DE/rand/1/bin
##                3 --> DE/target-to-best/1/exp 9 --> DE/target-to-best/1/bin
##                4 --> DE/best/2/exp           10--> DE/best/2/bin
##                5 --> DE/rand/2/exp           11--> DE/rand/2/bin
##                6 --> DEGL/SAW/exp            else  DEGL/SAW/bin
## refresh      : intermediate output will be produced after "refresh"
##                iterations. No intermediate output will be produced
##                if refresh is < 1
## VTR          : Stopping criterion: "Value To Reach"
##                de_min will stop when obj_value <= VTR.
##                Use this if you know which value you expect.
## tol          : Stopping criterion: "tolerance"
##                stops if (best-worst)/max(1,worst) < tol
##                This stops basically if the whole population is "good".
## maxnfe       : maximum number of function evaluations
## maxiter      : maximum number of iterations (generations)
##
##       The algorithm seems to work well only if [XVmin,XVmax] covers the 
##       region where the global minimum is expected.
##       DE is also somewhat sensitive to the choice of the
##       difference factor F. A good initial guess is to choose F from
##       interval [0.5, 1], e.g. 0.8.
##       CR, the crossover probability constant from interval [0, 1]
##       helps to maintain the diversity of the population and is
##       rather uncritical but affects strongly the convergence speed.
##       If the parameters are correlated, high values of CR work better.
##       The reverse is true for no correlation.
##       Experiments suggest that /bin likes to have a slightly
##       larger CR than /exp.
##       The number of population members NP is also not very critical. A
##       good initial guess is 10*D. Depending on the difficulty of the
##       problem NP can be lower than 10*D or must be higher than 10*D
##       to achieve convergence.
##
## Default Values:
## ---------------
## XVmin = [-2];
## XVmax = [ 2];
## constr= 0;
## const = [];
## NP    = 10 *D
## F     = 0.8;
## CR    = 0.9;
## strategy = 12;
## refresh  = 0;
## VTR   = -Inf;
## tol   = 1.e-3;
## maxnfe  = 1e6;
## maxiter = 1000;
##
##
## Example to find the minimum of the Rosenbrock saddle:
## ----------------------------------------------------
## Define f as:
##                    function result = f(x);
##                      result = 100 * (x(2) - x(1)^2)^2 + (1 - x(1))^2;
##                    end
## Then type:
##
## 	ctl.XVmin = [-2 -2];
## 	ctl.XVmax = [ 2  2];
## 	[x, obj_value, nfeval, convergence] = de_min (@f, ctl);
##
## Keywords: global-optimisation optimisation minimisation

function [bestmem, bestval, nfeval, convergence] = de_min(fcn, varargin)

## Default options
XVmin = [-2 ];
XVmax = [ 2 ];
constr= 0;
const = [];
NP    = 0;         # NP will be set later
F     = 0.8;
CR    = 0.9;
strategy = 12;
refresh  = 0;
VTR   = -Inf;
tol   = 1.e-3;
maxnfe  = 1e6;
maxiter = 1000;

## ------------ Check input variables (ctl) --------------------------------
if nargin >= 2,			# Read control arguments
  va_arg_cnt = 1;
  if nargin > 2,
    ctl = struct (varargin{:});
  else
    ctl = varargin{va_arg_cnt++};
  end
  if isnumeric (ctl)
    if length (ctl)>=1 && !isnan (ctl(1)), XVmin  = ctl(1); end
    if length (ctl)>=2 && !isnan (ctl(2)), XVmax  = ctl(2); end
    if length (ctl)>=3 && !isnan (ctl(3)), constr = ctl(3); end
    if length (ctl)>=4 && !isnan (ctl(4)), const  = ctl(4); end
    if length (ctl)>=5 && !isnan (ctl(5)), NP  = ctl(5); end
    if length (ctl)>=6 && !isnan (ctl(6)), F   = ctl(6); end
    if length (ctl)>=7 && !isnan (ctl(7)), CR  = ctl(7); end
    if length (ctl)>=8 && !isnan (ctl(8)), strategy = ctl(8); end
    if length (ctl)>=9 && !isnan (ctl(9)), refresh  = ctl(9); end
    if length (ctl)>=10&& !isnan (ctl(10)), VTR   = ctl(10); end
    if length (ctl)>=11&& !isnan (ctl(11)), tol   = ctl(11); end
    if length (ctl)>=12&& !isnan (ctl(12)), maxnfe  = ctl(12); end
    if length (ctl)>=13&& !isnan (ctl(13)), maxiter = ctl(13); end
  else
    if isfield (ctl,"XVmin") && !isnan (ctl.XVmin), XVmin=ctl.XVmin; end
    if isfield (ctl,"XVmax") && !isnan (ctl.XVmax), XVmax=ctl.XVmax; end
    if isfield (ctl,"constr")&& !isnan (ctl.constr), constr=ctl.constr; end
    if isfield (ctl,"const") && !isnan (ctl.const), const=ctl.const; end
    if isfield (ctl, "NP" ) && ! isnan (ctl.NP  ), NP  = ctl.NP  ; end
    if isfield (ctl, "F"  ) && ! isnan (ctl.F   ), F   = ctl.F   ; end
    if isfield (ctl, "CR" ) && ! isnan (ctl.CR  ), CR  = ctl.CR  ; end
    if isfield (ctl, "strategy") && ! isnan (ctl.strategy),
      strategy = ctl.strategy  ; end
    if isfield (ctl, "refresh") && ! isnan (ctl.refresh),
      refresh  = ctl.refresh   ; end
    if isfield (ctl, "VTR") && ! isnan (ctl.VTR ), VTR = ctl.VTR ; end
    if isfield (ctl, "tol") && ! isnan (ctl.tol ), tol = ctl.tol ; end
    if isfield (ctl, "maxnfe")  && ! isnan (ctl.maxnfe)
      maxnfe = ctl.maxnfe;
    end
    if isfield (ctl, "maxiter") && ! isnan (ctl.maxiter)
      maxiter = ctl.maxiter;
    end
  end
end

## set dimension D and population size NP
D  = length (XVmin);
if (NP == 0);
  NP = 10 * D;
end

## -------- do a few sanity checks --------------------------------
if (length (XVmin) != length (XVmax))
  error("Length of upper and lower bounds does not match.")
end
if (NP < 5)
  error("Population size NP must be bigger than 5.")
end
if ((F <= 0) || (F > 2))
  error("Difference Factor F out of range (0,2].")
end
if ((CR < 0) || (CR > 1))
  error("CR value out of range [0,1].")
end
if (maxiter <= 0)
  error("maxiter must be positive.")
end
if (maxnfe <= 0)
  error("maxnfe must be positive.")
end
refresh = floor(abs(refresh));

## ----- Initialize population and some arrays --------------------------

pop = zeros(NP,D);  # initialize pop

## pop is a matrix of size NPxD. It will be initialized with
## random values between the min and max values of the parameters
for i = 1:NP
   pop(i,:) = XVmin + rand (1,D) .* (XVmax - XVmin);
end

## initialise the weighting factors between 0.0 and 1.0
w  = rand (NP,1);
wi = w;

popold    = zeros (size (pop));   # toggle population
val       = zeros (1, NP);        # create and reset the "cost array"
bestmem   = zeros (1, D);         # best population member ever
bestmemit = zeros (1 ,D);         # best population member in iteration
nfeval    = 0;                    # number of function evaluations

## ------ Evaluate the best member after initialization ------------------

ibest   = 1;                      # start with first population member
val(1)  = feval (fcn, [pop(ibest,:) const]);
bestval = val(1);                 # best objective function value so far
bestw   = w(1);                   # weighting of best design so far
for i = 2:NP                      # check the remaining members
  val(i) = feval (fcn, [pop(i,:) const]);
  if (val(i) < bestval)           # if member is better
     ibest   = i;                 # save its location
     bestval = val(i);
     bestw   = w(i);
  end   
end
nfeval  = nfeval + NP;
bestmemit = pop(ibest,:);         # best member of current iteration
bestvalit = bestval;              # best value of current iteration

bestmem = bestmemit;              # best member ever

## ------ DE - Minimization ---------------------------------------
## popold is the population which has to compete. It is static
## through one iteration. pop is the newly emerging population.

bm_n= zeros (NP, D);            # initialize bestmember matrix in neighbourh.
lpm1= zeros (NP, D);            # initialize local population matrix 1
lpm1= zeros (NP, D);            # initialize local population matrix 2
rot = 0:1:NP-1;                 # rotating index array (size NP)
rotd= 0:1:D-1;                  # rotating index array (size D)

iter = 1;
while ((iter < maxiter) && (nfeval < maxnfe) &&  (bestval > VTR)  && ...
       ((abs (max (val) - bestval) / max (1, abs (max (val))) > tol)))
  popold = pop;                   # save the old population
  wold   = w;                     # save the old weighting factors

  ind = randperm (4);             # index pointer array

  a1  = randperm (NP);            # shuffle locations of vectors
  rt = rem (rot + ind(1), NP);    # rotate indices by ind(1) positions
  a2  = a1(rt+1);                 # rotate vector locations
  rt = rem (rot + ind(2), NP);
  a3  = a2(rt+1);                
  rt = rem (rot  +ind(3), NP);
  a4  = a3(rt+1);               
  rt = rem (rot + ind(4), NP);
  a5  = a4(rt+1);                

  pm1 = popold(a1,:);             # shuffled population 1
  pm2 = popold(a2,:);             # shuffled population 2
  pm3 = popold(a3,:);             # shuffled population 3
  w1  = wold(a4);                 # shuffled weightings 1
  w2  = wold(a5);                 # shuffled weightings 2

  bm = repmat (bestmemit, NP, 1); # population filled with the best member
                                  # of the last iteration
  bw = repmat (bestw, NP, 1);     # the same for the weighting of the best

  mui = rand (NP, D) < CR;        # mask for intermediate population
                                  # all random numbers < CR are 1, 0 otherwise

  if (strategy > 6)
    st = strategy - 6;		      # binomial crossover
  else
    st = strategy;		          # exponential crossover
    mui = sort (mui');	          # transpose, collect 1's in each column
    for i = 1:NP
      n = floor (rand * D);
      if (n > 0)
         rtd = rem (rotd + n, D);
         mui(:,i) = mui(rtd+1,i); #rotate column i by n
      endif
    endfor
    mui = mui';			  		  # transpose back
  endif
  mpo = mui < 0.5;                # inverse mask to mui

  if (st == 1)                      	  # DE/best/1
    ui = bm + F*(pm1 - pm2);        	  # differential variation
  elseif (st == 2)                  	  # DE/rand/1
    ui = pm3 + F*(pm1 - pm2);       	  # differential variation
  elseif (st == 3)                        # DE/target-to-best/1
    ui = popold + F*(bm-popold) + F*(pm1 - pm2);        
  elseif (st == 4)                  	  # DE/best/2
    pm4 = popold(a4,:);                 # shuffled population 4
    pm5 = popold(a5,:);                 # shuffled population 5
    ui = bm + F*(pm1 - pm2 + pm3 - pm4);  # differential variation
  elseif (st == 5)                  	  # DE/rand/2
    pm4 = popold(a4,:);                 # shuffled population 4
    pm5 = popold(a5,:);                 # shuffled population 5
    ui = pm5 + F*(pm1 - pm2 + pm3 - pm4); # differential variation
  else                                    # DEGL/SAW
    ## The DEGL/SAW method is more complicated.
	## We have to generate a neighbourhood first.
	## The neighbourhood size is 10% of NP and at least 1.
	nr = max (1, ceil ((0.1*NP -1)/2));  # neighbourhood radius
	## FIXME: I don't know how to vectorise this. - if possible
	for i = 1:NP
	  neigh_ind = i-nr:i+nr;              # index range of neighbourhood
	  neigh_ind = neigh_ind + ((neigh_ind <= 0)-(neigh_ind > NP))*NP;
                                          # do wrap around
	  [x, ix] = min (val(neigh_ind));     # find the local best and its index
	  bm_n(i,:) = popold(neigh_ind(ix),:); # copy the data from the local best
	  neigh_ind(nr+1) = [];                 # remove "i"
	  pq = neigh_ind(randperm (length (neigh_ind)));
                                        # permutation of the remaining ind.
	  lpm1(i,:) = popold(pq(1),:);        # create the local pop member matrix
	  lpm2(i,:) = popold(pq(2),:);        # for the random point p,q
	endfor
    ## calculate the new weihting factors
    wi = wold + F*(bw - wold) + F*(w1 - w2); # use DE/target-to-best/1/nocross
                                          # for optimisation of weightings
    ## fix bounds for weightings
    o = ones (NP, 1);
    wi = sort ([0.05*o, wi, 0.95*o],2)(:,2); # sort and take the second column
    ## fill weighting matrix
    wm = repmat (wi, 1, D);
    li = popold + F*(bm_n- popold) + F*(lpm1 - lpm2);
    gi = popold + F*(bm  - popold) + F*(pm1 - pm2);
    ui = wm.*gi + (1-wm).*li;             # combine global and local part
  endif
  ## crossover
  ui = popold.*mpo + ui.*mui;
  
  ## enforce initial bounds/constraints if specified
  if (constr == 1)
    for i = 1:NP
      ui(i,:) = max (ui(i,:), XVmin);
      ui(i,:) = min (ui(i,:), XVmax);
    end
  end

  ## ----- Select which vectors are allowed to enter the new population ------
  for i = 1:NP
    tempval = feval (fcn, [ui(i,:) const]);   # check cost of competitor
    if (tempval <= val(i))  # if competitor is better
       pop(i,:) = ui(i,:);  # replace old vector with new one
       val(i)   = tempval;  # save value in "cost array"
       w(i)     = wi(i);    # save the weighting factor

       ## we update bestval only in case of success to save time
       if (tempval <= bestval)     # if competitor better than the best one ever
          bestval = tempval;      # new best value
          bestmem = ui(i,:);      # new best parameter vector ever
          bestw   = wi(i);        # save best weighting
       end
    end
  endfor #---end for i = 1:NP

  nfeval  = nfeval + NP;     # increase number of function evaluations

  bestmemit = bestmem;       # freeze the best member of this iteration for the
                             # coming iteration. This is needed for some of the
                             # strategies.

  ## ---- Output section ----------------------------------------------------

  if (refresh > 0)
    if (rem (iter, refresh) == 0)
       printf ('Iteration: %d,  Best: %8.4e,  Worst: %8.4e\n', ...
                iter, bestval, max(val));
       for n = 1:D
         printf ('x(%d) = %e\n', n, bestmem(n));
       end
    end
  end

  iter = iter + 1;
endwhile #---end while ((iter < maxiter) ...

## check that all variables are well within bounds/constraints
boundsOK = 1;
for i = 1:NP
  range = XVmax - XVmin;
  if (ui(i,:) < XVmin + 0.01*range)
    boundsOK = 0;
  end
  if (ui(i,:) > XVmax - 0.01*range)
    boundsOK = 0;
  end
end

## create the convergence result
if (bestval <= VTR)
  convergence = 1;
elseif (abs (max (val) - bestval) / max (1, abs (max (val))) <= tol)
  convergence = 0;
elseif (boundsOK == 0)
  convergence = -1;
elseif (iter >= maxiter)
  convergence = -2;
elseif (nfeval >= maxnfe)
  convergence = -3;
end

endfunction

%!function result = f(x);
%!  result = 100 * (x(2) - x(1)^2)^2 + (1 - x(1))^2;
%!test
%! tol = 1.0e-4;
%! ctl.tol   = 0.0;
%! ctl.VTR   = 1.0e-6;
%! ctl.XVmin = [-2 -2];
%! ctl.XVmax = [ 2  2];
%! rand("state", 11)
%! [x, obj_value, nfeval, convergence] = de_min (@f, ctl);
%! assert (convergence == 1);
%! assert (f(x) == obj_value);
%! assert (obj_value < ctl.VTR);

%!demo
%! ## define a simple example function
%! f = @(x) peaks(x(1), x(2));
%! ## plot the function to see where the minimum might be
%! peaks()
%! ## first we set the region where we expect the minimum
%! ctl.XVmin = [-3 -3];
%! ctl.XVmax = [ 3  3];
%! ## and solve it with de_min
%! [x, obj_value, nfeval, convergence] = de_min (f, ctl)
