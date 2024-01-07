#!/bin/bash

A="111"
B="222"
C="333"

if [ "$A" -gt "$B" ] && [ "$A" -gt "$C" ]; then
	echo "a is most num"
else
	echo "a is not most num"
fi


if [ "$A" -lt "$B" -a "$A" -lt "$C" ]; then
        echo "a is most num"
else
        echo "a is not most num"
fi
