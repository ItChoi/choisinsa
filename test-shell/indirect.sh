#!/usr/local/bin/bash

ABC="hello"
XYZ="world"

indirect="ABC"

echo "$ABC"
echo "$XYZ"

echo ${!indirect}
