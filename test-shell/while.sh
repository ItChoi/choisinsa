#!/bin/bash

index=0;
while [ $index -lt 10 ] # index가 10보다 작을때
do
  echo "index is $index"
  # let 'index++'
  ((index++))
done

index=0;
until [ $index -eq 10 ] 
do
  echo "index is $index"
  let 'index++'
done

echo "-----"
index=0;
while [ $index -lt 10 ] # index가 10보다 작을때
do
  echo "index is $index"
  # let 'index++'
  ((index++))
  echo "now index is $index"
  if [ $index -eq 5 ]; then
      break;
  fi
done
