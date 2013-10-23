%% Copyright (C) 2011, Bilen Oytun Peksel <oytun.peksel@gmail.com>
%% All rights reserved.
%%
%% Redistribution and use in source and binary forms, with or without
%% modification, are permitted provided that the following conditions are met:
%%
%%    1 Redistributions of source code must retain the above copyright notice,
%%      this list of conditions and the following disclaimer.
%%    2 Redistributions in binary form must reproduce the above copyright
%%      notice, this list of conditions and the following disclaimer in the
%%      documentation and/or other materials provided with the distribution.
%%
%% THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS ''AS IS''
%% AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
%% IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
%% ARE DISCLAIMED. IN NO EVENT SHALL THE AUTHOR OR CONTRIBUTORS BE LIABLE FOR
%% ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
%% DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
%% SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
%% CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
%% OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
%% OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

%% -*- texinfo -*-
%% @deftypefn {Function File} {@var{data} =} pch2mat (@var{filename})
%% Converts NASTRAN PCH file (SORT2) to a data structure and frequency vector. A
%% filename as a string is the only needed input.
%%
%% The output is in the form of struct. containing a freq vector n x 1 called
%% data.f, and the remaining data are in the form of subcases, point ids
%% and directions respectively. for ex. data.S1.p254686.x and they are n x 2
%%
%% @end deftypefn

function [data] = pch2mat(filename);

    %% Open the file and read the file line by line to form a line character array
    fid = fopen(filename);
    l = 1;
    while (~feof(fid))
        raw{l} = fgets(fid);
        l      = l + 1;
    end

    %% Determine Freq_count and number of lines for each case
    a          = strmatch('$TITLE',raw);
    lines      = a(2) -a(1); %number of lines on each subcase and related point id
    freq_count = (lines-7)/4;

    %% Read from array
    C = length(raw) / lines; %number of subcase and related point id

    for k = 1 : C; %looping through every case
        scase = char(raw{(k-1) * lines + 3}(12:16));
        scase = genvarname(scase);
        pid   = char(raw{(k-1) * lines + 7}(16:25));
        pid   = genvarname(['p' pid]);
        if (k==1)
            data.f = zeros(freq_count,1);
        end;
        data.(scase).(pid).x  = zeros(freq_count,2);
        data.(scase).(pid).y  = zeros(freq_count,2);
        data.(scase).(pid).z  = zeros(freq_count,2);
        data.(scase).(pid).r1 = zeros(freq_count,2);
        data.(scase).(pid).r2 = zeros(freq_count,2);
        data.(scase).(pid).r3 = zeros(freq_count,2);
        i = (k-1) * lines + 8 ;
        j = 0;

        while( i <= (lines * k)) %loop for each case
            j=j+1;
            if (k==1)
                data.f(j) = str2double(raw{i}(5:17));
            end
            data.(scase).(pid).x(j,1)  = str2double(raw{i}(25:37));
            data.(scase).(pid).y(j,1)  = str2double(raw{i}(43:55));
            data.(scase).(pid).z(j,1)  = str2double(raw{i}(61:73));
            i=i+1;
            data.(scase).(pid).r1(j,1) = str2double(raw{i}(25:37));
            data.(scase).(pid).r2(j,1) = str2double(raw{i}(43:55));
            data.(scase).(pid).r3(j,1) = str2double(raw{i}(61:73));
            i=i+1;
            data.(scase).(pid).x(j,2)  = str2double(raw{i}(25:37));
            data.(scase).(pid).y(j,2)  = str2double(raw{i}(43:55));
            data.(scase).(pid).z(j,2)  = str2double(raw{i}(61:73));
            i=i+1;
            data.(scase).(pid).r1(j,2) = str2double(raw{i}(25:37));
            data.(scase).(pid).r2(j,2) = str2double(raw{i}(43:55));
            data.(scase).(pid).r3(j,2) = str2double(raw{i}(61:73));
            i=i+1;
        end
    end
end
