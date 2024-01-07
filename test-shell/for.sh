#!/bin/bash

COLORS="red orange yellow black while"
for color in $COLORS
do
	echo "this is $color"
	echo "$color is beautiful"
	echo "---------"
done

for index in {0..11}
do
  echo "index is $index"
  echo "---------"
done

for index in {0..13...2}
do
  echo "i is +3 $index"
done


for ((index=0; index < 10; index+=3))
do
  echo "@@index is $index"
done

a=100
echo "a is $a"

let 'a++'
echo "a is $a"

let 'a=a+a'
echo "a is $a"
