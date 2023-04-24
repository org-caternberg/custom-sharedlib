#! /bin/bash
export HW_RESULT="Hello $1"
echo $HW_RESULT
echo "Hello $1" || exit 1

