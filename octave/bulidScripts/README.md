octave4android build scripts
============================

For building all of the packages, except the main packages, use the makeOctavePackages*.sh scripts.

This generates two .tar.gz files.  They get renamed to have a .mp3 extension and put in the assets folder of the associated android app.

For building the main package, I don't have a script of my own (I should, but only had to do this once so far).  I have done it manually.  First I use debootstrap to create a debian rootfs with Octave as an included package.  I then run the same linkify script that is used in the makeOctavePackages*.sh.  I rename the two outputs to be .obb files, but really are tar.gz files, and put them in the obb directory for the associated app.

In the main package assets directory the is the following:
1) noexec_custom.mp3 - a tar.gz file of changes I want to make to rootfs after it is unpacked.
2)busybox.mp2 - the statically compile busybox pulled from the debian wheezy busybox staic .deb.
3) proot.mp2 - proot built from https://github.com/corbinlc/PRoot/commit/f0ce438853562b70b03d9001dcdb4ac9ee8925f9
