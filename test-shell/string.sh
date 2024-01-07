#!/bin/bash

A="hello"
B="world"

if [ "${A}" == "${B}" ]; then
	echo "TRUE"
else
	echo "FALSE"
fi

B="hello"
if [ "${A}" != "${B}" ]; then
        echo "FALSE"
else
        echo "TRUE"
fi

B="hi"
if [ "${A}" \> "${B}" ]; then
        echo "TRUE"
else
        echo "FALSE"
fi

C=""
if [ -z "${C}" ]; then
        echo "\$C is null"
else
        echo "\$C is not null"
fi

if [ -n "${C}" ]; then
        echo "\$C is not null"
else
        echo "\$C is null"
fi
