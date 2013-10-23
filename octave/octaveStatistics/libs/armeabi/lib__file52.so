## Copyright (C) 2011 Alexander Klein <alexander.klein@math.uni-giessen.de>
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
## @deftypefn{Function File} {@var{NORMALISED} =} normalise_distribution (@var{DATA})
## @deftypefnx{Function File} {@var{NORMALISED} =} normalise_distribution (@var{DATA}, @var{DISTRIBUTION})
## @deftypefnx{Function File} {@var{NORMALISED} =} normalise_distribution (@var{DATA}, @var{DISTRIBUTION}, @var{DIMENSION})
## 
## Transform a set of data so as to be N(0,1) distributed according to an idea
## by van Albada and Robinson.
## This is achieved by first passing it through its own cumulative distribution
## function (CDF) in order to get a uniform distribution, and then mapping
## the uniform to a normal distribution.
## The data must be passed as a vector or matrix in @var{DATA}.
## If the CDF is unknown, then [] can be passed in @var{DISTRIBUTION}, and in
## this case the empirical CDF will be used.
## Otherwise, if the CDFs for all data are known, they can be passed in
## @var{DISTRIBUTION},
## either in the form of a single function name as a string,
## or a single function handle,
## or a cell array consisting of either all function names as strings,
## or all function handles.
## In the latter case, the number of CDFs passed must match the number
## of rows, or columns respectively, to normalise.
## If the data are passed as a matrix, then the transformation will
## operate either along the first non-singleton dimension,
## or along @var{DIMENSION} if present.
##
## Notes: 
## The empirical CDF will map any two sets of data
## having the same size and their ties in the same places after sorting
## to some permutation of the same normalised data:
## @example
## @code{normalise_distribution([1 2 2 3 4])}
## @result{} -1.28  0.00  0.00  0.52  1.28
##
## @code{normalise_distribution([1 10 100 10 1000])}
## @result{} -1.28  0.00  0.52  0.00  1.28
## @end example
##
## Original source:
## S.J. van Albada, P.A. Robinson
## "Transformation of arbitrary distributions to the
## normal distribution with application to EEG
## test-retest reliability"
## Journal of Neuroscience Methods, Volume 161, Issue 2,
## 15 April 2007, Pages 205-211
## ISSN 0165-0270, 10.1016/j.jneumeth.2006.11.004.
## (http://www.sciencedirect.com/science/article/pii/S0165027006005668)
## @end deftypefn

function [ normalised ] = normalise_distribution ( data, distribution, dimension )

  if ( nargin < 1 || nargin > 3 )
    print_usage;
  elseif ( !ismatrix ( data ) || length ( size ( data ) ) > 2 )
    error ( "First argument must be a vector or matrix" );
  end


  if ( nargin >= 2 )

    if ( !isempty ( distribution ) )

      #Wrap a single handle in a cell array.
      if ( strcmp ( typeinfo ( distribution ), typeinfo ( @(x)(x) ) ) )

        distribution = { distribution };
      
      #Do we have a string argument instead?
      elseif ( ischar ( distribution ) )

        ##Is it a single string?
        if ( rows ( distribution ) == 1 )

          distribution = { str2func( distribution ) };
        else
          error ( ["Second argument cannot contain more than one string" ...
             " unless in a cell array"] );
        end


      ##Do we have a cell array of distributions instead?
      elseif ( iscell ( distribution ) )

        ##Does it consist of strings only?
        if ( all ( cellfun ( @ischar, distribution ) ) )

          distribution = cellfun ( @str2func, distribution, "UniformOutput", false );
        end

        ##Does it eventually consist of function handles only
        if ( !all ( cellfun ( @ ( h ) ( strcmp ( typeinfo ( h ), typeinfo ( @(x)(x) ) ) ), distribution ) ) )

          error ( ["Second argument must contain either" ...
             " a single function name or handle or " ...
             " a cell array of either all function names or handles!"] );
        end

      else
        error ( "Illegal second argument: ", typeinfo ( distribution ) );
      end

    end

  else

    distribution = [];
  end


  if ( nargin == 3 )

    if ( !isscalar ( dimension ) || ( dimension != 1 && dimension != 2 ) )
      error ( "Third argument must be either 1 or 2" );
    end

  else
    if ( isvector ( data ) && rows ( data ) == 1 )

      dimension = 2;

    else

      dimension = 1;
    end
  end

  trp = ( dimension == 2 );

  if ( trp )
    data = data';
  end

  r = rows ( data );
  c = columns ( data );
  normalised = NA ( r, c );

  ##Do we know the distribution of the sample?
  if ( isempty ( distribution ) )

    precomputed_normalisation = [];


    for k = 1 : columns ( data )

      ##Note that this line is in accordance with equation (16) in the
      ##original text. The author's original program, however, produces
      ##different values in the presence of ties, namely those you'd
      ##get replacing "last" by "first".
      [ uniq, indices ] = unique ( sort ( data ( :, k ) ), "last" );


      ##Does the sample have ties?
      if ( rows ( uniq ) != r )

        ##Transform to uniform, then normal distribution.
        uniform = ( indices - 1/2 ) / r;
        normal = norminv ( uniform );

      else
        ## Without ties everything is pretty much straightforward as
        ## stated in the text.
        if ( isempty ( precomputed_normalisation ) )

          precomputed_normalisation = norminv ( 1 / (2*r) : 1/r : 1 - 1 / (2*r) );
        end

        normal = precomputed_normalisation;
      end

      #Find the original indices in the unsorted sample.
      #This somewhat quirky way of doing it is still faster than
      #using a for-loop.
      [ ignore, ignore, target_indices ] = unique ( data (:, k ) );

      #Put normalised values in the places where they belong.

      ## A regression in the 3.4 series made this no longer work so we behave
      ## differently depending on octave version. This applies the fix for all
      ## 3.4 releases but it may have appeared on 3.2.4 (can someone check?)
      ## See https://savannah.gnu.org/bugs/index.php?34765
      ## FIXME Once package dependency increases beyond an octave version that
      ## has this fixed, remove this
      if (compare_versions (OCTAVE_VERSION, "3.4", "<") || compare_versions (OCTAVE_VERSION, "3.6.2", ">="))
        ## this is how it should work
        f_remap = @( k ) ( normal ( k ) );
        normalised ( :, k ) = arrayfun ( f_remap, target_indices );
      else
        ## this is the workaround because of bug in 3.4.??
        for index = 1:numel(target_indices)
          normalised ( index, k ) = normal(target_indices(index));
        endfor
      endif

    end

  else
    ##With known distributions, everything boils down to a few lines of code

    ##The same distribution for all data?
    if ( all ( size ( distribution ) == 1 ) )

      normalised = norminv ( distribution {1,1} ( data ) );

    elseif ( length ( vec ( distribution ) ) == c )

      for k = 1 : c

        normalised ( :, k ) = norminv ( distribution { k } ( data ) ( :, k ) );
      end

    else
      error ( "Number of distributions does not match data size! ")

    end
  end

  if ( trp )

    normalised = normalised';
  end

