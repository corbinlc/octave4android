#!/bin/bash
pkg[0]='http://ftp.us.debian.org/debian/pool/main/o/octave-control/octave-control_2.3.52-1_i386.deb'
pkg[1]='http://ftp.us.debian.org/debian/pool/main/o/octave-financial/octave-financial_0.4.0-1_all.deb'
pkg[2]='http://ftp.us.debian.org/debian/pool/main/o/octave-io/octave-io_1.0.19-1_i386.deb'
pkg[3]='http://ftp.us.debian.org/debian/pool/main/o/octave-mapping/octave-mapping_1.0.7-4_all.deb'
pkg[4]='http://ftp.us.debian.org/debian/pool/main/o/octave-missing-functions/octave-missing-functions_1.0.2-4_all.deb'
pkg[5]='http://ftp.us.debian.org/debian/pool/main/o/octave-odepkg/octave-odepkg_0.8.2-2_i386.deb'
pkg[6]='http://ftp.us.debian.org/debian/pool/main/o/octave-optim/octave-optim_1.2.0-1_i386.deb'
pkg[7]='http://ftp.us.debian.org/debian/pool/main/o/octave-signal/octave-signal_1.1.3-1_i386.deb'
pkg[8]='http://ftp.us.debian.org/debian/pool/main/o/octave-specfun/octave-specfun_1.1.0-1_i386.deb'
pkg[9]='http://ftp.us.debian.org/debian/pool/main/o/octave-statistics/octave-statistics_1.1.3-1_all.deb'
pkg[10]='http://ftp.us.debian.org/debian/pool/main/o/octave-symbolic/octave-symbolic_1.1.0-1_i386.deb http://ftp.us.debian.org/debian/pool/main/g/ginac/libginac2_1.6.2-1_i386.deb http://ftp.us.debian.org/debian/pool/main/c/cln/libcln6_1.3.2-1.2_i386.deb'
pkg[11]='http://ftp.us.debian.org/debian/pool/main/g/gnuplot/gnuplot-nox_4.6.0-8_i386.deb http://ftp.us.debian.org/debian/pool/main/f/fontconfig/fontconfig_2.9.0-7.1_i386.deb http://ftp.us.debian.org/debian/pool/main/libb/libbsd/libbsd0_0.4.2-1_i386.deb http://ftp.us.debian.org/debian/pool/main/c/cairo/libcairo2_1.12.2-3_i386.deb http://ftp.us.debian.org/debian/pool/main/libd/libdatrie/libdatrie1_0.2.5-3_i386.deb http://ftp.us.debian.org/debian/pool/main/libe/libedit/libedit2_2.11-20080614-5_i386.deb http://ftp.us.debian.org/debian/pool/main/libg/libgd2/libgd2-noxpm_2.0.36~rc1~dfsg-6.1_i386.deb http://ftp.us.debian.org/debian/pool/main/l/lua5.1/liblua5.1-0_5.1.5-4_i386.deb http://ftp.us.debian.org/debian/pool/main/p/pango1.0/libpango1.0-0_1.30.0-1_i386.deb http://ftp.us.debian.org/debian/pool/main/p/pixman/libpixman-1-0_0.26.0-4+deb7u1_i386.deb http://ftp.us.debian.org/debian/pool/main/libt/libthai/libthai-data_0.1.18-2_all.deb http://ftp.us.debian.org/debian/pool/main/libt/libthai/libthai0_0.1.18-2_i386.deb http://ftp.us.debian.org/debian/pool/main/libx/libxcb/libxcb-render0_1.8.1-2+deb7u1_i386.deb http://ftp.us.debian.org/debian/pool/main/libx/libxcb/libxcb-shm0_1.8.1-2+deb7u1_i386.deb http://ftp.us.debian.org/debian/pool/main/f/fonts-liberation/fonts-liberation_1.07.2-6_all.deb'

dir[0]='octaveControlx86Release'
dir[1]='octaveFinancialAllRelease'
dir[2]='octaveIOx86Release'
dir[3]='octaveMappingAllRelease'
dir[4]='octaveMissingAllRelease'
dir[5]='octaveODEx86Release'
dir[6]='octaveOptimx86Release'
dir[7]='octaveSignalx86Release'
dir[8]='octaveSpecfunx86Release'
dir[9]='octaveStatisticsAllRelease'
dir[10]='octaveSymbolicx86Release'
dir[11]='octaveGnuplotx86Release'

i=0
for dir in "${dir[@]}"
do
  if [ -d "$dir" ]; then
    rm -rf "$dir"
  fi
  mkdir "$dir"
  cd "$dir"
  debArray=( ${pkg[$i]} )
  for deb in "${debArray[@]}"
  do
    wget "$deb"
  done
  for deb in *.deb; do
    dpkg -x "$deb" .
    rm "$deb"
  done
  /home/corbin/linkify_new2.sh .
  tar -cvzf exec.mp3 --hard-dereference *
  tree -aFfi . > exec_files.txt
  mv /noexec .
  cd noexec
  tar -cvzf ../noexec.mp3 --hard-dereference *
  tree -aFfi . > ../noexec_files.txt
  cd ..
  cd ..
  i=$((i+1))
done
