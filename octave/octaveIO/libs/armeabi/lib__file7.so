% Check Octave / Matlab environment for spreadsheet I/O support.
%
%   usage:  [ RETVAL ] = chk_spreadsheet_support ( [/PATH/TO/JARS], [,DEBUG_LEVEL] [,PATH_TO_OOO])
%
% CHK_SPREADSHEET_SUPPORT first checks ActiveX (native MS-Excel); then
% Java JRE presence, then Java support (builtin/activated (Matlab) or
% added tru octave-forge Java package (Octave); then check existing
% javaclasspath for Java class libraries (.jar) needed for various
% Java-based spreadsheet I/O interfaces.
% If desired the relevant classes can be added to the dynamic
% javaclasspath. In that case the path name to the directory 
% containing these classes should be specified as input argument
% with -TAKE NOTICE- /forward/ slashes. In these jars reside in
% different directories, multiple calls to chk_spreadsheet_support
% can be made.
%
%     Input arguments (all are optional, but the order is important):
% /PATH/TO/JARS = (string) path (relative or absolute) to a
%                 subdirectory where java class libraries (.jar)
%                 for spreadsheet I/O reside. Can be [] or ''
% DEBUG_LEVEL   = (integer) between [0 (no output) .. 3 (full output]
% PATH_TO_OOO   = (string) installation directory of Openffice.org,
%                 usually (but not guaranteed):
%                 - Windows: C:\Program Files\OpenOffice.org
%                 - *nix:    /usr/lib/ooo
%                 - Mac OSX: ?????
%                 IMPORTANT: PATH_TO_OOO should be such that both:
%                 1. PATH_TO_OOO/program/       
%                  and
%                 2. PATH_TO_OOO/ure/.../ridl.jar
%                 resolve OK
%     Returns:
% RETVAL        =  0 No spreadsheet I/O support found
%               <> 0 At least one spreadsheet I/O interface found. RETVAL
%                  RETVAL will be set to the sum of values for found interfaces:
%                  ---------- XLS (Excel) interfaces: ----------
%                    1 = COM (ActiveX / Excel)
%                    2 = POI (Java / Apache POI)
%                    4 = POI+OOXML (Java / Apache POI)
%                    8 = JXL (Java / JExcelAPI)
%                   16 = OXS (Java / OpenXLS)
%                  --- ODS (OpenOffice.org Calc) interfaces ----
%                   32 = OTK (Java/ ODF Toolkit)
%                   64 = JOD (Java / jOpenDocument)
%                  ----------------- XLS & ODS: ----------------
%                  128 = UNO (Java / UNO bridge - OpenOffice.org)

function  [ retval ]  = chk_spreadsheet_support (path_to_jars, dbug, path_to_ooo)

% Copyright (C) 2009,2010,2011 Philip Nienhuis <prnienhuis at users.sf.net>
%
% This program is free software; you can redistribute it and/or modify it under
% the terms of the GNU General Public License as published by the Free Software
% Foundation; either version 3 of the License, or (at your option) any later
% version.
%
% This program is distributed in the hope that it will be useful, but WITHOUT
% ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
% FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
% details.
%
% You should have received a copy of the GNU General Public License along with
% this program; if not, see <http://www.gnu.org/licenses/>.


% Author: Philip Nienhuis
% Created 2010-11-03 for Octave & Matlab
% Updates:
% 2010-12-19 Found that dom4j-1.6.1.jar is needed regardless of ML's dom4j
%            presence in static classpath (ML r2007a)
% 2011-01-04 Adapted for general checks, debugging & set up, both Octave & ML
% 2011-04-04 Rebuilt into general setup/debug tool for spreadsheet I/O support
%            and renamed chk_spreadsheet_support()
% 2011-05-04 Added in UNO support (OpenOffice.org & clones)
%     ''     Improved finding jar names in javaclasspath
% 2011-05-07 Improved help text
% 2011-05-15 Better error msg if OOo instal dir isn't found
% 2011-05-20 Attempt to cope with case variations in subdir names of OOo install dir (_get_dir_)
% 2011-05-27 Fix proper return value (retval); header text improved
% 2011-05-29 Made retval value dependent on detected interfaces & adapted help text
% 2011-06-06 Fix for javaclasspath format in *nix w. octave-java-1.2.8 pkg
%     ''     Fixed wrong return value update when adding UNO classes
% 2011-09-03 Small fix to better detect Basis* subdir when searching unoil.jar
% 2011-09-18 Fixed 'Matlab style short circuit' warning in L. 152
% 2012-12-24 Amended code stanze to find unoil.jar; now works in LibreOffice 3.5b2 as well
% 2012-06-07 Replaced all tabs by double space
% 2012-06-24 Replaced error msg by printf & return
%     ''     Added Java pkg inquiry (Octave) before attempting javaclasspath()
%     ''     Updated check for odfdom version (now supports 0.8.8)
% 2012-10-07 Moved common classpath entry code to private function
% 2012-12-21 POI 3.9 support (w. either xmlbeans.jar or xbeans.jar)

  jcp = []; retval = 0;
  if (nargin < 3); path_to_ooo= ''; end %if
    if (nargin < 2); dbug = 0; end %if
  isOctave = exist ('OCTAVE_VERSION', 'builtin') ~= 0;
    if (dbug); fprintf ('\n'); end %if
  % interfaces = {'COM', 'POI', 'POI+OOXML', 'JXL', 'OXS', 'OTK', 'JOD', 'UNO'}; % Order  = vital

  % Check if MS-Excel COM ActiveX server runs
  if (dbug), fprintf ('Checking Excel/ActiveX/COM... '); end %if
  try
    app = actxserver ('Excel.application');
    % If we get here, the call succeeded & COM works.
    xlsinterfaces.COM = 1;
    % Close Excel to avoid zombie Excel invocation
    app.Quit();
    delete(app);
    if (dbug), fprintf ('OK.\n\n'); end %if
    retval = retval + 1;
  catch
    % COM not supported
    if (dbug), fprintf ('not working.\n\n'); end %if
  end %try_catch

    % Check Java
  if (dbug), fprintf ('Checking Java support...\n'); end %if
  if (dbug > 1), fprintf ('  1. Checking Java JRE presence.... '); end %if
  % Try if Java is installed at all
  if (isOctave)
    if (ispc)
      jtst = (system ('java -version 2> nul'));
    else
      jtst = (system ('java -version 2> /dev/null'));
    end %if
  else
    tst1 = version ('-java');
    jtst = isempty (strfind (tst1, 'Java'));
  end %if
  if (jtst)
    printf ('Apparently no Java JRE installed.\n');
    return;
  else
    if (dbug > 1), fprintf ('OK, found one.\n'); end %if
  end %if
  if (dbug > 1 && isOctave), fprintf ('  2. Checking Octave Java support... '); end %if
  try
    if (isOctave)
      % Check Java package
      [~, b] = pkg ('describe', 'java');
      if    (strcmpi (b{:}, 'Not loaded'))
        if (dbug > 1); printf ('Java package not loaded. First do: "pkg load java"\n'); end %if
      elseif strcmpi (b{:}, 'Not installed')
        if (dbug > 1); printf ('Java package is not installed.\n'); end %if
      endif
    end %if
    jcp = javaclasspath ('-all');            % For Octave java pkg > 1.2.7
    if (isempty (jcp)), jcp = javaclasspath; end %if  % For Octave java pkg < 1.2.8
    % If we get here, at least Java works.
    if (dbug > 1 && isOctave), fprintf ('Java package seems to work OK.\n'); end %if
    % Now check for proper version (> 1.6.x.x)
    jver = char (javaMethod ('getProperty', 'java.lang.System', 'java.version'));
    cjver = strsplit (jver, '.');
    if (sscanf (cjver{2}, '%d') < 6)
      if (dbug)
        fprintf ('  Java version (%s) too old - you need at least Java 6 (v. 1.6.x.x)\n', jver);
        if (isOctave)
          warning ('    At Octave prompt, try "!system ("java -version")"'); 
        else
          warning ('    At Matlab prompt, try "version -java"');
        end %if
      end %if
      return
    else
      if (dbug > 2), fprintf ('  Java (version %s) seems OK.\n', jver); end %if
    end %if
    % Under *nix the classpath must first be split up.
    % Matlab is braindead here. For ML we need a replacement for Octave's builtin strsplit()
    % This is found on ML Central (BSD license so this is allowed) & adapted for input arg order
    if (isunix && ~iscell (jcp)); jcp = strsplit (char (jcp), ':'); end %if
    if (dbug > 1)
      % Check JVM virtual memory settings
      jrt = javaMethod ('getRuntime', 'java.lang.Runtime');
      jmem = jrt.maxMemory ();
            if (isOctave), jmem = jmem.doubleValue(); end %if
      jmem = int16 (jmem/1024/1024);
      fprintf ('  Maximum JVM memory: %5d MiB; ', jmem);
      if (jmem < 400)
        fprintf ('should better be at least 400 MB!\n');
        fprintf ('    Hint: adapt setting -Xmx in file "java.opts" (supposed to be here:)\n');
        if (isOctave)
          fprintf ('    %s\n', [matlabroot filesep 'share' filesep 'octave' filesep 'packages' filesep 'java-<version>' filesep 'java.opts']);
        else
          fprintf ('    $matlabroot/bin/<arch>]\n');
        end %if
      else
        fprintf ('sufficient.\n');
      end %if
    end %if
    if (dbug), fprintf ('Java support OK\n'); end %if
  catch
    printf ('No Java support found.\n');
    return
  end %try_catch

  if (dbug), fprintf ('\nChecking javaclasspath for .jar class libraries needed for spreadsheet I/O...:\n'); end %if

  % Try Java & Apache POI. First Check basic .xls (BIFF8) support
  if (dbug > 1), fprintf ('\nBasic POI (.xls) <poi-3> <poi-ooxml>:\n'); end %if
  entries1 = {'poi-3', 'poi-ooxml-3'}; missing1 = zeros (1, numel (entries1));
  % Only under *nix we might use brute force: e.g., strfind (javaclasspath, classname)
  % as javaclasspath is one long string. Under Windows however classpath is a cell array
  % so we need the following more subtle, platform-independent approach:
  [jpchk1, missing1] = chk_jar_entries (jcp, entries1, dbug);
  if (jpchk1 >= numel (entries1)), retval = retval + 2; end %if
  if (dbug > 1)
    if (jpchk1 >= numel (entries1))
      fprintf ('  => Apache (POI) OK\n');
    else
      fprintf ('  => Not all classes (.jar) required for POI in classpath\n');
    end %if
  end %if
  % Next, check OOXML support
  if (dbug > 1), fprintf ('\nPOI OOXML (.xlsx) <xbean/xmlbean> <poi-ooxml-schemas> <dom4j>:\n'); end %if
  entries2 = {{'xbean', 'xmlbean'}, 'poi-ooxml-schemas', 'dom4j'}; 
  % Only update retval if all classes for basic POI have been found in javaclasspath
  [jpchk2, missing2] = chk_jar_entries (jcp, entries2, dbug);
  if (jpchk1 >= numel (entries1) && jpchk2 >= numel (entries2)), retval = retval + 4; end %if
  if (dbug > 1)
    if (jpchk2 >= numel (entries2)) 
      fprintf ('  => POI OOXML OK\n');
    else
      fprintf ('  => Some classes for POI OOXML support missing\n'); 
    end %if
  end %if

  % Try Java & JExcelAPI
  if (dbug > 1), fprintf ('\nJExcelAPI (.xls (incl. BIFF5 read)) <jxl>:\n'); end %if
  entries3 = {'jxl'}; missing3 = zeros (1, numel (entries3));
  [jpchk, missing3] = chk_jar_entries (jcp, entries3, dbug);
  if (jpchk >= numel (entries3)), retval = retval + 8; end %if
  if (dbug > 1)
    if (jpchk >= numel (entries3))
      fprintf ('  => Java/JExcelAPI (JXL) OK.\n');
    else
      fprintf ('  => Not all classes (.jar) required for JXL in classpath\n');
    end %if
  end %if

  % Try Java & OpenXLS
  if (dbug > 1), fprintf ('\nOpenXLS (.xls (BIFF8)) <OpenXLS>:\n'); end %if
  entries4 = {'OpenXLS'}; missing4 = zeros (1, numel (entries4));
  [jpchk, missing4] = chk_jar_entries (jcp, entries4, dbug);
  if (jpchk >= numel (entries4)), retval = retval + 16; end %if
  if (dbug > 1)
    if (jpchk >= numel (entries4))
      fprintf ('  => Java/OpenXLS (OXS) OK.\n');
    else
      fprintf ('  => Not all classes (.jar) required for OXS in classpath\n');
    end %if
  end %if

  % Try Java & ODF toolkit
  if (dbug > 1), fprintf ('\nODF Toolkit (.ods) <odfdom> <xercesImpl>:\n'); end %if
  entries5 = {'odfdom', 'xercesImpl'}; missing5 = zeros (1, numel (entries5));
  [jpchk, missing5] = chk_jar_entries (jcp, entries5, dbug);
  if (jpchk >= numel (entries5))    % Apparently all requested classes present.
    % Only now we can check for proper odfdom version (only 0.7.5 & 0.8.6 work OK).
    % The odfdom team deemed it necessary to change the version call so we need this:
    odfvsn = ' ';
    try
      % New in 0.8.6
      odfvsn = javaMethod ('getOdfdomVersion', 'org.odftoolkit.odfdom.JarManifest');
    catch
      % Worked in 0.7.5
      odfvsn = javaMethod ('getApplicationVersion', 'org.odftoolkit.odfdom.Version');
    end %try_catch
    if ~(strcmp (odfvsn, '0.7.5') || strcmp (odfvsn, '0.8.6') || strcmp (odfvsn, '0.8.7')
      || ~isempty (strfind (odfvsn, '0.8.8')))
      warning ('  *** odfdom version (%s) is not supported - use v. 0.8.6 or newer\n', odfvsn);
    else  
      if (dbug > 1), fprintf ('  => ODFtoolkit (OTK) OK.\n'); end %if
      retval = retval + 32;
    end %if
    elseif (dbug > 1)
    fprintf ('  => Not all required classes (.jar) in classpath for OTK\n');
  end %if

  % Try Java & jOpenDocument
  if (dbug > 1), fprintf ('\njOpenDocument (.ods + experimental .sxc readonly) <jOpendocument>:\n'); end %if
  entries6 = {'jOpenDocument'}; missing6 = zeros (1, numel (entries6));
  [jpchk, missing6] = chk_jar_entries (jcp, entries6, dbug);
  if (jpchk >= numel (entries6)), retval = retval + 64; end %if
  if (dbug > 1)
    if (jpchk >= numel(entries6))
      fprintf ('  => jOpenDocument (JOD) OK.\n');
    else
      fprintf ('  => Not all required classes (.jar) in classpath for JOD\n');
    end %if
  end %if

  % Try Java & UNO
  if (dbug > 1), fprintf ('\nUNO/Java (.ods, .xls, .xlsx, .sxc) <OpenOffice.org>:\n'); end %if
  % entries0(1) = not a jar but a directory (<000_install_dir/program/>)
  entries0 = {'program', 'unoil', 'jurt', 'juh', 'unoloader', 'ridl'};
  [jpchk, missing0] = chk_jar_entries (jcp, entries0, dbug);
  if (jpchk >= numel (entries0)), retval = retval + 128; end %if
  if (dbug > 1)
    if (jpchk >= numel (entries0))
      fprintf ('  => UNO (OOo) OK\n');
    else
      fprintf ('  => One or more UNO classes (.jar) missing in javaclasspath\n');
    end %if
  end %if

  % If requested, try to add UNO stuff to javaclasspath
  ujars_complete = isempty (find (missing0, 1));

  if (~ujars_complete && nargin > 0 && ~isempty (path_to_ooo))
    if (dbug), fprintf ('\nTrying to add missing UNO java class libs to javaclasspath...\n'); end %if
    if (~ischar (path_to_jars)), printf ('Path expected for arg # 1\n'); return; end %if
    % Add missing jars to javaclasspath. First combine all entries
    targt = sum (missing0);
    if (missing0(1))
      % Add program dir (= where soffice or soffice.exe or ooffice resides)
      programdir = [path_to_ooo filesep entries0{1}];
      if (exist (programdir, 'dir'))
        if (dbug > 2), fprintf ('  Found %s, adding it to javaclasspath ... ', programdir); end %if
        try
          javaaddpath (programdir);
          targt = targt - 1;
          if (dbug > 2), fprintf ('OK\n'); end %if
        catch
          if (dbug > 2), fprintf ('FAILED\n'); end %if
        end %try_catch
      else
        if (dbug > 2)
          printf ('Suggested OpenOffice.org install directory: %s not found!\n', path_to_ooo); 
          return
        end %if
      end %if
    end %if
    % Rest of missing entries. Find where URE is located. Watch out because case of ./ure is unknown
    uredir = get_dir_ (path_to_ooo, 'ure');
    if (isempty (uredir)), return; end %if
    % Now search for UNO jars
    for ii=2:length (entries0)
      if (missing0(ii))
        if (ii == 2)
          % Special case as unoil.jar usually resides in ./Basis<something>/program/classes
          % Find out the exact name of Basis.....
          basisdirlst = dir ([path_to_ooo filesep '?asis' '*']);
          jj = 1;
          if (numel (basisdirlst) > 0) 
            while (jj <= size (basisdirlst, 1) && jj > 0)
              basisdir = basisdirlst(jj).name;
              if (basisdirlst(jj).isdir)
                basisdir = basisdirlst(jj).name;
                jj = 0;
              else
                jj = jj + 1;
              end %if
            end %while
            basisdir = [path_to_ooo filesep basisdir ];
          else
            basisdir = path_to_ooo;
          endif
          basisdirentries = {'program', 'classes'};
          tmp = basisdir; jj=1;
          while (~isempty (tmp) && jj <= numel (basisdirentries))
            tmp = get_dir_ (tmp, basisdirentries{jj});
            jj = jj + 1;
          end %if
          unojarpath = tmp;
          file = dir ([ unojarpath filesep entries0{2} '*' ]);
        else
          % Rest of jars in ./ure/share/java or ./ure/java
          unojardir = get_dir_ (uredir, 'share');
          if (isempty (unojardir))
            tmp = uredir;
          else
            tmp = unojardir;
          end %if
          unojarpath = get_dir_ (tmp, 'java');
          file = dir ([unojarpath filesep entries0{ii} '*']);
        end %if
        % Path found, now try to add jar
        if (isempty (file))
          if (dbug > 2), fprintf ('  ? %s<...>.jar ?\n', entries0{ii}); end %if
        else
          if (dbug > 2), fprintf ('  Found %s, adding it to javaclasspath ... ', file.name); end %if
          try
            javaaddpath ([unojarpath filesep file.name]);
            targt = targt - 1;
            if (dbug > 2), fprintf ('OK\n'); end %if
          catch
            if (dbug > 2), fprintf ('FAILED\n'); end %if
                    end %try_catch
        end %if
      end %if
    end %for
    if (~targt); retval = retval + 128; end %if
    if (dbug)
      if (targt)
        fprintf ('Some UNO class libs still lacking...\n\n'); 
      else
        fprintf ('UNO interface supported now.\n\n');
      end %if
    end %f
  end %if

% ----------Rest of Java interfaces----------------------------------

  missing = [missing1 missing2 missing3 missing4 missing5 missing6];
  jars_complete = isempty (find (missing, 1));
  if (dbug)
    if (jars_complete)
      fprintf ('All Java-based interfaces (save UNO) fully supported.\n\n');
    else
      fprintf ('Some class libs lacking yet...\n\n'); 
    end %if
  end %if

  if (~jars_complete && nargin > 0 && ~isempty (path_to_jars))
    % Add missing jars to javaclasspath. Assume they're all in the same place
    if (dbug), fprintf ('Trying to add missing java class libs to javaclasspath...\n'); end %if
    if (~ischar (path_to_jars)), printf ('Path expected for arg # 1\n'); return; end %if
    % First combine all entries
    targt = sum (missing);
    % For each interface, search tru list of missing entries
    for ii=1:6   % Adapt number in case of future new interfaces
      tmpe = eval ([ 'entries' char(ii + '0') ]);
      tmpm = eval ([ 'missing' char(ii + '0') ]);
      if (sum (tmpm))
        for jj=1:numel (tmpe)
          if (tmpm(jj))
            if (iscellstr (tmpe{jj}))
              rtval = 0; kk = 1;
              while (kk <= numel (tmpe{jj}) && ! rtval)
                jtmpe = tmpe{jj}{kk};
                rtval = add_jars_to_jcp (path_to_jars, jtmpe, dbug);
                ++kk;
              end %while
            else
              rtval = add_jars_to_jcp (path_to_jars, tmpe{jj}, dbug);
            end %if
            if (rtval)
              targt = targt - rtval;
              tmpm(jj) = 0;
            end %if
          end %if
        end %for
        if (~sum (tmpm))
          retval = retval + 2^ii;
        end %if
      end %if
    end %for
    if (dbug)
      if (targt)
        fprintf ('Some other class libs still lacking...\n\n');
      else
        fprintf ('All Java-based interfaces fully supported.now.\n\n');
      end %if
    end %f
  end %if

end %function


function [ ret_dir ] = get_dir_ (base_dir, req_dir)

% Construct path to subdirectory req_dir in a subdir tree, aimed
% at taking care of proper case (esp. for *nix) of existing subdir
% in the result. Case of input var req_dir is ignored on purpose.

  ret_dir = '';
  % Get list of directory entries
  ret_dir_list = dir (base_dir);
  % Find matching entries
  idx = find (strcmpi ({ret_dir_list.name}, req_dir));
  % On *nix, several files and subdirs in one dir may have the same name as long as case differs
  if (~isempty (idx))
    ii = 1;
    while (~ret_dir_list(idx(ii)).isdir)
      ii = ii + 1;
      if (ii > numel (idx)); return; end %if
    end %while
    % If we get here, a dir with proper name has been found. Construct path
    ret_dir = [ base_dir filesep  ret_dir_list(idx(ii)).name ];
  end %if

end %function


function [ retval ] = add_jars_to_jcp (path_to_jars, jarname, dbug)

% Given a subdirectory path and a (sufficiently unique part of a) Java class
% lib file (.jar), checks if it can find the file in the subdir and tries to
% add it to the javaclasspath

  retval = 0;
  file = dir ([path_to_jars filesep jarname '*']);  %%% FIXME mult_jar
  if (isempty (file))
    if (dbug > 2), fprintf ('  ? %s<...>.jar ?\n', jarname); end %if
  else
    if (dbug > 2), fprintf ('  Found %s, adding it to javaclasspath ... ', file(1).name); end %if
    try
      javaaddpath ([path_to_jars filesep file(1).name]);
      if (dbug > 2), fprintf ('OK\n'); end %if
      retval = 1;
    catch
      if (dbug > 2), fprintf ('FAILED\n'); end %if
    end %try_catch
  end %if

end %function