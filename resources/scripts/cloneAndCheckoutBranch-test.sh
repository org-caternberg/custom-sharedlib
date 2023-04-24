#!/usr/bin/env bash


[ -d .tmp/ ] && rm -Rf .tmp || mkdir  .tmp
export REPO_URL=https://github.com/pipeline-demo-caternberg/maven-executable-jar-example.git
export REPO_BRANCH=master
export GIT_USERNAME=cccaternberg
export GIT_PASSWORD="!develop01"
export GIT_CLONE_DIR=.tmp/app
export GIT_SHALLOW_DEPTH=1

./cloneAndCheckoutBranch.sh