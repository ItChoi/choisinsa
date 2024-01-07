#!/bin/bash

function test1() {
  if [ -z "${1}" ]; then
    echo "param1 is missing"
    return 1
  fi
  if [ -z "${2}" ]; then
    echo "param2 is missing"
    return 2
  fi
  param1="$1"
  param2="$2"
  echo "this is test1"
  echo "first param is $param1"
  echo "second param is $param2"
  echo "first param is $1"
  echo "second param is $2"

  return 40
}
# test1 "hello" "world"
test1 "hello"
result="$?"

result=$(test1 "hello" "world")
echo "test1 returns \"$result\""
echo "test1 returns $result"

echo "test123: $?"

result=$(ls)
echo "result ls: \"$result\""
