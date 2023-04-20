#! /bin/bash


docker buildx build \
    --platform linux/amd64 \
    -t caternberg/ci-toolbox .

docker push caternberg/ci-toolbox