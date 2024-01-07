#!/bin/bash

if [ -e /etc/passwd ]; then
	echo "exists file"
else 
	echo "not exists file"
fi

if [ -d /etc/passwd ]; then
  echo "is dir"
else 
  echo "is not dir"
fi

if [ -d /etc ]; then
  echo "is dir"
else 
  echo "is not dir"
fi
