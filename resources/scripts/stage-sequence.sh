#! /bin/bash

helloworld.sh | xargs  jq-sample-step.sh
JSON=$(script)
script2 $JSON