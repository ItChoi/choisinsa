#!/usr/local/bin/bash

declare -A ARRAY
ARRAY=([a]=hello [b]=world [c]=three [d]=1234)
echo "number count is ${#ARRAY[@]}"

for arr in "${ARRAY[@]}"
do
  echo "array element \"$arr\""
done

echo "[a] element: ${ARRAY[a]}"
echo "[b] element: ${ARRAY[b]}"
echo "[c] element: ${ARRAY[c]}"
echo "[d] element: ${ARRAY[d]}"
