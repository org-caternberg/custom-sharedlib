#! /bin/bash
set -xeu


echo $(WORKSPACE)
ls -ltra
mvn -version
exit $?