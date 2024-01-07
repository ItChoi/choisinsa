#!/bin/bash

for i in {2..9} 
do
  for (( j=1; j < 10; j++ ))
  do
    ((result=i*j))
    if [ $result -lt 30 ]; then
	echo "$i x $j = $result"
    else
	echo "$i x $j = ..... give up" 
        break
    fi
  done
  echo ""
done
