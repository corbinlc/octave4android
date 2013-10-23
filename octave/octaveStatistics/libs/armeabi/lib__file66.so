## Copyright (C) 2011 Kyle Winfree <kyle.winfree@gmail.com>
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
## @deftypefn {Function File} {[@var{pval}, @var{table}, @var{st}] =} repanova (@var{X}, @var{cond})
## @deftypefnx {Function File} {[@var{pval}, @var{table}, @var{st}] =} repanova (@var{X}, @var{cond}, ['string' | 'cell'])
## Perform a repeated measures analysis of variance (Repeated ANOVA).
## X is formated such that each row is a subject and each column is a condition.
##
## condition is typically a point in time, say t=1 then t=2, etc
## condition can also be thought of as groups.
##
## The optional flag can be either 'cell' or 'string' and reflects
## the format of the table returned.  Cell is the default.
##
## NaNs are ignored using nanmean and nanstd.
## 
## This fuction does not currently support multiple columns of the same
## condition!
## @end deftypefn

function [p, table, st] = repanova(varargin)

switch nargin
     case 0
	  error('Too few inputs.');
     case 1
		X = varargin{1};
		for c = 1:size(X, 2)
			condition{c} = ['time', num2str(c)];
		end
		option = 'cell';
     case 2
	  X = varargin{1};
	  condition = varargin{2};
	  option = 'cell';
     case 3
	  X = varargin{1};
	  condition = varargin{2};
	  option = varargin{3};
     otherwise
	  error('Too many inputs.');
end
     % Find the means of the subjects and measures, ignoring any NaNs
     u_subjects = nanmean(X,2);
     u_measures = nanmean(X,1);
     u_grand = nansum(nansum(X)) / (size(X,1) * size(X,2));
     % Differences between rows will be reflected in SS subjects, differences
     % between columns will be reflected in SS_within subjects.
     N = size(X,1); % number of subjects
     J = size(X,2); % number of samples per subject
     SS_measures = N * nansum((u_measures - u_grand).^2);
     SS_subjects = J * nansum((u_subjects - u_grand).^2);
     SS_total = nansum(nansum((X - u_grand).^2));
     SS_error = SS_total - SS_measures - SS_subjects;
     df_measures = J - 1;
     df_subjects = N - 1;
     df_grand = (N*J) - 1;
     df_error = df_grand - df_measures - df_subjects;
     MS_measures = SS_measures / df_measures;
     MS_subjects = SS_subjects / df_subjects;
     MS_error = SS_error / df_error; % variation expected as a result of sampling error alone
     F = MS_measures / MS_error;
     p = 1 - fcdf(F, df_measures, df_error); % Probability of F given equal means.

     if strcmp(option, 'string')
	  table  = [sprintf('\nSource\tSS\tdf\tMS\tF\tProb > F'), ...
		    sprintf('\nSubject\t%g\t%i\t%g', SS_subjects, df_subjects, MS_subjects), ...
		    sprintf('\nMeasure\t%g\t%i\t%g\t%g\t%g', SS_measures, df_measures, MS_measures, F, p), ...
		    sprintf('\nError\t%g\t%i\t%g', SS_error, df_error, MS_error), ...
		    sprintf('\n')];
     else
	  table  = {'Source', 'Partial SS', 'df', 'MS', 'F', 'Prob > F'; ...
		    'Subject', SS_subjects, df_subjects, MS_subjects, '', ''; ...
		    'Measure', SS_measures, df_measures, MS_measures, F, p};
     end

     st.gnames = condition'; % this is the same struct format used in anova1
     st.n = repmat(N, 1, J);
     st.source = 'anova1'; % it cannot be assumed that 'repanova' is a supported source for multcompare
     st.means = u_measures;
     st.df = df_error;
     st.s = sqrt(MS_error);
end

% This function was created with guidance from the following websites:
% http://courses.washington.edu/stat217/rmANOVA.html
% http://grants.hhp.coe.uh.edu/doconnor/PEP6305/Topic%20010%20Repeated%20Measures.htm
