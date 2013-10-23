%% Author: Petr Mikulik (2009)
%% This program is granted to the public domain.

%% Compute peak full-width at half maximum (FWHM) or at another level of peak
%% maximum for vector or matrix data y, optionally sampled as y(x). If y is
%% a matrix, return FWHM for each column as a row vector.
%%   Syntax:
%%      f = fwhm({x, } y {, 'zero'|'min' {, 'rlevel', rlevel}})
%%      f = fwhm({x, } y {, 'alevel', alevel})
%%   Examples:
%%      f = fwhm(y)
%%      f = fwhm(x, y)
%%      f = fwhm(x, y, 'zero')
%%      f = fwhm(x, y, 'min')
%%      f = fwhm(x, y, 'alevel', 15.3)
%%      f = fwhm(x, y, 'zero', 'rlevel', 0.5)
%%      f = fwhm(x, y, 'min',  'rlevel', 0.1)
%%
%% The default option 'zero' computes fwhm at half maximum, i.e. 0.5*max(y).
%% The option 'min' computes fwhm at the middle curve, i.e. 0.5*(min(y)+max(y)).
%%
%% The option 'rlevel' computes full-width at the given relative level of peak
%% profile, i.e. at rlevel*max(y) or rlevel*(min(y)+max(y)), respectively.
%% For example, fwhm(..., 'rlevel', 0.1) computes full width at 10 % of peak
%% maximum with respect to zero or minimum; FWHM is equivalent to
%% fwhm(..., 'rlevel', 0.5).
%%
%% The option 'alevel' computes full-width at the given absolute level of y.
%%
%% Return 0 if FWHM does not exist (e.g. monotonous function or the function
%% does not cut horizontal line at rlevel*max(y) or rlevel*(max(y)+min(y)) or
%% alevel, respectively).
%%
%% Compatibility: Octave 3.x, Matlab

function myfwhm = fwhm (y, varargin)

    if nargin < 1 || nargin > 5
        print_usage;
    end
    opt = 'zero';
    is_alevel = 0;
    level = 0.5;
    if nargin==1
        x = 1:length(y);
    else
        if ischar(varargin{1})
            x = 1:length(y);
            k = 1;
        else
            x = y;
            y = varargin{1};
            k = 2;
        end
        while k <= length(varargin)
            if strcmp(varargin{k}, 'alevel')
                is_alevel = 1;
                k = k+1;
                if k > length(varargin)
                    error('option "alevel" requires an argument');
                end
                level = varargin{k};
                if ~isreal(level) || length(level) > 1
                    error('argument of "alevel" must be real number');
                end
                k = k+1;
                break
            end
            if any(strcmp(varargin{k}, {'zero', 'min'}))
                opt = varargin{k};
                k = k+1;
            end
            if k > length(varargin) break; end
            if strcmp(varargin{k}, 'rlevel')
                k = k+1;
                if k > length(varargin)
                    error('option "rlevel" requires an argument');
                end
                level = varargin{k};
                if ~isreal(level) || length(level) > 1 || level(1) < 0 || level(:) > 1
                    error('argument of "rlevel" must be real number from 0 to 1 (it is 0.5 for fwhm)');
                end
                k = k+1;
                break
            end
            break
        end
        if k ~= length(varargin)+1
            error('fwhm: extraneous option(s)');
        end
    end

    % test the y matrix
    [nr, nc] = size(y);
    if (nr == 1 && nc > 1)
        y = y'; nr = nc; nc = 1;
    end

    if length(x) ~= nr
        error('dimension of input arguments do not match');
    end

    % Shift matrix columns so that y(+-xfwhm) = 0:
    if is_alevel
            % case: full-width at the given absolute position
            y = y - level;
    else
        if strcmp(opt, 'zero')
            % case: full-width at half maximum
            y = y - level * repmat(max(y), nr, 1);
        else
            % case: full-width above background
            y = y - level * repmat((max(y) + min(y)), nr, 1);
        end
    end

    % Trial for a "vectorizing" calculation of fwhm (i.e. all
    % columns in one shot):
    % myfwhm = zeros(1,nc); % default: 0 for fwhm undefined
    % ind = find (y(1:end-1, :) .* y(2:end, :) <= 0);
    % [r1,c1] = ind2sub(size(y), ind);
    % ... difficult to proceed further.
    % Thus calculate fwhm for each column independently:
    myfwhm = zeros(1,nc); % default: 0 for fwhm undefined
    for n=1:nc
        yy = y(:, n);
        ind = find((yy(1:end-1) .* yy(2:end)) <= 0);
        if length(ind) >= 2 && yy(ind(1)) > 0 % must start ascending
            ind = ind(2:end);
        end
        [mx, imax] = max(yy); % protection against constant or (almost) monotonous functions
        if length(ind) >= 2 && imax >= ind(1) && imax <= ind(end)
            ind1 = ind(1);
            ind2 = ind1 + 1;
            xx1 = x(ind1) - yy(ind1) * (x(ind2) - x(ind1)) / (yy(ind2) - yy(ind1));
            ind1 = ind(end);
            ind2 = ind1 + 1;
            xx2 = x(ind1) - yy(ind1) * (x(ind2) - x(ind1)) / (yy(ind2) - yy(ind1));
            myfwhm(n) = xx2 - xx1;
        end
    end
end

%!test
%! x=-pi:0.001:pi; y=cos(x);
%! assert( abs(fwhm(x, y) - 2*pi/3) < 0.01 );
%!
%!test
%! assert( fwhm(-10:10) == 0 && fwhm(ones(1,50)) == 0 );
%!
%!test
%! x=-20:1:20;
%! y1=-4+zeros(size(x)); y1(4:10)=8;
%! y2=-2+zeros(size(x)); y2(4:11)=2;
%! y3= 2+zeros(size(x)); y3(5:13)=10;
%! assert( max(abs(fwhm(x, [y1;y2;y3]') - [20.0/3,7.5,9.25])) < 0.01 );
%!
%!test
%! x=1:3; y=[-1,3,-1]; assert(abs(fwhm(x,y)-0.75)<0.001 && abs(fwhm(x,y,'zero')-0.75)<0.001 && abs(fwhm(x,y,'min')-1.0)<0.001);
%!
%!test
%! x=1:3; y=[-1,3,-1]; assert(abs(fwhm(x,y, 'rlevel', 0.1)-1.35)<0.001 && abs(fwhm(x,y,'zero', 'rlevel', 0.1)-1.35)<0.001 && abs(fwhm(x,y,'min', 'rlevel', 0.1)-1.40)<0.001);
%!
%!test
%! x=1:3; y=[-1,3,-1]; assert(abs(fwhm(x,y, 'alevel', 2.5)-0.25)<0.001 && abs(fwhm(x,y,'alevel', -0.5)-1.75)<0.001);
%!
%!test
%! x=-10:10; assert( fwhm(x.*x) == 0 );
%!
%!test
%! x=-5:5; y=18-x.*x; assert( abs(fwhm(y)-6.0) < 0.001 && abs(fwhm(x,y,'zero')-6.0) < 0.001 && abs(fwhm(x,y,'min')-7.0 ) < 0.001);
