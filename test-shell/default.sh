#!/usr/local/bin/bash
echo "${STR:-hello}"
echo "${STR:=hi}"
echo "${STR:-hello}"

echo "${HAHA:?_error_}"
