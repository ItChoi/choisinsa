#!/bin/bash


for i in {2..9}
do
	for j in {1..9}
	do
		((result=i*j))
		if [ $result -gt 30 ]; then
			echo "$i x $j = $result"
		fi
	done
	echo ""

done






