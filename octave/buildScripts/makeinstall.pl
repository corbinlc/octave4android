#!/usr/bin/perl -w

#print tree to file
$result = `tree -aFfi $ARGV[0]/freeRoot > /tmp/tree.tmp`;
$result = `rm -rf installdir`;
$result = `mkdir installdir`;

#read file in and process it
open(MYINPUTFILE, '</tmp/tree.tmp');
open(INSTALLDIRFILE, '>installdir/lib__install_dir.so') or die "Can't write to file\n";
open(INSTALLFILEFILE, '>installdir/lib__install_file.so') or die "Can't write to file\n";
open(INSTALLLINKFILE, '>installdir/lib__install_link.so') or die "Can't write to file\n";

$libcnt = 0;
$linecnt = 0;
while (<MYINPUTFILE>) {
   $line = $_;
   chomp($line);
   $line =~ s/\*//g;

   if ($linecnt > 0) {
      if ($line =~ /^\//) {
         if ($line =~ / -> /) {	
            #link found in tree
            @link = split(/ -> /, $line);
            @path = split(/\//,$link[0]);
            $tempString = '';
            $pathCount = 0;
            foreach (@path) {
               if ($pathCount > 0) {
                  print INSTALLLINKFILE "$tempString/";
 	          $tempString = $_;
               }
               $pathCount++;
            } 
            print INSTALLLINKFILE "$link[1] $link[0]\n";
         } elsif ($line =~ /\/$/) {
            #directory found in tree
            print INSTALLDIRFILE "$line\n";
         } else {
            #file found in tree
            $result = `cp $line installdir/lib__file$libcnt.so`;
            print INSTALLFILEFILE "$ARGV[0]/lib/lib__file$libcnt.so $line\n";
            $libcnt++;
         } 
      }
   } else {
      print INSTALLDIRFILE "$line\n";
   }
   $linecnt++;
}
close(MYINPUTFILE);
close(INSTALLDIRFILE);
close(INSTALLFILEFILE);
close(INSTALLLINKFILE);
