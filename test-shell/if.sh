#!/bin/bash

ABC=123
declare -i BCD="123"

if [ $ABC -eq $BCD ]; then
        echo "true"
else
        echo "false"
fi

if [ $ABC == "123" ]; then
        echo "true"
else
        echo "false"
fi

if [ $ABC -gt 122 ]; then
        echo "true"
else
        echo "false"
fi




























