#!/bin/bash

traverse()
{
  # Traverse a directory
  
  ls "$1" | while read i
  do
    if [ "$1" == "." ]; then
      if [ "$i" = "dev" ]; then
        continue
      fi
      if [ "$i" == "mnt" ]; then
        continue
      fi
      if [ "$i" == "proc" ]; then
        continue
      fi
      if [ "$i" == "host-rootfs" ]; then
        continue
      fi
      if [ "$i" == "noexec" ]; then
        continue
      fi
      if [[ "$i" == *.deb ]]; then
        continue
      fi
    fi
    if [ -L "$1/$i" ]; then
      continue
    elif [ -d "$1/$i" ]; then
      traverse "$1/$i"
    elif [ -x "$1/$i" ]; then
      continue
    elif (file "$1/$i" | grep ELF); then
      continue
    else
      #make directory to move things
      mkdir -p "/noexec/$1"
      #copy files/dirs to noexec
      cp "$1/$i" "/noexec/$1/"
      #remove original files/dirs
      rm -f "$1/$i" 
      #create link to noexec
      ln -sf "/noexec/$1/$i" "$1/$i"
    fi
  done
}

if [ -z "$1" ]; then
  traverse .
else
  traverse "$1"
fi