endfunction

%!test
%! v = normalise_distribution ( [ 1 2 3 ], [], 1 );
%! assert ( v, [ 0 0 0 ] )

%!test
%! v = normalise_distribution ( [ 1 2 3 ], [], 2 );
%! assert ( v, norminv ( [ 1 3 5 ] / 6 ),  3 * eps )

%!test
%! v = normalise_distribution ( [ 1 2 3 ]', [], 2 );
%! assert ( v, [ 0 0 0 ]' )

%!test
%! v = normalise_distribution ( [ 1 2 3 ]' , [], 1 );
%! assert ( v, norminv ( [ 1 3 5 ]' / 6 ), 3 * eps )

%!test
%! v = normalise_distribution ( [ 1 1 2 2 3 3 ], [], 2 );
%! assert ( v, norminv ( [ 3 3 7 7 11 11 ] / 12 ),  3 * eps )

%!test
%! v = normalise_distribution ( [ 1 1 2 2 3 3 ]', [], 1 );
%! assert ( v, norminv ( [ 3 3 7 7 11 11 ]' / 12 ),  3 * eps )

%!test
%! A = randn ( 10 );
%! N = normalise_distribution ( A, @normcdf );
%! assert ( A, N, 1000 * eps )

%!xtest
%! A = exprnd ( 1, 100 );
%! N = normalise_distribution ( A, @ ( x ) ( expcdf ( x, 1 ) ) );
%! assert ( mean ( vec ( N ) ), 0, 0.1 )
%! assert ( std ( vec ( N ) ), 1, 0.1 )

%!xtest
%! A = rand (1000,1);
%! N = normalise_distribution  ( A, "unifcdf" );
%! assert ( mean ( vec ( N ) ), 0, 0.1 )
%! assert ( std ( vec ( N ) ), 1, 0.1 )

%!xtest 
%! A = [rand(1000,1), randn( 1000, 1)];
%! N = normalise_distribution  ( A, { "unifcdf", "normcdf" } );
%! assert ( mean ( N ), [ 0, 0 ], 0.1 )
%! assert ( std ( N ), [ 1, 1 ], 0.1 )

%!xtest
%! A = [rand(1000,1), randn( 1000, 1), exprnd( 1, 1000, 1 )]';
%! N = normalise_distribution  ( A, { @unifcdf; @normcdf;  @( x )( expcdf ( x, 1 ) ) }, 2 );
%! assert ( mean ( N, 2 ), [ 0, 0, 0 ]', 0.1 )
%! assert ( std ( N, [], 2 ), [ 1, 1, 1 ]', 0.1 )

%!xtest
%! A = exprnd ( 1, 1000, 9 ); A ( 300 : 500, 4:6 ) = 17;
%! N = normalise_distribution ( A );
%! assert ( mean ( N ), [ 0 0 0 0.38 0.38 0.38 0 0 0 ], 0.1 );
%! assert ( var ( N ), [ 1 1 1 2.59 2.59 2.59 1 1 1 ], 0.1 );

%!test
%! fail ("normalise_distribution( zeros ( 3, 4 ), { @unifcdf; @normcdf; @( x )( expcdf ( x, 1 ) ) } )", ...
%! "Number of distributions does not match data size!");
