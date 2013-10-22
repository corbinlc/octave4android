# Filename:    Makefile
# Description: Makefile for the doc directory of the OdePkg
# ChangeLog: 20070222, this Makefile was originally be created
#    from the Makefile of the comm package. Modifications have
#    been done to create the documentation of the OdePkg.

sinclude ../../../Makeconf

# Fill in the variables as it makes testing the package manager easier
ifeq ($(MKDOC),)
MKDOC = ../../../admin/mkdoc
MKTEXI = ../../../admin/mktexi
MAKEINFO = makeinfo --no-split --document-language=en_us
TEXI2DVI = texi2dvi --clean
DVIPS = dvips
LN_S = ln -s
endif

INFODOC = odepkg.info
PSDOC   = $(patsubst %.info, %.ps, $(INFODOC))
PDFDOC  = $(patsubst %.info, %.pdf, $(INFODOC))
HTMLDOC = $(patsubst %.info, %.html, $(INFODOC))
TEXIDOC = $(patsubst %.info, %.txi, $(INFODOC))
DOCS = $(INFODOC) $(PDFDOC)  
DOCSTRINGS = DOCSTRINGS
INDEX = ../INDEX
TMPDELETES = *.log *.dvi $(DOCSTRINGS) $(TEXIDOC) *~
DELETES = $(TMPDELETES) *.ps *.pdf *.info $(DOCS) *.html odepkg/ html/

all : $(PDFDOC) $(HTMLDOC) ../inst/doc.info
.PHONY : all

../inst/doc.info : $(INFODOC)
	cp -f $(INFODOC) ../inst/doc.info

ifeq (,$(TEXI2PDF))
%.pdf : %.dvi
	@if test "x$(TEXI2DVI)" != "x" && test "x$(DVIPDF)" != "x"; then \
	  echo "Making pdf $@"; \
	  $(DVIPDF) $< ; \
	fi

%.dvi : %.texi
	@if test "x$(TEXI2DVI)" != "x"; then \
	  echo "Making dvi $@"; \
	  TEXINPUTS="./:$../../..:$(TEXINPUTS):"; \
          export TEXINPUTS; \
	  $(TEXI2DVI) $< ; \
	fi

%.ps : %.dvi 
	@if test "x$(TEXI2DVI)" != "x" && test "x$(DVIPS)" != "x"; then \
	  echo "Making postscript $@"; \
	  $(DVIPS) -o $@ $< ; \
	fi
else
%.pdf : %.texi
	@if test "x$(TEXI2PDF)" != "x"; then \
	  echo "Making pdf $@"; \
	  TEXINPUTS="./:../../..:$(TEXINPUTS):"; \
          export TEXINPUTS; \
	  $(TEXI2PDF) $< ; \
	fi
endif

%.info : %.texi
	@if test "x$(MAKEINFO)" != "x"; then \
	  echo "Making info $@"; \
	  $(MAKEINFO) -I./ -I../../../ $< ; \
	fi

%.html : %.texi
	@if test "x$(MAKEINFO)" != "x"; then \
	  echo "Making html $@"; \
	  $(MAKEINFO) --html -I./ -I../../../ $< ; \
	fi

%.texi : %.txi
	$(RM) -f $(DOCSTRINGS); \
	$(MKDOC) ../ > $(DOCSTRINGS); \
	$(MKTEXI) $< $(DOCSTRINGS) $(INDEX) > $@ ; \
	$(RM) -f $(DOCSTRINGS);

clean :
	@echo "Cleaning..."; \
	$(RM) -fr $(DELETES) ../inst/doc.info;
.PHONY : clean

realclean : clean
.PHONY : realclean

distclean : clean
.PHONY : distclean

dist : all
.PHONY : dist
