## Copyright (C) 2004 Laurent Mazet <mazet@crm.mot.com>
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
## @deftypefn {Function File} {@var{nb} =} xmlwrite (@var{filename}, @var{value})
## @deftypefnx {Function File} {@var{nb} =} xmlwrite (@var{fd}, @var{value}, [@var{name}])
##
## Write a @var{value} into @var{filename} (@var{fd}) as an XML file.
##
##The number of elements (@var{nb}) or 0 is returned.
## @end deftypefn

function nb = xmlwrite (filename, value, name)
  persistent indent = "";
  persistent separator = "\n";

  ## Check argument number
  nb = 0;
  if (nargin < 2) || (nargin > 3)
    print_usage;
  endif
  
  ## Get the file identificator
  isopen = false;
  if ischar(filename)

    ## Check file name
    sn = char (strsplit (filename, "."));
    if !strcmp(tolower(deblank(sn(end,:))), "xml")
      filename = [filename, ".xml"];
    endif

    ## Open file
    fd = fopen (filename, "w");
    if fd <= 0
      error("xmlwrite: error opening file \"%s\"\n", filename);
    endif

    ## XML header
    fprintf (fd, "<?xml version=\"1.0\"?>\n");
    fprintf (fd, "<!DOCTYPE octave SYSTEM \"octave.dtd\">\n");
    fprintf (fd, "<octave>\n");
    indent = "  ";
  else
    isopen = true;
    fd = filename;
  endif
  
  ## Store name in optional argument
  opt = "";
  if nargin == 3
    opt = sprintf(" name=\"%s\"", name);
  endif
  
  ## Process by type

  if ischar(value) && (rows(value) <= 1)
    ## String type
    
    fprintf (fd, "%s<string%s length=\"%d\">%s</string>%s",
             indent, opt, length(value), value, separator);
    
  elseif ischar(value)
    ## String array type
    
    fprintf (fd, "%s<array%s rows=\"%d\">\n", indent, opt, rows(value));
    _indent = indent; indent = [indent, "  "];
    for k=1:rows(value),
      nb += xmlwrite (fd, deblank(value(k, :)));
    endfor
    indent = _indent;
    fprintf (fd, "%s</array>\n", indent);
    
  elseif isscalar(value)
    ## Scalar type
    
    if iscomplex(value)
      ## Complex type

      fprintf (fd, "%s<complex%s>", indent, opt);
      _indent = indent; indent = ""; _separator = separator; separator = "";
      nb += xmlwrite (fd, real(value));
      nb += xmlwrite (fd, imag(value));
      indent = _indent; separator = _separator;
      fprintf (fd, "</complex>%s", separator);

    elseif isbool(value)
      ## Boolean type
    
      if value
        fprintf (fd, "%s<scalar%s value=\"true\"/>%s", indent, opt, separator);
      else
        fprintf (fd, "%s<scalar%s value=\"false\"/>%s", indent, opt, separator);
      endif
    
    elseif isinf(value)
      ## Infinite type
    
      if value > 0
        fprintf (fd, "%s<scalar%s value=\"inf\"/>%s",
                 indent, opt, separator);
      else
        fprintf (fd, "%s<scalar%s value=\"neginf\"/>%s",
                 indent, opt, separator);
      endif
    
    elseif isnan(value)
      ## Not-A-Number type
      
      fprintf (fd, "%s<scalar%s value=\"nan\"/>%s", indent, opt, separator);
      
    elseif isna(value)
      ## Not-Avaliable
      
      fprintf (fd, "%s<scalar%s value=\"na\"/>%s", indent, opt, separator);
      
    else
      sc = sprintf(sprintf("%%.%dg", save_precision), value);
      fprintf (fd, "%s<scalar%s>%s</scalar>%s", indent, opt, sc, ...
               separator);
    endif
    
  elseif ismatrix(value) && isnumeric(value) && (length(size(value)) <= 2)
    ## Matrix type
    
    fprintf (fd, "%s<matrix%s rows=\"%d\" columns=\"%d\">\n",
             indent, opt, rows(value), columns(value));
    _indent = indent; indent = ""; separator = "";
    for k=1:rows(value),
      fprintf (fd, "%s  ", _indent);
      for l=1:columns(value)-1,
        nb += xmlwrite (fd, value(k, l));
        fprintf (fd, " ");
      endfor
      nb += xmlwrite (fd, value(k, end));
      fprintf (fd, "\n");
    endfor
    indent = _indent; separator = "\n";
    fprintf (fd, "%s</matrix>\n", indent);
    
  elseif isstruct(value)
    ## Structure type

    st = fieldnames(value);
    fprintf (fd, "%s<structure%s>\n", indent, opt);
    _indent = indent; indent = [indent, "  "];
    for k=1:length(st),
      eval(sprintf("nb += xmlwrite (fd, value.%s, \"%s\");", st{k}, st{k}));
    endfor
    indent = _indent;
    fprintf (fd, "%s</structure>\n", indent);
    
  elseif iscell(value)
    ## Cell type
    
    fprintf (fd, "%s<cell%s rows=\"%d\" columns=\"%d\">\n",
             indent, opt, rows(value), columns(value));
    _indent = indent; indent = [indent, "  "];
    for k=1:rows(value),
      for l=1:columns(value),
        nb += xmlwrite (fd, value{k, l});
      endfor
    endfor
    indent = _indent;
    fprintf (fd, "%s</cell>\n", indent);
    
  elseif islist(value)
    ## List type
    
    fprintf (fd, "%s<list%s length=\"%d\">\n", indent, opt, length(value));
    _indent = indent; indent = [indent, "  "];
    for k=1:length(value),
      nb += xmlwrite (fd, value{k});
    endfor
    indent = _indent;
    fprintf (fd, "%s</list>\n", indent);
    
  else
    ## Unknown type
    error("xmlwrite: unknown type\n");
  endif
  nb++;
  
  if !isopen
    fprintf (fd, "</octave>\n");
    fclose(fd);
  endif
  
endfunction
